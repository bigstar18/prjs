package cn.com.agree.eteller.afa.dao;

import cn.com.agree.eteller.afa.persistence.CisCommdata;
import java.util.Map;

public abstract interface ICisCommdataDao
{
  public abstract boolean addCisCommdata(CisCommdata paramCisCommdata);
  
  public abstract boolean deleteCisCommdata(CisCommdata paramCisCommdata);
  
  public abstract boolean updateCisCommdata(CisCommdata paramCisCommdata);
  
  public abstract CisCommdata[] getCisCommdataBymap(Map paramMap);
  
  public abstract CisCommdata[] getAllCisCommdata();
  
  public abstract CisCommdata getCisCommdataBysql(String paramString);
}
