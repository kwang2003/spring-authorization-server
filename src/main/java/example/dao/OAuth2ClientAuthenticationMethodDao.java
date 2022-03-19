package example.dao;

import example.entity.OAuth2ClientAuthenticationMethod;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OAuth2ClientAuthenticationMethodDao extends AbstractDao{
    public void insert(OAuth2ClientAuthenticationMethod clientSetting) {
        this.getSqlSession().insert(getNamespacePrefix() + "insert", clientSetting);
    }
    public OAuth2ClientAuthenticationMethod get(Long id) {
        return this.getSqlSession().selectOne(getNamespacePrefix()+"getById", id);
    }
    public List<OAuth2ClientAuthenticationMethod> getByClientId(String clientId){
        return this.getSqlSession().selectList(getNamespacePrefix()+"getByClientId", clientId);
    }
    public int update(OAuth2ClientAuthenticationMethod setting) {
        return this.getSqlSession().update(getNamespacePrefix()+"update", setting);
    }
    public int delete(Long id) {
        return this.getSqlSession().delete(getNamespacePrefix()+"delete", id);
    }
}
