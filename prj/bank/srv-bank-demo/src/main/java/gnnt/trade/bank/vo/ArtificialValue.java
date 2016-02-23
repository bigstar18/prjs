package gnnt.trade.bank.vo;
import java.io.Serializable;
/**
 * 手工凭证类
 * @author liuzx
 */
public class ArtificialValue implements Serializable{
	private static final long serialVersionUID = 1L;
	/** 资金划转方向 */
	public int inOutMoney;
	/** 划转金额 */
	public double money;
	/** 交易账号代码 */
	public String firmID;
	/** 银行编号 */
	public String bankID;
	/** 备注信息 */
	public String note;
	/** 操作员 */
	public String trader;
	/**
	 * 本类信息
	 */
	public String toString(){
		StringBuilder sb = new StringBuilder();
		String str = "\n";
		sb.append(str+"---"+this.getClass().getName()+"---"+str);
		sb.append("inOutMoney:"+inOutMoney+str);
		sb.append("money:"+money+str);
		sb.append("firmID:"+firmID+str);
		sb.append("bankID:"+bankID+str);
		sb.append("note:"+note+str);
		sb.append("trader:"+trader+str);
		return sb.toString();
	}
}
