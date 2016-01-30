package gnnt.trade.bank;

/**
 * <p>Title: 资金划转抽象类</p>
 *
 * <p>Description:根据不同的业务类型进行资金划转</p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: gnnt</p>
 *
 * @author zhangzhongli
 * @version 1.0
 */
public abstract class TransMoney {

	/**
     * 划转资金
     * @param bankID 银行代码
     * @param money 金额
     * @param operator 资金划转发起端
     * @param firmID 交易商代码
     * @param processor 处理器对象
     * @return long 银行接口业务流水号,<0表示操作失败
     * @throws 
     */
	protected  abstract long tranfer(String bankID , double money , String operator, String firmID,CapitalProcessor processor); 
}
