package cn.com.agree.eteller.afa.dao;

import cn.com.agree.eteller.afa.persistence.AfaZhinfo;
import java.util.Map;

public abstract interface IAfaZhinfoDao
{
  public abstract boolean addAfaZhinfo(AfaZhinfo paramAfaZhinfo);
  
  public abstract boolean deleteAfaZhinfo(AfaZhinfo paramAfaZhinfo);
  
  public abstract boolean updateAfaZhinfo(AfaZhinfo paramAfaZhinfo);
  
  public abstract AfaZhinfo[] getAfaZhinfoBymap(Map paramMap);
  
  public abstract AfaZhinfo[] getAllAfaZhinfo();
}
