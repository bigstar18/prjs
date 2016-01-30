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
 * [BI_STOCKLOG].仓单操作日志
 * 
 * @author : xuejt
 * @version 1.0
 */
public class StockLogPO extends Clone implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3512204580573753268L;
	private long id;
	private long operationID;
	private java.util.Date createTime;
	private String remark;
	private String stockID;

	/** 普通构造函数,注意:所有的属性都为NULL. */
	public StockLogPO() {
	}

	/**
	 * 日志编号
	 * 
	 * @return
	 */
	public long getId() {
		return id;
	}

	/**
	 * 日志编号
	 * 
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * 操作标示1：注册 2：解除注册 3：出库  4：参与交易 5：拆单 6：融资
	 * 
	 * @return
	 */
	public long getOperationID() {
		return operationID;
	}
	
	/**
	 * 根据数字状态转换中文意思用于提示信息的展示
	 * @return
	 */
	public String getOperationIDMeaning(){
		String sta="";
		switch (Tool.strToInt(this.getOperationID()+"")) {
		case 1:
			sta="注册";
			break;
		case 2:
			sta="解除注册";
			break;
		case 3:
			sta="出库";
			break;
		case 4:
			sta="参与交易";
			break;
		case 5:
			sta="拆单";
			break;
		case 6:
			sta="融资";
			break;
		default:
			break;
		}
		return sta;
	}

	/**
	 * 操作标示 1：注册 2：解除注册 3：出库  4：参与交易 5：拆单 6：融资
	 * 
	 * @param operationID
	 */
	public void setOperationID(long operationID) {
		this.operationID = operationID;
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
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 备注
	 * 
	 * @return
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 备注
	 * 
	 * @param remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
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
	 * 返回值对象调试信息字符串.
	 * 
	 * @return 值对象的调试信息
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[W_StockLogPO:\n");

		sb.append("[Long |").append("id|").append(this.id).append("]\n");
		sb.append("[Long |").append("operationID|").append(this.operationID)
				.append("]\n");
		sb.append("[java.util.Date|").append("createTime|").append(
				this.createTime).append("]\n");
		sb.append("[String |").append("remark|").append(this.remark).append(
				"]\n");
		sb.append("[String |").append("stockID|").append(this.stockID).append(
				"]\n");
		sb.append("]");
		return sb.toString();
	}
}