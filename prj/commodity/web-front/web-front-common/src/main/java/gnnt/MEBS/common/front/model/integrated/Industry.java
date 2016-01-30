package gnnt.MEBS.common.front.model.integrated;

import gnnt.MEBS.common.front.model.StandardModel;
import gnnt.MEBS.common.front.model.translate.ClassDiscription;

public class Industry extends StandardModel {
	private static final long serialVersionUID = -5164424968558888161L;
	@ClassDiscription(name = "行业编码", description = "")
	private String code;
	@ClassDiscription(name = "行业名称", description = "")
	private String name;
	@ClassDiscription(name = "是否显示", description = "是否显示 Y：显示 N：不显示")
	private String isvisibal;
	@ClassDiscription(name = "排序号", description = "")
	private Integer sortNo;

	public String getCode() {
		return this.code;
	}

	public void setCode(String paramString) {
		this.code = paramString;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String paramString) {
		this.name = paramString;
	}

	public String getIsvisibal() {
		return this.isvisibal;
	}

	public void setIsvisibal(String paramString) {
		this.isvisibal = paramString;
	}

	public Integer getSortNo() {
		return this.sortNo;
	}

	public void setSortNo(Integer paramInteger) {
		this.sortNo = paramInteger;
	}

	public PrimaryInfo fetchPKey() {
		return new PrimaryInfo("code", this.code);
	}
}
