package example.service;

import example.entity.OAuth2AuthorizationConsent;

public interface OAuth2AuthorizationConsentService {
    /**
     * 插入授权信息
     * @param oAuth2AuthorizationConsent
     * @return
     */
    int insert(OAuth2AuthorizationConsent oAuth2AuthorizationConsent);

    /**
     * 通过id获取授权信息
     * @param id
     * @return
     */
    OAuth2AuthorizationConsent get(Long id);

    /**
     * 通过client_id和用户名后取授权信息
     * @param clientId
     * @param principalName
     * @return
     */
    OAuth2AuthorizationConsent getByClientIdPrincipalName(String clientId,String principalName);

    /**
     * 更新授权信息
     * @param setting
     * @return
     */
    int update(OAuth2AuthorizationConsent setting);

    /**
     * 删除授权信息
     * @param clientId
     * @param principalName
     * @return
     */
    int delete(String clientId,String principalName);
}
