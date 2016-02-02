package gnnt.MEBS.timebargain.server.rmi;


import gnnt.MEBS.checkLogon.vo.front.TraderLogonInfo;

import gnnt.MEBS.timebargain.server.model.Consigner;
import gnnt.MEBS.timebargain.server.model.Order;
import gnnt.MEBS.timebargain.server.model.OrderReturnValue;
import gnnt.MEBS.timebargain.server.model.Trader;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * 交易RMI接口.
 *
 * <p><a href="TradeRMI.java.html"><i>View Source</i></a></p>
 *
 * @version 1.0.0.4
 * @author <a href="mailto:chenxc@gnnt.com.cn">chenxc</a>
 *  修改记录：<br>
 * <ul>1、由于AU中抛出Exception异常，故将抛出的RemoteException改为Exceprion  20130401 mod by liuchao</ul><br>
 * <ul>2、remoteLogin方法返回值改为ActiveUser,以适应新的AU 20130401 mod by liuchao </ul><br>
 * <ul>3、屏蔽掉checkUser方法 </ul><br>
 */
public interface TradeRMI extends Remote {

	/**
	 * 交易员登录
	 * 
	 * @param Trader trader  包括ID，密码,key和IP
	 * @return TraderInfo,其中TraderInfo.auSessionId>0表示成功；-1：交易员代码不存在；-2：口令不正确；-3：禁止登录；-4：Key盘验证错误；-5：交易板块被禁止;-6:其它异常;
	 * @throws RemoteException
	 * @throws Exception 
	 */
	public TraderLogonInfo logon(Trader trader) throws RemoteException,Exception;
	
	/**
	 * 远程AU验证是否登录
	 * @param traderID  交易员ID
	 * @param auSessionId  auSessionId
	 * @param logonIP  登录IP
	 * @param logonType 登录类型(web web服务登录，pc 电脑客户端登录，mobile 手机客户端登录)
	 * @param fromModuleID 来源模块ID
	 * @param moduleID 去向模块ID
	 * @return TraderInfo,其中TraderInfo.auSessionId>0表示成功；-7：au没有此用户 -8：没有板块权限
	 * @throws RemoteException
	 */
	public TraderLogonInfo remoteLogon(String traderID, long auSessionId, String logonIP, String logonType, int fromModuleID, String toLogonType, int toModuleID) throws RemoteException,Exception;
	
	/**
	 * 验证交易员是否登录
	 * 
	 * @param traderID ， sessionID
	 * @param logonType 登录类型(web web服务登录，pc 电脑客户端登录，mobile 手机客户端登录)
	 * @return true:登录；false:未登录；
	 * @throws RemoteException
	 * @throws Exception 
	 */
	public boolean isLogon(String traderID, long sessionID, String logonType) throws  RemoteException,Exception;
	
	/** 
	 * 根据traderID查找TraderInfo对象属性
	 * @param traderID
	 * @return TraderInfo
	 * @throws RemoteException	 * 
	 */
	public TraderLogonInfo getTraderInfo(String traderID) throws RemoteException;
	
	/**
	 * 代为委托员登录
	 * 
	 * @param consigner 包括ID，密码和IP
	 * @return 成功返回sessionID； -1：代为委托员代码不存在；-2：口令不正确；-3：禁止登录；-204：交易服务器已关闭
	 */
	public long consignerLogon(Consigner consigner) throws RemoteException;
	
	/**
	 * 交易员下委托单
	 * 
	 * @param sessionID  登录sessionID
	 * @param order  委托对象
	 * @param logonType 登录类型(web web服务登录，pc 电脑客户端登录，mobile 手机客户端登录)
	 * @return 0：成功；1：用户身份不合法；2：市场未启用；3：不是交易时间；4：不是代为委托员交易时间；5：交易员和代为委托员不能同时存在；
	 * 		   10：此商品处于禁止交易状态；11：商品不属于当前交易节；12：委托价格超出涨幅上限；13：委托价格低于跌幅下限；14：委托价格不在此商品最小价位变动范围内；15：不存在此商品；16：委托数量不在此商品最小变动数量范围内；
	 *         30：此交易员不存在；31：此交易员没有操作该客户的权限；32：此交易客户不存在；33：此交易客户为禁止交易状态；34：此交易商不存在；35：此交易商为禁止交易状态；37：此代为委托员没有操作该交易商的权限；38：此代为委托员不存在；  
	 *         40：计算交易保证金错误；41：计算交易手续费错误；42：此委托已成交或已撤单；
	 *         200：代码异常而失败；
	 *         -1  资金余额不足;-2  超过交易商对此商品的最大订货量;-3  超过客户总的最大订货量;-4  超过品种最大订货量;-5  超过商品最大订货量;-6  超过交易商对此商品的最大净订货量;-7  超过单笔最大委托量;
	 *         -11  此委托已处于正在撤单状态；-12  此委托已全部成交了；-13  此委托已完成撤单了；
	 *         -21  持仓不足；-22  指定仓不足；
	 *         -99  执行存储时未找到相关数据；-100 执行存储失败；
	 *         -204：交易服务器已关闭；-206委托信息不能为空;
	 * @throws RemoteException
	 * @throws Exception 
	 */
	public OrderReturnValue order(long sessionID, Order order, String logonType) throws RemoteException,Exception;
	
	/**
	 * 代为委托员下委托单
	 * 
	 * @param sessionID
	 * @param order
	 * @return 同上交易员下单
	 * @throws RemoteException
	 */
	public int consignerOrder(long sessionID, Order order) throws RemoteException;
	
	/**
	 * 交易中重算交易资金
	 * 
	 * @return 1：成功；-100 执行存储失败
	 * -204：交易服务器已关闭；-207：交易服务器不是暂停状态，不能调整！
	 */
	public int tradingReComputeFunds() throws RemoteException;
	
	/**
	 * 闭市结算时检查是否存在冻结数量
	 * 
	 * @return 0：检查冻结数量通过；2：存在冻结数量；
	 * @throws RemoteException
	 */
	public int checkFrozenQtyAtBalance() throws RemoteException;

	/**
	 * 闭市结算
	 * 
	 * @return 1：成功；-2 交收处理出错;
	 * -100 执行存储失败
	 * @throws RemoteException
	 */
	public int balance() throws RemoteException;	

	/**
	 * 交易员注销登录
	 * 
	 * @param sessionID
	 * @param logonType 登录类型(web web服务登录，pc 电脑客户端登录，mobile 手机客户端登录)
	 * @throws RemoteException
	 * @throws Exception 
	 */
	public void logoff(long sessionID, String logonType) throws RemoteException,Exception;
	/**
	 * 代为委托员注销登录
	 * 
	 * @param sessionID
	 * @throws RemoteException
	 */
	public void consignerLogoff(long sessionID) throws RemoteException;
	
	/**
	 * 验证用户ID和密码
	 * 
	 * @param userID
	 * @param pswd
	 * @return 成功返回0；-1：交易用户ID不存在；-2：密码不正确；-3：禁止登录；-6:交易板块被禁止;-204：交易服务器已关闭
	 * @throws RemoteException
	 */
//	public int checkUser(String userID, String pswd) throws RemoteException ;
	
	/**
	 * 根据sessionID取得交易员ID
	 * 
	 * @param sessionID
	 * @param logonType 登录类型(web web服务登录，pc 电脑客户端登录，mobile 手机客户端登录)
	 * @return 返回交易员ID
	 * @throws RemoteException
	 * @throws Exception 
	 */
	public String getUserID(long sessionID, String logonType) throws RemoteException,Exception;
	
	/**
	 * 根据sessionID取得代为委托员ID
	 * 
	 * @param sessionID
	 * @return 返回代为委托员ID
	 * @throws RemoteException
	 */
	public String getConsignerID(long sessionID) throws RemoteException ;

	/**
	 * 返回在线交易员
	 * 返回所有当前有效的登录用户，如果在MUTIL_MODE模式下，同一个用户有多个连接则返回多条记录
     * 返回一个字符串数组,数组中的每一个元素代表一个用户登录连接,内容是用户ID,登录的时间和登录IP,用","加以分隔。
	 * @throws Exception 
     * 
	 */
	public List getTraders() throws RemoteException,Exception;
	
	/**
	 * 返回在线代为委托员
	 * 内容是用户ID、登录的时间和IP
     * 
     * @return list
	 * @throws RemoteException
	 */
	public List getConsigners() throws RemoteException;
	
	/**
	 * 根据交易员ID取得交易商ID
	 * 
	 * @param traderID
	 * @return 返回交易商ID
	 * @throws RemoteException
	 */
	public String getFirmID(String traderID) throws RemoteException;
	
	/**
	 * 手工交收处理
	 * 
	 * @param commodityID  商品代码
	 * @return 1 成功；-1 交收时所需数据不全; -100 其它错误; 
	 * -202 不是闭市或结算完成状态，不能交收; -204：交易服务器已关闭;
	 */
	public int settleProcess(String commodityID) throws RemoteException;	
	
	/**
	 * 修改口令
	 * @param userId
	 * @param password
	 * @return
	 * 成功返回0；-1：原口令不正确； -2操作异常
	 */
	public int changePassowrd(String userId, String passwordOld,String password,String logonIP) throws RemoteException;
	
	/**
	 * 强减平仓委托录入
	 * 直接插入委托表，并且是已撤单的
	 * @param order, 需要传递order.getCommodityID(),order.getBuyOrSell(),order.getQuantity(),order.getPrice(),order.getCustomerID() 5个参数
	 * @return 0：成功；-1：此交易客户不存在；-2：买卖标志错误；-3：此商品不存在；-202 不是闭市状态，不能录入强减委托;
	 */
	public int deductCloseOrder(Order order) throws  RemoteException;
	
}
