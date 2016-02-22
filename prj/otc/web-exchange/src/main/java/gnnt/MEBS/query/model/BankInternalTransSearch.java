package gnnt.MEBS.query.model;

import gnnt.MEBS.base.model.Clone;
import java.io.Serializable;

public class BankInternalTransSearch
  extends Clone
{
  private Long transId;
  private String firmId;
  private String firmName;
  private String bankCode;
  private String bankCode_target;
  private Double amount;
  private String bankName;
  private String bankTargetName;
  private String firmType;
  
  public String getBankTargetName()
  {
    return this.bankTargetName;
  }
  
  public void setBankTargetName(String bankTargetName)
  {
    this.bankTargetName = bankTargetName;
  }
  
  public String getFirmType()
  {
    return this.firmType;
  }
  
  public void setFirmType(String firmType)
  {
    this.firmType = firmType;
  }
  
  public Long getTransId()
  {
    return this.transId;
  }
  
  public void setTransId(Long transId)
  {
    this.transId = transId;
  }
  
  public String getFirmId()
  {
    return this.firmId;
  }
  
  public void setFirmId(String firmId)
  {
    this.firmId = firmId;
  }
  
  public String getFirmName()
  {
    return this.firmName;
  }
  
  public void setFirmName(String firmName)
  {
    this.firmName = firmName;
  }
  
  public String getBankCode()
  {
    return this.bankCode;
  }
  
  public void setBankCode(String bankCode)
  {
    this.bankCode = bankCode;
  }
  
  public String getBankCode_target()
  {
    return this.bankCode_target;
  }
  
  public void setBankCode_target(String bankCode_target)
  {
    this.bankCode_target = bankCode_target;
  }
  
  public Double getAmount()
  {
    return this.amount;
  }
  
  public void setAmount(Double amount)
  {
    this.amount = amount;
  }
  
  public String getBankName()
  {
    return this.bankName;
  }
  
  public void setBankName(String bankName)
  {
    this.bankName = bankName;
  }
  
  public <T extends Serializable> T getId()
  {
    return null;
  }
  
  public void setPrimary(String primary) {}
  
  public BankInternalTransSearch() {}
  
  public BankInternalTransSearch(Long transId, String firmId, String firmName, String bankCode, String bankCode_target, Double amount, String bankName, String firmType, String bankTargetName)
  {
    this.transId = transId;
    this.firmId = firmId;
    this.firmName = firmName;
    this.bankCode = bankCode;
    this.bankCode_target = bankCode_target;
    this.amount = amount;
    this.bankName = bankName;
    this.firmType = firmType;
    this.bankTargetName = bankTargetName;
  }
}
