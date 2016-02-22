package gnnt.MEBS.report.model;

import gnnt.MEBS.base.model.Clone;
import java.io.Serializable;
import java.util.Date;

public class BankTransferNettingReport
  extends Clone
{
  private Date clearDate;
  private String firmId;
  private String bankCode;
  private Double transferfund;
  private String bankName;
  
  public String getFirmId()
  {
    return this.firmId;
  }
  
  public void setFirmId(String firmId)
  {
    this.firmId = firmId;
  }
  
  public Date getClearDate()
  {
    return this.clearDate;
  }
  
  public void setClearDate(Date clearDate)
  {
    this.clearDate = clearDate;
  }
  
  public String getBankCode()
  {
    return this.bankCode;
  }
  
  public void setBankCode(String bankCode)
  {
    this.bankCode = bankCode;
  }
  
  public Double getTransferfund()
  {
    return this.transferfund;
  }
  
  public void setTransferfund(Double transferfund)
  {
    this.transferfund = transferfund;
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
}
