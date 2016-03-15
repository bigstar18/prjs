package cn.com.agree.eteller.afa.dao;

import cn.com.agree.eteller.afa.persistence.CredRecktradecode;
import cn.com.agree.eteller.generic.utils.Pagination;
import java.util.List;
import java.util.Map;

public abstract interface ICredRecktradecodeDao
{
  public abstract boolean addCredRecktradecode(CredRecktradecode paramCredRecktradecode);
  
  public abstract boolean deleteCredRecktradecode(CredRecktradecode paramCredRecktradecode);
  
  public abstract boolean updateCredRecktradecode(CredRecktradecode paramCredRecktradecode);
  
  public abstract CredRecktradecode[] getAllCredRecktradecode();
  
  public abstract CredRecktradecode[] getCredRecktradecodeByMap(Map paramMap);
  
  public abstract CredRecktradecode getCredRecktradecodeBysql(String paramString);
  
  public abstract List<CredRecktradecode> getCredRecktradecodeByMap(Map<String, String> paramMap, Pagination paramPagination);
}
