package cn.com.agree.eteller.afa.dao;

import cn.com.agree.eteller.afa.persistence.AfaBranch;
import cn.com.agree.eteller.generic.utils.Pagination;
import java.util.List;
import java.util.Map;

public abstract interface IAfaBrachDao
{
  public abstract boolean addAfaBrach(AfaBranch paramAfaBranch);
  
  public abstract boolean deleteAfaBrach(AfaBranch paramAfaBranch);
  
  public abstract boolean updateAfaBrach(AfaBranch paramAfaBranch);
  
  public abstract AfaBranch[] getAllAfaBrach();
  
  public abstract AfaBranch[] getAfaBrachByMap(Map paramMap);
  
  public abstract AfaBranch getAfaBrachBysql(String paramString);
  
  public abstract AfaBranch[] getAfaBrachBysql2(String paramString);
  
  public abstract List<AfaBranch> getAfaBranchByMap(Map<String, String> paramMap, Pagination paramPagination);
  
  public abstract String getBranchNameByNo(String paramString);
}
