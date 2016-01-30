package gnnt.MEBS.common.front.model.front;

import gnnt.MEBS.common.front.model.StandardModel;
import gnnt.MEBS.common.front.model.translate.ClassDiscription;

public class MyMenu extends StandardModel {
	private static final long serialVersionUID = -4801559198487094801L;
	@ClassDiscription(name = "所属交易员 ", description = "")
	private String userID;
	@ClassDiscription(name = "所属权限", description = "")
	private Right belongtoRight;

	public String getUserID() {
		return this.userID;
	}

	public void setUserID(String paramString) {
		this.userID = paramString;
	}

	public Right getBelongtoRight() {
		return this.belongtoRight;
	}

	public void setBelongtoRight(Right paramRight) {
		this.belongtoRight = paramRight;
	}

	public PrimaryInfo fetchPKey() {
		return null;
	}
}
