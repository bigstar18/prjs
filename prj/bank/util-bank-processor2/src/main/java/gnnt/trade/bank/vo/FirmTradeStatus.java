package gnnt.trade.bank.vo;

import java.io.Serializable;
import java.sql.Date;

public class FirmTradeStatus
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String BankId;
  public String DealId;
  public String CoBrn;
  public String TxDate;
  public String BankAcc;
  public String FundAcc;
  public String CustName;
  public String CurCode;
  public String Status;
  public Date compareDate;
  
  public Date getCompareDate()
  {
    return this.compareDate;
  }
  
  public void setCompareDate(Date compareDate)
  {
    this.compareDate = compareDate;
  }
  
  public String getBankId()
  {
    return this.BankId;
  }
  
  public void setBankId(String bankId)
  {
    this.BankId = bankId;
  }
  
  public String getDealId()
  {
    return this.DealId;
  }
  
  public void setDealId(String dealId)
  {
    this.DealId = dealId;
  }
  
  public String getCoBrn()
  {
    return this.CoBrn;
  }
  
  public void setCoBrn(String coBrn)
  {
    this.CoBrn = coBrn;
  }
  
  public String getTxDate()
  {
    return this.TxDate;
  }
  
  public void setTxDate(String txDate)
  {
    this.TxDate = txDate;
  }
  
  public String getBankAcc()
  {
    return this.BankAcc;
  }
  
  public void setBankAcc(String bankAcc)
  {
    this.BankAcc = bankAcc;
  }
  
  public String getFundAcc()
  {
    return this.FundAcc;
  }
  
  public void setFundAcc(String fundAcc)
  {
    this.FundAcc = fundAcc;
  }
  
  public String getCustName()
  {
    return this.CustName;
  }
  
  public void setCustName(String custName)
  {
    this.CustName = custName;
  }
  
  public String getCurCode()
  {
    return this.CurCode;
  }
  
  public void setCurCode(String curCode)
  {
    this.CurCode = curCode;
  }
  
  public String getStatus()
  {
    return this.Status;
  }
  
  public void setStatus(String status)
  {
    this.Status = status;
  }
  
  public String toString()
  {
    String s = "\n";
    StringBuffer buf = new StringBuffer();
    buf.append("**" + getClass().getName() + "**" + s);
    buf.append("BankId : " + this.BankId + s);
    buf.append("DealId : " + this.DealId + s);
    buf.append("CoBrn : " + this.CoBrn + s);
    buf.append("TxDate : " + this.TxDate + s);
    buf.append("BankAcc : " + this.BankAcc + s);
    buf.append("CustName : " + this.CustName + s);
    buf.append("CurCode : " + this.CurCode + s);
    buf.append("Status : " + this.Status + s);
    buf.append(s);
    return buf.toString();
  }
}
