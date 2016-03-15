package cn.com.agree.eteller.afa.dao;

import cn.com.agree.eteller.afa.persistence.EtellerExcelPub;
import java.util.Map;

public abstract interface IEtellerExcelPubDao
{
  public abstract EtellerExcelPub[] getEtellerExcelPubByMap(Map paramMap);
  
  public abstract boolean deleteEtellerExcelPubOne(EtellerExcelPub paramEtellerExcelPub);
  
  public abstract boolean addEtellerExcelPub(EtellerExcelPub paramEtellerExcelPub);
}
