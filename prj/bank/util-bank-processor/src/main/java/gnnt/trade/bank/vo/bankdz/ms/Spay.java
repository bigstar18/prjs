package gnnt.trade.bank.vo.bankdz.ms;

import java.io.Serializable;
import java.sql.Date;

public class Spay
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String sOrganId;
  private String businType;
  private String businCode;
  private String companyCode;
  private String companyName;
  private String varCode;
  private String varName;
  private String bCustAcct;
  private String bCustAcctName;
  private String MoneyType;
  private double money;
  private Date date;
  private String info;

  public String getsOrganId()
  {
    return this.sOrganId;
  }
  public void setsOrganId(String sOrganId) {
    this.sOrganId = sOrganId;
  }
  public String getBusinType() {
    return this.businType;
  }
  public void setBusinType(String businType) {
    this.businType = businType;
  }
  public String getBusinCode() {
    return this.businCode;
  }
  public void setBusinCode(String businCode) {
    this.businCode = businCode;
  }
  public String getCompanyCode() {
    return this.companyCode;
  }
  public void setCompanyCode(String companyCode) {
    this.companyCode = companyCode;
  }
  public String getCompanyName() {
    return this.companyName;
  }
  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }
  public String getVarCode() {
    return this.varCode;
  }
  public void setVarCode(String varCode) {
    this.varCode = varCode;
  }
  public String getVarName() {
    return this.varName;
  }
  public void setVarName(String varName) {
    this.varName = varName;
  }
  public String getbCustAcct() {
    return this.bCustAcct;
  }
  public void setbCustAcct(String bCustAcct) {
    this.bCustAcct = bCustAcct;
  }
  public String getbCustAcctName() {
    return this.bCustAcctName;
  }
  public void setbCustAcctName(String bCustAcctName) {
    this.bCustAcctName = bCustAcctName;
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
  public Date getDate() {
    return this.date;
  }
  public void setDate(Date date) {
    this.date = date;
  }
  public String getInfo() {
    return this.info;
  }
  public void setInfo(String info) {
    this.info = info;
  }
}