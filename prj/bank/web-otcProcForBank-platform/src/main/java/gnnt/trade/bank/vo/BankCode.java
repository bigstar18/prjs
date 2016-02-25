package gnnt.trade.bank.vo;

import java.io.Serializable;

public class BankCode
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String bankID;
  public String bankCode;
  public String marketOpen;
  
  public String getMarketOpen()
  {
    return this.marketOpen;
  }
  
  public void setMarketOpen(String marketOpen)
  {
    this.marketOpen = marketOpen;
  }
  
  public String getBankID()
  {
    return this.bankID;
  }
  
  public void setBankID(String bankID)
  {
    this.bankID = bankID;
  }
  
  public String getBankCode()
  {
    return this.bankCode;
  }
  
  public void setBankCode(String bankCode)
  {
    this.bankCode = bankCode;
  }
  
  public String toString()
  {
    return "BankCode [bankCode=" + this.bankCode + ", bankID=" + this.bankID + ", marketOpen=" + this.marketOpen + "]";
  }
}
