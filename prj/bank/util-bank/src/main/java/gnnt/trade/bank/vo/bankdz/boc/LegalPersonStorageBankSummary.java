package gnnt.trade.bank.vo.bankdz.boc;

import java.io.Serializable;

public class LegalPersonStorageBankSummary  implements Serializable {
	//法人存管银行银行间资金交收汇总表  S11YYYYMMDD
	//银行代码	CHAR(4)	中国银行：004 
	private String BankCode;
	//交易所代码	CHAR(8)	8位交易所代码（证监会分配）
	private String MarketCode;
	//交收日期	CHAR(8)	T+1日，考虑节假日因素
	private String TransDateTime;
	//银行简称	CHAR(32)
	private String BankAbbreviation;
	//法人交收账户名称	CHAR(60)
	private String LegalPersonSettlementName;
	//法人交收账户账号	CHAR(32)	
	private String LegalPersonSettlementAccount;
	//法人交收开户银行	CHAR(60)	
	private String LegalPersonSettlementOpenBank;
	//存管银行汇总账户名称	CHAR(60)	
	private String StorageTubeBankSummaryName;
	//存管银行汇总账号	CHAR(32)	
	private String StorageTubeBankSummaryAccount;
	//存管银行汇总账户开户银行	CHAR(60)	
	private String StorageTubeBankSummaryOpenBank;
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
	public String getBankAbbreviation() {
		return BankAbbreviation;
	}
	public void setBankAbbreviation(String bankAbbreviation) {
		BankAbbreviation = bankAbbreviation;
	}
	public String getLegalPersonSettlementName() {
		return LegalPersonSettlementName;
	}
	public void setLegalPersonSettlementName(String legalPersonSettlementName) {
		LegalPersonSettlementName = legalPersonSettlementName;
	}
	public String getLegalPersonSettlementAccount() {
		return LegalPersonSettlementAccount;
	}
	public void setLegalPersonSettlementAccount(String legalPersonSettlementAccount) {
		LegalPersonSettlementAccount = legalPersonSettlementAccount;
	}
	public String getLegalPersonSettlementOpenBank() {
		return LegalPersonSettlementOpenBank;
	}
	public void setLegalPersonSettlementOpenBank(
			String legalPersonSettlementOpenBank) {
		LegalPersonSettlementOpenBank = legalPersonSettlementOpenBank;
	}
	public String getStorageTubeBankSummaryName() {
		return StorageTubeBankSummaryName;
	}
	public void setStorageTubeBankSummaryName(String storageTubeBankSummaryName) {
		StorageTubeBankSummaryName = storageTubeBankSummaryName;
	}
	public String getStorageTubeBankSummaryAccount() {
		return StorageTubeBankSummaryAccount;
	}
	public void setStorageTubeBankSummaryAccount(
			String storageTubeBankSummaryAccount) {
		StorageTubeBankSummaryAccount = storageTubeBankSummaryAccount;
	}
	public String getStorageTubeBankSummaryOpenBank() {
		return StorageTubeBankSummaryOpenBank;
	}
	public void setStorageTubeBankSummaryOpenBank(
			String storageTubeBankSummaryOpenBank) {
		StorageTubeBankSummaryOpenBank = storageTubeBankSummaryOpenBank;
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
