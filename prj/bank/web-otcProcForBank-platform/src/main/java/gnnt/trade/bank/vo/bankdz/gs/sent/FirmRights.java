package gnnt.trade.bank.vo.bankdz.gs.sent;

import java.io.Serializable;

public class FirmRights
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String firmId;
  public String firmName;
  public String account;
  public double money;
  
  public String toString()
  {
    String str = "\n";
    StringBuffer sb = new StringBuffer();
    sb.append("firmId[" + this.firmId + "]" + str);
    sb.append("firmName[" + this.firmName + "]" + str);
    sb.append("account[" + this.account + "]" + str);
    sb.append("money[" + this.money + "]" + str);
    sb.append(str);
    return sb.toString();
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
  
  public String getAccount()
  {
    return this.account;
  }
  
  public void setAccount(String account)
  {
    this.account = account;
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
