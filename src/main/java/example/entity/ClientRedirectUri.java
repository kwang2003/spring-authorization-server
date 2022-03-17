package example.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class ClientRedirectUri implements Serializable {
    private Long id;
    private String clientId;
    private String redirectUri;
}
