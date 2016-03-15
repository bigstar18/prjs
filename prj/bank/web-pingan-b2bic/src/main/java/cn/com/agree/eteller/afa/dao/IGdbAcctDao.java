package cn.com.agree.eteller.afa.dao;

import cn.com.agree.eteller.afa.persistence.GdbAcct;
import cn.com.agree.eteller.generic.utils.Pagination;
import java.util.List;
import java.util.Map;

public abstract interface IGdbAcctDao
{
  public abstract boolean addGdbAcct(GdbAcct paramGdbAcct);
  
  public abstract boolean deleteGdbAcct(GdbAcct paramGdbAcct);
  
  public abstract boolean updateGdbAcct(GdbAcct paramGdbAcct1, GdbAcct paramGdbAcct2);
  
  public abstract GdbAcct[] getGdbAcctBymap(Map paramMap);
  
  public abstract GdbAcct getGdbAcctBysql(String paramString);
  
  public abstract List<GdbAcct> getGdbAcctByMap(Map<String, String> paramMap, Pagination paramPagination);
}
