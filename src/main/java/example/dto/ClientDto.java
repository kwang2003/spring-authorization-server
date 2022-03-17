package example.dto;

import example.entity.Client;
import example.entity.ClientAuthenticationMethod;
import example.entity.ClientAuthorizationGrantType;
import example.entity.ClientRedirectUri;
import example.entity.ClientAuthorizationScope;
import example.entity.ClientSetting;
import example.entity.TokenSetting;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ClientDto implements Serializable {
	private static final long serialVersionUID = -7629449746878262848L;
	private Client client;
	private List<ClientSetting> clientSettings;
	private List<TokenSetting> tokenSettings;
	private List<ClientAuthenticationMethod> clientAuthenticationMethods;
	private List<ClientAuthorizationGrantType> clientAuthorizationGrantTypes;
	private List<ClientAuthorizationScope> clientAuthorizationScopes;
	private List<ClientRedirectUri> clientRedirectUris;
}
