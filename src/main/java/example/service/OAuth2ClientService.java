package example.service;

import example.entity.OAuth2Client;

public interface OAuth2ClientService {
    /**
     * 获取客户端信息
     * @param id
     * @return
     */
    OAuth2Client get(String id);

    /**
     * 通过client_id获取客户端信息
     * @param clientId
     * @return
     */
    OAuth2Client getByClientId(String clientId);
}
