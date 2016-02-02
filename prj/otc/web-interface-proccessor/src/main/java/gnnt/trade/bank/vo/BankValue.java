package gnnt.trade.bank.vo;

import java.io.Serializable;

public class BankValue
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String bankID;
  public String bankName;
  public String adapterClassname;
  public int validFlag;
  public int inMoneyFlag;
  public int outMoneyFlag;
  public String beginTime;
  public String endTime;
  public int control;
  public int bankType;
  public String beleiveProcessor;
  public String inBeginTime;
  public String inEndTime;
  public String addBeginTime;
  public String addEndTime;
  public double maxOutMoney;
  public double cmaxOutMoney;
  public double mmaxOutMoney;
  public String cOutMoney;
  public String mOutMoney;
  
  public String toString()
  {
    String sep = "\n";
    StringBuilder sb = new StringBuilder();
    sb.append("**" + getClass().getName() + "**" + sep);
    sb.append("bankID:" + this.bankID + sep);
    sb.append("bankName:" + this.bankName + sep);
    sb.append("adapterClassname:" + this.adapterClassname + sep);
    sb.append("validFlag:" + this.validFlag + sep);
    sb.append("inMoneyFlag:" + this.inMoneyFlag + sep);
    sb.append("outMoneyFlag:" + this.outMoneyFlag + sep);
    sb.append("beginTime:" + this.beginTime + sep);
    sb.append("endTime:" + this.endTime + sep);
    sb.append("control:" + this.control + sep);
    sb.append("bankType:" + this.bankType + sep);
    sb.append("inBeginTime:" + this.inBeginTime + sep);
    sb.append("inEndTime:" + this.inEndTime + sep);
    sb.append("addBeginTime:" + this.addBeginTime + sep);
    sb.append("addEndTime:" + this.addEndTime + sep);
    sb.append("maxOutMoney:" + this.maxOutMoney + sep);
    
    sb.append("cmaxOutMoney:" + this.cmaxOutMoney + sep);
    sb.append("mmaxOutMoney:" + this.mmaxOutMoney + sep);
    sb.append("cOutMoney:" + this.cOutMoney + sep);
    sb.append("mOutMoney:" + this.mOutMoney + sep);
    

    sb.append(sep);
    return sb.toString();
  }
}
