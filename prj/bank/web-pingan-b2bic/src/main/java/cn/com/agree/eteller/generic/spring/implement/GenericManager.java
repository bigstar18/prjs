package cn.com.agree.eteller.generic.spring.implement;

import cn.com.agree.eteller.generic.dao.IGenericDao;
import cn.com.agree.eteller.generic.spring.IGenericManager;
import cn.com.agree.eteller.generic.utils.Pagination;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.annotation.Resource;

public class GenericManager
  implements IGenericManager
{
  @Resource
  private IGenericDao genericDao;
  
  public IGenericDao getGenericDao()
  {
    return this.genericDao;
  }
  
  public void setGenericDao(IGenericDao genericDao)
  {
    this.genericDao = genericDao;
  }
  
  public boolean addEntity(Object entity)
  {
    return this.genericDao.addEntity(entity);
  }
  
  public boolean deleteAllEntity(List entitys)
  {
    return this.genericDao.deleteAllEntity(entitys);
  }
  
  public boolean deleteEntity(Object entity)
  {
    return this.genericDao.deleteEntity(entity);
  }
  
  public <T> List<T> getAllEntity(Class<T> clazz)
  {
    return this.genericDao.getAllEntity(clazz);
  }
  
  public Object getEntityByPK(Class clazz, String id)
  {
    return this.genericDao.getEntityByPK(clazz, id);
  }
  
  public Object getEntityByZHPK(Class clazz, Serializable entityPK)
  {
    return this.genericDao.getEntityByZHPK(clazz, entityPK);
  }
  
  public List getResultListByHQL(String selectHql, String... args)
  {
    return this.genericDao.getResultListByHQL(selectHql, args);
  }
  
  public String[][] getSearchResultBySQL(String selectSql)
  {
    return this.genericDao.getSearchResultBySQL(selectSql);
  }
  
  public boolean updateEntity(Object entity)
  {
    return this.genericDao.updateEntity(entity);
  }
  
  public boolean updateEntity(Object newentity, Object oldentity)
  {
    return this.genericDao.updateEntity(newentity, oldentity);
  }
  
  public boolean updateEntityByPK(Object entity, String id)
  {
    return this.genericDao.updateEntityByPK(entity, id);
  }
  
  public boolean updateEntityByZHPK(Object entity, Serializable entityPK)
  {
    return this.genericDao.updateEntityByZHPK(entity, entityPK);
  }
  
  public Integer getTotalRownum(Class clazz)
    throws Exception
  {
    return this.genericDao.getTotalRownum(clazz);
  }
  
  public <T> List<T> getAllEntity(Class<T> clazz, Pagination page)
    throws Exception
  {
    page.setAllRecords(getTotalRownum(clazz));
    return this.genericDao.getAllEntity(clazz, page);
  }
  
  public List getCollection(Collection col, Class clazz, Pagination page)
    throws Exception
  {
    return this.genericDao.getCollection(col, clazz, page);
  }
  
  public <T> T getEntity(Class<T> clazz, Serializable id)
  {
    return this.genericDao.getEntity(clazz, id);
  }
}
