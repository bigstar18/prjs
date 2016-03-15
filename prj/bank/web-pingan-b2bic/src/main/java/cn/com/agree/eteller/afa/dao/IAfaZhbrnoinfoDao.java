package cn.com.agree.eteller.afa.dao;

import cn.com.agree.eteller.afa.persistence.AfaZhbrnoinfo;
import java.util.Map;

public abstract interface IAfaZhbrnoinfoDao
{
  public abstract boolean addAfaZhbrnoinfo(AfaZhbrnoinfo paramAfaZhbrnoinfo);
  
  public abstract boolean deleteAfaZhbrnoinfo(AfaZhbrnoinfo paramAfaZhbrnoinfo);
  
  public abstract boolean updateAfaZhbrnoinfo(AfaZhbrnoinfo paramAfaZhbrnoinfo);
  
  public abstract AfaZhbrnoinfo[] getAfaZhbrnoinfoBymap(Map paramMap);
  
  public abstract AfaZhbrnoinfo[] getAllAfaZhbrnoinfo();
}
