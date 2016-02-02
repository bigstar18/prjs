package gnnt.trade.bank.vo;
import java.io.Serializable;
/**
 * 市场方发起出金用
 * @author liuzx
 */
public class OutMoneyMarket implements Serializable{
	private static final long serialVersionUID = 1L;
	/**银行代码*/
	public String bankID;
	/**交易账号代码*/
	public String firmID;
	/**交易员*/
	public String trader;
	/**交易账号和银行绑定号*/
	public String contact;
	/**交易账号银行账号*/
	public String account;
	/**出金银行卡密码*/
	public String accountPwd;
	/**出金金额*/
	public double money;
	/**出金发起方*/
	public int type=0;
	/**备注信息*/
	public String remark;
	/**
	 * 本类信息
	 */
	public String toString(){
		StringBuilder sb = new StringBuilder();
		String str = "\n";
		sb.append("---"+this.getClass().getName()+"---"+str);
		sb.append("bankID:"+bankID+str);
		sb.append("firmID:"+firmID+str);
		sb.append("trader:"+trader+str);
		sb.append("contact:"+contact+str);
		sb.append("account:"+account+str);
		sb.append("money:"+money+str);
		sb.append("type:"+type+str);
		sb.append("remark:"+remark+str);
		return sb.toString();
	}
}
