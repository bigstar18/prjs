package gnnt.trade.bank.vo.bankdz;
import java.io.Serializable;
import java.util.Date;
/**银行清算日期表*/
public class BankQSVO implements Serializable{
	private static final long serialVersionUID = 1;
	/**银行编号*/
	public String bankID;
	/**清算日期*/
	public Date tradeDate;
	/**清算类型*/
	public int tradeType;
	/**清算状态*/
	public int tradeStatus;
	/**备注信息*/
	public String note;
	/**记录创建时间*/
	public Date createDate;
	/**
	 * 本类信息
	 */
	public String toString(){
		StringBuilder sb = new StringBuilder();
		String str = "\n";
		sb.append("---"+this.getClass().getName()+"---"+str);
		sb.append("银行编号[bankID]("+bankID+")"+str);
		sb.append("清算日期[tradeDate]("+tradeDate+")"+str);
		sb.append("清算类型[tradeType]("+tradeType+")"+str);
		sb.append("清算状态[tradeStatus]("+tradeStatus+")"+str);
		sb.append("备注信息[note]("+note+")"+str);
		sb.append("记录创建时间[createDate]("+createDate+")"+str);
		return sb.toString();
	}
}
