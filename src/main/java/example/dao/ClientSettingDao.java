package example.dao;

import example.entity.ClientSetting;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author macbookair
 *
 */
@Repository
public class ClientSettingDao extends AbstractDao {
	public void insert(ClientSetting clientSetting) {
        this.getSqlSession().insert(getNamespacePrefix() + "insert", clientSetting);
    }
	public ClientSetting get(Long id) {
		return this.getSqlSession().selectOne(getNamespacePrefix()+"getById", id);
	}
	public List<ClientSetting> getByClientId(String clientId){
		return this.getSqlSession().selectList(getNamespacePrefix()+"getByClientId", clientId);
	}
	public int update(ClientSetting setting) {
		return this.getSqlSession().update(getNamespacePrefix()+"update", setting);
	}
	public int delete(Long id) {
		return this.getSqlSession().delete(getNamespacePrefix()+"delete", id);
	}
}
