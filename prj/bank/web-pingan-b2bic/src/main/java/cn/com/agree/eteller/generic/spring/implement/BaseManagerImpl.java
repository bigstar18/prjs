package cn.com.agree.eteller.generic.spring.implement;

import cn.com.agree.eteller.generic.dao.GenericDAO;
import cn.com.agree.eteller.generic.spring.BaseManager;
import cn.com.agree.eteller.generic.utils.Pagination;
import java.io.Serializable;
import java.util.List;

public class BaseManagerImpl<T>
  implements BaseManager<T>
{
  private static final long serialVersionUID = 964503562355561116L;
  protected GenericDAO<T> baseDAO;
  
  public BaseManagerImpl() {}
  
  public BaseManagerImpl(GenericDAO<T> baseDAO)
  {
    this.baseDAO = baseDAO;
  }
  
  public T getById(Serializable id)
    throws Exception
  {
    return this.baseDAO.findById(id);
  }
  
  public List<T> getAll()
    throws Exception
  {
    return this.baseDAO.findAll();
  }
  
  public List<T> getAll(Pagination page)
    throws Exception
  {
    return this.baseDAO.findAll(page);
  }
  
  public void add(T entity)
    throws Exception
  {
    this.baseDAO.save(entity);
  }
  
  public void modify(T entity)
    throws Exception
  {
    this.baseDAO.update(entity);
  }
  
  public void remove(Serializable id)
    throws Exception
  {
    this.baseDAO.delete(id);
  }
  
  public List<T> getByHql(String hql, Object... args)
    throws Exception
  {
    return this.baseDAO.findByHql(hql, args);
  }
  
  public List<Object[]> getBySql(String sql, Object... args)
    throws Exception
  {
    return this.baseDAO.findBySql(sql, args);
  }
  
  public int executeSql(String sql, Object... args)
    throws Exception
  {
    return this.baseDAO.executeSql(sql, args);
  }
  
  public List<T> getByProperties(String[] names, Object... values)
    throws Exception
  {
    return this.baseDAO.findByProperties(names, values);
  }
  
  public void removeList(List<T> list)
    throws Exception
  {
    this.baseDAO.deleteList(list);
  }
  
  public void removeListById(List<?> idList)
    throws Exception
  {
    this.baseDAO.deleteListById(idList);
  }
  
  public void addOrModify(T entity)
    throws Exception
  {
    this.baseDAO.saveOrUpdate(entity);
  }
  
  public List<T> getByProperties(Pagination page, String[] names, Object... values)
    throws Exception
  {
    return this.baseDAO.findByProperties(page, names, values);
  }
  
  public List<T> getByProperties(Object[] params, Pagination page)
    throws Exception
  {
    return this.baseDAO.findByProperties(params, page);
  }
  
  public List<T> getByProperties(Object[] params)
    throws Exception
  {
    return this.baseDAO.findByProperties(params);
  }
  
  public Integer count(Object[] params)
    throws Exception
  {
    return this.baseDAO.count(params);
  }
  
  public Long count()
    throws Exception
  {
    return this.baseDAO.count();
  }
  
  public void add(List<T> entityList)
    throws Exception
  {
    this.baseDAO.add(entityList);
  }
  
  public void modify(List<T> entityList)
    throws Exception
  {
    this.baseDAO.modify(entityList);
  }
}
