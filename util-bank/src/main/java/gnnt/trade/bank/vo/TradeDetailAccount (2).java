package gnnt.trade.bank.vo;

import java.io.Serializable;
import java.sql.Date;

public class TradeDetailAccount
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String BatchNo;
  public String BankId;
  public String DealId;
  public String CoBrn;
  public String TxDate;
  public String TxTime;
  public String BkSerial;
  public String CoSerial;
  public String BankAcc;
  public String FundAcc;
  public String CustName;
  public String TradeSource;
  public String BusinessCode;
  public String CurCode;
  public Date compareDate;

  public Date getCompareDate()
  {
    return this.compareDate;
  }
  public void setCompareDate(Date compareDate) {
    this.compareDate = compareDate;
  }
  public String getBatchNo() {
    return this.BatchNo;
  }
  public void setBatchNo(String batchNo) {
    this.BatchNo = batchNo;
  }
  public String getBankId() {
    return this.BankId;
  }
  public void setBankId(String bankId) {
    this.BankId = bankId;
  }
  public String getDealId() {
    return this.DealId;
  }
  public void setDealId(String dealId) {
    this.DealId = dealId;
  }
  public String getCoBrn() {
    return this.CoBrn;
  }
  public void setCoBrn(String coBrn) {
    this.CoBrn = coBrn;
  }
  public String getTxDate() {
    return this.TxDate;
  }
  public void setTxDate(String txDate) {
    this.TxDate = txDate;
  }
  public String getTxTime() {
    return this.TxTime;
  }
  public void setTxTime(String txTime) {
    this.TxTime = txTime;
  }
  public String getBkSerial() {
    return this.BkSerial;
  }
  public void setBkSerial(String bkSerial) {
    this.BkSerial = bkSerial;
  }
  public String getCoSerial() {
    return this.CoSerial;
  }
  public void setCoSerial(String coSerial) {
    this.CoSerial = coSerial;
  }
  public String getBankAcc() {
    return this.BankAcc;
  }
  public void setBankAcc(String bankAcc) {
    this.BankAcc = bankAcc;
  }
  public String getFundAcc() {
    return this.FundAcc;
  }
  public void setFundAcc(String fundAcc) {
    this.FundAcc = fundAcc;
  }
  public String getCustName() {
    return this.CustName;
  }
  public void setCustName(String custName) {
    this.CustName = custName;
  }
  public String getTradeSource() {
    return this.TradeSource;
  }
  public void setTradeSource(String tradeSource) {
    this.TradeSource = tradeSource;
  }
  public String getBusinessCode() {
    return this.BusinessCode;
  }
  public void setBusinessCode(String businessCode) {
    this.BusinessCode = businessCode;
  }
  public String getCurCode() {
    return this.CurCode;
  }
  public void setCurCode(String curCode) {
    this.CurCode = curCode;
  }
}