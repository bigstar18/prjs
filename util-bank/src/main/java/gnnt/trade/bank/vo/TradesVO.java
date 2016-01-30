package gnnt.trade.bank.vo;

import java.io.Serializable;
import java.util.Date;
/**
 * 交易系统交易商信息类
 * @author 薛计涛
 *
 */
public class TradesVO implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Date     t_date;        //日期
	public String   firmid;		//摊位代码
	public double   marg;			//履约保证金发生额(-)
	public double   add_marg;		//追加保证金发生额(-)
	public double   settle_marg;	//交收保证金发生额(-)
	public double   liq_pl;		//转让盈亏(+)
	public double   t_spl;			//交货盈亏(+)
	public double   tradecomm;		//交易手续费(-)
	public double   completecomm;	//交收手续费(-)
	public double   oliq_l;        //购货支出(-)
	public double   payment;       //销售收入(+)
	/**
	 * 返回对象属性值
	 */
	public String toString()
	{
		String sep = "\n";
		StringBuffer sb = new StringBuffer();
		sb.append("**"+this.getClass().getName()+"**"+sep);
		sb.append("t_date:" + this.t_date+sep);
		sb.append("firmid:" + this.firmid+sep);
		sb.append("marg:" + this.marg+sep);
		sb.append("add_marg:" + this.add_marg+sep);
		sb.append("settle_marg:" + this.settle_marg+sep);
		sb.append("liq_pl:" + this.liq_pl+sep);
		sb.append("t_spl:" + this.t_spl+sep);
		sb.append("tradecomm:" + this.tradecomm+sep);
		sb.append("completecomm:" + this.completecomm+sep);
		sb.append("oliq_l:" + this.oliq_l+sep);
		sb.append("payment:" + this.payment+sep);
		sb.append(sep);
		return sb.toString();
	}
}
