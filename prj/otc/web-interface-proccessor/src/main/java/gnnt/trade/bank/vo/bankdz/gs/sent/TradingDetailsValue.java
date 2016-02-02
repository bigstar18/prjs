package gnnt.trade.bank.vo.bankdz.gs.sent;

import java.io.Serializable;

public class TradingDetailsValue
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public long maketNum;
  public String account;
  public String firmId;
  public String firmName;
  public String updown;
  public double money;
  
  public String toString()
  {
    String str = "\n";
    StringBuffer sb = new StringBuffer();
    sb.append("maketNum(业务流水号)[" + this.maketNum + "]" + str);
    sb.append("account(银行账号)[" + this.account + "]" + str);
    sb.append("firmId(交易商代码)[" + this.firmId + "]" + str);
    sb.append("firmName(客户姓名)[" + this.firmName + "]" + str);
    sb.append("updown(权益增或减)[" + this.updown + "]" + str);
    sb.append("money(交收金额)[" + this.money + "]" + str);
    sb.append(str);
    return sb.toString();
  }
  
  public long getMaketNum()
  {
    return this.maketNum;
  }
  
  public void setMaketNum(long maketNum)
  {
    this.maketNum = maketNum;
  }
  
  public String getAccount()
  {
    return this.account;
  }
  
  public void setAccount(String account)
  {
    this.account = account;
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
  
  public String getUpdown()
  {
    return this.updown;
  }
  
  public void setUpdown(String updown)
  {
    this.updown = updown;
  }
  
  public double getMoney()
  {
    return this.money;
  }
  
  public void setMoney(double money)
  {
    this.money = money;
  }
}
