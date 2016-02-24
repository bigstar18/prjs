package gnnt.trade.bank.vo.bankdz.gs.sent;

import java.io.Serializable;

public class TradingDetailsValue implements Serializable{
	private static final long serialVersionUID = 1L;
	/**业务流水号*/
	public long maketNum;
	/**银行账号*/
	public String account;
	/**交易商代码*/
	public String firmId;
	/**客户姓名*/
	public String firmName;
	/**权益增或减*/
	public String updown;
	/**交收金额*/
	public double money;
	public String toString(){
		String str="\n";
		StringBuffer sb = new StringBuffer();
		sb.append("maketNum(业务流水号)["+maketNum+"]"+str);
		sb.append("account(银行账号)["+account+"]"+str);
		sb.append("firmId(交易商代码)["+firmId+"]"+str);
		sb.append("firmName(客户姓名)["+firmName+"]"+str);
		sb.append("updown(权益增或减)["+updown+"]"+str);
		sb.append("money(交收金额)["+money+"]"+str);
		sb.append(str);
		return sb.toString();
	}
	/**
	 * @return 业务流水号
	 */
	public long getMaketNum() {
		return maketNum;
	}
	/**
	 * @param 业务流水号
	 */
	public void setMaketNum(long maketNum) {
		this.maketNum = maketNum;
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
	 * @return 客户姓名
	 */
	public String getFirmName() {
		return firmName;
	}
	/**
	 * @param 客户姓名
	 */
	public void setFirmName(String firmName) {
		this.firmName = firmName;
	}
	/**
	 * @return 权益增或减
	 */
	public String getUpdown() {
		return updown;
	}
	/**
	 * @param 权益增或减
	 */
	public void setUpdown(String updown) {
		this.updown = updown;
	}
	/**
	 * @return 交收金额
	 */
	public double getMoney() {
		return money;
	}
	/**
	 * @param 交收金额
	 */
	public void setMoney(double money) {
		this.money = money;
	}
	
}
