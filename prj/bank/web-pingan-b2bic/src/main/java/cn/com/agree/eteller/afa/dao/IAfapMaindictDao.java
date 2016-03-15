package cn.com.agree.eteller.afa.dao;

import cn.com.agree.eteller.afa.persistence.AfapMaindict;
import cn.com.agree.eteller.generic.utils.Pagination;
import java.util.List;
import java.util.Map;

public abstract interface IAfapMaindictDao
{
  public abstract boolean addAfapMaindict(AfapMaindict paramAfapMaindict);
  
  public abstract boolean deleteAfapMaindict(AfapMaindict paramAfapMaindict);
  
  public abstract boolean updateAfapMaindict(AfapMaindict paramAfapMaindict);
  
  public abstract AfapMaindict[] getAllAfapMaindict();
  
  public abstract AfapMaindict[] getAfapMaindictByItem(String paramString);
  
  public abstract AfapMaindict[] getAfapMaindictByMap(Map paramMap);
  
  public abstract List<AfapMaindict> getAfapMaindictByMap(Map<String, Object> paramMap, Pagination paramPagination);
  
  public abstract String getNextItem();
  
  public abstract AfapMaindict getAfapMaindictBysql(String paramString);
}
