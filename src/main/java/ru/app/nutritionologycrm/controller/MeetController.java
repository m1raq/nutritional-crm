package ru.app.nutritionologycrm.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.app.nutritionologycrm.dto.ResponseMessageDTO;
import ru.app.nutritionologycrm.dto.meet.MeetCreateRequestDTO;
import ru.app.nutritionologycrm.dto.meet.MeetDTO;
import ru.app.nutritionologycrm.dto.meet.MeetUpdateRequestDTO;
import ru.app.nutritionologycrm.service.MeetService;

import java.util.List;

@Tag(name = "Встречи")
@SecurityRequirement(name = "JWT")
@RequestMapping("/api/v1/meet")
@RestController
public class MeetController {

    private final MeetService meetService;

    @Autowired
    public MeetController(MeetService meetService) {
        this.meetService = meetService;
    }

    @Operation(summary = "Создание встречи"
            , parameters = {@Parameter(name = "clientId", description = "Id клиента с которым будет " +
            "организована встреча")})
    @PostMapping("/create")
    public ResponseEntity<ResponseMessageDTO> createMeet(@RequestParam Long clientId
            , @RequestBody MeetCreateRequestDTO request) {
        meetService.save(request, clientId);
        return new ResponseEntity<>(ResponseMessageDTO.builder()
                .message("Meet is created")
                .success(true)
                .build(), HttpStatus.OK);
    }

    @Operation(summary = "Создание встречи")
    @PutMapping("/update")
    public ResponseEntity<ResponseMessageDTO> updateMeet(@RequestBody MeetUpdateRequestDTO request) {
        meetService.update(request);
        return new ResponseEntity<>(ResponseMessageDTO.builder()
                .message("Meet is updated")
                .success(true)
                .build(), HttpStatus.OK);
    }

    @Operation(summary = "Поиск встречи по id клиента"
            , parameters = {@Parameter(name = "clientId", description = "Id клиента")})
    @GetMapping("/get-by-client-id")
    public ResponseEntity<List<MeetDTO>> getMeetByClientId(@RequestParam Long clientId) {
        return new ResponseEntity<>(meetService.findAllByClientId(clientId), HttpStatus.OK);
    }

    @Operation(summary = "Удаление встречи по id"
            , parameters = {@Parameter(name = "meetId", description = "Id встречи")})
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseMessageDTO> deleteMeet(@RequestParam Long meetId) {
        meetService.delete(meetId);
        return new ResponseEntity<>(ResponseMessageDTO.builder()
                .success(true)
                .message("Meet is deleted")
                .build(),HttpStatus.OK);
    }

}
