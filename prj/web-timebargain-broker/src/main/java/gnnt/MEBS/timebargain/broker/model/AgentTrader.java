package gnnt.MEBS.timebargain.broker.model;

import gnnt.MEBS.common.broker.model.StandardModel;
import gnnt.MEBS.common.broker.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.broker.model.translate.ClassDiscription;
import java.util.Date;

public class AgentTrader extends StandardModel
{
  private static final long serialVersionUID = -536024199522683791L;

  @ClassDiscription(name="代为交易员代码 ", description="")
  private String agentTraderId;

  @ClassDiscription(name="代为交易员名称", description="")
  private String name;

  @ClassDiscription(name="代为交易员密码", description="")
  private String password;

  @ClassDiscription(name="代为交易员类型", description="0 代为委托员1 强平员")
  private Long type;

  @ClassDiscription(name="代为交易员状态 ", description="0 正常1 禁止登陆")
  private Long status;

  @ClassDiscription(name="可操作交易商", description="")
  private String operateFirm;

  @ClassDiscription(name="创建时间", description="")
  private Date createTime;

  @ClassDiscription(name="修改时间", description="最后一次修改时间")
  private Date modifyTime;

  public String getAgentTraderId()
  {
    return this.agentTraderId;
  }

  public void setAgentTraderId(String paramString)
  {
    this.agentTraderId = paramString;
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String paramString)
  {
    this.name = paramString;
  }

  public String getPassword()
  {
    return this.password;
  }

  public void setPassword(String paramString)
  {
    this.password = paramString;
  }

  public Long getType()
  {
    return this.type;
  }

  public void setType(Long paramLong)
  {
    this.type = paramLong;
  }

  public Long getStatus()
  {
    return this.status;
  }

  public void setStatus(Long paramLong)
  {
    this.status = paramLong;
  }

  public String getOperateFirm()
  {
    return this.operateFirm;
  }

  public void setOperateFirm(String paramString)
  {
    this.operateFirm = paramString;
  }

  public Date getCreateTime()
  {
    return this.createTime;
  }

  public void setCreateTime(Date paramDate)
  {
    this.createTime = paramDate;
  }

  public Date getModifyTime()
  {
    return this.modifyTime;
  }

  public void setModifyTime(Date paramDate)
  {
    this.modifyTime = paramDate;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo("agentTraderId", this.agentTraderId);
  }
}