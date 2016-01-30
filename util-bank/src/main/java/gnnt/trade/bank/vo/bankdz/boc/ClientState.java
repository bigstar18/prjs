package gnnt.trade.bank.vo.bankdz.boc;

import java.io.Serializable;
import java.sql.Date;

public class ClientState implements Serializable{
	/**客户账户状态对账文档*/
	private String BankNo;//银行代码
	private String StrandsNo;//股商代码
	private String TransAddressCode;//交易所地区码
	private Date TransDate;//交易日期
	private String BankAccount;//银行账号
	private String TransFundAcc;//交易所资金账号
	private String ClientName;//客户姓名
	private String MoneyType;//币别
	private String RemitMark;//钞汇标示
	private String SaveState;//存管状态
	public String getBankNo() {
		return BankNo;
	}
	public void setBankNo(String bankNo) {
		BankNo = bankNo;
	}
	public String getStrandsNo() {
		return StrandsNo;
	}
	public void setStrandsNo(String strandsNo) {
		StrandsNo = strandsNo;
	}
	public String getTransAddressCode() {
		return TransAddressCode;
	}
	public void setTransAddressCode(String transAddressCode) {
		TransAddressCode = transAddressCode;
	}
	
	public Date getTransDate() {
		return TransDate;
	}
	public void setTransDate(Date transDate) {
		TransDate = transDate;
	}
	public String getBankAccount() {
		return BankAccount;
	}
	public void setBankAccount(String bankAccount) {
		BankAccount = bankAccount;
	}
	public String getTransFundAcc() {
		return TransFundAcc;
	}
	public void setTransFundAcc(String transFundAcc) {
		TransFundAcc = transFundAcc;
	}
	public String getClientName() {
		return ClientName;
	}
	public void setClientName(String clientName) {
		ClientName = clientName;
	}
	public String getMoneyType() {
		return MoneyType;
	}
	public void setMoneyType(String moneyType) {
		MoneyType = moneyType;
	}
	public String getRemitMark() {
		return RemitMark;
	}
	public void setRemitMark(String remitMark) {
		RemitMark = remitMark;
	}
	public String getSaveState() {
		return SaveState;
	}
	public void setSaveState(String saveState) {
		SaveState = saveState;
	}
	
	
	
}
