package gnnt.MEBS.common.front.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import gnnt.MEBS.common.front.common.ProcedureParameter;
import gnnt.MEBS.common.front.model.StandardModel;

@Repository("com_standardDao")
public class StandardDao extends AbstractDao {
	protected final transient Log logger = LogFactory.getLog(getClass());

	@Resource(name = "sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public void add(StandardModel entity) {
		this.logger.debug("enter add");
		getHibernateTemplate().save(entity);
	}

	public void update(StandardModel entity) {
		this.logger.debug("enter update");
		getHibernateTemplate().update(entity);
	}

	public void delete(StandardModel entity) {
		this.logger.debug("enter delete");
		getHibernateTemplate().delete(entity);
	}

	public void deleteBYBulk(Collection<StandardModel> entities) {
		this.logger.debug("enter deleteBYBulk by getHibernateTemplate().deleteAll");
		getHibernateTemplate().deleteAll(entities);
	}

	public void deleteBYBulk(StandardModel entity, final Object[] ids) {
		this.logger.debug("enter deleteBYBulk");
		final String queryString = "delete " + entity.getClass().getName() + " where " + entity.fetchPKey().getKey() + " in (:ids) ";
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(org.hibernate.Session paramAnonymousSession) throws HibernateException, SQLException {
				Query localQuery = paramAnonymousSession.createQuery(queryString);
				localQuery.setParameterList("ids", ids);
				return Integer.valueOf(localQuery.executeUpdate());
			}
		});
	}

	public Object executeProcedure(final String procedureSql, final List<ProcedureParameter> parameterList) throws Exception {
		return getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(org.hibernate.Session paramAnonymousSession) throws HibernateException, SQLException {
				Connection localConnection = null;
				CallableStatement localCallableStatement = null;
				Hashtable localHashtable = new Hashtable();
				try {
					StandardDao.this.logger.debug("procedureSql=" + procedureSql);
					int i = 0;
					for (int j = 0; j < procedureSql.length(); j++) {
						if (procedureSql.charAt(j) == '?') {
							i++;
						}
					}
					if (parameterList.size() != i) {
						throw new Exception(" parameter  is not equal to placeholderCount ");
					}
					localConnection = paramAnonymousSession.connection();
					localCallableStatement = localConnection.prepareCall(procedureSql);
					ProcedureParameter localProcedureParameter;
					for (int j = 0; j < parameterList.size(); j++) {
						localProcedureParameter = (ProcedureParameter) parameterList.get(j);
						StandardDao.this.logger.debug("ParameterType: " + localProcedureParameter.getParameterType());
						StandardDao.this.logger.debug("SqlType: " + localProcedureParameter.getSqlType());
						StandardDao.this.logger.debug("Value: " + localProcedureParameter.getValue());
						if (localProcedureParameter.getParameterType() == 2) {
							localCallableStatement.setObject(j + 1, localProcedureParameter.getValue(), localProcedureParameter.getSqlType());
						} else {
							localCallableStatement.registerOutParameter(j + 1, localProcedureParameter.getSqlType());
						}
					}
					if (!localCallableStatement.execute()) {
						for (int j = 0; j < parameterList.size(); j++) {
							localProcedureParameter = (ProcedureParameter) parameterList.get(j);
							if ((localProcedureParameter.getParameterType() == 1) && (localProcedureParameter.getName() != null)
									&& (localProcedureParameter.getName().length() > 0)
									&& (!localHashtable.containsKey(localProcedureParameter.getName()))) {
								localHashtable.put(localProcedureParameter.getName(), localCallableStatement.getObject(j + 1));
							}
						}
					}
					return localHashtable;
				} catch (Exception localException3) {
					StandardDao.this.logger.debug("Failed executing procedureSql:" + procedureSql);
					StandardDao.this.logger.error("Error Message:" + localException3.getMessage());
					return localHashtable;
				} finally {
					try {
						localCallableStatement.close();
					} catch (Exception localException6) {
					}
					try {
						localConnection.close();
					} catch (Exception localException7) {
					}
				}
			}
		});
	}

	public int executeProcedure(final String procedureName, final Map<String, Object> map) {
		return ((Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(org.hibernate.Session paramAnonymousSession) throws HibernateException, SQLException {
				Query localQuery = paramAnonymousSession.getNamedQuery(procedureName);
				Iterator localIterator = map.keySet().iterator();
				while (localIterator.hasNext()) {
					String str = (String) localIterator.next();
					localQuery.setParameter(str, map.get(str));
				}
				int i = ((Number) localQuery.uniqueResult()).intValue();
				return Integer.valueOf(i);
			}
		})).intValue();
	}

	public Object executeProcedure(final String procedureSql, final Object[] params) throws Exception {
		return getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(org.hibernate.Session paramAnonymousSession) throws HibernateException, SQLException {
				Connection localConnection = null;
				CallableStatement localCallableStatement = null;
				Object localObject1 = null;
				try {
					StandardDao.this.logger.debug("procedureSql=" + procedureSql);
					for (int i = 0; i < params.length; i++) {
						StandardDao.this.logger.debug("params[" + i + "]: " + params[i]);
					}
					localConnection = paramAnonymousSession.connection();
					localCallableStatement = localConnection.prepareCall(procedureSql);
					localCallableStatement.registerOutParameter(1, 2);
					int i = 0;
					for (int j = 2; i < params.length; j++) {
						localCallableStatement.setObject(j, params[i]);
						i++;
					}
					if (!localCallableStatement.execute()) {
						localObject1 = localCallableStatement.getObject(1);
					}
					return localObject1;
				} catch (Exception localException3) {
					StandardDao.this.logger.debug("Failed executing procedureSql:" + procedureSql);
					StandardDao.this.logger.error("Error Message:" + localException3.getMessage());
					return localObject1;
				} finally {
					try {
						localCallableStatement.close();
					} catch (Exception localException6) {
					}
					try {
						localConnection.close();
					} catch (Exception localException7) {
					}
				}
			}
		});
	}

	public List<Map<String, String>> getBrokerName() throws SQLException {
		this.logger.debug("enter query broker name");
		Connection conn = getHibernateTemplate().getSessionFactory().getCurrentSession().connection();
		String sql = "select b1.name name, b1.brokerid brokerid,b2.name area,b1.areaid areaid from BR_BROKER b1,BR_BROKERAREA b2 where b1.areaid = b2.areaid(+)";
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		List<Map<String, String>> brokers = new ArrayList();
		while (rs.next()) {
			String name = rs.getString("name");
			String brokerId = rs.getString("brokerid");
			String area = rs.getString("area");
			int areaid = rs.getInt("areaid");
			Map<String, String> brokerMap = new HashMap();
			brokerMap.put("BROKERID", brokerId);
			brokerMap.put("NAME", name);
			brokerMap.put("AREA", area);
			brokerMap.put("AREAID", areaid + "");
			brokers.add(brokerMap);
		}
		pstm.close();
		rs.close();
		return brokers;
	}
}
