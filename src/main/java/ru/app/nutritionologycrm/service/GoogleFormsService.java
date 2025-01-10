package ru.app.nutritionologycrm.service;

public interface GoogleFormsService {

    String createForm(String token, String formTitle);

    void createQuestion(String token, String formId, String question);
}
