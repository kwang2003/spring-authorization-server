package example.service;

import example.dto.OAuth2ClientDto;

public interface OAuth2ClientDetailService {
    OAuth2ClientDto get(String id);
    OAuth2ClientDto getByClientId(String clientId);
}
