package example.dao;

import example.entity.OAuth2ClientRedirectUri;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OAuth2ClientRedirectUriDao extends AbstractDao{
    public void insert(OAuth2ClientRedirectUri clientSetting) {
        this.getSqlSession().insert(getNamespacePrefix() + "insert", clientSetting);
    }
    public OAuth2ClientRedirectUri get(Long id) {
        return this.getSqlSession().selectOne(getNamespacePrefix()+"getById", id);
    }
    public List<OAuth2ClientRedirectUri> getByClientId(String clientId){
        return this.getSqlSession().selectList(getNamespacePrefix()+"getByClientId", clientId);
    }
    public int update(OAuth2ClientRedirectUri setting) {
        return this.getSqlSession().update(getNamespacePrefix()+"update", setting);
    }
    public int delete(Long id) {
        return this.getSqlSession().delete(getNamespacePrefix()+"delete", id);
    }
}
