package gnnt.trade.bank.vo;

import java.io.Serializable;

public class BankValue
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String bankID;
  public String bankName;
  public double maxAuditMoney;
  public double maxPerTransMoney;
  public double maxPerSglTransMoney;
  public int maxPerTransCount;
  public String adapterClassname;
  public int validFlag;
  public String beginTime;
  public String endTime;
  public int control;

  public String getBankID()
  {
    return this.bankID;
  }

  public void setBankID(String bankID)
  {
    this.bankID = bankID;
  }

  public String getBankName()
  {
    return this.bankName;
  }

  public void setBankName(String bankName)
  {
    this.bankName = bankName;
  }

  public String toString()
  {
    String sep = "\n";
    StringBuffer sb = new StringBuffer();
    sb.append("**" + getClass().getName() + "**" + sep);
    sb.append("bankID:" + this.bankID + sep);
    sb.append("bankName:" + this.bankName + sep);
    sb.append("maxAuditMoney:" + this.maxAuditMoney + sep);
    sb.append("maxPerTransMoney:" + this.maxPerTransMoney + sep);
    sb.append("maxPerTransCount:" + this.maxPerTransCount + sep);
    sb.append("adapterClassname:" + this.adapterClassname + sep);
    sb.append("validFlag:" + this.validFlag + sep);
    sb.append("beginTime:" + this.beginTime + sep);
    sb.append("endTime:" + this.endTime + sep);
    sb.append("control:" + this.control + sep);
    sb.append(sep);
    return sb.toString();
  }
}