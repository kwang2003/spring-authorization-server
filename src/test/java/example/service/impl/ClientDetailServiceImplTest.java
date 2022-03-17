package example.service.impl;

import example.AbstractTestCase;
import example.dto.ClientDto;
import example.service.ClientDetailService;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

class ClientDetailServiceImplTest extends AbstractTestCase {
    @Resource
    private ClientDetailService clientDetailService;

    @Test
    void testGetByClientId(){
        String clientId = "messaging-client";
        ClientDto clientDto =clientDetailService.getByClientId(clientId);
        assertNotNull(clientDto);
    }
}