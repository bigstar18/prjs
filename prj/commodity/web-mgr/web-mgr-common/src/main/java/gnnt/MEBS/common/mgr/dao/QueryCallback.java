package gnnt.MEBS.common.mgr.dao;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Query;

/**
 * <p>
 * 查询回调接口 在执行query.list前调用;可以进行设置属性等操作 如：<br>
 * query.setCacheable(); query.setCacheRegion(); query.setCacheMode();
 * 
 * @author xuejt
 * 
 */
public interface QueryCallback {
	/**
	 * 执行查询
	 * 
	 * @param query
	 * @throws HibernateException
	 * @throws SQLException
	 */
	public void doInQuery(Query query) throws HibernateException, SQLException;
}
