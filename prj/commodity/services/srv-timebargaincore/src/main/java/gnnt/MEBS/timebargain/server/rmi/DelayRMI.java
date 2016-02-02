package gnnt.MEBS.timebargain.server.rmi;

import gnnt.MEBS.timebargain.server.model.ApplyBill;
import gnnt.MEBS.timebargain.server.model.DelayOrder;
import gnnt.MEBS.timebargain.server.model.DelayStatus;

import java.rmi.*;

/**
 * 延期交易RMI接口.
 *
 * <p><a href="DelayRMI.java.html"><i>View Source</i></a></p>
 *
 * @version 1.0.2.2
 * @author <a href="mailto:chenxc@gnnt.com.cn">chenxc</a>
 */
public interface DelayRMI extends Remote {

	/**
	 * 延期交易控制
	 * 
	 * @param status  0：暂停交易；1：继续交易
	 * @throws RemoteException
	 */
	public void ctlTrade(int status) throws RemoteException;

	/**
	 * 根据当前时间来恢复暂停后的延期交易
	 * 
	 * @param recoverTime 恢复时间 HH:mm:ss表示
	 * @throws RemoteException
	 */
	public void timingContinueTrade(String recoverTime) throws RemoteException ;
	
	/**
	 * 延期交易结束
	 * 
	 * @throws RemoteException
	 */
	public void tradeEnd() throws RemoteException;
	
	/**
	 * 延期交易结束后恢复交易
	 * 主要用于异常处理，防止误点交易结束后恢复交易
	 * 
	 * @throws RemoteException
	 */
	public void recoverTrade() throws RemoteException;

	/**
	 * 获取延期交易状态对象
	 * 
	 * @return 延期交易状态对象
	 * @throws RemoteException
	 */
	public DelayStatus getDelayStatus() throws RemoteException;

	/**
	 * 刷新延期交易节并且定时任务立刻生效
	 * 
	 * @throws RemoteException
	 */
	public void refreshDelayTradeTime() throws RemoteException;
	
	
	

	/**
	 * 交易员下延期委托单
	 * 
	 * @param sessionID  登陆sessionID
	 * @param DelayOrder  延期委托对象  
	 * @param logonType 登录类型(web web服务登录，pc 电脑客户端登录，mobile 手机客户端登录)
	 * 下单需要传递值：order.getCommodityID(),order.getDelayOrderType(),order.getBuyOrSell(),order.getTraderID(),order.getCustomerID(),order.getQuantity()
	 * 撤单需要传递值：order.getCommodityID(),order.getDelayOrderType(),order.getTraderID(),order.getCustomerID(),order.getWithdrawID(),order.getWd_DelayOrderType()
	 * @return 0：成功；1：用户身份不合法；2：市场未启用；3：不是交易时间；4：不是代为委托员交易时间；5：交易员和代为委托员不能同时存在；
	 * 		   10：此商品处于禁止交易状态；11：商品不属于当前交易节；12：委托价格超出涨幅上限；13：委托价格低于跌幅下限；14：委托价格不在此商品最小价位变动范围内；15：不存在此商品；16：委托数量不在此商品最小变动数量范围内；17  委托数量少于商品最小交割数量;
	 *         30：此交易员不存在；31：此交易员没有操作该客户的权限；32：此交易客户不存在；33：此交易客户为禁止交易状态；34：此交易商不存在；35：此交易商为禁止交易状态；37：此代为委托员没有操作该交易商的权限；38：此代为委托员不存在；  
	 *         40：计算交易保证金错误；41：计算交易手续费错误；42：此委托已成交或已撤单；
	 *         50: 不是交收申报时间；51：不是中立仓申报时间；52:此商品不允许下中立仓；53：中立仓申报方向不对；54：未知被撤委托类型；
	 *         200：代码异常而失败；
	 *         -1  持仓不足;-2  资金余额不足;
	 *         -7  超过单笔最大委托量;
	 *         -31  持仓不足;-32  仓单数量不足;
	 *         -99  执行存储时未找到相关数据；-100 执行存储失败；
	 *         -204：交易服务器已关闭；-206委托信息不能为空;
	 * @throws RemoteException
	 * @throws Exception 
	 */
	public int order(long sessionID, DelayOrder order, String logonType) throws Exception;
	
	/**
	 * 代为委托员下延期委托单
	 * 代为委托员下单要先验证交易商列表中是否存在其所属的交易商，如果没有则加载该交易商和旗下交易客户
	 * @param sessionID
	 * @param DelayOrder
	 * @return 同上交易员下单
	 * @throws RemoteException
	 */
	public int consignerOrder(long sessionID, DelayOrder order) throws RemoteException;
		
	/**
	 * 延期交收仓单
	 * 
	 * @param ApplyBill   需要传递 申请单号,商品代码,卖方交易商ID,仓单号,仓单数量,最后修改人
	 * @return 1 成功；2：已处理过；-100 失败
	 * -204：交易服务器已关闭;
	 */
	public int delaySettleBill(ApplyBill applyBill) throws RemoteException;
	
	/**
	 * 撤消延期交收仓单
	 * 
	 * @param ApplyBill   需要传递 生效仓单号,最后修改人
	 * @return 1 成功；2：已处理过；3：存在冻结数量，不能撤销；-100 失败
	 * -204：交易服务器已关闭;
	 */
	public int delaySettleBillCancel(ApplyBill applyBill) throws RemoteException;
	
}
