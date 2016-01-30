package gnnt.MEBS.common.front.model.integrated;

import java.util.Date;

import gnnt.MEBS.common.front.model.StandardModel;
import gnnt.MEBS.common.front.model.TradeModule;
import gnnt.MEBS.common.front.model.translate.ClassDiscription;

public class ErrorLoginLog extends StandardModel {
	private static final long serialVersionUID = 5879284003784001954L;
	@ClassDiscription(name = "交易员ID", description = "")
	private String traderID;
	@ClassDiscription(name = "登录日期", description = "")
	private Date loginDate;
	@ClassDiscription(name = "所属交易模块号", description = "")
	private TradeModule tradeModule;
	@ClassDiscription(name = " 登录IP", description = "")
	private String ip;

	public String getTraderID() {
		return this.traderID;
	}

	public void setTraderID(String paramString) {
		this.traderID = paramString;
	}

	public Date getLoginDate() {
		return this.loginDate;
	}

	public void setLoginDate(Date paramDate) {
		this.loginDate = paramDate;
	}

	public TradeModule getTradeModule() {
		return this.tradeModule;
	}

	public void setTradeModule(TradeModule paramTradeModule) {
		this.tradeModule = paramTradeModule;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String paramString) {
		this.ip = paramString;
	}

	public PrimaryInfo fetchPKey() {
		return null;
	}
}
