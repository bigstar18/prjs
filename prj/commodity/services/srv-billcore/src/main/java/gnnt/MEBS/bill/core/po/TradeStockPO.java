/**
 * ===========================================================================
 * Title:  javaBean
 * Description:  XHTRADE, Version 1.0
 * Copyright (c) 2010-12.  All rights reserved. 
 * ============================================================================
 * Author:xuejt.
 */

package gnnt.MEBS.bill.core.po;

import gnnt.MEBS.bill.core.util.Tool;

import java.io.Serializable;


/**
 * [BI_TRADESTOCK]. 交收仓单
 * 
 * @author : xuejt
 * @version 1.0
 */
public class TradeStockPO extends Clone implements Serializable {
	private static final long serialVersionUID = 2268559476332236098L;
	
	private long tradestockID;
	private String stockID;
	private int moduleid;
	private String tradeNO;
	private java.util.Date createTime;
	private java.util.Date releaseTime;
	private long status;

	/** 普通构造函数,注意:所有的属性都为NULL. */
	public TradeStockPO() {
	}

	/**
	 * 交易仓单编号
	 * 
	 * @return
	 */
	public long getTradestockID() {
		return tradestockID;
	}

	/**
	 * 交易仓单编号
	 * 
	 * @param tradestockID
	 */
//	public void setTradestockID(long tradestockID) {
//		this.tradestockID = tradestockID;
//	}

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
	public String getTradeNO() {
		return tradeNO;
	}

	/**
	 * 用于交易的合同号
	 * 
	 * @param tradeNO
	 */
	public void setTradeNO(String tradeNO) {
		this.tradeNO = tradeNO;
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
//	public void setCreateTime(java.util.Date createTime) {
//		this.createTime = createTime;
//	}

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

		sb.append("[Long |").append("tradestockID|").append(this.tradestockID)
				.append("]\n");
		sb.append("[String |").append("stockID|").append(this.stockID).append(
				"]\n");
		sb.append("[Long |").append("tradeNO|").append(this.tradeNO).append(
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