package gnnt.MEBS.timebargain.tradeweb.model;

import java.util.List;

public class ResponseResult
{
  private String name;
  private String userID;
  private int retCode;
  private long longRetCode;
  private String message;
  private List resultList;
  private TotalDate totalDate;
  
  public String getUserID()
  {
    return this.userID;
  }
  
  public void setUserID(String paramString)
  {
    this.userID = paramString;
  }
  
  public TotalDate getTotalDate()
  {
    return this.totalDate;
  }
  
  public void setTotalDate(TotalDate paramTotalDate)
  {
    this.totalDate = paramTotalDate;
  }
  
  public String getMessage()
  {
    return this.message;
  }
  
  public void setMessage(String paramString)
  {
    this.message = paramString;
  }
  
  public List getResultList()
  {
    return this.resultList;
  }
  
  public void setResultList(List paramList)
  {
    this.resultList = paramList;
  }
  
  public int getRetCode()
  {
    return this.retCode;
  }
  
  public void setRetCode(int paramInt)
  {
    this.retCode = paramInt;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
  
  public long getLongRetCode()
  {
    return this.longRetCode;
  }
  
  public void setLongRetCode(long paramLong)
  {
    this.longRetCode = paramLong;
  }
}
