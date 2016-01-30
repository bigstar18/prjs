package gnnt.trade.bank.vo.bankdz.boc;

import java.io.Serializable;

public class StorageBankMoneySettlementSummary  implements Serializable {
	//存管银行资金交收汇总表  S08YYYYMMDD
	//银行代码	CHAR(4)	中国银行：004
	private String BankCode;
	//交易所代码	CHAR(8)	8位交易所代码（证监会分配）
	private String MarketCode;
	//交收日期	CHAR(8)	YYYYMMDD
	private String TransDateTime;
	//本行汇总账户名称	CHAR(60)	
	private String SummaryName;
	//本行汇总账户账号	CHAR(32)	
	private String SummaryAccount;
	//本行汇总账户开户银行	CHAR(60)	
	private String SummaryOpenBank;
	//法人交收账户名称	CHAR(60)	
	private String LegalPersontName;
	//法人交收账户账号	CHAR(32)	
	private String LegalPersonAccount;
	//法人交收开户银行	CHAR(60)
	private String LegalPersonOpenBank;
	//买卖差标志	CHAR(1)	0净卖差 1净买差
	private String TradeDifference;
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
	public String getSummaryName() {
		return SummaryName;
	}
	public void setSummaryName(String summaryName) {
		SummaryName = summaryName;
	}
	public String getSummaryAccount() {
		return SummaryAccount;
	}
	public void setSummaryAccount(String summaryAccount) {
		SummaryAccount = summaryAccount;
	}
	public String getSummaryOpenBank() {
		return SummaryOpenBank;
	}
	public void setSummaryOpenBank(String summaryOpenBank) {
		SummaryOpenBank = summaryOpenBank;
	}
	public String getLegalPersontName() {
		return LegalPersontName;
	}
	public void setLegalPersontName(String legalPersontName) {
		LegalPersontName = legalPersontName;
	}
	public String getLegalPersonAccount() {
		return LegalPersonAccount;
	}
	public void setLegalPersonAccount(String legalPersonAccount) {
		LegalPersonAccount = legalPersonAccount;
	}
	public String getLegalPersonOpenBank() {
		return LegalPersonOpenBank;
	}
	public void setLegalPersonOpenBank(String legalPersonOpenBank) {
		LegalPersonOpenBank = legalPersonOpenBank;
	}
	public String getTradeDifference() {
		return TradeDifference;
	}
	public void setTradeDifference(String tradeDifference) {
		TradeDifference = tradeDifference;
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
