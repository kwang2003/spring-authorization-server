package example.authentication;

import example.dao.OAuth2AuthorizationDao;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.security.oauth2.core.OAuth2TokenType;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class RemoteOAuth2AuthorizationService implements OAuth2AuthorizationService {
    @Resource
    private OAuth2AuthorizationConvertor oAuth2AuthorizationConvertor;
    @Resource
    private OAuth2AuthorizationDao oAuth2AuthorizationDao;
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
        oAuth2AuthorizationDao.update(oAuth2AuthorizationConvertor.from(authorization));
    }

    private void insertAuthorization(OAuth2Authorization authorization) {
        oAuth2AuthorizationDao.insert(oAuth2AuthorizationConvertor.from(authorization));
    }

    @Override
    public void remove(OAuth2Authorization authorization) {
        Assert.notNull(authorization, "authorization cannot be null");
        oAuth2AuthorizationDao.delete(authorization.getId());
    }

    @Override
    public OAuth2Authorization findById(String id) {
        return Optional.ofNullable(oAuth2AuthorizationDao.get(id)).map(a -> oAuth2AuthorizationConvertor.to(a)).orElse(null);
    }

    @Override
    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
        Assert.hasText(token, "token cannot be empty");
        List<SqlParameterValue> parameters = new ArrayList<>();
        if (tokenType == null) {
            return oAuth2AuthorizationConvertor.to(oAuth2AuthorizationDao.findByUnknownTokenType(token));
        } else if (OAuth2ParameterNames.STATE.equals(tokenType.getValue())) {
            return oAuth2AuthorizationConvertor.to(oAuth2AuthorizationDao.findByState(token));
        } else if (OAuth2ParameterNames.CODE.equals(tokenType.getValue())) {
            return oAuth2AuthorizationConvertor.to(oAuth2AuthorizationDao.findByAuthorizationCode(token));
        } else if (OAuth2TokenType.ACCESS_TOKEN.equals(tokenType)) {
            return oAuth2AuthorizationConvertor.to(oAuth2AuthorizationDao.findByAccessToken(token));
        } else if (OAuth2TokenType.REFRESH_TOKEN.equals(tokenType)) {
            return oAuth2AuthorizationConvertor.to(oAuth2AuthorizationDao.findByRefreshToken(token));
        }
        return null;
    }
}
