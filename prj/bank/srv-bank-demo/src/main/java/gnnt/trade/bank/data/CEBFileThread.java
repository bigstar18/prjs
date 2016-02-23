package gnnt.trade.bank.data;
import gnnt.bank.adapter.rmi.BankAdapterRMI;
import gnnt.trade.bank.CapitalProcessor;
import gnnt.trade.bank.dao.BankDAO;
import gnnt.trade.bank.dao.BankDAOFactory;
import gnnt.trade.bank.util.ProcConstants;
import gnnt.trade.bank.util.Tool;
import gnnt.trade.bank.vo.ClearingStatusVO;
import gnnt.trade.bank.vo.SystemStatusVO;

import java.util.Date;
public class CEBFileThread extends Thread {
	/**银行编号*/
	private String bankID = "003";
	/**DAO*/
	private BankDAO DAO = null;
	public CEBFileThread (){
		try {
			DAO = BankDAOFactory.getDAO();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		}
	}
	/**
	 * 启动线程
	 */
	public void run(){
		while(true){
			try {
				Thread.sleep(getTime());
				ClearingStatusVO clearing = ifsendFile();
				if(clearing != null){
					int result = sendFile(bankID,clearing.tradeDate);
					clearing.generalTime = new Date();
					if(result == 0){
						clearing.generalStatus = 2;
					}else{
						clearing.generalStatus = 3;
					}
					DAO.modClearing(clearing);
					System.out.println("生成清算文件，返回生成结果["+result+"]记录结果信息："+clearing.toString());
				}else{
					System.out.println("当前状态不满足生成清算文件条件");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (Exception e){
				e.printStackTrace();
			}
		}
	}
	/**
	 * 判断当前是否需要发送清算数据
	 */
	private ClearingStatusVO ifsendFile(){
		ClearingStatusVO result = null;
		SystemStatusVO ssv = getSSV();
		if(ssv.status != 3){//如果交易系统还未结算，不发送信息
			return result;
		}
		try{
			ClearingStatusVO csv = DAO.getMaxClearing(bankID);
			if(csv == null){
				csv = new ClearingStatusVO();
				csv.bankID = bankID;
				csv.tradeDate = ssv.tradeDate;
				int len = DAO.addClearing(csv);
				if(len>0){
					result = DAO.getMaxClearing(bankID);
				}
				return result;
			}
			if(!Tool.fmtDate(csv.tradeDate).equalsIgnoreCase(Tool.fmtDate(ssv.tradeDate))){
				csv = new ClearingStatusVO();
				csv.bankID = bankID;
				csv.tradeDate = ssv.tradeDate;
				int len = DAO.addClearing(csv);
				if(len>0){
					result = DAO.getMaxClearing(bankID);
				}
				return result;
			}
			if(csv.generalStatus==1){
				result = csv;
			}
			return result;
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 获取当前系统信息
	 * @return SystemStatusVO 系统状态信息
	 */
	private SystemStatusVO getSSV(){
		SystemStatusVO result = null;
		try{
			result = DAO.getSystemStatus();
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 生成清算文件
	 * @param date 清算文件日期
	 * @return int (0 成功 -1 失败);
	 */
	private int sendFile(String bankID,Date date){
		CEBExDataImpl impl =new CEBExDataImpl(bankID);
		CapitalProcessor cp =new CapitalProcessor();
		BankAdapterRMI adapter = cp.getAdapter("003");
		return impl.createDataFile(adapter,date);
	}
	/**
	 * 获取间隔时间
	 * @return int
	 */
	private int getTime(){
		int time = 5;//分钟
		try{
			String sec = Tool.getConfig(ProcConstants.qssleeptime);
			if(sec != null && sec.trim().length()>0){
				time = Integer.parseInt(sec);
				if(time<=0 || time>100){
					time = 2;
				}
			}
		}catch(Exception e){
		}
		return time*1000*60;
	}
}
