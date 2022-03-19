package example.dao;

import example.entity.OAuth2ClientAuthorizationGrantType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OAuth2ClientAuthorizationGrantTypeDao extends AbstractDao{
    public void insert(OAuth2ClientAuthorizationGrantType clientSetting) {
        this.getSqlSession().insert(getNamespacePrefix() + "insert", clientSetting);
    }
    public OAuth2ClientAuthorizationGrantType get(Long id) {
        return this.getSqlSession().selectOne(getNamespacePrefix()+"getById", id);
    }
    public List<OAuth2ClientAuthorizationGrantType> getByClientId(String clientId){
        return this.getSqlSession().selectList(getNamespacePrefix()+"getByClientId", clientId);
    }
    public int update(OAuth2ClientAuthorizationGrantType setting) {
        return this.getSqlSession().update(getNamespacePrefix()+"update", setting);
    }
    public int delete(Long id) {
        return this.getSqlSession().delete(getNamespacePrefix()+"delete", id);
    }
}
