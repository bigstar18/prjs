package gnnt.trade.bank.vo.bankdz.boc;

import java.io.Serializable;

public class AccountStatusReconciliation  implements Serializable{
	private static final long serialVersionUID = 1L;
	//客户账户状态对账文档  B02YYYYMMDD
	//银行代码	CHAR(4)	中国银行：004 
	private String BankCode;
	//交易所代码	CHAR(8)	8位交易所代码（证监会分配）
	private String MarketCode;
	//证券地区码	CHAR(4)	
	private String TransAddressCode;
	//交易日期	CHAR(8)	YYYYMMDD
	private String TransDateTime;
	//银行账号	CHAR(32)	客户银行帐号
	private String BankAccount;
	//证券资金账号	CHAR(14)	即保证金帐号（必须保证唯一）
	private String bondAcc;
	//客户姓名	CHAR(32)
	private String certificationName;
	//币别	CHAR(3)	001：人民币 013：港币 014：美元
	private String MoneyType;
	//钞汇标示	CHAR(1)	0钞 1汇
	private String CashExCode;
	//存管状态	CHAR(1)	0正常 1销户
	private String status;
	public AccountStatusReconciliation() {
		// TODO Auto-generated constructor stub
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
