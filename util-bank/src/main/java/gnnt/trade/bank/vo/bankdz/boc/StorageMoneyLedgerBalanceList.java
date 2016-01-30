package gnnt.trade.bank.vo.bankdz.boc;

import java.io.Serializable;

public class StorageMoneyLedgerBalanceList implements Serializable {
	// 存管客户资金台账资金余额明细表 S07YYYYMMDD
	// 银行代码 CHAR(4) 中国银行：004
	private String BankCode;
	// 交易所代码 CHAR(8) 8位交易所代码（证监会分配）
	private String MarketCode;
	// 证券地区码 CHAR(4)
	private String TransAddressCode;
	// 交易日期 CHAR(8) YYYYMMDD
	private String TransDateTime;
	// 台账账号 CHAR(22) 交易所代码+证券资金账号
	private String TaiZhangZhangHao;
	// 证券资金账号 CHAR(14)
	private String bondAcc;
	// 客户姓名 CHAR(32)
	private String certificationName;
	// 币别 CHAR(3) 001：人民币 013：港币 014：美元
	private String MoneyType;
	// 钞汇标示 CHAR(1) 0钞 1汇
	private String CashExCode;
	// 台账资金金额 INT(14)
	private double money;
	private double lastMoney;

	public double getLastMoney() {
		return this.lastMoney;
	}

	public void setLastMoney(double lastMoney) {
		this.lastMoney = lastMoney;
	}

	public StorageMoneyLedgerBalanceList() {
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

	public String getTaiZhangZhangHao() {
		return TaiZhangZhangHao;
	}

	public void setTaiZhangZhangHao(String taiZhangZhangHao) {
		TaiZhangZhangHao = taiZhangZhangHao;
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

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}
}
