package gnnt.trade.bank.vo;

import java.io.Serializable;

public class FirmMessageVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 交易商id
	 */
	public String firmid;
	/**
	 * 交易商名称
	 */
	public String firmName;
	/**
	 * 交易商状态
	 * N正常 D禁止
	 */
	public String firmStatus;
	/**
	 * 交易商密码MD5处理
	 */
	public String Password;
	
	public String getFirmid() {
		return firmid;
	}
	public void setFirmid(String firmid) {
		this.firmid = firmid;
	}
	public String getFirmName() {
		return firmName;
	}
	public void setFirmName(String firmName) {
		this.firmName = firmName;
	}
	public String getFirmStatus() {
		return firmStatus;
	}
	public void setFirmStatus(String firmStatus) {
		this.firmStatus = firmStatus;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	
	public String toString()
	{
		String str = "交易员ID=["+firmid+"],交易员名称=["+firmName+"]"+"交易员状态=["+firmStatus+"]";
		return str;
	}
}
