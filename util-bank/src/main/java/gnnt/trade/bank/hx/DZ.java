package gnnt.trade.bank.hx;

import java.io.Serializable;

/**
 * 对账文件
 * @author Administrator
 *
 */
public class DZ implements Serializable{
	private static final long serialVersionUID = 1L;
	/*
	 * 账号
	 */
	public String firmId;	
	/*
	 * 余额 
	 */
	public double amt;
	/*
	 * 可用余额 
	 */
	public double useAmt;
}
