package cn.com.agree.eteller.generic.spring;

import cn.com.agree.eteller.generic.utils.Pagination;
import java.io.Serializable;
import java.util.List;

public abstract interface BaseManager<T>
  extends Serializable
{
  public abstract T getById(Serializable paramSerializable)
    throws Exception;
  
  public abstract List<T> getAll()
    throws Exception;
  
  public abstract List<T> getAll(Pagination paramPagination)
    throws Exception;
  
  public abstract void add(T paramT)
    throws Exception;
  
  public abstract void addOrModify(T paramT)
    throws Exception;
  
  public abstract void modify(T paramT)
    throws Exception;
  
  public abstract void remove(Serializable paramSerializable)
    throws Exception;
  
  public abstract void removeList(List<T> paramList)
    throws Exception;
  
  public abstract void removeListById(List<?> paramList)
    throws Exception;
  
  public abstract List<T> getByHql(String paramString, Object... paramVarArgs)
    throws Exception;
  
  public abstract List<Object[]> getBySql(String paramString, Object... paramVarArgs)
    throws Exception;
  
  public abstract int executeSql(String paramString, Object... paramVarArgs)
    throws Exception;
  
  public abstract List<T> getByProperties(String[] paramArrayOfString, Object... paramVarArgs)
    throws Exception;
  
  public abstract List<T> getByProperties(Pagination paramPagination, String[] paramArrayOfString, Object... paramVarArgs)
    throws Exception;
  
  public abstract List<T> getByProperties(Object[] paramArrayOfObject, Pagination paramPagination)
    throws Exception;
  
  public abstract List<T> getByProperties(Object[] paramArrayOfObject)
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
