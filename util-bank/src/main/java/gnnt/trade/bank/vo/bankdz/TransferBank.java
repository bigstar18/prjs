package gnnt.trade.bank.vo.bankdz;
  

import javax.xml.bind.annotation.XmlElement;

public class TransferBank {
	/**
	 * 银行编号
	 */
	public String bankID;
	/**
	 * 银行名称
	 */
	public  String bankName;

	/**
	 * 帐号
	 * */

	public String Id = "";
	/**
	 * 户主名称
	 * */

	public String Name = "";
	/**
	 * 银行账户状态
	 * */
 
	public int St  ;
	/**
	 * 银行账户类型
	 * */

	public int accountType ;
	/**
	 * 密码
	 * */

	public String  Pwd ="";
	/**
	 * 业务开通标志
	 * */
 
	public String OpFlg = "Y";
	/**
	 * 开户日期
	 * */

	public String RegDt = "";
	/**
	 * 有效日期
	 * */

	public String VldDt = "";
	/**
	 * 交易类型 （0000001） 1:跨行转账 2：市场出金  
	 */
	public  String type;
	
	public String status;
	/**
	 * 行号
	 */
	public String bankNum;
	public String getBankID() {
		return bankID;
	}
	public void setBankID(String bankID) {
		this.bankID = bankID;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public int getSt() {
		return St;
	}
	public void setSt(int st) {
		St = st;
	}
	public int getAccountType() {
		return accountType;
	}
	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}
	public String getPwd() {
		return Pwd;
	}
	public void setPwd(String pwd) {
		Pwd = pwd;
	}
	public String getOpFlg() {
		return OpFlg;
	}
	public void setOpFlg(String opFlg) {
		OpFlg = opFlg;
	}
	public String getRegDt() {
		return RegDt;
	}
	public void setRegDt(String regDt) {
		RegDt = regDt;
	}
	public String getVldDt() {
		return VldDt;
	}
	public void setVldDt(String vldDt) {
		VldDt = vldDt;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBankNum() {
		return bankNum;
	}
	public void setBankNum(String bankNum) {
		this.bankNum = bankNum;
	}

	
	
}
