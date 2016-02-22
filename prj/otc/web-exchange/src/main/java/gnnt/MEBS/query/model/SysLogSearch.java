package gnnt.MEBS.query.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;
import gnnt.MEBS.base.model.inner.ClassStatus;
import java.util.Date;

public class SysLogSearch
  extends Clone
{
  private Long id;
  private String operator;
  private Long operateType;
  private String operateName;
  private Date operateTime;
  private String operateIp;
  private String operateContent;
  private String operateResult;
  private String operatorType;
  private String mark;
  
  public String getMark()
  {
    return this.mark;
  }
  
  public void setMark(String mark)
  {
    this.mark = mark;
  }
  
  public String getOperator()
  {
    return this.operator;
  }
  
  public void setOperator(String operator)
  {
    this.operator = operator;
  }
  
  public Long getOperateType()
  {
    return this.operateType;
  }
  
  public void setOperateType(Long operateType)
  {
    this.operateType = operateType;
  }
  
  public Date getOperateTime()
  {
    return this.operateTime;
  }
  
  public void setOperateTime(Date operateTime)
  {
    this.operateTime = operateTime;
  }
  
  public String getOperateIp()
  {
    return this.operateIp;
  }
  
  public void setOperateIp(String operateIp)
  {
    this.operateIp = operateIp;
  }
  
  public String getOperateContent()
  {
    return this.operateContent;
  }
  
  public void setOperateContent(String operateContent)
  {
    this.operateContent = operateContent;
  }
  
  public String getOperateResult()
  {
    return this.operateResult;
  }
  
  public void setOperateResult(String operateResult)
  {
    this.operateResult = operateResult;
  }
  
  public void setId(Long id)
  {
    this.id = id;
  }
  
  @ClassDiscription(name="业务名称", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="M", value="综合会员管理系统"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="E", value="交易所管理系统"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="S", value="特别会员管理系统")})
  public String getOperatorType()
  {
    return this.operatorType;
  }
  
  public void setOperatorType(String operatorType)
  {
    this.operatorType = operatorType;
  }
  
  public void setPrimary(String primary) {}
  
  public Long getId()
  {
    return this.id;
  }
  
  public String getOperateName()
  {
    return this.operateName;
  }
  
  public void setOperateName(String operateName)
  {
    this.operateName = operateName;
  }
  
  public SysLogSearch() {}
  
  public SysLogSearch(Long id, String operator, Long operateType, String operateName, Date operateTime, String operateIp, String operateContent, String operateResult, String operatorType)
  {
    this.id = id;
    this.operator = operator;
    this.operateType = operateType;
    this.operateName = operateName;
    this.operateTime = operateTime;
    this.operateIp = operateIp;
    this.operateContent = operateContent;
    this.operateResult = operateResult;
    this.operatorType = operatorType;
  }
}
