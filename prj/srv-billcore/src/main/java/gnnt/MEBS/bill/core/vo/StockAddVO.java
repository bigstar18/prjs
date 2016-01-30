package gnnt.MEBS.bill.core.vo;

import java.util.List;

import gnnt.MEBS.bill.core.po.GoodsPropertyPO;

/**
 * 仓库系统添加仓单VO
 * 
 * @author liuzx
 */
public class StockAddVO extends ResultVO {
	private static final long serialVersionUID = -4254490545343840639L;
	/** 交易商代码 */
	private String firmID;
	/** 交易商名称 */
	private String firmName;
	/** 交易商密码 */
	private String wareHousePassword;
	/** 分类名称 */
	private String categoryName;
	/** 品名名称 */
	private String breedName;
	/** 商品单位 */
	private String unit;
	/** 商品数量 */
	private double quantity;
	/** 仓单单号 */
	private String stockID;
	/** 仓库编号 */
	private String wareHouseID;

	public String getWareHouseID() {
		return wareHouseID;
	}

	public void setWareHouseID(String wareHouseId) {
		this.wareHouseID = wareHouseId;
	}

	private String wareHouseName;
	/** 属性集合 */
	private List<GoodsPropertyPO> propertyList;

	/**
	 * 交易商代码
	 * 
	 * @return String
	 */
	public String getFirmID() {
		return firmID;
	}

	/**
	 * 交易商代码
	 * 
	 * @param firmID
	 */
	public void setFirmID(String firmID) {
		this.firmID = firmID;
	}

	/**
	 * 交易商名称
	 * 
	 * @return String
	 */
	public String getFirmName() {
		return firmName;
	}

	/**
	 * 交易商名称
	 * 
	 * @param firmName
	 */
	public void setFirmName(String firmName) {
		this.firmName = firmName;
	}

	/**
	 * 交易商密码
	 * 
	 * @return String
	 */
	public String getWareHousePassword() {
		return wareHousePassword;
	}

	/**
	 * 交易商密码
	 * 
	 * @param wareHousePassword
	 */
	public void setWareHousePassword(String wareHousePassword) {
		this.wareHousePassword = wareHousePassword;
	}

	/**
	 * 分类名称
	 * 
	 * @return String
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * 分类名称
	 * 
	 * @param categoryName
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/**
	 * 品名名称
	 * 
	 * @return String
	 */
	public String getBreedName() {
		return breedName;
	}

	/**
	 * 品名名称
	 * 
	 * @param breedName
	 */
	public void setBreedName(String breedName) {
		this.breedName = breedName;
	}

	/**
	 * 商品单位
	 * 
	 * @return
	 */
	public String getUnit() {
		return unit;
	}

	/**
	 * 商品单位
	 * 
	 * @param unit
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * 商品数量
	 * 
	 * @return double
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
	 * 仓单单号
	 * 
	 * @return String
	 */
	public String getStockID() {
		return stockID;
	}

	/**
	 * 仓单单号
	 * 
	 * @param stockID
	 */
	public void setStockID(String stockID) {
		this.stockID = stockID;
	}

	/**
	 * 仓库编号
	 * 
	 * @return String
	 */
	public String getWareHouseName() {
		return wareHouseName;
	}

	/**
	 * 仓库编号
	 * 
	 * @param wareHouseID
	 */
	public void setWareHouseName(String wareHouseID) {
		this.wareHouseName = wareHouseID;
	}

	/**
	 * 属性集合
	 * 
	 * @return List<W_GoodsPropertyPO>
	 */
	public List<GoodsPropertyPO> getPropertyList() {
		return propertyList;
	}

	/**
	 * 属性集合
	 * 
	 * @param propertyList
	 */
	public void setPropertyList(List<GoodsPropertyPO> propertyList) {
		this.propertyList = propertyList;
	}
}
