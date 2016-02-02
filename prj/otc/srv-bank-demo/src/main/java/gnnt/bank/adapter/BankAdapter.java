package gnnt.bank.adapter;
import gnnt.bank.adapter.util.Tool;
import gnnt.trade.bank.processorrmi.CapitalProcessorRMI;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.InMoneyVO;
import gnnt.trade.bank.vo.MoneyInfoValue;
import gnnt.trade.bank.vo.OutMoneyVO;
import gnnt.trade.bank.vo.ReturnValue;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.Vector;

/**
 * <p>Title: 银行接口适配器</p>
 *
 * <p>Description:实现与银行间的通信功能</p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: gnnt</p>
 *
 * @author zhangzhongli
 * @version 1.0
 */
public abstract class BankAdapter extends Thread implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 适配器对应的处理器
	 */
	public CapitalProcessorRMI PROCESSOR;
	/**
	 * 适配器对应的银行代码
	 */
	public String BANKID;
	/**
     *市场代码 
     */
	public String MARKETCODE;
	/**
	 * 适配器对象
	 */
	public static BankAdapter adpter = null;
    /**
     * 设置市场代码
     */
	public void setMarketCode(String marketCode){
       MARKETCODE = marketCode;
    }

    /**
     * 取市场代码
     */
    public String getMarketCode() {
       return MARKETCODE;
    }
	
	/**
	 * 构造函数
	 */
	public BankAdapter()
	{
	}	
	
	/**
	 * 实例化适配器对象
	 * @return
	 */
	public static BankAdapter getInstance(){
		if(adpter == null){
			try {
				adpter = (BankAdapter) Class.forName("gnnt.bank.adapter.GNNTBankImpl").newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return adpter;
	}
	
	
	public BankAdapter(CapitalProcessorRMI processor,String bankID){
		PROCESSOR = processor;
		BANKID = bankID;
	}
	/**
	 * 重新获取处理器RMI服务对象
	 * @return
	 */
	public void reUpCapitalProcessorRMI(){
		PROCESSOR = getProcessor();
	}
	/**
	 * 重新获取处理器RMI服务对象
	 * @return
	 */
	public static CapitalProcessorRMI getProcessor(){
		CapitalProcessorRMI result = null;
		String processorIP = Tool.getConfig(Tool.ProcessorIP);
		String processorPort = Tool.getConfig(Tool.ProcessorPort);
		String serviceName = Tool.getConfig(Tool.ServiceName);
		try {
			result = (CapitalProcessorRMI) Naming.lookup("//"+processorIP+":"+processorPort+"/"+serviceName);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 入金
	 * @param inMoneyInfo 入金信息
	 * @return ReturnValue
	 */
	public abstract ReturnValue inMoneyQueryBank(InMoneyVO inMoneyVO);
	/**
     * 市场出金
     * @param outMoneyInfo 出金对象
     * @return ReturnValue
     */
	public abstract ReturnValue outMoneyMarketDone(OutMoneyVO outMoneyVO);
	/**
     * 银行账户注册申请
     * @param correspondValue 交易商代码银行账户对应关系对象
     * @return
     */
	public abstract ReturnValue rgstAccountQuery(CorrespondValue correspondValue);
	/**
	 * 银行账户注销申请
	 * @param correspondValue 交易商代码银行账户对应关系对象
	 * @return 
	 */
	public abstract ReturnValue delAccountQuery(CorrespondValue correspondValue);
	/**
     * 查询帐户余额
     * @return 
     * @throws 
     */
	public abstract double accountQuery(CorrespondValue correspondValue,String password);

	/**
     * 返回银行对帐信息
     * @return Vector 每项数据为 MoneyInfoValue
     * @throws 
     */
	public abstract Vector<MoneyInfoValue> getBankMoneyInfo(Date date,Vector v);
	/**
	 * 模拟银行测试方法
	 */
	public abstract String getStr(long second);

}
