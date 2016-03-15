package cn.com.agree.eteller.afa.dao;

import cn.com.agree.eteller.afa.persistence.AfaPininfo;
import cn.com.agree.eteller.generic.utils.Pagination;
import java.util.List;
import java.util.Map;

public abstract interface IAfaPininfoDao
{
  public abstract AfaPininfo[] loadAfaPininfo(Map paramMap);
  
  public abstract boolean addAfaPininfo(AfaPininfo paramAfaPininfo);
  
  public abstract boolean deleteAfaPininfo(AfaPininfo paramAfaPininfo);
  
  public abstract boolean updateAfaPininfo(AfaPininfo paramAfaPininfo1, AfaPininfo paramAfaPininfo2);
  
  public abstract AfaPininfo getAfaPininfoBysql(String paramString);
  
  public abstract List<AfaPininfo> loadAfaPininfoByMap(Map<String, String> paramMap, Pagination paramPagination);
}
