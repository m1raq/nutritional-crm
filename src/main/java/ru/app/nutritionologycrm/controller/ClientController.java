package ru.app.nutritionologycrm.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.app.nutritionologycrm.dto.client.ClientCreateRequestDTO;
import ru.app.nutritionologycrm.dto.ResponseMessageDTO;
import ru.app.nutritionologycrm.dto.client.ClientDTO;
import ru.app.nutritionologycrm.dto.client.ClientUpdateRequestDTO;
import ru.app.nutritionologycrm.service.ClientService;

import java.util.List;

@Tag(name = "Клиенты")
@SecurityRequirement(name = "JWT")
@RequestMapping("/api/v1/client")
@RestController
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @Operation(summary = "Создать карточку клиента")
    @PostMapping("/create")
    public ResponseEntity<ResponseMessageDTO> createClient(@RequestBody ClientCreateRequestDTO client) {
        clientService.saveClient(client);
        return new ResponseEntity<>(ResponseMessageDTO.builder()
                .message("Client is created")
                .success(true)
                .build(), HttpStatus.CREATED);
    }

    @Operation(summary = "Обновить карточку клиента")
    @PutMapping("/update")
    public ResponseEntity<ResponseMessageDTO> updateClient(@RequestBody ClientUpdateRequestDTO client) {
        clientService.updateClient(client);
        return new ResponseEntity<>(ResponseMessageDTO.builder()
                .message("Client is updated")
                .success(true)
                .build(), HttpStatus.OK);
    }

    @Operation(summary = "Обновить статус клиента"
            , parameters = {@Parameter(name = "id", description = "Id обновляемого клиента")})
    @PutMapping("/update-status")
    public ResponseEntity<ResponseMessageDTO> updateClientStatus(@RequestParam Long id, @RequestParam String status) {
        clientService.updateClientStatus(id, status);
        return new ResponseEntity<>(ResponseMessageDTO.builder()
                .message("Client status is updated")
                .build(), HttpStatus.OK);
    }

    @Operation(summary = "Получить список клиентов текущего юзера")
    @GetMapping("/get-by-current-user")
    public ResponseEntity<List<ClientDTO>> getClientsByCurrentUser() {
        return new ResponseEntity<>(clientService.findAllByCurrentUser(), HttpStatus.FOUND);
    }

    @Operation(summary = "Поиск клиентов текущего юзера по имени"
            ,  parameters = {@Parameter(name = "name", description = "Имя клиента")})
    @GetMapping("/get-by-name")
    public ResponseEntity<List<ClientDTO>> getClientsByName(@RequestParam String name) {
        return new ResponseEntity<>(clientService.findByName(name), HttpStatus.FOUND);
    }

    @Operation(summary = "Поиск клиентов текущего юзера по контакту"
            , parameters = {@Parameter(name = "contacts", description = "Контакт клиента")})
    @GetMapping("/get-by-contacts")
    public ResponseEntity<ClientDTO> getClientsByContacts(@RequestParam String contacts) {
        return new ResponseEntity<>(clientService.findByContacts(contacts), HttpStatus.FOUND);
    }

    @Operation(summary = "Поиск клиентов текущего юзера по возрасту"
            , parameters = {@Parameter(name = "age", description = "Возраст клиента")})
    @GetMapping("/get-by-age")
    public ResponseEntity<List<ClientDTO>> getClientByAge(@RequestParam Integer age) {
        return new ResponseEntity<>(clientService.findByAge(age), HttpStatus.FOUND);
    }

}
