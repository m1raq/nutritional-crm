package ru.app.nutritionologycrm.service;

import java.util.List;

public interface GoogleFormsService {

    String createForm(String token, String formTitle);

    void createQuestion(String formId, String questionText);

    void createQuestion(String formId, List<String> questionTexts);

}
