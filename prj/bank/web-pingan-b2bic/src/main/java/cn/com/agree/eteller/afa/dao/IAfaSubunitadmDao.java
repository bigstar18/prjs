package cn.com.agree.eteller.afa.dao;

import cn.com.agree.eteller.afa.persistence.AfaSubunitadm;
import cn.com.agree.eteller.generic.utils.Pagination;
import java.util.List;
import java.util.Map;

public abstract interface IAfaSubunitadmDao
{
  public abstract boolean addSubunitadm(AfaSubunitadm paramAfaSubunitadm);
  
  public abstract boolean deleteSubunitadm(AfaSubunitadm paramAfaSubunitadm);
  
  public abstract boolean updateSubunitadm(AfaSubunitadm paramAfaSubunitadm);
  
  public abstract AfaSubunitadm[] getSubunitadmBymap(Map paramMap);
  
  public abstract AfaSubunitadm[] getAllsubunitadm();
  
  public abstract AfaSubunitadm[] getSubunitadmByunitno(String paramString1, String paramString2);
  
  public abstract AfaSubunitadm getAfaSubunitadmBysql(String paramString);
  
  public abstract List<AfaSubunitadm> getAfaSubunitadmByMap(Map<String, String> paramMap, Pagination paramPagination);
}
