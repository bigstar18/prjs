package gnnt.MEBS.timebargain.server.rmi;

import gnnt.MEBS.timebargain.server.model.SystemStatus;
import gnnt.MEBS.timebargain.server.model.TradeMonitor;

import java.io.Serializable;
import java.rmi.*;
import java.util.Map;

public interface TradeMonitorRMI  extends Remote   {



	/***
	 * 查询所有队列 
	 * @return
	 */
	public  Map<String,Object> querySaleOrders()throws Exception;


	/**
	 *  查询买卖队列  
	 * @return
	 * @throws RemoteException
	 */
	public  Map<String,Object> querySaleOrders(String commodity,Map<String,Integer> map)throws RemoteException;

	 
	/***
	 *  查询等待撮合队列
	 * @return
	 * @throws Exception 
	 */
	public Map<String, Object> queryWaitOrders(String commodity,Map<String,Integer> map) throws RemoteException;

}
