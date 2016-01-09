package gnnt.MEBS.timebargain.front.model;

import gnnt.MEBS.common.front.model.StandardModel;
import gnnt.MEBS.common.front.model.translate.ClassDiscription;

public class Market extends StandardModel {
	private static final long serialVersionUID = -307415924612718485L;

	@ClassDiscription(name = " 市场代码", description = "")
	private String marketcode;

	@ClassDiscription(name = "市场名称", description = "")
	private String marketName;

	@ClassDiscription(name = "状态", description = "")
	private Short status;

	@ClassDiscription(name = "保证金调整方式", description = "")
	private Short marginFBFlag;

	@ClassDiscription(name = "市场名称简称", description = "")
	private String shortName;

	@ClassDiscription(name = "浮动盈亏是否扣税", description = "")
	private Short floatingLossComputeType;

	@ClassDiscription(name = "运行模式", description = "")
	private Short runMode;

	@ClassDiscription(name = "平仓算法", description = "")
	private Short closeAlgr;

	@ClassDiscription(name = "交收模式", description = "")
	private Short settleMode;

	@ClassDiscription(name = "成交价算法", description = "")
	private Short tradePriceAlgr;

	@ClassDiscription(name = "写成交流水类型", description = "")
	private Short tradeflowType;

	@ClassDiscription(name = "浮动盈亏是否扣税", description = "")
	private Short floatingProfitSubTax;

	@ClassDiscription(name = "抵顶模式", description = "")
	private Short gageMode;

	@ClassDiscription(name = "交易时间类型", description = "")
	private Short tradeTimeType;

	@ClassDiscription(name = "延期交收是否需要仓单", description = "")
	private Short delayNeedBill;

	@ClassDiscription(name = "中立仓启用标志 ", description = "0启用 1 不启用")
	private Short neutralFlag;

	@ClassDiscription(name = "中立仓撮合优先级", description = "")
	private Short neutralmatchpriority;

	@ClassDiscription(name = "行情单双边", description = "")
	private Short quotationTwoSide;

	@ClassDiscription(name = "延期交收行情显示类型", description = "")
	private Short delayQuoShowType;

	@ClassDiscription(name = "中立仓交收手续费收取方式", description = "")
	private Short neutralFeeWay;

	@ClassDiscription(name = "提前交收是否收取保证金", description = "")
	private Short asMarginType;

	@ClassDiscription(name = "交收申报是否按净订货量申报", description = "")
	private Short delayOrderIsPure;

	@ClassDiscription(name = "收取延期补偿金类型", description = "0：按净订货量收取（默认）；1：按单边订货总量收取；")
	private Short chargeDelayFeeType;

	public String getMarketName() {
		return this.marketName;
	}

	public void setMarketName(String paramString) {
		this.marketName = paramString;
	}

	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short paramShort) {
		this.status = paramShort;
	}

	public Short getMarginFBFlag() {
		return this.marginFBFlag;
	}

	public void setMarginFBFlag(Short paramShort) {
		this.marginFBFlag = paramShort;
	}

	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(String paramString) {
		this.shortName = paramString;
	}

	public Short getFloatingLossComputeType() {
		return this.floatingLossComputeType;
	}

	public void setFloatingLossComputeType(Short paramShort) {
		this.floatingLossComputeType = paramShort;
	}

	public Short getRunMode() {
		return this.runMode;
	}

	public void setRunMode(Short paramShort) {
		this.runMode = paramShort;
	}

	public Short getCloseAlgr() {
		return this.closeAlgr;
	}

	public void setCloseAlgr(Short paramShort) {
		this.closeAlgr = paramShort;
	}

	public Short getSettleMode() {
		return this.settleMode;
	}

	public void setSettleMode(Short paramShort) {
		this.settleMode = paramShort;
	}

	public Short getTradePriceAlgr() {
		return this.tradePriceAlgr;
	}

	public void setTradePriceAlgr(Short paramShort) {
		this.tradePriceAlgr = paramShort;
	}

	public Short getTradeflowType() {
		return this.tradeflowType;
	}

	public void setTradeflowType(Short paramShort) {
		this.tradeflowType = paramShort;
	}

	public Short getFloatingProfitSubTax() {
		return this.floatingProfitSubTax;
	}

	public void setFloatingProfitSubTax(Short paramShort) {
		this.floatingProfitSubTax = paramShort;
	}

	public Short getGageMode() {
		return this.gageMode;
	}

	public void setGageMode(Short paramShort) {
		this.gageMode = paramShort;
	}

	public Short getTradeTimeType() {
		return this.tradeTimeType;
	}

	public void setTradeTimeType(Short paramShort) {
		this.tradeTimeType = paramShort;
	}

	public Short getDelayNeedBill() {
		return this.delayNeedBill;
	}

	public void setDelayNeedBill(Short paramShort) {
		this.delayNeedBill = paramShort;
	}

	public Short getNeutralFlag() {
		return this.neutralFlag;
	}

	public void setNeutralFlag(Short paramShort) {
		this.neutralFlag = paramShort;
	}

	public Short getNeutralmatchpriority() {
		return this.neutralmatchpriority;
	}

	public void setNeutralmatchpriority(Short paramShort) {
		this.neutralmatchpriority = paramShort;
	}

	public Short getQuotationTwoSide() {
		return this.quotationTwoSide;
	}

	public void setQuotationTwoSide(Short paramShort) {
		this.quotationTwoSide = paramShort;
	}

	public Short getDelayQuoShowType() {
		return this.delayQuoShowType;
	}

	public void setDelayQuoShowType(Short paramShort) {
		this.delayQuoShowType = paramShort;
	}

	public Short getNeutralFeeWay() {
		return this.neutralFeeWay;
	}

	public void setNeutralFeeWay(Short paramShort) {
		this.neutralFeeWay = paramShort;
	}

	public Short getAsMarginType() {
		return this.asMarginType;
	}

	public void setAsMarginType(Short paramShort) {
		this.asMarginType = paramShort;
	}

	public Short getDelayOrderIsPure() {
		return this.delayOrderIsPure;
	}

	public void setDelayOrderIsPure(Short paramShort) {
		this.delayOrderIsPure = paramShort;
	}

	public Short getChargeDelayFeeType() {
		return this.chargeDelayFeeType;
	}

	public void setChargeDelayFeeType(Short paramShort) {
		this.chargeDelayFeeType = paramShort;
	}

	public String getMarketcode() {
		return this.marketcode;
	}

	public void setMarketcode(String paramString) {
		this.marketcode = paramString;
	}

	public StandardModel.PrimaryInfo fetchPKey() {
		return new StandardModel.PrimaryInfo("marketcode", this.marketcode);
	}
}