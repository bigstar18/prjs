package gnnt.trade.bank.vo;

import java.io.Serializable;
import java.sql.Date;

public class FirmTradeStatus implements Serializable {
	private final static long serialVersionUID = 1L;
	/**银行编号*/
	public String BankId;
	/**合作方编号*/
	public String DealId;
	/**合作方机构号*/
	public String CoBrn;
	/**交易日期*/
	public String TxDate;
	/**银行账号*/
	public String BankAcc;
	/**交易所交易账号*/
	public String FundAcc;
	/**客户姓名*/
	public String CustName;
	/**货币代码*/
	public String CurCode;
	/**协议状态*/
	public String Status;
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


	public String getCurCode() {
		return CurCode;
	}


	public void setCurCode(String curCode) {
		CurCode = curCode;
	}


	public String getStatus() {
		return Status;
	}


	public void setStatus(String status) {
		Status = status;
	}


	public String toString(){
		String s = "\n";
		StringBuffer buf = new StringBuffer();
		buf.append("**"+this.getClass().getName()+"**"+s);
		buf.append("BankId : "+this.BankId+s);
		buf.append("DealId : "+this.DealId+s);
		buf.append("CoBrn : "+this.CoBrn+s);
		buf.append("TxDate : "+this.TxDate+s);
		buf.append("BankAcc : "+this.BankAcc+s);
		buf.append("CustName : "+this.CustName+s);
		buf.append("CurCode : "+this.CurCode+s);
		buf.append("Status : "+this.Status+s);
		buf.append(s);
		return buf.toString();
	}
}
