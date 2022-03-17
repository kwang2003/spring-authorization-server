package example.dao;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.Getter;

/**
 * @author macbookair
 *
 */
public abstract class AbstractDao extends SqlSessionDaoSupport{
	@Getter
	private String namespacePrefix = getClass().getName()+".";
	@Override
	@Autowired
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
}
}
