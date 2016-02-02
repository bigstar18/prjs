package gnnt.trade.bank.vo;

import java.io.Serializable;

public class InMoneyMarket implements Serializable{
	private static final long serialVersionUID = 1L;
	/** 交易账号代码 */
	public String firmID;
	/** 操作员 */
	public String trader;
	/** 交易账号和银行绑定号 */
	public String contact;
	/** 银行代码 */
	public String bankID;
	/** 银行账号 */
	public String account;
	/** 银行卡密码 */
	public String accountPwd;
	/** 金额 */
	public double money;
	/** 备注 */
	public String remark;
	/**
	 * 本类信息
	 */
	public String toString(){
		StringBuilder sb = new StringBuilder();
		String str = "\n";
		sb.append("---"+this.getClass().getName()+"---"+str);
		sb.append("firmID:"+firmID+str);
		sb.append("trader:"+trader+str);
		sb.append("contact:"+contact+str);
		sb.append("bankID:"+bankID+str);
		sb.append("account:"+account+str);
		sb.append("accountPwd:"+accountPwd+str);
		sb.append("money:"+money+str);
		sb.append("remark:"+remark+str);
		return sb.toString();
	}
}
