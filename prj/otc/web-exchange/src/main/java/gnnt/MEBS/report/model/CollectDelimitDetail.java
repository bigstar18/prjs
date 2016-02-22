package gnnt.MEBS.report.model;

import gnnt.MEBS.base.model.Clone;
import java.io.Serializable;
import java.util.Date;

public class CollectDelimitDetail
  extends Clone
{
  private Date clearDate;
  private String desBankCode;
  private String desBankName;
  private String desBankAccount;
  private String desBankAccountName;
  private String srcBankCode;
  private String srcBankName;
  private String srcBankAccount;
  private String srcBankAccountName;
  private Double offsetbalance;
  
  public Date getClearDate()
  {
    return this.clearDate;
  }
  
  public void setClearDate(Date clearDate)
  {
    this.clearDate = clearDate;
  }
  
  public String getDesBankCode()
  {
    return this.desBankCode;
  }
  
  public void setDesBankCode(String desBankCode)
  {
    this.desBankCode = desBankCode;
  }
  
  public String getDesBankName()
  {
    return this.desBankName;
  }
  
  public void setDesBankName(String desBankName)
  {
    this.desBankName = desBankName;
  }
  
  public String getDesBankAccount()
  {
    return this.desBankAccount;
  }
  
  public void setDesBankAccount(String desBankAccount)
  {
    this.desBankAccount = desBankAccount;
  }
  
  public String getDesBankAccountName()
  {
    return this.desBankAccountName;
  }
  
  public void setDesBankAccountName(String desBankAccountName)
  {
    this.desBankAccountName = desBankAccountName;
  }
  
  public String getSrcBankCode()
  {
    return this.srcBankCode;
  }
  
  public void setSrcBankCode(String srcBankCode)
  {
    this.srcBankCode = srcBankCode;
  }
  
  public String getSrcBankName()
  {
    return this.srcBankName;
  }
  
  public void setSrcBankName(String srcBankName)
  {
    this.srcBankName = srcBankName;
  }
  
  public String getSrcBankAccount()
  {
    return this.srcBankAccount;
  }
  
  public void setSrcBankAccount(String srcBankAccount)
  {
    this.srcBankAccount = srcBankAccount;
  }
  
  public String getSrcBankAccountName()
  {
    return this.srcBankAccountName;
  }
  
  public void setSrcBankAccountName(String srcBankAccountName)
  {
    this.srcBankAccountName = srcBankAccountName;
  }
  
  public Double getOffsetbalance()
  {
    return this.offsetbalance;
  }
  
  public void setOffsetbalance(Double offsetbalance)
  {
    this.offsetbalance = offsetbalance;
  }
  
  public <T extends Serializable> T getId()
  {
    return null;
  }
  
  public void setPrimary(String primary) {}
}
