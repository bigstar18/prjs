package gnnt.trade.bank.vo;

import java.io.Serializable;
import java.util.Date;

public class FirmRightsValue
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public long id;
  public String firmid;
  public double avilableBlance;
  public double timebargainBalance;
  public double vendueBalance;
  public double zcjsBalance;
  public double bankBalance;
  public double payment;
  public double income;
  public double fee;
  public double jie;
  public double dai;
  public Date recordDate;
  
  public String toString()
  {
    String sep = "\n";
    StringBuffer sb = new StringBuffer();
    sb.append("id:" + this.id + sep);
    sb.append("firmid:" + this.firmid + sep);
    sb.append("avilableBlance:" + this.avilableBlance + sep);
    sb.append("vendueBalance:" + this.vendueBalance + sep);
    sb.append("timebargainBalance:" + this.timebargainBalance + sep);
    sb.append("zcjsBalance:" + this.zcjsBalance + sep);
    sb.append("bankBalance:" + this.bankBalance + sep);
    sb.append("payment:" + this.payment + sep);
    sb.append("income:" + this.income + sep);
    sb.append("fee:" + this.fee + sep);
    sb.append("jie:" + this.jie + sep);
    sb.append("dai:" + this.dai + sep);
    sb.append("recordDate:" + this.recordDate + sep);
    sb.append(sep);
    return sb.toString();
  }
  
  public double getAvilableBlance()
  {
    return this.avilableBlance;
  }
  
  public void setAvilableBlance(double avilableBlance)
  {
    this.avilableBlance = avilableBlance;
  }
  
  public double getBankBalance()
  {
    return this.bankBalance;
  }
  
  public void setBankBalance(double bankBalance)
  {
    this.bankBalance = bankBalance;
  }
  
  public String getFirmid()
  {
    return this.firmid;
  }
  
  public void setFirmid(String firmid)
  {
    this.firmid = firmid;
  }
  
  public long getId()
  {
    return this.id;
  }
  
  public void setId(long id)
  {
    this.id = id;
  }
  
  public Date getRecordDate()
  {
    return this.recordDate;
  }
  
  public void setRecordDate(Date recordDate)
  {
    this.recordDate = recordDate;
  }
  
  public double getTimebargainBalance()
  {
    return this.timebargainBalance;
  }
  
  public void setTimebargainBalance(double timebargainBalance)
  {
    this.timebargainBalance = timebargainBalance;
  }
  
  public double getVendueBalance()
  {
    return this.vendueBalance;
  }
  
  public void setVendueBalance(double vendueBalance)
  {
    this.vendueBalance = vendueBalance;
  }
  
  public double getZcjsBalance()
  {
    return this.zcjsBalance;
  }
  
  public void setZcjsBalance(double zcjsBalance)
  {
    this.zcjsBalance = zcjsBalance;
  }
  
  public double getFee()
  {
    return this.fee;
  }
  
  public void setFee(double fee)
  {
    this.fee = fee;
  }
  
  public double getIncome()
  {
    return this.income;
  }
  
  public void setIncome(double income)
  {
    this.income = income;
  }
  
  public double getPayment()
  {
    return this.payment;
  }
  
  public void setPayment(double payment)
  {
    this.payment = payment;
  }
  
  public double getJie()
  {
    return this.jie;
  }
  
  public void setJie(double jie)
  {
    this.jie = jie;
  }
  
  public double getDai()
  {
    return this.dai;
  }
  
  public void setDai(double dai)
  {
    this.dai = dai;
  }
}
