package example.service.impl;

import example.dao.OAuth2AuthorizationDao;
import example.entity.OAuth2Authorization;
import example.service.OAuth2AuthorizationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class OAuth2AuthorizationServiceImpl implements OAuth2AuthorizationService {
    @Resource
    private OAuth2AuthorizationDao oAuth2AuthorizationDao;
    @Override
    public int insert(OAuth2Authorization authorization) {
        return oAuth2AuthorizationDao.insert(authorization);
    }

    @Override
    public OAuth2Authorization get(String id) {
        return oAuth2AuthorizationDao.get(id);
    }

    @Override
    public int update(OAuth2Authorization setting) {
        return oAuth2AuthorizationDao.update(setting);
    }

    @Override
    public int delete(String id) {
        return oAuth2AuthorizationDao.delete(id);
    }

    @Override
    public OAuth2Authorization findByUnknownTokenType(String token) {
        return oAuth2AuthorizationDao.findByUnknownTokenType(token);
    }

    @Override
    public OAuth2Authorization findByState(String state) {
        return oAuth2AuthorizationDao.findByState(state);
    }

    @Override
    public OAuth2Authorization findByAuthorizationCode(String code) {
        return oAuth2AuthorizationDao.findByAuthorizationCode(code);
    }

    @Override
    public OAuth2Authorization findByAccessToken(String token) {
        return oAuth2AuthorizationDao.findByAccessToken(token);
    }

    @Override
    public OAuth2Authorization findByRefreshToken(String token) {
        return null;
    }
}
