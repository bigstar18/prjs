package gnnt.trade.bank.vo;
import java.io.Serializable;
/**
 * 出金信息
 * @author 薛计涛
 */
public class OutMoneyVO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
     * 出金
     * @param bankID 银行代码
     * @param bankname 银行名称
     * @param money 金额
     * @param firmID 交易账号代码
     * @param payInfo 付款方信息
     * @param receiveInfo 收款方信息
     * @param actionID 转账模块业务流水号
     * @param funID 银行流水号
	 */
	public OutMoneyVO(String bankID,String bankname ,double money,String firmID, TransferInfoValue payInfo,TransferInfoValue receiveInfo,long actionID,String funID){
		this.bankID=bankID;
		this.bankname=bankname;
		this.firmID=firmID;
		this.money=money;
		this.payInfo=payInfo;
		this.receiveInfo=receiveInfo;
		this.actionID=actionID;
		this.funID=funID;
	}
	/** 银行名称 */
	public String bankname;
	/** 银行代码 */
	public String bankID;
	/** 金额 */
	public double money;
	/** 交易账号代码 */
	public String firmID;
	/** 付款方信息 */
	public TransferInfoValue payInfo;
	/** 收款方信息 */
	public TransferInfoValue receiveInfo;
	/** 转账模块业务流水号 */
	public long actionID;
	/** 是否加急 0：正常 1：加急 */
	public int  express;
	/** 银行流水号 */
	public String funID;
}
