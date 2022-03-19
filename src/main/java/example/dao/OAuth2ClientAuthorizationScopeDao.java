package example.dao;

import example.entity.OAuth2ClientAuthorizationScope;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OAuth2ClientAuthorizationScopeDao extends AbstractDao{
    public void insert(OAuth2ClientAuthorizationScope clientSetting) {
        this.getSqlSession().insert(getNamespacePrefix() + "insert", clientSetting);
    }
    public OAuth2ClientAuthorizationScope get(Long id) {
        return this.getSqlSession().selectOne(getNamespacePrefix()+"getById", id);
    }
    public List<OAuth2ClientAuthorizationScope> getByClientId(String clientId){
        return this.getSqlSession().selectList(getNamespacePrefix()+"getByClientId", clientId);
    }
    public int update(OAuth2ClientAuthorizationScope setting) {
        return this.getSqlSession().update(getNamespacePrefix()+"update", setting);
    }
    public int delete(Long id) {
        return this.getSqlSession().delete(getNamespacePrefix()+"delete", id);
    }
}
