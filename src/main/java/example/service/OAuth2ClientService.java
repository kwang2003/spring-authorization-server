package example.service;

import example.entity.OAuth2Client;

public interface OAuth2ClientService {
    OAuth2Client get(String id);
    OAuth2Client getByClientId(String clientId);
}
