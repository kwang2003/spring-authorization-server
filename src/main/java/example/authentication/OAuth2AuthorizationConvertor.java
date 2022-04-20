package example.authentication;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import example.entity.OAuth2Authorization;
import lombok.Data;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.core.AbstractOAuth2Token;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthorizationCode;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization.Builder;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Component
public class OAuth2AuthorizationConvertor {
    private static final ZoneOffset ZONE_OFFSET = ZoneOffset.ofHours(8);
    private ObjectMapper objectMapper = new ObjectMapper();
    @Resource
    private RegisteredClientRepository registeredClientRepository;
    public OAuth2AuthorizationConvertor(){
        ClassLoader classLoader = JdbcOAuth2AuthorizationService.class.getClassLoader();
        List<Module> securityModules = SecurityJackson2Modules.getModules(classLoader);
        this.objectMapper.registerModules(securityModules);
        this.objectMapper.registerModule(new OAuth2AuthorizationServerJackson2Module());
    }

    public OAuth2Authorization from(org.springframework.security.oauth2.server.authorization.OAuth2Authorization authorization){
        if(authorization == null){
            return null;
        }

        OAuth2Authorization result = new OAuth2Authorization();
        result.setId(authorization.getId());
        result.setRegisteredClientId(authorization.getRegisteredClientId());
        result.setPrincipalName(authorization.getPrincipalName());
        result.setAuthorizationGrantType(authorization.getAuthorizationGrantType().getValue());
        result.setState(authorization.getAttribute(OAuth2ParameterNames.STATE));

        Optional.ofNullable(toTokenInfo(authorization.getToken(OAuth2AuthorizationCode.class))).ifPresent(token ->{
            result.setAuthorizationCodeValue(token.getTokenValue() );
            result.setAuthorizationCodeExpiresAt(token.getTokenExpiresAt().toLocalDateTime());
            result.setAuthorizationCodeIssuedAt(token.getTokenIssuedAt().toLocalDateTime());
            result.setAuthorizationCodeMetadata(token.getMetadata());
        });

        Optional.ofNullable(toTokenInfo(authorization.getToken(OAuth2AccessToken.class))).ifPresent(token ->{
            result.setAccessTokenMetadata(token.getMetadata());
            result.setAccessTokenValue(token.getTokenValue());
            result.setAccessTokenIssuedAt(token.getTokenIssuedAt().toLocalDateTime());
            result.setAccessTokenExpiresAt(token.getTokenExpiresAt().toLocalDateTime());
        });

        Optional.ofNullable(authorization.getToken(OAuth2AccessToken.class)).ifPresent(accessToken ->{
            String accessTokenType = accessToken.getToken().getTokenType().getValue();
            if (!CollectionUtils.isEmpty(accessToken.getToken().getScopes())) {
                result.setAccessTokenScopes(StringUtils.collectionToDelimitedString(accessToken.getToken().getScopes(), ","));
            }
            result.setAccessTokenType(accessTokenType);
        });

        Optional.ofNullable(toTokenInfo(authorization.getToken(OidcIdToken.class))).ifPresent(token ->{
            result.setOidcIdTokenValue(token.getTokenValue());
            result.setOidcIdTokenExpiresAt(token.getTokenExpiresAt().toLocalDateTime());
            result.setOidcIdTokenMetadata(token.getMetadata());
            result.setOidcIdTokenIssuedAt(token.getTokenIssuedAt().toLocalDateTime());
        });

        Optional.ofNullable(toTokenInfo(authorization.getRefreshToken())).ifPresent(token ->{
            result.setRefreshTokenIssuedAt(token.getTokenIssuedAt().toLocalDateTime());
            result.setRefreshTokenMetadata(token.getMetadata());
            result.setRefreshTokenExpiresAt(token.getTokenExpiresAt().toLocalDateTime());
            result.setRefreshTokenValue(token.getTokenValue());
        });

        String attributes = writeMap(authorization.getAttributes());
        result.setAttributes(attributes);
        return result;
    }


    private <T extends AbstractOAuth2Token> TokenInfo toTokenInfo(org.springframework.security.oauth2.server.authorization.OAuth2Authorization.Token<T> token) {
        if(token == null){
            return null;
        }
        String tokenValue = null;
        Timestamp tokenIssuedAt = null;
        Timestamp tokenExpiresAt = null;
        String metadata = null;
        if (token != null) {
            tokenValue = token.getToken().getTokenValue();
            if (token.getToken().getIssuedAt() != null) {
                tokenIssuedAt = Timestamp.from(token.getToken().getIssuedAt());
            }
            if (token.getToken().getExpiresAt() != null) {
                tokenExpiresAt = Timestamp.from(token.getToken().getExpiresAt());
            }
            metadata = writeMap(token.getMetadata());
        }

        TokenInfo result = new TokenInfo();
        result.setMetadata(metadata);
        result.setTokenExpiresAt(tokenExpiresAt);
        result.setTokenExpiresAt(tokenExpiresAt);
        result.setTokenValue(tokenValue);
        result.setTokenIssuedAt(tokenIssuedAt);

        return result;
    }

    @Data
    private static class TokenInfo{
        private String tokenValue;
        private Timestamp tokenIssuedAt;
        private Timestamp tokenExpiresAt;
        private String metadata;
    }

    public org.springframework.security.oauth2.server.authorization.OAuth2Authorization to(OAuth2Authorization oAuth2Authorization){
        if(oAuth2Authorization == null){
            return null;
        }

        Builder builder = org.springframework.security.oauth2.server.authorization.OAuth2Authorization.withRegisteredClient(registeredClientRepository.findById(oAuth2Authorization.getRegisteredClientId()));
        builder.id(oAuth2Authorization.getId());
        builder.authorizationGrantType(new AuthorizationGrantType(oAuth2Authorization.getAuthorizationGrantType()));
        builder.principalName(oAuth2Authorization.getPrincipalName());

        Map<String, Object> attributes = parseMap(oAuth2Authorization.getAttributes());
        builder.attributes((attrs) -> attrs.putAll(attributes));

        if(!Strings.isNullOrEmpty(oAuth2Authorization.getState())){
            builder.attribute(OAuth2ParameterNames.STATE,oAuth2Authorization.getState());
        }

        Instant tokenIssuedAt;
        Instant tokenExpiresAt;
        String authorizationCodeValue = oAuth2Authorization.getAuthorizationCodeValue();

        if (StringUtils.hasText(authorizationCodeValue)) {
            tokenIssuedAt = oAuth2Authorization.getAuthorizationCodeIssuedAt().toInstant(ZONE_OFFSET);
            tokenExpiresAt = oAuth2Authorization.getAuthorizationCodeExpiresAt().toInstant(ZONE_OFFSET);
            Map<String, Object> authorizationCodeMetadata = parseMap(oAuth2Authorization.getAuthorizationCodeMetadata());

            OAuth2AuthorizationCode authorizationCode = new OAuth2AuthorizationCode(
                    authorizationCodeValue, tokenIssuedAt, tokenExpiresAt);
            builder.token(authorizationCode, (metadata) -> metadata.putAll(authorizationCodeMetadata));
        }

        String accessTokenValue = oAuth2Authorization.getAccessTokenValue();
        if (StringUtils.hasText(accessTokenValue)) {
            tokenIssuedAt = oAuth2Authorization.getAccessTokenIssuedAt().toInstant(ZONE_OFFSET);
            tokenExpiresAt = oAuth2Authorization.getAccessTokenExpiresAt().toInstant(ZONE_OFFSET);
            Map<String, Object> accessTokenMetadata = parseMap(oAuth2Authorization.getAccessTokenMetadata());
            OAuth2AccessToken.TokenType tokenType = null;
            if (OAuth2AccessToken.TokenType.BEARER.getValue().equalsIgnoreCase(oAuth2Authorization.getAccessTokenType())) {
                tokenType = OAuth2AccessToken.TokenType.BEARER;
            }

            Set<String> scopes = Collections.emptySet();
            String accessTokenScopes = oAuth2Authorization.getAccessTokenScopes();
            if (accessTokenScopes != null) {
                scopes = StringUtils.commaDelimitedListToSet(accessTokenScopes);
            }
            OAuth2AccessToken accessToken = new OAuth2AccessToken(tokenType, accessTokenValue, tokenIssuedAt, tokenExpiresAt, scopes);
            builder.token(accessToken, (metadata) -> metadata.putAll(accessTokenMetadata));
        }

        String oidcIdTokenValue = oAuth2Authorization.getOidcIdTokenValue();
        if (StringUtils.hasText(oidcIdTokenValue)) {
            tokenIssuedAt = oAuth2Authorization.getOidcIdTokenIssuedAt().toInstant(ZONE_OFFSET);
            tokenExpiresAt = oAuth2Authorization.getOidcIdTokenExpiresAt().toInstant(ZONE_OFFSET);
            Map<String, Object> oidcTokenMetadata = parseMap(oAuth2Authorization.getOidcIdTokenMetadata());

            OidcIdToken oidcToken = new OidcIdToken(
                    oidcIdTokenValue, tokenIssuedAt, tokenExpiresAt, (Map<String, Object>) oidcTokenMetadata.get(org.springframework.security.oauth2.server.authorization.OAuth2Authorization.Token.CLAIMS_METADATA_NAME));
            builder.token(oidcToken, (metadata) -> metadata.putAll(oidcTokenMetadata));
        }

        String refreshTokenValue = oAuth2Authorization.getRefreshTokenValue();
        if (StringUtils.hasText(refreshTokenValue)) {
            tokenIssuedAt = oAuth2Authorization.getRefreshTokenIssuedAt().toInstant(ZONE_OFFSET);
            tokenExpiresAt = null;
            if (oAuth2Authorization.getRefreshTokenExpiresAt() != null) {
                tokenExpiresAt = oAuth2Authorization.getRefreshTokenExpiresAt().toInstant(ZONE_OFFSET);
            }
            Map<String, Object> refreshTokenMetadata = parseMap(oAuth2Authorization.getRefreshTokenMetadata());

            OAuth2RefreshToken refreshToken = new OAuth2RefreshToken(
                    refreshTokenValue, tokenIssuedAt, tokenExpiresAt);
            builder.token(refreshToken, (metadata) -> metadata.putAll(refreshTokenMetadata));
        }

        return builder.build();
    }

    private String writeMap(Map<String, Object> data) {
        try {
            return this.objectMapper.writeValueAsString(data);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    private Map<String, Object> parseMap(String data) {
        try {
            return this.objectMapper.readValue(data, new TypeReference<Map<String, Object>>() {});
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }
}
