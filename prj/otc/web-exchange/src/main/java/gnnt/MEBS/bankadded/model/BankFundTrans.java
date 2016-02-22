package gnnt.MEBS.bankadded.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;
import gnnt.MEBS.base.model.inner.ClassStatus;
import java.util.Date;

public class BankFundTrans
  extends Clone
{
  private Long transID;
  private String bankName;
  private String bankCode;
  private String transType;
  private String iOFlag;
  private Double amount;
  private Date createTime;
  
  public BankFundTrans() {}
  
  public BankFundTrans(Long transID, String bankCode, String bankName, String transType, Double amount, Date createTime)
  {
    this.transID = transID;
    this.bankCode = bankCode;
    this.bankName = bankName;
    this.transType = transType;
    this.amount = amount;
    this.createTime = createTime;
  }
  
  @ClassDiscription(name="银行名称")
  public String getBankName()
  {
    return this.bankName;
  }
  
  public void setBankName(String bankName)
  {
    this.bankName = bankName;
  }
  
  @ClassDiscription(name="划转ID", key=true, keyWord=true)
  public Long getTransID()
  {
    return this.transID;
  }
  
  public void setTransID(Long transID)
  {
    this.transID = transID;
  }
  
  @ClassDiscription(name="银行代码")
  public String getBankCode()
  {
    return this.bankCode;
  }
  
  public void setBankCode(String bankCode)
  {
    this.bankCode = bankCode;
  }
  
  @ClassDiscription(name="划转类型", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="F", value="手续费划转"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="O", value="轧差划转")})
  public String getTransType()
  {
    return this.transType;
  }
  
  public void setTransType(String transType)
  {
    this.transType = transType;
  }
  
  @ClassDiscription(name="出入标志", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="I", value="入金"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="O", value="出金")})
  public String getiOFlag()
  {
    return this.iOFlag;
  }
  
  public void setiOFlag(String iOFlag)
  {
    this.iOFlag = iOFlag;
  }
  
  @ClassDiscription(name="发生额")
  public Double getAmount()
  {
    return this.amount;
  }
  
  public void setAmount(Double amount)
  {
    this.amount = amount;
  }
  
  @ClassDiscription(name="发生时间")
  public Date getCreateTime()
  {
    return this.createTime;
  }
  
  public void setCreateTime(Date createTime)
  {
    this.createTime = createTime;
  }
  
  public Long getId()
  {
    return this.transID;
  }
  
  public void setPrimary(String primary) {}
}
