package gnnt.trade.bank.hx;

import java.io.Serializable;

/**
 * 批量导入子账户文件
 * @author Administrator
 *
 */
public class ZZH  implements Serializable{
	private static final long serialVersionUID = 1L;
	/*
	 * 子账户号
	 */
	public String firmId;	
	/*
	 * 交易商名称
	 */
	public String firmName;
	/*
	 * 子账户余额 
	 */
	public double amt;
	/*
	 * 交易商类型
	 */
	public int type;
}
