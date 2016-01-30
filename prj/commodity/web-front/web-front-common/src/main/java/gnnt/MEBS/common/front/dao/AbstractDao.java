package gnnt.MEBS.common.front.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import gnnt.MEBS.common.front.common.Page;
import gnnt.MEBS.common.front.common.PageRequest;
import gnnt.MEBS.common.front.common.QueryConditions;
import gnnt.MEBS.common.front.model.StandardModel;

public abstract class AbstractDao extends HibernateDaoSupport {
	public List<StandardModel> getListByBulk(StandardModel paramStandardModel, final Object[] paramArrayOfObject) {
		this.logger.debug("enter getListByBulk");
		if (paramStandardModel == null) {
			throw new IllegalArgumentException("业务对象为空，所以操作表未知，不允许通过主键数组批量查询！");
		}
		if ((paramStandardModel.fetchPKey() == null) || (paramStandardModel.fetchPKey().getKey() == null)
				|| (paramStandardModel.fetchPKey().getKey().length() == 0)) {
			throw new IllegalArgumentException("业务对象未设置主键，不允许通过主键数组批量查询！");
		}
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
		if (paramStandardModel.fetchPKey() != null) {
			try {
				return (StandardModel) getHibernateTemplate().get(paramStandardModel.getClass(), paramStandardModel.fetchPKey().getValue());
			} catch (Exception localException) {
				localException.printStackTrace();
				System.out.println(localException.getMessage());
			}
		}
		return (StandardModel) getHibernateTemplate().get(paramStandardModel.getClass(), paramStandardModel);
	}

	public Page<StandardModel> getPage(PageRequest<?> paramPageRequest, StandardModel paramStandardModel) {
		this.logger.debug("enter getPage");
		if (paramPageRequest == null) {
			throw new IllegalArgumentException("pageRequest 不允许为空");
		}
		if (paramStandardModel == null) {
			throw new IllegalArgumentException("entity 不允许为空");
		}
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

	public void flush() {
		getHibernateTemplate().flush();
	}

	public void evict(StandardModel paramStandardModel) {
		getHibernateTemplate().evict(paramStandardModel);
	}

	public Page<StandardModel> queryByHQL(String paramString, Object[] paramArrayOfObject, PageRequest<?> paramPageRequest,
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
				AbstractDao.this.logger.debug("findAllByHQL:" + paramString);
				Query localQuery = paramAnonymousSession.createQuery(paramString);
				if ((paramArrayOfObject != null) && (paramArrayOfObject.length > 0)) {
					for (int i = 0; i < paramArrayOfObject.length; i++) {
						AbstractDao.this.logger.debug("   params[i]:" + paramArrayOfObject[i]);
						localQuery.setParameter(i, paramArrayOfObject[i]);
					}
				}
				localQuery.setFirstResult(paramInt1);
				localQuery.setMaxResults(paramInt2);
				if (paramQueryCallback != null) {
					paramQueryCallback.doInQuery(localQuery);
				}
				return localQuery.list();
			}
		});
	}

	private int totalRow(String paramString, final Object[] paramArrayOfObject) {
		if (!paramString.trim().startsWith("from")) {
			throw new IllegalArgumentException("hql必须以from开头");
		}
		final String str = "select count(*) " + paramString + "";
		return ((Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session paramAnonymousSession) throws HibernateException, SQLException {
				AbstractDao.this.logger.debug("count hql:" + str);
				Query localQuery = paramAnonymousSession.createQuery(str);
				if ((paramArrayOfObject != null) && (paramArrayOfObject.length > 0)) {
					for (int i = 0; i < paramArrayOfObject.length; i++) {
						AbstractDao.this.logger.debug("   params[i]:" + paramArrayOfObject[i]);
						localQuery.setParameter(i, paramArrayOfObject[i]);
					}
				}
				AbstractDao.this.logger.debug("--------------------");
				int i = ((Number) localQuery.uniqueResult()).intValue();
				AbstractDao.this.logger.debug("count:" + i);
				return Integer.valueOf(i);
			}
		})).intValue();
	}
}
