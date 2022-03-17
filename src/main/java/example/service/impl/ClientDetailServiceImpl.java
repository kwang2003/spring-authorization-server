package example.service.impl;

import example.dao.ClientAuthenticationMethodDao;
import example.dao.ClientAuthorizationGrantTypeDao;
import example.dao.ClientDao;
import example.dao.ClientRedirectUriDao;
import example.dao.ClientAuthorizationScopeDao;
import example.dao.ClientSettingDao;
import example.dao.TokenSettingDao;
import example.dto.ClientDto;
import example.entity.Client;
import example.entity.ClientAuthenticationMethod;
import example.entity.ClientAuthorizationGrantType;
import example.entity.ClientRedirectUri;
import example.entity.ClientAuthorizationScope;
import example.entity.ClientSetting;
import example.entity.TokenSetting;
import example.service.ClientDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ClientDetailServiceImpl implements ClientDetailService {
    @Resource
    private ClientDao clientDao;
    @Resource
    private ClientSettingDao clientSettingDao;
    @Resource
    private ClientAuthenticationMethodDao clientAuthenticationMethodDao;
    @Resource
    private ClientAuthorizationScopeDao clientAuthorizationScopeDao;
    @Resource
    private ClientAuthorizationGrantTypeDao clientAuthorizationGrantTypeDao;
    @Resource
    private ClientRedirectUriDao clientRedirectUriDao;
    @Resource
    private TokenSettingDao tokenSettingDao;
    @Override
    public ClientDto get(String id) {
        Client client = clientDao.get(id);
        if(client == null){
            return null;
        }

        return create(client);
    }

    @Override
    public ClientDto getByClientId(String clientId) {
        Client client = clientDao.getByClientId(clientId);
        if(client == null){
            return null;
        }

        return create(client);
    }

    private ClientDto create(Client client){
        ClientDto dto = new ClientDto();
        dto.setClient(client);

        List<ClientSetting> clientSettings = clientSettingDao.getByClientId(client.getClientId());
        dto.setClientSettings(clientSettings);

        List<ClientAuthenticationMethod> clientAuthenticationMethods = clientAuthenticationMethodDao.getByClientId(client.getClientId());
        dto.setClientAuthenticationMethods(clientAuthenticationMethods);

        List<ClientAuthorizationGrantType> clientAuthorizationGrantTypes = clientAuthorizationGrantTypeDao.getByClientId(client.getClientId());
        dto.setClientAuthorizationGrantTypes(clientAuthorizationGrantTypes);

        List<ClientAuthorizationScope> clientScopes = clientAuthorizationScopeDao.getByClientId(client.getClientId());
        dto.setClientAuthorizationScopes(clientScopes);

        List<ClientRedirectUri> clientRedirectUris = clientRedirectUriDao.getByClientId(client.getClientId());
        dto.setClientRedirectUris(clientRedirectUris);

        List<TokenSetting> tokenSettings = tokenSettingDao.getByClientId(client.getClientId());
        dto.setTokenSettings(tokenSettings);
        return dto;
    }
}
