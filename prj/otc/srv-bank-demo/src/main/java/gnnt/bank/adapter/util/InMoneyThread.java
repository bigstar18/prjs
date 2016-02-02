package gnnt.bank.adapter.util;

import gnnt.bank.adapter.AdapterFirmVO;
import gnnt.bank.adapter.BankAdapter;
import gnnt.bank.adapter.dao.DAOFactory;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.InterfaceLog;
import gnnt.trade.bank.vo.LogEndmsg;
import gnnt.trade.bank.vo.ReturnValue;
import java.rmi.RemoteException;
import java.sql.Timestamp;
public class InMoneyThread extends Thread{
	/** 交易商签约信息  */
	private CorrespondValue _correspondValue;
	/** 入金金额 */
	private double _money;
	/** 当前时间 */
	private long BankSer = new java.util.Date().getTime();
	/**
	 * 构造方法
	 * @param firmID 交易商签约号
	 * @param money 入金金额
	 */
	public InMoneyThread(CorrespondValue correspondValue,double money){
		Tool.log("构造方法  InMoneyThread  money:"+money);
		this._correspondValue = correspondValue;
		this._money = money;
	}
	/**
	 * 启动线程
	 */
	public void run(){
		try {
			Thread.sleep(getTime());
			bankProcInMoney();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	/** 调用处理器入金 */
	private ReturnValue bankProcInMoney() {
		ReturnValue result = new ReturnValue();
		LogEndmsg le = new LogEndmsg();
		try {
			long m = 0;
			try {
				m = BankAdapter.getProcessor().inMoney(_correspondValue.bankID, _correspondValue.firmID, _correspondValue.account, new Timestamp(BankSer),
						_money, BankSer + "", 0, 0, "BFMem");
				Tool.log("签约自动入金[" + _money + "]调用 inMoney 返回: " + m);
			} catch (NumberFormatException e) {
				Tool.log(Tool.getExceptionTrace(e));
				m = -1;
			} catch (RemoteException e) {
				Tool.log(Tool.getExceptionTrace(e));
				m = -1;
			}
			if (m >= 0) {
				le.actionID = m;
				result.result = 0;
				result.remark = "银行端发起入金成功";
				result.funID = BankSer + "";
			} else {
				try {
					AdapterFirmVO value = DAOFactory.getDAO().getFirmVO(_correspondValue.bankID, _correspondValue.firmID);
					value.setMoney(_money);
					DAOFactory.getDAO().outMoney(value);
				} catch (Exception e) {
					Tool.log(Tool.getExceptionTrace(e));
				}
				result.result = (int) m;
				result.remark = "银行端发起入金失败" + m;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			InterfaceLog log = new InterfaceLog();
			log.bankID = _correspondValue.bankID;
			log.account = _correspondValue.account;
			log.contact = _correspondValue.firmID;
			log.launcher = 1;
			log.type = 7;
			le.funID = ""+BankSer;
			le.code = ""+result.result;
			le.remark = result.remark;
			if(!"0".equalsIgnoreCase(le.code)){
				log.result = 1;
			}
			log.endMsg = le.toString();
			interfaceLog(log);
		}
		return result;
	}
	/**
	 * 获取睡眠时间
	 * @return int
	 */
	private int getTime(){
		int time = 3;
		try{
			String mini = Tool.getConfig(Tool.sleeptime);
			if(mini != null && mini.trim().length()>0){
				time = Integer.parseInt(mini);
				if(time<=0 || time>100){
					time = 3;
				}
			}
		}catch(Exception e){
			Tool.log(Tool.getExceptionTrace(e));
		}
		return time*1000;
	}
	
	private void interfaceLog(InterfaceLog log){
		try{
			Tool.log(log.toString());
			BankAdapter.getProcessor().interfaceLog(log);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
