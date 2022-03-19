package example.dao;

import com.google.common.collect.Maps;
import example.entity.OAuth2AuthorizationConsent;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @author macbookair
 *
 */
@Repository
public class OAuth2AuthorizationConsentDao extends AbstractDao {
	public void insert(OAuth2AuthorizationConsent clientSetting) {
        this.getSqlSession().insert(getNamespacePrefix() + "insert", clientSetting);
    }
	public OAuth2AuthorizationConsent get(Long id) {
		return this.getSqlSession().selectOne(getNamespacePrefix()+"getById", id);
	}
	public OAuth2AuthorizationConsent getByClientIdPrincipalName(String clientId,String principalName){
		Map<String,Object> param = Maps.newHashMap();
		param.put("clientId",clientId);
		param.put("principalName",principalName);
		return this.getSqlSession().selectOne(getNamespacePrefix()+"getByClientIdPrincipalName", param);
	}
	public int update(OAuth2AuthorizationConsent setting) {
		return this.getSqlSession().update(getNamespacePrefix()+"update", setting);
	}
	public int delete(String clientId,String principalName) {
		Map<String,Object> param = Maps.newHashMap();
		param.put("clientId",clientId);
		param.put("principalName",principalName);
		return this.getSqlSession().delete(getNamespacePrefix()+"delete", param);
	}
}
