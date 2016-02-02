package gnnt.trade.bank.vo;
import java.io.Serializable;
/**
 * 流水表中资金信息
 */
public class CapitalMoneyVO implements Serializable{
	private static final long serialVersionUID = 1L;
	/** 转账类型 */
	public int type;
	/** 当前状态 */
	public int status;
	/** 求和资金 */
	public double money;
	/** 总条数 */
	public int rowcount;
	public CapitalMoneyVO(int type,int status){
		this.type = type;
		this.status = status;
	}
	/**
	 * 本类信息
	 */
	public String toString(){
		StringBuilder sb = new StringBuilder();
		String str = "\n";
		sb.append("type["+type+"]"+str);
		sb.append("status["+status+"]"+str);
		sb.append("money["+money+"]"+str);
		sb.append("rowcount["+rowcount+"]"+str);
		return sb.toString();
	}
}
