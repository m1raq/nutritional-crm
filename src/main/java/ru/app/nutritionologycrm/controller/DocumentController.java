package ru.app.nutritionologycrm.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.app.nutritionologycrm.dto.DocumentDTO;
import ru.app.nutritionologycrm.dto.ResponseMessageDTO;
import ru.app.nutritionologycrm.service.DocumentService;

import java.util.Date;
import java.util.List;

@Tag(name = "Документы")
@SecurityRequirement(name = "JWT")
@RequestMapping("/api/v1/document")
@RestController
public class DocumentController {

    private final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @Operation(summary = "Загрузить документ на в систему"
            , parameters = {@Parameter(name = "file", description = "Загружаемый файл")
            , @Parameter(name = "clientId", description = "Id клиента к которому присвоен данный документ")})
    @PostMapping("/upload")
    public ResponseEntity<ResponseMessageDTO> upload(@RequestParam MultipartFile file, @RequestParam Long clientId) {
        documentService.save(file, clientId);
        return new ResponseEntity<>(ResponseMessageDTO.builder()
                .message("File is uploaded successfully")
                .success(true)
                .build()
                , HttpStatus.CREATED);
    }

    @Operation(summary = "Скачать документ"
            , parameters = {@Parameter(name = "fileName", description = "Имя файла")})
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam String fileName) {
        return new ResponseEntity<>(documentService.loadFile(fileName), HttpStatus.OK);
    }

    @Operation(summary = "Получить список файлов текущего юзера")
    @GetMapping("/get-all")
    public ResponseEntity<List<DocumentDTO>> getAllFiles() {
        return new ResponseEntity<>(documentService.getAllFiles(), HttpStatus.OK);
    }

    @Operation(summary = "Получить файл по названию"
            , parameters = {@Parameter(name = "fileName", description = "Имя файла")})
    @GetMapping("/get-by-name")
    public ResponseEntity<DocumentDTO> getFileByName(@RequestParam String fileName) {
        return new ResponseEntity<>(documentService.findByNameAndCurrentUser(fileName), HttpStatus.OK);
    }

    @Operation(summary = "Получить файл по дате создания в системе"
            , parameters = {@Parameter(name = "date", description = "Дата создания файла")})
    @GetMapping("/get-by-date")
    public ResponseEntity<List<DocumentDTO>> getFileByDate(@RequestParam Date date) {
        return new ResponseEntity<>(documentService.findByDateAndCurrentUser(date), HttpStatus.OK);
    }
}
