package cn.com.agree.eteller.generic.dao;

import cn.com.agree.eteller.generic.utils.Pagination;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public abstract interface IGenericDao
{
  public abstract boolean addEntity(Object paramObject);
  
  public abstract boolean deleteEntity(Object paramObject);
  
  public abstract boolean deleteAllEntity(List paramList);
  
  public abstract boolean updateEntity(Object paramObject);
  
  public abstract boolean updateEntity(Object paramObject1, Object paramObject2);
  
  public abstract boolean updateEntityByZHPK(Object paramObject, Serializable paramSerializable);
  
  public abstract boolean updateEntityByPK(Object paramObject, String paramString);
  
  public abstract Object getEntityByZHPK(Class paramClass, Serializable paramSerializable);
  
  public abstract Object getEntityByPK(Class paramClass, String paramString);
  
  public abstract <T> T getEntity(Class<T> paramClass, Serializable paramSerializable);
  
  public abstract <T> List<T> getAllEntity(Class<T> paramClass);
  
  public abstract <T> List<T> getAllEntity(Class<T> paramClass, Pagination paramPagination)
    throws Exception;
  
  public abstract String[][] getSearchResultBySQL(String paramString);
  
  public abstract List getResultListByHQL(String paramString, String... paramVarArgs);
  
  public abstract List getCollection(Collection paramCollection, Class paramClass, Pagination paramPagination)
    throws Exception;
  
  public abstract Integer getTotalRownum(Class paramClass)
    throws Exception;
}
