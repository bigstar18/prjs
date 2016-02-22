package gnnt.trade.bank.vo;
import java.io.Serializable;
/**
 * 变更资金类型
 */
public class QSChangeResult implements Serializable{
	private static final long serialVersionUID = 1L;
	/**银行编号*/
	public String bankID;
	/**交易商代码*/
	public String firmID;
	/**清算日期*/
	public java.util.Date tradeDate;
	/**发生额*/
	public double money;
	/**借贷标示(1:借,资金减少 2:贷,资金增加)*/
	public int type;
	/**资金类型(1:收货款 2:付货款 3:盈利 4:亏损 5:手续费 6:待付货款 7:浮动盈亏 8:欠款 9:远期保证金 10:交收保证金 11:现货保证金 12:竞价保证金 13:浮亏 14:入金 15:出金 16:可用余额变化量 17:冻结资金变化量 18:权益变化量)*/
	public int tradeType;
	/**备注信息*/
	public String note;
	/**写入日期*/
	public java.util.Date createDate;
	/**操作状态(0:成功 1:失败 2:情况未知)*/
	public int status;
	/**
	 * 本类信息
	 */
	public String toString(){
		StringBuilder sb = new StringBuilder();
		String str = "\n";
		sb.append("银行编号[bankID]("+bankID+")"+str);
		sb.append("交易商代码[firmID]("+firmID+")"+str);
		sb.append("清算日期[tradeDate]("+tradeDate+")"+str);
		sb.append("发生额[money]("+money+")"+str);
		sb.append("借贷标示[type]("+type+")"+str);
		sb.append("资金类型[tradeType]("+tradeType+")"+str);
		sb.append("备注信息[note]("+note+")"+str);
		sb.append("写入日期[createDate]("+createDate+")"+str);
		sb.append("操作状态[status]("+status+")"+str);
		return sb.toString();
	}
}
