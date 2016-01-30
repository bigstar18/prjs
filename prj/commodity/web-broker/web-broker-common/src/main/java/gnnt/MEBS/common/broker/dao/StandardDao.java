package gnnt.MEBS.common.broker.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import gnnt.MEBS.common.broker.common.Page;
import gnnt.MEBS.common.broker.common.PageRequest;
import gnnt.MEBS.common.broker.common.QueryConditions;
import gnnt.MEBS.common.broker.model.StandardModel;

@Repository("com_standardDao")
public class StandardDao extends HibernateDaoSupport {
	protected final transient Log logger = LogFactory.getLog(getClass());

	@Resource(name = "sessionFactory")
	public void setSuperSessionFactory(SessionFactory paramSessionFactory) {
		super.setSessionFactory(paramSessionFactory);
	}

	public void add(StandardModel paramStandardModel) {
		this.logger.debug("enter add");
		getHibernateTemplate().save(paramStandardModel);
	}

	public void update(StandardModel paramStandardModel) {
		this.logger.debug("enter update");
		getHibernateTemplate().update(paramStandardModel);
	}

	public void delete(StandardModel paramStandardModel) {
		this.logger.debug("enter delete");
		getHibernateTemplate().delete(paramStandardModel);
	}

	public void deleteBYBulk(Collection<StandardModel> paramCollection) {
		this.logger.debug("enter deleteBYBulk by getHibernateTemplate().deleteAll");
		getHibernateTemplate().deleteAll(paramCollection);
	}

	public void deleteBYBulk(StandardModel paramStandardModel, final Object[] paramArrayOfObject) {
		this.logger.debug("enter deleteBYBulk");
		final String str = "delete " + paramStandardModel.getClass().getName() + " where " + paramStandardModel.fetchPKey().getKey() + " in (:ids) ";
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session paramAnonymousSession) throws HibernateException, SQLException {
				Query localQuery = paramAnonymousSession.createQuery(str);
				localQuery.setParameterList("ids", paramArrayOfObject);
				return Integer.valueOf(localQuery.executeUpdate());
			}
		});
	}

	public void updateBYBulk(StandardModel paramStandardModel, String paramString, final Object[] paramArrayOfObject) {
		this.logger.debug("enter updateBYBulk");
		final String str = "update " + paramStandardModel.getClass().getName() + " set " + paramString + " where "
				+ paramStandardModel.fetchPKey().getKey() + " in (:ids) ";
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session paramAnonymousSession) throws HibernateException, SQLException {
				Query localQuery = paramAnonymousSession.createQuery(str);
				localQuery.setParameterList("ids", paramArrayOfObject);
				return Integer.valueOf(localQuery.executeUpdate());
			}
		});
	}

	public void executeUpdateBySql(final String paramString) {
		this.logger.debug("enter executeUpdateBySql");
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session paramAnonymousSession) throws HibernateException, SQLException {
				SQLQuery localSQLQuery = paramAnonymousSession.createSQLQuery(paramString);
				return Integer.valueOf(localSQLQuery.executeUpdate());
			}
		});
	}

	public List<StandardModel> getListByBulk(StandardModel paramStandardModel, final Object[] paramArrayOfObject) {
		this.logger.debug("enter getListByBulk");
		if (paramStandardModel == null)
			throw new IllegalArgumentException("业务对象为空，所以操作表未知，不允许通过主键数组批量查询！");
		if ((paramStandardModel.fetchPKey() == null) || (paramStandardModel.fetchPKey().getKey() == null)
				|| (paramStandardModel.fetchPKey().getKey().length() == 0))
			throw new IllegalArgumentException("业务对象未设置主键，不允许通过主键数组批量查询！");
		final StandardModel localStandardModel = paramStandardModel.clone();
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session paramAnonymousSession) throws HibernateException, SQLException {
				return paramAnonymousSession.createCriteria(localStandardModel.getClass())
						.add(Restrictions.in(localStandardModel.fetchPKey().getKey(), paramArrayOfObject)).list();
			}
		});
	}

	public StandardModel get(StandardModel paramStandardModel) {
		this.logger.debug("enter get");
		if (paramStandardModel.fetchPKey() != null)
			return (StandardModel) getHibernateTemplate().get(paramStandardModel.getClass(), paramStandardModel.fetchPKey().getValue());
		return (StandardModel) getHibernateTemplate().get(paramStandardModel.getClass(), paramStandardModel);
	}

	public Page<StandardModel> getPage(PageRequest<?> paramPageRequest, StandardModel paramStandardModel) {
		this.logger.debug("enter getPage");
		if (paramPageRequest == null)
			throw new IllegalArgumentException("pageRequest 不允许为空");
		if (paramStandardModel == null)
			throw new IllegalArgumentException("entity 不允许为空");
		String str = " from " + paramStandardModel.getClass().getName() + " as primary where 1=1 ";
		Object[] arrayOfObject = null;
		Object localObject;
		if ((paramPageRequest.getFilters() instanceof String)) {
			localObject = (String) paramPageRequest.getFilters();
			str = str + (String) localObject;
		} else if ((paramPageRequest.getFilters() instanceof QueryConditions)) {
			localObject = (QueryConditions) paramPageRequest.getFilters();
			if ((localObject != null) && (((QueryConditions) localObject).getFieldsSqlClause() != null)) {
				arrayOfObject = ((QueryConditions) localObject).getValueArray();
				str = str + " and " + ((QueryConditions) localObject).getFieldsSqlClause();
			}
		}
		return queryByHQL(str, arrayOfObject, paramPageRequest, null);
	}

	public List<StandardModel> queryBySql(final String paramString, final StandardModel paramStandardModel) {
		List localList = (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session paramAnonymousSession) throws HibernateException, SQLException {
				SQLQuery localSQLQuery = paramAnonymousSession.createSQLQuery(paramString).addEntity(paramStandardModel.getClass());
				return localSQLQuery.list();
			}
		});
		return localList;
	}

	public List<Map<Object, Object>> queryBySql(final String paramString) {
		List localList = (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session paramAnonymousSession) throws HibernateException, SQLException {
				Query localQuery = paramAnonymousSession.createSQLQuery(paramString).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				return localQuery.list();
			}
		});
		return localList;
	}

	public int executeProcedure(final String paramString, final Map<String, Object> paramMap) {
		return ((Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session paramAnonymousSession) throws HibernateException, SQLException {
				Query localQuery = paramAnonymousSession.getNamedQuery(paramString);
				Iterator localIterator = paramMap.keySet().iterator();
				while (localIterator.hasNext()) {
					String str = (String) localIterator.next();
					localQuery.setParameter(str, paramMap.get(str));
				}
				int i = ((Number) localQuery.uniqueResult()).intValue();
				return Integer.valueOf(i);
			}
		})).intValue();
	}

	public Object executeProcedure(final String paramString, final Object[] paramArrayOfObject) throws Exception {
		return getHibernateTemplate().execute(new HibernateCallback<Object>() {
			public Object doInHibernate(Session paramAnonymousSession) throws HibernateException, SQLException {
				Connection localConnection = null;
				CallableStatement localCallableStatement = null;
				Object localObject1 = null;
				try {
					StandardDao.this.logger.debug("procedureSql=" + paramString);
					for (int i = 0; i < paramArrayOfObject.length; i++)
						StandardDao.this.logger.debug("params[" + i + "]: " + paramArrayOfObject[i]);
					localConnection = paramAnonymousSession.connection();
					localCallableStatement = localConnection.prepareCall(paramString);
					localCallableStatement.registerOutParameter(1, 2);
					int i = 0;
					for (int j = 2; i < paramArrayOfObject.length; j++) {
						localCallableStatement.setObject(j, paramArrayOfObject[i]);
						i++;
					}
					if (!localCallableStatement.execute())
						localObject1 = localCallableStatement.getObject(1);
				} catch (Exception localException3) {
					StandardDao.this.logger.debug("Failed executing procedureSql:" + paramString);
					StandardDao.this.logger.error("Error Message:" + localException3.getMessage());
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
				return localObject1;
			}
		});
	}

	public void flush() {
		getHibernateTemplate().flush();
	}

	public void evict(StandardModel paramStandardModel) {
		getHibernateTemplate().evict(paramStandardModel);
	}

	private Page<StandardModel> queryByHQL(String paramString, Object[] paramArrayOfObject, PageRequest<?> paramPageRequest,
			QueryCallback paramQueryCallback) {
		this.logger.debug("enter queryByHQL!");
		int i = totalRow(paramString, paramArrayOfObject);
		int j = (paramPageRequest.getPageNumber() - 1) * paramPageRequest.getPageSize();
		paramString = paramString + paramPageRequest.getSortColumns();
		int k = paramPageRequest.getPageSize();
		List localList = findAllByHQL(j, k, paramString, paramArrayOfObject, paramQueryCallback);
		Page localPage = new Page(paramPageRequest.getPageNumber(), paramPageRequest.getPageSize(), i, localList);
		return localPage;
	}

	private List<StandardModel> findAllByHQL(final int paramInt1, final int paramInt2, final String paramString, final Object[] paramArrayOfObject,
			final QueryCallback paramQueryCallback) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session paramAnonymousSession) throws HibernateException, SQLException {
				StandardDao.this.logger.debug("findAllByHQL:" + paramString);
				Query localQuery = paramAnonymousSession.createQuery(paramString);
				if ((paramArrayOfObject != null) && (paramArrayOfObject.length > 0))
					for (int i = 0; i < paramArrayOfObject.length; i++) {
						StandardDao.this.logger.debug("   params[i]:" + paramArrayOfObject[i]);
						localQuery.setParameter(i, paramArrayOfObject[i]);
					}
				localQuery.setFirstResult(paramInt1);
				localQuery.setMaxResults(paramInt2);
				if (paramQueryCallback != null)
					paramQueryCallback.doInQuery(localQuery);
				return localQuery.list();
			}
		});
	}

	private int totalRow(String paramString, final Object[] paramArrayOfObject) {
		if (!paramString.trim().startsWith("from"))
			throw new IllegalArgumentException("hql必须以from开头");
		final String str = "select count(*) " + paramString + "";
		return ((Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session paramAnonymousSession) throws HibernateException, SQLException {
				StandardDao.this.logger.debug("count hql:" + str);
				Query localQuery = paramAnonymousSession.createQuery(str);
				if ((paramArrayOfObject != null) && (paramArrayOfObject.length > 0))
					for (int i = 0; i < paramArrayOfObject.length; i++) {
						StandardDao.this.logger.debug("   params[i]:" + paramArrayOfObject[i]);
						localQuery.setParameter(i, paramArrayOfObject[i]);
					}
				StandardDao.this.logger.debug("--------------------" + localQuery.uniqueResult());
				int i = ((Number) localQuery.uniqueResult()).intValue();
				StandardDao.this.logger.debug("count:" + i);
				return Integer.valueOf(i);
			}
		})).intValue();
	}
}