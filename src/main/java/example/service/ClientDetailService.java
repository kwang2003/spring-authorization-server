package example.service;

import example.dto.ClientDto;

public interface ClientDetailService {
    ClientDto get(String id);
    ClientDto getByClientId(String clientId);
}
