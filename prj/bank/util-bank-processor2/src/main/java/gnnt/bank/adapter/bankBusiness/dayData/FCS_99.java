package gnnt.bank.adapter.bankBusiness.dayData;

import java.io.Serializable;

public class FCS_99
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String tradeTime;
  public String busiNum;
  public String batchNum;
  public String fileName;
  public String rspCode;
  public String errorDescription;
  
  public String getTradeTime()
  {
    return this.tradeTime;
  }
  
  public void setTradeTime(String tradeTime)
  {
    this.tradeTime = tradeTime;
  }
  
  public String getBusiNum()
  {
    return this.busiNum;
  }
  
  public void setBusiNum(String busiNum)
  {
    this.busiNum = busiNum;
  }
  
  public String getBatchNum()
  {
    return this.batchNum;
  }
  
  public void setBatchNum(String batchNum)
  {
    this.batchNum = batchNum;
  }
  
  public String getErrorDescription()
  {
    return this.errorDescription;
  }
  
  public void setErrorDescription(String errorDescription)
  {
    this.errorDescription = errorDescription;
  }
  
  public String getFileName()
  {
    return this.fileName;
  }
  
  public void setFileName(String fileName)
  {
    this.fileName = fileName;
  }
  
  public String getRspCode()
  {
    return this.rspCode;
  }
  
  public void setRspCode(String rspCode)
  {
    this.rspCode = rspCode;
  }
}
