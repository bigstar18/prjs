package gnnt.trade.bank.vo;

import java.io.Serializable;
import java.util.Date;

public class SystemQSValue
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String firmID;
  public String sysFirmID;
  public String systemID;
  public String bankCode;
  public Date tradeDate;
  public double rights;
  public double freeze;
  public double available;
  public double rightsChange;
  public double freezeChange;
  public double availableChange;
  public double Fee;
  
  public String getFirmID()
  {
    return this.firmID;
  }
  
  public void setFirmID(String firmID)
  {
    this.firmID = firmID;
  }
  
  public String getSysFirmID()
  {
    return this.sysFirmID;
  }
  
  public void setSysFirmID(String sysFirmID)
  {
    this.sysFirmID = sysFirmID;
  }
  
  public String getSystemID()
  {
    return this.systemID;
  }
  
  public void setSystemID(String systemID)
  {
    this.systemID = systemID;
  }
  
  public String getBankCode()
  {
    return this.bankCode;
  }
  
  public void setBankCode(String bankCode)
  {
    this.bankCode = bankCode;
  }
  
  public Date getTradeDate()
  {
    return this.tradeDate;
  }
  
  public void setTradeDate(Date tradeDate)
  {
    this.tradeDate = tradeDate;
  }
  
  public double getRights()
  {
    return this.rights;
  }
  
  public void setRights(double rights)
  {
    this.rights = rights;
  }
  
  public double getFreeze()
  {
    return this.freeze;
  }
  
  public void setFreeze(double freeze)
  {
    this.freeze = freeze;
  }
  
  public double getAvailable()
  {
    return this.available;
  }
  
  public void setAvailable(double available)
  {
    this.available = available;
  }
  
  public double getRightsChange()
  {
    return this.rightsChange;
  }
  
  public void setRightsChange(double rightsChange)
  {
    this.rightsChange = rightsChange;
  }
  
  public double getFreezeChange()
  {
    return this.freezeChange;
  }
  
  public void setFreezeChange(double freezeChange)
  {
    this.freezeChange = freezeChange;
  }
  
  public double getAvailableChange()
  {
    return this.availableChange;
  }
  
  public void setAvailableChange(double availableChange)
  {
    this.availableChange = availableChange;
  }
  
  public double getFee()
  {
    return this.Fee;
  }
  
  public void setFee(double fee)
  {
    this.Fee = fee;
  }
}
