package example.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class ClientAuthenticationMethod implements Serializable {
    private Long id;
    private String clientId;
    private String method;
}
