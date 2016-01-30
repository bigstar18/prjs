package gnnt.MEBS.integrated.front.model;

import gnnt.MEBS.common.front.model.StandardModel;
import gnnt.MEBS.common.front.model.translate.ClassDiscription;

public class Zone extends StandardModel {
	private static final long serialVersionUID = 977593279994999511L;
	@ClassDiscription(name = "地域编码", description = "")
	private String code;
	@ClassDiscription(name = "地域名称", description = "")
	private String name;

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

	public PrimaryInfo fetchPKey() {
		return new PrimaryInfo("code", this.code);
	}
}
