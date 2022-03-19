package example.service;

import example.entity.OAuth2Authorization;

public interface OAuth2AuthorizationService {
    int insert(OAuth2Authorization authorization);
    OAuth2Authorization get(String id);
    int update(OAuth2Authorization setting);
    int delete(String id);
    OAuth2Authorization findByUnknownTokenType(String token);
    OAuth2Authorization findByState(String state);
    OAuth2Authorization findByAuthorizationCode(String code);
    OAuth2Authorization findByAccessToken(String token);
    OAuth2Authorization findByRefreshToken(String token);
}
