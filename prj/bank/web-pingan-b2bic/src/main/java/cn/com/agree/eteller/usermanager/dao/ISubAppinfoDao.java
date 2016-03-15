package cn.com.agree.eteller.usermanager.dao;

import cn.com.agree.eteller.usermanager.persistence.EtellerSubappinfo;

public abstract interface ISubAppinfoDao
{
  public abstract EtellerSubappinfo getSubAppinfo(String paramString);
  
  public abstract EtellerSubappinfo[] getSubAppinfoByappid(String paramString);
  
  public abstract boolean addSubAppinfo(EtellerSubappinfo paramEtellerSubappinfo);
  
  public abstract boolean updateSubAppinfo(EtellerSubappinfo paramEtellerSubappinfo);
  
  public abstract boolean deleteSubAppinfo(EtellerSubappinfo paramEtellerSubappinfo);
  
  public abstract EtellerSubappinfo[] getAllSubAppinfo();
  
  public abstract int getMaxSubappid();
}
