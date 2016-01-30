package gnnt.MEBS.integrated.mgr.model.usermanage;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class ErrorLoginLog
  extends StandardModel
{
  private static final long serialVersionUID = 1L;
  @ClassDiscription(name="错误编号", description="")
  private long errorLoginID;
  @ClassDiscription(name="用户代码", description="")
  private String userID;
  @ClassDiscription(name="登录日期", description="")
  private Date loginDate;
  @ClassDiscription(name="仓库编号", description="")
  private String warehouseID;
  @ClassDiscription(name="登录IP", description="")
  private String ip;
  
  public long getErrorLoginID()
  {
    return this.errorLoginID;
  }
  
  public void setErrorLoginID(long paramLong)
  {
    this.errorLoginID = paramLong;
  }
  
  public String getUserID()
  {
    return this.userID;
  }
  
  public void setUserID(String paramString)
  {
    this.userID = paramString;
  }
  
  public Date getLoginDate()
  {
    return this.loginDate;
  }
  
  public void setLoginDate(Date paramDate)
  {
    this.loginDate = paramDate;
  }
  
  public String getWarehouseID()
  {
    return this.warehouseID;
  }
  
  public void setWarehouseID(String paramString)
  {
    this.warehouseID = paramString;
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
    return new StandardModel.PrimaryInfo("errorLoginID", Long.valueOf(this.errorLoginID));
  }
}
