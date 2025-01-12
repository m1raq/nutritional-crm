package ru.app.nutritionologycrm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.app.nutritionologycrm.dto.ResponseMessage;
import ru.app.nutritionologycrm.entity.DocumentEntity;
import ru.app.nutritionologycrm.service.DocumentService;

import java.util.Date;
import java.util.List;

@RequestMapping("/api/v1/document")
@RestController
public class DocumentController {

    private final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> upload(@RequestParam MultipartFile file, @RequestParam Long clientId) {
        documentService.save(file, clientId);
        return new ResponseEntity<>(ResponseMessage.builder()
                .message("File is uploaded successfully")
                .success(true)
                .build()
                , HttpStatus.CREATED);
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam String fileName) {
        return new ResponseEntity<>(documentService.loadFile(fileName), HttpStatus.OK);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<DocumentEntity>> getAllFiles() {
        return new ResponseEntity<>(documentService.getAllFiles(), HttpStatus.OK);
    }

    @GetMapping("/get-by-name")
    public ResponseEntity<DocumentEntity> getFileByName(@RequestParam String fileName) {
        return new ResponseEntity<>(documentService.findByNameAndCurrentUser(fileName), HttpStatus.OK);
    }

    @GetMapping("/get-by-date")
    public ResponseEntity<List<DocumentEntity>> getFileByDate(@RequestParam Date date) {
        return new ResponseEntity<>(documentService.findByDateAndCurrentUser(date), HttpStatus.OK);
    }
}
