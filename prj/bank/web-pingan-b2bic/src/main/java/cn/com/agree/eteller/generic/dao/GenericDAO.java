package cn.com.agree.eteller.generic.dao;

import cn.com.agree.eteller.generic.utils.Pagination;
import java.io.Serializable;
import java.util.List;

public abstract interface GenericDAO<T>
  extends Serializable
{
  public abstract void save(T paramT)
    throws Exception;
  
  public abstract void saveOrUpdate(T paramT)
    throws Exception;
  
  public abstract List<T> findAll()
    throws Exception;
  
  public abstract List<T> findAll(Pagination paramPagination)
    throws Exception;
  
  public abstract T findById(Serializable paramSerializable)
    throws Exception;
  
  public abstract void update(T paramT)
    throws Exception;
  
  public abstract void delete(Serializable paramSerializable)
    throws Exception;
  
  public abstract void deleteList(List<T> paramList)
    throws Exception;
  
  public abstract List<T> findByProperties(String[] paramArrayOfString, Object... paramVarArgs)
    throws Exception;
  
  public abstract List<T> findByProperties(Pagination paramPagination, String[] paramArrayOfString, Object... paramVarArgs)
    throws Exception;
  
  public abstract List<T> findByProperties(Object[] paramArrayOfObject)
    throws Exception;
  
  public abstract List<T> findByProperties(Object[] paramArrayOfObject, Pagination paramPagination)
    throws Exception;
  
  public abstract List<T> findByHql(String paramString, Object... paramVarArgs)
    throws Exception;
  
  public abstract List<Object[]> findBySql(String paramString, Object... paramVarArgs)
    throws Exception;
  
  public abstract int executeSql(String paramString, Object... paramVarArgs)
    throws Exception;
  
  public abstract void deleteListById(List<?> paramList)
    throws Exception;
  
  public abstract Integer count(Object[] paramArrayOfObject)
    throws Exception;
  
  public abstract Long count()
    throws Exception;
  
  public abstract void add(List<T> paramList)
    throws Exception;
  
  public abstract void modify(List<T> paramList)
    throws Exception;
}
