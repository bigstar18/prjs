package gnnt.trade.bank.vo;
import gnnt.trade.bank.util.Tool;
import java.io.Serializable;
import java.util.Date;
/**
 * 银行接口和银行通信日志信息
 */
public class InterfaceLog implements Serializable{
	private static final long serialVersionUID = 1L;
	/**日志编号 */
	public long logID;
	
	/**银行编号*/
	public String bankID;
	/**发起方(0 市场;1 银行)*/
	public int launcher;
	/**交易类型(1 签到;2 签退;3 签约;4 解约;5 查询余额;6 出金;7 入金;8 冲正;9 改约;10 密钥同步)*/
	public int type;
	/**签约号*/
	public String contact;
	/**银行帐号*/
	public String account;
	/**发起方信息*/
	public String beginMsg;
	/**响应方信息*/
	public String endMsg;
	/**执行结果(0 成功;1 失败)*/
	public int result;
	/**记录创建时间*/
	public Date createtime;
	/**交易商ID*/
	public String firmID;
	/**银行名称*/
	public String bankName;
	/**
	 * 本类信息
	 */
	public String toString(){
		StringBuilder sb = new StringBuilder();
		String str = "\n";
		sb.append(str+"---"+this.getClass().getName()+"---"+str);
		sb.append("logID["+logID+"]"+str);
		sb.append("bankID["+bankID+"]"+str);
		sb.append("launcher["+launcher+"]"+str);
		sb.append("type["+type+"]"+str);
		sb.append("account["+account+"]"+str);
		sb.append("account["+account+"]"+str);
		sb.append("beginMsg["+beginMsg+"]"+str);
		sb.append("endMsg["+endMsg+"]"+str);
		sb.append("result["+result+"]"+str);
		sb.append("createtime["+(createtime==null ? "为空" : Tool.fmtTime(createtime))+"]"+str);
		sb.append("firmID["+firmID+"]"+str);
		sb.append("bankName["+bankName+"]"+str);
		return sb.toString();
	}
}