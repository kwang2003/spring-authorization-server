package example.service;

import example.entity.OAuth2AuthorizationConsent;

public interface OAuth2AuthorizationConsentService {
    int insert(OAuth2AuthorizationConsent oAuth2AuthorizationConsent);
    OAuth2AuthorizationConsent get(Long id);
    OAuth2AuthorizationConsent getByClientIdPrincipalName(String clientId,String principalName);
    int update(OAuth2AuthorizationConsent setting);
    int delete(String clientId,String principalName);
}
