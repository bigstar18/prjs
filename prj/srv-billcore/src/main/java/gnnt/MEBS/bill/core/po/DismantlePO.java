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
 * [BI_DISMANTLE]. 拆单信息
 * 
 * @author : xuejt
 * @version 1.0
 */
public class DismantlePO extends Clone implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 639299049429913317L;

	private long dismantleID;
	private String realStockCode;
	private double amount;
	private String newstockID;
	private java.util.Date askTime;
	private java.util.Date processTime;
	private String status;
	private String stockID;

	/** 普通构造函数,注意:所有的属性都为NULL. */
	public DismantlePO() {
	}

	/**
	 * 拆单表编号
	 * 
	 * @return
	 */
	public long getDismantleID() {
		return dismantleID;
	}

	/**
	 * 拆单表编号
	 * 
	 * @param dismantleID
	 */
	public void setDismantleID(long dismantleID) {
		this.dismantleID = dismantleID;
	}

	
	/**
	 * @return 仓库真实仓单号
	 */
	public String getRealStockCode() {
		return realStockCode;
	}

	/**
	 * @param 仓库真实仓单号
	 */
	public void setRealStockCode(String realStockCode) {
		this.realStockCode = realStockCode;
	}
	
	/**
	 * 拆单后数量
	 * 
	 * @return
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * 拆单后数量
	 * 
	 * @param amount
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * 新仓单号
	 * 
	 * @return
	 */
	public String getNewstockID() {
		return newstockID;
	}

	/**
	 * 新仓单号
	 * 
	 * @param newstockID
	 */
	public void setNewstockID(String newstockID) {
		this.newstockID = newstockID;
	}

	/**
	 * 申请时间
	 * 
	 * @return
	 */
	public java.util.Date getAskTime() {
		return askTime;
	}

	/**
	 * 申请时间
	 * 
	 * @param askTime
	 */
	public void setAskTime(java.util.Date askTime) {
		this.askTime = askTime;
	}

	/**
	 * 处理时间
	 * 
	 * @return
	 */
	public java.util.Date getProcessTime() {
		return processTime;
	}

	/**
	 * 处理时间
	 * 
	 * @param processTime
	 */
	public void setProcessTime(java.util.Date processTime) {
		this.processTime = processTime;
	}

	/**
	 * 状态 0:申请中 1：拆单成功 2：拆单失败
	 * 
	 * @return
	 */
	public String getStatus() {
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
			sta="申请中";
			break;
		case 1:
			sta="拆单成功";
			break;
		case 2:
			sta="拆单失败";
			break;
		default:
			break;
		}
		return sta;
	}

	/**
	 * 状态  0:申请中 1：拆单成功 2：拆单失败
	 * 
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 拆单前仓单号
	 * 
	 * @return
	 */
	public String getStockID() {
		return stockID;
	}

	/**
	 * 拆单前仓单号
	 * 
	 * @param stockID
	 */
	public void setStockID(String stockID) {
		this.stockID = stockID;
	}

	/**
	 * 返回值对象调试信息字符串.
	 * 
	 * @return 值对象的调试信息
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[W_DismantlePO:\n");

		sb.append("[Long |").append("dismantleID|").append(this.dismantleID)
				.append("]\n");
		sb.append("[Long |").append("amount|").append(this.amount)
				.append("]\n");
		sb.append("[Long |").append("newstockID|").append(this.newstockID)
				.append("]\n");
		sb.append("[java.util.Date|").append("askTime|").append(this.askTime)
				.append("]\n");
		sb.append("[java.util.Date|").append("processTime|").append(
				this.processTime).append("]\n");
		sb.append("[String |").append("status|").append(this.status).append(
				"]\n");
		sb.append("[String |").append("stockID|").append(this.stockID).append(
				"]\n");
		sb.append("]");
		return sb.toString();
	}
}