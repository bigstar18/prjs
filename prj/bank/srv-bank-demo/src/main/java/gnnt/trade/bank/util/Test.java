package gnnt.trade.bank.util;
import gnnt.trade.bank.dao.BankDAO;
import gnnt.trade.bank.dao.BankDAOFactory;
import gnnt.trade.bank.processorrmi.CapitalProcessorRMI;
import gnnt.trade.bank.vo.CapitalValue;
import gnnt.trade.bank.vo.CompareResult;
import gnnt.trade.bank.vo.CorrespondValue;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.util.Vector;
public class Test {
	private static CapitalProcessorRMI _processor;
	private static BankDAO _DAO;
	public Test(){
		//getProcessor();
		getDAO();
	}
	/**
	 * 将银行端成功的单边账同步到市场端
	 * @param firmid 交易账号代码
	 * @param type 转账类型
	 * @param money 转账金额
	 */
	public void supplyCapitalInfo(String firmid,int type,double money){
		try{
			outfirmMoney(0,firmid);
			outPrint(getProcessor().supplyCapitalInfo(getCapitalInfo(firmid,type,money)));
			outfirmMoney(0,firmid);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 冲正成功的流水
	 * @param actionID
	 */
	public void refuseCapitalInfo(long actionID){
		try{
			outfirmMoney(actionID,null);
			outPrint(getProcessor().refuseCapitalInfo(actionID));
			outfirmMoney(actionID,null);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 取得市场和银行两边不一致的流水信息
	 * @param bankID 银行编号
	 * @param tradeDate 交易日期
	 * @param conn 数据库连接
	 * @return Vector<CompareResult>
	 */
	public Vector<CompareResult> getdefaultInfo(String bankID,java.util.Date tradeDate){
		Vector<CompareResult> result = new Vector<CompareResult>();
		result.addAll(this.getBankNoInfo(bankID, tradeDate));
		result.addAll(this.getMarketNoInfo(bankID, tradeDate));
		return result;
	}
	/**
	 * 取得要插入流水的信息
	 * @param firmid
	 * @param type
	 * @return
	 */
	private CapitalValue getCapitalInfo(String firmid,int type,double money){
		CapitalValue value = new CapitalValue();
		Vector<CorrespondValue> vec = getCorrespondValue(firmid);
		if(vec != null && vec.size()>0){
			CorrespondValue cv = vec.get(0);
			value.firmID = firmid;
			value.funID = new java.util.Date().getTime()+"";
			value.account = cv.account;
			value.bankID = cv.bankID;
			value.bankName = cv.bankName;
			value.bankTime = new java.sql.Timestamp(new java.util.Date().getTime());
			value.contact = cv.contact;
			value.launcher = ProcConstants.marketLaunch;
			value.money = money;
			value.note = "日终调整数据";
			value.status = 0;
			value.trader = "market";
			value.type = type;
		}
		return value;
	}
	/**
	 * 查询交易账号和银行的绑定关系
	 * @param firmid 交易账号代码
	 * @return Vector<CorrespondValue>
	 */
	private Vector<CorrespondValue> getCorrespondValue(String firmid){
		Vector<CorrespondValue> result = null;
		try{
			result = getProcessor().getCorrespondValue(" and firmid='"+firmid+"'");
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 打印交易账号当前资金
	 * @param actionID
	 */
	private void outfirmMoney(long actionID,String firmid){
		if(firmid == null || firmid.trim().length()<=0){
			Vector<CapitalValue> v = getCapitalList(actionID);
			if(v != null && v.size()>0){
				firmid = v.get(0).firmID;
				
			}
		}
		if(firmid != null && firmid.trim().length()>0){
			try{
				outPrint(getProcessor().getMarketBalance(firmid, null));
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	/**
	 * 查询流水信息
	 * @param actionID 市场流水号
	 * @return
	 */
	private Vector<CapitalValue> getCapitalList(long actionID){
		Vector<CapitalValue> result = null;
		try{
			result = getProcessor().getCapitalList(" and actionID='"+actionID+"'");
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 打印信息
	 * @param obj 信息对象
	 */
	private void outPrint(Object obj){
		System.out.println(obj.toString());
	}
	/**
	 * 取得处理器对象
	 * @return CapitalProcessorRMI 处理器对象
	 */
	private CapitalProcessorRMI getProcessor(){
		if(_processor == null){
			synchronized(this){
				if(_processor == null){
					String processorIP = Tool.getConfig("RmiIpAddress");
					String processorPort = Tool.getConfig("RmiPortNumber");
					String serviceName = Tool.getConfig("RmiServiceName");
					try {
						_processor = (CapitalProcessorRMI) Naming.lookup("//"+processorIP+":"+processorPort+"/"+serviceName);
					} catch (MalformedURLException e) {
						e.printStackTrace();
					} catch (RemoteException e) {
						e.printStackTrace();
					} catch (NotBoundException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return _processor;
	}
	/**
	 * 取得数据库连接类
	 */
	private BankDAO getDAO(){
		if(_DAO == null){
			synchronized(this){
				if(_DAO == null){
					try{
						_DAO = BankDAOFactory.getDAO();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InstantiationException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return _DAO;
	}
	/**
	 * 查询转账不平交易账号信息
	 */
	public void sumResultInfo(String bankID,java.util.Date date){
		try{
			outPrint(getProcessor().sumResultInfo(bankID, null, date));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 查询市场有，银行没有的流水
	 */
	private Vector<CompareResult> getBankNoInfo(String bankID,java.util.Date tradeDate){
		Vector<CompareResult> result = null;
		Connection conn = null;
		try{
			conn = this.getDAO().getConnection();
			result = getDAO().getBankNoInfo(bankID, tradeDate, conn,null);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.getDAO().closeStatement(null, null, conn);
		}
		return result;
	}
	/**
	 * 查询银行有，市场没有的流水
	 */
	private Vector<CompareResult> getMarketNoInfo(String bankID,java.util.Date tradeDate){
		Vector<CompareResult> result = null;
		Connection conn = null;
		try{
			conn = this.getDAO().getConnection();
			result = getDAO().getMarketNoInfo(bankID, tradeDate, conn,null);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.getDAO().closeStatement(null, null, conn);
		}
		return result;
	}
	/**
	 * 测试方法
	 * @param args
	 */
	public static void main(String args[]){
		Test test = new Test();
		//test.refuseCapitalInfo(30);//冲正成功流水
		//test.supplyCapitalInfo("001",ProcConstants.outMoneyType,1000);//增加成功流水
//		test.sumResultInfo("01",new java.util.Date());//查看转账资金不平的交易账号
		Vector<CompareResult> result = test.getdefaultInfo("01", Tool.strToDate("2011-06-10"));
		if(result != null){
			for(CompareResult cr : result){
				System.out.println(cr.toString());
			}
		}
	}
}
