package gnnt.MEBS.timebargain.server.rmi;

import gnnt.MEBS.timebargain.server.model.Order;
import gnnt.MEBS.timebargain.server.model.ReturnResult;

import java.rmi.*;

/**
 * 代理功能RMI接口.
 *
 * <p><a href="ProxyRMI.java.html"><i>View Source</i></a></p>
 *
 * @version 1.0.0.6
 * @author <a href="mailto:chenxc@gnnt.com.cn">chenxc</a>
 */
public interface ProxyRMI extends Remote {

	/**
	 * 根据条件查所有商品List，不包括已转入交收的
	 * 
	 * @param traderID 交易员ID
	 * @param auSessionID 
	 * @param commodityID  商品代码；为null查所有的
	 * @param logonType 登录类型(web web服务登录，pc 电脑客户端登录，mobile 手机客户端登录)
	 * @return ReturnResult,vResult包含商品对象List
	 * @throws Exception 
	 */
	public ReturnResult getCommodityListByID(String traderID,long auSessionID,String commodityID, String logonType) throws  RemoteException,Exception;
	
	/**
	 * 根据交易员查询交易商信息
	 * 
	 * @param traderID 交易员ID
	 * @param auSessionID 
	 * @param logonType 登录类型(web web服务登录，pc 电脑客户端登录，mobile 手机客户端登录)
	 * @return ReturnResult,vResult包含FirmInfo
	 * @throws Exception 
	 * 
	 */
	public ReturnResult getFirmInfoByTraderID(String traderID,long auSessionID, String logonType) throws RemoteException,Exception;
		
	/**
	 * 查询二级客户持仓合计
	 * @param traderID 交易员ID
	 * @param auSessionID
	 * @param firmID 交易商ID
	 * @param commodityID 商品ID
	 * @param customerID 二级客户ID
	 * @param logonType 登录类型(web web服务登录，pc 电脑客户端登录，mobile 手机客户端登录)
	 * @return ReturnResult,vResult包含CustomerHoldSumInfo对象的list
	 * @throws Exception 
	 * 
	 */
	public ReturnResult queryCustomerHoldSumInfo(String traderID,long auSessionID,String firmID,String commodityID,String customerID, String logonType) throws  RemoteException,Exception;
	
	/**
	 * 查询委托
	 * 
	 * @param traderID 交易员ID
	 * @param auSessionID 
	 * @param Order
	 * @param logonType 登录类型(web web服务登录，pc 电脑客户端登录，mobile 手机客户端登录)
	 * @return ReturnResult,vResult包含Order对象的list
	 * @throws Exception 
	 * 
	 */
	public ReturnResult queryOrder(String traderID,long auSessionID,Order order, String logonType) throws RemoteException,Exception;	
	
	/**
	 * 查询成交
	 * 
	 * @param traderID 交易员ID
	 * @param auSessionID 
	 * @param lastTradeNo 
	 * @param firmID 
	 * @param logonType 登录类型(web web服务登录，pc 电脑客户端登录，mobile 手机客户端登录)
	 * @return ReturnResult,vResult包含TradeInfo对象的list
	 * @throws Exception 
	 * 
	 */
	public ReturnResult queryTradeInfo(String traderID,long auSessionID,long lastTradeNo,String firmID, String logonType) throws  RemoteException,Exception;
	
	/**
	 * 查询成交
	 * @param traderID 交易员ID
	 * @param auSessionID
	 * @param lastTradeNo 
	 * @param firmID
	 * @param lastBroadcastID，最后查到的消息ID，<0表示不查广播消息
	 * @param logonType 登录类型(web web服务登录，pc 电脑客户端登录，mobile 手机客户端登录)
	 * @return ReturnResult,vResult包含TradeInfo对象的list,vResult2包含Broadcast对象的list
	 * @throws Exception 
	 * 
	 */
	public ReturnResult queryTradeInfo(String traderID,long auSessionID,long lastTradeNo,String firmID,long lastBroadcastID, String logonType) throws  RemoteException,Exception;
	/**
	 * 查询某时间后的行情
	 * 
	 * @param queryTime 查询时间(HHmmss格式)
	 * 
	 * @return Quotation对象的list
	 * 
	 */
	public ReturnResult queryQuotation(String queryTime) throws RemoteException;	
	
	/**
	 * 代理交易员下委托单
	 * 
	 * @param sessionID  登陆sessionID
	 * @param order  委托对象
	 * @param logonType 登录类型(web web服务登录，pc 电脑客户端登录，mobile 手机客户端登录)
	 * @return ReturnResult.retCode:  跟TradeRMI.order一样
	 *         
	 *         ReturnResult.vResult.get(0):存放下单的委托单号
	 *         ReturnResult.vResult.get(1):存放撤单的数量
	 * @throws RemoteException
	 * @throws Exception 
	 */
	public ReturnResult order(long sessionID, Order order, String logonType) throws RemoteException,Exception;
	
	/**
	 * 获取交易服务器状态对象
	 * 需要身份验证
	 * 
	 * @param traderID 交易员ID
	 * @param auSessionID 
	 * @param logonType 登录类型(web web服务登录，pc 电脑客户端登录，mobile 手机客户端登录)
	 * @return ReturnResult.vResult.get(0):存放交易服务器状态对象
	 * @throws RemoteException
	 * @throws Exception 
	 */
	public ReturnResult getSystemStatus(String traderID,long auSessionID, String logonType) throws  RemoteException,Exception;
}
