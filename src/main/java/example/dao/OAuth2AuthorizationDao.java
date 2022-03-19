package example.dao;

import com.google.common.base.Strings;
import example.entity.OAuth2Authorization;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class OAuth2AuthorizationDao extends AbstractDao{
    public void insert(OAuth2Authorization authorization) {
        if(Strings.isNullOrEmpty(authorization.getId())){
            authorization.setId(UUID.randomUUID().toString());
        }
        this.getSqlSession().insert(getNamespacePrefix() + "insert", authorization);
    }
    public OAuth2Authorization get(String id) {
        return this.getSqlSession().selectOne(getNamespacePrefix()+"getById", id);
    }
    public int update(OAuth2Authorization setting) {
        return this.getSqlSession().update(getNamespacePrefix()+"update", setting);
    }
    public int delete(String id) {
        return this.getSqlSession().delete(getNamespacePrefix()+"delete", id);
    }

    public OAuth2Authorization findByUnknownTokenType(String token){
        List<OAuth2Authorization>  list = this.getSqlSession().selectList(getNamespacePrefix()+"findByUnknownTokenType",token);
        if(!list.isEmpty()){
            return list.get(0);
        }
        return null;
    }

    public OAuth2Authorization findByState(String state){
        List<OAuth2Authorization>  list = this.getSqlSession().selectList(getNamespacePrefix()+"findByState",state);
        if(!list.isEmpty()){
            return list.get(0);
        }
        return null;
    }

    public OAuth2Authorization findByAuthorizationCode(String state){
        List<OAuth2Authorization>  list = this.getSqlSession().selectList(getNamespacePrefix()+"findByAuthorizationCode",state);
        if(!list.isEmpty()){
            return list.get(0);
        }
        return null;
    }

    public OAuth2Authorization findByAccessToken(String accessToken){
        List<OAuth2Authorization>  list = this.getSqlSession().selectList(getNamespacePrefix()+"findByAccessToken",accessToken);
        if(!list.isEmpty()){
            return list.get(0);
        }
        return null;
    }

    public OAuth2Authorization findByRefreshToken(String refreshToken){
        List<OAuth2Authorization>  list = this.getSqlSession().selectList(getNamespacePrefix()+"findByRefreshToken",refreshToken);
        if(!list.isEmpty()){
            return list.get(0);
        }
        return null;
    }
}
