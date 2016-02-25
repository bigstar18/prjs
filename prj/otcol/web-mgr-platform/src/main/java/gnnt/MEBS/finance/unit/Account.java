package gnnt.MEBS.finance.unit;

public class Account
{
  private String code;
  private String name;
  private Integer accountLevel;
  private String dCFlag;
  
  public Integer getAccountLevel()
  {
    return this.accountLevel;
  }
  
  public void setAccountLevel(Integer paramInteger)
  {
    this.accountLevel = paramInteger;
  }
  
  public String getCode()
  {
    return this.code;
  }
  
  public void setCode(String paramString)
  {
    this.code = paramString;
  }
  
  public String getDCFlag()
  {
    return this.dCFlag;
  }
  
  public void setDCFlag(String paramString)
  {
    this.dCFlag = paramString;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
}
