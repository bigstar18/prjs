package cn.com.agree.eteller.afa.dao;

import cn.com.agree.eteller.afa.persistence.AfaFeeinfo;
import cn.com.agree.eteller.generic.utils.Pagination;
import java.util.List;
import java.util.Map;

public abstract interface IAfaFeeinfoDao
{
  public abstract boolean addAfaFeeinfo(AfaFeeinfo paramAfaFeeinfo);
  
  public abstract boolean deleteAfaFeeinfo(AfaFeeinfo paramAfaFeeinfo);
  
  public abstract boolean updateAfaFeeinfo(AfaFeeinfo paramAfaFeeinfo);
  
  public abstract AfaFeeinfo[] getAllAfaFeeinfo();
  
  public abstract AfaFeeinfo[] getAfaFeeinfoByMap(Map paramMap);
  
  public abstract AfaFeeinfo getAfaFeeinfoBysql(String paramString);
  
  public abstract List<AfaFeeinfo> getAfaFeeinfoByMap2(Map<String, String> paramMap, Pagination paramPagination);
  
  public abstract boolean updateAfaFeeinfo2(AfaFeeinfo paramAfaFeeinfo1, AfaFeeinfo paramAfaFeeinfo2)
    throws Exception;
}
