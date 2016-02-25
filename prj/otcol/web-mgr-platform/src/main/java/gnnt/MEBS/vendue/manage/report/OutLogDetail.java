package gnnt.MEBS.vendue.manage.report;

import java.math.BigDecimal;

public class OutLogDetail
{
  private String outId;
  private String contractId;
  private String tradeDate;
  private String code;
  private BigDecimal amount;
  private String userCode;
  private String sellerName;
  private String wareHouse;
  private BigDecimal tradeAmount;
  private BigDecimal price;
  private String buyerName;
  
  public String getBuyerName()
  {
    return this.buyerName;
  }
  
  public void setBuyerName(String paramString)
  {
    this.buyerName = paramString;
  }
  
  public BigDecimal getPrice()
  {
    return this.price;
  }
  
  public void setPrice(BigDecimal paramBigDecimal)
  {
    this.price = paramBigDecimal;
  }
  
  public String getSellerName()
  {
    return this.sellerName;
  }
  
  public void setSellerName(String paramString)
  {
    this.sellerName = paramString;
  }
  
  public BigDecimal getTradeAmount()
  {
    return this.tradeAmount;
  }
  
  public void setTradeAmount(BigDecimal paramBigDecimal)
  {
    this.tradeAmount = paramBigDecimal;
  }
  
  public String getWareHouse()
  {
    return this.wareHouse;
  }
  
  public void setWareHouse(String paramString)
  {
    this.wareHouse = paramString;
  }
  
  public BigDecimal getAmount()
  {
    return this.amount;
  }
  
  public void setAmount(BigDecimal paramBigDecimal)
  {
    this.amount = paramBigDecimal;
  }
  
  public String getCode()
  {
    return this.code;
  }
  
  public void setCode(String paramString)
  {
    this.code = paramString;
  }
  
  public String getContractId()
  {
    return this.contractId;
  }
  
  public void setContractId(String paramString)
  {
    this.contractId = paramString;
  }
  
  public String getOutId()
  {
    return this.outId;
  }
  
  public void setOutId(String paramString)
  {
    this.outId = paramString;
  }
  
  public String getTradeDate()
  {
    return this.tradeDate;
  }
  
  public void setTradeDate(String paramString)
  {
    this.tradeDate = paramString;
  }
  
  public String getUserCode()
  {
    return this.userCode;
  }
  
  public void setUserCode(String paramString)
  {
    this.userCode = paramString;
  }
}
