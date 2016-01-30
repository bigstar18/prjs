package gnnt.MEBS.common.mgr.model;

import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

/**
 * 交易模块表
 * @author liuzx
 */
public class TradeModule extends StandardModel{
	private static final long serialVersionUID = -3662370087483814027L;

	/** 交易模块ID */
	@ClassDiscription(name="交易模块ID",description="")
	private Integer moduleId;

	/** 交易模块中文名称 */
	@ClassDiscription(name="交易模块中文名称",description="")
	private String cnName;

	/** 交易模块英文简称 */
	@ClassDiscription(name="交易模块英文简称",description="")
	private String enName;

	/** 交易模块简称 */
	@ClassDiscription(name="交易模块简称",description="")
	private String shortName;

	/** 添加交易商函数 */
	@ClassDiscription(name="添加交易商函数",description="")
	private String addFirmFn;

	/** 修改交易商状态函数 */
	@ClassDiscription(name="修改交易商状态函数",description="")
	private String updateFirmStatusFn;

	/** 删除交易商函数 */
	@ClassDiscription(name="删除交易商函数",description="")
	private String delFirmFn;

	/**
	 * 是否用于交易商设置<br/>
	 * ‘Y’ 是 ‘N’ 否
	 */
	@ClassDiscription(name="是否用于交易商设置",description="是否用于交易商设置 Y：是 N：否")
	private String isFirmSet;

	/**
	 * 是否需要综合系统增加商品<br/>
	 * Y 需要 N 不需要
	 */
	private String isNeedBreed;

	/**
	 * 交易模块ID
	 * @return Integer
	 */
	public Integer getModuleId() {
		return moduleId;
	}

	/**
	 * 交易模块ID
	 * @param moduleID
	 */
	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	/**
	 * 交易模块中文名称
	 * @return String
	 */
	public String getCnName() {
		return cnName;
	}

	/**
	 * 交易模块中文名称
	 * @param cnName
	 */
	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

	/**
	 * 交易模块英文简称
	 * @return String
	 */
	public String getEnName() {
		return enName;
	}

	/**
	 * 交易模块英文简称
	 * @param enName
	 */
	public void setEnName(String enName) {
		this.enName = enName;
	}

	/**
	 * 交易模块简称
	 * @return String
	 */
	public String getShortName() {
		return shortName;
	}

	/**
	 * 交易模块简称
	 * @param shortName
	 */
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	/**
	 * 添加交易商函数
	 * @return String
	 */
	public String getAddFirmFn() {
		return addFirmFn;
	}

	/**
	 * 添加交易商函数
	 * @param addFirmFn
	 */
	public void setAddFirmFn(String addFirmFn) {
		this.addFirmFn = addFirmFn;
	}

	/**
	 * 修改交易商状态函数
	 * @return String
	 */
	public String getUpdateFirmStatusFn() {
		return updateFirmStatusFn;
	}

	/**
	 * 修改交易商状态函数
	 * @param updateFirmStatusFn
	 */
	public void setUpdateFirmStatusFn(String updateFirmStatusFn) {
		this.updateFirmStatusFn = updateFirmStatusFn;
	}

	/**
	 * 删除交易商函数
	 * @return String
	 */
	public String getDelFirmFn() {
		return delFirmFn;
	}

	/**
	 * 删除交易商函数
	 * @param delFirmFn
	 */
	public void setDelFirmFn(String delFirmFn) {
		this.delFirmFn = delFirmFn;
	}

	/**
	 * 是否用于交易商设置<br/>
	 * ‘Y’ 是 ‘N’ 否
	 * @return String
	 */
	public String getIsFirmSet() {
		return isFirmSet;
	}

	/**
	 * 是否用于交易商设置<br/>
	 * ‘Y’ 是 ‘N’ 否
	 * @param isFirmSet
	 */
	public void setIsFirmSet(String isFirmSet) {
		this.isFirmSet = isFirmSet;
	}

	/**
	 * 
	 * 是否需要综合系统增加商品<br/>
	 * Y 需要 N 不需要
	 * <br/><br/>
	 * @return
	 */
	public String getIsNeedBreed() {
		return isNeedBreed;
	}

	/**
	 * 
	 * 是否需要综合系统增加商品<br/>
	 * Y 需要 N 不需要
	 * <br/><br/>
	 * @param isNeedBreed
	 */
	public void setIsNeedBreed(String isNeedBreed) {
		this.isNeedBreed = isNeedBreed;
	}

	@Override
	public PrimaryInfo fetchPKey() {
		return new PrimaryInfo("moduleId",moduleId);
	}

}
