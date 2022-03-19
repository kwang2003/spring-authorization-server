package example.authentication;

import org.springframework.security.oauth2.core.OAuth2TokenType;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Optional;

@Component
public class RemoteOAuth2AuthorizationServiceImpl implements OAuth2AuthorizationService {
    @Resource
    private OAuth2AuthorizationConvertor oAuth2AuthorizationConvertor;
    @Resource
    private example.service.OAuth2AuthorizationService oAuth2AuthorizationService;
    @Override
    public void save(OAuth2Authorization authorization) {
        Assert.notNull(authorization, "authorization cannot be null");
        OAuth2Authorization existingAuthorization = findById(authorization.getId());
        if (existingAuthorization == null) {
            insertAuthorization(authorization);
        } else {
            updateAuthorization(authorization);
        }
    }

    private void updateAuthorization(OAuth2Authorization authorization) {
        oAuth2AuthorizationService.update(oAuth2AuthorizationConvertor.from(authorization));
    }

    private void insertAuthorization(OAuth2Authorization authorization) {
        oAuth2AuthorizationService.insert(oAuth2AuthorizationConvertor.from(authorization));
    }

    @Override
    public void remove(OAuth2Authorization authorization) {
        Assert.notNull(authorization, "authorization cannot be null");
        oAuth2AuthorizationService.delete(authorization.getId());
    }

    @Override
    public OAuth2Authorization findById(String id) {
        return Optional.ofNullable(oAuth2AuthorizationService.get(id)).map(a -> oAuth2AuthorizationConvertor.to(a)).orElse(null);
    }

    @Override
    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
        Assert.hasText(token, "token cannot be empty");
        if (tokenType == null) {
            return oAuth2AuthorizationConvertor.to(oAuth2AuthorizationService.findByUnknownTokenType(token));
        } else if (OAuth2ParameterNames.STATE.equals(tokenType.getValue())) {
            return oAuth2AuthorizationConvertor.to(oAuth2AuthorizationService.findByState(token));
        } else if (OAuth2ParameterNames.CODE.equals(tokenType.getValue())) {
            return oAuth2AuthorizationConvertor.to(oAuth2AuthorizationService.findByAuthorizationCode(token));
        } else if (OAuth2TokenType.ACCESS_TOKEN.equals(tokenType)) {
            return oAuth2AuthorizationConvertor.to(oAuth2AuthorizationService.findByAccessToken(token));
        } else if (OAuth2TokenType.REFRESH_TOKEN.equals(tokenType)) {
            return oAuth2AuthorizationConvertor.to(oAuth2AuthorizationService.findByRefreshToken(token));
        }
        return null;
    }
}
