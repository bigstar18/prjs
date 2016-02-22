package gnnt.MEBS.query.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;
import gnnt.MEBS.base.model.inner.ClassStatus;
import java.io.Serializable;
import java.sql.Timestamp;

public class SpecialMemberFundFlowSearch
  extends Clone
{
  private String s_memberNo;
  private String s_name;
  private String s_signNo;
  private Integer fundFlowId;
  private String oprcode;
  private Timestamp createTime;
  private Double amount;
  private Double balance;
  private String voucherno;
  
  public String getS_memberNo()
  {
    return this.s_memberNo;
  }
  
  public void setS_memberNo(String s_memberNo)
  {
    this.s_memberNo = s_memberNo;
  }
  
  public String getS_name()
  {
    return this.s_name;
  }
  
  public void setS_name(String s_name)
  {
    this.s_name = s_name;
  }
  
  public String getS_signNo()
  {
    return this.s_signNo;
  }
  
  public void setS_signNo(String s_signNo)
  {
    this.s_signNo = s_signNo;
  }
  
  public Integer getFundFlowId()
  {
    return this.fundFlowId;
  }
  
  public void setFundFlowId(Integer fundFlowId)
  {
    this.fundFlowId = fundFlowId;
  }
  
  @ClassDiscription(name="业务名称", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="101", value="入金"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="102", value="出金"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="211", value="与会员结算延期费"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="212", value="与会员结算手续费"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="213", value="与会员结算平仓盈亏"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="214", value="与会员结算持仓盈亏"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="310", value="会员与特别会员的结算盈亏")})
  public String getOprcode()
  {
    return this.oprcode;
  }
  
  public void setOprcode(String oprcode)
  {
    this.oprcode = oprcode;
  }
  
  public Timestamp getCreateTime()
  {
    return this.createTime;
  }
  
  public void setCreateTime(Timestamp createTime)
  {
    this.createTime = createTime;
  }
  
  public Double getAmount()
  {
    return this.amount;
  }
  
  public void setAmount(Double amount)
  {
    this.amount = amount;
  }
  
  public Double getBalance()
  {
    return this.balance;
  }
  
  public void setBalance(Double balance)
  {
    this.balance = balance;
  }
  
  public String getVoucherno()
  {
    return this.voucherno;
  }
  
  public void setVoucherno(String voucherno)
  {
    this.voucherno = voucherno;
  }
  
  public void setPrimary(String primary) {}
  
  public <T extends Serializable> T getId()
  {
    return null;
  }
}
