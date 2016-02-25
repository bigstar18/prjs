package gnnt.bank.platform.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BankSetBalance
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Date b_date;
  private String bankCode;
  private BigDecimal lastRights;
  private BigDecimal outinMoney;
  private BigDecimal offsetBalance;
  private BigDecimal todayRights;
  private String bankName;
  
  public String getBankName()
  {
    return this.bankName;
  }
  
  public void setBankName(String bankName)
  {
    this.bankName = bankName;
  }
  
  public Date getB_date()
  {
    return this.b_date;
  }
  
  public void setB_date(Date bDate)
  {
    this.b_date = bDate;
  }
  
  public String getBankCode()
  {
    return this.bankCode;
  }
  
  public void setBankCode(String bankCode)
  {
    this.bankCode = bankCode;
  }
  
  public BigDecimal getLastRights()
  {
    return this.lastRights;
  }
  
  public void setLastRights(BigDecimal lastRights)
  {
    this.lastRights = lastRights;
  }
  
  public BigDecimal getOutinMoney()
  {
    return this.outinMoney;
  }
  
  public void setOutinMoney(BigDecimal outinMoney)
  {
    this.outinMoney = outinMoney;
  }
  
  public BigDecimal getOffsetBalance()
  {
    return this.offsetBalance;
  }
  
  public void setOffsetBalance(BigDecimal offsetBalance)
  {
    this.offsetBalance = offsetBalance;
  }
  
  public BigDecimal getTodayRights()
  {
    return this.todayRights;
  }
  
  public void setTodayRights(BigDecimal todayRights)
  {
    this.todayRights = todayRights;
  }
  
  public static long getSerialversionuid()
  {
    return 1L;
  }
}
