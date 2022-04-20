package example.authentication;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import example.dto.OAuth2ClientDto;
import example.entity.OAuth2Client;
import example.entity.OAuth2ClientAuthenticationMethod;
import example.entity.OAuth2ClientAuthorizationGrantType;
import example.entity.OAuth2ClientAuthorizationScope;
import example.entity.OAuth2ClientRedirectUri;
import example.entity.OAuth2ClientSetting;
import example.service.OAuth2ClientDetailService;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2TokenFormat;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient.Builder;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.security.oauth2.server.authorization.config.ConfigurationSettingNames;
import org.springframework.security.oauth2.server.authorization.config.ConfigurationSettingNames.Token;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TimeZone;

@Component
public class RemoteRegisteredClientRepository implements RegisteredClientRepository, InitializingBean {
    @Resource
    private OAuth2ClientDetailService oAuth2ClientDetailService;
    private TypeHandlerRegistry typeHandlerRegistry = new TypeHandlerRegistry();
    @Override
    public void save(RegisteredClient registeredClient) {
        Preconditions.checkNotNull(registeredClient,"参数不能为空");
        OAuth2ClientDto clientDto = this.convert(registeredClient);
        oAuth2ClientDetailService.save(clientDto);
    }

    @Override
    public RegisteredClient findById(String id) {
        OAuth2ClientDto dto = oAuth2ClientDetailService.get(id);
        return Optional.ofNullable(convert(dto)).orElse(null);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        OAuth2ClientDto dto = oAuth2ClientDetailService.getByClientId(clientId);
        return Optional.ofNullable(convert(dto)).orElse(null);
    }

    private OAuth2ClientDto convert(RegisteredClient registeredClient){
        OAuth2ClientDto clientDto = new OAuth2ClientDto();
        OAuth2Client client = new OAuth2Client();
        client.setClientId(registeredClient.getClientId());
        client.setId(registeredClient.getId());
        client.setClientSecret(registeredClient.getClientSecret());
        ZoneId zoneId = ZoneId.of("+8");
        Optional.ofNullable(registeredClient.getClientIdIssuedAt()).ifPresent(ia ->{
            client.setClientIdIssuedAt(LocalDateTime.ofInstant(ia, zoneId));
        });
        Optional.ofNullable(registeredClient.getClientSecretExpiresAt()).ifPresent(ex ->{
            client.setClientSecretExpiresAt(LocalDateTime.ofInstant(ex,zoneId));
        });
        clientDto.setClient(client);

        List<OAuth2ClientSetting> clientSettings = Lists.newArrayList();
        Optional.ofNullable(registeredClient.getClientSettings()).ifPresent(settings ->{
            settings.getSettings().entrySet().forEach(en ->{
                OAuth2ClientSetting setting = new OAuth2ClientSetting();
                setting.setClientId(registeredClient.getClientId());
                setting.setName(en.getKey());
                setting.setValue(en.getValue() == null ?null:en.getValue().toString());
                clientSettings.add(setting);
            });
        });
        clientDto.setClientSettings(clientSettings);

        //认证方式
        List<OAuth2ClientAuthenticationMethod> clientAuthenticationMethods = Lists.newArrayList();
        Optional.ofNullable(registeredClient.getClientAuthenticationMethods()).ifPresent(methods ->{
            methods.forEach(method ->{
                OAuth2ClientAuthenticationMethod m = new OAuth2ClientAuthenticationMethod();
                m.setClientId(registeredClient.getClientId());
                m.setMethod(method.getValue());
                clientAuthenticationMethods.add(m);
            });
        });
        clientDto.setClientAuthenticationMethods(clientAuthenticationMethods);

        List<OAuth2ClientAuthorizationGrantType> clientAuthorizationGrantTypes = Lists.newArrayList();
        Optional.ofNullable(registeredClient.getAuthorizationGrantTypes()).ifPresent(grantTypes ->{
            grantTypes.forEach(g ->{
                OAuth2ClientAuthorizationGrantType type = new OAuth2ClientAuthorizationGrantType();
                type.setClientId(registeredClient.getClientId());
                type.setGrantType(g.getValue());
                clientAuthorizationGrantTypes.add(type);
            });
        });
        clientDto.setClientAuthorizationGrantTypes(clientAuthorizationGrantTypes);;

        List<OAuth2ClientAuthorizationScope> clientAuthorizationScopes = Lists.newArrayList();
        Optional.ofNullable(registeredClient.getScopes()).ifPresent(scopes ->{
            scopes.forEach(scope ->{
                OAuth2ClientAuthorizationScope s = new OAuth2ClientAuthorizationScope();
                s.setClientId(registeredClient.getClientId());
                s.setScope(scope);
                clientAuthorizationScopes.add(s);
            });
        });
        clientDto.setClientAuthorizationScopes(clientAuthorizationScopes);

        List<OAuth2ClientRedirectUri> clientRedirectUris = Lists.newArrayList();
        Optional.ofNullable(registeredClient.getRedirectUris()).ifPresent(uris ->{
            uris.forEach(uri ->{
                OAuth2ClientRedirectUri record = new OAuth2ClientRedirectUri();
                record.setClientId(registeredClient.getClientId());
                record.setRedirectUri(uri);
                clientRedirectUris.add(record);
            });
        });
        clientDto.setClientRedirectUris(clientRedirectUris);;
        return clientDto;
    }

    private RegisteredClient convert(OAuth2ClientDto clientDto){
        OAuth2Client client = clientDto.getClient();

        Builder builder = RegisteredClient.withId(client.getId());
        builder
                .clientId(client.getClientId())
                .clientSecret(client.getClientSecret())
                .clientIdIssuedAt(client.getClientIdIssuedAt().toInstant(ZoneOffset.ofHours(8)))
                .clientSecretExpiresAt(client.getClientSecretExpiresAt().toInstant(ZoneOffset.ofHours(8)));

        Optional.ofNullable(clientDto.getClientSettings()).ifPresent(clientSettings -> {
            Map<String,Object> clientSettingsMap = Maps.newHashMap();
            clientSettings.forEach(setting ->{
                clientSettingsMap.put(setting.getName(),typeHandlerRegistry.handle(setting.getName(),setting.getValue()));
            });
            builder.clientSettings(ClientSettings.withSettings(clientSettingsMap).build());
        });

        // client认证方式
        Optional.ofNullable(clientDto.getClientAuthenticationMethods()).ifPresent(list ->{
            list.forEach(s ->{
                builder.clientAuthenticationMethod(new ClientAuthenticationMethod(s.getMethod()));
            });
        });

        //grant types
        Optional.ofNullable(clientDto.getClientAuthorizationGrantTypes()).ifPresent(list ->{
            list.forEach(grant ->{
                builder.authorizationGrantType(new AuthorizationGrantType(grant.getGrantType()));
            });
        });

        //scope
        Optional.of(clientDto.getClientAuthorizationScopes()).ifPresent(list ->{
            list.forEach(scope ->{
                builder.scope(scope.getScope());
            });
        });

        // redirect uris
        Optional.ofNullable(clientDto.getClientRedirectUris()).ifPresent(list ->{
            list.forEach(uri ->{
                builder.redirectUri(uri.getRedirectUri());
            });
        });

        // token settings
        Optional.ofNullable(clientDto.getTokenSettings()).ifPresent(list ->{
            Map<String,Object> map = Maps.newHashMap();
            list.forEach(setting ->{
                map.put(setting.getName(),typeHandlerRegistry.handle(setting.getName(),setting.getValue()));
            });
            builder.tokenSettings(TokenSettings.withSettings(map).build()) ;
        });

        RegisteredClient registeredClient = builder.build();
        return registeredClient;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        BooleanTypeHandler booleanTypeHandler = new BooleanTypeHandler();
        DurationTypeHandler durationTypeHandler = new DurationTypeHandler();
        SignatureAlgorithmTypeHandler signatureAlgorithmTypeHandler = new SignatureAlgorithmTypeHandler();
        JwsAlgorithmTypeHandler jwsAlgorithmTypeHandler = new JwsAlgorithmTypeHandler();

        typeHandlerRegistry.regist(ConfigurationSettingNames.Token.REUSE_REFRESH_TOKENS,booleanTypeHandler);
        typeHandlerRegistry.regist(ConfigurationSettingNames.Token.ACCESS_TOKEN_TIME_TO_LIVE,durationTypeHandler);
        typeHandlerRegistry.regist(ConfigurationSettingNames.Token.REFRESH_TOKEN_TIME_TO_LIVE,durationTypeHandler);
        typeHandlerRegistry.regist(ConfigurationSettingNames.Token.ID_TOKEN_SIGNATURE_ALGORITHM,signatureAlgorithmTypeHandler);
        typeHandlerRegistry.regist(Token.ACCESS_TOKEN_FORMAT,new OAuth2TokenFormatTypeHandler());

        typeHandlerRegistry.regist(ConfigurationSettingNames.Client.REQUIRE_PROOF_KEY,booleanTypeHandler);
        typeHandlerRegistry.regist(ConfigurationSettingNames.Client.REQUIRE_AUTHORIZATION_CONSENT,booleanTypeHandler);
        typeHandlerRegistry.regist(ConfigurationSettingNames.Client.TOKEN_ENDPOINT_AUTHENTICATION_SIGNING_ALGORITHM,jwsAlgorithmTypeHandler);
    }

    @FunctionalInterface
    private interface TypeHandler{
        /**
         * 处理类型转换
         * @param key
         * @param value
         * @return
         */
        Object handle(String key,String value);
    }

    private class BooleanTypeHandler implements TypeHandler{
        @Override
        public Object handle(String key, String value) {
            return Boolean.valueOf(value);
        }
    }

    private class DurationTypeHandler implements TypeHandler{
        @Override
        public Object handle(String key, String value) {
            return Duration.ofSeconds(Long.valueOf(value));
        }
    }

    private class SignatureAlgorithmTypeHandler implements TypeHandler{
        @Override
        public Object handle(String key, String value) {
            SignatureAlgorithm algorithm = SignatureAlgorithm.from(value);
            return algorithm;
        }
    }

    private class JwsAlgorithmTypeHandler implements TypeHandler{
        @Override
        public Object handle(String key, String value) {
            MacAlgorithm temp = MacAlgorithm.from(value);
            if(temp != null){
                return temp;
            }
            return SignatureAlgorithm.from(value);
        }
    }

    private class OAuth2TokenFormatTypeHandler implements  TypeHandler{
        @Override
        public Object handle(String key, String value) {
            if(!Strings.isNullOrEmpty(value)){
                return new OAuth2TokenFormat(value);
            }
            return null;
        }
    }

    private static class TypeHandlerRegistry implements TypeHandler{
        private Map<String,TypeHandler> registry = Maps.newHashMap();
        public TypeHandlerRegistry regist(String key,TypeHandler handler){
            this.registry.put(key,handler);
            return this;
        }
        @Override
        public Object handle(String key, String value) {
            TypeHandler handler = registry.get(key);
            if(handler == null){
                return value;
            }
            return handler.handle(key,value);
        }
    }

}
