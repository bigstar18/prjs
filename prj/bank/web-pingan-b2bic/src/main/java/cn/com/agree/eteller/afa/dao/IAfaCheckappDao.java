package cn.com.agree.eteller.afa.dao;

import cn.com.agree.eteller.afa.persistence.EtellerCheckapp;
import cn.com.agree.eteller.generic.utils.Pagination;
import java.util.List;
import java.util.Map;

public abstract interface IAfaCheckappDao
{
  public abstract String addAfaCheckapp(EtellerCheckapp paramEtellerCheckapp);
  
  public abstract boolean deleteAfaCheckapp(EtellerCheckapp paramEtellerCheckapp);
  
  public abstract boolean updateAfaCheckapp(EtellerCheckapp paramEtellerCheckapp);
  
  public abstract EtellerCheckapp[] loadAfaCheckapp(Map paramMap);
  
  public abstract List<EtellerCheckapp> getEtellerCheckappByMap(Map<String, String> paramMap, Pagination paramPagination);
  
  public abstract EtellerCheckapp getEtellerCheckappBysql(String paramString);
}
