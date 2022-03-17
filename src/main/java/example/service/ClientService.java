package example.service;

import example.entity.Client;

public interface ClientService {
    Client get(String id);
    Client getByClientId(String clientId);
}
