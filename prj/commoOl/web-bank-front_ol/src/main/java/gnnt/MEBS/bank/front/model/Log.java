package gnnt.MEBS.bank.front.model;

import gnnt.MEBS.common.front.model.StandardModel;
import gnnt.MEBS.common.front.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.front.model.translate.ClassDiscription;
import java.util.Date;

public class Log
  extends StandardModel
{
  private static final long serialVersionUID = 2872748809694940875L;
  @ClassDiscription(name="日志编号", description="")
  private Long logID;
  @ClassDiscription(name="操作员", description="")
  private String logopr;
  @ClassDiscription(name="操作内容 ", description="")
  private String logcontent;
  @ClassDiscription(name="日志记录日期", description="")
  private Date logDate;
  @ClassDiscription(name="日志记录登录机器", description="")
  private String logIP;
  
  public Long getLogID()
  {
    return this.logID;
  }
  
  public void setLogID(Long logID)
  {
    this.logID = logID;
  }
  
  public String getLogopr()
  {
    return this.logopr;
  }
  
  public void setLogopr(String logopr)
  {
    this.logopr = logopr;
  }
  
  public String getLogcontent()
  {
    return this.logcontent;
  }
  
  public void setLogcontent(String logcontent)
  {
    this.logcontent = logcontent;
  }
  
  public Date getLogDate()
  {
    return this.logDate;
  }
  
  public void setLogDate(Date logDate)
  {
    this.logDate = logDate;
  }
  
  public String getLogIP()
  {
    return this.logIP;
  }
  
  public void setLogIP(String logIP)
  {
    this.logIP = logIP;
  }
  
  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo("logID", this.logID);
  }
}
