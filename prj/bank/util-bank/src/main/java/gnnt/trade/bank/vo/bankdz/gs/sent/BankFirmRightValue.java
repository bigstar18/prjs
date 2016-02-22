package gnnt.trade.bank.vo.bankdz.gs.sent;

import java.io.Serializable;
import java.sql.Timestamp;
/**交易商权益的分分核对*/
public class BankFirmRightValue implements Serializable {
	private static final long serialVersionUID = 1L;
	/**银行编号*/
	public String bankId;
	/**日期*/
	public Timestamp bdate;
	/**交易商代码*/
	public String firmId;
	/**银行端权益*/
	public double bankRight;
	/**市场权益*/
	public double maketRight;
	/**不平原因(0金额不平 1银行端账户未建立 2机构端账户未建立)*/
	public int reason;
	public String toString(){
		String str="\n";
		StringBuffer sb = new StringBuffer();
		sb.append("bankId["+bankId+"]"+str);
		sb.append("bdate["+bdate+"]"+str);
		sb.append("firmId["+firmId+"]"+str);
		sb.append("bankRight["+bankRight+"]"+str);
		sb.append("maketRight["+maketRight+"]"+str);
		sb.append(str);
		return sb.toString();
	}
	/**
	 * @return 日期
	 */
	public Timestamp getBdate() {
		return bdate;
	}
	/**
	 * @param 日期
	 */
	public void setBdate(Timestamp bdate) {
		this.bdate = bdate;
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
	 * @return 银行端权益
	 */
	public double getBankRight() {
		return bankRight;
	}
	/**
	 * @param 银行端权益
	 */
	public void setBankRight(double bankRight) {
		this.bankRight = bankRight;
	}
	/**
	 * @return 市场权益
	 */
	public double getMaketRight() {
		return maketRight;
	}
	/**
	 * @param 市场权益
	 */
	public void setMaketRight(double maketRight) {
		this.maketRight = maketRight;
	}
	/**
	 * @return 不平原因(0金额不平 1银行端账户未建立 2机构端账户未建立)
	 */
	public int getReason() {
		return reason;
	}
	/**
	 * @param 不平原因(0金额不平 1银行端账户未建立 2机构端账户未建立)
	 */
	public void setReason(int reason) {
		this.reason = reason;
	}
	/**
	 * @return 银行编号
	 */
	public String getBankId() {
		return bankId;
	}
	/**
	 * @param 银行编号
	 */
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
}
