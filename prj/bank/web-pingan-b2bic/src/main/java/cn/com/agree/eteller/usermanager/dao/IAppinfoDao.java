package cn.com.agree.eteller.usermanager.dao;

import cn.com.agree.eteller.generic.utils.Pagination;
import cn.com.agree.eteller.usermanager.persistence.Appinfo;
import cn.com.agree.eteller.usermanager.persistence.EtellerSubappinfo;
import java.util.List;

public abstract interface IAppinfoDao
{
  public abstract Appinfo getAppinfo(String paramString);
  
  public abstract boolean addAppinfo(Appinfo paramAppinfo);
  
  public abstract boolean updateAppinfo(Appinfo paramAppinfo);
  
  public abstract boolean deleteAppinfo(Appinfo paramAppinfo);
  
  public abstract EtellerSubappinfo[] getAllSubAppinfo();
  
  public abstract int getMaxAppid();
  
  public abstract Appinfo[] getAllAppinfo();
  
  public abstract List<Appinfo> getAppinfosByCondition(Appinfo paramAppinfo, Pagination paramPagination)
    throws Exception;
}
