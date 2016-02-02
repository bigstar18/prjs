package gnnt.trade.bank.vo.bankdz.gs.sent;

import java.io.Serializable;

/**交易商权益类*/
public class FirmRights implements Serializable {
	private static final long serialVersionUID = 1L;
	/**交易商代码*/
	public String firmId;
	/**交易商名称*/
	public String firmName;
	/**银行账号*/
	public String account;
	/**交易商总权益(金额)*/
	public double money;
	public String toString(){
		String str="\n";
		StringBuffer sb= new StringBuffer();
		sb.append("firmId["+this.firmId+"]"+str);
		sb.append("firmName["+this.firmName+"]"+str);
		sb.append("account["+this.account+"]"+str);
		sb.append("money["+this.money+"]"+str);
		sb.append(str);
		return sb.toString();
	}
	/**
	 * @return 交易商代码
	 */
	public String getFirmId() {
		return firmId;
	}
	/**
	 * @param 交易商代码
	 */
	public void setFirmId(String firmId) {
		this.firmId = firmId;
	}
	/**
	 * @return 交易商名称
	 */
	public String getFirmName() {
		return firmName;
	}
	/**
	 * @param 交易商名称
	 */
	public void setFirmName(String firmName) {
		this.firmName = firmName;
	}
	/**
	 * @return 银行账号
	 */
	public String getAccount() {
		return account;
	}
	/**
	 * @param 银行账号
	 */
	public void setAccount(String account) {
		this.account = account;
	}
	/**
	 * @return 交易商总权益(金额)
	 */
	public double getMoney() {
		return money;
	}
	/**
	 * @param 交易商总权益(金额)
	 */
	public void setMoney(double money) {
		this.money = money;
	}
	
}
