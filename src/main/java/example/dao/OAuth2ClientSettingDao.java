package example.dao;

import example.entity.OAuth2ClientSetting;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author macbookair
 *
 */
@Repository
public class OAuth2ClientSettingDao extends AbstractDao {
	public void insert(OAuth2ClientSetting clientSetting) {
        this.getSqlSession().insert(getNamespacePrefix() + "insert", clientSetting);
    }
	public OAuth2ClientSetting get(Long id) {
		return this.getSqlSession().selectOne(getNamespacePrefix()+"getById", id);
	}
	public List<OAuth2ClientSetting> getByClientId(String clientId){
		return this.getSqlSession().selectList(getNamespacePrefix()+"getByClientId", clientId);
	}
	public int update(OAuth2ClientSetting setting) {
		return this.getSqlSession().update(getNamespacePrefix()+"update", setting);
	}
	public int delete(Long id) {
		return this.getSqlSession().delete(getNamespacePrefix()+"delete", id);
	}
}
