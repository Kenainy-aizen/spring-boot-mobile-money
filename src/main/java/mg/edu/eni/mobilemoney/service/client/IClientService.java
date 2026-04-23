package mg.edu.eni.mobilemoney.service.client;

import mg.edu.eni.mobilemoney.model.Client;
import mg.edu.eni.mobilemoney.request.AddClientRequest;
import mg.edu.eni.mobilemoney.request.ClientUpdateRequest;

import java.util.List;

public interface IClientService {
    Client addClient(AddClientRequest client);
    Client getClientById(String id);
    void deleteClientById(String id);
    Client updateClientById(ClientUpdateRequest client, String clientId);
    List<Client> getAllClients();
    List<Client> getClientsByName(String nom);
    List<Client> rechercherClients(String keyword);
}
