package gnnt.trade.bank.vo;
/**
 * 银行端科目
 * @author Administrator
 *
 */
public class Account {

	/**
	 * 科目代码
	 */
	public String code;
	
	/**
	 * 科目名称
	 */
	public String info;
	
	/**
	 * 科目类型 0：可付可收款科目 1：付款科目 2：收款科目
	 */
	public int type;
}