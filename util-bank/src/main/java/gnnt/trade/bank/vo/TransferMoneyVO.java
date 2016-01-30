package gnnt.trade.bank.vo;

import java.io.Serializable;

/**
 * 转账对象
 * @author 薛计涛
 *
 */
public class TransferMoneyVO implements Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 转账对象 
     * @param bankID 银行代码
     * @param money 金额
     * @param firmID 交易商代码
     * @param payInfo 付款方信息
     * @param receiveInfo 收款方信息
     * @param actionID 转账模块业务流水号
	 */
	public TransferMoneyVO(String bankID,String firmID,double money,TransferInfoValue payInfo,TransferInfoValue receiveInfo,long actionID)
	{
		this.bankID=bankID;
		this.firmID=firmID;
		this.money=money;
		this.payInfo=payInfo;
		this.receiveInfo=receiveInfo;
		this.actionID=actionID;
	}
	/**
	 * 银行代码
	 */
	public String bankID;
	/**
	 * 金额
	 */
	public double money;
	/**
	 * 交易商代码
	 */
	public String firmID;
	/**
	 * 付款方信息
	 */
	public TransferInfoValue payInfo;
	/**
	 * 收款方信息
	 */
	public TransferInfoValue receiveInfo;
	/**
	 * 转账模块业务流水号
	 */
	public long actionID;
}
