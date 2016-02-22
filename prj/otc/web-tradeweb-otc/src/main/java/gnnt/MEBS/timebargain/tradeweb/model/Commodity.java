package gnnt.MEBS.timebargain.tradeweb.model;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Commodity
  extends BaseObject
  implements Serializable
{
  private static final long serialVersionUID = 3690197650654049810L;
  private String Uni_Cmdty_Code;
  private String CommodityID;
  private String MarketCode;
  private String Name;
  private Long SortID;
  private Short Status;
  private Double ContractFactor;
  private Double MinPriceMove;
  private Long BreedID;
  private Short SpreadAlgr;
  private Double SpreadUpLmt;
  private Double SpreadDownLmt;
  private Short FeeAlgr;
  private Double FeeRate_B;
  private Double FeeRate_S;
  private Short MarginAlgr;
  private Double MarginRate_B;
  private Double MarginRate_S;
  private Date MarketDate;
  private Date SettleDate;
  private Date SettleDate1;
  private Date SettleDate2;
  private Date SettleDate3;
  private Date SettleDate4;
  private Double FeeItem1;
  private Double FeeItem2;
  private Double MarginItem1;
  private Double MarginItem2;
  private Double MarginItem3;
  private Double MarginItem4;
  private String StartTime;
  private String EndTime;
  private Double LastPrice;
  
  public boolean equals(Object o)
  {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Commodity)) {
      return false;
    }
    Commodity m = (Commodity)o;
    
    return this.Uni_Cmdty_Code != null ? this.Uni_Cmdty_Code.equals(m.Uni_Cmdty_Code) : 
      m.Uni_Cmdty_Code == null;
  }
  
  public int hashCode()
  {
    return this.Uni_Cmdty_Code != null ? this.Uni_Cmdty_Code.hashCode() : 0;
  }
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this, 
      ToStringStyle.MULTI_LINE_STYLE);
  }
  
  public Long getBreedID()
  {
    return this.BreedID;
  }
  
  public void setBreedID(Long breedID)
  {
    this.BreedID = breedID;
  }
  
  public String getCommodityID()
  {
    return this.CommodityID;
  }
  
  public void setCommodityID(String commodityID)
  {
    this.CommodityID = commodityID;
  }
  
  public Double getContractFactor()
  {
    return this.ContractFactor;
  }
  
  public void setContractFactor(Double contractFactor)
  {
    this.ContractFactor = contractFactor;
  }
  
  public String getEndTime()
  {
    return this.EndTime;
  }
  
  public void setEndTime(String endTime)
  {
    this.EndTime = endTime;
  }
  
  public Short getFeeAlgr()
  {
    return this.FeeAlgr;
  }
  
  public void setFeeAlgr(Short feeAlgr)
  {
    this.FeeAlgr = feeAlgr;
  }
  
  public Double getFeeItem1()
  {
    return this.FeeItem1;
  }
  
  public void setFeeItem1(Double feeItem1)
  {
    this.FeeItem1 = feeItem1;
  }
  
  public Double getFeeItem2()
  {
    return this.FeeItem2;
  }
  
  public void setFeeItem2(Double feeItem2)
  {
    this.FeeItem2 = feeItem2;
  }
  
  public Double getFeeRate_B()
  {
    return this.FeeRate_B;
  }
  
  public void setFeeRate_B(Double feeRate_B)
  {
    this.FeeRate_B = feeRate_B;
  }
  
  public Double getFeeRate_S()
  {
    return this.FeeRate_S;
  }
  
  public void setFeeRate_S(Double feeRate_S)
  {
    this.FeeRate_S = feeRate_S;
  }
  
  public Short getMarginAlgr()
  {
    return this.MarginAlgr;
  }
  
  public void setMarginAlgr(Short marginAlgr)
  {
    this.MarginAlgr = marginAlgr;
  }
  
  public Double getMarginItem1()
  {
    return this.MarginItem1;
  }
  
  public void setMarginItem1(Double marginItem1)
  {
    this.MarginItem1 = marginItem1;
  }
  
  public Double getMarginItem2()
  {
    return this.MarginItem2;
  }
  
  public void setMarginItem2(Double marginItem2)
  {
    this.MarginItem2 = marginItem2;
  }
  
  public Double getMarginItem3()
  {
    return this.MarginItem3;
  }
  
  public void setMarginItem3(Double marginItem3)
  {
    this.MarginItem3 = marginItem3;
  }
  
  public Double getMarginItem4()
  {
    return this.MarginItem4;
  }
  
  public void setMarginItem4(Double marginItem4)
  {
    this.MarginItem4 = marginItem4;
  }
  
  public Double getMarginRate_B()
  {
    return this.MarginRate_B;
  }
  
  public void setMarginRate_B(Double marginRate_B)
  {
    this.MarginRate_B = marginRate_B;
  }
  
  public Double getMarginRate_S()
  {
    return this.MarginRate_S;
  }
  
  public void setMarginRate_S(Double marginRate_S)
  {
    this.MarginRate_S = marginRate_S;
  }
  
  public String getMarketCode()
  {
    return this.MarketCode;
  }
  
  public void setMarketCode(String marketCode)
  {
    this.MarketCode = marketCode;
  }
  
  public Date getMarketDate()
  {
    return this.MarketDate;
  }
  
  public void setMarketDate(Date marketDate)
  {
    this.MarketDate = marketDate;
  }
  
  public Double getMinPriceMove()
  {
    return this.MinPriceMove;
  }
  
  public void setMinPriceMove(Double minPriceMove)
  {
    this.MinPriceMove = minPriceMove;
  }
  
  public String getName()
  {
    return this.Name;
  }
  
  public void setName(String name)
  {
    this.Name = name;
  }
  
  public Date getSettleDate()
  {
    return this.SettleDate;
  }
  
  public void setSettleDate(Date settleDate)
  {
    this.SettleDate = settleDate;
  }
  
  public Date getSettleDate1()
  {
    return this.SettleDate1;
  }
  
  public void setSettleDate1(Date settleDate1)
  {
    this.SettleDate1 = settleDate1;
  }
  
  public Date getSettleDate2()
  {
    return this.SettleDate2;
  }
  
  public void setSettleDate2(Date settleDate2)
  {
    this.SettleDate2 = settleDate2;
  }
  
  public Date getSettleDate3()
  {
    return this.SettleDate3;
  }
  
  public void setSettleDate3(Date settleDate3)
  {
    this.SettleDate3 = settleDate3;
  }
  
  public Date getSettleDate4()
  {
    return this.SettleDate4;
  }
  
  public void setSettleDate4(Date settleDate4)
  {
    this.SettleDate4 = settleDate4;
  }
  
  public Long getSortID()
  {
    return this.SortID;
  }
  
  public void setSortID(Long sortID)
  {
    this.SortID = sortID;
  }
  
  public Short getSpreadAlgr()
  {
    return this.SpreadAlgr;
  }
  
  public void setSpreadAlgr(Short spreadAlgr)
  {
    this.SpreadAlgr = spreadAlgr;
  }
  
  public Double getSpreadDownLmt()
  {
    return this.SpreadDownLmt;
  }
  
  public void setSpreadDownLmt(Double spreadDownLmt)
  {
    this.SpreadDownLmt = spreadDownLmt;
  }
  
  public Double getSpreadUpLmt()
  {
    return this.SpreadUpLmt;
  }
  
  public void setSpreadUpLmt(Double spreadUpLmt)
  {
    this.SpreadUpLmt = spreadUpLmt;
  }
  
  public String getStartTime()
  {
    return this.StartTime;
  }
  
  public void setStartTime(String startTime)
  {
    this.StartTime = startTime;
  }
  
  public Short getStatus()
  {
    return this.Status;
  }
  
  public void setStatus(Short status)
  {
    this.Status = status;
  }
  
  public String getUni_Cmdty_Code()
  {
    return this.Uni_Cmdty_Code;
  }
  
  public void setUni_Cmdty_Code(String uni_Cmdty_Code)
  {
    this.Uni_Cmdty_Code = uni_Cmdty_Code;
  }
  
  public Double getLastPrice()
  {
    return this.LastPrice;
  }
  
  public void setLastPrice(Double lastPrice)
  {
    this.LastPrice = lastPrice;
  }
}
