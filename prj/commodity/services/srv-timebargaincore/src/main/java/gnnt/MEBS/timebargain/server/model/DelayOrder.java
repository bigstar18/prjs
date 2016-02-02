package gnnt.MEBS.timebargain.server.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 延期委托对象.
 *
 * <p><a href="DelayOrder.java.html"><i>View Source</i></a></p>
 *
 * @version 1.0.2.2
 * @author <a href="mailto:chenxc@gnnt.com.cn">chenxc</a>
 */
public class DelayOrder implements Serializable {
	private static final long serialVersionUID = 1690197650654049814L;

	//买卖标志
	public static final short BS_FLAG_BUY = 1; //买
	public static final short BS_FLAG_SELL = 2;//卖

	//延期委托类型
	public static final short ORDER_TYPE_SETTLE = 1;//交收申报
	public static final short ORDER_TYPE_NEUTRAL = 2;//中立仓申报
	public static final short ORDER_TYPE_CANCEL = 4;//撤单委托

	//委托状态
	public static final short STATUS_NORMAL = 1;//正常未成交委托
	public static final short STATUS_PARTIAL = 2;//部分成交
	public static final short STATUS_ALL = 3;//全部成交
	public static final short STATUS_CANCELING = 4;//正在撤单
	public static final short STATUS_CANCELED = 5;//已撤单
	public static final short STATUS_PARTIALCANCELED = 6;//部分成交后撤单

	//撤单类型
	public static final short WITHDRAWTYPE_ORDER = 1;//委托撤单
	public static final short WITHDRAWTYPE_AUTO = 4;//自动撤单

	private Long orderNo; // 委托单号

	private String customerID; // 客户ID
	
	private String firmID; // 交易商ID
	
	private String consignerID; //委托员ID

	private String commodityID; // 商品代码

	private Short delayOrderType; // 委托类型，1：交收申报；2：中立仓申报；4：撤单

	private Short buyOrSell; // 买卖标志，1：买；2：卖

	private Double price; // 价格

	private Long quantity; // 数量
	
	private String traderID; // 交易员ID

	private Date orderTime;  //委托时间
	
	private Long tradeQty = new Long(0);  //已成交数量

    private Short status; // 状态
    
    private Short withdrawType; // 撤单类型
    
    private String withdrawerID; // 撤单员ID
    
    private Long withdrawID; // 被撤委托单号
    
    private Double margin;//委托应收保证金
    
    private Short delayQuoShowType;//延期行情显示类型，0：交收申报结束和中立仓申报结束显示； 1：实时显示；

    private Short wd_DelayOrderType; // 被撤委托类型，1：交收申报；2：中立仓申报
    
    private Short delayNeedBill; // 延期交收是否需要仓单，0：不需要； 1：需要；
    
	public Short getDelayNeedBill() {
		return delayNeedBill;
	}

	public void setDelayNeedBill(Short delayNeedBill) {
		this.delayNeedBill = delayNeedBill;
	}

	public Short getWd_DelayOrderType() {
		return wd_DelayOrderType;
	}

	public void setWd_DelayOrderType(Short wdDelayOrderType) {
		wd_DelayOrderType = wdDelayOrderType;
	}

	public Short getDelayQuoShowType() {
		return delayQuoShowType;
	}

	public void setDelayQuoShowType(Short delayQuoShowType) {
		this.delayQuoShowType = delayQuoShowType;
	}

	public Long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public String getFirmID() {
		return firmID;
	}

	public void setFirmID(String firmID) {
		this.firmID = firmID;
	}

	public String getConsignerID() {
		return consignerID;
	}

	public void setConsignerID(String consignerID) {
		this.consignerID = consignerID;
	}

	public String getCommodityID() {
		return commodityID;
	}

	public void setCommodityID(String commodityID) {
		this.commodityID = commodityID;
	}

	public Short getDelayOrderType() {
		return delayOrderType;
	}

	public void setDelayOrderType(Short delayOrderType) {
		this.delayOrderType = delayOrderType;
	}

	public Short getBuyOrSell() {
		return buyOrSell;
	}

	public void setBuyOrSell(Short buyOrSell) {
		this.buyOrSell = buyOrSell;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public String getTraderID() {
		return traderID;
	}

	public void setTraderID(String traderID) {
		this.traderID = traderID;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Long getTradeQty() {
		return tradeQty;
	}

	public void setTradeQty(Long tradeQty) {
		this.tradeQty = tradeQty;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Short getWithdrawType() {
		return withdrawType;
	}

	public void setWithdrawType(Short withdrawType) {
		this.withdrawType = withdrawType;
	}

	public String getWithdrawerID() {
		return withdrawerID;
	}

	public void setWithdrawerID(String withdrawerID) {
		this.withdrawerID = withdrawerID;
	}

	public Double getMargin() {
		return margin;
	}

	public Long getWithdrawID() {
		return withdrawID;
	}

	public void setWithdrawID(Long withdrawID) {
		this.withdrawID = withdrawID;
	}
	
	public void setMargin(Double margin) {
		this.margin = margin;
	}
    
    public String toString() {
    	return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
}