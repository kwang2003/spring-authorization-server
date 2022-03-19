package example.dao;

import example.entity.OAuth2Client;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class OAuth2ClientDao extends AbstractDao{
    public void insert(OAuth2Client client) {
        client.setClientId(UUID.randomUUID().toString());
        this.getSqlSession().insert(getNamespacePrefix() + "insert", client);
    }
    public OAuth2Client get(String id) {
        return this.getSqlSession().selectOne(getNamespacePrefix()+"selectByPrimaryKey", id);
    }
    public OAuth2Client getByClientId(String clientId) {
        return this.getSqlSession().selectOne(getNamespacePrefix()+"getByClientId", clientId);
    }
}
