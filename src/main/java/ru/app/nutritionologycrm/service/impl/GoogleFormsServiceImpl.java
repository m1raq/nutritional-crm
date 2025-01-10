package ru.app.nutritionologycrm.service.impl;

import com.google.api.services.forms.v1.Forms;
import com.google.api.services.forms.v1.model.Form;
import com.google.api.services.forms.v1.model.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.app.nutritionologycrm.service.GoogleFormsService;

import java.io.IOException;


@Service
public class GoogleFormsServiceImpl implements GoogleFormsService {

    private final Forms formsService;

    @Autowired
    public GoogleFormsServiceImpl(Forms formsService) {
        this.formsService = formsService;
    }

    @Override
    public String createForm(String token, String formTitle) {
        Form form = new Form().setInfo(new Info().setTitle(formTitle));
        try {
            form = formsService.forms().create(form).execute();
            return form.getFormId();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createQuestion(String token, String formId, String question) {

    }
}
