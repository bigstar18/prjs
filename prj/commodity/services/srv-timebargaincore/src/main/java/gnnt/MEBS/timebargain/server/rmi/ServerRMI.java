package gnnt.MEBS.timebargain.server.rmi;

import gnnt.MEBS.timebargain.server.model.SystemStatus;

import java.rmi.*;
import java.util.Date;

/**
 * 交易服务器控制RMI接口.
 *
 * <p><a href="ServerRMI.java.html"><i>View Source</i></a></p>
 *
 * @version 1.0.0.4
 * @author <a href="mailto:chenxc@gnnt.com.cn">chenxc</a>
 */
public interface ServerRMI extends Remote {

	/**
	 * 交易服务器初始化
	 * 
	 * @throws RemoteException
	 */
	public void start() throws RemoteException;

	/**
	 * 交易服务器闭市
	 * 
	 * @throws RemoteException
	 */
	public void close() throws RemoteException;

	/**
	 * 交易服务器控制
	 * 
	 * @param status  0：暂停交易；1：继续交易
	 * @throws RemoteException
	 */
	public void ctlTrade(int status) throws RemoteException ;
	
	/**
	 * 根据当前时间来恢复暂停后的交易
	 * 
	 * @param recoverTime 恢复时间 HH:mm:ss表示
	 * @throws RemoteException
	 */
	public void timingContinueTrade(String recoverTime) throws RemoteException;
	
	/**
	 * 交易结束
	 * 
	 * @throws RemoteException
	 */
	public void tradeEnd() throws RemoteException;
	
	/**
	 * 恢复交易
	 * 主要用于异常处理，防止误点交易结束后恢复交易
	 * 
	 * @throws RemoteException
	 */
	public void recoverTrade() throws RemoteException;

	/**
	 * 获取交易服务器状态对象
	 * 
	 * @return 交易服务器状态对象
	 * @throws RemoteException
	 */
	public SystemStatus getSystemStatus() throws RemoteException;

	/**
	 * 刷新交易节并且定时任务立刻生效
	 * 
	 * @throws RemoteException
	 */
	public void refreshTradeTime() throws RemoteException;
	
	/**
	 * 刷新内存信息
	 * 
	 * @throws RemoteException
	 */
	public void refreshMemory() throws RemoteException;
	
	/**
	 * 刷新交易商队列
	 * 
	 * @param firmID: 为空则所有，否则单个
	 * @throws RemoteException
	 */
	public void refreshFirmQueue(String firmID) throws RemoteException ;
	
	/**
	 * 查询数据库当前日期时间
	 * @return sysdate
	 */
	public Date getCurDbDate() throws RemoteException;

}
