package gnnt.MEBS.zcjs.model;

import gnnt.MEBS.base.model.Clone;
import java.util.Date;

public class FundFlow
  extends Clone
{
  private long fundFlowID;
  private String firmID;
  private String oprCode;
  private String contractNo;
  private String commodityID;
  private double amount;
  private double balance;
  private double appendAmount;
  private long voucherNo;
  private Date createTime;
  private Date b_Date;
  
  public long getFundFlowID()
  {
    return this.fundFlowID;
  }
  
  public void setFundFlowID(long paramLong)
  {
    this.fundFlowID = paramLong;
  }
  
  public String getFirmID()
  {
    return this.firmID;
  }
  
  public void setFirmID(String paramString)
  {
    this.firmID = paramString;
  }
  
  public String getOprCode()
  {
    return this.oprCode;
  }
  
  public void setOprCode(String paramString)
  {
    this.oprCode = paramString;
  }
  
  public String getContractNo()
  {
    return this.contractNo;
  }
  
  public void setContractNo(String paramString)
  {
    this.contractNo = paramString;
  }
  
  public String getCommodityID()
  {
    return this.commodityID;
  }
  
  public void setCommodityID(String paramString)
  {
    this.commodityID = paramString;
  }
  
  public double getAmount()
  {
    return this.amount;
  }
  
  public void setAmount(double paramDouble)
  {
    this.amount = paramDouble;
  }
  
  public double getBalance()
  {
    return this.balance;
  }
  
  public void setBalance(double paramDouble)
  {
    this.balance = paramDouble;
  }
  
  public double getAppendAmount()
  {
    return this.appendAmount;
  }
  
  public void setAppendAmount(double paramDouble)
  {
    this.appendAmount = paramDouble;
  }
  
  public long getVoucherNo()
  {
    return this.voucherNo;
  }
  
  public void setVoucherNo(long paramLong)
  {
    this.voucherNo = paramLong;
  }
  
  public Date getCreateTime()
  {
    return this.createTime;
  }
  
  public void setCreateTime(Date paramDate)
  {
    this.createTime = paramDate;
  }
  
  public Date getB_Date()
  {
    return this.b_Date;
  }
  
  public void setB_Date(Date paramDate)
  {
    this.b_Date = paramDate;
  }
}
