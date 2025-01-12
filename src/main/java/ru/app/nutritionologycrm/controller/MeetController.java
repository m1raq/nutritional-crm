package ru.app.nutritionologycrm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.app.nutritionologycrm.dto.ResponseMessageDTO;
import ru.app.nutritionologycrm.dto.meet.MeetCreateRequestDTO;
import ru.app.nutritionologycrm.dto.meet.MeetUpdateRequestDTO;
import ru.app.nutritionologycrm.entity.MeetEntity;
import ru.app.nutritionologycrm.service.MeetService;

import java.util.List;

@RequestMapping("/api/v1/meet")
@RestController
public class MeetController {

    private final MeetService meetService;

    @Autowired
    public MeetController(MeetService meetService) {
        this.meetService = meetService;
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseMessageDTO> createMeet(@RequestParam Long clientId
            , @RequestBody MeetCreateRequestDTO request) {
        meetService.save(request, clientId);
        return new ResponseEntity<>(ResponseMessageDTO.builder()
                .message("Meet is created")
                .success(true)
                .build(), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseMessageDTO> updateMeet(@RequestBody MeetUpdateRequestDTO request) {
        meetService.update(request);
        return new ResponseEntity<>(ResponseMessageDTO.builder()
                .message("Meet is updated")
                .success(true)
                .build(), HttpStatus.OK);
    }

    @GetMapping("/get-by-client-id")
    public ResponseEntity<List<MeetEntity>> getMeetByClientId(@RequestParam Long clientId) {
        return new ResponseEntity<>(meetService.findAllByClientId(clientId), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseMessageDTO> deleteMeet(@RequestParam Long meetId) {
        meetService.delete(meetId);
        return new ResponseEntity<>(ResponseMessageDTO.builder()
                .success(true)
                .message("Meet is deleted")
                .build(),HttpStatus.OK);
    }

}
