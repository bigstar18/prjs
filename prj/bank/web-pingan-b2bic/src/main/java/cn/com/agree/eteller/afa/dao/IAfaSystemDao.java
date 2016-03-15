package cn.com.agree.eteller.afa.dao;

import cn.com.agree.eteller.afa.persistence.AfaSystem;
import cn.com.agree.eteller.generic.utils.Pagination;
import java.util.List;
import java.util.Map;

public abstract interface IAfaSystemDao
{
  public abstract boolean addAfaSystem(AfaSystem paramAfaSystem);
  
  public abstract boolean deleteAfaSystem(AfaSystem paramAfaSystem);
  
  public abstract boolean updateAfaSystem(AfaSystem paramAfaSystem);
  
  public abstract AfaSystem[] getAfaSystemBymap(Map paramMap);
  
  public abstract List<AfaSystem> getAfaSystemByMap(Map<String, String> paramMap, Pagination paramPagination);
  
  public abstract AfaSystem[] getAllafaSystem();
  
  public abstract AfaSystem getAfaSystemBysql(String paramString);
}
