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
 * [BI_FINANCINGSTOCK]. 融资仓单
 * 
 * @author : xuejt
 * @version 1.0
 */
public class FinancingStockPO extends Clone implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3670139568181159690L;
	private long financingstockID;
	private String stockID;
	private java.util.Date createTime;
	private java.util.Date releaseTime;
	private String status;

	/** 普通构造函数,注意:所有的属性都为NULL. */
	public FinancingStockPO() {
	}

	/**
	 * 融资仓单编号
	 * 
	 * @return
	 */
	public long getFinancingstockID() {
		return financingstockID;
	}

	/**
	 * 融资仓单编号
	 * 
	 * @param financingstockID
	 */
	public void setFinancingstockID(long financingstockID) {
		this.financingstockID = financingstockID;
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
	 * 状态 'Y' 有效  'N' 无效
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
		if("Y".equals(this.getStatus())){
			sta="有效";
		}else if("N".equals(this.getStatus())){
			sta="无效";
		}
		return sta;
	}
	/**
	 * 状态 'Y' 有效  'N' 无效
	 * 
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 返回值对象调试信息字符串.
	 * 
	 * @return 值对象的调试信息
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[W_FinancingStockPO:\n");

		sb.append("[Long |").append("financingstockID|").append(
				this.financingstockID).append("]\n");
		sb.append("[String |").append("stockID|").append(this.stockID).append(
				"]\n");
		sb.append("[java.util.Date|").append("createTime|").append(
				this.createTime).append("]\n");
		sb.append("[java.util.Date|").append("releaseTime|").append(
				this.releaseTime).append("]\n");
		sb.append("[String |").append("status|").append(this.status).append(
				"]\n");
		sb.append("]");
		return sb.toString();
	}
}