/**
 * ===========================================================================
 * Title:  javaBean
 * Description:  XHTRADE, Version 1.0
 * Copyright (c) 2010-12.  All rights reserved. 
 * ============================================================================
 * Author:xuejt.
 */

package gnnt.MEBS.bill.core.po;

import java.io.Serializable;


/**
 * [BI_STOCKCHGLOG]. 仓单变更记录
 * 
 * @author : xuejt
 * @version 1.0
 */
public class StockChgLogPO extends Clone implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4125074715401501824L;
	private String id;
	private String stockID;
	private String srcFirm;
	private String tarFirm;
	private java.util.Date createTime;

	/** 普通构造函数,注意:所有的属性都为NULL. */
	public StockChgLogPO() {
	}

	/**
	 * 变更记录编号
	 * 
	 * @return
	 */
	public String getID() {
		return id;
	}

	/**
	 * 变更记录编号
	 * 
	 * @param id
	 */
	public void setID(String id) {
		this.id = id;
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
	 * 原货权人
	 * 
	 * @return
	 */
	public String getSrcFirm() {
		return srcFirm;
	}

	/**
	 * 原货权人
	 * 
	 * @param srcFirm
	 */
	public void setSrcFirm(String srcFirm) {
		this.srcFirm = srcFirm;
	}

	/**
	 * 新货权人
	 * 
	 * @return
	 */
	public String getTarFirm() {
		return tarFirm;
	}

	/**
	 * 新货权人
	 * 
	 * @param tarFirm
	 */
	public void setTarFirm(String tarFirm) {
		this.tarFirm = tarFirm;
	}

	/**
	 *变更时间
	 * 
	 * @return
	 */
	public java.util.Date getcreateTime() {
		return createTime;
	}

	/**
	 * 变更时间
	 * 
	 * @param createTime
	 */
	public void setcreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 返回值对象调试信息字符串.
	 * 
	 * @return 值对象的调试信息
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[W_StockChgLogPO:\n");

		sb.append("[String |").append("id|").append(this.id).append("]\n");
		sb.append("[String |").append("stockID|").append(this.stockID).append(
				"]\n");
		sb.append("[String |").append("srcFirm|").append(this.srcFirm).append(
				"]\n");
		sb.append("[String |").append("tarFirm|").append(this.tarFirm).append(
				"]\n");
		sb.append("[java.util.Date|").append("createTime|").append(
				this.createTime).append("]\n");
		sb.append("]");
		return sb.toString();
	}
}