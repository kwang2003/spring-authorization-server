package example.dto;

import example.entity.OAuth2Client;
import example.entity.OAuth2ClientAuthenticationMethod;
import example.entity.OAuth2ClientAuthorizationGrantType;
import example.entity.OAuth2ClientRedirectUri;
import example.entity.OAuth2ClientAuthorizationScope;
import example.entity.OAuth2ClientSetting;
import example.entity.OAuth2TokenSetting;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OAuth2ClientDto implements Serializable {
	private static final long serialVersionUID = -7629449746878262848L;
	private OAuth2Client client;
	private List<OAuth2ClientSetting> clientSettings;
	private List<OAuth2TokenSetting> tokenSettings;
	private List<OAuth2ClientAuthenticationMethod> clientAuthenticationMethods;
	private List<OAuth2ClientAuthorizationGrantType> clientAuthorizationGrantTypes;
	private List<OAuth2ClientAuthorizationScope> clientAuthorizationScopes;
	private List<OAuth2ClientRedirectUri> clientRedirectUris;
}
