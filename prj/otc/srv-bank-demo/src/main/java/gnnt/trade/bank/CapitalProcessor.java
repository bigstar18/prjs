package gnnt.trade.bank;
import gnnt.bank.adapter.rmi.BankAdapterRMI;
import gnnt.trade.bank.dao.BankDAO;
import gnnt.trade.bank.dao.BankDAOFactory;
import gnnt.trade.bank.data.CEBFileThread;
import gnnt.trade.bank.data.ExchangeData;
import gnnt.trade.bank.util.Encryption;
import gnnt.trade.bank.util.ErrorCode;
import gnnt.trade.bank.util.ProcConstants;
import gnnt.trade.bank.util.StartupRmiserver;
import gnnt.trade.bank.util.Tool;
import gnnt.trade.bank.vo.BankValue;
import gnnt.trade.bank.vo.CapitalCompare;
import gnnt.trade.bank.vo.CapitalValue;
import gnnt.trade.bank.vo.ChargeAgainstValue;
import gnnt.trade.bank.vo.CompareResult;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.DicValue;
import gnnt.trade.bank.vo.FirmBalanceValue;
import gnnt.trade.bank.vo.FirmBankMsg;
import gnnt.trade.bank.vo.FirmMessageVo;
import gnnt.trade.bank.vo.FirmValue;
import gnnt.trade.bank.vo.InMoneyMarket;
import gnnt.trade.bank.vo.InMoneyVO;
import gnnt.trade.bank.vo.InterfaceLog;
import gnnt.trade.bank.vo.MoneyInfoValue;
import gnnt.trade.bank.vo.OutMoneyMarket;
import gnnt.trade.bank.vo.OutMoneyVO;
import gnnt.trade.bank.vo.ReturnValue;
import gnnt.trade.bank.vo.TransferInfoValue;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;
/**
 * <p>Title: 银行接口处理器</p>
 * <p>Description:处理基本不变的核心业务</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: gnnt</p>
 */
public class CapitalProcessor {
	/********************常量定义***********************/
	/**与远期系统有关的数据处理对象*/
	protected static DataProcess dataProcess;
	/**是否已加载配置信息*/
	public static boolean ISLOADED = false;
	/**远程访问适配器对象url队列, key:银行代码,value:remoteUrl*/
	public static Hashtable<String,String> ADAPTERLIST;
	/**数据库访问对象*/
	public static BankDAO DAO;
	/**
	 * 构造函数
	 */
	public CapitalProcessor() {
		try {
			DAO = BankDAOFactory.getDAO();//取得数据库访问对象
			if(!ISLOADED) {
				ErrorCode errorCode=new ErrorCode();
				errorCode.load();//加载错误码对应的信息
				new CEBFileThread().start();
				dataProcess=new DataProcess();
				if(loadAdapter()==0) {//加载适配器对象
					ISLOADED = true;
				} else {
					log("初始化处理器失败：加载适配器对象错误");
				}
			}
		} catch(Exception e) {
			log("初始化处理器失败："+Tool.getExceptionTrace(e));
		}
	}
	/**
	 * 向指定日志文件写入日志信息
	 * @param content 日志内容
	 */
	public void log(String content) {
		Logger plog = Logger.getLogger(ProcConstants.cpLog);
		plog.debug(content);
	}
	/**
	 * 加载适配器
	 * @return int 0:加载成功；-1：加载失败
	 */
	private int loadAdapter() {
		int result = 0;
		try {
			Vector<BankValue> bankList = DAO.getBankList("");
			log("开始加载适配器远程访问对象url");
			for (int i = 0; i < bankList.size(); i++) {
				BankValue bVal = (BankValue)bankList.get(i);
				String abClassName = bVal.adapterClassname;
				addAdapter(bVal.bankID, abClassName);
				log(bVal.bankName + "[" + bVal.bankID + "]["+abClassName+"]适配器远程访问对象url加载成功");
			}
			log("结束加载适配器远程访问对象url");
		} catch(Exception e) {
			result = -1;
			log("加载适配器远程访问对象url异常："+Tool.getExceptionTrace(e));
		} finally {
		}
		return result;
	}
	/**
	 * 将适配器放入内存队列
	 * @param bankID 银行代码
	 * @param adapter 适配器对象
	 */
	private void addAdapter(String bankID ,String adapterClassname) {
		if(ADAPTERLIST == null) ADAPTERLIST = new Hashtable<String,String>();		
		ADAPTERLIST.put(bankID, adapterClassname);
	}
	/**
	 * 取得适配器对象
	 * @param bankID 银行代码
	 * @param adapter 适配器对象
	 * @return BankAdapter
	 */
	public BankAdapterRMI getAdapter(String bankID) {
		log("取得适配器对象 bankID["+bankID+"]");
		BankAdapterRMI bankadapter = null;
		try {
			String remoteUrl = ADAPTERLIST.get(bankID);
			log("bankID["+bankID+"]地址["+remoteUrl+"]");
			bankadapter = (BankAdapterRMI) Naming.lookup(remoteUrl);
		} catch (MalformedURLException e) {
			this.log("MalformedURLException:"+Tool.getExceptionTrace(e));
		} catch (RemoteException e) {
			this.log("RemoteException:"+Tool.getExceptionTrace(e));
		} catch (NotBoundException e) {
			this.log("NotBoundException:"+Tool.getExceptionTrace(e));
		} catch(Exception e){
			this.log("Exception"+Tool.getExceptionTrace(e));
		}
		this.log("返回银行适配器对象信息："+bankadapter);
		return bankadapter;
	}
	/**
	 * 取得付款方信息
	 * @param bankID 银行代码
	 * @param firmID 交易账号代码
	 * @param account 交易账号银行账号
	 * @param type 付款类型 0：入金 1：出金
	 * @param conn 数据库连接
	 * @return TransferInfoValue  付款方信息
	 */
	private TransferInfoValue getPayInfo(String bankID, String firmID ,int type, Connection conn) throws SQLException{
		this.log("取得付款方信息bankID["+bankID+"]firmID["+firmID+"]type["+type+"]时间："+Tool.getNowStr());
		TransferInfoValue result = new TransferInfoValue();
		String bankAccount="";//银行账号
		try  {
			Vector<CorrespondValue> cList = DAO.getCorrespondList(" and bankid='"+bankID+"' and firmid='"+firmID+"' and isOpen="+ProcConstants.firmTypeOpen, conn);
			if(cList.size() > 0) {
				bankAccount = ((CorrespondValue)cList.get(0)).account;
			}
			BankValue bv = DAO.getBank(bankID,conn);
			result.headOffice=bv.bankName;
			if(type == ProcConstants.inMoneyType) {
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
			} else if(type == ProcConstants.outMoneyType) {
				//取得付款方信息
				Vector<DicValue> list = DAO.getDicList(" and type=2 and bankid='"+bankID+"'", conn);
				for (int i = 0; i < list.size(); i++)  {
					DicValue val = (DicValue)list.get(i);
					if(val.name.equals("Email")) {
						result.email = val.value;
					} else if(val.name.equals("mobile")) {
						result.mobile = val.value;
					} else if(val.name.equals("bankCity")) {
						result.city = val.value;
					} else if(val.name.equals("bankProvince")) {
						result.province = val.value;
					} else if(val.name.equals("bankName")) {
						result.bankName = val.value;
					} else if(val.name.equals("accountName")) {
						result.name = val.value;
					} else if(val.name.equals("marketAccount")) {
						result.account = val.value;
					}
				}
			}
		} catch(SQLException e) {
			throw e;
		} finally {
		}
		return result;
	}
	/**
	 * 取得收款方信息
	 * @param bankID 银行代码
	 * @param firmID 交易账号代码
	 * @param account 交易账号账号
	 * @param type 付款类型 0：入金 1：出金
	 * @param conn 数据库连接
	 * @return TransferInfoValue  收款方信息
	 */
	private TransferInfoValue getReceiveInfo(String bankID, String firmID, int type, Connection conn) throws SQLException {
		log("取得收款方信息 getReceiveInfo bankID["+bankID+"]firmID["+firmID+"]type["+type+"]");
		TransferInfoValue result = new TransferInfoValue();
		String bankAccount="";//银行账号
		try {
			Vector<CorrespondValue> cList = DAO.getCorrespondList(" and bankid='"+bankID+"' and firmid='"+firmID+"' and isOpen="+ProcConstants.firmTypeOpen, conn);
			if(cList.size() > 0) {
				bankAccount = ((CorrespondValue)cList.get(0)).account;
			}
			BankValue bv = DAO.getBank(bankID,conn);
			result.headOffice=bv.bankName;
			if(type == ProcConstants.inMoneyType) {	
				Vector<DicValue> list = DAO.getDicList(" and type=2 and bankid='"+bankID+"'", conn);//取得收款方信息
				for (int i = 0; i < list.size(); i++) {
					DicValue val = (DicValue)list.get(i);
					if(val.name.equals("Email")) {
						result.email = val.value;
					} else if(val.name.equals("mobile")) {
						result.mobile = val.value;
					} else if(val.name.equals("bankCity")) {
						result.city = val.value;
					} else if(val.name.equals("bankProvince")) {
						result.province = val.value;
					} else if(val.name.equals("bankName")) {
						result.bankName = val.value;
					} else if(val.name.equals("accountName")) {
						result.name = val.value;
					} else if(val.name.equals("marketAccount")) {
						result.account = val.value;
					}
				}	
			} else if(type == ProcConstants.outMoneyType) {
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
		} catch(SQLException e) {
			throw e;
		} finally {
		}
		return result;
	}
	/**
	 * 银行开户销户变更账户检验参数方法
	 * @return int 0成功 <>0失败
	 * @param CorrespondValue
	 */
	private long chenckCorrespondValue(CorrespondValue cv) {
		this.log("银行开户销户变更账户检验参数，" + (cv == null ? "传入参数为空，" : "\n" + cv.toString() + "\n") + "时间：" + Tool.getNowStr());
		long checkResult = ErrorCode.Account_Error;
		String bankid = cv.bankID;
		String contact = cv.firmID;
		String account = cv.account;
		if(cv.contact != null && cv.contact.trim().length()>0){
			contact = cv.contact;
		}
		if(bankid==null || bankid.trim().length()<=0) {
			checkResult = ErrorCode.BankID_Error;
			return checkResult;
		}
		if(contact==null || contact.trim().length()<=0) {
			checkResult = ErrorCode.FirmID_Error;
			return checkResult;
		}
		if(account==null || account.trim().length()<=0) {
			checkResult = ErrorCode.Account_Error;
			return checkResult;
		}
		try {
			List<CorrespondValue> cvresult = DAO.getCorrespondList(" and bankId='"+bankid+"' and CONTACT='"+contact+"' and (account like '%"+account.trim()+"%' or account='"+Tool.getConfig("DefaultAccount")+"')");
			if(cvresult == null || cvresult.size()<=0) {
				checkResult = ErrorCode.NotFindFirmIDAndAccount;
				return checkResult;
			}
			if(cvresult.size()>1){
				checkResult = ErrorCode.FindFirmIDAndAccountMore;
				return checkResult;
			}
			checkResult = ErrorCode.handle_success;
		} catch (SQLException e) {
			checkResult = ErrorCode.DBError;
			log("交易账号代码与银行账号对应SQLException,"+Tool.getExceptionTrace(e));
		} catch (Exception e) {
			checkResult = ErrorCode.SystemError;
			log("交易账号代码与银行账号对应Exception,"+Tool.getExceptionTrace(e));
		}
		return checkResult;
	}
	/**
	 * 根据交易账号代码和银行账号等，查询交易账号绑定信息
	 * @param bankID 银行代码
	 * @param firmID 交易账号代码
	 * @param contact 交易账号和银行绑定号
	 * @param account 银行账号
	 */
	private CorrespondValue getCorrespond(String bankID,String firmID,String contact,String account)throws SQLException,ClassNotFoundException{
		log("根据交易账号代码和银行账号等，查询交易账号绑定信息 getCorrespond bankID["+bankID+"]firmID["+contact+"]account["+account+"]");
		CorrespondValue result = null;
		try{
			String filter = " and bankID='"+bankID.trim()+"' and CONTACT='"+contact.trim()+"' ";
			if(firmID != null && firmID.trim().length()>0){
				filter += " and firmID='"+firmID.trim()+"'";
			}
			if(account != null && account.trim().length()>0){
				filter += " and account='"+account.trim()+"'";
			}
			Vector<CorrespondValue> cvs = DAO.getCorrespondList(filter);
			if(cvs == null || cvs.size()<=0){
				log("根据传入信息filter["+filter+"]未查到信息");
			}else if(cvs.size() != 1){
				log("根据传入信息filter["+filter+"]查到信息不为一");
			}else{
				result = cvs.get(0);
			}
		}catch(SQLException e){
			throw e;
		}catch(ClassNotFoundException e){
			throw e;
		}
		return result;
	}
	/**
	 * 判断交易账号当前资金是否够出金
	 * @return long 0:可出  <0:不可出
	 * @throws SQLException
	 */
	private ReturnValue checkArgs(Connection conn,double money,String firmID,String bankID) throws SQLException {
		log("判断交易账号当前资金是否够出金 checkArgs money["+money+"]firmID["+firmID+"]");
		ReturnValue result = new ReturnValue();
		result.result = ErrorCode.outMoney_ErrorLackFunds;
		double realFunds=dataProcess.getRealFunds(bankID,firmID,1, conn);
		result.remark = "可出金额不足，可用余额["+realFunds+"]出金金额["+money+"]";
		log("交易账号["+firmID+"]可用余额["+realFunds+"]出金金额["+money+"]");
		if(realFunds>=money){
			result.result=ErrorCode.handle_success;
			result.remark = "";
		}
		return result;
	}
	/**
	 * 银行接口资金冻结
	 * @param firmID 交易账号代码
	 * @param contact 交易账号和银行绑定号
	 * @param bankID 银行编号
	 * @param account 银行账号
	 * @param money 金额
	 * @param conn 数据库连接
	 * @return int
	 */
	private long bankFrozenFuns(String firmID,String contact,String bankID,String account,double money,Connection conn) throws SQLException{
		log("银行接口资金冻结 bankFrozenFuns firmID["+firmID+"]bankID["+bankID+"]account["+account+"]");
		long result = ErrorCode.Account_Error;
		if(firmID==null || firmID.trim().length()<=0 || bankID==null || bankID.trim().length()<=0){
			log("冻结(解冻)资金，信息不完整 firmID="+firmID+" bankID="+bankID);
			result = ErrorCode.outMoneyB_ErrorParameter;
			return result;
		}
		String filter=" and firmID='"+firmID.trim()+"' and bankID='"+bankID.trim()+"' ";
		if(account!=null && account.trim().length()>0){
			filter = filter+" and account='"+account.trim()+"'";
		}else if(contact != null && contact.trim().length()>0){
			filter = filter+" and contact='"+contact.trim()+"'";
		}
		String filter2 = filter+" for update";
		try{
			Vector<CorrespondValue> vector = DAO.getCorrespondList(filter2, conn);
			if(vector!=null && vector.size()<=0){
				log("冻结(解冻)资金，根据["+filter2+"]未查到客户绑定信息");
				result = ErrorCode.NotFindFirmIDAndAccount;
				return result;
			}
			CorrespondValue cv = vector.get(0);
			if(cv.frozenFuns+money<0){
				result = ErrorCode.outMoneyB_SysException;
				log("交易账号冻结资金不足以释放当前资金：冻结资金["+cv.frozenFuns+"]当前资金["+money+"]");
				return result;
			}
			result = DAO.modBankFrozenFuns(filter, money, conn);
		}catch(SQLException e){
			throw e;
		}
		return result;
	}
	/**
	 * 检查交易账号是否符合出金条件,符合条件进行出金处理,记录流水
	 * @param conn 数据库连接
	 * @param bankID 银行编号
	 * @param trader 操作员
	 * @param money 金额
	 * @param firmID 交易账号代码
	 * @param contact 交易账号和银行绑定号
	 * @param account 银行账号
	 * @param funID 银行流水号
	 * @param type 入金发起方 (0 市场,其他 银行)
	 * @return HashMap<Integer,Long> (1 业务流水号，2 表编号)
	 * @throws SQLException
	 */
	private long handleOUtMoney(Connection conn,String bankID,String trader,double money,String firmID,String contact,String account,String funID ,int type) throws SQLException {
		log("检查交易账号是否符合出金条件,符合条件进行出金处理,记录流水 handleOUtMoney bankID["+bankID+"]trader["+trader+"]money["+money+"]firmID["+firmID+"]contact["+contact+"]account["+account+"]funID["+funID+"]type["+type+"]");
		long result = ErrorCode.outMoneyAudit_OradyAudite;
		try  {
			conn.setAutoCommit(false);//启动数据库事务
			if(funID != null && funID.trim().length()>0){
				//Vector<CapitalValue> cv = DAO.getCapitalInfoList(" and bankID='"+bankID+"' and trunc(createtime)=trunc(sysdate) and funID='"+funID+"'",conn);
				Vector<CapitalValue> cv = DAO.getCapitalInfoList(" and bankID='"+bankID+"' and createtime>=trunc(sysdate) and createtime<trunc(sysdate)+1 and funID='"+funID+"'",conn);
				if(cv != null && cv.size()>0){
					result = ErrorCode.outMoneyAudit_OradyAudite;
					log("银行出金，银行流水号["+funID+"]已经存在");
					return result;
				}
			}
			result = checkArgs(conn,money,firmID,bankID).result;
			if(result<0){
				return result;
			}
			result = DAO.getActionID();//取得市场业务流水号
			CapitalValue cVal = new CapitalValue();
			cVal.trader = trader;
			cVal.funID = (funID==null?"":funID);
			cVal.actionID = result;
			cVal.firmID = firmID;
			cVal.contact = contact;
			cVal.bankID = bankID;
			cVal.type = ProcConstants.outMoneyType;
			cVal.money = money;
			cVal.status = ProcConstants.statusMarketProcessing;
			if(type == ProcConstants.marketLaunch) {
				cVal.launcher = ProcConstants.marketLaunch;
				cVal.note = "市场端出金";
			} else {
				cVal.launcher = ProcConstants.bankLaunch;
				cVal.note = "银行端出金";
			}
			DAO.addCapitalInfo(cVal, conn);//出金流水
			dataProcess.updateFrozenFunds(firmID, money, conn);//调用交易系统接口
			this.bankFrozenFuns(firmID, contact, bankID, account, money, conn);//银行接口表冻结资金
			conn.commit();
		}  catch(SQLException e) {
			conn.rollback();
			throw e;
		} catch(Exception e){
			conn.rollback();
			result = ErrorCode.outMoney_ErrorAddCapital;
			log("出金Exception,数据回滚"+Tool.getExceptionTrace(e));
		}finally {
			conn.setAutoCommit(true);
		}
		return result;
	}
	/**
	 * 出金自动审核
	 * @param actionID  转账模块业务流水号
	 * @return long 0 成功 <0失败 
	 */
	private ReturnValue outMoneyAutoAudit(long actionID) {
		log("出金自动审核 outMoneyAutoAudit actionID["+actionID+"]");
		ReturnValue result = new ReturnValue();//返回信息
		Connection conn = null;//数据库连接
		Timestamp curTime = new Timestamp(System.currentTimeMillis());//当前系统时间
		CapitalValue capital =null;//资金划转流水对象
		try  {
			conn = DAO.getConnection();
			Vector<CapitalValue> list = DAO.getCapitalInfoList(" and actionid="+actionID, conn);//获取资金流水对象（包括出金和出金手续费流水）
			for (int i = 0; i < list.size(); i++) {
				CapitalValue val = (CapitalValue)list.get(i);
				if(val.type == ProcConstants.outMoneyType) {
					capital = val;//出金流水
				}
			}
			if(capital==null) {
				result.result =ErrorCode.outMoneyAudit_ErrorActionID;
				result.remark = "没有发现业务流水号为："+actionID+"的流水";
				log(result.remark);
				return result;
			}
			try {
				conn.setAutoCommit(false);
				list = DAO.getCapitalInfoList(" and actionid="+actionID+" and STATUS="+ProcConstants.statusMarketProcessing+" for update", conn);
				if(list == null || list.size()<=0){
					result.result =ErrorCode.outMoneyAudit_ErrorActionID;
					result.remark = "没有发现业务流水号为："+actionID+"状态为处理中的流水";
					log(result.remark);
					return result;
				}
				conn.commit();
			}catch(SQLException e){
				conn.rollback();
				throw e;
			}finally{
				conn.setAutoCommit(true);
			}
			if(result.result<0){
				return result;
			}
			try {
				if(capital.launcher == ProcConstants.marketLaunch) {//如果是市场端发起出金
					BankAdapterRMI bankadapter = getAdapter(capital.bankID);//取得适配器对象
					if(bankadapter == null){
						result.result = ErrorCode.GetAdapter_Error;
						result.remark = "网络异常，处理器无法连接适配器["+capital.bankID+"]";
						log(result.remark);
					}else{
						TransferInfoValue payInfo = getPayInfo(capital.bankID, capital.firmID,ProcConstants.outMoneyType, conn);//取得出金付款信息
						TransferInfoValue receiveInfo=getReceiveInfo(capital.bankID, capital.firmID,ProcConstants.outMoneyType, conn);//取得出金收款方信息
//						BankValue bv = DAO.getBank(capital.bankID);
						OutMoneyVO outMoneyInfo=new OutMoneyVO(capital.bankID,"",capital.money,capital.contact,payInfo,receiveInfo,actionID,capital.funID);
						result = bankadapter.outMoneyMarketDone(outMoneyInfo);//调用适配器市场端出金接口
					}
				} else {//如果为银行端出金
					result.actionId=capital.actionID;
					result.funID=capital.funID;
				}
				conn.setAutoCommit(false);//启动数据库事务
				if(result.result == 0) {
					dataProcess.updateFundsFull(capital.bankID,capital.firmID, capital.money, ProcConstants.outMoneyType, conn);
					dataProcess.updateFrozenFunds(capital.firmID, -1*capital.money, conn);//资金解冻
					this.bankFrozenFuns(capital.firmID,capital.contact, capital.bankID, null, -1*capital.money, conn);//修改交易账号银行接口冻结资金
					DAO.modCapitalInfoStatus(capital.iD,result.funID, ProcConstants.statusSuccess,curTime, conn);
					DAO.modCapitalInfoNote(capital.iD, "出金成功", conn);
					log(actionID+"出金成功,银行流水号="+result.funID);
					result.type = ProcConstants.statusSuccess;
					result.remark = "出金成功";
				} else if(result.result == 5) {//状态修改为rVal.result：发送给银行 处理过程中
					DAO.modCapitalInfoStatus(capital.iD,result.funID, ProcConstants.statusBankProcessing,curTime, conn);
					DAO.modCapitalInfoNote(capital.iD, result.remark, conn);
					log(actionID+"银行处理处理中");
					result.type = ProcConstants.statusBankProcessing;
				} else if(result.result == ErrorCode.bankNull) {//状态修改为rVal.result：发送给银行 处理过程中
					DAO.modCapitalInfoStatus(capital.iD,result.funID, ProcConstants.statusBankNull,curTime, conn);
					DAO.modCapitalInfoNote(capital.iD, result.remark, conn);
					log(actionID+"银行无返回");
					result.type = ProcConstants.statusBankNull;
				} else if(result.result == ErrorCode.ActionID_Error) {//状态修改为rVal.result：发送给银行 处理过程中
					DAO.modCapitalInfoStatus(capital.iD,result.funID, ProcConstants.statusBankError,curTime, conn);
					DAO.modCapitalInfoNote(capital.iD, result.remark, conn);
					log(actionID+"银行返回信息异常");
					result.type = ProcConstants.statusBankError;
				} else {
					dataProcess.updateFrozenFunds(capital.firmID, -1*capital.money, conn);//资金解冻
					this.bankFrozenFuns(capital.firmID,capital.contact, capital.bankID, null, -1*capital.money, conn);//修改交易账号银行接口冻结资金
					DAO.modCapitalInfoStatus(capital.iD,result.funID, ProcConstants.statusFailure,curTime, conn);
					DAO.modCapitalInfoNote(capital.iD,result.remark, conn);
					log(actionID+"银行处理失败,退还全部出金和手续费，错误码="+result);
					result.type = ProcConstants.statusFailure;
				}
				conn.commit();
			} catch(SQLException e) {
				e.printStackTrace();
				conn.rollback();
				result.result = ErrorCode.outMoneyAudit_PassDataBaseException;
				result.remark = "处理出金流水["+actionID+"]数据库异常";
				log(actionID+result.remark+Tool.getExceptionTrace(e));
			} finally {
				conn.setAutoCommit(true);
			}
			if(result.result == ErrorCode.outMoneyAudit_PassDataBaseException) {//流水状态修改为1：审核过程中出现异常
				DAO.modCapitalInfoStatus(capital.iD,capital.funID, ProcConstants.statusMarketError,curTime, conn);
				DAO.modCapitalInfoNote(capital.iD, "数据库异常", conn);
				result.type = ProcConstants.statusMarketError;
			}
			result.actionId = capital.actionID;
			result.funID = capital.funID;
		} catch(SQLException e) {
			result.result = ErrorCode.checkTrans_DataBaseException;
			log(actionID+"审核出金SQLException，错误码="+result+"  "+Tool.getExceptionTrace(e));
		} catch(Exception e) {
			result.result = ErrorCode.checkTrans_SysException;
			log(actionID+"审核出金Exception，错误码="+result+"  "+Tool.getExceptionTrace(e));
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}
	/**
	 * 取得市场业务流水号
	 * @return long 市场业务流水号，返回-1表示操作失败
	 */
	public long getMktActionID() {
		long result = -168;
		try{
			result = DAO.getActionID();
		}catch(SQLException e){
			result = ErrorCode.DBError;
		}catch(Exception e){
			result = ErrorCode.SystemError;
		}
		return result;
	}
	/**
	 * 查询银行列表
	 * @param filter 查询条件
	 * @return Vector<BankValue>
	 */
	public Vector<BankValue> getBankList(String filter){
		log("查询银行列表 filter["+filter+"]");
		Vector<BankValue> result = null;
		try {
			result = DAO.getBankList(filter);
		} catch (SQLException e) {
			log("SQLException "+Tool.getExceptionTrace(e));
		} catch (Exception e) {
			log("Exception "+Tool.getExceptionTrace(e));
		}
		log("查询银行列表，返回："+(result == null ? "null" : result.size()+"条"));
		return result;
	}
	/**
	 * 取得银行信息表
	 * @param bankID 银行代码
	 * @return BankValue
	 */
	public BankValue getBank(String bankID){
		BankValue value = null;
		try {
			value = DAO.getBank(bankID);
		} catch (SQLException e) {
			log(Tool.getExceptionTrace(e));
		} catch (Exception e) {
			log(Tool.getExceptionTrace(e));
		}
		return value;
	}
	/**
	 * 修改银行信息
	 * @param bankValue 银行信息表
	 * @return ReturnValue
	 */
	public ReturnValue modBank(BankValue bankValue){
		ReturnValue result = new ReturnValue();
		if(bankValue == null){
			result.result = -1;
			result.remark = "出入银行信息类为 null";
		}else if(bankValue.bankID==null || bankValue.bankID.trim().length()<=0){
			result.result = -1;
			result.remark = "传入的银行信息中不包含银行代码";
		}else{
			try{
				result.result = DAO.modBank(bankValue);
				result.remark = "修改了["+bankValue.bankID+"]银行数据";
			}catch(SQLException e){
				log(Tool.getExceptionTrace(e));
				result.result = ErrorCode.DBError;
				result.remark = "数据库异常";
			}catch(Exception e){
				log(Tool.getExceptionTrace(e));
				result.result = ErrorCode.SystemError;
				result.remark = "系统异常";
			}
		}
		return result;
	}
	/**
	 * 银行交易禁用/恢复
	 * @param bankID 银行编号
	 * @param type 修改类型 (0 修改银行可用状态,1 修改入金可用状态,2 修改出金可用状态)
	 * @param value 操作状态 (0 可用,1 禁用)
	 * @return ReturnValue
	 */
	public ReturnValue changeBankTradeType(Vector<String> bankIDs,int type,int value){
		ReturnValue result = new ReturnValue();
		try{
			String str = (value==0 ? "可用" : "禁用");
			if(type == 0){//修改银行状态
				result.result = DAO.chBankValid(bankIDs, value);
				result.remark = "修改银行状态为["+str+"]成功";
			}else if(type == 1){
				result.result = DAO.chBankInMoney(bankIDs, value);
				result.remark = "修改银行入金状态为["+str+"]成功";
			}else if(type == 2){
				result.result = DAO.chBankOutMoney(bankIDs, value);
				result.remark = "修改银行出金状态为["+str+"]成功";
			}else{
				result.result = ErrorCode.SystemError;
				result.remark = "系统无法找到需要修改的信息";
			}
		}catch(SQLException e){
			result.result = ErrorCode.DBError;
			result.remark = "修改银行状态，数据库异常";
			log(Tool.getExceptionTrace(e));
		}catch(Exception e){
			result.result = ErrorCode.SystemError;
			result.remark = "修改银行状态，系统异常";
			log(Tool.getExceptionTrace(e));
		}
		return result;
	}
	/**
	 * 根据银行ID获取市场银行信息
	 * @param bankID 银行代码
	 * @return Hashtable<name,value>
	 */
	public Hashtable<String,String> getBankInfo(String bankID) {
		log("根据银行ID获取市场银行信息 getBankInfo bankID["+bankID+"]");
		Hashtable<String,String> hashTable=new Hashtable<String, String>();
		Connection conn = null;//数据库连接
		try {
			conn = DAO.getConnection();
			Vector<DicValue> list = DAO.getDicList(" and type=2 and bankid='"+bankID+"'",conn);
			for (int i = 0; i < list.size(); i++) {
				DicValue val = (DicValue)list.get(i);
				if(val.value == null){
					val.value = "";
				}
				hashTable.put(val.name, val.value);
			}
		} catch(Exception e) {
			log(Tool.getExceptionTrace(e));
		}finally{
			DAO.closeStatement(null, null, conn);
		}
		return hashTable;
	}
	/**
	 * 获取交易账号银行对应关系
	 * @param contacts 交易账号和银行绑定号
	 * @param bankID 银行编号
	 * @return 
	 */
	public Map<String,CorrespondValue> getCorrespondValue(Vector<String> contacts,String bankID) {
		Map<String,CorrespondValue> result = new HashMap<String,CorrespondValue>();
		Connection conn = null;//数据连接
		try {
			conn = DAO.getConnection();
			if(contacts != null && contacts.size()>0){
				for(String contact : contacts){
					Vector<CorrespondValue> correspondList=DAO.getCorrespondList(" and contact='"+contact+"' and bankid='"+bankID+"' ", conn);
					if(correspondList != null && correspondList.size()>0){
						CorrespondValue cv = correspondList.get(0);
						result.put(contact, cv);
					}
				}
			}
		} catch(Exception e) {
			log(Tool.getExceptionTrace(e));
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return  result;
	}
	/**
	 * 获取交易账号银行对应关系
	 * @param filter 查询条件
	 * @return 
	 */
	public Vector<CorrespondValue> getCorrespondValue(String filter) {
		log("获取交易账号银行对应关 getCorrespondValue filter["+filter+"]");
		Connection conn = null;//数据连接
		Vector<CorrespondValue> correspondList=null;
		try {
			conn = DAO.getConnection();
			correspondList=DAO.getCorrespondList(filter, conn);
		} catch(Exception e) {
			log(Tool.getExceptionTrace(e));
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return  correspondList;
	}
	/**
	 * 查询交易账号列表
	 * @param filter 查询条件
	 * @return Vector<FirmValue>
	 */
	public Vector<FirmValue> getFirmUser(String filter){
		log("查询交易账号列表 filter["+filter+"]");
		Vector<FirmValue> result = null;
		try{
			result = DAO.getFirmList(filter);
		}catch(SQLException e){
			log("SQLException "+Tool.getExceptionTrace(e));
		}catch(Exception e){
			log("Exception "+Tool.getExceptionTrace(e));
		}
		return result;
	}
	/**
	 * 判断密码合法性
	 * @param firmID 交易账号代码
	 * @param password 交易账号密码
	 * @return long (0 成功;1 未设置密码;-1 验证失败;-2 查询不到交易账号)
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
						log("交易账号["+firmID+"]输入的密码错误");
					}
				}else{
					result = 1;
					log("交易账号["+firmID+"]尚未初始化密码");
				}
			}else{
				result = -2;
				log(Tool.fmtTime(new java.util.Date())+"查询不到交易账号["+firmID+"]");
			}
		}catch(SQLException e){
			log(Tool.getExceptionTrace(e));
		}catch(Exception e){
			log(Tool.getExceptionTrace(e));
		}
		return result;
	}
	/**
	 * 修改交易账号密码
	 * @param firmID 交易账号代码
	 * @param password 交易账号原密码
	 * @param chpassword 交易账号将要更改密码
	 * @return long(0 成功;-1 原密码错误;-2 未找到交易账号)
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
					int n=DAO.modFirmPassword(fv);
					if(n>=0){
						result = 0;
					}else{
						log(Tool.fmtTime(new java.util.Date())+"修改交易账号密码，返回数值小于0");
					}
				}else{
					result = -1;
					log(Tool.fmtTime(new java.util.Date())+"交易账号["+fv.firmID+"]修改密码，原密码错误。oldpassword["+oldpassword+"]nowpassword["+newpassword+"]");
				}
			}else{
				result = -2;
				log(Tool.fmtTime(new java.util.Date())+"未查询到相应交易账号["+firmID+"]");
			}
		}catch(SQLException e){
			log(Tool.fmtTime(new java.util.Date())+"修改密码数据库异常，输入的密码："+newpassword);
			log(Tool.getExceptionTrace(e));
		}catch(Exception e){
			log(Tool.fmtTime(new java.util.Date())+"修改密码系统异常，输入的密码："+newpassword);
			log(Tool.getExceptionTrace(e));
		}
		return result;
	}
	/**
	 * 初始化交易账号密码
	 * @param firmID 交易账号代码
	 * @param password 交易账号密码
	 * @return ReturnValue
	 */
	public ReturnValue initializeFrimPwd(String firmID,String password){
		ReturnValue result = new ReturnValue();
		try{
			String newpassword = Encryption.encryption(firmID,password,null);
			FirmValue fv = DAO.getFirm(firmID);
			if(fv == null){
				result.result = ErrorCode.FirmID_Error;
				result.remark = "修改交易账号"+firmID+"密码，通过交易账号编号找不到交易账号信息";
			}else{
				fv.password = newpassword;
				int n=DAO.modFirmPassword(fv);
				if(n>=0){
					result.result = 0;
					result.remark = "修改交易账号"+firmID+"密码成功";
				}else{
					result.result = ErrorCode.SystemError;
					result.remark = "修改交易账号"+firmID+"密码失败";
				}
			}
		}catch(SQLException e){
			result.result = ErrorCode.DBError;
			result.remark = "修改交易账号"+firmID+"密码，数据库异常";
			log(result.remark+Tool.getExceptionTrace(e));
		}catch(Exception e){
			result.result = ErrorCode.SystemError;
			result.remark = "修改交易账号0"+firmID+"密码，系统异常";
			log(result.remark+Tool.getExceptionTrace(e));
		}
		return result;
	}
	/**
	 * 初始化交易账号密码
	 * @param firmID 交易账号代码
	 * @return ReturnValue
	 */
	public ReturnValue initializeFrimPwd(String firmID){
		ReturnValue result = new ReturnValue();
		try{
			FirmValue fv = DAO.getFirm(firmID);
			if(fv == null){
				result.result = ErrorCode.FirmID_Error;
				result.remark = "修改交易账号"+firmID+"密码，通过交易账号编号找不到交易账号信息";
			}else{
				fv.password = "";
				int n=DAO.modFirmPassword(fv);
				if(n>=0){
					result.result = 0;
					result.remark = "修改交易账号"+firmID+"密码成功";
				}else{
					result.result = ErrorCode.SystemError;
					result.remark = "修改交易账号"+firmID+"密码失败";
				}
			}
		}catch(SQLException e){
			result.result = ErrorCode.DBError;
			result.remark = "修改交易账号"+firmID+"密码，数据库异常";
			log(result.remark+Tool.getExceptionTrace(e));
		}catch(Exception e){
			result.result = ErrorCode.SystemError;
			result.remark = "修改交易账号0"+firmID+"密码，系统异常";
			log(result.remark+Tool.getExceptionTrace(e));
		}
		return result;
	}
	/**
	 * 禁用、恢复交易账号绑定表状态
	 * @param cav 交易账号绑定信息
	 * @return ReturnValue
	 */
	public ReturnValue modCorrespondStatus(CorrespondValue cav){
		ReturnValue result = new ReturnValue();
		if(cav == null){
			result.result = -1;
			result.remark = "信息为空";
		}else if(cav.bankID==null){
			result.result = -1;
			result.remark = "银行编号为空";
		}else{
			if(cav.firmID==null && cav.contact==null){
				result.result = -1;
				result.remark = "交易账号代码和绑定号至少选其一";
			}else{
				try{
					result.result = DAO.modCorrespondStatus(cav);
					result.remark = "修改状态为["+result.result+"]";
				}catch(SQLException e){
					result.result = -1;
					result.remark = "数据库异常";
					log(Tool.getExceptionTrace(e));
				}catch(Exception e){
					result.result = -1;
					result.remark = "系统异常";
					log(Tool.getExceptionTrace(e));
				}
			}
		}
		return result;
	}
	/**
	 * 银行账号注册
	 * @param correspondValue 交易账号和银行对应关系
	 * @return  long 操作结果：0 成功  非0 失败(-40001;:信息不完整 -40002：交易账号不存在 -40003：银行不存在  -40004：账号已注册 -40005:银行签约失败 -40006：数据库操作失败 -40007：系统异常 -40008:交易账号密码错误
	 */
	public long rgstAccount(CorrespondValue correspondValue) {
		log("银行账号注册 rgstAccount correspondValue:" + (correspondValue == null ? "为 null" : "\n"+correspondValue.toString()+"\n"));
		long result = ifbankTrade(correspondValue.bankID,correspondValue.firmID,null,3,ProcConstants.marketLaunch).result;
		if(result < 0){
			return result;
		}
		String defaultAccount=Tool.getConfig(ProcConstants.defaultAccount);//特殊定制的银行账号
		Connection conn = null;
		if(correspondValue.bankID==null || correspondValue.bankID.length()==0 ||
				correspondValue.firmID==null || correspondValue.firmID.length()==0 ||
				correspondValue.account==null || correspondValue.account.length()==0
		){
			return ErrorCode.rgstAccount_InfoHalfbaked;
		}
		try {
			conn = DAO.getConnection();
			if(DAO.getFirm(correspondValue.firmID, conn) == null) {//交易账号不存在
				result =ErrorCode.rgstAccount_NOFirm;
				log("银行账号注册，交易账号不存在，错误码="+result);
			} else if(DAO.getBank(correspondValue.bankID, conn) == null) {//银行不存在
				result = ErrorCode.rgstAccount_NOBank;
				log("银行账号注册，银行不存在，错误码="+result);
			} else if(DAO.getCorrespondList(" and bankID='"+correspondValue.bankID+"' and firmID='"+correspondValue.firmID+"' and (account='"+correspondValue.account+"' or account='"+defaultAccount+"') and isopen=1",conn).size()>0) {//账号已签约
				result = ErrorCode.rgstAccount_GRSAcount;
				log("银行账号注册，账号已注册，错误码="+result);
			} else if(DAO.getCorrespondList(" and bankID='"+correspondValue.bankID+"' and firmID='"+correspondValue.firmID+"' and (account='"+correspondValue.account+"' or account='"+defaultAccount+"')",conn).size()>0){
				BankAdapterRMI bankadapter = getAdapter(correspondValue.bankID);
				ReturnValue returnValue=bankadapter.rgstAccountQuery(correspondValue);
				if(returnValue==null || returnValue.result==0) {//满足注册条件，修改为签约状态
					correspondValue.isOpen = ProcConstants.firmTypeOpen;
					try{
						conn.setAutoCommit(false);
						dataProcess.changeFirmIsOpen(correspondValue.firmID,1,correspondValue.bankID,conn);
						DAO.openCorrespond(correspondValue,conn);
						conn.commit();
					}catch(SQLException e){
						conn.rollback();
						throw e;
					}finally{
						conn.setAutoCommit(true);
					}
					log("满足注册条件，修改为签约状态"+result);
				} else {
					log(returnValue.remark);//银行签约失败
					result = ErrorCode.rgstAccount_BankRgsFail;
					log("银行账号注册，银行签约失败，错误码="+result);
				}
			} else {//添加账户
				if(DAO.getCorrespondList(" and bankID='"+correspondValue.bankID+"' and firmID='"+correspondValue.firmID+"' and account='"+correspondValue.account+"'",conn).size()<=0){//20091021
					log("添加交易账号");
					DAO.addCorrespond(correspondValue, conn);
				}else{
					log("账号已存在");
				}
			}
		} catch(SQLException e) {
			e.printStackTrace();
			result = ErrorCode.rgstAccount_DatabaseException;
			log("银行账号注册SQLException，错误码="+result+"  "+Tool.getExceptionTrace(e));
		} catch(Exception e) {
			e.printStackTrace();
			result = ErrorCode.rgstAccount_SysException;
			log("银行账号注册Exception，错误码="+result+"  "+Tool.getExceptionTrace(e));
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}
	/**
	 * 银行开户方法
	 * 变更 交易账号代码与银行账号对应关系 的签约状态
	 * @param cv 交易账号信息
	 * @return ReturnValue
	 */
	public ReturnValue openAccount(CorrespondValue cv) {
		log("银行开户方法 openAccount cv:"+(cv == null ? "为 null" : "\n" + cv.toString() + "\n"));
		ReturnValue rv = ifbankTrade(cv.bankID,null,cv.firmID,3,ProcConstants.bankLaunch);
		if(rv.result < 0){
			return rv;
		}
		synchronized(cv.firmID){
			Connection conn = null;
			try {
				if(cv.contact == null || cv.contact.trim().length()<=0){
					cv.contact = cv.firmID;
				}
				List<CorrespondValue> cvresult = DAO.getCorrespondList(" and bankID='"+cv.bankID+"' and CONTACT='"+cv.contact+"'");
				if(cvresult == null || cvresult.size()<=0){
					cvresult = DAO.getCorrespondList(" and trim(contact)='"+cv.contact.trim()+"' and trim(bankID) is null ");
					if(cvresult != null && cvresult.size()>0){
						CorrespondValue corr = cvresult.get(0);
						corr.account = cv.account;
						corr.isOpen = ProcConstants.firmTypeOpen;
						corr.status = ProcConstants.firmStatusTrue;
						corr.accountName = cv.accountName;
						corr.card = cv.card;
						corr.cardType = cv.cardType;
						corr.account1 = cv.account1;
						corr.bankID = cv.bankID;
						rv.actionId=getMktActionID();
						conn = DAO.getConnection();
						try{
							conn.setAutoCommit(false);
							rv = dataProcess.changeFirmIsOpen(corr.firmID,1,corr.bankID,conn);
							if(rv.result<0){
								return rv;
							}
							DAO.openCorrespond(corr,conn);
							conn.commit();
						}catch(SQLException er){
							conn.rollback();
							throw er;
						}finally{
							conn.setAutoCommit(true);
						}
					}else{
						Vector<FirmValue> vfv = DAO.getFirmList(" and CONTACT='"+cv.contact+"'");
						if(vfv == null || vfv.size() != 1){
							rv.result = ErrorCode.rgstAccount_NOFirm;
							rv.remark = "通过签约号查询客户信息异常";
							return rv;
						}
						FirmValue fv = vfv.get(0);
						if(cv.card == null || !cv.card.equals(fv.card)){
							rv.result = ErrorCode.outMoney_CardNo;
							rv.remark = "客户证件号码错误";
							return rv;
						}
						cv.firmID = fv.firmID;
						cv.contact = fv.contact;
						cv.frozenFuns = 0;
						cv.isOpen = ProcConstants.firmTypeOpen;
						cv.status = ProcConstants.firmStatusTrue;;
						cv.opentime = new Date();
						rv.actionId=getMktActionID();
						conn = DAO.getConnection();
						try{
							conn.setAutoCommit(false);
							rv = dataProcess.changeFirmIsOpen(fv.firmID,1,cv.bankID,conn);
							if(rv.result<0){
								return rv;
							}
							DAO.addCorrespond(cv,conn);
							conn.commit();
						}catch(SQLException er){
							conn.rollback();
							throw er;
						}finally{
							conn.setAutoCommit(true);
						}
					}
				}else if(cvresult.size()!=1){
					rv.result = ErrorCode.FindFirmIDAndAccountMore;
					rv.remark = "通过银行编号、签约号查询出信息重复";
					return rv;
				}else{
					CorrespondValue cv2 = cvresult.get(0);
					if(cv.card==null || !cv.card.equals(cv2.card)){
						rv.result = ErrorCode.outMoney_CardNo;
						rv.remark = "客户证件号码错误";
						return rv;
					}
					rv.actionId=getMktActionID();
					conn = DAO.getConnection();
					try{
						conn.setAutoCommit(false);
						rv = dataProcess.changeFirmIsOpen(cv2.firmID,1,cv2.bankID,conn);
						if(rv.result<0){
							return rv;
						}
						cv.contact = cv2.contact;
						cv.id = cv2.id;
						DAO.openCorrespond(cv,conn);
						conn.commit();
					}catch(SQLException er){
						conn.rollback();
						throw er;
					}finally{
						conn.setAutoCommit(true);
					}
				}
			} catch (SQLException e) {
				rv.result=(int) ErrorCode.rgstAccount_DatabaseException;
				rv.remark = "签约时数据库异常";
				log("银行开户 交易账号代码与银行账号对应SQLException,"+Tool.getExceptionTrace(e));
			} catch (Exception e) {
				rv.result=(int) ErrorCode.rgstAccount_SysException;
				rv.remark = "签约时系统异常";
				log("银行开户 交易账号代码与银行账号对应SQLException,"+Tool.getExceptionTrace(e));
			} finally{
				DAO.closeStatement(null, null, conn);
			}
		}
		if(rv.result>0){
			rv.result = 0;
		}
		return rv;
	}
	/**
	 * 银行开户方法
	 * 变更 交易账号代码与银行账号对应关系 的签约状态
	 */
	public ReturnValue openAccountMarket(CorrespondValue cv) {
		log("市场开户方法 openAccountMarket cv:" + (cv == null ? "为 null" : "\n" + cv.toString() + "\n"));
		ReturnValue rv = ifbankTrade(cv.bankID,cv.firmID,cv.contact,3,ProcConstants.marketLaunch);
		if(rv.result < 0){
			return rv;
		}
		Connection conn = null;
		synchronized(cv.contact){
			try {
				List<CorrespondValue> cvresult = DAO.getCorrespondList(" and bankID='"+cv.bankID+"' and contact='"+cv.contact.trim()+"'");
				if(cvresult == null || cvresult.size()<=0) {
					cvresult = DAO.getCorrespondList(" and trim(contact)='"+cv.contact.trim()+"' and trim(bankID) is null ");
					if(cvresult != null && cvresult.size()>0){
						CorrespondValue corr = cvresult.get(0);
						corr.account = cv.account;
						corr.isOpen = ProcConstants.firmTypeOpen;
						corr.status = ProcConstants.firmStatusTrue;
						corr.accountName = cv.accountName;
						corr.bankCardPassword = cv.bankCardPassword;
						corr.card = cv.card;
						corr.cardType = cv.cardType;
						String firmID = cv.firmID;
						corr.firmID = cv.contact;
						corr.bankID = cv.bankID;
						corr.frozenFuns = cv.frozenFuns;
						//
						conn = DAO.getConnection();
						try{
							conn.setAutoCommit(false);
							BankAdapterRMI bankadapter = this.getAdapter(cv.bankID);
							if(bankadapter==null){
								rv.result = ErrorCode.GetAdapter_Error;
								rv.remark = "获取连接银行适配器失败";
								return rv;
							}
							
							rv = dataProcess.changeFirmIsOpen(firmID,1,corr.bankID,conn);
							if(rv.result<0){
								return rv;
							}
							
							rv = bankadapter.rgstAccountQuery(corr);
							if(rv.result<0){
								return rv;
							}
							corr.firmID = firmID;
							corr.account1 = rv.account1;
							rv.result = DAO.openCorrespond(corr,conn);
							conn.commit();
						}catch(Exception er){
							conn.rollback();
							throw er;
						}finally{
							conn.setAutoCommit(true);
						}
					}else{
						Vector<FirmValue> vfv = DAO.getFirmList(" and CONTACT='"+cv.contact+"'");
						if(vfv == null || vfv.size() != 1){
							rv.result = ErrorCode.rgstAccount_NOFirm;
							rv.remark = "通过签约号查询客户信息异常";
							return rv;
						}
//						FirmValue fv = vfv.get(0);
						cv.isOpen = ProcConstants.firmTypeOpen;
						cv.status = ProcConstants.firmStatusTrue;
						String firmID = cv.firmID;
						cv.firmID = cv.contact;
						conn = DAO.getConnection();
						try{
							conn.setAutoCommit(false);
							BankAdapterRMI bankadapter = this.getAdapter(cv.bankID);
							if(bankadapter==null){
								rv.result = ErrorCode.GetAdapter_Error;
								rv.remark = "获取连接银行适配器失败";
								return rv;
							}
							
							rv = dataProcess.changeFirmIsOpen(firmID,1,cv.bankID,conn);
							if(rv.result<0){
								return rv;
							}
							
							rv = bankadapter.rgstAccountQuery(cv);
							if(rv.result<0){
								return rv;
							}
							cv.firmID = firmID;
							cv.account1 = rv.account1;
							DAO.addCorrespond(cv,conn);
							conn.commit();
						}catch(SQLException er){
							conn.rollback();
							throw er;
						}finally{
							conn.setAutoCommit(true);
						}
					}
				}else if(cvresult.size() != 1){
					rv.result = ErrorCode.FindFirmIDAndAccountMore;
					rv.remark = "通过银行编号、签约号查询出信息重复";
					return rv;
				}else{
					CorrespondValue cv2 = cvresult.get(0);
					if(cv.firmID == null || !cv.firmID.equals(cv2.firmID)){
						rv.result = ErrorCode.rgstAccount_NOFirm;
						rv.remark = "传入客户编号错误";
						return rv;
					}
					cv2.account = cv.account;
					cv2.isOpen = ProcConstants.firmTypeOpen;
					cv2.status = ProcConstants.firmStatusTrue;
					cv2.accountName = cv.accountName;
					cv2.bankCardPassword = cv.bankCardPassword;
					cv2.card = cv.card;
					cv2.cardType = cv.cardType;
					String firmID = cv.firmID;
					cv2.firmID = cv.contact;
					cv2.frozenFuns = cv.frozenFuns;
					conn = DAO.getConnection();
					try{
						conn.setAutoCommit(false);
						BankAdapterRMI bankadapter = this.getAdapter(cv.bankID);
						if(bankadapter==null){
							rv.result = ErrorCode.GetAdapter_Error;
							rv.remark = "获取连接银行适配器失败";
							return rv;
						}
						rv = dataProcess.changeFirmIsOpen(firmID,1,cv2.bankID,conn);
						if(rv.result<0){
							return rv;
						}
						rv = bankadapter.rgstAccountQuery(cv2);
						if(rv.result<0){
							return rv;
						}
						cv2.firmID = firmID;
						cv2.account1 = rv.account1;
						rv.result = DAO.openCorrespond(cv2,conn);
						conn.commit();
					}catch(Exception er){
						conn.rollback();
						throw er;
					}finally{
						conn.setAutoCommit(true);
					}
				}
				if(rv.result>=0){
					try{
						dataProcess.openAccount(cv.firmID,cv.bankID,conn);
					}catch(Exception e){
						log(Tool.getExceptionTrace(e));
					}
				}
			} catch (SQLException e) {
				rv.result=(int) ErrorCode.rgstAccount_DatabaseException;
				rv.remark = "数据库异常";
				log("银行开户 交易账号与银行账号对应SQLException,"+Tool.getExceptionTrace(e));
			} catch (Exception e) {
				rv.result=(int) ErrorCode.rgstAccount_SysException;
				rv.remark = "系统异常";
				log("银行开户 交易账号与银行账号对应Exception,"+Tool.getExceptionTrace(e));
			}finally{
				DAO.closeStatement(null, null, conn);
			}
		}
		return rv;
	}
	/**
	 * 银行账号注销
	 * @param bankID 银行代码
	 * @param firmID 交易账号代码
	 * @param bankAccount 银行账号
	 * @return int 操作结果：0 成功  非0 失败(1:信息不完整 2：账号未注册 3：银行解约失败 4：数据库操作失败 5：系统异常)
	 */
	public long delAccount(CorrespondValue correspondValue) {
		log("银行账号注销 delAccount correspondValue:"+(correspondValue == null ? "为 null" : "\n" + correspondValue.toString() + "\n"));
		long result = -1;
		if(correspondValue.bankID==null || correspondValue.bankID.length()==0 || correspondValue.firmID==null || correspondValue.firmID.length()==0 || correspondValue.account==null || correspondValue.account.length()==0 ){
			result = ErrorCode.delAccount_InfoHalfbaked;
			return result;
		}
		result = ifbankTrade(correspondValue.bankID,null,correspondValue.firmID,4,ProcConstants.bankLaunch).result;
		if(result <0){
			return result;
		}
		if(correspondValue.contact == null || correspondValue.contact.trim().length()<=0){
			correspondValue.contact = correspondValue.firmID;
		}
		CorrespondValue cv = null;
		try {
			cv = getCorrespond(correspondValue.bankID,null,correspondValue.contact,correspondValue.account);
		} catch (SQLException e1) {
			result = ErrorCode.DBError;
			return result;
		} catch (ClassNotFoundException e1) {
			result = ErrorCode.SystemError;
			return result;
		}
		if(cv == null){
			result = getMktActionID();
			return result;
		}
		if(cv.frozenFuns>0){
			result = ErrorCode.delAccount_FrozenFuns;
			return result;
		}
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			Vector<CapitalValue> capitals = DAO.getCapitalInfoList(" and bankID='"+cv.bankID.trim()+"' and contact='"+cv.contact.trim()+"' and createtime>=trunc(sysdate) and createtime<trunc(sysdate)+1  ",conn);
			if(capitals != null && capitals.size()>0){
				result = ErrorCode.delAccount_Transfer;
				return result;
			}
			try{
				conn.setAutoCommit(false);
				result = dataProcess.ifFirmDelAccount(cv.firmID, cv.bankID, conn);
				if(result<0){
					conn.rollback();
					return result;
				}
				Vector<CorrespondValue> vcv = DAO.getCorrespondList(" and isOpen="+ProcConstants.firmTypeOpen+" and contact='"+correspondValue.contact+"' and bankID<>'"+correspondValue.bankID+"'",conn);
				if(vcv == null || vcv.size()<=0){
					result = dataProcess.changeFirmIsOpen(cv.firmID, 2,cv.bankID, conn).result;
				}
				if(result >=0){
					DAO.destroyAccount(cv, conn);
					result = getMktActionID();
				}
				conn.commit();
			}catch(SQLException er){
				conn.rollback();
				throw er;
			}finally{
				conn.setAutoCommit(true);
			}
		} catch(SQLException e) {
			result = ErrorCode.delAccount_DatabaseException;
			log("银行账号注销SQLException，错误码="+result+"  "+Tool.getExceptionTrace(e));
		} catch(Exception e) {
			result = ErrorCode.delAccount_SysException;
			log("银行账号注销Exception，错误码="+result+"  "+Tool.getExceptionTrace(e));
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}
	/**
	 * 银行账号注销
	 * @param bankID 银行代码
	 * @param firmID 交易账号代码
	 * @param bankAccount 银行账号
	 * @return int 操作结果：0 成功  非0 失败(1:信息不完整 2：账号未注册 3：银行解约失败 4：数据库操作失败 5：系统异常)
	 * @throws 
	 */
	public ReturnValue delAccountMaket(CorrespondValue correspondValue) {
		log("银行账号注销 delAccountMaket correspondValue:"+(correspondValue == null ? "为 null" : "\n"+correspondValue.toString() + "\n"));
		ReturnValue result = ifbankTrade(correspondValue.bankID,correspondValue.firmID,correspondValue.contact,4,ProcConstants.marketLaunch);
		if(result.result<0){
			return result;
		}
		CorrespondValue cv = null;
		try {
			cv = getCorrespond(correspondValue.bankID,correspondValue.firmID,correspondValue.contact,correspondValue.account);
		} catch (SQLException e) {
			result.result = ErrorCode.DBError;
			result.remark = "查询账号绑定关系时数据库异常";
			log(Tool.getExceptionTrace(e));
			return result;
		} catch (Exception e) {
			result.result = ErrorCode.SystemError;
			result.remark = "查询账号绑定关系时系统异常";
			log(Tool.getExceptionTrace(e));
			return result;
		}
		if(cv == null){
			result.result = ErrorCode.delAccount_NORgsAcount;
			result.remark = "未查询到绑定信息";
			return result;
		}
		if(cv.frozenFuns>0){
			result.result = ErrorCode.delAccount_FrozenFuns;
			result.remark = "在途资金不为 0 不能解约";
			return result;
		}
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			Vector<CapitalValue> capitals = DAO.getCapitalInfoList(" and bankID='"+cv.bankID.trim()+"' and contact='"+cv.contact.trim()+"' and createtime>=trunc(sysdate) and createtime<trunc(sysdate)+1 ",conn);
			if(capitals != null && capitals.size()>0){
				result.result = ErrorCode.delAccount_Transfer;
				result.remark = "当日有转账，不能解约";
				return result;
			}
			BankAdapterRMI bankadapter = getAdapter(correspondValue.bankID);//适配器对象
			if(bankadapter==null){
				result.result = ErrorCode.GetAdapter_Error;
				result.remark = "连接银行通讯机(适配器)失败";
				return result;
			}
			try{
				conn.setAutoCommit(false);
				result.result = dataProcess.ifFirmDelAccount(cv.firmID, cv.bankID, conn);
				if(result.result<0){
					conn.rollback();
					result.remark = "不满足交易系统解约条件";
					return result;
				}
				Vector<CorrespondValue> vcv = DAO.getCorrespondList(" and isOpen="+ProcConstants.firmTypeOpen+" and contact='"+correspondValue.contact+"' and bankID<>'"+correspondValue.bankID+"'",conn);
				if(vcv == null || vcv.size()<=0){
					result = dataProcess.changeFirmIsOpen(cv.firmID, 2, cv.bankID, conn);
					if(result.result <0 ){
						conn.rollback();
						return result;
					}
				}
				DAO.destroyAccount(cv, conn);
				cv.firmID = cv.contact;
				ReturnValue returnValue=bankadapter.delAccountQuery(cv);
				if(returnValue == null){
					conn.rollback();
					log("解约，银行返回信息为空");
					result.result = ErrorCode.bankNull;
					result.remark = "解约适配器返回对象为空";
					return result;
				}
				if(returnValue.result<0){//银行解约失败
					conn.rollback();
					this.log(returnValue.toString());
					result.result = returnValue.result;
					result.remark = returnValue.remark;
					return result;
				}
				conn.commit();
			}catch(SQLException er){
				conn.rollback();
				throw er;
			} catch(Exception er){
				conn.rollback();
				throw er;
			}finally{
				conn.setAutoCommit(true);
			}
			result.result = ErrorCode.handle_success;
			result.remark = "解约账户信息成功";
		} catch(SQLException e) {
			result.result = ErrorCode.delAccount_DatabaseException;
			result.remark = "解约账号信息时数据库异常";
			log("银行账号注销SQLException，错误码="+result+"  "+Tool.getExceptionTrace(e));
		} catch(Exception e) {
			result.result = ErrorCode.delAccount_SysException;
			result.remark = "解约账户信息时系统异常";
			log("银行账号注销Exception，错误码="+result+"  "+Tool.getExceptionTrace(e));
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}
	
	/**
	 * 变更账户方法
	 * 变更 交易账号代码与银行账号对应关系 的签约状态
	 * @param cv1 原数据
	 * @param cv2 要修改的数据
	 * @return ReturnValue
	 */
	public ReturnValue modAccount(CorrespondValue cv1,CorrespondValue cv2) {
		log("变更账户方法 modAccount cv1:"+(cv1 == null ? "为 null" : "\n" + cv1.toString() + "\n") + " cv2:" + (cv2 == null ? "为 null" : "\n" +cv2.toString() + "\n"));
		long checkResult = chenckCorrespondValue(cv1);
		ReturnValue rv = new ReturnValue();
		if(checkResult==0) {
//			rv = ifbankTrade(cv1.bankID,null,cv1.contact,4,ProcConstants.marketLaunch);
//			if(rv.result<0){
//				return rv;
//			}
			rv.actionId=getMktActionID();
			try {
				cv1.account=cv2.account;
				cv1.isOpen=1;
				cv1.status=0;
				rv.result = DAO.modCorrespond(cv1);
			} catch (SQLException e) {
				rv.result = (int) ErrorCode.rgstAccount_DatabaseException;
				log("修改交易账号代码与银行账号对应SQLException,"+Tool.getExceptionTrace(e));
			} catch (Exception e) {
				rv.result = (int) ErrorCode.rgstAccount_SysException;
				log("修改交易账号代码与银行账号对应SQLException,"+Tool.getExceptionTrace(e));
			}
		} else {
			rv.result= checkResult;
			rv.remark = ErrorCode.error.get(rv.result);
		}
		return rv;
	}
	/**
	 * 市场变更账户方法
	 * 变更 交易账号代码与银行账号对应关系 的签约状态
	 * @param cv1 原数据
	 * @param cv2 要修改的数据
	 * @return ReturnValue
	 */
	public ReturnValue modAccountMarket(CorrespondValue cv1,CorrespondValue cv2) {
		log("变更账户方法 modAccountMarket cv1:"+(cv1 == null ? "为 null" : "\n" + cv1.toString() + "\n") + " cv2:" + (cv2 == null ? "为 null" : "\n" +cv2.toString() + "\n"));
		ReturnValue rv = new ReturnValue();
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			cv1.account=cv2.account;
			if(cv2.isOpen != ProcConstants.firmTypeOpen){
				Vector<CapitalValue> capitals = DAO.getCapitalInfoList(" and bankID='"+cv2.bankID.trim()+"' and trim(contact)='"+cv2.contact.trim()+"' and createtime>=trunc(sysdate) and createtime<trunc(sysdate)+1  ",conn);
				if(capitals != null && capitals.size()>0){
					rv.result = ErrorCode.delAccount_Transfer;
					rv.remark = "当日有转账，不能修改成解约状态";
					return rv;
				}
			}
			try{
				conn.setAutoCommit(false);
				if(cv2.isOpen == ProcConstants.firmTypeOpen){
					rv = dataProcess.changeFirmIsOpen(cv2.firmID, 1, cv2.bankID, conn);
				}else{
					rv.result = dataProcess.ifFirmDelAccount(cv2.firmID, cv2.bankID, conn);
					if(rv.result<0){
						rv.remark = "不满足交易系统解约条件";
						return rv;
					}
					Vector<CorrespondValue> vcv = DAO.getCorrespondList(" and isOpen="+ProcConstants.firmTypeOpen+" and trim(contact)='"+cv2.contact+"' and bankID<>'"+cv2.bankID+"'",conn);
					if(vcv == null || vcv.size()<=0){
						rv = dataProcess.changeFirmIsOpen(cv2.firmID, 2, cv2.bankID, conn);
					}
				}
				if(rv.result>=0){
					rv.result = DAO.modCorrespond(cv1,conn);
					rv.remark = "修改成功";
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
		} catch (SQLException e) {
			rv.result = (int) ErrorCode.DBError;
			rv.remark = "数据库异常";
			log("修改交易账号代码与银行账号对应SQLException,"+Tool.getExceptionTrace(e));
		} catch (Exception e) {
			rv.result = (int) ErrorCode.SystemError;
			rv.remark = "系统异常";
			log("修改交易账号代码与银行账号对应SQLException,"+Tool.getExceptionTrace(e));
		}finally{
			DAO.closeStatement(null, null, conn);
		}
		return rv;
	}
	/**
	 * 判断是否可以发生转账交易
	 * @param bankID 交易账号代码
	 * @return long
	 */
	public long tradeDate(String bankID){
		this.log("判断是否可以转账 tradeDate bankID["+bankID+"]");
		long result = -1;
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
			result = ErrorCode.Co_Trade;
			log(Tool.getExceptionTrace(e));
		}
		return result;
	}
	/**
	 * 判断某一天是否是交易日
	 * @param tradeDate 日期
	 * @return ReturnValue (0 可交易，-1 数据库异常，-2 系统异常，-3 不是交易日，-4 传入日期不合法)
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
			log(Tool.getExceptionTrace(e));
		}catch(Exception e){
			result.result = -2;
			result.remark = "查询日期["+Tool.fmtDate(tradeDate)+"]是否为交易日时系统异常";
			log(Tool.getExceptionTrace(e));
		}
		return result;
	}
	/**
	 * 取交易系统结算状态
	 * @return boolean 系统状态 true 已结算
	 */
	public boolean getTraderStatus() {
		log("取交易系统结算状态 getTraderStatus ");
		boolean status = false;
		try {
			  status = DAO.getTraderStatus();
		} catch (SQLException e) {
			log(Tool.getExceptionTrace(e));
		} catch (ClassNotFoundException e) {
			log(Tool.getExceptionTrace(e));
		}
		return status;
	}
	/**
	 * 查询交易账号信息
	 * @param contact 交易账号和银行绑定号
	 * @return FirmMessageVo
	 */
	public FirmMessageVo getFirmMSG(String contact) {
		FirmMessageVo result = null;
		try{
			Vector<FirmValue> cvl = DAO.getFirmList(" and CONTACT='"+contact+"'");
			if(cvl != null && cvl.size()==1){
				FirmValue fv = cvl.get(0);
				result = DAO.getFirmMSG(fv.firmID);
				result.Password = fv.password;
				if(result.Password != null && result.Password.trim().length()>0){
					return result;
				}
				String password = Tool.getConfig(ProcConstants.password);
				if(password != null && password.trim().length()>0){
					result.Password = Encryption.encryption(fv.firmID, password, null);
					return result;
				}
				password = fv.card;
				if(password == null || password.length()<=0){
					result.Password = Encryption.encryption(fv.firmID, "000000", null);
					return result;
				}
				password = password.trim().replaceAll("\\D", "0");
				for(int i=password.length();i<6;i++){
					password +="0";
				}
				if(password.length()>=6){
					result.Password = Encryption.encryption(fv.firmID, password.substring(password.length()-6, password.length()), null);
					return result;
				}
			}
		}catch(SQLException e){
			log(Tool.getExceptionTrace(e));
		}catch(Exception e){
			log(Tool.getExceptionTrace(e));
		}
		return result;
	}
	/**
	 * 判断当前是否可以发生转账
	 * @param bankID 银行编号
	 * @param contact 交易账号代码和银行绑定码
	 * @param type 转账类型 (0 入金,1 出金,2 查询余额,3 签约,4 解约)
	 * @param launcher 发起方(0 市场,1 银行)
	 * @return ReturnValue
	 */
	public ReturnValue ifbankTrade(String bankID,String firmID,String contact,int type,int launcher){
		log("判断当前是否可以发生转账 ifbankTrade bankID["+bankID+"]type["+type+"]");
		ReturnValue result = new ReturnValue();
		result.result = -1;
		try{
			BankValue bv = DAO.getBank(bankID);
			if(bv == null){
				result.result = ErrorCode.NotFindFirmIDAndAccount;
				result.remark = "未初始化此银行信息";
				return result;
			}
			if(bv.validFlag != 0){
				result.result = ErrorCode.BankDisable;
				result.remark = "["+bv.bankName+"]被禁用";
				return result;
			}
			if(type == ProcConstants.inMoneyType && bv.inMoneyFlag != 0){
				result.result = ErrorCode.BankDisable;
				result.remark = "["+bv.bankName+"]入金交易被禁用";
				return result;
			}
			if(type == ProcConstants.outMoneyType && bv.outMoneyFlag != 0){
				result.result = ErrorCode.BankDisable;
				result.remark = "["+bv.bankName+"]出金交易被禁用";
				return result;
			}
			result = firmFrozen(firmID,type,launcher,contact);
			if(result.result != 0){
				return result;
			}
			result.result = tradeDate(bankID);//判断配置日期时间
			if(result.result<0){
				result.remark = ErrorCode.error.get(result.result);
			}
		}catch(SQLException e){
			result.result = ErrorCode.DBError;
			result.remark = "数据库异常";
			log(Tool.getExceptionTrace(e));
		}catch(Exception e){
			result.result = ErrorCode.SystemError;
			result.remark = "系统异常";
			log(Tool.getExceptionTrace(e));
		}
		return result;
	}
	/**
	 * 判断交易商是否被冻结
	 * @param firmID 交易商代码
	 * @param type 出入金(0 入金,1 出金,2  其他)
	 * @param contact 签约号
	 * @return ReturnValue
	 * @throws SQLException,ClassNotFoundException
	 */
	private ReturnValue firmFrozen(String firmID,int type,int launcher,String contact)throws SQLException,ClassNotFoundException{
		ReturnValue result = new ReturnValue();
		FirmMessageVo fmv = null;
		if(firmID != null && firmID.trim().length()>0){
			fmv = DAO.getFirmMSG(firmID);
		}else if(contact != null && contact.trim().length()>0){
			fmv = this.getFirmMSG(contact);
		}
		if(fmv == null){
			result.result = ErrorCode.FirmID_Error;
			result.remark = "未查询到交易帐户信息";
			return result;
		}
		if("N".equalsIgnoreCase(fmv.status)){
			result.result = ErrorCode.handle_success;
			result.remark = "客户可操作状态";
			return result;
		}
		if(type != ProcConstants.inMoneyType && type != ProcConstants.outMoneyType){
			result.result = ErrorCode.handle_success;
			result.remark = "客户可操作状态";
			return result;
		}
		if("U".equalsIgnoreCase(fmv.status)){
			if(ProcConstants.bankLaunch == launcher){
				result.result = ErrorCode.handle_success;
				result.remark = "客户可操作状态";
				return result;
			}else{
				result.result = ErrorCode.inMoneyM_FirmNOUse;
				result.remark = "账户未激活";
				return result;
//				result.result = ErrorCode.handle_success;
//				result.remark = "客户可操作状态";
//				return result;
			}
		}
		if("F".equalsIgnoreCase(fmv.status)){
			if(type==ProcConstants.inMoneyType && !"C".equalsIgnoreCase(fmv.firmType)){
				result.result = ErrorCode.handle_success;
				result.remark = "客户可操作状态";
				return result;
			}else{
				result.result = ErrorCode.inMoneyM_FirmNOUse;
				result.remark = "账户被冻结";
				return result;
			}
		}
		result.result = ErrorCode.inMoneyM_FirmNOUse;
		result.remark = "客户禁用状态";
		return result;
	}
	/**
	 * 入金，由adapter调用
	 * @param bankID 银行代码
	 * @param money 金额
	 * @param contact 交易账号和银行绑定的绑定号
	 * @param account 交易账号银行账号
	 * @param bankTime 银行端入金时间
	 * @param funID 银行端业务流水号
	 * @param actionID 转账模块业务流水号
	 * @param funFlag 银行端操作成功或失败的标志 0：成功 1：失败，actionID>0时有效 5：处理中
	 * @param remark 备注
	 * @return long 银行接口业务流水号,返回<0的值表示操作失败(-10001：非法银行账号 -10002：非法交易账号代码 -10003：交易账号代码和账号对应关系错误 -10004：数据库异常 -10005：系统异常 -10006：参数错误
	 */
	public long inMoney (String bankID, String contact ,String account ,Timestamp bankTime ,double money ,String funID, long actionID, int funFlag, String remark){
		log("入金，由adapter调用 inMoney bankID["+bankID+"]firmID["+contact+"]account["+account+"]bankTime["+bankTime+"]money["+money+"]funID["+funID+"]actionID["+actionID+"]funFlag["+funFlag+"]remark["+remark+"]");
		long result = 0;//市场业务流水号
		long capitalID = 0;//资金划转流水号
		String firmID = null;//交易账号编号
		Connection conn = null;//数据库连接
		if(bankTime==null){
			bankTime=new Timestamp(System.currentTimeMillis());//当前系统时间
		}
		try {
			CorrespondValue cv = getCorrespond(bankID,null,contact,account);
			if(cv == null){
				result = ErrorCode.NotFindFirmIDAndAccount;
				return result;
			}
			if(cv.isOpen != ProcConstants.firmTypeOpen){//签约成功状态
				result = ErrorCode.inMoneyM_FirmNO;
				log("银行发起入金，交易账号未签约。");
				return result;
			}
			if(cv.status != ProcConstants.firmStatusTrue){
				result = ErrorCode.inMoneyM_FirmNOUse;
				log("银行发起入金，交易账号禁用");
				return result;
			}
			log("查询到交易账号绑定信息为："+cv.toString());
			contact = cv.contact;
			account = cv.account;
			firmID = cv.firmID;
			ReturnValue rv = ifbankTrade(bankID,firmID,contact,ProcConstants.inMoneyType,ProcConstants.bankLaunch);
			if(rv.result != 0){
				log(rv.remark);
				result = rv.result;
				return result;
			}
			conn = DAO.getConnection();
			Vector<CapitalValue> v = DAO.getCapitalInfoList(" and actionid="+actionID+" or (funid='"+funID+"' and createtime>=to_date('"+Tool.fmtDate(bankTime)+"','yyyy-MM-dd') and createtime<to_date('"+Tool.fmtDate(bankTime)+"','yyyy-MM-dd')+1 and bankID='"+bankID+"')",conn);
			if(result == 0) {
				try {
					if(v==null||v.size()<=0) {//未在数据库中查询到流水，则认为是银行发起的入金
						if(money<=0){
							result = ErrorCode.moneyMastZ;
							return result;
						}
						result = DAO.getActionID(conn);//取得市场业务流水号
						CapitalValue cVal = new CapitalValue();
						cVal.trader = "";
						cVal.funID = funID;
						cVal.actionID = result;
						cVal.firmID = firmID;
						cVal.contact = contact;
						cVal.bankID = bankID;
						cVal.type = ProcConstants.inMoneyType;
						cVal.launcher = ProcConstants.bankLaunch;
						cVal.money = money;
						cVal.status = ProcConstants.statusMarketProcessing;
						cVal.bankTime = bankTime;
						cVal.note = "银行端入金";
						capitalID = DAO.addCapitalInfo(cVal, conn);//记录转账模块资金流水
					} else {//从数据库中查询到了流水，则用已经有的流水
						if(v.size()>0) {
							for(int i=0;i<v.size();i++){
								CapitalValue val = (CapitalValue)v.get(i);
								if(val.type == ProcConstants.inMoneyType) {
									capitalID = val.iD;
									result = val.actionID;
								}
							}
						}
					}
				} catch(SQLException e) {
					throw e;
				}
			}
			if(result>0) {
				try {
					v = DAO.getCapitalInfoList(" and actionid="+result+" and status="+ProcConstants.statusMarketProcessing);
					conn.setAutoCommit(false);//启动数据库事务
					if(v.size()>0){//if(actionID > 0)
						Vector<CapitalValue> ll = DAO.getCapitalInfoList(" and actionid="+result+" and status="+ProcConstants.statusMarketProcessing+" for update ",conn);
						if(ll == null || ll.size()<=0){
							result = ErrorCode.outMoneyAudit_OradyAudite;
							log("信息已在处理中");
							return result;
						}
						if(funFlag == 0) {//银行处理成功
							dataProcess.updateFundsFull(bankID,firmID, money, ProcConstants.inMoneyType, conn);
							DAO.modCapitalInfoStatus(capitalID,funID, ProcConstants.statusSuccess,bankTime, conn);
							DAO.modCapitalInfoNote(capitalID, "入金成功", conn);
						} else if(funFlag == 5) {//银行返回处理中
							DAO.modCapitalInfoStatus(capitalID,funID, ProcConstants.statusBankProcessing,bankTime, conn);
							DAO.modCapitalInfoNote(capitalID, "银行处理中", conn);
						} else if(funFlag == ErrorCode.bankNull) {//未接收到银行返回
							DAO.modCapitalInfoStatus(capitalID,funID, ProcConstants.statusBankNull,bankTime, conn);
							DAO.modCapitalInfoNote(capitalID, "银行无返回", conn);
						} else if(funFlag == ErrorCode.ActionID_Error) {//银行返回报文异常
							DAO.modCapitalInfoStatus(capitalID,funID, ProcConstants.statusBankError,bankTime, conn);
							DAO.modCapitalInfoNote(capitalID, "银行返回异常", conn);
						} else {//银行返回失败
							DAO.modCapitalInfoStatus(capitalID,funID, ProcConstants.statusFailure,bankTime, conn);
							DAO.modCapitalInfoNote(capitalID, remark, conn);
						}
					}else{
						result = ErrorCode.inMoney_ErrorAddCapital;
						log("查询到流水"+capitalID+"的状态不正确");
					}
					conn.commit();
				} catch(SQLException e) {
					conn.rollback();
					throw e;
				} finally {
					conn.setAutoCommit(true);
				}
				if(result == ErrorCode.inMoney_DataBaseException) {//调用交易系统接口失败，将资金流水置为处理异常状态
					DAO.modCapitalInfoStatus(capitalID,funID, ProcConstants.statusMarketError,bankTime, conn);
					DAO.modCapitalInfoNote(capitalID, "市场处理异常", conn);
				}
			}
		} catch(SQLException e) {
			result = ErrorCode.inMoney_DataBaseException;
			log("入金回调方法SQLException,异常值="+result+"  "+Tool.getExceptionTrace(e));
		} catch(Exception e) {
			result = ErrorCode.inMoney_SysException;
			log("入金回调方法Exception,异常值="+result+"  "+Tool.getExceptionTrace(e));
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		log("由adapter调用 inMoney bankID["+bankID+"]firmID["+contact+"]account["+account+"]bankTime["+bankTime+"]money["+money+"]funID["+funID+"]actionID["+actionID+"]funFlag["+funFlag+"]remark["+remark+"]返回给适配器["+result+"]");
		return result;
	}
	/**
	 * 入金，由市场端调用
	 * @param imm 入金信息
	 * @return ReturnValue
	 */
	public ReturnValue inMoneyMarket(InMoneyMarket imm) {
		log("市场入金 inMoneyMarket imm:"+(imm == null ? "为 null" : "\n" + imm.toString() + "\n"));
		ReturnValue result = new ReturnValue();//返回信息
		long capitalID = 0;//资金划转流水号
		Connection conn = null;//数据库连接
		Timestamp curTime = new Timestamp(System.currentTimeMillis());//当前系统时间
		try {
			if(imm.money<=0){
				result.result = ErrorCode.moneyMastZ;
				result.remark = "入金金额必须为正数";
				return result;
			}
			conn = DAO.getConnection();
			CorrespondValue cv = getCorrespond(imm.bankID,imm.firmID,imm.contact,imm.account);
			if(cv == null){
				result.result = ErrorCode.NotFindFirmIDAndAccount;
				result.remark = "未查询到交易账号信息";
				return result;
			}
			imm.contact = cv.contact;
			imm.account = cv.account;
			imm.firmID = cv.firmID;
			result = ifbankTrade(imm.bankID,imm.firmID,imm.contact,ProcConstants.inMoneyType,ProcConstants.marketLaunch);
			if(result.result != 0) {
				return result;
			}
			if(cv.isOpen != ProcConstants.firmTypeOpen){//交易账号未签约状态
				result.result = ErrorCode.inMoneyM_FirmNO;
				result.remark = "交易账号未签约";
				return result;
			}
			if(cv.status != ProcConstants.firmStatusTrue){
				result.result = ErrorCode.inMoneyM_FirmNOUse;
				result.remark = "交易账号不可用";
				return result;
			}
			log(imm.toString());
			result.actionId = DAO.getActionID(conn);//取得市场业务流水号
			result.result = result.actionId;
			try {
				//记录转账模块资金流水
				CapitalValue cVal = new CapitalValue();
				cVal.trader = "";
				cVal.actionID = result.actionId;
				cVal.firmID = imm.firmID;
				cVal.contact = imm.contact;
				cVal.bankID = imm.bankID;
				cVal.type = ProcConstants.inMoneyType;
				cVal.launcher = ProcConstants.marketLaunch;
				cVal.money = imm.money;
				cVal.status = ProcConstants.statusMarketProcessing;
				cVal.note = "市场端入金";
				log(cVal.toString());
				capitalID = DAO.addCapitalInfo(cVal, conn);//入金流水
			} catch(SQLException e) {
				e.printStackTrace();
				result.result=ErrorCode.inMoneyM_ErrorAddCapital;
				result.remark = "添加流水信息时数据库异常";
				log("市场端发起入金写资金流水SQLException,数据回滚:"+e);
				return result;
			}
			if(result.result<=0) {
				return result;
			}
			TransferInfoValue payInfo = getPayInfo(imm.bankID, imm.firmID,ProcConstants.inMoneyType, conn);//取得入金付款方信息
			TransferInfoValue receiveInfo= getReceiveInfo(imm.bankID, imm.firmID,ProcConstants.inMoneyType, conn);//取得入金收款方消息
			InMoneyVO inMoneyInfo=new InMoneyVO(imm.bankID,null, imm.money, imm.contact, payInfo, imm.accountPwd,receiveInfo,imm.remark, result.actionId);
			BankAdapterRMI bankadapter = getAdapter(imm.bankID);//取得适配器对象
			if(bankadapter == null){
				result.result = ErrorCode.GetAdapter_Error;
				result.remark = "网络异常，处理器无法连接适配器";
				log(result.remark);
			}else{
				result = bankadapter.inMoneyQueryBank(inMoneyInfo);//调用适配器入金接口,适配器入金接口回调inmoney方法.
			}
			log("市场端调用适配器入金，市场返回信息："+result.toString());
			try {
				if(result.result == 5) {
					DAO.modCapitalInfoStatus(capitalID,result.funID,ProcConstants.statusBankProcessing,curTime, conn);
					DAO.modCapitalInfoNote(capitalID, result.remark, conn);
					log("适配器处理成功，业务流水处理中。");
				} else if(result.result >= 0) {
					result.remark = "入金成功";
					log("适配器处理成功，入金成功。");
				} else if( result.result==ErrorCode.bankNull ) {
					DAO.modCapitalInfoStatus(capitalID,result.funID, ProcConstants.statusBankNull,curTime, conn);
					DAO.modCapitalInfoNote(capitalID, result.remark, conn);
					log("适配器连接银行，银行无应答");
				} else if( result.result==ErrorCode.ActionID_Error ) {
					DAO.modCapitalInfoStatus(capitalID,result.funID, ProcConstants.statusBankError,curTime, conn);
					DAO.modCapitalInfoNote(capitalID, result.remark, conn);
					log("适配器连接银行，银行返回市场流水异常");
				} else {//适配器提交失败
					DAO.modCapitalInfoStatus(capitalID,null, ProcConstants.statusFailure,curTime, conn);
					DAO.modCapitalInfoNote(capitalID, result.remark, conn);
					log("适配器提交失败:"+result.toString());
				}
			} catch(SQLException e) {
				result.result =ErrorCode.inMoneyM_DataBaseException;
				result.remark = "修改流水状态，数据库异常";
				e.printStackTrace();
				log("市场端发起入金,更新资金流失状态为失败SQLException,数据回滚:"+result.toString());
			} catch(Exception e) {
				result.result =ErrorCode.inMoneyM_SysException;
				result.remark = "修改流水状态，系统异常";
				e.printStackTrace();
				log("市场端发起入金,系统异常Exception,数据回滚:"+result.toString());
			}
			if(result.result == ErrorCode.inMoneyM_DataBaseException) {//调用交易系统接口失败，将资金流水置为处理失败状态
				DAO.modCapitalInfoStatus(capitalID,result.funID, ProcConstants.statusMarketError,curTime, conn);
				DAO.modCapitalInfoNote(capitalID, "处理异常", conn);
			}
		} catch(SQLException e) {
			result.result = ErrorCode.inMoneyM_DataBaseException;
			result.remark = "数据库异常，请联系交易所";
			log("市场端发起入金SQLException:"+result+Tool.getExceptionTrace(e));
		} catch(Exception e) {
			result.result = ErrorCode.inMoneyM_SysException;
			result.remark = "系统异常，请联系交易所";
			log("市场端发起入金Exception:"+result+Tool.getExceptionTrace(e));
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}
	/**
	 * 出金
	 * @param bankID 银行代码
	 * @param money 金额
	 * @param contact 交易账号和银行绑定号
	 * @param bankAccount 银行账号
	 * @param funID 银行流水号
	 * @param express 0：正常 1：加急
	 * @param type 0：市场端出金 1：银行端同步出金 2：银行端异步出金 3：银行端出金必须审核
	 * @return long 银行接口业务流水号,返回<0的值表示操作失败
	 */
	public ReturnValue outMoney(String bankID,double money,String contact,String bankAccount,String funID,String operator,int express,int type){
		log("出金: outMoney bankID["+bankID+"]money["+money+"]contact["+contact+"]bankAccount["+bankAccount+"]funID["+funID+"]operator["+operator+"]express["+express+"]type["+type+"]");
		ReturnValue result=new ReturnValue();//返回结果集
		String firmID = null;//交易账号代码
		Connection conn = null;//数据库连接
		try {
			CorrespondValue cv = getCorrespond(bankID,null,contact,bankAccount);
			if(cv == null){
				result.result = ErrorCode.NotFindFirmIDAndAccount;
				result.remark = "出金，未查询到交易账号绑定信息";
				return result;
			}
			if(cv.isOpen != ProcConstants.firmTypeOpen){//签约成功状态
				result.result = ErrorCode.outMoney_FirmNO;
				result.remark = "交易账号未签约。";
				log(result.remark);
				return result;
			}
			if(cv.status != ProcConstants.firmStatusTrue){
				result.result = ErrorCode.outMoney_FirmNOUse;
				result.remark = "交易账号不可用";
				log(result.remark);
				return result;
			}
			contact = cv.contact;
			bankAccount = cv.account;
			firmID = cv.firmID;
			result = ifbankTrade(bankID,firmID,contact,ProcConstants.outMoneyType,ProcConstants.bankLaunch);
			if(result.result<0){
				return result;
			}
			conn = DAO.getConnection();
			result = checkArgs(conn,money,firmID,bankID);//判断是否符合出入金条件 0：符合条件；<>0:不符合转账条件
			if(result.result != 0){
				return result;
			}
			long actionId = handleOUtMoney(conn,bankID,"",money,firmID,contact,bankAccount,funID,type);
			result.result = actionId;
			if(result.result<=0) {
				result.remark = "写入资金流水失败";
				return result;
			}
			result = outMoneyAutoAudit(result.result);
			result.actionId = actionId;
		} catch(SQLException e) {
			result.result =ErrorCode.outMoney_DataBaseException;
			result.remark = "数据库异常";
			log("银行发起出金SQLException，错误码="+result.result+"  "+Tool.getExceptionTrace(e));
		} catch(Exception e) {
			result.result = ErrorCode.outMoney_SysException;
			result.remark = "系统异常";
			log("银行发起出金Exception，错误码="+result.result+"  "+Tool.getExceptionTrace(e));
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}
	/**
	 * 出金 (市场方调用)
	 * @param omm 市场出金类
	 * @return ReturnValue
	 */
	public ReturnValue outMoneyMarket(OutMoneyMarket omm){
		log("出金: outMoney omm:"+(omm==null ? "为空" : "\n" + omm.toString()+"\n"));
		ReturnValue result=new ReturnValue();//返回结果集
		Connection conn = null;//数据库连接
		try {
			CorrespondValue cv = getCorrespond(omm.bankID,omm.firmID,omm.contact,omm.account);
			if(cv == null){
				result.result = ErrorCode.NotFindFirmIDAndAccount;
				result.remark = "出金，未查询到交易账号绑定信息";
				return result;
			}
			if(cv.isOpen != ProcConstants.firmTypeOpen){//签约成功状态
				result.result = ErrorCode.outMoney_FirmNO;
				result.remark = "交易账号未签约。";
				log(result.remark);
				return result;
			}
			if(cv.status != ProcConstants.firmStatusTrue){
				result.result = ErrorCode.outMoney_FirmNOUse;
				result.remark = "交易账号不可用";
				log(result.remark);
				return result;
			}
			omm.firmID = cv.firmID;
			omm.contact = cv.contact;
			omm.account = cv.account;
			result = ifbankTrade(omm.bankID,omm.firmID,omm.contact,ProcConstants.outMoneyType,ProcConstants.marketLaunch);
			if(result.result<0){
				return result;
			}
			conn = DAO.getConnection();
			result = checkArgs(conn,omm.money,omm.firmID,omm.bankID);//判断是否符合出入金条件 0：符合条件；<>0:不符合转账条件
			if(result.result != 0) {
				return result;
			}
			long actionId = handleOUtMoney(conn,omm.bankID,"",omm.money,omm.firmID,omm.contact,omm.account,null,omm.type);
			result.result = actionId;
			if(result.result<=0) {
				result.remark = ErrorCode.error.get(result.result);
			}
			result = outMoneyAutoAudit(result.result);
			result.actionId = actionId;
		} catch(SQLException e) {
			result.result =ErrorCode.outMoney_DataBaseException;
			result.remark = "数据库发生异常，请联系交易所";
			log("市场发起出金SQLException，错误码="+result.result+"  "+Tool.getExceptionTrace(e));
		} catch(Exception e) {
			result.result = ErrorCode.outMoney_SysException;
			result.remark = "转账系统异常，请联系交易所";
			log("市场发起出金Exception，错误码="+result.result+"  "+Tool.getExceptionTrace(e));
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}
	/**
	 * 审核处理中的信息
	 * @param actionID 市场流水号，
	 * @param funFlag true 通过、false 拒绝
	 * @return ReturnValue
	 */
	public ReturnValue moneyInAudit(long actionID,String funID,boolean funFlag){
		log("审核处理中的信息 moneyInAudit actionID["+actionID+"]funFlag["+funFlag+"]");
		Connection conn = null;//数据库连接
		Timestamp curTime = new Timestamp(System.currentTimeMillis());
		CapitalValue capital =null;//流水信息
		ReturnValue result = new ReturnValue();//返回码
		try {
			conn = DAO.getConnection();
			Vector<CapitalValue> list = DAO.getCapitalInfoList(" and actionid="+actionID, conn);//获取资金流水对象（包括出金和出金手续费流水）
			for (int i = 0; i < list.size(); i++) {
				CapitalValue val = (CapitalValue)list.get(i);
				if(val.type == ProcConstants.outMoneyType || val.type == ProcConstants.inMoneyType) {
					capital = val;//出金流水
					result.actionId = capital.actionID;
					result.bankTime = Tool.fmtTime(curTime);
					result.funID = capital.funID;
					if(capital.status==0 || capital.status==1){
						result.result = ErrorCode.outMoneyAudit_OradyAudite;
						result.remark = "本条记录["+actionID+"]已经有人处理";
						log(result.remark);
						return result;
					}
				}
			}
			if(capital == null){
				result.result = ErrorCode.Audit_Arraidy;
				result.remark = "未查询到流水["+actionID+"]信息";
				return result;
			} else {
				if(funFlag){//审核通过
					try {
						conn.setAutoCommit(false);//启动数据库事务
						if(capital.type==ProcConstants.outMoneyType){//出金流水
							dataProcess.updateFundsFull(capital.bankID,capital.firmID, capital.money, ProcConstants.outMoneyType, conn);
							dataProcess.updateFrozenFunds(capital.firmID, -1*capital.money, conn);//资金解冻
							//修改交易账号银行接口冻结资金
							this.bankFrozenFuns(capital.firmID,capital.contact, capital.bankID, null, -1*capital.money, conn);
							DAO.modCapitalInfoStatus(capital.iD,funID, ProcConstants.statusSuccess,curTime, conn);
							DAO.modCapitalInfoNote(capital.iD, "审核通过", conn);
						}else if(capital.type==ProcConstants.inMoneyType){//入金流水
							dataProcess.updateFundsFull(capital.bankID,capital.firmID, capital.money, ProcConstants.inMoneyType, conn);
							DAO.modCapitalInfoStatus(capital.iD,funID, ProcConstants.statusSuccess,curTime, conn);
							DAO.modCapitalInfoNote(capital.iD, "审核通过", conn);
						}
						DAO.modCapitalInfoTrader(capital.iD, "manual", conn);
						log(actionID+"单边账审核通过");
						conn.commit();
					}catch(SQLException e){
						conn.rollback();
						log(actionID+"审核出金SQLException，错误码="+result+"  "+Tool.getExceptionTrace(e));
						throw e;
					} finally {
						conn.setAutoCommit(true);
					}
				}else{//审核拒绝
					try{
						conn.setAutoCommit(false);//启动数据库事务
						if(capital.type==ProcConstants.outMoneyType){//拒绝出金
							dataProcess.updateFrozenFunds(capital.firmID, -1*capital.money, conn);//资金解冻
							this.bankFrozenFuns(capital.firmID,capital.contact, capital.bankID, null, -1*capital.money, conn);//修改交易账号银行接口冻结资金
							DAO.modCapitalInfoStatus(capital.iD,result.funID, ProcConstants.statusFailure,curTime, conn);
							DAO.modCapitalInfoNote(capital.iD, "审核拒绝", conn);
						}else if(capital.type == ProcConstants.inMoneyType){//拒绝入金
							DAO.modCapitalInfoStatus(capital.iD,result.funID, ProcConstants.statusFailure,curTime, conn);
							DAO.modCapitalInfoNote(capital.iD, "审核拒绝", conn);
						}
						DAO.modCapitalInfoTrader(capital.iD, "manual", conn);
						log(actionID+"单边账审核拒绝");
						conn.commit();
					}catch(SQLException e){
						conn.rollback();
						log(actionID+"审核出金SQLException，错误码="+result+"  "+Tool.getExceptionTrace(e));
						throw e;
					}finally{
						conn.setAutoCommit(true);
					}
				}
			}
		}catch(SQLException e){
			result.result = ErrorCode.checkTrans_DataBaseException;
			result.remark = "单边账处理["+actionID+"]数据库异常";
			log(actionID+"审核出金SQLException，错误码="+result+"  "+Tool.getExceptionTrace(e));
		} catch (Exception e) {
			result.result = ErrorCode.checkTrans_SysException;
			result.remark = "单边账处理["+actionID+"]系统异常";
			log(Tool.getExceptionTrace(e));
		}finally{
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}
	/**
	 * 冲证(银行方调用)
	 * @param actionID  转账模块业务流水号
	 * @param funID 银行业务流水号
	 * @return  操作结果：0 处理成功；-1 参数非法；-2 未找到要冲证的数据；-3 数据处理失败；-4 系统异常
	 */
	public ReturnValue chargeAgainst(ChargeAgainstValue cav){
		log("冲证(银行方调用) chargeAgainst cav:"+(cav==null ? "为 null" : "\n"+cav.toString()+"\n"));
		Connection conn = null;//数据库连接
		String filter = "";//查询条件
		ReturnValue rv = new ReturnValue();//返回信息
		if(cav==null){
			rv.result=ErrorCode.charge_bankNull;
			rv.remark="传入的参数为空";
		}else{
			try {
				conn = DAO.getConnection();
				//filter = " and bankID='"+cav.bankID+"' and (trunc(createtime)=trunc(sysdate) or trunc(bankTime)=trunc(sysdate)) and status not in ("+ProcConstants.statusFailure+","+ProcConstants.statusBlunt+") and (type="+ProcConstants.inMoneyType+" or type="+ProcConstants.outMoneyType+") ";
				filter = " and bankID='"+cav.bankID+"' and ((createtime>=trunc(sysdate) and createtime<trunc(sysdate)+1) or (bankTime>=trunc(sysdate) and bankTime<trunc(sysdate)+1)) and status not in ("+ProcConstants.statusFailure+","+ProcConstants.statusBlunt+") and (type="+ProcConstants.inMoneyType+" or type="+ProcConstants.outMoneyType+") ";
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
					if(capList == null || capList.size() <= 0){
						rv.result=ErrorCode.marketSer_None;
						rv.remark="查询流水不存在：市场流水号["+cav.actionID+"]银行流水号["+cav.funID+"]";
					}else{
						Map<String,FirmBalanceValue> firmMsg = new HashMap<String,FirmBalanceValue>();
						conn.setAutoCommit(false);
						FirmBalanceValue fbv =null;
						rv.actionId=DAO.getActionID(conn);//获取actionID
						CapitalValue cv = new CapitalValue();
						cv.actionID = rv.actionId;
						cv.trader ="";
						cv.bankTime = cav.bankTime;//获取银行时间
						cv.funID = cav.funIDCA;//获取银行流水号
						cv.money = 0;//冲正金额
						cv.bankID = cav.bankID;
						cv.launcher = ProcConstants.bankLaunch;
						cv.note = "冲正，记录号:";
						int status = 0;
						for (int i = 0; i < capList.size(); i++){
							CapitalValue capVal = (CapitalValue)capList.get(i);
							status = capVal.status;
							fbv=null;
							if(firmMsg.get(capVal.firmID)!=null){
								fbv = firmMsg.get(capVal.firmID);
							}else{
								String filter2 = " and firmid='"+capVal.firmID+"' and bankcode='"+cav.bankID+"' for update ";
								fbv = DAO.availableBalance(filter2, conn);
								if(fbv!=null){
									firmMsg.put(capVal.firmID, fbv);
								}else{
									rv.result=ErrorCode.charge_noFirm;
									rv.remark = "没有查询到交易账号"+capVal.firmID;
									throw new Exception("没有查询到当个交易账号"+capVal.firmID);
								}
							}
							cv.firmID = capVal.firmID;
							cv.contact = capVal.contact;
							cv.money += capVal.money;
							cv.note = cv.note+capVal.iD+" ";
							capVal.status = ProcConstants.statusBlunt;
							if(capVal.type==ProcConstants.inMoneyType){
								cv.type = ProcConstants.inMoneyBlunt;
							}else if(capVal.type==ProcConstants.outMoneyType){
								cv.type = ProcConstants.outMoneyBlunt;
							}
							DAO.modCapitalInfoStatus(capVal.iD, capVal.funID+"r", capVal.status, capVal.bankTime, conn);
							DAO.modCapitalInfoNote(capVal.iD, "被银行冲正", conn);
						}
						if(fbv==null){
							rv.result=ErrorCode.charge_noFirm;
							rv.remark = "没有查询到交易账号，或本交易账号当前资金不足以冲正";
							throw new Exception("在交易系统的交易账号资金表查询不到本交易账号");
						}else if( cv.money+fbv.canOutMoney<0){
							rv.result = ErrorCode.marketBackMoney_False;
							rv.remark = "交易账号中资金不足以冲正";
							throw new Exception("账号资金不足，不能冲正");
						}
						DAO.addCapitalInfo(cv, conn);//添加冲正流水
						if(status == 0){
							dataProcess.updateFundsFull(cv.bankID,cv.firmID, cv.money, cv.type, conn);
						}else if(cv.type == ProcConstants.outMoneyBlunt){
							dataProcess.updateFrozenFunds(cv.firmID, -1*cv.money, conn);
						}
						conn.commit();
					}
				}
			}catch(SQLException e){
				log(Tool.getExceptionTrace(e));
				if(rv.result==0){
					rv.result = ErrorCode.charge_SQLException;
					rv.remark = "冲正操作中出现数据库异常";
				}
				try{
				conn.rollback();
				}catch(SQLException e1){
					log(Tool.getExceptionTrace(e1));
				}
			}catch(Exception e){
				log(Tool.getExceptionTrace(e));
				if(rv.result==0){
					rv.result = ErrorCode.czSystem_error;
					rv.remark = "冲正操作中出现系统异常";
				}
				try{
				conn.rollback();
				}catch(SQLException e1){
					log(Tool.getExceptionTrace(e1));
				}
			}finally{
				try{
				conn.setAutoCommit(true);
				}catch(SQLException e){
					log(Tool.getExceptionTrace(e));
				}
				DAO.closeStatement(null, null, conn);
			}
		}
		return rv;
	}
	public Vector<FirmBankMsg> getfirmBankMsg(String filter){
		Vector<FirmBankMsg> result = null;
		try{
			result = DAO.getfirmBankMsg(filter);
		}catch(SQLException e){
			log(Tool.getExceptionTrace(e));
		}catch(Exception e){
			log(Tool.getExceptionTrace(e));
		}
		return result;
	}
	/**
	 * 查询交易账号市场资金
	 * @param contact 交易账号和银行绑定号
	 * @return  FirmBalanceValue
	 */
	public FirmBalanceValue getMarketBalance(String contact,String bankID) {
		FirmBalanceValue result = getMarketBalance(null,contact,bankID);
		if(result != null){
			result.firmId = contact;
		}
		return result;
	}
	/**
	 * 查询交易账号市场资金
	 * @param firmID 交易账号代码
	 * @param contact 交易账号和银行绑定号
	 * @return  FirmBalanceValue
	 */
	public FirmBalanceValue getMarketBalance(String firmID,String contact,String bankID) {
		FirmBalanceValue fbv = null;
		String filter = "";
		Connection conn = null;
		CorrespondValue cv = null;
		try{
			if(firmID != null && firmID.trim().length()>0){
				Vector<CorrespondValue> vec = DAO.getCorrespondList(" and firmID='"+firmID.trim()+"'");
				if(vec != null && vec.size()>0){
					cv = vec.get(0);
				}
			}else if(contact != null && contact.trim().length()>0){
				Vector<CorrespondValue> vec = DAO.getCorrespondList(" and contact='"+contact.trim()+"'");
				if(vec != null && vec.size()>0){
					cv = vec.get(0);
					firmID = cv.firmID;
				}
			}
			if(firmID==null || firmID.trim().length()<=0) {
				log("查询交易账号市场余额，未获取到交易账号代码");
				return new FirmBalanceValue();
			}else{
				filter += " and firmid='"+firmID+"' and bankcode='"+bankID+"' ";
			}
			fbv = DAO.availableBalance(filter);
			BankValue bv = DAO.getBank(bankID);
			if(bv != null){
				fbv.bankName = bv.bankName;
			}
			fbv.inTransitMoney = cv == null ? 0 : cv.frozenFuns;
			conn = DAO.getConnection();
			fbv.canOutMoney = dataProcess.getRealFunds(bankID,firmID,0, conn);
			if(fbv.canOutMoney<0){
				fbv.canOutMoney = 0;
			}
		} catch (SQLException e) {
			log(Tool.getExceptionTrace(e));
		} catch (Exception e) {
			log(Tool.getExceptionTrace(e));
		}finally{
			DAO.closeStatement(null, null, conn);
		}
		log("交易账号["+firmID+"]市场资金信息"+(fbv==null ? "市场资金查询失败" : fbv.toString()));
		return fbv;
	}
	/**
	 * 查询交易账号市场可用资金和银行账号余额
	 * @param bankID 银行编号
	 * @param firmID 交易账号代码
	 * @param pwd 银行卡密码
	 * @return  FirmBalanceValue
	 */
	public FirmBalanceValue getFirmBalance(String bankid,String firmid,String pwd) {
		log("查询交易账号市场可用资金和银行账号余额 getFirmBalance bankid["+bankid+"]firmid["+firmid+"]pwd["+pwd+"]");
		FirmBalanceValue fv = new FirmBalanceValue();
		ReturnValue rv = ifbankTrade(bankid,firmid,null,2,ProcConstants.marketLaunch);
		if(rv.result<0){
			fv.setCanOutMoney(rv.result);
			return fv;
		}
//		FirmBalanceValue fv1 = getMarketBalance(firmid,null,bankid);
		FirmBalanceValue fv2 = getBankBalance(bankid, firmid, pwd);
		fv.setFirmId(firmid);
		fv.setFirmBankId(bankid);
//		fv.setAvilableBalance(fv1.avilableBalance);
//		fv.setFrozenBalance(fv1.frozenBalance);
//		fv.setMarketBalance(fv1.marketBalance);
//		fv.setLastBalance(fv1.lastBalance);
//		fv.setCanOutMoney(fv1.canOutMoney);
//		fv.setInOutMoney(fv1.inOutMoney);
		fv.setBankAccount(fv2.bankAccount);
		fv.setBankBalance(fv2.bankBalance);
		log("查询交易账号余额返回：\n"+fv.toString());
		return fv;
	}
	/**
	 * 查询交易账号银行资金
	 * @param bankID 银行编号
	 * @param firmID 交易账号代码
	 * @param pwd 银行卡密码
	 * @return  FirmBalanceValue
	 */
	public FirmBalanceValue getBankBalance(String bankID,String firmID,String pwd) {
		log("查询交易账号银行资金 getBankBalance bankid["+bankID+"]firmid["+firmID+"]pwd["+pwd+"]");
		FirmBalanceValue fv = new FirmBalanceValue();
		String filter = " and bankid='"+bankID+"' and firmid='"+firmID+"' ";
	  	Vector<CorrespondValue> v = null;
		try {
			v = DAO.getCorrespondList(filter);
		} catch (SQLException e) {
			log(Tool.getExceptionTrace(e));
		} catch (ClassNotFoundException e) {
			log(Tool.getExceptionTrace(e));
		}
  		double accountBalance=0;
  		ReturnValue rv = ifbankTrade(bankID,firmID,null,2,ProcConstants.marketLaunch);
		if(rv.result>=0){
			log("当前银行设置允许查询余额");
			try {
				if(v != null && v.size()>0) {
					CorrespondValue cv = (CorrespondValue) v.get(0);
					fv.setBankAccount(cv.account);
					BankAdapterRMI bankadapter = getAdapter(bankID);
					if(bankadapter != null){
						cv.firmID = cv.contact;
						accountBalance = bankadapter.accountQuery(cv,pwd);
					}else{
						accountBalance = ErrorCode.GetAdapter_Error;
					}
				}else{
					log("查询余额时，未查询到交易账号信息");
				}
			} catch (RemoteException e) {
				accountBalance = ErrorCode.GetAdapter_Error;
				log(Tool.getExceptionTrace(e));
			} catch (Exception e){
				accountBalance = ErrorCode.GetAdapter_Error;
				log(Tool.getExceptionTrace(e));
			}
		}else{
			accountBalance = rv.result;
			log(rv.toString());
		}
		fv.setBankBalance(accountBalance);
		log("查询银行余额返回：\n"+fv.toString());
		return fv;
	}
	/**
	 * 获取市场资金流水数据
	 * @param filter 
	 * @return 获取资金流水数据
	 */
	public Vector<CapitalValue> getCapitalList(String filter) {
		log("获取市场资金流水数据 getCapitalList filter["+filter+"]");
		Connection conn = null;//数据连接
		Vector<CapitalValue> capitalList=null;
		try {
			conn = DAO.getConnection();
			capitalList = DAO.getCapitalInfoList(filter, conn);
		} catch(Exception e) {
			log(Tool.getExceptionTrace(e));
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return  capitalList;
	}
	/**
	 * 获取银行对账信息
	 * @param bankID  银行ID 如果为null则通知所有适配器取银行数据
	 * @param date 获取数据日期
	 * @return  操作结果：0 成功   -30011:没有找到bankID对应的适配器 -30012:将对账信息插入数据库发生错误（银行代码为空） -30013：将对账信息插入数据库发生错误（银行代码不为空）
	 */
	public int getBankCompareInfo(String bankID, java.util.Date date) {
		log("获取银行对账信息 getBankCompareInfo bankID[" + bankID + "]date[" + date + "]");
		int result = 0;
		if (bankID == null) {
			result = (int) ErrorCode.BankID_Error;
			return result;
		}
		BankAdapterRMI bankadapter = getAdapter(bankID);
		if (bankadapter == null) {
			result = (int) ErrorCode.GetAdapter_Error;
			log("获取银行对账信息,错误码=" + result);
			return result;
		}
		Vector<MoneyInfoValue> moneyInfoList = null;
		try {
			Vector bankmsg = new Vector();
			bankmsg.add(bankID);
			moneyInfoList = bankadapter.getBankMoneyInfo(date, bankmsg);
		} catch (RemoteException e) {
			log(Tool.getExceptionTrace(e));
		} catch (Exception e) {
			log(Tool.getExceptionTrace(e));
		}
		if (moneyInfoList == null) {
			result = -30013;
			log("获取银行对账信息,错误码=" + result);
			return result;
		}
		result = insertBankCompareInfo(moneyInfoList);
		if (result < 0) {
			result = -30013;
			log("插入银行转账信息失败=" + result);
		}
		return result;
	}
	/**
	 * 写入银行对账信息
	 * @param list 对账信息，每项为MoneyInfoValue
	 * @return int 操作结果：0 成功  非0 失败: -1对账日期不一致  -2对账信息为空  -3系统异常
	 */
	public int insertBankCompareInfo(Vector<MoneyInfoValue> list) {
		int result = (int)insertBankMoneyInfo(list,1);
		return result;
	}
	/**
	 * 提供给适配器的保存银行数据的方法
	 * @param mv：bank data  ready: true:insertBankMoneyInfo false:checkMoney
	 * @return long 0:success <>0:failure
	 */
	public long insertBankMoneyInfo(Vector<MoneyInfoValue> mv,int ready) {
		log("提供给适配器的保存银行数据的方法 insertBankMoneyInfo mv["+mv+"]ready["+ready+"]");
		long result = 0;
		if(ready!=1 && ready!=2){//银行数据准备完毕  保存银行数据
			log("传入的 ready ["+ready+"]");
			result = -1;
			return result;
		}
		if(mv==null){
			log("传入的集合为 null");
			result = -1;
			return result;
		}
		if(mv.size()<=0){
			log("传入的集合中没有数据");
			return result;
		}
		MoneyInfoValue first = mv.get(0);
		if(first==null || first.compareDate==null) {
			result = -1;
			log("传入的信息中没有日期信息");
			return result;
		}
		for (int i = 1; i < mv.size(); i++) {
			if(mv.get(i).compareDate==null) {
				result = -1;
				log("写入银行对账信息,对账日期为空");
				return result;
			}
			if(!Tool.fmtDate(first.compareDate).equals(Tool.fmtDate(mv.get(i).compareDate))) {
				result = -1;
				log("写入银行对账信息,对账日期不相符，错误码="+result);
				return result;
			}
		}
		Connection conn = null;
		try {
			result = mv.size();
			conn = DAO.getConnection();
			try{
				conn.setAutoCommit(false);
				DAO.delMoneyInfo(" and bankid='"+first.bankID+"' and trunc(compareDate)=to_date('"+Tool.fmtDate(first.compareDate)+"','yyyy-MM-dd')", conn);
				for(int a=0;a<mv.size();a++) {
					MoneyInfoValue miv = mv.get(a);
					if(miv != null){
						if(ready==2) {
							miv.account=-1+"";
							miv.status=0;
							miv.note="day_Netting";
						}
						DAO.addMoneyInfo(miv,conn);
					}
				}
				conn.commit();
			}catch(SQLException e){
				conn.rollback();
				throw e;
			}finally{
				conn.setAutoCommit(true);
			}
		} catch (SQLException e) {
			result = -1;
			log(Tool.getExceptionTrace(e));
		} catch (Exception e) {
			result = -1;
			log(Tool.getExceptionTrace(e));
		} finally{
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}
	/**
	 * 处理日间产生的对账不平数据
	 * @param bankID 银行编号
	 * @param tradeDate 交易日期
	 * @return ReturnValue
	 */
	public synchronized ReturnValue roughInfo(String bankID,java.util.Date tradeDate){
		ReturnValue result = new ReturnValue();
		Vector<CompareResult> bankNoInfo = this.getBankNoInfo(bankID, tradeDate);
		Vector<CompareResult> marketNoInfo = this.getMarketNoInfo(bankID, tradeDate);
		Vector<Long> actionIDs = new Vector<Long>();
		Vector<CapitalValue> capitals = new Vector<CapitalValue>();
		if(bankNoInfo != null){
			for(CompareResult cr : bankNoInfo){
				actionIDs.add(cr.actionID);
			}
		}
		if(marketNoInfo != null){
			CapitalValue value = null;
			for(CompareResult cr : marketNoInfo){
				value = new CapitalValue();
				value.account = cr.account;
				value.bankID = cr.bankID;
				value.bankTime = new java.sql.Timestamp(cr.tradeDate.getTime());
				value.contact = cr.contact;
				value.firmID = cr.firmID;
				value.funID = cr.funID;
				value.launcher = ProcConstants.marketLaunch;
				value.money = cr.money;
				value.note = "日终流水";
				value.status = ProcConstants.statusSuccess;
				value.trader = "endofday";
				value.type = cr.type;
				capitals.add(value);
			}
		}
		ReturnValue reresult = this.refuseCapitalInfo(actionIDs);
		ReturnValue suresult = this.supplyCapitalInfo(capitals);
		if(reresult.result<0){
			result.result = reresult.result;
			result.remark += reresult.remark;
		}
		if(suresult.result<0){
			result.result = suresult.result;
			result.remark += suresult.remark;
		}
		if(result.result>=0){
			result.remark = "全部对账流水处理成功";
		}
		return result;
	}
	/**
	 * 查询市场有，银行没有流水的记录信息
	 * @param bankID 银行编号
	 * @param tradeDate 交易日期
	 * @return Vector<CompareResult>
	 */
	public Vector<CompareResult> getBankNoInfo(String bankID,java.util.Date tradeDate){
		Vector<CompareResult> result = null;
		Connection conn = null;
		try{
			conn = DAO.getConnection();
			result = DAO.getBankNoInfo(bankID, tradeDate, conn,null);
		}catch(SQLException e){
			log(Tool.getExceptionTrace(e));
		}catch(Exception e){
			log(Tool.getExceptionTrace(e));
		}finally{
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}
	/**
	 * 查询银行有，市场没有流水的记录信息
	 * @param bankID 银行编号
	 * @param tradeDate 交易日期
	 * @return Vector<CompareResult>
	 */
	public Vector<CompareResult> getMarketNoInfo(String bankID,java.util.Date tradeDate){
		Vector<CompareResult> result = null;
		Connection conn = null;
		try{
			conn = DAO.getConnection();
			result = DAO.getMarketNoInfo(bankID, tradeDate, conn,null);
		}catch(SQLException e){
			log(Tool.getExceptionTrace(e));
		}catch(Exception e){
			log(Tool.getExceptionTrace(e));
		}finally{
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}
	/**
	 * 查询交易账号当天出入金求和数据
	 * @param bankID 银行编号
	 * @param firmIDs 交易账号代码集
	 * @param date 转账日期
	 * @return Vector<CapitalCompare>
	 */
	public Vector<CapitalCompare> sumResultInfo(String bankID,String[] firmIDs,java.util.Date date) {
		this.log("查询交易账号当天出入金求和数据 bankID["+bankID+"]firmIDs["+firmIDs+"]date["+date+"]");
		Vector<CapitalCompare> result = null;
		try{
			result = DAO.sumResultInfo(bankID, firmIDs, date);
		}catch(SQLException e){
			log(Tool.getExceptionTrace(e));
		}catch(Exception e){
			log(Tool.getExceptionTrace(e));
		}
		return result;
	}
	/**
	 * 批量冲正成功流水
	 * @param actionIDs 市场流水号列表
	 * @return ReturnValue
	 */
	public ReturnValue refuseCapitalInfo(Vector<Long> actionIDs){
		ReturnValue result = new ReturnValue();
		if(actionIDs != null){
			for(Long actionID:actionIDs){
				if(actionID != null){
					ReturnValue rv = refuseCapitalInfo(actionID);
					if(rv.result<0){
						result.result = -1;
						result.remark = "["+actionID+"]";
					}
				}
			}
		}
		if(result.result != 0){
			result.remark = "流水"+result.remark+"冲正失败";
		}
		return result;
	}
	/**
	 * 冲正银行没有成功的流水
	 * @param actionID 市场流水号
	 * @return ReturnValue
	 */
	public ReturnValue refuseCapitalInfo(long actionID){
		this.log("冲正银行没有成功的流水["+actionID+"]");
		ReturnValue result = new ReturnValue();
		Connection conn = null;
		try{
			conn = DAO.getConnection();
			try{
				conn.setAutoCommit(false);
				Vector<CapitalValue> value = DAO.getCapitalInfoList(" and (type="+ProcConstants.inMoneyType+" or type="+ProcConstants.outMoneyType+") and status<>"+ProcConstants.statusFailure+" and status<>"+ProcConstants.statusBlunt+" and ACTIONID="+actionID+" for update ",conn);
				if(value == null || value.size()<=0){
					result.result = ErrorCode.ActionInAudit;
					result.remark = "当比流水["+actionID+"]已经受理";
				}else{
					for(CapitalValue cv : value){
						DAO.modCapitalInfoStatus(cv.iD, "refuse"+cv.iD, ProcConstants.statusBlunt, null, conn);
						DAO.modCapitalInfoNote(cv.iD, "日终被冲正", conn);
						DAO.modCapitalInfoTrader(cv.iD, "endofday", conn);
						if(cv.type == ProcConstants.inMoneyType && cv.status == ProcConstants.statusSuccess){
							dataProcess.updateFundsFull(cv.bankID,cv.firmID, cv.money, ProcConstants.inMoneyBlunt, conn);
						}else if(cv.type == ProcConstants.outMoneyType){
							if(cv.status == ProcConstants.statusSuccess){
								dataProcess.updateFundsFull(cv.bankID,cv.firmID, cv.money, ProcConstants.outMoneyBlunt, conn);
							}else{
								dataProcess.updateFrozenFunds(cv.firmID, -1*cv.money, conn);//资金解冻
								this.bankFrozenFuns(cv.firmID, cv.contact, cv.bankID, cv.account, -1*cv.money, conn);//银行接口表冻结资金
							}
						}
					}
				}
				conn.commit();
			}catch(SQLException e){
				conn.rollback();
				result.result = ErrorCode.DBError;
				result.remark = "修改流水["+actionID+"]资金时数据库异常";
				throw e;
			}finally{
				conn.setAutoCommit(true);
			}
		}catch(SQLException e){
			result.result = ErrorCode.DBError;
			result.remark = "冲正流水["+actionID+"]数据库异常";
			log(Tool.getExceptionTrace(e));
		}catch(Exception e){
			result.result = ErrorCode.SystemError;
			result.remark = "重整流水["+actionID+"]系统异常";
			log(Tool.getExceptionTrace(e));
		}finally{
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}
	/**
	 * 批量导入银行成功流水
	 * @param vector 流水信息
	 * @return ReturnValue
	 */
	public ReturnValue supplyCapitalInfo(Vector<CapitalValue> vector){
		ReturnValue result = new ReturnValue();
		result.remark = "";
		if(vector != null && vector.size()>0){
			for(CapitalValue cv : vector){
				if(cv != null){
					ReturnValue cvresult = supplyCapitalInfo(cv);
					if(cvresult.result < 0){
						result.result = -1;
						result.remark += "["+cv.funID+"]";
					}
				}
			}
		}
		if(result.result != 0){
			result.remark = "流水"+result.remark+"导入失败";
		}
		return result;
	}
	/**
	 * 添加成功资金流水
	 * @param value 流水对象
	 * @return ReturnValue
	 */
	public ReturnValue supplyCapitalInfo(CapitalValue value){
		this.log("添加成功资金流水");
		ReturnValue result = new ReturnValue();
		Connection conn = null;
		if(value == null){
			result.result = ErrorCode.ActionInAudit;
			result.remark = "传入对象为空";
		}else if(value.funID == null || value.funID.trim().length()<=0){
			result.result = ErrorCode.ActionInAudit;
			result.remark = "传入银行流水号为空";
		}else if(value.bankID == null || value.bankID.trim().length()<=0){
			result.result = ErrorCode.ActionInAudit;
			result.remark = "传入银行代码为空";
		}else if(value.firmID == null || value.firmID.trim().length()<=0){
			result.result = ErrorCode.ActionInAudit;
			result.remark = "传入交易账号为空";
		}else if(value.contact == null || value.contact.trim().length()<=0){
			result.result = ErrorCode.ActionInAudit;
			result.remark = "传入和银行绑定号为空";
		}else if(value.money<=0){
			result.result = ErrorCode.ActionInAudit;
			result.remark = "传入资金必须大于0";
		}else if(value.bankTime == null){
			result.result = ErrorCode.ActionInAudit;
			result.remark = "传入的银行时间不合法";
		}else{
			try{
				conn = DAO.getConnection();
				try{
					conn.setAutoCommit(false);
					Vector<CapitalValue> cvlist = DAO.getCapitalInfoList(" and bankid='"+value.bankID+"' and funid='"+value.funID+"' and trunc(BANKTIME)=to_date('"+Tool.fmtDate(value.bankTime)+"','yyyy-MM-dd')",conn);
					if(cvlist != null && cvlist.size()>0){
						result.result = ErrorCode.ActionInAudit;
						result.remark = "当笔流水已经处理";
					}else{
						value.actionID = DAO.getActionID();
						value.status = ProcConstants.statusSuccess;
						result.result = DAO.addCapitalInfo(value, conn);
						dataProcess.updateFundsFull(value.bankID,value.firmID, value.money, value.type, conn);
						result.remark = "流水["+result.result+"]金额["+value.money+"]类型["+value.type+"]";
					}
					conn.commit();
				}catch(SQLException e){
					conn.rollback();
					throw e;
				}finally{
					conn.setAutoCommit(true);
				}
			}catch(SQLException e){
				result.result = ErrorCode.DBError;
				result.remark = "添加成功资金流水数据库异常";
				log(Tool.getExceptionTrace(e));
			}catch(Exception e){
				result.result = ErrorCode.SystemError;
				result.remark = "添加成功资金流水系统异常";
				log(Tool.getExceptionTrace(e));
			}finally{
				DAO.closeStatement(null, null, conn);
			}
		}
		log("插入单条银行单边流水：\n"+result.toString());
		return result;
	}
	/**
	 * 查询银行接口和银行通讯信息
	 * @param filter 查询条件
	 * @return Vector<InterfaceLog>
	 */
	public Vector<InterfaceLog> interfaceLogList(String filter,int[] pageinfo){
		Vector<InterfaceLog> result = null;
		try{
			result = DAO.interfaceLogList(filter, pageinfo);
		}catch(Exception e){
			log(Tool.getExceptionTrace(e));
		}
		return result;
	}
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
	/**
	 * 获得清算文件
	 * @param bankId
	 * @param date
	 * @return 0成功;
	 * @author tanglt 2011-5-29
	 */
	public int getDataFile(String bankId,Date date){
		return 0;
	}
	
	/**
	 * 发送清算文件
	 * @param bankId
	 * @param date
	 * @return 0成功
	 * @author tanglt 2011-5-29
	 */
	public int sendDataFile(String bankId,Date date){
		
		BankAdapterRMI bankadapter = getAdapter(bankId);
		ExchangeData exdata=null;
		String clazz="gnnt.trade.bank.data.CEBExDataImpl";
		//clazz = dao.getClazzName from table(字典表?)
		try {
			exdata=(ExchangeData)Class.forName(clazz).newInstance();
		} catch (Exception e) {
			log(Tool.getExceptionTrace(e));
		}
		
		return exdata.createDataFile(bankadapter, date);
	}
	public static void main(String args[]){
		StartupRmiserver sr = new StartupRmiserver();
		try {
			sr.init();
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
	
}
