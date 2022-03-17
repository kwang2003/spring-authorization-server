package example.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * OAuth2 Token配置附件系你想
 * @author KevinWang
 *
 */
@Data
public class TokenSetting implements Serializable {
	private static final long serialVersionUID = -7370265956276958570L;
	private Long id;
	private String clientId;
	private String name;
	private String value;
	private LocalDateTime createdAt;
}
