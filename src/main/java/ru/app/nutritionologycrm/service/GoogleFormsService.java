package ru.app.nutritionologycrm.service;

import java.util.List;

public interface GoogleFormsService {

    String createForm(String token, String formTitle);

    String createQuestion(String formId, String questionText);

    String createQuestion(String formId, List<String> questionTexts);

}
