package cn.com.agree.eteller.afa.dao;

import cn.com.agree.eteller.afa.persistence.AfaAgentadm;
import cn.com.agree.eteller.generic.utils.Pagination;
import java.util.List;
import java.util.Map;

public abstract interface IAfaAgentadmDao
{
  public abstract boolean addAfaAgentadm(AfaAgentadm paramAfaAgentadm);
  
  public abstract boolean deleteAfaAgentadm(AfaAgentadm paramAfaAgentadm);
  
  public abstract boolean updateAfaAgentadm(AfaAgentadm paramAfaAgentadm);
  
  public abstract AfaAgentadm[] getAfaAgentadmBymap(Map paramMap);
  
  public abstract AfaAgentadm[] getAllAfaAgentadm();
  
  public abstract AfaAgentadm getAfaAgentadmBysql(String paramString);
  
  public abstract List<AfaAgentadm> getAfaAgentadmByMap(Map<String, String> paramMap, Pagination paramPagination);
}
