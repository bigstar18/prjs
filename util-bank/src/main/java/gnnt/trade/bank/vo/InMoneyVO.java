package gnnt.trade.bank.vo;

import java.io.Serializable;

/**
 * 入金信息
 * 
 * @author 薛计涛
 * 
 */
public class InMoneyVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 入金
	 * 
	 * @param bankID
	 *            银行代码
	 * @param money
	 *            金额
	 * @param firmID
	 *            交易商代码
	 * @param payInfo
	 *            付款方信息
	 * @param payPwd
	 *            付款方密码
	 * @param receiveInfo
	 *            收款方信息
	 * @param operator
	 *            入金发起端
	 * @param actionID
	 *            转账模块业务流水号
	 */
	public InMoneyVO(String bankID, String bankname, double money,
			String firmID, TransferInfoValue payInfo, String payPwd,
			TransferInfoValue receiveInfo, String operator, long actionID,String inOutStart,
			String personName, String amoutDate, String bankName,
			String outAccount) {
		this.setActionID(actionID);
		this.setBankID(bankID);
		this.setBankname(bankname);
		this.setMoney(money);
		this.setFirmID(firmID);
		this.setPayInfo(payInfo);
		this.setPayPwd(payPwd);
		this.setReceiveInfo(receiveInfo);
		this.setOperator(operator);
		this.setInOutStart(inOutStart);
		this.setPersonName(personName);
		this.setAmoutDate(amoutDate);
		this.setBankName(bankName);
		this.setOutAccount(outAccount);
	}

	/**
     * 入金
     * @param bankID 银行代码
     * @param money 金额
     * @param firmID 交易商代码
     * @param payInfo 付款方信息
     * @param payPwd 付款方密码
	 * @param receiveInfo 收款方信息
	 * @param operator 入金发起端
     * @param actionID 转账模块业务流水号
	 */
	public InMoneyVO(String bankID ,String bankname,double money , String firmID,TransferInfoValue payInfo,String payPwd,TransferInfoValue receiveInfo, String operator ,long actionID)
	{
		this.setActionID(actionID);
		this.setBankID(bankID);
		this.setBankname(bankname);
		this.setMoney(money);
		this.setFirmID(firmID);
		this.setPayInfo(payInfo);
		this.setPayPwd(payPwd);
		this.setReceiveInfo(receiveInfo);
		this.setOperator(operator);
	}
	/**
	 * 银行名称
	 */
	private String bankname;
	/**
	 * 银行代码
	 */
	private String bankID;// 银行代码
	/**
	 * 金额
	 */
	private double money;// 金额
	/**
	 * 交易商代码
	 */
	private String firmID;// 交易商代码
	/**
	 * 付款方信息
	 */
	private TransferInfoValue payInfo;// 付款方信息
	/**
	 * 付款方密码
	 */
	private String payPwd;// 付款方密码
	/**
	 * 收款方信息
	 */
	private TransferInfoValue receiveInfo;// 收款方信息
	/**
	 * 入金发起端
	 */
	private String operator;// 入金发起端
	/**
	 * 转账模块业务流水号
	 */
	private long actionID;// 转账模块业务流水号
	/**
	 * 入金方式：（★） 1：他行现金汇款 2：他行转账汇款
	 * 
	 */
	private String inOutStart;// 汇款日期
	/**
	 * 汇款人姓名
	 */
	private String personName;// 汇款人姓名
	/**
	 * 汇款日期
	 */
	private String amoutDate;// 汇款日期
	/**
	 * 汇款银行
	 */
	private String bankName;// 汇款银行
	/**
	 * 汇款账号
	 */
	private String outAccount;// 汇款账号

	/**
	 * 本类信息
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String str = "\n";
		sb.append("bankname:" + bankname + str);
		sb.append("bankID:" + bankID + str);
		sb.append("money:" + money + str);
		sb.append("firmID:" + firmID + str);
		sb.append("payInfo:" + payInfo + str);
		sb.append("receiveInfo:" + receiveInfo + str);
		sb.append("operator:" + operator + str);
		sb.append("actionID:" + actionID + str);
		sb.append("InOutStart:" + inOutStart + str);
		sb.append("personName:" + personName + str);
		sb.append("amoutDate:" + amoutDate + str);
		sb.append("bankName:" + bankName + str);
		sb.append("outAccount:" + outAccount + str);
		return sb.toString();
	}

	public String getInOutStart() {
		return inOutStart;
	}

	public void setInOutStart(String inOutStart) {
		this.inOutStart = inOutStart;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getAmoutDate() {
		return amoutDate;
	}

	public void setAmoutDate(String amoutDate) {
		this.amoutDate = amoutDate;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getOutAccount() {
		return outAccount;
	}

	public void setOutAccount(String outAccount) {
		this.outAccount = outAccount;
	}

	/**
	 * @return 转账模块业务流水号
	 */
	public long getActionID() {
		return actionID;
	}

	/**
	 * @param 转账模块业务流水号
	 */
	public void setActionID(long actionID) {
		this.actionID = actionID;
	}

	/**
	 * @return 银行代码
	 */
	public String getBankID() {
		return bankID;
	}

	/**
	 * @param 银行代码
	 */
	public void setBankID(String bankID) {
		this.bankID = bankID;
	}

	/**
	 * @return 交易商代码
	 */
	public String getFirmID() {
		return firmID;
	}

	/**
	 * @param 交易商代码
	 */
	public void setFirmID(String firmID) {
		this.firmID = firmID;
	}

	/**
	 * @return 金额
	 */
	public double getMoney() {
		return money;
	}

	/**
	 * @param 金额
	 */
	public void setMoney(double money) {
		this.money = money;
	}

	/**
	 * @return 入金发起端
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * @param 入金发起端
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * @return 付款方信息
	 */
	public TransferInfoValue getPayInfo() {
		return payInfo;
	}

	/**
	 * @param 付款方信息
	 */
	public void setPayInfo(TransferInfoValue payInfo) {
		this.payInfo = payInfo;
	}

	/**
	 * @return 付款方密码
	 */
	public String getPayPwd() {
		return payPwd;
	}

	/**
	 * @param 付款方密码
	 */
	public void setPayPwd(String payPwd) {
		this.payPwd = payPwd;
	}

	/**
	 * @return 收款方信息
	 */
	public TransferInfoValue getReceiveInfo() {
		return receiveInfo;
	}

	/**
	 * @param 收款方信息
	 */
	public void setReceiveInfo(TransferInfoValue receiveInfo) {
		this.receiveInfo = receiveInfo;
	}

	public String getBankname() {
		return bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
}
