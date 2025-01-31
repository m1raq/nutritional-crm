package ru.app.nutritionologycrm.frontend.page.client.meet;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.dialog.DialogVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.vaadin.stefan.fullcalendar.*;
import ru.app.nutritionologycrm.dto.client.ClientDTO;
import ru.app.nutritionologycrm.dto.meet.MeetCreateRequestDTO;
import ru.app.nutritionologycrm.dto.meet.MeetDTO;
import ru.app.nutritionologycrm.dto.meet.MeetUpdateRequestDTO;
import ru.app.nutritionologycrm.frontend.VaadinComponentBuilder;
import ru.app.nutritionologycrm.service.MeetService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;


@Setter
@Getter
public class MeetForm extends HorizontalLayout {

    private FullCalendar calendar;

    private final MeetService meetService;

    private static SecurityContext securityContext;

    private static ClientDTO actualClient;

    private List<MeetDTO> meets;

    public MeetForm(MeetService meetService) {
        this.meetService = meetService;
        meets = meetService.findAllByClientId(actualClient.getId());

        setWidth("1000px");
        setAlignItems(FlexComponent.Alignment.CENTER);
        calendar = FullCalendarBuilder.create().build();
        calendar.setLocale(Locale.ENGLISH);
        calendar.setWidth("800px");
        calendar.setHeight("600px");

        meetService.findAllByClientId(actualClient.getId());
        meets.forEach(meet -> {
            Entry tableEntry = new Entry();
            tableEntry.setTitle(meet.getPlace());
            tableEntry.setColor("#ff3333");
            tableEntry.setStart(meet.getStart());
            tableEntry.setEnd(meet.getEnd());

            calendar.getEntryProvider().asInMemory().addEntries(tableEntry);
            calendar.getEntryProvider().refreshAll();
        });

        calendar.addTimeslotClickedListener(event -> {

            Dialog dialog = new Dialog();
            TextField placeField = new TextField("Место");
            TimePicker startField = new TimePicker("С");
            TimePicker endField = new TimePicker("До");

            VerticalLayout dialogLayout = createDialogLayout(startField, endField, placeField);

            Button close = createDialogBackButton(dialog);
            Button save = createDialogSaveButton(event, startField, endField, placeField, dialog);

            dialog.add(dialogLayout);
            dialog.getFooter().add(save, close);
            dialog.setHeaderTitle("Назначить встречу");
            dialog.setCloseOnEsc(true);
            dialog.setCloseOnOutsideClick(true);
            dialog.addThemeVariants(DialogVariant.LUMO_NO_PADDING);
            dialog.setWidth("500px");

            dialog.open();
        });

        calendar.addEntryClickedListener(event -> {
            MeetDTO meetDTO = meets.stream()
                    .filter(meet -> meet.getStart().isEqual(event.getEntry().getStart()))
                    .findFirst()
                    .orElseThrow();

            Dialog dialog = new Dialog();

            TextField placeField = new TextField("Место");
            placeField.setValue(event.getEntry().getTitle());

            TimePicker startField = new TimePicker("С");
            startField.setValue(event.getEntry().getStart().toLocalTime());

            TimePicker endField = new TimePicker("До");
            endField.setValue(event.getEntry().getEnd().toLocalTime());

            VerticalLayout dialogLayout = createDialogLayout(startField, endField, placeField);

            Button close = createDialogBackButton(dialog);
            Button save = createDialogSaveButton(event, meetDTO, startField, endField, placeField, dialog);
            Button delete = createDialogDeleteButton(meetDTO, event, dialog);

            dialog.add(dialogLayout);
            dialog.getFooter().add(save, delete, close);
            dialog.setCloseOnEsc(true);
            dialog.setCloseOnOutsideClick(true);
            dialog.addThemeVariants(DialogVariant.LUMO_NO_PADDING);
            dialog.setWidth("500px");
            dialog.open();
        });

        add(calendar);

    }

    private Button createDialogBackButton(Dialog dialog) {
        Button close = new Button("Назад");
        close.addClickListener(buttonClick -> dialog.close());
        return close;
    }

    private Button createDialogDeleteButton(MeetDTO meetDTO, EntryClickedEvent event, Dialog dialog) {
        Button delete = new Button("Удалить");
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        delete.addClickListener(buttonClick -> {
            meetService.delete(meetDTO.getId());
            meets = meetService.findAllByClientId(actualClient.getId());

            calendar.getEntryProvider().asInMemory().removeEntry(event.getEntry());
            calendar.getEntryProvider().refreshAll();

            dialog.close();
        });
        return delete;
    }

    private Button createDialogSaveButton(TimeslotClickedEvent event, TimePicker startField
            , TimePicker endField, TextField placeField, Dialog dialog) {
        Button save = new Button("Сохранить");
        save.addClickListener(buttonClick -> {
            SecurityContextHolder.setContext(securityContext);

            LocalDateTime meetStart = event.getDate().atStartOfDay()
                    .plusHours(startField.getValue().getHour());
            LocalDateTime meetEnd = event.getDate().atStartOfDay()
                    .plusHours(endField.getValue().getHour());

            Entry tableEntry = new Entry();
            tableEntry.setTitle(placeField.getValue());
            tableEntry.setColor("#ff3333");

            tableEntry.setStart(meetStart);
            tableEntry.setEnd(meetEnd);

            try {
                meetService.save(MeetCreateRequestDTO.builder()
                                .duration(String.valueOf(meetEnd.getHour() - meetStart.getHour()))
                                .place(placeField.getValue())
                                .end(meetEnd)
                                .start(meetStart)
                                .build()
                        , actualClient.getId()
                        , SecurityContextHolder.getContext().getAuthentication().getName());
                meets = meetService.findAllByClientId(actualClient.getId());

                calendar.getEntryProvider().asInMemory().addEntries(tableEntry);
                calendar.getEntryProvider().refreshAll();
            } catch (Exception e) {
                Notification notification = VaadinComponentBuilder
                        .buildNotification("Встреча на данное время уже существует"
                                , NotificationVariant.LUMO_ERROR);
                notification.open();
            } finally {
                dialog.close();
            }
        });
        return save;
    }

    private Button createDialogSaveButton(EntryClickedEvent event, MeetDTO meetDTO, TimePicker startField
            , TimePicker endField, TextField placeField, Dialog dialog) {
        Button save = new Button("Сохранить");
        save.addClickListener(buttonClick -> {
            SecurityContextHolder.setContext(securityContext);

            LocalDateTime meetStart = event.getEntry().getStart().minusHours(event.getEntry().getStart().getHour())
                    .plusHours(startField.getValue().getHour());
            LocalDateTime meetEnd = event.getEntry().getEnd().minusHours(event.getEntry().getEnd().getHour())
                    .plusHours(endField.getValue().getHour());

            Entry tableEntry = new Entry();
            tableEntry.setTitle(placeField.getValue());
            tableEntry.setColor("#ff3333");

            tableEntry.setStart(meetStart);
            tableEntry.setEnd(meetEnd);


            try {
                meetService.update(MeetUpdateRequestDTO.builder()
                                .duration(String.valueOf(meetEnd.getHour() - meetStart.getHour()))
                                .start(meetStart)
                                .end(meetEnd)
                                .id(meetDTO.getId())
                                .build()
                        , SecurityContextHolder.getContext().getAuthentication().getName());
                meets = meetService.findAllByClientId(actualClient.getId());

                calendar.getEntryProvider().asInMemory().removeEntries(event.getEntry());
                calendar.getEntryProvider().asInMemory().addEntries(tableEntry);
                calendar.getEntryProvider().refreshAll();
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                dialog.close();
            }
        });
        return save;
    }

    private VerticalLayout createDialogLayout(Component... components) {
        VerticalLayout dialogLayout = new VerticalLayout(components);
        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "18rem").set("max-width", "100%");
        return dialogLayout;
    }

    public static void initSecurity(SecurityContext context) {
        securityContext = context;
    }

    public static void initActualClient(ClientDTO dto) {
        actualClient = dto;
    }

}
