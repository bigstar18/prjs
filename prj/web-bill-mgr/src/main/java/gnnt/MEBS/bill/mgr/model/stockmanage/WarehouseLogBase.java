package gnnt.MEBS.bill.mgr.model.stockmanage;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class WarehouseLogBase
  extends StandardModel
{
  private static final long serialVersionUID = 2313125464095942060L;
  @ClassDiscription(name="主键", description="")
  private Long id;
  @ClassDiscription(name="操作人", description="")
  private String operator;
  @ClassDiscription(name="操作时间", description="")
  private Date operateTime;
  @ClassDiscription(name="操作人IP", description="")
  private String operateIp;
  @ClassDiscription(name="操作人类型", description="")
  private String operatorType;
  @ClassDiscription(name="备注", description="")
  private String mark;
  @ClassDiscription(name="操作内容", description="")
  private String operateContent;
  @ClassDiscription(name="当前值", description="")
  private String currentValue;
  @ClassDiscription(name="操作结果", description="操作结果 1:成功 0：失败")
  private Integer operateResult;
  @ClassDiscription(name="仓库系统对应的日志类别", description="")
  private WarehouseLogCatalog logCatalog;
  @ClassDiscription(name="日志类型", description="日志类型 0 其他、1 后台、2 前台、3 核心")
  private Integer logType;
  
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
  
  public WarehouseLogCatalog getLogCatalog()
  {
    return this.logCatalog;
  }
  
  public void setLogCatalog(WarehouseLogCatalog paramWarehouseLogCatalog)
  {
    this.logCatalog = paramWarehouseLogCatalog;
  }
  
  public Integer getLogType()
  {
    return this.logType;
  }
  
  public void setLogType(Integer paramInteger)
  {
    this.logType = paramInteger;
  }
  
  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo("id", this.id);
  }
}
