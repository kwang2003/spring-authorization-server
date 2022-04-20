package example.service;

import example.entity.OAuth2Authorization;

public interface OAuth2AuthorizationService {
    /**
     * 保存认证信息
     * @param authorization
     * @return
     */
    int insert(OAuth2Authorization authorization);

    /**
     * 通过id获取获取保存的认证信息
     * @param id
     * @return
     */
    OAuth2Authorization get(String id);

    /**
     * 更新已有认证信息
     * @param setting
     * @return
     */
    int update(OAuth2Authorization setting);

    /**
     * 删除认证信息
     * @param id
     * @return
     */
    int delete(String id);

    /**
     * 通过未知token类型查找认证信息
     * @param token
     * @return
     */
    OAuth2Authorization findByUnknownTokenType(String token);

    /**
     * 通过state查询认证信息
     * @param state
     * @return
     */
    OAuth2Authorization findByState(String state);

    /**
     * 通过授权码获取认证信息
     * @param code
     * @return
     */
    OAuth2Authorization findByAuthorizationCode(String code);

    /**
     * 通过访问令牌获取认证信息
     * @param token
     * @return
     */
    OAuth2Authorization findByAccessToken(String token);

    /**
     * 通过刷新令牌获取认证信息
     * @param token
     * @return
     */
    OAuth2Authorization findByRefreshToken(String token);
}
