package gnnt.MEBS.account.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;
import java.io.Serializable;
import java.util.Date;

public class ErrorLogin
  extends Clone
{
  private String traderId;
  private Date loginDate;
  private String moduleId;
  private String ip;
  private int count;
  
  @ClassDiscription(name="交易账号")
  public String getTraderId()
  {
    return this.traderId;
  }
  
  public void setTraderId(String traderId)
  {
    this.traderId = traderId;
  }
  
  @ClassDiscription(name="登陆时间")
  public Date getLoginDate()
  {
    return this.loginDate;
  }
  
  public void setLoginDate(Date loginDate)
  {
    this.loginDate = loginDate;
  }
  
  public String getModuleId()
  {
    return this.moduleId;
  }
  
  public void setModuleId(String moduleId)
  {
    this.moduleId = moduleId;
  }
  
  @ClassDiscription(name="登陆Ip")
  public String getIp()
  {
    return this.ip;
  }
  
  public void setIp(String ip)
  {
    this.ip = ip;
  }
  
  public <T extends Serializable> T getId()
  {
    return null;
  }
  
  public void setPrimary(String primary) {}
}
