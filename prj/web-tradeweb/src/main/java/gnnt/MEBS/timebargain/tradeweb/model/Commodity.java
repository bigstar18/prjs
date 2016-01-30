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
  private int SettleWay;
  
  public int getSettleWay()
  {
    return this.SettleWay;
  }
  
  public void setSettleWay(int paramInt)
  {
    this.SettleWay = paramInt;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof Commodity)) {
      return false;
    }
    Commodity localCommodity = (Commodity)paramObject;
    return this.Uni_Cmdty_Code != null ? this.Uni_Cmdty_Code.equals(localCommodity.Uni_Cmdty_Code) : localCommodity.Uni_Cmdty_Code == null;
  }
  
  public int hashCode()
  {
    return this.Uni_Cmdty_Code != null ? this.Uni_Cmdty_Code.hashCode() : 0;
  }
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
  
  public Long getBreedID()
  {
    return this.BreedID;
  }
  
  public void setBreedID(Long paramLong)
  {
    this.BreedID = paramLong;
  }
  
  public String getCommodityID()
  {
    return this.CommodityID;
  }
  
  public void setCommodityID(String paramString)
  {
    this.CommodityID = paramString;
  }
  
  public Double getContractFactor()
  {
    return this.ContractFactor;
  }
  
  public void setContractFactor(Double paramDouble)
  {
    this.ContractFactor = paramDouble;
  }
  
  public String getEndTime()
  {
    return this.EndTime;
  }
  
  public void setEndTime(String paramString)
  {
    this.EndTime = paramString;
  }
  
  public Short getFeeAlgr()
  {
    return this.FeeAlgr;
  }
  
  public void setFeeAlgr(Short paramShort)
  {
    this.FeeAlgr = paramShort;
  }
  
  public Double getFeeItem1()
  {
    return this.FeeItem1;
  }
  
  public void setFeeItem1(Double paramDouble)
  {
    this.FeeItem1 = paramDouble;
  }
  
  public Double getFeeItem2()
  {
    return this.FeeItem2;
  }
  
  public void setFeeItem2(Double paramDouble)
  {
    this.FeeItem2 = paramDouble;
  }
  
  public Double getFeeRate_B()
  {
    return this.FeeRate_B;
  }
  
  public void setFeeRate_B(Double paramDouble)
  {
    this.FeeRate_B = paramDouble;
  }
  
  public Double getFeeRate_S()
  {
    return this.FeeRate_S;
  }
  
  public void setFeeRate_S(Double paramDouble)
  {
    this.FeeRate_S = paramDouble;
  }
  
  public Short getMarginAlgr()
  {
    return this.MarginAlgr;
  }
  
  public void setMarginAlgr(Short paramShort)
  {
    this.MarginAlgr = paramShort;
  }
  
  public Double getMarginItem1()
  {
    return this.MarginItem1;
  }
  
  public void setMarginItem1(Double paramDouble)
  {
    this.MarginItem1 = paramDouble;
  }
  
  public Double getMarginItem2()
  {
    return this.MarginItem2;
  }
  
  public void setMarginItem2(Double paramDouble)
  {
    this.MarginItem2 = paramDouble;
  }
  
  public Double getMarginItem3()
  {
    return this.MarginItem3;
  }
  
  public void setMarginItem3(Double paramDouble)
  {
    this.MarginItem3 = paramDouble;
  }
  
  public Double getMarginItem4()
  {
    return this.MarginItem4;
  }
  
  public void setMarginItem4(Double paramDouble)
  {
    this.MarginItem4 = paramDouble;
  }
  
  public Double getMarginRate_B()
  {
    return this.MarginRate_B;
  }
  
  public void setMarginRate_B(Double paramDouble)
  {
    this.MarginRate_B = paramDouble;
  }
  
  public Double getMarginRate_S()
  {
    return this.MarginRate_S;
  }
  
  public void setMarginRate_S(Double paramDouble)
  {
    this.MarginRate_S = paramDouble;
  }
  
  public String getMarketCode()
  {
    return this.MarketCode;
  }
  
  public void setMarketCode(String paramString)
  {
    this.MarketCode = paramString;
  }
  
  public Date getMarketDate()
  {
    return this.MarketDate;
  }
  
  public void setMarketDate(Date paramDate)
  {
    this.MarketDate = paramDate;
  }
  
  public Double getMinPriceMove()
  {
    return this.MinPriceMove;
  }
  
  public void setMinPriceMove(Double paramDouble)
  {
    this.MinPriceMove = paramDouble;
  }
  
  public String getName()
  {
    return this.Name;
  }
  
  public void setName(String paramString)
  {
    this.Name = paramString;
  }
  
  public Date getSettleDate()
  {
    return this.SettleDate;
  }
  
  public void setSettleDate(Date paramDate)
  {
    this.SettleDate = paramDate;
  }
  
  public Date getSettleDate1()
  {
    return this.SettleDate1;
  }
  
  public void setSettleDate1(Date paramDate)
  {
    this.SettleDate1 = paramDate;
  }
  
  public Date getSettleDate2()
  {
    return this.SettleDate2;
  }
  
  public void setSettleDate2(Date paramDate)
  {
    this.SettleDate2 = paramDate;
  }
  
  public Date getSettleDate3()
  {
    return this.SettleDate3;
  }
  
  public void setSettleDate3(Date paramDate)
  {
    this.SettleDate3 = paramDate;
  }
  
  public Date getSettleDate4()
  {
    return this.SettleDate4;
  }
  
  public void setSettleDate4(Date paramDate)
  {
    this.SettleDate4 = paramDate;
  }
  
  public Long getSortID()
  {
    return this.SortID;
  }
  
  public void setSortID(Long paramLong)
  {
    this.SortID = paramLong;
  }
  
  public Short getSpreadAlgr()
  {
    return this.SpreadAlgr;
  }
  
  public void setSpreadAlgr(Short paramShort)
  {
    this.SpreadAlgr = paramShort;
  }
  
  public Double getSpreadDownLmt()
  {
    return this.SpreadDownLmt;
  }
  
  public void setSpreadDownLmt(Double paramDouble)
  {
    this.SpreadDownLmt = paramDouble;
  }
  
  public Double getSpreadUpLmt()
  {
    return this.SpreadUpLmt;
  }
  
  public void setSpreadUpLmt(Double paramDouble)
  {
    this.SpreadUpLmt = paramDouble;
  }
  
  public String getStartTime()
  {
    return this.StartTime;
  }
  
  public void setStartTime(String paramString)
  {
    this.StartTime = paramString;
  }
  
  public Short getStatus()
  {
    return this.Status;
  }
  
  public void setStatus(Short paramShort)
  {
    this.Status = paramShort;
  }
  
  public String getUni_Cmdty_Code()
  {
    return this.Uni_Cmdty_Code;
  }
  
  public void setUni_Cmdty_Code(String paramString)
  {
    this.Uni_Cmdty_Code = paramString;
  }
  
  public Double getLastPrice()
  {
    return this.LastPrice;
  }
  
  public void setLastPrice(Double paramDouble)
  {
    this.LastPrice = paramDouble;
  }
}
