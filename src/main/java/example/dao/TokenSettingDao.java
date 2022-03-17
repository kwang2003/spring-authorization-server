package example.dao;

import example.entity.TokenSetting;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author macbookair
 *
 */
@Repository
public class TokenSettingDao extends AbstractDao {
	public void insert(TokenSetting tokenSetting) {
        this.getSqlSession().insert(getNamespacePrefix() + "insert", tokenSetting);
    }
	public TokenSetting get(Long id) {
		return this.getSqlSession().selectOne(getNamespacePrefix()+"getById", id);
	}
	public List<TokenSetting> getByClientId(String clientId){
		return this.getSqlSession().selectList(getNamespacePrefix()+"getByClientId", clientId);
	}
	public int update(TokenSetting setting) {
		return this.getSqlSession().update(getNamespacePrefix()+"update", setting);
	}
	public int delete(Long id) {
		return this.getSqlSession().delete(getNamespacePrefix()+"delete", id);
	}
}
