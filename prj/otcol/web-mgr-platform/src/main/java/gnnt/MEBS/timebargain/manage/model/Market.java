package gnnt.MEBS.timebargain.manage.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Market
  extends BaseObject
  implements Serializable
{
  private static final long serialVersionUID = 3690197650654049813L;
  private String marketCode;
  private String marketName;
  private Short status;
  private String firmID;
  private String tradePassword;
  private Short marginFBFlag;
  private String shortName;
  private Short marginPriceType;
  private Short floatingLossComputeType;
  private Short commoditySetType;
  private String upToTime;
  private String IssueDate;
  private String VerifyStr;
  private String traderId;
  private String crud = "";
  private Short runMode;
  private Short closeAlgr;
  private Double addedTax;
  private Short settlePriceType;
  private Short settleMode;
  private Long beforeDays;
  private Short tradePriceAlgr;
  private Short floatingProfitSubTax;
  private Short gageMode;
  private Short tradeTimeType;
  private Short delayQuoShowType;
  private Short neutralFeeWay;
  private Short asMarginType;
  private Short delayOrderIsPure;
  private Short chargeDelayFeeType;
  private Short IsCPriceCpFloatingLoss;
  
  public Short getTradePriceAlgr()
  {
    return this.tradePriceAlgr;
  }
  
  public void setTradePriceAlgr(Short tradePriceAlgr)
  {
    this.tradePriceAlgr = tradePriceAlgr;
  }
  
  public Long getBeforeDays()
  {
    return this.beforeDays;
  }
  
  public void setBeforeDays(Long beforeDays)
  {
    this.beforeDays = beforeDays;
  }
  
  public Short getSettleMode()
  {
    return this.settleMode;
  }
  
  public void setSettleMode(Short settleMode)
  {
    this.settleMode = settleMode;
  }
  
  public Short getSettlePriceType()
  {
    return this.settlePriceType;
  }
  
  public void setSettlePriceType(Short settlePriceType)
  {
    this.settlePriceType = settlePriceType;
  }
  
  public Double getAddedTax()
  {
    return this.addedTax;
  }
  
  public void setAddedTax(Double addedTax)
  {
    this.addedTax = addedTax;
  }
  
  public Short getCloseAlgr()
  {
    return this.closeAlgr;
  }
  
  public void setCloseAlgr(Short closeAlgr)
  {
    this.closeAlgr = closeAlgr;
  }
  
  public Short getRunMode()
  {
    return this.runMode;
  }
  
  public void setRunMode(Short runMode)
  {
    this.runMode = runMode;
  }
  
  public String getCrud()
  {
    return this.crud;
  }
  
  public void setCrud(String crud)
  {
    this.crud = crud;
  }
  
  public String getTraderId()
  {
    return this.traderId;
  }
  
  public void setTraderId(String traderId)
  {
    this.traderId = traderId;
  }
  
  public String getIssueDate()
  {
    return this.IssueDate;
  }
  
  public void setIssueDate(String issueDate)
  {
    this.IssueDate = issueDate;
  }
  
  public String getVerifyStr()
  {
    return this.VerifyStr;
  }
  
  public void setVerifyStr(String verifyStr)
  {
    this.VerifyStr = verifyStr;
  }
  
  public String getUpToTime()
  {
    return this.upToTime;
  }
  
  public void setUpToTime(String upToTime)
  {
    this.upToTime = upToTime;
  }
  
  public Short getMarginPriceType()
  {
    return this.marginPriceType;
  }
  
  public void setMarginPriceType(Short marginPriceType)
  {
    this.marginPriceType = marginPriceType;
  }
  
  public boolean equals(Object o)
  {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Market)) {
      return false;
    }
    Market m = (Market)o;
    
    return this.marketCode != null ? this.marketCode.equals(m.marketCode) : m.marketCode == null;
  }
  
  public int hashCode()
  {
    return this.marketCode != null ? this.marketCode.hashCode() : 0;
  }
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
  
  public String getFirmID()
  {
    return this.firmID;
  }
  
  public void setFirmID(String firmID)
  {
    this.firmID = firmID;
  }
  
  public String getMarketCode()
  {
    return this.marketCode;
  }
  
  public void setMarketCode(String marketCode)
  {
    this.marketCode = marketCode;
  }
  
  public String getMarketName()
  {
    return this.marketName;
  }
  
  public void setMarketName(String marketName)
  {
    this.marketName = marketName;
  }
  
  public Short getStatus()
  {
    return this.status;
  }
  
  public void setStatus(Short status)
  {
    this.status = status;
  }
  
  public String getTradePassword()
  {
    return this.tradePassword;
  }
  
  public void setTradePassword(String tradePassword)
  {
    this.tradePassword = tradePassword;
  }
  
  public Short getMarginFBFlag()
  {
    return this.marginFBFlag;
  }
  
  public void setMarginFBFlag(Short marginFBFlag)
  {
    this.marginFBFlag = marginFBFlag;
  }
  
  public String getShortName()
  {
    return this.shortName;
  }
  
  public void setShortName(String shortName)
  {
    this.shortName = shortName;
  }
  
  public Short getFloatingLossComputeType()
  {
    return this.floatingLossComputeType;
  }
  
  public void setFloatingLossComputeType(Short floatingLossComputeType)
  {
    this.floatingLossComputeType = floatingLossComputeType;
  }
  
  public Short getCommoditySetType()
  {
    return this.commoditySetType;
  }
  
  public void setCommoditySetType(Short commoditySetType)
  {
    this.commoditySetType = commoditySetType;
  }
  
  public Short getFloatingProfitSubTax()
  {
    return this.floatingProfitSubTax;
  }
  
  public void setFloatingProfitSubTax(Short floatingProfitSubTax)
  {
    this.floatingProfitSubTax = floatingProfitSubTax;
  }
  
  public Short getGageMode()
  {
    return this.gageMode;
  }
  
  public void setGageMode(Short gageMode)
  {
    this.gageMode = gageMode;
  }
  
  public Short getDelayQuoShowType()
  {
    return this.delayQuoShowType;
  }
  
  public void setDelayQuoShowType(Short delayQuoShowType)
  {
    this.delayQuoShowType = delayQuoShowType;
  }
  
  public Short getNeutralFeeWay()
  {
    return this.neutralFeeWay;
  }
  
  public void setNeutralFeeWay(Short neutralFeeWay)
  {
    this.neutralFeeWay = neutralFeeWay;
  }
  
  public Short getTradeTimeType()
  {
    return this.tradeTimeType;
  }
  
  public void setTradeTimeType(Short tradeTimeType)
  {
    this.tradeTimeType = tradeTimeType;
  }
  
  public Short getAsMarginType()
  {
    return this.asMarginType;
  }
  
  public void setAsMarginType(Short asMarginType)
  {
    this.asMarginType = asMarginType;
  }
  
  public Short getDelayOrderIsPure()
  {
    return this.delayOrderIsPure;
  }
  
  public void setDelayOrderIsPure(Short delayOrderIsPure)
  {
    this.delayOrderIsPure = delayOrderIsPure;
  }
  
  public Short getChargeDelayFeeType()
  {
    return this.chargeDelayFeeType;
  }
  
  public void setChargeDelayFeeType(Short chargeDelayFeeType)
  {
    this.chargeDelayFeeType = chargeDelayFeeType;
  }
  
  public Short getIsCPriceCpFloatingLoss()
  {
    return this.IsCPriceCpFloatingLoss;
  }
  
  public void setIsCPriceCpFloatingLoss(Short isCPriceCpFloatingLoss)
  {
    this.IsCPriceCpFloatingLoss = isCPriceCpFloatingLoss;
  }
}
