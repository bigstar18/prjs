package gnnt.trade.bank.processorrmi;
import gnnt.trade.bank.vo.BankCompareInfoValue;
import gnnt.trade.bank.vo.BankTransferValue;
import gnnt.trade.bank.vo.BankValue;
import gnnt.trade.bank.vo.CapitalCompare;
import gnnt.trade.bank.vo.CapitalValue;
import gnnt.trade.bank.vo.ChargeAgainstValue;
import gnnt.trade.bank.vo.CitysValue;
import gnnt.trade.bank.vo.CompareResult;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.FirmBalanceValue;
import gnnt.trade.bank.vo.FirmMessageVo;
import gnnt.trade.bank.vo.FirmTradeStatus;
import gnnt.trade.bank.vo.InterfaceLog;
import gnnt.trade.bank.vo.MoneyInfoValue;
import gnnt.trade.bank.vo.QSChangeResult;
import gnnt.trade.bank.vo.QSRresult;
import gnnt.trade.bank.vo.ReturnValue;
import gnnt.trade.bank.vo.RgstCapitalValue;
import gnnt.trade.bank.vo.TradeDetailAccount;
import gnnt.trade.bank.vo.TradeResultValue;
import gnnt.trade.bank.vo.bankdz.boc.ClientState;
import gnnt.trade.bank.vo.bankdz.gs.sent.BankFirmRightValue;
import gnnt.trade.bank.vo.bankdz.gs.sent.FirmRights;
import gnnt.trade.bank.vo.bankdz.gs.sent.OpenOrDelFirmValue;
import gnnt.trade.bank.vo.bankdz.gs.sent.ProperBalanceValue;
import gnnt.trade.bank.vo.bankdz.gs.sent.TradingDetailsValue;
import gnnt.trade.bank.vo.bankdz.hx.sent.HXSentQSMsgValue;
import gnnt.trade.bank.vo.bankdz.pf.sent.FundsMarg;
import gnnt.trade.bank.vo.bankdz.pf.sent.Margins;
import gnnt.trade.bank.vo.bankdz.pf.sent.TradeList;
import gnnt.trade.bank.vo.bankdz.sfz.resave.child.BatCustDzBChild;
import gnnt.trade.bank.vo.bankdz.sfz.resave.child.BatCustDzFailChild;
import gnnt.trade.bank.vo.bankdz.sfz.resave.child.BatFailResultChild;
import gnnt.trade.bank.vo.bankdz.sfz.resave.child.KXHfailChild;
import gnnt.trade.bank.vo.bankdz.xy.XYMarketMoney;
import gnnt.trade.bank.vo.bankdz.xy.resave.FFHDValue;
import gnnt.trade.bank.vo.bankdz.xy.resave.ZFPHValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.RZDZValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.RZQSValue;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public interface CapitalProcessorRMI extends Remote{


	/**
	 * ȡ���г�ҵ����ˮ��
	 * @return long �г�ҵ����ˮ�ţ�����-1��ʾ����ʧ��
	 */
	public long getMktActionID() throws RemoteException;

	/**
	   * ȡ��������Ϣ�б�
	   * @param filter ��ѯ����
	   * @return Vector ÿһ��ΪBankValue 
	   * @throws SQLException,ClassNotFoundException
	   */
	public Vector<BankValue> getBankList(String filter) throws RemoteException;
	
	/**
	 * �����adapter����
	 * @param bankID ���д���
	 * @param money ���
	 * @param firmID �����̴���
	 * @param account �����������ʺ�
	 * @param bankTime ���ж����ʱ��
	 * @param funID ���ж�ҵ����ˮ��
	 * @param actionID ת��ģ��ҵ����ˮ��
	 * @param funFlag ���ж˲����ɹ���ʧ�ܵı�־ 0���ɹ� 1��ʧ�ܣ�actionID>0ʱ��Ч
	 * @param remark ��ע
	 * @return long ���нӿ�ҵ����ˮ��,����<0��ֵ��ʾ����ʧ��
	 *                             (-10001���Ƿ������ʺ� -10002���Ƿ������̴��� -10003�������̴�����ʺŶ�Ӧ��ϵ���� -10004�����ݿ��쳣 -10005��ϵͳ�쳣 -10006����������
	 */
	public long inMoney
	(String bankID, String firmID ,String account ,Timestamp bankTime ,double money ,String funID, long actionID, int funFlag, String remark) throws RemoteException;

	/**
	 * ����������˵���
	 * @param bankID ���д���
	 * @param firmID �����̴���
	 * @param account �����������ʺ�
	 * @param accountPwd �����������ʺ�����
	 * @param money   ���
	 * @param remark   ��ע
	 * @return long ���нӿ�ҵ����ˮ��,����<0��ֵ��ʾ����ʧ��
	 *                             (-10001���Ƿ������ʺ� -10002���Ƿ������̴��� -10003�������̴�����ʺŶ�Ӧ��ϵ���� -10004�����ݿ��쳣 -10005��ϵͳ�쳣 -10006���������� --10008:�������ύʧ�� )
	 */
	public long inMoneyMarket(String bankID, String firmID ,
			String account,String accountPwd,double money ,
			String remark,String InOutStart,
			String PersonName, String AmoutDate, String BankName,
			String OutAccount) throws RemoteException;
	
	/**
	 * ����������˵���
	 * @param bankID ���д���
	 * @param firmID �����̴���
	 * @param account �����������ʺ�
	 * @param accountPwd �����������ʺ�����
	 * @param money   ���
	 * @param remark   ��ע
	 * @return long ���нӿ�ҵ����ˮ��,����<0��ֵ��ʾ����ʧ��
	 *                             (-10001���Ƿ������ʺ� -10002���Ƿ������̴��� -10003�������̴�����ʺŶ�Ӧ��ϵ���� -10004�����ݿ��쳣 -10005��ϵͳ�쳣 -10006���������� --10008:�������ύʧ�� )
	 */
	public long inMoneyMarket(String bankID, String firmID ,
			String account,String accountPwd,double money ,
			String remark) throws RemoteException;
	/**
	 * ����
	 * @param bankID ���д���
	 * @param money ���
	 * @param firmID �����̴���
	 * @param bankAccount �����ʺ�
	 * @param operator �������
	 * @param express 0������ 1���Ӽ�
	 * @param type 0���г��˳��� 1�����ж˳���
	 * @return long ���нӿ�ҵ����ˮ��,����<0��ֵ��ʾ����ʧ��                          
	 * @throws 
	 */
	public ReturnValue outMoney(String bankID,double money,String firmID ,String bankAccount , String funid,String operator,int express,int type) throws RemoteException;
	
	/**
	 * ��ת�ʽ�
	 * @param bankID ���д���
	 * @param money ���
	 * @param type �ʽ�ת���� 0����ת����������
	 * @param operator �ʽ�ת�����
	 * @param firmID �����̴���
	 * @return long ���нӿ�ҵ����ˮ�� <0��ֵ��ʾ����ʧ��
	 * @throws 
	 */
	public long transferMoney(String bankID , int type , double money , String operator ,String firmID) throws RemoteException;
	
	/**
	 * �����ʺ�ע��
	 * @param correspondValue
	 * @return  long ���������0 �ɹ�  ��0 ʧ��(1:��Ϣ������ 2�������̲����� 3�����в�����  4���ʺ���ע�� 5:����ǩԼʧ�� 6�����ݿ����ʧ�� 7��ϵͳ�쳣
	 */
	public long rgstAccount(CorrespondValue correspondValue) throws RemoteException;

	/**
	 * �����ʺ�ע��
	 * @param bankID ���д���
	 * @param firmID �����̴���
	 * @param bankAccount �����ʺ�
	 * @return int ���������0 �ɹ�  ��0 ʧ��(1:��Ϣ������ 2���ʺ�δע�� 3�����н�Լʧ�� 4�����ݿ����ʧ�� 5��ϵͳ�쳣)
	 * @throws 
	 */
	public long delAccount(CorrespondValue correspondValue) throws RemoteException;

	/**
	 * �����ʺ�ע��
	 * @param bankID ���д���
	 * @param firmID �����̴���
	 * @param bankAccount �����ʺ�
	 * @return int ���������0 �ɹ�  ��0 ʧ��(1:��Ϣ������ 2���ʺ�δע�� 3�����н�Լʧ�� 4�����ݿ����ʧ�� 5��ϵͳ�쳣)
	 * @throws 
	 */
	public long delAccountMaket(CorrespondValue correspondValue) throws RemoteException;

	/**
	 * �����з�������
	 * @param bankID ����ID ���Ϊnull����������������ķ������ݷ���
	 * @param date ������������
	 * @return int ���������0 �ɹ�  ��0ʧ��  1:û���ҵ�bankID��Ӧ�������� 2:����ϵͳ��δ������� 3:�쳣 ���������������������
	 */
	public int setMoneyInfo(String bankID,java.util.Date date) throws RemoteException;
	
	/**
	 * �����з�������
	 * @param bankID ����ID ���Ϊnull����������������ķ������ݷ���
	 * @param date ������������
	 * @param moduleid ����
	 * @return int ���������0 �ɹ�  ��0ʧ��  1:û���ҵ�bankID��Ӧ�������� 2:����ϵͳ��δ������� 3:�쳣 ���������������������
	 */
	public long setMoneyInfoAutoOrNo(String bankID,java.util.Date date,int moduleid) throws RemoteException;
	
	/**
	 * ��ȡ���ж�����Ϣ
	 * @param bankID  ����ID ���Ϊnull��֪ͨ����������ȡ��������
	 * @param date ��ȡ��������
	 * @return  ���������0 �ɹ�   -30011:û���ҵ�bankID��Ӧ�������� -30012:��������Ϣ�������ݿⷢ���������д���Ϊ�գ� -30013����������Ϣ�������ݿⷢ���������д��벻Ϊ�գ�
	 */
	public int getBankCompareInfo(String bankID,java.util.Date date) throws RemoteException;

	
	/**
	 * ���˼�����������Ƿ��ȶԱ���ˮ����
	 * @return int 0:���Աȱ��� 1:�ȶԱ������������ 2���ȶԱ������������� -1:�쳣
	 * @throws 
	 */
	public int checkMoneyByNumber() throws RemoteException;
	
	
	/**
	 * �ֹ����˼��
	 * @param bankID ���д���
	 * @param date ��������
	 * @return Vector<CompareResult> ���������������Ϣ��һ�µ�����
	 * @throws 
	 */
	public Vector<CompareResult> checkMoney(String bankID,java.util.Date date) throws RemoteException;

	/**
	 * ��ѯ�����̵��������������
	 * @param bankID ���б��
	 * @param firmIDs �����̴��뼯
	 * @param date ת������
	 * @return Vector<CapitalCompare>
	 */
	public Vector<CapitalCompare> sumResultInfo(String bankID,String[] firmIDs,java.util.Date date) throws RemoteException;
	/**
	 * �Զ����˼�� �����õ��Զ�����ʱ��Ϊ׼
	 * @param date ��������
	 * @return Vector<CompareResult> ���������������Ϣ��һ�µ�����
	 * @throws 
	 */
	public Vector<CompareResult> checkMoney(java.util.Date date) throws RemoteException;

	/**
	 * д�����ж�����Ϣ
	 * @param list ������Ϣ��ÿ��ΪMoneyInfoValue
	 * @return int ���������0 �ɹ�  ��0 ʧ��: -1�������ڲ�һ��  -2������ϢΪ��  -3ϵͳ�쳣
	 */
	public int insertBankCompareInfo(Vector<MoneyInfoValue> list) throws RemoteException;

	/**
	 * ��������ID��ȡ�г�������Ϣ
	 * @param bankID ���д���
	 * @return Hashtable<name,value>
	 */
	public Hashtable<String,String> getBankInfo(String bankID) throws RemoteException;

	/**
	 * ��ȡ�ʽ���ˮ����
	 * @param filter 
	 * @return ��ȡ�ʽ���ˮ����
	 */
	public Vector<CapitalValue> getCapitalList(String filter) throws RemoteException;

	/**
	 * ��ȡ���������ж�Ӧ��ϵ
	 * @param filter 
	 * @return 
	 */
	public Vector<CorrespondValue> getCorrespondValue(String filter)  throws RemoteException;
	/**
	 * ��ȡ���������ж�Ӧ��ϵ
	 * @param filter 
	 * @return 
	 */
	public Map<String,CorrespondValue> getCorrespondValue(Vector<String> firmIDs,String bankID)  throws RemoteException;
	/**
	 * ��֤
	 * @param actionID  ת��ģ��ҵ����ˮ��
	 * @param funID ����ҵ����ˮ��
	 * @return  ���������0 ����ɹ���-1 �����Ƿ���-2 δ�ҵ�Ҫ��֤�����ݣ�-3 ���ݴ���ʧ�ܣ�-4 ϵͳ�쳣
	 */
	public ReturnValue chargeAgainst(ChargeAgainstValue cav) throws RemoteException;
	/**
	 * ��֤(�г�������)
	 * @param actionID  ת��ģ��ҵ����ˮ��
	 * @param funID ����ҵ����ˮ��
	 * @return  ���������0 ����ɹ���-1 �����Ƿ���-2 δ�ҵ�Ҫ��֤�����ݣ�-3 ���ݴ���ʧ�ܣ�-4 ϵͳ�쳣
	 */
	public ReturnValue chargeAgainstMaket(ChargeAgainstValue cav) throws RemoteException;
	
	/**
	 * ȡ�ý����̽���ϵͳ�ʽ���ϸ
	 * @param Date ��������
	 * @return  Hashtable key �����̴��룻value �������ʽ����Լ��ϣ�key �ʽ��������ƣ�value �ʽ�����ֵ��
	 */
	public Hashtable<String, Hashtable<String, String>> getFirmTradeBal(Date b_date) throws RemoteException;
	
	/**
	 * ���г��ܽ��[�ʽ��Ȩ��]�������ж���
	 * @param filter
	 * @param moduleid
	 * @return  Hashtable
	 */
	public long sendTotalMoneyToBank(String bankid,Hashtable<String, Double> ht) throws RemoteException;
	
	/**
	 * ������ˢ���д���״̬��֪ͨ����������
	 * @param rv
	 * @return
	 * @throws RemoteException
	 */
	public long  outMoneyForAccess(int rvResult,String bankid,String firmid,String account,String actionid,String funcid) throws RemoteException;
	/**
	 * public String testRmi() throws RemoteException;
	 * test
	 */
	public String testRmi() throws RemoteException;
	
	/**
	 * ���������ѯ�������д����������ʵ��������
	 * @return hashtable<bankid,adapterClassname>
	 * 
	 */	
	public Hashtable<String,String> getBankIdAndAdapterClassname() throws RemoteException;
	
	/**
	 * �ṩ���������ı����������ݵķ���
	 * @param mv��bank data  ready: true:insertBankMoneyInfo false:checkMoney
	 * @return long 0:success <>0:failure
	 */
	public long insertBankMoneyInfo(Vector<MoneyInfoValue> mv,int ready) throws RemoteException;
	
	/**
	 * ���п�������
	 * ��� �����̴����������ʺŶ�Ӧ��ϵ ��ǩԼ״̬
	 */
	public ReturnValue openAccount(CorrespondValue cv) throws RemoteException;
	/**
	 * ������������
	 * ��� �����̴����������ʺŶ�Ӧ��ϵ ��ǩԼ״̬
	 */
	public ReturnValue destroyAccount(CorrespondValue cv) throws RemoteException;
	
	/**
	 * ����ʻ�����
	 * ��� �����̴����������ʺŶ�Ӧ��ϵ ��ǩԼ״̬
	 */
	public ReturnValue modAccount(CorrespondValue cv1,CorrespondValue cv2) throws RemoteException;
	
	/**
	 * ��ѯ�������г��ʽ�
	 * @return  FirmBalanceValue
	 */
	public FirmBalanceValue getMarketBalance(String firmid) throws RemoteException;
	
	/**
	 * ��ѯ�������г������ʽ�������ʺ����
	 * @return  FirmBalanceValue
	 */
	public FirmBalanceValue getFirmBalance(String bankid,String firmid,String pwd) throws RemoteException;
	
	/**
	 * �����з������� ��HashTable��ʽ��
	 * @param bankID ����ID ���Ϊnull����������������ķ������ݷ���
	 * @param date ������������
	 * @return int ���������0 �ɹ�  ����ʧ��  1:û���ҵ�bankID��Ӧ�������� 2:����ϵͳ��δ������� 3:�쳣 >100��(int-100)���������������
	 */
	public long setMoneyInfoByHashtable(String bankID,Hashtable<String, TradeResultValue> ht)  throws RemoteException;
	
	/**
	 * �������[1��]
	 * @param actionID  ת��ģ��ҵ����ˮ��
	 * @param funFlag  ��˽��
	 * @param audit  �ֹ�[false]���Զ�[true] 
	 * @return long 0 �ɹ� <0ʧ�� 
	 */
	public long outMoneyAudit(long actionID,boolean funFlag)  throws RemoteException;
	
	/**
	 * ����������
	 * @param actionID  ת��ģ��ҵ����ˮ��
	 * @param funFlag  ��˽�� 
	 * @return long 0 �ɹ� <0ʧ�� 
	 */
	public long outMoneySecondAudit(long actionID,boolean funFlag)  throws RemoteException;
	
	/**
	 * ��ȡ�����ʽ���ˮ����
	 * @param filter 
	 * @return ��ȡ�ʽ���ˮ����
	 */
	public Vector<BankCompareInfoValue> getBankCapList(String filter)  throws RemoteException;
	/**
	 * ��ѯ���������½���Ա��Ϣ
	 * @param firmid ����Աid
	 * @return string ����
	 */
	public FirmMessageVo getFirmMSG(String firmid)  throws RemoteException;
	
	/**
	   * ȡ����ϵͳ����״̬
	   * @return int ϵͳ״̬
	   * @throws SQLException
	   */
	public boolean getTraderStatus() throws RemoteException;
	/**
	 * ��˴����е���Ϣ
	 * actionID   ��ˮ��,
	 * funFlag    ͨ�����ܾ�,
	 */
	public long moneyInAudit(long actionID,boolean funFlag) throws RemoteException;
	/**
	 * �ж��Ƿ���Է���ת�˽���
	 */
	public long tradeDate(String bankID) throws RemoteException;
	/**
	 * �ж�����Ϸ���,true ��֤�ɹ�;false ��֤ʧ��
	 * @param firmID �����̴���
	 * @param password ����������
	 * @return long (0 �ɹ�;1 δ��������;-1 ��֤ʧ��;-2 ��ѯ����������)
	 */
	public long isPassword(String firmID, String password) throws RemoteException;
	/**
	 * �޸Ľ���������
	 * @param firmID �����̴���
	 * @param password ������ԭ����
	 * @param chpassword �����̽�Ҫ��������
	 * @return long(0 �ɹ�;-1 ԭ�������;-2 Ϊ�ҵ�������)
	 */
	public long modPwd(String firmID, String password, String chpassword) throws RemoteException;
	/**
	 * ���п�������
	 * ��� �����̴����������ʺŶ�Ӧ��ϵ ��ǩԼ״̬
	 */
	public ReturnValue openAccountMarket(CorrespondValue cv) throws RemoteException;
	/**
	 * ͬ�����˺ŷ���
	 */
	public ReturnValue synchroAccountMarket(CorrespondValue cv) throws RemoteException;
	/**
	 * ȡ��������Ϣ
	 */
	public BankValue getBank(String bankID) throws RemoteException;
	/**
	 * �ж�ϵͳ�Ƿ��Ѿ�����
	 */
	public boolean getTradeFlag() throws RemoteException;

	/**
	 * �ж�ĳһ���Ƿ��ǽ�����
	 * @param tradeDate ����
	 * @return ReturnValue 
	 */
	public ReturnValue isTradeDate(java.util.Date tradeDate) throws RemoteException;
	
//*********************************�ֹ������*************************************
	/**
	 * ������г��˵���
	 * @param bankID ���д���(����������)
	 * @param firmID �����̴���V
	 * @param account �����������ʺ�
	 * @param accountPwd �����������ʺ�����
	 * @param money   ���
	 * @param remark   ��ע
	 * @return long ���нӿ�ҵ����ˮ��,����<0��ֵ��ʾ����ʧ��
	 * (-10001���Ƿ������ʺ� -10002���Ƿ������̴��� -10003�������̴�����ʺŶ�Ӧ��ϵ���� -10004�����ݿ��쳣 -10005��ϵͳ�쳣 -10006���������� --10008:�������ύʧ�� )
	 */
	@SuppressWarnings("static-access")
	public long inMoneyNoAdapter(String maketbankID, String firmID,String account,String bankName,double money ,String remark) throws RemoteException;
	/**
	 * ����
	 * @param bankID ���д���
	 * @param money ���
	 * @param firmID �����̴���
	 * @param bankAccount �����ʺ�
	 * @param funID ������ˮ��
	 * @param express 0������ 1���Ӽ�
	 * @param type 0���г��˳��� 1�����ж˳���
	 * @return long ���нӿ�ҵ����ˮ��,����<0��ֵ��ʾ����ʧ��                          
	 * @throws 
	 */
	public long outMoneyNoAdapter(String maketbankID,double money,String firmID,String bankName,String account,String operator,int express,int type) throws RemoteException;
	/**
	 * ��ѯ�������г��ʽ�
	 * @return  FirmBalanceValue
	 */
	public FirmBalanceValue getBalanceNoAdapter(String firmid) throws RemoteException;
	/**
	 * ��������
	 * @param actionID  ת��ģ��ҵ����ˮ��
	 * @param funFlag  ��˽��
	 * @param audit  �ֹ�[false]���Զ�[true] 
	 * @return long 0 �ɹ� <0ʧ�� 
	 */
	public long moneyAuditNoAdapter(long actionID,boolean funFlag) throws RemoteException;
	/**
	 * �޸Ľ�������Ϣ(�ֹ������)
	 */
	public ReturnValue modAccountNoAdapter(CorrespondValue correspondValue) throws RemoteException;
	/**
	 * �����ʺ�ע��(�ֹ������)
	 * @param bankID ���д���
	 * @param firmID �����̴���
	 * @param bankAccount �����ʺ�
	 * @return int ���������0 �ɹ�  ��0 ʧ��(1:��Ϣ������ 2���ʺ�δע�� 3�����н�Լʧ�� 4�����ݿ����ʧ�� 5��ϵͳ�쳣)
	 * @throws 
	 */
	public long delAccountNoAdapter(CorrespondValue correspondValue) throws RemoteException;

	/**
	 * �жϽ������Ƿ���Խ�Լ
	 * @param firmID �����̴���
	 * @param bankID ���б��
	 * @return ReturnValue
	 */
	public ReturnValue ifQuitFirm(String firmID,String bankID) throws RemoteException;
//*********************************����ⶨ��*************************************
	/**���з�������˻�*/
	public ReturnValue relevanceAccount(CorrespondValue cv) throws RemoteException;
	/**��ӻ�Ա��������Ϣ*/
	public ReturnValue saveFirmKXH(Vector<KXHfailChild> vector,String bankID) throws RemoteException;
	/**��ӻ�Ա��������Ϣ*/
	public ReturnValue saveFirmKXH(java.util.Date date,String bankID) throws RemoteException;
	/**��ȡ���з������Ķ��˲�ƽ�ļ�*/
	public ReturnValue getBatCustDz(java.util.Date date,String bankID) throws RemoteException;
	/**��ȡ���з������Ķ��˲�ƽ�ļ�*/
	public ReturnValue getBatCustDz(Vector<BatCustDzFailChild> bcd,java.util.Date date,String bankID) throws RemoteException;
	/**��ȡ���з������Ļ�Ա����ļ�*/
	public ReturnValue getfirmBalanceFile(java.util.Date date,String bankID) throws RemoteException;
	/**��ȡ���з������Ļ�Ա����ļ�*/
	public ReturnValue getfirmBalanceFile(Vector<BatCustDzBChild> fbf,java.util.Date date,String bankID) throws RemoteException;
	/**��ȡ���н����̶���ʧ���ļ�*/
	public ReturnValue getFirmBalanceError(java.util.Date date,String bankID) throws RemoteException;
	/**��ȡ���н����̶���ʧ���ļ�*/
	public ReturnValue getFirmBalanceError(Vector<BatFailResultChild> fbe,java.util.Date date,String bankID) throws RemoteException;
	/**��ѯ���������ļ���״̬*/
	public ReturnValue getBankFileStatus(java.util.Date tradeDate,int type,String bankID) throws RemoteException;
	/**���������ļ���֪ͨ���ж���*/
	public ReturnValue sentMaketQS(java.util.Date date,String bankID) throws RemoteException;
//********************************�����������ⶨ��*************************************
	/**
	 * �޸�������ˮ��
	 */
	public ReturnValue modCapitalInfoStatus(long actionID,String funID) throws RemoteException;
	/**�����з��������Ѻ��ʽ�仯��*/
	public ReturnValue sentFirmBalance(String bankID,java.util.Date date) throws RemoteException;
	/**
	 * ���в�ѯ�г���ˮ
	 */
	public Map<String,CapitalValue> getCapitalValue(Vector<String> funids,String bankID) throws RemoteException;
	/**
	 * ����������������Ϣ
	 */
	public ReturnValue saveQSResult(String bankID,String tradeDate) throws RemoteException;
	/**
	 * ����������������Ϣ
	 */
	public ReturnValue saveQSResult(Vector<QSRresult> qsResult) throws RemoteException;
//********************************�����������ⶨ��***************************************
	/**
	 * �����з����г���������Ϣ
	 * @parm bankID ���б��
	 * @param firmIDs �����̴��뼯
	 * @return ReturnValue
	 */
	public ReturnValue synchronousFirms(String bankID,String[] firmIDs) throws RemoteException;
	/**
	 * ��̨���÷������������Ϣ������
	 * @param bankID �����̴���
	 * @param date ��������
	 * @return ReturnValue
	 */
	public ReturnValue hxSentQS(String bankID,java.util.Date date) throws RemoteException;
	/**
	 * ��̨���÷������������Ϣ������
	 * @param bankID �����̴���
	 * @param date ��������
	 * @return ReturnValue
	 */
	public ReturnValue hxSentDZ(String bankID,java.util.Date date) throws RemoteException;
	/**
	 * ���������û�ȡ���������Ϣ
	 * @param bankID
	 * @param date
	 * @return
	 * @throws RemoteException
	 */
	public Vector<HXSentQSMsgValue> hxGetQS(String bankID,java.util.Date date) throws RemoteException;
	/**
	 * ���������û�ȡ���������Ϣ
	 * @param bankID
	 * @param date
	 * @return
	 * @throws RemoteException
	 */
	public Vector<HXSentQSMsgValue> hxGetDZ(String bankID,java.util.Date date) throws RemoteException;
	/**
	 * ��ȡ��������ʧ���ļ���Ϣ
	 * @param bankID ���д���
	 * @param tradeDate ��������
	 * @return ReturnValue
	 * @throws RemoteException
	 */
	public ReturnValue hxGetQSError(String bankID,java.util.Date tradeDate) throws RemoteException;
	/**
	 * ��ȡ���ж���ʧ���ļ���Ϣ
	 * @param bankID ���д���
	 * @param tradeDate ��������
	 * @return ReturnValue
	 * @throws RemoteException
	 */
	public ReturnValue hxGetDZError(String bankID,java.util.Date tradeDate) throws RemoteException;
	/**
	 * ������������ʧ����Ϣ
	 * @param vector ��Ϣ����
	 * @return ReturnValue
	 * @throws RemoteException
	 */
	public ReturnValue hxSaveQSError(Vector<QSChangeResult> vector) throws RemoteException;
	/**
	 * �������ж���ʧ����Ϣ
	 * @param vector ��Ϣ����
	 * @return ReturnValue
	 * @throws RemoteException
	 */
	public ReturnValue hxSaveDZError(Vector<QSRresult> vector) throws RemoteException;
//********************************�ַ��������ⶨ��****************************************
	/**
	 * ��̨���ñ���������Ϣ
	 * @param bankID ���б��
	 * @param date �������������
	 */
	public ReturnValue pfdbQS(String bankID,java.util.Date date) throws RemoteException;
	/**
	 * ����Ȩ��仯��
	 * @param bankID ���б��
	 * @param date ��������
	 * @param sendCount ÿ��������������
	 * @param timeOutCount ���ͳ�ʱ����(��������ֹͣ����)
	 * @param faileCount ���з��ش�������(��������ֹͣ����)
	 * @return ReturnValue
	 */
	public ReturnValue getTradesDateDetailsList(String bankID,java.util.Date date,int sendCount,int timeOutCount,int faileCount) throws RemoteException;
	/**
	 * ����Ȩ��仯��
	 * @param bankID ���б��
	 * @param date ��������
	 * @param flag ����״̬(Nδ���� F���д���ʧ�� Y���д���ɹ�)
	 * @return Vector<TradeList>
	 */
	public Vector<TradeList> getTradesDateDetailsList(String bankID,java.util.Date date,String[] flag)throws RemoteException;
	/**
	 * ���Ͷ����ʽ�
	 * @param bankID ���б��
	 * @param date ��������
	 * @param sendCount ÿ��������������
	 * @param timeOutCount ���ͳ�ʱ����(��������ֹͣ����)
	 * @param faileCount ���з��ش�������(��������ֹͣ����)
	 * @return ReturnValue
	 */
	public ReturnValue getDongjieDetailList(String bankID,java.util.Date date,int sendCount,int timeOutCount,int faileCount) throws RemoteException;
	/**
	 * ���ض����ʽ�仯��
	 * @param bankID ���б��
	 * @param date ��������
	 * @param flag ����״̬(Nδ���� F���д���ʧ�� Y���д���ɹ�)
	 * @return Vector<Margins>
	 */
	public Vector<Margins> getDongjieDetailList(String bankID,java.util.Date date,String[] flag) throws RemoteException;
	/**
	 * ȡ�����̿����������ʽ�������
	 * @param bankID ���б��
	 * @param date ��������
	 * @return Hashtable<String,FundsMarg>
	 */
	public Hashtable<String,FundsMarg> getFundsMarg(String bankID,java.util.Date date) throws RemoteException;
//******************************************************�������ж���***************************************************8
	/**
	 * ���͹�������������Ϣ
	 * @param bankId ���б��
	 * @param firmId �����̴���
	 * @param qdate ��������
	 * @return ReturnValue
	 */
	public ReturnValue sendGHQS(String bankId,String firmId,java.util.Date qdate) throws RemoteException;
	/**
	 * ��ȡ�������н�����Ȩ��
	 * @param bankId ���б��
	 * @param firmId �����̴���
	 * @param qdate ��������
	 * @return List<FirmRights>
	 */
	public List<FirmRights> getRightsList(String bankId,String firmId,java.util.Date qdate) throws RemoteException;
	/**
	 * ��ȡ�����̵���Ľ�������
	 * @param bankId ���б��
	 * @param firmId �����̴���
	 * @param qdate ��������
	 * @return List<TradingDetailsValue>
	 */
	public List<TradingDetailsValue> getChangeBalance(String bankId,String firmId,java.util.Date qdate) throws RemoteException;
	/**
	 * ��ȡ����ǩ��Լ��������Ϣ
	 * @param bankId ���б��
	 * @param qdate ��������
	 * @return List<OpenOrDelFirmValue>
	 */
	public List<OpenOrDelFirmValue> getOpenOrDropMaket(String bankId,java.util.Date qdate) throws RemoteException;
	/**
	 * ������Ȩ��ķַֺ˶�
	 * @param list �ַֺ˶�����
	 * @return long
	 */
	public long setBankFirmRightValue(List<BankFirmRightValue> list) throws RemoteException;
	/**
	 * �ܷ�ƽ����
	 * @param pbv �ܷ�ƽ������
	 * @return long
	 */
	public long setProperBalanceValue(ProperBalanceValue pbv) throws RemoteException;
	/**
	 * ��ȡ�ַֺ˶�����
	 * @param bankID
	 * @param date
	 * @return
	 * @throws RemoteException
	 */
	public ReturnValue getBankFirmRightValue(String bankID,java.util.Date date) throws RemoteException;
	/**
	 * ��ȡ�ܷ�ƽ������
	 * @param bankID
	 * @param date
	 * @return
	 * @throws RemoteException
	 */
	public ReturnValue getProperBalanceValue(String bankID,java.util.Date date) throws RemoteException;
//******************************************************��ҵ���ж���****************************************************
	/**
	 * �ֹ�ǩ��ǩ��
	 * @param bankID ���б��
	 * @param type (0: ǩ�� 1: ǩ��)
	 */
	public ReturnValue updownBank(String bankID,int type) throws RemoteException;
	/**
	 * ���ͽ�����������Ϣ
	 * @param bankID ���б��
	 * @param firmIDs �����̱�ż�
	 * @param tradeDate ��������
	 */
	public ReturnValue sendXYQSValue(String bankID,String[] firmIDs,java.util.Date tradeDate) throws RemoteException;
	/**
	 * ��ȡ������������Ϣ
	 * @param bankID ���б��
	 * @param firmIDs �����̱�ż�
	 * @param tradeDate ��������
	 * @return Vector<XYQSValue>
	 */
	public RZQSValue getXYQSValue(String bankID,String[] firmIDs,java.util.Date tradeDate) throws RemoteException;
	/**
	 * ��ȡ�����̶�����Ϣ
	 * @param bankID ���б��
	 * @param firmIDs �����̱�ż�
	 * @param tradeDate ��������
	 * @return Vector<XYDZValue>
	 */
	public RZDZValue getXYDZValue(String bankID,String[] firmIDs,java.util.Date tradeDate) throws RemoteException;
	/**
	 * ��ȡ�ܷ�ƽ����
	 * @param bankID ���б��
	 * @param date ��������
	 * @return ReturnValue
	 */
	public ReturnValue getZFPH(String bankID,java.util.Date date) throws RemoteException;
	/**
	 * ��ȡ�׷׺˶���Ϣ
	 * @param bankID ���б��
	 * @param date ��������
	 * @return ReturnValue
	 */
	public ReturnValue getFFHD(String bankID,java.util.Date date) throws RemoteException;
	/**
	 * �����ܷ�ƽ������Ϣ
	 * @param zfph �ܷ�ƽ����Ϣ
	 * @return ReturnValue
	 */
	public ReturnValue saveZFPH(ZFPHValue zfph) throws RemoteException;
	/**
	 * ����ַֺ˶���Ϣ
	 * @param ffhd �ַֺ˶���Ϣ
	 * @return ReturnValue
	 */
	public ReturnValue saveFFHD(FFHDValue ffhd) throws RemoteException;
	/**
	 * ����г������ʽ�䶯��
	 * @param xymm �г������ʽ���Ϣ
	 * @return ReturnValue
	 */
	public ReturnValue addMarketMoney(XYMarketMoney xymm) throws RemoteException;
	/**
	 * �޸��г������ʽ�䶯��
	 * @param xymm �г������ʽ���Ϣ
	 * @return ReturnValue
	 */
	public ReturnValue modMarketMoney(XYMarketMoney xymm) throws RemoteException;
	
	/**���м��ʽ�ת���֪ͨ*/
	public ReturnValue BankTransferResultNotice(long actionId,int optRst) throws RemoteException;
	public ReturnValue marketTransfer(BankTransferValue bankTransferValue) throws RemoteException;	
	public long bankTransfer(long id,int optFlag) throws RemoteException;	
//******************************************************���������ж���****************************************************
	/**
	 * ���ͽ�����������Ϣ
	 * @param bankID ���б��
	 * @param firmIDs �����̱�ż�
	 * @param tradeDate ��������
	 */
	public ReturnValue sendHRBQSValue(String bankID,String[] firmIDs,java.util.Date tradeDate) throws RemoteException;

//-----------------------------------���ж���  start---------------------------------------------------
	
	public long insertFirmTradeStatus(Vector<FirmTradeStatus> veFirmStatus, int ready)throws RemoteException;
	
	public long insertTradeDetailAccount(Vector<TradeDetailAccount> veDetail, int ready)throws RemoteException;
	
	/**
	 * ���п�������
	 * ��� �����̴����������ʺŶ�Ӧ��ϵ ��ǩԼ״̬
	 */
	public ReturnValue preOpenAccountMarket(CorrespondValue cv) throws RemoteException;
	//-----------------------------------���ж���  end---------------------------------------------------
	//-----------------------------------��������  start------------------------------
	public ReturnValue sendCMBCQSValue(String bankID,Date date) throws RemoteException;
	/**
	 * �ж�����Ϸ���,true ��֤�ɹ�;false ��֤ʧ��
	 * @param firmID �����̴���
	 * @param password ����������
	 * @return long (0 �ɹ�;1 δ��������;-1 ��֤ʧ��;-2 ��ѯ����������)
	 */
	public long isLogPassword(String firmID, String password) throws RemoteException;
	//-----------------------------------��������  end------------------------------
	/**
	 * ���Ͽ�������
	 */
	public ReturnValue marketOpenAccount(CorrespondValue cv) throws RemoteException;
	
	//------------------------------------������ G����ͨ begin------------------------------------
	/**
	 * ��ѯ�г�ǩԼ��ˮ
	 */
	public Vector<RgstCapitalValue> getRgstCapitalValue(String file) throws RemoteException;
	/**
	 * �޸�ǩԼ��ˮ
	 * @param cv
	 * @return
	 */
	public ReturnValue modRgstCapitalValue(RgstCapitalValue rc) throws RemoteException;
	/**
	 * ����ǩԼ��ˮ
	 * @param cv
	 * @return
	 */
	public ReturnValue addRgstCapitalValue(CorrespondValue rc,int type) throws RemoteException;
	/**
	 * �޸��ʽ���ˮ״̬
	 * actionID   ��ˮ�ţ�
	 * funID 	������ˮ��
	 * funFlag    ͨ�����ܾ�
	 * type       �����
	 */
	public long modMoneyCapital(long actionID,String funID,boolean funFlag) throws RemoteException;
	
	/**
	 * ������G����ͨ����������˵���
	 * @param bankID ���д���
	 * @param firmID �����̴���
	 * @param account �����������ʺ�
	 * @param accountPwd �����������ʺ�����
	 * @param money   ���
	 * @param remark   ��ע
	 * @return long ���нӿ�ҵ����ˮ��,����<0��ֵ��ʾ����ʧ��
	 *                             (-10001���Ƿ������ʺ� -10002���Ƿ������̴��� -10003�������̴�����ʺŶ�Ӧ��ϵ���� -10004�����ݿ��쳣 -10005��ϵͳ�쳣 -10006���������� --10008:�������ύʧ�� )
	 */
	public long inMoneyMarketGS(String bankID, String firmID ,
			String account,String accountPwd,double money ,
			String remark) throws RemoteException;
	/**
	 * ����������
	 * @param bankID ���д���
	 * @param money ���
	 * @param firmID �����̴���
	 * @param bankAccount �����ʺ�
	 * @param operator �������
	 * @param express 0������ 1���Ӽ�
	 * @param type 0���г��˳��� 1�����ж˳���
	 * @return long ���нӿ�ҵ����ˮ��,����<0��ֵ��ʾ����ʧ��                          
	 * @throws 
	 */
	public ReturnValue outMoneyGS(String bankID,double money,String firmID ,String bankAccount , String funid,String operator,int express,int type) throws RemoteException;
	/**
	 * ��ѯ�������г������ʽ�������ʺ����
	 * @return  FirmBalanceValue
	 */
	public FirmBalanceValue getBankBalance(String bankid,String firmid,String pwd) throws RemoteException;
	//------------------------------------������ G����ͨ end------------------------------------
	//---------------------------------�������ⶨ�� begin---------------------------------
	/**
	 * ���ж˷����Ԥָ���������ȷ��
	 * 
	 * @return
	 * @throws RemoteException
	 * @author : taog
	 * @Date :2011-12-28����10:12:10
	 */
	public ReturnValue SpecifiedStorageTubeBankSure()throws RemoteException;
	/**
	 * 
	 * ���в���ͨѶ
	 * @param bankID
	 * @return
	 * @throws RemoteException
	 * @author : taog
	 * @Date :2011-12-28����10:08:25
	 */
	public ReturnValue CommunicationsTest(String bankID) throws RemoteException;
	
	/**
	 * ����ͨ��֤����Ϣ����ԤǩԼ
	 * 
	 * @param bankid
	 * @param cardtype
	 * @param card
	 * @param account
	 * @return
	 * @throws RemoteException
	 * @author : taog
	 * @Date :2011-12-28����10:11:14
	 */
	public ReturnValue BankYuSigning(String bankid,String cardtype,String card,String account)throws RemoteException;
	/**
	 * ���ж˷����ȡ������Ϣ
	 * 
	 * @param bankID
	 * @param tradeDate
	 * @return
	 * @throws RemoteException
	 * @author : taog
	 * @Date :2011-12-28����10:12:54
	 */
	public Hashtable<String, List> getZHQSValue(String bankID, Date tradeDate) throws RemoteException;
	/**
	 * ���пͻ��˺�״̬����
	 * 
	 * @param states
	 * @param ready
	 * @return
	 * @throws RemoteException
	 * @author : taog
	 * @Date :2011-12-28����10:12:47
	 */
	public long insertClientStates(Vector<ClientState> states,int  ready) throws RemoteException;
	/**
	 * ����������Ϣ
	 * 
	 * @param bankID
	 * @param firmIDs
	 * @param tradeDate
	 * @return
	 * @throws RemoteException
	 * @author : taog
	 * @Date :2011-12-28����10:12:29
	 */
	public ReturnValue sendZHQS(String bankID,String[] firmIDs,java.util.Date tradeDate) throws RemoteException;
	
	//---------------------------------�������ⶨ�� end--------------------------------
	//--------------ͨѶ��־ start -----------------
	/**
	 * ¼�����нӿں�����ͨѶ��Ϣ
	 * @param log ��־��Ϣ
	 * @return int ��������
	 */
	public int interfaceLog(InterfaceLog log) throws RemoteException;
	//--------------ͨѶ��־ end -----------------
	public Vector<CitysValue> getCitysValue(String filter) throws RemoteException;
}
