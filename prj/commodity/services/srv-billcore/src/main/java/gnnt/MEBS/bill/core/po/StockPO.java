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
 * [BI_STOCK].仓单系统 仓单对象
 * 
 * @author : xuejt
 * @version 1.0
 */
public class StockPO extends Clone implements Serializable {
	private static final long serialVersionUID = 5520958349945920541L;

	private String stockID;
	private String realStockCode;
	private long breedID;
	private String warehouseID;
	private double quantity;
	private String unit;
	private String ownerFirm;
	private java.util.Date lastTime;

	private java.util.Date createTime;
	private int stockStatus;
	private String key;

	/** 普通构造函数,注意:所有的属性都为NULL. */
	public StockPO() {
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
	 * @return 原始仓单号
	 */
	public String getRealStockCode() {
		return realStockCode;
	}

	/**
	 * @param 原始仓单号
	 */
	public void setRealStockCode(String realStockCode) {
		this.realStockCode = realStockCode;
	}

	/**
	 * 商品品名代码
	 * 
	 * @return
	 */
	public long getBreedID() {
		return breedID;
	}

	/**
	 * 商品品名代码
	 * 
	 * @param goodsCategoryID
	 */
	public void setBreedID(long breedID) {
		this.breedID = breedID;
	}

	/**
	 * 仓库编号
	 * 
	 * @return
	 */
	public String getWarehouseID() {
		return warehouseID;
	}

	/**
	 * 仓库编号
	 * 
	 * @param warehouseID
	 */
	public void setWarehouseID(String warehouseID) {
		this.warehouseID = warehouseID;
	}

	/**
	 * 商品数量
	 * 
	 * @return
	 */
	public double getQuantity() {
		return quantity;
	}

	/**
	 * 商品数量
	 * 
	 * @param quantity
	 */
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	/**
	 * 仓单所属交易商
	 * 
	 * @return
	 */
	public String getOwnerFirm() {
		return ownerFirm;
	}

	/**
	 * 仓单所属交易商
	 * 
	 * @param ownerFirm
	 */
	public void setOwnerFirm(String ownerFirm) {
		this.ownerFirm = ownerFirm;
	}

	/**
	 * 最后变更时间
	 * 
	 * @return
	 */
	public java.util.Date getLastTime() {
		return lastTime;
	}

	/**
	 * 最后变更时间
	 * 
	 * @param lastTime
	 */
	public void setLastTime(java.util.Date lastTime) {
		this.lastTime = lastTime;
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
	 * 仓单状态 0:未注册仓单 1：注册仓单 2：已出库仓单 3、已拆仓单 4、拆单处理中
	 * 
	 * @return
	 */
	public int getStockStatus() {
		return stockStatus;
	}
	
	/**
	 * 根据数字状态转换中文意思用于提示信息的展示
	 * @return
	 */
	public String getStockStatusMeaning(){
		String sta="";
		switch (Tool.strToInt(this.getStockStatus()+"")) {
		case 0:
			sta="未注册仓单";
			break;
		case 1:
			sta="注册仓单";
			break;
		case 2:
			sta="已出库仓单";
			break;
		case 3:
			sta="已拆仓单";
			break;
		case 4:
			sta="拆单处理中";
			break;
		default:
			break;
		}
		return sta;
	}

	/**
	 * 仓单状态 0:未注册仓单 1：注册仓单 2：已出库仓单 3、已拆仓单 4、拆单处理中
	 * 
	 * @param stockStatus
	 */
	public void setStockStatus(int stockStatus) {
		this.stockStatus = stockStatus;
	}

	/**
	 * 
	 * 商品单位
	 * 
	 * @return
	 */
	public String getUnit() {
		return unit;
	}

	/**
	 * 
	 * 商品单位
	 * 
	 * @param unit
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * @return 出库密钥
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param 出库密钥
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * 返回值对象调试信息字符串.
	 * 
	 * @return 值对象的调试信息
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[StockPO:\n");

		sb.append("[String |").append("stockID|").append(this.stockID).append(
				"]\n");
		sb.append("[Long |").append("breedID|").append(this.breedID).append(
				"]\n");
		sb.append("[Long |").append("warehouseID|").append(this.warehouseID)
				.append("]\n");
		sb.append("[Long |").append("quantity|").append(this.quantity).append(
				"]\n");
		sb.append("[String |").append("ownerFirm|").append(this.ownerFirm)
				.append("]\n");
		sb.append("[java.util.Date|").append("lastTime|").append(this.lastTime)
				.append("]\n");
		sb.append("[java.util.Date|").append("createTime|").append(
				this.createTime).append("]\n");
		sb.append("[int |").append("stockStatus|").append(this.stockStatus)
				.append("]\n");
		sb.append("[String |").append("key|").append(this.key).append("]\n");
		sb.append("]");
		return sb.toString();
	}
}