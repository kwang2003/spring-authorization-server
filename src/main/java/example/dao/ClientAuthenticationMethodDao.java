package example.dao;

import example.entity.ClientAuthenticationMethod;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClientAuthenticationMethodDao extends AbstractDao{
    public void insert(ClientAuthenticationMethod clientSetting) {
        this.getSqlSession().insert(getNamespacePrefix() + "insert", clientSetting);
    }
    public ClientAuthenticationMethod get(Long id) {
        return this.getSqlSession().selectOne(getNamespacePrefix()+"getById", id);
    }
    public List<ClientAuthenticationMethod> getByClientId(String clientId){
        return this.getSqlSession().selectList(getNamespacePrefix()+"getByClientId", clientId);
    }
    public int update(ClientAuthenticationMethod setting) {
        return this.getSqlSession().update(getNamespacePrefix()+"update", setting);
    }
    public int delete(Long id) {
        return this.getSqlSession().delete(getNamespacePrefix()+"delete", id);
    }
}
