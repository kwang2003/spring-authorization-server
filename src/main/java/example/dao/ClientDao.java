package example.dao;

import example.entity.Client;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class ClientDao extends AbstractDao{
    public void insert(Client client) {
        client.setClientId(UUID.randomUUID().toString());
        this.getSqlSession().insert(getNamespacePrefix() + "insert", client);
    }
    public Client get(String id) {
        return this.getSqlSession().selectOne(getNamespacePrefix()+"selectByPrimaryKey", id);
    }
    public Client getByClientId(String clientId) {
        return this.getSqlSession().selectOne(getNamespacePrefix()+"getByClientId", clientId);
    }
}
