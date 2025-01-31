package ru.app.nutritionologycrm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.app.nutritionologycrm.dto.client.ClientDTO;
import ru.app.nutritionologycrm.dto.medical.history.MedicalHistoryUpdateRequestDTO;
import ru.app.nutritionologycrm.frontend.page.client.medical.history.MedicalHistoryView;
import ru.app.nutritionologycrm.service.ClientService;
import ru.app.nutritionologycrm.service.MedicalHistoryService;

import java.util.Arrays;

@Service
public class BotService extends TelegramLongPollingBot {

    private final String token;

    private final String botUsername;

    private final MedicalHistoryService medicalHistoryService;

    private final ClientService clientService;

    private static MedicalHistoryView medicalHistoryView;

    @Autowired
    public BotService(TelegramBotsApi bot, @Value("${bot.token}") String token
            , @Value("${bot.username}") String botUsername, MedicalHistoryService medicalHistoryService
            , ClientService clientService) throws TelegramApiException {
        this.token = token;
        this.botUsername = botUsername;
        this.medicalHistoryService = medicalHistoryService;
        this.clientService = clientService;

        bot.registerBot(this);
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage()) {
            if (update.getMessage().hasText()) {
                String message = update.getMessage().getText();
                String chatId = update.getMessage().getChatId().toString();

                if (message.matches("/start anamnes-[0-9]+")){
                    String clientId = message.split("-")[1];

                    if (!clientService.existsByTgBotChatId(clientId)) {
                        ClientDTO clientDTO = clientService.findById(Long.valueOf(clientId));
                        clientDTO.setTgBotChatId(update.getMessage().getChatId().toString());
                        clientService.updateClient(clientDTO);
                    }

                    sendMessage("""
                           Здравствуйте! Для заполнения анамнеза отправьте сообщение в следующем формате (
                           ❗️ Все поля должны быть заполнены
                           ❗️ Лишних пробелов быть не должно
                           ❗️ Символы строго соблюдены
                           ❗️ Каждое поле с новой строки
                           ❗️ В блоке [ВАШ ТЕКСТ] должны отсутствовать переносы на новую строку и знаки '-' ):
                           
                           
                           
                           
                           Антропометрия - [ВАШ ТЕКСТ]
                           Режим жизни - [ВАШ ТЕКСТ]
                           Жалобы - [ВАШ ТЕКСТ]
                           Гипотезы - [ВАШ ТЕКСТ]
                           Питание - [ВАШ ТЕКСТ]
                           Питьевой режим - [ВАШ ТЕКСТ]
                           Физическая активность - [ВАШ ТЕКСТ]
                           Цели - [ВАШ ТЕКСТ]
                           Особые состояния - [ВАШ ТЕКСТ]
                           """
                            , update.getMessage().getChatId().toString());
                } else if (message.startsWith("Антропометрия -")){
                    String[] lines = message.split("\n");

                    MedicalHistoryUpdateRequestDTO medicalHistory = MedicalHistoryUpdateRequestDTO.builder().build();
                    medicalHistory.setId(clientService
                            .findByTgBotChatId(update.getMessage().getChatId().toString())
                            .getMedicalHistory()
                            .getId());

                    Arrays.asList(lines).forEach(line -> {
                        String clientText = line.split("-")[1].trim();
                        switch (line.split("-")[0].trim()) {
                            case "Антропометрия" -> medicalHistory.setAnthropometry(clientText);
                            case "Режим жизни" -> medicalHistory.setLifeMode(clientText);
                            case "Жалобы" -> medicalHistory.setComplaints(clientText);
                            case "Гипотезы" -> medicalHistory.setHypotheses(clientText);
                            case "Питание" -> medicalHistory.setNutrition(clientText);
                            case "Питьевой режим" -> medicalHistory.setDrinkingMode(clientText);
                            case "Физическая активность" -> medicalHistory.setPhysicalActivity(clientText);
                            case "Цели" -> medicalHistory.setGoals(clientText);
                            case "Особые состояния" -> medicalHistory.setSpecialConditions(clientText);
                        }
                    });

                    medicalHistoryService.updateMedicalHistory(medicalHistory);
                    medicalHistoryView.showUpdateNotification();

                    sendMessage("Ваш ответ записан", chatId);
                }
            }
        }

    }

    private void sendMessage(String message, String chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public static void initMedicalHistoryView(MedicalHistoryView view) {
        medicalHistoryView = view;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return token;
    }

}
