package gnnt.trade.bank;
//import gnnt.bank.adapter.rmi.BankAdapterRMI;
import gnnt.MEBS.member.ActiveUser.LogonManager;
import gnnt.bank.adapter.rmi.BankAdapterRMI;
import gnnt.trade.bank.dao.BankDAO;
import gnnt.trade.bank.dao.BankDAOFactory;
import gnnt.trade.bank.util.Arith;
import gnnt.trade.bank.util.Encryption;
import gnnt.trade.bank.util.ErrorCode;
import gnnt.trade.bank.util.Tool;
import gnnt.trade.bank.vo.BankCompareInfoValue;
import gnnt.trade.bank.vo.BankTransferValue;
import gnnt.trade.bank.vo.BankValue;
import gnnt.trade.bank.vo.CapitalCompare;
import gnnt.trade.bank.vo.CapitalValue;
import gnnt.trade.bank.vo.ChargeAgainstValue;
import gnnt.trade.bank.vo.CitysValue;
import gnnt.trade.bank.vo.CompareResult;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.DicValue;
import gnnt.trade.bank.vo.Firm;
import gnnt.trade.bank.vo.FirmBalanceValue;
import gnnt.trade.bank.vo.FirmFundsValue;
import gnnt.trade.bank.vo.FirmMessageVo;
import gnnt.trade.bank.vo.FirmTradeStatus;
import gnnt.trade.bank.vo.FirmValue;
import gnnt.trade.bank.vo.InMoneyVO;
import gnnt.trade.bank.vo.InterfaceLog;
import gnnt.trade.bank.vo.MarketFirmMsg;
import gnnt.trade.bank.vo.MoneyInfoValue;
import gnnt.trade.bank.vo.OutMoneyVO;
import gnnt.trade.bank.vo.QSChangeResult;
import gnnt.trade.bank.vo.QSRresult;
import gnnt.trade.bank.vo.ReturnValue;
import gnnt.trade.bank.vo.RgstCapitalValue;
import gnnt.trade.bank.vo.TradeDetailAccount;
import gnnt.trade.bank.vo.TradeResultValue;
import gnnt.trade.bank.vo.TransMnyObjValue;
import gnnt.trade.bank.vo.TransferInfoValue;
import gnnt.trade.bank.vo.bankdz.BankQSVO;
import gnnt.trade.bank.vo.bankdz.TransferBank;
import gnnt.trade.bank.vo.bankdz.boc.AccountStatusReconciliation;
import gnnt.trade.bank.vo.bankdz.boc.ClientState;
import gnnt.trade.bank.vo.bankdz.boc.StorageMoneyLedgerBalanceList;
import gnnt.trade.bank.vo.bankdz.boc.StorageMoneySettlementList;
import gnnt.trade.bank.vo.bankdz.boc.TransferAccountsTransactionDetailed;
import gnnt.trade.bank.vo.bankdz.gs.sent.BankFirmRightValue;
import gnnt.trade.bank.vo.bankdz.gs.sent.FirmRights;
import gnnt.trade.bank.vo.bankdz.gs.sent.FirmRightsValue;
import gnnt.trade.bank.vo.bankdz.gs.sent.OpenOrDelFirmValue;
import gnnt.trade.bank.vo.bankdz.gs.sent.ProperBalanceValue;
import gnnt.trade.bank.vo.bankdz.gs.sent.TradingDetailsValue;
import gnnt.trade.bank.vo.bankdz.hx.sent.HXSentQSMsgValue;
import gnnt.trade.bank.vo.bankdz.jh.sent.FirmBalance;
import gnnt.trade.bank.vo.bankdz.ms.Accr;
import gnnt.trade.bank.vo.bankdz.ms.Sbala;
import gnnt.trade.bank.vo.bankdz.ms.Sbusi;
import gnnt.trade.bank.vo.bankdz.ms.Spay;
import gnnt.trade.bank.vo.bankdz.pf.resave.TraderResult;
import gnnt.trade.bank.vo.bankdz.pf.sent.FundsMarg;
import gnnt.trade.bank.vo.bankdz.pf.sent.Margins;
import gnnt.trade.bank.vo.bankdz.pf.sent.TradeList;
import gnnt.trade.bank.vo.bankdz.sfz.resave.child.BatCustDzBChild;
import gnnt.trade.bank.vo.bankdz.sfz.resave.child.BatCustDzFailChild;
import gnnt.trade.bank.vo.bankdz.sfz.resave.child.BatFailResultChild;
import gnnt.trade.bank.vo.bankdz.sfz.resave.child.KXHfailChild;
import gnnt.trade.bank.vo.bankdz.sfz.sent.BatQs;
import gnnt.trade.bank.vo.bankdz.sfz.sent.child.BatQsChild;
import gnnt.trade.bank.vo.bankdz.xy.XYMarketMoney;
import gnnt.trade.bank.vo.bankdz.xy.resave.FFHDValue;
import gnnt.trade.bank.vo.bankdz.xy.resave.ZFPHValue;
import gnnt.trade.bank.vo.bankdz.xy.resave.child.FirmDateValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.RZDZValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.RZQSValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.child.FirmDZValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.child.FirmRightValue;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.log4j.Logger;
/**
 * <p>Title: 银行接口处理器</p>
 * <p>Description:处理基本不变的核心业务</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: gnnt</p>
 */
public class CapitalProcessor {
	/********************常量定义***********************/ 
	/**入金类型 0*/
	private static final int INMONEY = 0;
	/**出金类型 1*/
	private static final int OUTMONEY = 1;
	/**出入金手续费类型 2*/
	private static final int RATE = 2;
	/**资金划转 3*/
	private static final int TRANS = 3;
	/**与远期系统有关的数据处理对象*/
	protected static DataProcess dataProcess;
	/**是否已加载配置信息*/
	private static boolean ISLOADED = false;
	/**远程访问适配器对象url队列, key:银行代码,value:remoteUrl*/
	private static Hashtable<String,String> ADAPTERLIST;
	/**bankid与adapterClassname队列, key:adapterClassname,value:银行代码*/
	private Hashtable<String,String> BankIdAndAdapterClassnameList;
	/**数据库访问对象*/
	private static BankDAO DAO;
//	/**适配器对象*/
//	private BankAdapterRMI bankadapter = null;
//	/**银行传送并保存数据状态 true 成功 false 失败*/
//	public  boolean dataSaved = false;
	/**
	 * 构造函数
	 */
	public CapitalProcessor() {
		try {
			DAO = BankDAOFactory.getDAO();//取得数据库访问对象
//			Vector<MoneyInfoValue> mivv = DAO.getMoneyInfoList(" where trunc(compareDate)=trunc(sysdate) and note is not null");
//			if(mivv!=null&&mivv.size()>0){
//				dataSaved = true;
//			}
			if(!ISLOADED) {
				ErrorCode errorCode=new ErrorCode();
				errorCode.load();//加载错误码对应的信息
				dataProcess=new DataProcess();
				if(dataProcess.loadConfig()==0) {//加载配置的科目与摘要信息
					if(loadAdapter()==0) {//加载适配器对象
						this.startServiceThread();//启动处理器对象服务线程
						ISLOADED = true;
					} else {
						log("初始化处理器失败：加载适配器对象错误");
					}
				} else {
					log("初始化处理器失败：加载配置的科目与摘要信息错误");
				}
			}			
		} catch(Exception e) {
			e.printStackTrace();
			log("初始化处理器失败："+e);
		}		
	}
//	/**
//	 * main方法
//	 */
//	public static void main(String[] args) {
//		//实例化处理器对象
//		CapitalProcessor processor = new CapitalProcessor();
//		processor.log("实例化处理器对象");
//		//启动处理器对象服务线程
//		processor.startServiceThread();
//		processor.log("启动处理器服务线程");
//	}
	/**
	 * 向指定日志文件写入日志信息
	 * @param content 日志内容
	 */
	private void log(String content) {
		Logger plog = Logger.getLogger("Processorlog");
		plog.debug(content);		
	}
	/**
	 * 取得配置信息
	 * @param key 配置项
	 * @return String
	 */
	public String getConfig(String key) {
		return Tool.getConfig(key);
	}
	/**
	 * 启动服务线程
	 */
	private void startServiceThread() {
		ServiceThread svrThread = new ServiceThread(this);
		svrThread.start();
	}
	/**
	 * 加载适配器
	 * @param 
	 * @return int 0:加载成功；-1：加载失败
	 */
	private int loadAdapter() {
		int result = 0;
		try {
			Vector<BankValue> bankList = DAO.getBankList("where validFlag = 0");
			log("=========>开始加载适配器远程访问对象url");
			for (int i = 0; i < bankList.size(); i++) 
			{
				BankValue bVal = (BankValue)bankList.get(i);
				String abClassName = bVal.adapterClassname;
				String className = abClassName.substring(abClassName.lastIndexOf("/")+1,abClassName.length());
				System.out.println("URL:"+className);
				addAdapter(bVal.bankID, abClassName);
				addBankIdAndAdapterClassname(className,bVal.bankID);
				System.out.println(bVal.bankID + "适配器远程访问对象url加载成功");
				log(bVal.bankID + "适配器加载成功");
			}
			log("=========>结束加载适配器远程访问对象url");
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
			log("加载适配器远程访问对象url异常："+e);
			result = -1;
		}
		finally
		{
		}		
		return result;
	}

	/**
	 * 将适配器放入内存队列
	 * @param bankID 银行代码
	 * @param adapter 适配器对象
	 * @return void
	 */
	private void addAdapter(String bankID ,String adapterClassname)
	{
		if(ADAPTERLIST == null) ADAPTERLIST = new Hashtable<String,String>();		
		ADAPTERLIST.put(bankID, adapterClassname);
	}

	/**
	 * 将适配器放入内存队列
	 * @param bankID 银行代码
	 * @param adapter 适配器对象
	 * @return void
	 */
	private void addBankIdAndAdapterClassname(String adapterClassname ,String bankID)
	{
		if(BankIdAndAdapterClassnameList == null) {BankIdAndAdapterClassnameList = new Hashtable<String,String>();}		
		BankIdAndAdapterClassnameList.put(adapterClassname, bankID);
	}
	/**
	 * 取得数据库访问对象
	 * @return BankDAO
	 */
	protected BankDAO getBankDAO()
	{
		return DAO;
	}

	/**
	 * 取得资金划转业务类型
	 * @return int
	 */
	protected int getTransType()
	{
		return TRANS;
	}
	public static void main(String args[]){
		BankAdapterRMI bankadapter = null;
		try {
			bankadapter = (BankAdapterRMI) Naming.lookup("//172.16.1.124:31000/CEBBankImpl");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		}
		System.out.println(bankadapter);
	}

	/**
	 * 取得适配器对象
	 * @param bankID 银行代码
	 * @param adapter 适配器对象
	 * @return BankAdapter
	 */
	public BankAdapterRMI getAdapter(String bankID)
	{
		BankAdapterRMI bankadapter = null;
		try {
			System.out.println("bankID："+bankID);
			String remoteUrl = ADAPTERLIST.get(bankID);
			System.out.println("===RMI_url---------->>>>"+remoteUrl);
			bankadapter = (BankAdapterRMI) Naming.lookup(remoteUrl);
			System.out.println(bankadapter == null ? "获取的适配器为空" : "获取的适配器服务不为空");
		} 
		catch (MalformedURLException e) {
			System.out.println("MalformedURLException:"+e.getMessage());
			e.printStackTrace();
		} catch (RemoteException e) {
			System.out.println("RemoteException:"+e.getMessage());
			e.printStackTrace();
		} catch (NotBoundException e) {
			System.out.println("NotBoundException:"+e.getMessage());
			e.printStackTrace();
		} 
		catch(Exception e){
			System.out.println("Exception"+e.getMessage());
			e.printStackTrace();
		}
		System.out.println("返回银行适配器对象信息"+bankadapter);
		return bankadapter;
	}

	/**
	 * 取得市场业务流水号
	 * @return long 市场业务流水号，返回-1表示操作失败
	 */
	public long getMktActionID()
	{
		return DAO.getActionID();
	}
	

	/**
	 * 启动服务查询并绑定银行代码和适配器实现类名称
	 * @return hashtable<adapterClassname,bankid>
	 * 
	 */	
	public Hashtable<String,String> getBankIdAndAdapterClassname()
	{		
		return this.BankIdAndAdapterClassnameList;
	}
	
	
	/**
	 * 取得付款方信息
	 * @param bankID 银行代码
	 * @param firmID 交易商代码
	 * @param account 交易商帐号
	 * @param type 付款类型 0：入金 1：出金
	 * @param conn 数据库连接
	 * @return TransferInfoValue  付款方信息
	 */
	private TransferInfoValue getPayInfo(String bankID, String firmID ,int type, Connection conn)
	{
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("取得付款方信息");
		System.out.println("bankID["+bankID+"]firmID["+firmID+"]type["+type+"]");
		System.out.println("}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}");
		TransferInfoValue result = new TransferInfoValue();
		String bankAccount="";//银行账号
		
		try 
		{
			Vector<CorrespondValue> cList = DAO.getCorrespondList("where bankid='"+bankID+"' and firmid='"+firmID+"'", conn);
			if(cList.size() > 0)
			{
				bankAccount = ((CorrespondValue)cList.get(0)).account;
			}
			
			BankValue bv = DAO.getBank(bankID);
			result.headOffice=bv.bankName;
			if(type == INMONEY)
			{
				//取得付款方信息
				CorrespondValue val = DAO.getCorrespond(bankID, firmID, bankAccount, conn);
				result.account = val.account;
				result.bankName = val.bankName;
				result.city = val.bankCity;
				result.email = val.email;
				result.mobile = val.mobile;
				result.name = val.accountName;
				result.province = val.bankProvince;
				result.account1 = val.account1;
				result.cardType=val.cardType;
				result.card=val.card;
				
			}
			else if(type == OUTMONEY)
			{
				//取得付款方信息
				Vector<DicValue> list = DAO.getDicList("where type=2 and bankid='"+bankID+"'", conn);
				for (int i = 0; i < list.size(); i++) 
				{
					DicValue val = (DicValue)list.get(i);
					if(val.name.equals("Email"))
					{
						result.email = val.value;
					}
					else if(val.name.equals("mobile"))
					{
						result.mobile = val.value;
					}
					else if(val.name.equals("bankCity"))
					{
						result.city = val.value;
					}
					else if(val.name.equals("bankProvince"))
					{
						result.province = val.value;
					}
					else if(val.name.equals("bankName"))
					{
						result.bankName = val.value;
					}
					else if(val.name.equals("accountName"))
					{
						result.name = val.value;
					}
					else if(val.name.equals("marketAccount"))
					{
						result.account = val.value;
					}
				}
			}
		} 
		catch(Exception e) 
		{
			log("取得付款方信息Exception:"+e);
			e.printStackTrace();
		}
		finally
		{
		}
		return result;
	}

	/**
	 * 取得收款方信息
	 * @param bankID 银行代码
	 * @param firmID 交易商代码
	 * @param account 交易商帐号
	 * @param type 付款类型 0：入金 1：出金
	 * @param conn 数据库连接
	 * @return TransferInfoValue  收款方信息
	 */
	private TransferInfoValue getReceiveInfo(String bankID, String firmID, int type, Connection conn)
	{
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("取得收款方信息");
		System.out.println("bankID["+bankID+"]firmID["+firmID+"]type["+type+"]");
		System.out.println("}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}");
		TransferInfoValue result = new TransferInfoValue();
		String bankAccount="";//银行账号
		try 
		{
			Vector<CorrespondValue> cList = DAO.getCorrespondList("where bankid='"+bankID+"' and firmid='"+firmID+"'", conn);
			if(cList.size() > 0)
			{
				bankAccount = ((CorrespondValue)cList.get(0)).account;
			}
			BankValue bv = DAO.getBank(bankID);
			result.headOffice=bv.bankName;
			
			if(type == INMONEY)
			{	
				//取得收款方信息
				Vector<DicValue> list = DAO.getDicList("where type=2 and bankid='"+bankID+"'", conn);
				for (int i = 0; i < list.size(); i++) 
				{
					DicValue val = (DicValue)list.get(i);
					if(val.name.equals("Email"))
					{
						result.email = val.value;
					}
					else if(val.name.equals("mobile"))
					{
						result.mobile = val.value;
					}
					else if(val.name.equals("bankCity"))
					{
						result.city = val.value;
					}
					else if(val.name.equals("bankProvince"))
					{
						result.province = val.value;
					}
					else if(val.name.equals("bankName"))
					{
						result.bankName = val.value;
					}
					else if(val.name.equals("accountName"))
					{
						result.name = val.value;
					}
					else if(val.name.equals("marketAccount"))
					{
						result.account = val.value;
					}
				}	
			}
			else if(type == OUTMONEY)
			{
				//取得收款方信息
				CorrespondValue val = DAO.getCorrespond(bankID, firmID, bankAccount, conn);
				result.account = val.account;
				result.bankName = val.bankName;
				result.city = val.bankCity;
				result.email = val.email;
				result.mobile = val.mobile;
				result.name = val.accountName;
				result.province = val.bankProvince;
				result.account1 = val.account1;	
				result.cardType=val.cardType;
				result.card=val.card;
				result.account1Name = val.accountName1;
				result.accountName = val.accountName;
			}
		} 
		catch(Exception e) 
		{
			log("取得收款方信息Exception:"+e);
			e.printStackTrace();
		}
		finally
		{

		}

		return result;
	}

	/**
	 * 取得交易商已转账金额
	 * @param bankID 银行代码
	 * @param firmID 交易商代码
	 * @param type 转账类型 0：入金 1：出金
	 * @param conn 数据库连接
	 * @return double  交易商总转账金额
	 * @throws SQLException
	 */
	@SuppressWarnings("unused")
	private double getTotalTransMoney(String bankID, String firmID, int type, Connection conn) throws SQLException
	{
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("取得交易商已转账金额");
		System.out.println("bankID["+bankID+"]firmID["+firmID+"]type["+type+"]");
		System.out.println("}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}}}}}");
		double result = 0; 
		Vector<CapitalValue> capitalList = DAO.getCapitalInfoList("where status=0 and bankID='"+bankID+"' and firmid='"+firmID+"' and type="+type, conn);
		for (int i = 0; i < capitalList.size(); i++) 
		{
			CapitalValue cVal = (CapitalValue)capitalList.get(i);
			result = Arith.add(result, cVal.money);
		}
		return result;
	}
	
	/**
	 * 取得某日交易商已转账金额
	 * @param bankID 银行代码
	 * @param firmID 交易商代码
	 * @param type 转账类型 0：入金 1：出金
	 * @param time 统计时间
	 * @param conn 数据库连接
	 * @return double  <0 
	 * @throws SQLException
	 */
	private double getDayTransMoney(String bankID, String firmID, int type, Timestamp time, Connection conn) throws SQLException
	{
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("取得某日交易商已转账金额");
		System.out.println("bankID["+bankID+"]firmID["+firmID+"]type["+type+"]time["+time+"]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}}}}}");
		double result = 0; 

		Vector<CapitalValue> capitalList = DAO.getCapitalInfoList("where status=0 and to_char(createtime,'yyyy-mm-dd')='"+Tool.convertTS(time)+"'" +
				" and bankID='"+bankID+"' and firmid='"+firmID+"' and type="+type, conn);
		for (int i = 0; i < capitalList.size(); i++) 
		{
			CapitalValue cVal = (CapitalValue)capitalList.get(i);
			result = Arith.add(result, cVal.money);
		}

		return result;
	}

	/**
	 * 取得某日交易商已转账次数
	 * @param bankID 银行代码
	 * @param firmID 交易商代码
	 * @param type 转账类型 0：入金 1：出金
	 * @param time 统计时间
	 * @param conn 数据库连接
	 * @return int  当日已转账次数 
	 * @throws SQLException
	 */
	private int getDayTransCount(String bankID, String firmID, int type, Timestamp time, Connection conn) throws SQLException
	{
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("取得某日交易商已转账次数");
		System.out.println("bankID["+bankID+"]firmID["+firmID+"]type["+type+"]time["+time+"]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}}}}");
		Vector<CapitalValue> capitalList = DAO.getCapitalInfoList("where (status!=1 and status !=-1) and to_char(createtime,'yyyy-mm-dd')='"+Tool.convertTS(time)+"'" +
				" and bankID='"+bankID+"' and firmid='"+firmID+"' and type="+type, conn);

		return capitalList.size();
	}

	/**
	 * 取得出入金手续费
	 * @param bankID 银行代码
	 * @param firmID 交易商代码
	 * @param money 转账金额
	 * @param type 转账类型
	 * @param express 0：正常 1：加急
	 * @param account 银行账号 用来判断账号类型（同城 异地）
	 * @param conn 数据库连接
	 * @return double 手续费金额>=0有效；-1 数据库异常 -2 系统异常
	 * @throws 
	 */
	private double getTransRate(String bankID, String firmID, double money, int type, int express, String account, Connection conn)
	{
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("bankID["+bankID+"]firmID["+firmID+"]money["+money+"]type["+type+"]express["+express+"]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}}}}}");
		FeeProcess feeProcess=new FeeProcess();
		if(account==null)
		{
			try {
				Vector<CorrespondValue> v = DAO.getCorrespondList(" where bankid='"+bankID+"' and firmid='"+firmID+"'");
				if(v!=null&&v.size()>0)
				{
					account = ((CorrespondValue)v.get(0)).account;	
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//应收手续费
		double result = 0;
		if(type == INMONEY)
		{
			result=feeProcess.getInRate(bankID, firmID, money, express, account, conn);
		}
		else if(type == OUTMONEY)
		{
			result = feeProcess.getOutRate(bankID, firmID, money, express, account, conn);
		}
		return result;
	}
	
	

	/**
	 * 判断是否符合出入金条件
	 * @param bankID 银行代码
	 * @param firmID 交易商代码
	 * @param money 转账金额
	 * @param time 转账时间
	 * @param type 转账类型 0：入金 1：出金
	 * @param conn 数据库连接
	 * @return long 0：符合条件；否则不符合转帐条件 
	 * (-30000:超出每日单比最大转账金额 -30001：超出最大转账金额 -30002：超出当日最大转账金额 -30003：超出当日最大转账次数 -30004：数据库异常 -30005：系统异常)
	 * @throws SQLException
	 */
	private long checkTrans(String bankID, String firmID, double money, Timestamp time, int type, Connection conn)
	{
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("bankID["+bankID+"]firmID["+firmID+"]money["+money+"]time["+time+"]type["+type+"]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}}}}");
		long result = 0;

		try 
		{			
			BankValue bVal = DAO.getBank(bankID, conn);
			FirmValue fVal = DAO.getFirm(firmID, conn);

			//单笔最大转账金额
			double maxPerSglTransMoney = bVal.maxPerSglTransMoney;
			
			//最大转账金额
			//double maxTransMoney = bVal.maxTransMoney;

			//每日最大转账金额
			double maxPerTransMoney = bVal.maxPerTransMoney;

			//每日最大转账次数
			int maxPerTransCount = bVal.maxPerTransCount;

			//if(fVal.maxTransMoney > 0) maxTransMoney = fVal.maxTransMoney;
			if(fVal.maxPerTransMoney > 0) maxPerTransMoney = fVal.maxPerTransMoney;
			if(fVal.maxPerTransCount > 0) maxPerTransCount = fVal.maxPerTransCount;
			if(fVal.maxPerSglTransMoney > 0) maxPerSglTransMoney = fVal.maxPerSglTransMoney;

			if(Arith.compareTo(money, maxPerSglTransMoney) == 1)
			{
				//超出单笔最大转账金额限制
				result = ErrorCode.checkTrans_ErrorExceedDayMaxPerMoney;
			}
			else if(Arith.compareTo(Arith.add(money, getDayTransMoney(bankID, firmID, type, time, conn)), maxPerTransMoney) == 1)
			{
				//超出当日最大转账金额限制
				result = ErrorCode.checkTrans_ErrorExceedDayMaxMoney;
			}
			else if(Arith.compareTo(Arith.add(1, getDayTransCount(bankID, firmID, type, time, conn)), maxPerTransCount) == 1)
			{
				//超出当日最大转账次数限制
				result =ErrorCode.checkTrans_ErrorExceedDayMaxNum;
			}
		} 
		catch(SQLException e) 
		{
			e.printStackTrace();
			log("判断是否符合出入金条件SQLException:"+e);
			result = ErrorCode.checkTrans_DataBaseException;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log("判断是否符合出入金条件Exception:"+e);
			result = ErrorCode.checkTrans_SysException;
		}
		finally
		{
		}
		return result;
	}
	/**
	 * 入金，由adapter调用
	 * @param bankID 银行代码
	 * @param money 金额
	 * @param firmID 交易商代码
	 * @param account 交易商银行帐号
	 * @param bankTime 银行端入金时间
	 * @param funID 银行端业务流水号
	 * @param actionID 转账模块业务流水号
	 * @param funFlag 银行端操作成功或失败的标志 0：成功 1：失败，actionID>0时有效 5：处理中
	 * @param remark 备注
	 * @return long 银行接口业务流水号,返回<0的值表示操作失败
	 *                             (-10001：非法银行帐号 -10002：非法交易商代码 -10003：交易商代码和帐号对应关系错误 -10004：数据库异常 -10005：系统异常 -10006：参数错误
	 */
	public long inMoney
	(String bankID, String firmID ,String account ,Timestamp bankTime ,double money ,String funID, long actionID, int funFlag, String remark)
	{
		System.out.println("{{{{{{{{{{{{{{{{{{{{入金，由adapter调用{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("bankID["+bankID+"]firmID["+firmID+"]account["+account+"]bankTime["+bankTime+"]money["+money+"]funID["+funID+"]actionID["+actionID+"]funFlag["+funFlag+"]remark["+remark+"]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}}");
		//市场业务流水号
		long result = 0;
		result = tradeDate(bankID);
		if(result!=0){
			return result;
		}

		//资金划转流水号
		long capitalID = 0;

		//手续费划转流水号
		long rateID = 0;

		//数据库连接
		Connection conn = null;

		//当前系统时间
		Timestamp curTime = new Timestamp(System.currentTimeMillis());

		//入金手续费
		double inRate = 0;
		
		if(bankTime==null) bankTime=curTime;
		log("firmID="+firmID+"   bankID="+bankID+"   account="+account);
		System.out.println("firmID="+firmID+"   bankID="+bankID+"   account="+account);
		boolean isopen = (bankAccountIsOpen(firmID,bankID,account)==1);
		System.out.println("isopen="+isopen);
		log("isopen="+isopen);
		try 
		{
			if(isopen)//签约成功状态
			{
				conn = DAO.getConnection();
				System.out.println("actionid="+actionID);
				/**根据传入的actionID查询是否存在这笔流水，有则为市场入金，无则为银行入金*/
				Vector<CapitalValue> v = DAO.getCapitalInfoList(" where actionid="+actionID+" or (funid='"+funID+"' and trunc(createtime)=to_date('"+Tool.fmtDate(bankTime)+"','yyyy-MM-dd'))");
				System.out.println("v.lenght()="+v.size());
					//取得入金手续费
					inRate = getTransRate(bankID, firmID, money, INMONEY,0,account,conn);
				/**********************接口参数合法性检查***************************/
				if((firmID == null || firmID.trim().equals("")) && (account == null || account.trim().equals("")))
				{
					//参数非法
					result=ErrorCode.inMoney_ErrorParameter;
					log("入金回调，参数非法，错误码="+result+"错误信息："+ErrorCode.error.get(result));
				}
				else if(firmID == null || firmID.trim().equals(""))
				{
					Vector<CorrespondValue> cList = DAO.getCorrespondList("where bankid='"+bankID+"' and account='"+account+"'", conn);
					if(cList.size() > 0)
					{
						firmID = ((CorrespondValue)cList.get(0)).firmID;
					}
					else
					{
						//非法银行帐号
						result = ErrorCode.inMoney_ErrorBankAcount;
						log("入金回调，非法银行帐号，错误码="+result+"错误信息："+ErrorCode.error.get(result));
					}
				}
				else if(account == null || account.trim().equals(""))
				{
					Vector<CorrespondValue> cList = DAO.getCorrespondList("where bankid='"+bankID+"' and firmid='"+firmID+"'", conn);
					if(cList.size() > 0)
					{
						account = ((CorrespondValue)cList.get(0)).account;
					}
					else
					{
						//非法交易商代码
						result = ErrorCode.inMoney_ErrorFirmCode;
						log("入金回调，非法交易商代码，错误码="+result+"错误信息："+ErrorCode.error.get(result));
					}
				}
				else if(DAO.getCorrespond(bankID, firmID, account, conn) == null)
				{
					//交易商代码和帐号对应关系错误
					result = ErrorCode.inMoney_ErrorCorrespond;
					log("入金回调，交易商代码和帐号对应关系错误，错误码="+result+"错误信息："+ErrorCode.error.get(result));
				}
				if(result == 0){
					Vector<CorrespondValue> cList = DAO.getCorrespondList("where bankid='"+bankID+"' and firmid='"+firmID+"' and status=0 ", conn);
					if(cList == null || cList.size() != 1){
						result = ErrorCode.inMoney_ErrorCorrespond;
					}
					if(result==0 && (inRate==-1 || inRate==-2))
					{	
						result=ErrorCode.inMoneyM_ErrorGetRate;
						log("入金回调，计算手续费错误，错误码="+result);
					}
				}
				/*********************检查交易商是否符合入金条件,符合条件进行入金处理**************************/
				if(result == 0)
				{
					long checkTrans = 0;
					if(getConfig("inMoneyAudit") != null && getConfig("inMoneyAudit").trim().equals("true")){
						checkTrans = checkTrans(bankID, firmID, money, curTime, INMONEY, conn);
					}
					if(checkTrans == 0)
					{
						try
						{
							if(v==null||v.size()<=0)
							{
								//取得市场业务流水号
								result = DAO.getActionID(conn);
	
								//启动数据库事务
								conn.setAutoCommit(false);
	
								//记录转帐模块资金流水
								CapitalValue cVal = new CapitalValue();
								cVal.actionID = result;
								cVal.bankID = bankID;
								cVal.bankTime = bankTime;
								cVal.creditID = firmID;
								cVal.debitID = dataProcess.getBANKSUB().get(bankID);
								cVal.firmID = firmID;
								cVal.funID = funID;
								cVal.money = money;
								cVal.note = ("BFMem".equals(remark)?"bank_in":remark);
								cVal.oprcode = dataProcess.getINSUMMARY()+"";
								cVal.status = 2;
								cVal.type = INMONEY;
								capitalID = DAO.addCapitalInfo(cVal, conn);//入金流水		
								cVal.creditID = "Market";
								cVal.debitID = firmID;
								cVal.money = inRate;
								cVal.oprcode = dataProcess.getFEESUMMARY()+"";
								cVal.type = RATE;
	
								rateID = DAO.addCapitalInfo(cVal, conn);//手续费流水
	
								conn.commit();
							}
							else
							{
								result = actionID;
								System.out.println("result="+result);
								Vector<CapitalValue> list = DAO.getCapitalInfoList("where actionid="+actionID, conn);
								if(list.size()>0) 
								{
									for(int i=0;i<list.size();i++){
										CapitalValue val = (CapitalValue)list.get(i);
										if(val.type == INMONEY)
										{
											capitalID = val.iD;
//											if(val.status!=2 && val.status!=3 && val.status!=4 ){
//												return ErrorCode.Audit_Arraidy;
//											}
										}
										else if(val.type == RATE)
										{
											rateID = val.iD;
										}
									}
								}
								System.out.println("capitalID="+capitalID);
								System.out.println("rateID="+rateID);
							}
	
						} 
						catch(SQLException e) 
						{
							e.printStackTrace();
							conn.rollback();
							result=ErrorCode.inMoney_ErrorAddCapital;
							log("入金回调方法-记录资金流水SQLException,数据回滚:"+e);
							throw e;
						}
						finally
						{
							conn.setAutoCommit(true);
						}
					}
					else
					{
						result = checkTrans;
					}
				}
				if(result>0)
				{
					try 
					{
						v = DAO.getCapitalInfoList(" where actionid="+result+" and (status=2 or status=3) ");
						//启动数据库事务
						conn.setAutoCommit(false);
						if(v.size()>0)//if(actionID > 0)
						{
							Vector<CapitalValue> ll = DAO.getCapitalInfoList(" where actionid="+result+" and (status=2 or status=3) for update ",conn);
							if(ll == null || ll.size()<=0){
								result = ErrorCode.outMoneyAudit_OradyAudite;
								log("信息已在处理中");
								return result;
							}
							if(funFlag == 0)
							{
								//调用交易系统接口
								dataProcess.updateFundsFull(firmID, dataProcess.getINSUMMARY()+"", dataProcess.getBANKSUB().get(bankID), money,result, conn);//添加入金
								dataProcess.updateFundsFull(firmID, dataProcess.getFEESUMMARY()+"", null, inRate,result, conn);//扣除手续费

								DAO.modCapitalInfoStatus(capitalID,funID, 0,bankTime, conn);
								DAO.modCapitalInfoStatus(rateID,funID, 0, bankTime,conn);
							}
							else if(funFlag == 5)
							{
								DAO.modCapitalInfoStatus(capitalID,funID, 2,bankTime, conn);
								DAO.modCapitalInfoStatus(rateID,funID, 2,bankTime, conn);
							}
							else if(funFlag == ErrorCode.bankNull)
							{
								DAO.modCapitalInfoStatus(capitalID,funID, 5,bankTime, conn);
								DAO.modCapitalInfoStatus(rateID,funID, 5,bankTime, conn);
							}
							else if(funFlag == ErrorCode.ActionID_Error)
							{
								DAO.modCapitalInfoStatus(capitalID,funID, 6,bankTime, conn);
								DAO.modCapitalInfoStatus(rateID,funID, 6,bankTime, conn);
							}
							else
							{
								DAO.modCapitalInfoStatus(capitalID,funID, 1,bankTime, conn);
								DAO.modCapitalInfoStatus(rateID,funID, 1,bankTime, conn);
							}
						}
						conn.commit();
					} 
					catch(SQLException e) 
					{
						result =ErrorCode.inMoney_DataBaseException;
						e.printStackTrace();
						conn.rollback();
						log("银行操作结果："+funFlag+"（0：成功 1：失败）。入金回调方法-调用交易系统接口SQLException,数据回滚:"+e);
					}
					finally
					{
						conn.setAutoCommit(true);
					}

					//调用交易系统接口失败，将资金流水置为处理失败状态
					if(result == ErrorCode.inMoney_DataBaseException)
					{
						DAO.modCapitalInfoStatus(capitalID,funID, 1,bankTime, conn);
						DAO.modCapitalInfoStatus(rateID,funID, 1,bankTime, conn);
						if(v.size()>0)//if(actionID > 0)
						{
							if(funFlag==0)
							{
								DAO.modCapitalInfoNote(capitalID, "银行入金成功，市场处理失败", conn);
								DAO.modCapitalInfoNote(rateID, "银行入金成功，市场处理失败", conn);
							}
						}
						else
						{
							DAO.modCapitalInfoNote(capitalID, "银行发起入金，市场处理失败", conn);
							DAO.modCapitalInfoNote(rateID, "银行发起入金，市场处理失败", conn);
						}
					}
				}
	
				/********************************************************************/
			}
			else
			{
				result = ErrorCode.inMoneyM_FirmNO;
				log("市场发起入金，交易商未签约。");
			}
		} 
		catch(SQLException e) 
		{
			e.printStackTrace();
			result = ErrorCode.inMoney_DataBaseException;
			log("入金回调方法SQLException,异常值="+result+"  "+e);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			result = ErrorCode.inMoney_SysException;
			log("入金回调方法Exception,异常值="+result+"  "+e);
		}
		finally
		{
			DAO.closeStatement(null, null, conn);
		}

		return result;
	}



	/**
	 * 入金，由市场端调用
	 * @param bankID 银行代码
	 * @param firmID 交易商代码
	 * @param account 交易商银行帐号
	 * @param accountPwd 交易商银行帐号密码
	 * @param money   金额
	 * @param remark   备注
	 * @return long 银行接口业务流水号,返回<0的值表示操作失败
	 *                             (-10001：非法银行帐号 -10002：非法交易商代码 -10003：交易商代码和帐号对应关系错误 -10004：数据库异常 -10005：系统异常 -10006：参数错误 --10008:适配器提交失败 )
	 */
	public long inMoneyMarket(String bankID, String firmID ,String account,String accountPwd,double money ,String remark)
	{
		System.out.println("{{{{{{{{{{{{{{{{{{{{入金，由市场端调用{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("bankID["+bankID+"]firmID["+firmID+"]account["+account+"]accountPwd["+accountPwd+"]money["+money+"]remark["+remark+"]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}}}}}}");
		//返回码
		long result = 0;
		result = tradeDate(bankID);
		if(result!=0){
			return result;
		}

		//资金划转流水号
		long capitalID = 0;

		//手续费划转流水号
		long rateID = 0;

		//付款方信息
		TransferInfoValue payInfo = null;

		//收款方方信息
		TransferInfoValue receiveInfo=null;

		//数据库连接
		Connection conn = null;

		//当前系统时间
		Timestamp curTime = new Timestamp(System.currentTimeMillis());

		//入金手续费
		double inRate = 0;
		
		//判断交易商是否为可用状态
		boolean isopen = (bankAccountIsOpen(firmID,bankID,account)==1);
		if(!isopen){
			result = ErrorCode.inMoneyM_FirmNO;
			log("市场发起入金，交易商未签约。");
			return result;
		}
		try 
		{
			conn = DAO.getConnection();

			//取得入金手续费
			inRate = getTransRate(bankID, firmID, money, INMONEY,0,account,conn);
			
			/**********************接口参数合法性检查***************************/
			if((firmID == null || firmID.trim().equals("")) && (account == null || account.trim().equals("")))
			{
				//参数非法
				result = ErrorCode.inMoneyM_ErrorParameter;
				log("市场发起入金，参数非法，错误码="+result);
			}
			else if(firmID == null || firmID.trim().equals(""))
			{
				Vector<CorrespondValue> cList = DAO.getCorrespondList("where bankid='"+bankID+"' and account='"+account+"'", conn);
				if(cList.size() > 0)
				{
					firmID = ((CorrespondValue)cList.get(0)).firmID;
				}
				else
				{
					//非法银行帐号
					result = ErrorCode.inMoneyM_ErrorBankAcount;
					log("市场发起入金，非法银行帐号，错误码="+result);
				}
			}
			else if(account == null || account.trim().equals(""))
			{
				Vector<CorrespondValue> cList = DAO.getCorrespondList("where bankid='"+bankID+"' and firmid='"+firmID+"'", conn);
				if(cList.size() > 0)
				{
					account = ((CorrespondValue)cList.get(0)).account;
				}
				else
				{
					//非法交易商代码
					result = ErrorCode.inMoneyM_ErrorFirmCode;	
					log("市场发起入金，非法交易商代码，错误码="+result);
				}
			}
			else if(DAO.getCorrespond(bankID, firmID, account, conn) == null)
			{
				//交易商代码和帐号对应关系错误
				result = ErrorCode.inMoneyM_ErrorCorrespond;
				log("市场发起入金，交易商代码和帐号对应关系错误，错误码="+result);
			}
			Vector<CorrespondValue> cList = DAO.getCorrespondList("where bankid='"+bankID+"' and firmid='"+firmID+"' and status=0 ", conn);
			if(cList == null || cList.size() != 1){
				result = ErrorCode.inMoneyM_ErrorCorrespond;
			}
			if(result==0 && (inRate==-1 || inRate==-2))
			{	
				result=ErrorCode.inMoneyM_ErrorGetRate;
				log("市场发起入金，计算手续费错误，错误码="+result);
			}
					
			/********************************************************************/

			/*********************检查交易商是否符合入金条件,符合条件进行入金处理**************************/
			if(result == 0)
			{
				long checkTrans=0;
				if(getConfig("inMoneyAudit") != null && getConfig("inMoneyAudit").trim().equals("true")){
					checkTrans=checkTrans(bankID, firmID, money, curTime, INMONEY, conn);
				}
				if(checkTrans== 0)
				{
					//取得市场业务流水号
					result = DAO.getActionID(conn);
					try 
					{
						//启动数据库事务
						conn.setAutoCommit(false);

						//记录转帐模块资金流水
						CapitalValue cVal = new CapitalValue();
						cVal.actionID = result;
						cVal.bankID = bankID;
						cVal.creditID = firmID;
						cVal.debitID = dataProcess.getBANKSUB().get(bankID);
						cVal.firmID = firmID;
						cVal.money = money;
						cVal.note = remark;
						cVal.oprcode = dataProcess.getINSUMMARY()+"";
						cVal.status = 2;
						cVal.type = INMONEY;

						capitalID = DAO.addCapitalInfo(cVal, conn);//入金流水

						cVal.creditID = "Market";
						cVal.debitID = firmID;
						cVal.money = inRate;
						cVal.oprcode = dataProcess.getFEESUMMARY()+"";
						cVal.type = RATE;

						rateID = DAO.addCapitalInfo(cVal, conn);//手续费流水

						conn.commit();
					} 
					catch(SQLException e) 
					{
						e.printStackTrace();
						conn.rollback();
						result=ErrorCode.inMoneyM_ErrorAddCapital;
						log("市场端发起入金写资金流水SQLException,数据回滚:"+e);
						throw e;
					}
					finally
					{
						conn.setAutoCommit(true);
					}
				}
				else
				{
					result = checkTrans;
				}
			}
			
			if(result>0)
			{
				//取得入金付款方信息
				payInfo = getPayInfo(bankID, firmID,INMONEY, conn);
				//取得入金收款方消息
				receiveInfo= getReceiveInfo(bankID, firmID,INMONEY, conn);
				
				BankValue bv = DAO.getBank(bankID);
				InMoneyVO inMoneyInfo=new InMoneyVO(bankID,bv.bankName, money, firmID, payInfo, accountPwd,receiveInfo,remark, result);
				//取得适配器对象
				BankAdapterRMI bankadapter = getAdapter(bankID);
				ReturnValue rVal = null;
				System.out.println(bankadapter);
				if(bankadapter == null){
					rVal = new ReturnValue();
					rVal.result = ErrorCode.GetAdapter_Error;
					log("处理器连接适配器["+bankID+"]失败");
				}else{
					//调用适配器入金接口,适配器入金接口回调inmoney方法.
					rVal = bankadapter.inMoneyQueryBank(inMoneyInfo);
				}
				try 
				{
					//启动数据库事务
					conn.setAutoCommit(false);
					if(rVal.result == 0)
					{
						log("适配器处理成功，业务流水处理成功,入金成功。");
					}
					else if(rVal.result == 5)
					{
						result = rVal.result;
						DAO.modCapitalInfoStatus(capitalID,rVal.funID, 2,curTime, conn);
						DAO.modCapitalInfoStatus(rateID,rVal.funID, 2,curTime, conn);
						log("适配器处理成功，业务流水处理中。");
					}
					else if( rVal.result==ErrorCode.bankNull )
					{
						result = rVal.result;
						DAO.modCapitalInfoStatus(capitalID,rVal.funID, 5,curTime, conn);
						DAO.modCapitalInfoStatus(rateID,rVal.funID, 5,curTime, conn);
						log("适配器连接银行，返回银行无应答");
					}
					else if( rVal.result==ErrorCode.ActionID_Error )
					{
						result = rVal.result;
						DAO.modCapitalInfoStatus(capitalID,rVal.funID, 6,curTime, conn);
						DAO.modCapitalInfoStatus(rateID,rVal.funID, 6,curTime, conn);
						log("适配器连接银行，返回银行返回市场代码为空");
					}
					else//适配器提交失败
					{
						result = rVal.result;
						DAO.modCapitalInfoStatus(capitalID,rVal.funID, 1,curTime, conn);
						DAO.modCapitalInfoStatus(rateID,rVal.funID, 1,curTime, conn);
						result=rVal.result;
						log("适配器提交失败:"+result);
					}
					conn.commit();
				} 
				catch(SQLException e) 
				{
					result =ErrorCode.inMoneyM_DataBaseException;
					e.printStackTrace();
					conn.rollback();
					log("市场端发起入金,更新资金流失状态为失败SQLException,数据回滚:"+result);
				}
				catch(Exception e)
				{
					result =ErrorCode.inMoneyM_SysException;
					e.printStackTrace();
					conn.rollback();
					log("市场端发起入金,系统异常Exception,数据回滚:"+result);
				}
				finally
				{
					conn.setAutoCommit(true);
				}

				//调用交易系统接口失败，将资金流水置为处理失败状态
				if(result == ErrorCode.inMoneyM_DataBaseException)
				{
					DAO.modCapitalInfoStatus(capitalID,rVal.funID, 1,curTime, conn);
					DAO.modCapitalInfoStatus(rateID,rVal.funID, 1,curTime, conn);
				}
			}
			/********************************************************************/
		} 
		catch(SQLException e) 
		{
			e.printStackTrace();
			result = ErrorCode.inMoneyM_DataBaseException;
			log("市场端发起入金SQLException:"+result);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			result = ErrorCode.inMoneyM_SysException;
			log("市场端发起入金Exception:"+result);
		}
		finally
		{
			DAO.closeStatement(null, null, conn);
		}

		return result;
	}

	/**
	 * 入金，由市场端调用
	 * @param bankID 银行代码
	 * @param firmID 交易商代码
	 * @param account 交易商银行帐号
	 * @param accountPwd 交易商银行帐号密码
	 * @param money   金额
	 * @param remark   备注
	 * @return long 银行接口业务流水号,返回<0的值表示操作失败
	 *                             (-10001：非法银行帐号 -10002：非法交易商代码 -10003：交易商代码和帐号对应关系错误 -10004：数据库异常 -10005：系统异常 -10006：参数错误 --10008:适配器提交失败 )
	 */
	public long inMoneyMarket(String bankID, String firmID ,String account,String accountPwd,double money ,String remark, String inOutStart, String PersonName,
			String AmoutDate, String BankName, String OutAccount)
	{
		System.out.println("{{{{{{{{{{{{{{{{{{{{入金，由市场端调用{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("bankID["+bankID+"]firmID["+firmID+"]account["+account+"]accountPwd["+accountPwd+"]money["+money+"]remark["+remark+"]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}}}}}}");
		//返回码
		long result = 0;
		result = tradeDate(bankID);
		if(result!=0){
			return result;
		}

		//资金划转流水号
		long capitalID = 0;

		//手续费划转流水号
		long rateID = 0;

		//付款方信息
		TransferInfoValue payInfo = null;

		//收款方方信息
		TransferInfoValue receiveInfo=null;

		//数据库连接
		Connection conn = null;

		//当前系统时间
		Timestamp curTime = new Timestamp(System.currentTimeMillis());

		//入金手续费
		double inRate = 0;
		
		//判断交易商是否为可用状态
		boolean isopen = (bankAccountIsOpen(firmID,bankID,account)==1);
		if(!isopen){
			result = ErrorCode.inMoneyM_FirmNO;
			log("市场发起入金，交易商未签约。");
			return result;
		}
		try 
		{
			conn = DAO.getConnection();

			//取得入金手续费
			inRate = getTransRate(bankID, firmID, money, INMONEY,0,account,conn);
			
			/**********************接口参数合法性检查***************************/
			if((firmID == null || firmID.trim().equals("")) && (account == null || account.trim().equals("")))
			{
				//参数非法
				result = ErrorCode.inMoneyM_ErrorParameter;
				log("市场发起入金，参数非法，错误码="+result);
			}
			else if(firmID == null || firmID.trim().equals(""))
			{
				Vector<CorrespondValue> cList = DAO.getCorrespondList("where bankid='"+bankID+"' and account='"+account+"'", conn);
				if(cList.size() > 0)
				{
					firmID = ((CorrespondValue)cList.get(0)).firmID;
				}
				else
				{
					//非法银行帐号
					result = ErrorCode.inMoneyM_ErrorBankAcount;
					log("市场发起入金，非法银行帐号，错误码="+result);
				}
			}
			else if(account == null || account.trim().equals(""))
			{
				Vector<CorrespondValue> cList = DAO.getCorrespondList("where bankid='"+bankID+"' and firmid='"+firmID+"'", conn);
				if(cList.size() > 0)
				{
					account = ((CorrespondValue)cList.get(0)).account;
				}
				else
				{
					//非法交易商代码
					result = ErrorCode.inMoneyM_ErrorFirmCode;	
					log("市场发起入金，非法交易商代码，错误码="+result);
				}
			}
			else if(DAO.getCorrespond(bankID, firmID, account, conn) == null)
			{
				//交易商代码和帐号对应关系错误
				result = ErrorCode.inMoneyM_ErrorCorrespond;
				log("市场发起入金，交易商代码和帐号对应关系错误，错误码="+result);
			}
			Vector<CorrespondValue> cList = DAO.getCorrespondList("where bankid='"+bankID+"' and firmid='"+firmID+"' and status=0 ", conn);
			if(cList == null || cList.size() != 1){
				result = ErrorCode.inMoneyM_ErrorCorrespond;
			}
			if(result==0 && (inRate==-1 || inRate==-2))
			{	
				result=ErrorCode.inMoneyM_ErrorGetRate;
				log("市场发起入金，计算手续费错误，错误码="+result);
			}
					
			/********************************************************************/

			/*********************检查交易商是否符合入金条件,符合条件进行入金处理**************************/
			if(result == 0)
			{
				long checkTrans=0;
				if(getConfig("inMoneyAudit") != null && getConfig("inMoneyAudit").trim().equals("true")){
					checkTrans=checkTrans(bankID, firmID, money, curTime, INMONEY, conn);
				}
				if(checkTrans== 0)
				{
					//取得市场业务流水号
					result = DAO.getActionID(conn);
					try 
					{
						//启动数据库事务
						conn.setAutoCommit(false);

						//记录转帐模块资金流水
						CapitalValue cVal = new CapitalValue();
						cVal.actionID = result;
						cVal.bankID = bankID;
						cVal.creditID = firmID;
						cVal.debitID = dataProcess.getBANKSUB().get(bankID);
						cVal.firmID = firmID;
						cVal.money = money;
						cVal.note = remark;
						cVal.oprcode = dataProcess.getINSUMMARY()+"";
						cVal.status = 2;
						cVal.type = INMONEY;

						capitalID = DAO.addCapitalInfo(cVal, conn);//入金流水

						cVal.creditID = "Market";
						cVal.debitID = firmID;
						cVal.money = inRate;
						cVal.oprcode = dataProcess.getFEESUMMARY()+"";
						cVal.type = RATE;

						rateID = DAO.addCapitalInfo(cVal, conn);//手续费流水

						conn.commit();
					} 
					catch(SQLException e) 
					{
						e.printStackTrace();
						conn.rollback();
						result=ErrorCode.inMoneyM_ErrorAddCapital;
						log("市场端发起入金写资金流水SQLException,数据回滚:"+e);
						throw e;
					}
					finally
					{
						conn.setAutoCommit(true);
					}
				}
				else
				{
					result = checkTrans;
				}
			}
			
			if(result>0)
			{
				//取得入金付款方信息
				payInfo = getPayInfo(bankID, firmID,INMONEY, conn);
				//取得入金收款方消息
				receiveInfo= getReceiveInfo(bankID, firmID,INMONEY, conn);
				
				BankValue bv = DAO.getBank(bankID);
				InMoneyVO inMoneyInfo=new InMoneyVO(bankID,bv.bankName, money, 
						firmID, payInfo, accountPwd,receiveInfo,remark,
						result, inOutStart, PersonName, AmoutDate, BankName,
						OutAccount);
				//取得适配器对象
				BankAdapterRMI bankadapter = getAdapter(bankID);
				ReturnValue rVal = null;
				System.out.println(bankadapter);
				if(bankadapter == null){
					rVal = new ReturnValue();
					rVal.result = ErrorCode.GetAdapter_Error;
					log("处理器连接适配器["+bankID+"]失败");
				}else{
					//调用适配器入金接口,适配器入金接口回调inmoney方法.
					rVal = bankadapter.inMoneyQueryBank(inMoneyInfo);
				}
				try 
				{
					//启动数据库事务
					conn.setAutoCommit(false);
					if(rVal.result == 0)
					{
						log("适配器处理成功，业务流水处理成功,入金成功。");
					}
					else if(rVal.result == 5)
					{
						result = rVal.result;
						DAO.modCapitalInfoStatus(capitalID,rVal.funID, 2,curTime, conn);
						DAO.modCapitalInfoStatus(rateID,rVal.funID, 2,curTime, conn);
						log("适配器处理成功，业务流水处理中。");
					}
					else if( rVal.result==ErrorCode.bankNull )
					{
						result = rVal.result;
						DAO.modCapitalInfoStatus(capitalID,rVal.funID, 5,curTime, conn);
						DAO.modCapitalInfoStatus(rateID,rVal.funID, 5,curTime, conn);
						log("适配器连接银行，返回银行无应答");
					}
					else if( rVal.result==ErrorCode.ActionID_Error )
					{
						result = rVal.result;
						DAO.modCapitalInfoStatus(capitalID,rVal.funID, 6,curTime, conn);
						DAO.modCapitalInfoStatus(rateID,rVal.funID, 6,curTime, conn);
						log("适配器连接银行，返回银行返回市场代码为空");
					}
					else//适配器提交失败
					{
						result = rVal.result;
						DAO.modCapitalInfoStatus(capitalID,rVal.funID, 1,curTime, conn);
						DAO.modCapitalInfoStatus(rateID,rVal.funID, 1,curTime, conn);
						result=rVal.result;
						log("适配器提交失败:"+result);
					}
					conn.commit();
				} 
				catch(SQLException e) 
				{
					result =ErrorCode.inMoneyM_DataBaseException;
					e.printStackTrace();
					conn.rollback();
					log("市场端发起入金,更新资金流失状态为失败SQLException,数据回滚:"+result);
				}
				catch(Exception e)
				{
					result =ErrorCode.inMoneyM_SysException;
					e.printStackTrace();
					conn.rollback();
					log("市场端发起入金,系统异常Exception,数据回滚:"+result);
				}
				finally
				{
					conn.setAutoCommit(true);
				}

				//调用交易系统接口失败，将资金流水置为处理失败状态
				if(result == ErrorCode.inMoneyM_DataBaseException)
				{
					DAO.modCapitalInfoStatus(capitalID,rVal.funID, 1,curTime, conn);
					DAO.modCapitalInfoStatus(rateID,rVal.funID, 1,curTime, conn);
				}
			}
			/********************************************************************/
		} 
		catch(SQLException e) 
		{
			e.printStackTrace();
			result = ErrorCode.inMoneyM_DataBaseException;
			log("市场端发起入金SQLException:"+result);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			result = ErrorCode.inMoneyM_SysException;
			log("市场端发起入金Exception:"+result);
		}
		finally
		{
			DAO.closeStatement(null, null, conn);
		}

		return result;
	}

	
	/**
	 * 出金审核[1次]
	 * @param actionID  转账模块业务流水号
	 * @param funFlag  审核结果
	 * @param audit  手工[false]与自动[true] 
	 * @return long 0 成功 <0失败 
	 */
	public long outMoneyAudit(long actionID,boolean funFlag)
	{
		System.out.println("{{{{{{{{{{{{{{{{{{{{出金审核[1次]{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("传入参数：actionID["+actionID+"]funFlag["+funFlag+"]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}}}");
		String auditInfo="审核业务流水号："+actionID+";";
		//数据库连接
		Connection conn = null;

		//当前系统时间
		Timestamp curTime = new Timestamp(System.currentTimeMillis());

		//资金划转流水对象
		CapitalValue capital =null;

		//手续费流水对象
		CapitalValue rate =null;

		//适配器返回结果
		ReturnValue rVal = null;

		//返回码
		long result = 0;
		
		//是否2次审核
		boolean secondMoneyAudit = getConfig("secondMoneyAudit").equalsIgnoreCase("true");

		//当比流水的状态是否为3
//		Vector<CapitalValue> vv=null;

		//如果设置了审核时间则判断是否在审核时间内
		if(getConfig("AuditBeginTime")!=null && getConfig("AuditBeginTime").length()>0)
		{
			java.sql.Time AuditBeginTime=Tool.convertTime(getConfig("AuditBeginTime"));
			java.sql.Time curSqlTime=Tool.convertTime(Tool.fmtOnlyTime(curTime));
			if(AuditBeginTime!=null && AuditBeginTime.after(curSqlTime))
			{
				result = ErrorCode.outMoneyAudit_ErrorTimeUn;
				log("审核时间未到！");
				return result;
			}
		}
		
		//如果设置了审核时间则判断是否在审核时间内
		if(getConfig("AuditEndTime")!=null && getConfig("AuditEndTime").length()>0)
		{
			java.sql.Time AuditEndTime=Tool.convertTime(getConfig("AuditEndTime"));
			java.sql.Time curSqlTime=Tool.convertTime(Tool.fmtOnlyTime(curTime));
			if(AuditEndTime!=null && curSqlTime.after(AuditEndTime))
			{
				result = ErrorCode.outMoneyAudit_ErrorTimeOut;
				log("审核时间已过！");
				return result;
			}
		}
		//synchronized(firstAudit){
			try 
			{
				conn = DAO.getConnection();
				//判断本条信息是否已近有人审核
//				vv=DAO.getCapitalInfoList("where actionID='"+actionID+"' and status=3");
//				if(vv==null || vv.size()<=0){
//					result = ErrorCode.outMoneyAudit_OradyAudite;
//					log("信息已在处理中");
//					return result;
//				}
				Vector<CapitalValue> list = DAO.getCapitalInfoList("where actionid="+actionID+"", conn);//获取资金流水对象（包括出金和出金手续费流水）
				for (int i = 0; i < list.size(); i++) 
				{
					CapitalValue val = (CapitalValue)list.get(i);
					if(val.type == OUTMONEY)
					{
						capital = val;
//						if(capital.status!=3){
//							result = ErrorCode.outMoneyAudit_OradyAudite;
//							log("信息已在处理中");
//							return result;
//						}
						System.out.println("[流水信息：]"+capital.toString());
					}
					else if(val.type == RATE)
					{
						rate = val;
					}
				}
				if(capital!=null && rate!=null)
				{
					if(secondMoneyAudit){
						if(funFlag)//审核通过
						{
							/**
							 * 如果设置为二次审核
							 * 一审成功：资金不解冻、只更改资金流水状态。
							 * 一审拒绝：资金解冻。
							 */
								try 
								{										
									//启动数据库事务
									conn.setAutoCommit(false);
									Vector<CapitalValue> ll = DAO.getCapitalInfoList("where actionID='"+actionID+"' and status=3 for update ",conn);
									if(ll == null || ll.size()<=0){
										result = ErrorCode.outMoneyAudit_OradyAudite;
										log("信息已在处理中");
										return result;
									}
									//dataProcess.updateFrozenFunds(capital.firmID, -1*Arith.add(capital.money, rate.money), conn);//资金解冻
									DAO.modCapitalInfoStatus(capital.iD,capital.funID, 4,curTime, conn);
									DAO.modCapitalInfoStatus(rate.iD,rate.funID, 4,curTime, conn);
									DAO.modCapitalInfoNote(capital.iD, capital.note+"_Audit_1", conn);
									DAO.modCapitalInfoNote(rate.iD, capital.note+"_Audit_1", conn);
									result = 0;	
									conn.commit();
								} 
								catch(SQLException e) 
								{
									e.printStackTrace();
									conn.rollback();
									result = ErrorCode.outMoneyAudit_DataBaseException;
								}
								finally
								{
									conn.setAutoCommit(true);
								}	
							} else {//拒绝出金处理
							
								//取得出金付款信息
								TransferInfoValue payInfo = getPayInfo(capital.bankID, capital.firmID,OUTMONEY, conn);
								//取得出金收款方信息
								TransferInfoValue receiveInfo=getReceiveInfo(capital.bankID, capital.firmID,OUTMONEY, conn);
								BankValue bv = DAO.getBank(capital.bankID);
								OutMoneyVO outMoneyInfo=new OutMoneyVO(capital.bankID,bv.bankName, capital.money, capital.firmID, payInfo, receiveInfo,actionID,capital.express,capital.funID);

								try {
									//如果为银行端出金，则通知银行未通过审核
									if("bank_out".equals(capital.note)) {
										long number = tradeDate(capital.bankID);
										if(number!=0){
											return number;
										}
										//取得适配器对象
										BankAdapterRMI bankadapter = getAdapter(capital.bankID);
										if(bankadapter == null){
											result = ErrorCode.GetAdapter_Error;
											log("处理器连接适配器["+capital.bankID+"]失败");
											return result;
										}else{
											try{
												conn.setAutoCommit(false);
												Vector<CapitalValue> ll = DAO.getCapitalInfoList("where actionID='"+actionID+"' and status=3 for update ",conn);
												if(ll == null || ll.size()<=0){
													result = ErrorCode.outMoneyAudit_OradyAudite;
													log("信息已在处理中");
													return result;
												}
												DAO.modCapitalInfoStatus(capital.iD,capital.funID, 2,curTime, conn);
												DAO.modCapitalInfoStatus(rate.iD,rate.funID, 2,curTime, conn);
												conn.commit();
											}catch(SQLException e){
												conn.rollback();
												e.printStackTrace();
												result = ErrorCode.outMoneyAudit_DataBaseException;
												log("修改记录为处理中失败，SQL异常");
												return result;
											}finally{
												conn.setAutoCommit(true);
											}
											//调用适配器银行端出金接口
											rVal = bankadapter.outMoneyBackBank(outMoneyInfo, false);
										}
									}
									else
									{
										try{
											conn.setAutoCommit(false);
											Vector<CapitalValue> ll = DAO.getCapitalInfoList("where actionID='"+actionID+"' and status=3 for update ",conn);
											if(ll == null || ll.size()<=0){
												result = ErrorCode.outMoneyAudit_OradyAudite;
												log("信息已在处理中");
												return result;
											}
											DAO.modCapitalInfoStatus(capital.iD,capital.funID, 2,curTime, conn);
											DAO.modCapitalInfoStatus(rate.iD,rate.funID, 2,curTime, conn);
											conn.commit();
										}catch(SQLException e){
											conn.rollback();
											e.printStackTrace();
											result = ErrorCode.outMoneyAudit_DataBaseException;
											log("修改记录为处理中失败，SQL异常");
											return result;
										}finally{
											conn.setAutoCommit(true);
										}
										rVal = new ReturnValue();
										rVal.result = 0;
										rVal.funID="";
									}
									//启动数据库事务
									conn.setAutoCommit(false);
									if(rVal.result==0){
										//调用交易系统接口
										dataProcess.updateFrozenFunds(capital.firmID, -1*Arith.add(capital.money, rate.money), conn);//资金解冻
										//修改交易商银行接口冻结资金
										this.bankFrozenFuns(capital.firmID, capital.bankID, null, -1*Arith.add(capital.money, rate.money), conn);
										System.out.println("审核数据ID:>>"+capital.iD+"<<");
										System.out.println("手续费的ID:>>"+rate.iD+"<<");
										DAO.modCapitalInfoStatus(capital.iD,rVal==null ? "" : rVal.funID, 1,curTime, conn);
										DAO.modCapitalInfoStatus(rate.iD, rVal==null ? "" : rVal.funID,1,curTime, conn);
										DAO.modCapitalInfoNote(capital.iD, "["+capital.note+"]审核拒绝成功", conn);
										System.out.println("["+capital.note+"]审核拒绝成功");
										DAO.modCapitalInfoNote(rate.iD, "["+capital.note+"]审核拒绝成功", conn);
										System.out.println("["+capital.note+"]审核拒绝成功");
									}else if(rVal.result==5){
										result = rVal.result;
										this.log("市场审核 "+capital.firmID+" 交易商的银行出金记录 "+actionID+" 银行返回处理中。");
										//前面已经把当条记录设置成处理中所以不需要再重新设置
									}else if(rVal.result==ErrorCode.bankNull){
										result = rVal.result;
										DAO.modCapitalInfoStatus(capital.iD,rVal==null ? "" : rVal.funID, 5,curTime, conn);
										DAO.modCapitalInfoStatus(rate.iD, rVal==null ? "" : rVal.funID,5,curTime, conn);
										DAO.modCapitalInfoNote(capital.iD, "["+capital.note+"]审核拒绝，银行无响应", conn);
										System.out.println("["+capital.note+"]审核银行端出金拒绝，银行无响应");
										DAO.modCapitalInfoNote(rate.iD, "["+capital.note+"]审核拒绝，银行无响应", conn);
									}else if(rVal.result==ErrorCode.ActionID_Error){
										result = rVal.result;
										DAO.modCapitalInfoStatus(capital.iD,rVal==null ? "" : rVal.funID, 6,curTime, conn);
										DAO.modCapitalInfoStatus(rate.iD, rVal==null ? "" : rVal.funID,6,curTime, conn);
										DAO.modCapitalInfoNote(capital.iD, "["+capital.note+"]审核拒绝，银行返回市场流水号异常", conn);
										System.out.println("["+capital.note+"]审核银行端出金拒绝，银行返回市场流水号异常");
										DAO.modCapitalInfoNote(rate.iD, "["+capital.note+"]审核拒绝，银行返回市场流水号异常", conn);
									}else {
										this.log("市场审核 "+capital.firmID+" 交易商的银行出金记录 "+actionID+" 银行禁止拒绝。适配器返回码："+rVal.result);
									}
									conn.commit();
								} 
								catch(SQLException e) 
								{
									e.printStackTrace();
									conn.rollback();
									result = ErrorCode.outMoneyAudit_RefuseDataBaseException;
									log(auditInfo+"拒绝出金SQLException，数据回滚，需要手工处理出金和手续费，错误码="+result+"  "+e);
								}
								finally
								{
									conn.setAutoCommit(true);
								}
								//如果调用交易系统接口失败则将转帐模块资金流水记录置为失败状态
								if(result == ErrorCode.outMoneyAudit_RefuseDataBaseException)
								{
									//流水状态修改为1：审核员拒绝出金后 退出金出错
									DAO.modCapitalInfoStatus(capital.iD,rVal.funID, 1,curTime, conn);
									DAO.modCapitalInfoStatus(rate.iD, rVal.funID,1,curTime, conn);
									
									DAO.modCapitalInfoNote(capital.iD, "["+capital.note+"]审核拒绝出金后 资金解冻出错，需要手工处理出金和手续费", conn);
									DAO.modCapitalInfoNote(rate.iD, "["+capital.note+"]审核拒绝出金后 资金解冻出错，需要手工处理出金和手续费", conn);
								}	
							}
					
					}else{
						//取得出金付款信息
						TransferInfoValue payInfo = getPayInfo(capital.bankID, capital.firmID,OUTMONEY, conn);
						//取得出金收款方信息
						TransferInfoValue receiveInfo=getReceiveInfo(capital.bankID, capital.firmID,OUTMONEY, conn);
						BankValue bv = DAO.getBank(capital.bankID);
						OutMoneyVO outMoneyInfo=new OutMoneyVO(capital.bankID,bv.bankName,capital.money,capital.firmID,payInfo,receiveInfo,actionID,capital.express,capital.funID);
						
						if(funFlag)//审核通过
						{
							long number = tradeDate(capital.bankID);
							if(number!=0){
								return number;
							}
							if(result == 0)
							{
								try 
								{
									//调用适配器出金接口
									System.out.println(capital.note);
									if("market_out".equals(capital.note)||"market_out_Audit_1".equals(capital.note))
									{
										//取得适配器对象
										BankAdapterRMI bankadapter = getAdapter(capital.bankID);
										if(bankadapter == null){
											result = ErrorCode.outMoney_SysException;
											log("处理器连接适配器["+capital.bankID+"]失败");
											return result;
										}else{
											try{
												conn.setAutoCommit(false);
												Vector<CapitalValue> ll = DAO.getCapitalInfoList("where actionID='"+actionID+"' and status=3 for update ",conn);
												if(ll == null || ll.size()<=0){
													result = ErrorCode.outMoneyAudit_OradyAudite;
													log("信息已在处理中");
													return result;
												}
												DAO.modCapitalInfoStatus(capital.iD,capital.funID, 2,curTime, conn);
												DAO.modCapitalInfoStatus(rate.iD,rate.funID, 2,curTime, conn);
												conn.commit();
											}catch(SQLException e){
												conn.rollback();
												e.printStackTrace();
												result = ErrorCode.outMoneyAudit_DataBaseException;
												log("修改记录为处理中失败，SQL异常");
												return result;
											}finally{
												conn.setAutoCommit(true);
											}
											//调用适配器市场端出金接口
											rVal = bankadapter.outMoneyMarketDone(outMoneyInfo);
										}
									}
									else if("bank_out".equals(capital.note)||"bank_out_Audit_1".equals(capital.note))
									{
										//取得适配器对象
										BankAdapterRMI bankadapter = getAdapter(capital.bankID);
										if(bankadapter == null){
											result = ErrorCode.outMoney_SysException;
											log("处理器连接适配器["+capital.bankID+"]失败");
											return result;
										}else{
											try{
												conn.setAutoCommit(false);
												Vector<CapitalValue> ll = DAO.getCapitalInfoList("where actionID='"+actionID+"' and status=3 for update ",conn);
												if(ll == null || ll.size()<=0){
													result = ErrorCode.outMoneyAudit_OradyAudite;
													log("信息已在处理中");
													return result;
												}
												DAO.modCapitalInfoStatus(capital.iD,capital.funID, 2,curTime, conn);
												DAO.modCapitalInfoStatus(rate.iD,rate.funID, 2,curTime, conn);
												conn.commit();
											}catch(SQLException e){
												conn.rollback();
												e.printStackTrace();
												result = ErrorCode.outMoneyAudit_DataBaseException;
												log("修改记录为处理中失败，SQL异常");
												return result;
											}finally{
												conn.setAutoCommit(true);
											}
											rVal = bankadapter.outMoneyBackBank(outMoneyInfo, true);
										}
									}
										/**如果银行端处理失败则进行市场端数据(数据库数据，内存数据)回滚*/
									//启动数据库事务
									conn.setAutoCommit(false);
									if(rVal.result == 5){
										result = rVal.result;
										DAO.modCapitalInfoStatus(capital.iD,rVal.funID, 2,curTime, conn);
										DAO.modCapitalInfoStatus(rate.iD,rVal.funID, 2,curTime, conn);
										DAO.modCapitalInfoNote(capital.iD, "["+capital.note+"]审核通过，银行处理中", conn);
										DAO.modCapitalInfoNote(rate.iD, "["+capital.note+"]审核通过，银行处理中", conn);
									}
									else if(rVal.result == ErrorCode.bankNull){
										result = rVal.result;
										DAO.modCapitalInfoStatus(capital.iD,rVal.funID, 5,curTime, conn);
										DAO.modCapitalInfoStatus(rate.iD,rVal.funID, 5,curTime, conn);
										DAO.modCapitalInfoNote(capital.iD, "["+capital.note+"]审核通过，银行无返回，待处理", conn);
										DAO.modCapitalInfoNote(rate.iD, "["+capital.note+"]审核通过，银行无返回，待处理", conn);
									}
									else if(rVal.result == ErrorCode.ActionID_Error){
										result = rVal.result;
										DAO.modCapitalInfoStatus(capital.iD,rVal.funID, 6,curTime, conn);
										DAO.modCapitalInfoStatus(rate.iD,rVal.funID, 6,curTime, conn);
										DAO.modCapitalInfoNote(capital.iD, "["+capital.note+"]审核通过，银行返回市场流水号和市场本身流水号不符", conn);
										DAO.modCapitalInfoNote(rate.iD, "["+capital.note+"]审核通过，银行返回市场流水号和市场本身流水号不符", conn);
									}
									else if(rVal.result == 0)//银行端处理成功，出金
									{
										dataProcess.updateFundsFull(capital.firmID, dataProcess.getOUTSUMMARY()+"", dataProcess.getBANKSUB().get(capital.bankID), capital.money, actionID,conn);//扣除出金
										dataProcess.updateFundsFull(capital.firmID, dataProcess.getFEESUMMARY()+"", null, rate.money,actionID, conn);//扣除手续费
										dataProcess.updateFrozenFunds(capital.firmID, -1*Arith.add(capital.money, rate.money), conn);//资金解冻
										//修改交易商银行接口冻结资金
										this.bankFrozenFuns(capital.firmID, capital.bankID, null, -1*Arith.add(capital.money, rate.money), conn);
										DAO.modCapitalInfoStatus(capital.iD,rVal.funID, 0,curTime, conn);
										DAO.modCapitalInfoStatus(rate.iD,rVal.funID, 0,curTime, conn);
										DAO.modCapitalInfoNote(capital.iD, "["+capital.note+"]审核通过，银行处理成功，出金成功", conn);
										DAO.modCapitalInfoNote(rate.iD, "["+capital.note+"]审核通过，银行处理成功，扣除手续费成功", conn);
									}
									else 
									{
										result =rVal.result;	//发送给银行过程中处理失败
										if(!"false".equalsIgnoreCase(getConfig("OutFailProcess")))//失败后是否进行退资金操作
										{
											dataProcess.updateFrozenFunds(capital.firmID, -1*Arith.add(capital.money, rate.money), conn);//资金解冻
											//修改交易商银行接口冻结资金
											this.bankFrozenFuns(capital.firmID, capital.bankID, null, -1*Arith.add(capital.money, rate.money), conn);
											DAO.modCapitalInfoStatus(capital.iD,rVal.funID, 1,curTime, conn);
											DAO.modCapitalInfoStatus(rate.iD,rVal.funID, 1,curTime, conn);
											DAO.modCapitalInfoNote(capital.iD, "["+capital.note+"]审核通过，银行处理失败，出金金额已解冻", conn);
											DAO.modCapitalInfoNote(rate.iD, "["+capital.note+"]审核通过，银行处理失败，手续费金额已解冻", conn);

											log(auditInfo+"审核通过，银行处理失败,退还全部出金和手续费，错误码="+result);
										}
										else
										{//状态修改为1：发送给银行过程中处理失败
											DAO.modCapitalInfoStatus(capital.iD,rVal.funID, 1,curTime, conn);
											DAO.modCapitalInfoStatus(rate.iD, rVal.funID,1,curTime, conn);
											DAO.modCapitalInfoNote(capital.iD, "["+capital.note+"]审核通过，银行处理失败，需手工解冻出金", conn);
											DAO.modCapitalInfoNote(rate.iD, "["+capital.note+"]审核通过，银行处理失败，需手工解冻手续费", conn);
											log(auditInfo+"审核通过，银行处理失败，需要手工解冻出金和手续费，错误码="+result);
										}
									}
									conn.commit();
								} 
								catch(SQLException e) 
								{
									e.printStackTrace();
									conn.rollback();
									result = ErrorCode.outMoneyAudit_PassDataBaseException;
									log(auditInfo+"审核出金SQLException，数据回滚，需要手工处理出金和手续费，错误码="+result+"  "+e);
								}
								finally
								{
									conn.setAutoCommit(true);
								}	
							}
	
							/**如果调用交易系统接口失败则将转帐模块资金流水记录置为失败状态*/
							if(result == ErrorCode.outMoneyAudit_PassDataBaseException)
							{//流水状态修改为1：审核过程中出现异常
								DAO.modCapitalInfoStatus(capital.iD,rVal.funID, 1,curTime, conn);
								DAO.modCapitalInfoStatus(rate.iD, rVal.funID,1,curTime, conn);
								
								DAO.modCapitalInfoNote(capital.iD, "["+capital.note+"]审核通过，系统异常，需要手工处理出金和手续费", conn);
								DAO.modCapitalInfoNote(rate.iD, "["+capital.note+"]审核通过，系统异常，需要手工处理出金和手续费", conn);
							}
						}
						else//拒绝出金处理
						{
							try
							{
								/**如果为银行端出金，则通知银行未通过审核*/
								if("bank_out".equals(capital.note))
								{
									long number = tradeDate(capital.bankID);
									if(number!=0){
										return number;
									}
									//取得适配器对象
									BankAdapterRMI bankadapter = getAdapter(capital.bankID);
									if(bankadapter == null){
										result = ErrorCode.GetAdapter_Error;
										log("处理器连接适配器["+capital.bankID+"]失败");
										return result;
									}else{
										try{
											conn.setAutoCommit(false);
											Vector<CapitalValue> ll = DAO.getCapitalInfoList("where actionID='"+actionID+"' and status=3 for update ",conn);
											if(ll == null || ll.size()<=0){
												result = ErrorCode.outMoneyAudit_OradyAudite;
												log("信息已在处理中");
												return result;
											}
											DAO.modCapitalInfoStatus(capital.iD,capital.funID, 2,curTime, conn);
											DAO.modCapitalInfoStatus(rate.iD,rate.funID, 2,curTime, conn);
											conn.commit();
										}catch(SQLException e){
											conn.rollback();
											e.printStackTrace();
											result = ErrorCode.outMoneyAudit_DataBaseException;
											log("修改记录为处理中失败，SQL异常");
											return result;
										}finally{
											conn.setAutoCommit(true);
										}
										//调用适配器银行端出金接口
										rVal = bankadapter.outMoneyBackBank(outMoneyInfo, false);
									}
								}
								else
								{
									try{
										conn.setAutoCommit(false);
										Vector<CapitalValue> ll = DAO.getCapitalInfoList("where actionID='"+actionID+"' and status=3 for update ",conn);
										if(ll == null || ll.size()<=0){
											result = ErrorCode.outMoneyAudit_OradyAudite;
											log("信息已在处理中");
											return result;
										}
										DAO.modCapitalInfoStatus(capital.iD,capital.funID, 2,curTime, conn);
										DAO.modCapitalInfoStatus(rate.iD,rate.funID, 2,curTime, conn);
										conn.commit();
									}catch(SQLException e){
										conn.rollback();
										e.printStackTrace();
										result = ErrorCode.outMoneyAudit_DataBaseException;
										log("修改记录为处理中失败，SQL异常");
										return result;
									}finally{
										conn.setAutoCommit(true);
									}
									rVal=new ReturnValue();
									rVal.funID="";
								}
								//启动数据库事务
								conn.setAutoCommit(false);
								if(rVal.result==0){
									//调用交易系统接口
									dataProcess.updateFrozenFunds(capital.firmID, -1*Arith.add(capital.money, rate.money), conn);//资金解冻
									//修改交易商银行接口冻结资金
									this.bankFrozenFuns(capital.firmID, capital.bankID, null, -1*Arith.add(capital.money, rate.money), conn);

									DAO.modCapitalInfoStatus(capital.iD,rVal.funID, 1,curTime, conn);
									DAO.modCapitalInfoStatus(rate.iD, rVal.funID,1,curTime, conn);
									DAO.modCapitalInfoNote(capital.iD, "["+capital.note+"]审核拒绝成功", conn);
									DAO.modCapitalInfoNote(rate.iD, "["+capital.note+"]审核拒绝成功", conn);
								}else if(rVal.result==5){
									result = rVal.result;
									this.log("市场拒绝 "+rVal.funID+" 交易商的出金"+actionID+" 银行处理中");
								}else if(rVal.result==ErrorCode.bankNull){
									result = rVal.result;
									DAO.modCapitalInfoStatus(capital.iD,rVal.funID, 5,curTime, conn);
									DAO.modCapitalInfoStatus(rate.iD, rVal.funID,5,curTime, conn);
									DAO.modCapitalInfoNote(capital.iD, "["+capital.note+"]审核拒绝,适配器发送给银行，银行无返回", conn);
									DAO.modCapitalInfoNote(rate.iD, "["+capital.note+"]审核拒绝,适配器发送给银行，银行无返回", conn);
								}else if(rVal.result==ErrorCode.ActionID_Error){
									result = rVal.result;
									DAO.modCapitalInfoStatus(capital.iD,rVal.funID, 6,curTime, conn);
									DAO.modCapitalInfoStatus(rate.iD, rVal.funID,6,curTime, conn);
									DAO.modCapitalInfoNote(capital.iD, "["+capital.note+"]审核拒绝,适配器发送给银行，银行返回市场流水号异常", conn);
									DAO.modCapitalInfoNote(rate.iD, "["+capital.note+"]审核拒绝,适配器发送给银行，银行返回市场流水号异常", conn);
								}else{
									result = rVal.result;
									this.log("市场拒绝 "+rVal.funID+" 交易商的出金"+actionID+" 银行处理失败，适配器返回码："+rVal.result);
								}
								conn.commit();
							} 
							catch(SQLException e) 
							{
								e.printStackTrace();
								conn.rollback();
								result = ErrorCode.outMoneyAudit_RefuseDataBaseException;
								log(auditInfo+"拒绝出金SQLException，数据回滚，需要手工处理出金和手续费，错误码="+result+"  "+e);
							}
							finally
							{
								conn.setAutoCommit(true);
							}
							//如果调用交易系统接口失败则将转帐模块资金流水记录置为失败状态
							if(result == ErrorCode.outMoneyAudit_RefuseDataBaseException)
							{
								//流水状态修改为1：审核员拒绝出金后 退出金出错
								DAO.modCapitalInfoStatus(capital.iD,rVal.funID, 1,curTime, conn);
								DAO.modCapitalInfoStatus(rate.iD, rVal.funID,1,curTime, conn);
								
								DAO.modCapitalInfoNote(capital.iD, "["+capital.note+"]审核拒绝出金,资金解冻出错，需要手工处理出金和手续费", conn);
								DAO.modCapitalInfoNote(rate.iD, "["+capital.note+"]审核拒绝出,资金解冻出错，需要手工处理出金和手续费", conn);
							}	
						}
					}
				}
				else
				{
					result =ErrorCode.outMoneyAudit_ErrorActionID;
					log(auditInfo+"没有发现业务流水号为："+actionID+"的资金流水");
				}
			}
			catch(SQLException e) 
			{
				e.printStackTrace();
				result = ErrorCode.checkTrans_DataBaseException;
				log(auditInfo+"审核出金SQLException，错误码="+result+"  "+e);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				result = ErrorCode.checkTrans_SysException;
				log(auditInfo+"审核出金Exception，错误码="+result+"  "+e);
			}
			finally
			{
				DAO.closeStatement(null, null, conn);
			}
		//}
		return result;
	}
	
	
	/**
	 * 出金二次审核
	 * @param actionID  转账模块业务流水号
	 * @param funFlag  审核结果 
	 * @return long 0 成功 <0失败 
	 */
	public long outMoneySecondAudit(long actionID,boolean funFlag) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{出金二次审核{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("市场流水号："+actionID+" 审核结果："+(funFlag ? "通过" : "驳回"));
		System.out.println("}}}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}}}}}}");
		String auditInfo="审核业务流水号："+actionID+";";
		//数据库连接
		Connection conn = null;

		//当前系统时间
		Timestamp curTime = new Timestamp(System.currentTimeMillis());

		//资金划转流水对象
		CapitalValue capital =null;

		//手续费流水对象
		CapitalValue rate =null;

		//适配器返回结果
		ReturnValue rVal = null;

		//返回码
		long result = 0;
		
//		Vector<CapitalValue> vv=null;

		//如果设置了审核时间则判断是否在审核时间内
		if(getConfig("AuditBeginTime")!=null && getConfig("AuditBeginTime").length()>0)
		{
			java.sql.Time AuditBeginTime=Tool.convertTime(getConfig("AuditBeginTime"));
			java.sql.Time curSqlTime=Tool.convertTime(Tool.fmtOnlyTime(curTime));
			if(AuditBeginTime!=null && AuditBeginTime.after(curSqlTime))
			{
				result = ErrorCode.outMoneyAudit_ErrorTimeUn;
				log("审核时间未到！");
				return result;
			}
		}
		
		//如果设置了审核时间则判断是否在审核时间内
		if(getConfig("AuditEndTime")!=null && getConfig("AuditEndTime").length()>0)
		{
			java.sql.Time AuditEndTime=Tool.convertTime(getConfig("AuditEndTime"));
			java.sql.Time curSqlTime=Tool.convertTime(Tool.fmtOnlyTime(curTime));
			if(AuditEndTime!=null && curSqlTime.after(AuditEndTime))
			{
				result = ErrorCode.outMoneyAudit_ErrorTimeOut;
				log("审核时间已过！");
				return result;
			}
		}
			try 
			{
				conn = DAO.getConnection();
				//判断本条信息是否已近有人审核
//					vv=DAO.getCapitalInfoList("where actionID='"+actionID+"' and (status=4 or status=3)");
//					if(vv==null || vv.size()<=0){
//						result = ErrorCode.outMoneyAudit_OradyAudite;
//						log("信息已在处理中");
//						return result;
//					}
				Vector<CapitalValue> list = DAO.getCapitalInfoList("where actionid="+actionID, conn);//获取资金流水对象（包括出金和出金手续费流水）
				for (int i = 0; i < list.size(); i++) 
				{
					CapitalValue val = (CapitalValue)list.get(i);
					if(val.type == OUTMONEY)
					{
						capital = val;//出金流水
					}
					else if(val.type == RATE)
					{
						rate = val;//手续费流水
					}
				}
				
				if(capital!=null && rate!=null)
				{
					//取得出金付款信息
					TransferInfoValue payInfo = getPayInfo(capital.bankID, capital.firmID,OUTMONEY, conn);
					//取得出金收款方信息
					TransferInfoValue receiveInfo=getReceiveInfo(capital.bankID, capital.firmID,OUTMONEY, conn);
					BankValue bv = DAO.getBank(capital.bankID);
					OutMoneyVO outMoneyInfo=new OutMoneyVO(capital.bankID,bv.bankName,capital.money,capital.firmID,payInfo,receiveInfo,actionID,capital.express,capital.funID);
					
					if(funFlag) {//审核通过
						long number = tradeDate(capital.bankID);
						if(number!=0){
							return number;
						}
						if(result == 0) {
							try {
								//调用适配器出金接口
								if("market_out".equals(capital.note)||"market_out_Audit_1".equals(capital.note)) {
									//取得适配器对象
									BankAdapterRMI bankadapter = getAdapter(capital.bankID);
									if(bankadapter == null){
										result = ErrorCode.GetAdapter_Error;
										log("处理器连接适配器["+capital.bankID+"]失败");
										return result;
									}else{
										try{
											conn.setAutoCommit(false);
											Vector<CapitalValue> ll = DAO.getCapitalInfoList("where actionID='"+actionID+"' and (status=4 or status=3) for update",conn);
											if(ll==null || ll.size()<=0){
												result = ErrorCode.outMoneyAudit_OradyAudite;
												log("信息已在处理中");
												return result;
											}
											DAO.modCapitalInfoStatus(capital.iD,capital.funID, 2,curTime, conn);
											DAO.modCapitalInfoStatus(rate.iD,rate.funID, 2,curTime, conn);
											conn.commit();
										}catch(SQLException e){
											conn.rollback();
											e.printStackTrace();
											result = ErrorCode.outMoneyAudit_DataBaseException;
											log("修改记录为处理中失败，SQL异常");
											return result;
										}finally{
											conn.setAutoCommit(true);
										}
										//调用适配器市场端出金接口
										rVal = bankadapter.outMoneyMarketDone(outMoneyInfo);
									}
								}
								//如果为银行端出金，则通知银行通过审核
								if("bank_out".equals(capital.note)||"bank_out_Audit_1".equals(capital.note))
								{//调用适配器银行端出金接口
									//取得适配器对象
									BankAdapterRMI bankadapter = getAdapter(capital.bankID);
									if(bankadapter == null){
										result = ErrorCode.outMoney_SysException;
										log("处理器连接适配器["+capital.bankID+"]失败");
										return result;
									}else{
										try{
											conn.setAutoCommit(false);
											Vector<CapitalValue> ll = DAO.getCapitalInfoList("where actionID='"+actionID+"' and (status=4 or status=3) for update",conn);
											if(ll==null || ll.size()<=0){
												result = ErrorCode.outMoneyAudit_OradyAudite;
												log("信息已在处理中");
												return result;
											}
											DAO.modCapitalInfoStatus(capital.iD,capital.funID, 2,curTime, conn);
											DAO.modCapitalInfoStatus(rate.iD,rate.funID, 2,curTime, conn);
											conn.commit();
										}catch(SQLException e){
											conn.rollback();
											e.printStackTrace();
											result = ErrorCode.outMoneyAudit_DataBaseException;
											log("修改记录为处理中失败，SQL异常");
											return result;
										}finally{
											conn.setAutoCommit(true);
										}
										rVal = bankadapter.outMoneyBackBank(outMoneyInfo, true);
									}
								}
								//启动数据库事务
								conn.setAutoCommit(false);
								//如果银行端处理失败则进行市场端数据(数据库数据，内存数据)回滚
								if(rVal.result == 0)
								{
									dataProcess.updateFundsFull(capital.firmID, dataProcess.getOUTSUMMARY()+"", dataProcess.getBANKSUB().get(capital.bankID), capital.money, actionID,conn);//扣除出金
									dataProcess.updateFundsFull(capital.firmID, dataProcess.getFEESUMMARY()+"", null, rate.money,actionID, conn);//扣除手续费
									dataProcess.updateFrozenFunds(capital.firmID, -1*Arith.add(capital.money, rate.money), conn);//资金解冻
									//修改交易商银行接口冻结资金
									this.bankFrozenFuns(capital.firmID, capital.bankID, null, -1*Arith.add(capital.money, rate.money), conn);
									
									DAO.modCapitalInfoStatus(capital.iD,rVal.funID, 0,curTime, conn);
									DAO.modCapitalInfoStatus(rate.iD,rVal.funID, 0,curTime, conn);
									
									DAO.modCapitalInfoNote(capital.iD, "["+capital.note+"]审核通过，银行处理成功，出金成功", conn);
									DAO.modCapitalInfoNote(rate.iD, "["+capital.note+"]审核通过，银行处理成功，扣除手续费成功", conn);
									
									log(auditInfo+"审核通过，银行处理成功,出金成功,扣除手续费成功,银行流水号="+rVal.funID);
								}
								else if(rVal.result == 5)
								{//状态修改为rVal.result：发送给银行 处理过程中
									result = rVal.result;
									DAO.modCapitalInfoStatus(capital.iD,rVal.funID, 2,curTime, conn);
									DAO.modCapitalInfoStatus(rate.iD, rVal.funID,2,curTime, conn);
									DAO.modCapitalInfoNote(capital.iD, "["+capital.note+"]审核通过，银行处理成功，等待处理中", conn);
									DAO.modCapitalInfoNote(rate.iD, "["+capital.note+"]审核通过，银行处理成功，等待处理中", conn);

									log(auditInfo+"审核通过，银行处理成功，等待处理中");
								}
								else if(rVal.result == ErrorCode.bankNull)
								{//状态修改为rVal.result：发送给银行 处理过程中
									result = rVal.result;
									DAO.modCapitalInfoStatus(capital.iD,rVal.funID, 5,curTime, conn);
									DAO.modCapitalInfoStatus(rate.iD, rVal.funID,5,curTime, conn);
									DAO.modCapitalInfoNote(capital.iD, "["+capital.note+"]审核通过，银行无返回", conn);
									DAO.modCapitalInfoNote(rate.iD, "["+capital.note+"]审核通过，银行无返回", conn);

									log(auditInfo+"审核通过，银行无返回");
								}
								else if(rVal.result == ErrorCode.ActionID_Error)
								{//状态修改为rVal.result：发送给银行 处理过程中
									result = rVal.result;
									DAO.modCapitalInfoStatus(capital.iD,rVal.funID, 6,curTime, conn);
									DAO.modCapitalInfoStatus(rate.iD, rVal.funID,6,curTime, conn);
									DAO.modCapitalInfoNote(capital.iD, "["+capital.note+"]审核通过，银行返回的市场流水号和市场本身流水不一致", conn);
									DAO.modCapitalInfoNote(rate.iD, "["+capital.note+"]审核通过，银行返回的市场流水号和市场本身流水不一致", conn);

									log(auditInfo+"审核通过，银行返回的市场流水号和市场本身流水不一致");
								}
								else 
								{
									result =rVal.result;	//发送给银行过程中处理失败
									if(!"false".equalsIgnoreCase(getConfig("OutFailProcess")))//失败后是否进行退资金操作
									{
										dataProcess.updateFrozenFunds(capital.firmID, -1*Arith.add(capital.money, rate.money), conn);//资金解冻
										//修改交易商银行接口冻结资金
										this.bankFrozenFuns(capital.firmID, capital.bankID, null, -1*Arith.add(capital.money, rate.money), conn);
										DAO.modCapitalInfoStatus(capital.iD,rVal.funID, 1,curTime, conn);
										DAO.modCapitalInfoStatus(rate.iD,rVal.funID, 1,curTime, conn);
										DAO.modCapitalInfoNote(capital.iD,"["+capital.note+"]审核通过，银行处理失败，出金金额已解冻", conn);
										DAO.modCapitalInfoNote(rate.iD, "["+capital.note+"]审核通过，银行处理失败，手续费金额已解冻", conn);
										log(auditInfo+"审核通过，银行处理失败,退还全部出金和手续费，错误码="+result);
									}
									else
									{//状态修改为1：发送给银行过程中处理失败
										DAO.modCapitalInfoStatus(capital.iD,rVal.funID, 1,curTime, conn);
										DAO.modCapitalInfoStatus(rate.iD, rVal.funID,1,curTime, conn);
										DAO.modCapitalInfoNote(capital.iD,"["+capital.note+"]审核通过，银行处理失败，需手工解冻出金金额", conn);
										DAO.modCapitalInfoNote(rate.iD, "["+capital.note+"]审核通过，银行处理失败，需手工解冻手续费金额", conn);

										log(auditInfo+"审核通过，银行处理失败，需手工解冻出金和手续费，错误码="+result);
									}
								}
								conn.commit();
							} 
							catch(SQLException e) 
							{
								e.printStackTrace();
								conn.rollback();
								result = ErrorCode.outMoneyAudit_PassDataBaseException;
								log(auditInfo+"审核出金SQLException，数据回滚，需要手工处理出金和手续费，错误码="+result+"  "+e);
							}
							finally
							{
								conn.setAutoCommit(true);
							}	
						}
	
						/**如果调用交易系统接口失败则将转帐模块资金流水记录置为失败状态*/
						if(result == ErrorCode.outMoneyAudit_PassDataBaseException)
						{//流水状态修改为1：审核过程中出现异常
							DAO.modCapitalInfoStatus(capital.iD,capital.funID, 1,curTime, conn);
							DAO.modCapitalInfoStatus(rate.iD, capital.funID,1,curTime, conn);
							DAO.modCapitalInfoNote(capital.iD, "["+capital.note+"]审核通过，系统异常，需手工处理出金", conn);
							DAO.modCapitalInfoNote(rate.iD, "["+capital.note+"]审核通过，系统异常，需手工处理手续费", conn);
						}
					}
					else//拒绝出金处理
					{
						try
						{//如果为银行端出金，则通知银行未通过审核
							if("bank_out".equals(capital.note)||"bank_out_Audit_1".equals(capital.note))
							{
								long number = tradeDate(capital.bankID);
								if(number!=0){
									return number;
								}
								//取得适配器对象
								BankAdapterRMI bankadapter = getAdapter(capital.bankID);
								if(bankadapter == null){
									result = ErrorCode.GetAdapter_Error;
									log("处理器连接适配器["+capital.bankID+"]失败");
									return result;
								}else{
									try{
										conn.setAutoCommit(false);
										Vector<CapitalValue> ll = DAO.getCapitalInfoList("where actionID='"+actionID+"' and (status=4 or status=3) for update",conn);
										if(ll==null || ll.size()<=0){
											result = ErrorCode.outMoneyAudit_OradyAudite;
											log("信息已在处理中");
											return result;
										}
										DAO.modCapitalInfoStatus(capital.iD,capital.funID, 2,curTime, conn);
										DAO.modCapitalInfoStatus(rate.iD,rate.funID, 2,curTime, conn);
										conn.commit();
									}catch(SQLException e){
										conn.rollback();
										e.printStackTrace();
										result = ErrorCode.outMoneyAudit_DataBaseException;
										log("修改记录为处理中失败，SQL异常");
										return result;
									}finally{
										conn.setAutoCommit(true);
									}
									//调用适配器银行端出金接口
									rVal = bankadapter.outMoneyBackBank(outMoneyInfo, false);
								}
							} else {
								try{
									conn.setAutoCommit(false);
									Vector<CapitalValue> ll = DAO.getCapitalInfoList("where actionID='"+actionID+"' and (status=4 or status=3) for update",conn);
									if(ll==null || ll.size()<=0){
										result = ErrorCode.outMoneyAudit_OradyAudite;
										log("信息已在处理中");
										return result;
									}
									DAO.modCapitalInfoStatus(capital.iD,capital.funID, 2,curTime, conn);
									DAO.modCapitalInfoStatus(rate.iD,rate.funID, 2,curTime, conn);
									conn.commit();
								}catch(SQLException e){
									conn.rollback();
									e.printStackTrace();
									result = ErrorCode.outMoneyAudit_DataBaseException;
									log("修改记录为处理中失败，SQL异常");
									return result;
								}finally{
									conn.setAutoCommit(true);
								}
								rVal = new ReturnValue();
								rVal.result = 0;
							}
							//启动数据库事务
							conn.setAutoCommit(false);
							if(rVal.result==0){
								//调用交易系统接口
								dataProcess.updateFrozenFunds(capital.firmID, -1*Arith.add(capital.money, rate.money), conn);//资金解冻
								//修改交易商银行接口冻结资金
								this.bankFrozenFuns(capital.firmID, capital.bankID, null, -1*Arith.add(capital.money, rate.money), conn);
								DAO.modCapitalInfoStatus(capital.iD,rVal==null?"":rVal.funID, 1,curTime, conn);
								DAO.modCapitalInfoStatus(rate.iD, rVal==null?"":rVal.funID,1,curTime, conn);

								DAO.modCapitalInfoNote(capital.iD, "["+capital.note+"]审核拒绝成功", conn);
								DAO.modCapitalInfoNote(rate.iD, "["+capital.note+"]审核拒绝成功", conn);
							}else if(rVal.result==5){
								result = rVal.result;
								this.log("拒绝 "+capital.firmID+" 交易商的银行出金 "+actionID+" ，银行返回处理中");
							}else if(rVal.result==ErrorCode.bankNull){
								result = rVal.result;
								DAO.modCapitalInfoStatus(capital.iD,rVal==null?"":rVal.funID, 5,curTime, conn);
								DAO.modCapitalInfoStatus(rate.iD, rVal==null?"":rVal.funID,5,curTime, conn);

								DAO.modCapitalInfoNote(capital.iD, "["+capital.note+"]审核拒绝，适配器发送给银行，银行无返回", conn);
								DAO.modCapitalInfoNote(rate.iD, "["+capital.note+"]审核拒绝，适配器发送给银行，银行无返回", conn);
							}else if(rVal.result==ErrorCode.ActionID_Error){
								result = rVal.result;
								DAO.modCapitalInfoStatus(capital.iD,rVal==null?"":rVal.funID, 6,curTime, conn);
								DAO.modCapitalInfoStatus(rate.iD, rVal==null?"":rVal.funID,6,curTime, conn);

								DAO.modCapitalInfoNote(capital.iD, "["+capital.note+"]审核拒绝，适配器发送给银行，银行返回市场流水号异常", conn);
								DAO.modCapitalInfoNote(rate.iD, "["+capital.note+"]审核拒绝，适配器发送给银行，银行返回市场流水号异常", conn);
							}else{
								result = rVal.result;
								this.log("拒绝 "+capital.firmID+" 交易商的银行出金 "+actionID+" ，银行处理失败，适配器返回码："+rVal.result);
							}
							log(auditInfo+"审核拒绝成功");
							conn.commit();
						} 
						catch(SQLException e) 
						{
							e.printStackTrace();
							conn.rollback();
							result = ErrorCode.outMoneyAudit_RefuseDataBaseException;
							
							log(auditInfo+"审核拒绝出金SQLException，数据回滚，需要手工处理出金和手续费，错误码="+result+"  "+e);
						}
						finally
						{
							conn.setAutoCommit(true);
						}
						//如果调用交易系统接口失败则将转帐模块资金流水记录置为失败状态
						if(result == ErrorCode.outMoneyAudit_RefuseDataBaseException)
						{//流水状态修改为1：审核员拒绝出金后 退出金出错
							DAO.modCapitalInfoStatus(capital.iD,rVal.funID, 1,curTime, conn);
							DAO.modCapitalInfoStatus(rate.iD, rVal.funID,1,curTime, conn);
							DAO.modCapitalInfoNote(capital.iD, "["+capital.note+"]审核拒绝出金 资金解冻出错，需要手工处理出金", conn);
							DAO.modCapitalInfoNote(rate.iD, "["+capital.note+"]审核拒绝出金 资金解冻出错，需要手工处理手续费", conn);
							log(auditInfo+"审核拒绝出金 资金解冻出错，需要手工处理出金");
						}	
					}
				}
				else
				{
					result =ErrorCode.outMoneyAudit_ErrorActionID;
					log(auditInfo+"没有发现业务流水号为："+actionID+"的资金流水");
				}
			}
			catch(SQLException e) 
			{
				e.printStackTrace();
				result = ErrorCode.checkTrans_DataBaseException;
				log(auditInfo+"审核出金SQLException，错误码="+result+"  "+e);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				result = ErrorCode.checkTrans_SysException;
				log(auditInfo+"审核出金Exception，错误码="+result+"  "+e);
			}
			finally
			{
				DAO.closeStatement(null, null, conn);
			}
		return result;
	}
	/**
	 * 出金自动审核
	 * @param actionID  转账模块业务流水号
	 * @param funFlag  审核结果 
	 * @return long 0 成功 <0失败 
	 */
	public long outMoneyAutoAudit(long actionID,boolean funFlag)
	{
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{出金自动审核{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("市场流水号："+actionID+" 审核结果："+(funFlag ? "通过" : "驳回"));
		System.out.println("}}}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}}}}}}");
		String auditInfo="审核业务流水号："+actionID+";";
		//数据库连接
		Connection conn = null;

		//当前系统时间
		Timestamp curTime = new Timestamp(System.currentTimeMillis());

		//资金划转流水对象
		CapitalValue capital =null;

		//手续费流水对象
		CapitalValue rate =null;

		//适配器返回结果
		ReturnValue rVal = null;

		//返回码
		long result = 0;
		
//		Vector<CapitalValue> vv=null;

		//如果设置了审核时间则判断是否在审核时间内
		if(getConfig("AuditBeginTime")!=null && getConfig("AuditBeginTime").length()>0)
		{
			java.sql.Time AuditBeginTime=Tool.convertTime(getConfig("AuditBeginTime"));
			java.sql.Time curSqlTime=Tool.convertTime(Tool.fmtOnlyTime(curTime));
			if(AuditBeginTime!=null && AuditBeginTime.after(curSqlTime))
			{
				result = ErrorCode.outMoneyAudit_ErrorTimeUn;
				log("审核时间未到！");
				return result;
			}
		}
		
		//如果设置了审核时间则判断是否在审核时间内
		if(getConfig("AuditEndTime")!=null && getConfig("AuditEndTime").length()>0)
		{
			java.sql.Time AuditEndTime=Tool.convertTime(getConfig("AuditEndTime"));
			java.sql.Time curSqlTime=Tool.convertTime(Tool.fmtOnlyTime(curTime));
			if(AuditEndTime!=null && curSqlTime.after(AuditEndTime))
			{
				result = ErrorCode.outMoneyAudit_ErrorTimeOut;
				log("审核时间已过！");
				return result;
			}
		}
			try 
			{
				conn = DAO.getConnection();
				Vector<CapitalValue> list = DAO.getCapitalInfoList("where actionid="+actionID, conn);//获取资金流水对象（包括出金和出金手续费流水）
				for (int i = 0; i < list.size(); i++) 
				{
					CapitalValue val = (CapitalValue)list.get(i);
					if(val.type == OUTMONEY)
					{
						capital = val;//出金流水
					}
					else if(val.type == RATE)
					{
						rate = val;//手续费流水
					}
				}
				
				if(capital!=null && rate!=null)
				{
					long number = tradeDate(capital.bankID);
					if(number!=0){
						return number;
					}
					//取得出金付款信息
					TransferInfoValue payInfo = getPayInfo(capital.bankID, capital.firmID,OUTMONEY, conn);
					//取得出金收款方信息
					TransferInfoValue receiveInfo=getReceiveInfo(capital.bankID, capital.firmID,OUTMONEY, conn);
					BankValue bv = DAO.getBank(capital.bankID);
					OutMoneyVO outMoneyInfo=new OutMoneyVO(capital.bankID,bv.bankName,capital.money,capital.firmID,payInfo,receiveInfo,actionID,capital.express,capital.funID);
					
					if(funFlag) {//审核通过
						if(result == 0) {
							try {
								//调用适配器出金接口
								if("market_out".equals(capital.note)||"market_out_Audit_1".equals(capital.note)) {
									//取得适配器对象
									BankAdapterRMI bankadapter = getAdapter(capital.bankID);
									if(bankadapter == null){
										result = ErrorCode.GetAdapter_Error;
										log("处理器连接适配器["+capital.bankID+"]失败");
										return result;
									}else{
										try{
											conn.setAutoCommit(false);
											Vector<CapitalValue> ll = DAO.getCapitalInfoList("where actionID='"+actionID+"' and (status=4 or status=3) for update ",conn);
											if(ll == null || ll.size() <= 0){
												result = ErrorCode.outMoneyAudit_OradyAudite;
												log("信息已在处理中");
												return result;
											}
											DAO.modCapitalInfoStatus(capital.iD,capital.funID, 2,curTime, conn);
											DAO.modCapitalInfoStatus(rate.iD,rate.funID, 2,curTime, conn);
											conn.commit();
										}catch(SQLException e){
											conn.rollback();
											e.printStackTrace();
											result = ErrorCode.outMoneyAudit_DataBaseException;
											log("修改记录为处理中失败，SQL异常");
											return result;
										}finally{
											conn.setAutoCommit(true);
										}
										//调用适配器市场端出金接口
										rVal = bankadapter.outMoneyMarketDone(outMoneyInfo);
									}
								}
								//如果为银行端出金，则通知银行通过审核
								if("bank_out".equals(capital.note)||"bank_out_Audit_1".equals(capital.note)) {
									try{
										conn.setAutoCommit(false);
										Vector<CapitalValue> ll = DAO.getCapitalInfoList("where actionID='"+actionID+"' and (status=4 or status=3) for update ",conn);
										if(ll == null || ll.size() <= 0){
											result = ErrorCode.outMoneyAudit_OradyAudite;
											log("信息已在处理中");
											return result;
										}
										DAO.modCapitalInfoStatus(capital.iD,capital.funID, 2,curTime, conn);
										DAO.modCapitalInfoStatus(rate.iD,rate.funID, 2,curTime, conn);
										conn.commit();
									}catch(SQLException e){
										conn.rollback();
										e.printStackTrace();
										result = ErrorCode.outMoneyAudit_DataBaseException;
										log("修改记录为处理中失败，SQL异常");
										return result;
									}finally{
										conn.setAutoCommit(true);
									}
									System.out.println("[流水信息：]"+capital.toString());
									rVal = new ReturnValue();
									rVal.result=0;
									rVal.actionId=capital.actionID;
									rVal.funID=capital.funID;
								}
								//启动数据库事务
								conn.setAutoCommit(false);
								//如果银行端处理失败则进行市场端数据(数据库数据，内存数据)回滚
								if(rVal.result == 0) {
									dataProcess.updateFundsFull(capital.firmID, dataProcess.getOUTSUMMARY()+"", dataProcess.getBANKSUB().get(capital.bankID), capital.money, actionID,conn);//扣除出金
									dataProcess.updateFundsFull(capital.firmID, dataProcess.getFEESUMMARY()+"", null, rate.money,actionID, conn);//扣除手续费
									dataProcess.updateFrozenFunds(capital.firmID, -1*Arith.add(capital.money, rate.money), conn);//资金解冻
									//修改交易商银行接口冻结资金
									this.bankFrozenFuns(capital.firmID, capital.bankID, null, -1*Arith.add(capital.money, rate.money), conn);
									
									DAO.modCapitalInfoStatus(capital.iD,rVal.funID, 0,curTime, conn);
									DAO.modCapitalInfoStatus(rate.iD,rVal.funID, 0,curTime, conn);
									
									DAO.modCapitalInfoNote(capital.iD, "["+capital.note+"]审核通过，银行处理成功，出金成功", conn);
									DAO.modCapitalInfoNote(rate.iD, "["+capital.note+"]审核通过，银行处理成功，扣除手续费成功", conn);
									
									log(auditInfo+"审核通过，银行处理成功,出金成功,扣除手续费成功,银行流水号="+rVal.funID);
								} else if(rVal.result == 5) {//状态修改为rVal.result：发送给银行 处理过程中
									result = rVal.result;
									DAO.modCapitalInfoStatus(capital.iD,rVal.funID, 2,curTime, conn);
									DAO.modCapitalInfoStatus(rate.iD, rVal.funID,2,curTime, conn);
									DAO.modCapitalInfoNote(capital.iD, "["+capital.note+"]审核通过，银行处理成功，等待处理中", conn);
									DAO.modCapitalInfoNote(rate.iD, "["+capital.note+"]审核通过，银行处理成功，等待处理中", conn);

									log(auditInfo+"审核通过，银行处理成功，等待处理中");
								} else if(rVal.result == ErrorCode.bankNull) {//状态修改为rVal.result：发送给银行 处理过程中
									result = rVal.result;
									DAO.modCapitalInfoStatus(capital.iD,rVal.funID, 5,curTime, conn);
									DAO.modCapitalInfoStatus(rate.iD, rVal.funID,5,curTime, conn);
									DAO.modCapitalInfoNote(capital.iD, "["+capital.note+"]审核通过，银行无返回", conn);
									DAO.modCapitalInfoNote(rate.iD, "["+capital.note+"]审核通过，银行无返回", conn);

									log(auditInfo+"审核通过，银行无返回");
								} else if(rVal.result == ErrorCode.ActionID_Error) {//状态修改为rVal.result：发送给银行 处理过程中
									result = rVal.result;
									DAO.modCapitalInfoStatus(capital.iD,rVal.funID, 6,curTime, conn);
									DAO.modCapitalInfoStatus(rate.iD, rVal.funID,6,curTime, conn);
									DAO.modCapitalInfoNote(capital.iD, "["+capital.note+"]审核通过，银行返回的市场流水号和市场本身流水不一致", conn);
									DAO.modCapitalInfoNote(rate.iD, "["+capital.note+"]审核通过，银行返回的市场流水号和市场本身流水不一致", conn);

									log(auditInfo+"审核通过，银行返回的市场流水号和市场本身流水不一致");
								} else {
									result =rVal.result;	//发送给银行过程中处理失败
									if(!"false".equalsIgnoreCase(getConfig("OutFailProcess"))) {//失败后是否进行退资金操作
										dataProcess.updateFrozenFunds(capital.firmID, -1*Arith.add(capital.money, rate.money), conn);//资金解冻
										//修改交易商银行接口冻结资金
										this.bankFrozenFuns(capital.firmID, capital.bankID, null, -1*Arith.add(capital.money, rate.money), conn);
										DAO.modCapitalInfoStatus(capital.iD,rVal.funID, 1,curTime, conn);
										DAO.modCapitalInfoStatus(rate.iD,rVal.funID, 1,curTime, conn);
										DAO.modCapitalInfoNote(capital.iD,"["+capital.note+"]审核通过，银行处理失败，出金金额已解冻", conn);
										DAO.modCapitalInfoNote(rate.iD, "["+capital.note+"]审核通过，银行处理失败，手续费金额已解冻", conn);
										log(auditInfo+"审核通过，银行处理失败,退还全部出金和手续费，错误码="+result);
									} else {//状态修改为1：发送给银行过程中处理失败
										DAO.modCapitalInfoStatus(capital.iD,rVal.funID, 1,curTime, conn);
										DAO.modCapitalInfoStatus(rate.iD, rVal.funID,1,curTime, conn);
										DAO.modCapitalInfoNote(capital.iD,"["+capital.note+"]审核通过，银行处理失败，需手工解冻出金金额", conn);
										DAO.modCapitalInfoNote(rate.iD, "["+capital.note+"]审核通过，银行处理失败，需手工解冻手续费金额", conn);

										log(auditInfo+"审核通过，银行处理失败，需手工解冻出金和手续费，错误码="+result);
									}
								}
								conn.commit();
							} catch(SQLException e) {
								e.printStackTrace();
								conn.rollback();
								result = ErrorCode.outMoneyAudit_PassDataBaseException;
								log(auditInfo+"审核出金SQLException，数据回滚，需要手工处理出金和手续费，错误码="+result+"  "+e);
							} finally {
								conn.setAutoCommit(true);
							}	
						}
	
						/**如果调用交易系统接口失败则将转帐模块资金流水记录置为失败状态*/
						if(result == ErrorCode.outMoneyAudit_PassDataBaseException) {//流水状态修改为1：审核过程中出现异常
							DAO.modCapitalInfoStatus(capital.iD,capital.funID, 1,curTime, conn);
							DAO.modCapitalInfoStatus(rate.iD, capital.funID,1,curTime, conn);
							DAO.modCapitalInfoNote(capital.iD, "["+capital.note+"]审核通过，系统异常，需手工处理出金", conn);
							DAO.modCapitalInfoNote(rate.iD, "["+capital.note+"]审核通过，系统异常，需手工处理手续费", conn);
						}
					} else {//拒绝出金处理
						try {//如果为银行端出金，则通知银行未通过审核
							if("bank_out".equals(capital.note)||"bank_out_Audit_1".equals(capital.note)) {
								//取得适配器对象
								BankAdapterRMI bankadapter = getAdapter(capital.bankID);
								if(bankadapter == null){
									result = ErrorCode.outMoney_SysException;
									log("处理器连接适配器["+capital.bankID+"]失败");
									return result;
								}else{
									try{
										conn.setAutoCommit(false);
										Vector<CapitalValue> ll = DAO.getCapitalInfoList("where actionID='"+actionID+"' and (status=4 or status=3) for update ",conn);
										if(ll == null || ll.size() <= 0){
											result = ErrorCode.outMoneyAudit_OradyAudite;
											log("信息已在处理中");
											return result;
										}
										DAO.modCapitalInfoStatus(capital.iD,capital.funID, 2,curTime, conn);
										DAO.modCapitalInfoStatus(rate.iD,rate.funID, 2,curTime, conn);
										conn.commit();
									}catch(SQLException e){
										conn.rollback();
										e.printStackTrace();
										result = ErrorCode.outMoneyAudit_DataBaseException;
										log("修改记录为处理中失败，SQL异常");
										return result;
									}finally{
										conn.setAutoCommit(true);
									}
									//调用适配器银行端出金接口
									rVal = bankadapter.outMoneyBackBank(outMoneyInfo, false);
								}
							} else {
								try{
									conn.setAutoCommit(false);
									Vector<CapitalValue> ll = DAO.getCapitalInfoList("where actionID='"+actionID+"' and (status=4 or status=3) for update ",conn);
									if(ll == null || ll.size() <= 0){
										result = ErrorCode.outMoneyAudit_OradyAudite;
										log("信息已在处理中");
										return result;
									}
									DAO.modCapitalInfoStatus(capital.iD,capital.funID, 2,curTime, conn);
									DAO.modCapitalInfoStatus(rate.iD,rate.funID, 2,curTime, conn);
									conn.commit();
								}catch(SQLException e){
									conn.rollback();
									e.printStackTrace();
									result = ErrorCode.outMoneyAudit_DataBaseException;
									log("修改记录为处理中失败，SQL异常");
									return result;
								}finally{
									conn.setAutoCommit(true);
								}
								rVal = new ReturnValue();
								rVal.result = 0;
							}
							//启动数据库事务
							conn.setAutoCommit(false);
							if(rVal.result==0){
								//调用交易系统接口
								dataProcess.updateFrozenFunds(capital.firmID, -1*Arith.add(capital.money, rate.money), conn);//资金解冻
								//修改交易商银行接口冻结资金
								this.bankFrozenFuns(capital.firmID, capital.bankID, null, -1*Arith.add(capital.money, rate.money), conn);
								DAO.modCapitalInfoStatus(capital.iD,rVal==null?"":rVal.funID, 1,curTime, conn);
								DAO.modCapitalInfoStatus(rate.iD, rVal==null?"":rVal.funID,1,curTime, conn);

								DAO.modCapitalInfoNote(capital.iD, "["+capital.note+"]审核拒绝成功", conn);
								DAO.modCapitalInfoNote(rate.iD, "["+capital.note+"]审核拒绝成功", conn);
							}else if(rVal.result==5){
								result = rVal.result;
								this.log("拒绝 "+capital.firmID+" 交易商的银行出金 "+actionID+" ，银行返回处理中");
							}else if(rVal.result==ErrorCode.bankNull){
								result = rVal.result;
								DAO.modCapitalInfoStatus(capital.iD,rVal==null?"":rVal.funID, 5,curTime, conn);
								DAO.modCapitalInfoStatus(rate.iD, rVal==null?"":rVal.funID,5,curTime, conn);

								DAO.modCapitalInfoNote(capital.iD, "["+capital.note+"]审核拒绝，适配器发送给银行，银行无返回", conn);
								DAO.modCapitalInfoNote(rate.iD, "["+capital.note+"]审核拒绝，适配器发送给银行，银行无返回", conn);
							}else if(rVal.result==ErrorCode.ActionID_Error){
								result = rVal.result;
								DAO.modCapitalInfoStatus(capital.iD,rVal==null?"":rVal.funID, 6,curTime, conn);
								DAO.modCapitalInfoStatus(rate.iD, rVal==null?"":rVal.funID,6,curTime, conn);

								DAO.modCapitalInfoNote(capital.iD, "["+capital.note+"]审核拒绝，适配器发送给银行，银行返回市场流水号异常", conn);
								DAO.modCapitalInfoNote(rate.iD, "["+capital.note+"]审核拒绝，适配器发送给银行，银行返回市场流水号异常", conn);
							}else{
								result = rVal.result;
								this.log("拒绝 "+capital.firmID+" 交易商的银行出金 "+actionID+" ，银行处理失败，适配器返回码："+rVal.result);
							}
							log(auditInfo+"审核拒绝成功");
							conn.commit();
						} 
						catch(SQLException e) 
						{
							e.printStackTrace();
							conn.rollback();
							result = ErrorCode.outMoneyAudit_RefuseDataBaseException;
							
							log(auditInfo+"审核拒绝出金SQLException，数据回滚，需要手工处理出金和手续费，错误码="+result+"  "+e);
						}
						finally
						{
							conn.setAutoCommit(true);
						}
						//如果调用交易系统接口失败则将转帐模块资金流水记录置为失败状态
						if(result == ErrorCode.outMoneyAudit_RefuseDataBaseException)
						{//流水状态修改为1：审核员拒绝出金后 退出金出错
							DAO.modCapitalInfoStatus(capital.iD,rVal.funID, 1,curTime, conn);
							DAO.modCapitalInfoStatus(rate.iD, rVal.funID,1,curTime, conn);
							DAO.modCapitalInfoNote(capital.iD, "["+capital.note+"]审核拒绝出金 资金解冻出错，需要手工处理出金", conn);
							DAO.modCapitalInfoNote(rate.iD, "["+capital.note+"]审核拒绝出金 资金解冻出错，需要手工处理手续费", conn);
							log(auditInfo+"审核拒绝出金 资金解冻出错，需要手工处理出金");
						}	
					}
				} else {
					result =ErrorCode.outMoneyAudit_ErrorActionID;
					log(auditInfo+"没有发现业务流水号为："+actionID+"的资金流水");
				}
			} catch(SQLException e) {
				e.printStackTrace();
				result = ErrorCode.checkTrans_DataBaseException;
				log(auditInfo+"审核出金SQLException，错误码="+result+"  "+e);
			} catch(Exception e) {
				e.printStackTrace();
				result = ErrorCode.checkTrans_SysException;
				log(auditInfo+"审核出金Exception，错误码="+result+"  "+e);
			} finally {
				DAO.closeStatement(null, null, conn);
			}
		return result;
	}
	/**
	 * 审核处理中的信息
	 * actionID   流水号，
	 * funFlag    通过、拒绝
	 * type       出入金
	 */
	public long moneyInAudit(long actionID,boolean funFlag){
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("actionID["+actionID+"]funFlag["+funFlag+"]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}}}}}}");
		String auditInfo="审核业务流水号："+actionID+";";
		//数据库连接
		Connection conn = null;

		//当前系统时间
		Timestamp curTime = new Timestamp(System.currentTimeMillis());

		//资金划转流水对象
		CapitalValue capital =null;

		//手续费流水对象
		CapitalValue rate =null;
		
		//适配器返回结果
		ReturnValue rVal = null;

		//返回码
		long result = 0;
		//如果设置了审核时间则判断是否在审核时间内
		if(getConfig("AuditBeginTime")!=null && getConfig("AuditBeginTime").length()>0)
		{
			java.sql.Time AuditBeginTime=Tool.convertTime(getConfig("AuditBeginTime"));
			java.sql.Time curSqlTime=Tool.convertTime(Tool.fmtOnlyTime(curTime));
			if(AuditBeginTime!=null && AuditBeginTime.after(curSqlTime))
			{
				result = ErrorCode.outMoneyAudit_ErrorTimeUn;
				log("审核时间未到！");
				return result;
			}
		}

		//如果设置了审核时间则判断是否在审核时间内
		if(getConfig("AuditEndTime")!=null && getConfig("AuditEndTime").length()>0)
		{
			java.sql.Time AuditEndTime=Tool.convertTime(getConfig("AuditEndTime"));
			java.sql.Time curSqlTime=Tool.convertTime(Tool.fmtOnlyTime(curTime));
			if(AuditEndTime!=null && curSqlTime.after(AuditEndTime))
			{
				result = ErrorCode.outMoneyAudit_ErrorTimeOut;
				log("审核时间已过！");
				return result;
			}
		}
		//synchronized(moneyInAudit){
			try 
			{
				conn = DAO.getConnection();
				Vector<CapitalValue> list = DAO.getCapitalInfoList("where actionid="+actionID, conn);//获取资金流水对象（包括出金和出金手续费流水）
				for (int i = 0; i < list.size(); i++) 
				{
					CapitalValue val = (CapitalValue)list.get(i);
					if(val.type == OUTMONEY || val.type == INMONEY)
					{
						capital = val;//出金流水
						if(capital.status==0 || capital.status==1){
							log("本条记录已经审核"+new java.util.Date());
							result = -3;
							return result;
						}
					}
					else if(val.type == RATE)
					{
						rate = val;//手续费流水
					}
				}
				if(capital != null && rate != null){
					if(funFlag)//审核通过
					{
						if(result == 0)
						{
							try 
							{
								//启动数据库事务
								conn.setAutoCommit(false);
								if(capital.type==OUTMONEY){
									System.out.println("[流水信息：]"+capital.toString());
									rVal = new ReturnValue();
									rVal.result=0;
									rVal.actionId=capital.actionID;
									rVal.funID=capital.funID;

									dataProcess.updateFundsFull(capital.firmID, dataProcess.getOUTSUMMARY()+"", dataProcess.getBANKSUB().get(capital.bankID), capital.money, actionID,conn);//扣除出金
									dataProcess.updateFundsFull(capital.firmID, dataProcess.getFEESUMMARY()+"", null, rate.money,actionID, conn);//扣除手续费
									dataProcess.updateFrozenFunds(capital.firmID, -1*Arith.add(capital.money, rate.money), conn);//资金解冻
									//修改交易商银行接口冻结资金
									this.bankFrozenFuns(capital.firmID, capital.bankID, null, -1*Arith.add(capital.money, rate.money), conn);

									DAO.modCapitalInfoStatus(capital.iD,rVal.funID, 0,curTime, conn);
									DAO.modCapitalInfoStatus(rate.iD,rVal.funID, 0,curTime, conn);
	
									DAO.modCapitalInfoNote(capital.iD, "["+capital.note+"]审核通过，银行处理成功，出金成功", conn);
									DAO.modCapitalInfoNote(rate.iD, "["+capital.note+"]审核通过，银行处理成功，扣除手续费成功", conn);

									log(auditInfo+"审核通过，银行处理成功,出金成功,扣除手续费成功,银行流水号="+rVal.funID);

								}else if(capital.type==INMONEY){
									rVal = new ReturnValue();
									rVal.result=0;
									rVal.actionId=capital.actionID;
									rVal.funID=capital.funID;
									//调用交易系统接口
									dataProcess.updateFundsFull(capital.firmID, dataProcess.getINSUMMARY()+"", dataProcess.getBANKSUB().get(capital.bankID), capital.money,actionID, conn);//添加入金
									dataProcess.updateFundsFull(capital.firmID, dataProcess.getFEESUMMARY()+"", null, rate.money,actionID, conn);//扣除手续费

									DAO.modCapitalInfoStatus(capital.iD,rVal.funID, 0,curTime, conn);
									DAO.modCapitalInfoStatus(rate.iD,rVal.funID, 0, curTime,conn);
								}
								conn.commit();
							}catch(SQLException e){
								conn.rollback();
								e.printStackTrace();
								result = ErrorCode.checkTrans_DataBaseException;
								log(auditInfo+"审核出金SQLException，错误码="+result+"  "+e);
							}
							finally
							{
								conn.setAutoCommit(true);
							}
						}
					}else{//审核拒绝
						try{
							//启动数据库事务
							conn.setAutoCommit(false);
							if(capital.type==OUTMONEY){
								rVal = new ReturnValue();
								rVal.result=0;
								rVal.actionId=capital.actionID;
								rVal.funID=capital.funID;
								//调用交易系统接口
								dataProcess.updateFrozenFunds(capital.firmID, -1*Arith.add(capital.money, rate.money), conn);//资金解冻
								//修改交易商银行接口冻结资金
								this.bankFrozenFuns(capital.firmID, capital.bankID, null, -1*Arith.add(capital.money, rate.money), conn);
								DAO.modCapitalInfoStatus(capital.iD,rVal==null?"":rVal.funID, 1,curTime, conn);
								DAO.modCapitalInfoStatus(rate.iD, rVal==null?"":rVal.funID,1,curTime, conn);

								DAO.modCapitalInfoNote(capital.iD, "["+capital.note+"]审核拒绝成功", conn);
								DAO.modCapitalInfoNote(rate.iD, "["+capital.note+"]审核拒绝成功", conn);

								log(auditInfo+"审核拒绝成功");
							}else if(capital.type == INMONEY){
								rVal = new ReturnValue();
								rVal.result=0;
								rVal.actionId=capital.actionID;
								rVal.funID=capital.funID;
								DAO.modCapitalInfoStatus(capital.iD,rVal==null?"":rVal.funID, 1,curTime, conn);
								DAO.modCapitalInfoStatus(rate.iD,rVal==null?"":rVal.funID, 1,curTime, conn);
							}
							conn.commit();
						}catch(SQLException e){
							conn.rollback();
							e.printStackTrace();
							result = ErrorCode.checkTrans_DataBaseException;
							log(auditInfo+"审核出金SQLException，错误码="+result+"  "+e);
						}finally{
							conn.setAutoCommit(true);
						}
					}
				}
			}catch(SQLException e){
				e.printStackTrace();
				result = ErrorCode.checkTrans_DataBaseException;
				log(auditInfo+"审核出金SQLException，错误码="+result+"  "+e);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}finally{
				DAO.closeStatement(null, null, conn);
			}
		//}
		return result;
	}

	/**
	 * 出金
	 * @param bankID 银行代码
	 * @param money 金额
	 * @param firmID 交易商代码
	 * @param bankAccount 银行帐号
	 * @param funID 银行流水号
	 * @param express 0：正常 1：加急
	 * @param type 0：市场端出金 1：银行端同步出金 2：银行端异步出金 3：银行端出金必须审核
	 * @return long 银行接口业务流水号,返回<0的值表示操作失败                          
	 * @throws 
	 */
	public ReturnValue outMoney(String bankID,double money,String firmID,String bankAccount,String funID,String operator,int express,int type)
	{
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{出金{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("bankID["+bankID+"]money["+money+"]firmID["+firmID+"]bankAccount["+bankAccount+"]funID["+funID+"]operator["+operator+"]express["+express+"]type["+type+"]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}}}}}}");
		//返回结果集
		ReturnValue rv=new ReturnValue();
		long number = tradeDate(bankID);
		if(number!=0){
			rv.result = number;
			rv.remark = ErrorCode.error.get(number);
			return rv;
		}
		//返回码
		long result = 0;
		//资金划转流水号
		long capitalID = 0;
		//手续费划转流水号
		long rateID = 0;
		//数据库连接
		Connection conn = null;
		//当前系统时间
		Timestamp curTime = new Timestamp(System.currentTimeMillis());
		//交易商当前资金
		double realFunds=0;
		//出金手续费
		double outRate = 0;
		/**判断是否签约成功*/
		boolean isopen = (bankAccountIsOpen(firmID,bankID,bankAccount)==1);
		
		/**判断是否超出审核额度标志*/
		boolean notBeyond = true;
		try 
		{
			conn = DAO.getConnection();
			if(isopen)//签约成功状态
			{
				BankValue bVal = DAO.getBank(bankID, conn);
				FirmValue fVal = DAO.getFirm(firmID, conn);
				double auditBalance = bVal.maxAuditMoney;
				if(fVal.maxAuditMoney > 0) auditBalance = fVal.maxAuditMoney;

				if(money > auditBalance && auditBalance>=0)
				{
					notBeyond = false;
				}
			/****接口参数合法性检查方法****** @return 0:合法  <>0:不合法 @throws SQLException ****/

			if(express!=1)
			{
				express=0;
			}
			if (bankAccount==null)
			{
				//查询对应关系
				Vector<CorrespondValue> v = DAO.getCorrespondList(" where 1=1 and bankid='"+bankID+"' and firmid='"+firmID+"'");
				if(v!=null && v.size()>0)
				{
					bankAccount = ((CorrespondValue)v.get(0)).account;
				}
			}
			//取得出金手续费
			outRate = getTransRate(bankID, firmID, money, OUTMONEY,express,bankAccount,conn);
			rv.result = checkArgs(conn,realFunds,outRate,bankID,money,firmID,bankAccount,express,type);
			
			/*********************检查交易商是否符合出金条件,符合条件进行出金处理**************************/
			if(rv.result == 0)
			{//判断是否符合出入金条件 0：符合条件；<>0:不符合转帐条件
				long checkTrans = checkTrans(bankID, firmID, money, curTime, OUTMONEY, conn);
				if(checkTrans == 0)
				{// >0市场业务流水号  -1异常
					HashMap<Integer,Long> map = handleOUtMoney(rv.result,conn,checkTrans,outRate,bankID,money,firmID,funID,express,type);
					result = map.get(1).longValue();
					rv.result=result;
					capitalID = map.get(2).longValue();
					rateID = map.get(3).longValue();
				}
				else
				{
					result = checkTrans;
					rv.result=result;
				}
				
				if(result>0)
				{
					rv.actionId=result;
					try 
					{
						//是否2次审核
//						boolean secondMoneyAudit = "true".equalsIgnoreCase(getConfig("secondMoneyAudit"));
						//启动数据库事务
						conn.setAutoCommit(false);
						realFunds = dataProcess.getRealFunds(firmID, conn);
						if(realFunds<Arith.add(money,outRate)){
							throw new SQLException("冻结资金时，交易商资金不足！！！");
						}
						//调用交易系统接口
						dataProcess.updateFrozenFunds(firmID, Arith.add(money,outRate), conn);
						//银行接口表冻结资金
						this.bankFrozenFuns(firmID, bankID, bankAccount, Arith.add(money,outRate), conn);
						DAO.modCapitalInfoStatus(capitalID,funID==null?"":funID, 3,curTime, conn);
						DAO.modCapitalInfoStatus(rateID, funID==null?"":funID,3,curTime, conn);
						rv.type=3;
						DAO.modCapitalInfoNote(capitalID, type==0?"market_out":"bank_out", conn);
						DAO.modCapitalInfoNote(rateID, type==0?"market_out":"bank_out", conn);
						conn.commit();
					}
					catch(SQLException e) 
					{
						e.printStackTrace();
						conn.rollback();
						rv.result = ErrorCode.outMoney_DataBaseException;
						log("市场发起出金SQLException，数据回滚，错误码="+rv.result+"  "+e);
					}
					finally
					{
						conn.setAutoCommit(true);
					}				

					//如果调用交易系统接口失败则将转帐模块资金流水记录置为失败状态
					if(rv.result == ErrorCode.outMoney_DataBaseException)
					{
						DAO.modCapitalInfoStatus(capitalID,funID==null?"":funID, 1,curTime, conn);
						DAO.modCapitalInfoStatus(rateID,funID==null?"":funID, 1,curTime, conn);
						rv.type=1;
					}
					else
					{//从配置文件中读取是否自动审核
						if(getConfig("AutoAudit")!=null && getConfig("AutoAudit").equalsIgnoreCase("True") && 3 != type)
						{
							rv.result = outMoneyAutoAudit(rv.actionId, true);
							if(rv.result>=0){
								rv.type = 0;
							}else{
								rv.type = 1;
							}
						}
						else
						{
							if(notBeyond && 3 != type)//审核额度内,不需手工审核
							{
								long auditResult=outMoneyAutoAudit(rv.actionId, true);
								rv.result=auditResult;
								if(rv.result>=0){
									rv.type = 0;
								}else{
									rv.type = 1;
								}
							}
							else
							{//超出审核额度，需进行手工审核
								if(type==1)
								{/** 交通银行定制 银行出金超出审核额度直接拒绝 */
									try
									{
										conn.setAutoCommit(false);
										
										dataProcess.updateFrozenFunds(firmID, -1*Arith.add(money,outRate), conn);//资金解冻
										//修改交易商银行接口冻结资金
										this.bankFrozenFuns(firmID, bankID, null, -1*Arith.add(money,outRate), conn);
										DAO.modCapitalInfoStatus(capitalID,funID==null?"":funID, 1,curTime, conn);
										DAO.modCapitalInfoStatus(rateID, funID==null?"":funID,1,curTime, conn);
										rv.type=1;
										DAO.modCapitalInfoNote(capitalID, "[bank_out]出金超出审核额度，出金拒绝", conn);
										DAO.modCapitalInfoNote(rateID, "[bank_out]出金超出审核额度，出金拒绝", conn);
										rv.result = ErrorCode.outMoneyB_successRefused;
										conn.commit();
									} 
									catch(SQLException e) 
									{
										e.printStackTrace();
										conn.rollback();
										rv.result = ErrorCode.outMoney_DataBaseException;
										log("市场发起出金SQLException，数据回滚，错误码="+rv.result+"  "+e);
									}
									finally
									{
										conn.setAutoCommit(true);
									}
//									如果调用交易系统接口失败则将转帐模块资金流水记录置为失败状态
									if(rv.result == ErrorCode.outMoneyAudit_RefuseDataBaseException)
									{
										//流水状态修改为1：审核员拒绝出金后 退出金出错
										DAO.modCapitalInfoStatus(capitalID,funID==null?"":funID, 1,curTime, conn);
										DAO.modCapitalInfoStatus(rateID, funID==null?"":funID,1,curTime, conn);
										rv.type=1;
										DAO.modCapitalInfoNote(capitalID, "出金超过审核额度，拒绝出金,资金解冻出错，需要手工处理出金", conn);
										DAO.modCapitalInfoNote(rateID, "出金超过审核额度，拒绝出金,资金解冻出错，需要手工处理手续费", conn);
									}
								} else if(type==0){
									rv.result=ErrorCode.checkTrans_ErrorSingleMaxMoney;
									rv.remark="超出审核额度，需要市场审核";
								}
							}
							
						}
					}
				}
			}

			/********************************************************************/
			}
			else
			{
				rv.result = ErrorCode.outMoneyM_FirmNO;
				log("市场发起出金，交易商未签约。");
			}
		} 
		catch(SQLException e) 
		{
			e.printStackTrace();
			rv.result =ErrorCode.outMoney_DataBaseException;
			log("市场发起出金SQLException，错误码="+rv.result+"  "+e);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			rv.result = ErrorCode.outMoney_SysException;
			log("市场发起出金Exception，错误码="+rv.result+"  "+e);
			try {
				//把记录更新为失败
				DAO.modCapitalInfoStatus(capitalID,funID==null?"":funID, 1,curTime, conn);
				DAO.modCapitalInfoStatus(rateID, funID==null?"":funID,1,curTime, conn);	
				rv.type=1;
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		finally
		{
			DAO.closeStatement(null, null, conn);
		}
		return rv;
	}

	/**
	 * 划转资金
	 * @param bankID 银行代码
	 * @param money 金额
	 * @param type 资金划转类型 0：划转交易手续费
	 * @param operator 资金划转发起端
	 * @param firmID 交易商代码
	 * @return long 银行接口业务流水号 <0的值表示操作失败
	 * @throws 
	 */
	public long transferMoney(String bankID , int type , double money , String operator ,String firmID)
	{
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("划转资金");
		System.out.println("bankID["+bankID+"]type["+type+"]money["+money+"]operator["+operator+"]firmID["+firmID+"]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}}}}");
		long result = -1;
		try 
		{
			TransMnyObjValue transObj = DAO.getTransMnyObj(type);
			TransMoney transMny = (TransMoney)Class.forName("gnnt.trade.bank." + transObj.className).newInstance();
			result = transMny.tranfer(bankID, money, operator, firmID, this);
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{

		}

		return result;
	}

	/**
	 * 银行帐号注册
	 * @param correspondValue 交易商和银行对应关系
	 * @return  long 操作结果：0 成功  非0 失败(-40001;:信息不完整 -40002：交易商不存在 -40003：银行不存在  -40004：帐号已注册 -40005:银行签约失败 -40006：数据库操作失败 -40007：系统异常 -40008:交易商密码错误
	 */
	public long rgstAccount(CorrespondValue correspondValue)
	{
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("银行帐号注册");
		System.out.println("correspondValue["+correspondValue+"]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}}}}}}");
		long result = 0;
		String defaultAccount=getConfig("DefaultAccount");//特殊定制的银行账号
		Connection conn = null;
		if(correspondValue.bankID==null || correspondValue.bankID.length()==0 ||
				correspondValue.firmID==null || correspondValue.firmID.length()==0 ||
				correspondValue.account==null || correspondValue.account.length()==0
		){
			return ErrorCode.rgstAccount_InfoHalfbaked;
		}

		try 
		{
			conn = DAO.getConnection();

			if(DAO.getFirm(correspondValue.firmID, conn) == null)
			{
				//交易商不存在
				result =ErrorCode.rgstAccount_NOFirm;
				log("银行帐号注册，交易商不存在，错误码="+result);
			}
			else if(DAO.getBank(correspondValue.bankID, conn) == null)
			{
				//银行不存在
				result = ErrorCode.rgstAccount_NOBank;
				log("银行帐号注册，银行不存在，错误码="+result);
			}
			else if(DAO.getCorrespond(correspondValue.bankID, correspondValue.firmID, correspondValue.account, conn) != null)
			{
				//帐号已注册
				result = ErrorCode.rgstAccount_GRSAcount;
				log("银行帐号注册，帐号已注册，错误码="+result);
			}
//			else if(DAO.getCorrespondList(" where bankID='"+correspondValue.bankID+"' and firmID='"+correspondValue.firmID+"' and account='"+correspondValue.account+"' and isopen=0",conn).size()>0)//20091021
			else if(DAO.getCorrespondList(" where bankID='"+correspondValue.bankID+"' and firmID='"+correspondValue.firmID+"' and (account='"+correspondValue.account+"' or account='"+defaultAccount+"') and isopen=0",conn).size()>0)
			{//如果满足bankId,firmId,account或account=9999999999999 则给他注册
				BankAdapterRMI bankadapter = getAdapter(correspondValue.bankID);
				ReturnValue returnValue=bankadapter.rgstAccountQuery(correspondValue);
				if(returnValue==null || returnValue.result==0)
				{
					if(correspondValue.bankID.equals("017")||correspondValue.bankID.equals("17")){
						correspondValue.account1=returnValue.remark;
					}
					//满足注册条件，修改为签约状态
					correspondValue.isOpen = 1;
					DAO.modCorrespond(correspondValue, conn);
					log("满足注册条件，修改为签约状态"+result);
				}
				else
				{
					this.log(returnValue.remark);
					//银行签约失败
					result = ErrorCode.rgstAccount_BankRgsFail;
					log("银行帐号注册，银行签约失败，错误码="+result);
				}
			}
			else if(DAO.getCorrespondList(" where bankID='"+correspondValue.bankID+"' and firmID='"+correspondValue.firmID+"' and account='"+correspondValue.account+"' and status=1 and isopen=1",conn).size()>0)////20091021
//			else if(DAO.getCorrespondList(" where bankID='"+correspondValue.bankID+"' and firmID='"+correspondValue.firmID+"' and status=1 and isopen=1",conn).size()>0)
			{
				DAO.modCorrespond(correspondValue, conn);
			}
			else
			{//添加帐户
				if(DAO.getCorrespondList(" where bankID='"+correspondValue.bankID+"' and firmID='"+correspondValue.firmID+"' and account='"+correspondValue.account+"'",conn).size()<=0){//20091021
//				if(DAO.getCorrespondList(" where bankID='"+correspondValue.bankID+"' and firmID='"+correspondValue.firmID+"'",conn).size()<=0){
					log("添加交易商");
//					if(DAO.getCorrespondList(" where Card='"+correspondValue.card+"' and firmid not like '%D%' ").size()>0){
//						log("该证件已注册，不允许重复使用");
//						result=ErrorCode.rgstAccount_cardHaveExist;
//					}else{
						DAO.addCorrespond(correspondValue, conn);
//					}
//					
				}else{
					log("帐号已存在");
				}
			}
		} 
		catch(SQLException e) 
		{
			e.printStackTrace();
			result = ErrorCode.rgstAccount_DatabaseException;
			log("银行帐号注册SQLException，错误码="+result+"  "+e);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			result = ErrorCode.rgstAccount_SysException;
			log("银行帐号注册Exception，错误码="+result+"  "+e);
		}
		finally
		{
			DAO.closeStatement(null, null, conn);
		}

		return result;
	}
	
	
	/**
	 * 银行帐号注销
	 * @param bankID 银行代码
	 * @param firmID 交易商代码
	 * @param bankAccount 银行帐号
	 * @return int 操作结果：0 成功  非0 失败(1:信息不完整 2：帐号未注册 3：银行解约失败 4：数据库操作失败 5：系统异常)
	 * @throws 
	 */
	public long delAccount(CorrespondValue correspondValue)
	{
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("银行帐号注销");
		System.out.println("correspondValue["+correspondValue+"]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}}}}}");
		long result = 0;
		Connection conn = null;
		if(correspondValue.bankID==null || correspondValue.bankID.length()==0 || correspondValue.firmID==null || correspondValue.firmID.length()==0 || correspondValue.account==null || correspondValue.account.length()==0 ){
			return ErrorCode.delAccount_InfoHalfbaked;
		}
		try 
		{
			conn = DAO.getConnection();
			Vector<CorrespondValue> vector =DAO.getCorrespondList(" where bankID='"+correspondValue.bankID+"' and firmID='"+correspondValue.firmID+"' and account='"+correspondValue.account+"'",conn);
			if(vector==null || vector.size()<=0)
			{
				//帐号未注册
				result = ErrorCode.delAccount_NORgsAcount;
				log("银行帐号注销，帐号未注册，错误码="+result);
			}
			else 
			{
				CorrespondValue cv = vector.get(0);
				if(cv.frozenFuns>0){
					return ErrorCode.delAccount_FrozenFuns;
				}else {
//					DAO.delCorrespond(correspondValue, conn);
					DAO.closeCorrespond(correspondValue.bankID, correspondValue.firmID, correspondValue.card, conn);
				}
			}
		} 
		catch(SQLException e) 
		{
			e.printStackTrace();
			result = ErrorCode.delAccount_DatabaseException;
			log("银行帐号注销SQLException，错误码="+result+"  "+e);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			result = ErrorCode.delAccount_SysException;
			log("银行帐号注销Exception，错误码="+result+"  "+e);
		}
		finally
		{
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}
	/**
	 * 银行帐号注销
	 * @param bankID 银行代码
	 * @param firmID 交易商代码
	 * @param bankAccount 银行帐号
	 * @return int 操作结果：0 成功  非0 失败(1:信息不完整 2：帐号未注册 3：银行解约失败 4：数据库操作失败 5：系统异常)
	 * @throws 
	 */
	public long delAccountMaket(CorrespondValue correspondValue)
	{
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("银行帐号注销");
		System.out.println("correspondValue["+correspondValue.toString()+"]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}}}}}");
		long result = 0;
		Connection conn = null;
		if(correspondValue.bankID==null || correspondValue.bankID.length()==0 ||
				correspondValue.firmID==null || correspondValue.firmID.length()==0 ||
				correspondValue.account==null || correspondValue.account.length()==0
		){
			return ErrorCode.delAccount_InfoHalfbaked;
		}
		try 
		{
			conn = DAO.getConnection();
			Vector<CorrespondValue> vector =DAO.getCorrespondList(" where bankID='"+correspondValue.bankID+"' and firmID='"+correspondValue.firmID+"' and account='"+correspondValue.account+"'",conn);
			if(vector==null || vector.size()<=0)
			{
				//帐号未注册
				result = ErrorCode.delAccount_NORgsAcount;
				log("银行帐号注销，帐号未注册，错误码="+result);
			}
			else 
			{
				CorrespondValue cv = vector.get(0);
				if(cv.frozenFuns>0){
					return ErrorCode.delAccount_FrozenFuns;
				}else if(cv.isOpen==1){
					//适配器对象
					BankAdapterRMI bankadapter = getAdapter(correspondValue.bankID);
					ReturnValue returnValue=null;
					if(bankadapter!=null){
						returnValue=bankadapter.delAccountQuery(correspondValue);
					}
					if(returnValue!=null && returnValue.result==0){
						//满足注销条件，注销交易商银行账号
						DAO.delCorrespond(correspondValue, conn);
					}else{
						//银行解约失败
						this.log(returnValue.remark);
						result = ErrorCode.delAccount_BankDelFail;
						log("银行帐号注销，银行解约失败，错误码="+result);
					}
				}else{
					DAO.delCorrespond(correspondValue, conn);
				}
			}
		} 
		catch(SQLException e) 
		{
			e.printStackTrace();
			result = ErrorCode.delAccount_DatabaseException;
			log("银行帐号注销SQLException，错误码="+result+"  "+e);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			result = ErrorCode.delAccount_SysException;
			log("银行帐号注销Exception，错误码="+result+"  "+e);
		}
		finally
		{
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}
	/**
	 * 给银行发送数据
	 * @param bankID 银行ID 如果为null则调用所有适配器的发送数据方法
	 * @param date 发送数据日期
	 * @return int 操作结果：0 成功  非0失败  1:没有找到bankID对应的适配器 2:交易系统尚未结算完毕 3:异常 负数：出错的适配器数量
	 */
	public int setMoneyInfo(String bankID,java.util.Date date)
	{
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("给银行发送数据");
		System.out.println("bankID["+bankID+"]date["+date+"]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}}}}}");
		boolean traderStatus=false;//交易系统结算状态状态
		int response = 2;
		Connection conn = null;
		if(date==null)
		{
			date=new java.util.Date();
		}
		try 
		{
			conn = DAO.getConnection();
			//TradeData tradeData=new TradeData();
			traderStatus=DAO.getTraderStatus();
			if(traderStatus)//结算完成
			{
				//适配器对象
				if(bankID==null)
				{
					BankAdapterRMI bankadapter = getAdapter(bankID);
					if(bankadapter!=null)
					{
						response = bankadapter.setBankMoneyInfo(date);
					}
					else
					{
						response = 1;
					}
				}
				else
				{
					Vector<BankValue> bankList = DAO.getBankList(" where validFlag=0 ");
					for(int i=0;i<bankList.size();i++)
					{
						BankValue bv = (BankValue)bankList.get(i);
						BankAdapterRMI bankadapter = getAdapter(bv.bankID);
						if(bankadapter!=null){
							response = bankadapter.setBankMoneyInfo(date);
						}
						if(response != 0)
						{
							response = response - 1;
						}
					}
				}
				
			}
		}catch(SQLException e){
			response = 3;
			e.printStackTrace();
			log("给银行发送数据SQLException:"+e);
		}catch(Exception e){
			response = 3;
			e.printStackTrace();
			log("给银行发送数据Exception:"+e);
		}finally{
			DAO.closeStatement(null, null, conn);
		}
		return response;
	}
	
	
	/**
	 * 给银行发送数据 以HashTable方式传
	 * @param bankID 银行ID 如果为null则调用所有适配器的发送数据方法
	 * @param date 发送数据日期
	 * @return int 操作结果：0 成功  负数失败  1:没有找到bankID对应的适配器 2:交易系统尚未结算完毕 3:异常 >100：(int-100)出错的适配器数量
	 */
	public long setMoneyInfoByHashtable(String bankID,Hashtable<String, TradeResultValue> ht)
	{
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("bankID["+bankID+"]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}}}}}");
		boolean traderStatus=false;//交易系统结算状态状态
		ReturnValue returnValue = null;
		long response = 2;
		int amount = 100;
		Connection conn = null;
		try 
		{
			conn = DAO.getConnection();
			//TradeData tradeData=new TradeData();
			traderStatus=DAO.getTraderStatus();
			if(traderStatus)//结算完成
			{
				//适配器对象
				if(bankID==null)
				{
					BankAdapterRMI bankadapter = getAdapter(bankID);
					if(bankadapter!=null)
					{
						returnValue = bankadapter.setBankMoneyInfo(ht);
					}
					else
					{
						response = 1;
					}
				}
				else
				{
					Vector<BankValue> bankList = DAO.getBankList(" where validFlag=0 ");
					for(int i=0;i<bankList.size();i++)
					{
						BankValue bv = (BankValue)bankList.get(i);
						BankAdapterRMI bankadapter = getAdapter(bv.bankID);
						if(bankadapter!=null) {
							returnValue = bankadapter.setBankMoneyInfo(ht);
						}
						if(returnValue.result != 0)
						{
							amount = amount + 1;
						}
					}
				}
				
			}
		} 
		catch(SQLException e) 
		{
			response = 3;
			e.printStackTrace();
			log("给银行发送数据SQLException:"+e);
		}
		catch(Exception e)
		{
			response = 3;
			e.printStackTrace();
			log("给银行发送数据Exception:"+e);
		}
		finally
		{
			DAO.closeStatement(null, null, conn);
		}
		
		if(returnValue!=null && amount==100)
		{
			response = returnValue.result;
		}
		else
		{
			response = amount;
		}
		return response;
	}

	/**
	 * 给银行发送数据
	 * @param bankID 银行ID 如果为null则调用所有适配器的发送数据方法
	 * @param date 发送数据日期
	 * @param moduleid 板块号
	 * @return int 操作结果：0 成功  非0失败  1:没有找到bankID对应的适配器 2:交易系统尚未结算完毕 3:异常 负数：出错的适配器数量
	 */
	public long setMoneyInfoAutoOrNo(String bankID,java.util.Date date,int moduleid)
	{
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("给银行发送数据");
		System.out.println("bankID["+bankID+"]date["+date+"]moduleid["+moduleid+"]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}}}}");
		boolean traderStatus=false;//交易系统结算状态状态
		long response = 0;
		ReturnValue returnValue = null;
		Connection conn = null;
		if(date==null)
		{
			date=new java.util.Date();
		}
		try 
		{
			conn = DAO.getConnection();
			//TradeData tradeData=new TradeData();
			traderStatus=DAO.getTraderStatus();
			if(traderStatus)//结算完成
			{
				//适配器对象
				if(bankID==null)
				{
					BankAdapterRMI bankadapter = getAdapter(bankID);
					if(bankadapter!=null)
					{
						String filter = " 1=1 ";
						if(date!=null) filter = filter+" and f.b_date=to_date('"+date+"','yyyy-MM-dd')";
						Hashtable<String, TradeResultValue> ht = DAO.getTradeDataInHashTable(filter, moduleid);
						returnValue = bankadapter.setBankMoneyInfo(ht);
						response = returnValue.result;
					}
					else
					{
						response = 1;
					}
				}
				else
				{
					Vector<BankValue> bankList = DAO.getBankList(" where validFlag=0 ");
					String filter = " 1=1 ";
					if(date!=null) filter = filter+" and f.b_date=to_date('"+date+"','yyyy-MM-dd')";
					Hashtable<String, TradeResultValue> ht = DAO.getTradeDataInHashTable(filter, moduleid);
					for(int i=0;i<bankList.size();i++)
					{
						BankValue bv = (BankValue)bankList.get(i);
						BankAdapterRMI bankadapter = getAdapter(bv.bankID);
						returnValue = bankadapter.setBankMoneyInfo(ht);
						if(returnValue==null || returnValue.result != 0)
						{
							response = response - 1;//如果给任意一个银行传送数据后返回失败，直接跳出
						}
					}
				}
				
			}
			else
			{
				response = 2;
			}
		} 
		catch(SQLException e) 
		{
			response = 3;
			e.printStackTrace();
			log("给银行发送数据SQLException:"+e);
		}
		catch(Exception e)
		{
			response = 3;
			e.printStackTrace();
			log("给银行发送数据Exception:"+e);
		}
		finally
		{
			DAO.closeStatement(null, null, conn);
		}
		
		return response;
	}
	
	
	/**
	 * 获取银行对账信息
	 * @param bankID  银行ID 如果为null则通知所有适配器取银行数据
	 * @param date 获取数据日期
	 * @return  操作结果：0 成功   -30011:没有找到bankID对应的适配器 -30012:将对账信息插入数据库发生错误（银行代码为空） -30013：将对账信息插入数据库发生错误（银行代码不为空）
	 */
	public int getBankCompareInfo(String bankID,java.util.Date date)
	{
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("获取银行对账信息");
		System.out.println("bankID["+bankID+"]date["+date+"]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}}}}}");
		int result=0;
		if(bankID==null) {  
			Enumeration<String> enumeration = ADAPTERLIST.elements();
			while(enumeration.hasMoreElements()) 
			{
					Vector<MoneyInfoValue> moneyInfoList=null;
				try {
					Vector<DicValue> v = DAO.getDicList(" where bankid='"+bankID+"' order by type,id ");
					BankAdapterRMI bankadapter=getAdapter((String)enumeration.nextElement());
					moneyInfoList = bankadapter.getBankMoneyInfo(date,v);
				} catch (RemoteException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(moneyInfoList!=null) 
				{	
					if(insertBankCompareInfo(moneyInfoList)!=0)
					{
						result=-30012;
						log("获取银行对账信息,错误码="+result);
					}
				}
			}
		}
		else
		{
			BankAdapterRMI bankadapter=getAdapter(bankID);
			if(bankadapter!=null)
			{
				Vector<MoneyInfoValue> moneyInfoList=null;
				try {
					Vector<DicValue> v = DAO.getDicList(" order by type,id ");
					moneyInfoList = bankadapter.getBankMoneyInfo(date,v);
				} catch (RemoteException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(moneyInfoList!=null)
				{
					if(insertBankCompareInfo(moneyInfoList)!=0)
					{
						result=-30013;
						log("获取银行对账信息,错误码="+result);
					}
				}else{
					result=-30013;
					log("获取银行对账信息,错误码="+result);
				}
			}
			else{
				result=-30011;
				log("获取银行对账信息,错误码="+result);
			}
		}
		return result;
	}

	
	/**
	 * 对账检查条件，看是否先对比流水笔数
	 * @return int 0:不对比笔数 1:比对笔数，笔数相等 2：比对笔数，笔数不等 -1:异常
	 * @throws 
	 */
	public int checkMoneyByNumber()
	{
		int result = 0;
		//数据连接
		Connection conn = null;
		try 
		{
			conn = DAO.getConnection();
			//如果比对总笔数，首先读取总笔数
			if(getConfig("compareCapNumber")!=null && getConfig("compareCapNumber").equalsIgnoreCase("true"))
			{
				int sumCapnumber = DAO.countCapitalInfo(null, -1);
				int sumBankCapnumber = DAO.countBankCompareInfo(null, -1);
				
				if(sumCapnumber == sumBankCapnumber)
				{
					result = 1;
				}
				else
				{
					result = 2;
				}
			}
		} 
		catch (SQLException e) {
			result = -1;
			e.printStackTrace();
			log("对账检查SQLException："+e);
		} catch (ClassNotFoundException e) {
			result = -1;
			e.printStackTrace();
			log("对账检查ClassNotFoundException："+e);
		}
		catch(Exception e) 
		{
			result = -1;
			e.printStackTrace();
			log("对账检查Exception："+e);
		}
		finally
		{
			DAO.closeStatement(null, null, conn);
		}
		return result;
	
	}

	/**
	 * 写入银行对账信息
	 * @param list 对账信息，每项为MoneyInfoValue
	 * @return int 操作结果：0 成功  非0 失败: -1对账日期不一致  -2对账信息为空  -3系统异常
	 */
	public int insertBankCompareInfo(Vector<MoneyInfoValue> list)
	{
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("写入银行对账信息");
		System.out.println("list.size()["+list.size()+"]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}}}}}");
		int result = 0;
		Connection conn = null;
		String dateStr = "";
		try 
		{
			//判断对账信息日期是否一致
			if(list != null && list.size()>0)
			{
				if(list.get(0).compareDate!=null)
				{
					dateStr = list.get(0).compareDate.toString();
					String dateStr1 = "";
					for (int i = 1; i < list.size(); i++) 
					{
						if(list.get(i).compareDate!=null)
						{
							dateStr1 = list.get(i).compareDate.toString();
							if(!dateStr1.equals(dateStr))
							{
								result = -1;
								log("写入银行对账信息,对账日期不相符，错误码="+result);
								break;
							}
						}
						else
						{
							result = -1;
							log("写入银行对账信息,对账日期为空，错误码="+result);
							break;
						}
					}
				}
				else
				{
					result = -1;
				}
			}
			else if(list == null)
			{
				result = -2;
			}

			if(result == 0)
			{
				try 
				{
					conn = DAO.getConnection();

					conn.setAutoCommit(false);

					//写入对账信息
					for (int i = 0; i < list.size(); i++)
					{
						MoneyInfoValue moneyInfoValue=list.get(i);
//						检查现有对账信息，如果已有当前的数据则不插入数据
						Vector<MoneyInfoValue> curList = DAO.getMoneyInfoList("where funID='"+moneyInfoValue.id+
								"' and trunc(comparedate)=to_date('"+Tool.fmtDate(moneyInfoValue.compareDate)+"','yyyy-MM-dd') and bankid='"+moneyInfoValue.bankID+"'", conn);
						if(curList.size()==0)
						{
							DAO.addMoneyInfo(moneyInfoValue, conn);
						}	
					}
//					dataSaved = true;
					conn.commit();
				} 
				catch(SQLException e) 
				{
					e.printStackTrace();
					log("写入银行对账信息异常，数据回滚");
					throw e;
				}
				finally
				{
					conn.setAutoCommit(true);
				}

			}
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
			result = -3;
			log("写入银行对账信息异常，错误码="+result);
		}
		finally
		{
			DAO.closeStatement(null, null, conn);
		}	
		return result;
	}


	/**
	 * 根据银行ID获取市场银行信息
	 * @param bankID 银行代码
	 * @return Hashtable<name,value>
	 */
	public Hashtable<String,String> getBankInfo(String bankID)
	{
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("根据银行ID获取市场银行信息");
		System.out.println("bankId["+bankID+"]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}}}}}}");
		Hashtable<String,String> hashTable=new Hashtable<String, String>();
		//数据库连接
		Connection conn = null;

		try 
		{
			conn = DAO.getConnection();
			//取得收款方信息
			Vector<DicValue> list = DAO.getDicList("where type=2 and bankid='"+bankID+"'",conn);
			for (int i = 0; i < list.size(); i++) 
			{
				DicValue val = (DicValue)list.get(i);
				if(val.value == null){
					val.value = "";
				}
				hashTable.put(val.name, val.value);
			}
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}finally{
			DAO.closeStatement(null, null, conn);
		}

		return hashTable;
	}
	/**
	 * 查询交易员保证金余额
	 * @param firmID 交易商ID
	 * @return -1：查询失败 其它：交易商保证金余额
	 * @throws SQLException
	 */
	public double queryFirmBalance(String firmID) 	throws SQLException
	{
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("查询交易员保证金余额");
		System.out.println("firmID["+firmID+"]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}}}}}");
		double result = -1;
		//数据库连接
		Connection conn = null;
		try 
		{
			conn = DAO.getConnection();
			result=dataProcess.getRealFunds(firmID, conn);
		} 
		catch(SQLException e) 
		{
			e.printStackTrace();
			throw e;
		}
		catch(Exception e) 
		{
			e.printStackTrace();
			result = -1;
		}
		finally
		{
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}



	/**
	 * 获取市场资金流水数据
	 * @param filter 
	 * @return 获取资金流水数据
	 */
	public Vector<CapitalValue> getCapitalList(String filter)
	{
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("获取市场资金流水数据");
		System.out.println("filter["+filter+"]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}}}}}}");
		//数据连接
		Connection conn = null;
		Vector<CapitalValue> capitalList=null;
		try 
		{
			conn = DAO.getConnection();
			capitalList = DAO.getCapitalInfoList(filter, conn);
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
			DAO.closeStatement(null, null, conn);
		}
		return  capitalList;
	}

	/**
	 * 获取银行资金流水数据
	 * @param filter 
	 * @return 获取资金流水数据
	 */
	public Vector<BankCompareInfoValue> getBankCapList(String filter)
	{
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("获取银行资金流水数据");
		System.out.println("filter["+filter+"]");
		System.out.println("}}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}}}}}}}");
		//数据连接
		Connection conn = null;
		Vector<BankCompareInfoValue> capitalList=null;
		try 
		{
			conn = DAO.getConnection();
			capitalList = DAO.getBankCapInfoList(filter);
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
			DAO.closeStatement(null, null, conn);
		}
		return  capitalList;
	}
	
	/**
	 * 获取交易商银行对应关系
	 * @param filter 
	 * @return 
	 */
	public Vector<CorrespondValue> getCorrespondValue(String filter) 
	{
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("获取交易商银行对应关系");
		System.out.println("filter["+filter+"]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}}}}}}");
		//数据连接
		Connection conn = null;
		Vector<CorrespondValue> correspondList=null;
		try 
		{
			conn = DAO.getConnection();
			correspondList=DAO.getCorrespondList(filter, conn);
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
			DAO.closeStatement(null, null, conn);
		}
		return  correspondList;
	}
	/**
	 * 获取交易商银行对应关系
	 * @param filter 
	 * @return 
	 */
	public Map<String,CorrespondValue> getCorrespondValue(Vector<String> firmIDs,String bankID) 
	{
		Map<String,CorrespondValue> result = new HashMap<String,CorrespondValue>();
		//数据连接
		Connection conn = null;
		try 
		{
			conn = DAO.getConnection();
			if(firmIDs != null && firmIDs.size()>0){
				for(String firmID : firmIDs){
					Vector<CorrespondValue> correspondList=DAO.getCorrespondList(" where firmid='"+firmID+"' and bankid='"+bankID+"' ", conn);
					if(correspondList != null && correspondList.size()>0){
						CorrespondValue cv = correspondList.get(0);
						result.put(firmID, cv);
					}
				}
			}
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
			DAO.closeStatement(null, null, conn);
		}
		return  result;
	}
	/**
	 * 冲证(银行方调用)
	 * @param actionID  转账模块业务流水号
	 * @param funID 银行业务流水号
	 * @return  操作结果：0 处理成功；-1 参数非法；-2 未找到要冲证的数据；-3 数据处理失败；-4 系统异常
	 */
	public ReturnValue chargeAgainst(ChargeAgainstValue cav){
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{冲证(银行方调用){{{{{{{{{{{{{{{{{{{{{");
		System.out.println("cav["+cav.toString()+"]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}}}}}");
		Connection conn = null;
		String filter = null;
		ReturnValue rv = new ReturnValue();
		rv.result = 0;
		if(cav==null){
			rv.result=ErrorCode.charge_bankNull;
			rv.remark="传入的参数为空";
		}
		//synchronized(chargeAgainst){
			Map<String,FirmBalanceValue> firmMsg = new HashMap<String,FirmBalanceValue>();
			try {
				conn = DAO.getConnection();
				filter = " where bankID='"+cav.bankID+"' and (trunc(createtime)=trunc(sysdate) or trunc(bankTime)=trunc(sysdate)) and status=0 and (type=0 or type=1 or type=2) ";
				if(cav.actionID > 0){
					filter = filter+" and actionID="+cav.actionID;
				}else if(cav.funID!=null && cav.funID.trim().length()>0){
					filter = filter+" and funID='"+cav.funID.trim()+"' ";
				}else{
					rv.result=ErrorCode.charge_noReasion;
					rv.remark = "冲正流水时市场流水号和银行流水号至少选其一";
				}
				if(rv.result == 0){
					Vector<CapitalValue> capList = DAO.getCapitalInfoList(filter, conn);
					if(capList != null && capList.size() > 0){
						conn.setAutoCommit(false);
						FirmBalanceValue fbv =null;
						CapitalValue cv = new CapitalValue();
						cv.actionID = DAO.getActionID(conn);//获取actionID
						cv.bankTime = cav.bankTime;//获取银行时间
						cv.funID = cav.funIDCA;//获取银行流水号
						cv.money = 0;//冲正金额
						cv.bankID = cav.bankID;
						cv.note = "bank_charge 冲正，记录号:";
//						cv.oprcode = dataProcess.getCHSUMMARY()+"";
						for (int i = 0; i < capList.size(); i++){
							CapitalValue capVal = (CapitalValue)capList.get(i);
							fbv=null;
							if(firmMsg.get(capVal.firmID)!=null){
								fbv = firmMsg.get(capVal.firmID);
							}else{
								String filter2 = " where firmid='"+capVal.firmID+"' for update ";
								fbv = DAO.availableBalance(filter2, conn);
								if(fbv!=null){
									if("true".equalsIgnoreCase(Tool.getConfig("fuYing"))){//如果市场记录浮盈
										try{
											BankDAO DAO= BankDAOFactory.getDAO();
											Vector<FirmBalanceValue> floatingloss = DAO.getFlote(new String[]{capVal.firmID});
											if(floatingloss != null && floatingloss.size()>0){
												for(FirmBalanceValue tp : floatingloss){
													if(tp != null && capVal.firmID.equals(tp.firmId)){
														fbv.floatingloss = tp.floatingloss;
														if(tp.floatingloss>0){
															fbv.avilableBalance = fbv.avilableBalance-tp.floatingloss;
														}
														break;
													}
												}
											}
										}catch(SQLException e){
											throw e;
										}catch(Exception e){
											e.printStackTrace();
										}
									}
									firmMsg.put(capVal.firmID, fbv);
								}else{
									rv.result=ErrorCode.charge_noFirm;
									rv.remark = "没有查询到交易商"+capVal.firmID;
									throw new Exception("没有查询到当个交易商"+capVal.firmID);
								}
							}
							if(capVal.type==INMONEY){
								cv.firmID = capVal.firmID;
								cv.money += -1*capVal.money;//冲正入金(减钱)
								cv.debitID = capVal.creditID;
								cv.creditID = capVal.debitID;
								cv.note = cv.note+capVal.iD+" ";
								cv.type = 4;
								capVal.status = -1;
								cv.oprcode = dataProcess.getINSUMMARY()+"";
							}else if(capVal.type==OUTMONEY){
								cv.firmID = capVal.firmID;
								cv.money += capVal.money;//冲正出金,手续费(加钱)
								cv.debitID = capVal.creditID;
								cv.creditID = capVal.debitID;
								cv.note = cv.note+capVal.iD+" ";
								cv.type = 5;
								capVal.status = -1;
								cv.oprcode = dataProcess.getOUTSUMMARY()+"";
							}else if(capVal.type==RATE) {
								cv.money += capVal.money;//冲正出金,手续费(加钱)
								cv.note = cv.note+capVal.iD+" ";
								capVal.status = -1;
							}
							DAO.modCapitalInfoStatus(capVal.iD, capVal.funID, capVal.status, capVal.bankTime, conn);
						}
						if(fbv==null || cv.money+fbv.avilableBalance<0){
							rv.result=ErrorCode.charge_noFirm;
							rv.remark = "没有查询到交易商，或本交易商当前资金不足以冲正";
							throw new Exception("在交易系统的交易商资金表查询不到本交易商");
						}
						DAO.addCapitalInfo(cv, conn);//添加冲正流水
						dataProcess.updateFundsFull(cv.firmID, cv.oprcode, dataProcess.getBANKSUB().get(cv.bankID),((dataProcess.getOUTSUMMARY()+"").equalsIgnoreCase(cv.oprcode)? -cv.money : cv.money), cv.actionID,conn);
						conn.commit();
					}else{
						rv.result=ErrorCode.marketSer_None;
						rv.remark="查询流水不存在：市场流水号["+cav.actionID+"]银行流水号["+cav.funID+"]";
					}
				}
			}catch(SQLException e){
				e.printStackTrace();
				if(rv.result==0){
					rv.result = ErrorCode.charge_SQLException;
					rv.remark = "冲正操作中出现数据库异常";
				}
				try{
				conn.rollback();
				}catch(SQLException e1){
					e1.printStackTrace();
				}
			}catch(Exception e){
				e.printStackTrace();
				if(rv.result==0){
					rv.result = ErrorCode.czSystem_error;
					rv.remark = "冲正操作中出现系统异常";
				}
				try{
				conn.rollback();
				}catch(SQLException e1){
					e1.printStackTrace();
				}
			}finally{
				try{
				conn.setAutoCommit(true);
				}catch(SQLException e){
					e.printStackTrace();
				}
				DAO.closeStatement(null, null, conn);
			}
		//}
		return rv;
	}
	/**
	 * 冲证(市场方调用)
	 * @param actionID  转账模块业务流水号
	 * @param funID 银行业务流水号
	 * @return  操作结果：0 处理成功；-1 参数非法；-2 未找到要冲证的数据；-3 数据处理失败；-4 系统异常
	 */
	public ReturnValue chargeAgainstMaket(ChargeAgainstValue cav){
		System.out.println("{{{{{{{{{{{{{{{{{{{{{冲证(市场方调用){{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("cav["+cav.toString()+"]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}}}}");
		Connection conn = null;
		String filter = null;
		ReturnValue rv = new ReturnValue();
		rv.result = 0;
		if(cav==null){
			rv.result=ErrorCode.charge_bankNull;
			rv.remark="传入的参数为空";
		}
		//synchronized(chargeAgainstMaket){
			Map<String,FirmBalanceValue> firmMsg = new HashMap<String,FirmBalanceValue>();
			try {
				conn = DAO.getConnection();
				filter = " where bankID='"+cav.bankID+"' and (trunc(createtime)=trunc(sysdate) or trunc(bankTime)=trunc(sysdate)) and status=0 and (type=0 or type=1 or type=2) ";
				if(cav.actionID > 0){
					filter = filter+" and actionID="+cav.actionID;
				}else if(cav.funID!=null && cav.funID.trim().length()>0){
					filter = filter+" and funID="+cav.funID.trim();
				}else{
					rv.result=ErrorCode.charge_noReasion;
					rv.remark = "冲正流水时市场流水号和银行流水号至少选其一";
				}
				if(rv.result == 0){
					Vector<CapitalValue> capList = DAO.getCapitalInfoList(filter, conn);
					if(capList != null && capList.size() > 0){
						conn.setAutoCommit(false);
						FirmBalanceValue fbv =null;
						CapitalValue cv = new CapitalValue();
						cv.actionID = DAO.getActionID(conn);//获取actionID
						cv.money = 0;//冲正金额
						cv.bankID = cav.bankID;
						cv.note = "maket_charge冲正，记录号：";
//						cv.oprcode = dataProcess.getCHSUMMARY()+"";
						for (int i = 0; i < capList.size(); i++){
							CapitalValue capVal = (CapitalValue)capList.get(i);
							fbv=null;
							if(firmMsg.get(capVal.firmID)!=null){
								fbv = firmMsg.get(capVal.firmID);
							}else{
								String filter2 = " where firmid='"+capVal.firmID+"' for update ";
								fbv = DAO.availableBalance(filter2, conn);
								if(fbv!=null){
									if("true".equalsIgnoreCase(Tool.getConfig("fuYing"))){//如果市场记录浮盈
										try{
											BankDAO DAO= BankDAOFactory.getDAO();
											Vector<FirmBalanceValue> floatingloss = DAO.getFlote(new String[]{capVal.firmID});
											if(floatingloss != null && floatingloss.size()>0){
												for(FirmBalanceValue tp : floatingloss){
													if(tp != null && capVal.firmID.equals(tp.firmId)){
														fbv.floatingloss = tp.floatingloss;
														if(tp.floatingloss>0){
															fbv.avilableBalance = fbv.avilableBalance-tp.floatingloss;
														}
														break;
													}
												}
											}
										}catch(SQLException e){
											throw e;
										}catch(Exception e){
											e.printStackTrace();
										}
									}
									firmMsg.put(capVal.firmID, fbv);
								}else{
									rv.result=ErrorCode.charge_noFirm;
									rv.remark = "没有查询到交易商"+capVal.firmID;
									throw new Exception("没有查询到当个交易商"+capVal.firmID);
								}
							}
							if(capVal.type==INMONEY){
								cv.firmID = capVal.firmID;
								cv.money += -1*capVal.money;//冲正入金(减钱)
								cv.debitID = capVal.creditID;
								cv.creditID = capVal.debitID;
								cv.note = cv.note+capVal.iD+" ";
								cv.type = 4;
								capVal.status = -1;
								cv.oprcode = dataProcess.getINSUMMARY()+"";
							}else if(capVal.type==OUTMONEY){
								cv.firmID = capVal.firmID;
								cv.money += capVal.money;//冲正出金,手续费(加钱)
								cv.debitID = capVal.creditID;
								cv.creditID = capVal.debitID;
								cv.note = cv.note+capVal.iD+" ";
								cv.type = 5;
								capVal.status = -1;
								cv.oprcode = dataProcess.getOUTSUMMARY()+"";
							}else if(capVal.type==RATE) {
								cv.money += capVal.money;//冲正出金,手续费(加钱)
								cv.note = cv.note+capVal.iD+" ";
								capVal.status = -1;
							}
							DAO.modCapitalInfoStatus(capVal.iD, capVal.funID, capVal.status, capVal.bankTime, conn);
						}
						if(fbv==null || cv.money+fbv.avilableBalance<0){
							rv.result=ErrorCode.charge_noFirm;
							rv.remark = "没有查询到交易商，或本交易商当前资金不足以冲正";
							throw new Exception("在交易系统的交易商资金表查询不到本交易商");
						}
//						BankAdapterRMI bankadapter = this.getAdapter(cv.bankID);
						ReturnValue rvt = null;//调用适配器方法
						if(rvt==null || rvt.result==5 || rvt.result==ErrorCode.bankNull){
							cv.status=2;
							DAO.addCapitalInfo(cv, conn);//添加冲正流水
							if((dataProcess.getINSUMMARY()+"").equalsIgnoreCase(cv.oprcode)){
								dataProcess.updateFundsFull(cv.firmID, cv.oprcode, dataProcess.getBANKSUB().get(cv.bankID),((dataProcess.getOUTSUMMARY()+"").equalsIgnoreCase(cv.oprcode) ? -cv.money : cv.money), cv.actionID,conn);
							}
						}else if(rvt.result==0){
							cv.status=0;
							DAO.addCapitalInfo(cv, conn);//添加冲正流水
							dataProcess.updateFundsFull(cv.firmID, cv.oprcode, dataProcess.getBANKSUB().get(cv.bankID),((dataProcess.getOUTSUMMARY()+"").equalsIgnoreCase(cv.oprcode) ? -cv.money : cv.money), cv.actionID,conn);
						}else if(rvt.result<0){
							cv.status=1;
							DAO.addCapitalInfo(cv, conn);//添加冲正流水
						}
						conn.commit();
					}else{
						rv.result=ErrorCode.marketSer_None;
						rv.remark="查询流水不存在：市场流水号["+cav.actionID+"]银行流水号["+cav.funID+"]";
					}
				}
			}catch(SQLException e){
				e.printStackTrace();
				if(rv.result==0){
					rv.result = ErrorCode.charge_SQLException;
					rv.remark = "冲正操作中出现数据库异常";
				}
				try{
				conn.rollback();
				}catch(SQLException e1){
					e1.printStackTrace();
				}
			}catch(Exception e){
				e.printStackTrace();
				if(rv.result==0){
					rv.result = ErrorCode.czSystem_error;
					rv.remark = "冲正操作中出现系统异常";
				}
				try{
				conn.rollback();
				}catch(SQLException e1){
					e1.printStackTrace();
				}
			}finally{
				try{
				conn.setAutoCommit(true);
				}catch(SQLException e){
					e.printStackTrace();
				}
				DAO.closeStatement(null, null, conn);
			}
		//}
		return rv;
	}
	/**
	 * 取得交易商交易系统资金明细
	 * @param Date 结算日期
	 * @return  Hashtable key 交易商代码；value 交易商资金属性集合（key 资金属性名称；value 资金属性值）
	 */
	public Hashtable<String, Hashtable<String, String>> getFirmTradeBal(Date b_date)
	{
		return dataProcess.getFirmTradeBal(b_date);
	}
	
	/**
	 * 从交易系统同步交易商
	 * @return  void
	 */
	public void synchronizedFirm()
	{
		dataProcess.synchronizedFirm();
	}
	
	/**
	 * 判断交易商用户名口令是否合法
	 * @param firmid
	 * @param password
	 * @return  boolean true 合法；false 非法
	 */
	public boolean checkFirmPwd(String firmid ,String password)
	{
		return true;
	}
	
	/**
	 * 将市场总金额[资金和权益]传给银行对账
	 * @param filter
	 * @param moduleid
	 * @return  Hashtable
	 */
	public long sendTotalMoneyToBank(String bankid,Hashtable<String, Double> ht)
	{
		BankAdapterRMI bankadapter=getAdapter(bankid);
		ReturnValue rv = null;
		if(bankadapter!=null)
		{
			try {
				rv = bankadapter.setTotalMoneyInfo(ht);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		return rv.result;
	}
	
	/**
	 * 查询交易商市场资金
	 * @return  FirmBalanceValue
	 */
	public FirmBalanceValue getMarketBalance(String firmid)
	{
		FirmBalanceValue fbv = null;
		String filter = " where 1=1 ";
		if(firmid!=null)
		{
			filter += "  and firmid='"+firmid+"'  ";
		}
		fbv = DAO.availableBalance(filter);
		if("true".equalsIgnoreCase(this.getConfig("fuYing"))){//如果市场记录浮盈
			try{
				Vector<FirmBalanceValue> floatingloss = DAO.getFlote(new String[]{firmid});
				if(floatingloss != null && floatingloss.size()>0){
					for(FirmBalanceValue fb : floatingloss){
						if(fb != null && firmid.equals(fb.firmId)){
							fbv.floatingloss = fb.floatingloss;
							if(fb.floatingloss>0){
								fbv.avilableBalance = fbv.avilableBalance-fb.floatingloss;
							}
							break;
						}
					}
				}
			}catch(SQLException e){
				e.printStackTrace();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		System.out.println(fbv.toString());
		return fbv;
	}
	/**
	 * 查询交易商银行资金
	 * @return  FirmBalanceValue
	 */
	public FirmBalanceValue getBankBalance(String bankid,String firmid,String pwd)
	{
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("查询交易商银行资金");
		System.out.println("bankid["+bankid+"]firmid["+firmid+"]pwd["+pwd+"]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}}}}");
		FirmBalanceValue fv = new FirmBalanceValue();
		String filter = " where 1=1 ";
		if(firmid!=null)
		{
			filter += "  and firmid='"+firmid+"'  ";
		}
		if(bankid!=null)
		{
			filter += "  and bankid='"+bankid+"'  ";
		}

	  	Vector<CorrespondValue> v = null;
		try {
			v = DAO.getCorrespondList(filter);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		
  		double accountBalance=0;
		if(tradeDate(bankid)==0){
			try {
				CorrespondValue cv = new CorrespondValue();
				if(v.size()>0)
				{
					cv = (CorrespondValue) v.get(0);
				}
				fv.setBankAccount(cv.account);
				BankAdapterRMI bankadapter = getAdapter(bankid);
				accountBalance = bankadapter.accountQuery(cv,pwd);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		fv.setBankBalance(accountBalance);
		
		return fv;
	}
	/**
	 * 查询交易商市场可用资金和银行帐号余额
	 * @return  FirmBalanceValue
	 */
	public FirmBalanceValue getFirmBalance(String bankid,String firmid,String pwd)
	{
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("查询交易商市场可用资金和银行帐号余额");
		System.out.println("bankid["+bankid+"]firmid["+firmid+"]pwd["+pwd+"]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}}}}}");
		FirmBalanceValue fv = new FirmBalanceValue();
		FirmBalanceValue fv1 = getMarketBalance(firmid);
		FirmBalanceValue fv2 = getBankBalance(bankid, firmid, pwd);
		
		fv.setFirmId(firmid);
		fv.setFirmBankId(bankid);
		
		fv.setAvilableBalance(fv1.avilableBalance);
		fv.setFrozenBalance(fv1.frozenBalance);
		fv.setMarketBalance(fv1.marketBalance);
		fv.setLastBalance(fv1.lastBalance);
		
		fv.setBankAccount(fv2.bankAccount);
		fv.setBankBalance(fv2.bankBalance);
		
		return fv;
	}
	/**
	 * 取得银行信息表
	 */
	public BankValue getBank(String bankID){
		BankValue value = new BankValue();
		try {
			value = DAO.getBank(bankID);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return value;
	}
	
//-------start----------交易商对账------------------------------------------------------
	/**
	 * 手工对账检查
	 * @param bankID 银行代码
	 * @param date 对账日期
	 * @return Vector<CompareResult> 操作结果：对账信息不一致的数据
	 * @throws 
	 */
	public Vector<CompareResult> checkMoney(String bankID,java.util.Date date)
	{
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("手工对账检查");
		System.out.println("bankID["+bankID+"]date["+date+"]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}}}}}}");
		
		Vector<CompareResult> compareResultList= null;
		//数据连接
		Connection conn = null;
		Timestamp ts = new Timestamp(date.getTime());
		String time = Tool.fmtDate(ts);
		try 
		{
			conn = DAO.getConnection();
//			if(dataSaved)
//			{
				compareResultList=DAO.compareResultInfo(bankID,time);
//			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
			log("对账检查SQLException："+e);
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
			log("对账检查ClassNotFoundException："+e);
		}
		catch(Exception e) 
		{
			e.printStackTrace();
			log("对账检查Exception："+e);
		}
		finally
		{
			DAO.closeStatement(null, null, conn);
		}
		return compareResultList;
	}
	
	/**
	 * 自动对账检查 以配置的自动对账时间为准
	 * @param date 对账日期
	 * @return Vector<CompareResult> 操作结果：对账信息不一致的数据
	 * @throws 
	 */
	public Vector<CompareResult> checkMoney(java.util.Date date)
	{
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("自动对账检查 以配置的自动对账时间为准");
		System.out.println("date["+date+"]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}}}}}");
		Vector<CompareResult> compareResultList=null;
		//数据连接
		Connection conn = null;

		Timestamp ts = new Timestamp(date.getTime());
		String time = Tool.fmtDate(ts);
		try 
		{
			conn = DAO.getConnection();
//			if(dataSaved)
//			{
				Vector<BankValue> bankList = DAO.getBankList(" where validFlag=0 ");
				for(int i=0;i<bankList.size();i++)
				{
					BankValue bv = (BankValue)bankList.get(i);
					compareResultList=DAO.compareResultInfo(bv.bankID,time);
				}
//			}
		} catch (SQLException e) {
			e.printStackTrace();
			log("对账检查SQLException："+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			log("对账检查ClassNotFoundException："+e);
		} catch(Exception e) {
			e.printStackTrace();
			log("对账检查Exception："+e);
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return compareResultList;
	}
	/**
	 * 查询交易商当天出入金求和数据
	 * @param bankID 银行编号
	 * @param firmIDs 交易商代码集
	 * @param date 转账日期
	 * @return Vector<CapitalCompare>
	 */
	public Vector<CapitalCompare> sumResultInfo(String bankID,String[] firmIDs,java.util.Date date) {
		this.log("查询交易商当天出入金求和数据 bankID["+bankID+"]firmIDs["+firmIDs+"]date["+date+"]");
		Vector<CapitalCompare> result = null;
		try{
			result = DAO.sumResultInfo(bankID, firmIDs, date);
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
//-------end----------交易商对账------------------------------------------------------
//-------start----------提供给适配器的保存银行数据的方法------------------------------------------------------
	/**
	 * 提供给适配器的保存银行数据的方法
	 * @param mv：bank data  ready: true:insertBankMoneyInfo false:checkMoney
	 * @return long 0:success <>0:failure
	 */
	public long insertBankMoneyInfo(Vector<MoneyInfoValue> mv,int ready)
	{
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("提供给适配器的保存银行数据的方法");
		System.out.println("mv.size()["+mv.size()+"]ready["+ready+"]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}}}}}}");
		long result = 0;
		if(ready==1 || ready==2)//银行数据准备完毕  保存银行数据
		{
			if(mv!=null)
			{
				try {
					for(int a=0;a<mv.size();a++)
					{
						MoneyInfoValue miv = mv.get(a);
//						检查现有对账信息，如果已有当前的数据则不插入数据
						Vector<MoneyInfoValue> curList = DAO.getMoneyInfoList("where funID='"+miv.id+
								"' and trunc(compareDate)=to_date('"+Tool.fmtDate(miv.compareDate)+"','yyyy-MM-dd') and bankid='"+miv.bankID+"'");
						if(miv.id==null)
						{
							miv.id="-1";
						}

						if(curList==null || curList.size()==0)
						{
							if(ready==2)
							{
								miv.account=-1+"";
								miv.status=0;
								miv.note="day_Netting";
							}
							DAO.addMoneyInfo(miv);
						}
					}
//					dataSaved = true;
				} catch (SQLException e) {
					result = -1;
					e.printStackTrace();
				} catch (Exception e) {
					result = -1;
					e.printStackTrace();
				}
				finally
				{
				}
			}
			else
			{
				result = -1;
			}
		}
		else if(ready==3)
		{
			
		}
		System.out.println("===保存银行数据------处理结果result---："+result);
		return result;
	}
	
//-------end----------提供给适配器的保存银行数据的方法--------------------------------------------------------

//-------start----------市场发起出入金  适配器扫描银行操作状态需求----------------------------------------------
	public boolean tf(String str)
	{
		if(str==null || "".equals(str) || "0".equals(str))
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	/** 2、适配器线程扫描银行，获得流水状态改变并通知市场 */
	//public long  outMoneyForAccess(ReturnValue rv)
	public long  outMoneyForAccess(int rvResult,String bankid,String firmid,String account,String actionid,String funcid) 
	{
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{适配器线程扫描银行，获得流水状态改变并通知市场{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("rvResult["+rvResult+"]bankid["+bankid+"]firmid["+firmid+"]account["+account+"]actionid["+actionid+"]funcid["+funcid+"]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}}}}}");
		//返回码
		long result = 0;
		result = tradeDate(bankid);
		if(result!=0){
			return result;
		}
		//数据库连接
		Connection conn = null;
		//银行业务流水号
		long actionID = 0;
		//市场业务流水号
		String funId = null;
		//资金划转流水对象
		CapitalValue capital =null;
		//手续费流水对象
		CapitalValue rate =null;
		//当前系统时间
		Timestamp curTime = new Timestamp(System.currentTimeMillis());
		//synchronized(outMoneyForAccess){
			try {
					result = rvResult;
					conn = DAO.getConnection();
					String filter = " where 1=1 and status in (2,3,4) and bankid='"+bankid+"' ";
					if(tf(firmid))
					{
						filter = filter + " and firmid='"+firmid+"' ";
						System.out.println("firmid非空，值为："+firmid);
					}
					filter = filter + "  and actionid="+actionid+" and funid='"+funcid+"' ";
					System.out.println(filter);
					
					Vector<CapitalValue> list = DAO.getCapitalInfoList(filter, conn);//获取资金流水对象（包括出金和出金手续费流水）
					if(list.size()>0)
					{
						for (int i = 0; i < list.size(); i++) 
						{
							CapitalValue val = (CapitalValue)list.get(i);
							if(val.type == OUTMONEY)
							{
								capital = val;
							}
							else if(val.type == RATE)
							{
								rate = val;
							}
						}
						//启动数据库事务
						conn.setAutoCommit(false);
						Vector<CapitalValue> ll = DAO.getCapitalInfoList(filter+" for update ",conn);
						if(ll == null || ll.size()<=0){
							result = ErrorCode.outMoneyAudit_OradyAudite;
							log("信息已在处理中");
							return result;
						}
						if(result == 0)
						{
							//long actionid = Long.parseLong(actionId);
							dataProcess.updateFundsFull(capital.firmID, dataProcess.getOUTSUMMARY()+"", dataProcess.getBANKSUB().get(capital.bankID), capital.money, actionID,conn);//扣除出金
							dataProcess.updateFundsFull(capital.firmID, dataProcess.getFEESUMMARY()+"", null, rate.money,actionID, conn);//扣除手续费
							dataProcess.updateFrozenFunds(capital.firmID, -1*Arith.add(capital.money, rate.money), conn);//资金解冻
							//修改交易商银行接口冻结资金
							this.bankFrozenFuns(capital.firmID, capital.bankID, null, -1*Arith.add(capital.money, rate.money), conn);

							DAO.modCapitalInfoStatus(capital.iD,capital.funID, 0,curTime, conn);
							DAO.modCapitalInfoStatus(rate.iD,capital.funID, 0,curTime, conn);

							System.out.println("===适配器刷银行，返回成功---流水号：---"+capital.iD);
							DAO.modCapitalInfoNote(capital.iD, "market_out_Audit_1审核通过，银行处理成功，出金成功", conn);
							DAO.modCapitalInfoNote(rate.iD, "market_out_Audit_1审核通过，银行处理成功，扣除手续费成功", conn);
						}
						else
						{
							System.out.println("===适配器刷银行，返回失败------");
							if(getConfig("OutFailProcess").equalsIgnoreCase("true"))//失败后是否进行退资金操作
							{
//								if(alreadyFailure)
//								{
								dataProcess.updateFrozenFunds(capital.firmID, -1*Arith.add(capital.money, rate.money), conn);//资金解冻
								//修改交易商银行接口冻结资金
								this.bankFrozenFuns(capital.firmID, capital.bankID, null, -1*Arith.add(capital.money, rate.money), conn);
//								}
	
								DAO.modCapitalInfoStatus(capital.iD,funId, 1,curTime, conn);
								DAO.modCapitalInfoStatus(rate.iD,funId, 1,curTime, conn);
								DAO.modCapitalInfoNote(capital.iD, "market_out_Audit_1审核通过，银行处理失败，出金金额全部解冻", conn);
								DAO.modCapitalInfoNote(rate.iD, "market_out_Audit_1审核通过，银行处理失败，手续费金额全部解冻", conn);
								log("审核通过，银行处理失败退还全部出金和手续费，错误码="+result);
							}
							else
							{
								//状态修改为1：发送给银行过程中处理失败
								DAO.modCapitalInfoStatus(capital.iD,funId, 1,curTime, conn);
								DAO.modCapitalInfoStatus(rate.iD, funId,1,curTime, conn);
								DAO.modCapitalInfoNote(capital.iD, "market_out_Audit_1审核通过，银行处理失败，需手工解冻出金", conn);
								DAO.modCapitalInfoNote(rate.iD, "market_out_Audit_1审核通过，银行处理失败，需手工解冻手续费", conn);
								result =ErrorCode.outMoneyAudit_ErrorBank;	//发送给银行过程中处理失败
								log("审核通过，银行处理失败，需要手工解冻出金和手续费，错误码="+result);
							}
						}
						conn.commit();
					}
					else
					{System.out.println("===适配器刷银行，市场发起出金:记录资金流水错误------");
						result = ErrorCode.outMoney_ErrorAddCapital;
					}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}finally{
				DAO.closeStatement(null, null, conn);
			}
		//}
		return result;
	}
	
	
//-------end----------市场发起出入金  适配器扫描银行操作状态需求-----------------------------------------------
	
	/**接口参数合法性检查方法
	 * @return long 0:合法  <0:不合法
	 * @throws SQLException */
	public long checkArgs(Connection conn,double realFunds,double outRate,String bankID,double money,String firmID,String bankAccount,int express,int type) throws SQLException
	{
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("接口参数合法性检查方法");
		System.out.println("realFunds["+realFunds+"]outRate["+outRate+"]bankID["+bankID+"]money["+money+"]firmID["+firmID+"]bankAccount["+bankAccount+"]express["+express+"]type["+type+"]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}}}}}}");
		long result = 0;
		//取得出金手续费
		outRate = getTransRate(bankID, firmID, money, OUTMONEY,express,bankAccount,conn);
		//取得交易商当前资金
		realFunds=dataProcess.getRealFunds(firmID, conn);
		
		/**********************接口参数合法性检查***************************/
		if((firmID == null || firmID.trim().equals("")) && (bankAccount == null || bankAccount.trim().equals("")))
		{
			//参数非法
			result = ErrorCode.outMoney_ErrorParameter;
			log("市场发起出金，参数非法，错误码="+result);
		}
		else if(firmID == null || firmID.trim().equals(""))
		{
			Vector<CorrespondValue> cList = DAO.getCorrespondList("where bankid='"+bankID+"' and account='"+bankAccount+"'", conn);
			if(cList.size() > 0)
			{
				firmID = ((CorrespondValue)cList.get(0)).firmID;
			}
			else
			{
				//非法银行帐号
				result =ErrorCode.outMoney_ErrorBankAcount;	
				log("市场发起出金，非法银行帐号，错误码="+result);
			}
		}
		else if(bankAccount == null || bankAccount.trim().equals(""))
		{
			Vector<CorrespondValue> cList = DAO.getCorrespondList("where bankid='"+bankID+"' and firmid='"+firmID+"'", conn);
			if(cList.size() > 0)
			{
				bankAccount = ((CorrespondValue)cList.get(0)).account;
			}
			else
			{
				//非法交易商代码
				result = ErrorCode.outMoney_ErrorFirmCode;
				log("市场发起出金，非法交易商代码，错误码="+result);
			}
		}
		else if(DAO.getCorrespond(bankID, firmID, bankAccount, conn) == null)
		{
			//交易商代码和帐号对应关系错误
			result = ErrorCode.outMoney_ErrorCorrespond;
			log("市场发起出金，交易商代码和帐号对应关系错误，错误码="+result);
		}
		if(result == 0){
			Vector<CorrespondValue> cList = DAO.getCorrespondList("where bankid='"+bankID+"' and firmid='"+firmID+"' and status=0 ", conn);
			if(cList == null || cList.size() != 1){
				result = ErrorCode.outMoney_ErrorCorrespond;
			}
		}
		if(result==0 && (outRate==-1 || outRate==-2))
		{
			result=ErrorCode.outMoney_ErrorGetRate;
			log("市场发起出金，计算出金手续费错误，错误码="+result);
		}
		else if(realFunds<Arith.add(money, outRate))
		{
			System.out.println("realFunds-------------->"+realFunds);
			System.out.println("Arith.add(money, outRate)-------------->"+(Arith.add(money, outRate)));
			result=ErrorCode.outMoney_ErrorLackFunds;
			log("市场发起出金，资金余额不足，错误码="+result);
		}

		return result;
	}
	
	/**检查交易商是否符合出金条件,符合条件进行出金处理,记录流水
	 * @return long 市场业务流水号
	 * @throws SQLException */
	public HashMap<Integer,Long> handleOUtMoney(long result,Connection conn,long checkTrans,double outRate,String bankID,
			double money,String firmID,String funID ,int express,int type) throws SQLException
	{
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("检查交易商是否符合出金条件,符合条件进行出金处理,记录流水");
		System.out.println("result["+result+"]checkTrans["+checkTrans+"]outRate["+outRate+"]bankID["+bankID+"]money["+money+"]firmID["+firmID+"]funID["+funID+"]express["+express+"]type["+type+"]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}}}}");
		HashMap<Integer,Long> map = new HashMap<Integer,Long>();
		long capitalID = 0;
		long rateID = 0;
			//取得市场业务流水号
			result = DAO.getActionID(conn);
			try 
			{
				//启动数据库事务
				conn.setAutoCommit(false);
				if(funID != null && funID.trim().length()>0){
					Vector<CapitalValue> cv = DAO.getCapitalInfoList(" where bankID='"+bankID+"' and trunc(createtime)=trunc(sysdate) and funID='"+funID+"'",conn);
					if(cv != null && cv.size()>0){
						result = ErrorCode.outMoneyAudit_OradyAudite;
						log("银行出金，银行流水号["+funID+"]已经存在");
					}
				}
				if(result>=0){
					//记录转帐模块资金流水
					CapitalValue cVal = new CapitalValue();
					cVal.actionID = result;
					cVal.bankID = bankID;
					cVal.creditID = dataProcess.getBANKSUB().get(bankID);
					cVal.debitID = firmID;
					cVal.firmID = firmID;
					cVal.money = money;
					cVal.oprcode = dataProcess.getOUTSUMMARY()+"";
					cVal.status = 3;
					cVal.type = OUTMONEY;
					cVal.express=express;
					cVal.funID = (funID==null?"":funID);
					if(type == 0)
					{
						cVal.note = "market_out";
					}
					else if(type == 1)
					{
						cVal.note = "bank_out";
					}
					
					capitalID = DAO.addCapitalInfo(cVal, conn);//出金流水
					
					cVal.creditID = "Market";
					cVal.debitID = firmID;
					cVal.money = outRate;
					cVal.oprcode = dataProcess.getFEESUMMARY()+"";
					cVal.type = RATE;
	
					rateID = DAO.addCapitalInfo(cVal, conn);//手续费流水
				}
				conn.commit();
			} 
			catch(SQLException e) 
			{
				e.printStackTrace();
				conn.rollback();
				result=ErrorCode.outMoney_ErrorAddCapital;
				log("市场发起出金SQLException,数据回滚"+e);
				throw e;
			}
			finally
			{
				conn.setAutoCommit(true);
			}
			map.put(1, result);
			map.put(2, capitalID);
			map.put(3, rateID);
		return map;
	}
	/**
	 * 判断某一天是否是交易日
	 * @param tradeDate 日期
	 * @return ReturnValue 
	 */
	public ReturnValue isTradeDate(java.util.Date tradeDate){
		ReturnValue result = new ReturnValue();
		try{
			if(tradeDate != null){
				boolean flag = DAO.getTradeDate(tradeDate);
				if(flag){
					result.remark = "日期["+Tool.fmtDate(tradeDate)+"]是交易日";
				}else{
					result.result = -3;
					result.remark = "日期["+Tool.fmtDate(tradeDate)+"]不是交易日";
				}
			}else{
				result.result = -4;
				result.remark = "传入的日期为空，不合法";
			}
		}catch(SQLException e){
			result.result = -1;
			result.remark = "查询日期["+Tool.fmtDate(tradeDate)+"]是否为交易日时数据库异常";
			e.printStackTrace();
		}catch(Exception e){
			result.result = -2;
			result.remark = "查询日期["+Tool.fmtDate(tradeDate)+"]是否为交易日时系统异常";
			e.printStackTrace();
		}
		return result;
	}
	
//--------------- start --------------- 银行开户 销户 变更帐户 -----------------------------------------
	/**
	 * 银行开户销户变更帐户检验参数方法
	 * @return int 0成功 <>0失败
	 * @param CorrespondValue
	 */
	public long chenckCorrespondValue(CorrespondValue cv)
	{
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("银行开户销户变更帐户检验参数方法");
		System.out.println("cv["+cv.toString()+"]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}}}}}");
		long checkResult = 0;
		String bankid = cv.bankID;
		String firmid = cv.firmID;
		String account = cv.account;
		if(bankid==null || firmid==null || account==null)
		{
			if(bankid==null)
			{
				checkResult = ErrorCode.rgstAccount_NOBank;
			}
			if(firmid==null)
			{
				checkResult = ErrorCode.rgstAccount_NOFirm;
			}
			if(account==null)
			{
				checkResult = ErrorCode.rgstAccount_InfoHalfbaked;
			}
		}
		else
		{
			try {
				List<CorrespondValue> cvresult = DAO.getCorrespondList(" where bankId='"+bankid+"' and firmId='"+firmid+"' and (account like '%"+account.trim()+"%' or account='"+Tool.getConfig("DefaultAccount")+"')");
				if("005".equals(bankid)||"17".equals(bankid)){
					cvresult = DAO.getCorrespondList(" where bankId='"+bankid+"' and firmId='"+firmid+"' ");
				}
				if(cvresult == null || cvresult.size()<=0)
				{
					checkResult = ErrorCode.rgstAccount_InfoHalfbaked;
				}
			} catch (SQLException e) {
				checkResult = ErrorCode.rgstAccount_DatabaseException;
				e.printStackTrace();
				log("交易商代码与银行帐号对应SQLException,"+e);
			} catch (Exception e) {
				checkResult = ErrorCode.rgstAccount_SysException;
				e.printStackTrace();
				log("交易商代码与银行帐号对应SQLException,"+e);
			}
		}
		return checkResult;
	}
	
	/**
	 * 银行开户方法
	 * 变更 交易商代码与银行帐号对应关系 的签约状态
	 */
	public ReturnValue openAccount(CorrespondValue cv)
	{
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("银行开户方法");
		System.out.println("cv["+cv.toString()+"]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}}}}}}");
		long checkResult = chenckCorrespondValue(cv);
		ReturnValue rv = new ReturnValue();
		if(checkResult==0)
		{
			rv.actionId=getMktActionID();
			try {
				cv.isOpen=1;
				if (cv.opentime == null) {
					cv.opentime = new java.util.Date();
				}
//				rv.result = DAO.modCorrespond(cv);//2012.11.12 lipj 银行端签约未录入签约时间，修改调用签约的方法为openCorrespond
				rv.result = DAO.openCorrespond(cv);
			} catch (SQLException e) {
				rv.result=(int) ErrorCode.rgstAccount_DatabaseException;
				e.printStackTrace();
				log("银行开户 交易商代码与银行帐号对应SQLException,"+e);
			} catch (Exception e) {
				rv.result=(int) ErrorCode.rgstAccount_SysException;
				e.printStackTrace();
				log("银行开户 交易商代码与银行帐号对应SQLException,"+e);
			}
		}
		else
		{
			rv.result=(int) checkResult;
			rv.remark="交易商代码与银行、帐号对应有误!";
		}
		return rv;
	}

	/**
	 * 银行开户方法
	 * 变更 交易商代码与银行帐号对应关系 的签约状态
	 */
	public ReturnValue openAccountMarket(CorrespondValue cv)
	{
		System.out.println(">>>>市场开户方法>>>>时间："+Tool.fmtTime(new java.util.Date())+">>>>\ncv["+cv.toString()+"]");
		long checkResult = chenckCorrespondValue(cv);
		ReturnValue rv = new ReturnValue();
		CorrespondValue cv2 = null;
		if(checkResult==0)
		{
			rv.actionId=getMktActionID();
			try {
				cv.isOpen=1;
				List<CorrespondValue> cvresult=null;
				if("005".equals(cv.bankID)||"17".equals(cv.bankID)){//如果是农行的户，市场端就不校验银行卡号 张锦锦 2011.11.03
					cvresult = DAO.getCorrespondList(" where bankId='"+cv.bankID.trim()+"' and firmId='"+cv.firmID.trim()+"' ");
				}else{//如果不是农行的户，则市场端校验银行卡号  张锦锦 2011.11.03
					cvresult = DAO.getCorrespondList(" where bankId='"+cv.bankID.trim()+"' and firmId='"+cv.firmID.trim()+"' and account like '%"+cv.account.trim()+"%'");
				}
				if(cvresult == null || cvresult.size()<=0)
				{
					rv.result = ErrorCode.rgstAccount_InfoHalfbaked;
				}else{
					cv2 = cvresult.get(0);
					cv2.isOpen = 1;
					cv2.status = cv.status;
					cv2.account = cv.account;
					cv2.falg = cv.falg;
					cv2.OpenBankCode = cv.OpenBankCode;
					cv2.Phone = cv.Phone;
					cv2.Linkman = cv.Linkman;
					cv2.addr = cv.addr;
					cv2.LawName = cv.LawName;
					cv2.NoteFlag = cv.NoteFlag;
					cv2.NotePhone = cv.NotePhone;
					cv2.email = cv.email;
					cv2.zipCode = cv.zipCode;
					cv2.checkFlag = cv.checkFlag;
					BankAdapterRMI bankadapter = this.getAdapter(cv2.bankID);
					if(bankadapter==null){
						rv.result = ErrorCode.GetAdapter_Error;
					}else{
						cv2.bankCardPassword=cv.bankCardPassword;//市场端签约，银行卡密码发给银行校验  张锦锦 2011.11.03
						
						rv = bankadapter.rgstAccountQuery(cv2);
						if(rv.result == 0){
							if(cv.bankID.equals("005")||cv.bankID.equals("17")){//农行市场端签约
								cv2.bankCardPassword=null;//市场端签约，市场不记录客户银行卡密码  张锦锦 2011.11.03
								cv2.account1=rv.remark;//银行内部账号，农行适配器传过来，保留在数据库中
							}
							rv.result = DAO.modCorrespond(cv2);
						}else if(rv.result == 5){
							cv2.isOpen = 2;
							rv.result = DAO.modCorrespond(cv2);
						}
					}
				}
			} catch (SQLException e) {
				rv.result=(int) ErrorCode.rgstAccount_DatabaseException;
				e.printStackTrace();
				log("银行开户 交易商代码与银行帐号对应SQLException,"+e);
			} catch (Exception e) {
				rv.result=(int) ErrorCode.rgstAccount_SysException;
				e.printStackTrace();
				log("银行开户 交易商代码与银行帐号对应SQLException,"+e);
			}
		}else{
			rv.result=(int) checkResult;
			rv.remark="交易商代码与银行、帐号对应有误!";
		}
		return rv;
	}
	
	/**
	 * 同步子账号方法 20110311  lipj 华夏定制
	 */
	public ReturnValue synchroAccountMarket(CorrespondValue cv)
	{
		System.out.println(">>>>市场同步账号方法>>>>时间："+Tool.fmtTime(new java.util.Date())+">>>>\ncv["+cv.toString()+"]");
		long checkResult = chenckCorrespondValue(cv);
		ReturnValue rv = new ReturnValue();
		CorrespondValue cv2 = null;
		if(checkResult==0)
		{
			rv.actionId=getMktActionID();
			try {
				cv.isOpen=1;
				List<CorrespondValue> cvresult = DAO.getCorrespondList(" where bankId='"+cv.bankID.trim()+"' and firmId='"+cv.firmID.trim()+"' and account like '%"+cv.account.trim()+"%'");
				if(cvresult == null || cvresult.size()<=0)
				{
					rv.result = ErrorCode.rgstAccount_InfoHalfbaked;
				}else{
					cv2 = cvresult.get(0);
					cv2.isOpen = 0;
					cv2.status = cv.status;
					BankAdapterRMI bankadapter = this.getAdapter(cv2.bankID);
					if(bankadapter==null){
						rv.result = ErrorCode.GetAdapter_Error;
					}else{
						rv = bankadapter.synchroAccount(cv2);
						if(rv.result == 0){
							rv.result = DAO.modCorrespond(cv2);
						}
					}
				}
			} catch (SQLException e) {
				rv.result=(int) ErrorCode.rgstAccount_DatabaseException;
				e.printStackTrace();
				log("市场同步账号 交易商代码与银行帐号对应SQLException,"+e);
			} catch (Exception e) {
				rv.result=(int) ErrorCode.rgstAccount_SysException;
				e.printStackTrace();
				log("市场同步账号 交易商代码与银行帐号对应SQLException,"+e);
			}
		}else{
			rv.result=(int) checkResult;
			rv.remark="交易商代码与银行、帐号对应有误!";
		}
		return rv;
	}
	
	/**
	 * 银行销户方法
	 * 变更 交易商代码与银行帐号对应关系 的签约状态
	 */
	public ReturnValue destroyAccount(CorrespondValue cv)
	{
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("银行销户方法");
		System.out.println("cv["+cv.toString()+"]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}}}}}");
		long checkResult = 0;//chenckCorrespondValue(cv);
		ReturnValue rv = new ReturnValue();
		if(checkResult==0)
		{
			rv.actionId=getMktActionID();
			try {
				cv.isOpen=0;
				cv.status=1;
				rv.result = DAO.modCorrespond(cv);
			} catch (SQLException e) {
				rv.result=(int) ErrorCode.delAccount_DatabaseException;
				e.printStackTrace();
				log("银行销户 交易商代码与银行帐号对应SQLException,"+e);
			} catch (Exception e) {
				rv.result=(int) ErrorCode.delAccount_SysException;
				e.printStackTrace();
				log("银行销户 交易商代码与银行帐号对应SQLException,"+e);
			}
		}
		else
		{
			rv.result=(int) ErrorCode.delAccount_InfoHalfbaked;
			rv.remark="交易商代码与银行、帐号对应有误!";
		}
		return rv;
	}
	
	/**
	 * 变更帐户方法
	 * 变更 交易商代码与银行帐号对应关系 的签约状态
	 */
	public ReturnValue modAccount(CorrespondValue cv1,CorrespondValue cv2)
	{
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("变更帐户方法");
		System.out.println("cv1["+cv1.toString()+"]\ncv2["+cv2.toString()+"]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}}}}}}");
		long checkResult = chenckCorrespondValue(cv1);
		//long checkResult2 = chenckCorrespondValue(cv2);
		ReturnValue rv = new ReturnValue();
		if(checkResult==0)
		{
			rv.actionId=getMktActionID();
			try {
				cv1.account=cv2.account;
				cv1.isOpen=1;
				cv1.status=0;
				rv.result = DAO.modCorrespond(cv1);
			} catch (SQLException e) {
				rv.result = (int) ErrorCode.rgstAccount_DatabaseException;
				e.printStackTrace();
				log("修改交易商代码与银行帐号对应SQLException,"+e);
			} catch (Exception e) {
				rv.result = (int) ErrorCode.rgstAccount_SysException;
				e.printStackTrace();
				log("修改交易商代码与银行帐号对应SQLException,"+e);
			}
		}
		else
		{
				rv.result=(int) checkResult;
		}
		return rv;
	}
//--------------- end --------------- 银行开户 销户 变更帐户 -----------------------------------------
	/**
	 * 银行接口资金冻结
	 */
	public int bankFrozenFuns(String firmID,String bankID,String account,double money,Connection conn) throws SQLException{
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("银行接口资金冻结");
		System.out.println("firmID["+firmID+"]bankID["+bankID+"]account["+account+"]money["+money+"]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}}}}}");
		int result = 0;
		if(firmID==null || firmID.trim().length()<=0 || bankID==null || bankID.trim().length()<=0){
			log("冻结(解冻)资金，信息不完整 firmID="+firmID+" bankID="+bankID);
			throw new SQLException("冻结(解冻)资金，信息不完整 firmID="+firmID+" bankID="+bankID);
		}
		String filter=" where 1=1 and firmID='"+firmID.trim()+"' and bankID='"+bankID.trim()+"' ";
		if(account!=null){
			filter = filter+" and account='"+account.trim()+"'";
		}
		String filter2 = filter+" for update";
		try{
			Vector<CorrespondValue> vector = DAO.getCorrespondList(filter2, conn);
			if(vector!=null && vector.size()>0){
				CorrespondValue cv = vector.get(0);
				if(cv.frozenFuns+money<0){
					throw new SQLException("交易商冻结资金不足以释放当前资金：冻结资金["+cv.frozenFuns+"]当前资金["+money+"]");
				}
				result = DAO.modBankFrozenFuns(filter, money, conn);
			}else { 
				throw new SQLException("没有找到本条记录+"+filter);
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw e;
		}
		return result;
	}
	/**查询银行帐号的签约状态*/
	public int bankAccountIsOpen(String firmid,String bankid,String account)
	{
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("查询银行帐号的签约状态");
		System.out.println("firmid["+firmid+"]bankid["+bankid+"]account["+account+"]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}}}}}");
		String filter = " where 1=1 ";
		if(firmid!=null)
		{
			filter = filter + " and firmid='"+firmid+"'";
			if(account!=null)
			{
				filter = filter + " and account='"+account+"'";
			}
		}
		else
		{
			filter = filter + " and account='"+account+"'";
		}
		if(bankid!=null)
		{
			filter = filter + " and bankid='"+bankid+"'";
		}
		int value=DAO.bankAccountIsOpen(filter);
		System.out.println("DAO.bankAccountIsOpen(filter)="+value);
		return value;
	}
	
	/**
	 * 查询交易商名下交易员信息
	 * @param firmid 交易员id
	 * @return string 密码
	 */
	public FirmMessageVo getFirmMSG(String firmid)
	{
		return DAO.getFirmMSG(firmid);
	}
	/**
	   * 取交易系统结算状态
	   * @return boolean 系统状态 true 已结算
	   * @throws SQLException
	   */
	  public boolean getTraderStatus()
	  {
		  boolean status = false;
		  try {
				System.out.println("getTraderStatus运行前"+status);
			  status = DAO.getTraderStatus();
				System.out.println("getTraderStatus运行后"+status);
			  
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("getTraderStatus返回"+status);
		return status;
	  }
	  /**
	   * 判断是否可以发生转账交易
	   */
	  public long tradeDate(String bankID){
		  this.log("判断是否可以转账"+Tool.fmtDate(new java.util.Date()));
		  long result = 0;
		  try{
			  int n = DAO.useBank(bankID);
			  switch(n){
			  case 0 : result = 0;break;
			  case 1 : result = ErrorCode.Not_TradeDate;break;
			  case 2 : result = ErrorCode.Be_TradeTime;break;
			  case 3 : result = ErrorCode.Af_TradeTime;break;
			  default : result = ErrorCode.Co_Trade;break;
			  }
		  }catch(Exception e){
			  e.printStackTrace();
			  result = ErrorCode.Co_Trade;
		  }
		  return result;
	  }
		/**
		 * 判断密码合法性,true 验证成功;false 验证失败
		 * @param firmID 交易商代码
		 * @param password 交易商密码
		 * @return long (0 成功;1 未设置密码;-1 验证失败;-2 查询不到交易商)
		 */
		public long isPassword(String firmID, String password) {
			long result=-1;
			String newpassword = Encryption.encryption(firmID,password,null);
			try{
				FirmValue fv = DAO.getFirm(firmID);
				if(fv!=null){
					String oldpassword = fv.password;
					if(oldpassword!=null && oldpassword.trim().length()>0){
						if(newpassword.equals(oldpassword)){
							result = 0;
						}else{
							log("交易商["+firmID+"]输入的密码错误");
						}
					}else{
						result = 1;
						log("交易商["+firmID+"]尚未初始化密码");
					}
				}else{
					result = -2;
					log(Tool.fmtTime(new java.util.Date())+"查询不到交易商["+firmID+"]");
				}
			}catch(SQLException e){
				e.printStackTrace();
			}catch(Exception e){
				e.printStackTrace();
			}
			return result;
		}
		public long isLogPassword(String firmID, String password) {
			long result=-1;
			try{
				FirmValue fv = DAO.getFirm(firmID);
				if(fv!=null){
					
						LogonManager manager = LogonManager.createInstance("5", DAO.getDataSource());
						int ispassword =  manager.checkUser(firmID, password);
						if(ispassword == 0){
							result = 0;
						}else{
							log("交易商["+firmID+"]输入的密码错误");
						}
				}else{
					result = -2;
					log(Tool.fmtTime(new java.util.Date())+"查询不到交易商["+firmID+"]");
				}
			}catch(SQLException e){
				e.printStackTrace();
			}catch(Exception e){
				e.printStackTrace();
			}
			return result;
		}
		/**
		 * 修改交易商密码
		 * @param firmID 交易商代码
		 * @param password 交易商原密码
		 * @param chpassword 交易商将要更改密码
		 * @return long(0 成功;-1 原密码错误;-2 为找到交易商)
		 */
		public long modPwd(String firmID, String password, String chpassword) {
			long result = -1;
			String newpassword = Encryption.encryption(firmID,password,null);
			try{
				FirmValue fv = DAO.getFirm(firmID);
				if(fv!=null){
					String oldpassword = fv.password;
					if(oldpassword==null || oldpassword.trim().length()<=0 || newpassword.equals(oldpassword)){
						fv.password = Encryption.encryption(firmID,chpassword,null);
						int n=DAO.modFirm(fv);
						if(n>=0){
							result = 0;
						}else{
							log(Tool.fmtTime(new java.util.Date())+"修改交易商密码，返回数值小于0");
						}
					}else{
						result = -1;
						log(Tool.fmtTime(new java.util.Date())+"交易商["+fv.firmID+"]修改密码，原密码错误。oldpassword["+oldpassword+"]nowpassword["+newpassword+"]");
					}
				}else{
					result = -2;
					log(Tool.fmtTime(new java.util.Date())+"未查询到相应交易商["+firmID+"]");
				}
			}catch(SQLException e){
				log(Tool.fmtTime(new java.util.Date())+"修改密码数据库异常，输入的密码："+newpassword);
				e.printStackTrace();
			}catch(Exception e){
				log(Tool.fmtTime(new java.util.Date())+"修改密码系统异常，输入的密码："+newpassword);
				e.printStackTrace();
			}
			return result;
		}
//******************************************手工出入金形式*************************************

		/**
		 * 入金(手工出入金)
		 * @param bankID 银行代码(或银行名称)
		 * @param firmID 交易商代码V
		 * @param account 交易商银行帐号
		 * @param accountPwd 交易商银行帐号密码
		 * @param money   金额
		 * @param remark   备注
		 * @return long 银行接口业务流水号,返回<0的值表示操作失败
		 * (-10001：非法银行帐号 -10002：非法交易商代码 -10003：交易商代码和帐号对应关系错误 -10004：数据库异常 -10005：系统异常 -10006：参数错误 --10008:适配器提交失败 )
		 */
		@SuppressWarnings("static-access")
		public long inMoneyNoAdapter(String maketbankID, String firmID,String account,String bankName,double money ,String remark){

			System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
			System.out.println("入金(手工出入金)");
			System.out.println("maketbankID["+maketbankID+"]firmID["+firmID+"]account["+bankName+"]money["+money+"]remark["+remark+"]");
			System.out.println("}}}}}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}}}}");
			//返回码
			long result = 0;

			result = tradeDate(maketbankID);
			if(result!=0){
				return result;
			}
			//数据库连接
			Connection conn = null;

			//当前系统时间
			Timestamp curTime = new Timestamp(System.currentTimeMillis());
			
			try{
				//获取数据库连接
				conn = DAO.getConnection();
				//取得入金手续费
				double inRate = getTransRate(maketbankID, firmID, money, INMONEY,0,account,conn);
				if(result == 0){
					//判断是否符合入金条件
					long checkTrans=checkTrans(maketbankID, firmID, money, curTime, INMONEY, conn);
					if(checkTrans== 0){
						//取得市场业务流水号
						result = DAO.getActionID(conn);
						try{
							//启动数据库事务
							conn.setAutoCommit(false);
							//记录转帐模块资金流水
							CapitalValue cVal = new CapitalValue();
							cVal.actionID = result;//市场业务流水号
							cVal.bankID = maketbankID;//市场银行ID
							cVal.funID = "";//银行业务流水号
							cVal.creditID = firmID;//贷方科目代码
							cVal.debitID = dataProcess.getBANKSUB().get(maketbankID);//借方科目代码
							cVal.firmID = firmID;//交易商ID
							cVal.money = money;//入金金额
							cVal.note = remark;//备注
							cVal.oprcode = dataProcess.getINSUMMARY()+"";//业务代码/入金财务摘要号
							cVal.status = 13;//待审核状态
							cVal.type = INMONEY;//标明入金
							cVal.bankName=bankName;//交易商银行名称
							cVal.account=account;//交易商银行账号
							DAO.addCapitalInfo(cVal, conn);//入金流水
							cVal.creditID = "Market";
							cVal.debitID = firmID;
							cVal.money = inRate;
							cVal.oprcode = dataProcess.getFEESUMMARY()+"";
							cVal.type = RATE;

							DAO.addCapitalInfo(cVal, conn);//手续费流水
							conn.commit();
						}catch(SQLException e){
							e.printStackTrace();
							conn.rollback();
							//写入资金流水失败-10026
							result=ErrorCode.inMoneyM_ErrorAddCapital;
							log("市场端发起入金写资金流水SQLException,数据回滚:"+e);
							throw e;
						}finally{
							conn.setAutoCommit(true);
						}
					}else{
						//不符合入金条件
						result = checkTrans;
					}
					
//					if(result>=0){
//						if(Tool.getConfig("inMoneyNoAdapterAddMoney").equals("true")){
//							dataProcess.updateFundsFull(firmID, dataProcess.getINSUMMARY()+"", dataProcess.getBANKSUB().get(maketbankID), money,result, conn);//添加入金
//							dataProcess.updateFundsFull(firmID, dataProcess.getFEESUMMARY()+"", null, inRate,result, conn);//扣除手续费
//							log("假银行入金，添加资金凭证成功");
//						}else{
//							log("假银行入金，流水写入成功，inMoneyNoAdapterAddMoney为false，不添加资金凭证");
//						}
//					}else{
//						log("假银行入金，添加写入流水失败");
//					}
				}
			}catch(SQLException e){
				e.printStackTrace();
				//数据库异常-10014
				result = ErrorCode.inMoneyM_DataBaseException;
				log("市场端发起入金SQLException:"+result);
			}catch(Exception e){
				e.printStackTrace();
				//系统异常-10015
				result = ErrorCode.inMoneyM_SysException;
				log("市场端发起入金Exception:"+result);
			}finally{
				DAO.closeStatement(null, null, conn);
			}
			return result;
		}

		/**
		 * 出金(手工出入金)
		 * @param bankID 银行代码
		 * @param money 金额
		 * @param firmID 交易商代码
		 * @param bankAccount 银行帐号
		 * @param funID 银行流水号
		 * @param express 0：正常 1：加急
		 * @param type 0：市场端出金 1：银行端出金
		 * @return long 银行接口业务流水号,返回<0的值表示操作失败                          
		 * @throws 
		 */
		public long outMoneyNoAdapter(String maketbankID,double money,String firmID,String bankName,String account,String operator,int express,int type){
			System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
			System.out.println("出金(手工出入金)");
			System.out.println("maketbankID["+maketbankID+"]money["+money+"]firmID["+firmID+"]bankName["+bankName+"]account["+account+"]operator["+operator+"]express["+express+"]type["+type+"]");
			System.out.println("}}}}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}}}}}");
			/**返回码*/
			long result = 0;
			result = tradeDate(maketbankID);
			if(result!=0){
				return result;
			}
			/**资金划转流水号*/
			long capitalID = 0;
			/**手续费划转流水号*/
			long rateID = 0;
			//数据库连接
			Connection conn = null;
			//当前系统时间
			Timestamp curTime = new Timestamp(System.currentTimeMillis());
			try{
				//获取数据库连接
				conn = DAO.getConnection();
				//查询接口是否合法0合法<0不合法
				result = checkArgs(conn,maketbankID,money,firmID,account,OUTMONEY);
				if(result == 0){
					result = checkArgs(conn,0,0,maketbankID,money,firmID,account,express,type);
				}
				/*********************检查交易商是否符合出金条件,符合条件进行出金处理**************************/
				if(result == 0){
					//判断是否符合出入金条件 0：符合条件；<>0:不符合转帐条件
					long checkTrans = checkTrans(maketbankID, firmID, money, curTime, OUTMONEY, conn);
					if(checkTrans == 0){// >0市场业务流水号  -1异常
						//添加资金流水，返回添加是否成功和流水号
						HashMap<Integer,Long> map = handleOUtMoney(result,conn,checkTrans,maketbankID,money,firmID,express,type,bankName,account);
						result = map.get(1).longValue();
						capitalID = map.get(2).longValue();
						rateID = map.get(3).longValue();
					}else{
						//可能值：-30000 -30002 -30003 -30004 -30005 0
						result = checkTrans;
					}
				}
			}catch(SQLException e){
				e.printStackTrace();
				//数据库异常-20004
				result =ErrorCode.outMoney_DataBaseException;
				log("市场发起出金SQLException，错误码="+result+"  "+e);
			}catch(Exception e){
				e.printStackTrace();
				//系统异常-20005
				result = ErrorCode.outMoney_SysException;
				log("市场发起出金Exception，错误码="+result+"  "+e);
				try {
					//把记录更新为失败
					DAO.modCapitalInfoStatus(capitalID,"", 1,curTime, conn);
					DAO.modCapitalInfoStatus(rateID, "",1,curTime, conn);			
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}finally{
				DAO.closeStatement(null, null, conn);
			}
			return result;
		}
		/**
		 * 查询交易商市场资金
		 * @return  FirmBalanceValue
		 */
		public FirmBalanceValue getBalanceNoAdapter(String firmid){
			System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
			System.out.println("查询交易商市场资金");
			System.out.println("firmID["+firmid+"]");
			System.out.println("}}}}}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}}}}");
			String filter = " where 1=1 ";
			if(firmid!=null)
			{
				filter += "  and firmid='"+firmid+"'  ";
			}
			FirmBalanceValue firmBalanceValue=DAO.availableBalance(filter);
			if("true".equalsIgnoreCase(this.getConfig("fuYing"))){//如果市场记录浮盈
				try{
					Vector<FirmBalanceValue> floatingloss = DAO.getFlote(new String[]{firmid});
					if(floatingloss != null && floatingloss.size()>0){
						for(FirmBalanceValue fb : floatingloss){
							if(fb != null && firmid.equals(fb.firmId)){
								firmBalanceValue.floatingloss = fb.floatingloss;
								if(fb.floatingloss>0){
									firmBalanceValue.avilableBalance = firmBalanceValue.avilableBalance-fb.floatingloss;
								}
								break;
							}
						}
					}
				}catch(SQLException e){
					e.printStackTrace();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			try {
				Firm firm = DAO.getMFirmByFirmId(firmid);
				firmBalanceValue.bankAccount=firm.bankAccount;
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return firmBalanceValue;
		}
		/**
		 * 出入金审核
		 * @param actionID  转账模块业务流水号
		 * @param funFlag  审核结果
		 * @param audit  手工[false]与自动[true] 
		 * @return long 0 成功 <0失败 
		 */
		public long moneyAuditNoAdapter(long actionID,boolean funFlag){
			System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
			System.out.println("出入金审核(手工出入金)");
			System.out.println("actionID["+actionID+"]funFlag["+funFlag+"]");
			System.out.println("}}}}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}}}}}");
			String auditInfo="审核业务流水号："+actionID+";";
			//数据库连接
			Connection conn = null;
			//当前系统时间
			Timestamp curTime = new Timestamp(System.currentTimeMillis());
			//资金划转流水对象
			CapitalValue capital =null;
			//手续费
			CapitalValue rate =null;
			//适配器返回结果
			ReturnValue rVal = new ReturnValue();
			//返回码
			long result = 0;
			//如果设置了审核时间则判断是否在审核时间内
			if(getConfig("AuditBeginTime")!=null && getConfig("AuditBeginTime").length()>0){
				java.sql.Time AuditBeginTime=Tool.convertTime(getConfig("AuditBeginTime"));
				java.sql.Time curSqlTime=Tool.convertTime(Tool.fmtOnlyTime(curTime));
				if(AuditBeginTime!=null && AuditBeginTime.after(curSqlTime)){
					//出金审核时间未到-20018
					result = ErrorCode.outMoneyAudit_ErrorTimeUn;
					log("审核时间未到！");
					return result;
				}
			}
			//如果设置了审核时间则判断是否在审核时间内
			if(getConfig("AuditEndTime")!=null && getConfig("AuditEndTime").length()>0){
				java.sql.Time AuditEndTime=Tool.convertTime(getConfig("AuditEndTime"));
				java.sql.Time curSqlTime=Tool.convertTime(Tool.fmtOnlyTime(curTime));
				if(AuditEndTime!=null && curSqlTime.after(AuditEndTime)){
					//出金审核时间已过-20017
					result = ErrorCode.outMoneyAudit_ErrorTimeOut;
					log("审核时间已过！");
					return result;
				}
			}
			try{
				conn = DAO.getConnection();
				//获取资金流水对象根据流水列表
				Vector<CapitalValue> list = DAO.getCapitalInfoList("where actionid="+actionID, conn);
				if(list.size()>0){
					for(CapitalValue cv : list){
						if(cv.type == OUTMONEY || cv.type == INMONEY){
							capital = cv;
						}else if(cv.type == RATE){
							rate = cv;
						}
					}
				}
				if(capital != null){
					if(capital.status!=13){
						result=ErrorCode.Audit_Arraidy;
						log(auditInfo+"当笔资金流水已经受理完成，错误码="+result);
						return result;
					}
					if(funFlag){//审核通过
						try {
							//启动数据库事务
							conn.setAutoCommit(false);
							/**如果银行端处理失败则进行市场端数据(数据库数据，内存数据)回滚*/
							result = 0;
							if(capital.type==OUTMONEY){
								dataProcess.updateFundsFull(capital.firmID, dataProcess.getOUTSUMMARY()+"", dataProcess.getBANKSUB().get(capital.bankID), capital.money, actionID,conn);//扣除出金
								dataProcess.updateFundsFull(capital.firmID, dataProcess.getFEESUMMARY()+"", null, rate.money,actionID, conn);//扣除手续费
								dataProcess.updateFrozenFunds(capital.firmID, -1*Arith.add(capital.money, rate.money), conn);//资金解冻
								//修改交易商银行接口冻结资金
								this.bankFrozenFuns(capital.firmID, capital.bankID, null, -1*Arith.add(capital.money, rate.money), conn);
								DAO.modCapitalInfoStatus(capital.iD,rVal.funID, 0,curTime, conn);
								DAO.modCapitalInfoStatus(rate.iD,rVal.funID, 0,curTime, conn);
								DAO.modCapitalInfoNote(capital.iD, "["+capital.note+"]审核通过，出金成功", conn);
								DAO.modCapitalInfoNote(rate.iD, "["+rate.note+"]审核通过，出金成功", conn);
							}else if(capital.type==INMONEY){
								DAO.modCapitalInfoStatus(capital.iD,rVal.funID, 0,curTime, conn);
								DAO.modCapitalInfoStatus(rate.iD,rVal.funID, 0,curTime, conn);
								DAO.modCapitalInfoNote(capital.iD, "["+capital.note+"]审核通过，入金成功", conn);
								DAO.modCapitalInfoNote(rate.iD, "["+rate.note+"]审核通过，入金成功", conn);
								
								if(result>=0){
									if(Tool.getConfig("inMoneyNoAdapterAddMoney").equals("true")){
										dataProcess.updateFundsFull(capital.firmID, dataProcess.getINSUMMARY()+"", dataProcess.getBANKSUB().get(capital.bankID), capital.money,result, conn);//添加入金
										dataProcess.updateFundsFull(rate.firmID, dataProcess.getFEESUMMARY()+"", null, rate.money,result, conn);//扣除手续费
										log("假银行入金，添加资金凭证成功");
									}else{
										log("假银行入金，流水写入成功，inMoneyNoAdapterAddMoney为false，不添加资金凭证");
									}
								}else{
									log("假银行入金，添加写入流水失败");
								}
								
							}
							conn.commit();
						}catch(SQLException e){
							e.printStackTrace();
							conn.rollback();
							//审核出金SQLException，数据回滚，需要手工处理出金和手续费-20013
							result = ErrorCode.outMoneyAudit_PassDataBaseException;
							log(auditInfo+"审核出金SQLException，数据回滚，需要手工处理出金和手续费，错误码="+result+"  "+e);
						}finally{
							conn.setAutoCommit(true);
						}
						/**如果调用交易系统接口失败则将转帐模块资金流水记录置为失败状态*/
						if(result == ErrorCode.outMoneyAudit_PassDataBaseException){//流水状态修改为1：审核过程中出现异常
							DAO.modCapitalInfoStatus(capital.iD,rVal.funID, 1,curTime, conn);
							DAO.modCapitalInfoStatus(rate.iD,rVal.funID, 1,curTime, conn);
							DAO.modCapitalInfoNote(capital.iD, "["+capital.note+"]审核通过，系统异常，需要手工处理出入金", conn);
							DAO.modCapitalInfoNote(rate.iD, "["+rate.note+"]审核通过，系统异常，需要手工处理出入金", conn);
						}
					}else{//拒绝出金处理
						try{
							//启动数据库事务
							conn.setAutoCommit(false);
							if(capital.type==OUTMONEY){
								//调用交易系统接口
								dataProcess.updateFrozenFunds(capital.firmID, -1*Arith.add(capital.money, rate.money), conn);//资金解冻
								//修改交易商银行接口冻结资金
								this.bankFrozenFuns(capital.firmID, capital.bankID, null, -1*Arith.add(capital.money, rate.money), conn);
								DAO.modCapitalInfoStatus(capital.iD,rVal.funID, 1,curTime, conn);
								DAO.modCapitalInfoStatus(rate.iD,rVal.funID, 1,curTime, conn);
								DAO.modCapitalInfoNote(capital.iD, "["+capital.note+"]审核拒绝成功", conn);
								DAO.modCapitalInfoNote(rate.iD, "["+rate.note+"]审核拒绝成功", conn);
							}else if(capital.type==INMONEY){
								DAO.modCapitalInfoStatus(capital.iD,rVal.funID, 1,curTime, conn);
								DAO.modCapitalInfoStatus(rate.iD,rVal.funID, 1,curTime, conn);
								DAO.modCapitalInfoNote(capital.iD, "["+capital.note+"]审核拒绝成功", conn);
								DAO.modCapitalInfoNote(rate.iD, "["+rate.note+"]审核拒绝成功", conn);
							}
							conn.commit();
						}catch(SQLException e){
							e.printStackTrace();
							conn.rollback();
							//拒绝出金SQLException，数据回滚，需要手工处理出金和手续费-20014
							result = ErrorCode.outMoneyAudit_RefuseDataBaseException;
							log(auditInfo+"拒绝出金SQLException，数据回滚，需要手工处理出金和手续费，错误码="+result+"  "+e);
						}finally{
							conn.setAutoCommit(true);
						}
						//如果调用交易系统接口失败则将转帐模块资金流水记录置为失败状态
						if(result == ErrorCode.outMoneyAudit_RefuseDataBaseException){
							//流水状态修改为1：审核员拒绝出金后 退出金出错
							DAO.modCapitalInfoStatus(capital.iD,rVal.funID, 1,curTime, conn);
							DAO.modCapitalInfoStatus(rate.iD,rVal.funID, 1,curTime, conn);
							DAO.modCapitalInfoNote(capital.iD, "["+capital.note+"]审核拒绝出金,资金解冻出错，需要手工处理出金和手续费", conn);
							DAO.modCapitalInfoNote(rate.iD, "["+rate.note+"]审核拒绝出金,资金解冻出错，需要手工处理出金和手续费", conn);
						}
					}
				}else{
					
				}
			}catch (SQLException e) {
				result =ErrorCode.outMoney_DataBaseException;
				log(auditInfo+"审核时数据库异常    "+e);
			} catch (ClassNotFoundException e) {
				result = ErrorCode.rgstAccount_SysException;
				log(auditInfo+"数据库连接异常    "+e);
			}finally{
				DAO.closeStatement(null, null, conn);
			}
			return result;
		}
		/**
		 * 修改交易商信息(手工出入金)
		 */
		public ReturnValue modAccountNoAdapter(CorrespondValue correspondValue){
			System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
			System.out.println("修改交易商信息(手工出入金)");
			System.out.println(correspondValue.toString());
			System.out.println("}}}}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}}}}}");
			ReturnValue result = new ReturnValue();
			if(correspondValue.bankID==null || correspondValue.bankID.length()==0 ||
					correspondValue.firmID==null || correspondValue.firmID.length()==0 ||
					correspondValue.account==null || correspondValue.account.length()==0
			){
				result.result = ErrorCode.delAccount_InfoHalfbaked;
				result.remark = "信息不完整";
				return result;
			}
			try{
				DAO.modCorrespond(correspondValue);
			}catch(Exception e){
				e.printStackTrace();
				result.remark = "修改交易商账号时出现异常";
			}
			return result;
		}
		/**
		 * 银行帐号注销(手工出入金)
		 * @param bankID 银行代码
		 * @param firmID 交易商代码
		 * @param bankAccount 银行帐号
		 * @return int 操作结果：0 成功  非0 失败(1:信息不完整 2：帐号未注册 3：银行解约失败 4：数据库操作失败 5：系统异常)
		 * @throws 
		 */
		public long delAccountNoAdapter(CorrespondValue correspondValue){
			System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
			System.out.println("银行帐号注销(手工出入金)");
			System.out.println(correspondValue.toString());
			System.out.println("}}}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}}}}}}");
			long result = 0;
			Connection conn = null;
			if(correspondValue.bankID==null || correspondValue.bankID.length()==0 ||
					correspondValue.firmID==null || correspondValue.firmID.length()==0 ||
					correspondValue.account==null || correspondValue.account.length()==0
			)
				return ErrorCode.delAccount_InfoHalfbaked;
			try 
			{
				conn = DAO.getConnection();
				Vector<CorrespondValue> vector =DAO.getCorrespondList(" where bankID='"+correspondValue.bankID+"' and firmID='"+correspondValue.firmID+"' and account='"+correspondValue.account+"'",conn);
				if(vector.size()<=0)
				{
					//帐号未注册
					result = ErrorCode.delAccount_NORgsAcount;
					log("银行帐号注销，帐号未注册，错误码="+result);
				}
				else 
				{
					DAO.delCorrespond(correspondValue, conn);
				}
			} 
			catch(SQLException e) 
			{
				e.printStackTrace();
				result = ErrorCode.delAccount_DatabaseException;
				log("银行帐号注销SQLException，错误码="+result+"  "+e);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				result = ErrorCode.delAccount_SysException;
				log("银行帐号注销Exception，错误码="+result+"  "+e);
			}
			finally
			{
				DAO.closeStatement(null, null, conn);
			}
			return result;
		}
		/**接口参数合法性检查方法 返回 long 0:合法  <0:不合法*/
		private long checkArgs(Connection conn,String maketbankID,double money,String firmID,String bankAccount,int type) throws SQLException{
			System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
			System.out.println("手工出入金接口合法性检查");
			System.out.println("maketbankID["+maketbankID+"]money["+money+"]firmID["+firmID+"]bankAccount["+bankAccount+"]type["+type+"]");
			System.out.println("}}}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}}}}}}");
			long result = 0;
			//取得交易商当前资金
			double realFunds=dataProcess.getRealFunds(firmID, conn);
			//取得出金手续费
			double outRate = getTransRate(maketbankID, firmID, money, OUTMONEY,0,bankAccount,conn);
			System.out.println("realFunds="+realFunds);
			if(firmID == null || firmID.trim().equals("")){
				//参数非法
				result = ErrorCode.outMoney_ErrorParameter;
				log("市场发起出金，参数非法，交易商代码为空，错误码="+result);
			}
			if(type==INMONEY && (maketbankID == null || maketbankID.trim().equals(""))){
				//参数非法
				result = ErrorCode.outMoney_ErrorParameter;
				log("市场发起出金，参数非法，市场银行编号为空，错误码="+result);
			}
			if(type==OUTMONEY && realFunds<money+outRate){
				result=ErrorCode.outMoney_ErrorLackFunds;
				log("市场发起出金，资金余额不足，错误码="+result);
			}
			return result;
		}
		/**
		 * result:市场流水号 checkTrans
		 */
		private HashMap<Integer,Long> handleOUtMoney(long result,Connection conn,long checkTrans,String maketbankID,
				double money,String firmID,int express,int type,String bankName,String account) throws SQLException{
			System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
			System.out.println("手工出入金时添加出金信息");
			System.out.println("result["+result+"]checkTrans["+checkTrans+"]maketbankID["+maketbankID+"]");
			System.out.println("}}}}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}}}}}");
			HashMap<Integer,Long> map = new HashMap<Integer,Long>();
			long capitalID = 0;
			long rateID = 0;
				//取得市场业务流水号
				result = DAO.getActionID(conn);
				//取得出金手续费
				double outRate = getTransRate(maketbankID, firmID, money, OUTMONEY,0,account,conn);
				try {
					conn.setAutoCommit(false);//启动数据库事务
					CapitalValue cVal = new CapitalValue();//记录转帐模块资金流水
					cVal.actionID = result;//市场流水号
					cVal.bankID = maketbankID;
					cVal.creditID = dataProcess.getBANKSUB().get(maketbankID);
					cVal.debitID = firmID;//借方科目代码
					cVal.firmID = firmID;//交易商代码
					cVal.money = money;//金额
					cVal.oprcode = dataProcess.getOUTSUMMARY()+"";//业务代码
					cVal.status = 13;//状态：0：成功 1：失败 13:待审核
					cVal.type = OUTMONEY;//类型：0 入金 1 出金2 出入金手续费 3 其他资金划转
					cVal.express=express;//是否加急：0：正常 1：加急
					cVal.funID = "";//银行业务流水号
					cVal.note = "market_out";//备注信息
					cVal.bankName=bankName;//交易商银行
					cVal.account=account;//交易商银行账号
					capitalID = DAO.addCapitalInfo(cVal, conn);//增加出金流水记录 返回流水Id
					
					cVal.creditID = "Market";
					cVal.debitID = firmID;
					cVal.money = outRate;
					cVal.oprcode = dataProcess.getFEESUMMARY()+"";
					cVal.type = RATE;

					rateID = DAO.addCapitalInfo(cVal, conn);//手续费流水
					//调用交易系统接口
					dataProcess.updateFrozenFunds(firmID, Arith.add(money,outRate), conn);
					//银行接口表冻结资金
					this.bankFrozenFuns(firmID, maketbankID, account, Arith.add(money,outRate), conn);
					conn.commit();
				}catch(SQLException e){
					e.printStackTrace();
					conn.rollback();
					//记录资金流水错误-20038
					result=ErrorCode.outMoney_ErrorAddCapital;
					log("市场发起出金SQLException,数据回滚"+e);
					throw e;
				}finally{
					conn.setAutoCommit(true);
				}
				map.put(1, result);
				map.put(2, capitalID);
				map.put(3, rateID);
			return map;
		}

		/**
		 * 判断交易商是否可以解约
		 * @param firmID 交易商代码
		 * @param bankID 银行编号
		 * @return ReturnValue
		 */
		public ReturnValue ifQuitFirm(String firmID,String bankID){
			ReturnValue result = new ReturnValue();
			try{
				Vector<FirmFundsValue> ffvs = DAO.getFrimFunds(firmID, new java.util.Date());
				if(ffvs == null || ffvs.size()<=0){
					result.result = -1;
					result.remark = "未取得到交易商["+firmID+"]资金信息";
				}else if(ffvs.size() != 1){
					result.result = -1;
					result.remark = "取得交易商["+firmID+"]资金信息重复";
				}else{
					FirmFundsValue ffv = ffvs.get(0);
					if(ffv == null){
						result.result = -1;
						result.remark = "查询交易系统资金情况时失败";
					}else{
						if(ffv.inMoney != 0){
							result.result = -1;
							result.remark = "当天有入金不能解约";
						}else if(ffv.margin != 0){
							result.result = -1;
							result.remark = "交易商有保证金不能解约";
						}else if(ffv.outMoney != 0){
							result.result = -1;
							result.remark = "当天有出金不允许解约";
						}else if(ffv.runtimeMargin != 0){
							result.result = -1;
							result.remark = "远期保证金不为0不能解约";
						}else if(ffv.settleMargin != 0){
							result.result = -1;
							result.remark = "交收保证金不为0不能解约";
						}else if(ffv.todayFunds != 0){
							result.result = -1;
							result.remark = "当前资金不为0不能解约";
						}else if(ffv.yesFunds != 0){
							result.result = -1;
							result.remark = "上日资金不为0不能解约";
						}else if(ffv.zvMargin != 0){
							result.result = -1;
							result.remark = "现货竞价保证金不为0不能解约";
						}else{
							Vector<CapitalValue> cvs = DAO.getCapitalInfoList(" where firmid='"+firmID+"' and trunc(createtime)=trunc(sysdate)");
							if(cvs != null && cvs.size()>0){
								result.result = -1;
								result.remark = "当天有转账流水不允许解约";
							}
						}
					}
				}
			}catch(SQLException e){
				result.result = -1;
				result.remark = "取得交易商["+firmID+"]信息数据库异常";
				e.printStackTrace();
			}catch(Exception e){
				result.result = -2;
				result.remark = "取得交易商["+firmID+"]信息系统异常";
				e.printStackTrace();
			}
			return result;
		}
//*********************************深发展银行订制使用********************************************

	/**
	 * 银行发起绑定，市场将银行子账号和子账号名称等添加进去
	 * @param cv
	 * @return
	 */
	public ReturnValue relevanceAccount(CorrespondValue cv){
		System.out.println("{{{{{{{{{{{{{{{{{{{{{银行发起绑定，市场将银行子账号和子账号名称等添加进去{{{{{{{{{{{{{{{{{{{{{{{{");

		System.out.println(cv.toString());
		System.out.println("}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}");
		ReturnValue rv = new ReturnValue();
		if(cv == null){
			rv.result = ErrorCode.rgstAccount_InfoHalfbaked;
			rv.remark = "银行发起绑定，传入的信息包为空";
		} else {
			if(cv.account1==null){
				rv.result = ErrorCode.rgstAccount_InfoHalfbaked;
				rv.remark = ErrorCode.error.get(ErrorCode.rgstAccount_InfoHalfbaked);
			}else if(cv.firmID==null){
				rv.result = ErrorCode.rgstAccount_NOFirm;
				rv.remark = ErrorCode.error.get(ErrorCode.rgstAccount_NOFirm);
			}
		}
		try{
			rv.result = DAO.openCorrespond(cv);
		}catch(Exception e){
			rv.result = ErrorCode.rgstAccount_SysException;
			rv.remark = "处理器修改交易商信息异常";
			e.printStackTrace();
		}

		return rv;
	}
	/**添加会员开销户信息*/
	public ReturnValue saveFirmKXH(Vector<KXHfailChild> vector,String bankID){
		log("添加会员开销户信息，时间:"+Tool.fmtTime(new java.util.Date()));
		ReturnValue rv = new ReturnValue();
		if(vector==null){
			rv.result = -1;
			rv.remark = "添加会员开销户信息，传入的参数为空";
		}else{
			for(KXHfailChild kxh : vector){
				try{
					KXHfailChild vv = DAO.getFirmKXH(kxh.funID);
					if(vv == null){
						DAO.addFirmKXH(kxh,bankID);
					}
				}catch(SQLException e){
					e.printStackTrace();
					log("添加数据SQL异常：\n"+kxh.toString());
				}catch(Exception e){
					e.printStackTrace();
					log("添加数据系统异常：\n"+kxh.toString());
				}
			}
		}
		log("返回结果：\n"+rv.toString());
		return rv;
	}
	/**添加会员开销户信息*/
	public ReturnValue saveFirmKXH(java.util.Date date,String bankID){
		log("添加会员开销户信息市场去取，时间:"+Tool.fmtTime(new java.util.Date()));
		log("参数：date["+Tool.fmtDate(date)+"]bankID["+bankID+"]");
		ReturnValue rv = new ReturnValue();
		BankAdapterRMI bankadapter = this.getAdapter(bankID);
		try{
			if(bankadapter != null){
				Vector<KXHfailChild> vector = bankadapter.getFirmODInfo(date);
				rv = this.saveFirmKXH(vector,bankID);
			}else{
				rv.result = ErrorCode.GetAdapter_Error;
				rv.remark = "处理器获取适配器失败";
			}
		}catch(Exception e){
			e.printStackTrace();
			rv.result = -2;
			rv.remark = "添加会员开销户信息，系统异常";
		}
		log("返回结果：\n"+rv.toString());
		return rv;
	}
	/** 发送市场清算文件 */
	public ReturnValue sentMaketQS(java.util.Date date,String bankID){
		log("发送市场清算文件  sentMaketQS  时间："+Tool.fmtTime(new java.util.Date()));
		log("参数：date["+Tool.fmtDate(date)+"]bankID["+bankID+"]");
		ReturnValue rv = new ReturnValue();
		try{
			rv = this.isTradeDate(date);
			if(rv.result != 0){
				return rv;
			}else if(! getTraderStatus()){
				rv.result = -2;
				rv.remark = "交易系统尚未结算，不能发送清算数据";
				return rv;
			}
		}catch(Exception e){
			e.printStackTrace();
			log(e.getMessage());
			rv.result = -1;
			rv.remark = "获取当天是否为交易日失败";
			return rv;
		}
		Connection conn = null;
		if(!Tool.fmtDate(new java.util.Date()).equalsIgnoreCase(Tool.fmtDate(date)) || getTraderStatus()) {
			BatQs bq = new BatQs();
			try{
				//获取文件信息
				bq.child = this.getQSChild(date, bankID);
				bq.rowCount = bq.child.size();
				BankAdapterRMI bankadapter = this.getAdapter(bankID);
				if(bankadapter != null){
					ReturnValue rrv = bankadapter.sentMaketQS(bq);
					if(rrv.result >= 0){
						conn = DAO.getConnection();
						BankQSVO bq2 = new BankQSVO();
						bq2.bankID = bankID;
						bq2.tradeDate = date;
						bq2.note = "深发展银行清算";
						DAO.addBankQS(bq2, conn);
						rv.result = 0 ;
						rv.remark = "发送给银行清算文件成功";
					}else{
						rv.result = rrv.result;
						rv.remark = rrv.remark;
					}
				}else{
					rv.result = ErrorCode.GetAdapter_Error;
					rv.remark = "发送市场清算文件，处理器获取适配器失败";
				}
			}catch(Exception e){
				e.printStackTrace();
				rv.result = -2;
				rv.remark = "发送市场清算文件异常";
			}finally{
				DAO.closeStatement(null, null, conn);
			}
			log("返回结果：\n"+rv.toString());
		} else {
			log("银行未结算，无法发送市场清算文件  sentMaketQS  时间："+Tool.fmtTime(new java.util.Date()));
			rv.result = -2;
			rv.remark = "银行未结算，无法发送清算文件" ;
		}
		return rv;
	}
	/**获取某天的清算信息*/
	private Vector<BatQsChild> getQSChild(java.util.Date date,String bankID){
		log("获取清算信息  getQSChild  时间："+Tool.fmtTime(new java.util.Date()));
		try{
			ReturnValue rv = this.isTradeDate(date);
			if(rv.result != 0){
				log(rv.toString());
				return null;
			}else if(Tool.fmtDate(new java.util.Date()).equalsIgnoreCase(Tool.fmtDate(date)) && ! getTraderStatus()){
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
			log(e.getMessage());
			return null;
		}
		Vector<BatQsChild> result = new Vector<BatQsChild>();
		Map<String,BatQsChild> today = null;
		Connection conn = null;
		boolean flag = true;
		java.util.Date usdate = date;
		try{
			conn = DAO.getConnection();
			today = DAO.getQSChild(bankID, null,null, usdate, conn);
			System.out.println(today.size());
			Set<String> notFirmIDs = new HashSet<String>();
			Set<String> firmIDs = null;
			java.util.Date lastQSDate = DAO.getMaxBankQSList(bankID,usdate, conn);
			while(flag){//获取连续的前一天或几天的对账不成功的交易商的对账信息
				//获取修改当天签约的交易商的昨日余额为0
				String[] toBankFirmIDs = null;
				if(firmIDs != null && firmIDs.size()>0){
					toBankFirmIDs = new String[firmIDs.size()];
					Iterator<String> it = firmIDs.iterator();
					int num = 0;
					while(it.hasNext()){
						toBankFirmIDs[num++] = it.next();
					}
				}
				Vector<KXHfailChild> toBank = DAO.getBankOpen(bankID, toBankFirmIDs, 1, usdate);
				usdate = DAO.getlastDate(usdate, conn);//取得交易网的上一个交易日
				if(usdate == null){
					flag = false;//如果没有上个交易日则退出循环
				}else{
					if(toBank != null && toBank.size()>0){
						Set<String> firms = new HashSet<String>();
						for(int i = 0 ; i < toBank.size();i++){
							firms.add(toBank.get(i).firmID);
							notFirmIDs.add(toBank.get(i).firmID);
						}
						Map<String,BatQsChild> lastdate = DAO.getQSChild(bankID, firms,null, usdate, conn);
						Iterator<Map.Entry<String, BatQsChild>> it = lastdate.entrySet().iterator();
						while(it.hasNext()){
							Map.Entry<String, BatQsChild> ent = it.next();
							BatQsChild yestodaych = ent.getValue();
							BatQsChild todaych = today.get(ent.getKey());
							if(todaych != null){
								
								todaych.ProfitAmount += yestodaych.NewBalance;//将上个交易日的市场余额加入到盈利中
//								todaych.ProfitAmount += todaych.UnfreezeAmount;//将解冻加入到盈利中
//								todaych.UnfreezeAmount = 0;//将解冻置成0
							}
						}
					}
					if(lastQSDate != null && usdate.after(lastQSDate)){//将未清算的交易日交易信息放到当天的清算表中
						Map<String,BatQsChild> lastdate = DAO.getQSChild(bankID, firmIDs,notFirmIDs, usdate, conn);
						Iterator<Map.Entry<String, BatQsChild>> it = lastdate.entrySet().iterator();
						while(it.hasNext()){
							Map.Entry<String, BatQsChild> ent = it.next();
							String key = ent.getKey();
							BatQsChild todays = today.get(key);
							if(todays != null){
								BatQsChild last = ent.getValue();
								todays.AddTranAmount += last.AddTranAmount;
								todays.CutTranAmount += last.CutTranAmount;
								todays.FreezeAmount += last.FreezeAmount;
								todays.UnfreezeAmount += last.UnfreezeAmount;
								todays.LossAmount += last.LossAmount;
								todays.ProfitAmount += last.ProfitAmount;
								todays.TranCount += last.TranCount;
								todays.TranHandFee += last.TranHandFee;
								todays.toDhykAmount += last.toDhykAmount;
								todays.yeDhykAmount += last.yeDhykAmount;
								todays.toQianAmount += last.toQianAmount;
								todays.yeQianAmount += last.yeQianAmount;
							}
						}
					}else{//将清算过的但是失败的人的交易信息放到当天的交易表中
						if(lastQSDate != null){
							lastQSDate = DAO.getMaxBankQSList(bankID,lastQSDate, conn);
						}
						String[] str = null;
						if(firmIDs != null && firmIDs.size()>0){
							str = new String[firmIDs.size()];
							Iterator<String> it = firmIDs.iterator();
							int i=0;
							while(it.hasNext()){
								str[i++] = it.next();
							}
						}
						Vector<BatFailResultChild> bfr = DAO.getFirmBalanceError(str, bankID, usdate);//取得上个交易日的清算失败文件
						Vector<BatCustDzFailChild> bcd = DAO.getBatCustDz(str, bankID, usdate);//取得上个交易日的清算不平文件
						if((bfr==null || bfr.size()==0) && (bcd==null || bcd.size()==0)){//如果两个文件上一天都没有信息
							flag = false;
						}else{
							firmIDs = new HashSet<String>();
							if(bfr != null && bfr.size()>0){
								for(BatFailResultChild ch : bfr){
									firmIDs.add(ch.ThirdCustId);
								}
							}
							if(bcd != null && bcd.size()>0){
								for(BatCustDzFailChild ch : bcd){
									firmIDs.add(ch.ThirdCustId);
								}
							}
							Map<String,BatQsChild> lastdate = DAO.getQSChild(bankID, firmIDs,notFirmIDs, usdate, conn);
							Iterator<Map.Entry<String, BatQsChild>> it = lastdate.entrySet().iterator();
							while(it.hasNext()){
								Map.Entry<String, BatQsChild> ent = it.next();
								String key = ent.getKey();
								BatQsChild todays = today.get(key);
								if(todays != null){
									BatQsChild last = ent.getValue();
									todays.AddTranAmount += last.AddTranAmount;
									todays.CutTranAmount += last.CutTranAmount;
									todays.FreezeAmount += last.FreezeAmount;
									todays.UnfreezeAmount += last.UnfreezeAmount;
									todays.LossAmount += last.LossAmount;
									todays.ProfitAmount += last.ProfitAmount;
									todays.TranCount += last.TranCount;
									todays.TranHandFee += last.TranHandFee;
									todays.toDhykAmount += last.toDhykAmount;
									todays.yeDhykAmount += last.yeDhykAmount;
									todays.toQianAmount += last.toQianAmount;
									todays.yeQianAmount += last.yeQianAmount;
								}
							}
						}
					}
				}
			}
			
			//遍历今天该发送的信息生成信息组
			Iterator<Map.Entry<String, BatQsChild>> it = today.entrySet().iterator();
			while (it.hasNext()){
				Map.Entry<String, BatQsChild> ent = (Map.Entry<String, BatQsChild>) it.next();
				result.add(ent.getValue());
			}
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DAO.closeStatement(null, null, conn);
		}
			System.out.println("返回信息条数："+result.size());
		return result;
	}
	/**获取银行发送来的对账不平文件*/
	public ReturnValue getBatCustDz(java.util.Date date,String bankID) {
		log("获取银行发送来的对账不平文件  getBatCustDz  时间："+Tool.fmtTime(new java.util.Date()));
		log("参数：date["+Tool.fmtDate(date)+"]bankID["+bankID+"]");
		ReturnValue rv = new ReturnValue();
		BankAdapterRMI bankadapter = this.getAdapter(bankID);
		if(bankadapter != null){
			try{
				Vector<BatCustDzFailChild> bcd = bankadapter.getBatCustDz(date);
				if(bcd==null){
					rv.result = -2;
					rv.remark = "获取银行对账不平文件，文件不存在";
				}else if(bcd.size()==0){
					rv.result = 0;
					rv.remark = "对账不平文件为空，对账成功";
				}else{
					rv = this.getBatCustDz(bcd, date, bankID);
				}
			}catch(Exception e){
				e.printStackTrace();
				rv.result = -2;
				rv.remark = "获取银行对账不平文件异常";
			}
		}else{
			rv.result = ErrorCode.GetAdapter_Error;
			rv.remark = "获取银行发送来的对账不平文件，处理器获取适配器失败";
		}
		log("返回结果：\n"+rv.toString());
		return rv;
	}
	/**获取银行发送来的对账不平文件*/
	public ReturnValue getBatCustDz(Vector<BatCustDzFailChild> bcd,java.util.Date date,String bankID) {
		log("获取银行发送来的对账不平文件  getBatCustDz  时间："+Tool.fmtTime(new java.util.Date()));
		ReturnValue rv = new ReturnValue();
		if(bcd == null){
			rv.result = -1;
			rv.remark = "传入的对账不平信息为空";
		}else if(bcd.size()==0){
			rv.result = 0;
			rv.remark = "传入的对账不平文件为空，银行对账成功";
		}else{
			try{
				DAO.delBatCustDz(date, bankID);
				for(BatCustDzFailChild cd : bcd){
					DAO.addBatCustDz(cd, date, bankID);
				}
				rv.result = 0;
				rv.remark = "传入的对账不平文件不为空，信息已经添加入数据库中";
			}catch(SQLException e){
				rv.result = -1;
				rv.remark = "修改对账不平信息，数据库异常";
				e.printStackTrace();
			}catch(Exception e){
				rv.result = -1;
				rv.remark = "修改对账不平信息，系统异常";
				e.printStackTrace();
			}
		}
		log("返回结果：\n"+rv.toString());
		return rv;
	}
	/**获取银行发送来的会员余额文件*/
	public ReturnValue getfirmBalanceFile(java.util.Date date,String bankID) {
		log("获取银行发送来的会员余额文件  getfirmBalanceFile  时间："+Tool.fmtTime(new java.util.Date()));
		log("参数：date["+Tool.fmtDate(date)+"]bankID["+bankID+"]");
		ReturnValue rv = new ReturnValue();
		BankAdapterRMI bankadapter = this.getAdapter(bankID);
		if(bankadapter != null){
			try{
				Vector<BatCustDzBChild> fbf = bankadapter.getfirmBalanceFile(date);
				if(fbf==null){
					rv.result = -2;
					rv.remark = "获取银行发送来的会员余额文件，文件不存在";
				}else if(fbf.size()==0){
					rv.result = 0;
					rv.remark = "银行发送来的会员余额文件为空";
				}else {
					rv = this.getfirmBalanceFile(fbf, date, bankID);
				}
			}catch(Exception e){
				e.printStackTrace();
				rv.result = -2;
				rv.remark = "获取银行发送来的会员余额文件异常";
			}
		}else{
			rv.result = ErrorCode.GetAdapter_Error;
			rv.remark = "获取银行发送来的会员余额文件，处理器获取适配器失败";
		}
		log("返回结果：\n"+rv.toString());
		return rv;
	}
	/**获取银行发送来的会员余额文件*/
	public ReturnValue getfirmBalanceFile(Vector<BatCustDzBChild> fbf,java.util.Date date,String bankID) {
		log("获取银行发送来的会员余额文件  getfirmBalanceFile  时间："+Tool.fmtTime(new java.util.Date()));
		ReturnValue rv = new ReturnValue();
		if(fbf==null){
			rv.result = -1;
			rv.remark = "获取银行发送来的会员余额文件，传入的信息为空";
		}else if(fbf.size()==0){
			rv.result = -1;
			rv.remark = "获取银行发送来的会员余额文件，余额文件为空";
		}else{
			try{
				for(BatCustDzBChild bc : fbf){
					Vector<BatCustDzBChild> vv = DAO.getFirmBalanceFile(bc.ThirdCustId, bankID, date);
					if(vv != null && vv.size()>0){
						DAO.modFirmBalanceFile(bc, date, bankID);
					}else{
						DAO.addFirmBalanceFile(bc, date, bankID);
					}
				}
				rv.result = 0;
				rv.remark = "获取银行发送来的会员余额文件，市场保存信息成功";
			}catch(SQLException e){
				rv.result = -1;
				rv.remark = "获取银行发送来的会员余额文件，数据库异常";
			}catch(Exception e){
				rv.result = -1;
				rv.remark = "获取银行发送来的会员余额文件，系统异常";
			}
		}
		log("返回结果：\n"+rv.toString());
		return rv;
	}
	/**获取银行交易商对账失败文件*/
	public ReturnValue getFirmBalanceError(java.util.Date date,String bankID) {
		log("获取银行交易商对账失败文件  getFirmBalanceError  时间："+Tool.fmtTime(new java.util.Date()));
		log("参数：date["+Tool.fmtDate(date)+"]bankID["+bankID+"]");
		ReturnValue rv = new ReturnValue();
		BankAdapterRMI bankadapter = this.getAdapter(bankID);
		if(bankadapter != null){
			try{
				Vector<BatFailResultChild> fbe = bankadapter.getFirmBalanceError(date);
				//bankadapter
				if(fbe == null){
					rv.result = -2;
					rv.remark = "获取银行交易商对账失败文件，文件不存在";
				}else if(fbe.size()==0){
					rv.result = 0;
					rv.remark = "获取银行交易商对账失败文件为空";
				}else {
					rv = this.getFirmBalanceError(fbe, date, bankID);
				}
			}catch(Exception e){
				e.printStackTrace();
				rv.result = -2;
				rv.remark = "获取银行交易商对账失败文件异常";
			}
		}else{
			rv.result = ErrorCode.GetAdapter_Error;
			rv.remark = "获取银行交易商对账失败文件，处理器获取适配器失败";
		}
		log("返回结果：\n"+rv.toString());
		return rv;
	}
	/**获取银行交易商对账失败文件*/
	public ReturnValue getFirmBalanceError(Vector<BatFailResultChild> fbe,java.util.Date date,String bankID) {
		log("获取银行交易商对账失败文件  getFirmBalanceError  时间："+Tool.fmtTime(new java.util.Date()));
		ReturnValue rv = new ReturnValue();
		if(fbe == null){
			rv.result = -1;
			rv.remark = "获取银行交易商对账失败文件，银行文件不存在";
		}else if(fbe.size()<=0){
			rv.result = 0;
			rv.remark = "获取银行交易商对账失败文件，银行对账成功";
		} else {
			try{
				for(int i=0;i<fbe.size();i++){
					BatFailResultChild bfr = fbe.get(i);
					if(i==0){
						DAO.delFirmBalanceError(bankID, bfr.TranDateTime);
					}
					DAO.addFirmBalanceError(bfr, bankID);
				}
				rv.result = 0;
				rv.remark = "获取银行交易商对账失败文件，数据库保存成功";
			}catch(SQLException e){
				e.printStackTrace();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		log("返回结果：\n"+rv.toString());
		return rv;
	}
	/**查询银行生成文件的状态*/
	public ReturnValue getBankFileStatus(java.util.Date tradeDate,int type,String bankID){
		log("查询银行生成文件的状态  getBankFileStatus");
		log("参数：tradeDate["+Tool.fmtDate(tradeDate)+"]type["+type+"]bankID["+bankID+"]");
		ReturnValue rv = new ReturnValue();
		BankAdapterRMI bankadapter = this.getAdapter(bankID);
		if(bankadapter==null){
			rv.result = ErrorCode.GetAdapter_Error;
			rv.remark = "连接适配器时失败";
		}else{
			try{
				rv = bankadapter.getBankFileStatus(tradeDate, tradeDate, type);
			}catch(Exception e){
				e.printStackTrace();
				rv.result = ErrorCode.Bank_OtherError;
				rv.remark = "适配器查询银行文件生成状态时异常";
			}
		}
		return rv;
	}
//建设银行清算信息
	/**
	 * 修改银行流水号
	 */
	public ReturnValue modCapitalInfoStatus(long actionID,String funID){
		ReturnValue rv = new ReturnValue();
		Connection conn = null;
		try{
			conn = DAO.getConnection();
			DAO.modCapitalInfoStatus(actionID, funID, conn);
		}catch(SQLException e){
			e.printStackTrace();
			rv.result = -1;
			rv.remark = "修改 "+actionID+" 银行流水号 "+funID+" 数据库异常";
		}catch(Exception e){
			e.printStackTrace();
			rv.result = -1;
			rv.remark = "修改 "+actionID+" 银行流水号 "+funID+" 系统异常";
		}finally{
			DAO.closeStatement(null, null, conn);
		}
		return rv;
	}
	/**
	 * 给银行发送手续费和资金变化量
	 */
	public ReturnValue sentFirmBalance(String bankID,java.util.Date date){
		log("给银行发送手续费和资金变化量bankID["+bankID+"]date["+Tool.fmtDate(date)+"]");
		ReturnValue result = new ReturnValue();
		Connection conn = null;
		try{
			conn = DAO.getConnection();
			result = this.isTradeDate(date);
			if(result.result != 0){
				return result;
			}else if(Tool.fmtDate(new java.util.Date()).equalsIgnoreCase(Tool.fmtDate(date)) && ! getTraderStatus()){
				result.result = -2;
				result.remark = "当前系统尚未结算，禁止发送资金变化量";
				return result;
			}
			BankAdapterRMI bankadapter = this.getAdapter(bankID);
			if(bankadapter == null){
				result.result = ErrorCode.GetAdapter_Error;
				result.remark = "连接适配器["+bankID+"]失败";
				this.log(result.remark);
			}else{
				this.log("准备查询数据");
				Vector<FirmBalance> vector = DAO.getFirmBalance(bankID, null, date);
//				java.util.Date usdate = date;
//				java.util.Date lastQSDate = DAO.getMaxBankQSList(bankID,usdate, conn);
//				boolean flag = true;
//				while(flag){
//					usdate = DAO.getlastDate(usdate, conn);//取得交易网的上一个交易日
//					if(usdate == null){
//						flag = false;
//					}else{
//						if(lastQSDate != null && usdate.after(lastQSDate)){
//							Vector<FirmBalance> lasvec = DAO.getFirmBalance(bankID, null, usdate);
//							Map<String,FirmBalance> lasmap = new HashMap<String,FirmBalance>();
//							for(FirmBalance fb : lasvec){
//								if(fb != null){
//									lasmap.put(fb.firmID,fb);
//								}
//							}
//							for(FirmBalance fb : vector){
//								if(fb != null){
//									FirmBalance lasfb = lasmap.get(fb.firmID);
//									if(lasfb != null){
//										fb.FeeMoney += lasfb.FeeMoney;
//										fb.QYChangeMoney += lasfb.QYChangeMoney;
//									}
//								}
//							}
//						}else{
//							flag = false;
//						}
//					}
//				}
				this.log("取到数据了，准备发送数据");
				result = bankadapter.sendTradeDate(vector);
//				if(result != null && result.result == 0){
//					BankQSVO bq2 = new BankQSVO();
//					bq2.bankID = bankID;
//					bq2.tradeDate = date;
//					bq2.note = "建设银行清算";
//					DAO.addBankQS(bq2, conn);
//				}
				this.log("发送完毕了");
			}
		}catch(SQLException e){
			result.result = -1;
			result.remark = "给银行["+bankID+"]发送数据，数据库异常";
			this.log(result.remark);
			e.printStackTrace();
		}catch(Exception e){
			result.result = -2;
			result.remark = "给银行["+bankID+"]发送数据，系统异常";
			this.log(result.remark);
			e.printStackTrace();
		}finally{
			DAO.closeStatement(null, null, conn);
		}
		this.log("返回结果为\n"+result.toString());
		return result;
	}
	/**
	 * 银行查询市场流水
	 */
	public Map<String,CapitalValue> getCapitalValue(Vector<String> funids,String bankID){
		Map<String,CapitalValue> result = new HashMap<String,CapitalValue>();
		Connection conn = null;
		try{
			conn = DAO.getConnection();
			if(funids != null && funids.size()>0){
				for(String funid : funids){
					Vector<CapitalValue> vector = DAO.getCapitalInfoList(" where bankid='"+bankID+"' and funid='"+funid+"'",conn);
					if(vector != null && vector.size()>0){
						result.put(funid, vector.get(0));
					}
				}
			}
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}
	/**
	 * 保存银行清算结果信息
	 */
	public ReturnValue saveQSResult(String bankID,String tradeDate){
		this.log(">>>>保存银行清算结果信息>>>>时间："+Tool.fmtTime(new java.util.Date())+" 银行编号:"+bankID+">>>>清算日期"+tradeDate);
		ReturnValue result = new ReturnValue();
		BankAdapterRMI bankadapter = this.getAdapter(bankID);
		if(bankadapter == null){
			result.result = ErrorCode.GetAdapter_Error;
			result.remark = "保存银行["+bankID+"]清算结果数据，获取适配器服务失败";
		}else{
			try{
				Vector<QSRresult> qsResult = bankadapter.getQSRresult(Tool.strToDate(tradeDate));
				result = this.saveQSResult(qsResult);
			}catch(Exception e){
				result.result = -1;
				result.remark = "保存清算结果文件异常";
				e.printStackTrace();
			}
		}
		return result;
	}
	/**
	 * 保存银行清算结果信息
	 */
	public ReturnValue saveQSResult(Vector<QSRresult> qsResult){
		this.log(">>>>保存银行清算结果信息>>>>时间："+Tool.fmtTime(new java.util.Date())+"");
		ReturnValue result = new ReturnValue();
		boolean flag = true;
		Connection conn = null;
		if(qsResult != null){
			if(qsResult.size()<=0){
				result.remark = "保存银行清算结果，没有需要传入的数据。";
			}else{
				try{
					conn = DAO.getConnection();
					for(int i=0;i<qsResult.size();i++){
						QSRresult qs = qsResult.get(i);
						if(qs != null){
							this.log(qs.toString());
							if(qs.tradeDate == null){
								throw new SQLException("传入的数据类表中有数据的交易日期为空");
							}else{
								if(flag){//删除当天的本银行的信息
									flag = false;
									DAO.delQSResult(qs.bankID, qs.tradeDate, conn);
								}
								DAO.addQSResult(qs, conn);
							}
						}
					}
					result.remark = "保存银行清算结果数据成功";
				}catch(SQLException e){
					result.result = -1;
					result.remark = "保存银行清算结果，数据库异常";
					this.log(e.getMessage());
					e.printStackTrace();
				}catch(Exception e){
					result.result = -1;
					result.remark = "保存银行清算结果，系统异常";
					this.log(e.getMessage());
					e.printStackTrace();
				}finally{
					DAO.closeStatement(null, null, conn);
				}
			}
		}else{
			result.result = -1;
			result.remark = "保存银行清算结果，传入的信息为 null。";
		}
		return result;
	}
//**********************华夏银行清算定制*****************************************************
	/**
	 * 给银行发送市场交易商信息
	 * @parm bankID 银行编号
	 * @param firmIDs 交易商代码集
	 * @return ReturnValue
	 */
	public ReturnValue synchronousFirms(String bankID,String[] firmIDs){
		ReturnValue result = new ReturnValue();
		Connection conn = null;
		try{
			BankAdapterRMI bankadapter = this.getAdapter(bankID);
			if(bankadapter == null){
				result.result = ErrorCode.GetAdapter_Error;
				result.remark = "发送交易商信息到银行，获取适配器失败";
			}else{
				conn = DAO.getConnection();
				if(firmIDs != null && firmIDs.length>0){
					Vector<MarketFirmMsg> mfms = new Vector<MarketFirmMsg>();
					for(String firmID : firmIDs){
						if(firmID != null && firmID.trim().length()>0){
							Vector<CorrespondValue> cvs = DAO.getCorrespondList(" where firmid='"+firmID.trim()+"' and bankID='"+bankID.trim()+"'", conn);
							RZDZValue rzdz = DAO.getXYDZValue(bankID, new String[]{firmID.trim()}, DAO.getlastDate(new java.util.Date(), conn));
							if(cvs != null && cvs.size()==1 && rzdz != null && rzdz.getFdv() != null && rzdz.getFdv().size()==1){
								CorrespondValue cv = cvs.get(0);
								FirmDZValue frv = rzdz.getFdv().get(0);
								MarketFirmMsg mfm = new MarketFirmMsg();
								mfm.account = cv.account;
								mfm.accountName = cv.accountName;
								mfm.balance = frv.availableBalance;
								mfm.bankID = bankID;
								mfm.card = cv.card;
								mfm.cardType = cv.cardType;
								mfm.firmID = cv.firmID;
								mfm.frozen = frv.cash;
								mfm.right = frv.firmRights;
								mfms.add(mfm);
							}
						}
					}
					result = bankadapter.synchronousFirms(mfms);
				}
			}
		}catch(SQLException e){
			result.result = -1;
			result.remark = "同步交易商信息，数据库异常";
			e.printStackTrace();
		}catch(Exception e){
			result.result = -2;
			result.remark = "同步交易商信息，系统异常";
			e.printStackTrace();
		}finally{
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}
	/**后台调用处理器发送清算方法*/
	public ReturnValue hxSentQS(String bankID,java.util.Date date){
		ReturnValue result = new ReturnValue();
		try{
			result = this.isTradeDate(date);
			if(result.result != 0){
				return result;
			}else if(Tool.fmtDate(new java.util.Date()).equalsIgnoreCase(Tool.fmtDate(date)) && ! getTraderStatus()){
				result.result = -2;
				result.remark = "系统尚未结算，不允许发送清算数据";
				return result;
			}
		}catch(Exception e){
			e.printStackTrace();
			log(e.getMessage());
			result.result = -1;
			result.remark = "取得当天是否是交易日失败";
			return result;
		}
		Vector<HXSentQSMsgValue> vector = this.hxGetQS(bankID, date);
		BankAdapterRMI bankadapter = this.getAdapter(bankID);
		if(bankadapter == null){
			result.result = ErrorCode.GetAdapter_Error;
			result.remark = "获取适配器["+bankID+"]异常";
		}else{
			try{
				result = bankadapter.hxSentQS(vector,date);
				if(result != null && result.result == 0){
					try{
						Connection conn = DAO.getConnection();
						BankQSVO bq2 = new BankQSVO();
						bq2.bankID = bankID;
						bq2.tradeDate = date;
						bq2.note = "华夏银行清算";
						DAO.addBankQS(bq2, conn);
					}catch(SQLException e){
						result.result = -1;
						result.remark = "发送清算成功，插入清算表数据库异常";
						throw e;
					}catch(Exception e){
						result.result = -1;
						result.remark = "发送清算成功，插入清算表系统异常";
						throw e;
					}
				}
				System.out.println("发送清算返回信息：\n"+result.toString());
			}catch(Exception e){
				result.result = -1;
				result.remark = "发送银行["+bankID+"]["+Tool.fmtDate(date)+"]清算文件，异常";
				e.printStackTrace();
			}
		}
		return result;
	}
	/**后台调用处理器发送对账方法*/
	public ReturnValue hxSentDZ(String bankID,java.util.Date date){
		ReturnValue result = new ReturnValue();
		try{
			result = this.isTradeDate(date);
			if(result.result != 0){
				return result;
			}else if(Tool.fmtDate(new java.util.Date()).equalsIgnoreCase(Tool.fmtDate(date)) && ! getTraderStatus()){
				result.result = -2;
				result.remark = "系统尚未结算，不允许发送对账数据";
				return result;
			}
		}catch(Exception e){
			e.printStackTrace();
			log(e.getMessage());
			result.result = -1;
			result.remark = "取得当天是否是交易日失败";
			return result;
		}
		Vector<HXSentQSMsgValue> vector = this.hxGetQS(bankID, date);
		BankAdapterRMI bankadapter = this.getAdapter(bankID);
		if(bankadapter == null){
			result.result = ErrorCode.GetAdapter_Error;
			result.remark = "获取适配器["+bankID+"]异常";
		}else{
			try{
				result = bankadapter.hxSentDZ(vector,date);
			}catch(Exception e){
				result.result = -1;
				result.remark = "发送银行["+bankID+"]["+Tool.fmtDate(date)+"]对账文件，异常";
				e.printStackTrace();
			}
		}
		return result;
	}
	/**适配器调用处理器获取清算信息*/
	public Vector<HXSentQSMsgValue> hxGetQS(String bankID,java.util.Date date){
		Vector<HXSentQSMsgValue> result = null;
		try{
			ReturnValue rv = this.isTradeDate(date);
			if(rv.result != 0){
				return result;
			}else if(Tool.fmtDate(new java.util.Date()).equalsIgnoreCase(Tool.fmtDate(date)) && ! getTraderStatus()){
				return result;
			}
			result = DAO.getHXQSMsg(bankID, null, date);
			
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 获取银行清算失败文件信息
	 * @param bankID 银行代码
	 * @param tradeDate 交易日期
	 * @return ReturnValue
	 * @throws RemoteException
	 */
	public ReturnValue hxGetQSError(String bankID,java.util.Date tradeDate){
		ReturnValue result = new ReturnValue();
		BankAdapterRMI bankadapter = this.getAdapter(bankID);
		if(bankadapter == null){
			result.result = ErrorCode.GetAdapter_Error;
			result.remark = "获取银行["+bankID+"]["+Tool.fmtDate(tradeDate)+"]清算信息文件，连接适配器失败";
		}else{
			try{
				Vector<QSChangeResult> vector = bankadapter.hxGetQSError(tradeDate);
				result = this.hxSaveQSError(vector);
			}catch(Exception e){
				result.result = -1;
				result.remark = "获取银行["+bankID+"]["+Tool.fmtDate(tradeDate)+"]清算信息文件,异常";
				e.printStackTrace();
			}
		}
		return result;
	}
	/**
	 * 获取银行对账失败文件信息
	 * @param bankID 银行代码
	 * @param tradeDate 交易日期
	 * @return ReturnValue
	 * @throws RemoteException
	 */
	public ReturnValue hxGetDZError(String bankID,java.util.Date tradeDate){
		ReturnValue result = new ReturnValue();
		BankAdapterRMI bankadapter = this.getAdapter(bankID);
		if(bankadapter == null){
			result.result = ErrorCode.GetAdapter_Error;
			result.remark = "获取银行["+bankID+"]["+Tool.fmtDate(tradeDate)+"]对账信息文件，连接适配器失败";
		}else{
			try{
				Vector<QSRresult> vector = bankadapter.hxGetDZError(tradeDate);
				result = this.hxSaveDZError(vector);
			}catch(Exception e){
				result.result = -1;
				result.remark = "获取银行["+bankID+"]["+Tool.fmtDate(tradeDate)+"]对账信息文件,异常";
				e.printStackTrace();
			}
		}
		return result;
	}
	/**
	 * 保存银行清算失败信息
	 * @param vector 信息集合
	 * @return ReturnValue
	 * @throws RemoteException
	 */
	public ReturnValue hxSaveQSError(Vector<QSChangeResult> vector){
		ReturnValue result = new ReturnValue();
		Connection conn = null;
		try{
			conn = DAO.getConnection();
			try{
				conn.setAutoCommit(false);
				if(vector != null && vector.size()>0){
					QSChangeResult qs = new QSChangeResult();
					qs.bankID =  vector.get(0).bankID;
					qs.tradeDate =  vector.get(0).tradeDate;
					DAO.delQSError(qs, conn);
					for(QSChangeResult qsc : vector){
						DAO.addQSError(qsc,conn);
					}
				}
				conn.commit();
				result.remark = "写入银行清算信息成功";
			}catch(SQLException e){
				conn.rollback();
			}finally{
				conn.setAutoCommit(true);
			}
		}catch(SQLException e){
			result.result = -1;
			result.remark = "写入银行清算信息，数据库异常";
			e.printStackTrace();
		}catch(Exception e){
			result.result = -2;
			result.remark = "写入银行清算信息，系统异常";
			e.printStackTrace();
		}finally{
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}
	/**
	 * 保存银行对账失败信息
	 * @param vector 信息集合
	 * @return ReturnValue
	 * @throws RemoteException
	 */
	public ReturnValue hxSaveDZError(Vector<QSRresult> vector){
		ReturnValue result = new ReturnValue();
		Connection conn = null;
		try{
			conn = DAO.getConnection();
			try{
				conn.setAutoCommit(false);
				if(vector != null && vector.size()>0){
					QSRresult rs = vector.get(0);
					if(rs != null){
						DAO.delQSResult(rs.bankID, rs.tradeDate, conn);
					}
					for(QSRresult rsa : vector){
						DAO.addQSResult(rsa, conn);
					}
				}
				conn.commit();
				result.remark = "添加对账信息文件成功";
			}catch(SQLException e){
				conn.rollback();
				result.result = -1;
				result.remark = "添加对账信息文件，数据库异常";
				e.printStackTrace();
			}finally{
				conn.setAutoCommit(true);
			}
		}catch(SQLException e){
			result.result = -1;
			result.remark = "写入对账信息文件，数据库异常";
			e.printStackTrace();
		}catch(Exception e){
			result.result = -2;
			result.remark = "写入对账信息文件，系统异常";
			e.printStackTrace();
		}finally{
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}
//**********************浦发银行清算定制********************************************************
	/**
	 * 后台调用保存清算信息
	 * @param bankID 银行编号
	 * @param date 保存的清算日期
	 */
	public ReturnValue pfdbQS(String bankID,java.util.Date date){
		ReturnValue result = new ReturnValue();
		Connection conn = null;
		try{
			conn = DAO.getConnection();
			Vector<TradeList> v1 = DAO.getChangeFirmRights(" and trunc(Trade_Date)=to_date('"+Tool.fmtDate(date)+"','yyyy-MM-dd')", conn);
			if(v1 != null && v1.size()>0){
				try{
					conn.setAutoCommit(false);
					Vector<TradeList> v11 = DAO.getChangeFirmRights(" and trunc(Trade_Date)=to_date('"+Tool.fmtDate(date)+"','yyyy-MM-dd') and flag<>'Y' for update ", conn);
					if(v11 != null && v11.size()>0){
						String[] Serial_Id1 = new String[v11.size()];
						String[] firmIDs = new String[v11.size()];
						for(int i=0;i<v11.size();i++){
							TradeList tl = v11.get(i);
							Serial_Id1[i] = tl.Serial_Id;
							if("2".equalsIgnoreCase(tl.Trade_Type) || "3".equalsIgnoreCase(tl.Trade_Type) || "4".equalsIgnoreCase(tl.Trade_Type)){
								firmIDs[i] = tl.B_Member_Code;
							}else{
								firmIDs[i] = tl.S_Member_Code;
							}
						}
						DAO.delChangeFirmRights(bankID, date, Serial_Id1, conn);
						DAO.addChangeFirmRights(bankID, firmIDs, date, conn);
					}
					conn.commit();
				}catch(SQLException e1){
					conn.rollback();
					throw e1;
				}catch(Exception e1){
					conn.rollback();
					throw e1;
				}finally{
					conn.setAutoCommit(true);
				}
			}else{
				DAO.addChangeFirmRights(bankID, null, date, conn);
			}
			Vector<Margins> v2 = DAO.getChangeFirmFrozen(" and trunc(Trade_Date)=to_date('"+Tool.fmtDate(date)+"','yyyy-MM-dd')", conn);
			if(v2 != null && v2.size()>0){
				try{
					conn.setAutoCommit(false);
					Vector<Margins> v21 = DAO.getChangeFirmFrozen(" and trunc(Trade_Date)=to_date('"+Tool.fmtDate(date)+"','yyyy-MM-dd') and flag<>'Y' for update", conn);
					if(v21 != null && v21.size()>0){
						String[] Serial_Id2 = new String[v21.size()];
						String[] firmIDs = new String[v21.size()];
						for(int i=0;i<v21.size();i++){
							Margins mg = v21.get(i);
							Serial_Id2[i] = mg.Serial_Id;
							firmIDs[i] = mg.Member_Code;
						}
						DAO.delChangeFirmFrozen(bankID, date, Serial_Id2, conn);
						DAO.addChangeFirmFrozen(bankID, firmIDs, date, conn);
					}
					conn.commit();
				}catch(SQLException e1){
					conn.rollback();
					throw e1;
				}catch(Exception e1){
					conn.rollback();
					throw e1;
				}finally{
					conn.setAutoCommit(true);
				}
			}else{
				DAO.addChangeFirmFrozen(bankID, null, date, conn);
			}
		}catch(SQLException e){
			result.result = -1;
			result.remark = "保存浦发银行 "+Tool.fmtDate(date)+" 清算数据，数据库异常";
			this.log(result.remark);
			e.printStackTrace();
		}catch(Exception e){
			result.result = -1;
			result.remark = "保存浦发银行 "+Tool.fmtDate(date)+" 清算数据，系统异常";
			this.log(result.remark);
			e.printStackTrace();
		}finally{
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}
	/**
	 * 发送权益变化量
	 * @param bankID 银行编号
	 * @param date 交易日期
	 * @param sendCount 每个包中所含条数
	 * @param timeOutCount 发送超时次数(超过次数停止发送)
	 * @param faileCount 银行返回错误数量(超过数量停止发送)
	 * @return ReturnValue
	 */
	public ReturnValue getTradesDateDetailsList(String bankID,java.util.Date date,int sendCount,int timeOutCount,int faileCount){
		ReturnValue result = new ReturnValue();
		try{
			result = this.isTradeDate(date);
			if(result.result != 0){
				return result;
			}else if(Tool.fmtDate(new java.util.Date()).equalsIgnoreCase(Tool.fmtDate(date)) && ! getTraderStatus()){
				result.result = -2;
				result.remark = "系统尚未结算，不允许发送权益变化量";
				return result;
			}
		}catch(Exception e){
			e.printStackTrace();
			log(e.getMessage());
			result.result = -1;
			result.remark = "取得当天是否是转账日期失败";
			return result;
		}
		Vector<TradeList> balance = this.getTradesDateDetailsList(bankID, date, new String[]{"N","F"});
		BankAdapterRMI bankadapter = this.getAdapter(bankID);
		if(bankadapter == null){
			result.result = ErrorCode.GetAdapter_Error;
		}else{
			Connection conn = null;
			try{
				conn = DAO.getConnection();
				TraderResult tr = bankadapter.sendTradeDetails(balance, sendCount, timeOutCount, faileCount);
				try{
					conn.setAutoCommit(false);
					if(tr != null && tr.tVe != null){
						for(int i=0;i<tr.tVe.size();i++){
							DAO.modChangeFirmRights(tr.tVe.get(i).Serial_Id, tr.tVe.get(i).flag, conn);
						}
					}
					conn.commit();
				}catch(SQLException e){
					conn.rollback();
					throw e;
				}catch(Exception e){
					conn.rollback();
					throw e;
				}finally{
					conn.setAutoCommit(true);
				}
			}catch(SQLException e){
				e.printStackTrace();
				result.result = -1;
				result.remark = "发送权益变化量时数据库异常";
			}catch(Exception e){
				e.printStackTrace();
				result.result = -1;
				result.remark = "发送权益该变量信息时系统异常";
			}finally{
				DAO.closeStatement(null, null, conn);
			}
		}
		return result;
	}
	/**
	 * 返回权益变化量
	 * @param bankID 银行编号
	 * @param date 交易日期
	 * @param flag 发送状态(N未发送 F银行处理失败 Y银行处理成功)
	 * @return Vector<TradeList>
	 */
	public Vector<TradeList> getTradesDateDetailsList(String bankID,java.util.Date date,String[] flag){
		Vector<TradeList> result = null;
		Connection conn = null;
		try{
			ReturnValue rv = this.isTradeDate(date);
			if(rv.result != 0){
				return result;
			}else if(Tool.fmtDate(new java.util.Date()).equalsIgnoreCase(Tool.fmtDate(date)) && ! getTraderStatus()){
				return result;
			}
			conn = DAO.getConnection();
			String filter = " and trunc(Trade_Date)=to_date('"+Tool.fmtDate(date)+"','yyyy-MM-dd') ";
			if(flag != null && flag.length>0){
				String flags = "'B'";
				for(String fl : flag){
					if(fl != null && fl.trim().length()>0){
						flags += ",'"+fl.trim()+"'";
					}
				}
				filter += " and flag in ("+flags+")";
			}
			result = DAO.getChangeFirmRights(filter, conn);
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 发送冻结资金
	 * @param bankID 银行编号
	 * @param date 交易日期
	 * @param sendCount 每个包中所含条数
	 * @param timeOutCount 发送超时次数(超过次数停止发送)
	 * @param faileCount 银行返回错误数量(超过数量停止发送)
	 * @return ReturnValue
	 */
	public ReturnValue getDongjieDetailList(String bankID,java.util.Date date,int sendCount,int timeOutCount,int faileCount){
		ReturnValue result = new ReturnValue();
		try{
			result = this.isTradeDate(date);
			if(result.result != 0){
				return result;
			}else if(Tool.fmtDate(new java.util.Date()).equalsIgnoreCase(Tool.fmtDate(date)) && ! getTraderStatus()){
				result.result = -2;
				result.remark = "系统尚未结算，不允许发送冻结资金";
				return result;
			}
		}catch(Exception e){
			e.printStackTrace();
			this.log(e.getMessage());
			result.result = -1;
			result.remark = "取得当天是否是转账日期失败";
			return result;
		}
		Connection conn = null;
		Vector<Margins> margins = this.getDongjieDetailList(bankID, date, new String[]{"N","F"});
		try{
			BankAdapterRMI bankadapter = this.getAdapter(bankID);
			if(bankadapter == null){
				result.result = ErrorCode.GetAdapter_Error;
				result.remark = "发送银行 "+bankID+" 在 "+Tool.fmtDate(date)+" 的冻结资金，连接银行失败";
			}else{
				TraderResult tr = bankadapter.sendMargins(margins, sendCount, timeOutCount, faileCount);
				if(tr != null && tr.mVe != null){
					conn = DAO.getConnection();
					try{
						conn.setAutoCommit(false);
						for(int i=0;i<tr.mVe.size();i++){
							DAO.modChangeFirmFrozen(tr.mVe.get(i).Serial_Id, tr.mVe.get(i).flag, conn);
						}
						conn.commit();
						result.remark = "发送银行 "+bankID+" 在 "+Tool.fmtDate(date)+" 的冻结资金成功";
					}catch(SQLException e){
						conn.rollback();
						throw e;
					}catch(Exception e){
						conn.rollback();
						throw e;
					}finally{
						conn.setAutoCommit(true);
					}
				}else{
					result.result = -1;
					result.remark = "发送银行 "+bankID+" 在 "+Tool.fmtDate(date)+" 的冻结资金，返回信息为空";
				}
			}
		}catch(SQLException e){
			result.result = -1;
			result.remark = "发送冻结资金时数据库异常";
			e.printStackTrace();
		}catch(Exception e){
			result.result = -1;
			result.remark = "发送冻结资金时系统异常";
			e.printStackTrace();
		}finally{
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}
	/**
	 * 返回冻结资金变化量
	 * @param bankID 银行编号
	 * @param date 交易日期
	 * @param flag 发送状态(N未发送 F银行处理失败 Y银行处理成功)
	 * @return Vector<Margins>
	 */
	public Vector<Margins> getDongjieDetailList(String bankID,java.util.Date date,String[] flag){
		Vector<Margins> result = null;
		Connection conn = null;
		try{
			ReturnValue rv = this.isTradeDate(date);
			if(rv.result != 0){
				return result;
			}else if(Tool.fmtDate(new java.util.Date()).equalsIgnoreCase(Tool.fmtDate(date)) && ! getTraderStatus()){
				return result;
			}
			conn = DAO.getConnection();
			String filter = " and trunc(Trade_Date)=to_date('"+Tool.fmtDate(date)+"','yyyy-MM-dd') ";
			if(flag != null && flag.length>0){
				String flags = "'B'";
				for(String fl : flag){
					if(fl != null && fl.trim().length()>0){
						flags += ",'"+fl.trim()+"'";
					}
				}
				filter += " and flag in ("+flags+")";
			}
			result = DAO.getChangeFirmFrozen(filter, conn);
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 取得交易商的可用余额、冻结余额和手续费
	 */
	public Hashtable<String,FundsMarg> getFundsMarg(String bankID,java.util.Date date){
		Hashtable<String,FundsMarg> result = null;
		
		
		return result;
	}
//工商银行清算信息
	/**
	 * 发送工行清算数据
	 * @param bankId 银行编号
	 * @param firmId 交易商代码
	 * @param qdate 交易日期
	 * @return ReturnValue
	 */
	public ReturnValue sendGHQS(String bankId,String firmId,java.util.Date qdate){
		
		ReturnValue result = new ReturnValue();
		Connection conn = null;
		BankAdapterRMI bankAdapter = this.getAdapter(bankId); 
		try {
			result = this.isTradeDate(qdate);
			if (result.result != 0) {
				return result;
			}else if (Tool.fmtDate(new java.util.Date()).equalsIgnoreCase(Tool.fmtDate(qdate)) && ! getTraderStatus()){
				result.result = -2;
				result.remark = "系统尚未结算，不允许发送清算数据";
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log(e.getMessage());
			result.result = -1;
			result.remark = "取得是否是交易日期异常";
			return result;
		}
		if (bankAdapter == null) {
			result.result = ErrorCode.GetAdapter_Error;
			result.remark = "发送["+bankId+"]银行交易商清算信息，连接适配器失败";
		}else{
			//获取清算对账信息
			List<FirmRights> frs = this.getRightsList(bankId,firmId,qdate);
			List<TradingDetailsValue> tds = this.getChangeBalance(bankId,firmId,qdate);
			List<OpenOrDelFirmValue> opdf = this.getOpenOrDropMaket(bankId, qdate);
			try{
				result = bankAdapter.sendTradeData(frs, tds, opdf, qdate);
			}catch(Exception e){
				result.result = -1;
				result.remark = "发送["+bankId+"]银行交易商清算信息，适配器抛出异常";
				e.printStackTrace();
			}
		}
		return result;
	}
	/**
	 * 获取工行交易商权益分分核对信息
	 * @param bankID
	 * @param date
	 * @return
	 */
	public ReturnValue getBankFirmRightValue(String bankID, Date date) {
		ReturnValue result = new ReturnValue();
		BankAdapterRMI bankadapter = this.getAdapter(bankID);
		if(bankadapter == null){
			result.result = ErrorCode.GetAdapter_Error;
			result.remark = "获取分分核对平衡信息，获取适配器失败";
		}else{
			try{
				List<BankFirmRightValue> firmRightValues = bankadapter.getBankFirmRightValue(bankID,date);
				try {
					for (BankFirmRightValue bfrv : firmRightValues){
						List<BankFirmRightValue> bfrlist= DAO.getBankCapital(bfrv);
						if(bfrlist!=null && bfrlist.size()>0){
							DAO.updateBankCapital(bfrv);
						}else{
							DAO.addBankCapital(bfrv);
						}
						result.remark = "写入分分核对信息成功";
					}
				} catch (Exception e) {
					result.result = -1;
					result.remark = "传入分分核对信息，系统异常";
					e.printStackTrace();
				}
			}catch(Exception e){
				result.result = -1;
				result.remark = "传入分分核对信息，系统异常";
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 获取工行总分平衡信息
	 * @param bankID
	 * @param date
	 * @return
	 */
	public ReturnValue getProperBalanceValue(String bankID, Date date) {
		ReturnValue result = new ReturnValue();
		BankAdapterRMI bankadapter = this.getAdapter(bankID);
		if(bankadapter == null){
			result.result = ErrorCode.GetAdapter_Error;
			result.remark = "获取总分平衡信息，获取适配器失败";
		}else{
			try{
				ProperBalanceValue balanceValue = bankadapter.getProperBalanceValue(bankID, date);
				try {
					List<ProperBalanceValue> list=DAO.getProperBalance(balanceValue);
					if(list!=null && list.size()>0){
						DAO.updateProperBalance(balanceValue);
					}else{
						DAO.addProperBalance(balanceValue);
					}
					result.remark = "写入总分平衡信息成功";
				} catch (Exception e) {
					result.result = -1;
					result.remark = "传入总分平衡信息，系统异常";
					e.printStackTrace();
				}
			}catch(Exception e){
				result.result = -1;
				result.remark = "传入总分平衡信息，系统异常";
				e.printStackTrace();
			}
		}
		return result;
	}
	/**
	 * 从财务和交易系统获取交易商资金余额及各交易板块权益
	 * @param String bankId String firmid,java.util.Date date
	 * @return HashMap<String, TradeDataValue>
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<FirmRightsValue> getTradeDataMsg(String bankId,String firmId,java.util.Date qdate){
		List<FirmRightsValue> rightList=null;
		try{
			ReturnValue rv = this.isTradeDate(qdate);
			if(rv.result != 0){
				return rightList;
			}else if(Tool.fmtDate(new java.util.Date()).equalsIgnoreCase(Tool.fmtDate(qdate)) && ! getTraderStatus()){
				return rightList;
			}
			rightList=DAO.getTradeDataMsg(bankId, firmId, qdate);
		}catch(SQLException e){
			System.out.println("获取交易商权益信息数据库异常");
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			System.out.println("获取交易商权益信息找不到类");
			e.printStackTrace();
		}
		return rightList;
	}
	/**查询某天的交易商权益*/
	public Map<String ,FirmRights> getRightsMap(String bankId,String firmId,java.util.Date qdate){
		this.log("查询某天的交易商权益bankId["+bankId+"]firmId["+firmId+"]qdate["+qdate==null ? "为空" : Tool.fmtDate(qdate)+"]");
		try{
			ReturnValue rv = this.isTradeDate(qdate);
			if(rv.result != 0){
				return null;
			}else if(Tool.fmtDate(new java.util.Date()).equalsIgnoreCase(Tool.fmtDate(qdate)) && ! getTraderStatus()){
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
			log(e.getMessage());
			return null;
		}
		Map<String ,FirmRights> rights=new HashMap<String,FirmRights>();
		List<FirmRightsValue> frvs=this.getTradeDataMsg(bankId, firmId, qdate);
		if(frvs!=null &&frvs.size()>0){
			for(FirmRightsValue frv:frvs){
				FirmRights fr = new FirmRights();
				fr.firmId=frv.firmid;
				fr.firmName=frv.accountname;
				fr.account=frv.account;
				fr.money=frv.getAvilableBlance()+frv.getTimebargainBalance()+frv.getZcjsBalance()+frv.getVendueBalance();
				rights.put(frv.firmid, fr);
			}
		}
		return rights;
	}
	/**查询某天的交易商权益*/
	public List<FirmRights> getRightsList(String bankId,String firmId,java.util.Date qdate){
		try{
			ReturnValue rv = this.isTradeDate(qdate);
			if(rv.result != 0){
				return null;
			}else if(Tool.fmtDate(new java.util.Date()).equalsIgnoreCase(Tool.fmtDate(qdate)) && ! getTraderStatus()){
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
			log(e.getMessage());
			return null;
		}
		List<FirmRights> rights = new ArrayList<FirmRights>();
		List<FirmRightsValue> frvs=this.getTradeDataMsg(bankId, firmId, qdate);
		if(frvs!=null &&frvs.size()>0){
			for(FirmRightsValue frv:frvs){
				FirmRights fr = new FirmRights();
				fr.firmId=frv.firmid;
				fr.firmName=frv.accountname;
				fr.account=frv.account;
				fr.money=frv.getAvilableBlance()+frv.getTimebargainBalance()+frv.getZcjsBalance()+frv.getVendueBalance();
				rights.add(fr);
			}
		}
		return rights;
	}
	/**获取交易商和上一个交易日比较的权益变化*/
	public List<TradingDetailsValue> getChangeBalance(String bankId,String firmId,java.util.Date qdate){
		Connection conn =null;
		List<TradingDetailsValue> result= null;
		try{
			ReturnValue rv = this.isTradeDate(qdate);
			if(rv.result != 0){
				return result;
			}else if(Tool.fmtDate(new java.util.Date()).equalsIgnoreCase(Tool.fmtDate(qdate)) && ! getTraderStatus()){
				return result;
			}
		}catch(Exception e){
			e.printStackTrace();
			log(e.getMessage());
			return result;
		}
		List<FirmRights> selectDate=this.getRightsList(bankId, firmId, qdate);
		try{
			conn =DAO.getConnection();
			//获取查询时间的上一个交易日
			Date upData = DAO.getlastDate(qdate,conn);
			Map<String ,FirmRights> lastDate=this.getRightsMap(bankId, firmId, upData);
			int i=1;//zjj 2012.11.19 节省流水号序列优化
			for(FirmRights fr : selectDate){
				double incomeMoney = DAO.sumCapitalInfo("where firmid='"+fr.firmId+"' and  trunc(b_date)=to_date('"+Tool.fmtDate(qdate)+"','yyyy-MM-dd') and code='Deposit' ", conn);
				double outcomeMoney = DAO.sumCapitalInfo("where firmid='"+fr.firmId+"' and  trunc(b_date)=to_date('"+Tool.fmtDate(qdate)+"','yyyy-MM-dd') and code='Fetch' ", conn);
				double changeBalance = fr.getMoney()-incomeMoney+outcomeMoney-(lastDate.get(fr.firmId)==null ? 0 : lastDate.get(fr.firmId).getMoney());
				System.out.println(" 交易商 " + fr.firmId + " 资金变化为 本日权益["+fr.getMoney()+"] 入金["+incomeMoney+"] 出金["+outcomeMoney+"] 上日权益["+(lastDate.get(fr.firmId)==null ? 0 : lastDate.get(fr.firmId).getMoney())+"]");
				if(changeBalance!=0){
					TradingDetailsValue tdv = new TradingDetailsValue();
					tdv.maketNum=i;//getMktActionID();//zjj 2012.11.19 节省流水号序列优化
					tdv.account=fr.account;
					tdv.firmId=fr.firmId;
					tdv.firmName=fr.firmName;
					if(changeBalance>0){
						tdv.money=changeBalance;
						tdv.updown="2";//1:借(权益减) ;2:贷(权益增)
					}else{
						tdv.money=-changeBalance;
						tdv.updown="1";
					}
					if(result==null){
						result=new ArrayList<TradingDetailsValue>();
					}
					i++;//zjj 2012.11.19 节省流水号序列优化
					result.add(tdv);
				}
			}
		}catch(SQLException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		if(result==null){
			System.out.println("返回前 结果为 null 需要初始化");
			result=new ArrayList<TradingDetailsValue>();
		}
		System.out.println(result.size());
		return result;
	}
	/**查询开户销户信息*/
	public List<OpenOrDelFirmValue> getOpenOrDropMaket(String bankId,java.util.Date qdate){
		List<OpenOrDelFirmValue> result=null;
		String openFilter=" where 1=1 and isopen='1' ";//修复【只注册未签约时给银行发开户】问题，zjj 2012.11.19
		String delFilter=" where 1=1 ";
		if(bankId!=null && !bankId.trim().equals("")){
			openFilter=openFilter+"and bankId='"+bankId+"' ";
			delFilter=delFilter+"and bankId='"+bankId+"_D' ";
		}
		if(qdate!=null){
			openFilter=openFilter+ "and trunc(openTime)=to_date('"+Tool.fmtDate(qdate)+"','yyyy-MM-dd') ";
			delFilter=delFilter+ "and trunc(delTime)=to_date('"+Tool.fmtDate(qdate)+"','yyyy-MM-dd') ";
		}
		//签约帐户
		Vector<CorrespondValue> openFirmVector=null;
		//解约帐户
		Vector<CorrespondValue> delFirmVector=null;
		try {
			//获取当天签约的交易商
			openFirmVector=DAO.getCorrespondList(openFilter);
			//获取当天解约的交易商
			delFirmVector=DAO.getCorrespondList(delFilter);
			if(openFirmVector==null){
				openFirmVector=new Vector<CorrespondValue>();
			}
			if(delFirmVector==null){
				delFirmVector=new Vector<CorrespondValue>();
			}
			int i=1;//zjj 2012.11.19 节省流水号序列优化
			for(CorrespondValue cv : openFirmVector){
				OpenOrDelFirmValue odf=new OpenOrDelFirmValue();
				if(result==null){
					result=new ArrayList<OpenOrDelFirmValue>();
				}
				odf.firmId=cv.firmID;
				odf.firmName=cv.accountName;
				odf.maketNum=i;//getMktActionID();//zjj 2012.11.19 节省流水号序列优化
				odf.openordel="0";//0:开户;1:销户
				i++;//zjj 2012.11.19 节省流水号序列优化
				result.add(odf);
			}
			for(CorrespondValue cv : delFirmVector){
				OpenOrDelFirmValue odf=new OpenOrDelFirmValue();
				if(result==null){
					result=new ArrayList<OpenOrDelFirmValue>();
				}
				odf.firmId=cv.firmID;
				odf.firmName=cv.accountName;
				odf.maketNum=i;//getMktActionID();
				odf.openordel="1";
				result.add(odf);
				i++;//zjj 2012.11.19 节省流水号序列优化
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		if(result==null){
			result=new ArrayList<OpenOrDelFirmValue>();
		}
		return result;
	}
	/**交易商权益的分分核对*/
	public long getBankFirmRightValue(List<BankFirmRightValue> list){
		long result=0;
		for (BankFirmRightValue bfrv : list){
			List<BankFirmRightValue> bfrlist= DAO.getBankCapital(bfrv);
			if(bfrlist!=null && bfrlist.size()>0){
				result=DAO.updateBankCapital(bfrv);
			}else{
				result=DAO.addBankCapital(bfrv);
			}
		}
		return result;
	}
	/**总分平衡监管*/
	public long getProperBalanceValue(ProperBalanceValue pbv){
		long result=0;
		List<ProperBalanceValue> list=DAO.getProperBalance(pbv);
		if(list!=null && list.size()>0){
			result=DAO.updateProperBalance(pbv);
		}else{
			result=DAO.addProperBalance(pbv);
		}
		return result;
	}
//哈尔滨银行清算信息
	/**
	 * 发送交易商清算信息
	 * @param bankID 银行编号
	 * @param firmIDs 交易商编号集
	 * @param tradeDate 清算日期
	 */
	public ReturnValue sendHRBQSValue(String bankID,String[] firmIDs,java.util.Date tradeDate){
		ReturnValue result = new ReturnValue();
		BankAdapterRMI bankAdapter = this.getAdapter(bankID); 
		Connection conn = null;
		try {
			result = this.isTradeDate(tradeDate);
			if (result.result != 0) {
				return result;
			}else if (Tool.fmtDate(new java.util.Date()).equalsIgnoreCase(Tool.fmtDate(tradeDate)) && ! getTraderStatus()){
				result.result = -2;
				result.remark = "系统尚未结算，不允许发送清算数据";
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log(e.getMessage());
			result.result = -1;
			result.remark = "取得是否是交易日期异常";
			return result;
		}
		if (bankAdapter == null) {
			result.result = ErrorCode.GetAdapter_Error;
			result.remark = "发送["+bankID+"]银行交易商清算信息，连接适配器失败";
		}else{
			/**获取清算对账信息*/
			RZQSValue qs = this.getHRBQSValue(bankID, firmIDs, tradeDate);
			RZDZValue dz = this.getHRBDZValue(bankID, firmIDs, tradeDate);
			try{
				result = bankAdapter.setRZ(qs,dz,tradeDate);
				if(result != null && result.result == 0){
					try{
						conn = DAO.getConnection();
						BankQSVO bq2 = new BankQSVO();
						bq2.bankID = bankID;
						bq2.tradeDate = tradeDate;
						bq2.note = "哈尔滨银行清算";
						DAO.addBankQS(bq2, conn);
					}catch(SQLException e){
						result.result = -1;
						result.remark = "发送清算成功，插入清算表数据库异常";
						throw e;
					}catch(Exception e){
						result.result = -1;
						result.remark = "发送清算成功，插入清算表系统异常";
						throw e;
					}
				}
			}catch(Exception e){
				result.result = -1;
				result.remark = "发送["+bankID+"]银行交易商清算信息，适配器抛出异常";
				e.printStackTrace();
			}
		}
		
		
		return result;
	}
	
	/**
	 * 获取交易商清算信息（哈尔滨银行）
	 * @param bankID 银行编号
	 * @param firmIDs 交易商编号集
	 * @param tradeDate 交易日期
	 * @return Vector<XYQSValue>
	 */
	public RZQSValue getHRBQSValue(String bankID, String[] firmIDs,
			Date tradeDate) {
		RZQSValue result = null;
		try {
			ReturnValue rv = this.isTradeDate(tradeDate);
			if(rv.result != 0){
				return result;
			}else if(Tool.fmtDate(new java.util.Date()).equalsIgnoreCase(Tool.fmtDate(tradeDate)) && ! getTraderStatus()){
				return result;
			}
			result = DAO.getXYQSValue(bankID, firmIDs, tradeDate);//复用兴业SQL
		}catch (SQLException e) {
			e.printStackTrace();
			log(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
		}
		if(result == null){
			result = new RZQSValue();
		}
		return result;
	}
	
	/**
	 * 获取交易商对账信息(哈尔滨银行)
	 * @param bankID 银行编号
	 * @param firmIDs 交易商编号集
	 * @param tradeDate 交易日期
	 * @return Vector<XYDZValue>
	 */
	public RZDZValue getHRBDZValue(String bankID, String[] firmIDs,
			Date tradeDate) {
		RZDZValue result = null;
		try {
			result = DAO.getXYDZValue(bankID, firmIDs, tradeDate);//复用兴业SQL
		} catch (SQLException e) {
			e.printStackTrace();
			log(e.getMessage());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		if(result == null){
			result = new RZDZValue();
		}
		return result;
	}
//兴业银行清算信息
	/**手动签到签退(0:签到 1:签退)*/
	public ReturnValue updownBank(String bankID,int type){
		ReturnValue result = new ReturnValue();
		BankAdapterRMI bankadapter = this.getAdapter(bankID);
		try{
			if(type==0){
				result = bankadapter.loginBank(bankID);
			}else if (type==1){
				result = bankadapter.quitBank(bankID);
			}else {
				result.remark = "没有相对应的操作";
				result.result = ErrorCode.GetAdapter_Error;
			}
		}catch(Exception e){
			e.printStackTrace();
			result.remark = "调用适配器失败";
			result.result = ErrorCode.GetAdapter_Error;
		}
		
		return result;
	}
	
	
	/**
	 * 发送交易商清算信息
	 * @param bankID 银行编号
	 * @param firmIDs 交易商编号集
	 * @param tradeDate 清算日期
	 */
	public ReturnValue sendXYQSValue(String bankID,String[] firmIDs,java.util.Date tradeDate){
		ReturnValue result = new ReturnValue();
		BankAdapterRMI bankadapter = this.getAdapter(bankID);
		
		Connection conn = null;
		try{			
			conn = DAO.getConnection();
			
			result = this.isTradeDate(tradeDate);
			System.out.println(result.toString());
			if(result.result != 0){
				return result;
			}else if(Tool.fmtDate(new java.util.Date()).equalsIgnoreCase(Tool.fmtDate(tradeDate)) && ! getTraderStatus()){
				result.result = -2;
				result.remark = "系统尚未结算，不允许发送清算数据";
				return result;
			}
		}catch(Exception e){
			e.printStackTrace();
			log(e.getMessage());
			result.result = -1;
			result.remark = "取得是否是交易日期异常";
			return result;
		}
		if(bankadapter == null){
			result.result = ErrorCode.GetAdapter_Error;
			result.remark = "发送["+bankID+"]银行交易商清算信息，连接适配器失败";
		}else{
			RZQSValue qs = this.getXYQSValue(bankID, firmIDs, tradeDate);
			RZDZValue dz = this.getXYDZValue(bankID, firmIDs, tradeDate);
			
			
//			/**判断指定日期有无签约会员,如果有则把该会员的可用余额当盈利    start*/
//			java.util.Date usdate = null;
//			try {
//				usdate = DAO.getlastDate(tradeDate, conn);//取得上一个交易日
//			
//				RZDZValue dz1 = this.getXYDZValue(bankID, firmIDs, usdate);
//									
//				String filter = " where bankid = '"+bankID+"' and trunc(openTime)=to_date('"+Tool.fmtDate(tradeDate)+"','yyyy-MM-dd')";
//				if(firmIDs != null && firmIDs.length > 0){
//					String firms = " ";
//					for(String firmID : firmIDs){
//						if(firmID != null && firmID.trim().length()>0){
//							firms += "'"+firmID.trim()+"',";
//						}
//					}
//					if(firms != null && firms.trim().length()>0){
//						firms = " and firmID in (" +firms.substring(0, firms.length()-1)+")";
//						filter += firms;
//					}
//				}
//				Vector<CorrespondValue> vec = null;
//				vec = DAO.getCorrespondList(filter);
//				if (vec.size() > 0 && vec != null) {
//					for (int i = 0; i < vec.size(); i++) {
//						CorrespondValue cv = vec.get(i);
//						for (int j = 0; j < qs.getFrv().size(); j++) {
//							if (cv.firmID.equals(qs.getFrv().get(j).firmID)) {
//								for (int j2 = 0; j2 < dz1.getFdv().size(); j2++) {
//									FirmDZValue fz = dz1.getFdv().get(j2);
//									if (cv.firmID.equals(fz.firmID)) {
//										qs.getFrv().get(j).availableBalance += fz.firmRights;
//									}
//								}
//							}
//						}
//					}
//				}	
//				/**               end               */
//			} catch (SQLException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			} catch (Exception e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
			
			try{
				result = bankadapter.setRZ(qs,dz,tradeDate);
				if(result != null && result.result == 0){
					try{
						conn = DAO.getConnection();
						BankQSVO bq2 = new BankQSVO();
						bq2.bankID = bankID;
						bq2.tradeDate = tradeDate;
						bq2.note = "兴业银行清算";
						DAO.addBankQS(bq2, conn);
					}catch(SQLException e){
						result.result = -1;
						result.remark = "发送清算成功，插入清算表数据库异常";
						throw e;
					}catch(Exception e){
						result.result = -1;
						result.remark = "发送清算成功，插入清算表系统异常";
						throw e;
					}
				}
			}catch(Exception e){
				result.result = -1;
				result.remark = "发送["+bankID+"]银行交易商清算信息，适配器抛出异常";
				e.printStackTrace();
			}
		}
		return result;
	}
	/**
	 * 获取交易商清算信息
	 * @param bankID 银行编号
	 * @param firmIDs 交易商编号集
	 * @param tradeDate 交易日期
	 * @return Vector<XYQSValue>
	 */
	public RZQSValue getXYQSValue(String bankID,String[] firmIDs,java.util.Date tradeDate){
		RZQSValue result = null;
		Connection conn = null;
		try{
			conn = DAO.getConnection();
			ReturnValue rv = this.isTradeDate(tradeDate);
			if(rv.result != 0){
				return result;
			}else if(Tool.fmtDate(new java.util.Date()).equalsIgnoreCase(Tool.fmtDate(tradeDate)) && ! getTraderStatus()){
				return result;
			}
			result = DAO.getXYQSValue(bankID, firmIDs, tradeDate);
			
//			/*******************指定日期开户会员清算处理 start lipj ********************/
//			Map<String,FirmRightValue> today = null;
//			for (int i = 0; i < result.getFrv().size(); i++) {
//				today.put(result.getFrv().get(i).firmID, result.getFrv().get(i));
//			}
//			//指定日期的上一个交易日
//			java.util.Date lastQSDate = DAO.getMaxBankQSList(bankID,tradeDate, conn);
//			//取得某日开户会员
//			Vector<KXHfailChild> toBank = DAO.getBankOpen(bankID, firmIDs, 1, tradeDate);
//			if(toBank != null && toBank.size()>0){
//				String[] firms = null;
//				for(int i = 0 ; i < toBank.size();i++){
//					firms[i] = toBank.get(i).firmID;
//				}
//				RZDZValue yestodayQUANYI = getXYDZValue(bankID,firms,lastQSDate);
//				Map<String,FirmDZValue> lastdate = null;
//				for (int i = 0; i < yestodayQUANYI.getFdv().size(); i++) {
//					lastdate.put(yestodayQUANYI.getFdv().get(i).firmID, yestodayQUANYI.getFdv().get(i));
//				}
//				Iterator<Map.Entry<String, FirmDZValue>> it = lastdate.entrySet().iterator();
//				while(it.hasNext()){
//					Map.Entry<String, FirmDZValue> ent = it.next();
//					FirmDZValue yestodaych = ent.getValue();
//					FirmRightValue todaych = today.get(ent.getKey());
//					if(todaych != null){
//						
//						todaych.availableBalance += yestodaych.availableBalance;//将上个交易日的市场余额加入到盈利中
////						todaych.ProfitAmount += todaych.UnfreezeAmount;//将解冻加入到盈利中
////						todaych.UnfreezeAmount = 0;//将解冻置成0
//					}
//				}
//			}
//			/*******************指定日期开户会员清算处理 end lipj ********************/
							
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
//			DAO.closeStatement(null, null, conn);
		}
		if(result == null){
			result = new RZQSValue();
		}
		return result;
	}
	/**
	 * 获取交易商对账信息
	 * @param bankID 银行编号
	 * @param firmIDs 交易商编号集
	 * @param tradeDate 交易日期
	 * @return Vector<XYDZValue>
	 */
	public RZDZValue getXYDZValue(String bankID,String[] firmIDs,java.util.Date tradeDate){
		RZDZValue result = null;
		Connection conn = null;
		try{
			result = DAO.getXYDZValue(bankID, firmIDs, tradeDate);
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DAO.closeStatement(null, null, conn);
		}
		if(result == null){
			result = new RZDZValue();
		}
		return result;
	}
	/**
	 * 获取总分平衡监管
	 * @param bankID 银行编号
	 * @param date 交易日期
	 * @return ReturnValue
	 */
	public ReturnValue getZFPH(String bankID,java.util.Date date){
		ReturnValue result = new ReturnValue();
		BankAdapterRMI bankadapter = this.getAdapter(bankID);
		if(bankadapter == null){
			result.result = ErrorCode.GetAdapter_Error;
			result.remark = "获取总分平衡信息，获取适配器失败";
		}else{
			try{
				ZFPHValue zfph = bankadapter.getZFPH(date);
				result = this.saveZFPH(zfph);
			}catch(Exception e){
				result.result = -1;
				result.remark = "获取总分平衡监管信息，适配器异常";
				e.printStackTrace();
			}
		}
		return result;
	}
	/**
	 * 获取纷纷核对信息
	 * @param bankID 银行编号
	 * @param date 交易日期
	 * @return ReturnValue
	 */
	public ReturnValue getFFHD(String bankID,java.util.Date date){
		ReturnValue result = new ReturnValue();
		BankAdapterRMI bankadapter = this.getAdapter(bankID);
		if(bankadapter == null){
			result.result = ErrorCode.GetAdapter_Error;
			result.remark = "获取分分核对平衡信息，获取适配器失败";
		}else{
			try{
				FFHDValue ffhd = bankadapter.getFFHD(date);
				result = this.saveFFHD(ffhd);
			}catch(Exception e){
				result.result = -1;
				result.remark = "获取纷纷核对平衡信息，适配器异常";
				e.printStackTrace();
			}
		}
		return result;
	}
	/**
	 * 保存总分平衡监管信息
	 * @param zfph 总分平衡信息
	 * @return ReturnValue
	 */
	public ReturnValue saveZFPH(ZFPHValue zfph){
		ReturnValue result = new ReturnValue();
		Connection conn = null;
		try{
			conn = DAO.getConnection();
			try{
				conn.setAutoCommit(false);
				if(zfph.bankID != null && zfph.bankID.trim().length()>0 && zfph.tradeDate != null){
					DAO.delZFPH(zfph.bankID, zfph.tradeDate, zfph.result, conn);
					DAO.addZFPH(zfph, conn);
					result.remark = "添加银行["+zfph.bankID+"]["+Tool.fmtDate(zfph.tradeDate)+"]总分平衡信息成功";
				}else{
					result.result = -1;
					result.remark = "写入总分平衡监管信息，传来数据不完整";
					this.log(zfph.toString());
				}
				conn.commit();
			}catch(SQLException e){
				conn.rollback();
				result.result = -1;
				result.remark = "添加总分平衡时数据库异常";
				e.printStackTrace();
			}finally{
				conn.setAutoCommit(true);
			}
		}catch(SQLException e){
			result.result = -1;
			result.remark = "添加总分平衡取得数据库连接时数据库异常";
			e.printStackTrace();
		}catch(Exception e){
			result.result = -2;
			result.remark = "添加总分平衡时系统异常";
			e.printStackTrace();
		}finally{
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}
	/**
	 * 保存分分核对信息
	 * @param ffhd 分分核对信息
	 * @return ReturnValue
	 */
	public ReturnValue saveFFHD(FFHDValue ffhd){
		ReturnValue result = new ReturnValue();
		Connection conn = null;
		try{
			conn = DAO.getConnection();
			try{
				conn.setAutoCommit(false);
				if(ffhd != null && ffhd.getFdv() != null && ffhd.getFdv().size()>0){
					FirmDateValue fdv = ffhd.getFdv().get(0);
					if(fdv != null){
						DAO.delFFHD(fdv.bankID, null, fdv.tradeDate, conn);
						DAO.addFFHD(ffhd, conn);
					}
				}
				result.remark = "写入分分核对信息成功";
				conn.commit();
			}catch(SQLException e){
				conn.rollback();
				result.result = -1;
				result.remark = "写入分分核对信息，数据库异常";
				e.printStackTrace();
			}finally{
				conn.setAutoCommit(true);
			}
		}catch(SQLException e){
			result.result = -1;
			result.remark = "传入分分核对信息，数据库异常";
			e.printStackTrace();
		}catch(Exception e){
			result.result = -1;
			result.remark = "传入分分核对信息，系统异常";
			e.printStackTrace();
		}finally{
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}
	/**
	 * 添加市场自有资金变动表
	 * @param xymm 市场自有资金信息
	 * @return ReturnValue
	 */
	public ReturnValue addMarketMoney(XYMarketMoney xymm){
		ReturnValue result = new ReturnValue();
		try{
			if(xymm == null){
				result.result = -1;
				result.remark = "添加流水时，传入对象为空";
			}else if(xymm.bankNumber != null && xymm.bankNumber.trim().length()>0){
				Vector<XYMarketMoney> vec = DAO.getMarketMoney(" and bankID='"+xymm.bankID+"' and bankNumber='"+xymm.bankNumber+"' and bankNumber is not null");
				if(vec != null && vec.size()>0){
					result.result = -1;
					result.remark = "银行["+xymm.bankID+"]资金划转["+xymm.bankNumber+"]已经存在";
				}
			}
			if(result.result == 0){
				int num = DAO.addMarketMoney(xymm);
				result.remark = "添加入了["+num+"]条流水";
			}
		}catch(SQLException e){
			e.printStackTrace();
			result.result = -1;
			result.remark = "添加自有金额，数据库异常";
		}catch(Exception e){
			e.printStackTrace();
			result.result = -2;
			result.remark = "添加自有金额，系统异常";
		}
		return result;
	}
	/**
	 * 修改市场自有资金变动表
	 * @param xymm 市场自有资金信息
	 * @return ReturnValue
	 */
	public ReturnValue modMarketMoney(XYMarketMoney xymm){
		ReturnValue result = new ReturnValue();
		try{
			int num = DAO.modMarketMoney(xymm);
			result.remark = "修改市场自有资金["+num+"]条";
		}catch(SQLException e){
			e.printStackTrace();
			result.result = -1;
			result.remark = "修改自有资金表数据库异常";
		}catch(Exception e){
			e.printStackTrace();
			result.result = -2;
			result.remark = "修改自有资金表系统异常";
		}
		return result;
	}
	//---------------------------------------------招行订制  start------------------------------------------------
	/**
	 * 提供给适配器的保存客户协议状态（增量）对账文件B02的方法
	 * @param mv：B02 data  ready: true:insertFirmTradeStatus false:checkMoney
	 * @return long 0:success <>0:failure
	 */
	public long insertFirmTradeStatus(Vector<FirmTradeStatus> veFirmStatus, int ready){
		long result = 0;
		if(ready==1 || ready==2){
			if(veFirmStatus!=null){
				try{
					int size = veFirmStatus.size();
					for(int i=0;i<size;i++){
						FirmTradeStatus val = veFirmStatus.get(i);
						System.out.println("客户协议状态（增量）对账文件B02："+val.toString());
						//BANKID, BANKACC, COMPAREDATE
						String sql  = "where BANKID='"+val.BankId+"' and trunc(COMPAREDATE)=to_date('"+val.compareDate+"','yyyy-MM-dd') and BANKACC='"+val.BankAcc+"'";
						log("SQL语句：【"+sql+"】");
						Vector<FirmTradeStatus> veVal = DAO.getFirmTradeStatusList(sql);
						if(veVal==null||veVal.size()==0){
							if(ready==2){
								val.Status = "0";
//								val.BankAcc = -1+"";
							}
							DAO.addFirmTradeStatus(val);
						}
					}
				}catch(SQLException e){
					e.printStackTrace();
					result = -1;
				}catch(Exception e){
					e.printStackTrace();
					result = -1;
				}finally{
					
				}
			}else {
				result = -1;
			}
		}else if(ready==3){
			
		}
		return result;
	}
	/**
	 * 提供给适配器的保存账户类交易对账明细文件B03的方法
	 * @param mv：B03 data  ready: true:insertTradeDetailAccount false:checkMoney
	 * @return long 0:success <>0:failure
	 */
	public long insertTradeDetailAccount(Vector<TradeDetailAccount> veDetail, int ready){
		long result = 0;
		if(ready==1||ready==2){
			try{
				if(veDetail!=null){
					int size = veDetail.size();
					for(int i=0;i<size;i++){
						TradeDetailAccount val = veDetail.get(i);
						//BANKID, BKSERIAL, COMPAREDATE
						String sql = "where BANKID='"+val.BankId+"' and BKSERIAL='"+val.BkSerial+"' and trunc(COMPAREDATE)=to_date('"+val.compareDate+"','yyyy-MM-dd')";
						log("SQL语句：【"+sql+"】");
						Vector<TradeDetailAccount> vec = DAO.getTradeDetailAccountList(sql);
						if(vec==null || vec.size()==0){
//							if(ready==2){
//								v
//							}
							DAO.addTradeDetailAccount(val);
						}
					}
				}else {
					result = -1;
				}
			}catch(SQLException e){
				e.printStackTrace();
				result = -1;
			}catch(Exception e){
				e.printStackTrace();
				result = -1;
			}
		}else if(ready == 3){
			
		}
		return result;
	}
	/**
	 * 市场端预签约方法
	 * 变更 交易商代码与银行帐号对应关系 的签约状态
	 */
	public ReturnValue preOpenAccount(CorrespondValue cv)
	{
		System.out.println(">>>>市场开户方法>>>>时间："+Tool.fmtTime(new java.util.Date())+">>>>\ncv["+cv.toString()+"]");
		long checkResult = chenckCorrespondValue(cv);
		ReturnValue rv = new ReturnValue();
		CorrespondValue cv2 = null;
		if(checkResult==0)
		{
			rv.actionId=getMktActionID();
			try {
				cv.isOpen=1;
				List<CorrespondValue> cvresult=null;
				if(cv.bankID.equals("005")){//如果是农行的户，市场端就不校验银行卡号 张锦锦 2011.11.03
					cvresult = DAO.getCorrespondList(" where bankId='"+cv.bankID.trim()+"' and firmId='"+cv.firmID.trim()+"' ");
				}else{//如果不是农行的户，则市场端校验银行卡号  张锦锦 2011.11.03
					cvresult = DAO.getCorrespondList(" where bankId='"+cv.bankID.trim()+"' and firmId='"+cv.firmID.trim()+"' and account like '%"+cv.account.trim()+"%'");
				}
				if(cvresult == null || cvresult.size()<=0)
				{
					rv.result = ErrorCode.rgstAccount_InfoHalfbaked;
				}else{
					cv2 = cvresult.get(0);
					cv2.isOpen = 2;
					cv2.status = cv.status;
					BankAdapterRMI bankadapter = this.getAdapter(cv2.bankID);
					if(bankadapter==null){
						rv.result = ErrorCode.GetAdapter_Error;
					}else{
						cv2.bankCardPassword=cv.bankCardPassword;//市场端签约，银行卡密码发给银行校验  张锦锦 2011.11.03
						
						rv = bankadapter.rgstAccountQuery(cv2);
						if(rv.result == 0){
							if(cv.bankID.equals("005")){//农行市场端签约
								cv2.bankCardPassword=null;//市场端签约，市场不记录客户银行卡密码  张锦锦 2011.11.03
								cv2.account1=rv.remark;//银行内部账号，农行适配器传过来，保留在数据库中
							}
							rv.result = DAO.modCorrespond(cv2);
						}
					}
				}
			} catch (SQLException e) {
				rv.result=(int) ErrorCode.rgstAccount_DatabaseException;
				e.printStackTrace();
				log("市场预签约 交易商代码与银行帐号对应SQLException,"+e);
			} catch (Exception e) {
				rv.result=(int) ErrorCode.rgstAccount_SysException;
				e.printStackTrace();
				log("市场预签约 交易商代码与银行帐号对应SQLException,"+e);
			}
		}else{
			rv.result=(int) checkResult;
			rv.remark="交易商代码与银行、帐号对应有误!";
		}
		return rv;
	}
	//---------------------------------------------招行订制  end------------------------------------------------
	
	
	
	//---------------------------------------------民生银行清算---------------------------------begin/
	public ReturnValue sendCMBCQSValue(String bankID, Date date) {
		ReturnValue result = new ReturnValue();
		Connection conn = null;
		List<Sbusi> sbusis = new ArrayList<Sbusi>();
		List<Sbala> sbalas = new ArrayList<Sbala>();
		List<Spay> spays = new ArrayList<Spay>();
		List<Accr> accrs = new ArrayList<Accr>();
		try {
			conn = DAO.getConnection();
			Vector<FirmBalance> vector = DAO.getFirmBalance(bankID,date);
			double QYHZ=0;
			double FeeHZ=0;
			for(int i=0;i<vector.size();i++) { 
				// 1、存管客户交易资金净额清算文件
				Sbusi sbusi = new Sbusi();
				sbusi.setMoney(vector.get(i).QYChangeMoney-vector.get(i).FeeMoney);
				sbusi.setsCustAcct(vector.get(i).firmID);
				sbusi.setbCustAcct(vector.get(i).account);
				sbusi.setMoneyType("0");
				if(sbusi.getMoney()!=0){
					// 1、存管客户交易资金净额清算文件
					sbusis.add(sbusi);
				}
				
				// 2、存管客户资金余额明细文件
				Sbala sbala = new Sbala();
				sbala.setbCustAcct(vector.get(i).account);//存管账号/银行结算账号
				sbala.setsCustAcct(vector.get(i).firmID);//商品交易所资金账号
				sbala.setMoney(vector.get(i).QYMoney); //权益
				sbala.setMoneyType("0");//币种
				sbalas.add(sbala);
				
				QYHZ+=(vector.get(i).QYChangeMoney);
				FeeHZ+=vector.get(i).FeeMoney;
			}
			
			if(QYHZ!=0){//权益变化
			Spay spay = new Spay();
			spay.setMoney(QYHZ);
			spay.setBusinCode("O");
			spay.setMoneyType("0");//币种
			spays.add(spay);
			}
			if(FeeHZ!=0){//手续费
				Spay spay = new Spay();
				spay.setMoney(FeeHZ*(-1));
				spay.setBusinCode("C");
				spay.setMoneyType("0");//币种
				spays.add(spay);
			}
			// 4、存管客户批量利息入帐文件（不是必须文件，市场有利息业务，则发送此文件）
//			accrs = DAO.getAccr(bankID,date,conn);
			
			BankAdapterRMI adapter = this.getAdapter(bankID);
			if (adapter == null) {
				result.result=-30011;
				log("发送民生银行清算数据，连接适配器异常，错误码："+result.result);
				return result;
			}
			//调用适配器生成清算文件并发送交易所文件生成完毕通知(24908)
			result = adapter.sendCMBCQSValue(sbusis,sbalas,spays,null,date);
		} catch (SQLException e) {
			log("发送民生银行清算数据，数据库异常");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			log("发送民生银行清算数据，连接适配器异常");
			e.printStackTrace();
		} catch (Exception e) {
			result.result=-1;
			result.remark="发送民生银行清算数据，连接适配器异常";
			log("发送民生银行清算数据，连接适配器异常");
			e.printStackTrace();
		}finally{
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}
	
	
	//***********************************网站预签约调用方法 start******************************
	public ReturnValue marketOpenAccount(CorrespondValue val){
		long result = -2;
		ReturnValue returnValue = new ReturnValue();
//		val.bankID=val.bankID.split(",")[0];
		try {
			if(DAO.getCorrespond(val.bankID,val.firmID,val.account)==null)
			{
				if(!"15".equals(val.bankID)){
					val.status = 1;
				}
				val.isOpen = 0;
				result = rgstAccount(val);
			}
		} catch (SQLException e) {
			returnValue.result =  -40007;
			returnValue.remark = "系统异常";
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			returnValue.result =  -40007;
			returnValue.remark = "系统异常";
			e.printStackTrace();
		}
		if(result == 0 ){
			if("15".equals(val.bankID)){
				returnValue = preOpenAccount(val);
				if(returnValue.result == 0){
					return returnValue;
				}else if(returnValue.result<0){
					delAccountNoAdapter(val);
				}
			}else{
				returnValue.result = 0;
				returnValue.remark = "注册成功";
			}
		}else if(result == -40001){
			returnValue.result = -40001;
			returnValue.remark = "信息不完整";
			
		}else if(result == -40002){
			returnValue.result = -40002;
			returnValue.remark = "交易商不存在";
		}else if(result == -40003){
			returnValue.result = -40003;
			returnValue.remark = "银行不存在";
		}else if(result == -40004){
			returnValue.result = -40004;
			returnValue.remark = "帐号已注册";
		}else if(result == -40005){
			returnValue.result = -40005;
			returnValue.remark = "银行签约失败";
		}else if(result == -40006){
			returnValue.result = -40006;
			returnValue.remark = "数据库操作失败";
		}else if(result == -40007){
			returnValue.result = -40008;
			returnValue.remark = "交易商密码错误";
		}else if(result == -40008){
			returnValue.result = -40007;
			returnValue.remark = "系统异常";
		}
        return returnValue;
		
	} 
	//***********************************网站预签约调用方法 end******************************
	
	
	
	
	
	//---------------------------------------------国付宝 G商贸通 定制 begin--------------------------------------------
	/**
	 *增加签约流水
	 * @param cv
	 * @return
	 */
	public ReturnValue addRgstCapitalValue(CorrespondValue cv,int type)
	{
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("增加签约流水");
		System.out.println("cv["+cv.toString()+"]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}}}}}}");
		RgstCapitalValue rc=new RgstCapitalValue();
		rc.account = cv.account!=null?cv.account:Tool.getConfig("DefaultAccount");
		rc.card = cv.card;
		rc.cardType = cv.cardType;
		rc.accountName = cv.accountName;
		rc.actionID= DAO.getActionID();
		rc.firmID=cv.firmID;
		rc.bankID=cv.bankID;
		rc.type=type;
		rc.status=2;//流水处理中
		if(type==1)
		rc.note="rgst_account";
		else rc.note="del_account";
		
		long checkResult = chenckCorrespondValue(cv);
		
		ReturnValue rv = new ReturnValue();
		
		if(checkResult==0)
		{
			String limit=Tool.getConfig("isLimit");//解约是否判断资金情况
			if(type==2&&limit.equals("1"))
				rv=ifQuitFirm(rc.firmID,rc.bankID);
			else {
				rv.result = 0;
			}
			if(rv.result!=0){
				return rv;
			}
			rv.actionId=rc.actionID;
			try {
				Connection conn = null;
				conn = DAO.getConnection();
				
				rv.result=DAO.addRgstCapitalValue(rc,conn);
			} catch (SQLException e) {
				rv.result=(int) ErrorCode.rgstAccount_DatabaseException;
				e.printStackTrace();
				log("银行开户 交易商代码与银行帐号对应SQLException,"+e);
			} catch (Exception e) {
				rv.result=(int) ErrorCode.rgstAccount_SysException;
				e.printStackTrace();
				log("银行开户 交易商代码与银行帐号对应SQLException,"+e);
			}
			
		}
		else
		{
			rv.result=(int) checkResult;
			rv.remark="交易商代码与银行、帐号对应有误!";
		}
		return rv;
	}
	
	/**
	 * 查询市场签约流水
	 */
	public Vector<RgstCapitalValue> getRgstCapitalValue(String file){
		
		Connection conn = null;
		try{
			conn = DAO.getConnection();
			Vector<RgstCapitalValue> vector = DAO.getRgstCapitalValue(file,conn);
			return vector;
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DAO.closeStatement(null, null, conn);
		}
		return null;
	}
	
	/**
	 * 修改签约流水
	 * @param cv
	 * @return
	 */
	public ReturnValue modRgstCapitalValue(RgstCapitalValue rc)
	{
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("修改签约流水");
		System.out.println("rc["+rc.toString()+"]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}}}}}}");
		CorrespondValue cv=new CorrespondValue();
		cv.account = Tool.getConfig("DefaultAccount");
		cv.card = rc.card;
		cv.cardType = rc.cardType;
		cv.accountName = rc.accountName;
		cv.firmID=rc.firmID;
		cv.account=rc.account;
		cv.bankID=rc.bankID;
		long checkResult = chenckCorrespondValue(cv);
		ReturnValue rv = new ReturnValue();
		if(checkResult==0)
		{
			rv.actionId=rc.actionID;
			try {
				Connection conn = null;
				conn = DAO.getConnection();
				
				rv.result=DAO.modRgstCapitalValue(rc.bankID,rc.firmID,rc.account,rc.compleTime,rc.status,
						  rc.actionID,rc.type,conn);
			} catch (SQLException e) {
				rv.result=(int) ErrorCode.rgstAccount_DatabaseException;
				e.printStackTrace();
				log("银行开户 交易商代码与银行帐号对应SQLException,"+e);
			} catch (Exception e) {
				rv.result=(int) ErrorCode.rgstAccount_SysException;
				e.printStackTrace();
				log("银行开户 交易商代码与银行帐号对应SQLException,"+e);
			}
		}
		else
		{
			rv.result=(int) checkResult;
			rv.remark="交易商代码与银行、帐号对应有误!";
		}
		return rv;
	}
	
	/**
	 * 修改资金流水状态
	 * actionID   流水号，
	 * funID 	银行流水号
	 * funFlag    通过、拒绝
	 * type       出入金
	 */
	public long modMoneyCapital(long actionID,String funID,boolean funFlag){
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("actionID["+actionID+"]funFlag["+funFlag+"]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}}}}}}");
		String auditInfo="修改资金流水状态："+actionID+";";
		//数据库连接
		Connection conn = null;

		//当前系统时间
		Timestamp curTime = new Timestamp(System.currentTimeMillis());

		//资金划转流水对象
		CapitalValue capital =null;

		//手续费流水对象
		CapitalValue rate =null;
		
		//适配器返回结果
		ReturnValue rVal = null;

		//返回码
		long result = 0;
		
		
		
			try 
			{
				conn = DAO.getConnection();
				Vector<CapitalValue> list = DAO.getCapitalInfoList("where actionid="+actionID, conn);//获取资金流水对象（包括出金和出金手续费流水）
				for (int i = 0; i < list.size(); i++) 
				{
					CapitalValue val = (CapitalValue)list.get(i);
					if(val.type == OUTMONEY || val.type == INMONEY)
					{
						capital = val;//出金流水
						if(capital.status==0 || capital.status==1){
							log("本条记录已经明确"+new java.util.Date());
							result = -3;
							return result;
						}
					}
					else if(val.type == RATE)
					{
						rate = val;//手续费流水
					}
				}
				if(capital != null && rate != null){
					if(funFlag)//审核通过
					{
						if(result == 0)
						{
							try 
							{
								//启动数据库事务
								conn.setAutoCommit(false);
								if(capital.type==OUTMONEY){
									System.out.println("[流水信息：]"+capital.toString());
									rVal = new ReturnValue();
									rVal.result=0;
									rVal.actionId=capital.actionID;
									rVal.funID= funID;

									dataProcess.updateFundsFull(capital.firmID, dataProcess.getOUTSUMMARY()+"", dataProcess.getBANKSUB().get(capital.bankID), capital.money, actionID,conn);//扣除出金
									dataProcess.updateFundsFull(capital.firmID, dataProcess.getFEESUMMARY()+"", null, rate.money,actionID, conn);//扣除手续费
									dataProcess.updateFrozenFunds(capital.firmID, -1*Arith.add(capital.money, rate.money), conn);//资金解冻
									//修改交易商银行接口冻结资金
									this.bankFrozenFuns(capital.firmID, capital.bankID, null, -1*Arith.add(capital.money, rate.money), conn);

									DAO.modCapitalInfoStatus(capital.iD,rVal.funID, 0,curTime, conn);
									DAO.modCapitalInfoStatus(rate.iD,rVal.funID, 0,curTime, conn);
	
									DAO.modCapitalInfoNote(capital.iD, "["+capital.note+"]市场处理通过，银行处理成功，出金成功", conn);
									DAO.modCapitalInfoNote(rate.iD, "["+capital.note+"]市场处理通过，银行处理成功，扣除手续费成功", conn);

									log(auditInfo+"市场处理通过，银行处理成功,出金成功,扣除手续费成功,银行流水号="+rVal.funID);

								}else if(capital.type==INMONEY){
									rVal = new ReturnValue();
									rVal.result=0;
									rVal.actionId=capital.actionID;
									rVal.funID=funID;
									//调用交易系统接口
									dataProcess.updateFundsFull(capital.firmID, dataProcess.getINSUMMARY()+"", dataProcess.getBANKSUB().get(capital.bankID), capital.money,actionID, conn);//添加入金
									dataProcess.updateFundsFull(capital.firmID, dataProcess.getFEESUMMARY()+"", null, rate.money,actionID, conn);//扣除手续费

									DAO.modCapitalInfoStatus(capital.iD,rVal.funID, 0,curTime, conn);
									DAO.modCapitalInfoStatus(rate.iD,rVal.funID, 0, curTime,conn);
								}
								conn.commit();
							}catch(SQLException e){
								conn.rollback();
								e.printStackTrace();
								result = ErrorCode.checkTrans_DataBaseException;
								log(auditInfo+"市场处理出金SQLException，错误码="+result+"  "+e);
							}
							finally
							{
								conn.setAutoCommit(true);
							}
						}
					}else{//审核拒绝
						try{
							//启动数据库事务
							conn.setAutoCommit(false);
							if(capital.type==OUTMONEY){
								rVal = new ReturnValue();
								rVal.result=0;
								rVal.actionId=capital.actionID;
								rVal.funID=funID;
								//调用交易系统接口
								dataProcess.updateFrozenFunds(capital.firmID, -1*Arith.add(capital.money, rate.money), conn);//资金解冻
								//修改交易商银行接口冻结资金
								this.bankFrozenFuns(capital.firmID, capital.bankID, null, -1*Arith.add(capital.money, rate.money), conn);
								DAO.modCapitalInfoStatus(capital.iD,rVal==null?"":rVal.funID, 1,curTime, conn);
								DAO.modCapitalInfoStatus(rate.iD, rVal==null?"":rVal.funID,1,curTime, conn);

								DAO.modCapitalInfoNote(capital.iD, "["+capital.note+"]市场处理拒绝成功", conn);
								DAO.modCapitalInfoNote(rate.iD, "["+capital.note+"]市场处理拒绝成功", conn);

								log(auditInfo+"市场处理拒绝成功");
							}else if(capital.type == INMONEY){
								rVal = new ReturnValue();
								rVal.result=0;
								rVal.actionId=capital.actionID;
								rVal.funID=funID;
								DAO.modCapitalInfoStatus(capital.iD,rVal==null?"":rVal.funID, 1,curTime, conn);
								DAO.modCapitalInfoStatus(rate.iD,rVal==null?"":rVal.funID, 1,curTime, conn);
							}
							conn.commit();
						}catch(SQLException e){
							conn.rollback();
							e.printStackTrace();
							result = ErrorCode.checkTrans_DataBaseException;
							log(auditInfo+"市场处理出金SQLException，错误码="+result+"  "+e);
						}finally{
							conn.setAutoCommit(true);
						}
					}
				}
			}catch(SQLException e){
				e.printStackTrace();
				result = ErrorCode.checkTrans_DataBaseException;
				log(auditInfo+"市场处理出金SQLException，错误码="+result+"  "+e);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}finally{
				DAO.closeStatement(null, null, conn);
			}
		
		return result;
	}
	
	/**
	 * 国付宝入金，由市场端调用
	 * @param bankID 银行代码
	 * @param firmID 交易商代码
	 * @param account 交易商银行帐号
	 * @param accountPwd 交易商银行帐号密码
	 * @param money   金额
	 * @param remark   备注
	 * @return long 银行接口业务流水号,返回<0的值表示操作失败
	 *                             (-10001：非法银行帐号 -10002：非法交易商代码 -10003：交易商代码和帐号对应关系错误 -10004：数据库异常 -10005：系统异常 -10006：参数错误 --10008:适配器提交失败 )
	 */
	public long inMoneyMarketGS(String bankID, String firmID ,String account,String accountPwd,double money ,String remark)
	{
		System.out.println("{{{{{{{{{{{{{{{{{{{{国付宝入金，由市场端调用{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("bankID["+bankID+"]firmID["+firmID+"]account["+account+"]accountPwd["+accountPwd+"]money["+money+"]remark["+remark+"]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}}}}}}");
		//返回码
		long result = 0;
		result = tradeDate(bankID);
		if(result!=0){
			return result;
		}

		//数据库连接
		Connection conn = null;

		//当前系统时间
		Timestamp curTime = new Timestamp(System.currentTimeMillis());

		//入金手续费
		double inRate = 0;
		
		//判断交易商是否为可用状态
		boolean isopen = (bankAccountIsOpen(firmID,bankID,account)==1);
		if(!isopen){
			result = ErrorCode.inMoneyM_FirmNO;
			log("市场发起入金，交易商未签约。");
			return result;
		}
		try 
		{
			conn = DAO.getConnection();

			//取得入金手续费
			inRate = getTransRate(bankID, firmID, money, INMONEY,0,account,conn);
			
			/**********************接口参数合法性检查***************************/
			if((firmID == null || firmID.trim().equals("")) && (account == null || account.trim().equals("")))
			{
				//参数非法
				result = ErrorCode.inMoneyM_ErrorParameter;
				log("市场发起入金，参数非法，错误码="+result);
			}
			else if(firmID == null || firmID.trim().equals(""))
			{
				Vector<CorrespondValue> cList = DAO.getCorrespondList("where bankid='"+bankID+"' and account='"+account+"'", conn);
				if(cList.size() > 0)
				{
					firmID = ((CorrespondValue)cList.get(0)).firmID;
				}
				else
				{
					//非法银行帐号
					result = ErrorCode.inMoneyM_ErrorBankAcount;
					log("市场发起入金，非法银行帐号，错误码="+result);
				}
			}
			else if(account == null || account.trim().equals(""))
			{
				Vector<CorrespondValue> cList = DAO.getCorrespondList("where bankid='"+bankID+"' and firmid='"+firmID+"'", conn);
				if(cList.size() > 0)
				{
					account = ((CorrespondValue)cList.get(0)).account;
				}
				else
				{
					//非法交易商代码
					result = ErrorCode.inMoneyM_ErrorFirmCode;	
					log("市场发起入金，非法交易商代码，错误码="+result);
				}
			}
			else if(DAO.getCorrespond(bankID, firmID, account, conn) == null)
			{
				//交易商代码和帐号对应关系错误
				result = ErrorCode.inMoneyM_ErrorCorrespond;
				log("市场发起入金，交易商代码和帐号对应关系错误，错误码="+result);
			}
			Vector<CorrespondValue> cList = DAO.getCorrespondList("where bankid='"+bankID+"' and firmid='"+firmID+"' and status=0 ", conn);
			if(cList == null || cList.size() != 1){
				result = ErrorCode.inMoneyM_ErrorCorrespond;
			}
			if(result==0 && (inRate==-1 || inRate==-2))
			{	
				result=ErrorCode.inMoneyM_ErrorGetRate;
				log("市场发起入金，计算手续费错误，错误码="+result);
			}
					
			/********************************************************************/

			/*********************检查交易商是否符合入金条件,符合条件进行入金处理**************************/
			if(result == 0)
			{
				long checkTrans=0;
				if(getConfig("inMoneyAudit") != null && getConfig("inMoneyAudit").trim().equals("true")){
					checkTrans=checkTrans(bankID, firmID, money, curTime, INMONEY, conn);
				}
				if(checkTrans== 0)
				{
					//取得市场业务流水号
					result = DAO.getActionID(conn);
//					try 
//					{
//						//启动数据库事务
//						conn.setAutoCommit(false);
//
//						//记录转帐模块资金流水
//						CapitalValue cVal = new CapitalValue();
//						cVal.actionID = result;
//						cVal.bankID = bankID;
//						cVal.creditID = firmID;
//						cVal.debitID = dataProcess.getBANKSUB().get(bankID);
//						cVal.firmID = firmID;
//						cVal.money = money;
//						cVal.note = remark;
//						cVal.oprcode = dataProcess.getINSUMMARY()+"";
//						cVal.status = 2;
//						cVal.type = INMONEY;
//						cVal.account=account;
//						
//						DAO.addCapitalInfo(cVal, conn);//入金流水
//
//						cVal.creditID = "Market";
//						cVal.debitID = firmID;
//						cVal.money = inRate;
//						cVal.oprcode = dataProcess.getFEESUMMARY()+"";
//						cVal.type = RATE;
//
//						DAO.addCapitalInfo(cVal, conn);//手续费流水
//
//						conn.commit();
//					} 
//					catch(SQLException e) 
//					{
//						e.printStackTrace();
//						conn.rollback();
//						result=ErrorCode.inMoneyM_ErrorAddCapital;
//						log("市场端发起入金写资金流水SQLException,数据回滚:"+e);
//						throw e;
//					}
//					finally
//					{
//						conn.setAutoCommit(true);
//					}
				}
				else
				{
					result = checkTrans;
				}
			}
			
			/********************************************************************/
		} 
		catch(SQLException e) 
		{
			e.printStackTrace();
			result = ErrorCode.inMoneyM_DataBaseException;
			log("市场端发起入金SQLException:"+result);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			result = ErrorCode.inMoneyM_SysException;
			log("市场端发起入金Exception:"+result);
		}
		finally
		{
			DAO.closeStatement(null, null, conn);
		}

		return result;
	}
	
	/**
	 * 国付宝市场收益划拨
	 * @param bankID 银行代码
	 * @param money 金额
	 * @param firmID 交易商代码
	 * @param bankAccount 银行帐号
	 * @param funID 银行流水号
	 * @param express 0：正常 1：加急
	 * @param type 0：市场端出金 1：银行端同步出金 2：银行端异步出金 3：银行端出金必须审核
	 * @return long 银行接口业务流水号,返回<0的值表示操作失败                          
	 * @throws 
	 */
	public ReturnValue outMoneyGS(String bankID,double money,String firmID,String bankAccount,String funID,String operator,int express,int type)
	{
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{国付宝市场收益划拨{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("bankID["+bankID+"]money["+money+"]firmID["+firmID+"]bankAccount["+bankAccount+"]funID["+funID+"]operator["+operator+"]express["+express+"]type["+type+"]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}}}}}}");
		//返回结果集
		ReturnValue rv=new ReturnValue();
		//市场流水号
		long actionID=0;
		//资金划转流水对象
		CapitalValue capital =null;
		//手续费流水对象
		CapitalValue rate =null;
		//当前系统时间
		Timestamp curTime = new Timestamp(System.currentTimeMillis());
		String auditInfo="市场收益划拨，银行号:"+bankID;
		Connection conn;
		try
		{
			conn = DAO.getConnection();
		
			HashMap<Integer,Long> map = handleOUtMoney(rv.result,conn,0,0,bankID,money,firmID,funID,express,type);
			actionID = map.get(1).longValue();
			rv.result=actionID;
			
			Vector<CapitalValue> list = DAO.getCapitalInfoList("where actionid="+actionID, conn);//获取资金流水对象（包括出金和出金手续费流水）
			for (int i = 0; i < list.size(); i++) 
			{
				CapitalValue val = (CapitalValue)list.get(i);
				if(val.type == OUTMONEY)
				{
					capital = val;//出金流水
				}
				else if(val.type == RATE)
				{
					rate = val;//手续费流水
				}
			}
			
			if(capital!=null && rate!=null)
			{
				BankValue bv = DAO.getBank(capital.bankID);
				OutMoneyVO outMoneyInfo=new OutMoneyVO(capital.bankID,bv.bankName,capital.money,capital.firmID,null,null,actionID,capital.express,capital.funID);
				
				BankAdapterRMI bankadapter = getAdapter(bankID);
				if(bankadapter == null){
					rv.result = ErrorCode.GetAdapter_Error;
					log("处理器连接适配器["+bankID+"]失败");
					return rv;
				}else{
					try{
						conn.setAutoCommit(false);
						Vector<CapitalValue> ll = DAO.getCapitalInfoList("where actionID='"+rv.result+"' and (status=4 or status=3) for update ",conn);
						if(ll == null || ll.size() <= 0){
							rv.result = ErrorCode.outMoneyAudit_OradyAudite;
							log("信息已在处理中");
							return rv;
						}
						DAO.modCapitalInfoStatus(capital.iD,capital.funID, 2,curTime, conn);
						DAO.modCapitalInfoStatus(rate.iD,rate.funID, 2,curTime, conn);
						conn.commit();
					}catch(SQLException e){
						conn.rollback();
						e.printStackTrace();
						rv.result = ErrorCode.outMoneyAudit_DataBaseException;
						log("修改记录为处理中失败，SQL异常");
						return rv;
					}finally{
						conn.setAutoCommit(true);
					}
					//调用适配器市场端出金接口
					rv = bankadapter.outMoneyMarketDone(outMoneyInfo);
					
					if(rv.result == 0) {
						DAO.modCapitalInfoStatus(capital.iD,rv.funID, 0,curTime, conn);
						DAO.modCapitalInfoStatus(rate.iD,rv.funID, 0,curTime, conn);
						
						DAO.modCapitalInfoNote(capital.iD, "["+capital.note+"]审核通过，银行处理成功，出金成功", conn);
						DAO.modCapitalInfoNote(rate.iD, "["+capital.note+"]审核通过，银行处理成功，扣除手续费成功", conn);
						
						log(auditInfo+"审核通过，银行处理成功,出金成功,扣除手续费成功,银行流水号="+rv.funID);
					} else if(rv.result == 5) {//状态修改为rVal.result：发送给银行 处理过程中
						
						DAO.modCapitalInfoStatus(capital.iD,rv.funID, 2,curTime, conn);
						DAO.modCapitalInfoStatus(rate.iD, rv.funID,2,curTime, conn);
						DAO.modCapitalInfoNote(capital.iD, "["+capital.note+"]审核通过，银行处理成功，等待处理中", conn);
						DAO.modCapitalInfoNote(rate.iD, "["+capital.note+"]审核通过，银行处理成功，等待处理中", conn);

						log(auditInfo+"审核通过，银行处理成功，等待处理中");
					} else if(rv.result == ErrorCode.bankNull) {//状态修改为rVal.result：发送给银行 处理过程中
						
						DAO.modCapitalInfoStatus(capital.iD,rv.funID, 5,curTime, conn);
						DAO.modCapitalInfoStatus(rate.iD, rv.funID,5,curTime, conn);
						DAO.modCapitalInfoNote(capital.iD, "["+capital.note+"]审核通过，银行无返回", conn);
						DAO.modCapitalInfoNote(rate.iD, "["+capital.note+"]审核通过，银行无返回", conn);

						log(auditInfo+"审核通过，银行无返回");
					} else if(rv.result == ErrorCode.ActionID_Error) {//状态修改为rVal.result：发送给银行 处理过程中
						
						DAO.modCapitalInfoStatus(capital.iD,rv.funID, 6,curTime, conn);
						DAO.modCapitalInfoStatus(rate.iD, rv.funID,6,curTime, conn);
						DAO.modCapitalInfoNote(capital.iD, "["+capital.note+"]审核通过，银行返回的市场流水号和市场本身流水不一致", conn);
						DAO.modCapitalInfoNote(rate.iD, "["+capital.note+"]审核通过，银行返回的市场流水号和市场本身流水不一致", conn);

						log(auditInfo+"审核通过，银行返回的市场流水号和市场本身流水不一致");
					} else {//发送给银行过程中处理失败
						if(!"false".equalsIgnoreCase(getConfig("OutFailProcess"))) {//失败后是否进行退资金操作
							DAO.modCapitalInfoStatus(capital.iD,rv.funID, 1,curTime, conn);
							DAO.modCapitalInfoStatus(rate.iD,rv.funID, 1,curTime, conn);
							DAO.modCapitalInfoNote(capital.iD,"["+capital.note+"]审核通过，银行处理失败，出金金额已解冻", conn);
							DAO.modCapitalInfoNote(rate.iD, "["+capital.note+"]审核通过，银行处理失败，手续费金额已解冻", conn);
							log(auditInfo+"审核通过，银行处理失败,退还全部出金和手续费，错误码="+rv.result);
						} else {//状态修改为1：发送给银行过程中处理失败
							DAO.modCapitalInfoStatus(capital.iD,rv.funID, 1,curTime, conn);
							DAO.modCapitalInfoStatus(rate.iD, rv.funID,1,curTime, conn);
							DAO.modCapitalInfoNote(capital.iD,"["+capital.note+"]审核通过，银行处理失败，需手工解冻出金金额", conn);
							DAO.modCapitalInfoNote(rate.iD, "["+capital.note+"]审核通过，银行处理失败，需手工解冻手续费金额", conn);

							log(auditInfo+"审核通过，银行处理失败，需手工解冻出金和手续费，错误码="+rv.result);
						}
					}
					conn.commit();
				}
			}
		} catch (SQLException e1)
		{
			e1.printStackTrace();
		} catch (ClassNotFoundException e1)
		{
			e1.printStackTrace();
		} catch (RemoteException e)
		{
			e.printStackTrace();
		}
		return rv;
	}
	
	//---------------------------------------------国付宝 G商贸通 定制 end--------------------------------------------
	//---------------------------------------------中行 定制 begin--------------------------------------------
	/**
	 * 银行端发起的预指定存管银行确认
	 */
	public ReturnValue SpecifiedStorageTubeBankSure() {
		ReturnValue returnValue=new ReturnValue();
		return returnValue;
	}
	/**
	 * 中行测试通讯
	 * @param bankID
	 * @return
	 */
	public ReturnValue CommunicationsTest(String bankID) {
		ReturnValue returnValue=new ReturnValue();
		BankAdapterRMI bankadapter = getAdapter(bankID);
		try{
//			 returnValue=bankadapter.CommunicatTest();//调用适配器
		}catch(Exception ex){
			ex.printStackTrace();
			returnValue.result=-1;
			returnValue.remark="调用适配器处理失败";
		}
		return returnValue;
	}
	/**
	 * 通过证件信息进行银行端发起的预签约
	 * @param bankid
	 * @param cardtype
	 * @param card
	 * @param account
	 * @return
	 */
	public ReturnValue BankYuSigning(String bankid, String cardtype,String firmid, String account) {
		
		ReturnValue returnValue=new ReturnValue();
		Connection conn=null;
		try {
			conn = DAO.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			returnValue.result=-1;
			returnValue.remark="获得数据库联接失败";
			return returnValue;
		}
		
		String fileter="where bankId='"+bankid+"' and cardtype='"+cardtype+"' and firmid='"+firmid+"' and account='"+account+"'";
		Vector <CorrespondValue>ves=null;
		try {
			ves=DAO.getCorrespondList(fileter,conn);
		} catch (SQLException e) {
			
			e.printStackTrace();
			returnValue.result=-1;
			returnValue.remark="获得对应关系失败";
			return returnValue;
		} 
		
		if (ves.size() == 1) {
			CorrespondValue value = ves.get(0);
			value.status = 2; //设置交易商账户状态为2  预签约待确定
			//value.opentime = Tool.strToDate(Tool.fmtDate(new Date())); //签约时间改为当前时间
			//修改客户账号对应关系表
			try {
				DAO.modCorrespond(value);
			} catch (SQLException e) {
				e.printStackTrace();
				returnValue.result=-1;
				returnValue.remark="修改状态失败";
				return returnValue;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				returnValue.result=-1;
				returnValue.remark="修改状态失败";
				return returnValue;
			}
			returnValue.result=0;
		}
		return returnValue;
	}
	
	/**
	 * 银行端发起清算
	 * @param bankID
	 * @param tradeDate
	 * @return
	 */
	public Hashtable<String, List> getZHQSValue(String bankID, Date tradeDate) {
		Connection conn = null;
		Hashtable<String, List> valueTmp = null;
		try{
			conn = DAO.getConnection();
			try {
				valueTmp = new Hashtable<String, List>();
				conn.setAutoCommit(false);
				//获取转账交易明细信息
				List<TransferAccountsTransactionDetailed> tatd = this.getZZJYMX(bankID, null, tradeDate,conn);
				valueTmp.put("S01", tatd);
				//获取客户账户交易状态
				List<AccountStatusReconciliation> asr = this.getKHZHZT(bankID, null, tradeDate,conn);
				valueTmp.put("S02", asr);
				//获取存管客户结息净额信息
				//Vector<StorageBearInterestList> sbil = this.getCGKHJXJE(bankID, null, tradeDate,conn);
				//valueTmp.put("S05", sbil);
				//获取存管客户资金交收明细信息
				List<StorageMoneySettlementList>  smsl = this.getCGKHZJJSMX(bankID, null, tradeDate,conn);
				valueTmp.put("S06", smsl);
				//获取存管客户资金台账余额明细信息
				List<StorageMoneyLedgerBalanceList> smlbl = this.getCGKHZJTZYEMX(bankID, null, tradeDate,conn);
				valueTmp.put("S07", smlbl);
				//获取存管银行资金交收汇总信息
				//Vector<StorageBankMoneySettlementSummary> sbmss = this.getCGYHZJJSHZ(bankID, null, tradeDate,conn);
				//valueTmp.put("S08", sbmss);
				// 获取法人存管银行资金交收汇总信息
				//Vector<LegalPersonStorageBankSummary> lpsbs = this.getFRCGYHZJJSHZ(bankID, null, tradeDate,conn);
				//valueTmp.put("S11", lpsbs);
				//获取存管银行备付金余额信息
				//Vector<StorageBankCashReserveBalance> sbcrb = this.getCGYHBFJYE(bankID, null, tradeDate,conn);
				//valueTmp.put("S12", sbcrb);
				//获取协办存管银行资金监管信息
				//Vector<StorageBankMoneySupervision> sbms = this.getXBCGYHZJJG(bankID, null, tradeDate,conn);
				//valueTmp.put("S13", sbms);
				conn.commit();
			} catch (Exception e) {
				log("查询数据库数据异常");
				e.printStackTrace();
			}finally{
				conn.setAutoCommit(true);
				DAO.closeStatement(null, null, conn);
			}
		}catch (Exception e) {
			log("获取数据库连接异常");
			e.printStackTrace();
		}
		return valueTmp;
	}
	
	/**
	 * 获取存管客户资金台账余额明细信息
	 * @param bankID
	 * @param firmIDs
	 * @param tradeDate
	 * @param conn 
	 * @return
	 * @throws Exception 
	 */
	public List<StorageMoneyLedgerBalanceList> getCGKHZJTZYEMX(String bankID, String[] firmIDs, Date tradeDate, Connection conn) throws Exception{
		List<StorageMoneyLedgerBalanceList> result = null;
		
		try {
			conn.setAutoCommit(false);
			
			result = DAO.getCGKHZJTZYEMX(bankID,tradeDate,conn);
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
		if (result == null ) {
			result = new ArrayList<StorageMoneyLedgerBalanceList>();
		}
		return result;
	}
	
	/**
	 * 中行客户账号状态对账
	 * @param states
	 * @param ready
	 * @return
	 */
	public long insertClientStates(Vector<ClientState> states, int ready) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("提供给适配器的保存银行数据的方法");
		System.out.println("mv.size()["+states.size()+"]ready["+ready+"]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}时间："+Tool.fmtTime(new java.util.Date())+"}}}}}}}}}}}}}}}}}}}}}}}}}}");
		long result = 0;
		if (ready == 1 || ready == 2) {
			if (states != null && states.size() != 0) {
				for (ClientState state : states) {
					Vector<ClientState> v_state  = null;
					//查询客户账户状态信息
					try {
						v_state = DAO.getClientState(" where bankid='"+state.getBankNo().trim()+"' and firmid='"+state.getTransFundAcc().trim()+"'" );
					} catch (SQLException e) {
						e.printStackTrace();
						log("查询交易商账户状态信息异常");
						result = -1;
						return result;
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
						log("查询交易商账户状态信息异常");
						result = -2;
						return result;
					}
					if (v_state == null || v_state.size() == 0) {
						try {
							//修改市场交易商账户信息
							DAO.addClientState(state);
						} catch (SQLException e) {
							e.printStackTrace();
							log("保存客户账户账户对账状态信息，写入数据库异常");
							result = -1;
							return result;
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
							log("保存客户账户账户对账状态信息，写入数据库异常");
							result = -2;
							return result;
						}
					}else{
						
					}
				}
			}else{
				result = -1;
			}
		}else {
			
		}
		return result;	
	}
	
	/**
	 * 发送中行清算信息
	 * @param bankID
	 * @param firmIDs
	 * @param tradeDate
	 * @return
	 */
	public ReturnValue sendZHQS(String bankID, String[] firmIDs, Date tradeDate) {
		ReturnValue result = new ReturnValue();
		Connection conn = null;
		System.out.println("中行清算业务");
		try {
			result = this.isTradeDate(tradeDate);
			if (result.result != 0) {
				return result;
			}else if (Tool.fmtDate(new java.util.Date()).equalsIgnoreCase(Tool.fmtDate(tradeDate)) && ! getTraderStatus()){
				result.result = -2;
				result.remark = "系统尚未结算，不允许发送清算数据";
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log(e.getMessage());
			result.result = -1;
			result.remark = "取得是否是交易日期异常";
			return result;
		}
		BankAdapterRMI bankAdapter = this.getAdapter(bankID);
		if (bankAdapter == null) {
			result.result = ErrorCode.GetAdapter_Error;
			result.remark = "发送["+bankID+"]银行交易商清算信息，连接适配器失败";
		}else{
			//获取清算对账信息
			try{
				conn = DAO.getConnection();
				List<TransferAccountsTransactionDetailed> tatd = null;
				List<AccountStatusReconciliation> asr = null;
				List<StorageMoneySettlementList>  smsl = null;
				List<StorageMoneyLedgerBalanceList> smlbl = null;
				try {
					//获取转账交易明细信息
					tatd = this.getZZJYMX(bankID, firmIDs, tradeDate,conn);
					//获取客户账户交易状态
					asr = this.getKHZHZT(bankID, firmIDs, tradeDate,conn);
					//获取存管客户资金交收明细信息
					smsl = this.getCGKHZJJSMX(bankID, firmIDs, tradeDate,conn);
					//获取存管客户资金台账余额明细信息
					smlbl = this.getCGKHZJTZYEMX(bankID, firmIDs, tradeDate,conn);
					//获取存管客户结息净额信息
					//Vector<StorageBearInterestList> sbil = this.getCGKHJXJE(bankID, firmIDs, tradeDate,conn);
					//获取存管银行资金交收汇总信息
					//Vector<StorageBankMoneySettlementSummary> sbmss = this.getCGYHZJJSHZ(bankID, firmIDs, tradeDate,conn);
					//valueTmp.put("S08", sbmss);
					// 获取法人存管银行资金交收汇总信息
					//Vector<LegalPersonStorageBankSummary> lpsbs = this.getFRCGYHZJJSHZ(bankID, firmIDs, tradeDate,conn);
					//valueTmp.put("S11", lpsbs);
					//获取存管银行备付金余额信息
					//Vector<StorageBankCashReserveBalance> sbcrb = this.getCGYHBFJYE(bankID, firmIDs, tradeDate,conn);
					//valueTmp.put("S12", sbcrb);
					//获取协办存管银行资金监管信息
					//Vector<StorageBankMoneySupervision> sbms = this.getXBCGYHZJJG(bankID, firmIDs, tradeDate,conn);
					//valueTmp.put("S13", sbms);
					
				} catch (Exception e) {
					result.result = -1;
					result.remark = "查询数据库数据异常";
					e.printStackTrace();
					return result;
				}finally{
					DAO.closeStatement(null, null, conn);
				}
				System.out.println("中行清算业务-查询清算文件数据信息完毕");
				//生成日终清算文件
				System.out.println("调用适配器发送清算文件");
				result = bankAdapter.sendZHQS(tatd,asr,smsl,smlbl,tradeDate);
				System.out.println("中行清算文件发送完毕");
			}catch (SQLException e) {
				result.result = -1;
				result.remark = "获取数据库连接异常";
				e.printStackTrace();
			}catch(Exception e){
				result.result = -1;
				result.remark = "发送["+bankID+"]银行交易商清算信息，适配器抛出异常";
				e.printStackTrace();
			}
		}
		return result;
	}
	/**
	 * 获取转账交易明细信息
	 * @param bankID
	 * @param firmIDs
	 * @param tradeDate
	 * @param conn 
	 * @return
	 * @throws Exception 
	 */
	public List<TransferAccountsTransactionDetailed> getZZJYMX(String bankID, String[] firmIDs, Date tradeDate, Connection conn) throws Exception{
		List<TransferAccountsTransactionDetailed> result = null;
		try {
			conn.setAutoCommit(false);
			result = DAO.getZZJYMX(bankID,firmIDs,tradeDate,conn);
			
		}catch (SQLException e) {
			throw e;
		} 
		catch (Exception e) {
			throw e;
		}
		if (result == null) {
			result = new ArrayList<TransferAccountsTransactionDetailed>();
		}
		return result;
	}
	/**
	 * 获取客户账户状态对账文档
	 * @param bankID
	 * @param firmIDs
	 * @param tradeDate
	 * @param conn 
	 * @return
	 * @throws Exception 
	 */
	public List<AccountStatusReconciliation> getKHZHZT(String bankID, String[] firmIDs, Date tradeDate, Connection conn) throws Exception{
		List<AccountStatusReconciliation> result = null;
		try {
			conn.setAutoCommit(false);
			result = DAO.getKHZHZT(bankID, firmIDs,tradeDate, conn);
		} catch (SQLException e) {
			throw e;
		}
		catch (Exception e) {
			throw e;
		}
		if (result == null ) {
			result = new ArrayList<AccountStatusReconciliation>();
		}
		return result;
	}
	/**
	 * 获取存管客户资金交收明细信息
	 * @param bankID
	 * @param firmIDs
	 * @param tradeDate
	 * @param conn 
	 * @return
	 * @throws Exception 
	 */
	public List<StorageMoneySettlementList> getCGKHZJJSMX(String bankID, String[] firmIDs, Date tradeDate, Connection conn) throws Exception{
		List<StorageMoneySettlementList> result = null;
		
		
		try {
			conn.setAutoCommit(false);
			result = DAO.getCGKHZJJSMX(bankID,tradeDate,conn);
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
		if (result == null ) {
			result = new ArrayList<StorageMoneySettlementList>();
		}
		return result;
	}
	//---------------------------------------------中行 定制 end--------------------------------------------
	
	
	//---------------------------通讯日志 start -------------------------------
	/**
	 * 录入银行接口和银行通讯信息
	 * @param log 日志信息
	 * @return int 录入条数
	 */
	public int interfaceLog(InterfaceLog log){
		int result = 0;
		try{
			result = DAO.interfaceLog(log);
		}catch(Exception e){
			log(Tool.getExceptionTrace(e));
		}
		return result;
	}
	//------------------------------通讯日志 end ---------------------------------------
	
	public long bankTransfer(long id,int optFlag){
		long result = 0 ;
		Connection conn = null;//取得数据库连接
		try {
			conn = DAO.getConnection();
			try{
				conn.setAutoCommit(false);
				Vector<BankTransferValue> bankTransferList = DAO.getBankTransferList(" and bt.id = " + id + " and bt.status = 2",conn);//取得划转记录
				if(bankTransferList != null && bankTransferList.size()>0){//如果取得了记录
					if(bankTransferList.size()==1){
						if(optFlag == 1){//审核拒绝
							DAO.modBankTransfer(id,4,conn);
						}else{//审核通过
								BankTransferValue bankTransferValue = bankTransferList.get(0);
								BankAdapterRMI bankadapter = getAdapter(bankTransferValue.payBankId);
								if(bankadapter != null){
									
									//2012.09.27 zjj 工行跨行划拨
									if(bankTransferValue.type==6&&bankTransferValue.payBankId.equals("10")){
										bankTransferValue = addBankAccount(bankTransferValue);
									}
									//-------2012.09.27 zjj ------
									
									
									ReturnValue returnValue = bankadapter.bankTransfer(bankTransferValue);
									if(returnValue != null){
										if(returnValue.result == 0){
											DAO.modBankTransfer(id,0,conn);
											
											if("true".equals(getConfig("TransferUpdateMoney"))){
												//更新凭证
												dataProcess.updateMarketMoney(bankTransferValue.info, bankTransferValue.money, conn);
											}
											
										}else if(returnValue.result == 5){
											DAO.modBankTransfer(id,3,conn);
										}else if(returnValue.result == 6){
											DAO.modBankTransfer(id,4,conn);
										}else{
											result = returnValue.result;
											DAO.modBankTransfer(id,1,conn);
										}
									}else{
										
									}
								}else{
									result = ErrorCode.GetAdapter_Error;
									this.log("获取适配器失败");
								}
						}
					}else{
						result = -1;
						this.log("记录流水重复");
					}
				}else{//如果没有取到记录
					result = -1;
					this.log("没有取到记录信息");
				}
				conn.commit();
			}catch(SQLException e){
				conn.rollback();
				result = ErrorCode.dbError;
				this.log("数据库异常");
				e.printStackTrace();
			}catch(Exception e){
				conn.rollback();
				result = ErrorCode.dbError;
				this.log("系统异常");
				e.printStackTrace();
			}finally{
				conn.setAutoCommit(true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			result = ErrorCode.dbError;
			this.log("数据库异常");
		} catch (Exception e) {
			e.printStackTrace();
			result = ErrorCode.dbError;
			this.log("数据库异常");
		}finally{
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	//-------------------------------跨行定制  2013.03.21 start------------------------------------
	/**
	 * 银行间资金划转结果通知
	 * @param actionId	市场流水号
	 * @param rst		结果 0:成功 1：失败
	 * @param info		备注信息
	 * @return
	 */
	public ReturnValue BankTransferResultNotice(long actionId,int optRst){
		System.out.println("银行间资金划转结果通知-->");
		ReturnValue returnValue = new ReturnValue();
		//取得数据库连接
		System.out.println("取得数据库连接-->");
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			try{
				conn.setAutoCommit(false);
				Vector<BankTransferValue> bankTransferList = DAO.getBankTransferList(" and bt.actionId = " + actionId + " and bt.status = 3",conn);
				if(bankTransferList != null || bankTransferList.size() > 0){
					if(bankTransferList.size()==1){
						BankTransferValue bankTransferValue = bankTransferList.get(0);
						if(optRst == 0){
							returnValue.result = DAO.modBankTransfer(bankTransferValue.id,0,conn);
							returnValue.remark = "审核通过，修改流水状态成功";
							if("true".equals(getConfig("TransferUpdateMoney"))){
								dataProcess.updateMarketMoney(bankTransferValue.info, bankTransferValue.money, conn);
							}
//							if("292".equalsIgnoreCase(bankTransferValue.payAcc)){//如果付款科目是手续费
////								dataProcess.updateVouchers(dataProcess.getMARKETFEESUB(),dataProcess.getMARKETMONEYSUB(),dataProcess.getOUTSUMMARY(),bankTransferValue.money, 0, new Timestamp(System.currentTimeMillis()),"bank_admin", conn);//从手续费科目划转到内部往来科目
//								dataProcess.updateVouchers(dataProcess.getMARKETFEESUB(),dataProcess.getBANKSUB().get(bankTransferValue.payBankId),dataProcess.getOUTSUMMARY(),bankTransferValue.money, 0, new Timestamp(System.currentTimeMillis()),"bank_admin", conn);//从手续费科目划转到内部往来科目
//							}else if("296".equalsIgnoreCase(bankTransferValue.payAcc)){//如果付款科目是市场利息
////								dataProcess.updateVouchers(dataProcess.getINTERESTSUB(),dataProcess.getMARKETMONEYSUB(),dataProcess.getOUTSUMMARY(),bankTransferValue.money, 0, new Timestamp(System.currentTimeMillis()),"bank_admin", conn);//从市场利息科目划转到内部往来科目
//								dataProcess.updateVouchers(dataProcess.getINTERESTSUB(),dataProcess.getBANKSUB().get(bankTransferValue.payBankId),dataProcess.getOUTSUMMARY(),bankTransferValue.money, 0, new Timestamp(System.currentTimeMillis()),"bank_admin", conn);//从市场利息科目划转到内部往来科目
//							}
						}else if(returnValue.result == 0){
							returnValue.result = DAO.modBankTransfer(bankTransferValue.id,1,conn);
							returnValue.remark = "审核拒绝，修改流水状态为失败";
						}
					}else{
						returnValue.result = -1;
						returnValue.remark = "查询到的流水条数重复";
					}
				}else{
					returnValue.result = -1;
					returnValue.remark = "查询到相应状态的流水";
				}
				conn.commit();
			}catch(SQLException e){
				conn.rollback();
				returnValue.result = ErrorCode.dbError;
				returnValue.remark = "修改流水时数据库异常";
				e.printStackTrace();
			}catch(Exception e){
				conn.rollback();
				returnValue.result = ErrorCode.dbError;
				returnValue.remark = "修改流水时系统异常";
				e.printStackTrace();
			}finally{
				conn.setAutoCommit(true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			returnValue.result = ErrorCode.dbError;
			returnValue.remark = "修改流水时数据库异常";
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			returnValue.result = ErrorCode.dbError;
			returnValue.remark = "修改流水时系统异常";
			e.printStackTrace();
		}finally{
			DAO.closeStatement(null, null, conn);
		}
		return returnValue;
	}
	
	/**
	 * 市场出入金
	 */
	public ReturnValue marketTransfer(BankTransferValue bankTransferValue){
		ReturnValue returnValue = new ReturnValue();
		Connection conn = null;//取得数据库连接
		try {
			conn = DAO.getConnection();
			try{
				returnValue = marketTransfer(bankTransferValue,conn);
			}catch(SQLException e){
				
			}catch(Exception e){
				
			}finally{
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			returnValue.result = ErrorCode.dbError;
			returnValue.remark = "数据库异常";
		} catch (Exception e) {
			e.printStackTrace();
			returnValue.result = ErrorCode.dbError;
			returnValue.remark = "系统异常";
		}finally{
			DAO.closeStatement(null, null, conn);
		}
		return returnValue;
	}
	
	/**
	 * 市场出入金
	 */
	public ReturnValue marketTransfer(BankTransferValue bankTransferValue,Connection conn)throws SQLException,Exception{
		System.out.println("--------->市场出入金");
		ReturnValue returnValue = new ReturnValue();
		//判重		
		Vector<BankTransferValue> bankTransferList = null;
		bankTransferList = DAO.getBankTransferList(" and bt.funId = " + bankTransferValue.funId,conn);
		if(bankTransferList != null && bankTransferList.size()>0){
			returnValue.result = -1;
			returnValue.remark = "查询银行流水号已存在";
		}else{
//			if("1".equalsIgnoreCase(bankTransferValue.info)){//手续费扣划
//				dataProcess.updateVouchers(dataProcess.getMARKETSUB(),dataProcess.getBANKSUB().get(bankTransferValue.payBankId),dataProcess.getOUTSUMMARY(),bankTransferValue.money, 0, new Timestamp(System.currentTimeMillis()),"bank_admin", conn);
//			}else if("2".equalsIgnoreCase(bankTransferValue.info)){//利息划入
//				dataProcess.updateVouchers(dataProcess.getBANKSUB().get(bankTransferValue.recBankId),dataProcess.getINTERESTSUB(),dataProcess.getOUTSUMMARY(),bankTransferValue.money, 0, new Timestamp(System.currentTimeMillis()),"bank_admin", conn);
//			}else{
//				throw new SQLException("传入信息中的配置类型不对["+bankTransferValue.info+"]");
//			}
			returnValue.actionId = DAO.addBankTransfer(bankTransferValue,conn);
			returnValue.remark = "出入金成功";
		}
		System.out.println(returnValue.toString());
		return returnValue;
	}
	//2012.09.27 zjj 工行跨行划拨
	public BankTransferValue addBankAccount(BankTransferValue bankTransferValue) throws SQLException, ClassNotFoundException{
		this.log("跨行转账收款银行ID：" + bankTransferValue.recBankId);
		TransferBank bankAccount = DAO.getTransferBank(bankTransferValue.recBankId);
		this.log("跨行转账收款银行账户：" + bankAccount.Id);
		System.out.println("跨行转账收款银行账户：" + bankAccount.Id);
		bankTransferValue.account=bankAccount.Id;
		bankTransferValue.Name=bankAccount.Name;
		bankTransferValue.VldDt=bankAccount.VldDt;
		bankTransferValue.Pwd =bankAccount.Pwd;
		bankTransferValue.RegDt=bankAccount.RegDt;
		bankTransferValue.St=bankAccount.St;
		bankTransferValue.accountType=bankAccount.accountType;
		bankTransferValue.flow = bankAccount.OpFlg;
		bankTransferValue.bankNum = bankAccount.bankNum;
		return bankTransferValue; 
	}
	//-------------------------------跨行定制  2013.03.21 end------------------------------------

	public Vector<CitysValue> getCitysValue(String filter){
		Vector<CitysValue> citysValue = null;
		try {
			citysValue = DAO.getCityOfBank(filter);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return citysValue;

	}





}
