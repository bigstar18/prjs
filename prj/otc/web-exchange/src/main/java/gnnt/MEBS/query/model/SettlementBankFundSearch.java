package gnnt.MEBS.query.model;

import gnnt.MEBS.base.model.Clone;
import java.io.Serializable;
import java.util.Date;

public class SettlementBankFundSearch
  extends Clone
{
  private Date b_date;
  private String firmId;
  private String firmName;
  private String bankName;
  private String bankCode;
  private Double fundio;
  private Double closepl;
  private Double holdpl;
  private Double tradefee;
  private Double delayfee;
  private Double capital;
  private Double lastcapital;
  private String firmType;
  
  public Double getClosepl()
  {
    return this.closepl;
  }
  
  public void setClosepl(Double closepl)
  {
    this.closepl = closepl;
  }
  
  public Double getHoldpl()
  {
    return this.holdpl;
  }
  
  public void setHoldpl(Double holdpl)
  {
    this.holdpl = holdpl;
  }
  
  public Double getTradefee()
  {
    return this.tradefee;
  }
  
  public void setTradefee(Double tradefee)
  {
    this.tradefee = tradefee;
  }
  
  public Double getDelayfee()
  {
    return this.delayfee;
  }
  
  public void setDelayfee(Double delayfee)
  {
    this.delayfee = delayfee;
  }
  
  public Double getLastcapital()
  {
    return this.lastcapital;
  }
  
  public void setLastcapital(Double lastcapital)
  {
    this.lastcapital = lastcapital;
  }
  
  public String getFirmType()
  {
    return this.firmType;
  }
  
  public void setFirmType(String firmType)
  {
    this.firmType = firmType;
  }
  
  public Date getB_date()
  {
    return this.b_date;
  }
  
  public void setB_date(Date b_date)
  {
    this.b_date = b_date;
  }
  
  public String getBankName()
  {
    return this.bankName;
  }
  
  public void setBankName(String bankName)
  {
    this.bankName = bankName;
  }
  
  public String getBankCode()
  {
    return this.bankCode;
  }
  
  public void setBankCode(String bankCode)
  {
    this.bankCode = bankCode;
  }
  
  public Double getFundio()
  {
    return this.fundio;
  }
  
  public void setFundio(Double fundio)
  {
    this.fundio = fundio;
  }
  
  public Double getCapital()
  {
    return this.capital;
  }
  
  public void setCapital(Double capital)
  {
    this.capital = capital;
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
  
  public <T extends Serializable> T getId()
  {
    return null;
  }
  
  public void setPrimary(String primary) {}
  
  public SettlementBankFundSearch() {}
  
  public SettlementBankFundSearch(Date b_date, String firmId, String firmName, String bankName, String bankCode, Double fundio, Double closepl, Double holdpl, Double tradefee, Double delayfee, Double capital, Double lastcapital, String firmType)
  {
    this.b_date = b_date;
    this.firmId = firmId;
    this.firmName = firmName;
    this.bankName = bankName;
    this.bankCode = bankCode;
    this.fundio = fundio;
    this.closepl = closepl;
    this.holdpl = holdpl;
    this.tradefee = tradefee;
    this.delayfee = delayfee;
    this.capital = capital;
    this.lastcapital = lastcapital;
    this.firmType = firmType;
  }
}
