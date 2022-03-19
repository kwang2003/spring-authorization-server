package example.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class OAuth2ClientAuthorizationScope implements Serializable {
    private Long id;
    private String clientId;
    private String scope;
}
