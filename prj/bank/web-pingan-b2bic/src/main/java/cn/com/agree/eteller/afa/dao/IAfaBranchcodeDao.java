package cn.com.agree.eteller.afa.dao;

import cn.com.agree.eteller.afa.persistence.AfaBranchcode;
import cn.com.agree.eteller.generic.utils.Pagination;
import java.util.List;
import java.util.Map;

public abstract interface IAfaBranchcodeDao
{
  public abstract boolean addAfabranchcode(AfaBranchcode paramAfaBranchcode);
  
  public abstract boolean deleteAfabranchcode(AfaBranchcode paramAfaBranchcode);
  
  public abstract boolean updateAfabranchcode(AfaBranchcode paramAfaBranchcode1, AfaBranchcode paramAfaBranchcode2);
  
  public abstract AfaBranchcode[] getAfabranchcodeBymap(Map paramMap);
  
  public abstract AfaBranchcode getAfabranchcodeBysql(String paramString);
  
  public abstract List<AfaBranchcode> getAfaBranchcodeByMap(Map<String, String> paramMap, Pagination paramPagination);
}
