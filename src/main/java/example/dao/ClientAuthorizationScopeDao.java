package example.dao;

import example.entity.ClientAuthorizationScope;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClientAuthorizationScopeDao extends AbstractDao{
    public void insert(ClientAuthorizationScope clientSetting) {
        this.getSqlSession().insert(getNamespacePrefix() + "insert", clientSetting);
    }
    public ClientAuthorizationScope get(Long id) {
        return this.getSqlSession().selectOne(getNamespacePrefix()+"getById", id);
    }
    public List<ClientAuthorizationScope> getByClientId(String clientId){
        return this.getSqlSession().selectList(getNamespacePrefix()+"getByClientId", clientId);
    }
    public int update(ClientAuthorizationScope setting) {
        return this.getSqlSession().update(getNamespacePrefix()+"update", setting);
    }
    public int delete(Long id) {
        return this.getSqlSession().delete(getNamespacePrefix()+"delete", id);
    }
}
