package cn.com.agree.eteller.afa.dao;

import cn.com.agree.eteller.afa.persistence.AfaHostfuncmap;
import java.util.Map;

public abstract interface IAfaHostfuncmapDao
{
  public abstract boolean addAfaHostfuncmap(AfaHostfuncmap paramAfaHostfuncmap);
  
  public abstract boolean deleteAfaHostfuncmap(AfaHostfuncmap paramAfaHostfuncmap);
  
  public abstract boolean updateAfaHostfuncmap(AfaHostfuncmap paramAfaHostfuncmap1, AfaHostfuncmap paramAfaHostfuncmap2);
  
  public abstract AfaHostfuncmap[] getAfaHostfuncmapBymap(Map paramMap);
  
  public abstract AfaHostfuncmap[] getAllafaHostfuncmap();
  
  public abstract AfaHostfuncmap getAfaHostfuncmapBysql(String paramString);
}
