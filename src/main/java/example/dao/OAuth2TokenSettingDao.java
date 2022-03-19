package example.dao;

import example.entity.OAuth2TokenSetting;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author macbookair
 *
 */
@Repository
public class OAuth2TokenSettingDao extends AbstractDao {
	public void insert(OAuth2TokenSetting tokenSetting) {
        this.getSqlSession().insert(getNamespacePrefix() + "insert", tokenSetting);
    }
	public OAuth2TokenSetting get(Long id) {
		return this.getSqlSession().selectOne(getNamespacePrefix()+"getById", id);
	}
	public List<OAuth2TokenSetting> getByClientId(String clientId){
		return this.getSqlSession().selectList(getNamespacePrefix()+"getByClientId", clientId);
	}
	public int update(OAuth2TokenSetting setting) {
		return this.getSqlSession().update(getNamespacePrefix()+"update", setting);
	}
	public int delete(Long id) {
		return this.getSqlSession().delete(getNamespacePrefix()+"delete", id);
	}
}
