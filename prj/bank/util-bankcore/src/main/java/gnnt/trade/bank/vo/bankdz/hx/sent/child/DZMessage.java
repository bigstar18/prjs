package gnnt.trade.bank.vo.bankdz.hx.sent.child;
import java.io.Serializable;
/**
 * 华夏银行对账信息发送类
 */
public class DZMessage implements Serializable{
	private static final long serialVersionUID = 1L;
	/**交易商可用资金*/
	public double todaybalance;
	/**交易商权益*/
	public double rights;
	/**本类信息*/
	public String toString(){
		StringBuilder sb = new StringBuilder();
		String str = "\n";
		sb.append("---"+this.getClass().getName()+"---"+str);
		sb.append("交易商可用资金["+todaybalance+"]("+todaybalance+")"+str);
		sb.append("交易商权益["+rights+"]("+rights+")"+str);
		return sb.toString();
	}
}
