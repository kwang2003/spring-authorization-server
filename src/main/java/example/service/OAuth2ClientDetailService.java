package example.service;

import example.dto.OAuth2ClientDto;

public interface OAuth2ClientDetailService {
    /**
     * 通过id获取客户端信息
     * @param id
     * @return
     */
    OAuth2ClientDto get(String id);

    /**
     * 通过client_id获取认证信息
     * @param clientId
     * @return
     */
    OAuth2ClientDto getByClientId(String clientId);

    /**
     * 更新或者插入客户端信息
     * @param client
     * @return
     */
    OAuth2ClientDto save(OAuth2ClientDto client);
}
