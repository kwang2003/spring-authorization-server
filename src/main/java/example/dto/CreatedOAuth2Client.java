package example.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * 创建成功的客户端对象
 * @author KevinWang
 *
 */
@Data
public class CreatedOAuth2Client implements Serializable {
	private static final long serialVersionUID = -5899993255433504583L;
	private String clientId;
	private String clientSecret;
}
