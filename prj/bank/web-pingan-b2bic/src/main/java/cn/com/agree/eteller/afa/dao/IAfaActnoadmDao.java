package cn.com.agree.eteller.afa.dao;

import cn.com.agree.eteller.afa.persistence.AfaActnoadm;
import cn.com.agree.eteller.generic.utils.Pagination;
import java.util.List;
import java.util.Map;

public abstract interface IAfaActnoadmDao
{
  public abstract boolean addAfaActnoadm(AfaActnoadm paramAfaActnoadm);
  
  public abstract boolean deleteAfaActnoadm(AfaActnoadm paramAfaActnoadm);
  
  public abstract boolean updateAfaActnoadm(AfaActnoadm paramAfaActnoadm);
  
  public abstract AfaActnoadm[] loadAfaActnoadm(Map paramMap);
  
  public abstract AfaActnoadm getAfaActnoadmBysql(String paramString);
  
  public abstract List<AfaActnoadm> getAfaActnoadmByMap(Map<String, String> paramMap, Pagination paramPagination);
}
