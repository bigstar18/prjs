package cn.com.agree.eteller.afa.dao;

import cn.com.agree.eteller.afa.persistence.AfaTradeadm;
import cn.com.agree.eteller.generic.utils.Pagination;
import java.util.List;
import java.util.Map;

public abstract interface IAfaTradeadmDao
{
  public abstract boolean addAfaTradeadm(AfaTradeadm paramAfaTradeadm);
  
  public abstract boolean deleteAfaTradeadm(AfaTradeadm paramAfaTradeadm);
  
  public abstract boolean updateAfaTradeadm(AfaTradeadm paramAfaTradeadm);
  
  public abstract AfaTradeadm[] getAfaTradeadmBymap(Map paramMap);
  
  public abstract AfaTradeadm[] getAllAfaTradeadm();
  
  public abstract AfaTradeadm getAfaTradeadmBysql(String paramString);
  
  public abstract List<AfaTradeadm> getAfaTradeadmByMap(Map<String, String> paramMap, Pagination paramPagination);
}
