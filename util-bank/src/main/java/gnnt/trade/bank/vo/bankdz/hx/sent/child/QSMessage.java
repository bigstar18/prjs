package gnnt.trade.bank.vo.bankdz.hx.sent.child;
import java.io.Serializable;
/**
 * 华夏银行清算发送类
 */
public class QSMessage implements Serializable{
	private static final long serialVersionUID=1L;
	/**交易商可用资金变化量*/
	public double balanceChanges;
	/**当前冻结资金*/
	public double frozenFunds;
	/**本类信息*/
	public String toString(){
		StringBuilder sb = new StringBuilder();
		String str = "\n";
		sb.append("---"+this.getClass().getName()+"---"+str);
		sb.append("交易商可用资金变化量[balanceChanges]("+balanceChanges+")"+str);
		sb.append("当前冻结资金[frozenFunds]("+frozenFunds+")"+str);
		return sb.toString();
	}
}
