package example.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class OAuth2AuthorizationConsent implements Serializable {
    private Long id;
    private String clientId;
    private String principalName;
    private String authorities;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
