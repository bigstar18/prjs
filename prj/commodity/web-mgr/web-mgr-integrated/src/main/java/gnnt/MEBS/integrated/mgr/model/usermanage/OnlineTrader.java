package gnnt.MEBS.integrated.mgr.model.usermanage;

import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.io.Serializable;

public class OnlineTrader
  implements Serializable
{
  private static final long serialVersionUID = 4489331502708185957L;
  @ClassDiscription(name="在线交易员Id", description="")
  private String traderId;
  @ClassDiscription(name="交易员登录时间", description="")
  private String logonTime;
  @ClassDiscription(name="交易员主机Ip", description="")
  private String logonIp;
  
  public String getTraderId()
  {
    return this.traderId;
  }
  
  public void setTraderId(String paramString)
  {
    this.traderId = paramString;
  }
  
  public String getLogonTime()
  {
    return this.logonTime;
  }
  
  public void setLogonTime(String paramString)
  {
    this.logonTime = paramString;
  }
  
  public String getLogonIp()
  {
    return this.logonIp;
  }
  
  public void setLogonIp(String paramString)
  {
    this.logonIp = paramString;
  }
}
