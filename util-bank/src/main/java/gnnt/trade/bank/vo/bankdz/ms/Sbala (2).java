package gnnt.trade.bank.vo.bankdz.ms;

import java.io.Serializable;
import java.sql.Date;

public class Sbala
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String bCustAcct;
  private String sCustAcct;
  private String MoneyType;
  private double money;
  private double inMoney;
  private double outMoney;
  private double fetchMoney;
  private Date date;

  public String getbCustAcct()
  {
    return this.bCustAcct;
  }
  public void setbCustAcct(String bCustAcct) {
    this.bCustAcct = bCustAcct;
  }
  public String getsCustAcct() {
    return this.sCustAcct;
  }
  public void setsCustAcct(String sCustAcct) {
    this.sCustAcct = sCustAcct;
  }
  public String getMoneyType() {
    return this.MoneyType;
  }
  public void setMoneyType(String moneyType) {
    this.MoneyType = moneyType;
  }
  public double getMoney() {
    return this.money;
  }
  public void setMoney(double money) {
    this.money = money;
  }
  public double getInMoney() {
    return this.inMoney;
  }
  public void setInMoney(double inMoney) {
    this.inMoney = inMoney;
  }
  public double getOutMoney() {
    return this.outMoney;
  }
  public void setOutMoney(double outMoney) {
    this.outMoney = outMoney;
  }
  public double getFetchMoney() {
    return this.fetchMoney;
  }
  public void setFetchMoney(double fetchMoney) {
    this.fetchMoney = fetchMoney;
  }
  public Date getDate() {
    return this.date;
  }
  public void setDate(Date date) {
    this.date = date;
  }
}