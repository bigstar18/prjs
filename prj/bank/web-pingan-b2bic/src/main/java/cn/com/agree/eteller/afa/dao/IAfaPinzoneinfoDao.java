package cn.com.agree.eteller.afa.dao;

import cn.com.agree.eteller.afa.persistence.AfaPinzoneinfo;
import java.util.Map;

public abstract interface IAfaPinzoneinfoDao
{
  public abstract boolean addAfaPinzoneinfo(AfaPinzoneinfo paramAfaPinzoneinfo);
  
  public abstract boolean deleteAfaPinzoneinfo(AfaPinzoneinfo paramAfaPinzoneinfo);
  
  public abstract boolean updateAfaPinzoneinfo(AfaPinzoneinfo paramAfaPinzoneinfo);
  
  public abstract AfaPinzoneinfo[] loadAfaPinzoneinfo(Map paramMap);
  
  public abstract AfaPinzoneinfo getAfaPinzoneinfoBysql(String paramString);
}
