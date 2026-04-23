package mg.edu.eni.mobilemoney.controller;

import jakarta.validation.constraints.PastOrPresent;
import lombok.RequiredArgsConstructor;
import mg.edu.eni.mobilemoney.exceptions.ClientNotFoundException;
import mg.edu.eni.mobilemoney.exceptions.NumtelExistException;
import mg.edu.eni.mobilemoney.model.Client;
import mg.edu.eni.mobilemoney.request.AddClientRequest;
import mg.edu.eni.mobilemoney.request.ClientUpdateRequest;
import mg.edu.eni.mobilemoney.response.ApiResponse;
import mg.edu.eni.mobilemoney.service.client.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("${api.prefix}/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllClients() {

        try {
            List<Client> clients = clientService.getAllClients();
            return ResponseEntity.ok(new ApiResponse("Found", clients));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error:", INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addClient(@RequestBody AddClientRequest client) {

        try {
            Client theClient = clientService.addClient(client);
            return ResponseEntity.ok(new ApiResponse("Success", theClient));
        } catch (NumtelExistException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/client/{numTel}/client")
    public ResponseEntity<ApiResponse> getClientById(@PathVariable String numTel) {

        try {
            Client theClient = clientService.getClientById(numTel);
            return ResponseEntity.ok(new ApiResponse("Success", theClient));
        } catch (ClientNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{nom}/client")
    public ResponseEntity<ApiResponse> getClientByName(@PathVariable String nom) {

        try {
            List<Client> theClient = clientService.getClientsByName(nom);
            return ResponseEntity.ok(new ApiResponse("Success", theClient));
        } catch (ClientNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/client/{numTel}/update")
    public ResponseEntity<ApiResponse> updateClient(@PathVariable String numTel, @RequestBody ClientUpdateRequest client) {

        try {
            Client theClient = clientService.updateClientById(client, numTel);
            return ResponseEntity.ok(new ApiResponse("Update success", theClient));
        } catch (ClientNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/client/{numTel}/delete")
    public ResponseEntity<ApiResponse> deleteClientById(@PathVariable String numTel) {

        try {
            clientService.deleteClientById(numTel);
            return ResponseEntity.ok(new ApiResponse("Deleted", null));
        } catch (ClientNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }


}
