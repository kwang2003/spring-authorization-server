package example.service.impl;

import example.AbstractTestCase;
import example.dto.OAuth2ClientDto;
import example.service.OAuth2ClientDetailService;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

class ClientDetailServiceImplTest extends AbstractTestCase {
    @Resource
    private OAuth2ClientDetailService oAuth2ClientDetailService;

    @Test
    void testGetByClientId(){
        String clientId = "messaging-client";
        OAuth2ClientDto clientDto = oAuth2ClientDetailService.getByClientId(clientId);
        assertNotNull(clientDto);
    }
}