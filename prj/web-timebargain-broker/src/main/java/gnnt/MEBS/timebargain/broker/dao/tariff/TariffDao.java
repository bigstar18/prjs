package gnnt.MEBS.timebargain.broker.dao.tariff;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import gnnt.MEBS.common.broker.common.Page;
import gnnt.MEBS.common.broker.common.PageRequest;
import gnnt.MEBS.common.broker.common.QueryConditions;
import gnnt.MEBS.common.broker.dao.QueryCallback;

@Repository("com_tariffDao")
public class TariffDao extends HibernateDaoSupport {
	private final transient Log logger = LogFactory.getLog(TariffDao.class);

	@Resource(name = "sessionFactory")
	public void setSuperSessionFactory(SessionFactory paramSessionFactory) {
		super.setSessionFactory(paramSessionFactory);
	}

	public Page getPage(PageRequest<?> paramPageRequest, String paramString) {
		this.logger.debug("enter getPage");
		if (paramPageRequest == null)
			throw new IllegalArgumentException("pageRequest 不允许为空");
		if (paramString == null)
			throw new IllegalArgumentException("entity 不允许为空");
		String str = new StringBuilder().append(" from ").append(paramString).append(" primary where 1=1 ").toString();
		Object[] arrayOfObject = null;
		Object localObject;
		if ((paramPageRequest.getFilters() instanceof String)) {
			localObject = (String) paramPageRequest.getFilters();
			str = new StringBuilder().append(str).append((String) localObject).toString();
		} else if ((paramPageRequest.getFilters() instanceof QueryConditions)) {
			localObject = (QueryConditions) paramPageRequest.getFilters();
			if ((localObject != null) && (((QueryConditions) localObject).getFieldsSqlClause() != null)) {
				arrayOfObject = ((QueryConditions) localObject).getValueArray();
				str = new StringBuilder().append(str).append(" and ").append(((QueryConditions) localObject).getFieldsSqlClause()).toString();
			}
		}
		return queryBySQL(str, arrayOfObject, paramPageRequest, null);
	}

	public Page getPageSurroundSql(PageRequest<?> paramPageRequest, String paramString1, String paramString2, String paramString3) {
		this.logger.debug("enter getPage");
		if (paramPageRequest == null)
			throw new IllegalArgumentException("pageRequest 不允许为空");
		if (paramString3.indexOf("?") <= 0)
			throw new IllegalArgumentException("sql语句格式错误");
		StringBuilder localStringBuilder = new StringBuilder(paramString1);
		Object[] arrayOfObject = null;
		Object localObject;
		if ((paramPageRequest.getFilters() instanceof String)) {
			localObject = (String) paramPageRequest.getFilters();
			localStringBuilder.append((String) localObject);
		} else if ((paramPageRequest.getFilters() instanceof QueryConditions)) {
			localObject = (QueryConditions) paramPageRequest.getFilters();
			if ((localObject != null) && (((QueryConditions) localObject).getFieldsSqlClause() != null)) {
				arrayOfObject = ((QueryConditions) localObject).getValueArray();
				localStringBuilder = localStringBuilder
						.append(new StringBuilder().append(" and ").append(((QueryConditions) localObject).getFieldsSqlClause()).toString());
			}
		}
		localStringBuilder.append(paramString2);
		return queryBySQL(
				new StringBuilder().append("from (").append(paramString3.replace("?", localStringBuilder.toString())).append(")").toString(),
				arrayOfObject, paramPageRequest, null);
	}

	private Page queryBySQL(String paramString, Object[] paramArrayOfObject, PageRequest<?> paramPageRequest, QueryCallback paramQueryCallback) {
		this.logger.debug("enter queryByHQL!");
		int i = totalRow(paramString, paramArrayOfObject);
		int j = (paramPageRequest.getPageNumber() - 1) * paramPageRequest.getPageSize();
		paramString = new StringBuilder().append(paramString).append(paramPageRequest.getSortColumns()).toString();
		int k = paramPageRequest.getPageSize();
		List localList = findAllBySQL(j, k, paramString, paramArrayOfObject, paramQueryCallback);
		Page localPage = new Page(paramPageRequest.getPageNumber(), paramPageRequest.getPageSize(), i, localList);
		return localPage;
	}

	private List findAllBySQL(final int paramInt1, final int paramInt2, final String paramString, final Object[] paramArrayOfObject,
			final QueryCallback paramQueryCallback) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session paramAnonymousSession) throws HibernateException, SQLException {
				TariffDao.this.logger.debug("findAllBySQL:" + paramString);
				Query localQuery = paramAnonymousSession.createSQLQuery("select * " + paramString)
						.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				if ((paramArrayOfObject != null) && (paramArrayOfObject.length > 0))
					for (int i = 0; i < paramArrayOfObject.length; i++) {
						TariffDao.this.logger.debug("   params[i]:" + paramArrayOfObject[i]);
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
			throw new IllegalArgumentException("sql必须以from开头");
		final String str = new StringBuilder().append("select count(*) ").append(paramString).append("").toString();
		return ((Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session paramAnonymousSession) throws HibernateException, SQLException {
				TariffDao.this.logger.debug("count sql:" + str);
				SQLQuery localSQLQuery = paramAnonymousSession.createSQLQuery(str);
				if ((paramArrayOfObject != null) && (paramArrayOfObject.length > 0))
					for (int i = 0; i < paramArrayOfObject.length; i++) {
						TariffDao.this.logger.debug("   params[i]:" + paramArrayOfObject[i]);
						localSQLQuery.setParameter(i, paramArrayOfObject[i]);
					}
				TariffDao.this.logger.debug("--------------------" + localSQLQuery.uniqueResult());
				int i = ((Number) localSQLQuery.uniqueResult()).intValue();
				TariffDao.this.logger.debug("count:" + i);
				return Integer.valueOf(i);
			}
		})).intValue();
	}
}