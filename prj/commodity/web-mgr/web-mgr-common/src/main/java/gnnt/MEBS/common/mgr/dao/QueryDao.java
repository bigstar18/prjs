package gnnt.MEBS.common.mgr.dao;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

/**
 * 只有查询相关数据操作<br>
 * 使用查询的数据源进行查询
 * 
 * @author xuejt
 * 
 */
@Repository("com_queryDao")
public class QueryDao extends AbstractDao {
	protected transient final Log logger = LogFactory.getLog(this.getClass());

	// 为父类HibernateDaoSupport注入sessionFactory的值
	@Resource(name = "sessionFactoryForQuery")
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

}
