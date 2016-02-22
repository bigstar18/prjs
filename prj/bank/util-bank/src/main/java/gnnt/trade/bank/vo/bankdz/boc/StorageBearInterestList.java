package gnnt.trade.bank.vo.bankdz.boc;

import java.io.Serializable;

public class StorageBearInterestList  implements Serializable {
	//存管客户结息净额明细表  S05YYYYMMDD
	//银行代码 中国银行：004 
	private String BankCode;
	//	交易所代码 8位交易所代码（证监会分配）
	private String MarketCode;
	//证券地区码 
	private String TransAddressCode;
	//	交易日期 YYYYMMDD
	private String TransDateTime;
	//证券流水号
	private String TransSerialNo;
	//台账账号
	private String TaiZhangZhangHao;
	//	证券资金账号 即保证金帐号（必须保证唯一）
	private String bondAcc;
	//	客户姓名
	private String certificationName;
	//结息类型 1批量结息  0 联机结息
	private String JieXiType;
	//	币别  001：人民币 013：港币 014：美元
	private String MoneyType;
	//	钞汇标示 0钞 1汇
	private String CashExCode;
	//金额
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
	public String getTransSerialNo() {
		return TransSerialNo;
	}
	public void setTransSerialNo(String transSerialNo) {
		TransSerialNo = transSerialNo;
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
	public String getJieXiType() {
		return JieXiType;
	}
	public void setJieXiType(String jieXiType) {
		JieXiType = jieXiType;
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
