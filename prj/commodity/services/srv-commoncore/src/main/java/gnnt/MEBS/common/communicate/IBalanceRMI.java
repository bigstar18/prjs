package gnnt.MEBS.common.communicate; 

import gnnt.MEBS.common.communicate.model.BalanceVO;

import java.rmi.Remote;
import java.rmi.RemoteException;

/** 
 * @author liulin  E-mail: liulin@gnnt.com.cn
 * @version ����ʱ�䣺2013-2-28 ����02:09:15 
 * ��˵��    ����rmi�ӿ���
 */
public interface IBalanceRMI extends Remote{
	
	/**
	 * ��������ϵͳ�Ƿ�������������,�������Ƿ�����������Լ��������ڣ����ʧ�ܷ���ʧ����Ϣ
	 * @return BalanceVO ������ϢVO
	 * @throws RemoteException
	 */
	public BalanceVO checkBalance() throws RemoteException;
	
	/**
	 * ֪ͨ������ϵͳ����������
	 * @return true �ɹ� false ʧ��
	 * @throws RemoteException
	 */
	public boolean noticeSubsystemStatus() throws RemoteException;

}
 