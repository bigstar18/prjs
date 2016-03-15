package cn.com.agree.eteller.afa.dao;

import cn.com.agree.eteller.afa.persistence.AfaUnitebrach;
import cn.com.agree.eteller.generic.utils.Pagination;
import java.util.List;
import java.util.Map;

public abstract interface IAfaUnitebrachDao
{
  public abstract boolean addAfaUnitebrach(AfaUnitebrach paramAfaUnitebrach);
  
  public abstract boolean deleteAfaUnitebrach(AfaUnitebrach paramAfaUnitebrach);
  
  public abstract boolean updateAfaUnitebrach(AfaUnitebrach paramAfaUnitebrach);
  
  public abstract AfaUnitebrach[] getAllAfaUnitebrach();
  
  public abstract AfaUnitebrach[] getAfaUnitebrachByMap(Map paramMap);
  
  public abstract AfaUnitebrach getAfaUnitebrachBysql(String paramString);
  
  public abstract List<AfaUnitebrach> getAfaUnitebrachByMap(Map<String, String> paramMap, Pagination paramPagination);
}
