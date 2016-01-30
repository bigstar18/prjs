package gnnt.trade.bank.hx;

import java.io.Serializable;

/**
 * 清算文件 标志位 1：当日冻结余额记录 0：总余额变更记录
 * @author Administrator
 *
 */
public class QS  implements Serializable{
	private static final long serialVersionUID = 1L;
	/*
	 * 标志位
	 */
	public int flag;
	/*
	 * 子账号
	 */
	public String firmId;
	/*
	 * 借贷标志
	 */
	public int direction;
	/*
	 * 标志位 1：冻结金额 标志位 0：发生额 
	 */
	public double amt;
	/*
	 * 摘要
	 */
	public String abstct;
}
