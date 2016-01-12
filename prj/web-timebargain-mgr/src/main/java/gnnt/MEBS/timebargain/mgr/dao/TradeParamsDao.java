package gnnt.MEBS.timebargain.mgr.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.common.QueryConditions;
import gnnt.MEBS.common.mgr.dao.QueryCallback;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.timebargain.mgr.exception.DeleteCheckException;
import gnnt.MEBS.timebargain.mgr.model.apply.Apply;
import gnnt.MEBS.timebargain.mgr.model.delay.CommoditySettleProp;
import gnnt.MEBS.timebargain.mgr.model.tradeparams.Commodity;
import gnnt.MEBS.timebargain.mgr.model.tradeparams.Tariff;

@Repository("com_tradeParamsDao")
public class TradeParamsDao extends HibernateDaoSupport {
	private final transient Log logger = LogFactory.getLog(TradeParamsDao.class);

	@Resource(name = "sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public List executeSelect(final String sqlName) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List list = session.getNamedQuery(sqlName).list();
				return list;
			}
		});
	}

	public List executeSqlById(final String sql, final String id) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List list = session.createQuery(sql).setString(0, id).list();
				return list;
			}
		});
	}

	public List executeSqlQuery(final String sql) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				return query.list();
			}
		});
	}

	private void executeUpdate(final String sql) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sql);

				return Integer.valueOf(query.executeUpdate());
			}
		});
	}

	private void executeSQLApply(final String sql, Apply apply) {
		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Work work = new Work() {
					public void execute(Connection connection) throws SQLException {
						java.sql.PreparedStatement ps = connection.prepareStatement(sql);
					}
				};
				session.doWork(work);
				return null;
			}
		});
	}

	public void executeFunctionApply(final Apply apply) {
		if (logger.isDebugEnabled())
			logger.debug("inserting Apply...");
		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Work work = new Work() {

					public void execute(Connection connection) throws SQLException {
						CallableStatement cs = connection.prepareCall("{ ?=call FN_T_CREATEAPPLY(?, ?, ?, ?) }");
						cs.setString(2, apply.getContent());
						cs.setInt(3, apply.getApplyType());
						cs.setInt(4, apply.getStatus());
						cs.setString(5, apply.getProposer());
						cs.registerOutParameter(1, 2);
						cs.executeUpdate();
					}

				};
				session.doWork(work);
				return null;
			}
		});
	}

	public void addCommodity(Commodity commodity) {
		if (this.logger.isDebugEnabled())
			this.logger.debug("enter TradeParamsDao's addCommodity");
		try {
			getHibernateTemplate().saveOrUpdate(commodity);
			clearCache();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void executeFunctionCommodity(final String commodityID) {
		if (this.logger.isDebugEnabled()) {
			this.logger.debug("inserting Commodity...");
		}
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Work work = new Work() {
					public void execute(Connection connection) throws SQLException {
						CallableStatement cs = connection.prepareCall("{ call SP_F_SynchCommodity(?) }");
						cs.setString(1, commodityID);
						cs.executeUpdate();
					}
				};
				session.doWork(work);

				return null;
			}
		});
	}

	public void deleteCommodity(List list) {
		if (this.logger.isDebugEnabled()) {
			this.logger.debug("enter TradeParamsDao's deleteCommodity");
		}
		Iterator it = list.iterator();
		String sql = "from Tariff t where t.commodityID = ?";
		while (it.hasNext()) {
			Commodity commodity = (Commodity) it.next();
			List tariffList = executeSqlById(sql, commodity.getCommodityID());
			getHibernateTemplate().deleteAll(tariffList);
		}

		getHibernateTemplate().deleteAll(list);
	}

	public void addTariffs(List<Tariff> tariffList) {
		if (this.logger.isDebugEnabled()) {
			this.logger.debug("enter TradeParamsDao's addTariffs");
		}
		getHibernateTemplate().saveOrUpdateAll(tariffList);
	}

	public void commoditySettlePropAdd(List<CommoditySettleProp> list) {
		executeUpdate("delete from " + CommoditySettleProp.class.getName());
		getHibernateTemplate().saveOrUpdateAll(list);
	}

	public void add(StandardModel entity) {
		if (this.logger.isDebugEnabled()) {
			this.logger.debug("enter TradeParamsDao's add");
		}
		getHibernateTemplate().save(entity);
		clearCache();
	}

	public void update(StandardModel entity) {
		if (this.logger.isDebugEnabled()) {
			this.logger.debug("enter TradeParamsDao's update");
		}
		getHibernateTemplate().update(entity);
		clearCache();
	}

	private void clearCache() {
		getHibernateTemplate().flush();
		getHibernateTemplate().clear();
	}

	public List getBmkTj(String tableName, String tjStr) throws Exception {
		if (this.logger.isDebugEnabled()) {
			this.logger.debug("retrieving all bmk with tj ...");
		}

		String sqlStr = "select * from " + tableName + (tjStr == null ? "" : tjStr);

		this.logger.debug("sqlStr: " + sqlStr);
		return executeSqlQuery(sqlStr);
	}

	public void deleleCheck(String sql, Object[] params, String alertMsg) throws Exception {
		this.logger.debug("enter deleleCheck");
		int count = totalRow(sql, params);
		if (count > 0)
			throw new DeleteCheckException(alertMsg);
	}

	public int deleleCheck(String sql, Object[] params) throws Exception {
		this.logger.debug("enter deleleCheck");
		int count = totalRow(sql, params);

		return count;
	}

	public Page getPage(PageRequest<?> pageRequest, String entity) {
		this.logger.debug("enter getPage");
		if (pageRequest == null) {
			throw new IllegalArgumentException("pageRequest 不允许为空");
		}

		if (entity == null) {
			throw new IllegalArgumentException("entity 不允许为空");
		}

		String sql = " from " + entity + " primary where 1=1 ";

		Object[] params = null;

		if ((pageRequest.getFilters() instanceof String)) {
			String filter = (String) pageRequest.getFilters();
			sql = sql + filter;
		} else if ((pageRequest.getFilters() instanceof QueryConditions)) {
			QueryConditions conditions = (QueryConditions) pageRequest.getFilters();

			if ((conditions != null) && (conditions.getFieldsSqlClause() != null)) {
				params = conditions.getValueArray();
				sql = sql + " and " + conditions.getFieldsSqlClause();
			}
		}

		return queryBySQL(sql, params, pageRequest, null);
	}

	private Page queryBySQL(String sql, Object[] params, PageRequest<?> pageRequest, QueryCallback queryCallback) {
		this.logger.debug("enter queryByHQL!");
		int totalRecords = totalRow(sql, params);

		int startCount = (pageRequest.getPageNumber() - 1) * pageRequest.getPageSize();

		sql = sql + pageRequest.getSortColumns();

		int pageSize = pageRequest.getPageSize();

		List list = findAllBySQL(startCount, pageSize, sql, params, queryCallback);

		Page page = new Page(pageRequest.getPageNumber(), pageRequest.getPageSize(), totalRecords, list);
		return page;
	}

	private List findAllBySQL(final int startCount, final int pageSize, final String sql, final Object[] params, final QueryCallback queryCallback) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				TradeParamsDao.this.logger.debug("findAllBySQL:" + sql);
				Query query = session.createSQLQuery("select * " + sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				if ((params != null) && (params.length > 0)) {
					for (int i = 0; i < params.length; i++) {
						TradeParamsDao.this.logger.debug("   params[i]:" + params[i]);
						query.setParameter(i, params[i]);
					}
				}
				query.setFirstResult(startCount);
				query.setMaxResults(pageSize);
				if (queryCallback != null) {
					queryCallback.doInQuery(query);
				}
				return query.list();
			}
		});
	}

	private int totalRow(String sql, final Object[] params) {
		if (!sql.trim().startsWith("from")) {
			throw new IllegalArgumentException("sql必须以from开头");
		}

		final String sqlCount = "select count(*) " + sql;

		return ((Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				TradeParamsDao.this.logger.debug("count sql:" + sqlCount);
				Query query = session.createSQLQuery(sqlCount);
				if ((params != null) && (params.length > 0)) {
					for (int i = 0; i < params.length; i++) {
						TradeParamsDao.this.logger.debug("   params[i]:" + params[i]);
						query.setParameter(i, params[i]);
					}
				}
				TradeParamsDao.this.logger.debug("--------------------" + query.uniqueResult());
				int count = ((Number) query.uniqueResult()).intValue();
				TradeParamsDao.this.logger.debug("count:" + count);
				return Integer.valueOf(count);
			}
		})).intValue();
	}
}