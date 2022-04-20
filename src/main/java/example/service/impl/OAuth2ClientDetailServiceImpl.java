package example.service.impl;

import com.google.common.base.Preconditions;
import example.dao.OAuth2ClientAuthenticationMethodDao;
import example.dao.OAuth2ClientAuthorizationGrantTypeDao;
import example.dao.OAuth2ClientDao;
import example.dao.OAuth2ClientRedirectUriDao;
import example.dao.OAuth2ClientAuthorizationScopeDao;
import example.dao.OAuth2ClientSettingDao;
import example.dao.OAuth2TokenSettingDao;
import example.dto.OAuth2ClientDto;
import example.entity.OAuth2Client;
import example.entity.OAuth2ClientAuthenticationMethod;
import example.entity.OAuth2ClientAuthorizationGrantType;
import example.entity.OAuth2ClientRedirectUri;
import example.entity.OAuth2ClientAuthorizationScope;
import example.entity.OAuth2ClientSetting;
import example.entity.OAuth2TokenSetting;
import example.service.OAuth2ClientDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OAuth2ClientDetailServiceImpl implements OAuth2ClientDetailService {
    @Resource
    private OAuth2ClientDao oAuth2ClientDao;
    @Resource
    private OAuth2ClientSettingDao oAuth2ClientSettingDao;
    @Resource
    private OAuth2ClientAuthenticationMethodDao oAuth2ClientAuthenticationMethodDao;
    @Resource
    private OAuth2ClientAuthorizationScopeDao oAuth2ClientAuthorizationScopeDao;
    @Resource
    private OAuth2ClientAuthorizationGrantTypeDao oAuth2ClientAuthorizationGrantTypeDao;
    @Resource
    private OAuth2ClientRedirectUriDao oAuth2ClientRedirectUriDao;
    @Resource
    private OAuth2TokenSettingDao oAuth2TokenSettingDao;
    @Override
    public OAuth2ClientDto get(String id) {
        OAuth2Client client = oAuth2ClientDao.get(id);
        if(client == null){
            return null;
        }

        return create(client);
    }

    @Override
    public OAuth2ClientDto getByClientId(String clientId) {
        OAuth2Client client = oAuth2ClientDao.getByClientId(clientId);
        if(client == null){
            return null;
        }

        return create(client);
    }

    @Override
    public OAuth2ClientDto save(OAuth2ClientDto client) {
        Preconditions.checkNotNull(client,"参数不能为空");
        OAuth2Client dbClient = oAuth2ClientDao.get(client.getClient().getId());
        //插入操作
        if(dbClient == null){
            return doInsert(client);
        }
        throw new IllegalArgumentException("不支持改操作");
    }

    private OAuth2ClientDto doInsert(OAuth2ClientDto client){
        oAuth2ClientDao.insert(client.getClient());
        client.getClientAuthenticationMethods().forEach(m ->{
            oAuth2ClientAuthenticationMethodDao.insert(m);
        });
        client.getClientSettings().forEach(s ->{
            oAuth2ClientSettingDao.insert(s);
        });
        client.getTokenSettings().forEach(s ->{
            oAuth2TokenSettingDao.insert(s);
        });
        client.getClientAuthorizationGrantTypes().forEach(t ->{
            oAuth2ClientAuthorizationGrantTypeDao.insert(t);
        });
        client.getClientAuthorizationScopes().forEach(s ->{
            oAuth2ClientAuthorizationScopeDao.insert(s);
        });
        client.getClientRedirectUris().forEach(u ->{
            oAuth2ClientRedirectUriDao.insert(u);
        });
        return client;
    }


    private OAuth2ClientDto create(OAuth2Client client){
        OAuth2ClientDto dto = new OAuth2ClientDto();
        dto.setClient(client);

        List<OAuth2ClientSetting> clientSettings = oAuth2ClientSettingDao.getByClientId(client.getClientId());
        dto.setClientSettings(clientSettings);

        List<OAuth2ClientAuthenticationMethod> clientAuthenticationMethods = oAuth2ClientAuthenticationMethodDao.getByClientId(client.getClientId());
        dto.setClientAuthenticationMethods(clientAuthenticationMethods);

        List<OAuth2ClientAuthorizationGrantType> clientAuthorizationGrantTypes = oAuth2ClientAuthorizationGrantTypeDao.getByClientId(client.getClientId());
        dto.setClientAuthorizationGrantTypes(clientAuthorizationGrantTypes);

        List<OAuth2ClientAuthorizationScope> clientScopes = oAuth2ClientAuthorizationScopeDao.getByClientId(client.getClientId());
        dto.setClientAuthorizationScopes(clientScopes);

        List<OAuth2ClientRedirectUri> clientRedirectUris = oAuth2ClientRedirectUriDao.getByClientId(client.getClientId());
        dto.setClientRedirectUris(clientRedirectUris);

        List<OAuth2TokenSetting> tokenSettings = oAuth2TokenSettingDao.getByClientId(client.getClientId());
        dto.setTokenSettings(tokenSettings);
        return dto;
    }
}
