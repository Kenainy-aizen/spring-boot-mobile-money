package mg.edu.eni.mobilemoney.service.client;

import mg.edu.eni.mobilemoney.exceptions.ClientNotFoundException;
import mg.edu.eni.mobilemoney.exceptions.NumtelExistException;
import mg.edu.eni.mobilemoney.model.Client;
import mg.edu.eni.mobilemoney.repository.ClientRepository;
import mg.edu.eni.mobilemoney.request.AddClientRequest;
import mg.edu.eni.mobilemoney.request.ClientUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService implements IClientService{

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client addClient(AddClientRequest request) {
        if (clientRepository.existsById(request.getNumtel())){
            throw new NumtelExistException("Ce numéro de téléphone est déjà utilisé.");
        }

        return clientRepository.save(createClient(request));
    }

    private Client createClient(AddClientRequest request) {
        return new Client(
                request.getNumtel(),
                request.getNom(),
                request.getSexe(),
                request.getAge(),
                request.getSolde(),
                request.getMail()
        );
    }

    @Override
    public Client getClientById(String id) {
        return clientRepository.findById(id)
                .orElseThrow(()-> new ClientNotFoundException("Client non trouve"));
    }

    @Override
    public void deleteClientById(String id) {
        clientRepository.findById(id).ifPresentOrElse(clientRepository::delete,
                ()-> {throw new ClientNotFoundException("Client non trouve");});
    }

    @Override
    public Client updateClientById(ClientUpdateRequest request, String clientId) {
        return clientRepository.findById(clientId)
                .map(existingClient -> updateExistingClient(existingClient, request))
                .map(clientRepository :: save)
                .orElseThrow(() -> new ClientNotFoundException("Client non trouve"));
    }

    private Client updateExistingClient( Client existingClient , ClientUpdateRequest request ) {
        existingClient.setAge(request.getAge());
        existingClient.setSexe(request.getSexe());
        existingClient.setSolde(request.getSolde());
        existingClient.setMail(request.getMail());
        existingClient.setNom(request.getNom());
            
        return existingClient;
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public List<Client> getClientsByName(String nom) {
        return clientRepository.findByNom(nom);
    }

    @Override
    public List<Client> rechercherClients(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return clientRepository.findAll();
        }
        return clientRepository.searchClients(keyword);
    }


}
