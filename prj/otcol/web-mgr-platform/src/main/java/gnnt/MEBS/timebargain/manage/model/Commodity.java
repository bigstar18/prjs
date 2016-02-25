package gnnt.MEBS.timebargain.manage.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Commodity
  extends BaseObject
  implements Serializable
{
  private static final long serialVersionUID = 3690197650654049818L;
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
  private Double MarginAssure_B;
  private Double MarginAssure_S;
  private Date MarketDate;
  private Date SettleDate;
  private Date SettleDate1;
  private Date SettleDate2;
  private Date SettleDate3;
  private Date SettleDate4;
  private Date SettleDate5;
  private Double FeeItem1;
  private Double FeeItem2;
  private Double MarginItemAssure1;
  private Double MarginItemAssure2;
  private Double MarginItemAssure3;
  private Double MarginItemAssure4;
  private Double MarginItemAssure5;
  private Double MarginItem1;
  private Double MarginItem2;
  private Double MarginItem3;
  private Double MarginItem4;
  private Double MarginItem5;
  private String StartTime;
  private String EndTime;
  private Double LastPrice;
  private Short MarketFeeAlgr;
  private Double MarketFeeRate_B;
  private Double MarketFeeRate_S;
  private Double MarginItemAssure1_S;
  private Double MarginItemAssure2_S;
  private Double MarginItemAssure3_S;
  private Double MarginItemAssure4_S;
  private Double MarginItemAssure5_S;
  private Double MarginItem1_S;
  private Double MarginItem2_S;
  private Double MarginItem3_S;
  private Double MarginItem4_S;
  private Double MarginItem5_S;
  private Double TodayCloseFeeRate_B;
  private Double TodayCloseFeeRate_S;
  private Double HistoryCloseFeeRate_B;
  private Double HistoryCloseFeeRate_S;
  private Short SettleFeeAlgr;
  private Short ForceCloseFeeAlgr;
  private Double SettleFeeRate_B;
  private Double SettleFeeRate_S;
  private Double ForceCloseFeeRate_B;
  private Double ForceCloseFeeRate_S;
  private Long LimitCmdtyQty;
  protected Set TradeTime = new HashSet();
  private Date SettleProcessDate;
  private String oper;
  private Short SettleMarginAlgr;
  private Double SettleMarginRate_B;
  private Double SettleMarginRate_S;
  private Double addedTax;
  private Short marginPriceType;
  private Double lowestSettleFee;
  private Long firmCleanQty;
  private Short settleMarginAlgr_B;
  private Short settleMarginAlgr_S;
  private Short orderPrivilege;
  private Short payoutAlgr;
  private Double payoutRate;
  private Long firmMaxHoldQty;
  private String year;
  private String month;
  private Double addedTaxFactor;
  private Short orderPrivilege_B;
  private Short orderPrivilege_S;
  private Short settlePriceType;
  private Long beforeDays;
  private Double specSettlePrice;
  private Short firmMaxHoldQtyAlgr;
  private Long startPercentQty;
  private Double maxPercentLimit;
  private Long oneMaxHoldQty;
  private int minQuantityMove;
  private int minSettleMoveQty;
  private int minSettleQty;
  private String contractFactorName;
  private Double delayRecoupRate;
  private Double delayRecoupRate_S;
  private Short settleWay;
  private Short delayFeeWay;
  private Double storeRecoupRate;
  private Double maxFeeRate;
  private int delaySettlePriceType;
  private int BeforeDays_M;
  private int aheadSettlePriceType;
  private int SettleMarginType;
  private int holdDaysLimit;
  private Integer maxHoldPositionDay;
  
  public int getHoldDaysLimit()
  {
    return this.holdDaysLimit;
  }
  
  public void setHoldDaysLimit(int paramInt)
  {
    this.holdDaysLimit = paramInt;
  }
  
  public Integer getMaxHoldPositionDay()
  {
    return this.maxHoldPositionDay;
  }
  
  public void setMaxHoldPositionDay(Integer paramInteger)
  {
    this.maxHoldPositionDay = paramInteger;
  }
  
  public int getSettleMarginType()
  {
    return this.SettleMarginType;
  }
  
  public void setSettleMarginType(int paramInt)
  {
    this.SettleMarginType = paramInt;
  }
  
  public int getBeforeDays_M()
  {
    return this.BeforeDays_M;
  }
  
  public void setBeforeDays_M(int paramInt)
  {
    this.BeforeDays_M = paramInt;
  }
  
  public Double getStoreRecoupRate()
  {
    return this.storeRecoupRate;
  }
  
  public void setStoreRecoupRate(Double paramDouble)
  {
    this.storeRecoupRate = paramDouble;
  }
  
  public Short getDelayFeeWay()
  {
    return this.delayFeeWay;
  }
  
  public void setDelayFeeWay(Short paramShort)
  {
    this.delayFeeWay = paramShort;
  }
  
  public Double getDelayRecoupRate()
  {
    return this.delayRecoupRate;
  }
  
  public void setDelayRecoupRate(Double paramDouble)
  {
    this.delayRecoupRate = paramDouble;
  }
  
  public Short getSettleWay()
  {
    return this.settleWay;
  }
  
  public Double getDelayRecoupRate_S()
  {
    return this.delayRecoupRate_S;
  }
  
  public void setDelayRecoupRate_S(Double paramDouble)
  {
    this.delayRecoupRate_S = paramDouble;
  }
  
  public void setSettleWay(Short paramShort)
  {
    this.settleWay = paramShort;
  }
  
  public String getContractFactorName()
  {
    return this.contractFactorName;
  }
  
  public void setContractFactorName(String paramString)
  {
    this.contractFactorName = paramString;
  }
  
  public int getMinQuantityMove()
  {
    return this.minQuantityMove;
  }
  
  public void setMinQuantityMove(int paramInt)
  {
    this.minQuantityMove = paramInt;
  }
  
  public Long getOneMaxHoldQty()
  {
    return this.oneMaxHoldQty;
  }
  
  public void setOneMaxHoldQty(Long paramLong)
  {
    this.oneMaxHoldQty = paramLong;
  }
  
  public Short getFirmMaxHoldQtyAlgr()
  {
    return this.firmMaxHoldQtyAlgr;
  }
  
  public void setFirmMaxHoldQtyAlgr(Short paramShort)
  {
    this.firmMaxHoldQtyAlgr = paramShort;
  }
  
  public Double getMaxPercentLimit()
  {
    return this.maxPercentLimit;
  }
  
  public void setMaxPercentLimit(Double paramDouble)
  {
    this.maxPercentLimit = paramDouble;
  }
  
  public Long getStartPercentQty()
  {
    return this.startPercentQty;
  }
  
  public void setStartPercentQty(Long paramLong)
  {
    this.startPercentQty = paramLong;
  }
  
  public Long getBeforeDays()
  {
    return this.beforeDays;
  }
  
  public void setBeforeDays(Long paramLong)
  {
    this.beforeDays = paramLong;
  }
  
  public Short getSettlePriceType()
  {
    return this.settlePriceType;
  }
  
  public void setSettlePriceType(Short paramShort)
  {
    this.settlePriceType = paramShort;
  }
  
  public Double getSpecSettlePrice()
  {
    return this.specSettlePrice;
  }
  
  public void setSpecSettlePrice(Double paramDouble)
  {
    this.specSettlePrice = paramDouble;
  }
  
  public Double getAddedTaxFactor()
  {
    return this.addedTaxFactor;
  }
  
  public void setAddedTaxFactor(Double paramDouble)
  {
    this.addedTaxFactor = paramDouble;
  }
  
  public String getMonth()
  {
    return this.month;
  }
  
  public void setMonth(String paramString)
  {
    this.month = paramString;
  }
  
  public String getYear()
  {
    return this.year;
  }
  
  public void setYear(String paramString)
  {
    this.year = paramString;
  }
  
  public Long getFirmMaxHoldQty()
  {
    return this.firmMaxHoldQty;
  }
  
  public void setFirmMaxHoldQty(Long paramLong)
  {
    this.firmMaxHoldQty = paramLong;
  }
  
  public Short getOrderPrivilege()
  {
    return this.orderPrivilege;
  }
  
  public void setOrderPrivilege(Short paramShort)
  {
    this.orderPrivilege = paramShort;
  }
  
  public Short getPayoutAlgr()
  {
    return this.payoutAlgr;
  }
  
  public void setPayoutAlgr(Short paramShort)
  {
    this.payoutAlgr = paramShort;
  }
  
  public Double getPayoutRate()
  {
    return this.payoutRate;
  }
  
  public void setPayoutRate(Double paramDouble)
  {
    this.payoutRate = paramDouble;
  }
  
  public Double getAddedTax()
  {
    return this.addedTax;
  }
  
  public void setAddedTax(Double paramDouble)
  {
    this.addedTax = paramDouble;
  }
  
  public Long getFirmCleanQty()
  {
    return this.firmCleanQty;
  }
  
  public void setFirmCleanQty(Long paramLong)
  {
    this.firmCleanQty = paramLong;
  }
  
  public Double getLowestSettleFee()
  {
    return this.lowestSettleFee;
  }
  
  public void setLowestSettleFee(Double paramDouble)
  {
    this.lowestSettleFee = paramDouble;
  }
  
  public Short getMarginPriceType()
  {
    return this.marginPriceType;
  }
  
  public void setMarginPriceType(Short paramShort)
  {
    this.marginPriceType = paramShort;
  }
  
  public Short getSettleMarginAlgr()
  {
    return this.SettleMarginAlgr;
  }
  
  public void setSettleMarginAlgr(Short paramShort)
  {
    this.SettleMarginAlgr = paramShort;
  }
  
  public Double getSettleMarginRate_B()
  {
    return this.SettleMarginRate_B;
  }
  
  public void setSettleMarginRate_B(Double paramDouble)
  {
    this.SettleMarginRate_B = paramDouble;
  }
  
  public Double getSettleMarginRate_S()
  {
    return this.SettleMarginRate_S;
  }
  
  public void setSettleMarginRate_S(Double paramDouble)
  {
    this.SettleMarginRate_S = paramDouble;
  }
  
  public String getOper()
  {
    return this.oper;
  }
  
  public void setOper(String paramString)
  {
    this.oper = paramString;
  }
  
  public Date getSettleProcessDate()
  {
    return this.SettleProcessDate;
  }
  
  public void setSettleProcessDate(Date paramDate)
  {
    this.SettleProcessDate = paramDate;
  }
  
  public Set getTradeTime()
  {
    return this.TradeTime;
  }
  
  public void setTradeTime(Set paramSet)
  {
    this.TradeTime = paramSet;
  }
  
  public Long getLimitCmdtyQty()
  {
    return this.LimitCmdtyQty;
  }
  
  public void setLimitCmdtyQty(Long paramLong)
  {
    this.LimitCmdtyQty = paramLong;
  }
  
  public Short getForceCloseFeeAlgr()
  {
    return this.ForceCloseFeeAlgr;
  }
  
  public void setForceCloseFeeAlgr(Short paramShort)
  {
    this.ForceCloseFeeAlgr = paramShort;
  }
  
  public Double getForceCloseFeeRate_B()
  {
    return this.ForceCloseFeeRate_B;
  }
  
  public void setForceCloseFeeRate_B(Double paramDouble)
  {
    this.ForceCloseFeeRate_B = paramDouble;
  }
  
  public Double getForceCloseFeeRate_S()
  {
    return this.ForceCloseFeeRate_S;
  }
  
  public void setForceCloseFeeRate_S(Double paramDouble)
  {
    this.ForceCloseFeeRate_S = paramDouble;
  }
  
  public Short getSettleFeeAlgr()
  {
    return this.SettleFeeAlgr;
  }
  
  public void setSettleFeeAlgr(Short paramShort)
  {
    this.SettleFeeAlgr = paramShort;
  }
  
  public Double getSettleFeeRate_B()
  {
    return this.SettleFeeRate_B;
  }
  
  public void setSettleFeeRate_B(Double paramDouble)
  {
    this.SettleFeeRate_B = paramDouble;
  }
  
  public Double getSettleFeeRate_S()
  {
    return this.SettleFeeRate_S;
  }
  
  public void setSettleFeeRate_S(Double paramDouble)
  {
    this.SettleFeeRate_S = paramDouble;
  }
  
  public Double getHistoryCloseFeeRate_B()
  {
    return this.HistoryCloseFeeRate_B;
  }
  
  public void setHistoryCloseFeeRate_B(Double paramDouble)
  {
    this.HistoryCloseFeeRate_B = paramDouble;
  }
  
  public Double getHistoryCloseFeeRate_S()
  {
    return this.HistoryCloseFeeRate_S;
  }
  
  public void setHistoryCloseFeeRate_S(Double paramDouble)
  {
    this.HistoryCloseFeeRate_S = paramDouble;
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
  
  public Double getMarginItem1_S()
  {
    return this.MarginItem1_S;
  }
  
  public void setMarginItem1_S(Double paramDouble)
  {
    this.MarginItem1_S = paramDouble;
  }
  
  public Double getMarginItem2_S()
  {
    return this.MarginItem2_S;
  }
  
  public void setMarginItem2_S(Double paramDouble)
  {
    this.MarginItem2_S = paramDouble;
  }
  
  public Double getMarginItem3_S()
  {
    return this.MarginItem3_S;
  }
  
  public void setMarginItem3_S(Double paramDouble)
  {
    this.MarginItem3_S = paramDouble;
  }
  
  public Double getMarginItem4_S()
  {
    return this.MarginItem4_S;
  }
  
  public void setMarginItem4_S(Double paramDouble)
  {
    this.MarginItem4_S = paramDouble;
  }
  
  public Short getMarketFeeAlgr()
  {
    return this.MarketFeeAlgr;
  }
  
  public void setMarketFeeAlgr(Short paramShort)
  {
    this.MarketFeeAlgr = paramShort;
  }
  
  public Double getMarketFeeRate_B()
  {
    return this.MarketFeeRate_B;
  }
  
  public void setMarketFeeRate_B(Double paramDouble)
  {
    this.MarketFeeRate_B = paramDouble;
  }
  
  public Double getMarketFeeRate_S()
  {
    return this.MarketFeeRate_S;
  }
  
  public void setMarketFeeRate_S(Double paramDouble)
  {
    this.MarketFeeRate_S = paramDouble;
  }
  
  public Double getMarginAssure_B()
  {
    return this.MarginAssure_B;
  }
  
  public void setMarginAssure_B(Double paramDouble)
  {
    this.MarginAssure_B = paramDouble;
  }
  
  public Double getMarginAssure_S()
  {
    return this.MarginAssure_S;
  }
  
  public void setMarginAssure_S(Double paramDouble)
  {
    this.MarginAssure_S = paramDouble;
  }
  
  public Double getMarginItemAssure1()
  {
    return this.MarginItemAssure1;
  }
  
  public void setMarginItemAssure1(Double paramDouble)
  {
    this.MarginItemAssure1 = paramDouble;
  }
  
  public Double getMarginItemAssure1_S()
  {
    return this.MarginItemAssure1_S;
  }
  
  public void setMarginItemAssure1_S(Double paramDouble)
  {
    this.MarginItemAssure1_S = paramDouble;
  }
  
  public Double getMarginItemAssure2()
  {
    return this.MarginItemAssure2;
  }
  
  public void setMarginItemAssure2(Double paramDouble)
  {
    this.MarginItemAssure2 = paramDouble;
  }
  
  public Double getMarginItemAssure2_S()
  {
    return this.MarginItemAssure2_S;
  }
  
  public void setMarginItemAssure2_S(Double paramDouble)
  {
    this.MarginItemAssure2_S = paramDouble;
  }
  
  public Double getMarginItemAssure3()
  {
    return this.MarginItemAssure3;
  }
  
  public void setMarginItemAssure3(Double paramDouble)
  {
    this.MarginItemAssure3 = paramDouble;
  }
  
  public Double getMarginItemAssure3_S()
  {
    return this.MarginItemAssure3_S;
  }
  
  public void setMarginItemAssure3_S(Double paramDouble)
  {
    this.MarginItemAssure3_S = paramDouble;
  }
  
  public Double getMarginItemAssure4()
  {
    return this.MarginItemAssure4;
  }
  
  public void setMarginItemAssure4(Double paramDouble)
  {
    this.MarginItemAssure4 = paramDouble;
  }
  
  public Double getMarginItemAssure4_S()
  {
    return this.MarginItemAssure4_S;
  }
  
  public void setMarginItemAssure4_S(Double paramDouble)
  {
    this.MarginItemAssure4_S = paramDouble;
  }
  
  public Double getTodayCloseFeeRate_B()
  {
    return this.TodayCloseFeeRate_B;
  }
  
  public void setTodayCloseFeeRate_B(Double paramDouble)
  {
    this.TodayCloseFeeRate_B = paramDouble;
  }
  
  public Double getTodayCloseFeeRate_S()
  {
    return this.TodayCloseFeeRate_S;
  }
  
  public void setTodayCloseFeeRate_S(Double paramDouble)
  {
    this.TodayCloseFeeRate_S = paramDouble;
  }
  
  public Short getSettleMarginAlgr_B()
  {
    return this.settleMarginAlgr_B;
  }
  
  public void setSettleMarginAlgr_B(Short paramShort)
  {
    this.settleMarginAlgr_B = paramShort;
  }
  
  public Short getSettleMarginAlgr_S()
  {
    return this.settleMarginAlgr_S;
  }
  
  public void setSettleMarginAlgr_S(Short paramShort)
  {
    this.settleMarginAlgr_S = paramShort;
  }
  
  public Double getMarginItem5()
  {
    return this.MarginItem5;
  }
  
  public void setMarginItem5(Double paramDouble)
  {
    this.MarginItem5 = paramDouble;
  }
  
  public Double getMarginItem5_S()
  {
    return this.MarginItem5_S;
  }
  
  public void setMarginItem5_S(Double paramDouble)
  {
    this.MarginItem5_S = paramDouble;
  }
  
  public Double getMarginItemAssure5()
  {
    return this.MarginItemAssure5;
  }
  
  public void setMarginItemAssure5(Double paramDouble)
  {
    this.MarginItemAssure5 = paramDouble;
  }
  
  public Double getMarginItemAssure5_S()
  {
    return this.MarginItemAssure5_S;
  }
  
  public void setMarginItemAssure5_S(Double paramDouble)
  {
    this.MarginItemAssure5_S = paramDouble;
  }
  
  public Date getSettleDate5()
  {
    return this.SettleDate5;
  }
  
  public void setSettleDate5(Date paramDate)
  {
    this.SettleDate5 = paramDate;
  }
  
  public Short getOrderPrivilege_B()
  {
    return this.orderPrivilege_B;
  }
  
  public void setOrderPrivilege_B(Short paramShort)
  {
    this.orderPrivilege_B = paramShort;
  }
  
  public Short getOrderPrivilege_S()
  {
    return this.orderPrivilege_S;
  }
  
  public void setOrderPrivilege_S(Short paramShort)
  {
    this.orderPrivilege_S = paramShort;
  }
  
  public Double getMaxFeeRate()
  {
    return this.maxFeeRate;
  }
  
  public void setMaxFeeRate(Double paramDouble)
  {
    this.maxFeeRate = paramDouble;
  }
  
  public int getMinSettleMoveQty()
  {
    return this.minSettleMoveQty;
  }
  
  public void setMinSettleMoveQty(int paramInt)
  {
    this.minSettleMoveQty = paramInt;
  }
  
  public int getMinSettleQty()
  {
    return this.minSettleQty;
  }
  
  public void setMinSettleQty(int paramInt)
  {
    this.minSettleQty = paramInt;
  }
  
  public int getDelaySettlePriceType()
  {
    return this.delaySettlePriceType;
  }
  
  public void setDelaySettlePriceType(int paramInt)
  {
    this.delaySettlePriceType = paramInt;
  }
  
  public int getAheadSettlePriceType()
  {
    return this.aheadSettlePriceType;
  }
  
  public void setAheadSettlePriceType(int paramInt)
  {
    this.aheadSettlePriceType = paramInt;
  }
}
