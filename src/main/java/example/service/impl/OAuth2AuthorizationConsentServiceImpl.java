package example.service.impl;

import example.dao.OAuth2AuthorizationConsentDao;

import example.entity.OAuth2AuthorizationConsent;
import example.service.OAuth2AuthorizationConsentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class OAuth2AuthorizationConsentServiceImpl implements OAuth2AuthorizationConsentService {
    @Resource
    private OAuth2AuthorizationConsentDao oAuth2AuthorizationConsentDao;

    @Override
    public int insert(OAuth2AuthorizationConsent oAuth2AuthorizationConsent) {
        return oAuth2AuthorizationConsentDao.insert(oAuth2AuthorizationConsent);
    }

    @Override
    public OAuth2AuthorizationConsent get(Long id) {
        return oAuth2AuthorizationConsentDao.get(id);
    }

    @Override
    public OAuth2AuthorizationConsent getByClientIdPrincipalName(String clientId, String principalName) {
        return oAuth2AuthorizationConsentDao.getByClientIdPrincipalName(clientId,principalName);
    }

    @Override
    public int update(OAuth2AuthorizationConsent setting) {
        return oAuth2AuthorizationConsentDao.update(setting);
    }

    @Override
    public int delete(String clientId, String principalName) {
        return oAuth2AuthorizationConsentDao.delete(clientId,principalName);
    }
}
