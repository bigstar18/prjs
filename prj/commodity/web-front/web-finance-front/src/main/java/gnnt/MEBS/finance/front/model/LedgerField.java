package gnnt.MEBS.finance.front.model;

import gnnt.MEBS.common.front.model.StandardModel;
import gnnt.MEBS.common.front.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.front.model.translate.ClassDiscription;

public class LedgerField extends StandardModel {
	private static final long serialVersionUID = 5784146401111672026L;

	@ClassDiscription(name = "总账字段代码", description = "")
	private String code;

	@ClassDiscription(name = "总账字段名称", description = "")
	private String name;

	@ClassDiscription(name = "增减项符号", description = "")
	private Integer fieldSign;

	@ClassDiscription(name = "模块ID", description = "")
	private String moduleId;

	@ClassDiscription(name = "排序号", description = "")
	private Integer orderNum;

	public String getCode() {
		return this.code;
	}

	public String getName() {
		return this.name;
	}

	public Integer getFieldSign() {
		return this.fieldSign;
	}

	public String getModuleId() {
		return this.moduleId;
	}

	public Integer getOrderNum() {
		return this.orderNum;
	}

	public void setCode(String paramString) {
		this.code = paramString;
	}

	public void setName(String paramString) {
		this.name = paramString;
	}

	public void setFieldSign(Integer paramInteger) {
		this.fieldSign = paramInteger;
	}

	public void setModuleId(String paramString) {
		this.moduleId = paramString;
	}

	public void setOrderNum(Integer paramInteger) {
		this.orderNum = paramInteger;
	}

	public StandardModel.PrimaryInfo fetchPKey() {
		return new StandardModel.PrimaryInfo("code", this.code);
	}
}