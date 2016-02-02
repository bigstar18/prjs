package gnnt.trade.bank.util;
import gnnt.trade.bank.dao.BankDAO;
import gnnt.trade.bank.dao.BankDAOFactory;
import gnnt.trade.bank.vo.BankValue;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * 常量控制类
 */
public class StaticMsg {
	/**保存初始化时银行信息*/
	private static Map<String,BankValue> bankMap;
	private static String StaticMsgsyn = "StaticMsgsyn";
	/**数据库连接*/
	private BankDAO DAO;
	public StaticMsg(){
		try {
			DAO = BankDAOFactory.getDAO();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
	}
	/**获取初始时银行信息*/
	public Map<String,BankValue> getBankMap(){
		if(bankMap == null){
			setBankMap();
		}
		return bankMap;
	}
	/**获取初始时银行信息*/
	public BankValue getBank(String bankID){
		Map<String,BankValue> bankMap = getBankMap();
		return bankMap.get(bankID);
	}
	/**初始化银行信息*/
	private void setBankMap(){
		if(bankMap != null){
			return;
		}
		synchronized(StaticMsgsyn){
			if(bankMap != null){
				return;
			}
			bankMap = new HashMap<String,BankValue>();
			try {
				Vector<BankValue> bankList = DAO.getBankList("");
				if(bankList != null && bankList.size()>0){
					for(BankValue bank : bankList){
						if(bank != null){
							bankMap.put(bank.bankID, bank);
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
