package gnnt.trade.bank.vo.bankdz.boc;

import java.io.Serializable;

public class StorageBankCashReserveBalance  implements Serializable {
	//存管银行备付金余额表  S12YYYYMMDD
	//银行代码	CHAR(4)	中国银行：004 
	private String BankCode;
	//交易所代码	CHAR(8)	8位交易所代码（证监会分配）
	private String MarketCode;
	//日期	CHAR(8)	YYYYMMDD
	private String TransDateTime;
	//币别	CHAR(3)	001：人民币 013：港币 014：美元
	private String MoneyType;
	//钞汇标示	CHAR(1)	0钞 1汇
	private String CashExCode;
	//交收金额	INT(14)	
	private int money;
	public String getBankCode() {
		return BankCode;
	}
	public void setBankCode(String bankCode) {
		BankCode = bankCode;
	}
	public String getMarketCode() {
		return MarketCode;
	}
	public void setMarketCode(String marketCode) {
		MarketCode = marketCode;
	}
	public String getTransDateTime() {
		return TransDateTime;
	}
	public void setTransDateTime(String transDateTime) {
		TransDateTime = transDateTime;
	}
	public String getMoneyType() {
		return MoneyType;
	}
	public void setMoneyType(String moneyType) {
		MoneyType = moneyType;
	}
	public String getCashExCode() {
		return CashExCode;
	}
	public void setCashExCode(String cashExCode) {
		CashExCode = cashExCode;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
}
