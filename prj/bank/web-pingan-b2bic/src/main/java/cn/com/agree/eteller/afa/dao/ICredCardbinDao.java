package cn.com.agree.eteller.afa.dao;

import cn.com.agree.eteller.afa.persistence.CredCardbin;
import cn.com.agree.eteller.generic.utils.Pagination;
import java.util.List;
import java.util.Map;

public abstract interface ICredCardbinDao
{
  public abstract boolean addCredCardbin(CredCardbin paramCredCardbin);
  
  public abstract boolean deleteCredCardbin(CredCardbin paramCredCardbin);
  
  public abstract boolean updateCredCardbin(CredCardbin paramCredCardbin);
  
  public abstract CredCardbin[] getAllCredCardbin();
  
  public abstract CredCardbin[] getCredCardbinByMap(Map paramMap);
  
  public abstract CredCardbin getCredCardbinBysql(String paramString);
  
  public abstract List<CredCardbin> getCredCardbinByMap(Map<String, String> paramMap, Pagination paramPagination);
}
