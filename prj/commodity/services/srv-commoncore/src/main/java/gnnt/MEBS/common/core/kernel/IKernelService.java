package gnnt.MEBS.common.core.kernel;

import java.rmi.RemoteException;

/**
 * 核心服务
 * 
 * @author xuejt
 * 
 */
public interface IKernelService {
	/**
	 * 
	 * 用于客户端连接服务测试使用的方法
	 * <br/><br/>
	 */
	public void checkStart();

	/**
	 * 关闭服务
	 * 
	 * @return
	 */
	public boolean shutdown();

	/**
	 * 给后台在线用户发送消息
	 * 
	 * @param msg
	 *            消息内容
	 */
	public void sendMgrTopicMsg(String msg) throws RemoteException;

	/**
	 * 给前台在线用户发送消息
	 * 
	 * @param msg
	 *            消息内容
	 */
	public void sendFrontTopicMsg(String msg) throws RemoteException;

	/**
	 * 给后台登录用户发送消息
	 * 
	 * @param userID
	 *            用户代码
	 * @param msg
	 *            消息内容
	 */
	public void sendMgrQueueMsg(String userID, String msg)
			throws RemoteException;

	/**
	 * 给前台登录用户发送消息
	 * 
	 * @param userID
	 *            用户代码
	 * @param msg
	 *            消息内容
	 */
	public void sendFrontQueueMsg(String userID, String msg)
			throws RemoteException;

}
