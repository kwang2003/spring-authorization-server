package example.authentication;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.keygen.Base64StringKeyGenerator;
import org.springframework.security.crypto.keygen.StringKeyGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.OAuth2TokenType;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.jwt.JoseHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.server.authorization.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenCustomizer;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.security.Principal;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author 01266953
 */
@Slf4j
public class OAuth2PasswordCredentialsAuthenticationProvider implements AuthenticationProvider {
    private static final StringKeyGenerator DEFAULT_REFRESH_TOKEN_GENERATOR = new Base64StringKeyGenerator(Base64.getUrlEncoder().withoutPadding(), 96);
    @Setter
    private UserDetailsService userDetailsService;
    @Setter
    private PasswordEncoder passwordEncoder;

    @Setter
    private ProviderSettings providerSettings;
    @Setter
    private OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer = (context) -> {};
    @Setter
    private JwtEncoder jwtEncoder;
    private Supplier<String> refreshTokenGenerator = DEFAULT_REFRESH_TOKEN_GENERATOR::generateKey;
    @Setter
    private OAuth2AuthorizationService authorizationService;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OAuth2PasswordCredentialsAuthenticationToken resouceOwnerPasswordAuthentication = (OAuth2PasswordCredentialsAuthenticationToken) authentication;
        OAuth2ClientAuthenticationToken clientPrincipal = getAuthenticatedClientElseThrowInvalidClient(resouceOwnerPasswordAuthentication);
        RegisteredClient registeredClient = clientPrincipal.getRegisteredClient();

        if (!registeredClient.getAuthorizationGrantTypes().contains(AuthorizationGrantType.PASSWORD)) {
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.UNAUTHORIZED_CLIENT);
        }

        Map<String, Object> additionalParameters = resouceOwnerPasswordAuthentication.getAdditionalParameters();
        String username = resouceOwnerPasswordAuthentication.getUsername();
        String password = resouceOwnerPasswordAuthentication.getPassword();
        if(!StringUtils.hasText(username)){
            log.error("用户名不能为空");
            throw new BadCredentialsException("用户名或密码不能为空");
        }
        if(!StringUtils.hasText(password)){
            log.error("密码不能为空:{}",username);
            throw new BadCredentialsException("用户名或密码不能为空");
        }
        UserDetails user = userDetailsService.loadUserByUsername(username);
        if(user == null){
            log.error("找不到用户:{}",username);
            throw new BadCredentialsException("用户名或密码错误");
        }
        if(!passwordEncoder.matches(password,user.getPassword())){
            log.error("密码不匹配:{}",username);
            throw new BadCredentialsException("用户名或密码错误");
        }

        try {
            Set<String> authorizedScopes = registeredClient.getScopes();		// Default to configured scopes
            if (!CollectionUtils.isEmpty(resouceOwnerPasswordAuthentication.getScopes())) {
                Set<String> unauthorizedScopes = resouceOwnerPasswordAuthentication.getScopes().stream()
                        .filter(requestedScope -> !registeredClient.getScopes().contains(requestedScope))
                        .collect(Collectors.toSet());
                if (!CollectionUtils.isEmpty(unauthorizedScopes)) {
                    throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_SCOPE);
                }

                authorizedScopes = new LinkedHashSet<>(resouceOwnerPasswordAuthentication.getScopes());
            }
            String issuer = this.providerSettings != null ? this.providerSettings.getIssuer() : null;

            JoseHeader.Builder headersBuilder = JwtUtils.headers();
            JwtClaimsSet.Builder claimsBuilder = JwtUtils.accessTokenClaims(
                    registeredClient, issuer, resouceOwnerPasswordAuthentication.getUsername(), authorizedScopes);

            JwtEncodingContext context = JwtEncodingContext.with(headersBuilder, claimsBuilder)
                    .registeredClient(registeredClient)
                    .principal(resouceOwnerPasswordAuthentication)
                    .authorizedScopes(authorizedScopes)
                    .tokenType(OAuth2TokenType.ACCESS_TOKEN)
                    .authorizationGrantType(AuthorizationGrantType.PASSWORD)
                    .authorizationGrant(resouceOwnerPasswordAuthentication)
                    .build();

            this.jwtCustomizer.customize(context);

            JoseHeader headers = context.getHeaders().build();
            JwtClaimsSet claims = context.getClaims().build();
            Jwt jwtAccessToken = this.jwtEncoder.encode(headers, claims);



            // Use the scopes after customizing the token
            authorizedScopes = claims.getClaim(OAuth2ParameterNames.SCOPE);

            OAuth2AccessToken accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER,
                    jwtAccessToken.getTokenValue(), jwtAccessToken.getIssuedAt(),
                    jwtAccessToken.getExpiresAt(), authorizedScopes);

            OAuth2RefreshToken refreshToken = null;
            if (registeredClient.getAuthorizationGrantTypes().contains(AuthorizationGrantType.REFRESH_TOKEN)) {
                refreshToken = generateRefreshToken(registeredClient.getTokenSettings().getRefreshTokenTimeToLive());
            }

            OAuth2Authorization.Builder authorizationBuilder = OAuth2Authorization.withRegisteredClient(registeredClient)
                    .principalName(resouceOwnerPasswordAuthentication.getName())
                    .authorizationGrantType(AuthorizationGrantType.PASSWORD)
                    .token(accessToken,
                            (metadata) ->
                                    metadata.put(OAuth2Authorization.Token.CLAIMS_METADATA_NAME, jwtAccessToken.getClaims()))
                    .attribute(OAuth2Authorization.AUTHORIZED_SCOPE_ATTRIBUTE_NAME, authorizedScopes)
                    .attribute(Principal.class.getName(), resouceOwnerPasswordAuthentication);

            if (refreshToken != null) {
                authorizationBuilder.refreshToken(refreshToken);
            }

            OAuth2Authorization authorization = authorizationBuilder.build();

            this.authorizationService.save(authorization);

            log.debug("OAuth2Authorization saved successfully");

            Map<String, Object> tokenAdditionalParameters = new HashMap<>();
            claims.getClaims().forEach((key, value) -> {
                if (!key.equals(OAuth2ParameterNames.SCOPE) &&
                        !key.equals(JwtClaimNames.IAT) &&
                        !key.equals(JwtClaimNames.EXP) &&
                        !key.equals(JwtClaimNames.NBF)) {
                    tokenAdditionalParameters.put(key, value);
                }
            });

            log.debug("returning OAuth2AccessTokenAuthenticationToken");

            return new OAuth2AccessTokenAuthenticationToken(registeredClient, clientPrincipal, accessToken, refreshToken, tokenAdditionalParameters);

        } catch (Exception ex) {
            log.error("problem in authenticate", ex);
            throw new OAuth2AuthenticationException(new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR), ex);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OAuth2PasswordCredentialsAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private OAuth2ClientAuthenticationToken getAuthenticatedClientElseThrowInvalidClient(Authentication authentication) {

        OAuth2ClientAuthenticationToken clientPrincipal = null;

        if (OAuth2ClientAuthenticationToken.class.isAssignableFrom(authentication.getPrincipal().getClass())) {
            clientPrincipal = (OAuth2ClientAuthenticationToken) authentication.getPrincipal();
        }

        if (clientPrincipal != null && clientPrincipal.isAuthenticated()) {
            return clientPrincipal;
        }

        throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_CLIENT);
    }

    private OAuth2RefreshToken generateRefreshToken(Duration tokenTimeToLive) {
        Instant issuedAt = Instant.now();
        Instant expiresAt = issuedAt.plus(tokenTimeToLive);
        return new OAuth2RefreshToken(this.refreshTokenGenerator.get(), issuedAt, expiresAt);
    }
}
