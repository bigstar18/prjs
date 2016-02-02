package gnnt.trade.bank.vo;
import java.io.Serializable;
public class FirmMessageVo implements Serializable{
	private static final long serialVersionUID = 1L;
	/** 交易账号编号 */
	public String firmid;
	/**
	 * 客户类型(C 客户;M 会员;S 特别会员)
	 */
	public String firmType;
	/**
	 * 客户操作状态(U 签约未激活;N 正常;F 已冻结)
	 */
	public String status;
	/** 交易账号密码MD5处理 */
	public String Password;
	
	public String getFirmid() {
		return firmid;
	}
	public void setFirmid(String firmid) {
		this.firmid = firmid;
	}
	public String getFirmType() {
		return firmType;
	}
	public void setFirmType(String firmType) {
		this.firmType = firmType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	
	public String toString(){
		String str = "交易员ID["+firmid+"],客户类型["+firmType+"]状态["+status+"]";
		return str;
	}
}
