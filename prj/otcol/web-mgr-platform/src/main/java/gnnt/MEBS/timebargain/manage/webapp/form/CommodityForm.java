package gnnt.MEBS.timebargain.manage.webapp.form;

import gnnt.MEBS.timebargain.manage.model.TradeTime;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class CommodityForm
  extends BaseForm
  implements Serializable
{
  private static final long serialVersionUID = 3690197650654049818L;
  private String Uni_Cmdty_Code;
  private String CommodityID;
  private String MarketCode;
  private String Name;
  private String SortID;
  private String Status;
  private String ContractFactor;
  private String MinPriceMove;
  private String BreedID;
  private String SpreadAlgr;
  private String SpreadUpLmt;
  private String SpreadDownLmt;
  private String FeeAlgr;
  private String FeeRate_B;
  private String FeeRate_S;
  private String MarginAlgr;
  private String MarginRate_B;
  private String MarginRate_S;
  private String MarginAssure_B;
  private String MarginAssure_S;
  private String MarketDate;
  private String SettleDate;
  private String SettleDate1;
  private String SettleDate2;
  private String SettleDate3;
  private String SettleDate4;
  private String SettleDate5;
  private String FeeItem1;
  private String FeeItem2;
  private String MarginItem1;
  private String MarginItem2;
  private String MarginItem3;
  private String MarginItem4;
  private String MarginItem5;
  private String MarginItemAssure1;
  private String MarginItemAssure2;
  private String MarginItemAssure3;
  private String MarginItemAssure4;
  private String MarginItemAssure5;
  private String StartTime;
  private String EndTime;
  private String LastPrice;
  private String MarketFeeAlgr;
  private String MarketFeeRate_B;
  private String MarketFeeRate_S;
  private String MarginItemAssure1_S;
  private String MarginItemAssure2_S;
  private String MarginItemAssure3_S;
  private String MarginItemAssure4_S;
  private String MarginItemAssure5_S;
  private String MarginItem1_S;
  private String MarginItem2_S;
  private String MarginItem3_S;
  private String MarginItem4_S;
  private String MarginItem5_S;
  private String crud = "";
  private String TodayCloseFeeRate_B;
  private String TodayCloseFeeRate_S;
  private String HistoryCloseFeeRate_B;
  private String HistoryCloseFeeRate_S;
  private String SettleFeeAlgr;
  private String ForceCloseFeeAlgr;
  private String SettleFeeRate_B;
  private String SettleFeeRate_S;
  private String ForceCloseFeeRate_B;
  private String ForceCloseFeeRate_S;
  private String LimitCmdtyQty;
  protected transient Set TradeTime = new HashSet();
  private String SettleProcessDate;
  private String oper;
  private String SettleMarginAlgr;
  private String SettleMarginRate_B;
  private String SettleMarginRate_S;
  private String addedTax;
  private String marginPriceType;
  private String lowestSettleFee;
  private String firmCleanQty;
  private String settleMarginAlgr_B;
  private String settleMarginAlgr_S;
  private String orderPrivilege;
  private String payoutAlgr;
  private String payoutRate;
  private String firmMaxHoldQty;
  private String year;
  private String month;
  private String addedTaxFactor;
  private String orderPrivilege_B;
  private String orderPrivilege_S;
  private String settlePriceType;
  private String beforeDays;
  private String specSettlePrice;
  private String firmMaxHoldQtyAlgr;
  private String startPercentQty;
  private String maxPercentLimit;
  private String oneMaxHoldQty;
  private String minQuantityMove;
  private String minSettleMoveQty;
  private String minSettleQty;
  private String contractFactorName;
  private String delayRecoupRate;
  private String delayRecoupRate_S;
  private String settleWay;
  private String delayFeeWay;
  private String storeRecoupRate;
  private String maxFeeRate;
  private int delaySettlePriceType;
  private String BeforeDays_M;
  private int aheadSettlePriceType;
  private String SettleMarginType;
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
  
  public String getSettleMarginType()
  {
    return this.SettleMarginType;
  }
  
  public void setSettleMarginType(String paramString)
  {
    this.SettleMarginType = paramString;
    System.out.println("Form:   " + this.SettleMarginType);
  }
  
  public String getBeforeDays_M()
  {
    return this.BeforeDays_M;
  }
  
  public void setBeforeDays_M(String paramString)
  {
    this.BeforeDays_M = paramString;
  }
  
  public String getMaxFeeRate()
  {
    return this.maxFeeRate;
  }
  
  public void setMaxFeeRate(String paramString)
  {
    this.maxFeeRate = paramString;
  }
  
  public String getDelayFeeWay()
  {
    return this.delayFeeWay;
  }
  
  public void setDelayFeeWay(String paramString)
  {
    this.delayFeeWay = paramString;
  }
  
  public String getDelayRecoupRate()
  {
    return this.delayRecoupRate;
  }
  
  public void setDelayRecoupRate(String paramString)
  {
    this.delayRecoupRate = paramString;
  }
  
  public String getSettleWay()
  {
    return this.settleWay;
  }
  
  public String getDelayRecoupRate_S()
  {
    return this.delayRecoupRate_S;
  }
  
  public void setDelayRecoupRate_S(String paramString)
  {
    this.delayRecoupRate_S = paramString;
  }
  
  public void setSettleWay(String paramString)
  {
    this.settleWay = paramString;
  }
  
  public String getOneMaxHoldQty()
  {
    return this.oneMaxHoldQty;
  }
  
  public void setOneMaxHoldQty(String paramString)
  {
    this.oneMaxHoldQty = paramString;
  }
  
  public String getFirmMaxHoldQtyAlgr()
  {
    return this.firmMaxHoldQtyAlgr;
  }
  
  public void setFirmMaxHoldQtyAlgr(String paramString)
  {
    this.firmMaxHoldQtyAlgr = paramString;
  }
  
  public String getMaxPercentLimit()
  {
    return this.maxPercentLimit;
  }
  
  public void setMaxPercentLimit(String paramString)
  {
    this.maxPercentLimit = paramString;
  }
  
  public String getStartPercentQty()
  {
    return this.startPercentQty;
  }
  
  public void setStartPercentQty(String paramString)
  {
    this.startPercentQty = paramString;
  }
  
  public String getAddedTaxFactor()
  {
    return this.addedTaxFactor;
  }
  
  public void setAddedTaxFactor(String paramString)
  {
    this.addedTaxFactor = paramString;
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
  
  public String getFirmMaxHoldQty()
  {
    return this.firmMaxHoldQty;
  }
  
  public void setFirmMaxHoldQty(String paramString)
  {
    this.firmMaxHoldQty = paramString;
  }
  
  public String getAddedTax()
  {
    return this.addedTax;
  }
  
  public void setAddedTax(String paramString)
  {
    this.addedTax = paramString;
  }
  
  public String getFirmCleanQty()
  {
    return this.firmCleanQty;
  }
  
  public void setFirmCleanQty(String paramString)
  {
    this.firmCleanQty = paramString;
  }
  
  public String getLowestSettleFee()
  {
    return this.lowestSettleFee;
  }
  
  public void setLowestSettleFee(String paramString)
  {
    this.lowestSettleFee = paramString;
  }
  
  public String getMarginPriceType()
  {
    return this.marginPriceType;
  }
  
  public void setMarginPriceType(String paramString)
  {
    this.marginPriceType = paramString;
  }
  
  public String getSettleMarginAlgr()
  {
    return this.SettleMarginAlgr;
  }
  
  public void setSettleMarginAlgr(String paramString)
  {
    this.SettleMarginAlgr = paramString;
  }
  
  public String getSettleMarginRate_B()
  {
    return this.SettleMarginRate_B;
  }
  
  public void setSettleMarginRate_B(String paramString)
  {
    this.SettleMarginRate_B = paramString;
  }
  
  public String getSettleMarginRate_S()
  {
    return this.SettleMarginRate_S;
  }
  
  public void setSettleMarginRate_S(String paramString)
  {
    this.SettleMarginRate_S = paramString;
  }
  
  public String getOper()
  {
    return this.oper;
  }
  
  public void setOper(String paramString)
  {
    this.oper = paramString;
  }
  
  public String getSettleProcessDate()
  {
    return this.SettleProcessDate;
  }
  
  public void setSettleProcessDate(String paramString)
  {
    this.SettleProcessDate = paramString;
  }
  
  public Set getTradeTime()
  {
    return this.TradeTime;
  }
  
  public void setTradeTime(Set paramSet)
  {
    this.TradeTime = paramSet;
  }
  
  public String getLimitCmdtyQty()
  {
    return this.LimitCmdtyQty;
  }
  
  public void setLimitCmdtyQty(String paramString)
  {
    this.LimitCmdtyQty = paramString;
  }
  
  public String getForceCloseFeeAlgr()
  {
    return this.ForceCloseFeeAlgr;
  }
  
  public void setForceCloseFeeAlgr(String paramString)
  {
    this.ForceCloseFeeAlgr = paramString;
  }
  
  public String getForceCloseFeeRate_B()
  {
    return this.ForceCloseFeeRate_B;
  }
  
  public void setForceCloseFeeRate_B(String paramString)
  {
    this.ForceCloseFeeRate_B = paramString;
  }
  
  public String getForceCloseFeeRate_S()
  {
    return this.ForceCloseFeeRate_S;
  }
  
  public void setForceCloseFeeRate_S(String paramString)
  {
    this.ForceCloseFeeRate_S = paramString;
  }
  
  public String getSettleFeeAlgr()
  {
    return this.SettleFeeAlgr;
  }
  
  public void setSettleFeeAlgr(String paramString)
  {
    this.SettleFeeAlgr = paramString;
  }
  
  public String getSettleFeeRate_B()
  {
    return this.SettleFeeRate_B;
  }
  
  public void setSettleFeeRate_B(String paramString)
  {
    this.SettleFeeRate_B = paramString;
  }
  
  public String getSettleFeeRate_S()
  {
    return this.SettleFeeRate_S;
  }
  
  public void setSettleFeeRate_S(String paramString)
  {
    this.SettleFeeRate_S = paramString;
  }
  
  public String getTodayCloseFeeRate_B()
  {
    return this.TodayCloseFeeRate_B;
  }
  
  public void setTodayCloseFeeRate_B(String paramString)
  {
    this.TodayCloseFeeRate_B = paramString;
  }
  
  public String getTodayCloseFeeRate_S()
  {
    return this.TodayCloseFeeRate_S;
  }
  
  public void setTodayCloseFeeRate_S(String paramString)
  {
    this.TodayCloseFeeRate_S = paramString;
  }
  
  public String getBreedID()
  {
    return this.BreedID;
  }
  
  public void setBreedID(String paramString)
  {
    this.BreedID = paramString;
  }
  
  public String getCommodityID()
  {
    return this.CommodityID;
  }
  
  public void setCommodityID(String paramString)
  {
    this.CommodityID = paramString;
  }
  
  public String getContractFactor()
  {
    return this.ContractFactor;
  }
  
  public void setContractFactor(String paramString)
  {
    this.ContractFactor = paramString;
  }
  
  public String getCrud()
  {
    return this.crud;
  }
  
  public void setCrud(String paramString)
  {
    this.crud = paramString;
  }
  
  public String getEndTime()
  {
    return this.EndTime;
  }
  
  public void setEndTime(String paramString)
  {
    this.EndTime = paramString;
  }
  
  public String getFeeAlgr()
  {
    return this.FeeAlgr;
  }
  
  public void setFeeAlgr(String paramString)
  {
    this.FeeAlgr = paramString;
  }
  
  public String getFeeItem1()
  {
    return this.FeeItem1;
  }
  
  public void setFeeItem1(String paramString)
  {
    this.FeeItem1 = paramString;
  }
  
  public String getFeeItem2()
  {
    return this.FeeItem2;
  }
  
  public void setFeeItem2(String paramString)
  {
    this.FeeItem2 = paramString;
  }
  
  public String getFeeRate_B()
  {
    return this.FeeRate_B;
  }
  
  public void setFeeRate_B(String paramString)
  {
    this.FeeRate_B = paramString;
  }
  
  public String getFeeRate_S()
  {
    return this.FeeRate_S;
  }
  
  public void setFeeRate_S(String paramString)
  {
    this.FeeRate_S = paramString;
  }
  
  public String getMarginAlgr()
  {
    return this.MarginAlgr;
  }
  
  public void setMarginAlgr(String paramString)
  {
    this.MarginAlgr = paramString;
  }
  
  public String getMarginItem1()
  {
    return this.MarginItem1;
  }
  
  public void setMarginItem1(String paramString)
  {
    this.MarginItem1 = paramString;
  }
  
  public String getMarginItem2()
  {
    return this.MarginItem2;
  }
  
  public void setMarginItem2(String paramString)
  {
    this.MarginItem2 = paramString;
  }
  
  public String getMarginItem3()
  {
    return this.MarginItem3;
  }
  
  public void setMarginItem3(String paramString)
  {
    this.MarginItem3 = paramString;
  }
  
  public String getMarginItem4()
  {
    return this.MarginItem4;
  }
  
  public void setMarginItem4(String paramString)
  {
    this.MarginItem4 = paramString;
  }
  
  public String getMarginRate_B()
  {
    return this.MarginRate_B;
  }
  
  public void setMarginRate_B(String paramString)
  {
    this.MarginRate_B = paramString;
  }
  
  public String getMarginRate_S()
  {
    return this.MarginRate_S;
  }
  
  public void setMarginRate_S(String paramString)
  {
    this.MarginRate_S = paramString;
  }
  
  public String getMarketCode()
  {
    return this.MarketCode;
  }
  
  public void setMarketCode(String paramString)
  {
    this.MarketCode = paramString;
  }
  
  public String getMarketDate()
  {
    return this.MarketDate;
  }
  
  public void setMarketDate(String paramString)
  {
    this.MarketDate = paramString;
  }
  
  public String getMinPriceMove()
  {
    return this.MinPriceMove;
  }
  
  public void setMinPriceMove(String paramString)
  {
    this.MinPriceMove = paramString;
  }
  
  public String getName()
  {
    return this.Name;
  }
  
  public void setName(String paramString)
  {
    this.Name = paramString;
  }
  
  public String getSettleDate()
  {
    return this.SettleDate;
  }
  
  public void setSettleDate(String paramString)
  {
    this.SettleDate = paramString;
  }
  
  public String getSettleDate1()
  {
    return this.SettleDate1;
  }
  
  public void setSettleDate1(String paramString)
  {
    this.SettleDate1 = paramString;
  }
  
  public String getSettleDate2()
  {
    return this.SettleDate2;
  }
  
  public void setSettleDate2(String paramString)
  {
    this.SettleDate2 = paramString;
  }
  
  public String getSettleDate3()
  {
    return this.SettleDate3;
  }
  
  public void setSettleDate3(String paramString)
  {
    this.SettleDate3 = paramString;
  }
  
  public String getSettleDate4()
  {
    return this.SettleDate4;
  }
  
  public void setSettleDate4(String paramString)
  {
    this.SettleDate4 = paramString;
  }
  
  public String getSortID()
  {
    return this.SortID;
  }
  
  public void setSortID(String paramString)
  {
    this.SortID = paramString;
  }
  
  public String getSpreadAlgr()
  {
    return this.SpreadAlgr;
  }
  
  public void setSpreadAlgr(String paramString)
  {
    this.SpreadAlgr = paramString;
  }
  
  public String getSpreadDownLmt()
  {
    return this.SpreadDownLmt;
  }
  
  public void setSpreadDownLmt(String paramString)
  {
    this.SpreadDownLmt = paramString;
  }
  
  public String getSpreadUpLmt()
  {
    return this.SpreadUpLmt;
  }
  
  public void setSpreadUpLmt(String paramString)
  {
    this.SpreadUpLmt = paramString;
  }
  
  public String getStartTime()
  {
    return this.StartTime;
  }
  
  public void setStartTime(String paramString)
  {
    this.StartTime = paramString;
  }
  
  public String getStatus()
  {
    return this.Status;
  }
  
  public void setStatus(String paramString)
  {
    this.Status = paramString;
  }
  
  public String getUni_Cmdty_Code()
  {
    return this.Uni_Cmdty_Code;
  }
  
  public void setUni_Cmdty_Code(String paramString)
  {
    this.Uni_Cmdty_Code = paramString;
  }
  
  public String getLastPrice()
  {
    return this.LastPrice;
  }
  
  public void setLastPrice(String paramString)
  {
    this.LastPrice = paramString;
  }
  
  public String getMarginItem1_S()
  {
    return this.MarginItem1_S;
  }
  
  public void setMarginItem1_S(String paramString)
  {
    this.MarginItem1_S = paramString;
  }
  
  public String getMarginItem2_S()
  {
    return this.MarginItem2_S;
  }
  
  public void setMarginItem2_S(String paramString)
  {
    this.MarginItem2_S = paramString;
  }
  
  public String getMarginItem3_S()
  {
    return this.MarginItem3_S;
  }
  
  public void setMarginItem3_S(String paramString)
  {
    this.MarginItem3_S = paramString;
  }
  
  public String getMarginItem4_S()
  {
    return this.MarginItem4_S;
  }
  
  public void setMarginItem4_S(String paramString)
  {
    this.MarginItem4_S = paramString;
  }
  
  public String getMarketFeeAlgr()
  {
    return this.MarketFeeAlgr;
  }
  
  public void setMarketFeeAlgr(String paramString)
  {
    this.MarketFeeAlgr = paramString;
  }
  
  public String getMarketFeeRate_B()
  {
    return this.MarketFeeRate_B;
  }
  
  public void setMarketFeeRate_B(String paramString)
  {
    this.MarketFeeRate_B = paramString;
  }
  
  public String getMarketFeeRate_S()
  {
    return this.MarketFeeRate_S;
  }
  
  public void setMarketFeeRate_S(String paramString)
  {
    this.MarketFeeRate_S = paramString;
  }
  
  public String getMarginAssure_B()
  {
    return this.MarginAssure_B;
  }
  
  public void setMarginAssure_B(String paramString)
  {
    this.MarginAssure_B = paramString;
  }
  
  public String getMarginAssure_S()
  {
    return this.MarginAssure_S;
  }
  
  public void setMarginAssure_S(String paramString)
  {
    this.MarginAssure_S = paramString;
  }
  
  public String getMarginItemAssure1()
  {
    return this.MarginItemAssure1;
  }
  
  public void setMarginItemAssure1(String paramString)
  {
    this.MarginItemAssure1 = paramString;
  }
  
  public String getMarginItemAssure1_S()
  {
    return this.MarginItemAssure1_S;
  }
  
  public void setMarginItemAssure1_S(String paramString)
  {
    this.MarginItemAssure1_S = paramString;
  }
  
  public String getMarginItemAssure2()
  {
    return this.MarginItemAssure2;
  }
  
  public void setMarginItemAssure2(String paramString)
  {
    this.MarginItemAssure2 = paramString;
  }
  
  public String getMarginItemAssure2_S()
  {
    return this.MarginItemAssure2_S;
  }
  
  public void setMarginItemAssure2_S(String paramString)
  {
    this.MarginItemAssure2_S = paramString;
  }
  
  public String getMarginItemAssure3()
  {
    return this.MarginItemAssure3;
  }
  
  public void setMarginItemAssure3(String paramString)
  {
    this.MarginItemAssure3 = paramString;
  }
  
  public String getMarginItemAssure3_S()
  {
    return this.MarginItemAssure3_S;
  }
  
  public void setMarginItemAssure3_S(String paramString)
  {
    this.MarginItemAssure3_S = paramString;
  }
  
  public String getMarginItemAssure4()
  {
    return this.MarginItemAssure4;
  }
  
  public void setMarginItemAssure4(String paramString)
  {
    this.MarginItemAssure4 = paramString;
  }
  
  public String getMarginItemAssure4_S()
  {
    return this.MarginItemAssure4_S;
  }
  
  public void setMarginItemAssure4_S(String paramString)
  {
    this.MarginItemAssure4_S = paramString;
  }
  
  public String getHistoryCloseFeeRate_B()
  {
    return this.HistoryCloseFeeRate_B;
  }
  
  public void setHistoryCloseFeeRate_B(String paramString)
  {
    this.HistoryCloseFeeRate_B = paramString;
  }
  
  public String getHistoryCloseFeeRate_S()
  {
    return this.HistoryCloseFeeRate_S;
  }
  
  public void setHistoryCloseFeeRate_S(String paramString)
  {
    this.HistoryCloseFeeRate_S = paramString;
  }
  
  public String[] getTradeTimes()
  {
    String[] arrayOfString = new String[this.TradeTime.size()];
    int i = 0;
    Iterator localIterator = this.TradeTime.iterator();
    while (localIterator.hasNext())
    {
      TradeTime localTradeTime = (TradeTime)localIterator.next();
      arrayOfString[i] = localTradeTime.getSectionID().toString();
      i++;
    }
    return arrayOfString;
  }
  
  public String getSettleMarginAlgr_B()
  {
    return this.settleMarginAlgr_B;
  }
  
  public void setSettleMarginAlgr_B(String paramString)
  {
    this.settleMarginAlgr_B = paramString;
  }
  
  public String getSettleMarginAlgr_S()
  {
    return this.settleMarginAlgr_S;
  }
  
  public void setSettleMarginAlgr_S(String paramString)
  {
    this.settleMarginAlgr_S = paramString;
  }
  
  public String getOrderPrivilege()
  {
    return this.orderPrivilege;
  }
  
  public void setOrderPrivilege(String paramString)
  {
    this.orderPrivilege = paramString;
  }
  
  public String getPayoutAlgr()
  {
    return this.payoutAlgr;
  }
  
  public void setPayoutAlgr(String paramString)
  {
    this.payoutAlgr = paramString;
  }
  
  public String getPayoutRate()
  {
    return this.payoutRate;
  }
  
  public void setPayoutRate(String paramString)
  {
    this.payoutRate = paramString;
  }
  
  public String getMarginItem5()
  {
    return this.MarginItem5;
  }
  
  public void setMarginItem5(String paramString)
  {
    this.MarginItem5 = paramString;
  }
  
  public String getMarginItem5_S()
  {
    return this.MarginItem5_S;
  }
  
  public void setMarginItem5_S(String paramString)
  {
    this.MarginItem5_S = paramString;
  }
  
  public String getMarginItemAssure5()
  {
    return this.MarginItemAssure5;
  }
  
  public void setMarginItemAssure5(String paramString)
  {
    this.MarginItemAssure5 = paramString;
  }
  
  public String getMarginItemAssure5_S()
  {
    return this.MarginItemAssure5_S;
  }
  
  public void setMarginItemAssure5_S(String paramString)
  {
    this.MarginItemAssure5_S = paramString;
  }
  
  public String getSettleDate5()
  {
    return this.SettleDate5;
  }
  
  public void setSettleDate5(String paramString)
  {
    this.SettleDate5 = paramString;
  }
  
  public String getOrderPrivilege_B()
  {
    return this.orderPrivilege_B;
  }
  
  public void setOrderPrivilege_B(String paramString)
  {
    this.orderPrivilege_B = paramString;
  }
  
  public String getOrderPrivilege_S()
  {
    return this.orderPrivilege_S;
  }
  
  public void setOrderPrivilege_S(String paramString)
  {
    this.orderPrivilege_S = paramString;
  }
  
  public String getBeforeDays()
  {
    return this.beforeDays;
  }
  
  public void setBeforeDays(String paramString)
  {
    this.beforeDays = paramString;
  }
  
  public String getSettlePriceType()
  {
    return this.settlePriceType;
  }
  
  public void setSettlePriceType(String paramString)
  {
    this.settlePriceType = paramString;
  }
  
  public String getSpecSettlePrice()
  {
    return this.specSettlePrice;
  }
  
  public void setSpecSettlePrice(String paramString)
  {
    this.specSettlePrice = paramString;
  }
  
  public String getContractFactorName()
  {
    return this.contractFactorName;
  }
  
  public void setContractFactorName(String paramString)
  {
    this.contractFactorName = paramString;
  }
  
  public String getMinQuantityMove()
  {
    return this.minQuantityMove;
  }
  
  public void setMinQuantityMove(String paramString)
  {
    this.minQuantityMove = paramString;
  }
  
  public String getStoreRecoupRate()
  {
    return this.storeRecoupRate;
  }
  
  public void setStoreRecoupRate(String paramString)
  {
    this.storeRecoupRate = paramString;
  }
  
  public String getMinSettleMoveQty()
  {
    return this.minSettleMoveQty;
  }
  
  public void setMinSettleMoveQty(String paramString)
  {
    this.minSettleMoveQty = paramString;
  }
  
  public String getMinSettleQty()
  {
    return this.minSettleQty;
  }
  
  public void setMinSettleQty(String paramString)
  {
    this.minSettleQty = paramString;
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
