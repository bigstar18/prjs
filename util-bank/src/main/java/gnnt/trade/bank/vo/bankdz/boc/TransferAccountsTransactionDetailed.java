package gnnt.trade.bank.vo.bankdz.boc;

import java.io.Serializable;



public class TransferAccountsTransactionDetailed implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//转账交易对账明细文档  B01YYYYMMDD
	//银行代码	CHAR(4)	中国银行：004 
	private String BankCode;
	//交易所代码	CHAR(8)	8位交易所代码（证监会分配）
	private String MarketCode;
	//证券地区码	CHAR(4)	
	private String TransAddressCode;
	//交易日期	CHAR(8)	YYYYMMDD
	private String TransDateTime;
	//交易时间	CHAR(6)	HHMMSS
	private String TransTime;
	//银行流水号	CHAR(20)
	private String BankSerialNumber;
	//发起方流水号	CHAR(20)
	private String LaunchSerialNumber;
	//银行账号	CHAR(32)
	private String BankAccount;
	//证券资金账号	CHAR(14)	即保证金帐号（必须保证唯一）
	private String bondAcc;
	//客户姓名	CHAR(32)	上送为空	
	private String certificationName;
	//发起方	CHAR(1)	该字段保留暂不用
	private String Launch;
	//转账方向	CHAR(1)	1：银转证,2：证转银
	private String TransferDirection;
	//币别	CHAR(3)	001：人民币 013：港币 014：美元
	private String MoneyType;
	//钞汇标示	CHAR(1)	0钞 1汇
	private String CashExCode;
	//交收金额	INT(14)	
	private double money;
	
	
	public TransferAccountsTransactionDetailed() {
		super();
	}
	
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
	public String getTransAddressCode() {
		return TransAddressCode;
	}
	public void setTransAddressCode(String transAddressCode) {
		TransAddressCode = transAddressCode;
	}
	public String getTransDateTime() {
		return TransDateTime;
	}
	public void setTransDateTime(String transDateTime) {
		TransDateTime = transDateTime;
	}
	public String getTransTime() {
		return TransTime;
	}
	public void setTransTime(String transTime) {
		TransTime = transTime;
	}
	public String getBankSerialNumber() {
		return BankSerialNumber;
	}
	public void setBankSerialNumber(String bankSerialNumber) {
		BankSerialNumber = bankSerialNumber;
	}
	public String getLaunchSerialNumber() {
		return LaunchSerialNumber;
	}
	public void setLaunchSerialNumber(String launchSerialNumber) {
		LaunchSerialNumber = launchSerialNumber;
	}
	public String getBankAccount() {
		return BankAccount;
	}
	public void setBankAccount(String bankAccount) {
		BankAccount = bankAccount;
	}
	public String getBondAcc() {
		return bondAcc;
	}
	public void setBondAcc(String bondAcc) {
		this.bondAcc = bondAcc;
	}
	public String getCertificationName() {
		return certificationName;
	}
	public void setCertificationName(String certificationName) {
		this.certificationName = certificationName;
	}
	public String getLaunch() {
		return Launch;
	}
	public void setLaunch(String launch) {
		Launch = launch;
	}
	public String getTransferDirection() {
		return TransferDirection;
	}
	public void setTransferDirection(String transferDirection) {
		TransferDirection = transferDirection;
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
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}

}
