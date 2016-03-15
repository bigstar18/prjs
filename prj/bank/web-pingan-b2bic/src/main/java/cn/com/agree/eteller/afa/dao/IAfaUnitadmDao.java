package cn.com.agree.eteller.afa.dao;

import cn.com.agree.eteller.afa.persistence.AfaUnitadm;
import cn.com.agree.eteller.generic.utils.Pagination;
import java.util.List;
import java.util.Map;

public abstract interface IAfaUnitadmDao
{
  public abstract boolean addAfaUnitadm(AfaUnitadm paramAfaUnitadm);
  
  public abstract boolean deleteAfaUnitadm(AfaUnitadm paramAfaUnitadm);
  
  public abstract boolean updateAfaUnitadm(AfaUnitadm paramAfaUnitadm);
  
  public abstract AfaUnitadm[] getAfaUnitadmBymap(Map paramMap);
  
  public abstract List<AfaUnitadm> getAfaUnitadmByMap(Map<String, String> paramMap, Pagination paramPagination);
  
  public abstract AfaUnitadm[] getAllAfaUnitadm();
  
  public abstract AfaUnitadm[] getAfaUnitadmBysysid(String paramString);
  
  public abstract AfaUnitadm[] getAfaUnitadmBysysid2(String paramString);
  
  public abstract AfaUnitadm[] getAfaUnitadmBysysid3(String paramString);
  
  public abstract AfaUnitadm getAfaUnitadmBysql(String paramString);
  
  public abstract AfaUnitadm getAfaUnitadmById(String paramString1, String paramString2);
}
