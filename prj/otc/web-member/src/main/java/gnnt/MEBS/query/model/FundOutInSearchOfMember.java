package gnnt.MEBS.query.model;

import gnnt.MEBS.base.model.Clone;
import java.io.Serializable;

public class FundOutInSearchOfMember
  extends Clone
{
  private String firmId;
  private String firmType;
  private String firmName;
  private String bankCode;
  private String bankName;
  private Double lastcapital;
  private Double fundoutbank;
  private Double v_lastcapital;
  private Double v_balancebegin;
  private Double v_balance;
  private Double v_fundin;
  private Double v_fundout;
  private Double minriskfund;
  private Double v_canoutfund;
  private Double bank_canoutfund;
  private Double unfrozenmargen;
  
  public Double getUnfrozenmargen()
  {
    return this.unfrozenmargen;
  }
  
  public void setUnfrozenmargen(Double unfrozenmargen)
  {
    this.unfrozenmargen = unfrozenmargen;
  }
  
  public String getBankName()
  {
    return this.bankName;
  }
  
  public void setBankName(String bankName)
  {
    this.bankName = bankName;
  }
  
  public String getFirmId()
  {
    return this.firmId;
  }
  
  public void setFirmId(String firmId)
  {
    this.firmId = firmId;
  }
  
  public String getBankCode()
  {
    return this.bankCode;
  }
  
  public void setBankCode(String bankCode)
  {
    this.bankCode = bankCode;
  }
  
  public String getFirmType()
  {
    return this.firmType;
  }
  
  public void setFirmType(String firmType)
  {
    this.firmType = firmType;
  }
  
  public String getFirmName()
  {
    return this.firmName;
  }
  
  public void setFirmName(String firmName)
  {
    this.firmName = firmName;
  }
  
  public Double getLastcapital()
  {
    return this.lastcapital;
  }
  
  public void setLastcapital(Double lastcapital)
  {
    this.lastcapital = lastcapital;
  }
  
  public Double getFundoutbank()
  {
    return this.fundoutbank;
  }
  
  public void setFundoutbank(Double fundoutbank)
  {
    this.fundoutbank = fundoutbank;
  }
  
  public Double getV_lastcapital()
  {
    return this.v_lastcapital;
  }
  
  public void setV_lastcapital(Double v_lastcapital)
  {
    this.v_lastcapital = v_lastcapital;
  }
  
  public Double getV_balancebegin()
  {
    return this.v_balancebegin;
  }
  
  public void setV_balancebegin(Double v_balancebegin)
  {
    this.v_balancebegin = v_balancebegin;
  }
  
  public Double getV_balance()
  {
    return this.v_balance;
  }
  
  public void setV_balance(Double v_balance)
  {
    this.v_balance = v_balance;
  }
  
  public Double getV_fundin()
  {
    return this.v_fundin;
  }
  
  public void setV_fundin(Double v_fundin)
  {
    this.v_fundin = v_fundin;
  }
  
  public Double getV_fundout()
  {
    return this.v_fundout;
  }
  
  public void setV_fundout(Double v_fundout)
  {
    this.v_fundout = v_fundout;
  }
  
  public Double getMinriskfund()
  {
    return this.minriskfund;
  }
  
  public void setMinriskfund(Double minriskfund)
  {
    this.minriskfund = minriskfund;
  }
  
  public Double getV_canoutfund()
  {
    return this.v_canoutfund;
  }
  
  public void setV_canoutfund(Double v_canoutfund)
  {
    this.v_canoutfund = v_canoutfund;
  }
  
  public Double getBank_canoutfund()
  {
    return this.bank_canoutfund;
  }
  
  public void setBank_canoutfund(Double bank_canoutfund)
  {
    this.bank_canoutfund = bank_canoutfund;
  }
  
  public <T extends Serializable> T getId()
  {
    return null;
  }
  
  public void setPrimary(String primary) {}
}
