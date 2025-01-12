package ru.app.nutritionologycrm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.app.nutritionologycrm.dto.client.ClientCreateRequestDTO;
import ru.app.nutritionologycrm.dto.ResponseMessage;
import ru.app.nutritionologycrm.dto.client.ClientUpdateRequestDTO;
import ru.app.nutritionologycrm.entity.ClientEntity;
import ru.app.nutritionologycrm.service.ClientService;

import java.util.List;

@RequestMapping("/api/v1/client")
@RestController
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseMessage> createClient(@RequestBody ClientCreateRequestDTO client) {
        clientService.saveClient(client);
        return new ResponseEntity<>(ResponseMessage.builder()
                .message("Client is created")
                .success(true)
                .build(), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseMessage> updateClient(@RequestBody ClientUpdateRequestDTO client) {
        clientService.updateClient(client);
        return new ResponseEntity<>(ResponseMessage.builder()
                .message("Client is updated")
                .success(true)
                .build(), HttpStatus.OK);
    }

    @PutMapping("/update-status")
    public ResponseEntity<ResponseMessage> updateClientStatus(@RequestParam Long id, @RequestParam String status) {
        clientService.updateClientStatus(id, status);
        return new ResponseEntity<>(ResponseMessage.builder()
                .message("Client status is updated")
                .build(), HttpStatus.OK);
    }

    @GetMapping("/get-clients-by-current-user")
    public ResponseEntity<List<ClientEntity>> getClientsByCurrentUser() {
        return new ResponseEntity<>(clientService.findAllByCurrentUser(), HttpStatus.FOUND);
    }

    @GetMapping("/get-clients-by-name")
    public ResponseEntity<List<ClientEntity>> getClientsByName(@RequestParam String name) {
        return new ResponseEntity<>(clientService.findByName(name), HttpStatus.FOUND);
    }

    @GetMapping("/get-client-by-contacts")
    public ResponseEntity<ClientEntity> getClientsByContacts(@RequestParam String contacts) {
        return new ResponseEntity<>(clientService.findByContacts(contacts), HttpStatus.FOUND);
    }

    @GetMapping("/get-client-by-age")
    public ResponseEntity<List<ClientEntity>> getClientByAge(@RequestParam Integer age) {
        return new ResponseEntity<>(clientService.findByAge(age), HttpStatus.FOUND);
    }

}
