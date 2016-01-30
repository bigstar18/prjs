package gnnt.MEBS.integrated.mgr.model.usermanage;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class ErrorLoginLog
  extends StandardModel
{
  private static final long serialVersionUID = 1L;
  @ClassDiscription(name="交易员ID", description="")
  private String traderId;
  @ClassDiscription(name="登录日期", description="")
  private Date loginDate;
  @ClassDiscription(name="交易模块ID", description="")
  private Integer moduleId;
  @ClassDiscription(name="登录IP", description="")
  private String ip;
  
  public String getTraderId()
  {
    return this.traderId;
  }
  
  public void setTraderId(String paramString)
  {
    this.traderId = paramString;
  }
  
  public Date getLoginDate()
  {
    return this.loginDate;
  }
  
  public void setLoginDate(Date paramDate)
  {
    this.loginDate = paramDate;
  }
  
  public Integer getModuleId()
  {
    return this.moduleId;
  }
  
  public void setModuleId(Integer paramInteger)
  {
    this.moduleId = paramInteger;
  }
  
  public String getIp()
  {
    return this.ip;
  }
  
  public void setIp(String paramString)
  {
    this.ip = paramString;
  }
  
  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo("traderId", this.traderId);
  }
}
