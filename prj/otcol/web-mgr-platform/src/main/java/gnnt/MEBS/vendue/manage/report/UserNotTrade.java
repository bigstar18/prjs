package gnnt.MEBS.vendue.manage.report;

import java.math.BigDecimal;

public class UserNotTrade
{
  private String companyName = null;
  private String usercode = null;
  private BigDecimal lastBail = null;
  
  public void setCompanyName(String paramString)
  {
    this.companyName = paramString;
  }
  
  public String getCompanyName()
  {
    return this.companyName;
  }
  
  public void setLastBail(BigDecimal paramBigDecimal)
  {
    this.lastBail = paramBigDecimal;
  }
  
  public BigDecimal getLastBail()
  {
    return this.lastBail;
  }
  
  public void setUsercode(String paramString)
  {
    this.usercode = paramString;
  }
  
  public String getUsercode()
  {
    return this.usercode;
  }
}
