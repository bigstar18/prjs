package gnnt.trade.bank.util;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Vector;

import gnnt.trade.bank.dao.BankDAO;
import gnnt.trade.bank.dao.BankDAOFactory;
import gnnt.trade.bank.processorrmi.CapitalProcessorRMI;
import gnnt.trade.bank.vo.CapitalValue;
import gnnt.trade.bank.vo.CompareResult;
import gnnt.trade.bank.vo.CorrespondValue;

public class Test {
	private static CapitalProcessorRMI _processor;
	private static BankDAO _DAO;

	public Test() {
		getDAO();
	}

	public void supplyCapitalInfo(String firmid, int type, double money) {
		try {
			outfirmMoney(0L, firmid);
			outPrint(getProcessor().supplyCapitalInfo(getCapitalInfo(firmid, type, money)));
			outfirmMoney(0L, firmid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void refuseCapitalInfo(long actionID) {
		try {
			outfirmMoney(actionID, null);
			outPrint(getProcessor().refuseCapitalInfo(actionID));
			outfirmMoney(actionID, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Vector<CompareResult> getdefaultInfo(String bankID, Date tradeDate) {
		Vector<CompareResult> result = new Vector();
		result.addAll(getBankNoInfo(bankID, tradeDate));
		result.addAll(getMarketNoInfo(bankID, tradeDate));
		return result;
	}

	private CapitalValue getCapitalInfo(String firmid, int type, double money) {
		CapitalValue value = new CapitalValue();
		Vector<CorrespondValue> vec = getCorrespondValue(firmid);
		if ((vec != null) && (vec.size() > 0)) {
			CorrespondValue cv = (CorrespondValue) vec.get(0);
			value.firmID = firmid;
			value.funID = new Date().getTime() + "";
			value.account = cv.account;
			value.bankID = cv.bankID;
			value.bankName = cv.bankName;
			value.bankTime = new Timestamp(new Date().getTime());
			value.contact = cv.contact;
			value.launcher = 0;
			value.money = money;
			value.note = "日终调整数据";
			value.status = 0;
			value.trader = "market";
			value.type = type;
		}
		return value;
	}

	private Vector<CorrespondValue> getCorrespondValue(String firmid) {
		Vector<CorrespondValue> result = null;
		try {
			result = getProcessor().getCorrespondValue(" and firmid='" + firmid + "'");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private void outfirmMoney(long actionID, String firmid) {
		if ((firmid == null) || (firmid.trim().length() <= 0)) {
			Vector<CapitalValue> v = getCapitalList(actionID);
			if ((v != null) && (v.size() > 0)) {
				firmid = ((CapitalValue) v.get(0)).firmID;
			}
		}
		if ((firmid != null) && (firmid.trim().length() > 0)) {
			try {
				outPrint(getProcessor().getMarketBalance(firmid, null));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private Vector<CapitalValue> getCapitalList(long actionID) {
		Vector<CapitalValue> result = null;
		try {
			result = getProcessor().getCapitalList(" and actionID='" + actionID + "'");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private void outPrint(Object obj) {
		System.out.println(obj.toString());
	}

	private CapitalProcessorRMI getProcessor() {
		if (_processor == null) {
			synchronized (this) {
				if (_processor == null) {
					String processorIP = Tool.getConfig("RmiIpAddress");
					String processorPort = Tool.getConfig("RmiPortNumber");
					String serviceName = Tool.getConfig("RmiServiceName");
					try {
						_processor = (CapitalProcessorRMI) Naming.lookup("//" + processorIP + ":" + processorPort + "/" + serviceName);
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

	private BankDAO getDAO() {
		if (_DAO == null) {
			synchronized (this) {
				if (_DAO == null) {
					try {
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

	public void sumResultInfo(String bankID, Date date) {
		try {
			outPrint(getProcessor().sumResultInfo(bankID, null, date));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Vector<CompareResult> getBankNoInfo(String bankID, Date tradeDate) {
		Vector<CompareResult> result = null;
		Connection conn = null;
		try {
			conn = getDAO().getConnection();
			result = getDAO().getBankNoInfo(bankID, tradeDate, conn, null);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getDAO().closeStatement(null, null, conn);
		}
		return result;
	}

	private Vector<CompareResult> getMarketNoInfo(String bankID, Date tradeDate) {
		Vector<CompareResult> result = null;
		Connection conn = null;
		try {
			conn = getDAO().getConnection();
			result = getDAO().getMarketNoInfo(bankID, tradeDate, conn, null);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getDAO().closeStatement(null, null, conn);
		}
		return result;
	}

	public static void main(String[] args) {
		Test test = new Test();

		Vector<CompareResult> result = test.getdefaultInfo("01", Tool.strToDate("2011-06-10"));
		if (result != null) {
			for (CompareResult cr : result) {
				System.out.println(cr.toString());
			}
		}
	}
}
