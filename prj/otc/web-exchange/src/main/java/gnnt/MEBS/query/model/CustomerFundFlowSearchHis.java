package gnnt.MEBS.query.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;
import gnnt.MEBS.base.model.inner.ClassStatus;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class CustomerFundFlowSearchHis
  extends Clone
{
  private String customerNo;
  private String customerName;
  private String memberName;
  private String organizationNo;
  private String organizationName;
  private String brokerageNo;
  private String brokerageName;
  private Integer fundFlowId;
  private Integer oprcode;
  private Double startAmount;
  private Double changeAmount;
  private Double afterAmount;
  private String tradeId;
  private Date d_date;
  private String note;
  private Timestamp createTime;
  private String contractNo;
  private Date clearDate;
  private String memberNo;
  
  public String getOrganizationName()
  {
    return this.organizationName;
  }
  
  public void setOrganizationName(String organizationName)
  {
    this.organizationName = organizationName;
  }
  
  public String getBrokerageName()
  {
    return this.brokerageName;
  }
  
  public void setBrokerageName(String brokerageName)
  {
    this.brokerageName = brokerageName;
  }
  
  public String getMemberNo()
  {
    return this.memberNo;
  }
  
  public void setMemberNo(String memberNo)
  {
    this.memberNo = memberNo;
  }
  
  public String getMemberName()
  {
    return this.memberName;
  }
  
  public void setMemberName(String memberName)
  {
    this.memberName = memberName;
  }
  
  public String getOrganizationNo()
  {
    return this.organizationNo;
  }
  
  public void setOrganizationNo(String organizationNo)
  {
    this.organizationNo = organizationNo;
  }
  
  public String getBrokerageNo()
  {
    return this.brokerageNo;
  }
  
  public void setBrokerageNo(String brokerageNo)
  {
    this.brokerageNo = brokerageNo;
  }
  
  public Timestamp getCreateTime()
  {
    return this.createTime;
  }
  
  public void setCreateTime(Timestamp createTime)
  {
    this.createTime = createTime;
  }
  
  public String getContractNo()
  {
    return this.contractNo;
  }
  
  public void setContractNo(String contractNo)
  {
    this.contractNo = contractNo;
  }
  
  public Date getClearDate()
  {
    return this.clearDate;
  }
  
  public void setClearDate(Date clearDate)
  {
    this.clearDate = clearDate;
  }
  
  public <T extends Serializable> T getId()
  {
    return null;
  }
  
  public String getCustomerName()
  {
    return this.customerName;
  }
  
  public String getCustomerNo()
  {
    return this.customerNo;
  }
  
  public void setCustomerNo(String customerNo)
  {
    this.customerNo = customerNo;
  }
  
  public void setCustomerName(String customerName)
  {
    this.customerName = customerName;
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
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="101", value="入金"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="102", value="出金"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="201", value="扣除手续费"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="204", value="持仓亏损"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="205", value="持仓盈利"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="206", value="平仓亏损"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="207", value="平仓盈利"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="210", value="扣除延期费"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="310", value="客户与会员的结算盈亏")})
  public Integer getOprcode()
  {
    return this.oprcode;
  }
  
  public void setOprcode(Integer oprcode)
  {
    this.oprcode = oprcode;
  }
  
  public Double getStartAmount()
  {
    return this.startAmount;
  }
  
  public void setStartAmount(Double startAmount)
  {
    this.startAmount = startAmount;
  }
  
  public Double getChangeAmount()
  {
    return this.changeAmount;
  }
  
  public void setChangeAmount(Double changeAmount)
  {
    this.changeAmount = changeAmount;
  }
  
  public Double getAfterAmount()
  {
    return this.afterAmount;
  }
  
  public void setAfterAmount(Double afterAmount)
  {
    this.afterAmount = afterAmount;
  }
  
  public String getTradeId()
  {
    return this.tradeId;
  }
  
  public void setTradeId(String tradeId)
  {
    this.tradeId = tradeId;
  }
  
  public String getNote()
  {
    return this.note;
  }
  
  public void setNote(String note)
  {
    this.note = note;
  }
  
  public void setPrimary(String primary) {}
  
  public Date getD_date()
  {
    return this.d_date;
  }
  
  public void setD_date(Date d_date)
  {
    this.d_date = d_date;
  }
}
