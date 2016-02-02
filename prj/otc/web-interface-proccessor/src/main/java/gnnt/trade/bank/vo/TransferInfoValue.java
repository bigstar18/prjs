package gnnt.trade.bank.vo;

import java.io.Serializable;

public class TransferInfoValue
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String account;
  public String accountName;
  public String account1;
  public String account1Name;
  public String name;
  public String bankName;
  public String headOffice;
  public String province;
  public String city;
  public String mobile;
  public String email;
  public String cardType;
  public String card;
  public String isCrossline;
  public String OpenBankCode;
  
  public String toString()
  {
    String sep = "\n";
    StringBuffer sb = new StringBuffer();
    sb.append("**" + getClass().getName() + "**" + sep);
    sb.append("depositAccount:" + this.account + sep);
    sb.append("depositAccount1:" + this.account1 + sep);
    sb.append("depositName:" + this.name + sep);
    sb.append("depositBankName:" + this.name + sep);
    sb.append("depositProvince:" + this.province + sep);
    sb.append("depositMobile:" + this.mobile + sep);
    sb.append("depositEmail:" + this.email + sep);
    sb.append("cardType:" + this.cardType + sep);
    sb.append("card:" + this.card + sep);
    sb.append("headOffice:" + this.headOffice + sep);
    sb.append(sep);
    return sb.toString();
  }
}
