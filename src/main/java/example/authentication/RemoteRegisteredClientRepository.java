package example.authentication;

import com.google.common.collect.Maps;
import example.dto.OAuth2ClientDto;
import example.entity.OAuth2Client;
import example.service.OAuth2ClientDetailService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
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
import java.time.ZoneOffset;
import java.util.Map;
import java.util.Optional;

@Component
public class RemoteRegisteredClientRepository implements RegisteredClientRepository, InitializingBean {
    @Resource
    private OAuth2ClientDetailService oAuth2ClientDetailService;
    private TypeHandlerRegistry typeHandlerRegistry = new TypeHandlerRegistry();
    @Override
    public void save(RegisteredClient registeredClient) {

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

        typeHandlerRegistry.regist(ConfigurationSettingNames.Client.REQUIRE_PROOF_KEY,booleanTypeHandler);
        typeHandlerRegistry.regist(ConfigurationSettingNames.Client.REQUIRE_AUTHORIZATION_CONSENT,booleanTypeHandler);
        typeHandlerRegistry.regist(ConfigurationSettingNames.Client.TOKEN_ENDPOINT_AUTHENTICATION_SIGNING_ALGORITHM,jwsAlgorithmTypeHandler);
    }

    @FunctionalInterface
    private interface TypeHandler{
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
