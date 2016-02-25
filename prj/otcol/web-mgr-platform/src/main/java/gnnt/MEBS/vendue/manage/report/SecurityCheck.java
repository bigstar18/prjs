package gnnt.MEBS.vendue.manage.report;

import java.math.BigDecimal;

public class SecurityCheck
{
  private String tradeNo = null;
  private String usercode = null;
  private String companyName = null;
  private String bank = null;
  private BigDecimal money = null;
  private BigDecimal totalMoney = null;
  
  public void setTradeNo(String paramString)
  {
    this.tradeNo = paramString;
  }
  
  public String getTradeNo()
  {
    return this.tradeNo;
  }
  
  public void setUsercode(String paramString)
  {
    this.usercode = paramString;
  }
  
  public String getUsercode()
  {
    return this.usercode;
  }
  
  public void setCompanyName(String paramString)
  {
    this.companyName = paramString;
  }
  
  public String getCompanyName()
  {
    return this.companyName;
  }
  
  public void setBank(String paramString)
  {
    this.bank = paramString;
  }
  
  public String getBank()
  {
    return this.bank;
  }
  
  public void setMoney(BigDecimal paramBigDecimal)
  {
    this.money = paramBigDecimal;
  }
  
  public BigDecimal getMoney()
  {
    return this.money;
  }
  
  public void setTotalMoney(BigDecimal paramBigDecimal)
  {
    this.totalMoney = paramBigDecimal;
  }
  
  public BigDecimal getTotalMoney()
  {
    return this.totalMoney;
  }
}
