package gnnt.MEBS.integrated.mgr.model.usermanage;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class VerrorLoginLog
  extends StandardModel
{
  private static final long serialVersionUID = 1L;
  @ClassDiscription(name="用户名", description="")
  private String userID;
  @ClassDiscription(name="管理员名称", description="")
  private String name;
  @ClassDiscription(name="仓库编号", description="")
  private String warehouseID;
  @ClassDiscription(name="登录错误次数", description="")
  private Integer counts;
  @ClassDiscription(name="登录的日期", description="")
  private Date loginDate;
  
  public String getUserID()
  {
    return this.userID;
  }
  
  public void setUserID(String paramString)
  {
    this.userID = paramString;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
  
  public String getWarehouseID()
  {
    return this.warehouseID;
  }
  
  public void setWarehouseID(String paramString)
  {
    this.warehouseID = paramString;
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
    return new StandardModel.PrimaryInfo("userID", this.userID);
  }
}
