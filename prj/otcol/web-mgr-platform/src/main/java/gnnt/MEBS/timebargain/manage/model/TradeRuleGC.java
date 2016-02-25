package gnnt.MEBS.timebargain.manage.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class TradeRuleGC
  extends BaseObject
  implements Serializable
{
  private static final long serialVersionUID = 3690197650654049818L;
  private String groupID;
  private String groupName;
  private String uni_Cmdty_Code;
  private String CommodityID;
  private String Name;
  private Short FeeAlgr;
  private Double FeeRate_B;
  private Double FeeRate_S;
  private Short MarginAlgr;
  private Double MarginRate_B;
  private Double MarginRate_S;
  private Double MarginAssure_B;
  private Double MarginAssure_S;
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
  private Double SettleFeeRate_B;
  private Double SettleFeeRate_S;
  private Short ForceCloseFeeAlgr;
  private Double ForceCloseFeeRate_B;
  private Double ForceCloseFeeRate_S;
  private Short SettleMarginAlgr;
  private Double SettleMarginRate_B;
  private Double SettleMarginRate_S;
  private Short settleMarginAlgr_B;
  private Short settleMarginAlgr_S;
  private Short payoutAlgr;
  private Double payoutRate;
  private String firmID;
  private Long maxHoldQty;
  private Long cleanMaxHoldQty;
  
  public String getFirmID()
  {
    return this.firmID;
  }
  
  public void setFirmID(String paramString)
  {
    this.firmID = paramString;
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
  
  public Short getFeeAlgr()
  {
    return this.FeeAlgr;
  }
  
  public void setFeeAlgr(Short paramShort)
  {
    this.FeeAlgr = paramShort;
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
  
  public String getGroupID()
  {
    return this.groupID;
  }
  
  public void setGroupID(String paramString)
  {
    this.groupID = paramString;
  }
  
  public String getGroupName()
  {
    return this.groupName;
  }
  
  public void setGroupName(String paramString)
  {
    this.groupName = paramString;
  }
  
  public Short getMarginAlgr()
  {
    return this.MarginAlgr;
  }
  
  public void setMarginAlgr(Short paramShort)
  {
    this.MarginAlgr = paramShort;
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
  
  public Double getMarginItem1()
  {
    return this.MarginItem1;
  }
  
  public void setMarginItem1(Double paramDouble)
  {
    this.MarginItem1 = paramDouble;
  }
  
  public Double getMarginItem1_S()
  {
    return this.MarginItem1_S;
  }
  
  public void setMarginItem1_S(Double paramDouble)
  {
    this.MarginItem1_S = paramDouble;
  }
  
  public Double getMarginItem2()
  {
    return this.MarginItem2;
  }
  
  public void setMarginItem2(Double paramDouble)
  {
    this.MarginItem2 = paramDouble;
  }
  
  public Double getMarginItem2_S()
  {
    return this.MarginItem2_S;
  }
  
  public void setMarginItem2_S(Double paramDouble)
  {
    this.MarginItem2_S = paramDouble;
  }
  
  public Double getMarginItem3()
  {
    return this.MarginItem3;
  }
  
  public void setMarginItem3(Double paramDouble)
  {
    this.MarginItem3 = paramDouble;
  }
  
  public Double getMarginItem3_S()
  {
    return this.MarginItem3_S;
  }
  
  public void setMarginItem3_S(Double paramDouble)
  {
    this.MarginItem3_S = paramDouble;
  }
  
  public Double getMarginItem4()
  {
    return this.MarginItem4;
  }
  
  public void setMarginItem4(Double paramDouble)
  {
    this.MarginItem4 = paramDouble;
  }
  
  public Double getMarginItem4_S()
  {
    return this.MarginItem4_S;
  }
  
  public void setMarginItem4_S(Double paramDouble)
  {
    this.MarginItem4_S = paramDouble;
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
  
  public String getUni_Cmdty_Code()
  {
    return this.uni_Cmdty_Code;
  }
  
  public void setUni_Cmdty_Code(String paramString)
  {
    this.uni_Cmdty_Code = paramString;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof Commodity)) {
      return false;
    }
    TradeRuleGC localTradeRuleGC = (TradeRuleGC)paramObject;
    return this.uni_Cmdty_Code != null ? this.uni_Cmdty_Code.equals(localTradeRuleGC.uni_Cmdty_Code) : localTradeRuleGC.uni_Cmdty_Code == null;
  }
  
  public int hashCode()
  {
    return this.uni_Cmdty_Code != null ? this.uni_Cmdty_Code.hashCode() : 0;
  }
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
  
  public String getName()
  {
    return this.Name;
  }
  
  public void setName(String paramString)
  {
    this.Name = paramString;
  }
  
  public String getCommodityID()
  {
    return this.CommodityID;
  }
  
  public void setCommodityID(String paramString)
  {
    this.CommodityID = paramString;
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
  
  public Long getCleanMaxHoldQty()
  {
    return this.cleanMaxHoldQty;
  }
  
  public void setCleanMaxHoldQty(Long paramLong)
  {
    this.cleanMaxHoldQty = paramLong;
  }
  
  public Long getMaxHoldQty()
  {
    return this.maxHoldQty;
  }
  
  public void setMaxHoldQty(Long paramLong)
  {
    this.maxHoldQty = paramLong;
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
}
