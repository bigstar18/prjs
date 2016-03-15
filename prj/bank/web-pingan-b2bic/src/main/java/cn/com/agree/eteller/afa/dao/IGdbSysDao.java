package cn.com.agree.eteller.afa.dao;

import cn.com.agree.eteller.afa.persistence.GdbSys;
import cn.com.agree.eteller.generic.utils.Pagination;
import java.util.List;
import java.util.Map;

public abstract interface IGdbSysDao
{
  public abstract boolean addGdbsys(GdbSys paramGdbSys);
  
  public abstract boolean deleteGdbsys(GdbSys paramGdbSys);
  
  public abstract boolean updateGdbsys(GdbSys paramGdbSys1, GdbSys paramGdbSys2);
  
  public abstract GdbSys[] getGdbSyBymap(Map paramMap);
  
  public abstract GdbSys getGdbSysBysql(String paramString);
  
  public abstract List<GdbSys> getGdbSysByMap(Map<String, String> paramMap, Pagination paramPagination);
}
