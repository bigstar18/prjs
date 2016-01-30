package gnnt.MEBS.timebargain.mgr.model.tradeparams;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

public class Market extends StandardModel
{

  @ClassDiscription(name="市场代码", description="")
  private String marketcode;

  @ClassDiscription(name="市场名称", description="")
  private String marketName;

  @ClassDiscription(name="状态", description="")
  private Short status;

  @ClassDiscription(name="", description="")
  private Short marginFBFlag;

  @ClassDiscription(name="", description="")
  private String shortName;

  @ClassDiscription(name="浮亏计算方式", description="")
  private Short floatingLossComputeType;

  @ClassDiscription(name="运行模式", description="")
  private Short runMode;

  @ClassDiscription(name="平仓算法", description="")
  private Short closeAlgr;

  @ClassDiscription(name="交收模式", description="")
  private Short settleMode;

  @ClassDiscription(name="成交价算法", description="")
  private Short tradePriceAlgr;

  @ClassDiscription(name="写成交流水类型", description="")
  private Short tradeflowType;

  @ClassDiscription(name="浮动盈亏是否扣税", description="")
  private Short floatingProfitSubTax;

  @ClassDiscription(name="抵顶模式", description="")
  private Short gageMode;

  @ClassDiscription(name="交易时间类型", description="")
  private Short tradeTimeType;

  @ClassDiscription(name="延期交收是否需要仓单", description="")
  private Short delayNeedBill;

  @ClassDiscription(name="中立仓启用标志", description="0不启用，1启用")
  private Short neutralFlag;

  @ClassDiscription(name="中立仓撮合优先级", description="")
  private Short neutralmatchpriority;

  @ClassDiscription(name="行情单双边", description="")
  private Short quotationTwoSide;

  @ClassDiscription(name="延期交收行情显示类型", description="")
  private Short delayQuoShowType;

  @ClassDiscription(name="中立仓交收手续费收取方式", description="")
  private Short neutralFeeWay;

  @ClassDiscription(name="提前交收是否收取保证金", description="")
  private Short asMarginType;

  @ClassDiscription(name="交收申报是否按净订货量申报", description="0：否，1：是")
  private Short delayOrderIsPure;

  @ClassDiscription(name="收取延期补偿金类型", description="0：按净订货量收取（默认）；1：按单边订货总量收取；")
  private Short chargeDelayFeeType;

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

  public Short getRunMode()
  {
    return this.runMode;
  }

  public void setRunMode(Short runMode)
  {
    this.runMode = runMode;
  }

  public Short getCloseAlgr()
  {
    return this.closeAlgr;
  }

  public void setCloseAlgr(Short closeAlgr)
  {
    this.closeAlgr = closeAlgr;
  }

  public Short getSettleMode()
  {
    return this.settleMode;
  }

  public void setSettleMode(Short settleMode)
  {
    this.settleMode = settleMode;
  }

  public Short getTradePriceAlgr()
  {
    return this.tradePriceAlgr;
  }

  public void setTradePriceAlgr(Short tradePriceAlgr)
  {
    this.tradePriceAlgr = tradePriceAlgr;
  }

  public Short getTradeflowType()
  {
    return this.tradeflowType;
  }

  public void setTradeflowType(Short tradeflowType)
  {
    this.tradeflowType = tradeflowType;
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

  public Short getTradeTimeType()
  {
    return this.tradeTimeType;
  }

  public void setTradeTimeType(Short tradeTimeType)
  {
    this.tradeTimeType = tradeTimeType;
  }

  public Short getDelayNeedBill()
  {
    return this.delayNeedBill;
  }

  public void setDelayNeedBill(Short delayNeedBill) {
    this.delayNeedBill = delayNeedBill;
  }

  public Short getNeutralFlag() {
    return this.neutralFlag;
  }

  public void setNeutralFlag(Short neutralFlag) {
    this.neutralFlag = neutralFlag;
  }

  public Short getNeutralmatchpriority() {
    return this.neutralmatchpriority;
  }

  public void setNeutralmatchpriority(Short neutralmatchpriority) {
    this.neutralmatchpriority = neutralmatchpriority;
  }

  public Short getQuotationTwoSide() {
    return this.quotationTwoSide;
  }

  public void setQuotationTwoSide(Short quotationTwoSide) {
    this.quotationTwoSide = quotationTwoSide;
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

  public String getMarketcode()
  {
    return this.marketcode;
  }

  public void setMarketcode(String marketcode)
  {
    this.marketcode = marketcode;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "marketcode", this.marketcode);
  }
}