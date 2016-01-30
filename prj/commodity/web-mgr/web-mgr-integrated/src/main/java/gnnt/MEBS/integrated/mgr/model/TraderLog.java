package gnnt.MEBS.integrated.mgr.model;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class TraderLog
  extends StandardModel
{
  private static final long serialVersionUID = -7650303301801228777L;
  @ClassDiscription(name="交易员代码 ", description="")
  private String traderId;
  @ClassDiscription(name=" 操作类型 ", description="")
  private String oprType;
  @ClassDiscription(name="模块号", description="1：财务资金  2：中远期  3：现货  4：竞价")
  private Integer moduleId;
  @ClassDiscription(name="操作主机IP ", description="")
  private String IP;
  @ClassDiscription(name="操作日期 ", description="")
  private Date occurTime;
  
  public String getTraderId()
  {
    return this.traderId;
  }
  
  public void setTraderId(String paramString)
  {
    this.traderId = paramString;
  }
  
  public String getOprType()
  {
    return this.oprType;
  }
  
  public void setOprType(String paramString)
  {
    this.oprType = paramString;
  }
  
  public Integer getModuleId()
  {
    return this.moduleId;
  }
  
  public void setModuleId(Integer paramInteger)
  {
    this.moduleId = paramInteger;
  }
  
  public String getIP()
  {
    return this.IP;
  }
  
  public void setIP(String paramString)
  {
    this.IP = paramString;
  }
  
  public Date getOccurTime()
  {
    return this.occurTime;
  }
  
  public void setOccurTime(Date paramDate)
  {
    this.occurTime = paramDate;
  }
  
  public StandardModel.PrimaryInfo fetchPKey()
  {
    return null;
  }
}
