package gnnt.MEBS.timebargain.front.model;

import java.util.Date;

import gnnt.MEBS.common.front.model.StandardModel;
import gnnt.MEBS.common.front.model.translate.ClassDiscription;

public class DelayOrder extends StandardModel {
	private static final long serialVersionUID = -2111454451776200150L;

	@ClassDiscription(name = "委托单号", description = "")
	private Long orderNO;

	@ClassDiscription(name = "商品代码", description = "")
	private String commodityID;

	@ClassDiscription(name = "交易客户ID", description = "")
	private String customerID;

	@ClassDiscription(name = "交易员ID", description = "")
	private String traderID;

	@ClassDiscription(name = "买卖标志", description = " 1:买   2:卖")
	private Short bsFlag;

	@ClassDiscription(name = "延期委托类型", description = "1:交收申报    2:中立仓申报")
	private Short delayorderType;

	@ClassDiscription(name = "状态", description = "1:已委托 2:部分成交 3:全部成交 5:全部撤单 6:部分成交后撤单")
	private Short status;

	@ClassDiscription(name = "撤单类型", description = "1:委托撤单 4:闭市时自动撤单")
	private Short withdrawType;

	@ClassDiscription(name = "委托数量", description = "数量")
	private Long quantity;

	@ClassDiscription(name = "委托价格", description = "")
	private Double price;

	@ClassDiscription(name = "已成交数量", description = "部分成交的数量")
	private Long tradeQty;

	@ClassDiscription(name = "冻结资金", description = "对应金额的持仓保证金和交易费用")
	private Double frozenFunds;

	@ClassDiscription(name = "解冻资金", description = "解冻资金数量")
	private Double unFrozenFunds;

	@ClassDiscription(name = "委托时间", description = "委托提交的时间")
	private Date orderTime;

	@ClassDiscription(name = "撤单时间", description = "")
	private Date withdrawTime;

	@ClassDiscription(name = "委托者IP", description = "")
	private String ordererIP;

	@ClassDiscription(name = "防抵赖码", description = "")
	private String signature;

	@ClassDiscription(name = "交易商ID", description = "")
	private String firmID;

	@ClassDiscription(name = "委托员ID", description = "")
	private String consignerID;

	@ClassDiscription(name = "撤单员ID", description = "")
	private String withdrawerID;

	@ClassDiscription(name = "撤单号id", description = "rmi调用的时候使用，与orderNO代表同一个意思")
	private Long withdrawID;

	public Long getOrderNO() {
		return this.orderNO;
	}

	public void setOrderNO(Long paramLong) {
		this.orderNO = paramLong;
	}

	public String getCommodityID() {
		return this.commodityID;
	}

	public void setCommodityID(String paramString) {
		this.commodityID = paramString;
	}

	public String getCustomerID() {
		return this.customerID;
	}

	public void setCustomerID(String paramString) {
		this.customerID = paramString;
	}

	public String getTraderID() {
		return this.traderID;
	}

	public void setTraderID(String paramString) {
		this.traderID = paramString;
	}

	public Short getBsFlag() {
		return this.bsFlag;
	}

	public void setBsFlag(Short paramShort) {
		this.bsFlag = paramShort;
	}

	public Short getDelayorderType() {
		return this.delayorderType;
	}

	public void setDelayorderType(Short paramShort) {
		this.delayorderType = paramShort;
	}

	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short paramShort) {
		this.status = paramShort;
	}

	public Short getWithdrawType() {
		return this.withdrawType;
	}

	public void setWithdrawType(Short paramShort) {
		this.withdrawType = paramShort;
	}

	public static long getSerialversionuid() {
		return -2111454451776200150L;
	}

	public Long getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Long paramLong) {
		this.quantity = paramLong;
	}

	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double paramDouble) {
		this.price = paramDouble;
	}

	public Long getTradeQty() {
		return this.tradeQty;
	}

	public void setTradeQty(Long paramLong) {
		this.tradeQty = paramLong;
	}

	public Double getFrozenFunds() {
		return this.frozenFunds;
	}

	public void setFrozenFunds(Double paramDouble) {
		this.frozenFunds = paramDouble;
	}

	public Double getUnFrozenFunds() {
		return this.unFrozenFunds;
	}

	public void setUnFrozenFunds(Double paramDouble) {
		this.unFrozenFunds = paramDouble;
	}

	public Date getOrderTime() {
		return this.orderTime;
	}

	public void setOrderTime(Date paramDate) {
		this.orderTime = paramDate;
	}

	public Date getWithdrawTime() {
		return this.withdrawTime;
	}

	public void setWithdrawTime(Date paramDate) {
		this.withdrawTime = paramDate;
	}

	public String getOrdererIP() {
		return this.ordererIP;
	}

	public void setOrdererIP(String paramString) {
		this.ordererIP = paramString;
	}

	public String getSignature() {
		return this.signature;
	}

	public void setSignature(String paramString) {
		this.signature = paramString;
	}

	public String getFirmID() {
		return this.firmID;
	}

	public void setFirmID(String paramString) {
		this.firmID = paramString;
	}

	public String getConsignerID() {
		return this.consignerID;
	}

	public void setConsignerID(String paramString) {
		this.consignerID = paramString;
	}

	public String getWithdrawerID() {
		return this.withdrawerID;
	}

	public void setWithdrawerID(String paramString) {
		this.withdrawerID = paramString;
	}

	public Long getWithdrawID() {
		return this.withdrawID;
	}

	public void setWithdrawID(Long paramLong) {
		this.withdrawID = paramLong;
	}

	public StandardModel.PrimaryInfo fetchPKey() {
		return new StandardModel.PrimaryInfo("orderNO", this.orderNO);
	}
}