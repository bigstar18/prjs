package gnnt.MEBS.common.mgr.dao;

import gnnt.MEBS.common.mgr.common.ProcedureParameter;
import gnnt.MEBS.common.mgr.model.StandardModel;

import java.sql.SQLException;
import java.sql.Types;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

/**
 * 标准Dao类 包含增删改查功能
 * 
 * @author xuejt
 * 
 */
@Repository("com_standardDao")
public class StandardDao extends AbstractDao {
	protected transient final Log logger = LogFactory.getLog(this.getClass());

	// 为父类HibernateDaoSupport注入sessionFactory的值
	@Resource(name = "sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	/**
	 * 向数据库添加一条对应于一个业务对象实例的记录
	 * 
	 * @param entity
	 *            业务对象实例
	 */
	public void add(StandardModel entity) {
		logger.debug("enter add");
		getHibernateTemplate().save(entity);
	}

	/**
	 * 向数据库更新一条对应于一个业务对象实例的记录
	 * 
	 * @param entity
	 *            业务对象实例
	 */
	public void update(StandardModel entity) {
		logger.debug("enter update");
		getHibernateTemplate().update(entity);
	}

	/**
	 * 从数据库删除一条对应于一个业务对象的记录
	 * 
	 * @param entity
	 *            业务对象实例
	 */
	public void delete(StandardModel entity) {
		logger.debug("enter delete");
		getHibernateTemplate().delete(entity);
	}

	/**
	 * 从数据库中删除业务对象实例集合<BR/>
	 * 内部操作为循环删除，所以性能较低；如果操作量过大并且业务对象包含主键请使用bulkDelete方法批量删除
	 * 
	 * @param entities
	 *            业务对象数据集合
	 */
	public void deleteBYBulk(Collection<StandardModel> entities) {
		logger.debug("enter deleteBYBulk by getHibernateTemplate().deleteAll");
		getHibernateTemplate().deleteAll(entities);
	}

	/**
	 * 通过sql 批量删除<br>
	 * 使用此方法 业务对象实例不能为空 且entity.fetchPKey().getKey()有返回值
	 * 
	 * @param entity
	 *            业务对象实例
	 * @param ids
	 *            主键值数组
	 */
	public void deleteBYBulk(StandardModel entity, final Object[] ids) {
		logger.debug("enter deleteBYBulk");
		final String queryString = "delete " + entity.getClass().getName()
				+ " where " + entity.fetchPKey().getKey() + " in (:ids) ";
		getHibernateTemplate().execute(new HibernateCallback<Object>() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(queryString);
				query.setParameterList("ids", ids);
				return query.executeUpdate();
			}
		});
	}

	/**
	 * 批量修改对象中的某列的值
	 * 
	 * @param entity
	 *            实体对象
	 * @param columnAssignSql
	 *            要修改的列名=某值
	 * @param ids
	 *            多个对象 主键值数组
	 */
	public void updateBYBulk(StandardModel entity, String columnAssignSql,
			final Object[] ids) {
		logger.debug("enter updateBYBulk");
		final String queryString = "update " + entity.getClass().getName()
				+ " set " + columnAssignSql + " where "
				+ entity.fetchPKey().getKey() + " in (:ids) ";
		getHibernateTemplate().execute(new HibernateCallback<Object>() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(queryString);
				query.setParameterList("ids", ids);
				return query.executeUpdate();
			}
		});
	}

	/**
	 * 通过sql来针对数据库操作
	 * 
	 * @param sql
	 *            传入sql对数据库操作
	 */
	public void executeUpdateBySql(final String sql) {
		logger.debug("enter executeUpdateBySql");
		getHibernateTemplate().execute(new HibernateCallback<Object>() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createSQLQuery(sql);
				return query.executeUpdate();
			}
		});

	}

	

	/**
	 * 执行存储过程
	 * 
	 * @param procedureName
	 *            存储过程名称
	 * @param map
	 *            key:存储过程变量名 value:变量对应的值
	 * @return 如果查询结果有多个值则抛出错误； 如果查询结果有且只有一个值,返回一个object； 如果没值,返回null
	 */
	public int executeProcedure(final String procedureName,
			final Map<String, Object> map) {
		return (Integer) getHibernateTemplate().execute(
				new HibernateCallback<Object>() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.getNamedQuery(procedureName);
						for (String key : map.keySet()) {
							query.setParameter(key, map.get(key));
						}
						int count = ((Number) query.uniqueResult()).intValue();
						return count;
					}
				});
	}

	/**
	 * 执行存储过程
	 * 
	 * @param procedureSql
	 *            存储过程sql 如 {? = call getTestList(?)}
	 * @param params
	 *            存储过程sql中的占位符变量值
	 * @return 存储过程的返回值
	 * @throws Exception
	 */
	public Object executeProcedure(final String procedureSql,
			final Object[] params) throws Exception {
		return getHibernateTemplate().execute(new HibernateCallback<Object>() {
			@SuppressWarnings("deprecation")
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				java.sql.Connection conn = null;
				java.sql.CallableStatement call = null;
				Object rval = null;
				try {

					logger.debug("procedureSql=" + procedureSql);

					for (int i = 0; i < params.length; i++)
						logger.debug("params[" + i + "]: " + params[i]);

					conn = session.connection();
					call = conn.prepareCall(procedureSql);
					call.registerOutParameter(1, Types.NUMERIC);

					int i = 0;
					for (int j = 2; i < params.length; j++) {
						call.setObject(j, params[i]);
						i++;
					}

					if (!call.execute()) {
						rval = call.getObject(1);
					}
				} catch (Exception e) {
					logger.debug("Failed executing procedureSql:"
							+ procedureSql);
					logger.error("Error Message:" + e.getMessage());
					// throw e;
				} finally {
					try {
						call.close();
					} catch (Exception e) {
					}
					try {
						conn.close();
					} catch (Exception e) {
					}
				}
				return rval;
			}
		});
	}

	/**
	 * 执行存储过程
	 * 
	 * @param procedureSql
	 *            存储过程sql 如 {? = call getTestList(?)}
	 * @param parameterList
	 *            存储过程sql中的占位符对应的参数列表
	 * @return 存储过程输出Map key:参数名称 value：输出值
	 * @throws Exception
	 */
	public Object executeProcedure(final String procedureSql,
			final List<ProcedureParameter> parameterList) throws Exception {
		return getHibernateTemplate().execute(new HibernateCallback<Object>() {
			@SuppressWarnings("deprecation")
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				java.sql.Connection conn = null;
				java.sql.CallableStatement call = null;
				Map<String, Object> rval = new Hashtable<String, Object>();
				try {

					logger.debug("procedureSql=" + procedureSql);

					// 占位符数量
					int placeholderCount = 0;
					for (int i = 0; i < procedureSql.length(); i++) {
						if (procedureSql.charAt(i) == '?')
							placeholderCount++;
					}

					if (parameterList.size() != placeholderCount) {
						throw new Exception(
								" parameter  is not equal to placeholderCount ");
					}

					conn = session.connection();
					call = conn.prepareCall(procedureSql);

					for (int i = 0; i < parameterList.size(); i++) {
						ProcedureParameter procedureParameter = parameterList
								.get(i);
						logger.debug("ParameterType: "
								+ procedureParameter.getParameterType());
						logger.debug("SqlType: "
								+ procedureParameter.getSqlType());
						logger.debug("Value: " + procedureParameter.getValue());
						if (procedureParameter.getParameterType() == ProcedureParameter.INPARAMETER) {
							call.setObject(i + 1,
									procedureParameter.getValue(),
									procedureParameter.getSqlType());
						} else {
							call.registerOutParameter(i + 1, procedureParameter
									.getSqlType());
						}
					}

					if (!call.execute()) {
						// 遍历传入的参数列表，如果为输出类型则将输出值写入到参数对应的Value中
						for (int i = 0; i < parameterList.size(); i++) {
							ProcedureParameter procedureParameter = parameterList
									.get(i);
							if (procedureParameter.getParameterType() == ProcedureParameter.OUTPARAMETER) {
								if (procedureParameter.getName() != null
										&& procedureParameter.getName()
												.length() > 0) {
									if (!rval.containsKey(procedureParameter
											.getName())) {
										rval.put(procedureParameter.getName(),
												call.getObject(i + 1));
									}
								}
							}
						}
					}
				} catch (Exception e) {
					logger.debug("Failed executing procedureSql:"
							+ procedureSql);
					logger.error("Error Message:" + e.getMessage());
					// throw e;
				} finally {
					try {
						call.close();
					} catch (Exception e) {
					}
					try {
						conn.close();
					} catch (Exception e) {
					}
				}
				return rval;
			}
		});
	}

	
}
