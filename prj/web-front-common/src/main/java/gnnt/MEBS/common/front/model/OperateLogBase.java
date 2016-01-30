package gnnt.MEBS.common.front.model;

import gnnt.MEBS.common.front.model.translate.ClassDiscription;
import java.util.Date;

public class OperateLogBase
  extends StandardModel
{
  private static final long serialVersionUID = 8416138017634995277L;
  @ClassDiscription(name="编号", description="")
  private Long id;
  @ClassDiscription(name="操作员", description="")
  private String operator;
  @ClassDiscription(name="操作时间", description="")
  private Date operateTime;
  @ClassDiscription(name="对应的日志类别", description="")
  private LogCatalog logCatalog;
  @ClassDiscription(name="操作IP", description="")
  private String operateIP;
  @ClassDiscription(name="操作人种类", description="")
  private String operatorType;
  @ClassDiscription(name="标示", description="")
  private String mark;
  @ClassDiscription(name="操作内容", description="")
  private String operateContent;
  @ClassDiscription(name="当前值", description="")
  private String currentValue;
  @ClassDiscription(name="操作结果", description="操作结果 1：成功 0：失败")
  private Integer operateResult;
  @ClassDiscription(name="日志类型", description="日志类型 0： 其他、1 ：后台、2 ：前台、3： 核心")
  private Integer logType;
  
  public Long getId()
  {
    return this.id;
  }
  
  public void setId(Long paramLong)
  {
    this.id = paramLong;
  }
  
  public String getOperator()
  {
    return this.operator;
  }
  
  public void setOperator(String paramString)
  {
    this.operator = paramString;
  }
  
  public Date getOperateTime()
  {
    return this.operateTime;
  }
  
  public void setOperateTime(Date paramDate)
  {
    this.operateTime = paramDate;
  }
  
  public LogCatalog getLogCatalog()
  {
    return this.logCatalog;
  }
  
  public void setLogCatalog(LogCatalog paramLogCatalog)
  {
    this.logCatalog = paramLogCatalog;
  }
  
  public String getOperateIP()
  {
    return this.operateIP;
  }
  
  public void setOperateIP(String paramString)
  {
    this.operateIP = paramString;
  }
  
  public String getOperatorType()
  {
    return this.operatorType;
  }
  
  public void setOperatorType(String paramString)
  {
    this.operatorType = paramString;
  }
  
  public String getMark()
  {
    return this.mark;
  }
  
  public void setMark(String paramString)
  {
    this.mark = paramString;
  }
  
  public String getOperateContent()
  {
    return this.operateContent;
  }
  
  public void setOperateContent(String paramString)
  {
    this.operateContent = paramString;
  }
  
  public String getCurrentValue()
  {
    return this.currentValue;
  }
  
  public void setCurrentValue(String paramString)
  {
    this.currentValue = paramString;
  }
  
  public Integer getOperateResult()
  {
    return this.operateResult;
  }
  
  public void setOperateResult(Integer paramInteger)
  {
    this.operateResult = paramInteger;
  }
  
  public Integer getLogType()
  {
    return this.logType;
  }
  
  public void setLogType(Integer paramInteger)
  {
    this.logType = paramInteger;
  }
  
  public PrimaryInfo fetchPKey()
  {
    return new PrimaryInfo("id", this.id);
  }
}
