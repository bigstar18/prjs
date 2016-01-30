package gnnt.bank.complex.vo;

import java.io.Serializable;

public class FirmFundsBankValue
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String bankID;
  public String bankName;
  public String firmID;
  public String contact;
  public String account;
  public String account1;
  public String mainBank;
  public double lastBalance;
  public double inOutMoney;
  public double canOutFunds;
  public double frozenFunds;

  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    String str = "\n";
    sb.append("---" + getClass().getName() + "---");
    sb.append("bankID[" + this.bankID + "]" + str);
    sb.append("bankName[" + this.bankName + "]" + str);
    sb.append("firmID[" + this.firmID + "]" + str);
    sb.append("contact[" + this.contact + "]" + str);
    sb.append("account[" + this.account + "]" + str);
    sb.append("account1[" + this.account1 + "]" + str);
    sb.append("mainBank[" + this.mainBank + "]" + str);
    sb.append("lastBalance[" + this.lastBalance + "]" + str);
    sb.append("inOutMoney[" + this.inOutMoney + "]" + str);
    sb.append("canOutFunds[" + this.canOutFunds + "]" + str);
    sb.append("frozenFunds[" + this.frozenFunds + "]" + str);
    return sb.toString();
  }
}