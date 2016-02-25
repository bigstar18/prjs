package gnnt.MEBS.vendue.manage.report;

import java.math.BigDecimal;

public class BargainIPCheck
{
  private String contractId = null;
  private String submitId = null;
  private String code = null;
  private String companyName = null;
  private String userId = null;
  private BigDecimal price = null;
  private BigDecimal tradeAmount = null;
  private String tradeDate = null;
  private String section = null;
  private String ip = null;
  
  public void setContractId(String paramString)
  {
    this.contractId = paramString;
  }
  
  public String getContractId()
  {
    return this.contractId;
  }
  
  public void setSubmitId(String paramString)
  {
    this.submitId = paramString;
  }
  
  public String getSubmitId()
  {
    return this.submitId;
  }
  
  public void setCode(String paramString)
  {
    this.code = paramString;
  }
  
  public String getCode()
  {
    return this.code;
  }
  
  public void setCompanyName(String paramString)
  {
    this.companyName = paramString;
  }
  
  public String getCompanyName()
  {
    return this.companyName;
  }
  
  public void setUserId(String paramString)
  {
    this.userId = paramString;
  }
  
  public String getUserId()
  {
    return this.userId;
  }
  
  public void setPrice(BigDecimal paramBigDecimal)
  {
    this.price = paramBigDecimal;
  }
  
  public BigDecimal getPrice()
  {
    return this.price;
  }
  
  public void setTradeAmount(BigDecimal paramBigDecimal)
  {
    this.tradeAmount = paramBigDecimal;
  }
  
  public BigDecimal getTradeAmount()
  {
    return this.tradeAmount;
  }
  
  public void setTradeDate(String paramString)
  {
    this.tradeDate = paramString;
  }
  
  public String getTradeDate()
  {
    return this.tradeDate;
  }
  
  public void setSection(String paramString)
  {
    this.section = paramString;
  }
  
  public String getSection()
  {
    return this.section;
  }
  
  public void setIp(String paramString)
  {
    this.ip = paramString;
  }
  
  public String getIp()
  {
    return this.ip;
  }
}
