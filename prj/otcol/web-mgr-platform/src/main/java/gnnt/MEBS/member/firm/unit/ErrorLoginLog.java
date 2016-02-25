package gnnt.MEBS.member.firm.unit;

import gnnt.MEBS.base.model.Clone;
import java.util.Date;

public class ErrorLoginLog
  extends Clone
{
  private String traderId;
  private Date loginDate;
  private String moduleId;
  private String ip;
  
  public Date getLoginDate()
  {
    return this.loginDate;
  }
  
  public void setLoginDate(Date paramDate)
  {
    this.loginDate = paramDate;
  }
  
  public String getTraderId()
  {
    return this.traderId;
  }
  
  public void setTraderId(String paramString)
  {
    this.traderId = paramString;
  }
  
  public String getModuleId()
  {
    return this.moduleId;
  }
  
  public void setModuleId(String paramString)
  {
    this.moduleId = paramString;
  }
  
  public String getIp()
  {
    return this.ip;
  }
  
  public void setIp(String paramString)
  {
    this.ip = paramString;
  }
}
