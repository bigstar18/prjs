package gnnt.trade.bank.vo;

import java.io.Serializable;
import java.util.Date;

public class TradeDetailsVO implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Date     t_date;
	public String   b_firmid;//借方、违约方、买方
	public String   s_firmid;//贷方、守约方、卖方
	public double   money;   //金额
	public int      type;	  //1:保证金，2：手续费，3：付货款，4：违约金5:浮亏,6:收货款,7:出入金手续费
	public String   contractno;  //合同号
	public String   perct;//货款百分比
	
	public String toString(){
		String sep = "\n";
		StringBuffer sb = new StringBuffer();
		sb.append("**"+this.getClass().getName()+"**"+sep);
		sb.append("t_date:" + this.t_date+sep);
		sb.append("b_firmid:" + this.b_firmid+sep);
		sb.append("s_firmid:" + this.s_firmid+sep);
		sb.append("money:" + this.money+sep);
		sb.append("type:" + this.type+sep);
		sb.append("contractno:" + this.contractno+sep);
		sb.append("perct:" + this.perct+sep);
		sb.append(sep);
		return sb.toString();
	}
}
