package gnnt.MEBS.common.mgr.dao;

import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.common.QueryConditions;
import gnnt.MEBS.common.mgr.model.StandardModel;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * 抽象DAO只有查询相关方法
 * 
 * @author xuejt
 * 
 * 
 */
public abstract class AbstractDao extends HibernateDaoSupport {
	/**
	 * 通过sql 批量查询<br>
	 * 使用此方法 业务对象实例不能为空 且entity.fetchPKey().getKey()有返回值
	 * 
	 * @param entity
	 *            业务对象实例
	 * @param ids
	 *            主键值数组
	 */
	@SuppressWarnings("unchecked")
	public List<StandardModel> getListByBulk(StandardModel entity,
			final Object[] ids) {
		logger.debug("enter getListByBulk");
		if (entity == null) {
			throw new IllegalArgumentException("业务对象为空，所以操作表未知，不允许通过主键数组批量查询！");
		}

		if (entity.fetchPKey() == null || entity.fetchPKey().getKey() == null
				|| entity.fetchPKey().getKey().length() == 0) {
			throw new IllegalArgumentException("业务对象未设置主键，不允许通过主键数组批量查询！");
		}

		final StandardModel model = entity.clone();
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {

				return session.createCriteria(model.getClass()).add(
						Restrictions.in(model.fetchPKey().getKey(), ids))
						.list();
			}
		});

	}

	/**
	 * 获取业务对象实例
	 * 
	 * @param entity
	 *            业务对象实例；
	 *            <UL>
	 *            <li>如果业务对象实例fetchPKey方法有返回值则通过主键查询，否则通过hibernare的配置文件中主键查询；</LI>
	 *            <li>如果既没有主键又没有配置则抛出异常</li>
	 *            <li>hibernare配置</li>
	 *            <li><composite-id></li>
	 *            <li><key-property name="composite1"></key-property></li>
	 *            <li><key-property name="composite2"></key-property></li>
	 *            <li></composite-id></li>
	 *            </UL>
	 * @return 查询结果
	 */
	public StandardModel get(StandardModel entity) {
		logger.debug("enter get");
		if (entity.fetchPKey() != null) {
			return (StandardModel) getHibernateTemplate().get(
					entity.getClass(), entity.fetchPKey().getValue());
		}

		return (StandardModel) getHibernateTemplate().get(entity.getClass(),
				entity);
	}

	/**
	 * 获取分页信息
	 * 
	 * @param pageRequest
	 *            分页请求信息
	 * @param entity
	 *            业务对象实例
	 * @return 分页信息
	 */
	public Page<StandardModel> getPage(PageRequest<?> pageRequest,
			StandardModel entity) {
		logger.debug("enter getPage");
		if (pageRequest == null) {
			throw new IllegalArgumentException("pageRequest 不允许为空");
		}

		if (entity == null) {
			throw new IllegalArgumentException("entity 不允许为空");
		}

		// 初始化hql语句
		String hql = " from " + entity.getClass().getName()
				+ " as primary where 1=1 ";

		// 替换占位符的变量数组
		Object[] params = null;

		// 判断过滤器类型拼装hql字符串

		// 如果过滤器为字符串类型则直接连接字符串
		if (pageRequest.getFilters() instanceof String) {
			String filter = (String) pageRequest.getFilters();
			hql += filter;
		} else if (pageRequest.getFilters() instanceof QueryConditions) {
			QueryConditions conditions = (QueryConditions) pageRequest
					.getFilters();

			if (conditions != null && conditions.getFieldsSqlClause() != null) {
				params = conditions.getValueArray();
				hql = hql + " and " + conditions.getFieldsSqlClause();
			}
		}

		return queryByHQL(hql, params, pageRequest, null);
	}

	/**
	 * 通过标准sql语句查询
	 * 
	 * @param sql
	 *            sql语句
	 * @param entity
	 *            业务对象实例
	 * @return 查询结果记录集
	 */
	@SuppressWarnings("unchecked")
	public List<StandardModel> queryBySql(final String sql,
			final StandardModel entity) {
		List list = (List) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(final Session session)
							throws HibernateException, SQLException {
						Query query = session.createSQLQuery(sql).addEntity(
								entity.getClass());
						return query.list();
					}
				});
		return list;
	}

	/**
	 * 通过标准sql语句查询
	 * 
	 * @param sql
	 *            sql语句
	 * @return 查询结果记录集 List元素为Map
	 */
	@SuppressWarnings("unchecked")
	public List<Map<Object, Object>> queryBySql(final String sql) {
		List list = (List) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(final Session session)
							throws HibernateException, SQLException {
						Query query = session.createSQLQuery(sql)
								.setResultTransformer(
										Transformers.ALIAS_TO_ENTITY_MAP);
						return query.list();
					}
				});
		return list;
	}

	/**
	 * 清理hibernate的缓存 将对象持久化到数据库
	 */
	public void flush() {
		this.getHibernateTemplate().flush();
	}

	/**
	 * 将 entity对象从缓存中清除出去，从而及时释放它占用的内存
	 * 
	 * @param entity
	 *            业务对象实例
	 */
	public void evict(StandardModel entity) {
		this.getHibernateTemplate().evict(entity);
	}

	/**
	 * 通过hql查询分页信息
	 * 
	 * @param hql
	 *            hql语句
	 * @param params
	 *            替换占位符值数组
	 * @param pageRequest
	 *            分页请求信息
	 * @param queryCallback
	 *            查询回调接口
	 * @return 分页信息
	 */
	private Page<StandardModel> queryByHQL(String hql, Object[] params,
			PageRequest<?> pageRequest, QueryCallback queryCallback) {
		// 得到符合条件的记录总数
		logger.debug("enter queryByHQL!");
		int totalRecords = totalRow(hql, params);
		// 开始条目数
		int startCount = (pageRequest.getPageNumber() - 1)
				* pageRequest.getPageSize();

		// 将排序条件加到结尾
		hql += pageRequest.getSortColumns();
		// 分页大小
		int pageSize = pageRequest.getPageSize();

		List<StandardModel> list = findAllByHQL(startCount, pageSize, hql,
				params, queryCallback);

		Page<StandardModel> page = new Page<StandardModel>(pageRequest
				.getPageNumber(), pageRequest.getPageSize(), totalRecords, list);
		return page;
	}

	/**
	 * 通过hql查询记录集
	 * 
	 * @param startCount
	 *            查询开始条目数
	 * @param pageSize
	 *            分页大小
	 * @param hql
	 *            hql语句
	 * @param params
	 *            替换占位符值数组
	 * @param queryCallback
	 *            查询回调接口
	 * @return 记录集
	 */
	@SuppressWarnings("unchecked")
	private List<StandardModel> findAllByHQL(final int startCount,
			final int pageSize, final String hql, final Object params[],
			final QueryCallback queryCallback) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				logger.debug("findAllByHQL:" + hql);
				Query query = session.createQuery(hql);
				if (params != null && params.length > 0) {
					for (int i = 0; i < params.length; i++) {
						logger.debug("   params[i]:" + params[i]);
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

	/**
	 * 查询总记录条数
	 * 
	 * @param hql
	 *            hql语句 必须以from开始
	 * @param params
	 *            替换占位符值数组
	 * @return 总行数
	 */
	private int totalRow(final String hql, final Object params[]) {
		if (!hql.trim().startsWith("from")) {
			throw new IllegalArgumentException("hql必须以from开头");
		}
		// 因为hql直接从from开始所以直接连接字符串
		final String hqlCount = "select count(*) " + hql + "";

		return (Integer) getHibernateTemplate().execute(
				new HibernateCallback<Object>() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						logger.debug("count hql:" + hqlCount);
						Query query = session.createQuery(hqlCount);
						if (params != null && params.length > 0) {
							for (int i = 0; i < params.length; i++) {
								logger.debug("   params[i]:" + params[i]);
								query.setParameter(i, params[i]);
							}
						}
						logger.debug("--------------------"
								+ query.uniqueResult());
						int count = ((Number) query.uniqueResult()).intValue();
						logger.debug("count:" + count);
						return count;
					}
				});
	}
}
