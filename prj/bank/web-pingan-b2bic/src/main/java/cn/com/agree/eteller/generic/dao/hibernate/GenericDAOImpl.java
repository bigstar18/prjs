package cn.com.agree.eteller.generic.dao.hibernate;

import java.io.Serializable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.SessionFactoryImpl;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.com.agree.eteller.generic.dao.GenericDAO;
import cn.com.agree.eteller.generic.utils.Pagination;
import cn.com.agree.eteller.generic.utils.ReflectUtil;

public class GenericDAOImpl<T> extends HibernateDaoSupport implements GenericDAO<T> {
	private static final long serialVersionUID = -4819203101330299698L;
	private static Logger log = Logger.getLogger(GenericDAOImpl.class);
	private int batchSize;
	protected Class<T> clazz;

	public GenericDAOImpl() {
		this.clazz = ReflectUtil.getClassGenericType(getClass());
	}

	@Resource
	public void setHibernateTemplateOverride(HibernateTemplate hibernateTemplate) {
		super.setHibernateTemplate(hibernateTemplate);
		try {
			SessionFactoryImpl factoryImpl = (SessionFactoryImpl) getSessionFactory();

			this.batchSize = factoryImpl.getSettings().getJdbcBatchSize();
		} catch (Exception e) {
			log.warn("加载hibernate配置失败：" + e);
			this.batchSize = 30;
		}
	}

	public void save(T entity) throws Exception {
		getHibernateTemplate().save(entity);
	}

	public List<T> findAll(Pagination page) throws Exception {
		long total = count().longValue();
		page.setAllRecords(Integer.valueOf((int) total));
		return getSession().createCriteria(this.clazz).setFirstResult(page.getFirstRecord().intValue())
				.setMaxResults(page.getPerPageRecords().intValue()).list();
	}

	public T findById(Serializable id) throws Exception {
		return (T) getSession().get(this.clazz, id);
	}

	public void update(T entity) throws Exception {
		getHibernateTemplate().update(entity);
	}

	public void delete(Serializable id) throws Exception {
		T entity = getHibernateTemplate().load(this.clazz, id);
		getSession().delete(entity);
	}

	public List<T> findAll() throws Exception {
		return getSession().createCriteria(this.clazz).list();
	}

	public List<T> findByHql(String hql, Object... args) throws Exception {
		return getHibernateTemplate().find(hql, args);
	}

	public List<Object[]> findBySql(String sql, Object... args) throws Exception {
		Query query = getSession().createSQLQuery(sql);

		Pattern pattern = Pattern.compile("\\?");
		Matcher matcher = pattern.matcher(sql);
		int count = 0;
		int index = 0;
		while (matcher.find(index)) {
			index = matcher.start() + 1;
			query.setParameter(count, args[(count++)]);
		}
		return query.list();
	}

	public int executeSql(String sql, Object... args) throws Exception {
		Query query = getSession().createSQLQuery(sql);

		Pattern pattern = Pattern.compile("\\?");
		Matcher matcher = pattern.matcher(sql);
		int count = 0;
		int index = 0;
		while (matcher.find(index)) {
			index = matcher.start() + 1;
			query.setParameter(count, args[(count++)]);
		}
		return query.executeUpdate();
	}

	public List<T> findByProperties(String[] names, Object... values) throws Exception {
		return findByProperties(null, names, values);
	}

	public void deleteList(List<T> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			T entity = list.get(i);
			getSession().delete(entity);
			if ((i + 1) % this.batchSize == 0) {
				getSession().flush();
				getSession().clear();
			}
		}
	}

	public void deleteListById(List<?> idList) throws Exception {
		for (Object id : idList) {
			delete((Serializable) id);
		}
	}

	public void saveOrUpdate(T entity) throws Exception {
		getSession().saveOrUpdate(entity);
	}

	public List<T> findByProperties(Pagination page, String[] names, Object... values) throws Exception {
		Criteria c = getSession().createCriteria(this.clazz);
		for (int i = 0; i < names.length; i++) {
			c.add(Restrictions.eq(names[i], values[i]));
		}
		if (page != null) {
			page.setAllRecords((Integer) c.setProjection(Projections.count(names[0])).uniqueResult());
			return c.setProjection(null).setFirstResult(page.getFirstRecord().intValue()).setMaxResults(page.getPerPageRecords().intValue()).list();
		}
		return c.list();
	}

	public List<T> findByProperties(Object[] params) throws Exception {
		return findByProperties(params, null);
	}

	public List<T> findByProperties(Object[] params, Pagination page) throws Exception {
		Criteria c = getSession().createCriteria(this.clazz);
		for (int i = 0; i < params.length; i += 2) {
			c.add(Restrictions.eq((String) params[i], params[(i + 1)]));
		}
		if (page != null) {
			page.setAllRecords((Integer) c.setProjection(Projections.count((String) params[0])).uniqueResult());
			return c.setProjection(null).setFirstResult(page.getFirstRecord().intValue()).setMaxResults(page.getPerPageRecords().intValue()).list();
		}
		return c.list();
	}

	public Integer count(Object[] params) throws Exception {
		Criteria c = getSession().createCriteria(this.clazz);
		for (int i = 0; i < params.length; i += 2) {
			c.add(Restrictions.eq((String) params[i], params[(i + 1)]));
		}
		return (Integer) c.setProjection(Projections.count((String) params[0])).uniqueResult();
	}

	public Long count() throws Exception {
		return (Long) getSession().createQuery("SELECT COUNT(*) FROM " + this.clazz.getSimpleName()).uniqueResult();
	}

	public void add(List<T> entityList) throws Exception {
		for (int i = 0; i < entityList.size(); i++) {
			T entity = entityList.get(i);
			save(entity);
			if ((i + 1) % this.batchSize == 0) {
				getSession().flush();
				getSession().clear();
			}
		}
	}

	public void modify(List<T> entityList) throws Exception {
		for (int i = 0; i < entityList.size(); i++) {
			T entity = entityList.get(i);
			update(entity);
			if ((i + 1) % this.batchSize == 0) {
				getSession().flush();
				getSession().clear();
			}
		}
	}
}
