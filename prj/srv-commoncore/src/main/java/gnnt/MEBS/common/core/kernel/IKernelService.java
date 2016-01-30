package gnnt.MEBS.common.core.kernel;

import java.rmi.RemoteException;

/**
 * ���ķ���
 * 
 * @author xuejt
 * 
 */
public interface IKernelService {
	/**
	 * 
	 * ���ڿͻ������ӷ������ʹ�õķ���
	 * <br/><br/>
	 */
	public void checkStart();

	/**
	 * �رշ���
	 * 
	 * @return
	 */
	public boolean shutdown();

	/**
	 * ����̨�����û�������Ϣ
	 * 
	 * @param msg
	 *            ��Ϣ����
	 */
	public void sendMgrTopicMsg(String msg) throws RemoteException;

	/**
	 * ��ǰ̨�����û�������Ϣ
	 * 
	 * @param msg
	 *            ��Ϣ����
	 */
	public void sendFrontTopicMsg(String msg) throws RemoteException;

	/**
	 * ����̨��¼�û�������Ϣ
	 * 
	 * @param userID
	 *            �û�����
	 * @param msg
	 *            ��Ϣ����
	 */
	public void sendMgrQueueMsg(String userID, String msg)
			throws RemoteException;

	/**
	 * ��ǰ̨��¼�û�������Ϣ
	 * 
	 * @param userID
	 *            �û�����
	 * @param msg
	 *            ��Ϣ����
	 */
	public void sendFrontQueueMsg(String userID, String msg)
			throws RemoteException;

}
