package gnnt.MEBS.integrated.mgr.model.usermanage;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class VerrorLoginLog
  extends StandardModel
{
  private static final long serialVersionUID = 1L;
  @ClassDiscription(name=" 交易员ID", description="")
  private String traderId;
  @ClassDiscription(name="用户名", description="")
  private String userId;
  @ClassDiscription(name="登录错误次数", description="")
  private Integer counts;
  @ClassDiscription(name="登录的日期", description="")
  private Date loginDate;
  
  public String getTraderId()
  {
    return this.traderId;
  }
  
  public void setTraderId(String paramString)
  {
    this.traderId = paramString;
  }
  
  public String getUserId()
  {
    return this.userId;
  }
  
  public void setUserId(String paramString)
  {
    this.userId = paramString;
  }
  
  public Integer getCounts()
  {
    return this.counts;
  }
  
  public void setCounts(Integer paramInteger)
  {
    this.counts = paramInteger;
  }
  
  public Date getLoginDate()
  {
    return this.loginDate;
  }
  
  public void setLoginDate(Date paramDate)
  {
    this.loginDate = paramDate;
  }
  
  public StandardModel.PrimaryInfo fetchPKey()
  {
    return null;
  }
}
