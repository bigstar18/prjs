package cn.com.agree.eteller.afa.dao;

import cn.com.agree.eteller.afa.persistence.AfaChanneladm;
import cn.com.agree.eteller.generic.utils.Pagination;
import java.util.List;
import java.util.Map;

public abstract interface IAfaChanneladmDao
{
  public abstract boolean addAfaChanneladm(AfaChanneladm paramAfaChanneladm);
  
  public abstract boolean deleteAfaChanneladm(AfaChanneladm paramAfaChanneladm);
  
  public abstract boolean updateAfaChanneladm(AfaChanneladm paramAfaChanneladm);
  
  public abstract AfaChanneladm[] getAfaChanneladmBymap(Map paramMap);
  
  public abstract AfaChanneladm[] getAfaChanneladmBymap2(Map paramMap);
  
  public abstract AfaChanneladm getAfaChanneladmBysql(String paramString);
  
  public abstract List<AfaChanneladm> getAfaChanneladmByMap(Map<String, String> paramMap, Pagination paramPagination);
}
