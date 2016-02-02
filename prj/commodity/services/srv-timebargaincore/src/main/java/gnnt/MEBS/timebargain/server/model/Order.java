package gnnt.MEBS.timebargain.server.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 委托对象.
 *
 * <p><a href="Order.java.html"><i>View Source</i></a></p>
 *
 * @version 1.0.0.1
 * @author <a href="mailto:chenxc@gnnt.com.cn">chenxc</a>
 */
public class Order implements Serializable {
	private static final long serialVersionUID = 1690197650654049814L;
	/**
	 * 买委托
	 */
	public static final short ORDER_BUY = 1; 
	/**
	 * 卖委托
	 */
	public static final short ORDER_SELL = 2;
	/**
	 * 开仓委托
	 */
	public static final short ORDER_TYPE_OPEN = 1;
	/**
	 * 平仓委托
	 */
	public static final short ORDER_TYPE_CLOSE = 2;
	/**
	 * 撤单委托
	 */
	public static final short ORDER_TYPE_CANCEL = 4;
	
	/**
	 * 正常平仓委托
	 */
	public static final short CLOSE_FLAG_NORMAL = 0;
	/**
	 * 代为委托员平仓委托
	 */
	public static final short CLOSE_FLAG_CONSIGNER = 1;
	/**
	 * 强制平仓委托
	 */
	public static final short CLOSE_FLAG_FORCE = 2;
	
	/**
	 * 正常未成交委托
	 */
	public static final short STATUS_NORMAL = 1;
	/**
	 * 部分成交
	 */
	public static final short STATUS_PARTIAL = 2;
	
	/**
	 * 全部成交
	 */
	public static final short STATUS_ALL = 3;
	
	/**
	 * 正在撤单
	 */
	public static final short STATUS_CANCELING = 4;
	/**
	 * 已撤单
	 */
	public static final short STATUS_CANCELED = 5;
	
	/**
	 * 部分成交后撤单
	 */
	public static final short STATUS_PARTIALCANCELED = 6;
	

	/**
	 * 委托撤单
	 */
	public static final short WITHDRAWTYPE_ORDER = 1;
	/**
	 * 自动撤单
	 */
	public static final short WITHDRAWTYPE_AUTO = 4;
	
	/**
	 * 自动撤销特殊单 add by xuejt 2011-11-14
	 */
	public static final short WITHDRAWTYPE_SPECIAL = 10;
    //平仓模式
    public static final short CLOSEMODE_NOTSPEC = 1;//不指定
	public static final short CLOSEMODE_SPECTIME = 2;//指定时间
	public static final short CLOSEMODE_SPECPRICE = 3;//指定价格
	
	//仓单交易类型
	public static final short BILLTRADETYPE_DEFAULT = 0;//正常 默认
	public static final short BILLTRADETYPE_SELLBILL = 1;//卖仓单
	public static final short BILLTRADETYPE_GAGE = 2;//抵顶转让
	
	private Long orderNo; // 委托单号

	private String customerID; // 客户ID
	
	private String firmID; // 交易商ID
	
	private String consignerID; //委托员ID

	private String commodityID; // 商品代码

	private Short orderType; // 委托类型，1：开仓；2：平仓；4：撤单

	private Short buyOrSell; // 买卖标志，1：买；2：卖

	private Double price; // 价格

	private Long quantity; // 数量

	private Short closeMode; // 平仓模式

	private Double specPrice; // 指定价格,平仓价格（用于指定价格平仓）

	private Short specTime; // 指定时间标志,开仓时间标志（用于指定时间平仓）

	private Long withdrawID; // 被撤委托单号

	private String traderID; // 交易员ID

	private Date orderTime;  //委托时间
	
	private Long tradeQty = new Long(0);  //已成交数量
	    
    private Double margin;//委托应收保证金
    
    private Double fee; //委托应收手续费 
    
    private Short closeFlag; // 平仓标志
    
    private Short status; // 状态
    
    private Short closeAlgr;  //平仓算法
    
    private Short withdrawType; // 撤单类型
    
    private String withdrawerID; // 撤单员ID
    
    private Short BillTradeType;//仓单交易类型

	public static final short NORMALORDER = 0;// 正常委托
	public static final short SPECIALORDER = 1;// 特殊委托
	private Short specialOrderFlag = 0;// 是否特殊委托(0：正常委托 1：特殊委托) 特殊委托只能和特殊委托成交
										// add by xuejt 2011-10-17

	// 2011-10-14

	public String toString() {
		//更改多行样式为默认样式
    	return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
    
    /**
     * @return 待成交数量
     */
    public Long getRemainQty(){
    	return quantity - tradeQty;
    }

	public Short getBuyOrSell() {
		return buyOrSell;
	}

	public void setBuyOrSell(Short buyOrSell) {
		this.buyOrSell = buyOrSell;
	}

	public Short getCloseAlgr() {
		return closeAlgr;
	}

	public void setCloseAlgr(Short closeAlgr) {
		this.closeAlgr = closeAlgr;
	}

	public Short getCloseFlag() {
		return closeFlag;
	}

	public void setCloseFlag(Short closeFlag) {
		this.closeFlag = closeFlag;
	}

	public Short getCloseMode() {
		return closeMode;
	}

	public void setCloseMode(Short closeMode) {
		this.closeMode = closeMode;
	}

	public String getCommodityID() {
		return commodityID;
	}

	public void setCommodityID(String commodityID) {
		this.commodityID = commodityID;
	}

	public String getConsignerID() {
		return consignerID;
	}

	public void setConsignerID(String consignerID) {
		this.consignerID = consignerID;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public String getFirmID() {
		return firmID;
	}

	public void setFirmID(String firmID) {
		this.firmID = firmID;
	}

	public Double getMargin() {
		return margin;
	}

	public void setMargin(Double margin) {
		this.margin = margin;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Short getOrderType() {
		return orderType;
	}

	public void setOrderType(Short orderType) {
		this.orderType = orderType;
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

	public Double getSpecPrice() {
		return specPrice;
	}

	public void setSpecPrice(Double specPrice) {
		this.specPrice = specPrice;
	}

	public Short getSpecTime() {
		return specTime;
	}

	public void setSpecTime(Short specTime) {
		this.specTime = specTime;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Long getTradeQty() {
		return tradeQty;
	}

	public void setTradeQty(Long tradeQty) {
		this.tradeQty = tradeQty;
	}

	public String getTraderID() {
		return traderID;
	}

	public void setTraderID(String traderID) {
		this.traderID = traderID;
	}

	public Long getWithdrawID() {
		return withdrawID;
	}

	public void setWithdrawID(Long withdrawID) {
		this.withdrawID = withdrawID;
	}

	public Long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
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

    public Short getBillTradeType() {
		return BillTradeType;
	}

	public void setBillTradeType(Short billTradeType) {
		BillTradeType = billTradeType;
	}

	public Short getSpecialOrderFlag() {
		return specialOrderFlag;
	}

	public void setSpecialOrderFlag(Short specialOrderFlag) {
		this.specialOrderFlag = specialOrderFlag;
	}
}