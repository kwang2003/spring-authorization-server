package example.dao;

import example.entity.ClientRedirectUri;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClientRedirectUriDao extends AbstractDao{
    public void insert(ClientRedirectUri clientSetting) {
        this.getSqlSession().insert(getNamespacePrefix() + "insert", clientSetting);
    }
    public ClientRedirectUri get(Long id) {
        return this.getSqlSession().selectOne(getNamespacePrefix()+"getById", id);
    }
    public List<ClientRedirectUri> getByClientId(String clientId){
        return this.getSqlSession().selectList(getNamespacePrefix()+"getByClientId", clientId);
    }
    public int update(ClientRedirectUri setting) {
        return this.getSqlSession().update(getNamespacePrefix()+"update", setting);
    }
    public int delete(Long id) {
        return this.getSqlSession().delete(getNamespacePrefix()+"delete", id);
    }
}
