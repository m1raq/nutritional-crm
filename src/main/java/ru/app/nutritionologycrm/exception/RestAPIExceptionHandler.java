package ru.app.nutritionologycrm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.app.nutritionologycrm.dto.ResponseMessage;

@ControllerAdvice
public class RestAPIExceptionHandler {


    @ExceptionHandler(value = {EntityProcessingException.class, DocumentProcessingException.class})
    public ResponseEntity<ResponseMessage> handleEntityProcessingException(RuntimeException e) {
        return new ResponseEntity<>(ResponseMessage.builder()
                .success(false)
                .message(e.getMessage())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
