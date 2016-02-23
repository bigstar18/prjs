package gnnt.bank.adapter;
import java.util.Date;
/**
 * 适配器交易商
 */
public class AdapterFirmVO {
	/**银行编号*/
	private String _bankID;
	/**交易商代码*/
	private String _firmID;
	/**交易商金额*/
	private double _money;
	/**签约时间*/
	private Date _createTime;
	public String getBankID() {
		return _bankID;
	}
	public void setBankID(String bankID) {
		this._bankID = bankID;
	}
	public String getFirmID() {
		return _firmID;
	}
	public void setFirmID(String firmID) {
		this._firmID = firmID;
	}
	public double getMoney() {
		return _money;
	}
	public void setMoney(double money) {
		this._money = money;
	}
	public Date getCreateTime() {
		return _createTime;
	}
	public void setCreateTime(Date createTime) {
		this._createTime = createTime;
	}
	public String toString(){
		StringBuilder sb = new StringBuilder();
		String str = "\n";
		sb.append("---"+this.getClass().getName()+"---");
		sb.append("firmID:"+_firmID+str);
		sb.append("money:"+_money+str);
		sb.append("createTime"+_createTime+str);
		return sb.toString();
	}
}
