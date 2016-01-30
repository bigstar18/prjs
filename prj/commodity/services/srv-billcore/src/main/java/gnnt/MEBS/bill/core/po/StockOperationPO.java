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
 * [BI_STOCKOPERATION].仓单业务表
 * 
 * @author : xuejt
 * @version 1.0
 */
public class StockOperationPO extends Clone implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 167262291082320389L;
	private String stockID;
	private int operationID;

	/** 普通构造函数,注意:所有的属性都为NULL. */
	public StockOperationPO() {
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
	 * 业务号 0：拆单 1：融资2：卖仓单 3：交收
	 * 
	 * @return
	 */
	public int getOperationID() {
		return operationID;
	}

	/**
	 * 业务号 0：拆单 1：融资2：卖仓单 3：交收
	 * 
	 * @param operationID
	 */
	public void setOperationID(int operationID) {
		this.operationID = operationID;
	}


	/**
	 * 返回值对象调试信息字符串.
	 * 
	 * @return 值对象的调试信息
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[W_StockOperation:\n");

		sb.append("[String |").append("stockID|").append(this.stockID).append(
				"]\n");
		sb.append("[Long |").append("operationID|").append(this.operationID)
				.append("]\n");
		sb.append("]");
		return sb.toString();
	}
}