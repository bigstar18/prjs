package gnnt.MEBS.common.front.model.integrated;

import gnnt.MEBS.common.front.model.StandardModel;
import gnnt.MEBS.common.front.model.translate.ClassDiscription;

public class MSystemProps extends StandardModel {
	private static final long serialVersionUID = -5395993769556465143L;
	@ClassDiscription(name = "参数名", description = "")
	private String key;
	@ClassDiscription(name = "参数值", description = "")
	private String value;
	@ClassDiscription(name = "运行时值", description = "")
	private String runtimeValue;
	@ClassDiscription(name = "参数说明", description = "")
	private String note;

	public String getKey() {
		return this.key;
	}

	public void setKey(String paramString) {
		this.key = paramString;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String paramString) {
		this.value = paramString;
	}

	public String getRuntimeValue() {
		return this.runtimeValue;
	}

	public void setRuntimeValue(String paramString) {
		this.runtimeValue = paramString;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String paramString) {
		this.note = paramString;
	}

	public PrimaryInfo fetchPKey() {
		return new PrimaryInfo("key", this.key);
	}
}
