package cn.com.agree.eteller.generic.dao.hibernate;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.com.agree.eteller.generic.dao.IGenericDao;
import cn.com.agree.eteller.generic.utils.Pagination;

public class GenericDao extends HibernateDaoSupport implements IGenericDao {
	public boolean addEntity(Object entity) {
		boolean isAdded = false;
		try {
			getHibernateTemplate().save(entity);
			isAdded = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("保存实体" + entity.getClass().getName() + "时不成功");
			isAdded = false;
		}
		return isAdded;
	}

	public boolean deleteEntity(Object entity) {
		boolean isDeleted = false;
		try {
			getHibernateTemplate().delete(entity);
			isDeleted = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("删除实体" + entity.getClass().getName() + "时不成功");
			isDeleted = false;
		}
		return isDeleted;
	}

	public boolean deleteAllEntity(List entitys) {
		boolean isDeleted = false;
		try {
			getHibernateTemplate().deleteAll(entitys);
			isDeleted = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("批量删除实体时不成功");
			isDeleted = false;
		}
		return isDeleted;
	}

	public boolean updateEntity(Object entity) {
		boolean isUpdated = false;
		try {
			getHibernateTemplate().update(entity);
			isUpdated = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("更新实体" + entity.getClass().getName() + "时不成功");
			isUpdated = false;
		}
		return isUpdated;
	}

	public boolean updateEntityByZHPK(Object newEntity, Serializable oldEntityPK) {
		boolean isChanged = false;
		Object tempEntity = null;
		try {
			tempEntity = getEntityByZHPK(newEntity.getClass(), oldEntityPK);
			getHibernateTemplate().delete(tempEntity);
			getHibernateTemplate().save(newEntity);
			isChanged = true;
		} catch (DataAccessException e) {
			e.printStackTrace();
			isChanged = false;
			System.out.println("替换更新实体" + newEntity + "时不成功");
		}
		return isChanged;
	}

	public boolean updateEntityByPK(Object newEntity, String id) {
		boolean isChanged = false;
		Object tempEntity = null;
		try {
			tempEntity = getEntityByPK(newEntity.getClass(), id);
			getHibernateTemplate().delete(tempEntity);
			getHibernateTemplate().save(newEntity);
			isChanged = true;
		} catch (DataAccessException e) {
			e.printStackTrace();
			isChanged = false;
			System.out.println("替换更新实体" + newEntity + "时不成功");
		}
		return isChanged;
	}

	public boolean updateEntity(Object newentity, Object oldentity) {
		boolean isUpdated = false;
		if (deleteEntity(oldentity)) {
			isUpdated = addEntity(newentity);
		}
		return isUpdated;
	}

	public Object getEntityByZHPK(Class clazz, Serializable entityPK) {
		Object entity = null;
		try {
			entity = getHibernateTemplate().get(clazz, entityPK);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return entity;
	}

	public Object getEntityByPK(Class clazz, String id) {
		Object entity = null;
		try {
			entity = getHibernateTemplate().get(clazz, id);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return entity;
	}

	public Object getEntityByid(Class clazz, String id) {
		Object entity = null;
		try {
			entity = getHibernateTemplate().get(clazz, id);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return entity;
	}

	public <T> List<T> getAllEntity(Class<T> clazz) {
		List ls = new ArrayList();
		try {
			ls = getHibernateTemplate().loadAll(clazz);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ls;
	}

	public String[][] getSearchResultBySQL(String selectSql) {
		String[][] result = (String[][]) null;
		SessionFactory sf = null;
		Session session = null;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		label1127: try {
			sf = getHibernateTemplate().getSessionFactory();
			session = sf.openSession();
			con = session.connection();
			st = con.createStatement();
			rs = st.executeQuery(selectSql);

			int rowSize = 0;
			ResultSetMetaData rsmd = rs.getMetaData();
			int colSize = rsmd.getColumnCount();

			List list = new ArrayList();
			while (rs.next()) {
				for (int i = 0; i < colSize; i++) {
					list.add(rs.getString(i + 1));
				}
				rowSize++;
			}
			result = new String[rowSize][colSize];

			int count = 0;
			for (int i = 0; i < rowSize; i++) {
				for (int z = 0; z < colSize; z++) {
					result[i][z] = ((String) list.get(count++));
					System.out.println(result[i][z]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("getSearchResultBySQL():" + selectSql + "数据查询失败！");
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				System.out.println("关闭ResultSet失败！");
				try {
					if (st != null) {
						st.close();
					}
				} catch (Exception e11) {
					e11.printStackTrace();
					System.out.println("关闭Statement失败！");
					try {
						if (session == null) {
							return result;
						}
						session.close();
					} catch (Exception e111) {
						e111.printStackTrace();
						System.out.println("关闭Session失败！");
					}
				} finally {
					try {
						if (session != null) {
							session.close();
						}
					} catch (Exception e11) {
						e11.printStackTrace();
						System.out.println("关闭Session失败！");
					}
				}
				try {
					if (session == null) {
						return result;
					}
					session.close();
				} catch (Exception e11) {
					e11.printStackTrace();
					System.out.println("关闭Session失败！");
				}
			} finally {
				try {
					if (st != null) {
						st.close();
					}
				} catch (Exception e1) {
					e1.printStackTrace();
					System.out.println("关闭Statement失败！");
					try {
						if (session != null) {
							session.close();
						}
					} catch (Exception e11) {
						e11.printStackTrace();
						System.out.println("关闭Session失败！");
					}
				} finally {
					try {
						if (session != null) {
							session.close();
						}
					} catch (Exception e1) {
						e1.printStackTrace();
						System.out.println("关闭Session失败！");
					}
				}
			}
			try {
				if (session != null) {
					session.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				System.out.println("关闭Session失败！");
			}
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("关闭ResultSet失败！");
				try {
					if (st != null) {
						st.close();
					}
				} catch (Exception e1) {
					e1.printStackTrace();
					System.out.println("关闭Statement失败！");
					try {
						if (session == null) {
							break label1127;
						}
						session.close();
					} catch (Exception e11) {
						e11.printStackTrace();
						System.out.println("关闭Session失败！");
					}
				} finally {
					try {
						if (session != null) {
							session.close();
						}
					} catch (Exception e1) {
						e1.printStackTrace();
						System.out.println("关闭Session失败！");
					}
				}
				try {
					if (session != null) {
						session.close();
					}
				} catch (Exception e1) {
					e1.printStackTrace();
					System.out.println("关闭Session失败！");
				}
			} finally {
				try {
					if (st != null) {
						st.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("关闭Statement失败！");
					try {
						if (session != null) {
							session.close();
						}
					} catch (Exception e1) {
						e1.printStackTrace();
						System.out.println("关闭Session失败！");
					}
				} finally {
					try {
						if (session != null) {
							session.close();
						}
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("关闭Session失败！");
					}
				}
			}
		}
		return result;
	}

	public List getResultListByHQL(String selectHql, String... args) {
		return getHibernateTemplate().find(selectHql, args);
	}

	public Integer getTotalRownum(Class clazz) throws Exception {
		long total = ((Long) getSession().createQuery("SELECT COUNT(*) FROM " + clazz.getSimpleName()).uniqueResult()).longValue();
		return Integer.valueOf((int) total);
	}

	public <T> List<T> getAllEntity(Class<T> clazz, Pagination page) throws Exception {
		page.setAllRecords(getTotalRownum(clazz));
		return getSession().createQuery("FROM " + clazz.getSimpleName()).setFirstResult(page.getFirstRecord().intValue())
				.setMaxResults(page.getPerPageRecords().intValue()).list();
	}

	public List getCollection(Collection col, Class clazz, Pagination page) throws Exception {
		page.setAllRecords(getTotalRownum(clazz));
		return getSession().createFilter(col, "").setFirstResult(page.getFirstRecord().intValue()).setMaxResults(page.getPerPageRecords().intValue())
				.list();
	}

	public <T> T getEntity(Class<T> clazz, Serializable id) {
		return (T) getSession().get(clazz, id);
	}
}
