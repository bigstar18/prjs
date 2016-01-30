package gnnt.MEBS.common.front.model.integrated;

import gnnt.MEBS.common.front.model.StandardModel;
import gnnt.MEBS.common.front.model.translate.ClassDiscription;

public class MFirmModule extends StandardModel {
	private static final long serialVersionUID = -8054488919579229963L;
	@ClassDiscription(name = "模块号", description = "")
	private Integer moduleID;
	@ClassDiscription(name = "关联交易商", description = "关联对应的交易商代码")
	private MFirm firm;
	@ClassDiscription(name = "是否启用", description = "是否启用: Y：启用  N：禁用，默认禁用")
	private String enabled;

	public Integer getModuleID() {
		return this.moduleID;
	}

	public void setModuleID(Integer paramInteger) {
		this.moduleID = paramInteger;
	}

	public MFirm getFirm() {
		return this.firm;
	}

	public void setFirm(MFirm paramMFirm) {
		this.firm = paramMFirm;
	}

	public String getEnabled() {
		return this.enabled;
	}

	public void setEnabled(String paramString) {
		this.enabled = paramString;
	}

	public PrimaryInfo fetchPKey() {
		return null;
	}
}
