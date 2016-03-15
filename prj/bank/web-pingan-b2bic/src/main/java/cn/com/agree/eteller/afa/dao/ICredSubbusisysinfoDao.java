package cn.com.agree.eteller.afa.dao;

import cn.com.agree.eteller.afa.persistence.CredSubbusisysinfo;
import cn.com.agree.eteller.generic.utils.Pagination;
import java.util.List;
import java.util.Map;

public abstract interface ICredSubbusisysinfoDao
{
  public abstract boolean addCredSubbusisysinfo(CredSubbusisysinfo paramCredSubbusisysinfo);
  
  public abstract boolean deleteCredSubbusisysinfo(CredSubbusisysinfo paramCredSubbusisysinfo);
  
  public abstract boolean updateCredSubbusisysinfo(CredSubbusisysinfo paramCredSubbusisysinfo);
  
  public abstract CredSubbusisysinfo[] getAllCredSubbusisysinfo();
  
  public abstract CredSubbusisysinfo[] getCredSubbusisysinfoByMap(Map paramMap);
  
  public abstract CredSubbusisysinfo getCredSubbusisysinfoBysql(String paramString);
  
  public abstract List<CredSubbusisysinfo> getCredSubbusisysinfoByMap(Map<String, String> paramMap, Pagination paramPagination);
}
