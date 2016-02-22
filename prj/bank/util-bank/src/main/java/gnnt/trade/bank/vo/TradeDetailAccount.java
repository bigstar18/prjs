package gnnt.trade.bank.vo;

import java.io.Serializable;
import java.sql.Date;

public class TradeDetailAccount implements Serializable{
	private final static long serialVersionUID = 1L;
	/**交易批次号*/
	public String BatchNo;
	/**银行编号*/
	public String BankId;
	/**合作方编号*/
	public String DealId;
	/**合作方机构号*/
	public String CoBrn;
	/**交易日期*/
	public String TxDate;
	/**交易时间*/
	public String TxTime;
	/**银行流水号*/
	public String BkSerial;
	/**合作方流水号*/
	public String CoSerial;
	/**银行账号*/
	public String BankAcc;
	/**交易所交易账号*/
	public String FundAcc;
	/**客户姓名*/
	public String CustName;
	/**交易发起方*/
	public String TradeSource;
	/**交易类型*/
	public String BusinessCode;
	/**货币代码*/
	public String CurCode;
	/**
	 * 对账日期
	 */
	public Date compareDate;
	
	public Date getCompareDate() {
		return compareDate;
	}
	public void setCompareDate(Date compareDate) {
		this.compareDate = compareDate;
	}
	public String getBatchNo() {
		return BatchNo;
	}
	public void setBatchNo(String batchNo) {
		BatchNo = batchNo;
	}
	public String getBankId() {
		return BankId;
	}
	public void setBankId(String bankId) {
		BankId = bankId;
	}
	public String getDealId() {
		return DealId;
	}
	public void setDealId(String dealId) {
		DealId = dealId;
	}
	public String getCoBrn() {
		return CoBrn;
	}
	public void setCoBrn(String coBrn) {
		CoBrn = coBrn;
	}
	public String getTxDate() {
		return TxDate;
	}
	public void setTxDate(String txDate) {
		TxDate = txDate;
	}
	public String getTxTime() {
		return TxTime;
	}
	public void setTxTime(String txTime) {
		TxTime = txTime;
	}
	public String getBkSerial() {
		return BkSerial;
	}
	public void setBkSerial(String bkSerial) {
		BkSerial = bkSerial;
	}
	public String getCoSerial() {
		return CoSerial;
	}
	public void setCoSerial(String coSerial) {
		CoSerial = coSerial;
	}
	public String getBankAcc() {
		return BankAcc;
	}
	public void setBankAcc(String bankAcc) {
		BankAcc = bankAcc;
	}
	public String getFundAcc() {
		return FundAcc;
	}
	public void setFundAcc(String fundAcc) {
		FundAcc = fundAcc;
	}
	public String getCustName() {
		return CustName;
	}
	public void setCustName(String custName) {
		CustName = custName;
	}
	public String getTradeSource() {
		return TradeSource;
	}
	public void setTradeSource(String tradeSource) {
		TradeSource = tradeSource;
	}
	public String getBusinessCode() {
		return BusinessCode;
	}
	public void setBusinessCode(String businessCode) {
		BusinessCode = businessCode;
	}
	public String getCurCode() {
		return CurCode;
	}
	public void setCurCode(String curCode) {
		CurCode = curCode;
	}
	
}
