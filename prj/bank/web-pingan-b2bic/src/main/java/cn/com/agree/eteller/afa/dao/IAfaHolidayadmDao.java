package cn.com.agree.eteller.afa.dao;

import cn.com.agree.eteller.afa.persistence.AfaHolidayadm;
import java.util.Map;

public abstract interface IAfaHolidayadmDao
{
  public abstract boolean addAfaHolidayadm(AfaHolidayadm paramAfaHolidayadm);
  
  public abstract boolean deleteAfaHolidayadm(AfaHolidayadm paramAfaHolidayadm);
  
  public abstract boolean updateAfaHolidayadm(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6);
  
  public abstract AfaHolidayadm[] getAfaHolidayadmBymap(Map paramMap);
  
  public abstract AfaHolidayadm[] getAllAfaHolidayadm();
  
  public abstract AfaHolidayadm getAfaHolidayadmBysql(String paramString);
  
  public abstract boolean deleteAfaHolidayadm(Map paramMap);
}
