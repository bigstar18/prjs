package gnnt.MEBS.timebargain.tradeweb.model;

import java.util.List;

public class ResponseResult
{
  private String name;
  private int retCode;
  private long longRetCode;
  private String message;
  private List resultList;
  
  public String getMessage()
  {
    return this.message;
  }
  
  public void setMessage(String message)
  {
    this.message = message;
  }
  
  public List getResultList()
  {
    return this.resultList;
  }
  
  public void setResultList(List resultList)
  {
    this.resultList = resultList;
  }
  
  public int getRetCode()
  {
    return this.retCode;
  }
  
  public void setRetCode(int retCode)
  {
    this.retCode = retCode;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public long getLongRetCode()
  {
    return this.longRetCode;
  }
  
  public void setLongRetCode(long longRetCode)
  {
    this.longRetCode = longRetCode;
  }
}
