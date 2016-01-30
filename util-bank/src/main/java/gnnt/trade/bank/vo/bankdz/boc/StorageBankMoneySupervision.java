package gnnt.trade.bank.vo.bankdz.boc;

import java.io.Serializable;

public class StorageBankMoneySupervision  implements Serializable {
	//协办存管银行资金监管表  S13YYYYMMDD
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
	//资金账户汇总金额	INT(14)	
	private int CapitalAccountSummaryMoney;
	//汇总账户金额	INT(14)	
	private int SummaryAccountMoney;
	//预留备付金额	INT(14)	
	private int ReservePreparedToPay;
	//买卖差标志	CHAR(1)	0净卖差 1净买差
	private String TradeDifference;
	//代交收金额	INT(14)	
	private int GenerationMoney;
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
	public int getCapitalAccountSummaryMoney() {
		return CapitalAccountSummaryMoney;
	}
	public void setCapitalAccountSummaryMoney(int capitalAccountSummaryMoney) {
		CapitalAccountSummaryMoney = capitalAccountSummaryMoney;
	}
	public int getSummaryAccountMoney() {
		return SummaryAccountMoney;
	}
	public void setSummaryAccountMoney(int summaryAccountMoney) {
		SummaryAccountMoney = summaryAccountMoney;
	}
	public int getReservePreparedToPay() {
		return ReservePreparedToPay;
	}
	public void setReservePreparedToPay(int reservePreparedToPay) {
		ReservePreparedToPay = reservePreparedToPay;
	}
	public String getTradeDifference() {
		return TradeDifference;
	}
	public void setTradeDifference(String tradeDifference) {
		TradeDifference = tradeDifference;
	}
	public int getGenerationMoney() {
		return GenerationMoney;
	}
	public void setGenerationMoney(int generationMoney) {
		GenerationMoney = generationMoney;
	}

}
