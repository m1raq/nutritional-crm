package ru.app.nutritionologycrm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.app.nutritionologycrm.dto.ResponseMessageDTO;

@ControllerAdvice
public class RestAPIExceptionHandler {


    @ExceptionHandler(value = {EntityProcessingException.class, DocumentProcessingException.class})
    public ResponseEntity<ResponseMessageDTO> handleEntityProcessingException(RuntimeException e) {
        return new ResponseEntity<>(ResponseMessageDTO.builder()
                .success(false)
                .message(e.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }


}
