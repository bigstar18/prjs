package gnnt.MEBS.query.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;
import gnnt.MEBS.base.model.inner.ClassStatus;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class MemberFundFlowSearchHis
  extends Clone
{
  private String memberNo;
  private String memberName;
  private Integer fundFlowId;
  private Integer oprcode;
  private Double amount;
  private Double balance;
  private Long voucherno;
  private Timestamp createtime;
  private Date clearDate;
  
  public Date getClearDate()
  {
    return this.clearDate;
  }
  
  public void setClearDate(Date clearDate)
  {
    this.clearDate = clearDate;
  }
  
  public String getMemberNo()
  {
    return this.memberNo;
  }
  
  public void setMemberNo(String memberNo)
  {
    this.memberNo = memberNo;
  }
  
  public <T extends Serializable> T getId()
  {
    return null;
  }
  
  public String getMemberName()
  {
    return this.memberName;
  }
  
  public void setMemberName(String memberName)
  {
    this.memberName = memberName;
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
  
  public Long getVoucherno()
  {
    return this.voucherno;
  }
  
  public void setVoucherno(Long voucherno)
  {
    this.voucherno = voucherno;
  }
  
  public Timestamp getCreatetime()
  {
    return this.createtime;
  }
  
  public void setCreatetime(Timestamp createtime)
  {
    this.createtime = createtime;
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
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="101", value="入金"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="102", value="出金"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="201", value="扣除手续费"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="204", value="持仓亏损"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="205", value="持仓盈利"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="206", value="平仓亏损"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="207", value="平仓盈利"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="210", value="扣除延期费"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="211", value="与客户结算延期费"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="212", value="与客户结算手续费"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="213", value="与客户结算平仓盈亏"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="214", value="与客户结算持仓盈亏"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="310", value="客户与会员的结算盈亏")})
  public Integer getOprcode()
  {
    return this.oprcode;
  }
  
  public void setOprcode(Integer oprcode)
  {
    this.oprcode = oprcode;
  }
  
  public void setPrimary(String primary) {}
}
