package ru.app.nutritionologycrm.service.impl;

import com.google.api.services.forms.v1.Forms;
import com.google.api.services.forms.v1.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.app.nutritionologycrm.service.GoogleFormsService;

import java.io.IOException;
import java.util.List;


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
    public void createQuestion(String formId, String questionText) {
        Question question = new Question()
                .setRequired(true)
                .setTextQuestion(new TextQuestion()
                        .set(questionText, ""));

        Request request = new Request().setCreateItem(new CreateItemRequest()
                        .setItem(new Item()
                                .setQuestionItem(new QuestionItem()
                                        .setQuestion(question))));

        BatchUpdateFormRequest batchUpdateFormRequest = new BatchUpdateFormRequest()
                .setRequests(List.of(request));
        try {
            formsService.forms().batchUpdate(formId, batchUpdateFormRequest).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createQuestion(String formId, List<String> questionTexts) {
        questionTexts.forEach(question -> createQuestion(formId, question));
    }
}
