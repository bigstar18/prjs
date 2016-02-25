package gnnt.trade.bank;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;

import gnnt.bank.adapter.rmi.BankAdapterRMI;
import gnnt.trade.bank.dao.BankDAO;
import gnnt.trade.bank.dao.BankDAOFactory;
import gnnt.trade.bank.data.ExchangeData;
import gnnt.trade.bank.data.FileThread;
import gnnt.trade.bank.data.ccb.vo.CCBConstant;
import gnnt.trade.bank.util.Arith;
import gnnt.trade.bank.util.Encryption;
import gnnt.trade.bank.util.ErrorCode;
import gnnt.trade.bank.util.StartupRmiserver;
import gnnt.trade.bank.util.Tool;
import gnnt.trade.bank.vo.BankValue;
import gnnt.trade.bank.vo.CapitalCompare;
import gnnt.trade.bank.vo.CapitalValue;
import gnnt.trade.bank.vo.ChargeAgainstValue;
import gnnt.trade.bank.vo.CompareResult;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.DicValue;
import gnnt.trade.bank.vo.FirmAuditValue;
import gnnt.trade.bank.vo.FirmBalanceValue;
import gnnt.trade.bank.vo.FirmBankMsg;
import gnnt.trade.bank.vo.FirmMessageVo;
import gnnt.trade.bank.vo.FirmValue;
import gnnt.trade.bank.vo.InMoneyMarket;
import gnnt.trade.bank.vo.InMoneyVO;
import gnnt.trade.bank.vo.InterfaceLog;
import gnnt.trade.bank.vo.LogEndmsg;
import gnnt.trade.bank.vo.MoneyInfoValue;
import gnnt.trade.bank.vo.OutMoneyMarket;
import gnnt.trade.bank.vo.OutMoneyVO;
import gnnt.trade.bank.vo.ReturnValue;
import gnnt.trade.bank.vo.TransferInfoValue;
import gnnt.trade.bank.vo.bankdz.BankQSVO;
import gnnt.trade.bank.vo.bankdz.jh.sent.CCBQSResult;

public class CapitalProcessor {
	protected static DataProcess dataProcess;
	private static boolean ISLOADED = false;
	private static Hashtable<String, String> ADAPTERLIST;
	private static BankDAO DAO;

	public CapitalProcessor() {
		try {
			DAO = BankDAOFactory.getDAO();
			if (!ISLOADED) {
				ErrorCode errorCode = new ErrorCode();
				errorCode.load();
				new FileThread().start();
				dataProcess = new DataProcess();
				if (loadAdapter() == 0) {
					ISLOADED = true;
				} else {
					log("初始化处理器失败：加载适配器对象错误");
				}
			}
		} catch (Exception e) {
			log("初始化处理器失败：" + Tool.getExceptionTrace(e));
		}
	}

	public void log(String content) {
		Logger plog = Logger.getLogger("Processorlog");
		plog.debug(content);
	}

	private int loadAdapter() {
		int result = 0;
		try {
			Vector<BankValue> bankList = DAO.getBankList("");
			log("开始加载适配器远程访问对象url");
			for (int i = 0; i < bankList.size(); i++) {
				BankValue bVal = (BankValue) bankList.get(i);
				String abClassName = bVal.adapterClassname;
				addAdapter(bVal.bankID, abClassName);
				log(bVal.bankName + "[" + bVal.bankID + "][" + abClassName + "]适配器远程访问对象url加载成功");
			}
			log("结束加载适配器远程访问对象url");
		} catch (Exception e) {
			result = -1;
			log("加载适配器远程访问对象url异常：" + Tool.getExceptionTrace(e));
		}
		return result;
	}

	private void addAdapter(String bankID, String adapterClassname) {
		if (ADAPTERLIST == null) {
			ADAPTERLIST = new Hashtable();
		}
		ADAPTERLIST.put(bankID, adapterClassname);
	}

	public BankAdapterRMI getAdapter(String bankID) {
		log("取得适配器对象 bankID[" + bankID + "]");
		BankAdapterRMI bankadapter = null;
		try {
			String remoteUrl = (String) ADAPTERLIST.get(bankID);
			log("bankID[" + bankID + "]地址[" + remoteUrl + "]");
			bankadapter = (BankAdapterRMI) Naming.lookup(remoteUrl);
		} catch (MalformedURLException e) {
			log("MalformedURLException:" + Tool.getExceptionTrace(e));
		} catch (RemoteException e) {
			log("RemoteException:" + Tool.getExceptionTrace(e));
		} catch (NotBoundException e) {
			log("NotBoundException:" + Tool.getExceptionTrace(e));
		} catch (Exception e) {
			log("Exception" + Tool.getExceptionTrace(e));
		}
		log("返回银行适配器对象信息：" + bankadapter);
		return bankadapter;
	}

	public TransferInfoValue getPayInfo(String bankID, String firmID, int type, Connection conn) throws SQLException {
		log("取得付款方信息bankID[" + bankID + "]firmID[" + firmID + "]type[" + type + "]时间：" + Tool.getNowStr());
		TransferInfoValue result = new TransferInfoValue();
		String bankAccount = "";
		try {
			Vector<CorrespondValue> cList = DAO.getCorrespondList(" and bankid='" + bankID + "' and firmid='" + firmID + "' and isOpen=" + 1, conn);
			if (cList.size() > 0) {
				bankAccount = ((CorrespondValue) cList.get(0)).account;
			}
			BankValue bv = DAO.getBank(bankID, conn);
			result.headOffice = bv.bankName;
			if (type == 0) {
				CorrespondValue val = DAO.getCorrespond(bankID, firmID, bankAccount, conn);
				result.account = val.account;
				result.bankName = val.bankName;
				result.city = val.bankCity;
				result.email = val.email;
				result.mobile = val.mobile;
				result.name = val.accountName;
				result.province = val.bankProvince;
				result.account1 = val.account1;
				result.cardType = val.cardType;
				result.card = val.card;
				result.accountName = val.accountName;
			} else if (type == 1) {
				Vector<DicValue> list = DAO.getDicList(" and type=2 and bankid='" + bankID + "'", conn);
				for (int i = 0; i < list.size(); i++) {
					DicValue val = (DicValue) list.get(i);
					if (val.name.equals("Email")) {
						result.email = val.value;
					} else if (val.name.equals("mobile")) {
						result.mobile = val.value;
					} else if (val.name.equals("bankCity")) {
						result.city = val.value;
					} else if (val.name.equals("bankProvince")) {
						result.province = val.value;
					} else if (val.name.equals("bankName")) {
						result.bankName = val.value;
					} else if (val.name.equals("accountName")) {
						result.name = val.value;
					} else if (val.name.equals("marketAccount")) {
						result.account = val.value;
					}
				}
			}
		} catch (SQLException e) {
			throw e;
		}
		return result;
	}

	public TransferInfoValue getReceiveInfo(String bankID, String firmID, int type, Connection conn) throws SQLException {
		log("取得收款方信息 getReceiveInfo bankID[" + bankID + "]firmID[" + firmID + "]type[" + type + "]");
		TransferInfoValue result = new TransferInfoValue();
		String bankAccount = "";
		try {
			Vector<CorrespondValue> cList = DAO.getCorrespondList(" and bankid='" + bankID + "' and firmid='" + firmID + "' and isOpen=" + 1, conn);
			if (cList.size() > 0) {
				bankAccount = ((CorrespondValue) cList.get(0)).account;
			}
			BankValue bv = DAO.getBank(bankID, conn);
			result.headOffice = bv.bankName;
			if (type == 0) {
				Vector<DicValue> list = DAO.getDicList(" and type=2 and bankid='" + bankID + "'", conn);
				for (int i = 0; i < list.size(); i++) {
					DicValue val = (DicValue) list.get(i);
					if (val.name.equals("Email")) {
						result.email = val.value;
					} else if (val.name.equals("mobile")) {
						result.mobile = val.value;
					} else if (val.name.equals("bankCity")) {
						result.city = val.value;
					} else if (val.name.equals("bankProvince")) {
						result.province = val.value;
					} else if (val.name.equals("bankName")) {
						result.bankName = val.value;
					} else if (val.name.equals("accountName")) {
						result.name = val.value;
					} else if (val.name.equals("marketAccount")) {
						result.account = val.value;
					}
				}
			} else if (type == 1) {
				CorrespondValue val = DAO.getCorrespond(bankID, firmID, bankAccount, conn);
				result.account = val.account;
				result.bankName = val.bankName;
				result.city = val.bankCity;
				result.email = val.email;
				result.mobile = val.mobile;
				result.name = val.accountName;
				result.province = val.bankProvince;
				result.account1 = val.account1;
				result.cardType = val.cardType;
				result.card = val.card;
				result.account1Name = val.accountName1;
				result.accountName = val.accountName;
				result.isCrossline = val.isCrossline;
				result.OpenBankCode = val.OpenBankCode;
			}
		} catch (SQLException e) {
			throw e;
		}
		return result;
	}

	public long chenckCorrespondValue(CorrespondValue cv) {
		log("银行开户销户变更账户检验参数，" + (cv == null ? "传入参数为空，" : new StringBuilder("\n").append(cv.toString()).append("\n").toString()) + "时间："
				+ Tool.getNowStr());
		long checkResult = -920012L;
		String bankid = cv.bankID;
		String contact = cv.firmID;
		String account = cv.account;
		if ((cv.contact != null) && (cv.contact.trim().length() > 0)) {
			contact = cv.contact;
		}
		if ((bankid == null) || (bankid.trim().length() <= 0)) {
			checkResult = -920011L;
			return checkResult;
		}
		if ((contact == null) || (contact.trim().length() <= 0)) {
			checkResult = -920010L;
			return checkResult;
		}
		if ((account == null) || (account.trim().length() <= 0)) {
			checkResult = -920012L;
			return checkResult;
		}
		try {
			List<CorrespondValue> cvresult = DAO.getCorrespondList(" and bankId='" + bankid + "' and CONTACT='" + contact + "' and (account like '%"
					+ account.trim() + "%' or account='" + Tool.getConfig("DefaultAccount") + "')");
			if ((cvresult == null) || (cvresult.size() <= 0)) {
				return -920013L;
			}
			if (cvresult.size() > 1) {
				return -920014L;
			}
			checkResult = 0L;
		} catch (SQLException e) {
			checkResult = -920015L;
			log("交易账号代码与银行账号对应SQLException," + Tool.getExceptionTrace(e));
		} catch (Exception e) {
			checkResult = -920016L;
			log("交易账号代码与银行账号对应Exception," + Tool.getExceptionTrace(e));
		}
		return checkResult;
	}

	public CorrespondValue getCorrespond(String bankID, String firmID, String contact, String account) throws SQLException, ClassNotFoundException {
		log("根据交易账号代码和银行账号等，查询交易账号绑定信息 getCorrespond bankID[" + bankID + "]firmID[" + contact + "]account[" + account + "]");
		CorrespondValue result = null;
		try {
			String filter = " and bankID='" + bankID.trim() + "' and CONTACT='" + contact.trim() + "' ";
			if ((firmID != null) && (firmID.trim().length() > 0)) {
				filter = filter + " and firmID='" + firmID.trim() + "'";
			}
			if ((account != null) && (account.trim().length() > 0)) {
				filter = filter + " and account='" + account.trim() + "'";
			}
			Vector<CorrespondValue> cvs = DAO.getCorrespondList(filter);
			if ((cvs == null) || (cvs.size() <= 0)) {
				log("根据传入信息filter[" + filter + "]未查到信息");
			} else if (cvs.size() != 1) {
				log("根据传入信息filter[" + filter + "]查到信息不为一");
			} else {
				result = (CorrespondValue) cvs.get(0);
			}
		} catch (SQLException e) {
			throw e;
		} catch (ClassNotFoundException e) {
			throw e;
		}
		return result;
	}

	public ReturnValue checkArgs(Connection conn, double money, String firmID, String bankID) throws SQLException {
		log("判断交易账号当前资金是否够出金 checkArgs money[" + money + "]firmID[" + firmID + "]");
		ReturnValue result = new ReturnValue();
		result.result = -20039L;
		double realFunds = dataProcess.getRealFunds(bankID, firmID, 1, conn);
		result.remark = ("可出金额不足，可用余额[" + realFunds + "]出金金额[" + money + "]");
		log("交易账号[" + firmID + "]可用余额[" + realFunds + "]出金金额[" + money + "]");
		if (realFunds >= money) {
			result.result = 0L;
			result.remark = "";
		}
		return result;
	}

	public long bankFrozenFuns(String firmID, String contact, String bankID, String account, double money, Connection conn) throws SQLException {
		log("银行接口资金冻结 bankFrozenFuns firmID[" + firmID + "]bankID[" + bankID + "]account[" + account + "]");
		long result = -920012L;
		if ((firmID == null) || (firmID.trim().length() <= 0) || (bankID == null) || (bankID.trim().length() <= 0)) {
			log("冻结(解冻)资金，信息不完整 firmID=" + firmID + " bankID=" + bankID);
			result = -20021L;
			return result;
		}
		String filter = " and firmID='" + firmID.trim() + "' and bankID='" + bankID.trim() + "' ";
		if ((account != null) && (account.trim().length() > 0)) {
			filter = filter + " and account='" + account.trim() + "'";
		} else if ((contact != null) && (contact.trim().length() > 0)) {
			filter = filter + " and contact='" + contact.trim() + "'";
		}
		String filter2 = filter + " for update";
		try {
			Vector<CorrespondValue> vector = DAO.getCorrespondList(filter2, conn);
			if ((vector != null) && (vector.size() <= 0)) {
				log("冻结(解冻)资金，根据[" + filter2 + "]未查到客户绑定信息");
				return -920013L;
			}
			CorrespondValue cv = (CorrespondValue) vector.get(0);
			if (cv.frozenFuns + money < 0.0D) {
				result = -20029L;
				log("交易账号冻结资金不足以释放当前资金：冻结资金[" + cv.frozenFuns + "]当前资金[" + money + "]");
				return result;
			}
			result = DAO.modBankFrozenFuns(filter, money, conn);
		} catch (SQLException e) {
			throw e;
		}
		return result;
	}

	protected long handleOUtMoney(Connection conn, String bankID, String trader, double money, String firmID, String contact, String account,
			String funID, int type) throws SQLException {
		log("检查交易账号是否符合出金条件,符合条件进行出金处理,记录流水 handleOUtMoney bankID[" + bankID + "]trader[" + trader + "]money[" + money + "]firmID[" + firmID
				+ "]contact[" + contact + "]account[" + account + "]funID[" + funID + "]type[" + type + "]");
		long result = -20042L;
		try {
			conn.setAutoCommit(false);
			long l1;
			if ((funID != null) && (funID.trim().length() > 0)) {
				Vector<CapitalValue> cv = DAO.getCapitalInfoList(
						" and bankID='" + bankID + "' and createtime>=trunc(sysdate) and createtime<trunc(sysdate)+1 and funID='" + funID + "'",
						conn);
				if ((cv != null) && (cv.size() > 0)) {
					result = -20042L;
					log("银行出金，银行流水号[" + funID + "]已经存在");
					l1 = result;
					return l1;
				}
			}
			result = checkArgs(conn, money, firmID, bankID).result;
			if (result < 0L) {
				l1 = result;
				return l1;
			}
			result = DAO.getActionID();
			CapitalValue cVal = new CapitalValue();
			cVal.trader = trader;
			cVal.funID = (funID == null ? "" : funID);
			cVal.actionID = result;
			cVal.firmID = firmID;
			cVal.contact = contact;
			cVal.bankID = bankID;
			cVal.type = 1;
			cVal.money = money;
			cVal.status = 7;
			if (type == 0) {
				cVal.launcher = 0;
				cVal.note = "市场端出金";
			} else {
				cVal.launcher = 1;
				cVal.note = "银行端出金";
			}
			DAO.addCapitalInfo(cVal, conn);
			dataProcess.updateFrozenFunds(firmID, money, conn);
			bankFrozenFuns(firmID, contact, bankID, account, money, conn);
			conn.commit();
		} catch (SQLException e) {
			conn.rollback();
			throw e;
		} catch (Exception e) {
			conn.rollback();
			result = -20038L;
			log("出金Exception,数据回滚" + Tool.getExceptionTrace(e));
		} finally {
			conn.setAutoCommit(true);
		}
		return result;
	}

	public ReturnValue outMoneyAutoAudit(long actionID) {
		log("出金自动审核 outMoneyAutoAudit actionID[" + actionID + "]");
		ReturnValue result = new ReturnValue();
		Connection conn = null;
		Timestamp curTime = new Timestamp(System.currentTimeMillis());
		CapitalValue capital = null;
		try {
			conn = DAO.getConnection();
			Vector<CapitalValue> list = DAO.getCapitalInfoList(" and actionid=" + actionID, conn);
			for (int i = 0; i < list.size(); i++) {
				CapitalValue val = (CapitalValue) list.get(i);
				if (val.type == 1) {
					capital = val;
				}
			}
			ReturnValue localReturnValue2;
			if (capital == null) {
				result.result = -20011L;
				result.remark = ("没有发现业务流水号为：" + actionID + "的流水");
				log(result.remark);
				localReturnValue2 = result;
				return localReturnValue2;
			}
			try {
				conn.setAutoCommit(false);
				list = DAO.getCapitalInfoList(" and actionid=" + actionID + " and STATUS=" + 7 + " for update", conn);
				if ((list == null) || (list.size() <= 0)) {
					result.result = -20011L;
					result.remark = ("没有发现业务流水号为：" + actionID + "状态为处理中的流水");
					log(result.remark);
					ReturnValue localReturnValue1 = result;

					conn.setAutoCommit(true);

					DAO.closeStatement(null, null, conn);
					return localReturnValue1;
				}
				DAO.modCapitalInfoStatus(capital.iD, result.funID, 2, curTime, conn);
				DAO.modCapitalInfoNote(capital.iD, "处理中", conn);
				DAO.modCapitalInfoTrader(capital.iD, "manual", conn);
				conn.commit();
			} catch (SQLException e) {
				conn.rollback();
				throw e;
			} finally {
				conn.setAutoCommit(true);
			}
			conn.setAutoCommit(true);
			if (result.result < 0L) {
				localReturnValue2 = result;
				return localReturnValue2;
			}
			try {
				if (capital.launcher == 0) {
					BankAdapterRMI bankadapter = getAdapter(capital.bankID);
					if (bankadapter == null) {
						result.result = -920000L;
						result.remark = ("网络异常，处理器无法连接适配器[" + capital.bankID + "]");
						log(result.remark);
					} else {
						Vector<CorrespondValue> csv = DAO
								.getCorrespondList(" and bankid='" + capital.bankID + "' and firmid='" + capital.firmID + "'", conn);
						TransferInfoValue payInfo = getPayInfo(capital.bankID, capital.firmID, 1, conn);
						TransferInfoValue receiveInfo = getReceiveInfo(capital.bankID, capital.firmID, 1, conn);

						receiveInfo.OpenBankCode = ((CorrespondValue) csv.get(0)).OpenBankCode;
						OutMoneyVO outMoneyInfo = new OutMoneyVO(capital.bankID, "", capital.money, capital.contact, payInfo, receiveInfo, actionID,
								capital.funID);
						outMoneyInfo.isCrossline = String.valueOf(((CorrespondValue) csv.get(0)).isCrossline);
						result = bankadapter.outMoneyMarketDone(outMoneyInfo);
					}
				} else {
					result.actionId = capital.actionID;
					result.funID = capital.funID;
				}
				conn.setAutoCommit(false);
				if (result.result == 0L) {
					dataProcess.updateFundsFull(capital.bankID, capital.firmID, actionID + "", capital.money, 1, conn);
					dataProcess.updateFrozenFunds(capital.firmID, -1.0D * capital.money, conn);
					bankFrozenFuns(capital.firmID, capital.contact, capital.bankID, null, -1.0D * capital.money, conn);
					DAO.modCapitalInfoStatus(capital.iD, result.funID, 0, curTime, conn);
					DAO.modCapitalInfoNote(capital.iD, "出金成功", conn);
					log(actionID + "出金成功,银行流水号=" + result.funID);
					result.type = 0;
					result.remark = "出金成功";
				} else if (result.result == 5L) {
					DAO.modCapitalInfoStatus(capital.iD, result.funID, 2, curTime, conn);
					DAO.modCapitalInfoNote(capital.iD, result.remark, conn);
					log(actionID + "银行处理处理中");
					result.type = 2;
				} else if (result.result == -50010L) {
					DAO.modCapitalInfoStatus(capital.iD, result.funID, 5, curTime, conn);
					DAO.modCapitalInfoNote(capital.iD, result.remark, conn);
					log(actionID + "银行无返回");
					result.type = 5;
				} else if (result.result == -920008L) {
					DAO.modCapitalInfoStatus(capital.iD, result.funID, 6, curTime, conn);
					DAO.modCapitalInfoNote(capital.iD, result.remark, conn);
					log(actionID + "银行返回信息异常");
					result.type = 6;
				} else {
					dataProcess.updateFrozenFunds(capital.firmID, -1.0D * capital.money, conn);
					bankFrozenFuns(capital.firmID, capital.contact, capital.bankID, null, -1.0D * capital.money, conn);
					DAO.modCapitalInfoStatus(capital.iD, result.funID, 1, curTime, conn);
					DAO.modCapitalInfoNote(capital.iD, result.remark, conn);
					log(actionID + "银行处理失败,退还全部出金和手续费，错误码=" + result);
					result.type = 1;
				}
				conn.commit();
			} catch (SQLException e) {
				e.printStackTrace();
				conn.rollback();
				result.result = -20013L;
				result.remark = ("处理出金流水[" + actionID + "]数据库异常");
				log(actionID + result.remark + Tool.getExceptionTrace(e));
			} finally {
				conn.setAutoCommit(true);
			}
			if (result.result == -20013L) {
				DAO.modCapitalInfoStatus(capital.iD, capital.funID, 8, curTime, conn);
				DAO.modCapitalInfoNote(capital.iD, "数据库异常", conn);
				result.type = 8;
			}
			result.actionId = capital.actionID;
			result.funID = capital.funID;
		} catch (SQLException e) {
			result.result = -30004L;
			log(actionID + "审核出金SQLException，错误码=" + result + "  " + Tool.getExceptionTrace(e));
		} catch (Exception e) {
			result.result = -30005L;
			log(actionID + "审核出金Exception，错误码=" + result + "  " + Tool.getExceptionTrace(e));
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public long getMktActionID() {
		long result = -168L;
		try {
			result = DAO.getActionID();
		} catch (SQLException e) {
			result = -920015L;
		} catch (Exception e) {
			result = -920016L;
		}
		return result;
	}

	public Vector<BankValue> getBankList(String filter) {
		log("查询银行列表 filter[" + filter + "]");
		Vector<BankValue> result = null;
		try {
			result = DAO.getBankList(filter);
		} catch (SQLException e) {
			log("SQLException " + Tool.getExceptionTrace(e));
		} catch (Exception e) {
			log("Exception " + Tool.getExceptionTrace(e));
		}
		log("查询银行列表，返回：" + (result == null ? "null" : new StringBuilder(String.valueOf(result.size())).append("条").toString()));
		return result;
	}

	public BankValue getBank(String bankID) {
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

	public ReturnValue modBank(BankValue bankValue) {
		ReturnValue result = new ReturnValue();
		if (bankValue == null) {
			result.result = -1L;
			result.remark = "出入银行信息类为 null";
		} else if ((bankValue.bankID == null) || (bankValue.bankID.trim().length() <= 0)) {
			result.result = -1L;
			result.remark = "传入的银行信息中不包含银行代码";
		} else {
			try {
				result.result = DAO.modBank(bankValue);
				result.remark = ("修改了[" + bankValue.bankID + "]银行数据");
			} catch (SQLException e) {
				log(Tool.getExceptionTrace(e));
				result.result = -920015L;
				result.remark = "数据库异常";
			} catch (Exception e) {
				log(Tool.getExceptionTrace(e));
				result.result = -920016L;
				result.remark = "系统异常";
			}
		}
		return result;
	}

	public ReturnValue changeBankTradeType(Vector<String> bankIDs, int type, int value) {
		ReturnValue result = new ReturnValue();
		try {
			String str = value == 0 ? "可用" : "禁用";
			if (type == 0) {
				result.result = DAO.chBankValid(bankIDs, value);
				result.remark = ("修改银行状态为[" + str + "]成功");
			} else if (type == 1) {
				result.result = DAO.chBankInMoney(bankIDs, value);
				result.remark = ("修改银行入金状态为[" + str + "]成功");
			} else if (type == 2) {
				result.result = DAO.chBankOutMoney(bankIDs, value);
				result.remark = ("修改银行出金状态为[" + str + "]成功");
			} else {
				result.result = -920016L;
				result.remark = "系统无法找到需要修改的信息";
			}
		} catch (SQLException e) {
			result.result = -920015L;
			result.remark = "修改银行状态，数据库异常";
			log(Tool.getExceptionTrace(e));
		} catch (Exception e) {
			result.result = -920016L;
			result.remark = "修改银行状态，系统异常";
			log(Tool.getExceptionTrace(e));
		}
		return result;
	}

	public Hashtable<String, String> getBankInfo(String bankID) {
		log("根据银行ID获取市场银行信息 getBankInfo bankID[" + bankID + "]");
		Hashtable<String, String> hashTable = new Hashtable();
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			Vector<DicValue> list = DAO.getDicList(" and type=2 and bankid='" + bankID + "'", conn);
			for (int i = 0; i < list.size(); i++) {
				DicValue val = (DicValue) list.get(i);
				if (val.value == null) {
					val.value = "";
				}
				hashTable.put(val.name, val.value);
			}
		} catch (Exception e) {
			log(Tool.getExceptionTrace(e));
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return hashTable;
	}

	public Map<String, CorrespondValue> getCorrespondValue(Vector<String> contacts, String bankID) {
		Map<String, CorrespondValue> result = new HashMap();
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			if ((contacts != null) && (contacts.size() > 0)) {
				for (String contact : contacts) {
					Vector<CorrespondValue> correspondList = DAO.getCorrespondList(" and contact='" + contact + "' and bankid='" + bankID + "' ",
							conn);
					if ((correspondList != null) && (correspondList.size() > 0)) {
						CorrespondValue cv = (CorrespondValue) correspondList.get(0);
						result.put(contact, cv);
					}
				}
			}
		} catch (Exception e) {
			log(Tool.getExceptionTrace(e));
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public Vector<CorrespondValue> getCorrespondValue(String filter) {
		log("获取交易账号银行对应关 getCorrespondValue filter[" + filter + "]");
		Connection conn = null;
		Vector<CorrespondValue> correspondList = null;
		try {
			conn = DAO.getConnection();
			correspondList = DAO.getCorrespondList(filter, conn);
		} catch (Exception e) {
			log(Tool.getExceptionTrace(e));
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return correspondList;
	}

	public Vector<FirmValue> getFirmUser(String filter) {
		log("查询交易账号列表 filter[" + filter + "]");
		Vector<FirmValue> result = null;
		try {
			result = DAO.getFirmList(filter);
		} catch (SQLException e) {
			log("SQLException " + Tool.getExceptionTrace(e));
		} catch (Exception e) {
			log("Exception " + Tool.getExceptionTrace(e));
		}
		return result;
	}

	public long isPassword(String firmID, String password) {
		long result = -1L;
		String newpassword = Encryption.encryption(firmID, password, null);
		try {
			FirmValue fv = DAO.getFirm(firmID);
			if (fv != null) {
				String oldpassword = fv.password;
				if ((oldpassword != null) && (oldpassword.trim().length() > 0)) {
					if (newpassword.equals(oldpassword)) {
						result = 0L;
					} else {
						log("交易账号[" + firmID + "]输入的密码错误");
					}
				} else {
					result = 1L;
					log("交易账号[" + firmID + "]尚未初始化密码");
				}
			} else {
				result = -2L;
				log(Tool.fmtTime(new java.util.Date()) + "查询不到交易账号[" + firmID + "]");
			}
		} catch (SQLException e) {
			log(Tool.getExceptionTrace(e));
		} catch (Exception e) {
			log(Tool.getExceptionTrace(e));
		}
		return result;
	}

	public long modPwd(String firmID, String password, String chpassword) {
		long result = -1L;
		String newpassword = Encryption.encryption(firmID, password, null);
		try {
			FirmValue fv = DAO.getFirm(firmID);
			if (fv != null) {
				String oldpassword = fv.password;
				if ((oldpassword == null) || (oldpassword.trim().length() <= 0) || (newpassword.equals(oldpassword))) {
					fv.password = Encryption.encryption(firmID, chpassword, null);
					int n = DAO.modFirmPassword(fv);
					if (n >= 0) {
						result = 0L;
					} else {
						log(Tool.fmtTime(new java.util.Date()) + "修改交易账号密码，返回数值小于0");
					}
				} else {
					result = -1L;
					log(Tool.fmtTime(new java.util.Date()) + "交易账号[" + fv.firmID + "]修改密码，原密码错误。oldpassword[" + oldpassword + "]nowpassword["
							+ newpassword + "]");
				}
			} else {
				result = -2L;
				log(Tool.fmtTime(new java.util.Date()) + "未查询到相应交易账号[" + firmID + "]");
			}
		} catch (SQLException e) {
			log(Tool.fmtTime(new java.util.Date()) + "修改密码数据库异常，输入的密码：" + newpassword);
			log(Tool.getExceptionTrace(e));
		} catch (Exception e) {
			log(Tool.fmtTime(new java.util.Date()) + "修改密码系统异常，输入的密码：" + newpassword);
			log(Tool.getExceptionTrace(e));
		}
		return result;
	}

	public ReturnValue initializeFrimPwd(String firmID, String password) {
		ReturnValue result = new ReturnValue();
		try {
			String newpassword = Encryption.encryption(firmID, password, null);
			FirmValue fv = DAO.getFirm(firmID);
			if (fv == null) {
				result.result = -920010L;
				result.remark = ("修改交易账号" + firmID + "密码，通过交易账号编号找不到交易账号信息");
			} else {
				fv.password = newpassword;
				int n = DAO.modFirmPassword(fv);
				if (n >= 0) {
					result.result = 0L;
					result.remark = ("修改交易账号" + firmID + "密码成功");
				} else {
					result.result = -920016L;
					result.remark = ("修改交易账号" + firmID + "密码失败");
				}
			}
		} catch (SQLException e) {
			result.result = -920015L;
			result.remark = ("修改交易账号" + firmID + "密码，数据库异常");
			log(result.remark + Tool.getExceptionTrace(e));
		} catch (Exception e) {
			result.result = -920016L;
			result.remark = ("修改交易账号0" + firmID + "密码，系统异常");
			log(result.remark + Tool.getExceptionTrace(e));
		}
		return result;
	}

	public ReturnValue initializeFrimPwd(String firmID) {
		ReturnValue result = new ReturnValue();
		try {
			FirmValue fv = DAO.getFirm(firmID);
			if (fv == null) {
				result.result = -920010L;
				result.remark = ("修改交易账号" + firmID + "密码，通过交易账号编号找不到交易账号信息");
			} else {
				fv.password = "";
				int n = DAO.modFirmPassword(fv);
				if (n >= 0) {
					result.result = 0L;
					result.remark = ("修改交易账号" + firmID + "密码成功");
				} else {
					result.result = -920016L;
					result.remark = ("修改交易账号" + firmID + "密码失败");
				}
			}
		} catch (SQLException e) {
			result.result = -920015L;
			result.remark = ("修改交易账号" + firmID + "密码，数据库异常");
			log(result.remark + Tool.getExceptionTrace(e));
		} catch (Exception e) {
			result.result = -920016L;
			result.remark = ("修改交易账号0" + firmID + "密码，系统异常");
			log(result.remark + Tool.getExceptionTrace(e));
		}
		return result;
	}

	public ReturnValue modCorrespondStatus(CorrespondValue cav) {
		ReturnValue result = new ReturnValue();
		if (cav == null) {
			result.result = -1L;
			result.remark = "信息为空";
		} else if (cav.bankID == null) {
			result.result = -1L;
			result.remark = "银行编号为空";
		} else if ((cav.firmID == null) && (cav.contact == null)) {
			result.result = -1L;
			result.remark = "交易账号代码和绑定号至少选其一";
		} else {
			try {
				result.result = DAO.modCorrespondStatus(cav);
				result.remark = ("修改状态为[" + result.result + "]");
			} catch (SQLException e) {
				result.result = -1L;
				result.remark = "数据库异常";
				log(Tool.getExceptionTrace(e));
			} catch (Exception e) {
				result.result = -1L;
				result.remark = "系统异常";
				log(Tool.getExceptionTrace(e));
			}
		}
		return result;
	}

	public long rgstAccount(CorrespondValue correspondValue) {
		log("银行账号注册 rgstAccount correspondValue:"
				+ (correspondValue == null ? "为 null" : new StringBuilder("\n").append(correspondValue.toString()).append("\n").toString()));
		long result = ifbankTrade(correspondValue.bankID, correspondValue.firmID, correspondValue.contact, 3, 0).result;
		if (result < 0L) {
			return result;
		}
		String defaultAccount = Tool.getConfig("DefaultAccount");
		Connection conn = null;
		if ((correspondValue.bankID == null) || (correspondValue.bankID.length() == 0) || (correspondValue.firmID == null)
				|| (correspondValue.firmID.length() == 0) || (correspondValue.account == null) || (correspondValue.account.length() == 0)) {
			return -40001L;
		}
		try {
			conn = DAO.getConnection();
			if (DAO.getFirm(correspondValue.firmID, conn) == null) {
				result = -40002L;
				log("银行账号注册，交易账号不存在，错误码=" + result);
			} else if (DAO.getBank(correspondValue.bankID, conn) == null) {
				result = -40003L;
				log("银行账号注册，银行不存在，错误码=" + result);
			} else if (DAO.getCorrespondList(" and bankID='" + correspondValue.bankID + "' and firmID='" + correspondValue.firmID + "' and (account='"
					+ correspondValue.account + "' or account='" + defaultAccount + "') and isopen=1", conn).size() > 0) {
				result = -40004L;
				log("银行账号注册，账号已注册，错误码=" + result);
			} else if (DAO.getCorrespondList(" and bankID='" + correspondValue.bankID + "' and firmID='" + correspondValue.firmID + "' and (account='"
					+ correspondValue.account + "' or account='" + defaultAccount + "')", conn).size() > 0) {
				BankAdapterRMI bankadapter = getAdapter(correspondValue.bankID);
				ReturnValue returnValue = bankadapter.rgstAccountQuery(correspondValue);
				if ((returnValue == null) || (returnValue.result == 0L)) {
					correspondValue.isOpen = 1;
					try {
						conn.setAutoCommit(false);
						dataProcess.changeFirmIsOpen(correspondValue.firmID, 1, correspondValue.bankID, conn);
						DAO.openCorrespond(correspondValue, conn);
						conn.commit();
					} catch (SQLException e) {
						conn.rollback();
						throw e;
					} finally {
						conn.setAutoCommit(true);
					}
					log("满足注册条件，修改为签约状态" + result);
				} else {
					log(returnValue.remark);
					result = -40005L;
					log("银行账号注册，银行签约失败，错误码=" + result);
				}
			} else if (DAO.getCorrespondList(" and bankID='" + correspondValue.bankID + "' and firmID='" + correspondValue.firmID + "' and account='"
					+ correspondValue.account + "'", conn).size() <= 0) {
				log("添加交易账号");
				DAO.addCorrespond(correspondValue, conn);
			} else {
				log("账号已存在");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			result = -40006L;
			log("银行账号注册SQLException，错误码=" + result + "  " + Tool.getExceptionTrace(e));
		} catch (Exception e) {
			e.printStackTrace();
			result = -40007L;
			log("银行账号注册Exception，错误码=" + result + "  " + Tool.getExceptionTrace(e));
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public ReturnValue openAccount(CorrespondValue cv) {
		log("银行开户方法 openAccount cv:" + (cv == null ? "为 null" : new StringBuilder("\n").append(cv.toString()).append("\n").toString()));
		ReturnValue rv = ifbankTrade(cv.bankID, cv.firmID, cv.contact, 3, 1);
		if (rv.result < 0L) {
			return rv;
		}
		synchronized (cv.firmID) {
			Connection conn = null;
			try {
				if ((cv.contact == null) || (cv.contact.trim().length() <= 0)) {
					cv.contact = cv.firmID;
				}
				List<CorrespondValue> cvresult = DAO.getCorrespondList(" and bankID='" + cv.bankID + "' and CONTACT='" + cv.contact + "'");
				ReturnValue localReturnValue4;
				if ((cvresult == null) || (cvresult.size() <= 0)) {
					cvresult = DAO.getCorrespondList(" and trim(contact)='" + cv.contact.trim() + "' and trim(bankID) is null ");
					if ((cvresult != null) && (cvresult.size() > 0)) {
						CorrespondValue corr = (CorrespondValue) cvresult.get(0);
						corr.account = cv.account;
						corr.isOpen = 1;
						corr.status = 0;
						corr.accountName = cv.accountName;
						corr.card = cv.card;
						corr.cardType = cv.cardType;
						corr.account1 = cv.account1;
						corr.bankID = cv.bankID;
						rv.actionId = getMktActionID();
						conn = DAO.getConnection();
						try {
							conn.setAutoCommit(false);
							rv = dataProcess.changeFirmIsOpen(corr.firmID, 1, corr.bankID, conn);
							if (rv.result < 0L) {
								ReturnValue localReturnValue1 = rv;

								conn.setAutoCommit(true);

								DAO.closeStatement(null, null, conn);
								return localReturnValue1;
							}
							DAO.openCorrespond(corr, conn);
							conn.commit();
						} catch (SQLException er) {
							conn.rollback();
							throw er;
						} finally {
							conn.setAutoCommit(true);
						}
						conn.setAutoCommit(true);
					} else {
						Vector<FirmValue> vfv = DAO.getFirmList(" and CONTACT='" + cv.contact + "'");
						if ((vfv == null) || (vfv.size() != 1)) {
							rv.result = -40002L;
							rv.remark = "通过签约号查询客户信息异常";
							localReturnValue4 = rv;

							DAO.closeStatement(null, null, conn);
							return localReturnValue4;
						}
						FirmValue fv = (FirmValue) vfv.get(0);
						if ((cv.card == null) || (!cv.card.equals(fv.card))) {
							rv.result = -20020L;
							rv.remark = "客户证件号码错误";
							localReturnValue4 = rv;

							DAO.closeStatement(null, null, conn);
							return localReturnValue4;
						}
						cv.firmID = fv.firmID;
						cv.contact = fv.contact;
						cv.frozenFuns = 0.0D;
						cv.isOpen = 1;
						cv.status = 0;
						cv.opentime = new java.util.Date();
						rv.actionId = getMktActionID();
						conn = DAO.getConnection();
						try {
							conn.setAutoCommit(false);
							rv = dataProcess.changeFirmIsOpen(fv.firmID, 1, cv.bankID, conn);
							if (rv.result < 0L) {
								ReturnValue localReturnValue3 = rv;

								conn.setAutoCommit(true);

								DAO.closeStatement(null, null, conn);
								return localReturnValue3;
							}
							DAO.addCorrespond(cv, conn);
							conn.commit();
						} catch (SQLException er) {
							conn.rollback();
							throw er;
						} finally {
							conn.setAutoCommit(true);
						}
						conn.setAutoCommit(true);
					}
				} else {
					if (cvresult.size() != 1) {
						rv.result = -920014L;
						rv.remark = "通过银行编号、签约号查询出信息重复";
						localReturnValue4 = rv;

						DAO.closeStatement(null, null, conn);
						return localReturnValue4;
					}
					CorrespondValue cv2 = (CorrespondValue) cvresult.get(0);
					if ((cv.card == null) || (!cv.card.equals(cv2.card))) {
						rv.result = -20020L;
						rv.remark = "客户证件号码错误";
						localReturnValue4 = rv;

						DAO.closeStatement(null, null, conn);
						return localReturnValue4;
					}
					rv.actionId = getMktActionID();
					conn = DAO.getConnection();
					try {
						conn.setAutoCommit(false);
						rv = dataProcess.changeFirmIsOpen(cv2.firmID, 1, cv2.bankID, conn);
						if (rv.result < 0L) {
							ReturnValue localReturnValue2 = rv;

							conn.setAutoCommit(true);

							DAO.closeStatement(null, null, conn);
							return localReturnValue2;
						}
						cv.contact = cv2.contact;
						cv.id = cv2.id;
						DAO.openCorrespond(cv, conn);
						conn.commit();
					} catch (SQLException er) {
						conn.rollback();
						throw er;
					} finally {
						conn.setAutoCommit(true);
					}
					conn.setAutoCommit(true);
				}
			} catch (SQLException e) {
				rv.result = -40006L;
				rv.remark = "签约时数据库异常";
				log("银行开户 交易账号代码与银行账号对应SQLException," + Tool.getExceptionTrace(e));
			} catch (Exception e) {
				rv.result = -40007L;
				rv.remark = "签约时系统异常";
				log("银行开户 交易账号代码与银行账号对应SQLException," + Tool.getExceptionTrace(e));
			} finally {
				DAO.closeStatement(null, null, conn);
			}
		}
		if (rv.result > 0L) {
			rv.result = 0L;
		}
		return rv;
	}

	public ReturnValue openAccountMarket(CorrespondValue cv) {
		log("市场开户方法 openAccountMarket cv:" + (cv == null ? "为 null" : new StringBuilder("\n").append(cv.toString()).append("\n").toString()));
		ReturnValue rv = ifbankTrade(cv.bankID, cv.firmID, cv.contact, 3, 0);
		if (rv.result < 0L) {
			return rv;
		}
		Connection conn = null;
		synchronized (cv.contact) {
			try {
				List<CorrespondValue> cvresult = DAO.getCorrespondList(" and bankID='" + cv.bankID + "' and contact='" + cv.contact.trim() + "'");
				ReturnValue localReturnValue1;
				ReturnValue localReturnValue2;
				if ((cvresult == null) || (cvresult.size() <= 0)) {
					cvresult = DAO.getCorrespondList(" and trim(contact)='" + cv.contact.trim() + "' and trim(bankID) is null ");
					if ((cvresult != null) && (cvresult.size() > 0)) {
						CorrespondValue corr = (CorrespondValue) cvresult.get(0);
						corr.account = cv.account;
						corr.isOpen = 1;
						corr.status = 0;
						corr.accountName = cv.accountName;
						corr.bankCardPassword = cv.bankCardPassword;
						corr.card = cv.card;
						corr.cardType = cv.cardType;
						String firmID = cv.firmID;
						corr.firmID = cv.contact;
						corr.bankID = cv.bankID;
						corr.frozenFuns = cv.frozenFuns;
						corr.actionID = cv.actionID;
						corr.signInfo = cv.signInfo;
						conn = DAO.getConnection();
						try {
							conn.setAutoCommit(false);
							BankAdapterRMI bankadapter = getAdapter(cv.bankID);
							if (bankadapter == null) {
								rv.result = -920000L;
								rv.remark = "获取连接银行适配器失败";
								localReturnValue1 = rv;

								conn.setAutoCommit(true);

								DAO.closeStatement(null, null, conn);
								return localReturnValue1;
							}
							if ((Tool.getConfig("YQYbankID") == null) || (Tool.getConfig("YQYbankID").indexOf(cv.bankID) < 0)) {
								rv = dataProcess.changeFirmIsOpen(firmID, 1, corr.bankID, conn);
								if (rv.result < 0L) {
									conn.rollback();
									localReturnValue1 = rv;

									conn.setAutoCommit(true);

									DAO.closeStatement(null, null, conn);
									return localReturnValue1;
								}
							} else {
								corr.falg = 3;
							}
							rv = bankadapter.rgstAccountQuery(corr);
							if (rv.result == 0L) {
								corr.firmID = firmID;
								corr.account1 = rv.account1;
								rv.result = DAO.openCorrespond(corr, conn);
								conn.commit();
							} else if (rv.result == 5L) {
								corr.firmID = firmID;
								corr.account1 = rv.account1;
								corr.isOpen = 2;
								rv.result = -9L;
							} else if (rv.result < 0L) {
								conn.rollback();
								localReturnValue1 = rv;

								conn.setAutoCommit(true);

								DAO.closeStatement(null, null, conn);
								return localReturnValue1;
							}
						} catch (Exception er) {
							conn.rollback();
							throw er;
						} finally {
							conn.setAutoCommit(true);
						}
						conn.setAutoCommit(true);
					} else {
						Vector<FirmValue> vfv = DAO.getFirmList(" and CONTACT='" + cv.contact + "'");
						if ((vfv == null) || (vfv.size() != 1)) {
							rv.result = -40002L;
							rv.remark = "通过签约号查询客户信息异常";
							localReturnValue2 = rv;

							DAO.closeStatement(null, null, conn);
							return localReturnValue2;
						}
						cv.isOpen = 1;
						cv.status = 0;
						String firmID = cv.firmID;
						cv.firmID = cv.contact;
						conn = DAO.getConnection();
						try {
							conn.setAutoCommit(false);
							BankAdapterRMI bankadapter = getAdapter(cv.bankID);
							if (bankadapter == null) {
								rv.result = -920000L;
								rv.remark = "获取连接银行适配器失败";
								localReturnValue1 = rv;

								conn.setAutoCommit(true);

								DAO.closeStatement(null, null, conn);
								return localReturnValue1;
							}
							if ((Tool.getConfig("YQYbankID") == null) || (Tool.getConfig("YQYbankID").indexOf(cv.bankID) < 0)) {
								rv = dataProcess.changeFirmIsOpen(firmID, 1, cv.bankID, conn);
								if (rv.result < 0L) {
									conn.rollback();
									localReturnValue1 = rv;

									conn.setAutoCommit(true);

									DAO.closeStatement(null, null, conn);
									return localReturnValue1;
								}
							} else {
								cv.falg = 3;
							}
							rv = bankadapter.rgstAccountQuery(cv);
							if (rv.result == 0L) {
								cv.firmID = firmID;
								cv.account1 = rv.account1;
								rv.result = DAO.openCorrespond(cv, conn);
								conn.commit();
							} else if (rv.result == 5L) {
								cv.firmID = firmID;
								cv.account1 = rv.account1;
								cv.isOpen = 2;
								rv.result = -9L;
							} else if (rv.result < 0L) {
								conn.rollback();
								localReturnValue1 = rv;

								conn.setAutoCommit(true);

								DAO.closeStatement(null, null, conn);
								return localReturnValue1;
							}
						} catch (SQLException er) {
							conn.rollback();
							throw er;
						} catch (Exception e) {
							conn.rollback();
							throw e;
						} finally {
							conn.setAutoCommit(true);
						}
						conn.setAutoCommit(true);
					}
				} else {
					if (cvresult.size() != 1) {
						rv.result = -920014L;
						rv.remark = "通过银行编号、签约号查询出信息重复";
						localReturnValue2 = rv;

						DAO.closeStatement(null, null, conn);
						return localReturnValue2;
					}
					CorrespondValue cv2 = (CorrespondValue) cvresult.get(0);
					if ((cv.firmID == null) || (!cv.firmID.equals(cv2.firmID))) {
						rv.result = -40002L;
						rv.remark = "传入客户编号错误";
						localReturnValue2 = rv;

						DAO.closeStatement(null, null, conn);
						return localReturnValue2;
					}
					cv2.account = cv.account;
					cv2.isOpen = 1;
					cv2.status = 0;
					cv2.accountName = cv.accountName;
					cv2.bankCardPassword = cv.bankCardPassword;
					cv2.card = cv.card;
					cv2.cardType = cv.cardType;
					String firmID = cv.firmID;
					cv2.firmID = cv.contact;
					cv2.frozenFuns = cv.frozenFuns;
					cv2.actionID = cv.actionID;
					cv2.signInfo = cv.signInfo;
					conn = DAO.getConnection();
					try {
						conn.setAutoCommit(false);
						BankAdapterRMI bankadapter = getAdapter(cv.bankID);
						if (bankadapter == null) {
							rv.result = -920000L;
							rv.remark = "获取连接银行适配器失败";
							localReturnValue1 = rv;

							conn.setAutoCommit(true);

							DAO.closeStatement(null, null, conn);
							return localReturnValue1;
						}
						if ((Tool.getConfig("YQYbankID") == null) || (Tool.getConfig("YQYbankID").indexOf(cv.bankID) < 0)) {
							rv = dataProcess.changeFirmIsOpen(firmID, 1, cv2.bankID, conn);
							if (rv.result < 0L) {
								conn.rollback();
								localReturnValue1 = rv;

								conn.setAutoCommit(true);

								DAO.closeStatement(null, null, conn);
								return localReturnValue1;
							}
						} else {
							cv2.falg = 3;
						}
						rv = bankadapter.rgstAccountQuery(cv2);
						if (rv.result == 0L) {
							cv2.firmID = firmID;
							cv2.account1 = rv.account1;
							rv.result = DAO.openCorrespond(cv2, conn);
							conn.commit();
						} else if (rv.result == 5L) {
							cv2.firmID = firmID;
							cv2.account1 = rv.account1;
							cv2.isOpen = 2;
							rv.result = -9L;
						} else if (rv.result < 0L) {
							conn.rollback();
							localReturnValue1 = rv;

							conn.setAutoCommit(true);

							DAO.closeStatement(null, null, conn);
							return localReturnValue1;
						}
					} catch (Exception er) {
						conn.rollback();
						throw er;
					} finally {
						conn.setAutoCommit(true);
					}
					conn.setAutoCommit(true);
				}
				if (rv.result >= 0L) {
					try {
						dataProcess.openAccount(cv.firmID, cv.bankID, conn);
					} catch (Exception e) {
						log(Tool.getExceptionTrace(e));
					}
				} else if (rv.result == -9L) {
					rv.result = 0L;
					rv.remark = "预签约成功";
				}
			} catch (SQLException e) {
				rv.result = -40006L;
				rv.remark = "数据库异常";
				log("银行开户 交易账号与银行账号对应SQLException," + Tool.getExceptionTrace(e));
			} catch (Exception e) {
				rv.result = -40007L;
				rv.remark = "系统异常";
				log("银行开户 交易账号与银行账号对应Exception," + Tool.getExceptionTrace(e));
			} finally {
				DAO.closeStatement(null, null, conn);
			}
		}
		return rv;
	}

	public long delAccount(CorrespondValue correspondValue) {
		log("银行账号注销 delAccount correspondValue:"
				+ (correspondValue == null ? "为 null" : new StringBuilder("\n").append(correspondValue.toString()).append("\n").toString()));
		long result = -1L;
		if ((correspondValue.bankID == null) || (correspondValue.bankID.length() == 0) || (correspondValue.firmID == null)
				|| (correspondValue.firmID.length() == 0) || (correspondValue.account == null) || (correspondValue.account.length() == 0)) {
			result = -40011L;
			return result;
		}
		result = ifbankTrade(correspondValue.bankID, correspondValue.firmID, correspondValue.contact, 4, 1).result;
		if (result < 0L) {
			return result;
		}
		if ((correspondValue.contact == null) || (correspondValue.contact.trim().length() <= 0)) {
			correspondValue.contact = correspondValue.firmID;
		}
		CorrespondValue cv = null;
		try {
			cv = getCorrespond(correspondValue.bankID, null, correspondValue.contact, correspondValue.account);
		} catch (SQLException e1) {
			return -920015L;
		} catch (ClassNotFoundException e1) {
			return -920016L;
		}
		if (cv == null) {
			result = getMktActionID();
			return result;
		}
		if (cv.frozenFuns > 0.0D) {
			result = -920002L;
			return result;
		}
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			Vector<CapitalValue> capitals = DAO.getCapitalInfoList(" and bankID='" + cv.bankID.trim() + "' and contact='" + cv.contact.trim()
					+ "' and createtime>=trunc(sysdate) and createtime<trunc(sysdate)+1  ", conn);
			if ((capitals != null) && (capitals.size() > 0)) {
				result = -941002L;
				long l2 = result;
				return l2;
			}
			try {
				conn.setAutoCommit(false);
				result = dataProcess.ifFirmDelAccount(cv.firmID, cv.bankID, conn);
				if (result < 0L) {
					conn.rollback();
					long l1 = result;

					conn.setAutoCommit(true);

					DAO.closeStatement(null, null, conn);
					return l1;
				}
				Vector<CorrespondValue> vcv = DAO.getCorrespondList(
						" and isOpen=1 and contact='" + correspondValue.contact + "' and bankID<>'" + correspondValue.bankID + "'", conn);
				if ((vcv == null) || (vcv.size() <= 0)) {
					result = dataProcess.changeFirmIsOpen(cv.firmID, 2, cv.bankID, conn).result;
				}
				if (result >= 0L) {
					DAO.destroyAccount(cv, conn);
					result = getMktActionID();
				}
				conn.commit();
			} catch (SQLException er) {
				conn.rollback();
				throw er;
			} finally {
				conn.setAutoCommit(true);
			}
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			result = -40016L;
			log("银行账号注销SQLException，错误码=" + result + "  " + Tool.getExceptionTrace(e));
		} catch (Exception e) {
			result = -40017L;
			log("银行账号注销Exception，错误码=" + result + "  " + Tool.getExceptionTrace(e));
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public ReturnValue delAccountMaket(CorrespondValue correspondValue) {
		log("银行账号注销 delAccountMaket correspondValue:"
				+ (correspondValue == null ? "为 null" : new StringBuilder("\n").append(correspondValue.toString()).append("\n").toString()));
		ReturnValue result = ifbankTrade(correspondValue.bankID, correspondValue.firmID, correspondValue.contact, 4, 0);
		if (result.result < 0L) {
			return result;
		}
		CorrespondValue cv = null;
		try {
			cv = getCorrespond(correspondValue.bankID, correspondValue.firmID, correspondValue.contact, correspondValue.account);
		} catch (SQLException e) {
			result.result = -920015L;
			result.remark = "查询账号绑定关系时数据库异常";
			log(Tool.getExceptionTrace(e));

			return result;
		} catch (Exception e) {
			result.result = -920016L;
			result.remark = "查询账号绑定关系时系统异常";
			log(Tool.getExceptionTrace(e));

			return result;
		}
		if (cv == null) {
			result.result = -40014L;
			result.remark = "未查询到绑定信息";

			return result;
		}
		if (cv.frozenFuns > 0.0D) {
			result.result = -920002L;
			result.remark = "在途资金不为 0 不能解约";

			return result;
		}
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			Vector<CapitalValue> capitals = DAO.getCapitalInfoList(" and bankID='" + cv.bankID.trim() + "' and contact='" + cv.contact.trim()
					+ "' and createtime>=trunc(sysdate) and createtime<trunc(sysdate)+1 ", conn);
			ReturnValue localReturnValue2;
			if ((capitals != null) && (capitals.size() > 0)) {
				result.result = -941002L;
				result.remark = "当日有转账，不能解约";

				localReturnValue2 = result;
				return localReturnValue2;
			}
			BankAdapterRMI bankadapter = getAdapter(correspondValue.bankID);
			if (bankadapter == null) {
				result.result = -920000L;
				result.remark = "连接银行通讯机(适配器)失败";

				localReturnValue2 = result;
				return localReturnValue2;
			}
			try {
				conn.setAutoCommit(false);
				result.result = dataProcess.ifFirmDelAccount(cv.firmID, cv.bankID, conn);
				ReturnValue localReturnValue1;
				if (result.result == -1L) {
					conn.rollback();
					result.remark = "账户余额不为0，不满足交易系统解约条件";

					localReturnValue1 = result;

					conn.setAutoCommit(true);

					DAO.closeStatement(null, null, conn);
					return localReturnValue1;
				}
				if (result.result == -3L) {
					conn.rollback();
					result.remark = "次账户与其他签约银行，不满足交易系统解约条件";

					localReturnValue1 = result;

					conn.setAutoCommit(true);

					DAO.closeStatement(null, null, conn);
					return localReturnValue1;
				}
				Vector<CorrespondValue> vcv = DAO.getCorrespondList(
						" and isOpen=1 and contact='" + correspondValue.contact + "' and bankID<>'" + correspondValue.bankID + "'", conn);
				if ((vcv == null) || (vcv.size() <= 0)) {
					result = dataProcess.changeFirmIsOpen(cv.firmID, 2, cv.bankID, conn);
					if (result.result < 0L) {
						conn.rollback();

						localReturnValue1 = result;

						conn.setAutoCommit(true);

						DAO.closeStatement(null, null, conn);
						return localReturnValue1;
					}
				}
				DAO.destroyAccount(cv, conn);
				cv.firmID = cv.contact;
				cv.actionID = correspondValue.actionID;
				cv.signInfo = correspondValue.signInfo;
				cv.bankCardPassword = correspondValue.bankCardPassword;
				ReturnValue returnValue = bankadapter.delAccountQuery(cv);
				if (returnValue == null) {
					conn.rollback();
					log("解约，银行返回信息为空");
					result.result = -50010L;
					result.remark = "解约适配器返回对象为空";
					localReturnValue1 = result;

					conn.setAutoCommit(true);

					DAO.closeStatement(null, null, conn);
					return localReturnValue1;
				}
				if (returnValue.result < 0L) {
					conn.rollback();
					log(returnValue.toString());
					result.result = returnValue.result;
					result.remark = returnValue.remark;

					localReturnValue1 = result;

					conn.setAutoCommit(true);

					DAO.closeStatement(null, null, conn);
					return localReturnValue1;
				}
				conn.commit();
			} catch (SQLException er) {
				conn.rollback();
				throw er;
			} catch (Exception er) {
				conn.rollback();
				throw er;
			} finally {
				conn.setAutoCommit(true);
			}
			conn.setAutoCommit(true);

			result.result = 0L;
			result.remark = "解约账户信息成功";
		} catch (SQLException e) {
			result.result = -40016L;
			result.remark = "解约账号信息时数据库异常";
			log("银行账号注销SQLException，错误码=" + result + "  " + Tool.getExceptionTrace(e));
		} catch (Exception e) {
			result.result = -40017L;
			result.remark = "解约账户信息时系统异常";
			log("银行账号注销Exception，错误码=" + result + "  " + Tool.getExceptionTrace(e));
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public ReturnValue modAccount(CorrespondValue cv1, CorrespondValue cv2) {
		log("变更账户方法 modAccount cv1:" + (cv1 == null ? "为 null" : new StringBuilder("\n").append(cv1.toString()).append("\n").toString()) + " cv2:"
				+ (cv2 == null ? "为 null" : new StringBuilder("\n").append(cv2.toString()).append("\n").toString()));
		long checkResult = chenckCorrespondValue(cv1);
		ReturnValue rv = new ReturnValue();
		if (checkResult == 0L) {
			rv.actionId = getMktActionID();
			try {
				cv1.account = cv2.account;
				cv1.isOpen = 1;
				cv1.status = 0;
				rv.result = DAO.modCorrespond(cv1);
			} catch (SQLException e) {
				rv.result = -40006L;
				log("修改交易账号代码与银行账号对应SQLException," + Tool.getExceptionTrace(e));
			} catch (Exception e) {
				rv.result = -40007L;
				log("修改交易账号代码与银行账号对应SQLException," + Tool.getExceptionTrace(e));
			}
		} else {
			rv.result = checkResult;
			rv.remark = ((String) ErrorCode.error.get(Long.valueOf(rv.result)));
		}
		return rv;
	}

	public ReturnValue modAccountMarket(CorrespondValue cv1, CorrespondValue cv2) {
		log("变更账户方法 modAccountMarket cv1:" + (cv1 == null ? "为 null" : new StringBuilder("\n").append(cv1.toString()).append("\n").toString())
				+ " cv2:" + (cv2 == null ? "为 null" : new StringBuilder("\n").append(cv2.toString()).append("\n").toString()));
		ReturnValue rv = new ReturnValue();
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			cv1.account = cv2.account;
			if (cv2.isOpen != 1) {
				Vector<CapitalValue> capitals = DAO.getCapitalInfoList(" and bankID='" + cv2.bankID.trim() + "' and trim(contact)='"
						+ cv2.contact.trim() + "' and createtime>=trunc(sysdate) and createtime<trunc(sysdate)+1  ", conn);
				if ((capitals != null) && (capitals.size() > 0)) {
					rv.result = -941002L;
					rv.remark = "当日有转账，不能修改成解约状态";
					ReturnValue localReturnValue2 = rv;
					return localReturnValue2;
				}
			}
			try {
				conn.setAutoCommit(false);
				if (cv2.isOpen == 1) {
					rv = dataProcess.changeFirmIsOpen(cv2.firmID, 1, cv2.bankID, conn);
				} else {
					rv.result = dataProcess.ifFirmDelAccount(cv2.firmID, cv2.bankID, conn);
					if (rv.result < 0L) {
						rv.remark = "不满足交易系统解约条件";
						ReturnValue localReturnValue1 = rv;

						conn.setAutoCommit(true);

						DAO.closeStatement(null, null, conn);
						return localReturnValue1;
					}
					Vector<CorrespondValue> vcv = DAO
							.getCorrespondList(" and isOpen=1 and trim(contact)='" + cv2.contact + "' and bankID<>'" + cv2.bankID + "'", conn);
					if ((vcv == null) || (vcv.size() <= 0)) {
						rv = dataProcess.changeFirmIsOpen(cv2.firmID, 2, cv2.bankID, conn);
					}
				}
				if (rv.result >= 0L) {
					rv.result = DAO.modCorrespond(cv1, conn);
					rv.remark = "修改成功";
				}
				conn.commit();
			} catch (SQLException e) {
				conn.rollback();
				throw e;
			} catch (Exception e) {
				conn.rollback();
				throw e;
			} finally {
				conn.setAutoCommit(true);
			}
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			rv.result = -920015L;
			rv.remark = "数据库异常";
			log("修改交易账号代码与银行账号对应SQLException," + Tool.getExceptionTrace(e));
		} catch (Exception e) {
			rv.result = -920016L;
			rv.remark = "系统异常";
			log("修改交易账号代码与银行账号对应SQLException," + Tool.getExceptionTrace(e));
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return rv;
	}

	public long tradeDate(String bankID) {
		log("判断是否可以转账 tradeDate bankID[" + bankID + "]");
		long result = -1L;
		try {
			int n = DAO.useBank(bankID, 1);
			switch (n) {
			case 0:
				result = 0L;
				break;
			case 1:
				result = -920003L;
				break;
			case 2:
				result = -920004L;
				break;
			case 3:
				result = -920005L;
				break;
			default:
				result = -920006L;
			}
		} catch (Exception e) {
			result = -920006L;
			log(Tool.getExceptionTrace(e));
		}
		return result;
	}

	public long tradeDate(String bankID, int type) {
		log("判断是否可以转账 tradeDate bankID[" + bankID + "]");
		long result = -1L;
		try {
			int n = DAO.useBank(bankID, type);
			switch (n) {
			case 0:
				result = 0L;
				break;
			case 1:
				result = -920003L;
				break;
			case 2:
				result = -920004L;
				break;
			case 3:
				result = -920005L;
				break;
			default:
				result = -920006L;
			}
		} catch (Exception e) {
			result = -920006L;
			log(Tool.getExceptionTrace(e));
		}
		return result;
	}

	public ReturnValue isTradeDate(java.util.Date tradeDate) {
		ReturnValue result = new ReturnValue();
		try {
			if (tradeDate != null) {
				boolean flag = DAO.getTradeDate(tradeDate);
				if (flag) {
					result.remark = ("日期[" + Tool.fmtDate(tradeDate) + "]是交易日");
				} else {
					result.result = -3L;
					result.remark = ("日期[" + Tool.fmtDate(tradeDate) + "]不是交易日");
				}
			} else {
				result.result = -4L;
				result.remark = "传入的日期为空，不合法";
			}
		} catch (SQLException e) {
			result.result = -1L;
			result.remark = ("查询日期[" + Tool.fmtDate(tradeDate) + "]是否为交易日时数据库异常");
			log(Tool.getExceptionTrace(e));
		} catch (Exception e) {
			result.result = -2L;
			result.remark = ("查询日期[" + Tool.fmtDate(tradeDate) + "]是否为交易日时系统异常");
			log(Tool.getExceptionTrace(e));
		}
		return result;
	}

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

	public FirmMessageVo getFirmMSG(String contact) {
		FirmMessageVo result = null;
		try {
			Vector<FirmValue> cvl = DAO.getFirmList(" and CONTACT='" + contact + "'");
			if ((cvl != null) && (cvl.size() == 1)) {
				FirmValue fv = (FirmValue) cvl.get(0);
				result = DAO.getFirmMSG(fv.firmID);
				result.Password = fv.password;
				if ((result.Password != null) && (result.Password.trim().length() > 0)) {
					return result;
				}
				String password = Tool.getConfig("password");
				if ((password != null) && (password.trim().length() > 0)) {
					result.Password = Encryption.encryption(fv.firmID, password, null);
					return result;
				}
				password = fv.card;
				if ((password == null) || (password.length() <= 0)) {
					result.Password = Encryption.encryption(fv.firmID, "000000", null);
					return result;
				}
				password = password.trim().replaceAll("\\D", "0");
				for (int i = password.length(); i < 6; i++) {
					password = password + "0";
				}
				if (password.length() >= 6) {
					result.Password = Encryption.encryption(fv.firmID, password.substring(password.length() - 6, password.length()), null);
					return result;
				}
			}
		} catch (SQLException e) {
			log(Tool.getExceptionTrace(e));
		} catch (Exception e) {
			log(Tool.getExceptionTrace(e));
		}
		return result;
	}

	public ReturnValue ifbankTrade(String bankID, String firmID, String contact, int type, int launcher) {
		log("判断当前是否可以发生转账 ifbankTrade bankID[" + bankID + "]type[" + type + "]");
		ReturnValue result = new ReturnValue();
		result.result = -1L;
		try {
			BankValue bv = DAO.getBank(bankID);
			if (bv == null) {
				result.result = -920013L;
				result.remark = "未初始化此银行信息";
				return result;
			}
			if (bv.validFlag != 0) {
				result.result = -920017L;
				result.remark = ("[" + bv.bankName + "]被禁用");
				return result;
			}
			if ((type == 0) && (bv.inMoneyFlag != 0)) {
				result.result = -9200171L;
				result.remark = ("[" + bv.bankName + "]入金交易被禁用");
				return result;
			}
			if ((type == 1) && (bv.outMoneyFlag != 0)) {
				result.result = -9200172L;
				result.remark = ("[" + bv.bankName + "]出金交易被禁用");
				return result;
			}
			result = firmFrozen(firmID, type, launcher, contact);
			if (result.result != 0L) {
				return result;
			}
			result.result = tradeDate(bankID, type);
			if (result.result < 0L) {
				result.remark = ((String) ErrorCode.error.get(Long.valueOf(result.result)));
			}
		} catch (SQLException e) {
			result.result = -920015L;
			result.remark = "数据库异常";
			log(Tool.getExceptionTrace(e));
		} catch (Exception e) {
			result.result = -920016L;
			result.remark = "系统异常";
			log(Tool.getExceptionTrace(e));
		}
		return result;
	}

	private ReturnValue firmFrozen(String firmID, int type, int launcher, String contact) throws SQLException, ClassNotFoundException {
		log("判断交易商是否被冻结firmID[" + firmID + "]contact[" + contact + "]");
		ReturnValue result = new ReturnValue();
		FirmMessageVo fmv = null;
		if ((firmID != null) && (firmID.trim().length() > 0)) {
			fmv = DAO.getFirmMSG(firmID);
		} else if ((contact != null) && (contact.trim().length() > 0)) {
			fmv = getFirmMSG(contact);
		}
		if (fmv == null) {
			result.result = -920010L;
			result.remark = "未查询到交易帐户信息";
			return result;
		}
		if ("N".equalsIgnoreCase(fmv.status)) {
			result.result = 0L;
			result.remark = "客户可操作状态";
			return result;
		}
		if ((type != 0) && (type != 1)) {
			result.result = 0L;
			result.remark = "客户可操作状态";
			return result;
		}
		if ("U".equalsIgnoreCase(fmv.status)) {
			if (1 == launcher) {
				result.result = 0L;
				result.remark = "客户可操作状态";
				return result;
			}
			result.result = 0L;
			result.remark = "客户可操作状态";
			return result;
		}
		if ("F".equalsIgnoreCase(fmv.status)) {
			if ((type == 0) && (!"C".equalsIgnoreCase(fmv.firmType))) {
				result.result = 0L;
				result.remark = "客户可操作状态";
				return result;
			}
			if (type == 1) {
				if (launcher == 0) {
					result.result = -200091L;
					result.remark = "交易所发起出金,账户被冻结";
					return result;
				}
				result.result = -200261L;
				result.remark = "银行发起出金，账户被冻结";
				return result;
			}
			if (launcher == 0) {
				result.result = -100200L;
				result.remark = "交易所发起入金,账户被冻结";
				return result;
			}
			result.result = -100201L;
			result.remark = "银行发起入金，账户被冻结";
			return result;
		}
		result.result = -10020L;
		result.remark = "客户禁用状态";
		return result;
	}

	public long inMoney(String bankID, String contact, String account, Timestamp bankTime, double money, String funID, long actionID, int funFlag,
			String remark) {
		log("入金，由adapter调用 inMoney bankID[" + bankID + "]firmID[" + contact + "]account[" + account + "]bankTime[" + bankTime + "]money[" + money
				+ "]funID[" + funID + "]actionID[" + actionID + "]funFlag[" + funFlag + "]remark[" + remark + "]");
		long result = 0L;
		long capitalID = 0L;
		String firmID = null;
		Connection conn = null;
		if (bankTime == null) {
			bankTime = new Timestamp(System.currentTimeMillis());
		}
		try {
			CorrespondValue cv = getCorrespond(bankID, null, contact, account);
			long l2;
			if (cv == null) {
				result = -920013L;
				l2 = result;
				return l2;
			}
			if (cv.isOpen != 1) {
				result = -100100L;
				log("银行发起入金，交易账号未签约。");
				l2 = result;
				return l2;
			}
			if (cv.status != 0) {
				result = -100101L;
				log("银行发起入金，交易账号禁用");
				l2 = result;
				return l2;
			}
			log("查询到交易账号绑定信息为：" + cv.toString());
			contact = cv.contact;
			account = cv.account;
			firmID = cv.firmID;
			ReturnValue rv = ifbankTrade(bankID, firmID, contact, 0, 1);
			if (rv.result != 0L) {
				log(rv.remark);
				result = rv.result;
				l2 = result;
				return l2;
			}
			conn = DAO.getConnection();
			Vector<CapitalValue> v = DAO.getCapitalInfoList(
					" and actionid=" + actionID + " or (funid='" + funID + "' and createtime>=to_date('" + Tool.fmtDate(bankTime)
							+ "','yyyy-MM-dd') and createtime<to_date('" + Tool.fmtDate(bankTime) + "','yyyy-MM-dd')+1 and bankID='" + bankID + "')",
					conn);
			if (result == 0L) {
				try {
					if ((v == null) || (v.size() <= 0)) {
						if (money <= 0.0D) {
							result = -920019L;
							l2 = result;

							DAO.closeStatement(null, null, conn);
							return l2;
						}
						result = DAO.getActionID(conn);
						CapitalValue cVal = new CapitalValue();
						cVal.trader = "";
						cVal.funID = funID;
						cVal.actionID = result;
						cVal.firmID = firmID;
						cVal.contact = contact;
						cVal.bankID = bankID;
						cVal.type = 0;
						cVal.launcher = 1;
						cVal.money = money;
						cVal.status = 7;
						cVal.bankTime = bankTime;
						cVal.note = "银行端入金";
						capitalID = DAO.addCapitalInfo(cVal, conn);
					} else if (v.size() > 0) {
						for (int i = 0; i < v.size(); i++) {
							CapitalValue val = (CapitalValue) v.get(i);
							if (val.type == 0) {
								capitalID = val.iD;
								result = val.actionID;
							}
						}
					}
				} catch (SQLException e) {
					throw e;
				}
			}
			if (result > 0L) {
				try {
					v = DAO.getCapitalInfoList(" and actionid=" + result + " and status=" + 7);
					conn.setAutoCommit(false);
					if (v.size() > 0) {
						Vector<CapitalValue> ll = DAO.getCapitalInfoList(" and actionid=" + result + " and status=" + 7 + " for update ", conn);
						if ((ll == null) || (ll.size() <= 0)) {
							result = -20042L;
							log("信息已在处理中");
							long l1 = result;

							conn.setAutoCommit(true);

							DAO.closeStatement(null, null, conn);
							return l1;
						}
						if (funFlag == 0) {
							dataProcess.updateFundsFull(bankID, firmID, actionID + "", money, 0, conn);
							DAO.modCapitalInfoStatus(capitalID, funID, 0, bankTime, conn);
							DAO.modCapitalInfoNote(capitalID, "入金成功", conn);
						} else if (funFlag == 5) {
							DAO.modCapitalInfoStatus(capitalID, funID, 2, bankTime, conn);
							DAO.modCapitalInfoNote(capitalID, "银行处理中", conn);
						} else if (funFlag == -50010L) {
							DAO.modCapitalInfoStatus(capitalID, funID, 5, bankTime, conn);
							DAO.modCapitalInfoNote(capitalID, "银行无返回", conn);
						} else if (funFlag == -920008L) {
							DAO.modCapitalInfoStatus(capitalID, funID, 6, bankTime, conn);
							DAO.modCapitalInfoNote(capitalID, "银行返回异常", conn);
						} else {
							DAO.modCapitalInfoStatus(capitalID, funID, 1, bankTime, conn);
							DAO.modCapitalInfoNote(capitalID, remark, conn);
						}
					} else {
						result = -10025L;
						log("查询到流水" + capitalID + "的状态不正确");
					}
					conn.commit();
				} catch (SQLException e) {
					conn.rollback();
					throw e;
				} finally {
					conn.setAutoCommit(true);
				}
				conn.setAutoCommit(true);
				if (result == -10004L) {
					DAO.modCapitalInfoStatus(capitalID, funID, 8, bankTime, conn);
					DAO.modCapitalInfoNote(capitalID, "市场处理异常", conn);
				}
			}
		} catch (SQLException e) {
			result = -10004L;
			log("入金回调方法SQLException,异常值=" + result + "  " + Tool.getExceptionTrace(e));
		} catch (Exception e) {
			result = -10005L;
			log("入金回调方法Exception,异常值=" + result + "  " + Tool.getExceptionTrace(e));
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		log("由adapter调用 inMoney bankID[" + bankID + "]firmID[" + contact + "]account[" + account + "]bankTime[" + bankTime + "]money[" + money
				+ "]funID[" + funID + "]actionID[" + actionID + "]funFlag[" + funFlag + "]remark[" + remark + "]返回给适配器[" + result + "]");
		return result;
	}

	public ReturnValue inMoneyMarket(InMoneyMarket imm) {
		log("市场入金 inMoneyMarket imm:" + (imm == null ? "为 null" : new StringBuilder("\n").append(imm.toString()).append("\n").toString()));
		ReturnValue result = new ReturnValue();
		long capitalID = 0L;
		Connection conn = null;
		Timestamp curTime = new Timestamp(System.currentTimeMillis());
		try {
			ReturnValue localReturnValue1;
			if (imm.money <= 0.0D) {
				result.result = -920019L;
				result.remark = "入金金额必须为正数";
				localReturnValue1 = result;
				return localReturnValue1;
			}
			conn = DAO.getConnection();
			CorrespondValue cv = getCorrespond(imm.bankID, imm.firmID, imm.contact, imm.account);
			if (cv == null) {
				result.result = -920013L;
				result.remark = "未查询到交易账号信息";
				localReturnValue1 = result;
				return localReturnValue1;
			}
			imm.contact = cv.contact;
			imm.account = cv.account;
			imm.firmID = cv.firmID;
			imm.bankFlag = String.valueOf(cv.isCrossline);
			result = ifbankTrade(imm.bankID, imm.firmID, imm.contact, 0, 0);
			if (result.result != 0L) {
				localReturnValue1 = result;
				return localReturnValue1;
			}
			if (cv.isOpen != 1) {
				result.result = -10019L;
				result.remark = "交易账号未签约";
				localReturnValue1 = result;
				return localReturnValue1;
			}
			if (cv.status != 0) {
				result.result = -10020L;
				result.remark = "交易账号不可用";
				localReturnValue1 = result;
				return localReturnValue1;
			}
			log(imm.toString());
			result.actionId = DAO.getActionID(conn);
			result.result = result.actionId;
			try {
				CapitalValue cVal = new CapitalValue();
				cVal.trader = "";
				cVal.actionID = result.actionId;
				cVal.firmID = imm.firmID;
				cVal.contact = imm.contact;
				cVal.bankID = imm.bankID;
				cVal.type = 0;
				cVal.launcher = 0;
				cVal.money = imm.money;

				cVal.status = 7;
				cVal.note = "市场端入金";
				log(cVal.toString());
				capitalID = DAO.addCapitalInfo(cVal, conn);
			} catch (SQLException e) {
				e.printStackTrace();
				result.result = -10026L;
				result.remark = "添加流水信息时数据库异常";
				log("市场端发起入金写资金流水SQLException,数据回滚:" + e);
				localReturnValue1 = result;

				DAO.closeStatement(null, null, conn);
				return localReturnValue1;
			}
			if (result.result <= 0L) {
				localReturnValue1 = result;
				return localReturnValue1;
			}
			TransferInfoValue payInfo = getPayInfo(imm.bankID, imm.firmID, 0, conn);
			TransferInfoValue receiveInfo = getReceiveInfo(imm.bankID, imm.firmID, 0, conn);

			payInfo.OpenBankCode = cv.OpenBankCode;
			log("市场端发起入金:" + payInfo.toString());
			InMoneyVO inMoneyInfo = new InMoneyVO(imm.amoutDate, imm.bankName, imm.outAccount, imm.personName, imm.inOutStart, imm.bankID, null,
					imm.money, imm.contact, payInfo, imm.accountPwd, receiveInfo, imm.remark, result.actionId);
			inMoneyInfo.setBankFlag(imm.bankFlag);
			BankAdapterRMI bankadapter = getAdapter(imm.bankID);
			if (bankadapter == null) {
				result.result = -920000L;
				result.remark = "网络异常，处理器无法连接适配器";
				log(result.remark);
			} else {
				result = bankadapter.inMoneyQueryBank(inMoneyInfo);
			}
			log("市场端调用适配器入金，市场返回信息：" + result.toString());

		} catch (SQLException e) {
			result.result = -10014L;
			result.remark = "数据库连接异常";
			log("市场端发起入金SQLException:" + result + Tool.getExceptionTrace(e));
		} catch (Exception e) {
			result.result = -10015L;
			result.remark = "系统异常";
			log("市场端发起入金Exception:" + result + Tool.getExceptionTrace(e));
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public ReturnValue outMoney(String bankID, double money, String contact, String bankAccount, String funID, String operator, int express,
			int type) {
		log("出金: outMoney bankID[" + bankID + "]money[" + money + "]contact[" + contact + "]bankAccount[" + bankAccount + "]funID[" + funID
				+ "]operator[" + operator + "]express[" + express + "]type[" + type + "]");
		ReturnValue result = new ReturnValue();
		String firmID = null;
		Connection conn = null;
		CapitalValue capital = null;

		Timestamp curTime = new Timestamp(System.currentTimeMillis());
		try {
			CorrespondValue cv = getCorrespond(bankID, null, contact, bankAccount);
			ReturnValue localReturnValue1;
			if (cv == null) {
				result.result = -920013L;
				result.remark = "出金，未查询到交易账号绑定信息";
				localReturnValue1 = result;
				return localReturnValue1;
			}
			if (cv.isOpen != 1) {
				result.result = -20008L;
				result.remark = "交易账号未签约。";
				log(result.remark);
				localReturnValue1 = result;
				return localReturnValue1;
			}
			if (cv.status != 0) {
				result.result = -20009L;
				result.remark = "交易账号不可用";
				log(result.remark);
				localReturnValue1 = result;
				return localReturnValue1;
			}
			contact = cv.contact;
			bankAccount = cv.account;
			firmID = cv.firmID;
			result = ifbankTrade(bankID, firmID, contact, 1, 1);
			if (result.result < 0L) {
				localReturnValue1 = result;
				return localReturnValue1;
			}
			conn = DAO.getConnection();
			result = checkArgs(conn, money, firmID, bankID);
			if (result.result < 0L) {
				localReturnValue1 = result;
				return localReturnValue1;
			}
			result = checkTrans(bankID, contact, money, curTime, 1, conn);

			log("checkTrans=============================" + result.result + "====" + result.remark);
			if (result.result < 0L) {
				localReturnValue1 = result;
				return localReturnValue1;
			}
			long actionId = handleOUtMoney(conn, bankID, "", money, firmID, contact, bankAccount, funID, type);
			result.result = actionId;
			if (result.result <= 0L) {
				result.remark = "写入资金流水失败";
				localReturnValue1 = result;
				return localReturnValue1;
			}
			Vector<CapitalValue> list = DAO.getCapitalInfoList(" and actionid=" + actionId, conn);
			for (int i = 0; i < list.size(); i++) {
				CapitalValue val = (CapitalValue) list.get(i);
				if (val.type == 1) {
					capital = val;
				}
			}
			if (capital == null) {
				result.result = -20011L;
				result.remark = ("没有发现业务流水号为：" + actionId + "的流水");
				log(result.remark);
				localReturnValue1 = result;
				return localReturnValue1;
			}
			boolean notBeyond = true;
			BankValue bVal = DAO.getBank(bankID, conn);
			FirmAuditValue fVal = DAO.getFirmAuditValue(contact, conn);
			log("BankValue" + bVal.toString());
			log("交易商的审核额度：" + fVal.toString());
			String firmType = DAO.getFirmType(firmID, conn);
			double auditBalance = bVal.cmaxOutMoney;
			double mauditBalance = bVal.mmaxOutMoney;
			double dayTransCount = getDayTransMoney(bankID, contact, 1, curTime, conn);
			if (fVal.maxAuditMoney > 0.0D) {
				auditBalance = fVal.maxAuditMoney;
			}
			log("审核金额为:  " + auditBalance);
			if ("C".equals(firmType)) {
				log("客户出金：" + money + "最大出金" + mauditBalance);
				if ("1".equals(bVal.cOutMoney)) {
					if ((money > auditBalance) && (auditBalance >= 0.0D)) {
						notBeyond = false;
					}
				} else if ((dayTransCount > auditBalance) && (auditBalance >= 0.0D)) {
					notBeyond = false;
				}
			} else {
				log("会员出金：" + money + "最大出金" + mauditBalance);
				if ("1".equals(bVal.mOutMoney)) {
					if ((money > mauditBalance) && (mauditBalance >= 0.0D)) {
						notBeyond = false;
					}
				} else if ((dayTransCount > mauditBalance) && (mauditBalance >= 0.0D)) {
					notBeyond = false;
				}
			}
			if ((Tool.getConfig("AutoAudit") != null) && (Tool.getConfig("AutoAudit").equalsIgnoreCase("True"))) {
				result = outMoneyAutoAudit(result.result);
				result.actionId = actionId;
			} else if ((notBeyond) && (3 != type)) {
				result = outMoneyAutoAudit(result.result);
				result.actionId = actionId;
			} else if (type == 1) {
				try {
					conn.setAutoCommit(false);

					dataProcess.updateFrozenFunds(firmID, -1.0D * Arith.add(money, 0.0D), conn);

					bankFrozenFuns(firmID, contact, bankID, null, -1.0D * Arith.add(money, 0.0D), conn);
					DAO.modCapitalInfoStatus(capital.iD, funID == null ? "" : funID, 1, curTime, conn);
					result.type = 1;
					DAO.modCapitalInfoNote(capital.iD, "[bank_out]出金超出审核额度，出金拒绝", conn);
					result.result = -20043L;
					conn.commit();
				} catch (SQLException e) {
					e.printStackTrace();
					conn.rollback();
					result.result = -20004L;
					log("市场发起出金SQLException，数据回滚，错误码=" + result.result + "  " + e);
				} finally {
					conn.setAutoCommit(true);
				}
				if (result.result == -20014L) {
					DAO.modCapitalInfoStatus(capital.iD, funID == null ? "" : funID, 1, curTime, conn);
					result.type = 1;
					DAO.modCapitalInfoNote(capital.iD, "出金超过审核额度，拒绝出金,资金解冻出错，需要手工处理出金", conn);
				}
			} else {
				DAO.modCapitalInfoStatus(capital.iD, funID == null ? "" : funID, 3, curTime, conn);
				result.result = -30006L;
				result.remark = "超出审核额度，需要市场审核";
			}
		} catch (SQLException e) {
			result.result = -20004L;
			result.remark = "数据库异常";
			log("银行发起出金SQLException，错误码=" + result.result + "  " + Tool.getExceptionTrace(e));
		} catch (Exception e) {
			result.result = -20005L;
			result.remark = "系统异常";
			log("银行发起出金Exception，错误码=" + result.result + "  " + Tool.getExceptionTrace(e));
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public ReturnValue outMoneyMarket(OutMoneyMarket omm) {
		log("出金: outMoney omm:" + (omm == null ? "为空" : new StringBuilder("\n").append(omm.toString()).append("\n").toString()));
		ReturnValue result = new ReturnValue();
		Connection conn = null;

		Timestamp curTime = new Timestamp(System.currentTimeMillis());
		try {
			CorrespondValue cv = getCorrespond(omm.bankID, omm.firmID, omm.contact, omm.account);
			ReturnValue localReturnValue1;
			if (cv == null) {
				result.result = -920013L;
				result.remark = "出金，未查询到交易账号绑定信息";
				log(result.remark);
				localReturnValue1 = result;
				return localReturnValue1;
			}
			if (cv.isOpen != 1) {
				result.result = -20008L;
				result.remark = "交易账号未签约。";
				log(result.remark);
				localReturnValue1 = result;
				return localReturnValue1;
			}
			if (cv.status != 0) {
				result.result = -20009L;
				result.remark = "交易账号不可用";
				log(result.remark);
				localReturnValue1 = result;
				return localReturnValue1;
			}
			omm.firmID = cv.firmID;
			omm.contact = cv.contact;
			omm.account = cv.account;
			result = ifbankTrade(omm.bankID, omm.firmID, omm.contact, 1, 0);
			if (result.result < 0L) {
				localReturnValue1 = result;
				return localReturnValue1;
			}
			conn = DAO.getConnection();
			result = checkArgs(conn, omm.money, omm.firmID, omm.bankID);
			if (result.result != 0L) {
				localReturnValue1 = result;
				return localReturnValue1;
			}
			result = checkTrans(omm.bankID, omm.contact, omm.money, curTime, 1, conn);

			log("checkTrans=============================" + result.result + "==================" + result.remark);
			if (result.result < 0L) {
				localReturnValue1 = result;
				return localReturnValue1;
			}
			long actionId = handleOUtMoney(conn, omm.bankID, "", omm.money, omm.firmID, omm.contact, omm.account, null, omm.type);
			result.result = actionId;
			if (result.result <= 0L) {
				result.remark = ((String) ErrorCode.error.get(Long.valueOf(result.result)));
			}
			if ("true".equalsIgnoreCase(Tool.getConfig("OutMoneyAudit"))) {
				log("出金审核功能开启状态");

				boolean notBeyond = true;
				BankValue bVal = DAO.getBank(omm.bankID, conn);
				FirmAuditValue fVal = DAO.getFirmAuditValue(omm.contact, conn);
				log("BankValue" + bVal.toString());
				log("交易商的审核额度：" + fVal.toString());
				String firmType = DAO.getFirmType(omm.firmID, conn);
				double auditBalance = bVal.cmaxOutMoney;
				double mauditBalance = bVal.mmaxOutMoney;
				double dayTransCount = getDayTransMoney(omm.bankID, omm.contact, 1, curTime, conn);
				if (fVal.maxAuditMoney > 0.0D) {
					auditBalance = fVal.maxAuditMoney;
					mauditBalance = fVal.maxAuditMoney;
				}
				log("客户的审核金额为:  " + auditBalance);
				log("会员的审核金额为:  " + mauditBalance);
				if ("C".equals(firmType)) {
					log("客户出金：" + omm.money + "最大出金" + auditBalance);
					if ("1".equals(bVal.cOutMoney)) {
						if ((omm.money > auditBalance) && (auditBalance >= 0.0D)) {
							notBeyond = false;
						}
					} else if ((dayTransCount > auditBalance) && (auditBalance >= 0.0D)) {
						notBeyond = false;
					}
				} else {
					log("会员出金：" + omm.money + "最大出金" + mauditBalance);
					if ("1".equals(bVal.mOutMoney)) {
						if ((omm.money > mauditBalance) && (mauditBalance >= 0.0D)) {
							notBeyond = false;
						}
					} else if ((dayTransCount > mauditBalance) && (mauditBalance >= 0.0D)) {
						notBeyond = false;
					}
				}
				if ("031".equals(omm.bankID)) {
					notBeyond = false;
				}
				if ((Tool.getConfig("AutoAudit") != null) && (Tool.getConfig("AutoAudit").equalsIgnoreCase("True"))) {
					result = outMoneyAutoAudit(result.result);
					result.actionId = actionId;
				} else if (notBeyond) {
					result = outMoneyAutoAudit(result.result);
					result.actionId = actionId;
				} else {
					CapitalValue vapv1 = DAO.getCapitalInfo(" and actionid=" + actionId);
					if (vapv1 == null) {
						log("将流水加入审核队列前未找到对应的流水");
						result.result = -20004L;
						result.remark = "将流水加入审核队列前未找到对应的流水";
					} else {
						DAO.modCapitalInfoStatus(vapv1.iD, null, 3, null, conn);
						DAO.modCapitalInfoNote(vapv1.iD, "等待市场审核", conn);
						result.result = -30006L;
						result.remark = "超出审核额度，需要市场审核";
					}
				}
			} else {
				log("出金审核功能不开启状态，直接进入自动审核");
				result = outMoneyAutoAudit(result.result);
			}
		} catch (SQLException e) {
			result.result = -20004L;
			result.remark = "数据库发生异常，请联系交易所";
			log("市场发起出金SQLException，错误码=" + result.result + "  " + Tool.getExceptionTrace(e));
		} catch (Exception e) {
			result.result = -20005L;
			result.remark = "转账系统异常";
			log("市场发起出金Exception，错误码=" + result.result + "  " + Tool.getExceptionTrace(e));
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public ReturnValue moneyInAudit(long actionID, String funID, boolean funFlag) {
		log("审核处理中的信息 moneyInAudit actionID[" + actionID + "]funFlag[" + funFlag + "]");
		Connection conn = null;
		Timestamp curTime = new Timestamp(System.currentTimeMillis());
		CapitalValue capital = null;
		ReturnValue result = new ReturnValue();
		try {
			conn = DAO.getConnection();
			Vector<CapitalValue> list = DAO.getCapitalInfoList(" and actionid=" + actionID, conn);
			ReturnValue localReturnValue1;
			for (int i = 0; i < list.size(); i++) {
				CapitalValue val = (CapitalValue) list.get(i);
				if ((val.type == 1) || (val.type == 0)) {
					capital = val;
					result.actionId = capital.actionID;
					result.bankTime = Tool.fmtTime(curTime);
					result.funID = capital.funID;
					if ((capital.status == 0) || (capital.status == 1)) {
						result.result = -20042L;
						result.remark = ("本条记录[" + actionID + "]已经有人处理");
						log(result.remark);
						localReturnValue1 = result;
						return localReturnValue1;
					}
				}
			}
			if (capital == null) {
				result.result = -930000L;
				result.remark = ("未查询到流水[" + actionID + "]信息");
				localReturnValue1 = result;
				return localReturnValue1;
			}
			if (funFlag) {
				try {
					conn.setAutoCommit(false);
					if (capital.type == 1) {
						dataProcess.updateFundsFull(capital.bankID, capital.firmID, actionID + "", capital.money, 1, conn);
						dataProcess.updateFrozenFunds(capital.firmID, -1.0D * capital.money, conn);

						bankFrozenFuns(capital.firmID, capital.contact, capital.bankID, null, -1.0D * capital.money, conn);
						DAO.modCapitalInfoStatus(capital.iD, funID, 0, curTime, conn);
						DAO.modCapitalInfoNote(capital.iD, "审核通过", conn);
					} else if (capital.type == 0) {
						dataProcess.updateFundsFull(capital.bankID, capital.firmID, actionID + "", capital.money, 0, conn);
						DAO.modCapitalInfoStatus(capital.iD, funID, 0, curTime, conn);
						DAO.modCapitalInfoNote(capital.iD, "审核通过", conn);
					}
					DAO.modCapitalInfoTrader(capital.iD, "manual", conn);
					log(actionID + "单边账审核通过");
					conn.commit();
				} catch (SQLException e) {
					conn.rollback();
					log(actionID + "审核出金SQLException，错误码=" + result + "  " + Tool.getExceptionTrace(e));
					throw e;
				} finally {
					conn.setAutoCommit(true);
				}
			} else {
				try {
					conn.setAutoCommit(false);
					if (capital.type == 1) {
						dataProcess.updateFrozenFunds(capital.firmID, -1.0D * capital.money, conn);
						bankFrozenFuns(capital.firmID, capital.contact, capital.bankID, null, -1.0D * capital.money, conn);
						DAO.modCapitalInfoStatus(capital.iD, result.funID, 1, curTime, conn);
						DAO.modCapitalInfoNote(capital.iD, "审核拒绝", conn);
					} else if (capital.type == 0) {
						DAO.modCapitalInfoStatus(capital.iD, result.funID, 1, curTime, conn);
						DAO.modCapitalInfoNote(capital.iD, "审核拒绝", conn);
					}
					DAO.modCapitalInfoTrader(capital.iD, "manual", conn);
					log(actionID + "单边账审核拒绝");
					conn.commit();
				} catch (SQLException e) {
					conn.rollback();
					log(actionID + "审核出金SQLException，错误码=" + result + "  " + Tool.getExceptionTrace(e));
					throw e;
				} finally {
					conn.setAutoCommit(true);
				}
			}
		} catch (SQLException e) {
			result.result = -30004L;
			result.remark = ("单边账处理[" + actionID + "]数据库异常");
			log(actionID + "审核出金SQLException，错误码=" + result + "  " + Tool.getExceptionTrace(e));
		} catch (Exception e) {
			result.result = -30005L;
			result.remark = ("单边账处理[" + actionID + "]系统异常");
			log(Tool.getExceptionTrace(e));
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public ReturnValue chargeAgainst(ChargeAgainstValue cav) {
		log("冲证(银行方调用) chargeAgainst cav:" + (cav == null ? "为 null" : new StringBuilder("\n").append(cav.toString()).append("\n").toString()));
		Connection conn = null;
		String filter = "";
		ReturnValue rv = new ReturnValue();
		if (cav == null) {
			rv.result = -500001L;
			rv.remark = "传入的参数为空";
		} else {
			try {
				conn = DAO.getConnection();

				filter = " and bankID='" + cav.bankID
						+ "' and ((createtime>=trunc(sysdate) and createtime<trunc(sysdate)+1) or (bankTime>=trunc(sysdate) and bankTime<trunc(sysdate)+1)) and status not in ("
						+ 1 + "," + 9 + ") and (type=" + 0 + " or type=" + 1 + ") ";
				if (cav.actionID > 0L) {
					filter = filter + " and actionID=" + cav.actionID;
				} else if ((cav.funID != null) && (cav.funID.trim().length() > 0)) {
					filter = filter + " and funID='" + cav.funID.trim() + "' ";
				} else {
					rv.result = -500003L;
					rv.remark = "冲正流水时市场流水号和银行流水号至少选其一";
				}
				if (rv.result == 0L) {
					Vector<CapitalValue> capList = DAO.getCapitalInfoList(filter, conn);
					if ((capList == null) || (capList.size() <= 0)) {
						rv.result = -50007L;
						rv.remark = ("查询流水不存在：市场流水号[" + cav.actionID + "]银行流水号[" + cav.funID + "]");
					} else {
						Map<String, FirmBalanceValue> firmMsg = new HashMap();
						conn.setAutoCommit(false);
						FirmBalanceValue fbv = null;
						rv.actionId = DAO.getActionID(conn);
						CapitalValue cv = new CapitalValue();
						cv.actionID = rv.actionId;
						cv.trader = "";
						cv.bankTime = cav.bankTime;
						cv.funID = cav.funIDCA;
						cv.money = 0.0D;
						cv.bankID = cav.bankID;
						cv.launcher = 1;
						cv.note = "冲正，记录号:";
						int status = 0;
						for (int i = 0; i < capList.size(); i++) {
							CapitalValue capVal = (CapitalValue) capList.get(i);
							status = capVal.status;
							fbv = null;
							if (firmMsg.get(capVal.firmID) != null) {
								fbv = (FirmBalanceValue) firmMsg.get(capVal.firmID);
							} else {
								String filter2 = " and firmid='" + capVal.firmID + "' and bankcode='" + cav.bankID + "' for update ";
								fbv = DAO.availableBalance(filter2, conn);
								if (fbv != null) {
									firmMsg.put(capVal.firmID, fbv);
								} else {
									rv.result = -500004L;
									rv.remark = ("没有查询到交易账号" + capVal.firmID);
									throw new Exception("没有查询到当个交易账号" + capVal.firmID);
								}
							}
							cv.firmID = capVal.firmID;
							cv.contact = capVal.contact;
							cv.money += capVal.money;
							cv.note = (cv.note + capVal.iD + " ");
							capVal.status = 9;
							if (capVal.type == 0) {
								cv.type = 4;
							} else if (capVal.type == 1) {
								cv.type = 5;
							}
							DAO.modCapitalInfoStatus(capVal.iD, capVal.funID + "r", capVal.status, capVal.bankTime, conn);
							DAO.modCapitalInfoNote(capVal.iD, "被银行冲正", conn);
						}
						if (fbv == null) {
							rv.result = -500004L;
							rv.remark = "没有查询到交易账号，或本交易账号当前资金不足以冲正";
							throw new Exception("在交易系统的交易账号资金表查询不到本交易账号");
						}
						if (cv.money + fbv.canOutMoney < 0.0D) {
							rv.result = -50008L;
							rv.remark = "交易账号中资金不足以冲正";
							throw new Exception("账号资金不足，不能冲正");
						}
						DAO.addCapitalInfo(cv, conn);
						if (status == 0) {
							dataProcess.updateFundsFull(cv.bankID, cv.firmID, cv.actionID + "", cv.money, cv.type, conn);
						} else if (cv.type == 5) {
							dataProcess.updateFrozenFunds(cv.firmID, -1.0D * cv.money, conn);
						}
						conn.commit();
					}
				}
			} catch (SQLException e) {
				log(Tool.getExceptionTrace(e));
				if (rv.result == 0L) {
					rv.result = -500005L;
					rv.remark = "冲正操作中出现数据库异常";
				}
				try {
					conn.rollback();
				} catch (SQLException e1) {
					log(Tool.getExceptionTrace(e1));
				}
			} catch (Exception e) {
				log(Tool.getExceptionTrace(e));
				if (rv.result == 0L) {
					rv.result = -50009L;
					rv.remark = "冲正操作中出现系统异常";
				}
				try {
					conn.rollback();
				} catch (SQLException e1) {
					log(Tool.getExceptionTrace(e1));
				}
			} finally {
				try {
					conn.setAutoCommit(true);
				} catch (SQLException e) {
					log(Tool.getExceptionTrace(e));
				}
				DAO.closeStatement(null, null, conn);
			}
		}
		return rv;
	}

	public Vector<FirmBankMsg> getfirmBankMsg(String filter) {
		Vector<FirmBankMsg> result = null;
		try {
			result = DAO.getfirmBankMsg(filter);
		} catch (SQLException e) {
			log(Tool.getExceptionTrace(e));
		} catch (Exception e) {
			log(Tool.getExceptionTrace(e));
		}
		return result;
	}

	public FirmBalanceValue getMarketBalance(String contact, String bankID) {
		FirmBalanceValue result = getMarketBalance(null, contact, bankID);
		if (result != null) {
			result.firmId = contact;
		}
		return result;
	}

	public FirmBalanceValue getMarketBalance(String firmID, String contact, String bankID) {
		FirmBalanceValue fbv = null;
		String filter = "";
		Connection conn = null;
		CorrespondValue cv = null;
		try {
			if ((firmID != null) && (firmID.trim().length() > 0)) {
				Vector<CorrespondValue> vec = DAO.getCorrespondList(" and firmID='" + firmID.trim() + "'");
				if ((vec != null) && (vec.size() > 0)) {
					cv = (CorrespondValue) vec.get(0);
				}
			} else if ((contact != null) && (contact.trim().length() > 0)) {
				Vector<CorrespondValue> vec = DAO.getCorrespondList(" and contact='" + contact.trim() + "'");
				if ((vec != null) && (vec.size() > 0)) {
					cv = (CorrespondValue) vec.get(0);
					firmID = cv.firmID;
				}
			}
			if ((firmID == null) || (firmID.trim().length() <= 0)) {
				log("查询交易账号市场余额，未获取到交易账号代码");
				FirmBalanceValue localFirmBalanceValue1 = new FirmBalanceValue();
				return localFirmBalanceValue1;
			}
			filter = filter + " and firmid='" + firmID + "' and bankcode='" + bankID + "' ";

			fbv = DAO.availableBalance(filter);
			BankValue bv = DAO.getBank(bankID);
			if (bv != null) {
				fbv.bankName = bv.bankName;
			}
			fbv.inTransitMoney = (cv == null ? 0.0D : cv.frozenFuns);
			conn = DAO.getConnection();
			fbv.canOutMoney = dataProcess.getRealFunds(bankID, firmID, 0, conn);
			if (fbv.canOutMoney < 0.0D) {
				fbv.canOutMoney = 0.0D;
			}
		} catch (SQLException e) {
			log(Tool.getExceptionTrace(e));
		} catch (Exception e) {
			log(Tool.getExceptionTrace(e));
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		log("交易账号[" + firmID + "]市场资金信息" + (fbv == null ? "市场资金查询失败" : fbv.toString()));
		return fbv;
	}

	public FirmBalanceValue getFirmBalance(String bankid, String firmid, String pwd) {
		log("查询交易账号市场可用资金和银行账号余额 getFirmBalance bankid[" + bankid + "]firmid[" + firmid + "]pwd[" + pwd + "]");
		FirmBalanceValue fv = new FirmBalanceValue();
		ReturnValue rv = ifbankTrade(bankid, firmid, null, 2, 0);
		if (rv.result < 0L) {
			fv.setCanOutMoney(rv.result);
			return fv;
		}
		FirmBalanceValue fv2 = getBankBalance(bankid, firmid, pwd);
		fv.setFirmId(firmid);
		fv.setFirmBankId(bankid);

		fv.setBankAccount(fv2.bankAccount);
		fv.setBankBalance(fv2.bankBalance);
		log("查询交易账号余额返回：\n" + fv.toString());
		return fv;
	}

	public FirmBalanceValue getBankBalance(String bankID, String firmID, String pwd) {
		log("查询交易账号银行资金 getBankBalance bankid[" + bankID + "]firmid[" + firmID + "]pwd[" + pwd + "]");
		FirmBalanceValue fv = new FirmBalanceValue();
		String filter = " and bankid='" + bankID + "' and firmid='" + firmID + "' ";
		Vector<CorrespondValue> v = null;
		try {
			v = DAO.getCorrespondList(filter);
		} catch (SQLException e) {
			log(Tool.getExceptionTrace(e));
		} catch (ClassNotFoundException e) {
			log(Tool.getExceptionTrace(e));
		}
		double accountBalance = 0.0D;
		ReturnValue rv = ifbankTrade(bankID, firmID, null, 2, 0);
		if (rv.result >= 0L) {
			log("当前银行设置允许查询余额");
			try {
				if ((v != null) && (v.size() > 0)) {
					CorrespondValue cv = (CorrespondValue) v.get(0);
					fv.setBankAccount(cv.account);
					BankAdapterRMI bankadapter = getAdapter(bankID);
					if (bankadapter != null) {
						cv.firmID = cv.contact;
						accountBalance = bankadapter.accountQuery(cv, pwd);
					} else {
						accountBalance = -920000.0D;
					}
				} else {
					log("查询余额时，未查询到交易账号信息");
				}
			} catch (RemoteException e) {
				accountBalance = -920000.0D;
				log(Tool.getExceptionTrace(e));
			} catch (Exception e) {
				accountBalance = -920000.0D;
				log(Tool.getExceptionTrace(e));
			}
		} else {
			accountBalance = rv.result;
			log(rv.toString());
		}
		fv.setBankBalance(accountBalance);
		log("查询银行余额返回：\n" + fv.toString());
		return fv;
	}

	public Vector<CapitalValue> getCapitalList(String filter) {
		log("获取市场资金流水数据 getCapitalList filter[" + filter + "]");
		Connection conn = null;
		Vector<CapitalValue> capitalList = null;
		try {
			conn = DAO.getConnection();
			capitalList = DAO.getCapitalInfoList(filter, conn);
		} catch (Exception e) {
			log(Tool.getExceptionTrace(e));
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return capitalList;
	}

	public int getBankCompareInfo(String bankID, java.util.Date date) {
		log("获取银行对账信息 getBankCompareInfo bankID[" + bankID + "]date[" + date + "]");
		int result = 0;
		if (bankID == null) {
			result = -920011;
			LogEndmsg endmsg = new LogEndmsg();
			InterfaceLog log = new InterfaceLog();
			log.account = "";
			log.bankID = bankID;
			log.contact = "";
			log.type = 20;
			endmsg.note = "取未知银行出入金流水明细失败";
			endmsg.note += ":未知的银行";
			log.result = 1;
			log.endMsg = endmsg.toString();
			interfaceLog(log);
			return result;
		}
		BankAdapterRMI bankadapter = getAdapter(bankID);
		if (bankadapter == null) {
			result = -920000;
			log("获取银行对账信息,错误码=" + result);
			LogEndmsg endmsg = new LogEndmsg();
			InterfaceLog log = new InterfaceLog();
			log.account = "";
			log.bankID = bankID;
			log.contact = "";
			log.type = 20;

			endmsg.note = "取银行出入金流水明细失败:获取适配器失败";

			log.result = 1;
			log.endMsg = endmsg.toString();
			interfaceLog(log);
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
			LogEndmsg endmsg = new LogEndmsg();
			InterfaceLog log = new InterfaceLog();
			log.account = "";
			log.bankID = bankID;
			log.contact = "";
			log.type = 20;

			endmsg.note = "取未知银行出入金流水明细失败:获取流水文件内容失败";

			log.result = 1;
			log.endMsg = endmsg.toString();
			interfaceLog(log);
			return result;
		}
		result = insertBankCompareInfo(moneyInfoList);
		if (result < 0) {
			result = -30013;
			log("插入银行转账信息失败=" + result);
			LogEndmsg endmsg = new LogEndmsg();
			InterfaceLog log = new InterfaceLog();
			log.account = "";
			log.bankID = bankID;
			log.contact = "";
			endmsg.code = "";
			log.type = 20;

			endmsg.note = "取未知银行出入金流水明细失败:添加到日志库失败";

			log.result = 1;
			log.endMsg = endmsg.toString();
			interfaceLog(log);
			return result;
		}
		LogEndmsg endmsg = new LogEndmsg();
		InterfaceLog log = new InterfaceLog();
		log.account = "";
		log.bankID = bankID;
		log.contact = "";
		endmsg.code = "0000";
		log.type = 20;

		endmsg.note = "对账成功";
		if (moneyInfoList.size() <= 0) {
			endmsg.note += "：当日没有出入金明细";
		}
		if (!"0000".equalsIgnoreCase(endmsg.code)) {
			log.result = 1;
		}
		log.endMsg = endmsg.toString();
		interfaceLog(log);
		return result;
	}

	public int insertBankCompareInfo(Vector<MoneyInfoValue> list) {
		int result = (int) insertBankMoneyInfo(list, 1);
		return result;
	}

	public long insertBankMoneyInfo(Vector<MoneyInfoValue> mv, int ready) {
		log("提供给适配器的保存银行数据的方法 insertBankMoneyInfo mv[" + mv + "]ready[" + ready + "]");
		long result = 0L;
		if ((ready != 1) && (ready != 2)) {
			log("传入的 ready [" + ready + "]");
			result = -1L;
			return result;
		}
		if (mv == null) {
			log("传入的集合为 null");
			result = -1L;
			return result;
		}
		if (mv.size() <= 0) {
			log("传入的集合中没有数据");
			return result;
		}
		MoneyInfoValue first = (MoneyInfoValue) mv.get(0);
		if ((first == null) || (first.compareDate == null)) {
			result = -1L;
			log("传入的信息中没有日期信息");
			return result;
		}
		for (int i = 1; i < mv.size(); i++) {
			if (((MoneyInfoValue) mv.get(i)).compareDate == null) {
				result = -1L;
				log("写入银行对账信息,对账日期为空");
				return result;
			}
			if (!Tool.fmtDate(first.compareDate).equals(Tool.fmtDate(((MoneyInfoValue) mv.get(i)).compareDate))) {
				result = -1L;
				log("写入银行对账信息,对账日期不相符，错误码=" + result);
				return result;
			}
		}
		Connection conn = null;
		try {
			result = mv.size();
			conn = DAO.getConnection();
			try {
				conn.setAutoCommit(false);
				DAO.delMoneyInfo(
						" and bankid='" + first.bankID + "' and trunc(compareDate)=to_date('" + Tool.fmtDate(first.compareDate) + "','yyyy-MM-dd')",
						conn);
				for (int a = 0; a < mv.size(); a++) {
					MoneyInfoValue miv = (MoneyInfoValue) mv.get(a);
					if (miv != null) {
						if (ready == 2) {
							miv.account = "-1";
							miv.status = 0;
							miv.note = "day_Netting";
						}
						DAO.addMoneyInfo(miv, conn);
					}
				}
				conn.commit();
			} catch (SQLException e) {
				conn.rollback();
				throw e;
			} finally {
				conn.setAutoCommit(true);
			}
		} catch (SQLException e) {
			result = -1L;
			log(Tool.getExceptionTrace(e));
		} catch (Exception e) {
			result = -1L;
			log(Tool.getExceptionTrace(e));
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public synchronized ReturnValue roughInfo(String bankID, java.util.Date tradeDate) {
		ReturnValue result = new ReturnValue();
		Vector<CompareResult> bankNoInfo = getBankNoInfo(bankID, tradeDate);
		Vector<CompareResult> marketNoInfo = getMarketNoInfo(bankID, tradeDate);
		Vector<Long> actionIDs = new Vector();
		Vector<CapitalValue> capitals = new Vector();
		if (bankNoInfo != null) {
			for (CompareResult cr : bankNoInfo) {
				actionIDs.add(Long.valueOf(cr.actionID));
			}
		}
		if (marketNoInfo != null) {
			CapitalValue value = null;
			for (CompareResult cr : marketNoInfo) {
				value = new CapitalValue();
				value.account = cr.account;
				value.bankID = cr.bankID;
				value.bankTime = new Timestamp(cr.tradeDate.getTime());
				value.contact = cr.contact;
				value.firmID = cr.firmID;
				value.funID = cr.funID;
				value.launcher = 0;
				value.money = cr.money;
				value.note = "日终流水";
				value.status = 0;
				value.trader = "endofday";
				value.type = cr.type;
				capitals.add(value);
			}
		}
		ReturnValue reresult = refuseCapitalInfo(actionIDs);
		ReturnValue suresult = supplyCapitalInfo(capitals);
		if (reresult.result < 0L) {
			result.result = reresult.result;
			result.remark += reresult.remark;
		}
		if (suresult.result < 0L) {
			result.result = suresult.result;
			result.remark += suresult.remark;
		}
		if (result.result >= 0L) {
			result.remark = "全部对账流水处理成功";
		}
		LogEndmsg endmsg = new LogEndmsg();
		InterfaceLog log = new InterfaceLog();
		log.account = "";
		log.bankID = bankID;
		log.contact = "";
		if (result.result == 0L) {
			endmsg.code = "0000";
		} else {
			endmsg.code = "";
		}
		log.type = 21;
		endmsg.note = result.remark;
		if (!"0000".equalsIgnoreCase(endmsg.code)) {
			log.result = 1;
		}
		log.endMsg = endmsg.toString();
		interfaceLog(log);
		return result;
	}

	public Vector<CompareResult> getBankNoInfo(String bankID, java.util.Date tradeDate) {
		Vector<CompareResult> result = null;
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			result = DAO.getBankNoInfo(bankID, tradeDate, conn, null);
		} catch (SQLException e) {
			log(Tool.getExceptionTrace(e));
		} catch (Exception e) {
			log(Tool.getExceptionTrace(e));
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public Vector<CompareResult> getMarketNoInfo(String bankID, java.util.Date tradeDate) {
		Vector<CompareResult> result = null;
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			result = DAO.getMarketNoInfo(bankID, tradeDate, conn, null);
		} catch (SQLException e) {
			log(Tool.getExceptionTrace(e));
		} catch (Exception e) {
			log(Tool.getExceptionTrace(e));
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public Vector<CapitalCompare> sumResultInfo(String bankID, String[] firmIDs, java.util.Date date) {
		log("查询交易账号当天出入金求和数据 bankID[" + bankID + "]firmIDs[" + firmIDs + "]date[" + date + "]");
		Vector<CapitalCompare> result = null;
		try {
			result = DAO.sumResultInfo(bankID, firmIDs, date);
		} catch (SQLException e) {
			log(Tool.getExceptionTrace(e));
		} catch (Exception e) {
			log(Tool.getExceptionTrace(e));
		}
		return result;
	}

	public ReturnValue refuseCapitalInfo(Vector<Long> actionIDs) {
		ReturnValue result = new ReturnValue();
		if (actionIDs != null) {
			for (Long actionID : actionIDs) {
				if (actionID != null) {
					ReturnValue rv = refuseCapitalInfo(actionID.longValue());
					if (rv.result < 0L) {
						result.result = -1L;
						result.remark = ("[" + actionID + "]");
					}
				}
			}
		}
		if (result.result != 0L) {
			result.remark = ("流水" + result.remark + "冲正失败");
		}
		return result;
	}

	public ReturnValue refuseCapitalInfo(long actionID) {
		log("冲正银行没有成功的流水[" + actionID + "]");
		ReturnValue result = new ReturnValue();
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			try {
				conn.setAutoCommit(false);
				Vector<CapitalValue> value = DAO
						.getCapitalInfoList(" and (type=0 or type=1) and status<>1 and status<>9 and ACTIONID=" + actionID + " for update ", conn);
				if ((value == null) || (value.size() <= 0)) {
					result.result = -920009L;
					result.remark = ("当比流水[" + actionID + "]已经受理");
				} else {
					for (CapitalValue cv : value) {
						DAO.modCapitalInfoStatus(cv.iD, "refuse" + cv.iD, 9, null, conn);
						DAO.modCapitalInfoNote(cv.iD, "日终被冲正", conn);
						DAO.modCapitalInfoTrader(cv.iD, "endofday", conn);
						if ((cv.type == 0) && (cv.status == 0)) {
							dataProcess.updateFundsFull(cv.bankID, cv.firmID, cv.actionID + "", cv.money, 4, conn);
						} else if (cv.type == 1) {
							if (cv.status == 0) {
								dataProcess.updateFundsFull(cv.bankID, cv.firmID, cv.actionID + "", cv.money, 5, conn);
							} else {
								dataProcess.updateFrozenFunds(cv.firmID, -1.0D * cv.money, conn);
								bankFrozenFuns(cv.firmID, cv.contact, cv.bankID, cv.account, -1.0D * cv.money, conn);
							}
						}
					}
				}
				conn.commit();
			} catch (SQLException e) {
				conn.rollback();
				result.result = -920015L;
				result.remark = ("修改流水[" + actionID + "]资金时数据库异常");
				throw e;
			} finally {
				conn.setAutoCommit(true);
			}
		} catch (SQLException e) {
			result.result = -920015L;
			result.remark = ("冲正流水[" + actionID + "]数据库异常");
			log(Tool.getExceptionTrace(e));
		} catch (Exception e) {
			result.result = -920016L;
			result.remark = ("重整流水[" + actionID + "]系统异常");
			log(Tool.getExceptionTrace(e));
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public ReturnValue supplyCapitalInfo(Vector<CapitalValue> vector) {
		ReturnValue result = new ReturnValue();
		result.remark = "";
		if ((vector != null) && (vector.size() > 0)) {
			for (CapitalValue cv : vector) {
				if (cv != null) {
					ReturnValue cvresult = supplyCapitalInfo(cv);
					if (cvresult.result < 0L) {
						result.result = -1L;
						ReturnValue tmp74_73 = result;
						tmp74_73.remark = (tmp74_73.remark + "[" + cv.funID + "]");
					}
				}
			}
		}
		if (result.result != 0L) {
			result.remark = ("流水" + result.remark + "导入失败");
		}
		return result;
	}

	public ReturnValue supplyCapitalInfo(CapitalValue value) {
		log("添加成功资金流水");
		ReturnValue result = new ReturnValue();
		Connection conn = null;
		if (value == null) {
			result.result = -920009L;
			result.remark = "传入对象为空";
		} else if ((value.funID == null) || (value.funID.trim().length() <= 0)) {
			result.result = -920009L;
			result.remark = "传入银行流水号为空";
		} else if ((value.bankID == null) || (value.bankID.trim().length() <= 0)) {
			result.result = -920009L;
			result.remark = "传入银行代码为空";
		} else if ((value.firmID == null) || (value.firmID.trim().length() <= 0)) {
			result.result = -920009L;
			result.remark = "传入交易账号为空";
		} else if ((value.contact == null) || (value.contact.trim().length() <= 0)) {
			result.result = -920009L;
			result.remark = "传入和银行绑定号为空";
		} else if (value.money <= 0.0D) {
			result.result = -920009L;
			result.remark = "传入资金必须大于0";
		} else if (value.bankTime == null) {
			result.result = -920009L;
			result.remark = "传入的银行时间不合法";
		} else {
			try {
				conn = DAO.getConnection();
				try {
					conn.setAutoCommit(false);
					Vector<CapitalValue> cvlist = DAO.getCapitalInfoList(" and bankid='" + value.bankID + "' and funid='" + value.funID
							+ "' and trunc(BANKTIME)=to_date('" + Tool.fmtDate(value.bankTime) + "','yyyy-MM-dd')", conn);
					if ((cvlist != null) && (cvlist.size() > 0)) {
						result.result = -920009L;
						result.remark = "当笔流水已经处理";
					} else {
						value.actionID = DAO.getActionID();
						value.status = 0;
						result.result = DAO.addCapitalInfo(value, conn);
						dataProcess.updateFundsFull(value.bankID, value.firmID, value.actionID + "", value.money, value.type, conn);
						result.remark = ("流水[" + result.result + "]金额[" + value.money + "]类型[" + value.type + "]");
					}
					conn.commit();
				} catch (SQLException e) {
					conn.rollback();
					throw e;
				} finally {
					conn.setAutoCommit(true);
				}
			} catch (SQLException e) {
				result.result = -920015L;
				result.remark = "添加成功资金流水数据库异常";
				log(Tool.getExceptionTrace(e));
			} catch (Exception e) {
				result.result = -920016L;
				result.remark = "添加成功资金流水系统异常";
				log(Tool.getExceptionTrace(e));
			} finally {
				DAO.closeStatement(null, null, conn);
			}
		}
		log("插入单条银行单边流水：\n" + result.toString());
		return result;
	}

	public Vector<InterfaceLog> interfaceLogList(String filter, int[] pageinfo) {
		Vector<InterfaceLog> result = null;
		try {
			result = DAO.interfaceLogList(filter, pageinfo);
		} catch (Exception e) {
			log(Tool.getExceptionTrace(e));
		}
		return result;
	}

	public int interfaceLog(InterfaceLog log) {
		int result = 0;
		try {
			result = DAO.interfaceLog(log);
		} catch (Exception e) {
			log(Tool.getExceptionTrace(e));
		}
		return result;
	}

	public int getDataFile(String bankId, java.util.Date date) {
		return 0;
	}

	public int sendDataFile(String bankId, java.util.Date date) {
		BankAdapterRMI bankadapter = getAdapter(bankId);
		ExchangeData exdata = null;
		String clazz = "gnnt.trade.bank.data.CEBExDataImpl";
		try {
			exdata = (ExchangeData) Class.forName(clazz).newInstance();
		} catch (Exception e) {
			log(Tool.getExceptionTrace(e));
		}
		return exdata.createDataFile(bankadapter, date);
	}

	public ReturnValue relevanceAccount(CorrespondValue cv) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{银行发起绑定，市场将银行子账号和子账号名称等添加进去{{{{{{{{{{{{{{{{{{{{{{{{");

		System.out.println(cv.toString());
		System.out.println("}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}");
		ReturnValue rv = new ReturnValue();
		if (cv == null) {
			rv.result = -40001L;
			rv.remark = "银行发起绑定，传入的信息包为空";

			return rv;
		}
		if (cv.account1 == null) {
			rv.result = -40001L;
			rv.remark = ((String) ErrorCode.error.get(Long.valueOf(-40001L)));

			return rv;
		}
		if (cv.firmID == null) {
			rv.result = -40002L;
			rv.remark = ((String) ErrorCode.error.get(Long.valueOf(-40002L)));

			return rv;
		}
		try {
			synchronized (cv.firmID) {
				Connection conn = null;
				try {
					if ((cv.contact == null) || (cv.contact.trim().length() <= 0)) {
						cv.contact = cv.firmID;
					}
					List<CorrespondValue> cvresult = DAO.getCorrespondList(" and bankID='" + cv.bankID + "' and CONTACT='" + cv.contact + "'");
					ReturnValue localReturnValue4;
					if ((cvresult == null) || (cvresult.size() <= 0)) {
						cvresult = DAO.getCorrespondList(" and trim(contact)='" + cv.contact.trim() + "' and trim(bankID) is null ");
						if ((cvresult != null) && (cvresult.size() > 0)) {
							CorrespondValue corr = (CorrespondValue) cvresult.get(0);
							corr.account = cv.account;
							corr.isOpen = 1;
							corr.status = 0;
							corr.accountName = cv.accountName;
							corr.card = cv.card;
							corr.cardType = cv.cardType;
							corr.account1 = cv.account1;
							corr.bankID = cv.bankID;
							rv.actionId = getMktActionID();
							conn = DAO.getConnection();
							try {
								conn.setAutoCommit(false);
								rv = dataProcess.changeFirmIsOpen(corr.firmID, 1, corr.bankID, conn);
								if (rv.result < 0L) {
									conn.rollback();
									ReturnValue localReturnValue1 = rv;

									conn.setAutoCommit(true);

									DAO.closeStatement(null, null, conn);
									return localReturnValue1;
								}
								DAO.openCorrespond(corr, conn);
								conn.commit();
							} catch (SQLException er) {
								conn.rollback();
								throw er;
							} finally {
								conn.setAutoCommit(true);
							}
							conn.setAutoCommit(true);
						} else {
							Vector<FirmValue> vfv = DAO.getFirmList(" and CONTACT='" + cv.contact + "'");
							if ((vfv == null) || (vfv.size() != 1)) {
								rv.result = -40002L;
								rv.remark = "通过签约号查询客户信息异常";
								localReturnValue4 = rv;

								DAO.closeStatement(null, null, conn);
								return localReturnValue4;
							}
							FirmValue fv = (FirmValue) vfv.get(0);
							if ((cv.card == null) || (!cv.card.equals(fv.card))) {
								rv.result = -20020L;
								rv.remark = "客户证件号码错误";
								localReturnValue4 = rv;

								DAO.closeStatement(null, null, conn);
								return localReturnValue4;
							}
							cv.firmID = fv.firmID;
							cv.contact = fv.contact;
							cv.frozenFuns = 0.0D;
							cv.isOpen = 1;
							cv.status = 0;
							cv.opentime = new java.util.Date();
							rv.actionId = getMktActionID();
							conn = DAO.getConnection();
							try {
								conn.setAutoCommit(false);
								rv = dataProcess.changeFirmIsOpen(fv.firmID, 1, cv.bankID, conn);
								if (rv.result < 0L) {
									conn.rollback();
									ReturnValue localReturnValue3 = rv;

									conn.setAutoCommit(true);

									DAO.closeStatement(null, null, conn);
									return localReturnValue3;
								}
								DAO.addCorrespond(cv, conn);
								conn.commit();
							} catch (SQLException er) {
								conn.rollback();
								throw er;
							} finally {
								conn.setAutoCommit(true);
							}
							conn.setAutoCommit(true);
						}
					} else {
						if (cvresult.size() != 1) {
							rv.result = -920014L;
							rv.remark = "通过银行编号、签约号查询出信息重复";
							localReturnValue4 = rv;

							DAO.closeStatement(null, null, conn);
							return localReturnValue4;
						}
						CorrespondValue cv2 = (CorrespondValue) cvresult.get(0);
						if ((cv.card == null) || (!cv.card.equals(cv2.card))) {
							rv.result = -20020L;
							rv.remark = "客户证件号码错误";
							localReturnValue4 = rv;

							DAO.closeStatement(null, null, conn);
							return localReturnValue4;
						}
						rv.actionId = getMktActionID();
						conn = DAO.getConnection();
						try {
							conn.setAutoCommit(false);
							rv = dataProcess.changeFirmIsOpen(cv2.firmID, 1, cv2.bankID, conn);
							if (rv.result < 0L) {
								conn.rollback();
								ReturnValue localReturnValue2 = rv;

								conn.setAutoCommit(true);

								DAO.closeStatement(null, null, conn);
								return localReturnValue2;
							}
							cv.contact = cv2.contact;
							cv.id = cv2.id;
							DAO.openCorrespond(cv, conn);
							conn.commit();
						} catch (SQLException er) {
							conn.rollback();
							throw er;
						} finally {
							conn.setAutoCommit(true);
						}
						conn.setAutoCommit(true);
					}
				} catch (SQLException e) {
					rv.result = -40006L;
					rv.remark = "签约时数据库异常";
					log("银行开户 交易账号代码与银行账号对应SQLException," + Tool.getExceptionTrace(e));
				} catch (Exception e) {
					rv.result = -40007L;
					rv.remark = "签约时系统异常";
					log("银行开户 交易账号代码与银行账号对应SQLException," + Tool.getExceptionTrace(e));
				} finally {
					DAO.closeStatement(null, null, conn);
				}
			}
		} catch (Exception e) {
			rv.result = -40007L;
			rv.remark = "处理器修改交易商信息异常";
			e.printStackTrace();
		}
		return rv;
	}

	public ReturnValue synchroAccount(CorrespondValue correspondValue) {
		log("华夏预签约华夏银行 synchroAccount correspondValue:"
				+ (correspondValue == null ? "为 null" : new StringBuilder("\n").append(correspondValue.toString()).append("\n").toString()));
		ReturnValue result = new ReturnValue();

		String defaultAccount = Tool.getConfig("DefaultAccount");
		Connection conn = null;
		if ((correspondValue.bankID == null) || (correspondValue.bankID.length() == 0) || (correspondValue.firmID == null)
				|| (correspondValue.firmID.length() == 0)) {
			result.result = -640000L;
			result.remark = "华夏预签约失败，信息不完整";

			return result;
		}
		try {
			conn = DAO.getConnection();
			BankValue bv = DAO.getBank(correspondValue.bankID);
			ReturnValue localReturnValue1;
			if (bv.validFlag != 0) {
				result.result = -920017L;
				result.remark = "华夏预签约失败，银行被禁用";
				log("华夏预签约，银行被禁用");

				localReturnValue1 = result;
				return localReturnValue1;
			}
			int tradeFlag = getTradeTime(bv.beginTime, bv.endTime);
			if (tradeFlag == 1) {
				result.result = -920004L;
				result.remark = "华夏预签约失败，交易时间未到";
				log("华夏预签约失败，交易时间未到");

				localReturnValue1 = result;
				return localReturnValue1;
			}
			if (tradeFlag == 2) {
				result.result = -920005L;
				result.remark = "华夏预签约失败，交易时间已过";
				log("华夏预签约失败，交易时间已过");

				localReturnValue1 = result;
				return localReturnValue1;
			}
			if (DAO.getFirm(correspondValue.firmID, conn) == null) {
				result.result = -640002L;
				result.remark = "华夏预签约失败，交易商不存在";
				log("华夏预签约失败，交易账号不存在，错误码=" + result.result);

				localReturnValue1 = result;
				return localReturnValue1;
			}
			if (DAO.getCorrespondList(" and bankID='" + correspondValue.bankID + "' and firmID='" + correspondValue.firmID + "' and (account='"
					+ correspondValue.account + "' or account='" + defaultAccount + "') and isopen=1", conn).size() > 0) {
				result.result = -640004L;
				result.remark = "华夏预签约失败，账号已注册";
				log("华夏预签约失败，账号已注册，错误码=" + result.result);

				localReturnValue1 = result;
				return localReturnValue1;
			}
			if (DAO.getCorrespondList(" and firmID='" + correspondValue.firmID + "' ", conn).size() > 0) {
				BankAdapterRMI bankadapter = getAdapter(correspondValue.bankID);
				ReturnValue returnValue = bankadapter.synchroAccount(correspondValue);
				if ((returnValue != null) && (returnValue.result == 0L)) {
					log("华夏预签约状态" + result);
				} else {
					log(returnValue.remark);
					result.result = -640001L;
					result.remark = returnValue.remark;
					log("华夏预签约失败，错误码=" + result);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			result.result = -640005L;
			result.remark = "华夏预签约错误，数据库操作失败";
			log("华夏预签约异常，错误码=" + Tool.getExceptionTrace(e));
		} catch (Exception e) {
			e.printStackTrace();
			result.result = -640006L;
			result.remark = "华夏预签约错误，系统异常";
			log("华夏预签约Exception，错误码" + Tool.getExceptionTrace(e));
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	protected int getTradeTime(String startTime, String endTime) {
		log("判断是否超出了交易时间范围");
		int result = 1;
		if ((startTime == null) || (startTime.trim().length() <= 0) || (endTime == null) || (endTime.trim().length() <= 0)) {
			return 0;
		}
		startTime = startTime.trim();
		endTime = endTime.trim();
		if (startTime.length() < 6) {
			for (int i = 0; i < 6 - startTime.length(); i++) {
				startTime = startTime + "0";
			}
		}
		if (endTime.length() < 6) {
			for (int i = 0; i < 6 - startTime.length(); i++) {
				endTime = endTime + "0";
			}
		}
		java.util.Date now = new java.util.Date();
		java.util.Date start = Tool.getDate(startTime);
		java.util.Date end = Tool.getDate(endTime);
		if (now.getTime() <= start.getTime()) {
			result = 1;
		} else if (now.getTime() >= end.getTime()) {
			result = 2;
		} else {
			result = 0;
		}
		return result;
	}

	public Vector<FirmValue> getFirmUserList(String filter, int[] pageinfo, String type, String key, String bankid) {
		log("查询客户信息表以及客户和银行对应表的数据\n" + filter);
		Vector<FirmValue> result = null;
		try {
			DAO.pageinfo = pageinfo;
			result = DAO.getFirmList3(filter, type, key, bankid);
		} catch (Exception e) {
			log(Tool.getExceptionTrace(e));
			result = new Vector();
		}
		return result;
	}

	public int[] getPageinfo() {
		int[] pageinfo = new int[4];
		try {
			pageinfo = DAO.pageinfo;
		} catch (Exception e) {
			log(Tool.getExceptionTrace(e));
		}
		return pageinfo;
	}

	public static void main(String[] args) {
		StartupRmiserver sr = new StartupRmiserver();
		try {
			sr.init();
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}

	public ReturnValue outMoneyAudit(long actionID, boolean funFlag) {
		log("{{{{{{{{{{{{{{{{{{{{{{出金审核{{{{{{{{{{{{{{{{{{{{{{{{");
		log("市场流水号：" + actionID + " 审核结果：" + (funFlag ? "通过" : "驳回"));
		log("}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}}");
		String auditInfo = "审核业务流水号：" + actionID + ";";

		Connection conn = null;

		Timestamp curTime = new Timestamp(System.currentTimeMillis());

		CapitalValue capital = null;

		ReturnValue rVal = null;

		ReturnValue result = new ReturnValue();
		result.result = 0L;
		if ((Tool.getConfig("AuditBeginTime") != null) && (Tool.getConfig("AuditBeginTime").length() > 0)) {
			Time AuditBeginTime = Tool.convertTime(Tool.getConfig("AuditBeginTime"));
			Time curSqlTime = Tool.convertTime(Tool.fmtOnlyTime(curTime));
			if ((AuditBeginTime != null) && (AuditBeginTime.after(curSqlTime))) {
				result.result = -20018L;
				result.remark = "出金审核时间未到";
				log("审核时间未到！");
				return result;
			}
		}
		if ((Tool.getConfig("AuditEndTime") != null) && (Tool.getConfig("AuditEndTime").length() > 0)) {
			Time AuditEndTime = Tool.convertTime(Tool.getConfig("AuditEndTime"));
			Time curSqlTime = Tool.convertTime(Tool.fmtOnlyTime(curTime));
			if ((AuditEndTime != null) && (curSqlTime.after(AuditEndTime))) {
				result.result = -20017L;
				result.remark = "出金审核时间已过";
				log("审核时间已过！");
				return result;
			}
		}
		try {
			conn = DAO.getConnection();

			Vector<CapitalValue> list = DAO.getCapitalInfoList("and actionID='" + actionID + "'", conn);
			for (int i = 0; i < list.size(); i++) {
				CapitalValue val = (CapitalValue) list.get(i);
				if (val.type == 1) {
					capital = val;
				}
			}
			if (capital != null) {
				Vector<CorrespondValue> csv = DAO.getCorrespondList(" and bankid='" + capital.bankID + "' and firmid='" + capital.firmID + "'", conn);

				TransferInfoValue payInfo = getPayInfo(capital.bankID, capital.firmID, 1, conn);

				TransferInfoValue receiveInfo = getReceiveInfo(capital.bankID, capital.firmID, 1, conn);
				BankValue bv = DAO.getBank(capital.bankID);
				OutMoneyVO outMoneyInfo = new OutMoneyVO(capital.bankID, bv.bankName, capital.money, capital.contact, payInfo, receiveInfo, actionID,
						capital.funID);
				outMoneyInfo.isCrossline = String.valueOf(((CorrespondValue) csv.get(0)).isCrossline);
				ReturnValue localReturnValue2;
				ReturnValue localReturnValue1;
				if (funFlag) {
					long number = tradeDate(capital.bankID);
					if (number != 0L) {
						result.result = number;
						result.remark = ((String) ErrorCode.error.get(Long.valueOf(number)));
						ReturnValue localReturnValue3 = result;
						return localReturnValue3;
					}
					if (result.result == 0L) {
						try {
							if ((capital.note != null) && (capital.note.startsWith("市场端出金"))) {
								BankAdapterRMI bankadapter = getAdapter(capital.bankID);
								if (bankadapter == null) {
									result.result = -920000L;
									result.remark = "处理器获取适配器为空";
									log("处理器连接适配器[" + capital.bankID + "]失败");
									localReturnValue2 = result;

									conn.setAutoCommit(true);
									return localReturnValue2;
								}
								try {
									conn.setAutoCommit(false);
									Vector<CapitalValue> ll = DAO
											.getCapitalInfoList("and actionID='" + actionID + "' and status=" + 3 + " for update", conn);
									if ((ll == null) || (ll.size() <= 0)) {
										result.result = -20042L;
										result.remark = "信息已经在处理中";
										log("信息已在处理中");
										localReturnValue1 = result;

										conn.setAutoCommit(true);

										conn.setAutoCommit(true);

										DAO.closeStatement(null, null, conn);
										return localReturnValue1;
									}
									DAO.modCapitalInfoStatus(capital.iD, capital.funID, 2, curTime, conn);
									conn.commit();
								} catch (SQLException e) {
									conn.rollback();
									e.printStackTrace();
									result.result = -20015L;
									result.remark = "出金审核SQL异常";
									log("修改记录为处理中失败，SQL异常");
									localReturnValue1 = result;

									conn.setAutoCommit(true);

									conn.setAutoCommit(true);

									DAO.closeStatement(null, null, conn);
									return localReturnValue1;
								} finally {
									conn.setAutoCommit(true);
								}
								conn.setAutoCommit(true);

								rVal = bankadapter.outMoneyMarketDone(outMoneyInfo);
							}
							if ((capital.note != null) && (capital.note.startsWith("银行端出金"))) {
								BankAdapterRMI bankadapter = getAdapter(capital.bankID);
								if (bankadapter == null) {
									result.result = -20005L;
									result.remark = "交易商发起出金，系统异常";
									log("处理器连接适配器[" + capital.bankID + "]失败");
									localReturnValue2 = result;

									conn.setAutoCommit(true);
									return localReturnValue2;
								}
								try {
									conn.setAutoCommit(false);
									Vector<CapitalValue> ll = DAO
											.getCapitalInfoList("and actionID='" + actionID + "' and status=" + 3 + " for update", conn);
									if ((ll == null) || (ll.size() <= 0)) {
										result.result = -20042L;
										result.remark = "信息已经在处理中";
										log("信息已在处理中");
										localReturnValue1 = result;

										conn.setAutoCommit(true);

										conn.setAutoCommit(true);

										DAO.closeStatement(null, null, conn);
										return localReturnValue1;
									}
									DAO.modCapitalInfoStatus(capital.iD, capital.funID, 2, curTime, conn);
									conn.commit();
								} catch (SQLException e) {
									conn.rollback();
									e.printStackTrace();
									result.result = -20015L;
									result.remark = "修改流水状态为处理中，SQL异常";
									log("修改记录为处理中失败，SQL异常");
									localReturnValue1 = result;

									conn.setAutoCommit(true);

									conn.setAutoCommit(true);

									DAO.closeStatement(null, null, conn);
									return localReturnValue1;
								} finally {
									conn.setAutoCommit(true);
								}
								conn.setAutoCommit(true);

								rVal = bankadapter.outMoneyBackBank(outMoneyInfo, true);
							}
							conn.setAutoCommit(false);
							if (rVal.result == 0L) {
								dataProcess.updateFundsFull(capital.bankID, capital.firmID, actionID + "", capital.money, 1, conn);
								dataProcess.updateFrozenFunds(capital.firmID, -1.0D * capital.money, conn);
								bankFrozenFuns(capital.firmID, capital.contact, capital.bankID, null, -1.0D * capital.money, conn);
								DAO.modCapitalInfoStatus(capital.iD, rVal.funID, 0, curTime, conn);
								DAO.modCapitalInfoNote(capital.iD, "出金成功", conn);

								log(auditInfo + "审核通过，银行处理成功,出金成功,扣除手续费成功,银行流水号=" + rVal.funID);
							} else if (rVal.result == 5L) {
								result.result = rVal.result;
								DAO.modCapitalInfoStatus(capital.iD, rVal.funID, 2, curTime, conn);
								DAO.modCapitalInfoNote(capital.iD, "[" + capital.note + "]审核通过，银行处理成功，等待处理中", conn);

								log(auditInfo + "审核通过，银行处理成功，等待处理中");
							} else if (rVal.result == -50010L) {
								result.result = rVal.result;
								DAO.modCapitalInfoStatus(capital.iD, rVal.funID, 5, curTime, conn);
								DAO.modCapitalInfoNote(capital.iD, "[" + capital.note + "]审核通过，银行无返回", conn);

								log(auditInfo + "审核通过，银行无返回");
							} else if (rVal.result == -920008L) {
								result.result = rVal.result;
								DAO.modCapitalInfoStatus(capital.iD, rVal.funID, 6, curTime, conn);
								DAO.modCapitalInfoNote(capital.iD, "[" + capital.note + "]审核通过，银行返回的市场流水号和市场本身流水不一致", conn);

								log(auditInfo + "审核通过，银行返回的市场流水号和市场本身流水不一致");
							} else {
								result.result = rVal.result;
								if (!"false".equalsIgnoreCase(Tool.getConfig("OutFailProcess"))) {
									dataProcess.updateFrozenFunds(capital.firmID, -1.0D * Arith.add(capital.money, 0.0D), conn);

									bankFrozenFuns(capital.firmID, capital.contact, capital.bankID, null, -1.0D * Arith.add(capital.money, 0.0D),
											conn);
									DAO.modCapitalInfoStatus(capital.iD, rVal.funID, 1, curTime, conn);
									DAO.modCapitalInfoNote(capital.iD, "[" + capital.note + "]审核通过，银行处理失败，出金金额已解冻", conn);
									log(auditInfo + "审核通过，银行处理失败,退还全部出金和手续费，错误码=" + result);
								} else {
									DAO.modCapitalInfoStatus(capital.iD, rVal.funID, 1, curTime, conn);
									DAO.modCapitalInfoNote(capital.iD, "[" + capital.note + "]审核通过，银行处理失败，需手工解冻出金金额", conn);

									log(auditInfo + "审核通过，银行处理失败，需手工解冻出金和手续费，错误码=" + result);
								}
							}
							conn.commit();
						} catch (SQLException e) {
							e.printStackTrace();
							conn.rollback();
							result.result = -20013L;
							result.remark = "审核出金SQLException，数据回滚，需要手工处理出金和手续费";
							log(auditInfo + "审核出金SQLException，数据回滚，需要手工处理出金和手续费，错误码=" + result + "  " + e);
						} finally {
							conn.setAutoCommit(true);
						}
					}
					if (result.result == -20013L) {
						DAO.modCapitalInfoStatus(capital.iD, capital.funID, 1, curTime, conn);
						DAO.modCapitalInfoNote(capital.iD, "[" + capital.note + "]审核通过，系统异常，需手工处理出金", conn);
					}
				} else {
					try {
						BankAdapterRMI bankadapter;
						if ((capital.note != null) && (capital.note.startsWith("银行端出金"))) {
							long number = tradeDate(capital.bankID);
							if (number != 0L) {
								result.result = number;
								result.remark = ((String) ErrorCode.error.get(Long.valueOf(number)));
								localReturnValue2 = result;

								conn.setAutoCommit(true);
								return localReturnValue2;
							}
							bankadapter = getAdapter(capital.bankID);
							if (bankadapter == null) {
								result.result = -920000L;
								result.remark = "处理器获取适配器失败";
								log("处理器连接适配器[" + capital.bankID + "]失败");
								localReturnValue2 = result;

								conn.setAutoCommit(true);
								return localReturnValue2;
							}
							try {
								conn.setAutoCommit(false);
								Vector<CapitalValue> ll = DAO.getCapitalInfoList("and actionID='" + actionID + "' and status=" + 3 + " for update",
										conn);
								if ((ll == null) || (ll.size() <= 0)) {
									result.result = -20042L;
									result.remark = "信息已经在处理中";
									log("信息已在处理中");
									localReturnValue1 = result;

									conn.setAutoCommit(true);

									conn.setAutoCommit(true);

									DAO.closeStatement(null, null, conn);
									return localReturnValue1;
								}
								DAO.modCapitalInfoStatus(capital.iD, capital.funID, 2, curTime, conn);
								conn.commit();
							} catch (SQLException e) {
								conn.rollback();
								e.printStackTrace();
								result.result = -20015L;
								result.remark = "修改流水状态为处理中失败，SQL异常";
								log("修改记录为处理中失败，SQL异常");
								localReturnValue1 = result;

								conn.setAutoCommit(true);

								conn.setAutoCommit(true);

								DAO.closeStatement(null, null, conn);
								return localReturnValue1;
							} finally {
								conn.setAutoCommit(true);
							}
							conn.setAutoCommit(true);

							rVal = bankadapter.outMoneyBackBank(outMoneyInfo, false);
						} else {
							try {
								conn.setAutoCommit(false);
								Vector<CapitalValue> ll = DAO.getCapitalInfoList("and actionID='" + actionID + "' and status=" + 3 + " for update",
										conn);
								if ((ll == null) || (ll.size() <= 0)) {
									result.result = -20042L;
									result.remark = "信息已经在处理中";
									log("信息已在处理中");

									conn.setAutoCommit(true);

									conn.setAutoCommit(true);

									DAO.closeStatement(null, null, conn);
									return result;
								}
								DAO.modCapitalInfoStatus(capital.iD, capital.funID, 2, curTime, conn);
								conn.commit();
							} catch (SQLException e) {
								conn.rollback();
								e.printStackTrace();
								result.result = -20015L;
								result.remark = "修改流水状态为处理中失败，SQL异常";
								log("修改记录为处理中失败，SQL异常");

								conn.setAutoCommit(true);

								conn.setAutoCommit(true);

								DAO.closeStatement(null, null, conn);
								return result;
							} finally {
								conn.setAutoCommit(true);
							}
							conn.setAutoCommit(true);

							rVal = new ReturnValue();
							rVal.result = 0L;
						}
						conn.setAutoCommit(false);
						if (rVal.result == 0L) {
							dataProcess.updateFrozenFunds(capital.firmID, -1.0D * Arith.add(capital.money, 0.0D), conn);

							bankFrozenFuns(capital.firmID, capital.contact, capital.bankID, null, -1.0D * Arith.add(capital.money, 0.0D), conn);
							DAO.modCapitalInfoStatus(capital.iD, rVal == null ? "" : rVal.funID, 1, curTime, conn);

							DAO.modCapitalInfoNote(capital.iD, "[" + capital.note + "]审核拒绝成功", conn);
						} else if (rVal.result == 5L) {
							result.result = rVal.result;
							log("拒绝 " + capital.firmID + " 交易商的银行出金 " + actionID + " ，银行返回处理中");
						} else if (rVal.result == -50010L) {
							result.result = rVal.result;
							DAO.modCapitalInfoStatus(capital.iD, rVal == null ? "" : rVal.funID, 5, curTime, conn);

							DAO.modCapitalInfoNote(capital.iD, "[" + capital.note + "]审核拒绝，适配器发送给银行，银行无返回", conn);
						} else if (rVal.result == -920008L) {
							result.result = rVal.result;
							DAO.modCapitalInfoStatus(capital.iD, rVal == null ? "" : rVal.funID, 6, curTime, conn);

							DAO.modCapitalInfoNote(capital.iD, "[" + capital.note + "]审核拒绝，适配器发送给银行，银行返回市场流水号异常", conn);
						} else {
							result.result = rVal.result;
							log("拒绝 " + capital.firmID + " 交易商的银行出金 " + actionID + " ，银行处理失败，适配器返回码：" + rVal.result);
						}
						log(auditInfo + "审核拒绝成功");
						conn.commit();
					} catch (SQLException e) {
						e.printStackTrace();
						conn.rollback();
						result.result = -20014L;
						result.remark = "出金审核拒绝SQL异常，数据回滚";
						log(auditInfo + "审核拒绝出金SQLException，数据回滚，需要手工处理出金和手续费，错误码=" + result + "  " + e);
					} finally {
						conn.setAutoCommit(true);
					}
					if (result.result == -20014L) {
						DAO.modCapitalInfoStatus(capital.iD, rVal.funID, 1, curTime, conn);
						DAO.modCapitalInfoNote(capital.iD, "[" + capital.note + "]审核拒绝出金 资金解冻出错，需要手工处理出金", conn);
						log(auditInfo + "审核拒绝出金 资金解冻出错，需要手工处理出金");
					}
				}
			} else {
				result.result = -20011L;
				result.remark = ("没有发现流水号为：" + actionID + "的记录");
				log(auditInfo + "没有发现业务流水号为：" + actionID + "的资金流水");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			result.result = -30004L;
			result.remark = "出金审核SQL异常";
			log(auditInfo + "审核出金SQLException，错误码=" + result + "  " + e);
		} catch (Exception e) {
			e.printStackTrace();
			result.result = -30005L;
			result.remark = "审核出金异常";
			log(auditInfo + "审核出金Exception，错误码=" + result + "  " + e);
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public String getfirmid(String contact) {
		log("通过contact[" + contact + "]获取firmid");
		String firmids = null;
		Vector<FirmValue> firmivalues = null;
		try {
			firmivalues = DAO.getFirmList("and contact ='" + contact + "'");
		} catch (SQLException e) {
			log(Tool.getExceptionTrace(e));
		} catch (ClassNotFoundException e) {
			log(Tool.getExceptionTrace(e));
		}
		if ((firmivalues != null) && (firmivalues.size() > 0)) {
			firmids = ((FirmValue) firmivalues.get(0)).firmID;
		}
		return firmids;
	}

	public long modBankQS(ReturnValue rv, java.util.Date date) {
		long rs = -1L;
		if (rv.result == 8L) {
			rs = modBankQS(CCBConstant.bankID, 0, date);
		} else if (rv.result == 6L) {
			rs = modBankQS(CCBConstant.bankID, 2, date);
		} else {
			rs = modBankQS(CCBConstant.bankID, 1, date);
		}
		if (rs == 0L) {
			rv.remark += "[清算结果更新成功]";
		} else {
			rv.remark += "[清算结果更新失败]";
		}
		return rs;
	}

	public long modBankQS(String bankid, int status, java.util.Date date) {
		System.out.println("<<<<<更新银行清算结果状态 " + new java.util.Date().toLocaleString() + "  >>>>>>>>");
		Connection conn = null;
		long result = -1L;
		try {
			conn = DAO.getConnection();
			BankQSVO bq = new BankQSVO();
			bq.bankID = bankid;
			bq.tradeStatus = status;
			bq.tradeDate = date;
			result = DAO.modBankQS(bq, conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public ReturnValue saveCCBQSResult(Vector<CCBQSResult> qsResult) {
		log(">>>>保存建行清算结果信息>>>>时间：" + Tool.fmtTime(new java.util.Date()));

		ReturnValue result = new ReturnValue();
		boolean flag = true;
		Connection conn = null;
		if (qsResult != null) {
			if (qsResult.size() <= 0) {
				result.remark = "保存建行清算结果，没有需要传入的数据。";
			} else {
				try {
					conn = DAO.getConnection();
					for (int i = 0; i < qsResult.size(); i++) {
						CCBQSResult qs = (CCBQSResult) qsResult.get(i);
						if (qs != null) {
							if (qs.qsDate == null) {
								throw new SQLException("传入的数据类表中有数据的交易日期为空");
							}
							if (flag) {
								flag = false;
								DAO.delCCBQSResult(qs.bankID, qs.qsDate, conn);
							}
							DAO.addCCBQSResult(qs, conn);
						}
					}
					log("保存数据" + qsResult.size());
					result.remark = "保存建行清算结果数据成功";
				} catch (SQLException e) {
					result.result = -1L;
					result.remark = "保存建行清算结果，数据库异常";
					log(e.getMessage());
					e.printStackTrace();
				} catch (Exception e) {
					result.result = -1L;
					result.remark = "保存建行清算结果，系统异常";
					log(e.getMessage());
					e.printStackTrace();
				} finally {
					DAO.closeStatement(null, null, conn);
				}
			}
		} else {
			result.result = -1L;
			result.remark = "保存银行清算结果，传入的信息为 null。";
		}
		return result;
	}

	public ReturnValue ifQuitFirm(String firmID, String bankID) {
		ReturnValue rv = new ReturnValue();
		String truefirmis = getfirmid(firmID);
		if (truefirmis == null) {
			rv.result = -920013L;
			return rv;
		}
		rv = ifbankTrade(bankID, truefirmis, null, 3, 0);
		if (rv.result != 0L) {
			log("验证失败，失败原因：" + rv.result + "失败说明：" + rv.remark);
			return rv;
		}
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			Vector<CapitalValue> capitals = DAO.getCapitalInfoList(
					" and bankID='" + bankID + "' and contact='" + firmID + "' and createtime>=trunc(sysdate) and createtime<trunc(sysdate)+1  ",
					conn);
			if ((capitals != null) && (capitals.size() > 0)) {
				rv.result = -941002L;
				log("验证失败，失败原因：当日有转账交易不能解约");
				return rv;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			log("验证失败，失败原因：SQLException异常");
			rv.result = -1L;
			return rv;
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
			log("验证失败，失败原因：ClassNotFoundException异常");
			rv.result = -1L;
			return rv;
		}
		try {
			conn.setAutoCommit(false);
			rv.result = dataProcess.ifFirmDelAccount(truefirmis, bankID, conn);
			ReturnValue localReturnValue1;
			if (rv.result < 0L) {
				rv.remark = "账户未终止";
				return rv;
			}
			rv = dataProcess.changeFirmIsOpen(truefirmis, 2, bankID, conn);
			if (rv.result < 0L) {
				return rv;
			}
		} catch (Exception e1) {
			log("判断账号解约异常=" + Tool.getExceptionTrace(e1));
		} finally {
			try {
				conn.rollback();
			} catch (SQLException e) {
				log("判断账号解约,数据回滚异常=" + Tool.getExceptionTrace(e));
			}
			DAO.closeStatement(null, null, conn);
		}
		try {
			conn.rollback();
		} catch (SQLException e) {
			log("判断账号解约,数据回滚异常=" + Tool.getExceptionTrace(e));
		}
		DAO.closeStatement(null, null, conn);

		return rv;
	}

	public double getDayTransMoney(String bankID, String contact, int type, Timestamp time, Connection conn) throws SQLException {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("取得某日交易商已转账金额");
		System.out.println("bankID[" + bankID + "]contact[" + contact + "]type[" + type + "]time[" + time + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}");
		double result = 0.0D;

		Vector<CapitalValue> capitalList = DAO.getCapitalInfoList("and status=0 and to_char(createtime,'yyyy-mm-dd')='" + Tool.convertTS(time) + "'"
				+ " and bankID='" + bankID + "' and contact='" + contact + "' and type=" + type, conn);
		for (int i = 0; i < capitalList.size(); i++) {
			CapitalValue cVal = (CapitalValue) capitalList.get(i);
			result = Arith.add(result, cVal.money);
		}
		return result;
	}

	public ReturnValue modCapitalInfoStatus(long id, String funID, int status, Timestamp bankTime) {
		ReturnValue rv = new ReturnValue();
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			if (bankTime == null) {
				bankTime = new Timestamp(System.currentTimeMillis());
			}
			DAO.modCapitalInfoStatus(id, funID, 1, bankTime, conn);
		} catch (SQLException e) {
			e.printStackTrace();
			rv.result = -1L;
			rv.remark = ("修改 " + id + " 银行流水号 " + funID + " 数据库异常");
		} catch (Exception e) {
			e.printStackTrace();
			rv.result = -1L;
			rv.remark = ("修改 " + id + " 银行流水号 " + funID + " 系统异常");
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return rv;
	}

	public Map<String, CapitalValue> getCapitalValue(Vector<String> funid, String bankID) {
		Map<String, CapitalValue> result = new HashMap();
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			if ((funid != null) && (funid.size() > 0)) {
				for (String contact : funid) {
					Vector<CapitalValue> capitalList = DAO.getCapitalInfoList(" and funid='" + contact + "' and bankid='" + bankID + "' ", conn);
					if ((capitalList != null) && (capitalList.size() > 0)) {
						CapitalValue cv = (CapitalValue) capitalList.get(0);
						result.put(contact, cv);
					}
				}
			}
		} catch (Exception e) {
			log(Tool.getExceptionTrace(e));
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public ReturnValue modAccount(CorrespondValue cv1, CorrespondValue cv2, String bankID) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("市场端改约方法");
		System.out.println("原交易商信息：cv1[" + cv1.toString() + "]\n 修改之后的交易商信息：cv2[" + cv2.toString() + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}}");
		long checkResult = chenckCorrespondValue(cv1);
		System.out.println("银行开户销户变更帐户检验参数方法返回结果：" + checkResult);
		ReturnValue rv = new ReturnValue();
		if (checkResult == 0L) {
			try {
				BankAdapterRMI bankadapter = getAdapter(bankID);
				if (bankadapter == null) {
					rv.result = -920000L;
					rv.remark = "连接银行通讯机(适配器)失败";
					return rv;
				}
				rv = bankadapter.modAccount(cv1, cv2);
				if (rv.result < 0L) {
					return rv;
				}
				rv.result = DAO.modCorrespond(cv2);
			} catch (RemoteException e) {
				rv.result = -920000L;
				e.printStackTrace();
				log("交易商改约提交适配器异常," + e);
			} catch (Exception e) {
				rv.result = -920000L;
				e.printStackTrace();
				log("交易商改约异常," + e);
			}
		} else {
			rv.result = ((int) checkResult);
		}
		return rv;
	}

	private ReturnValue checkTrans(String bankID, String contact, double money, Timestamp time, int type, Connection conn) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("bankID[" + bankID + "]firmID[" + contact + "]money[" + money + "]time[" + time + "]type[" + type + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}");
		ReturnValue resultValue = new ReturnValue();
		try {
			FirmAuditValue fVal = DAO.getFirmAuditValue(contact, conn);

			double maxPerSglTransMoney = 9999999999.0D;

			double maxPerTransMoney = 9999999999.0D;

			int maxPerTransCount = 9999999;
			if (fVal.maxPerTransMoney > 0.0D) {
				maxPerTransMoney = fVal.maxPerTransMoney;
			}
			if (fVal.maxPerTransCount > 0) {
				maxPerTransCount = fVal.maxPerTransCount;
			}
			if (fVal.maxPerSglTransMoney > 0.0D) {
				maxPerSglTransMoney = fVal.maxPerSglTransMoney;
			}
			if (Arith.compareTo(money, maxPerSglTransMoney) == 1) {
				resultValue.result = -30000L;
				resultValue.remark = "超出单笔最大转账金额限制";
			} else if (Arith.compareTo(Arith.add(money, getDayTransMoney(bankID, contact, type, time, conn)), maxPerTransMoney) == 1) {
				resultValue.result = -30002L;
				resultValue.remark = "超出当日最大转账金额限制";
			} else if (Arith.compareTo(Arith.add(1.0D, getDayTransCount(bankID, contact, type, time, conn)), maxPerTransCount) == 1) {
				resultValue.result = -30003L;
				resultValue.remark = "超出当日最大转账次数限制";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log("判断是否符合出入金条件SQLException:" + e);
			resultValue.result = -30004L;
		} catch (Exception e) {
			e.printStackTrace();
			log("判断是否符合出入金条件Exception:" + e);
			resultValue.result = -30005L;
		}
		return resultValue;
	}

	private int getDayTransCount(String bankID, String contact, int type, Timestamp time, Connection conn) throws SQLException {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("取得某日交易商已转账次数");
		System.out.println("bankID[" + bankID + "]firmID[" + contact + "]type[" + type + "]time[" + time + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}");
		Vector<CapitalValue> capitalList = DAO.getCapitalInfoList("and (status!=1 and status !=-1) and to_char(createtime,'yyyy-mm-dd')='"
				+ Tool.convertTS(time) + "'" + " and bankID='" + bankID + "' and contact='" + contact + "' and type=" + type, conn);

		return capitalList.size();
	}
}
