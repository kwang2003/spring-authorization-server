package example.authentication;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import example.dao.OAuth2AuthorizationConsentDao;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent.Builder;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class RemoteOAuth2AuthorizationConsentService implements OAuth2AuthorizationConsentService {
    @Resource
    private OAuth2AuthorizationConsentDao oAuth2AuthorizationConsentDao;
    @Override
    public void save(OAuth2AuthorizationConsent authorizationConsent) {
        Assert.notNull(authorizationConsent, "authorizationConsent cannot be null");
        OAuth2AuthorizationConsent existingAuthorizationConsent = findById(
                authorizationConsent.getRegisteredClientId(), authorizationConsent.getPrincipalName());
        if (existingAuthorizationConsent == null) {
            insertAuthorizationConsent(authorizationConsent);
        } else {
            updateAuthorizationConsent(authorizationConsent);
        }
    }

    private void updateAuthorizationConsent(OAuth2AuthorizationConsent authorizationConsent) {
        oAuth2AuthorizationConsentDao.update(convert(authorizationConsent));
    }

    private void insertAuthorizationConsent(OAuth2AuthorizationConsent authorizationConsent) {
        oAuth2AuthorizationConsentDao.insert(convert(authorizationConsent));
    }

    private example.entity.OAuth2AuthorizationConsent convert(OAuth2AuthorizationConsent authorizationConsent){
        example.entity.OAuth2AuthorizationConsent result = new example.entity.OAuth2AuthorizationConsent();
        result.setClientId(authorizationConsent.getRegisteredClientId());
        result.setPrincipalName(authorizationConsent.getPrincipalName());
        Optional.ofNullable(authorizationConsent.getAuthorities()).ifPresent(att ->{
            List<String> authorities = att.stream().map(a -> a.getAuthority()).collect(Collectors.toList());
            String str = StringUtils.collectionToDelimitedString(authorities, ",");
            result.setAuthorities(str);
        });
        return result;
    }

    @Override
    public void remove(OAuth2AuthorizationConsent authorizationConsent) {
        Assert.notNull(authorizationConsent, "authorizationConsent cannot be null");
        oAuth2AuthorizationConsentDao.delete(authorizationConsent.getRegisteredClientId(),authorizationConsent.getPrincipalName());
    }

    @Override
    public OAuth2AuthorizationConsent findById(String registeredClientId, String principalName) {
        return Optional.ofNullable(oAuth2AuthorizationConsentDao.getByClientIdPrincipalName(registeredClientId,principalName)).map(s ->{
            Builder builder = OAuth2AuthorizationConsent.withId(s.getClientId(),s.getPrincipalName());
            if(!Strings.isNullOrEmpty(s.getAuthorities())){
                Splitter.on(",").split(s.getAuthorities()).forEach(a ->{
                    builder.authority(new SimpleGrantedAuthority(a));
                });
            }
            return builder.build();
        }).orElse(null);
    }

}
