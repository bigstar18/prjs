package gnnt.trade.bank.data.icbc.vo;

import java.io.Serializable;
import java.sql.Timestamp;

public class ProperBalanceValue
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String bankId;
  public Timestamp bdate;
  public double allMoney;
  public double gongMoney;
  public double otherMoney;
  
  public Timestamp getBdate()
  {
    return this.bdate;
  }
  
  public void setBdate(Timestamp bdate)
  {
    this.bdate = bdate;
  }
  
  public double getAllMoney()
  {
    return this.allMoney;
  }
  
  public void setAllMoney(double allMoney)
  {
    this.allMoney = allMoney;
  }
  
  public double getGongMoney()
  {
    return this.gongMoney;
  }
  
  public void setGongMoney(double gongMoney)
  {
    this.gongMoney = gongMoney;
  }
  
  public double getOtherMoney()
  {
    return this.otherMoney;
  }
  
  public void setOtherMoney(double otherMoney)
  {
    this.otherMoney = otherMoney;
  }
  
  public String getBankId()
  {
    return this.bankId;
  }
  
  public void setBankId(String bankId)
  {
    this.bankId = bankId;
  }
}
