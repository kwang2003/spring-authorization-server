package example.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class OAuth2ClientAuthorizationGrantType implements Serializable {
    private Long id;
    private String clientId;
    private String grantType;
}
