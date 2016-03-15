package cn.com.agree.eteller.afa.dao;

import cn.com.agree.eteller.afa.persistence.AfapSubdict;
import cn.com.agree.eteller.generic.utils.Pagination;
import java.util.List;
import java.util.Map;

public abstract interface IAfapSubdictDao
{
  public abstract boolean addAfapSubdict(AfapSubdict paramAfapSubdict);
  
  public abstract boolean deleteAfapSubdict(AfapSubdict paramAfapSubdict);
  
  public abstract boolean updateAfapSubdict(AfapSubdict paramAfapSubdict);
  
  public abstract AfapSubdict[] getAfapSubdictBymap(Map paramMap);
  
  public abstract AfapSubdict[] getAllAfapSubdict();
  
  public abstract AfapSubdict[] getLable(String paramString, int paramInt);
  
  public abstract AfapSubdict getAfaBranchType(String paramString, int paramInt);
  
  public abstract AfapSubdict getAfaSubdictCodename(String paramString1, String paramString2);
  
  public abstract AfapSubdict getAfapSubdictBysql(String paramString);
  
  public abstract AfapSubdict[] getAfapSubdictBysql2(String paramString);
  
  public abstract List<AfapSubdict> getAfapSubdictByMap(Map<String, String> paramMap, Pagination paramPagination);
}
