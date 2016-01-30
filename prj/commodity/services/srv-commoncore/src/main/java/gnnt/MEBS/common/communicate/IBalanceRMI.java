package gnnt.MEBS.common.communicate; 

import gnnt.MEBS.common.communicate.model.BalanceVO;

import java.rmi.Remote;
import java.rmi.RemoteException;

/** 
 * @author liulin  E-mail: liulin@gnnt.com.cn
 * @version 创建时间：2013-2-28 下午02:09:15 
 * 类说明    结算rmi接口类
 */
public interface IBalanceRMI extends Remote{
	
	/**
	 * 检查各个子系统是否可以做财务结算,并返回是否可以做结算以及结算日期，如果失败返回失败信息
	 * @return BalanceVO 结算信息VO
	 * @throws RemoteException
	 */
	public BalanceVO checkBalance() throws RemoteException;
	
	/**
	 * 通知各个子系统财务结算完成
	 * @return true 成功 false 失败
	 * @throws RemoteException
	 */
	public boolean noticeSubsystemStatus() throws RemoteException;

}
 