package cn.com.agree.eteller.afa.dao;

import cn.com.agree.eteller.afa.persistence.AfaZoneinfo;
import java.util.Map;

public abstract interface IAfaZoneinfoDao
{
  public abstract boolean addAfaZoneinfo(AfaZoneinfo paramAfaZoneinfo);
  
  public abstract boolean deleteAfaZoneinfo(AfaZoneinfo paramAfaZoneinfo);
  
  public abstract boolean updateAfaZoneinfo(AfaZoneinfo paramAfaZoneinfo);
  
  public abstract AfaZoneinfo[] getAfaZoneinfoBymap(Map paramMap);
  
  public abstract AfaZoneinfo[] getAllAfaZoneinfo();
}
