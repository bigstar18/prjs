package gnnt.trade.bank.vo.bankdz.boc;

import java.io.Serializable;

public class TotalBalanceRegulation  implements Serializable {
	//总分平衡监管表*  B04YYYYMMDD
	//银行代码	CHAR(4)	中国银行：004 
	private String BankCode;
	//交易所代码	CHAR(8)
	private String MarketCode;
	//日期	CHAR(8)	YYYYMMDD
	private String TransDateTime;
	//币别	CHAR(3)	001：人民币 013：港币 014：美元
	private String MoneyType;
	//钞汇标示	CHAR(1)	0钞 1汇
	private String CashExCode;
	//台帐汇总金额	INT(14)	
	private int AccountSummary;
	//客户交易结算资金汇总账户金额	INT(14)	
	private int TransSettlementMoneySummary;
	//预留备付金额	INT(14)
	private int ReservationReserve;
	//买卖差标志	CHAR(1)	0净卖差 1净买差
	private int TradeDifferenceStatus;
	//代交收金额	INT(14)
	private int GenerationOfSettlement;
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
	public int getAccountSummary() {
		return AccountSummary;
	}
	public void setAccountSummary(int accountSummary) {
		AccountSummary = accountSummary;
	}
	public int getTransSettlementMoneySummary() {
		return TransSettlementMoneySummary;
	}
	public void setTransSettlementMoneySummary(int transSettlementMoneySummary) {
		TransSettlementMoneySummary = transSettlementMoneySummary;
	}
	public int getReservationReserve() {
		return ReservationReserve;
	}
	public void setReservationReserve(int reservationReserve) {
		ReservationReserve = reservationReserve;
	}
	public int getTradeDifferenceStatus() {
		return TradeDifferenceStatus;
	}
	public void setTradeDifferenceStatus(int tradeDifferenceStatus) {
		TradeDifferenceStatus = tradeDifferenceStatus;
	}
	public int getGenerationOfSettlement() {
		return GenerationOfSettlement;
	}
	public void setGenerationOfSettlement(int generationOfSettlement) {
		GenerationOfSettlement = generationOfSettlement;
	}
}
