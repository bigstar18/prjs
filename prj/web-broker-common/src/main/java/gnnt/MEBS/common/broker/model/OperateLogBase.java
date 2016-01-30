package gnnt.MEBS.common.broker.model;

import java.util.Date;

public class OperateLogBase extends StandardModel
{
  private static final long serialVersionUID = 2313125464095942060L;
  private Long id;
  private String operator;
  private Date operateTime;
  private String operateIp;
  private String operatorType;
  private String mark;
  private String operateContent;
  private String currentValue;
  private Integer operateResult;
  private LogCatalog logCatalog;

  public Long getId()
  {
    return this.id;
  }

  public void setId(long paramLong)
  {
    this.id = Long.valueOf(paramLong);
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

  public String getOperateIp()
  {
    return this.operateIp;
  }

  public void setOperateIp(String paramString)
  {
    this.operateIp = paramString;
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

  public LogCatalog getLogCatalog()
  {
    return this.logCatalog;
  }

  public void setLogCatalog(LogCatalog paramLogCatalog)
  {
    this.logCatalog = paramLogCatalog;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo("id", this.id);
  }
}