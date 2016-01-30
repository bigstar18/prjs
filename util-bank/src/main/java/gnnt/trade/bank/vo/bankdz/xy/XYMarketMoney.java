package gnnt.trade.bank.vo.bankdz.xy;

import java.io.Serializable;

/**
 * 市场自有资金变动数据类
 */
public class XYMarketMoney implements Serializable{
	private static final long serialVersionUID = 1;
	/**资金表ID*/
	public int id;
	/**银行编号*/
	public String bankID;
	/**银行转账号*/
	public String bankNumber;
	/**资金类型 (1 手续费，2 利息)*/
	public int type;
	/**资金改变方向 (1 增加，2 减少)*/
	public int addDelt;
	/**金额额度*/
	public double money;
	/**备注信息*/
	public String note;
	/**创建时间*/
	public java.util.Date createTime;
	/**
	 * 本类信息
	 */
	public String toString(){
		StringBuilder sb = new StringBuilder();
		String str = "\n";
		sb.append("---"+this.getClass().getName()+"---"+str);
		sb.append("资金表ID[id]("+id+")"+str);
		sb.append("银行编号[bankID]("+bankID+")"+str);
		sb.append("银行转账号[bankNumber]("+bankNumber+")"+str);
		sb.append("资金类型[type]("+type+")"+str);
		sb.append("资金改变方向[addDelt]("+addDelt+")"+str);
		sb.append("金额额度[money]("+money+")"+str);
		sb.append("备注信息[note]("+note+")"+str);
		return sb.toString();
	}
}
