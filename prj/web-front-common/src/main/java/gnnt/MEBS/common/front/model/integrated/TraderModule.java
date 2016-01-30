package gnnt.MEBS.common.front.model.integrated;

import gnnt.MEBS.common.front.model.StandardModel;
import gnnt.MEBS.common.front.model.translate.ClassDiscription;

public class TraderModule extends StandardModel {
	private static final long serialVersionUID = -8054488919579229963L;
	@ClassDiscription(name = "模块号", description = "")
	private Integer moduleID;
	@ClassDiscription(name = "交易员", description = "")
	private User user;
	@ClassDiscription(name = "是否启用", description = "是否启用： Y：启用 Ｎ：禁用")
	private String enabled;

	public Integer getModuleID() {
		return this.moduleID;
	}

	public void setModuleID(Integer paramInteger) {
		this.moduleID = paramInteger;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User paramUser) {
		this.user = paramUser;
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
