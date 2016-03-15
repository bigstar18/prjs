package cn.com.agree.eteller.afa.dao;

import cn.com.agree.eteller.afa.persistence.GdbHostabsinfo;
import cn.com.agree.eteller.generic.utils.Pagination;
import java.util.List;
import java.util.Map;

public abstract interface IGdbHostabsinfoDao
{
  public abstract boolean addGdbHostabsinfo(GdbHostabsinfo paramGdbHostabsinfo);
  
  public abstract boolean deleteGdbHostabsinfo(GdbHostabsinfo paramGdbHostabsinfo);
  
  public abstract boolean updateGdbHostabsinfo(GdbHostabsinfo paramGdbHostabsinfo1, GdbHostabsinfo paramGdbHostabsinfo2);
  
  public abstract GdbHostabsinfo[] getGdbHostabsinfoBymap(Map paramMap);
  
  public abstract GdbHostabsinfo getGdbHostabsinfoBysql(String paramString);
  
  public abstract List<GdbHostabsinfo> getGdbHostabsinfoByMap(Map<String, String> paramMap, Pagination paramPagination);
}
