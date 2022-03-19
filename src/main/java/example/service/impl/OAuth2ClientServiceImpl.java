package example.service.impl;

import example.dao.OAuth2ClientDao;
import example.entity.OAuth2Client;
import example.service.OAuth2ClientService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class OAuth2ClientServiceImpl implements OAuth2ClientService {
    @Resource
    private OAuth2ClientDao oAuth2ClientDao;
    @Override
    public OAuth2Client get(String id) {
        return oAuth2ClientDao.get(id);
    }

    @Override
    public OAuth2Client getByClientId(String clientId) {
        return oAuth2ClientDao.get(clientId);
    }
}
