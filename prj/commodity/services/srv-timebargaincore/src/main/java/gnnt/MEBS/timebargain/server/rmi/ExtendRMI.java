package gnnt.MEBS.timebargain.server.rmi;

import gnnt.MEBS.timebargain.server.model.ApplyBill;
import gnnt.MEBS.timebargain.server.model.Settle;

import java.rmi.*;

/**
 * 扩展功能RMI接口.
 *
 * <p><a href="ExtendRMI.java.html"><i>View Source</i></a></p>
 *
 * @version 1.0.0.4
 * @author <a href="mailto:chenxc@gnnt.com.cn">chenxc</a>
 * 
 *   20100423去掉gage、gageCancel、aheadSettle方法 modify by yukx
 */
public interface ExtendRMI extends Remote {

	/**
	 * @param ApplyBill  需要传递 申请单号,商品代码,卖方二级客户ID,仓单号,仓单数量,最后修改人
	 * @return 1 成功；2：已处理过；-1 抵顶时超出可抵顶数量；-2 抵顶数量大于持仓数量；-100 其它错误;
	 * -202 不是闭市或结算完成状态，不能抵顶;-203 正在闭市结算，不能抵顶; -204：交易服务器已关闭
	 */
	public int gage(ApplyBill applyBill) throws RemoteException;
	
	/**
	 * @param ApplyBill  需要传递 生效仓单号,最后修改人,申请种类(1或2)
	 * @return 1 成功；2：已处理过；-11 取消抵顶时超出抵顶数量;-12 取消抵顶数量大于抵顶数量;-13 资金余额不足；-100 其它错误;
	 * -202 不是闭市或结算完成状态，不能取消抵顶;-203 正在闭市结算，不能取消抵顶; -204：交易服务器已关闭
	 */
	public int gageCancel(ApplyBill applyBill) throws RemoteException;
		
	/**
	 * @param ApplyBill   需要传递 申请单号,商品代码,仓单号,仓单数量(即交收数量，包括抵顶的),交收价,卖方交易客户ID,其中卖交收抵顶数量,买方交易客户ID,其中买交收抵顶数量(业务上不存在此参数实现上保留),创建人,申请种类(3或6),生效仓单号(只有申请种类为3已有仓单转提前交收时才需要)
	 * @return 1 成功;-1  可交收买持仓数量不足;-2  可交收买抵顶数量不足;-3  交收买持仓数量大于可交收买持仓数量;-4  交收买抵顶数量大于可买抵顶数量
	 *				-11  可交收卖持仓数量不足;-12  可交收卖抵顶数量不足;-13  交收卖持仓数量大于可交收卖持仓数量;-14  交收卖抵顶数量大于可卖抵顶数;-100 其它错误
	 * -41 买卖交收数量不相等
	 * -202 不是闭市或结算完成状态，不能提前交收;  -204：交易服务器已关闭
	 */
	public int aheadSettle(ApplyBill applyBill) throws RemoteException;
	
	/**
	 * 协议平仓
	 * 
	 * @param Settle   需要传递 商品代码，平仓价，买交易客户ID，买平仓数量，其中买抵顶数量，卖交易客户ID，卖平仓数量，其中卖抵顶数量
	 * @return 1 成功;2：已处理过；-1  可平仓买持仓数量不足;-2  可平仓买抵顶数量不足;-3  平仓买持仓数量大于可平仓买持仓数量;-4  平仓买抵顶数量大于可买抵顶数量
	 *				-11  可平仓卖持仓数量不足;-12  可平仓卖抵顶数量不足;-13  平仓卖持仓数量大于可平仓卖持仓数量;-14  平仓卖抵顶数量大于可卖抵顶数;-100 其它错误
	 * -41 买卖平仓数量不相等；-51 协议平仓中不支持买抵顶；-52 协议平仓中不支持卖抵顶；
	 * -202 系统不是闭市状态，不能协议交收！ -204：交易服务器已关闭
	 */
	public int conferClose(Settle settle) throws RemoteException;	
	
	/**
	 * 等待交收
	 * 
	 * @param ApplyBill   需要传递 申请单号,商品代码,卖方交易商ID,仓单号,仓单数量,最后修改人
	 * @return 1 成功；2：已处理过；-100 失败
	 * -204：交易服务器已关闭;
	 */
	public int waitSettle(ApplyBill applyBill) throws RemoteException;
	
	/**
	 * 撤消等待交收
	 * 
	 * @param ApplyBill   需要传递 生效仓单号,最后修改人
	 * @return 1 成功；2：已处理过；-100 失败
	 * -204：交易服务器已关闭;
	 */
	public int waitSettleCancel(ApplyBill applyBill) throws RemoteException;	
	
}
