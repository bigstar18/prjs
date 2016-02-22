package gnnt.MEBS.bankadded.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;

public class BankFunds
  extends Clone
{
  private String bankCode;
  private String bankName;
  private Double firmBalance;
  private Double bankBalance;
  private Double marketFeeBalance;
  private Double offsetBalance;
  private Double lastFirmBalance;
  private Double lastBankBalance;
  private Double lastMarketFeeBalance;
  private Double lastOffsetBalance;
  private Double marketFeeNew;
  private Double marketdelayFeeNew;
  
  public Double getMarketdelayFeeNew()
  {
    return this.marketdelayFeeNew;
  }
  
  public void setMarketdelayFeeNew(Double marketdelayFeeNew)
  {
    this.marketdelayFeeNew = marketdelayFeeNew;
  }
  
  public BankFunds() {}
  
  public BankFunds(String bankCode, String bankName, Double marketFeeBalance, Double lastMarketFeeBalance, Double marketFeeNew, Double marketdelayFeeNew)
  {
    this.bankCode = bankCode;
    this.bankName = bankName;
    this.marketFeeBalance = marketFeeBalance;
    this.marketdelayFeeNew = marketdelayFeeNew;
    this.lastMarketFeeBalance = lastMarketFeeBalance;
    this.marketFeeNew = marketFeeNew;
  }
  
  public Double getMarketFeeNew()
  {
    return this.marketFeeNew;
  }
  
  public void setMarketFeeNew(Double marketFeeNew)
  {
    this.marketFeeNew = marketFeeNew;
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
  
  @ClassDiscription(name="银行代码", key=true, keyWord=true)
  public String getBankCode()
  {
    return this.bankCode;
  }
  
  public void setBankCode(String bankCode)
  {
    this.bankCode = bankCode;
  }
  
  @ClassDiscription(name="交易商资金总额")
  public Double getFirmBalance()
  {
    return this.firmBalance;
  }
  
  public void setFirmBalance(Double firmBalance)
  {
    this.firmBalance = firmBalance;
  }
  
  @ClassDiscription(name="银行资金余额")
  public Double getBankBalance()
  {
    return this.bankBalance;
  }
  
  public void setBankBalance(Double bankBalance)
  {
    this.bankBalance = bankBalance;
  }
  
  @ClassDiscription(name="交易所手续费余额")
  public Double getMarketFeeBalance()
  {
    return this.marketFeeBalance;
  }
  
  public void setMarketFeeBalance(Double marketFeeBalance)
  {
    this.marketFeeBalance = marketFeeBalance;
  }
  
  @ClassDiscription(name="轧差余额")
  public Double getOffsetBalance()
  {
    return this.offsetBalance;
  }
  
  public void setOffsetBalance(Double offsetBalance)
  {
    this.offsetBalance = offsetBalance;
  }
  
  @ClassDiscription(name="上日交易商资金总额")
  public Double getLastFirmBalance()
  {
    return this.lastFirmBalance;
  }
  
  public void setLastFirmBalance(Double lastFirmBalance)
  {
    this.lastFirmBalance = lastFirmBalance;
  }
  
  @ClassDiscription(name="上日银行资金余额")
  public Double getLastBankBalance()
  {
    return this.lastBankBalance;
  }
  
  public void setLastBankBalance(Double lastBankBalance)
  {
    this.lastBankBalance = lastBankBalance;
  }
  
  @ClassDiscription(name="上日交易所手续费余额")
  public Double getLastMarketFeeBalance()
  {
    return this.lastMarketFeeBalance;
  }
  
  public void setLastMarketFeeBalance(Double lastMarketFeeBalance)
  {
    this.lastMarketFeeBalance = lastMarketFeeBalance;
  }
  
  @ClassDiscription(name="上日轧差余额")
  public Double getLastOffsetBalance()
  {
    return this.lastOffsetBalance;
  }
  
  public void setLastOffsetBalance(Double lastOffsetBalance)
  {
    this.lastOffsetBalance = lastOffsetBalance;
  }
  
  public String getId()
  {
    return this.bankCode;
  }
  
  public void setPrimary(String primary) {}
}
