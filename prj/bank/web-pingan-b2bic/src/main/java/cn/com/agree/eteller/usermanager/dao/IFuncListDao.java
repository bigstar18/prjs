package cn.com.agree.eteller.usermanager.dao;

import cn.com.agree.eteller.generic.utils.Pagination;
import cn.com.agree.eteller.usermanager.persistence.Funclist;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract interface IFuncListDao
{
  public abstract Set getRoleFunctions(Serializable paramSerializable);
  
  public abstract boolean addFunction(Funclist paramFunclist);
  
  public abstract boolean deleteFunction(long paramLong);
  
  public abstract Funclist[] getFunctionByAppId(String paramString, Pagination paramPagination);
  
  public abstract boolean updateFunction(Funclist paramFunclist);
  
  public abstract Funclist[] getFunctionByRoleId(Serializable paramSerializable);
  
  public abstract Funclist[] getAllFunction();
  
  public abstract Funclist[] getFunctionList(Funclist paramFunclist);
  
  public abstract Funclist[] getFunctionBySubAppId(String paramString1, String paramString2);
  
  public abstract Funclist[] getFunclistByMap(Map paramMap, Pagination paramPagination)
    throws Exception;
  
  public abstract List<Funclist> getFunclistByMap2(Map<String, String> paramMap, Pagination paramPagination);
}
