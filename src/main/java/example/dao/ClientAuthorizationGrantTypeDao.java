package example.dao;

import example.entity.ClientAuthorizationGrantType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClientAuthorizationGrantTypeDao extends AbstractDao{
    public void insert(ClientAuthorizationGrantType clientSetting) {
        this.getSqlSession().insert(getNamespacePrefix() + "insert", clientSetting);
    }
    public ClientAuthorizationGrantType get(Long id) {
        return this.getSqlSession().selectOne(getNamespacePrefix()+"getById", id);
    }
    public List<ClientAuthorizationGrantType> getByClientId(String clientId){
        return this.getSqlSession().selectList(getNamespacePrefix()+"getByClientId", clientId);
    }
    public int update(ClientAuthorizationGrantType setting) {
        return this.getSqlSession().update(getNamespacePrefix()+"update", setting);
    }
    public int delete(Long id) {
        return this.getSqlSession().delete(getNamespacePrefix()+"delete", id);
    }
}
