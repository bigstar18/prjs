package gnnt.trade.bank.vo;
import java.io.Serializable;
/**会员开销户银行市场对比信息类*/
public class FirmOpenCloseBank implements Serializable{
	private static final long serialVersionUID = 1;
	/**交易商代码*/
	public String firmID;
	/**签解约信息(1：签约 2：解约)*/
	public int type;
	/**备注*/
	public String note;
	/**签解约日期*/
	public java.util.Date tradeDate;
}
