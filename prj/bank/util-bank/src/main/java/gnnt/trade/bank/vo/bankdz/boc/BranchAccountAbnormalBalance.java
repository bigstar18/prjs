package gnnt.trade.bank.vo.bankdz.boc;

import java.io.Serializable;

public class BranchAccountAbnormalBalance  implements Serializable {
	//分账户余额异常明细表*  B05YYYYMMDD
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
	//台帐账号	CHAR(22)	交易所代码+证券资金台账账号
	private String TaiZhangZhangHao;
	//证券资金账号	CHAR(14)	
	private String bondAcc;
	//客户姓名	CHAR(32)
	private String certificationName;
	//台帐金额	INT(14)
	private int DepositAmountMoney;
	//证券资金帐户金额	INT(14)
	private int StockFundAccountMoney;
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
	public int getDepositAmountMoney() {
		return DepositAmountMoney;
	}
	public void setDepositAmountMoney(int depositAmountMoney) {
		DepositAmountMoney = depositAmountMoney;
	}
	public int getStockFundAccountMoney() {
		return StockFundAccountMoney;
	}
	public void setStockFundAccountMoney(int stockFundAccountMoney) {
		StockFundAccountMoney = stockFundAccountMoney;
	}



}
