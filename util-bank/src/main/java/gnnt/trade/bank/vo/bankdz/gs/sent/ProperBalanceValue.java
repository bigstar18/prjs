package gnnt.trade.bank.vo.bankdz.gs.sent;
import java.io.Serializable;
import java.sql.Timestamp;
/**总分平衡监管*/
public class ProperBalanceValue implements Serializable {
	private static final long serialVersionUID = 1L;
	/**银行编号*/
	public String bankId;
	/**日期*/
	public Timestamp bdate;
	/**汇总金额*/
	public double allMoney;
	/**工行汇总账户金额*/
	public double gongMoney;
	/**他行汇总账户余额*/
	public double otherMoney;
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
	 * @return 总金额
	 */
	public double getAllMoney() {
		return allMoney;
	}
	/**
	 * @param 总金额
	 */
	public void setAllMoney(double allMoney) {
		this.allMoney = allMoney;
	}
	/**
	 * @return 工行汇总账户金额
	 */
	public double getGongMoney() {
		return gongMoney;
	}
	/**
	 * @param 工行汇总账户金额
	 */
	public void setGongMoney(double gongMoney) {
		this.gongMoney = gongMoney;
	}
	/**
	 * @return 他行汇总账户余额
	 */
	public double getOtherMoney() {
		return otherMoney;
	}
	/**
	 * @param 他行汇总账户余额
	 */
	public void setOtherMoney(double otherMoney) {
		this.otherMoney = otherMoney;
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
