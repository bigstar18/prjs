package gnnt.MEBS.common.front.model;

import gnnt.MEBS.common.front.model.translate.ClassDiscription;

public class TradeModule extends StandardModel {
	private static final long serialVersionUID = 986212660584678601L;
	@ClassDiscription(name = "交易模块ID", description = "")
	private Integer moduleID;
	@ClassDiscription(name = "交易模块中文名称", description = "")
	private String cnName;
	@ClassDiscription(name = "交易模块英文简称", description = "")
	private String enName;
	@ClassDiscription(name = "交易模块简称", description = "")
	private String shortName;
	@ClassDiscription(name = "添加交易商函数 ", description = "")
	private String addFirmFn;
	@ClassDiscription(name = "修改交易商状态函数", description = "")
	private String updateFirmStatusFn;
	@ClassDiscription(name = "删除交易商函数", description = "")
	private String delFirmFn;
	@ClassDiscription(name = "是否用于交易商设置", description = "是否用于交易商设置 Y：是 N：否 ")
	private String isFirmSet;
	@ClassDiscription(name = "是否需要综合系统增加商品", description = "是否需要综合系统增加商品： 需要 N：不需要")
	private String isNeedBreed;

	public Integer getModuleID() {
		return this.moduleID;
	}

	public void setModuleID(Integer paramInteger) {
		this.moduleID = paramInteger;
	}

	public String getCnName() {
		return this.cnName;
	}

	public void setCnName(String paramString) {
		this.cnName = paramString;
	}

	public String getEnName() {
		return this.enName;
	}

	public void setEnName(String paramString) {
		this.enName = paramString;
	}

	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(String paramString) {
		this.shortName = paramString;
	}

	public String getAddFirmFn() {
		return this.addFirmFn;
	}

	public void setAddFirmFn(String paramString) {
		this.addFirmFn = paramString;
	}

	public String getUpdateFirmStatusFn() {
		return this.updateFirmStatusFn;
	}

	public void setUpdateFirmStatusFn(String paramString) {
		this.updateFirmStatusFn = paramString;
	}

	public String getDelFirmFn() {
		return this.delFirmFn;
	}

	public void setDelFirmFn(String paramString) {
		this.delFirmFn = paramString;
	}

	public String getIsFirmSet() {
		return this.isFirmSet;
	}

	public void setIsFirmSet(String paramString) {
		this.isFirmSet = paramString;
	}

	public String getIsNeedBreed() {
		return this.isNeedBreed;
	}

	public void setIsNeedBreed(String paramString) {
		this.isNeedBreed = paramString;
	}

	public PrimaryInfo fetchPKey() {
		return new PrimaryInfo("moduleID", this.moduleID);
	}
}
