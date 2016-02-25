package gnnt.MEBS.timebargain.manage.webapp.form;

import java.io.Serializable;

public class MarketForm
  extends BaseForm
  implements Serializable
{
  private static final long serialVersionUID = 3690197650654049812L;
  private String marketCode;
  private String marketName;
  private String status;
  private String firmID;
  private String tradePassword;
  private String confirmPassword;
  private String marginFBFlag;
  private String shortName;
  private String marginPriceType;
  private String floatingLossComputeType;
  private String commoditySetType;
  private String upToTime;
  private String crud = "";
  private String runMode;
  private String closeAlgr;
  private String addedTax;
  private String settlePriceType;
  private String settleMode;
  private String beforeDays;
  private String tradePriceAlgr;
  private String floatingLossComputeType1;
  private String floatingLossComputeType2;
  private String floatingProfitSubTax;
  private Short gageMode;
  private String tradeTimeType;
  private String delayQuoShowType;
  private String neutralFeeWay;
  private String asMarginType;
  private Short delayOrderIsPure;
  private Short chargeDelayFeeType;
  private Short IsCPriceCpFloatingLoss;
  
  public Short getGageMode()
  {
    return this.gageMode;
  }
  
  public void setGageMode(Short gageMode)
  {
    this.gageMode = gageMode;
  }
  
  public String getFloatingProfitSubTax()
  {
    return this.floatingProfitSubTax;
  }
  
  public void setFloatingProfitSubTax(String floatingProfitSubTax)
  {
    this.floatingProfitSubTax = floatingProfitSubTax;
  }
  
  public String getTradePriceAlgr()
  {
    return this.tradePriceAlgr;
  }
  
  public void setTradePriceAlgr(String tradePriceAlgr)
  {
    this.tradePriceAlgr = tradePriceAlgr;
  }
  
  public String getBeforeDays()
  {
    return this.beforeDays;
  }
  
  public void setBeforeDays(String beforeDays)
  {
    this.beforeDays = beforeDays;
  }
  
  public String getSettleMode()
  {
    return this.settleMode;
  }
  
  public void setSettleMode(String settleMode)
  {
    this.settleMode = settleMode;
  }
  
  public String getSettlePriceType()
  {
    return this.settlePriceType;
  }
  
  public void setSettlePriceType(String settlePriceType)
  {
    this.settlePriceType = settlePriceType;
  }
  
  public String getAddedTax()
  {
    return this.addedTax;
  }
  
  public void setAddedTax(String addedTax)
  {
    this.addedTax = addedTax;
  }
  
  public String getCloseAlgr()
  {
    return this.closeAlgr;
  }
  
  public void setCloseAlgr(String closeAlgr)
  {
    this.closeAlgr = closeAlgr;
  }
  
  public String getRunMode()
  {
    return this.runMode;
  }
  
  public void setRunMode(String runMode)
  {
    this.runMode = runMode;
  }
  
  public String getConfirmPassword()
  {
    return this.confirmPassword;
  }
  
  public void setConfirmPassword(String confirmPassword)
  {
    this.confirmPassword = confirmPassword;
  }
  
  public String getCrud()
  {
    return this.crud;
  }
  
  public void setCrud(String crud)
  {
    this.crud = crud;
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
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String status)
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
  
  public String getMarginFBFlag()
  {
    return this.marginFBFlag;
  }
  
  public void setMarginFBFlag(String marginFBFlag)
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
  
  public String getMarginPriceType()
  {
    return this.marginPriceType;
  }
  
  public void setMarginPriceType(String marginPriceType)
  {
    this.marginPriceType = marginPriceType;
  }
  
  public String getFloatingLossComputeType()
  {
    return this.floatingLossComputeType;
  }
  
  public void setFloatingLossComputeType(String floatingLossComputeType)
  {
    this.floatingLossComputeType = floatingLossComputeType;
  }
  
  public String getCommoditySetType()
  {
    return this.commoditySetType;
  }
  
  public void setCommoditySetType(String commoditySetType)
  {
    this.commoditySetType = commoditySetType;
  }
  
  public String getUpToTime()
  {
    return this.upToTime;
  }
  
  public void setUpToTime(String upToTime)
  {
    this.upToTime = upToTime;
  }
  
  public String getFloatingLossComputeType1()
  {
    return this.floatingLossComputeType1;
  }
  
  public void setFloatingLossComputeType1(String floatingLossComputeType1)
  {
    this.floatingLossComputeType1 = floatingLossComputeType1;
  }
  
  public String getFloatingLossComputeType2()
  {
    return this.floatingLossComputeType2;
  }
  
  public void setFloatingLossComputeType2(String floatingLossComputeType2)
  {
    this.floatingLossComputeType2 = floatingLossComputeType2;
  }
  
  public String getDelayQuoShowType()
  {
    return this.delayQuoShowType;
  }
  
  public void setDelayQuoShowType(String delayQuoShowType)
  {
    this.delayQuoShowType = delayQuoShowType;
  }
  
  public String getNeutralFeeWay()
  {
    return this.neutralFeeWay;
  }
  
  public void setNeutralFeeWay(String neutralFeeWay)
  {
    this.neutralFeeWay = neutralFeeWay;
  }
  
  public String getTradeTimeType()
  {
    return this.tradeTimeType;
  }
  
  public void setTradeTimeType(String tradeTimeType)
  {
    this.tradeTimeType = tradeTimeType;
  }
  
  public String getAsMarginType()
  {
    return this.asMarginType;
  }
  
  public void setAsMarginType(String asMarginType)
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
