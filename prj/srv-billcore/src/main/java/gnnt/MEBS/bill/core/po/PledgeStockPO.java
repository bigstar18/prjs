package gnnt.MEBS.bill.core.po;

import gnnt.MEBS.bill.core.util.Tool;

import java.io.Serializable;

/**
 * [BI_PledgeStock] 卖仓单
 * 
 * @author xuejt
 * 
 */
public class PledgeStockPO extends Clone implements Serializable {

	private static final long serialVersionUID = -4053761232214768086L;

	private long pledgeStock;
	private String stockID;
	private int moduleid;
	private String orderID;
	private java.util.Date createTime;
	private java.util.Date releaseTime;
	private long status;

	/** 普通构造函数,注意:所有的属性都为NULL. */
	public PledgeStockPO() {
	}

	/**
	 * 卖仓单编号
	 * 
	 * @return
	 */
	public long getTradestockID() {
		return pledgeStock;
	}

	/**
	 * 仓单号
	 * 
	 * @return
	 */
	public String getStockID() {
		return stockID;
	}

	/**
	 * 仓单号
	 * 
	 * @param stockID
	 */
	public void setStockID(String stockID) {
		this.stockID = stockID;
	}

	/**
	 * @return 模块号
	 */
	public int getModuleid() {
		return moduleid;
	}

	/**
	 * @param 模块号
	 */
	public void setModuleid(int moduleid) {
		this.moduleid = moduleid;
	}

	/**
	 * 用于交易的合同号
	 * 
	 * @return
	 */
	public String getOrderID() {
		return orderID;
	}

	/**
	 * 用于交易的合同号
	 * 
	 * @param orderID
	 */
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	/**
	 * 创建时间
	 * 
	 * @return
	 */
	public java.util.Date getCreateTime() {
		return createTime;
	}

	/**
	 * 创建时间
	 * 
	 * @param createTime
	 */
	// public void setCreateTime(java.util.Date createTime) {
	// this.createTime = createTime;
	// }

	/**
	 * 释放时间
	 * 
	 * @return
	 */
	public java.util.Date getReleaseTime() {
		return releaseTime;
	}

	/**
	 * 释放时间
	 * 
	 * @param releaseTime
	 */
	public void setReleaseTime(java.util.Date releaseTime) {
		this.releaseTime = releaseTime;
	}

	/**
	 * 状态 0:仓单使用中 1：交易成功仓单释放状态
	 * 
	 * @return
	 */
	public long getStatus() {
		return status;
	}
	
	/**
	 * 根据数字状态转换中文意思用于提示信息的展示
	 * @return
	 */
	public String getStatusMeaning(){
		String sta="";
		switch (Tool.strToInt(this.getStatus()+"")) {
		case 0:
			sta="仓单使用中";
			break;
		case 1:
			sta="交易成功仓单释放状态";
			break;
		default:
			break;
		}
		return sta;
	}

	/**
	 * 状态 0:仓单使用中 1：交易成功仓单释放状态
	 * 
	 * @param status
	 */
	public void setStatus(long status) {
		this.status = status;
	}

	/**
	 * 返回值对象调试信息字符串.
	 * 
	 * @return 值对象的调试信息
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[W_TradeStockPO:\n");

		sb.append("[Long |").append("pledgeStock|").append(this.pledgeStock)
				.append("]\n");
		sb.append("[String |").append("stockID|").append(this.stockID).append(
				"]\n");
		sb.append("[Long |").append("orderID|").append(this.orderID).append(
				"]\n");
		sb.append("[java.util.Date|").append("createTime|").append(
				this.createTime).append("]\n");
		sb.append("[java.util.Date|").append("releaseTime|").append(
				this.releaseTime).append("]\n");
		sb.append("[Long |").append("status|").append(this.status)
				.append("]\n");
		sb.append("]");
		return sb.toString();
	}
}