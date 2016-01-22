package gnnt.bank.platform;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
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
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import gnnt.bank.adapter.bankBusiness.dayData.FCS_10_Result;
import gnnt.bank.adapter.bankBusiness.dayData.FCS_11_Result;
import gnnt.bank.adapter.bankBusiness.dayData.FCS_13_Result;
import gnnt.bank.adapter.bankBusiness.dayData.FCS_99;
import gnnt.bank.adapter.bankBusiness.message.CEB_RSP;
import gnnt.bank.adapter.bankBusiness.message.CEB_param;
import gnnt.bank.adapter.rmi.BankAdapterRMI;
import gnnt.bank.adapter.util.FtpUtil;
import gnnt.bank.platform.dao.BankDAO;
import gnnt.bank.platform.dao.BankDAOFactory;
import gnnt.bank.platform.util.Arith;
import gnnt.bank.platform.util.Encryption;
import gnnt.bank.platform.util.ErrorCode;
import gnnt.bank.platform.util.Tool;
import gnnt.bank.platform.vo.RequestMsg;
import gnnt.bank.trade.rmi.TradeProcessorRMI;
import gnnt.trade.bank.vo.AbcInfoValue;
import gnnt.trade.bank.vo.BankCompareInfoValue;
import gnnt.trade.bank.vo.BankTransferValue;
import gnnt.trade.bank.vo.BankValue;
import gnnt.trade.bank.vo.CapitalCompare;
import gnnt.trade.bank.vo.CapitalValue;
import gnnt.trade.bank.vo.ChargeAgainstValue;
import gnnt.trade.bank.vo.CompareResult;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.DicValue;
import gnnt.trade.bank.vo.Firm;
import gnnt.trade.bank.vo.FirmBalanceValue;
import gnnt.trade.bank.vo.FirmBankFunds;
import gnnt.trade.bank.vo.FirmID2SysFirmID;
import gnnt.trade.bank.vo.FirmMessageVo;
import gnnt.trade.bank.vo.FirmTradeStatus;
import gnnt.trade.bank.vo.FirmValue;
import gnnt.trade.bank.vo.InMoneyVO;
import gnnt.trade.bank.vo.InOutMoney;
import gnnt.trade.bank.vo.InterfaceLog;
import gnnt.trade.bank.vo.MFirmValue;
import gnnt.trade.bank.vo.MarketFirmMsg;
import gnnt.trade.bank.vo.MoneyInfoValue;
import gnnt.trade.bank.vo.QSChangeResult;
import gnnt.trade.bank.vo.QSRresult;
import gnnt.trade.bank.vo.RZQS;
import gnnt.trade.bank.vo.ReturnBalance;
import gnnt.trade.bank.vo.ReturnValue;
import gnnt.trade.bank.vo.RgstCapitalValue;
import gnnt.trade.bank.vo.SystemMessage;
import gnnt.trade.bank.vo.SystemQSValue;
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

public class CapitalProcessor {
	protected static final int INMONEY = 0;
	protected static final int OUTMONEY = 1;
	protected static final int RATE = 2;
	protected static final int TRANS = 3;
	protected final String TRADE_SOURCE_SYS = "0";

	protected final String TRADE_SOURCE_PT = "1";

	protected final String TRADE_SOURCE_BANK = "2";

	protected final String TRANSFER_OUT = "104";

	protected final String TRANSFER_IN = "103";
	protected static DataProcess dataProcess;
	private static boolean ISLOADED = false;
	private static Hashtable<String, String> ADAPTERLIST;
	private Hashtable<String, String> BankIdAndAdapterClassnameList;
	protected static BankDAO DAO;

	public CapitalProcessor() {
		try {
			DAO = BankDAOFactory.getDAO();
			if (!ISLOADED) {
				ErrorCode errorCode = new ErrorCode();
				errorCode.load();
				dataProcess = new DataProcess();
				if (dataProcess.loadConfig() == 0) {
					if (loadAdapter() == 0) {
						startServiceThread();
						ISLOADED = true;
					} else {
						log("初始化处理器失败：加载适配器对象错误");
					}
				} else
					log("初始化处理器失败：加载配置的科目与摘要信息错误");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log("初始化处理器失败：" + e);
		}
	}

	private void log(String content) {
		Logger plog = Logger.getLogger("Processorlog");
		plog.debug(content);
	}

	public String getConfig(String key) {
		return Tool.getConfig(key);
	}

	private void startServiceThread() {
		ServiceThread svrThread = new ServiceThread(this);
		svrThread.start();
	}

	private int loadAdapter() {
		int result = 0;
		try {
			Vector bankList = DAO.getBankList("where validFlag = 0");
			log("=========>开始加载适配器远程访问对象url");
			for (int i = 0; i < bankList.size(); i++) {
				BankValue bVal = (BankValue) bankList.get(i);
				String abClassName = bVal.adapterClassname;
				String className = abClassName.substring(abClassName.lastIndexOf("/") + 1, abClassName.length());
				System.out.println("URL:" + className);
				addAdapter(bVal.bankID, abClassName);
				addBankIdAndAdapterClassname(className, bVal.bankID);
				System.out.println(bVal.bankID + "适配器远程访问对象url加载成功");
				log(bVal.bankID + "适配器加载成功");
			}
			log("=========>结束加载适配器远程访问对象url");
		} catch (Exception e) {
			e.printStackTrace();
			log("加载适配器远程访问对象url异常：" + e);
			result = -1;
		}

		return result;
	}

	private void addAdapter(String bankID, String adapterClassname) {
		if (ADAPTERLIST == null)
			ADAPTERLIST = new Hashtable();
		ADAPTERLIST.put(bankID, adapterClassname);
	}

	private void addBankIdAndAdapterClassname(String adapterClassname, String bankID) {
		if (this.BankIdAndAdapterClassnameList == null)
			this.BankIdAndAdapterClassnameList = new Hashtable();
		this.BankIdAndAdapterClassnameList.put(adapterClassname, bankID);
	}

	protected BankDAO getBankDAO() {
		return DAO;
	}

	protected int getTransType() {
		return 3;
	}

	public static void main(String[] args) {
		TradeProcessorRMI tradeProc = null;
		try {
			tradeProc = (TradeProcessorRMI) Naming.lookup("//172.16.2.93:20512/TradeCapitalProcessorRMI");
			System.out.println(tradeProc == null ? "获取的交易系统处理器为空" : "获取的交易系统处理器不为空");
		} catch (MalformedURLException e) {
			System.out.println("MalformedURLException:" + e.getMessage());
			e.printStackTrace();
		} catch (RemoteException e) {
			System.out.println("RemoteException:" + e.getMessage());
			e.printStackTrace();
		} catch (NotBoundException e) {
			System.out.println("NotBoundException:" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Exception" + e.getMessage());
			e.printStackTrace();
		}
		System.out.println("返回交易系统处理器对象信息" + tradeProc);
	}

	public ReturnValue testRMI() {
		ReturnValue result = new ReturnValue();
		result.result = 0L;
		result.remark = "处理器链接成功";
		return result;
	}

	public ReturnValue testRMI(String str) {
		ReturnValue result = new ReturnValue();
		result.result = 0L;
		result.remark = ("处理器接收到了[" + str + "]");
		return result;
	}

	public BankAdapterRMI getAdapter(String bankID) {
		BankAdapterRMI bankadapter = null;
		try {
			System.out.println("bankID：" + bankID);
			String remoteUrl = (String) ADAPTERLIST.get(bankID);
			System.out.println("===RMI_url---------->>>>" + remoteUrl);
			bankadapter = (BankAdapterRMI) Naming.lookup(remoteUrl);
			System.out.println(bankadapter == null ? "获取的适配器为空" : "获取的适配器服务不为空");
		} catch (MalformedURLException e) {
			System.out.println("MalformedURLException:" + e.getMessage());
			e.printStackTrace();
		} catch (RemoteException e) {
			System.out.println("RemoteException:" + e.getMessage());
			e.printStackTrace();
		} catch (NotBoundException e) {
			System.out.println("NotBoundException:" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Exception" + e.getMessage());
			e.printStackTrace();
		}
		System.out.println("返回银行适配器对象信息" + bankadapter);
		return bankadapter;
	}

	public long getMktActionID() {
		return Long.parseLong(Tool.fmtDateNo2(new java.util.Date()) + DAO.getActionID());
	}

	public Hashtable<String, String> getBankIdAndAdapterClassname() {
		return this.BankIdAndAdapterClassnameList;
	}

	protected TransferInfoValue getPayInfo(String bankID, String firmID, int type, Connection conn) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("取得付款方信息");
		System.out.println("bankID[" + bankID + "]firmID[" + firmID + "]type[" + type + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}");
		TransferInfoValue result = new TransferInfoValue();
		String bankAccount = "";
		try {
			Vector cList = DAO.getCorrespondList("where bankid='" + bankID + "' and firmid='" + firmID + "'", conn);
			if (cList.size() > 0) {
				bankAccount = ((CorrespondValue) cList.get(0)).account;
				result.accountName = ((CorrespondValue) cList.get(0)).accountName;
				result.account1 = ((CorrespondValue) cList.get(0)).account1;
				result.isCrossLine = ((CorrespondValue) cList.get(0)).isCrossLine;
				result.openBankCode = ((CorrespondValue) cList.get(0)).OpenBankCode;
			}

			BankValue bv = DAO.getBank(bankID);
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
				result.accountType = val.accountType;
				result.openBankCode = val.OpenBankCode;
			} else if (type == 1) {
				Vector list = DAO.getDicList("where type=2 and bankid='" + bankID + "'", conn);
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
		} catch (Exception e) {
			log("取得付款方信息Exception:" + e);
			e.printStackTrace();
		}

		return result;
	}

	protected TransferInfoValue getReceiveInfo(String bankID, String firmID, int type, Connection conn) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("取得收款方信息");
		System.out.println("bankID[" + bankID + "]firmID[" + firmID + "]type[" + type + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}");
		TransferInfoValue result = new TransferInfoValue();
		String bankAccount = "";
		try {
			Vector cList = DAO.getCorrespondList("where bankid='" + bankID + "' and firmid='" + firmID + "'", conn);
			if (cList.size() > 0) {
				bankAccount = ((CorrespondValue) cList.get(0)).account;
			}
			BankValue bv = DAO.getBank(bankID);
			result.headOffice = bv.bankName;

			if (type == 0) {
				Vector list = DAO.getDicList("where type=2 and bankid='" + bankID + "'", conn);
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
				result.openBankCode = val.OpenBankCode;
				result.accountType = val.accountType;
			}
		} catch (Exception e) {
			log("取得收款方信息Exception:" + e);
			e.printStackTrace();
		}

		return result;
	}

	private double getTotalTransMoney(String bankID, String firmID, int type, Connection conn) throws SQLException {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("取得交易商已转账金额");
		System.out.println("bankID[" + bankID + "]firmID[" + firmID + "]type[" + type + "]");
		System.out.println("}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}");
		double result = 0.0D;
		Vector capitalList = DAO.getCapitalInfoList("where status=0 and bankID='" + bankID + "' and firmid='" + firmID + "' and type=" + type, conn);
		for (int i = 0; i < capitalList.size(); i++) {
			CapitalValue cVal = (CapitalValue) capitalList.get(i);
			result = Arith.add(result, cVal.money);
		}
		return result;
	}

	private double getDayTransMoney(String bankID, String firmID, int type, Timestamp time, Connection conn) throws SQLException {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("取得某日交易商已转账金额");
		System.out.println("bankID[" + bankID + "]firmID[" + firmID + "]type[" + type + "]time[" + time + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}");
		double result = 0.0D;

		Vector capitalList = DAO.getCapitalInfoList("where status=0 and to_char(createtime,'yyyy-mm-dd')='" + Tool.convertTS(time) + "'"
				+ " and bankID='" + bankID + "' and firmid='" + firmID + "' and type=" + type, conn);
		for (int i = 0; i < capitalList.size(); i++) {
			CapitalValue cVal = (CapitalValue) capitalList.get(i);
			result = Arith.add(result, cVal.money);
		}

		return result;
	}

	public ReturnValue rgstAccountforWeb(String firmid, String pwd, String type) {
		log("提供给交易系统的网站签约方法OpenAccountforWeb>>>>>" + new java.util.Date());
		log("交易商代码[" + firmid + "]请求类型[" + type + "]");
		ReturnValue returnValue = new ReturnValue();
		String filer = " where firmid='" + firmid + "' ";
		Vector vec = getCorrespondValue(filer);
		if ((vec != null) && (vec.size() > 0)) {
			log("查到交易商银行对应关系" + ((CorrespondValue) vec.get(0)).toString());
			CorrespondValue cpV = (CorrespondValue) vec.get(0);
			cpV.bankCardPassword = pwd;

			cpV.falg = Integer.parseInt(type);
			returnValue = openAccountMarket(cpV);
			if ((type.equals("3")) && (returnValue.result == -500L)) {
				returnValue.result = 0L;
				returnValue.remark = "验证通过";
			} else if ((returnValue.result == 0L) && (type.equals("1"))) {
				returnValue.result = 0L;
				returnValue.remark = "验证通过，签约/预指定成功";
			} else if (returnValue.result == -40001L) {
				returnValue.result = -40001L;
				returnValue.remark = "信息不完整";
			} else if (returnValue.result == -40002L) {
				returnValue.result = -40002L;
				returnValue.remark = "交易商不存在";
			} else if (returnValue.result == -40003L) {
				returnValue.result = -40003L;
				returnValue.remark = "银行不存在";
			} else if (returnValue.result == -40004L) {
				returnValue.result = -40004L;
				returnValue.remark = "帐号已注册";
			} else if (returnValue.result == -40005L) {
				returnValue.result = -40005L;
				returnValue.remark = "银行签约失败";
			} else if (returnValue.result == -40006L) {
				returnValue.result = -40006L;
				returnValue.remark = "数据库操作失败";
			} else if (returnValue.result == -40007L) {
				returnValue.result = -40008L;
				returnValue.remark = "交易商密码错误";
			} else if (returnValue.result == -40008L) {
				returnValue.result = -40007L;
				returnValue.remark = "系统异常";
			}

		} else {
			log("未找到交易商银行对应关系");
			returnValue.result = -40002L;
			returnValue.remark = "未找到交易商银行对应关系";
		}
		log("网站签约返回：" + returnValue.toString());
		return returnValue;
	}

	private int getDayTransCount(String bankID, String firmID, int type, Timestamp time, Connection conn) throws SQLException {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("取得某日交易商已转账次数");
		System.out.println("bankID[" + bankID + "]firmID[" + firmID + "]type[" + type + "]time[" + time + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}");
		Vector capitalList = DAO.getCapitalInfoList("where (status!=1 and status !=-1) and to_char(createtime,'yyyy-mm-dd')='" + Tool.convertTS(time)
				+ "'" + " and bankID='" + bankID + "' and firmid='" + firmID + "' and type=" + type, conn);

		return capitalList.size();
	}

	protected double getTransRate(String bankID, String firmID, double money, int type, int express, String account, Connection conn) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("bankID[" + bankID + "]firmID[" + firmID + "]money[" + money + "]type[" + type + "]express[" + express + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}");
		FeeProcess feeProcess = new FeeProcess();
		if (account == null) {
			try {
				Vector v = DAO.getCorrespondList(" where bankid='" + bankID + "' and firmid='" + firmID + "'");
				if ((v != null) && (v.size() > 0)) {
					account = ((CorrespondValue) v.get(0)).account;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		double result = 0.0D;
		if (type == 0) {
			result = feeProcess.getInRate(bankID, firmID, money, express, account, conn);
		} else if (type == 1) {
			result = feeProcess.getOutRate(bankID, firmID, money, express, account, conn);
		}
		return result;
	}

	private long checkTrans(String bankID, String firmID, double money, Timestamp time, int type, Connection conn) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("bankID[" + bankID + "]firmID[" + firmID + "]money[" + money + "]time[" + time + "]type[" + type + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}");
		long result = 0L;
		try {
			BankValue bVal = DAO.getBank(bankID, conn);
			FirmValue fVal = DAO.getFirm(firmID, conn);

			double maxPerSglTransMoney = bVal.maxPerSglTransMoney;

			double maxPerTransMoney = bVal.maxPerTransMoney;

			int maxPerTransCount = bVal.maxPerTransCount;

			if (fVal.maxPerTransMoney > 0.0D)
				maxPerTransMoney = fVal.maxPerTransMoney;
			if (fVal.maxPerTransCount > 0)
				maxPerTransCount = fVal.maxPerTransCount;
			if (fVal.maxPerSglTransMoney > 0.0D)
				maxPerSglTransMoney = fVal.maxPerSglTransMoney;

			if (Arith.compareTo(money, maxPerSglTransMoney) == 1) {
				result = -30000L;
			} else if (Arith.compareTo(Arith.add(money, getDayTransMoney(bankID, firmID, type, time, conn)), maxPerTransMoney) == 1) {
				result = -30002L;
			} else if (Arith.compareTo(Arith.add(1.0F, getDayTransCount(bankID, firmID, type, time, conn)), maxPerTransCount) == 1) {
				result = -30003L;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log("判断是否符合出入金条件SQLException:" + e);
			result = -30004L;
		} catch (Exception e) {
			e.printStackTrace();
			log("判断是否符合出入金条件Exception:" + e);
			result = -30005L;
		}

		return result;
	}

	public long inMoney(String bankID, String contact, String account, Timestamp bankTime, double money, String funID, long actionID, int funFlag,
			String remark) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{入金，由adapter调用{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("bankID[" + bankID + "]contact[" + contact + "]account[" + account + "]bankTime[" + bankTime + "]money[" + money
				+ "]funID[" + funID + "]actionID[" + actionID + "]funFlag[" + funFlag + "]remark[" + remark + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}");

		long result = 0L;

		Vector vec = null;
		try {
			vec = DAO.getCapitalInfoList(" where actionid=" + actionID + " or (funid='" + funID + "' and trunc(createtime)=to_date('"
					+ Tool.fmtDate(bankTime) + "','yyyy-MM-dd'))");
		} catch (Exception e) {
			log("查询流水信息异常:" + Tool.getExceptionTrace(e));
			result = -1L;
		}
		if ((vec != null) && (vec.size() > 0)) {
			result = actionID;
			return result;
		}

		Vector vecCorr = null;
		try {
			vecCorr = DAO.getCorrespondList(" where contact='" + contact + "' and bankID='" + bankID + "' and isOpen=1");
		} catch (Exception e) {
			log("查询签约关系异常：" + Tool.getExceptionTrace(e));
		}
		if ((vecCorr == null) || (vecCorr.size() != 1)) {
			log("签约号[" + contact + "]和银行[" + bankID + "]还没有签约，或者存在多条签约关系");
			result = -1L;
			return result;
		}
		String firmID = ((CorrespondValue) vecCorr.get(0)).firmID;

		Vector vecf2f = null;
		try {
			vecf2f = DAO.getFirmID2SysFirmID(" where firmid='" + firmID + "' and bankID='" + bankID + "'");
		} catch (SQLException e) {
			log("查询平台号、银行和交易系统编号、交易系统交易商对应关系异常：" + Tool.getExceptionTrace(e));
		}
		if ((vecf2f == null) || (vecf2f.size() <= 0)) {
			log("平台号[" + firmID + "]在银行[" + bankID + "]下不存在和交易系统交易商的对应关系，不允许入金");
			result = -1L;
			return result;
		}
		long PTactionID = getMktActionID();
		InOutMoney inMoney = new InOutMoney();
		inMoney.systemID = ((FirmID2SysFirmID) vecf2f.get(0)).systemID;
		inMoney.sysFirmID = ((FirmID2SysFirmID) vecf2f.get(0)).sysFirmID;
		inMoney.funID = funID;
		inMoney.bankID = bankID;
		inMoney.account = account;
		inMoney.money = money;
		inMoney.remark = "银行端入金";
		inMoney.sysToSys = "1";
		inMoney.actionID = PTactionID;
		inMoney.firmID = firmID;
		inMoney.bankTime = bankTime;

		ReturnValue returnVa = inMoneyBank(inMoney);
		if (returnVa.result == 0L) {
			result = PTactionID;
			log("子系统端入金成功");
		} else {
			result = -1L;
		}
		return result;
	}

	public long inMoneyMarket(String bankID, String firmID, String account, String accountPwd, double money, String remark) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{入金，由市场端调用{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("bankID[" + bankID + "]firmID[" + firmID + "]account[" + account + "]accountPwd[" + accountPwd + "]money[" + money
				+ "]remark[" + remark + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}}");

		long result = 0L;
		result = tradeDate(bankID);
		if (result != 0L) {
			return result;
		}

		long capitalID = 0L;

		long rateID = 0L;

		TransferInfoValue payInfo = null;

		TransferInfoValue receiveInfo = null;

		Connection conn = null;

		Timestamp curTime = new Timestamp(System.currentTimeMillis());

		double inRate = 0.0D;

		boolean isopen = bankAccountIsOpen(firmID, bankID, account) == 1;
		if (!isopen) {
			result = -10019L;
			log("市场发起入金，交易商未签约。");
			return result;
		}
		try {
			conn = DAO.getConnection();

			inRate = getTransRate(bankID, firmID, money, 0, 0, account, conn);

			if (((firmID == null) || (firmID.trim().equals(""))) && ((account == null) || (account.trim().equals("")))) {
				result = -10016L;
				log("市场发起入金，参数非法，错误码=" + result);
			} else if ((firmID == null) || (firmID.trim().equals(""))) {
				Vector cList = DAO.getCorrespondList("where bankid='" + bankID + "' and account='" + account + "'", conn);
				if (cList.size() > 0) {
					firmID = ((CorrespondValue) cList.get(0)).firmID;
				} else {
					result = -10011L;
					log("市场发起入金，非法银行帐号，错误码=" + result);
				}
			} else if ((account == null) || (account.trim().equals(""))) {
				Vector cList = DAO.getCorrespondList("where bankid='" + bankID + "' and firmid='" + firmID + "'", conn);
				if (cList.size() > 0) {
					account = ((CorrespondValue) cList.get(0)).account;
				} else {
					result = -10012L;
					log("市场发起入金，非法交易商代码，错误码=" + result);
				}
			} else if (DAO.getCorrespond(bankID, firmID, account, conn) == null) {
				result = -10013L;
				log("市场发起入金，交易商代码和帐号对应关系错误，错误码=" + result);
			}
			Vector cList = DAO.getCorrespondList("where bankid='" + bankID + "' and firmid='" + firmID + "' and status=0 ", conn);
			if ((cList == null) || (cList.size() != 1)) {
				result = -10013L;
			}
			if ((result == 0L) && ((inRate == -1.0D) || (inRate == -2.0D))) {
				result = -10024L;
				log("市场发起入金，计算手续费错误，错误码=" + result);
			}

			if (result == 0L) {
				long checkTrans = 0L;
				if ((getConfig("inMoneyAudit") != null) && (getConfig("inMoneyAudit").trim().equals("true"))) {
					checkTrans = checkTrans(bankID, firmID, money, curTime, 0, conn);
				}
				if (checkTrans == 0L) {
					result = DAO.getActionID(conn);
					try {
						conn.setAutoCommit(false);

						CapitalValue cVal = new CapitalValue();
						cVal.actionID = result;
						cVal.bankID = bankID;
						cVal.creditID = firmID;
						cVal.debitID = ((String) dataProcess.getBANKSUB().get(bankID));
						cVal.firmID = firmID;
						cVal.money = money;
						cVal.note = remark;
						cVal.oprcode = dataProcess.getINSUMMARY() + "";
						cVal.status = 2;
						cVal.type = 0;

						capitalID = DAO.addCapitalInfo(cVal, conn);

						cVal.creditID = "Market";
						cVal.debitID = firmID;
						cVal.money = inRate;
						cVal.oprcode = dataProcess.getFEESUMMARY() + "";
						cVal.type = 2;

						rateID = DAO.addCapitalInfo(cVal, conn);

						conn.commit();
					} catch (SQLException e) {
						e.printStackTrace();
						conn.rollback();
						result = -10026L;
						log("市场端发起入金写资金流水SQLException,数据回滚:" + e);
						throw e;
					} finally {
						conn.setAutoCommit(true);
					}

				}

				result = checkTrans;
			}

			if (result > 0L) {
				payInfo = getPayInfo(bankID, firmID, 0, conn);

				receiveInfo = getReceiveInfo(bankID, firmID, 0, conn);

				BankValue bv = DAO.getBank(bankID);
				InMoneyVO inMoneyInfo = new InMoneyVO(bankID, bv.bankName, money, firmID, payInfo, accountPwd, receiveInfo, remark, result);

				BankAdapterRMI bankadapter = getAdapter(bankID);
				ReturnValue rVal = null;
				System.out.println(bankadapter);
				if (bankadapter == null) {
					rVal = new ReturnValue();
					rVal.result = -920000L;
					log("处理器连接适配器[" + bankID + "]失败");
				} else {
					rVal = bankadapter.inMoneyQueryBank(inMoneyInfo);
				}

				try {
					conn.setAutoCommit(false);
					if (rVal.result == 0L) {
						log("适配器处理成功，业务流水处理成功,入金成功。");
					} else if (rVal.result == 5L) {
						result = rVal.result;
						DAO.modCapitalInfoStatus(capitalID, rVal.funID, 2, curTime, conn);
						DAO.modCapitalInfoStatus(rateID, rVal.funID, 2, curTime, conn);
						log("适配器处理成功，业务流水处理中。");
					} else if (rVal.result == -50010L) {
						result = rVal.result;
						DAO.modCapitalInfoStatus(capitalID, rVal.funID, 5, curTime, conn);
						DAO.modCapitalInfoStatus(rateID, rVal.funID, 5, curTime, conn);
						log("适配器连接银行，返回银行无应答");
					} else if (rVal.result == -920008L) {
						result = rVal.result;
						DAO.modCapitalInfoStatus(capitalID, rVal.funID, 6, curTime, conn);
						DAO.modCapitalInfoStatus(rateID, rVal.funID, 6, curTime, conn);
						log("适配器连接银行，返回银行返回市场代码为空");
					} else {
						result = rVal.result;
						DAO.modCapitalInfoStatus(capitalID, rVal.funID, 1, curTime, conn);
						DAO.modCapitalInfoStatus(rateID, rVal.funID, 1, curTime, conn);
						result = rVal.result;
						log("适配器提交失败:" + result);
					}
					conn.commit();
				} catch (SQLException e) {
					result = -10014L;
					e.printStackTrace();
					conn.rollback();
					log("市场端发起入金,更新资金流失状态为失败SQLException,数据回滚:" + result);
				} catch (Exception e) {
					result = -10015L;
					e.printStackTrace();
					conn.rollback();
					log("市场端发起入金,系统异常Exception,数据回滚:" + result);
				} finally {
					conn.setAutoCommit(true);
				}

				if (result == -10014L) {
					DAO.modCapitalInfoStatus(capitalID, rVal.funID, 1, curTime, conn);
					DAO.modCapitalInfoStatus(rateID, rVal.funID, 1, curTime, conn);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			result = -10014L;
			log("市场端发起入金SQLException:" + result);
		} catch (Exception e) {
			e.printStackTrace();
			result = -10015L;
			log("市场端发起入金Exception:" + result);
		} finally {
			DAO.closeStatement(null, null, conn);
		}

		return result;
	}

	public long inMoneyMarket(String bankID, String firmID, String account, String accountPwd, double money, String remark, String inOutStart,
			String PersonName, String AmoutDate, String BankName, String OutAccount) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{入金，由市场端调用{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("bankID[" + bankID + "]firmID[" + firmID + "]account[" + account + "]accountPwd[" + accountPwd + "]money[" + money
				+ "]remark[" + remark + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}}");

		long result = 0L;
		result = tradeDate(bankID);
		if (result != 0L) {
			return result;
		}

		long capitalID = 0L;

		long rateID = 0L;

		TransferInfoValue payInfo = null;

		TransferInfoValue receiveInfo = null;

		Connection conn = null;

		Timestamp curTime = new Timestamp(System.currentTimeMillis());

		double inRate = 0.0D;

		boolean isopen = bankAccountIsOpen(firmID, bankID, account) == 1;
		if (!isopen) {
			result = -10019L;
			log("市场发起入金，交易商未签约。");
			return result;
		}
		try {
			conn = DAO.getConnection();

			inRate = getTransRate(bankID, firmID, money, 0, 0, account, conn);

			if (((firmID == null) || (firmID.trim().equals(""))) && ((account == null) || (account.trim().equals("")))) {
				result = -10016L;
				log("市场发起入金，参数非法，错误码=" + result);
			} else if ((firmID == null) || (firmID.trim().equals(""))) {
				Vector cList = DAO.getCorrespondList("where bankid='" + bankID + "' and account='" + account + "'", conn);
				if (cList.size() > 0) {
					firmID = ((CorrespondValue) cList.get(0)).firmID;
				} else {
					result = -10011L;
					log("市场发起入金，非法银行帐号，错误码=" + result);
				}
			} else if ((account == null) || (account.trim().equals(""))) {
				Vector cList = DAO.getCorrespondList("where bankid='" + bankID + "' and firmid='" + firmID + "'", conn);
				if (cList.size() > 0) {
					account = ((CorrespondValue) cList.get(0)).account;
				} else {
					result = -10012L;
					log("市场发起入金，非法交易商代码，错误码=" + result);
				}
			} else if (DAO.getCorrespond(bankID, firmID, account, conn) == null) {
				result = -10013L;
				log("市场发起入金，交易商代码和帐号对应关系错误，错误码=" + result);
			}
			Vector cList = DAO.getCorrespondList("where bankid='" + bankID + "' and firmid='" + firmID + "' and status=0 ", conn);
			if ((cList == null) || (cList.size() != 1)) {
				result = -10013L;
			}
			if ((result == 0L) && ((inRate == -1.0D) || (inRate == -2.0D))) {
				result = -10024L;
				log("市场发起入金，计算手续费错误，错误码=" + result);
			}

			if (result == 0L) {
				long checkTrans = 0L;
				if ((getConfig("inMoneyAudit") != null) && (getConfig("inMoneyAudit").trim().equals("true"))) {
					checkTrans = checkTrans(bankID, firmID, money, curTime, 0, conn);
				}
				if (checkTrans == 0L) {
					result = DAO.getActionID(conn);
					try {
						conn.setAutoCommit(false);

						CapitalValue cVal = new CapitalValue();
						cVal.actionID = result;
						cVal.bankID = bankID;
						cVal.creditID = firmID;
						cVal.debitID = ((String) dataProcess.getBANKSUB().get(bankID));
						cVal.firmID = firmID;
						cVal.money = money;
						cVal.note = remark;
						cVal.oprcode = dataProcess.getINSUMMARY() + "";
						cVal.status = 2;
						cVal.type = 0;

						capitalID = DAO.addCapitalInfo(cVal, conn);

						cVal.creditID = "Market";
						cVal.debitID = firmID;
						cVal.money = inRate;
						cVal.oprcode = dataProcess.getFEESUMMARY() + "";
						cVal.type = 2;

						rateID = DAO.addCapitalInfo(cVal, conn);

						conn.commit();
					} catch (SQLException e) {
						e.printStackTrace();
						conn.rollback();
						result = -10026L;
						log("市场端发起入金写资金流水SQLException,数据回滚:" + e);
						throw e;
					} finally {
						conn.setAutoCommit(true);
					}

				}

				result = checkTrans;
			}

			if (result > 0L) {
				payInfo = getPayInfo(bankID, firmID, 0, conn);

				receiveInfo = getReceiveInfo(bankID, firmID, 0, conn);

				BankValue bv = DAO.getBank(bankID);
				InMoneyVO inMoneyInfo = new InMoneyVO(bankID, bv.bankName, money, firmID, payInfo, accountPwd, receiveInfo, remark, result,
						inOutStart, PersonName, AmoutDate, BankName, OutAccount);

				BankAdapterRMI bankadapter = getAdapter(bankID);
				ReturnValue rVal = null;
				System.out.println(bankadapter);
				if (bankadapter == null) {
					rVal = new ReturnValue();
					rVal.result = -920000L;
					log("处理器连接适配器[" + bankID + "]失败");
				} else {
					rVal = bankadapter.inMoneyQueryBank(inMoneyInfo);
				}

				try {
					conn.setAutoCommit(false);
					if (rVal.result == 0L) {
						log("适配器处理成功，业务流水处理成功,入金成功。");
					} else if (rVal.result == 5L) {
						result = rVal.result;
						DAO.modCapitalInfoStatus(capitalID, rVal.funID, 2, curTime, conn);
						DAO.modCapitalInfoStatus(rateID, rVal.funID, 2, curTime, conn);
						log("适配器处理成功，业务流水处理中。");
					} else if (rVal.result == -50010L) {
						result = rVal.result;
						DAO.modCapitalInfoStatus(capitalID, rVal.funID, 5, curTime, conn);
						DAO.modCapitalInfoStatus(rateID, rVal.funID, 5, curTime, conn);
						log("适配器连接银行，返回银行无应答");
					} else if (rVal.result == -920008L) {
						result = rVal.result;
						DAO.modCapitalInfoStatus(capitalID, rVal.funID, 6, curTime, conn);
						DAO.modCapitalInfoStatus(rateID, rVal.funID, 6, curTime, conn);
						log("适配器连接银行，返回银行返回市场代码为空");
					} else {
						result = rVal.result;
						DAO.modCapitalInfoStatus(capitalID, rVal.funID, 1, curTime, conn);
						DAO.modCapitalInfoStatus(rateID, rVal.funID, 1, curTime, conn);
						result = rVal.result;
						log("适配器提交失败:" + result);
					}
					conn.commit();
				} catch (SQLException e) {
					result = -10014L;
					e.printStackTrace();
					conn.rollback();
					log("市场端发起入金,更新资金流失状态为失败SQLException,数据回滚:" + result);
				} catch (Exception e) {
					result = -10015L;
					e.printStackTrace();
					conn.rollback();
					log("市场端发起入金,系统异常Exception,数据回滚:" + result);
				} finally {
					conn.setAutoCommit(true);
				}

				if (result == -10014L) {
					DAO.modCapitalInfoStatus(capitalID, rVal.funID, 1, curTime, conn);
					DAO.modCapitalInfoStatus(rateID, rVal.funID, 1, curTime, conn);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			result = -10014L;
			log("市场端发起入金SQLException:" + result);
		} catch (Exception e) {
			e.printStackTrace();
			result = -10015L;
			log("市场端发起入金Exception:" + result);
		} finally {
			DAO.closeStatement(null, null, conn);
		}

		return result;
	}

	public long moneyInAudit(long actionID, boolean funFlag) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("actionID[" + actionID + "]funFlag[" + funFlag + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}}");
		String auditInfo = "审核业务流水号：" + actionID + ";";

		Connection conn = null;

		Timestamp curTime = new Timestamp(System.currentTimeMillis());

		CapitalValue capital = null;

		CapitalValue rate = null;

		ReturnValue rVal = null;

		long result = 0L;

		if ((getConfig("AuditBeginTime") != null) && (getConfig("AuditBeginTime").length() > 0)) {
			Time AuditBeginTime = Tool.convertTime(getConfig("AuditBeginTime"));
			Time curSqlTime = Tool.convertTime(Tool.fmtOnlyTime(curTime));
			if ((AuditBeginTime != null) && (AuditBeginTime.after(curSqlTime))) {
				result = -20018L;
				log("审核时间未到！");
				return result;
			}

		}

		if ((getConfig("AuditEndTime") != null) && (getConfig("AuditEndTime").length() > 0)) {
			Time AuditEndTime = Tool.convertTime(getConfig("AuditEndTime"));
			Time curSqlTime = Tool.convertTime(Tool.fmtOnlyTime(curTime));
			if ((AuditEndTime != null) && (curSqlTime.after(AuditEndTime))) {
				result = -20017L;
				log("审核时间已过！");
				return result;
			}
		}

		try {
			conn = DAO.getConnection();
			Vector list = DAO.getCapitalInfoList("where actionid=" + actionID, conn);
			for (int i = 0; i < list.size(); i++) {
				CapitalValue val = (CapitalValue) list.get(i);
				if ((val.type == 1) || (val.type == 0)) {
					capital = val;
					if ((capital.status == 0) || (capital.status == 1)) {
						log("本条记录已经审核" + new java.util.Date());
						result = -3L;
						long l1 = result;
						return l1;
					}
				} else if (val.type == 2) {
					rate = val;
				}
			}
			if ((capital != null) && (rate != null))
				if (funFlag) {
					if (result == 0L) {
						try {
							conn.setAutoCommit(false);
							if (capital.type == 1) {
								System.out.println("[流水信息：]" + capital.toString());
								rVal = new ReturnValue();
								rVal.result = 0L;
								rVal.actionId = capital.actionID;
								rVal.funID = capital.funID;

								dataProcess.updateFundsFull(capital.firmID, dataProcess.getOUTSUMMARY() + "",
										(String) dataProcess.getBANKSUB().get(capital.bankID), capital.money, actionID, conn);
								dataProcess.updateFundsFull(capital.firmID, dataProcess.getFEESUMMARY() + "", null, rate.money, actionID, conn);
								dataProcess.updateFrozenFunds(capital.firmID, -1.0D * Arith.add(capital.money, rate.money), conn);

								bankFrozenFuns(capital.firmID, capital.bankID, null, -1.0D * Arith.add(capital.money, rate.money), conn);

								DAO.modCapitalInfoStatus(capital.iD, rVal.funID, 0, curTime, conn);
								DAO.modCapitalInfoStatus(rate.iD, rVal.funID, 0, curTime, conn);

								DAO.modCapitalInfoNote(capital.iD, "[" + capital.note + "]审核通过，银行处理成功，出金成功", conn);
								DAO.modCapitalInfoNote(rate.iD, "[" + capital.note + "]审核通过，银行处理成功，扣除手续费成功", conn);

								log(auditInfo + "审核通过，银行处理成功,出金成功,扣除手续费成功,银行流水号=" + rVal.funID);
							} else if (capital.type == 0) {
								rVal = new ReturnValue();
								rVal.result = 0L;
								rVal.actionId = capital.actionID;
								rVal.funID = capital.funID;

								dataProcess.updateFundsFull(capital.firmID, dataProcess.getINSUMMARY() + "",
										(String) dataProcess.getBANKSUB().get(capital.bankID), capital.money, actionID, conn);
								dataProcess.updateFundsFull(capital.firmID, dataProcess.getFEESUMMARY() + "", null, rate.money, actionID, conn);

								DAO.modCapitalInfoStatus(capital.iD, rVal.funID, 0, curTime, conn);
								DAO.modCapitalInfoStatus(rate.iD, rVal.funID, 0, curTime, conn);
							}
							conn.commit();
						} catch (SQLException e) {
							conn.rollback();
							e.printStackTrace();
							result = -30004L;
							log(auditInfo + "审核出金SQLException，错误码=" + result + "  " + e);
						} finally {
							conn.setAutoCommit(true);
						}
					}
				} else
					try {
						conn.setAutoCommit(false);
						if (capital.type == 1) {
							rVal = new ReturnValue();
							rVal.result = 0L;
							rVal.actionId = capital.actionID;
							rVal.funID = capital.funID;

							dataProcess.updateFrozenFunds(capital.firmID, -1.0D * Arith.add(capital.money, rate.money), conn);

							bankFrozenFuns(capital.firmID, capital.bankID, null, -1.0D * Arith.add(capital.money, rate.money), conn);
							DAO.modCapitalInfoStatus(capital.iD, rVal == null ? "" : rVal.funID, 1, curTime, conn);
							DAO.modCapitalInfoStatus(rate.iD, rVal == null ? "" : rVal.funID, 1, curTime, conn);

							DAO.modCapitalInfoNote(capital.iD, "[" + capital.note + "]审核拒绝成功", conn);
							DAO.modCapitalInfoNote(rate.iD, "[" + capital.note + "]审核拒绝成功", conn);

							log(auditInfo + "审核拒绝成功");
						} else if (capital.type == 0) {
							rVal = new ReturnValue();
							rVal.result = 0L;
							rVal.actionId = capital.actionID;
							rVal.funID = capital.funID;
							DAO.modCapitalInfoStatus(capital.iD, rVal == null ? "" : rVal.funID, 1, curTime, conn);
							DAO.modCapitalInfoStatus(rate.iD, rVal == null ? "" : rVal.funID, 1, curTime, conn);
						}
						conn.commit();
					} catch (SQLException e) {
						conn.rollback();
						e.printStackTrace();
						result = -30004L;
						log(auditInfo + "审核出金SQLException，错误码=" + result + "  " + e);
					} finally {
						conn.setAutoCommit(true);
					}
		} catch (SQLException e) {
			e.printStackTrace();
			result = -30004L;
			log(auditInfo + "审核出金SQLException，错误码=" + result + "  " + e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			DAO.closeStatement(null, null, conn);
		}

		return result;
	}

	public ReturnValue outMoney(String bankID, double money, String contact, String bankAccount, String funID, String operator, int express,
			int type) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{出金{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("bankID[" + bankID + "]money[" + money + "]contact[" + contact + "]bankAccount[" + bankAccount + "]funID[" + funID
				+ "]operator[" + operator + "]express[" + express + "]type[" + type + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}}");

		ReturnValue rv = new ReturnValue();

		Vector vecCorr = null;
		try {
			vecCorr = DAO.getCorrespondList(" where contact='" + contact + "' and bankID='" + bankID + "' and isOpen=1");
		} catch (Exception e1) {
			log("查询签约关系异常：" + Tool.getExceptionTrace(e1));
		}
		if ((vecCorr == null) || (vecCorr.size() != 1)) {
			log("签约号[" + contact + "]和银行[" + bankID + "]还没有签约，或者存在多条签约关系");
			rv.result = -1L;
			rv.remark = "还没有合该银行签约";
			return rv;
		}
		String firmID = ((CorrespondValue) vecCorr.get(0)).firmID;

		Vector vecf2f = null;
		try {
			vecf2f = DAO.getFirmID2SysFirmID(" where firmid='" + firmID + "' and bankID='" + bankID + "'");
		} catch (SQLException e) {
			log("查询平台号和交易系统交易商对应关系异常：" + Tool.getExceptionTrace(e));
		}
		if ((vecf2f == null) || (vecf2f.size() <= 0)) {
			log("平台号[" + firmID + "]在银行[" + bankID + "]下不存在和交易系统交易商的对应关系，不允许入金");
			rv.result = -1L;
			rv.remark = "没找到对应关系或存在多条对应关系";
			return rv;
		}

		long PTactionID = getMktActionID();
		InOutMoney outMoney = new InOutMoney();
		outMoney.systemID = ((FirmID2SysFirmID) vecf2f.get(0)).systemID;
		outMoney.defaultSystem = ((FirmID2SysFirmID) vecf2f.get(0)).defaultSystem;
		outMoney.sysFirmID = ((FirmID2SysFirmID) vecf2f.get(0)).sysFirmID;
		outMoney.funID = funID;
		outMoney.bankID = bankID;
		outMoney.account = bankAccount;
		outMoney.money = money;
		outMoney.remark = "银行端出金";
		outMoney.sysToSys = "1";
		outMoney.actionID = PTactionID;
		outMoney.firmID = firmID;
		outMoney.type = type;

		rv = outMoneyBank(outMoney);
		if (rv.result == 0L) {
			rv.remark = "出金成功";
			log("子系统端出金成功");
		} else if (rv.result == 5L) {
			log("子系统端出金，处理中");
			rv.remark = "处理中";
		} else {
			rv.result = -1L;
			rv.remark = "出金处理失败";
		}
		rv.actionId = PTactionID;
		return rv;
	}

	public long transferMoney(String bankID, int type, double money, String operator, String firmID) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("划转资金");
		System.out.println("bankID[" + bankID + "]type[" + type + "]money[" + money + "]operator[" + operator + "]firmID[" + firmID + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}");
		long result = -1L;
		try {
			TransMnyObjValue transObj = DAO.getTransMnyObj(type);
			TransMoney transMny = (TransMoney) Class.forName("gnnt.trade.bank." + transObj.className).newInstance();
			result = transMny.tranfer(bankID, money, operator, firmID, this);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public long rgstAccount(CorrespondValue correspondValue) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("银行帐号注册");
		System.out.println("correspondValue[" + correspondValue + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}}");
		long result = 0L;
		String defaultAccount = getConfig("DefaultAccount");
		Connection conn = null;
		if ((correspondValue.bankID == null) || (correspondValue.bankID.length() == 0) || (correspondValue.firmID == null)
				|| (correspondValue.firmID.length() == 0) || (correspondValue.account == null) || (correspondValue.account.length() == 0)) {
			return -40001L;
		}

		try {
			conn = DAO.getConnection();

			if (DAO.getFirm(correspondValue.firmID, conn) == null) {
				result = -40002L;
				log("银行帐号注册，交易商不存在，错误码=" + result);
			} else if (DAO.getBank(correspondValue.bankID, conn) == null) {
				result = -40003L;
				log("银行帐号注册，银行不存在，错误码=" + result);
			} else if (DAO.getCorrespond(correspondValue.bankID, correspondValue.firmID, correspondValue.account, conn) != null) {
				result = -40004L;
				log("银行帐号注册，帐号已注册，错误码=" + result);
			} else if (DAO.getCorrespondList(" where bankID='" + correspondValue.bankID + "' and firmID='" + correspondValue.firmID
					+ "' and (account='" + correspondValue.account + "' or account='" + defaultAccount + "') and isopen=0", conn).size() > 0) {
				BankAdapterRMI bankadapter = getAdapter(correspondValue.bankID);
				ReturnValue returnValue = bankadapter.rgstAccountQuery(correspondValue);
				if ((returnValue == null) || (returnValue.result == 0L)) {
					if ((correspondValue.bankID.equals("017")) || (correspondValue.bankID.equals("17"))) {
						correspondValue.account1 = returnValue.remark;
					}

					correspondValue.isOpen = 1;
					DAO.modCorrespond(correspondValue, conn);
					log("满足注册条件，修改为签约状态" + result);
				} else {
					log(returnValue.remark);

					result = -40005L;
					log("银行帐号注册，银行签约失败，错误码=" + result);
				}
			} else if (DAO.getCorrespondList(" where bankID='" + correspondValue.bankID + "' and firmID='" + correspondValue.firmID
					+ "' and account='" + correspondValue.account + "' and status=1 and isopen=1", conn).size() > 0) {
				DAO.modCorrespond(correspondValue, conn);
			} else if (DAO.getCorrespondList(" where bankID='" + correspondValue.bankID + "' and firmID='" + correspondValue.firmID
					+ "' and account='" + correspondValue.account + "'", conn).size() <= 0) {
				log("添加交易商");

				DAO.addCorrespond(correspondValue, conn);
			} else {
				log("帐号已存在");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			result = -40006L;
			log("银行帐号注册SQLException，错误码=" + result + "  " + e);
		} catch (Exception e) {
			e.printStackTrace();
			result = -40007L;
			log("银行帐号注册Exception，错误码=" + result + "  " + e);
		} finally {
			DAO.closeStatement(null, null, conn);
		}

		return result;
	}

	public long delAccount(CorrespondValue correspondValue) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("银行帐号注销");
		System.out.println("correspondValue[" + correspondValue + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}");
		long result = 0L;

		ReturnValue rv = delCorrBank(correspondValue);
		result = rv.result;
		return result;
	}

	public long delAccountMaket(CorrespondValue correspondValue) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("银行帐号注销");
		System.out.println("correspondValue[" + correspondValue.toString() + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}");
		long result = 0L;
		Connection conn = null;
		if ((correspondValue.bankID == null) || (correspondValue.bankID.length() == 0) || (correspondValue.firmID == null)
				|| (correspondValue.firmID.length() == 0) || (correspondValue.account == null) || (correspondValue.account.length() == 0)) {
			return -40011L;
		}
		try {
			conn = DAO.getConnection();
			Vector vector = DAO.getCorrespondList(" where bankID='" + correspondValue.bankID + "' and firmID='" + correspondValue.firmID
					+ "' and account='" + correspondValue.account + "'", conn);
			if ((vector == null) || (vector.size() <= 0)) {
				result = -40014L;
				log("银行帐号注销，帐号未注册，错误码=" + result);
			} else {
				CorrespondValue cv = (CorrespondValue) vector.get(0);
				if (cv.frozenFuns > 0.0D)
					return -920002L;

				if (cv.isOpen == 1) {
					ReturnValue returnValue = null;
					returnValue = ifQuitFirm(correspondValue.firmID, correspondValue.bankID);
					if (returnValue.result != 0L) {
						log("不满足解约条件");
						log("结果：result[" + returnValue.result + "]remark[" + returnValue.remark + "]");
						long l1 = returnValue.result;
						return l1;
					}

					BankAdapterRMI bankadapter = getAdapter(correspondValue.bankID);
					if (bankadapter != null) {
						returnValue = bankadapter.delAccountQuery(correspondValue);
					}
					if ((returnValue != null) && (returnValue.result == 0L)) {
						DAO.delCorrespond(correspondValue, conn);
					} else {
						log(returnValue.remark);
						result = -40015L;
						log("银行帐号注销，银行解约失败，错误码=" + result);
					}
				} else {
					DAO.delCorrespond(correspondValue, conn);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			result = -40016L;
			log("银行帐号注销SQLException，错误码=" + result + "  " + e);
		} catch (Exception e) {
			e.printStackTrace();
			result = -40017L;
			log("银行帐号注销Exception，错误码=" + result + "  " + e);
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public int setMoneyInfo(String bankID, java.util.Date date) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("给银行发送数据");
		System.out.println("bankID[" + bankID + "]date[" + date + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}");
		boolean traderStatus = false;
		int response = 2;
		Connection conn = null;
		if (date == null) {
			date = new java.util.Date();
		}
		try {
			conn = DAO.getConnection();

			traderStatus = DAO.getTraderStatus();
			if (traderStatus) {
				if (bankID == null) {
					BankAdapterRMI bankadapter = getAdapter(bankID);
					if (bankadapter != null) {
						response = bankadapter.setBankMoneyInfo(date);
					} else {
						response = 1;
					}
				} else {
					Vector bankList = DAO.getBankList(" where validFlag=0 ");
					for (int i = 0; i < bankList.size(); i++) {
						BankValue bv = (BankValue) bankList.get(i);
						BankAdapterRMI bankadapter = getAdapter(bv.bankID);
						if (bankadapter != null) {
							response = bankadapter.setBankMoneyInfo(date);
						}
						if (response != 0) {
							response--;
						}
					}
				}
			}
		} catch (SQLException e) {
			response = 3;
			e.printStackTrace();
			log("给银行发送数据SQLException:" + e);
		} catch (Exception e) {
			response = 3;
			e.printStackTrace();
			log("给银行发送数据Exception:" + e);
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return response;
	}

	public long setMoneyInfoByHashtable(String bankID, Hashtable<String, TradeResultValue> ht) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("bankID[" + bankID + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}");
		boolean traderStatus = false;
		ReturnValue returnValue = null;
		long response = 2L;
		int amount = 100;
		Connection conn = null;
		try {
			conn = DAO.getConnection();

			traderStatus = DAO.getTraderStatus();
			if (traderStatus) {
				if (bankID == null) {
					BankAdapterRMI bankadapter = getAdapter(bankID);
					if (bankadapter != null) {
						returnValue = bankadapter.setBankMoneyInfo(ht);
					} else {
						response = 1L;
					}
				} else {
					Vector bankList = DAO.getBankList(" where validFlag=0 ");
					for (int i = 0; i < bankList.size(); i++) {
						BankValue bv = (BankValue) bankList.get(i);
						BankAdapterRMI bankadapter = getAdapter(bv.bankID);
						if (bankadapter != null) {
							returnValue = bankadapter.setBankMoneyInfo(ht);
						}
						if (returnValue.result != 0L) {
							amount++;
						}
					}
				}
			}

		} catch (SQLException e) {
			response = 3L;
			e.printStackTrace();
			log("给银行发送数据SQLException:" + e);
		} catch (Exception e) {
			response = 3L;
			e.printStackTrace();
			log("给银行发送数据Exception:" + e);
		} finally {
			DAO.closeStatement(null, null, conn);
		}

		if ((returnValue != null) && (amount == 100)) {
			response = returnValue.result;
		} else {
			response = amount;
		}
		return response;
	}

	public long setMoneyInfoAutoOrNo(String bankID, java.util.Date date, int moduleid) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("给银行发送数据");
		System.out.println("bankID[" + bankID + "]date[" + date + "]moduleid[" + moduleid + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}");
		boolean traderStatus = false;
		long response = 0L;
		ReturnValue returnValue = null;
		Connection conn = null;
		if (date == null) {
			date = new java.util.Date();
		}
		try {
			conn = DAO.getConnection();

			traderStatus = DAO.getTraderStatus();
			if (traderStatus) {
				if (bankID == null) {
					BankAdapterRMI bankadapter = getAdapter(bankID);
					if (bankadapter != null) {
						String filter = " 1=1 ";
						if (date != null)
							filter = filter + " and f.b_date=to_date('" + date + "','yyyy-MM-dd')";
						Hashtable ht = DAO.getTradeDataInHashTable(filter, moduleid);
						returnValue = bankadapter.setBankMoneyInfo(ht);
						response = returnValue.result;
					} else {
						response = 1L;
					}
				} else {
					Vector bankList = DAO.getBankList(" where validFlag=0 ");
					String filter = " 1=1 ";
					if (date != null)
						filter = filter + " and f.b_date=to_date('" + date + "','yyyy-MM-dd')";
					Hashtable ht = DAO.getTradeDataInHashTable(filter, moduleid);
					for (int i = 0; i < bankList.size(); i++) {
						BankValue bv = (BankValue) bankList.get(i);
						BankAdapterRMI bankadapter = getAdapter(bv.bankID);
						returnValue = bankadapter.setBankMoneyInfo(ht);
						if ((returnValue == null) || (returnValue.result != 0L)) {
							response -= 1L;
						}
					}
				}

			} else {
				response = 2L;
			}
		} catch (SQLException e) {
			response = 3L;
			e.printStackTrace();
			log("给银行发送数据SQLException:" + e);
		} catch (Exception e) {
			response = 3L;
			e.printStackTrace();
			log("给银行发送数据Exception:" + e);
		} finally {
			DAO.closeStatement(null, null, conn);
		}

		return response;
	}

	public int getBankCompareInfo(String bankID, java.util.Date date) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("获取银行对账信息");
		System.out.println("bankID[" + bankID + "]date[" + date + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}");
		int result = 0;
		if (bankID == null) {
			Enumeration enumeration = ADAPTERLIST.elements();
			while (enumeration.hasMoreElements()) {
				Vector moneyInfoList = null;
				try {
					Vector v = DAO.getDicList(" where bankid='" + bankID + "' order by type,id ");
					BankAdapterRMI bankadapter = getAdapter((String) enumeration.nextElement());
					moneyInfoList = bankadapter.getBankMoneyInfo(date, v);
				} catch (RemoteException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (moneyInfoList != null) {
					if (insertBankCompareInfo(moneyInfoList) != 0) {
						result = -30012;
						log("获取银行对账信息,错误码=" + result);
					}
				}
			}
		} else {
			BankAdapterRMI bankadapter = getAdapter(bankID);
			if (bankadapter != null) {
				Vector moneyInfoList = null;
				try {
					Vector v = DAO.getDicList(" order by type,id ");
					moneyInfoList = bankadapter.getBankMoneyInfo(date, v);
				} catch (RemoteException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (moneyInfoList != null) {
					if (insertBankCompareInfo(moneyInfoList) != 0) {
						result = -30013;
						log("获取银行对账信息,错误码=" + result);
					}
				} else {
					result = -30013;
					log("获取银行对账信息,错误码=" + result);
				}
			} else {
				result = -30011;
				log("获取银行对账信息,错误码=" + result);
			}
		}
		return result;
	}

	public int checkMoneyByNumber() {
		int result = 0;

		Connection conn = null;
		try {
			conn = DAO.getConnection();

			if ((getConfig("compareCapNumber") != null) && (getConfig("compareCapNumber").equalsIgnoreCase("true"))) {
				int sumCapnumber = DAO.countCapitalInfo(null, -1);
				int sumBankCapnumber = DAO.countBankCompareInfo(null, -1);

				if (sumCapnumber == sumBankCapnumber) {
					result = 1;
				} else {
					result = 2;
				}
			}
		} catch (SQLException e) {
			result = -1;
			e.printStackTrace();
			log("对账检查SQLException：" + e);
		} catch (ClassNotFoundException e) {
			result = -1;
			e.printStackTrace();
			log("对账检查ClassNotFoundException：" + e);
		} catch (Exception e) {
			result = -1;
			e.printStackTrace();
			log("对账检查Exception：" + e);
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public int insertBankCompareInfo(Vector<MoneyInfoValue> list) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("写入银行对账信息");
		System.out.println("list.size()[" + list.size() + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}");
		int result = 0;
		Connection conn = null;
		String dateStr = "";
		try {
			if ((list != null) && (list.size() > 0)) {
				if (((MoneyInfoValue) list.get(0)).compareDate != null) {
					dateStr = ((MoneyInfoValue) list.get(0)).compareDate.toString();
					String dateStr1 = "";
					for (int i = 1; i < list.size(); i++) {
						if (((MoneyInfoValue) list.get(i)).compareDate != null) {
							dateStr1 = ((MoneyInfoValue) list.get(i)).compareDate.toString();
							if (!dateStr1.equals(dateStr)) {
								result = -1;
								log("写入银行对账信息,对账日期不相符，错误码=" + result);
								break;
							}
						} else {
							result = -1;
							log("写入银行对账信息,对账日期为空，错误码=" + result);
							break;
						}
					}
				} else {
					result = -1;
				}
			} else if (list == null) {
				result = -2;
			}

			if (result == 0) {
				try {
					conn = DAO.getConnection();

					conn.setAutoCommit(false);

					for (int i = 0; i < list.size(); i++) {
						MoneyInfoValue moneyInfoValue = (MoneyInfoValue) list.get(i);

						Vector curList = DAO.getMoneyInfoList("where funID='" + moneyInfoValue.id + "' and trunc(comparedate)=to_date('"
								+ Tool.fmtDate(moneyInfoValue.compareDate) + "','yyyy-MM-dd') and bankid='" + moneyInfoValue.bankID + "'", conn);
						if (curList.size() == 0) {
							DAO.addMoneyInfo(moneyInfoValue, conn);
						}
					}

					conn.commit();
				} catch (SQLException e) {
					e.printStackTrace();
					log("写入银行对账信息异常，数据回滚");
					throw e;
				} finally {
					conn.setAutoCommit(true);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			result = -3;
			log("写入银行对账信息异常，错误码=" + result);
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public Hashtable<String, String> getBankInfo(String bankID) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("根据银行ID获取市场银行信息");
		System.out.println("bankId[" + bankID + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}}");
		Hashtable hashTable = new Hashtable();

		Connection conn = null;
		try {
			conn = DAO.getConnection();

			Vector list = DAO.getDicList("where type=2 and bankid='" + bankID + "'", conn);
			for (int i = 0; i < list.size(); i++) {
				DicValue val = (DicValue) list.get(i);
				if (val.value == null) {
					val.value = "";
				}
				hashTable.put(val.name, val.value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAO.closeStatement(null, null, conn);
		}

		return hashTable;
	}

	public double queryFirmBalance(String firmID) throws SQLException {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("查询交易员保证金余额");
		System.out.println("firmID[" + firmID + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}");
		double result = -1.0D;

		Connection conn = null;
		try {
			conn = DAO.getConnection();
			result = dataProcess.getRealFunds(firmID, conn);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			result = -1.0D;
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public Vector<CapitalValue> getCapitalList(String filter) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("获取市场资金流水数据");
		System.out.println("filter[" + filter + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}}");

		Connection conn = null;
		Vector capitalList = null;
		try {
			conn = DAO.getConnection();
			capitalList = DAO.getCapitalInfoList(filter, conn);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return capitalList;
	}

	public Vector<BankCompareInfoValue> getBankCapList(String filter) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("获取银行资金流水数据");
		System.out.println("filter[" + filter + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}}}");

		Connection conn = null;
		Vector capitalList = null;
		try {
			conn = DAO.getConnection();
			capitalList = DAO.getBankCapInfoList(filter);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return capitalList;
	}

	public Vector<CorrespondValue> getCorrespondValue(String filter) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("获取交易商银行对应关系");
		System.out.println("filter[" + filter + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}}");

		Connection conn = null;
		Vector correspondList = null;
		try {
			conn = DAO.getConnection();
			correspondList = DAO.getCorrespondList(filter, conn);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return correspondList;
	}

	public Map<String, CorrespondValue> getCorrespondValue(Vector<String> firmIDs, String bankID) {
		Map result = new HashMap();

		Connection conn = null;
		try {
			conn = DAO.getConnection();
			if ((firmIDs != null) && (firmIDs.size() > 0)) {
				for (String firmID : firmIDs) {
					Vector correspondList = DAO.getCorrespondList(" where firmid='" + firmID + "' and bankid='" + bankID + "' ", conn);
					if ((correspondList != null) && (correspondList.size() > 0)) {
						CorrespondValue cv = (CorrespondValue) correspondList.get(0);
						result.put(firmID, cv);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public ReturnValue chargeAgainst(ChargeAgainstValue cav) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{冲证(银行方调用){{{{{{{{{{{{{{{{{{{{{");
		System.out.println("cav[" + cav.toString() + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}");
		Connection conn = null;
		String filter = null;
		ReturnValue rv = new ReturnValue();
		rv.result = 0L;
		if (cav == null) {
			rv.result = -500001L;
			rv.remark = "传入的参数为空";
		}
		synchronized (cav) {
			Map firmMsg = new HashMap();
			try {
				conn = DAO.getConnection();
				filter = " where bankID='" + cav.bankID
						+ "' and (trunc(createtime)=trunc(sysdate) or trunc(bankTime)=trunc(sysdate)) and status=0 and (type=0 or type=1 or type=2) ";
				if (cav.actionID > 0L) {
					filter = filter + " and actionID=" + cav.actionID;
				} else if ((cav.funID != null) && (cav.funID.trim().length() > 0)) {
					filter = filter + " and funID='" + cav.funID.trim() + "' ";
				} else {
					rv.result = -500003L;
					rv.remark = "冲正流水时市场流水号和银行流水号至少选其一";
				}
				if (rv.result == 0L) {
					Vector capList = DAO.getCapitalInfoList(filter, conn);
					if ((capList != null) && (capList.size() > 0)) {
						conn.setAutoCommit(false);
						FirmBalanceValue fbv = null;
						CapitalValue cv = new CapitalValue();
						cv.actionID = DAO.getActionID(conn);
						cv.bankTime = cav.bankTime;
						cv.funID = cav.funIDCA;
						cv.money = 0.0D;
						cv.bankID = cav.bankID;
						cv.note = "bank_charge 冲正，记录号:";

						for (int i = 0; i < capList.size(); i++) {
							CapitalValue capVal = (CapitalValue) capList.get(i);
							fbv = null;
							if (firmMsg.get(capVal.firmID) != null) {
								fbv = (FirmBalanceValue) firmMsg.get(capVal.firmID);
							} else {
								String filter2 = " where firmid='" + capVal.firmID + "' for update ";
								fbv = DAO.availableBalance(filter2, conn);
								if (fbv != null) {
									if ("true".equalsIgnoreCase(Tool.getConfig("fuYing"))) {
										try {
											BankDAO DAO = BankDAOFactory.getDAO();
											Vector<FirmBalanceValue> floatingloss = DAO.getFlote(new String[] { capVal.firmID });
											if ((floatingloss != null) && (floatingloss.size() > 0))
												for (FirmBalanceValue tp : floatingloss)
													if ((tp != null) && (capVal.firmID.equals(tp.firmId))) {
														fbv.floatingloss = tp.floatingloss;
														if (tp.floatingloss <= 0.0D)
															break;
														fbv.avilableBalance -= tp.floatingloss;

														break;
													}
										} catch (SQLException e) {
											throw e;
										} catch (Exception e) {
											e.printStackTrace();
										}
									}
									firmMsg.put(capVal.firmID, fbv);
								} else {
									rv.result = -500004L;
									rv.remark = ("没有查询到交易商" + capVal.firmID);
									throw new Exception("没有查询到当个交易商" + capVal.firmID);
								}
							}
							if (capVal.type == 0) {
								cv.firmID = capVal.firmID;
								cv.money += -1.0D * capVal.money;
								cv.debitID = capVal.creditID;
								cv.creditID = capVal.debitID;
								cv.note = (cv.note + capVal.iD + " ");
								cv.type = 4;
								capVal.status = -1;
								cv.oprcode = dataProcess.getINSUMMARY() + "";
							} else if (capVal.type == 1) {
								cv.firmID = capVal.firmID;
								cv.money += capVal.money;
								cv.debitID = capVal.creditID;
								cv.creditID = capVal.debitID;
								cv.note = (cv.note + capVal.iD + " ");
								cv.type = 5;
								capVal.status = -1;
								cv.oprcode = dataProcess.getOUTSUMMARY() + "";
							} else if (capVal.type == 2) {
								cv.money += capVal.money;
								cv.note = (cv.note + capVal.iD + " ");
								capVal.status = -1;
							}
							DAO.modCapitalInfoStatus(capVal.iD, capVal.funID, capVal.status, capVal.bankTime, conn);
						}
						if ((fbv == null) || (cv.money + fbv.avilableBalance < 0.0D)) {
							rv.result = -500004L;
							rv.remark = "没有查询到交易商，或本交易商当前资金不足以冲正";
							throw new Exception("在交易系统的交易商资金表查询不到本交易商");
						}
						DAO.addCapitalInfo(cv, conn);
						dataProcess.updateFundsFull(cv.firmID, cv.oprcode, (String) dataProcess.getBANKSUB().get(cv.bankID),
								(dataProcess.getOUTSUMMARY() + "").equalsIgnoreCase(cv.oprcode) ? -cv.money : cv.money, cv.actionID, conn);
						conn.commit();
					} else {
						rv.result = -50007L;
						rv.remark = ("查询流水不存在：市场流水号[" + cav.actionID + "]银行流水号[" + cav.funID + "]");
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
				if (rv.result == 0L) {
					rv.result = -500005L;
					rv.remark = "冲正操作中出现数据库异常";
				}
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
				if (rv.result == 0L) {
					rv.result = -50009L;
					rv.remark = "冲正操作中出现系统异常";
				}
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} finally {
				try {
					conn.setAutoCommit(true);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				DAO.closeStatement(null, null, conn);
			}
		}
		return rv;
	}

	public ReturnValue chargeAgainstMaket(ChargeAgainstValue cav) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{冲证(市场方调用){{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("cav[" + cav.toString() + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}");
		Connection conn = null;
		String filter = null;
		ReturnValue rv = new ReturnValue();
		rv.result = 0L;
		if (cav == null) {
			rv.result = -500001L;
			rv.remark = "传入的参数为空";
		}

		Map firmMsg = new HashMap();
		try {
			conn = DAO.getConnection();
			filter = " where bankID='" + cav.bankID
					+ "' and (trunc(createtime)=trunc(sysdate) or trunc(bankTime)=trunc(sysdate)) and status=0 and (type=0 or type=1 or type=2) ";
			if (cav.actionID > 0L) {
				filter = filter + " and actionID=" + cav.actionID;
			} else if ((cav.funID != null) && (cav.funID.trim().length() > 0)) {
				filter = filter + " and funID=" + cav.funID.trim();
			} else {
				rv.result = -500003L;
				rv.remark = "冲正流水时市场流水号和银行流水号至少选其一";
			}
			if (rv.result == 0L) {
				Vector capList = DAO.getCapitalInfoList(filter, conn);
				if ((capList != null) && (capList.size() > 0)) {
					conn.setAutoCommit(false);
					FirmBalanceValue fbv = null;
					CapitalValue cv = new CapitalValue();
					cv.actionID = DAO.getActionID(conn);
					cv.money = 0.0D;
					cv.bankID = cav.bankID;
					cv.note = "maket_charge冲正，记录号：";

					for (int i = 0; i < capList.size(); i++) {
						CapitalValue capVal = (CapitalValue) capList.get(i);
						fbv = null;
						if (firmMsg.get(capVal.firmID) != null) {
							fbv = (FirmBalanceValue) firmMsg.get(capVal.firmID);
						} else {
							String filter2 = " where firmid='" + capVal.firmID + "' for update ";
							fbv = DAO.availableBalance(filter2, conn);
							if (fbv != null) {
								if ("true".equalsIgnoreCase(Tool.getConfig("fuYing"))) {
									try {
										BankDAO DAO = BankDAOFactory.getDAO();
										Vector<FirmBalanceValue> floatingloss = DAO.getFlote(new String[] { capVal.firmID });
										if ((floatingloss != null) && (floatingloss.size() > 0))
											for (FirmBalanceValue tp : floatingloss)
												if ((tp != null) && (capVal.firmID.equals(tp.firmId))) {
													fbv.floatingloss = tp.floatingloss;
													if (tp.floatingloss <= 0.0D)
														break;
													fbv.avilableBalance -= tp.floatingloss;

													break;
												}
									} catch (SQLException e) {
										throw e;
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
								firmMsg.put(capVal.firmID, fbv);
							} else {
								rv.result = -500004L;
								rv.remark = ("没有查询到交易商" + capVal.firmID);
								throw new Exception("没有查询到当个交易商" + capVal.firmID);
							}
						}
						if (capVal.type == 0) {
							cv.firmID = capVal.firmID;
							cv.money += -1.0D * capVal.money;
							cv.debitID = capVal.creditID;
							cv.creditID = capVal.debitID;
							cv.note = (cv.note + capVal.iD + " ");
							cv.type = 4;
							capVal.status = -1;
							cv.oprcode = dataProcess.getINSUMMARY() + "";
						} else if (capVal.type == 1) {
							cv.firmID = capVal.firmID;
							cv.money += capVal.money;
							cv.debitID = capVal.creditID;
							cv.creditID = capVal.debitID;
							cv.note = (cv.note + capVal.iD + " ");
							cv.type = 5;
							capVal.status = -1;
							cv.oprcode = dataProcess.getOUTSUMMARY() + "";
						} else if (capVal.type == 2) {
							cv.money += capVal.money;
							cv.note = (cv.note + capVal.iD + " ");
							capVal.status = -1;
						}
						DAO.modCapitalInfoStatus(capVal.iD, capVal.funID, capVal.status, capVal.bankTime, conn);
					}
					if ((fbv == null) || (cv.money + fbv.avilableBalance < 0.0D)) {
						rv.result = -500004L;
						rv.remark = "没有查询到交易商，或本交易商当前资金不足以冲正";
						throw new Exception("在交易系统的交易商资金表查询不到本交易商");
					}

					ReturnValue rvt = null;
					if ((rvt == null) || (rvt.result == 5L) || (rvt.result == -50010L)) {
						cv.status = 2;
						DAO.addCapitalInfo(cv, conn);
						if ((dataProcess.getINSUMMARY() + "").equalsIgnoreCase(cv.oprcode))
							dataProcess.updateFundsFull(cv.firmID, cv.oprcode, (String) dataProcess.getBANKSUB().get(cv.bankID),
									(dataProcess.getOUTSUMMARY() + "").equalsIgnoreCase(cv.oprcode) ? -cv.money : cv.money, cv.actionID, conn);
					} else if (rvt.result == 0L) {
						cv.status = 0;
						DAO.addCapitalInfo(cv, conn);
						dataProcess.updateFundsFull(cv.firmID, cv.oprcode, (String) dataProcess.getBANKSUB().get(cv.bankID),
								(dataProcess.getOUTSUMMARY() + "").equalsIgnoreCase(cv.oprcode) ? -cv.money : cv.money, cv.actionID, conn);
					} else if (rvt.result < 0L) {
						cv.status = 1;
						DAO.addCapitalInfo(cv, conn);
					}
					conn.commit();
				} else {
					rv.result = -50007L;
					rv.remark = ("查询流水不存在：市场流水号[" + cav.actionID + "]银行流水号[" + cav.funID + "]");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			if (rv.result == 0L) {
				rv.result = -500005L;
				rv.remark = "冲正操作中出现数据库异常";
			}
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (rv.result == 0L) {
				rv.result = -50009L;
				rv.remark = "冲正操作中出现系统异常";
			}
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DAO.closeStatement(null, null, conn);
		}

		return rv;
	}

	public Hashtable<String, Hashtable<String, String>> getFirmTradeBal(java.util.Date b_date) {
		return dataProcess.getFirmTradeBal(b_date);
	}

	public void synchronizedFirm() {
		dataProcess.synchronizedFirm();
	}

	public boolean checkFirmPwd(String firmid, String password) {
		return true;
	}

	public long sendTotalMoneyToBank(String bankid, Hashtable<String, Double> ht) {
		BankAdapterRMI bankadapter = getAdapter(bankid);
		ReturnValue rv = null;
		if (bankadapter != null) {
			try {
				rv = bankadapter.setTotalMoneyInfo(ht);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		return rv.result;
	}

	public FirmBalanceValue getMarketBalance(String firmid, String bankID) {
		FirmBalanceValue fbv = new FirmBalanceValue();
		Vector vec = null;
		try {
			vec = DAO.getFirmBankFunds(" and f.firmid='" + firmid + "' and f.bankcode='" + bankID + "'");
		} catch (SQLException e1) {
			log("分银行查询可用资金异常：" + Tool.getExceptionTrace(e1));
		}
		if ((vec != null) && (vec.size() > 0)) {
			fbv.avilableBalance = ((FirmBankFunds) vec.get(0)).balance;
			fbv.canOutBalance = ((FirmBankFunds) vec.get(0)).balance;
			fbv.marketBalance = ((FirmBankFunds) vec.get(0)).balance;
		}
		return fbv;
	}

	public FirmBalanceValue getMarketBalance(String firmid) {
		FirmBalanceValue fbv = null;
		String filter = " where 1=1 ";
		if (firmid != null) {
			filter = filter + "  and firmid='" + firmid + "'  ";
		}
		fbv = DAO.availableBalance(filter);
		if ("true".equalsIgnoreCase(getConfig("fuYing"))) {
			try {
				Vector<FirmBalanceValue> floatingloss = DAO.getFlote(new String[] { firmid });
				if ((floatingloss != null) && (floatingloss.size() > 0))
					for (FirmBalanceValue fb : floatingloss)
						if ((fb != null) && (firmid.equals(fb.firmId))) {
							fbv.floatingloss = fb.floatingloss;
							if (fb.floatingloss <= 0.0D)
								break;
							fbv.avilableBalance -= fb.floatingloss;

							break;
						}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		Connection conn = null;
		try {
			conn = DAO.getConnection();
			fbv.canOutBalance = getCanOutBalance(firmid, conn);
		} catch (Exception e) {
			log(Tool.getExceptionTrace(e));
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		System.out.println(fbv.toString());
		return fbv;
	}

	public FirmBalanceValue getMarketBalance(String firmid, Connection conn) {
		FirmBalanceValue fbv = null;
		String filter = " where 1=1 ";
		if (firmid != null) {
			filter = filter + "  and firmid='" + firmid + "'  ";
		}
		try {
			fbv = DAO.availableBalance(filter, conn);
		} catch (SQLException e1) {
			log("查询余额异常：" + Tool.getExceptionTrace(e1));
		}
		if ("true".equalsIgnoreCase(getConfig("fuYing"))) {
			try {
				Vector<FirmBalanceValue> floatingloss = DAO.getFlote(new String[] { firmid }, conn);
				if ((floatingloss != null) && (floatingloss.size() > 0))
					for (FirmBalanceValue fb : floatingloss)
						if ((fb != null) && (firmid.equals(fb.firmId))) {
							fbv.floatingloss = fb.floatingloss;
							if (fb.floatingloss <= 0.0D)
								break;
							fbv.avilableBalance -= fb.floatingloss;

							break;
						}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		fbv.canOutBalance = getCanOutBalance(firmid, conn);
		return fbv;
	}

	public FirmBalanceValue getBankBalance(String bankid, String firmid, String pwd) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("查询交易商银行资金");
		System.out.println("bankid[" + bankid + "]firmid[" + firmid + "]pwd[" + pwd + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}");
		FirmBalanceValue fv = new FirmBalanceValue();
		String filter = " where 1=1 ";
		if (firmid != null) {
			filter = filter + "  and firmid='" + firmid + "'  ";
		}
		if (bankid != null) {
			filter = filter + "  and bankid='" + bankid + "'  ";
		}

		Vector v = null;
		try {
			v = DAO.getCorrespondList(filter);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		double accountBalance = 0.0D;
		if (tradeDate(bankid) == 0L) {
			try {
				CorrespondValue cv = new CorrespondValue();
				if (v.size() > 0) {
					cv = (CorrespondValue) v.get(0);
				}
				fv.setBankAccount(cv.account);
				BankAdapterRMI bankadapter = getAdapter(bankid);
				accountBalance = bankadapter.accountQuery(cv, pwd);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		fv.setBankBalance(accountBalance);

		return fv;
	}

	public FirmBalanceValue getFirmBalance(String bankid, String firmid, String pwd) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("查询交易商市场可用资金和银行帐号余额");
		System.out.println("bankid[" + bankid + "]firmid[" + firmid + "]pwd[" + pwd + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}");
		FirmBalanceValue fv = new FirmBalanceValue();
		FirmBalanceValue fv1 = getMarketBalance(firmid);
		FirmBalanceValue fv2 = getBankBalance(bankid, firmid, pwd);

		fv.setFirmId(firmid);
		fv.setFirmBankId(bankid);

		fv.setAvilableBalance(fv1.avilableBalance);
		fv.setFrozenBalance(fv1.frozenBalance);
		fv.setMarketBalance(fv1.marketBalance);
		fv.setLastBalance(fv1.lastBalance);

		Connection conn = null;
		try {
			conn = DAO.getConnection();
			fv.setCanOutBalance(getCanOutBalance(firmid, conn));
		} catch (Exception localException) {
		} finally {
			DAO.closeStatement(null, null, conn);
		}

		fv.setBankAccount(fv2.bankAccount);
		fv.setBankBalance(fv2.bankBalance);

		return fv;
	}

	public BankValue getBank(String bankID) {
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

	public Vector<CompareResult> checkMoney(String bankID, java.util.Date date) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("手工对账检查");
		System.out.println("bankID[" + bankID + "]date[" + date + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}}");

		Vector compareResultList = null;

		Connection conn = null;
		Timestamp ts = new Timestamp(date.getTime());
		String time = Tool.fmtDate(ts);
		try {
			conn = DAO.getConnection();

			compareResultList = DAO.compareResultInfo(bankID, time);
		} catch (SQLException e) {
			e.printStackTrace();
			log("对账检查SQLException：" + e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			log("对账检查ClassNotFoundException：" + e);
		} catch (Exception e) {
			e.printStackTrace();
			log("对账检查Exception：" + e);
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return compareResultList;
	}

	public Vector<CompareResult> checkMoney(java.util.Date date) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("自动对账检查 以配置的自动对账时间为准");
		System.out.println("date[" + date + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}");
		Vector compareResultList = null;

		Connection conn = null;

		Timestamp ts = new Timestamp(date.getTime());
		String time = Tool.fmtDate(ts);
		try {
			conn = DAO.getConnection();

			Vector bankList = DAO.getBankList(" where validFlag=0 ");
			for (int i = 0; i < bankList.size(); i++) {
				BankValue bv = (BankValue) bankList.get(i);
				compareResultList = DAO.compareResultInfo(bv.bankID, time);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log("对账检查SQLException：" + e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			log("对账检查ClassNotFoundException：" + e);
		} catch (Exception e) {
			e.printStackTrace();
			log("对账检查Exception：" + e);
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return compareResultList;
	}

	public Vector<CapitalCompare> sumResultInfo(String bankID, String[] firmIDs, java.util.Date date) {
		log("查询交易商当天出入金求和数据 bankID[" + bankID + "]firmIDs[" + firmIDs + "]date[" + date + "]");
		Vector result = null;
		try {
			result = DAO.sumResultInfo(bankID, firmIDs, date);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public long insertBankMoneyInfo(Vector<MoneyInfoValue> mv, int ready) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("提供给适配器的保存银行数据的方法");
		System.out.println("mv.size()[" + mv.size() + "]ready[" + ready + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}}");
		long result = 0L;
		if ((ready == 1) || (ready == 2)) {
			if (mv != null) {
				try {
					for (int a = 0; a < mv.size(); a++) {
						MoneyInfoValue miv = (MoneyInfoValue) mv.get(a);

						Vector curList = DAO.getMoneyInfoList("where funID='" + miv.id + "' and trunc(compareDate)=to_date('"
								+ Tool.fmtDate(miv.compareDate) + "','yyyy-MM-dd') and bankid='" + miv.bankID + "'");
						if (miv.id == null) {
							miv.id = "-1";
						}

						if ((curList == null) || (curList.size() == 0)) {
							if (ready == 2) {
								miv.account = "-1";
								miv.status = 0;
								miv.note = "day_Netting";
							}
							DAO.addMoneyInfo(miv);
						}
					}
				} catch (SQLException e) {
					result = -1L;
					e.printStackTrace();
				} catch (Exception e) {
					result = -1L;
					e.printStackTrace();
				}

			} else {
				result = -1L;
			}

		}

		System.out.println("===保存银行数据------处理结果result---：" + result);
		return result;
	}

	public boolean tf(String str) {
		if ((str == null) || ("".equals(str)) || ("0".equals(str))) {
			return false;
		}

		return true;
	}

	public long outMoneyForAccess(int rvResult, String bankid, String firmid, String account, String actionid, String funcid) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{适配器线程扫描银行，获得流水状态改变并通知市场{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("rvResult[" + rvResult + "]bankid[" + bankid + "]firmid[" + firmid + "]account[" + account + "]actionid[" + actionid
				+ "]funcid[" + funcid + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}");

		long result = 0L;
		result = tradeDate(bankid);
		if (result != 0L) {
			return result;
		}

		Connection conn = null;

		long actionID = 0L;

		String funId = null;

		CapitalValue capital = null;

		CapitalValue rate = null;

		Timestamp curTime = new Timestamp(System.currentTimeMillis());
		try {
			result = rvResult;
			conn = DAO.getConnection();
			String filter = " where 1=1 and status in (2,3,4) and bankid='" + bankid + "' ";
			if (tf(firmid)) {
				filter = filter + " and firmid='" + firmid + "' ";
				System.out.println("firmid非空，值为：" + firmid);
			}
			filter = filter + "  and actionid=" + actionid + " and funid='" + funcid + "' ";
			System.out.println(filter);

			Vector list = DAO.getCapitalInfoList(filter, conn);
			if (list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					CapitalValue val = (CapitalValue) list.get(i);
					if (val.type == 1) {
						capital = val;
					} else if (val.type == 2) {
						rate = val;
					}
				}

				conn.setAutoCommit(false);
				Vector ll = DAO.getCapitalInfoList(filter + " for update ", conn);
				if ((ll == null) || (ll.size() <= 0)) {
					result = -20042L;
					log("信息已在处理中");
					long l1 = result;
					return l1;
				}

				if (result == 0L) {
					dataProcess.updateFundsFull(capital.firmID, dataProcess.getOUTSUMMARY() + "",
							(String) dataProcess.getBANKSUB().get(capital.bankID), capital.money, actionID, conn);
					dataProcess.updateFundsFull(capital.firmID, dataProcess.getFEESUMMARY() + "", null, rate.money, actionID, conn);
					dataProcess.updateFrozenFunds(capital.firmID, -1.0D * Arith.add(capital.money, rate.money), conn);

					bankFrozenFuns(capital.firmID, capital.bankID, null, -1.0D * Arith.add(capital.money, rate.money), conn);

					DAO.modCapitalInfoStatus(capital.iD, capital.funID, 0, curTime, conn);
					DAO.modCapitalInfoStatus(rate.iD, capital.funID, 0, curTime, conn);

					System.out.println("===适配器刷银行，返回成功---流水号：---" + capital.iD);
					DAO.modCapitalInfoNote(capital.iD, "market_out_Audit_1审核通过，银行处理成功，出金成功", conn);
					DAO.modCapitalInfoNote(rate.iD, "market_out_Audit_1审核通过，银行处理成功，扣除手续费成功", conn);
				} else {
					System.out.println("===适配器刷银行，返回失败------");
					if (getConfig("OutFailProcess").equalsIgnoreCase("true")) {
						dataProcess.updateFrozenFunds(capital.firmID, -1.0D * Arith.add(capital.money, rate.money), conn);

						bankFrozenFuns(capital.firmID, capital.bankID, null, -1.0D * Arith.add(capital.money, rate.money), conn);

						DAO.modCapitalInfoStatus(capital.iD, funId, 1, curTime, conn);
						DAO.modCapitalInfoStatus(rate.iD, funId, 1, curTime, conn);
						DAO.modCapitalInfoNote(capital.iD, "market_out_Audit_1审核通过，银行处理失败，出金金额全部解冻", conn);
						DAO.modCapitalInfoNote(rate.iD, "market_out_Audit_1审核通过，银行处理失败，手续费金额全部解冻", conn);
						log("审核通过，银行处理失败退还全部出金和手续费，错误码=" + result);
					} else {
						DAO.modCapitalInfoStatus(capital.iD, funId, 1, curTime, conn);
						DAO.modCapitalInfoStatus(rate.iD, funId, 1, curTime, conn);
						DAO.modCapitalInfoNote(capital.iD, "market_out_Audit_1审核通过，银行处理失败，需手工解冻出金", conn);
						DAO.modCapitalInfoNote(rate.iD, "market_out_Audit_1审核通过，银行处理失败，需手工解冻手续费", conn);
						result = -20019L;
						log("审核通过，银行处理失败，需要手工解冻出金和手续费，错误码=" + result);
					}
				}
				conn.commit();
			} else {
				System.out.println("===适配器刷银行，市场发起出金:记录资金流水错误------");
				result = -20038L;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			DAO.closeStatement(null, null, conn);
		}

		return result;
	}

	public long checkArgs(Connection conn, double realFunds, double outRate, String bankID, double money, String firmID, String bankAccount,
			int express, int type) throws SQLException {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("接口参数合法性检查方法");
		System.out.println("realFunds[" + realFunds + "]outRate[" + outRate + "]bankID[" + bankID + "]money[" + money + "]firmID[" + firmID
				+ "]bankAccount[" + bankAccount + "]express[" + express + "]type[" + type + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}}");
		long result = 0L;

		outRate = getTransRate(bankID, firmID, money, 1, express, bankAccount, conn);

		realFunds = getCanOutBalance(firmID, conn);

		if (((firmID == null) || (firmID.trim().equals(""))) && ((bankAccount == null) || (bankAccount.trim().equals("")))) {
			result = -20007L;
			log("市场发起出金，参数非法，错误码=" + result);
		} else if ((firmID == null) || (firmID.trim().equals(""))) {
			Vector cList = DAO.getCorrespondList("where bankid='" + bankID + "' and account='" + bankAccount + "'", conn);
			if (cList.size() > 0) {
				firmID = ((CorrespondValue) cList.get(0)).firmID;
			} else {
				result = -20001L;
				log("市场发起出金，非法银行帐号，错误码=" + result);
			}
		} else if ((bankAccount == null) || (bankAccount.trim().equals(""))) {
			Vector cList = DAO.getCorrespondList("where bankid='" + bankID + "' and firmid='" + firmID + "'", conn);
			if (cList.size() > 0) {
				bankAccount = ((CorrespondValue) cList.get(0)).account;
			} else {
				result = -20002L;
				log("市场发起出金，非法交易商代码，错误码=" + result);
			}
		} else if (DAO.getCorrespond(bankID, firmID, bankAccount, conn) == null) {
			result = -20003L;
			log("市场发起出金，交易商代码和帐号对应关系错误，错误码=" + result);
		}
		if (result == 0L) {
			Vector cList = DAO.getCorrespondList("where bankid='" + bankID + "' and firmid='" + firmID + "' and status=0 ", conn);
			if ((cList == null) || (cList.size() != 1)) {
				result = -20003L;
			}
		}
		if ((result == 0L) && ((outRate == -1.0D) || (outRate == -2.0D))) {
			result = -20040L;
			log("市场发起出金，计算出金手续费错误，错误码=" + result);
		} else if (realFunds < Arith.add(money, outRate)) {
			System.out.println("realFunds-------------->" + realFunds);
			System.out.println("Arith.add(money, outRate)-------------->" + Arith.add(money, outRate));
			result = -20039L;
			log("市场发起出金，资金余额不足，错误码=" + result);
		}

		return result;
	}

	public HashMap<Integer, Long> handleOUtMoney(long result, Connection conn, long checkTrans, double outRate, String bankID, double money,
			String firmID, String funID, int express, int type) throws SQLException {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("检查交易商是否符合出金条件,符合条件进行出金处理,记录流水");
		System.out.println("result[" + result + "]checkTrans[" + checkTrans + "]outRate[" + outRate + "]bankID[" + bankID + "]money[" + money
				+ "]firmID[" + firmID + "]funID[" + funID + "]express[" + express + "]type[" + type + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}");
		HashMap map = new HashMap();
		long capitalID = 0L;
		long rateID = 0L;

		result = DAO.getActionID(conn);
		try {
			conn.setAutoCommit(false);
			if ((funID != null) && (funID.trim().length() > 0)) {
				Vector cv = DAO.getCapitalInfoList(" where bankID='" + bankID + "' and trunc(createtime)=trunc(sysdate) and funID='" + funID + "'",
						conn);
				if ((cv != null) && (cv.size() > 0)) {
					result = -20042L;
					log("银行出金，银行流水号[" + funID + "]已经存在");
				}
			}
			if (result >= 0L) {
				CapitalValue cVal = new CapitalValue();
				cVal.actionID = result;
				cVal.bankID = bankID;
				cVal.creditID = ((String) dataProcess.getBANKSUB().get(bankID));
				cVal.debitID = firmID;
				cVal.firmID = firmID;
				cVal.money = money;
				cVal.oprcode = dataProcess.getOUTSUMMARY() + "";
				cVal.status = 3;
				cVal.type = 1;
				cVal.express = express;
				cVal.funID = (funID == null ? "" : funID);
				if (type == 0) {
					cVal.note = "market_out";
				} else if (type == 1) {
					cVal.note = "bank_out";
				}

				capitalID = DAO.addCapitalInfo(cVal, conn);

				cVal.creditID = "Market";
				cVal.debitID = firmID;
				cVal.money = outRate;
				cVal.oprcode = dataProcess.getFEESUMMARY() + "";
				cVal.type = 2;

				rateID = DAO.addCapitalInfo(cVal, conn);
			}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			conn.rollback();
			result = -20038L;
			log("市场发起出金SQLException,数据回滚" + e);
			throw e;
		} finally {
			conn.setAutoCommit(true);
		}
		map.put(Integer.valueOf(1), Long.valueOf(result));
		map.put(Integer.valueOf(2), Long.valueOf(capitalID));
		map.put(Integer.valueOf(3), Long.valueOf(rateID));
		return map;
	}

	public ReturnValue isTradeDate(java.util.Date tradeDate) {
		ReturnValue result = new ReturnValue();

		Vector<SystemMessage> vecMsg = null;
		try {
			vecMsg = DAO.getSystemMessages(" ");
		} catch (SQLException e) {
			Tool.log("查询交易系统信息异常：" + Tool.getExceptionTrace(e));
		}
		if ((vecMsg == null) || (vecMsg.size() <= 0)) {
			Tool.log("查询交易系统列表失败");
			result.result = -1L;
			result.remark = "查询交易系统信息失败";
			return result;
		}

		TradeProcessorRMI tradeProc = null;
		RequestMsg req = new RequestMsg();
		req.setBankID("");
		req.setMethodName("isTradeDate");
		req.setParams(new Object[] { tradeDate });
		for (SystemMessage sm : vecMsg) {
			tradeProc = getTradeCaptialProcessor(sm.systemID);
			try {
				result = tradeProc.doWork(req);
			} catch (RemoteException e) {
				Tool.log("调用交易系统方法异常：" + Tool.getExceptionTrace(e));
				result.result = -1L;
				result.remark = "系统异常";
			}

			if (result.result == 0L) {
				result.remark = ("交易系统[" + sm.systemID + "]是交易日，有系统是交易日默认是交易日");
				return result;
			}
		}
		return result;
	}

	public long chenckCorrespondValue(CorrespondValue cv) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("银行开户销户变更帐户检验参数方法");
		System.out.println("cv[" + cv.toString() + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}");
		long checkResult = 0L;
		String bankid = cv.bankID;
		String firmid = cv.firmID;
		String account = cv.account;
		if ((bankid == null) || (firmid == null) || (account == null)) {
			if (bankid == null) {
				checkResult = -40003L;
			}
			if (firmid == null) {
				checkResult = -40002L;
			}
			if (account == null) {
				checkResult = -40001L;
				if (bankid.equals("20")) {
					checkResult = 0L;
					System.out.println("checkResult =0");
				}
			}
		} else {
			try {
				List cvresult = DAO.getCorrespondList(" where bankId='" + bankid + "' and firmId='" + firmid + "' and (account like '%"
						+ account.trim() + "%' or account='" + Tool.getConfig("DefaultAccount") + "')");
				if ((bankid.equals("20")) || (bankid.equals("005")) || (bankid.equals("66"))) {
					cvresult = DAO.getCorrespondList(" where bankId='" + bankid + "' and firmId='" + firmid + "' ");
				}
				if ((cvresult == null) || (cvresult.size() <= 0)) {
					checkResult = -40001L;
					if (bankid.equals("20")) {
						checkResult = 0L;
						System.out.println("checkResult2 =0");
					}
				}
			} catch (SQLException e) {
				checkResult = -40006L;
				e.printStackTrace();
				log("交易商代码与银行帐号对应SQLException," + e);
			} catch (Exception e) {
				checkResult = -40007L;
				e.printStackTrace();
				log("交易商代码与银行帐号对应SQLException," + e);
			}
		}
		return checkResult;
	}

	public long chenckCorrespondValue(CorrespondValue cv, Connection conn) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("银行开户销户变更帐户检验参数方法");
		System.out.println("cv[" + cv.toString() + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}");
		long checkResult = 0L;
		String bankid = cv.bankID;
		String firmid = cv.firmID;
		String account = cv.account;
		if ((bankid == null) || (firmid == null) || (account == null)) {
			if (bankid == null) {
				checkResult = -40003L;
			}
			if (firmid == null) {
				checkResult = -40002L;
			}
			if (account == null) {
				checkResult = -40001L;
				if (bankid.equals("20")) {
					checkResult = 0L;
					System.out.println("checkResult =0");
				}
			}
		} else {
			try {
				List cvresult = DAO.getCorrespondList(" where bankId='" + bankid + "' and firmId='" + firmid + "' and (account like '%"
						+ account.trim() + "%' or account='" + Tool.getConfig("DefaultAccount") + "')", conn);
				if ((bankid.equals("20")) || (bankid.equals("005")) || (bankid.equals("66"))) {
					cvresult = DAO.getCorrespondList(" where bankId='" + bankid + "' and firmId='" + firmid + "' ", conn);
				}
				if ((cvresult == null) || (cvresult.size() <= 0)) {
					checkResult = -40001L;
					if (bankid.equals("20")) {
						checkResult = 0L;
						System.out.println("checkResult2 =0");
					}
				}
			} catch (SQLException e) {
				checkResult = -40006L;
				e.printStackTrace();
				log("交易商代码与银行帐号对应SQLException," + e);
			} catch (Exception e) {
				checkResult = -40007L;
				e.printStackTrace();
				log("交易商代码与银行帐号对应SQLException," + e);
			}
		}
		return checkResult;
	}

	public ReturnValue openAccount(CorrespondValue cv) {
		System.out.println("cv[" + cv.toString() + "]");

		return openAccountBank(cv);
	}

	public ReturnValue openAccountMarket(CorrespondValue cv) {
		System.out.println(">>>>市场开户方法>>>>时间：" + Tool.fmtTime(new java.util.Date()) + ">>>>\ncv[" + cv.toString() + "]");
		long checkResult = chenckCorrespondValue(cv);
		ReturnValue rv = new ReturnValue();
		CorrespondValue cv2 = null;
		if (checkResult == 0L) {
			rv.actionId = getMktActionID();
			try {
				cv.isOpen = 1;
				List cvresult = null;
				if (cv.bankID.equals("005"))
					cvresult = DAO.getCorrespondList(" where bankId='" + cv.bankID.trim() + "' and firmId='" + cv.firmID.trim() + "' ");
				else {
					cvresult = DAO.getCorrespondList(" where bankId='" + cv.bankID.trim() + "' and firmId='" + cv.firmID.trim()
							+ "' and account like '%" + cv.account.trim() + "%'");
				}
				if ((cvresult == null) || (cvresult.size() <= 0)) {
					rv.result = -40001L;
				} else {
					cv2 = (CorrespondValue) cvresult.get(0);
					cv2.isOpen = 1;
					cv2.status = cv.status;
					cv2.falg = cv.falg;
					cv2.signInfo = cv.signInfo;
					cv2.actionID = cv.actionID;
					BankAdapterRMI bankadapter = getAdapter(cv2.bankID);
					if (bankadapter == null) {
						rv.result = -920000L;
					} else {
						cv2.bankCardPassword = cv.bankCardPassword;

						rv = bankadapter.rgstAccountQuery(cv2);
						if (rv.result == 0L) {
							if ((cv.bankID.equals("005")) || (cv.bankID.equals("17")) || ("21".equals(cv.bankID))) {
								cv2.bankCardPassword = null;
								cv2.account1 = rv.remark;
							}
							if (cv.bankID.equals("050")) {
								String sCustomer = rv.remark;
								String[] aa = sCustomer.split("\\|");
								cv2.account1 = aa[0];
								cv2.accountName = aa[1];
							}

							if (cv.bankID.equals("20")) {
								cv2.amount = getMarketBalance(cv2.firmID).avilableBalance;
							}
							rv.result = DAO.modCorrespond(cv2);
						} else if (rv.result == 5L) {
							cv2.isOpen = 2;
							rv.result = DAO.modCorrespond(cv2);
						}
					}
				}
			} catch (SQLException e) {
				rv.result = -40006L;
				e.printStackTrace();
				log("银行开户 交易商代码与银行帐号对应SQLException," + e);
			} catch (Exception e) {
				rv.result = -40007L;
				e.printStackTrace();
				log("银行开户 交易商代码与银行帐号对应SQLException," + e);
			}
		} else {
			rv.result = ((int) checkResult);
			rv.remark = "交易商代码与银行、帐号对应有误!";
		}
		return rv;
	}

	public ReturnValue synchroAccountMarket(CorrespondValue cv) {
		System.out.println(">>>>市场同步账号方法>>>>时间：" + Tool.fmtTime(new java.util.Date()) + ">>>>\ncv[" + cv.toString() + "]");
		long checkResult = chenckCorrespondValue(cv);
		ReturnValue rv = new ReturnValue();
		CorrespondValue cv2 = null;
		if (checkResult == 0L) {
			rv.actionId = getMktActionID();
			try {
				cv.isOpen = 1;
				List cvresult = DAO.getCorrespondList(" where bankId='" + cv.bankID.trim() + "' and firmId='" + cv.firmID.trim()
						+ "' and account like '%" + cv.account.trim() + "%'");
				if ((cvresult == null) || (cvresult.size() <= 0)) {
					rv.result = -40001L;
				} else {
					cv2 = (CorrespondValue) cvresult.get(0);
					cv2.isOpen = 2;
					cv2.status = cv.status;
					BankAdapterRMI bankadapter = getAdapter(cv2.bankID);
					if (bankadapter == null) {
						rv.result = -920000L;
					} else {
						rv = bankadapter.synchroAccount(cv2);
						if (rv.result == 0L)
							rv.result = DAO.modCorrespond(cv2);
					}
				}
			} catch (SQLException e) {
				rv.result = -40006L;
				e.printStackTrace();
				log("市场同步账号 交易商代码与银行帐号对应SQLException," + e);
			} catch (Exception e) {
				rv.result = -40007L;
				e.printStackTrace();
				log("市场同步账号 交易商代码与银行帐号对应SQLException," + e);
			}
		} else {
			rv.result = ((int) checkResult);
			rv.remark = "交易商代码与银行、帐号对应有误!";
		}
		return rv;
	}

	public ReturnValue destroyAccount(CorrespondValue cv) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("银行销户方法");
		System.out.println("cv[" + cv.toString() + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}");
		long checkResult = 0L;
		ReturnValue rv = new ReturnValue();
		if (checkResult == 0L) {
			rv.actionId = getMktActionID();
			try {
				cv.isOpen = 0;
				cv.status = 1;
				rv.result = DAO.modCorrespond(cv);
			} catch (SQLException e) {
				rv.result = -40016L;
				e.printStackTrace();
				log("银行销户 交易商代码与银行帐号对应SQLException," + e);
			} catch (Exception e) {
				rv.result = -40017L;
				e.printStackTrace();
				log("银行销户 交易商代码与银行帐号对应SQLException," + e);
			}
		} else {
			rv.result = -40011L;
			rv.remark = "交易商代码与银行、帐号对应有误!";
		}
		return rv;
	}

	public ReturnValue modAccount(CorrespondValue cv1, CorrespondValue cv2) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("变更帐户方法");
		System.out.println("cv1[" + cv1.toString() + "]\ncv2[" + cv2.toString() + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}}");
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
				e.printStackTrace();
				log("修改交易商代码与银行帐号对应SQLException," + e);
			} catch (Exception e) {
				rv.result = -40007L;
				e.printStackTrace();
				log("修改交易商代码与银行帐号对应SQLException," + e);
			}
		} else {
			rv.result = ((int) checkResult);
		}
		return rv;
	}

	public int bankFrozenFuns(String firmID, String bankID, String account, double money, Connection conn) throws SQLException {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("银行接口资金冻结");
		System.out.println("firmID[" + firmID + "]bankID[" + bankID + "]account[" + account + "]money[" + money + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}");
		int result = 0;
		if ((firmID == null) || (firmID.trim().length() <= 0) || (bankID == null) || (bankID.trim().length() <= 0)) {
			log("冻结(解冻)资金，信息不完整 firmID=" + firmID + " bankID=" + bankID);
			throw new SQLException("冻结(解冻)资金，信息不完整 firmID=" + firmID + " bankID=" + bankID);
		}
		String filter = " where 1=1 and firmID='" + firmID.trim() + "' and bankID='" + bankID.trim() + "' ";
		if (account != null) {
			filter = filter + " and account='" + account.trim() + "'";
		}
		String filter2 = filter + " for update";
		try {
			Vector vector = DAO.getCorrespondList(filter2, conn);
			if ((vector != null) && (vector.size() > 0)) {
				CorrespondValue cv = (CorrespondValue) vector.get(0);
				if (cv.frozenFuns + money < 0.0D) {
					throw new SQLException("交易商冻结资金不足以释放当前资金：冻结资金[" + cv.frozenFuns + "]当前资金[" + money + "]");
				}
				result = DAO.modBankFrozenFuns(filter, money, conn);
			} else {
				throw new SQLException("没有找到本条记录+" + filter);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	public int bankAccountIsOpen(String firmid, String bankid, String account) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("查询银行帐号的签约状态");
		System.out.println("firmid[" + firmid + "]bankid[" + bankid + "]account[" + account + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}");
		String filter = " where 1=1 ";
		if (firmid != null) {
			filter = filter + " and firmid='" + firmid + "'";
			if (account != null) {
				filter = filter + " and account='" + account + "'";
			}
		} else {
			filter = filter + " and account='" + account + "'";
		}
		if (bankid != null) {
			filter = filter + " and bankid='" + bankid + "'";
		}
		int value = DAO.bankAccountIsOpen(filter);
		System.out.println("DAO.bankAccountIsOpen(filter)=" + value);
		return value;
	}

	public FirmMessageVo getFirmMSG(String firmid) {
		return DAO.getFirmMSG(firmid);
	}

	public boolean getTraderStatus() {
		boolean status = false;
		try {
			System.out.println("getTraderStatus运行前" + status);
			status = DAO.getTraderStatus();
			System.out.println("getTraderStatus运行后" + status);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("getTraderStatus返回" + status);
		return status;
	}

	public long tradeDate(String bankID) {
		log("判断是否可以转账" + Tool.fmtDate(new java.util.Date()));
		long result = 0L;
		try {
			int n = DAO.useBank(bankID);
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
			e.printStackTrace();
			result = -920006L;
		}
		return result;
	}

	public ReturnValue isPassword(String firmID, String password) {
		log("开始调用isPassword(),参数firmID=[" + firmID + "],password=[" + password + "]");
		ReturnValue rv = new ReturnValue();
		rv.result = -1L;
		String newpassword = Encryption.encryption(firmID, password, null);
		log("passwoed加密之后是[" + newpassword + "]");
		try {
			FirmValue fv = DAO.getFirm(firmID);
			log("从f_b_firmuser中查询出来的密码是[" + fv.password + "]");
			if (fv != null) {
				String oldpassword = fv.password;
				if ((oldpassword != null) && (oldpassword.trim().length() > 0)) {
					if (newpassword.equals(oldpassword)) {
						rv.result = 0L;
					} else {
						rv.remark = ("交易商[" + firmID + "]输入的密码错误");
						log("交易商[" + firmID + "]输入的密码错误");
					}
				} else {
					rv.result = 1L;
					rv.remark = ("交易商[" + firmID + "]尚未初始化密码");
					log("交易商[" + firmID + "]尚未初始化密码");
				}
			} else {
				rv.result = -2L;
				rv.remark = ("查询不到交易商[" + firmID + "]");
				log(Tool.fmtTime(new java.util.Date()) + "查询不到交易商[" + firmID + "]");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rv;
	}

	public ReturnValue isPassword(String firmID, String password, Connection conn) {
		log("开始调用isPassword(),参数firmID=[" + firmID + "],password=[" + password + "]");
		ReturnValue rv = new ReturnValue();
		rv.result = -1L;
		String newpassword = Encryption.encryption(firmID, password, null);
		log("passwoed加密之后是[" + newpassword + "]");
		try {
			FirmValue fv = DAO.getFirm(firmID, conn);
			log("从f_b_firmuser中查询出来的密码是[" + fv.password + "]");
			if (fv != null) {
				String oldpassword = fv.password;
				if ((oldpassword != null) && (oldpassword.trim().length() > 0)) {
					if (newpassword.equals(oldpassword)) {
						rv.result = 0L;
					} else {
						rv.remark = ("交易商[" + firmID + "]输入的密码错误");
						log("交易商[" + firmID + "]输入的密码错误");
					}
				} else {
					rv.result = 1L;
					rv.remark = ("交易商[" + firmID + "]尚未初始化密码");
					log("交易商[" + firmID + "]尚未初始化密码");
				}
			} else {
				rv.result = -2L;
				rv.remark = ("查询不到交易商[" + firmID + "]");
				log(Tool.fmtTime(new java.util.Date()) + "查询不到交易商[" + firmID + "]");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rv;
	}

	// TODO
	public long isLogPassword(String firmID, String password) {
		long result = -1L;
		try {
			FirmValue fv = DAO.getFirm(firmID);
			if (fv != null) {
				// LogonManager manager = LogonManager.createInstance("5", DAO.getDataSource());
				// int ispassword = manager.checkUser(firmID, password);
				// if (ispassword == 0)
				result = 0L;
				// else
				// log("交易商[" + firmID + "]输入的密码错误");
			} else {
				result = -2L;
				log(Tool.fmtTime(new java.util.Date()) + "查询不到交易商[" + firmID + "]");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public ReturnValue modPwd(String firmID, String password, String chpassword) {
		ReturnValue rv = new ReturnValue();
		rv.result = -1L;
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			rv = modPwd(firmID, password, chpassword, conn);
		} catch (Exception e) {
			log(Tool.fmtTime(new java.util.Date()) + Tool.getExceptionTrace(e));
			e.printStackTrace();
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return rv;
	}

	public ReturnValue modPwd(String firmID, String password, String chpassword, Connection conn) {
		ReturnValue rv = new ReturnValue();
		rv.result = -1L;
		String newpassword = Encryption.encryption(firmID, password, null);
		try {
			FirmValue fv = DAO.getFirm(firmID, conn);
			if (fv != null) {
				String oldpassword = fv.password;
				if ((oldpassword == null) || (oldpassword.trim().length() <= 0) || (newpassword.equals(oldpassword))) {
					if ((chpassword == null) || ("".equals(chpassword)))
						fv.password = "";
					else {
						fv.password = Encryption.encryption(firmID, chpassword, null);
					}

					int n = DAO.modFirm(fv, conn);
					if (n >= 0) {
						rv.result = 0L;
					} else {
						rv.remark = "修改交易商密码，返回数值小于0";
						log(Tool.fmtTime(new java.util.Date()) + "修改交易商密码，返回数值小于0");
					}
				} else {
					rv.result = -1L;
					rv.remark = ("交易商[" + fv.firmID + "]修改密码，原密码错误");
					log(Tool.fmtTime(new java.util.Date()) + "交易商[" + fv.firmID + "]修改密码，原密码错误。oldpassword[" + oldpassword + "]nowpassword["
							+ newpassword + "]");
				}
			} else {
				rv.result = -2L;
				rv.remark = ("未查询到相应交易商[" + firmID + "]");
				log(Tool.fmtTime(new java.util.Date()) + "未查询到相应交易商[" + firmID + "]");
			}
		} catch (SQLException e) {
			log(Tool.fmtTime(new java.util.Date()) + "修改密码数据库异常，输入的密码：" + newpassword);
			e.printStackTrace();
		} catch (Exception e) {
			log(Tool.fmtTime(new java.util.Date()) + "修改密码系统异常，输入的密码：" + newpassword);
			e.printStackTrace();
		}
		return rv;
	}

	public long inMoneyNoAdapter(String maketbankID, String firmID, String account, String bankName, double money, String remark) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("入金(手工出入金)");
		System.out
				.println("maketbankID[" + maketbankID + "]firmID[" + firmID + "]account[" + bankName + "]money[" + money + "]remark[" + remark + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}");

		long result = 0L;

		result = tradeDate(maketbankID);
		if (result != 0L) {
			return result;
		}

		Connection conn = null;

		Timestamp curTime = new Timestamp(System.currentTimeMillis());
		try {
			conn = DAO.getConnection();

			double inRate = getTransRate(maketbankID, firmID, money, 0, 0, account, conn);
			if (result == 0L) {
				long checkTrans = checkTrans(maketbankID, firmID, money, curTime, 0, conn);
				if (checkTrans == 0L) {
					result = DAO.getActionID(conn);
					try {
						conn.setAutoCommit(false);

						CapitalValue cVal = new CapitalValue();
						cVal.actionID = result;
						cVal.bankID = maketbankID;
						cVal.funID = "";
						cVal.creditID = firmID;
						cVal.debitID = ((String) dataProcess.getBANKSUB().get(maketbankID));
						cVal.firmID = firmID;
						cVal.money = money;
						cVal.note = remark;
						cVal.oprcode = dataProcess.getINSUMMARY() + "";
						cVal.status = 13;
						cVal.type = 0;
						cVal.bankName = bankName;
						cVal.account = account;
						DAO.addCapitalInfo(cVal, conn);
						cVal.creditID = "Market";
						cVal.debitID = firmID;
						cVal.money = inRate;
						cVal.oprcode = dataProcess.getFEESUMMARY() + "";
						cVal.type = 2;

						DAO.addCapitalInfo(cVal, conn);
						conn.commit();
					} catch (SQLException e) {
						e.printStackTrace();
						conn.rollback();

						result = -10026L;
						log("市场端发起入金写资金流水SQLException,数据回滚:" + e);
						throw e;
					} finally {
						conn.setAutoCommit(true);
					}
				} else {
					result = checkTrans;
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();

			result = -10014L;
			log("市场端发起入金SQLException:" + result);
		} catch (Exception e) {
			e.printStackTrace();

			result = -10015L;
			log("市场端发起入金Exception:" + result);
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public long outMoneyNoAdapter(String maketbankID, double money, String firmID, String bankName, String account, String operator, int express,
			int type) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("出金(手工出入金)");
		System.out.println("maketbankID[" + maketbankID + "]money[" + money + "]firmID[" + firmID + "]bankName[" + bankName + "]account[" + account
				+ "]operator[" + operator + "]express[" + express + "]type[" + type + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}");

		long result = 0L;
		result = tradeDate(maketbankID);
		if (result != 0L) {
			return result;
		}

		long capitalID = 0L;

		long rateID = 0L;

		Connection conn = null;

		Timestamp curTime = new Timestamp(System.currentTimeMillis());
		try {
			conn = DAO.getConnection();

			result = checkArgs(conn, maketbankID, money, firmID, account, 1);
			if (result == 0L) {
				result = checkArgs(conn, 0.0D, 0.0D, maketbankID, money, firmID, account, express, type);
			}

			if (result == 0L) {
				long checkTrans = checkTrans(maketbankID, firmID, money, curTime, 1, conn);
				if (checkTrans == 0L) {
					HashMap map = handleOUtMoney(result, conn, checkTrans, maketbankID, money, firmID, express, type, bankName, account);
					result = ((Long) map.get(Integer.valueOf(1))).longValue();
					capitalID = ((Long) map.get(Integer.valueOf(2))).longValue();
					rateID = ((Long) map.get(Integer.valueOf(3))).longValue();
				} else {
					result = checkTrans;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();

			result = -20004L;
			log("市场发起出金SQLException，错误码=" + result + "  " + e);
		} catch (Exception e) {
			e.printStackTrace();

			result = -20005L;
			log("市场发起出金Exception，错误码=" + result + "  " + e);
			try {
				DAO.modCapitalInfoStatus(capitalID, "", 1, curTime, conn);
				DAO.modCapitalInfoStatus(rateID, "", 1, curTime, conn);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public FirmBalanceValue getBalanceNoAdapter(String firmid) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("查询交易商市场资金");
		System.out.println("firmID[" + firmid + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}");
		String filter = " where 1=1 ";
		if (firmid != null) {
			filter = filter + "  and firmid='" + firmid + "'  ";
		}
		FirmBalanceValue firmBalanceValue = DAO.availableBalance(filter);
		if ("true".equalsIgnoreCase(getConfig("fuYing")))
			try {
				Vector<FirmBalanceValue> floatingloss = DAO.getFlote(new String[] { firmid });
				if ((floatingloss != null) && (floatingloss.size() > 0))
					for (FirmBalanceValue fb : floatingloss)
						if ((fb != null) && (firmid.equals(fb.firmId))) {
							firmBalanceValue.floatingloss = fb.floatingloss;
							if (fb.floatingloss <= 0.0D)
								break;
							firmBalanceValue.avilableBalance -= fb.floatingloss;

							break;
						}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		try {
			Firm firm = DAO.getMFirmByFirmId(firmid);
			firmBalanceValue.bankAccount = firm.bankAccount;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return firmBalanceValue;
	}

	public long moneyAuditNoAdapter(long actionID, boolean funFlag) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("出入金审核(手工出入金)");
		System.out.println("actionID[" + actionID + "]funFlag[" + funFlag + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}");
		String auditInfo = "审核业务流水号：" + actionID + ";";

		Connection conn = null;

		Timestamp curTime = new Timestamp(System.currentTimeMillis());

		CapitalValue capital = null;

		CapitalValue rate = null;

		ReturnValue rVal = new ReturnValue();

		long result = 0L;

		if ((getConfig("AuditBeginTime") != null) && (getConfig("AuditBeginTime").length() > 0)) {
			Time AuditBeginTime = Tool.convertTime(getConfig("AuditBeginTime"));
			Time curSqlTime = Tool.convertTime(Tool.fmtOnlyTime(curTime));
			if ((AuditBeginTime != null) && (AuditBeginTime.after(curSqlTime))) {
				result = -20018L;
				log("审核时间未到！");
				return result;
			}
		}

		if ((getConfig("AuditEndTime") != null) && (getConfig("AuditEndTime").length() > 0)) {
			Time AuditEndTime = Tool.convertTime(getConfig("AuditEndTime"));
			Time curSqlTime = Tool.convertTime(Tool.fmtOnlyTime(curTime));
			if ((AuditEndTime != null) && (curSqlTime.after(AuditEndTime))) {
				result = -20017L;
				log("审核时间已过！");
				return result;
			}
		}
		try {
			conn = DAO.getConnection();

			Vector<CapitalValue> list = DAO.getCapitalInfoList("where actionid=" + actionID, conn);
			if (list.size() > 0) {
				for (CapitalValue cv : list) {
					if ((cv.type == 1) || (cv.type == 0))
						capital = cv;
					else if (cv.type == 2) {
						rate = cv;
					}
				}
			}
			if (capital != null) {
				if (capital.status != 13) {
					result = -930000L;
					log(auditInfo + "当笔资金流水已经受理完成，错误码=" + result);
					long l1 = result;
					return l1;
				}
				if (funFlag) {
					try {
						conn.setAutoCommit(false);

						result = 0L;
						if (capital.type == 1) {
							dataProcess.updateFundsFull(capital.firmID, dataProcess.getOUTSUMMARY() + "",
									(String) dataProcess.getBANKSUB().get(capital.bankID), capital.money, actionID, conn);
							dataProcess.updateFundsFull(capital.firmID, dataProcess.getFEESUMMARY() + "", null, rate.money, actionID, conn);
							dataProcess.updateFrozenFunds(capital.firmID, -1.0D * Arith.add(capital.money, rate.money), conn);

							bankFrozenFuns(capital.firmID, capital.bankID, null, -1.0D * Arith.add(capital.money, rate.money), conn);
							DAO.modCapitalInfoStatus(capital.iD, rVal.funID, 0, curTime, conn);
							DAO.modCapitalInfoStatus(rate.iD, rVal.funID, 0, curTime, conn);
							DAO.modCapitalInfoNote(capital.iD, "[" + capital.note + "]审核通过，出金成功", conn);
							DAO.modCapitalInfoNote(rate.iD, "[" + rate.note + "]审核通过，出金成功", conn);
						} else if (capital.type == 0) {
							DAO.modCapitalInfoStatus(capital.iD, rVal.funID, 0, curTime, conn);
							DAO.modCapitalInfoStatus(rate.iD, rVal.funID, 0, curTime, conn);
							DAO.modCapitalInfoNote(capital.iD, "[" + capital.note + "]审核通过，入金成功", conn);
							DAO.modCapitalInfoNote(rate.iD, "[" + rate.note + "]审核通过，入金成功", conn);

							if (result >= 0L) {
								if (Tool.getConfig("inMoneyNoAdapterAddMoney").equals("true")) {
									dataProcess.updateFundsFull(capital.firmID, dataProcess.getINSUMMARY() + "",
											(String) dataProcess.getBANKSUB().get(capital.bankID), capital.money, result, conn);
									dataProcess.updateFundsFull(rate.firmID, dataProcess.getFEESUMMARY() + "", null, rate.money, result, conn);
									log("假银行入金，添加资金凭证成功");
								} else {
									log("假银行入金，流水写入成功，inMoneyNoAdapterAddMoney为false，不添加资金凭证");
								}
							} else
								log("假银行入金，添加写入流水失败");

						}

						conn.commit();
					} catch (SQLException e) {
						e.printStackTrace();
						conn.rollback();

						result = -20013L;
						log(auditInfo + "审核出金SQLException，数据回滚，需要手工处理出金和手续费，错误码=" + result + "  " + e);
					} finally {
						conn.setAutoCommit(true);
					}

					if (result == -20013L) {
						DAO.modCapitalInfoStatus(capital.iD, rVal.funID, 1, curTime, conn);
						DAO.modCapitalInfoStatus(rate.iD, rVal.funID, 1, curTime, conn);
						DAO.modCapitalInfoNote(capital.iD, "[" + capital.note + "]审核通过，系统异常，需要手工处理出入金", conn);
						DAO.modCapitalInfoNote(rate.iD, "[" + rate.note + "]审核通过，系统异常，需要手工处理出入金", conn);
					}
				} else {
					try {
						conn.setAutoCommit(false);
						if (capital.type == 1) {
							dataProcess.updateFrozenFunds(capital.firmID, -1.0D * Arith.add(capital.money, rate.money), conn);

							bankFrozenFuns(capital.firmID, capital.bankID, null, -1.0D * Arith.add(capital.money, rate.money), conn);
							DAO.modCapitalInfoStatus(capital.iD, rVal.funID, 1, curTime, conn);
							DAO.modCapitalInfoStatus(rate.iD, rVal.funID, 1, curTime, conn);
							DAO.modCapitalInfoNote(capital.iD, "[" + capital.note + "]审核拒绝成功", conn);
							DAO.modCapitalInfoNote(rate.iD, "[" + rate.note + "]审核拒绝成功", conn);
						} else if (capital.type == 0) {
							DAO.modCapitalInfoStatus(capital.iD, rVal.funID, 1, curTime, conn);
							DAO.modCapitalInfoStatus(rate.iD, rVal.funID, 1, curTime, conn);
							DAO.modCapitalInfoNote(capital.iD, "[" + capital.note + "]审核拒绝成功", conn);
							DAO.modCapitalInfoNote(rate.iD, "[" + rate.note + "]审核拒绝成功", conn);
						}
						conn.commit();
					} catch (SQLException e) {
						e.printStackTrace();
						conn.rollback();

						result = -20014L;
						log(auditInfo + "拒绝出金SQLException，数据回滚，需要手工处理出金和手续费，错误码=" + result + "  " + e);
					} finally {
						conn.setAutoCommit(true);
					}

					if (result == -20014L) {
						DAO.modCapitalInfoStatus(capital.iD, rVal.funID, 1, curTime, conn);
						DAO.modCapitalInfoStatus(rate.iD, rVal.funID, 1, curTime, conn);
						DAO.modCapitalInfoNote(capital.iD, "[" + capital.note + "]审核拒绝出金,资金解冻出错，需要手工处理出金和手续费", conn);
						DAO.modCapitalInfoNote(rate.iD, "[" + rate.note + "]审核拒绝出金,资金解冻出错，需要手工处理出金和手续费", conn);
					}
				}
			}
		} catch (SQLException e) {
			result = -20004L;
			log(auditInfo + "审核时数据库异常    " + e);
		} catch (ClassNotFoundException e) {
			result = -40007L;
			log(auditInfo + "数据库连接异常    " + e);
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public ReturnValue modAccountNoAdapter(CorrespondValue correspondValue) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("修改交易商信息(手工出入金)");
		System.out.println(correspondValue.toString());
		System.out.println("}}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}");
		ReturnValue result = new ReturnValue();
		if ((correspondValue.bankID == null) || (correspondValue.bankID.length() == 0) || (correspondValue.firmID == null)
				|| (correspondValue.firmID.length() == 0) || (correspondValue.account == null) || (correspondValue.account.length() == 0)) {
			result.result = -40011L;
			result.remark = "信息不完整";
			return result;
		}
		try {
			DAO.modCorrespond(correspondValue);
		} catch (Exception e) {
			e.printStackTrace();
			result.remark = "修改交易商账号时出现异常";
		}
		return result;
	}

	public long delAccountNoAdapter(CorrespondValue correspondValue) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("银行帐号注销(手工出入金)");
		System.out.println(correspondValue.toString());
		System.out.println("}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}}");
		long result = 0L;
		Connection conn = null;
		if ((correspondValue.bankID == null) || (correspondValue.bankID.length() == 0) || (correspondValue.firmID == null)
				|| (correspondValue.firmID.length() == 0) || (correspondValue.account == null) || (correspondValue.account.length() == 0)) {
			return -40011L;
		}
		try {
			conn = DAO.getConnection();
			Vector vector = DAO.getCorrespondList(" where bankID='" + correspondValue.bankID + "' and firmID='" + correspondValue.firmID
					+ "' and account='" + correspondValue.account + "'", conn);
			if (vector.size() <= 0) {
				result = -40014L;
				log("银行帐号注销，帐号未注册，错误码=" + result);
			} else {
				DAO.delCorrespond(correspondValue, conn);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			result = -40016L;
			log("银行帐号注销SQLException，错误码=" + result + "  " + e);
		} catch (Exception e) {
			e.printStackTrace();
			result = -40017L;
			log("银行帐号注销Exception，错误码=" + result + "  " + e);
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	private long checkArgs(Connection conn, String maketbankID, double money, String firmID, String bankAccount, int type) throws SQLException {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("手工出入金接口合法性检查");
		System.out.println(
				"maketbankID[" + maketbankID + "]money[" + money + "]firmID[" + firmID + "]bankAccount[" + bankAccount + "]type[" + type + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}}");
		long result = 0L;

		double realFunds = dataProcess.getRealFunds(firmID, conn);

		double outRate = getTransRate(maketbankID, firmID, money, 1, 0, bankAccount, conn);
		System.out.println("realFunds=" + realFunds);
		if ((firmID == null) || (firmID.trim().equals(""))) {
			result = -20007L;
			log("市场发起出金，参数非法，交易商代码为空，错误码=" + result);
		}
		if ((type == 0) && ((maketbankID == null) || (maketbankID.trim().equals("")))) {
			result = -20007L;
			log("市场发起出金，参数非法，市场银行编号为空，错误码=" + result);
		}
		if ((type == 1) && (realFunds < money + outRate)) {
			result = -20039L;
			log("市场发起出金，资金余额不足，错误码=" + result);
		}
		return result;
	}

	private HashMap<Integer, Long> handleOUtMoney(long result, Connection conn, long checkTrans, String maketbankID, double money, String firmID,
			int express, int type, String bankName, String account) throws SQLException {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("手工出入金时添加出金信息");
		System.out.println("result[" + result + "]checkTrans[" + checkTrans + "]maketbankID[" + maketbankID + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}");
		HashMap map = new HashMap();
		long capitalID = 0L;
		long rateID = 0L;

		result = DAO.getActionID(conn);

		double outRate = getTransRate(maketbankID, firmID, money, 1, 0, account, conn);
		try {
			conn.setAutoCommit(false);
			CapitalValue cVal = new CapitalValue();
			cVal.actionID = result;
			cVal.bankID = maketbankID;
			cVal.creditID = ((String) dataProcess.getBANKSUB().get(maketbankID));
			cVal.debitID = firmID;
			cVal.firmID = firmID;
			cVal.money = money;
			cVal.oprcode = dataProcess.getOUTSUMMARY() + "";
			cVal.status = 13;
			cVal.type = 1;
			cVal.express = express;
			cVal.funID = "";
			cVal.note = "market_out";
			cVal.bankName = bankName;
			cVal.account = account;
			capitalID = DAO.addCapitalInfo(cVal, conn);

			cVal.creditID = "Market";
			cVal.debitID = firmID;
			cVal.money = outRate;
			cVal.oprcode = dataProcess.getFEESUMMARY() + "";
			cVal.type = 2;

			rateID = DAO.addCapitalInfo(cVal, conn);

			dataProcess.updateFrozenFunds(firmID, Arith.add(money, outRate), conn);

			bankFrozenFuns(firmID, maketbankID, account, Arith.add(money, outRate), conn);
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			conn.rollback();

			result = -20038L;
			log("市场发起出金SQLException,数据回滚" + e);
			throw e;
		} finally {
			conn.setAutoCommit(true);
		}
		map.put(Integer.valueOf(1), Long.valueOf(result));
		map.put(Integer.valueOf(2), Long.valueOf(capitalID));
		map.put(Integer.valueOf(3), Long.valueOf(rateID));
		return map;
	}

	public ReturnValue ifQuitFirm(String firmID, String bankID) {
		log("判断签约号[" + firmID + "]银行[" + bankID + "]是否可以解约");
		ReturnValue result = new ReturnValue();
		try {
			Vector vecCorr = DAO.getCorrespondList(" where contact='" + firmID + "' and bankid='" + bankID + "'");
			if ((vecCorr == null) || (vecCorr.size() <= 0)) {
				Tool.log("没有找到签约号[" + firmID + "]和银行[" + bankID + "]的签约关系");
				result.result = -1L;
				result.remark = "签约关系不存在";
				return result;
			}
			CorrespondValue corr = (CorrespondValue) vecCorr.get(0);
			if (corr.isOpen != 1) {
				Tool.log("签约号[" + firmID + "]和银行[" + bankID + "]的签约关系不是签约状态");
				result.result = -1L;
				result.remark = "已经不是签约状态";
				return result;
			}

			log("签约号[" + firmID + "]银行[" + bankID + "]可以解约");
			result.result = 0L;
			result.remark = "可以解约";
		} catch (SQLException e) {
			log("取得交易商[" + firmID + "]信息数据库异常:" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = ("取得交易商[" + firmID + "]信息数据库异常");
		} catch (Exception e) {
			log("取得交易商[" + firmID + "]信息系统异常:" + Tool.getExceptionTrace(e));
			result.result = -2L;
			result.remark = ("取得交易商[" + firmID + "]信息系统异常");
			e.printStackTrace();
		}
		return result;
	}

	public ReturnValue relevanceAccount(CorrespondValue cv) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{银行发起绑定，市场将银行子账号和子账号名称等添加进去{{{{{{{{{{{{{{{{{{{{{{{{");

		System.out.println(cv.toString());
		System.out.println("}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}");
		ReturnValue rv = new ReturnValue();
		if (cv == null) {
			rv.result = -40001L;
			rv.remark = "银行发起绑定，传入的信息包为空";
		} else if (cv.account1 == null) {
			rv.result = -40001L;
			rv.remark = ((String) ErrorCode.error.get(Long.valueOf(-40001L)));
		} else if (cv.firmID == null) {
			rv.result = -40002L;
			rv.remark = ((String) ErrorCode.error.get(Long.valueOf(-40002L)));
		}
		try {
			rv.result = DAO.openCorrespond(cv);
		} catch (Exception e) {
			rv.result = -40007L;
			rv.remark = "处理器修改交易商信息异常";
			e.printStackTrace();
		}

		return rv;
	}

	public ReturnValue saveFirmKXH(Vector<KXHfailChild> vector, String bankID) {
		log("添加会员开销户信息，时间:" + Tool.fmtTime(new java.util.Date()));
		ReturnValue rv = new ReturnValue();
		if (vector == null) {
			rv.result = -1L;
			rv.remark = "添加会员开销户信息，传入的参数为空";
		} else {
			for (KXHfailChild kxh : vector) {
				try {
					KXHfailChild vv = DAO.getFirmKXH(kxh.funID);
					if (vv == null)
						DAO.addFirmKXH(kxh, bankID);
				} catch (SQLException e) {
					e.printStackTrace();
					log("添加数据SQL异常：\n" + kxh.toString());
				} catch (Exception e) {
					e.printStackTrace();
					log("添加数据系统异常：\n" + kxh.toString());
				}
			}
		}
		log("返回结果：\n" + rv.toString());
		return rv;
	}

	public ReturnValue saveFirmKXH(java.util.Date date, String bankID) {
		log("添加会员开销户信息市场去取，时间:" + Tool.fmtTime(new java.util.Date()));
		log("参数：date[" + Tool.fmtDate(date) + "]bankID[" + bankID + "]");
		ReturnValue rv = new ReturnValue();
		BankAdapterRMI bankadapter = getAdapter(bankID);
		try {
			if (bankadapter != null) {
				Vector vector = bankadapter.getFirmODInfo(date);
				rv = saveFirmKXH(vector, bankID);
			} else {
				rv.result = -920000L;
				rv.remark = "处理器获取适配器失败";
			}
		} catch (Exception e) {
			e.printStackTrace();
			rv.result = -2L;
			rv.remark = "添加会员开销户信息，系统异常";
		}
		log("返回结果：\n" + rv.toString());
		return rv;
	}

	public ReturnValue sentMaketQS(java.util.Date date, String bankID) {
		log("发送市场清算文件  sentMaketQS  时间：" + Tool.fmtTime(new java.util.Date()));
		log("参数：date[" + Tool.fmtDate(date) + "]bankID[" + bankID + "]");
		ReturnValue rv = new ReturnValue();
		try {
			rv = isTradeDate(date);
			if (rv.result != 0L)
				return rv;
			if (!getTraderStatus()) {
				rv.result = -2L;
				rv.remark = "交易系统尚未结算，不能发送清算数据";
				return rv;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log(e.getMessage());
			rv.result = -1L;
			rv.remark = "获取当天是否为交易日失败";
			return rv;
		}
		Connection conn = null;
		if ((!Tool.fmtDate(new java.util.Date()).equalsIgnoreCase(Tool.fmtDate(date))) || (getTraderStatus())) {
			BatQs bq = new BatQs();
			try {
				bq.child = getQSChild(date, bankID);
				bq.rowCount = bq.child.size();
				BankAdapterRMI bankadapter = getAdapter(bankID);
				if (bankadapter != null) {
					ReturnValue rrv = bankadapter.sentMaketQS(bq);
					if (rrv.result >= 0L) {
						conn = DAO.getConnection();
						BankQSVO bq2 = new BankQSVO();
						bq2.bankID = bankID;
						bq2.tradeDate = date;
						bq2.note = "深发展银行清算";
						DAO.addBankQS(bq2, conn);
						rv.result = 0L;
						rv.remark = "发送给银行清算文件成功";
					} else {
						rv.result = rrv.result;
						rv.remark = rrv.remark;
					}
				} else {
					rv.result = -920000L;
					rv.remark = "发送市场清算文件，处理器获取适配器失败";
				}
			} catch (Exception e) {
				e.printStackTrace();
				rv.result = -2L;
				rv.remark = "发送市场清算文件异常";
			} finally {
				DAO.closeStatement(null, null, conn);
			}
			log("返回结果：\n" + rv.toString());
		} else {
			log("银行未结算，无法发送市场清算文件  sentMaketQS  时间：" + Tool.fmtTime(new java.util.Date()));
			rv.result = -2L;
			rv.remark = "银行未结算，无法发送清算文件";
		}
		return rv;
	}

	private Vector<BatQsChild> getQSChild(java.util.Date date, String bankID) {
		log("获取清算信息  getQSChild  时间：" + Tool.fmtTime(new java.util.Date()));
		try {
			ReturnValue rv = isTradeDate(date);
			if (rv.result != 0L) {
				log(rv.toString());
				return null;
			}
			if ((Tool.fmtDate(new java.util.Date()).equalsIgnoreCase(Tool.fmtDate(date))) && (!getTraderStatus()))
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			log(e.getMessage());
			return null;
		}
		Vector result = new Vector();
		Map today = null;
		Connection conn = null;
		boolean flag = true;
		java.util.Date usdate = date;
		try {
			conn = DAO.getConnection();
			today = DAO.getQSChild(bankID, null, null, usdate, conn);
			System.out.println(today.size());
			Set notFirmIDs = new HashSet();
			Set firmIDs = null;
			java.util.Date lastQSDate = DAO.getMaxBankQSList(bankID, usdate, conn);
			while (flag) {
				String[] toBankFirmIDs = (String[]) null;
				if ((firmIDs != null) && (firmIDs.size() > 0)) {
					toBankFirmIDs = new String[firmIDs.size()];
					Iterator it = firmIDs.iterator();
					int num = 0;
					while (it.hasNext()) {
						toBankFirmIDs[(num++)] = ((String) it.next());
					}
				}
				Vector toBank = DAO.getBankOpen(bankID, toBankFirmIDs, 1, usdate);
				usdate = DAO.getlastDate(usdate, conn);
				if (usdate == null) {
					flag = false;
				} else {
					if ((toBank != null) && (toBank.size() > 0)) {
						Set firms = new HashSet();
						for (int i = 0; i < toBank.size(); i++) {
							firms.add(((KXHfailChild) toBank.get(i)).firmID);
							notFirmIDs.add(((KXHfailChild) toBank.get(i)).firmID);
						}
						Map lastdate = DAO.getQSChild(bankID, firms, null, usdate, conn);
						Iterator it = lastdate.entrySet().iterator();
						while (it.hasNext()) {
							Map.Entry ent = (Map.Entry) it.next();
							BatQsChild yestodaych = (BatQsChild) ent.getValue();
							BatQsChild todaych = (BatQsChild) today.get(ent.getKey());
							if (todaych != null) {
								todaych.ProfitAmount += yestodaych.NewBalance;
							}
						}
					}
					if ((lastQSDate != null) && (usdate.after(lastQSDate))) {
						Map lastdate = DAO.getQSChild(bankID, firmIDs, notFirmIDs, usdate, conn);
						Iterator it = lastdate.entrySet().iterator();
						while (it.hasNext()) {
							Map.Entry ent = (Map.Entry) it.next();
							String key = (String) ent.getKey();
							BatQsChild todays = (BatQsChild) today.get(key);
							if (todays != null) {
								BatQsChild last = (BatQsChild) ent.getValue();
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
					} else {
						if (lastQSDate != null) {
							lastQSDate = DAO.getMaxBankQSList(bankID, lastQSDate, conn);
						}
						String[] str = (String[]) null;
						if ((firmIDs != null) && (firmIDs.size() > 0)) {
							str = new String[firmIDs.size()];
							Iterator it = firmIDs.iterator();
							int i = 0;
							while (it.hasNext()) {
								str[(i++)] = ((String) it.next());
							}
						}
						Vector<BatFailResultChild> bfr = DAO.getFirmBalanceError(str, bankID, usdate);
						Vector<BatCustDzFailChild> bcd = DAO.getBatCustDz(str, bankID, usdate);
						if (((bfr == null) || (bfr.size() == 0)) && ((bcd == null) || (bcd.size() == 0))) {
							flag = false;
						} else {
							firmIDs = new HashSet();
							if ((bfr != null) && (bfr.size() > 0)) {
								for (BatFailResultChild ch : bfr) {
									firmIDs.add(ch.ThirdCustId);
								}
							}
							if ((bcd != null) && (bcd.size() > 0)) {
								for (BatCustDzFailChild ch : bcd) {
									firmIDs.add(ch.ThirdCustId);
								}
							}
							Map lastdate = DAO.getQSChild(bankID, firmIDs, notFirmIDs, usdate, conn);
							Iterator it = lastdate.entrySet().iterator();
							while (it.hasNext()) {
								Map.Entry ent = (Map.Entry) it.next();
								String key = (String) ent.getKey();
								BatQsChild todays = (BatQsChild) today.get(key);
								if (todays != null) {
									BatQsChild last = (BatQsChild) ent.getValue();
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

			Iterator it = today.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry ent = (Map.Entry) it.next();
				result.add((BatQsChild) ent.getValue());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		System.out.println("返回信息条数：" + result.size());
		return result;
	}

	public ReturnValue getBatCustDz(java.util.Date date, String bankID) {
		log("获取银行发送来的对账不平文件  getBatCustDz  时间：" + Tool.fmtTime(new java.util.Date()));
		log("参数：date[" + Tool.fmtDate(date) + "]bankID[" + bankID + "]");
		ReturnValue rv = new ReturnValue();
		BankAdapterRMI bankadapter = getAdapter(bankID);
		if (bankadapter != null) {
			try {
				Vector bcd = bankadapter.getBatCustDz(date);
				if (bcd == null) {
					rv.result = -2L;
					rv.remark = "获取银行对账不平文件，文件不存在";
				} else if (bcd.size() == 0) {
					rv.result = 0L;
					rv.remark = "对账不平文件为空，对账成功";
				} else {
					rv = getBatCustDz(bcd, date, bankID);
				}
			} catch (Exception e) {
				e.printStackTrace();
				rv.result = -2L;
				rv.remark = "获取银行对账不平文件异常";
			}
		} else {
			rv.result = -920000L;
			rv.remark = "获取银行发送来的对账不平文件，处理器获取适配器失败";
		}
		log("返回结果：\n" + rv.toString());
		return rv;
	}

	public ReturnValue getBatCustDz(Vector<BatCustDzFailChild> bcd, java.util.Date date, String bankID) {
		log("获取银行发送来的对账不平文件  getBatCustDz  时间：" + Tool.fmtTime(new java.util.Date()));
		ReturnValue rv = new ReturnValue();
		if (bcd == null) {
			rv.result = -1L;
			rv.remark = "传入的对账不平信息为空";
		} else if (bcd.size() == 0) {
			rv.result = 0L;
			rv.remark = "传入的对账不平文件为空，银行对账成功";
		} else {
			try {
				DAO.delBatCustDz(date, bankID);
				for (BatCustDzFailChild cd : bcd) {
					DAO.addBatCustDz(cd, date, bankID);
				}
				rv.result = 0L;
				rv.remark = "传入的对账不平文件不为空，信息已经添加入数据库中";
			} catch (SQLException e) {
				rv.result = -1L;
				rv.remark = "修改对账不平信息，数据库异常";
				e.printStackTrace();
			} catch (Exception e) {
				rv.result = -1L;
				rv.remark = "修改对账不平信息，系统异常";
				e.printStackTrace();
			}
		}
		log("返回结果：\n" + rv.toString());
		return rv;
	}

	public ReturnValue getfirmBalanceFile(java.util.Date date, String bankID) {
		log("获取银行发送来的会员余额文件  getfirmBalanceFile  时间：" + Tool.fmtTime(new java.util.Date()));
		log("参数：date[" + Tool.fmtDate(date) + "]bankID[" + bankID + "]");
		ReturnValue rv = new ReturnValue();
		BankAdapterRMI bankadapter = getAdapter(bankID);
		if (bankadapter != null) {
			try {
				Vector fbf = bankadapter.getfirmBalanceFile(date);
				if (fbf == null) {
					rv.result = -2L;
					rv.remark = "获取银行发送来的会员余额文件，文件不存在";
				} else if (fbf.size() == 0) {
					rv.result = 0L;
					rv.remark = "银行发送来的会员余额文件为空";
				} else {
					rv = getfirmBalanceFile(fbf, date, bankID);
				}
			} catch (Exception e) {
				e.printStackTrace();
				rv.result = -2L;
				rv.remark = "获取银行发送来的会员余额文件异常";
			}
		} else {
			rv.result = -920000L;
			rv.remark = "获取银行发送来的会员余额文件，处理器获取适配器失败";
		}
		log("返回结果：\n" + rv.toString());
		return rv;
	}

	public ReturnValue getfirmBalanceFile(Vector<BatCustDzBChild> fbf, java.util.Date date, String bankID) {
		log("获取银行发送来的会员余额文件  getfirmBalanceFile  时间：" + Tool.fmtTime(new java.util.Date()));
		ReturnValue rv = new ReturnValue();
		if (fbf == null) {
			rv.result = -1L;
			rv.remark = "获取银行发送来的会员余额文件，传入的信息为空";
		} else if (fbf.size() == 0) {
			rv.result = -1L;
			rv.remark = "获取银行发送来的会员余额文件，余额文件为空";
		} else {
			try {
				for (BatCustDzBChild bc : fbf) {
					Vector vv = DAO.getFirmBalanceFile(bc.ThirdCustId, bankID, date);
					if ((vv != null) && (vv.size() > 0))
						DAO.modFirmBalanceFile(bc, date, bankID);
					else {
						DAO.addFirmBalanceFile(bc, date, bankID);
					}
				}
				rv.result = 0L;
				rv.remark = "获取银行发送来的会员余额文件，市场保存信息成功";
			} catch (SQLException e) {
				rv.result = -1L;
				rv.remark = "获取银行发送来的会员余额文件，数据库异常";
			} catch (Exception e) {
				rv.result = -1L;
				rv.remark = "获取银行发送来的会员余额文件，系统异常";
			}
		}
		log("返回结果：\n" + rv.toString());
		return rv;
	}

	public ReturnValue getFirmBalanceError(java.util.Date date, String bankID) {
		log("获取银行交易商对账失败文件  getFirmBalanceError  时间：" + Tool.fmtTime(new java.util.Date()));
		log("参数：date[" + Tool.fmtDate(date) + "]bankID[" + bankID + "]");
		ReturnValue rv = new ReturnValue();
		BankAdapterRMI bankadapter = getAdapter(bankID);
		if (bankadapter != null) {
			try {
				Vector fbe = bankadapter.getFirmBalanceError(date);

				if (fbe == null) {
					rv.result = -2L;
					rv.remark = "获取银行交易商对账失败文件，文件不存在";
				} else if (fbe.size() == 0) {
					rv.result = 0L;
					rv.remark = "获取银行交易商对账失败文件为空";
				} else {
					rv = getFirmBalanceError(fbe, date, bankID);
				}
			} catch (Exception e) {
				e.printStackTrace();
				rv.result = -2L;
				rv.remark = "获取银行交易商对账失败文件异常";
			}
		} else {
			rv.result = -920000L;
			rv.remark = "获取银行交易商对账失败文件，处理器获取适配器失败";
		}
		log("返回结果：\n" + rv.toString());
		return rv;
	}

	public ReturnValue getFirmBalanceError(Vector<BatFailResultChild> fbe, java.util.Date date, String bankID) {
		log("获取银行交易商对账失败文件  getFirmBalanceError  时间：" + Tool.fmtTime(new java.util.Date()));
		ReturnValue rv = new ReturnValue();
		if (fbe == null) {
			rv.result = -1L;
			rv.remark = "获取银行交易商对账失败文件，银行文件不存在";
		} else if (fbe.size() <= 0) {
			rv.result = 0L;
			rv.remark = "获取银行交易商对账失败文件，银行对账成功";
		} else {
			try {
				for (int i = 0; i < fbe.size(); i++) {
					BatFailResultChild bfr = (BatFailResultChild) fbe.get(i);
					if (i == 0) {
						DAO.delFirmBalanceError(bankID, bfr.TranDateTime);
					}
					DAO.addFirmBalanceError(bfr, bankID);
				}
				rv.result = 0L;
				rv.remark = "获取银行交易商对账失败文件，数据库保存成功";
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		log("返回结果：\n" + rv.toString());
		return rv;
	}

	public ReturnValue getBankFileStatus(java.util.Date tradeDate, int type, String bankID) {
		log("查询银行生成文件的状态  getBankFileStatus");
		log("参数：tradeDate[" + Tool.fmtDate(tradeDate) + "]type[" + type + "]bankID[" + bankID + "]");
		ReturnValue rv = new ReturnValue();
		BankAdapterRMI bankadapter = getAdapter(bankID);
		if (bankadapter == null) {
			rv.result = -920000L;
			rv.remark = "连接适配器时失败";
		} else {
			try {
				rv = bankadapter.getBankFileStatus(tradeDate, tradeDate, type);
			} catch (Exception e) {
				e.printStackTrace();
				rv.result = -920001L;
				rv.remark = "适配器查询银行文件生成状态时异常";
			}
		}
		return rv;
	}

	public ReturnValue modCapitalInfoStatus(long actionID, String funID) {
		ReturnValue rv = new ReturnValue();
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			DAO.modCapitalInfoStatus(actionID, funID, conn);
		} catch (SQLException e) {
			e.printStackTrace();
			rv.result = -1L;
			rv.remark = ("修改 " + actionID + " 银行流水号 " + funID + " 数据库异常");
		} catch (Exception e) {
			e.printStackTrace();
			rv.result = -1L;
			rv.remark = ("修改 " + actionID + " 银行流水号 " + funID + " 系统异常");
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return rv;
	}

	public ReturnValue sentFirmBalance(String bankID, java.util.Date date) {
		log("给银行发送手续费和资金变化量bankID[" + bankID + "]date[" + Tool.fmtDate(date) + "]");
		ReturnValue result = new ReturnValue();
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			result = isTradeDate(date);
			if (result.result != 0L) {
				ReturnValue localReturnValue1 = result;
				return localReturnValue1;
			}

			BankAdapterRMI bankadapter = getAdapter(bankID);
			if (bankadapter == null) {
				result.result = -920000L;
				result.remark = ("连接适配器[" + bankID + "]失败");
				log(result.remark);
			} else {
				log("准备查询数据");
				Vector<FirmBalance> vector = DAO.getFirmBalance(bankID, null, date);

				if (Tool.fmtDate(new java.util.Date()).equalsIgnoreCase(Tool.fmtDate(date))) {
					Vector vecHis = DAO.getFirmBankFundsHis(" and f.b_date=to_date('" + Tool.fmtDate(date) + "','yyyy-MM-dd')");
					if ((vecHis == null) || (vecHis.size() <= 0)) {
						for (FirmBalance firmB : vector) {
							firmB.FeeMoney = 0.0D;
							firmB.QYChangeMoney = 0.0D;
							firmB.QYMoney = 0.0D;
							firmB.CRJSum = 0.0D;
							firmB.yesQYMoney = 0.0D;
						}
					}

				}

				log("取到数据了，准备发送数据");
				result = bankadapter.sendTradeDate(vector);

				log("发送完毕了");
			}
		} catch (SQLException e) {
			result.result = -1L;
			result.remark = ("给银行[" + bankID + "]发送数据，数据库异常");
			log(result.remark);
			e.printStackTrace();
		} catch (Exception e) {
			result.result = -2L;
			result.remark = ("给银行[" + bankID + "]发送数据，系统异常");
			log(result.remark);
			e.printStackTrace();
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		log("返回结果为\n" + result.toString());
		return result;
	}

	public Map<String, CapitalValue> getCapitalValue(Vector<String> funids, String bankID) {
		Map result = new HashMap();
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			if ((funids != null) && (funids.size() > 0))
				for (String funid : funids) {
					Vector vector = DAO.getCapitalInfoList(" where bankid='" + bankID + "' and funid='" + funid + "'", conn);
					if ((vector != null) && (vector.size() > 0))
						result.put(funid, (CapitalValue) vector.get(0));
				}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public ReturnValue saveQSResult(String bankID, String tradeDate) {
		log(">>>>保存银行清算结果信息>>>>时间：" + Tool.fmtTime(new java.util.Date()) + " 银行编号:" + bankID + ">>>>清算日期" + tradeDate);
		ReturnValue result = new ReturnValue();
		BankAdapterRMI bankadapter = getAdapter(bankID);
		if (bankadapter == null) {
			result.result = -920000L;
			result.remark = ("保存银行[" + bankID + "]清算结果数据，获取适配器服务失败");
		} else {
			try {
				Vector qsResult = bankadapter.getQSRresult(Tool.strToDate(tradeDate));
				result = saveQSResult(qsResult);
			} catch (Exception e) {
				result.result = -1L;
				result.remark = "保存清算结果文件异常";
				e.printStackTrace();
			}
		}
		return result;
	}

	public ReturnValue saveQSResult(Vector<QSRresult> qsResult) {
		log(">>>>保存银行清算结果信息>>>>时间：" + Tool.fmtTime(new java.util.Date()));
		ReturnValue result = new ReturnValue();
		boolean flag = true;
		Connection conn = null;
		if (qsResult != null) {
			if (qsResult.size() <= 0)
				result.remark = "保存银行清算结果，没有需要传入的数据。";
			else
				try {
					conn = DAO.getConnection();
					for (int i = 0; i < qsResult.size(); i++) {
						QSRresult qs = (QSRresult) qsResult.get(i);
						if (qs != null) {
							log(qs.toString());
							if (qs.tradeDate == null) {
								throw new SQLException("传入的数据类表中有数据的交易日期为空");
							}
							if (flag) {
								flag = false;
								DAO.delQSResult(qs.bankID, qs.tradeDate, conn);
							}
							DAO.addQSResult(qs, conn);
						}
					}

					result.remark = "保存银行清算结果数据成功";
				} catch (SQLException e) {
					result.result = -1L;
					result.remark = "保存银行清算结果，数据库异常";
					log(e.getMessage());
					e.printStackTrace();
				} catch (Exception e) {
					result.result = -1L;
					result.remark = "保存银行清算结果，系统异常";
					log(e.getMessage());
					e.printStackTrace();
				} finally {
					DAO.closeStatement(null, null, conn);
				}
		} else {
			result.result = -1L;
			result.remark = "保存银行清算结果，传入的信息为 null。";
		}
		return result;
	}

	public ReturnValue synchronousFirms(String bankID, String[] firmIDs) {
		ReturnValue result = new ReturnValue();
		Connection conn = null;
		try {
			BankAdapterRMI bankadapter = getAdapter(bankID);
			if (bankadapter == null) {
				result.result = -920000L;
				result.remark = "发送交易商信息到银行，获取适配器失败";
			} else {
				conn = DAO.getConnection();
				if ((firmIDs != null) && (firmIDs.length > 0)) {
					Vector mfms = new Vector();
					for (String firmID : firmIDs) {
						if ((firmID != null) && (firmID.trim().length() > 0)) {
							Vector cvs = DAO.getCorrespondList(" where firmid='" + firmID.trim() + "' and bankID='" + bankID.trim() + "'", conn);
							RZDZValue rzdz = DAO.getXYDZValue(bankID, new String[] { firmID.trim() }, DAO.getlastDate(new java.util.Date(), conn));
							if ((cvs != null) && (cvs.size() == 1) && (rzdz != null) && (rzdz.getFdv() != null) && (rzdz.getFdv().size() == 1)) {
								CorrespondValue cv = (CorrespondValue) cvs.get(0);
								FirmDZValue frv = (FirmDZValue) rzdz.getFdv().get(0);
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
		} catch (SQLException e) {
			result.result = -1L;
			result.remark = "同步交易商信息，数据库异常";
			e.printStackTrace();
		} catch (Exception e) {
			result.result = -2L;
			result.remark = "同步交易商信息，系统异常";
			e.printStackTrace();
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public ReturnValue hxSentQS(String bankID, java.util.Date date) {
		ReturnValue result = new ReturnValue();
		try {
			result = isTradeDate(date);
			if (result.result != 0L)
				return result;
			if ((Tool.fmtDate(new java.util.Date()).equalsIgnoreCase(Tool.fmtDate(date))) && (!getTraderStatus())) {
				result.result = -2L;
				result.remark = "系统尚未结算，不允许发送清算数据";
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log(e.getMessage());
			result.result = -1L;
			result.remark = "取得当天是否是交易日失败";
			return result;
		}
		Vector vector = hxGetQS(bankID, date);
		BankAdapterRMI bankadapter = getAdapter(bankID);
		if (bankadapter == null) {
			result.result = -920000L;
			result.remark = ("获取适配器[" + bankID + "]异常");
		} else {
			try {
				result = bankadapter.hxSentQS(vector, date);
				if ((result != null) && (result.result == 0L)) {
					try {
						Connection conn = DAO.getConnection();
						BankQSVO bq2 = new BankQSVO();
						bq2.bankID = bankID;
						bq2.tradeDate = date;
						bq2.note = "华夏银行清算";
						DAO.addBankQS(bq2, conn);
					} catch (SQLException e) {
						result.result = -1L;
						result.remark = "发送清算成功，插入清算表数据库异常";
						throw e;
					} catch (Exception e) {
						result.result = -1L;
						result.remark = "发送清算成功，插入清算表系统异常";
						throw e;
					}
				}
				System.out.println("发送清算返回信息：\n" + result.toString());
			} catch (Exception e) {
				result.result = -1L;
				result.remark = ("发送银行[" + bankID + "][" + Tool.fmtDate(date) + "]清算文件，异常");
				e.printStackTrace();
			}
		}
		return result;
	}

	public ReturnValue hxSentDZ(String bankID, java.util.Date date) {
		ReturnValue result = new ReturnValue();
		try {
			result = isTradeDate(date);
			if (result.result != 0L)
				return result;
			if ((Tool.fmtDate(new java.util.Date()).equalsIgnoreCase(Tool.fmtDate(date))) && (!getTraderStatus())) {
				result.result = -2L;
				result.remark = "系统尚未结算，不允许发送对账数据";
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log(e.getMessage());
			result.result = -1L;
			result.remark = "取得当天是否是交易日失败";
			return result;
		}
		Vector vector = hxGetQS(bankID, date);
		BankAdapterRMI bankadapter = getAdapter(bankID);
		if (bankadapter == null) {
			result.result = -920000L;
			result.remark = ("获取适配器[" + bankID + "]异常");
		} else {
			try {
				result = bankadapter.hxSentDZ(vector, date);
			} catch (Exception e) {
				result.result = -1L;
				result.remark = ("发送银行[" + bankID + "][" + Tool.fmtDate(date) + "]对账文件，异常");
				e.printStackTrace();
			}
		}
		return result;
	}

	public Vector<HXSentQSMsgValue> hxGetQS(String bankID, java.util.Date date) {
		Vector result = null;
		try {
			ReturnValue rv = isTradeDate(date);
			if (rv.result != 0L)
				return result;
			if ((Tool.fmtDate(new java.util.Date()).equalsIgnoreCase(Tool.fmtDate(date))) && (!getTraderStatus())) {
				return result;
			}
			result = DAO.getHXQSMsg(bankID, null, date);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public ReturnValue hxGetQSError(String bankID, java.util.Date tradeDate) {
		ReturnValue result = new ReturnValue();
		BankAdapterRMI bankadapter = getAdapter(bankID);
		if (bankadapter == null) {
			result.result = -920000L;
			result.remark = ("获取银行[" + bankID + "][" + Tool.fmtDate(tradeDate) + "]清算信息文件，连接适配器失败");
		} else {
			try {
				Vector vector = bankadapter.hxGetQSError(tradeDate);
				result = hxSaveQSError(vector);
			} catch (Exception e) {
				result.result = -1L;
				result.remark = ("获取银行[" + bankID + "][" + Tool.fmtDate(tradeDate) + "]清算信息文件,异常");
				e.printStackTrace();
			}
		}
		return result;
	}

	public ReturnValue hxGetDZError(String bankID, java.util.Date tradeDate) {
		ReturnValue result = new ReturnValue();
		BankAdapterRMI bankadapter = getAdapter(bankID);
		if (bankadapter == null) {
			result.result = -920000L;
			result.remark = ("获取银行[" + bankID + "][" + Tool.fmtDate(tradeDate) + "]对账信息文件，连接适配器失败");
		} else {
			try {
				Vector vector = bankadapter.hxGetDZError(tradeDate);
				result = hxSaveDZError(vector);
			} catch (Exception e) {
				result.result = -1L;
				result.remark = ("获取银行[" + bankID + "][" + Tool.fmtDate(tradeDate) + "]对账信息文件,异常");
				e.printStackTrace();
			}
		}
		return result;
	}

	public ReturnValue hxSaveQSError(Vector<QSChangeResult> vector) {
		ReturnValue result = new ReturnValue();
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			try {
				conn.setAutoCommit(false);
				if ((vector != null) && (vector.size() > 0)) {
					QSChangeResult qs = new QSChangeResult();
					qs.bankID = ((QSChangeResult) vector.get(0)).bankID;
					qs.tradeDate = ((QSChangeResult) vector.get(0)).tradeDate;
					DAO.delQSError(qs, conn);
					for (QSChangeResult qsc : vector) {
						DAO.addQSError(qsc, conn);
					}
				}
				conn.commit();
				result.remark = "写入银行清算信息成功";
			} catch (SQLException e) {
				conn.rollback();
			} finally {
				conn.setAutoCommit(true);
			}
		} catch (SQLException e) {
			result.result = -1L;
			result.remark = "写入银行清算信息，数据库异常";
			e.printStackTrace();
		} catch (Exception e) {
			result.result = -2L;
			result.remark = "写入银行清算信息，系统异常";
			e.printStackTrace();
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public ReturnValue hxSaveDZError(Vector<QSRresult> vector) {
		ReturnValue result = new ReturnValue();
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			try {
				conn.setAutoCommit(false);
				if ((vector != null) && (vector.size() > 0)) {
					QSRresult rs = (QSRresult) vector.get(0);
					if (rs != null) {
						DAO.delQSResult(rs.bankID, rs.tradeDate, conn);
					}
					for (QSRresult rsa : vector) {
						DAO.addQSResult(rsa, conn);
					}
				}
				conn.commit();
				result.remark = "添加对账信息文件成功";
			} catch (SQLException e) {
				conn.rollback();
				result.result = -1L;
				result.remark = "添加对账信息文件，数据库异常";
				e.printStackTrace();
			} finally {
				conn.setAutoCommit(true);
			}
		} catch (SQLException e) {
			result.result = -1L;
			result.remark = "写入对账信息文件，数据库异常";
			e.printStackTrace();
		} catch (Exception e) {
			result.result = -2L;
			result.remark = "写入对账信息文件，系统异常";
			e.printStackTrace();
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public ReturnValue pfdbQS(String bankID, java.util.Date date) {
		ReturnValue result = new ReturnValue();
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			Vector v1 = DAO.getChangeFirmRights(" and trunc(Trade_Date)=to_date('" + Tool.fmtDate(date) + "','yyyy-MM-dd')", conn);
			if ((v1 != null) && (v1.size() > 0)) {
				try {
					conn.setAutoCommit(false);
					Vector v11 = DAO.getChangeFirmRights(
							" and trunc(Trade_Date)=to_date('" + Tool.fmtDate(date) + "','yyyy-MM-dd') and flag<>'Y' for update ", conn);
					if ((v11 != null) && (v11.size() > 0)) {
						String[] Serial_Id1 = new String[v11.size()];
						String[] firmIDs = new String[v11.size()];
						for (int i = 0; i < v11.size(); i++) {
							TradeList tl = (TradeList) v11.get(i);
							Serial_Id1[i] = tl.Serial_Id;
							if (("2".equalsIgnoreCase(tl.Trade_Type)) || ("3".equalsIgnoreCase(tl.Trade_Type))
									|| ("4".equalsIgnoreCase(tl.Trade_Type)))
								firmIDs[i] = tl.B_Member_Code;
							else {
								firmIDs[i] = tl.S_Member_Code;
							}
						}
						DAO.delChangeFirmRights(bankID, date, Serial_Id1, conn);
						DAO.addChangeFirmRights(bankID, firmIDs, date, conn);
					}
					conn.commit();
				} catch (SQLException e1) {
					conn.rollback();
					throw e1;
				} catch (Exception e1) {
					conn.rollback();
					throw e1;
				} finally {
					conn.setAutoCommit(true);
				}
			}
			DAO.addChangeFirmRights(bankID, null, date, conn);

			Vector v2 = DAO.getChangeFirmFrozen(" and trunc(Trade_Date)=to_date('" + Tool.fmtDate(date) + "','yyyy-MM-dd')", conn);
			if ((v2 != null) && (v2.size() > 0))
				try {
					conn.setAutoCommit(false);
					Vector v21 = DAO.getChangeFirmFrozen(
							" and trunc(Trade_Date)=to_date('" + Tool.fmtDate(date) + "','yyyy-MM-dd') and flag<>'Y' for update", conn);
					if ((v21 != null) && (v21.size() > 0)) {
						String[] Serial_Id2 = new String[v21.size()];
						String[] firmIDs = new String[v21.size()];
						for (int i = 0; i < v21.size(); i++) {
							Margins mg = (Margins) v21.get(i);
							Serial_Id2[i] = mg.Serial_Id;
							firmIDs[i] = mg.Member_Code;
						}
						DAO.delChangeFirmFrozen(bankID, date, Serial_Id2, conn);
						DAO.addChangeFirmFrozen(bankID, firmIDs, date, conn);
					}
					conn.commit();
				} catch (SQLException e1) {
					conn.rollback();
					throw e1;
				} catch (Exception e1) {
					conn.rollback();
					throw e1;
				} finally {
					conn.setAutoCommit(true);
				}
			else
				DAO.addChangeFirmFrozen(bankID, null, date, conn);
		} catch (SQLException e) {
			result.result = -1L;
			result.remark = ("保存浦发银行 " + Tool.fmtDate(date) + " 清算数据，数据库异常");
			log(result.remark);
			e.printStackTrace();
		} catch (Exception e) {
			result.result = -1L;
			result.remark = ("保存浦发银行 " + Tool.fmtDate(date) + " 清算数据，系统异常");
			log(result.remark);
			e.printStackTrace();
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public ReturnValue getTradesDateDetailsList(String bankID, java.util.Date date, int sendCount, int timeOutCount, int faileCount) {
		ReturnValue result = new ReturnValue();
		try {
			result = isTradeDate(date);
			if (result.result != 0L)
				return result;
			if ((Tool.fmtDate(new java.util.Date()).equalsIgnoreCase(Tool.fmtDate(date))) && (!getTraderStatus())) {
				result.result = -2L;
				result.remark = "系统尚未结算，不允许发送权益变化量";
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log(e.getMessage());
			result.result = -1L;
			result.remark = "取得当天是否是转账日期失败";
			return result;
		}
		Vector balance = getTradesDateDetailsList(bankID, date, new String[] { "N", "F" });
		BankAdapterRMI bankadapter = getAdapter(bankID);
		if (bankadapter == null) {
			result.result = -920000L;
		} else {
			Connection conn = null;
			try {
				conn = DAO.getConnection();
				TraderResult tr = bankadapter.sendTradeDetails(balance, sendCount, timeOutCount, faileCount);
				try {
					conn.setAutoCommit(false);
					if ((tr != null) && (tr.tVe != null)) {
						for (int i = 0; i < tr.tVe.size(); i++) {
							DAO.modChangeFirmRights(((TradeList) tr.tVe.get(i)).Serial_Id, ((TradeList) tr.tVe.get(i)).flag, conn);
						}
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
			} catch (SQLException e) {
				e.printStackTrace();
				result.result = -1L;
				result.remark = "发送权益变化量时数据库异常";
			} catch (Exception e) {
				e.printStackTrace();
				result.result = -1L;
				result.remark = "发送权益该变量信息时系统异常";
			} finally {
				DAO.closeStatement(null, null, conn);
			}
		}
		return result;
	}

	public Vector<TradeList> getTradesDateDetailsList(String bankID, java.util.Date date, String[] flag) {
		Vector result = null;
		Connection conn = null;
		try {
			ReturnValue rv = isTradeDate(date);
			if (rv.result != 0L)
				return result;
			if ((Tool.fmtDate(new java.util.Date()).equalsIgnoreCase(Tool.fmtDate(date))) && (!getTraderStatus())) {
				return result;
			}
			conn = DAO.getConnection();
			String filter = " and trunc(Trade_Date)=to_date('" + Tool.fmtDate(date) + "','yyyy-MM-dd') ";
			if ((flag != null) && (flag.length > 0)) {
				String flags = "'B'";
				for (String fl : flag) {
					if ((fl != null) && (fl.trim().length() > 0)) {
						flags = flags + ",'" + fl.trim() + "'";
					}
				}
				filter = filter + " and flag in (" + flags + ")";
			}
			result = DAO.getChangeFirmRights(filter, conn);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public ReturnValue getDongjieDetailList(String bankID, java.util.Date date, int sendCount, int timeOutCount, int faileCount) {
		ReturnValue result = new ReturnValue();
		try {
			result = isTradeDate(date);
			if (result.result != 0L)
				return result;
			if ((Tool.fmtDate(new java.util.Date()).equalsIgnoreCase(Tool.fmtDate(date))) && (!getTraderStatus())) {
				result.result = -2L;
				result.remark = "系统尚未结算，不允许发送冻结资金";
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log(e.getMessage());
			result.result = -1L;
			result.remark = "取得当天是否是转账日期失败";
			return result;
		}
		Connection conn = null;
		Vector margins = getDongjieDetailList(bankID, date, new String[] { "N", "F" });
		try {
			BankAdapterRMI bankadapter = getAdapter(bankID);
			if (bankadapter == null) {
				result.result = -920000L;
				result.remark = ("发送银行 " + bankID + " 在 " + Tool.fmtDate(date) + " 的冻结资金，连接银行失败");
			} else {
				TraderResult tr = bankadapter.sendMargins(margins, sendCount, timeOutCount, faileCount);
				if ((tr != null) && (tr.mVe != null)) {
					conn = DAO.getConnection();
					try {
						conn.setAutoCommit(false);
						for (int i = 0; i < tr.mVe.size(); i++) {
							DAO.modChangeFirmFrozen(((Margins) tr.mVe.get(i)).Serial_Id, ((Margins) tr.mVe.get(i)).flag, conn);
						}
						conn.commit();
						result.remark = ("发送银行 " + bankID + " 在 " + Tool.fmtDate(date) + " 的冻结资金成功");
					} catch (SQLException e) {
						conn.rollback();
						throw e;
					} catch (Exception e) {
						conn.rollback();
						throw e;
					} finally {
						conn.setAutoCommit(true);
					}
				} else {
					result.result = -1L;
					result.remark = ("发送银行 " + bankID + " 在 " + Tool.fmtDate(date) + " 的冻结资金，返回信息为空");
				}
			}
		} catch (SQLException e) {
			result.result = -1L;
			result.remark = "发送冻结资金时数据库异常";
			e.printStackTrace();
		} catch (Exception e) {
			result.result = -1L;
			result.remark = "发送冻结资金时系统异常";
			e.printStackTrace();
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public Vector<Margins> getDongjieDetailList(String bankID, java.util.Date date, String[] flag) {
		Vector result = null;
		Connection conn = null;
		try {
			ReturnValue rv = isTradeDate(date);
			if (rv.result != 0L)
				return result;
			if ((Tool.fmtDate(new java.util.Date()).equalsIgnoreCase(Tool.fmtDate(date))) && (!getTraderStatus())) {
				return result;
			}
			conn = DAO.getConnection();
			String filter = " and trunc(Trade_Date)=to_date('" + Tool.fmtDate(date) + "','yyyy-MM-dd') ";
			if ((flag != null) && (flag.length > 0)) {
				String flags = "'B'";
				for (String fl : flag) {
					if ((fl != null) && (fl.trim().length() > 0)) {
						flags = flags + ",'" + fl.trim() + "'";
					}
				}
				filter = filter + " and flag in (" + flags + ")";
			}
			result = DAO.getChangeFirmFrozen(filter, conn);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public Hashtable<String, FundsMarg> getFundsMarg(String bankID, java.util.Date date) {
		Hashtable result = null;

		return result;
	}

	public ReturnValue sendGHQS(String bankId, String firmId, java.util.Date qdate) {
		ReturnValue result = new ReturnValue();
		BankAdapterRMI bankAdapter = getAdapter(bankId);
		try {
			result = isTradeDate(qdate);
			if (result.result != 0L)
				return result;
			if ((Tool.fmtDate(new java.util.Date()).equalsIgnoreCase(Tool.fmtDate(qdate))) && (!getTraderStatus())) {
				result.result = -2L;
				result.remark = "系统尚未结算，不允许发送清算数据";
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log(e.getMessage());
			result.result = -1L;
			result.remark = "取得是否是交易日期异常";
			return result;
		}
		if (bankAdapter == null) {
			result.result = -920000L;
			result.remark = ("发送[" + bankId + "]银行交易商清算信息，连接适配器失败");
		} else {
			List frs = getRightsList(bankId, firmId, qdate);
			List tds = getChangeBalance(bankId, firmId, qdate);
			List opdf = getOpenOrDropMaket(bankId, qdate);
			try {
				result = bankAdapter.sendTradeData(frs, tds, opdf, qdate);
			} catch (Exception e) {
				result.result = -1L;
				result.remark = ("发送[" + bankId + "]银行交易商清算信息，适配器抛出异常");
				e.printStackTrace();
			}
		}
		return result;
	}

	public ReturnValue getBankFirmRightValue(String bankID, java.util.Date date) {
		ReturnValue result = new ReturnValue();
		BankAdapterRMI bankadapter = getAdapter(bankID);
		if (bankadapter == null) {
			result.result = -920000L;
			result.remark = "获取分分核对平衡信息，获取适配器失败";
		} else {
			try {
				List<BankFirmRightValue> firmRightValues = bankadapter.getBankFirmRightValue(bankID, date);
				try {
					for (BankFirmRightValue bfrv : firmRightValues) {
						List bfrlist = DAO.getBankCapital(bfrv);
						if ((bfrlist != null) && (bfrlist.size() > 0))
							DAO.updateBankCapital(bfrv);
						else {
							DAO.addBankCapital(bfrv);
						}
						result.remark = "写入分分核对信息成功";
					}
				} catch (Exception e) {
					result.result = -1L;
					result.remark = "传入分分核对信息，系统异常";
					e.printStackTrace();
				}
			} catch (Exception e) {
				result.result = -1L;
				result.remark = "传入分分核对信息，系统异常";
				e.printStackTrace();
			}
		}
		return result;
	}

	public ReturnValue getProperBalanceValue(String bankID, java.util.Date date) {
		ReturnValue result = new ReturnValue();
		BankAdapterRMI bankadapter = getAdapter(bankID);
		if (bankadapter == null) {
			result.result = -920000L;
			result.remark = "获取总分平衡信息，获取适配器失败";
		} else {
			try {
				ProperBalanceValue balanceValue = bankadapter.getProperBalanceValue(bankID, date);
				try {
					List list = DAO.getProperBalance(balanceValue);
					if ((list != null) && (list.size() > 0))
						DAO.updateProperBalance(balanceValue);
					else {
						DAO.addProperBalance(balanceValue);
					}
					result.remark = "写入总分平衡信息成功";
				} catch (Exception e) {
					result.result = -1L;
					result.remark = "传入总分平衡信息，系统异常";
					e.printStackTrace();
				}
			} catch (Exception e) {
				result.result = -1L;
				result.remark = "传入总分平衡信息，系统异常";
				e.printStackTrace();
			}
		}
		return result;
	}

	public List<FirmRightsValue> getTradeDataMsg(String bankId, String firmId, java.util.Date qdate) {
		List rightList = null;
		try {
			ReturnValue rv = isTradeDate(qdate);
			if (rv.result != 0L)
				return rightList;
			if ((Tool.fmtDate(new java.util.Date()).equalsIgnoreCase(Tool.fmtDate(qdate))) && (!getTraderStatus())) {
				return rightList;
			}
			rightList = DAO.getTradeDataMsg(bankId, firmId, qdate);
		} catch (SQLException e) {
			System.out.println("获取交易商权益信息数据库异常");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("获取交易商权益信息找不到类");
			e.printStackTrace();
		}
		return rightList;
	}

	public Map<String, FirmRights> getRightsMap(String bankId, String firmId, java.util.Date qdate) {
		log(Tool.fmtDate(qdate) + "]");
		try {
			ReturnValue rv = isTradeDate(qdate);
			if (rv.result != 0L)
				return null;
			if ((Tool.fmtDate(new java.util.Date()).equalsIgnoreCase(Tool.fmtDate(qdate))) && (!getTraderStatus()))
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			log(e.getMessage());
			return null;
		}
		Map rights = new HashMap();
		List<FirmRightsValue> frvs = getTradeDataMsg(bankId, firmId, qdate);
		if ((frvs != null) && (frvs.size() > 0)) {
			for (FirmRightsValue frv : frvs) {
				FirmRights fr = new FirmRights();
				fr.firmId = frv.firmid;
				fr.firmName = frv.accountname;
				fr.account = frv.account;
				fr.money = (frv.getAvilableBlance() + frv.getTimebargainBalance() + frv.getZcjsBalance() + frv.getVendueBalance());
				rights.put(frv.firmid, fr);
			}
		}
		return rights;
	}

	public List<FirmRights> getRightsList(String bankId, String firmId, java.util.Date qdate) {
		try {
			ReturnValue rv = isTradeDate(qdate);
			if (rv.result != 0L)
				return null;
			if ((Tool.fmtDate(new java.util.Date()).equalsIgnoreCase(Tool.fmtDate(qdate))) && (!getTraderStatus()))
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			log(e.getMessage());
			return null;
		}
		List rights = new ArrayList();
		List<FirmRightsValue> frvs = getTradeDataMsg(bankId, firmId, qdate);
		if ((frvs != null) && (frvs.size() > 0)) {
			for (FirmRightsValue frv : frvs) {
				FirmRights fr = new FirmRights();
				fr.firmId = frv.firmid;
				fr.firmName = frv.accountname;
				fr.account = frv.account;
				fr.money = (frv.getAvilableBlance() + frv.getTimebargainBalance() + frv.getZcjsBalance() + frv.getVendueBalance());
				rights.add(fr);
			}
		}
		return rights;
	}

	public List<TradingDetailsValue> getChangeBalance(String bankId, String firmId, java.util.Date qdate) {
		Connection conn = null;
		List result = null;
		try {
			ReturnValue rv = isTradeDate(qdate);
			if (rv.result != 0L)
				return result;
			if ((Tool.fmtDate(new java.util.Date()).equalsIgnoreCase(Tool.fmtDate(qdate))) && (!getTraderStatus()))
				return result;
		} catch (Exception e) {
			e.printStackTrace();
			log(e.getMessage());
			return result;
		}
		List<FirmRights> selectDate = getRightsList(bankId, firmId, qdate);
		try {
			conn = DAO.getConnection();

			java.util.Date upData = DAO.getlastDate(qdate, conn);
			Map lastDate = getRightsMap(bankId, firmId, upData);
			int i = 1;
			for (FirmRights fr : selectDate) {
				double incomeMoney = DAO.sumCapitalInfo(
						"where firmid='" + fr.firmId + "' and  trunc(b_date)=to_date('" + Tool.fmtDate(qdate) + "','yyyy-MM-dd') and code='Deposit' ",
						conn);
				double outcomeMoney = DAO.sumCapitalInfo(
						"where firmid='" + fr.firmId + "' and  trunc(b_date)=to_date('" + Tool.fmtDate(qdate) + "','yyyy-MM-dd') and code='Fetch' ",
						conn);
				double changeBalance = fr.getMoney() - incomeMoney + outcomeMoney
						- (lastDate.get(fr.firmId) == null ? 0.0D : ((FirmRights) lastDate.get(fr.firmId)).getMoney());
				System.out.println(" 交易商 " + fr.firmId + " 资金变化为 本日权益[" + fr.getMoney() + "] 入金[" + incomeMoney + "] 出金[" + outcomeMoney + "] 上日权益["
						+ (lastDate.get(fr.firmId) == null ? 0.0D : ((FirmRights) lastDate.get(fr.firmId)).getMoney()) + "]");
				if (changeBalance != 0.0D) {
					TradingDetailsValue tdv = new TradingDetailsValue();
					tdv.maketNum = i;
					tdv.account = fr.account;
					tdv.firmId = fr.firmId;
					tdv.firmName = fr.firmName;
					if (changeBalance > 0.0D) {
						tdv.money = changeBalance;
						tdv.updown = "2";
					} else {
						tdv.money = (-changeBalance);
						tdv.updown = "1";
					}
					if (result == null) {
						result = new ArrayList();
					}
					i++;
					result.add(tdv);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		if (result == null) {
			System.out.println("返回前 结果为 null 需要初始化");
			result = new ArrayList();
		}
		System.out.println(result.size());
		return result;
	}

	public List<OpenOrDelFirmValue> getOpenOrDropMaket(String bankId, java.util.Date qdate) {
		List result = null;
		String openFilter = " where 1=1 and isopen='1' ";
		String delFilter = " where 1=1 ";
		if ((bankId != null) && (!bankId.trim().equals(""))) {
			openFilter = openFilter + "and bankId='" + bankId + "' ";
			delFilter = delFilter + "and bankId='" + bankId + "_D' ";
		}
		if (qdate != null) {
			openFilter = openFilter + "and trunc(openTime)=to_date('" + Tool.fmtDate(qdate) + "','yyyy-MM-dd') ";
			delFilter = delFilter + "and trunc(delTime)=to_date('" + Tool.fmtDate(qdate) + "','yyyy-MM-dd') ";
		}

		Vector<CorrespondValue> openFirmVector = null;

		Vector<CorrespondValue> delFirmVector = null;
		try {
			openFirmVector = DAO.getCorrespondList(openFilter);

			delFirmVector = DAO.getCorrespondList(delFilter);
			if (openFirmVector == null) {
				openFirmVector = new Vector();
			}
			if (delFirmVector == null) {
				delFirmVector = new Vector();
			}
			int i = 1;
			for (CorrespondValue cv : openFirmVector) {
				OpenOrDelFirmValue odf = new OpenOrDelFirmValue();
				if (result == null) {
					result = new ArrayList();
				}
				odf.firmId = cv.firmID;
				odf.firmName = cv.accountName;
				odf.maketNum = i;
				odf.openordel = "0";
				i++;
				result.add(odf);
			}
			for (CorrespondValue cv : delFirmVector) {
				OpenOrDelFirmValue odf = new OpenOrDelFirmValue();
				if (result == null) {
					result = new ArrayList();
				}
				odf.firmId = cv.firmID;
				odf.firmName = cv.accountName;
				odf.maketNum = i;
				odf.openordel = "1";
				result.add(odf);
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		if (result == null) {
			result = new ArrayList();
		}
		return result;
	}

	public long getBankFirmRightValue(List<BankFirmRightValue> list) {
		long result = 0L;
		for (BankFirmRightValue bfrv : list) {
			List bfrlist = DAO.getBankCapital(bfrv);
			if ((bfrlist != null) && (bfrlist.size() > 0))
				result = DAO.updateBankCapital(bfrv);
			else {
				result = DAO.addBankCapital(bfrv);
			}
		}
		return result;
	}

	public long getProperBalanceValue(ProperBalanceValue pbv) {
		long result = 0L;
		List list = DAO.getProperBalance(pbv);
		if ((list != null) && (list.size() > 0))
			result = DAO.updateProperBalance(pbv);
		else {
			result = DAO.addProperBalance(pbv);
		}
		return result;
	}

	public ReturnValue sendHRBQSValue(String bankID, String[] firmIDs, java.util.Date tradeDate) {
		ReturnValue result = new ReturnValue();
		BankAdapterRMI bankAdapter = getAdapter(bankID);
		Connection conn = null;
		try {
			result = isTradeDate(tradeDate);
			if (result.result != 0L)
				return result;
			if ((Tool.fmtDate(new java.util.Date()).equalsIgnoreCase(Tool.fmtDate(tradeDate))) && (!getTraderStatus())) {
				result.result = -2L;
				result.remark = "系统尚未结算，不允许发送清算数据";
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log(e.getMessage());
			result.result = -1L;
			result.remark = "取得是否是交易日期异常";
			return result;
		}
		if (bankAdapter == null) {
			result.result = -920000L;
			result.remark = ("发送[" + bankID + "]银行交易商清算信息，连接适配器失败");
		} else {
			RZQSValue qs = getHRBQSValue(bankID, firmIDs, tradeDate);
			RZDZValue dz = getHRBDZValue(bankID, firmIDs, tradeDate);
			try {
				try {
					conn = DAO.getConnection();
					BankQSVO bq2 = new BankQSVO();
					bq2.bankID = bankID;
					bq2.tradeDate = tradeDate;
					bq2.tradeStatus = 2;
					bq2.note = ("银行" + bankID + "清算");
					Vector vt = DAO.getBankQSDate(
							" where trunc(tradedate)=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd')" + " and bankID='" + bankID + "'");
					if ((vt == null) || (vt.size() == 0))
						DAO.addBankQS(bq2, conn);
				} catch (SQLException e) {
					result.result = -1L;
					result.remark = "发送清算成功，插入清算表数据库异常";
					throw e;
				} catch (Exception e) {
					result.result = -1L;
					result.remark = "发送清算成功，插入清算表系统异常";
					throw e;
				}
				result = bankAdapter.setRZ(qs, dz, tradeDate);
				if ((result != null) && (result.result == 0L))
					try {
						conn = DAO.getConnection();
						BankQSVO bq2 = new BankQSVO();
						bq2.bankID = bankID;
						bq2.tradeDate = tradeDate;
						bq2.tradeStatus = 1;
						bq2.note = ("银行" + bankID + "清算");
						Vector vt = DAO.getBankQSDate(
								" where trunc(tradedate)=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd')" + " and bankID='" + bankID + "'");
						if (((BankQSVO) vt.get(0)).tradeStatus != 0)
							DAO.modBankQS(bq2, conn);
					} catch (SQLException e) {
						result.result = -1L;
						result.remark = "发送清算成功，插入清算表数据库异常";
						throw e;
					} catch (Exception e) {
						result.result = -1L;
						result.remark = "发送清算成功，插入清算表系统异常";
						throw e;
					}
			} catch (Exception e) {
				result.result = -1L;
				result.remark = ("发送[" + bankID + "]银行交易商清算信息，适配器抛出异常");
				e.printStackTrace();
			}

		}

		return result;
	}

	public RZQSValue getHRBQSValue(String bankID, String[] firmIDs, java.util.Date tradeDate) {
		RZQSValue result = null;
		try {
			ReturnValue rv = isTradeDate(tradeDate);
			if (rv.result != 0L)
				return result;
			if ((Tool.fmtDate(new java.util.Date()).equalsIgnoreCase(Tool.fmtDate(tradeDate))) && (!getTraderStatus())) {
				return result;
			}
			result = DAO.getXYQSValue(bankID, firmIDs, tradeDate);

			Connection conn = DAO.getConnection();
			boolean flag = true;
			java.util.Date usdate = tradeDate;
			Map todayDate = new HashMap();
			Vector firmsTody = new Vector();
			Vector vcTody = result.getFrv();
			for (int i = 0; i < vcTody.size(); i++) {
				todayDate.put(((FirmRightValue) vcTody.get(i)).firmID, (FirmRightValue) vcTody.get(i));
				firmsTody.add(((FirmRightValue) vcTody.get(i)).firmID);
			}
			java.util.Date lastQSDate = DAO.getMaxBankQSSuccessDate(bankID, usdate, conn);
			while (flag) {
				usdate = DAO.getlastDate(usdate, conn);
				if (usdate == null) {
					flag = false;
				} else if ((lastQSDate != null) && (usdate.after(lastQSDate))) {
					Map lastDay = new HashMap();
					RZQSValue rzqsv = DAO.getXYQSValue(bankID, firmIDs, usdate);
					Vector vc = rzqsv.getFrv();
					for (int i = 0; i < vc.size(); i++) {
						lastDay.put(((FirmRightValue) vc.get(i)).firmID, (FirmRightValue) vc.get(i));
					}
					for (int i = 0; i < firmsTody.size(); i++) {
						String firmID = (String) firmsTody.get(i);
						if (lastDay.get(firmID) != null) {
							FirmRightValue firmLast = (FirmRightValue) lastDay.get(firmID);
							FirmRightValue firmTody = (FirmRightValue) todayDate.get(firmID);

							if (firmLast != null) {
								firmTody.tradeFee = (Double.parseDouble(firmTody.tradeFee == null ? "0" : firmTody.tradeFee)
										+ Double.parseDouble(firmLast.tradeFee == null ? "0" : firmLast.tradeFee)) + "";
								firmTody.settleFee = (Double.parseDouble(firmTody.settleFee == null ? "0" : firmTody.settleFee)
										+ Double.parseDouble(firmLast.settleFee == null ? "0" : firmLast.settleFee)) + "";
								firmTody.firmErrorMoney += firmLast.firmErrorMoney;
								firmTody.cashMoney += firmLast.cashMoney;
								firmTody.billMoney += firmLast.billMoney;
								firmTody.availableBalance += firmLast.availableBalance;
								firmTody.cash += firmLast.cash;
							}
						}
					}

					result.getMarketRight().bankErrorMoney += rzqsv.getMarketRight().bankErrorMoney;
					result.getMarketRight().maketErrorMoney += rzqsv.getMarketRight().maketErrorMoney;
					result.getMarketRight().maketMoney = result.getMarketRight().maketMoney.add(rzqsv.getMarketRight().maketMoney);
				} else {
					flag = false;
				}

			}

			Vector vcTodyFinal = new Vector();
			for (int i = 0; i < firmsTody.size(); i++) {
				vcTodyFinal.add((FirmRightValue) todayDate.get(firmsTody.get(i)));
			}
			result.setFrv(vcTodyFinal);
		} catch (SQLException e) {
			e.printStackTrace();
			log(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result == null) {
			result = new RZQSValue();
		}
		return result;
	}

	public RZDZValue getHRBDZValue(String bankID, String[] firmIDs, java.util.Date tradeDate) {
		RZDZValue result = null;
		try {
			ReturnValue rv = isTradeDate(tradeDate);
			if (rv.result != 0L)
				return result;
			if ((Tool.fmtDate(new java.util.Date()).equalsIgnoreCase(Tool.fmtDate(tradeDate))) && (!getTraderStatus())) {
				return result;
			}
			result = DAO.getXYDZValue(bankID, firmIDs, tradeDate);

			Connection conn = DAO.getConnection();
			boolean flag = true;
			java.util.Date usdate = tradeDate;
			Map todayDate = new HashMap();
			Vector firmsTody = new Vector();
			Vector vcTody = result.getFdv();
			for (int i = 0; i < vcTody.size(); i++) {
				todayDate.put(((FirmDZValue) vcTody.get(i)).firmID, (FirmDZValue) vcTody.get(i));
				firmsTody.add(((FirmDZValue) vcTody.get(i)).firmID);
			}
			java.util.Date lastQSDate = DAO.getMaxBankQSSuccessDate(bankID, usdate, conn);
			while (flag) {
				usdate = DAO.getlastDate(usdate, conn);
				if (usdate == null) {
					flag = false;
				} else if ((lastQSDate != null) && (usdate.after(lastQSDate))) {
					Map lastDay = new HashMap();
					RZDZValue rzdzv = DAO.getXYDZValue(bankID, firmIDs, usdate);
					Vector vc = rzdzv.getFdv();
					for (int i = 0; i < vc.size(); i++) {
						lastDay.put(((FirmDZValue) vc.get(i)).firmID, (FirmDZValue) vc.get(i));
					}
					for (int i = 0; i < firmsTody.size(); i++) {
						String firmID = (String) firmsTody.get(i);
						if (lastDay.get(firmID) != null) {
							FirmDZValue firmLast = (FirmDZValue) lastDay.get(firmID);
							FirmDZValue firmTody = (FirmDZValue) todayDate.get(firmID);

							firmTody.quanyibh = new BigDecimal(firmTody.quanyibh).add(new BigDecimal(firmLast.quanyibh)).doubleValue();
						}
					}
				} else {
					flag = false;
				}

			}

			Vector vcTodyFinal = new Vector();
			for (int i = 0; i < firmsTody.size(); i++) {
				vcTodyFinal.add((FirmDZValue) todayDate.get(firmsTody.get(i)));
			}
			result.setFdv(vcTodyFinal);
		} catch (SQLException e) {
			e.printStackTrace();
			log(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result == null) {
			result = new RZDZValue();
		}
		return result;
	}

	public ReturnValue updownBank(String bankID, int type) {
		ReturnValue result = new ReturnValue();
		BankAdapterRMI bankadapter = getAdapter(bankID);
		try {
			if (type == 0) {
				result = bankadapter.loginBank(bankID);
			} else if (type == 1) {
				result = bankadapter.quitBank(bankID);
			} else {
				result.remark = "没有相对应的操作";
				result.result = -920000L;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.remark = "调用适配器失败";
			result.result = -920000L;
		}

		return result;
	}

	public ReturnValue sendXYQSValue(String bankID, String[] firmIDs, java.util.Date tradeDate) {
		ReturnValue result = new ReturnValue();
		BankAdapterRMI bankadapter = getAdapter(bankID);

		Connection conn = null;
		try {
			conn = DAO.getConnection();

			result = isTradeDate(tradeDate);
			System.out.println(result.toString());
			if (result.result != 0L)
				return result;
			if ((Tool.fmtDate(new java.util.Date()).equalsIgnoreCase(Tool.fmtDate(tradeDate))) && (!getTraderStatus())) {
				result.result = -2L;
				result.remark = "系统尚未结算，不允许发送清算数据";
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log(e.getMessage());
			result.result = -1L;
			result.remark = "取得是否是交易日期异常";
			return result;
		}
		if (bankadapter == null) {
			result.result = -920000L;
			result.remark = ("发送[" + bankID + "]银行交易商清算信息，连接适配器失败");
		} else {
			RZQSValue qs = getXYQSValue(bankID, firmIDs, tradeDate);
			RZDZValue dz = getXYDZValue(bankID, firmIDs, tradeDate);
			try {
				result = bankadapter.setRZ(qs, dz, tradeDate);
				if ((result != null) && (result.result == 0L))
					try {
						conn = DAO.getConnection();
						BankQSVO bq2 = new BankQSVO();
						bq2.bankID = bankID;
						bq2.tradeDate = tradeDate;
						bq2.note = "兴业银行清算";
						DAO.addBankQS(bq2, conn);
					} catch (SQLException e) {
						result.result = -1L;
						result.remark = "发送清算成功，插入清算表数据库异常";
						throw e;
					} catch (Exception e) {
						result.result = -1L;
						result.remark = "发送清算成功，插入清算表系统异常";
						throw e;
					}
			} catch (Exception e) {
				result.result = -1L;
				result.remark = ("发送[" + bankID + "]银行交易商清算信息，适配器抛出异常");
				e.printStackTrace();
			}
		}
		return result;
	}

	public RZQSValue getXYQSValue(String bankID, String[] firmIDs, java.util.Date tradeDate) {
		RZQSValue result = null;
		try {
			ReturnValue rv = isTradeDate(tradeDate);
			if (rv.result != 0L)
				return result;
			if ((Tool.fmtDate(new java.util.Date()).equalsIgnoreCase(Tool.fmtDate(tradeDate))) && (!getTraderStatus())) {
				return result;
			}
			result = DAO.getXYQSValue(bankID, firmIDs, tradeDate);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (result == null) {
			result = new RZQSValue();
		}
		return result;
	}

	public RZDZValue getXYDZValue(String bankID, String[] firmIDs, java.util.Date tradeDate) {
		RZDZValue result = null;
		Connection conn = null;
		try {
			result = DAO.getXYDZValue(bankID, firmIDs, tradeDate);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		if (result == null) {
			result = new RZDZValue();
		}
		return result;
	}

	public ReturnValue getZFPH(String bankID, java.util.Date date) {
		ReturnValue result = new ReturnValue();
		BankAdapterRMI bankadapter = getAdapter(bankID);
		if (bankadapter == null) {
			result.result = -920000L;
			result.remark = "获取总分平衡信息，获取适配器失败";
		} else {
			try {
				ZFPHValue zfph = bankadapter.getZFPH(date);
				result = saveZFPH(zfph);
			} catch (Exception e) {
				result.result = -1L;
				result.remark = "获取总分平衡监管信息，适配器异常";
				e.printStackTrace();
			}
		}
		return result;
	}

	public ReturnValue getFFHD(String bankID, java.util.Date date) {
		ReturnValue result = new ReturnValue();
		BankAdapterRMI bankadapter = getAdapter(bankID);
		if (bankadapter == null) {
			result.result = -920000L;
			result.remark = "获取分分核对平衡信息，获取适配器失败";
		} else {
			try {
				FFHDValue ffhd = bankadapter.getFFHD(date);
				result = saveFFHD(ffhd);
			} catch (Exception e) {
				result.result = -1L;
				result.remark = "获取纷纷核对平衡信息，适配器异常";
				e.printStackTrace();
			}
		}
		return result;
	}

	public ReturnValue saveZFPH(ZFPHValue zfph) {
		ReturnValue result = new ReturnValue();
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			try {
				conn.setAutoCommit(false);
				if ((zfph.bankID != null) && (zfph.bankID.trim().length() > 0) && (zfph.tradeDate != null)) {
					DAO.delZFPH(zfph.bankID, zfph.tradeDate, zfph.result, conn);
					DAO.addZFPH(zfph, conn);
					result.remark = ("添加银行[" + zfph.bankID + "][" + Tool.fmtDate(zfph.tradeDate) + "]总分平衡信息成功");
				} else {
					result.result = -1L;
					result.remark = "写入总分平衡监管信息，传来数据不完整";
					log(zfph.toString());
				}
				conn.commit();
			} catch (SQLException e) {
				conn.rollback();
				result.result = -1L;
				result.remark = "添加总分平衡时数据库异常";
				e.printStackTrace();
			} finally {
				conn.setAutoCommit(true);
			}
		} catch (SQLException e) {
			result.result = -1L;
			result.remark = "添加总分平衡取得数据库连接时数据库异常";
			e.printStackTrace();
		} catch (Exception e) {
			result.result = -2L;
			result.remark = "添加总分平衡时系统异常";
			e.printStackTrace();
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public ReturnValue saveFFHD(FFHDValue ffhd) {
		ReturnValue result = new ReturnValue();
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			try {
				conn.setAutoCommit(false);
				if ((ffhd != null) && (ffhd.getFdv() != null) && (ffhd.getFdv().size() > 0)) {
					FirmDateValue fdv = (FirmDateValue) ffhd.getFdv().get(0);
					if (fdv != null) {
						DAO.delFFHD(fdv.bankID, null, fdv.tradeDate, conn);
						DAO.addFFHD(ffhd, conn);
					}
				}
				result.remark = "写入分分核对信息成功";
				conn.commit();
			} catch (SQLException e) {
				conn.rollback();
				result.result = -1L;
				result.remark = "写入分分核对信息，数据库异常";
				e.printStackTrace();
			} finally {
				conn.setAutoCommit(true);
			}
		} catch (SQLException e) {
			result.result = -1L;
			result.remark = "传入分分核对信息，数据库异常";
			e.printStackTrace();
		} catch (Exception e) {
			result.result = -1L;
			result.remark = "传入分分核对信息，系统异常";
			e.printStackTrace();
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public ReturnValue addMarketMoney(XYMarketMoney xymm) {
		ReturnValue result = new ReturnValue();
		try {
			if (xymm == null) {
				result.result = -1L;
				result.remark = "添加流水时，传入对象为空";
			} else if ((xymm.bankNumber != null) && (xymm.bankNumber.trim().length() > 0)) {
				Vector vec = DAO
						.getMarketMoney(" and bankID='" + xymm.bankID + "' and bankNumber='" + xymm.bankNumber + "' and bankNumber is not null");
				if ((vec != null) && (vec.size() > 0)) {
					result.result = -1L;
					result.remark = ("银行[" + xymm.bankID + "]资金划转[" + xymm.bankNumber + "]已经存在");
				}
			}
			if (result.result == 0L) {
				int num = DAO.addMarketMoney(xymm);
				result.remark = ("添加入了[" + num + "]条流水");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			result.result = -1L;
			result.remark = "添加自有金额，数据库异常";
		} catch (Exception e) {
			e.printStackTrace();
			result.result = -2L;
			result.remark = "添加自有金额，系统异常";
		}
		return result;
	}

	public ReturnValue modMarketMoney(XYMarketMoney xymm) {
		ReturnValue result = new ReturnValue();
		try {
			int num = DAO.modMarketMoney(xymm);
			result.remark = ("修改市场自有资金[" + num + "]条");
		} catch (SQLException e) {
			e.printStackTrace();
			result.result = -1L;
			result.remark = "修改自有资金表数据库异常";
		} catch (Exception e) {
			e.printStackTrace();
			result.result = -2L;
			result.remark = "修改自有资金表系统异常";
		}
		return result;
	}

	public long insertFirmTradeStatus(Vector<FirmTradeStatus> veFirmStatus, int ready) {
		long result = 0L;
		if ((ready == 1) || (ready == 2)) {
			if (veFirmStatus != null) {
				try {
					int size = veFirmStatus.size();
					for (int i = 0; i < size; i++) {
						FirmTradeStatus val = (FirmTradeStatus) veFirmStatus.get(i);
						System.out.println("客户协议状态（增量）对账文件B02：" + val.toString());

						String sql = "where BANKID='" + val.BankId + "' and trunc(COMPAREDATE)=to_date('" + val.compareDate
								+ "','yyyy-MM-dd') and BANKACC='" + val.BankAcc + "'";
						log("SQL语句：【" + sql + "】");
						Vector veVal = DAO.getFirmTradeStatusList(sql);
						if ((veVal == null) || (veVal.size() == 0)) {
							if (ready == 2) {
								val.Status = "0";
							}

							DAO.addFirmTradeStatus(val);
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
					result = -1L;
				} catch (Exception e) {
					e.printStackTrace();
					result = -1L;
				}
			} else {
				result = -1L;
			}

		}

		return result;
	}

	public long insertTradeDetailAccount(Vector<TradeDetailAccount> veDetail, int ready) {
		long result = 0L;
		if ((ready == 1) || (ready == 2)) {
			try {
				if (veDetail != null) {
					int size = veDetail.size();
					for (int i = 0; i < size; i++) {
						TradeDetailAccount val = (TradeDetailAccount) veDetail.get(i);

						String sql = "where BANKID='" + val.BankId + "' and BKSERIAL='" + val.BkSerial + "' and trunc(COMPAREDATE)=to_date('"
								+ val.compareDate + "','yyyy-MM-dd')";
						log("SQL语句：【" + sql + "】");
						Vector vec = DAO.getTradeDetailAccountList(sql);
						if ((vec == null) || (vec.size() == 0)) {
							DAO.addTradeDetailAccount(val);
						}
					}
				} else {
					result = -1L;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				result = -1L;
			} catch (Exception e) {
				e.printStackTrace();
				result = -1L;
			}

		}

		return result;
	}

	public ReturnValue preOpenAccount(CorrespondValue cv) {
		System.out.println(">>>>市场开户方法>>>>时间：" + Tool.fmtTime(new java.util.Date()) + ">>>>\ncv[" + cv.toString() + "]");
		long checkResult = chenckCorrespondValue(cv);
		ReturnValue rv = new ReturnValue();
		CorrespondValue cv2 = null;
		if (checkResult == 0L) {
			rv.actionId = getMktActionID();
			try {
				cv.isOpen = 1;
				List cvresult = null;
				if (cv.bankID.equals("005"))
					cvresult = DAO.getCorrespondList(" where bankId='" + cv.bankID.trim() + "' and firmId='" + cv.firmID.trim() + "' ");
				else {
					cvresult = DAO.getCorrespondList(" where bankId='" + cv.bankID.trim() + "' and firmId='" + cv.firmID.trim()
							+ "' and account like '%" + cv.account.trim() + "%'");
				}
				if ((cvresult == null) || (cvresult.size() <= 0)) {
					rv.result = -40001L;
				} else {
					cv2 = (CorrespondValue) cvresult.get(0);
					cv2.isOpen = 2;
					cv2.status = cv.status;
					BankAdapterRMI bankadapter = getAdapter(cv2.bankID);
					if (bankadapter == null) {
						rv.result = -920000L;
					} else {
						cv2.bankCardPassword = cv.bankCardPassword;

						rv = bankadapter.rgstAccountQuery(cv2);
						if (rv.result == 0L) {
							if (cv.bankID.equals("005")) {
								cv2.bankCardPassword = null;
								cv2.account1 = rv.remark;
							}
							rv.result = DAO.modCorrespond(cv2);
						}
					}
				}
			} catch (SQLException e) {
				rv.result = -40006L;
				e.printStackTrace();
				log("市场预签约 交易商代码与银行帐号对应SQLException," + e);
			} catch (Exception e) {
				rv.result = -40007L;
				e.printStackTrace();
				log("市场预签约 交易商代码与银行帐号对应SQLException," + e);
			}
		} else {
			rv.result = ((int) checkResult);
			rv.remark = "交易商代码与银行、帐号对应有误!";
		}
		return rv;
	}

	public ReturnValue sendCMBCQSValue(String bankID, java.util.Date date) {
		ReturnValue result = new ReturnValue();
		Connection conn = null;
		List sbusis = new ArrayList();
		List sbalas = new ArrayList();
		List spays = new ArrayList();
		try {
			conn = DAO.getConnection();
			Vector vector = DAO.getFirmBalance(bankID, date);
			double QYHZ = 0.0D;
			double FeeHZ = 0.0D;
			for (int i = 0; i < vector.size(); i++) {
				Sbusi sbusi = new Sbusi();
				sbusi.setMoney(((FirmBalance) vector.get(i)).QYChangeMoney - ((FirmBalance) vector.get(i)).FeeMoney);
				sbusi.setsCustAcct(((FirmBalance) vector.get(i)).firmID);
				sbusi.setbCustAcct(((FirmBalance) vector.get(i)).account);
				sbusi.setMoneyType("0");
				if (sbusi.getMoney() != 0.0D) {
					sbusis.add(sbusi);
				}

				Sbala sbala = new Sbala();
				sbala.setbCustAcct(((FirmBalance) vector.get(i)).account);
				sbala.setsCustAcct(((FirmBalance) vector.get(i)).firmID);
				sbala.setMoney(((FirmBalance) vector.get(i)).QYMoney);
				sbala.setMoneyType("0");
				sbalas.add(sbala);

				QYHZ += ((FirmBalance) vector.get(i)).QYChangeMoney;
				FeeHZ += ((FirmBalance) vector.get(i)).FeeMoney;
			}
			if ("20".equals(bankID)) {
				QYHZ += DAO.sumAmount(
						"where 1= 1 and bankid ='20' and isopen = 1 and trunc(opentime)=to_date('" + Tool.fmtDate(date) + "','yyyy-MM-dd') ", conn);
			}
			if (QYHZ != 0.0D) {
				Spay spay = new Spay();
				spay.setMoney(QYHZ);
				spay.setBusinCode("O");
				spay.setMoneyType("0");
				spays.add(spay);
			}
			if (FeeHZ != 0.0D) {
				Spay spay = new Spay();
				spay.setMoney(FeeHZ * -1.0D);
				spay.setBusinCode("C");
				spay.setMoneyType("0");
				spays.add(spay);
			}

			BankAdapterRMI adapter = getAdapter(bankID);
			if (adapter == null) {
				result.result = -30011L;
				log("发送民生银行清算数据，连接适配器异常，错误码：" + result.result);
				ReturnValue localReturnValue1 = result;
				return localReturnValue1;
			}

			result = adapter.sendCMBCQSValue(sbusis, sbalas, spays, null, date);
		} catch (SQLException e) {
			log("发送民生银行清算数据，数据库异常");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			log("发送民生银行清算数据，连接适配器异常");
			e.printStackTrace();
		} catch (Exception e) {
			result.result = -1L;
			result.remark = "发送民生银行清算数据，连接适配器异常";
			log("发送民生银行清算数据，连接适配器异常");
			e.printStackTrace();
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public ReturnValue marketOpenAccount(CorrespondValue val) {
		long result = -2L;
		ReturnValue returnValue = new ReturnValue();
		try {
			if (DAO.getCorrespond(val.bankID, val.firmID, val.account) == null) {
				if (!"15".equals(val.bankID)) {
					val.status = 1;
				}
				val.isOpen = 0;
				result = rgstAccount(val);
			}
		} catch (SQLException e) {
			returnValue.result = -40007L;
			returnValue.remark = "系统异常";
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			returnValue.result = -40007L;
			returnValue.remark = "系统异常";
			e.printStackTrace();
		}
		if (result == 0L) {
			if ("15".equals(val.bankID)) {
				returnValue = preOpenAccount(val);
				if (returnValue.result == 0L)
					return returnValue;
				if (returnValue.result < 0L)
					delAccountNoAdapter(val);
			} else {
				returnValue.result = 0L;
				returnValue.remark = "注册成功";
			}
		} else if (result == -40001L) {
			returnValue.result = -40001L;
			returnValue.remark = "信息不完整";
		} else if (result == -40002L) {
			returnValue.result = -40002L;
			returnValue.remark = "交易商不存在";
		} else if (result == -40003L) {
			returnValue.result = -40003L;
			returnValue.remark = "银行不存在";
		} else if (result == -40004L) {
			returnValue.result = -40004L;
			returnValue.remark = "帐号已注册";
		} else if (result == -40005L) {
			returnValue.result = -40005L;
			returnValue.remark = "银行签约失败";
		} else if (result == -40006L) {
			returnValue.result = -40006L;
			returnValue.remark = "数据库操作失败";
		} else if (result == -40007L) {
			returnValue.result = -40008L;
			returnValue.remark = "交易商密码错误";
		} else if (result == -40008L) {
			returnValue.result = -40007L;
			returnValue.remark = "系统异常";
		}
		return returnValue;
	}

	public ReturnValue addRgstCapitalValue(CorrespondValue cv, int type) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("增加签约流水");
		System.out.println("cv[" + cv.toString() + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}}");
		RgstCapitalValue rc = new RgstCapitalValue();
		rc.account = (cv.account != null ? cv.account : Tool.getConfig("DefaultAccount"));
		rc.card = cv.card;
		rc.cardType = cv.cardType;
		rc.accountName = cv.accountName;
		rc.actionID = DAO.getActionID();
		rc.firmID = cv.firmID;
		rc.bankID = cv.bankID;
		rc.type = type;
		rc.status = 2;
		if (type == 1)
			rc.note = "rgst_account";
		else
			rc.note = "del_account";

		long checkResult = chenckCorrespondValue(cv);

		ReturnValue rv = new ReturnValue();

		if (checkResult == 0L) {
			String limit = Tool.getConfig("isLimit");
			if ((type == 2) && (limit.equals("1")))
				rv = ifQuitFirm(rc.firmID, rc.bankID);
			else {
				rv.result = 0L;
			}
			if (rv.result != 0L) {
				return rv;
			}
			rv.actionId = rc.actionID;
		} else {
			rv.result = ((int) checkResult);
			rv.remark = "交易商代码与银行、帐号对应有误!";
		}
		return rv;
	}

	public Vector<RgstCapitalValue> getRgstCapitalValue(String file) {
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			Vector vector = DAO.getRgstCapitalValue(file, conn);
			return vector;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return null;
	}

	public ReturnValue modRgstCapitalValue(RgstCapitalValue rc) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("修改签约流水");
		System.out.println("rc[" + rc.toString() + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}}");
		CorrespondValue cv = new CorrespondValue();
		cv.account = Tool.getConfig("DefaultAccount");
		cv.card = rc.card;
		cv.cardType = rc.cardType;
		cv.accountName = rc.accountName;
		cv.firmID = rc.firmID;
		cv.account = rc.account;
		cv.bankID = rc.bankID;
		long checkResult = chenckCorrespondValue(cv);
		ReturnValue rv = new ReturnValue();
		if (checkResult == 0L) {
			rv.actionId = rc.actionID;
			try {
				Connection conn = null;
				conn = DAO.getConnection();

				rv.result = DAO.modRgstCapitalValue(rc.bankID, rc.firmID, rc.account, rc.compleTime, rc.status, rc.actionID, rc.type, conn);
			} catch (SQLException e) {
				rv.result = -40006L;
				e.printStackTrace();
				log("银行开户 交易商代码与银行帐号对应SQLException," + e);
			} catch (Exception e) {
				rv.result = -40007L;
				e.printStackTrace();
				log("银行开户 交易商代码与银行帐号对应SQLException," + e);
			}
		} else {
			rv.result = ((int) checkResult);
			rv.remark = "交易商代码与银行、帐号对应有误!";
		}
		return rv;
	}

	public long modMoneyCapital(long actionID, String funID, boolean funFlag) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("actionID[" + actionID + "]funFlag[" + funFlag + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}}");
		String auditInfo = "修改资金流水状态：" + actionID + ";";

		Connection conn = null;

		Timestamp curTime = new Timestamp(System.currentTimeMillis());

		CapitalValue capital = null;

		CapitalValue rate = null;

		ReturnValue rVal = null;

		long result = 0L;
		try {
			conn = DAO.getConnection();
			Vector list = DAO.getCapitalInfoList("where actionid=" + actionID, conn);
			for (int i = 0; i < list.size(); i++) {
				CapitalValue val = (CapitalValue) list.get(i);
				if ((val.type == 1) || (val.type == 0)) {
					capital = val;
					if ((capital.status == 0) || (capital.status == 1)) {
						log("本条记录已经明确" + new java.util.Date());
						result = -3L;
						long l1 = result;
						return l1;
					}
				} else if (val.type == 2) {
					rate = val;
				}
			}
			if ((capital != null) && (rate != null))
				if (funFlag) {
					if (result == 0L) {
						try {
							conn.setAutoCommit(false);
							if (capital.type == 1) {
								System.out.println("[流水信息：]" + capital.toString());
								rVal = new ReturnValue();
								rVal.result = 0L;
								rVal.actionId = capital.actionID;
								rVal.funID = funID;

								dataProcess.updateFundsFull(capital.firmID, dataProcess.getOUTSUMMARY() + "",
										(String) dataProcess.getBANKSUB().get(capital.bankID), capital.money, actionID, conn);
								dataProcess.updateFundsFull(capital.firmID, dataProcess.getFEESUMMARY() + "", null, rate.money, actionID, conn);
								dataProcess.updateFrozenFunds(capital.firmID, -1.0D * Arith.add(capital.money, rate.money), conn);

								bankFrozenFuns(capital.firmID, capital.bankID, null, -1.0D * Arith.add(capital.money, rate.money), conn);

								DAO.modCapitalInfoStatus(capital.iD, rVal.funID, 0, curTime, conn);
								DAO.modCapitalInfoStatus(rate.iD, rVal.funID, 0, curTime, conn);

								DAO.modCapitalInfoNote(capital.iD, "[" + capital.note + "]市场处理通过，银行处理成功，出金成功", conn);
								DAO.modCapitalInfoNote(rate.iD, "[" + capital.note + "]市场处理通过，银行处理成功，扣除手续费成功", conn);

								log(auditInfo + "市场处理通过，银行处理成功,出金成功,扣除手续费成功,银行流水号=" + rVal.funID);
							} else if (capital.type == 0) {
								rVal = new ReturnValue();
								rVal.result = 0L;
								rVal.actionId = capital.actionID;
								rVal.funID = funID;

								dataProcess.updateFundsFull(capital.firmID, dataProcess.getINSUMMARY() + "",
										(String) dataProcess.getBANKSUB().get(capital.bankID), capital.money, actionID, conn);
								dataProcess.updateFundsFull(capital.firmID, dataProcess.getFEESUMMARY() + "", null, rate.money, actionID, conn);

								DAO.modCapitalInfoStatus(capital.iD, rVal.funID, 0, curTime, conn);
								DAO.modCapitalInfoStatus(rate.iD, rVal.funID, 0, curTime, conn);
							}
							conn.commit();
						} catch (SQLException e) {
							conn.rollback();
							e.printStackTrace();
							result = -30004L;
							log(auditInfo + "市场处理出金SQLException，错误码=" + result + "  " + e);
						} finally {
							conn.setAutoCommit(true);
						}
					}
				} else
					try {
						conn.setAutoCommit(false);
						if (capital.type == 1) {
							rVal = new ReturnValue();
							rVal.result = 0L;
							rVal.actionId = capital.actionID;
							rVal.funID = funID;

							dataProcess.updateFrozenFunds(capital.firmID, -1.0D * Arith.add(capital.money, rate.money), conn);

							bankFrozenFuns(capital.firmID, capital.bankID, null, -1.0D * Arith.add(capital.money, rate.money), conn);
							DAO.modCapitalInfoStatus(capital.iD, rVal == null ? "" : rVal.funID, 1, curTime, conn);
							DAO.modCapitalInfoStatus(rate.iD, rVal == null ? "" : rVal.funID, 1, curTime, conn);

							DAO.modCapitalInfoNote(capital.iD, "[" + capital.note + "]市场处理拒绝成功", conn);
							DAO.modCapitalInfoNote(rate.iD, "[" + capital.note + "]市场处理拒绝成功", conn);

							log(auditInfo + "市场处理拒绝成功");
						} else if (capital.type == 0) {
							rVal = new ReturnValue();
							rVal.result = 0L;
							rVal.actionId = capital.actionID;
							rVal.funID = funID;
							DAO.modCapitalInfoStatus(capital.iD, rVal == null ? "" : rVal.funID, 1, curTime, conn);
							DAO.modCapitalInfoStatus(rate.iD, rVal == null ? "" : rVal.funID, 1, curTime, conn);
						}
						conn.commit();
					} catch (SQLException e) {
						conn.rollback();
						e.printStackTrace();
						result = -30004L;
						log(auditInfo + "市场处理出金SQLException，错误码=" + result + "  " + e);
					} finally {
						conn.setAutoCommit(true);
					}
		} catch (SQLException e) {
			e.printStackTrace();
			result = -30004L;
			log(auditInfo + "市场处理出金SQLException，错误码=" + result + "  " + e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			DAO.closeStatement(null, null, conn);
		}

		return result;
	}

	public long inMoneyMarketGS(String bankID, String firmID, String account, String accountPwd, double money, String remark) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{国付宝入金，由市场端调用{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("bankID[" + bankID + "]firmID[" + firmID + "]account[" + account + "]accountPwd[" + accountPwd + "]money[" + money
				+ "]remark[" + remark + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}}");

		long result = 0L;
		result = tradeDate(bankID);
		if (result != 0L) {
			return result;
		}

		Connection conn = null;

		double inRate = 0.0D;

		boolean isopen = bankAccountIsOpen(firmID, bankID, account) == 1;
		if (!isopen) {
			result = -10019L;
			log("市场发起入金，交易商未签约。");
			return result;
		}
		try {
			conn = DAO.getConnection();

			inRate = getTransRate(bankID, firmID, money, 0, 0, account, conn);

			if (((firmID == null) || (firmID.trim().equals(""))) && ((account == null) || (account.trim().equals("")))) {
				result = -10016L;
				log("市场发起入金，参数非法，错误码=" + result);
			} else if ((firmID == null) || (firmID.trim().equals(""))) {
				Vector cList = DAO.getCorrespondList("where bankid='" + bankID + "' and account='" + account + "'", conn);
				if (cList.size() > 0) {
					firmID = ((CorrespondValue) cList.get(0)).firmID;
				} else {
					result = -10011L;
					log("市场发起入金，非法银行帐号，错误码=" + result);
				}
			} else if ((account == null) || (account.trim().equals(""))) {
				Vector cList = DAO.getCorrespondList("where bankid='" + bankID + "' and firmid='" + firmID + "'", conn);
				if (cList.size() > 0) {
					account = ((CorrespondValue) cList.get(0)).account;
				} else {
					result = -10012L;
					log("市场发起入金，非法交易商代码，错误码=" + result);
				}
			} else if (DAO.getCorrespond(bankID, firmID, account, conn) == null) {
				result = -10013L;
				log("市场发起入金，交易商代码和帐号对应关系错误，错误码=" + result);
			}
			Vector cList = DAO.getCorrespondList("where bankid='" + bankID + "' and firmid='" + firmID + "' and status=0 ", conn);
			if ((cList == null) || (cList.size() != 1)) {
				result = -10013L;
			}
			if ((result == 0L) && ((inRate == -1.0D) || (inRate == -2.0D))) {
				result = -10024L;
				log("市场发起入金，计算手续费错误，错误码=" + result);
			}

			if (result == 0L) {
				result = DAO.getActionID(conn);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			result = -10014L;
			log("市场端发起入金SQLException:" + result);
		} catch (Exception e) {
			e.printStackTrace();
			result = -10015L;
			log("市场端发起入金Exception:" + result);
		} finally {
			DAO.closeStatement(null, null, conn);
		}

		return result;
	}

	public ReturnValue outMoneyGS(String bankID, double money, String firmID, String bankAccount, String funID, String operator, int express,
			int type) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{国付宝市场收益划拨{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("bankID[" + bankID + "]money[" + money + "]firmID[" + firmID + "]bankAccount[" + bankAccount + "]funID[" + funID
				+ "]operator[" + operator + "]express[" + express + "]type[" + type + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}}");

		ReturnValue rv = new ReturnValue();

		return rv;
	}

	public ReturnValue SpecifiedStorageTubeBankSure() {
		ReturnValue returnValue = new ReturnValue();
		return returnValue;
	}

	public ReturnValue CommunicationsTest(String bankID) {
		ReturnValue returnValue = new ReturnValue();
		BankAdapterRMI bankadapter = getAdapter(bankID);
		try {
			returnValue = bankadapter.CommunicatTest();
		} catch (Exception ex) {
			ex.printStackTrace();
			returnValue.result = -1L;
			returnValue.remark = "调用适配器处理失败";
		}
		return returnValue;
	}

	public ReturnValue BankYuSigning(String bankid, String cardtype, String firmid, String account) {
		ReturnValue returnValue = new ReturnValue();
		Connection conn = null;
		try {
			conn = DAO.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			returnValue.result = -1L;
			returnValue.remark = "获得数据库联接失败";
			return returnValue;
		}

		String fileter = "where bankId='" + bankid + "' and cardtype='" + cardtype + "' and firmid='" + firmid + "' and account='" + account + "'";
		Vector ves = null;
		try {
			ves = DAO.getCorrespondList(fileter, conn);
		} catch (SQLException e) {
			e.printStackTrace();
			returnValue.result = -1L;
			returnValue.remark = "获得对应关系失败";
			return returnValue;
		}

		if (ves.size() == 1) {
			CorrespondValue value = (CorrespondValue) ves.get(0);
			value.status = 2;
			try {
				DAO.modCorrespond(value);
			} catch (SQLException e) {
				e.printStackTrace();
				returnValue.result = -1L;
				returnValue.remark = "修改状态失败";
				return returnValue;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				returnValue.result = -1L;
				returnValue.remark = "修改状态失败";
				return returnValue;
			}
			returnValue.result = 0L;
		}
		return returnValue;
	}

	public Hashtable<String, List> getZHQSValue(String bankID, java.util.Date tradeDate) {
		Connection conn = null;
		Hashtable valueTmp = null;
		try {
			conn = DAO.getConnection();
			try {
				valueTmp = new Hashtable();
				conn.setAutoCommit(false);

				List tatd = getZZJYMX(bankID, null, tradeDate, conn);
				valueTmp.put("S01", tatd);

				List asr = getKHZHZT(bankID, null, tradeDate, conn);
				valueTmp.put("S02", asr);

				List smsl = getCGKHZJJSMX(bankID, null, tradeDate, conn);
				valueTmp.put("S06", smsl);

				List smlbl = getCGKHZJTZYEMX(bankID, null, tradeDate, conn);
				valueTmp.put("S07", smlbl);

				conn.commit();
			} catch (Exception e) {
				log("查询数据库数据异常");
				e.printStackTrace();
			} finally {
				conn.setAutoCommit(true);
				DAO.closeStatement(null, null, conn);
			}
		} catch (Exception e) {
			log("获取数据库连接异常");
			e.printStackTrace();
		}
		return valueTmp;
	}

	public List<StorageMoneyLedgerBalanceList> getCGKHZJTZYEMX(String bankID, String[] firmIDs, java.util.Date tradeDate, Connection conn)
			throws Exception {
		List result = null;
		try {
			conn.setAutoCommit(false);

			result = DAO.getCGKHZJTZYEMX(bankID, tradeDate, conn);
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
		if (result == null) {
			result = new ArrayList();
		}
		return result;
	}

	public long insertClientStates(Vector<ClientState> states, int ready) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("提供给适配器的保存银行数据的方法");
		System.out.println("mv.size()[" + states.size() + "]ready[" + ready + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}}");
		long result = 0L;
		if ((ready == 1) || (ready == 2)) {
			if ((states != null) && (states.size() != 0)) {
				for (ClientState state : states) {
					Vector v_state = null;
					try {
						v_state = DAO.getClientState(
								" where bankid='" + state.getBankNo().trim() + "' and firmid='" + state.getTransFundAcc().trim() + "'");
					} catch (SQLException e) {
						e.printStackTrace();
						log("查询交易商账户状态信息异常");
						return -1L;
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
						log("查询交易商账户状态信息异常");
						return -2L;
					}

					if ((v_state == null) || (v_state.size() == 0)) {
						try {
							DAO.addClientState(state);
						} catch (SQLException e) {
							e.printStackTrace();
							log("保存客户账户账户对账状态信息，写入数据库异常");
							return -1L;
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
							log("保存客户账户账户对账状态信息，写入数据库异常");
							return -2L;
						}
					}
				}

			} else {
				result = -1L;
			}

		}

		return result;
	}

	public ReturnValue sendZHQS(String bankID, String[] firmIDs, java.util.Date tradeDate) {
		ReturnValue result = new ReturnValue();
		Connection conn = null;
		System.out.println("中行清算业务");
		try {
			result = isTradeDate(tradeDate);
			if (result.result != 0L)
				return result;
			if ((Tool.fmtDate(new java.util.Date()).equalsIgnoreCase(Tool.fmtDate(tradeDate))) && (!getTraderStatus())) {
				result.result = -2L;
				result.remark = "系统尚未结算，不允许发送清算数据";
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log(e.getMessage());
			result.result = -1L;
			result.remark = "取得是否是交易日期异常";
			return result;
		}
		BankAdapterRMI bankAdapter = getAdapter(bankID);
		if (bankAdapter == null) {
			result.result = -920000L;
			result.remark = ("发送[" + bankID + "]银行交易商清算信息，连接适配器失败");
		} else {
			try {
				conn = DAO.getConnection();
				List tatd = null;
				List asr = null;
				List smsl = null;
				List smlbl = null;
				try {
					tatd = getZZJYMX(bankID, firmIDs, tradeDate, conn);

					asr = getKHZHZT(bankID, firmIDs, tradeDate, conn);

					if ("qy".equals(Tool.getConfig("QSMode_ZH"))) {
						smsl = new ArrayList();

						smlbl = new ArrayList();

						Vector<FirmBalance> ve = new Vector();

						tradeDate = Tool.strToDate(Tool.fmtDate(tradeDate));

						ve = DAO.getFirmBalance(bankID, tradeDate);

						for (FirmBalance fb : ve) {
							StorageMoneySettlementList sms = new StorageMoneySettlementList();
							StorageMoneyLedgerBalanceList smb = new StorageMoneyLedgerBalanceList();

							String firmID = fb.firmID;
							String accountName = fb.accountName;
							double qyMoney = fb.QYMoney;
							double lastMoney = fb.yesQYMoney;
							double qyChange = fb.QYChangeMoney - fb.FeeMoney;
							String falg = "0";
							if (qyChange < 0.0D) {
								falg = "1";
							}
							sms.setTransDateTime(Tool.fmtDate(new java.util.Date()));
							sms.setTaiZhangZhangHao("333");
							sms.setBondAcc(firmID);
							sms.setCertificationName(accountName);
							sms.setTradeDifference(falg);
							sms.setMoneyType("001");
							sms.setCashExCode("0");
							sms.setMoney(qyChange);
							if (qyChange != 0.0D) {
								smsl.add(sms);
							}

							smb.setTransDateTime(Tool.fmtDate(new java.util.Date()));
							smb.setBondAcc(firmID);
							smb.setCertificationName(accountName);
							smb.setMoneyType("001");
							smb.setCashExCode("0");
							smb.setMoney(qyMoney);
							smb.setLastMoney(lastMoney);
							smlbl.add(smb);
						}

					} else {
						smsl = getCGKHZJJSMX(bankID, firmIDs, tradeDate, conn);

						smlbl = getCGKHZJTZYEMX(bankID, firmIDs, tradeDate, conn);
					}
				} catch (Exception e) {
					result.result = -1L;
					result.remark = "查询数据库数据异常";
					e.printStackTrace();
					ReturnValue localReturnValue1 = result;
					return localReturnValue1;
				} finally {
					DAO.closeStatement(null, null, conn);
				}
				System.out.println("中行清算业务-查询清算文件数据信息完毕");

				System.out.println("调用适配器发送清算文件");
				result = bankAdapter.sendZHQS(tatd, asr, smsl, smlbl, tradeDate);
				System.out.println("中行清算文件发送完毕");
			} catch (SQLException e) {
				result.result = -1L;
				result.remark = "获取数据库连接异常";
				e.printStackTrace();
			} catch (Exception e) {
				result.result = -1L;
				result.remark = ("发送[" + bankID + "]银行交易商清算信息，适配器抛出异常");
				e.printStackTrace();
			}
		}
		return result;
	}

	public List<TransferAccountsTransactionDetailed> getZZJYMX(String bankID, String[] firmIDs, java.util.Date tradeDate, Connection conn)
			throws Exception {
		List result = null;
		try {
			conn.setAutoCommit(false);
			result = DAO.getZZJYMX(bankID, firmIDs, tradeDate, conn);
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
		if (result == null) {
			result = new ArrayList();
		}
		return result;
	}

	public List<AccountStatusReconciliation> getKHZHZT(String bankID, String[] firmIDs, java.util.Date tradeDate, Connection conn) throws Exception {
		List result = null;
		try {
			conn.setAutoCommit(false);
			result = DAO.getKHZHZT(bankID, firmIDs, tradeDate, conn);
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
		if (result == null) {
			result = new ArrayList();
		}
		return result;
	}

	public List<StorageMoneySettlementList> getCGKHZJJSMX(String bankID, String[] firmIDs, java.util.Date tradeDate, Connection conn)
			throws Exception {
		List result = null;
		try {
			conn.setAutoCommit(false);
			result = DAO.getCGKHZJJSMX(bankID, tradeDate, conn);
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
		if (result == null) {
			result = new ArrayList();
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

	public long bankTransfer(long id, int optFlag) {
		long result = 0L;
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			try {
				conn.setAutoCommit(false);
				Vector bankTransferList = DAO.getBankTransferList(" and bt.id = " + id + " and bt.status = 2", conn);
				if ((bankTransferList != null) && (bankTransferList.size() > 0)) {
					if (bankTransferList.size() == 1) {
						if (optFlag == 1) {
							DAO.modBankTransfer(id, 4, conn);
						} else {
							BankTransferValue bankTransferValue = (BankTransferValue) bankTransferList.get(0);
							BankAdapterRMI bankadapter = getAdapter(bankTransferValue.payBankId);
							if (bankadapter != null) {
								if ((bankTransferValue.type == 6) && (bankTransferValue.payBankId.equals("10"))) {
									bankTransferValue = addBankAccount(bankTransferValue);
								}

								ReturnValue returnValue = bankadapter.bankTransfer(bankTransferValue);
								if (returnValue != null) {
									if (returnValue.result == 0L) {
										DAO.modBankTransfer(id, 0, conn);

										if ("true".equals(getConfig("TransferUpdateMoney"))) {
											dataProcess.updateMarketMoney(bankTransferValue.info, bankTransferValue.money, conn);
										}
									} else if (returnValue.result == 5L) {
										DAO.modBankTransfer(id, 3, conn);
									} else if (returnValue.result == 6L) {
										DAO.modBankTransfer(id, 4, conn);
									} else {
										result = returnValue.result;
										DAO.modBankTransfer(id, 1, conn);
									}
								}
							} else {
								result = -920000L;
								log("获取适配器失败");
							}
						}
					} else {
						result = -1L;
						log("记录流水重复");
					}
				} else {
					result = -1L;
					log("没有取到记录信息");
				}
				conn.commit();
			} catch (SQLException e) {
				conn.rollback();
				result = -990001L;
				log("数据库异常");
				e.printStackTrace();
			} catch (Exception e) {
				conn.rollback();
				result = -990001L;
				log("系统异常");
				e.printStackTrace();
			} finally {
				conn.setAutoCommit(true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			result = -990001L;
			log("数据库异常");
		} catch (Exception e) {
			e.printStackTrace();
			result = -990001L;
			log("数据库异常");
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public ReturnValue BankTransferResultNotice(long actionId, int optRst) {
		System.out.println("银行间资金划转结果通知-->");
		ReturnValue returnValue = new ReturnValue();

		System.out.println("取得数据库连接-->");
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			try {
				conn.setAutoCommit(false);
				Vector bankTransferList = DAO.getBankTransferList(" and bt.actionId = " + actionId + " and bt.status = 3", conn);
				if ((bankTransferList != null) && (bankTransferList.size() > 0)) {
					if (bankTransferList.size() == 1) {
						BankTransferValue bankTransferValue = (BankTransferValue) bankTransferList.get(0);
						if (optRst == 0) {
							returnValue.result = DAO.modBankTransfer(bankTransferValue.id, 0, conn);
							returnValue.remark = "审核通过，修改流水状态成功";
							if ("true".equals(getConfig("TransferUpdateMoney"))) {
								dataProcess.updateMarketMoney(bankTransferValue.info, bankTransferValue.money, conn);
							}

						} else if (returnValue.result == 0L) {
							returnValue.result = DAO.modBankTransfer(bankTransferValue.id, 1, conn);
							returnValue.remark = "审核拒绝，修改流水状态为失败";
						}
					} else {
						returnValue.result = -1L;
						returnValue.remark = "查询到的流水条数重复";
					}
				} else {
					returnValue.result = -1L;
					returnValue.remark = "查询到相应状态的流水";
				}
				conn.commit();
			} catch (SQLException e) {
				conn.rollback();
				returnValue.result = -990001L;
				returnValue.remark = "修改流水时数据库异常";
				e.printStackTrace();
			} catch (Exception e) {
				conn.rollback();
				returnValue.result = -990001L;
				returnValue.remark = "修改流水时系统异常";
				e.printStackTrace();
			} finally {
				conn.setAutoCommit(true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			returnValue.result = -990001L;
			returnValue.remark = "修改流水时数据库异常";
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			returnValue.result = -990001L;
			returnValue.remark = "修改流水时系统异常";
			e.printStackTrace();
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return returnValue;
	}

	public ReturnValue marketTransfer(BankTransferValue bankTransferValue) {
		ReturnValue returnValue = new ReturnValue();
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			try {
				returnValue = marketTransfer(bankTransferValue, conn);
			} catch (SQLException localSQLException1) {
			} catch (Exception localException1) {
			}
		} catch (SQLException e) {
			e.printStackTrace();
			returnValue.result = -990001L;
			returnValue.remark = "数据库异常";
		} catch (Exception e) {
			e.printStackTrace();
			returnValue.result = -990001L;
			returnValue.remark = "系统异常";
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return returnValue;
	}

	public ReturnValue marketTransfer(BankTransferValue bankTransferValue, Connection conn) throws SQLException, Exception {
		System.out.println("--------->市场出入金");
		ReturnValue returnValue = new ReturnValue();

		Vector bankTransferList = null;
		bankTransferList = DAO.getBankTransferList(" and bt.funId = " + bankTransferValue.funId, conn);
		if ((bankTransferList != null) && (bankTransferList.size() > 0)) {
			returnValue.result = -1L;
			returnValue.remark = "查询银行流水号已存在";
		} else {
			returnValue.actionId = DAO.addBankTransfer(bankTransferValue, conn);
			returnValue.remark = "出入金成功";
		}
		System.out.println(returnValue.toString());
		return returnValue;
	}

	public BankTransferValue addBankAccount(BankTransferValue bankTransferValue) throws SQLException, ClassNotFoundException {
		log("跨行转账收款银行ID：" + bankTransferValue.recBankId);
		TransferBank bankAccount = DAO.getTransferBank(bankTransferValue.recBankId);
		log("跨行转账收款银行账户：" + bankAccount.Id);
		System.out.println("跨行转账收款银行账户：" + bankAccount.Id);
		bankTransferValue.account = bankAccount.Id;
		bankTransferValue.Name = bankAccount.Name;
		bankTransferValue.VldDt = bankAccount.VldDt;
		bankTransferValue.Pwd = bankAccount.Pwd;
		bankTransferValue.RegDt = bankAccount.RegDt;
		bankTransferValue.St = bankAccount.St;
		bankTransferValue.accountType = bankAccount.accountType;
		bankTransferValue.flow = bankAccount.OpFlg;
		bankTransferValue.bankNum = bankAccount.bankNum;
		return bankTransferValue;
	}

	public CEB_RSP CEB(CEB_param param, String id) {
		System.out.println("CEB_RSP开始");
		BankAdapterRMI adapter = getAdapter("21");
		CEB_RSP rsp = new CEB_RSP();
		Connection conn = null;
		if (adapter == null) {
			log("错误码：");
			return new CEB_RSP();
		}

		System.out.println("id=" + id);
		try {
			conn = DAO.getConnection();
			CEB_RSP localCEB_RSP1;
			if ("CEB_F623".equals(id)) {
				rsp = adapter.CEB(param, id);
				System.out.println("rsp.flag=" + rsp.flag);
				System.out.println("param.transSerialNum=" + param.transSerialNum);
				if (("0".equals(rsp.flag)) && ("21".equals(param.type))) {
					ChargeAgainstValue cav = new ChargeAgainstValue();
					cav.funID = param.transSerialNum;
					cav.bankID = "21";
					chargeAgainstMaket_ceb(cav);
				} else if (("0".equals(rsp.flag)) && ("20".equals(param.type))) {
					DAO.modCapitalInfoStatus_ceb_f623(param.transSerialNum, -1, new Timestamp(System.currentTimeMillis()), conn);
				}

				return rsp;
			}
			if ("CEB_F610".equals(id)) {
				Vector cList = DAO.getCorrespondList("where bankid='21' and firmid='" + param.firmId + "'", conn);
				if (cList.size() > 0) {
					param.memNum = ((CorrespondValue) cList.get(0)).account1;
					param.sourceAccount = ((CorrespondValue) cList.get(0)).account;
					System.out.println("memNum=" + param.memNum);
				}

				rsp = adapter.CEB(param, id);
				if (("2".equals(param.depositType)) && ("0".equals(rsp.flag))) {
					CapitalValue cVal = new CapitalValue();
					cVal.actionID = getMktActionID();
					cVal.funID = param.fcsSerialNum;
					cVal.bankTime = new Timestamp(System.currentTimeMillis());
					cVal.bankID = "21";
					cVal.creditID = "Market";
					cVal.debitID = "MarketHang";
					cVal.firmID = "ceb999";
					cVal.money = Double.parseDouble(param.amount);
					cVal.note = "CEB01";
					cVal.oprcode = "CEB01";
					cVal.status = 0;
					cVal.type = 10;

					DAO.addCapitalInfo(cVal, conn);
				}

				return rsp;
			}
			if ("CEB_F612".equals(id)) {
				CorrespondValue source = null;
				CorrespondValue target = null;
				if (("2".equals(param.flag)) || ("4".equals(param.flag))) {
					Vector MarketAcountList01 = DAO.getMarketAcount(" and type = '01' ");
					source = (CorrespondValue) MarketAcountList01.get(0);
					Vector MarketAcountList02 = DAO.getMarketAcount(" and type = '02' ");
					target = (CorrespondValue) MarketAcountList02.get(0);
					param.isCrossLine = "01";
				} else if ("9".equals(param.flag)) {
					Vector MarketAcountList01 = DAO.getMarketAcount(" and type = '02' ");
					source = (CorrespondValue) MarketAcountList01.get(0);
					Vector MarketAcountList02 = DAO.getMarketAcount(" and type = '01' ");
					target = (CorrespondValue) MarketAcountList02.get(0);
					param.isCrossLine = "01";
				} else if ("91".equals(param.flag)) {
					Vector MarketAcountList01 = DAO.getMarketAcount(" and type = '01' ");
					source = (CorrespondValue) MarketAcountList01.get(0);
					Vector MarketAcountList02 = DAO.getMarketAcount(" and type = '02' ");
					target = (CorrespondValue) MarketAcountList02.get(0);
					param.isCrossLine = "01";
					param.flag = "9";
					param.targetAccountBankNum = target.accountBankNum;
				} else if ("92".equals(param.flag)) {
					Vector MarketAcountList02 = DAO.getMarketAcount(" and (type = '03' or account = '" + param.targetAccount + "') ");
					target = (CorrespondValue) MarketAcountList02.get(0);
					Vector MarketAcountList01 = DAO.getMarketAcount(" and type = '01' ");
					source = (CorrespondValue) MarketAcountList01.get(0);
					param.isCrossLine = "02";
					param.flag = "9";
					param.targetAccountBankNum = target.accountBankNum;
				}

				param.targetAccountBank = target.accountBank;
				param.targetAccountBankName = target.bankName;
				param.targetAccountName = target.accountName;
				param.targetAccount = target.account;
				param.sourceAccountName = source.accountName;
				param.sourceAccount = source.account;
				System.out.println("adapter.CEB 612");
				rsp = adapter.CEB(param, id);

				if ("0".equals(rsp.flag)) {
					param.bankId = "21";
					param.flag = rsp.req_body_F612.flag;
					param.busiSerialNum = rsp.req_body_F612.busiSerialNum;
					param.amount = rsp.req_body_F612.amount;
					param.tradeTime = rsp.req_body_F612.tradeTime;
					param.atOnce = rsp.req_body_F612.atOnce;
					param.isCrossLine = rsp.req_body_F612.isCrossLine;
					param.targetAccountBankNum = rsp.req_body_F612.targetAccountBankNum;
					param.targetAccountBank = rsp.req_body_F612.targetAccountBank;
					param.targetAccountBankName = rsp.req_body_F612.targetAccountBankName;
					param.targetAccountName = rsp.req_body_F612.targetAccountName;
					param.targetAccount = rsp.req_body_F612.targetAccount;
					param.sourceAccountName = rsp.req_body_F612.sourceAccountName;
					param.sourceAccount = rsp.req_body_F612.sourceAccount;
					param.fcsSerialNum = rsp.rsp_F612.body.fcsSerialNum;
					param.resultCode = rsp.rsp_F612.body.resultCode;

					DAO.addTransfer(param);
				}

				return rsp;
			}
			if ("CEB_F619".equals(id)) {
				ReturnValue result = sendFile_CEB("21", Tool.strToDate(param.tradeTime));

				rsp.flag = result.result + "";
				rsp.errorInfo = result.remark;
				return rsp;
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		DAO.closeStatement(null, null, conn);
		try {
			System.out.println("return adapter.CEB");
			return adapter.CEB(param, id);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return new CEB_RSP();
	}

	public ReturnValue sendFile_CEB(String bankID, java.util.Date date) {
		ReturnValue result = new ReturnValue();
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			Vector vector = DAO.getFirmBalance_CEB(bankID, date);

			BankAdapterRMI adapter = getAdapter(bankID);
			if (adapter == null) {
				result.result = -30011L;
				log("发送光大银行清算数据，连接适配器异常，错误码：" + result.result);
				ReturnValue localReturnValue1 = result;
				return localReturnValue1;
			}

			result = adapter.sendFile_CEB(vector);
		} catch (SQLException e) {
			log("发送光大银行清算数据，数据库异常");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			log("发送光大银行清算数据，连接适配器异常");
			e.printStackTrace();
		} catch (Exception e) {
			result.result = -1L;
			result.remark = "发送光大银行清算数据，连接适配器异常";
			log("发送光大银行清算数据，连接适配器异常");
			e.printStackTrace();
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public String insertFcs10(Vector<FCS_10_Result> fcs10_v) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("insertFcsInfo");

		System.out.println("}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}}");
		String result = "0";
		int k = 0;

		if (fcs10_v != null) {
			try {
				for (int i = 0; i < fcs10_v.size(); i++) {
					FCS_10_Result fcs10 = (FCS_10_Result) fcs10_v.get(i);
					k = DAO.addFCS_10(fcs10);
					if (k < 0) {
						result = "-1";
					}
				}
			} catch (SQLException e) {
				result = "-1";
				e.printStackTrace();
			} catch (Exception e) {
				result = "-1";
				e.printStackTrace();
			}

		} else {
			result = "-1";
		}

		System.out.println("===保存银行清算结果数据FCS_10------处理结果result---：" + result);
		return result;
	}

	public String insertFcs11(Vector<FCS_11_Result> fcs11_v) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("insertFcsInfo");

		System.out.println("}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}}");
		String result = "0";
		int k = 0;
		if (fcs11_v != null) {
			try {
				for (int i = 0; i < fcs11_v.size(); i++) {
					FCS_11_Result fcs11 = (FCS_11_Result) fcs11_v.get(i);
					k = DAO.addFCS_11(fcs11);
					if (k < 0) {
						result = "-1";
					}
				}
			} catch (SQLException e) {
				result = "-1";
				e.printStackTrace();
			} catch (Exception e) {
				result = "-1";
				e.printStackTrace();
			}

		} else {
			result = "-1";
		}

		System.out.println("===保存银行清算结果数据FCS_11------处理结果result---：" + result);
		return result;
	}

	public String insertFcs13(Vector<FCS_13_Result> fcs13_v) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("insertFcsInfo");

		System.out.println("}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}}");
		String result = "0";
		int k = 0;

		if (fcs13_v != null) {
			try {
				for (int i = 0; i < fcs13_v.size(); i++) {
					FCS_13_Result fcs13 = (FCS_13_Result) fcs13_v.get(i);
					k = DAO.addFCS_13(fcs13);
					if (k < 0) {
						result = "-1";
					}
				}
			} catch (SQLException e) {
				result = "-1";
				e.printStackTrace();
			} catch (Exception e) {
				result = "-1";
				e.printStackTrace();
			}

		} else {
			result = "-1";
		}

		System.out.println("===保存银行清算结果数据FCS_13------处理结果result---：" + result);
		return result;
	}

	public String insertFcs99(Vector<FCS_99> fcs99_v) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("insertFcsInfo");

		System.out.println("}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}}");
		String result = "0";
		int k = 0;

		if (fcs99_v != null) {
			try {
				for (int i = 0; i < fcs99_v.size(); i++) {
					FCS_99 fcs99 = (FCS_99) fcs99_v.get(i);
					k = DAO.addFCS_99(fcs99);
					if (k < 0) {
						result = "-1";
					}
				}
			} catch (SQLException e) {
				result = "-1";
				e.printStackTrace();
			} catch (Exception e) {
				result = "-1";
				e.printStackTrace();
			}

		} else {
			result = "-1";
		}

		System.out.println("===保存银行清算结果数据FCS_99------处理结果result---：" + result);
		return result;
	}

	public String insertFcs(Vector<FCS_10_Result> fcs10_v, Vector<FCS_11_Result> fcs11_v, Vector<FCS_13_Result> fcs13_v, Vector<FCS_99> fcs99_v) {
		String result = "0";
		String result_temp = "0";
		result_temp = insertFcs10(fcs10_v);
		if (!result_temp.equals("0")) {
			result = "-1";
		}
		result_temp = insertFcs11(fcs11_v);
		if (!result_temp.equals("0")) {
			result = "-1";
		}
		result_temp = insertFcs13(fcs13_v);
		if (!result_temp.equals("0")) {
			result = "-1";
		}
		result_temp = insertFcs99(fcs99_v);
		if (!result_temp.equals("0")) {
			result = "-1";
		}
		return result;
	}

	public ReturnValue chargeAgainstMaket_ceb(ChargeAgainstValue cav) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{冲证(市场方调用){{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("cav[" + cav.toString() + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}");
		Connection conn = null;
		String filter = null;
		ReturnValue rv = new ReturnValue();
		rv.result = 0L;
		if (cav == null) {
			rv.result = -500001L;
			rv.remark = "传入的参数为空";
		}

		Map firmMsg = new HashMap();
		try {
			conn = DAO.getConnection();

			filter = " where bankID='" + cav.bankID + "' and status=0 and (type=0 or type=1 or type=2) ";
			if (cav.actionID > 0L) {
				filter = filter + " and actionID=" + cav.actionID;
			} else if ((cav.funID != null) && (cav.funID.trim().length() > 0)) {
				filter = filter + " and funID=" + cav.funID.trim();
			} else {
				rv.result = -500003L;
				rv.remark = "冲正流水时市场流水号和银行流水号至少选其一";
			}
			if (rv.result == 0L) {
				Vector capList = DAO.getCapitalInfoList(filter, conn);
				if ((capList != null) && (capList.size() > 0)) {
					conn.setAutoCommit(false);
					FirmBalanceValue fbv = null;
					CapitalValue cv = new CapitalValue();
					cv.actionID = DAO.getActionID(conn);
					cv.money = 0.0D;
					cv.bankID = cav.bankID;
					cv.note = "maket_charge冲正，记录号：";

					for (int i = 0; i < capList.size(); i++) {
						CapitalValue capVal = (CapitalValue) capList.get(i);
						fbv = null;
						if (firmMsg.get(capVal.firmID) != null) {
							fbv = (FirmBalanceValue) firmMsg.get(capVal.firmID);
						} else {
							String filter2 = " where firmid='" + capVal.firmID + "' for update ";
							fbv = DAO.availableBalance(filter2, conn);
							if (fbv != null) {
								if ("true".equalsIgnoreCase(Tool.getConfig("fuYing"))) {
									try {
										BankDAO DAO = BankDAOFactory.getDAO();
										Vector<FirmBalanceValue> floatingloss = DAO.getFlote(new String[] { capVal.firmID });
										if ((floatingloss != null) && (floatingloss.size() > 0)) {
											for (FirmBalanceValue tp : floatingloss) {
												if ((tp != null) && (capVal.firmID.equals(tp.firmId))) {
													fbv.floatingloss = tp.floatingloss;
													if (tp.floatingloss <= 0.0D)
														break;
													fbv.avilableBalance -= tp.floatingloss;

													break;
												}
											}
										}
									} catch (SQLException e) {
										throw e;
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
								firmMsg.put(capVal.firmID, fbv);
							} else {
								rv.result = -500004L;
								rv.remark = ("没有查询到交易商" + capVal.firmID);
								throw new Exception("没有查询到当个交易商" + capVal.firmID);
							}
						}
						if (capVal.type == 0) {
							cv.firmID = capVal.firmID;
							cv.money += -1.0D * capVal.money;
							cv.debitID = capVal.creditID;
							cv.creditID = capVal.debitID;
							cv.note = (cv.note + capVal.iD + " ");
							cv.type = 4;
							capVal.status = -1;
							cv.oprcode = dataProcess.getINSUMMARY() + "";
						} else if (capVal.type == 1) {
							cv.firmID = capVal.firmID;
							cv.money += capVal.money;
							cv.debitID = capVal.creditID;
							cv.creditID = capVal.debitID;
							cv.note = (cv.note + capVal.iD + " ");
							cv.type = 5;
							capVal.status = -1;
							cv.oprcode = dataProcess.getOUTSUMMARY() + "";
						} else if (capVal.type == 2) {
							cv.money += capVal.money;
							cv.note = (cv.note + capVal.iD + " ");
							capVal.status = -1;
						}
						if ("21".equals(cav.bankID)) {
							DAO.modCapitalInfoStatus_ceb(capVal.iD, capVal.funID, capVal.status, 4, capVal.bankTime, conn);
						} else {
							DAO.modCapitalInfoStatus(capVal.iD, capVal.funID, capVal.status, capVal.bankTime, conn);
						}
					}

					if ((fbv == null) || (cv.money + fbv.avilableBalance < 0.0D)) {
						rv.result = -500004L;
						rv.remark = "没有查询到交易商，或本交易商当前资金不足以冲正";
						throw new Exception("在交易系统的交易商资金表查询不到本交易商");
					}

					ReturnValue rvt = null;
					if ((rvt == null) || (rvt.result == 5L) || (rvt.result == -50010L)) {
						cv.status = -1;
						DAO.addCapitalInfo(cv, conn);
						if ((dataProcess.getINSUMMARY() + "").equalsIgnoreCase(cv.oprcode)) {
							dataProcess.updateFundsFull(cv.firmID, cv.oprcode, (String) dataProcess.getBANKSUB().get(cv.bankID),
									(dataProcess.getOUTSUMMARY() + "").equalsIgnoreCase(cv.oprcode) ? -cv.money : cv.money, cv.actionID, conn);
						}
					} else if (rvt.result == 0L) {
						cv.status = 0;
						DAO.addCapitalInfo(cv, conn);
						dataProcess.updateFundsFull(cv.firmID, cv.oprcode, (String) dataProcess.getBANKSUB().get(cv.bankID),
								(dataProcess.getOUTSUMMARY() + "").equalsIgnoreCase(cv.oprcode) ? -cv.money : cv.money, cv.actionID, conn);
					} else if (rvt.result < 0L) {
						cv.status = 1;
						DAO.addCapitalInfo(cv, conn);
					}
					conn.commit();
				} else {
					rv.result = -50007L;
					rv.remark = ("查询流水不存在：市场流水号[" + cav.actionID + "]银行流水号[" + cav.funID + "]");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			if (rv.result == 0L) {
				rv.result = -500005L;
				rv.remark = "冲正操作中出现数据库异常";
			}
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (rv.result == 0L) {
				rv.result = -50009L;
				rv.remark = "冲正操作中出现系统异常";
			}
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DAO.closeStatement(null, null, conn);
		}

		return rv;
	}

	public ReturnValue modAccount(CorrespondValue cv1, CorrespondValue cv2, String bankID) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("变更帐户方法");
		System.out.println("cv1[" + cv1.toString() + "]\ncv2[" + cv2.toString() + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}}");
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
				e.printStackTrace();
				log("修改交易商代码与银行帐号对应SQLException," + e);
			} catch (Exception e) {
				rv.result = -40007L;
				e.printStackTrace();
				log("修改交易商代码与银行帐号对应SQLException," + e);
			}
		} else {
			rv.result = ((int) checkResult);
		}
		return rv;
	}

	public ReturnValue inMoneyMarketForYJF(String bankID, String firmID, String account, String accountPwd, double money, String remark) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{入金，由市场端调用{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("bankID[" + bankID + "]firmID[" + firmID + "]account[" + account + "]accountPwd[" + accountPwd + "]money[" + money
				+ "]remark[" + remark + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}}");

		ReturnValue result = new ReturnValue();

		result.result = tradeDate(bankID);
		if (result.result != 0L) {
			result.remark = "不是交易日";
			return result;
		}

		long capitalID = 0L;

		long rateID = 0L;

		TransferInfoValue payInfo = null;

		TransferInfoValue receiveInfo = null;

		Connection conn = null;

		Timestamp curTime = new Timestamp(System.currentTimeMillis());

		double inRate = 0.0D;

		boolean isopen = bankAccountIsOpen(firmID, bankID, account) == 1;
		if (!isopen) {
			result.result = -10019L;
			result.remark = "交易所未签到";
			log("市场发起入金，交易商未签约。");
			return result;
		}
		try {
			conn = DAO.getConnection();

			inRate = getTransRate(bankID, firmID, money, 0, 0, account, conn);

			if (((firmID == null) || (firmID.trim().equals(""))) && ((account == null) || (account.trim().equals("")))) {
				result.result = -10016L;
				result.remark = "市场入金，参数非法";
				log("市场发起入金，参数非法，错误码=" + result);
			} else if ((firmID == null) || (firmID.trim().equals(""))) {
				Vector cList = DAO.getCorrespondList("where bankid='" + bankID + "' and account='" + account + "'", conn);
				if (cList.size() > 0) {
					firmID = ((CorrespondValue) cList.get(0)).firmID;
				} else {
					result.result = -10011L;
					result.remark = "银行账号非法";
					log("市场发起入金，非法银行帐号，错误码=" + result);
				}
			} else if ((account == null) || (account.trim().equals(""))) {
				Vector cList = DAO.getCorrespondList("where bankid='" + bankID + "' and firmid='" + firmID + "'", conn);
				if (cList.size() > 0) {
					account = ((CorrespondValue) cList.get(0)).account;
				} else {
					result.result = -10012L;
					result.remark = "交易商代码非法";
					log("市场发起入金，非法交易商代码，错误码=" + result);
				}
			} else if (DAO.getCorrespond(bankID, firmID, account, conn) == null) {
				result.result = -10013L;
				result.remark = "交易商代码和账号对应关系错误";
				log("市场发起入金，交易商代码和帐号对应关系错误，错误码=" + result);
			}
			Vector cList = DAO.getCorrespondList("where bankid='" + bankID + "' and firmid='" + firmID + "' and status=0 ", conn);
			if ((cList == null) || (cList.size() != 1)) {
				result.result = -10013L;
				result.remark = "交易商代码和账号对应关系错误";
			}
			if ((result.result == 0L) && ((inRate == -1.0D) || (inRate == -2.0D))) {
				result.result = -10024L;
				result.remark = "计算手续费错误";
				log("市场发起入金，计算手续费错误，错误码=" + result);
			}

			if (result.result == 0L) {
				long checkTrans = 0L;
				if ((getConfig("inMoneyAudit") != null) && (getConfig("inMoneyAudit").trim().equals("true"))) {
					checkTrans = checkTrans(bankID, firmID, money, curTime, 0, conn);
				}
				if (checkTrans == 0L) {
					result.result = DAO.getActionID(conn);
					try {
						conn.setAutoCommit(false);

						CapitalValue cVal = new CapitalValue();
						cVal.actionID = result.result;
						cVal.bankID = bankID;
						cVal.creditID = firmID;
						cVal.debitID = ((String) dataProcess.getBANKSUB().get(bankID));
						cVal.firmID = firmID;
						cVal.money = money;
						cVal.note = remark;
						cVal.oprcode = dataProcess.getINSUMMARY() + "";
						cVal.status = 2;
						cVal.type = 0;

						capitalID = DAO.addCapitalInfo(cVal, conn);

						cVal.creditID = "Market";
						cVal.debitID = firmID;
						cVal.money = inRate;
						cVal.oprcode = dataProcess.getFEESUMMARY() + "";
						cVal.type = 2;

						rateID = DAO.addCapitalInfo(cVal, conn);

						conn.commit();
					} catch (SQLException e) {
						e.printStackTrace();
						conn.rollback();
						result.result = -10026L;
						result.remark = "写入资金流水异常";
						log("市场端发起入金写资金流水SQLException,数据回滚:" + e);
						throw e;
					} finally {
						conn.setAutoCommit(true);
					}

				}

				result.result = checkTrans;
			}

			if (result.result > 0L) {
				payInfo = getPayInfo(bankID, firmID, 0, conn);

				receiveInfo = getReceiveInfo(bankID, firmID, 0, conn);

				BankValue bv = DAO.getBank(bankID);
				InMoneyVO inMoneyInfo = new InMoneyVO(bankID, bv.bankName, money, firmID, payInfo, accountPwd, receiveInfo, remark, result.result);

				BankAdapterRMI bankadapter = getAdapter(bankID);
				ReturnValue rVal = null;
				System.out.println(bankadapter);
				if (bankadapter == null) {
					rVal = new ReturnValue();
					rVal.result = -920000L;
					log("处理器连接适配器[" + bankID + "]失败");
				} else {
					rVal = bankadapter.inMoneyQueryBank(inMoneyInfo);
				}

				try {
					conn.setAutoCommit(false);
					if (rVal.result == 0L) {
						result = rVal;
						log("适配器处理成功，业务流水处理成功,入金成功。");
					} else if (rVal.result == 5L) {
						result = rVal;
						DAO.modCapitalInfoStatus(capitalID, rVal.funID, 2, curTime, conn);
						DAO.modCapitalInfoStatus(rateID, rVal.funID, 2, curTime, conn);
						log("适配器处理成功，业务流水处理中。");
					} else if (rVal.result == -50010L) {
						result = rVal;
						DAO.modCapitalInfoStatus(capitalID, rVal.funID, 5, curTime, conn);
						DAO.modCapitalInfoStatus(rateID, rVal.funID, 5, curTime, conn);
						log("适配器连接银行，返回银行无应答");
					} else if (rVal.result == -920008L) {
						result = rVal;
						DAO.modCapitalInfoStatus(capitalID, rVal.funID, 6, curTime, conn);
						DAO.modCapitalInfoStatus(rateID, rVal.funID, 6, curTime, conn);
						log("适配器连接银行，返回银行返回市场代码为空");
					} else {
						result = rVal;
						DAO.modCapitalInfoStatus(capitalID, rVal.funID, 1, curTime, conn);
						DAO.modCapitalInfoStatus(rateID, rVal.funID, 1, curTime, conn);
						result = rVal;
						log("适配器提交失败:" + result);
					}
					conn.commit();
				} catch (SQLException e) {
					result.result = -10014L;
					result.remark = "数据库异常";
					e.printStackTrace();
					conn.rollback();
					log("市场端发起入金,更新资金流失状态为失败SQLException,数据回滚:" + result);
				} catch (Exception e) {
					result.result = -10015L;
					result.remark = "系统异常";
					e.printStackTrace();
					conn.rollback();
					log("市场端发起入金,系统异常Exception,数据回滚:" + result);
				} finally {
					conn.setAutoCommit(true);
				}

				if (result.result == -10014L) {
					DAO.modCapitalInfoStatus(capitalID, rVal.funID, 1, curTime, conn);
					DAO.modCapitalInfoStatus(rateID, rVal.funID, 1, curTime, conn);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			result.result = -10014L;
			result.remark = "SQL异常";
			log("市场端发起入金SQLException:" + result);
		} catch (Exception e) {
			e.printStackTrace();
			result.result = -10015L;
			result.remark = "系统异常";
			log("市场端发起入金Exception:" + result);
		} finally {
			DAO.closeStatement(null, null, conn);
		}

		return result;
	}

	public ReturnValue inMoneyQuery(long actionID) {
		ReturnValue rv = new ReturnValue();
		rv.result = -2L;

		Connection conn = null;
		try {
			conn = DAO.getConnection();
			Vector list = DAO.getCapitalInfoList("where actionid=" + actionID, conn);
			if ((list == null) || (list.size() == 0)) {
				rv.result = -1L;
				rv.remark = "没有找对对应记录";
			} else {
				CapitalValue cv = (CapitalValue) list.get(0);
				if ((cv.status == 2) || (cv.status == 5)) {
					BankAdapterRMI bankadapter = getAdapter(cv.bankID);
					if (bankadapter == null) {
						rv.result = -920000L;
						rv.remark = "处理器连接适配器失败";
						log("处理器连接适配器[" + cv.bankID + "]失败");
						return rv;
					}
					try {
						conn.setAutoCommit(false);
						Vector ll = DAO.getCapitalInfoList("where actionID=" + actionID, conn);

						CapitalValue cpv = (CapitalValue) ll.get(0);
						ReturnValue result = bankadapter.inMoneyResultQuery(actionID);
						if ((result.result == 0L) || (result.result == -1L)) {
							int funFlag = 0;
							if (result.result == -1L) {
								funFlag = 1;
							}
							long m = inMoney(cpv.bankID, cpv.firmID, cpv.account, cpv.bankTime, cpv.money, result.funID, actionID, funFlag,
									result.remark);
							if (m >= 0L) {
								rv.result = 0L;
								rv.remark = "待处理入金流水处理成功";
							} else {
								rv.result = -1L;
								rv.remark = "待处理入金流水处理失败";
							}
						} else if (result.result == 5L) {
							rv = result;
							return rv;
						}

						conn.commit();
					} catch (SQLException e) {
						ReturnValue localReturnValue1;
						conn.rollback();
						e.printStackTrace();
						rv.result = -20015L;
						rv.remark = "数据库操作异常";
						return rv;
					} finally {
						conn.setAutoCommit(true);
					}
					conn.setAutoCommit(true);
				} else if ((cv.status == 3) || (cv.status == 4)) {
					rv.result = -1L;
					rv.remark = "流水未通过市场审核";
				} else {
					rv.result = -1L;
					rv.remark = "流水状态已经确定";
				}
			}
		} catch (Exception e) {
			log("入金结果查询处理器异常");
			e.printStackTrace();
		}
		return rv;
	}

	public ReturnValue outMoneyQuery(long actionID) {
		ReturnValue rv = new ReturnValue();
		rv.result = -2L;

		Timestamp curTime = new Timestamp(System.currentTimeMillis());

		CapitalValue capital = null;

		CapitalValue rate = null;

		Connection conn = null;
		try {
			conn = DAO.getConnection();
			Vector list = DAO.getCapitalInfoList("where actionid=" + actionID, conn);
			if ((list == null) || (list.size() == 0)) {
				rv.result = -1L;
				rv.remark = "没有找对对应记录";
			} else {
				for (int i = 0; i < list.size(); i++) {
					CapitalValue val = (CapitalValue) list.get(i);
					if (val.type == 1)
						capital = val;
					else if (val.type == 2) {
						rate = val;
					}
				}
				CapitalValue cv = (CapitalValue) list.get(0);
				if ((cv.status == 2) || (cv.status == 5)) {
					BankAdapterRMI bankadapter = getAdapter(cv.bankID);
					if (bankadapter == null) {
						rv.result = -920000L;
						rv.remark = "处理器连接适配器失败";
						log("处理器连接适配器[" + cv.bankID + "]失败");
						return rv;
					}
					try {
						conn.setAutoCommit(false);

						ReturnValue result = bankadapter.outMoneyResultQuery(actionID);
						if (result.result == 0L) {
							dataProcess.updateFrozenFunds(capital.firmID, -1.0D * Arith.add(capital.money, rate.money), conn);
							dataProcess.updateFundsFull(capital.firmID, dataProcess.getOUTSUMMARY() + "",
									(String) dataProcess.getBANKSUB().get(capital.bankID), capital.money, actionID, conn);
							dataProcess.updateFundsFull(capital.firmID, dataProcess.getFEESUMMARY() + "", null, rate.money, actionID, conn);

							bankFrozenFuns(capital.firmID, capital.bankID, null, -1.0D * Arith.add(capital.money, rate.money), conn);

							DAO.modCapitalInfoStatus(capital.iD, result.funID, 0, curTime, conn);
							DAO.modCapitalInfoStatus(rate.iD, result.funID, 0, curTime, conn);

							DAO.modCapitalInfoNote(capital.iD, "[" + capital.note + "]审核通过，银行处理成功，出金成功", conn);
							DAO.modCapitalInfoNote(rate.iD, "[" + capital.note + "]审核通过，银行处理成功，扣除手续费成功", conn);
						} else if (result.result == -1L) {
							if (!"false".equalsIgnoreCase(getConfig("OutFailProcess"))) {
								dataProcess.updateFrozenFunds(capital.firmID, -1.0D * Arith.add(capital.money, rate.money), conn);

								bankFrozenFuns(capital.firmID, capital.bankID, null, -1.0D * Arith.add(capital.money, rate.money), conn);
								DAO.modCapitalInfoStatus(capital.iD, result.funID, 1, curTime, conn);
								DAO.modCapitalInfoStatus(rate.iD, result.funID, 1, curTime, conn);
								DAO.modCapitalInfoNote(capital.iD, "[" + capital.note + "]审核通过，银行处理失败，出金金额已解冻", conn);
								DAO.modCapitalInfoNote(rate.iD, "[" + capital.note + "]审核通过，银行处理失败，手续费金额已解冻", conn);
							} else {
								DAO.modCapitalInfoStatus(capital.iD, result.funID, 1, curTime, conn);
								DAO.modCapitalInfoStatus(rate.iD, result.funID, 1, curTime, conn);
								DAO.modCapitalInfoNote(capital.iD, "[" + capital.note + "]审核通过，银行处理失败，需手工解冻出金金额", conn);
								DAO.modCapitalInfoNote(rate.iD, "[" + capital.note + "]审核通过，银行处理失败，需手工解冻手续费金额", conn);
							}
						} else if (result.result == 5L) {
							rv = result;
							return rv;
						}

						conn.commit();
					} catch (SQLException e) {
						ReturnValue localReturnValue1;
						conn.rollback();
						e.printStackTrace();
						rv.result = -20015L;
						rv.remark = "数据库操作异常";
						return rv;
					} finally {
						conn.setAutoCommit(true);
					}
					conn.setAutoCommit(true);
				} else if ((cv.status == 3) || (cv.status == 4)) {
					rv.result = -1L;
					rv.remark = "流水未通过市场审核";
				} else {
					rv.result = -1L;
					rv.remark = "流水状态已经确定";
				}
			}
		} catch (Exception e) {
			log("出金结果查询处理器异常");
			e.printStackTrace();
		}
		return rv;
	}

	public ReturnValue modBankQS(BankQSVO bq) {
		ReturnValue rv = new ReturnValue();
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			try {
				conn.setAutoCommit(false);
				if (bq != null) {
					bq.createDate = new java.util.Date();
					rv.result = DAO.modBankQS(bq, conn);
				}
				rv.remark = "修改清算日期表成功";
				conn.commit();
			} catch (SQLException e) {
				conn.rollback();
				rv.result = -1L;
				rv.remark = "修改清算日期表，数据库异常";
				e.printStackTrace();
			} finally {
				conn.setAutoCommit(true);
			}
		} catch (SQLException e) {
			rv.result = -1L;
			rv.remark = "修改清算日期表，数据库异常";
			e.printStackTrace();
		} catch (Exception e) {
			rv.result = -1L;
			rv.remark = "传入清算对象，系统异常";
			e.printStackTrace();
		} finally {
			DAO.closeStatement(null, null, conn);
		}

		return rv;
	}

	public ReturnValue inMoneyMarketByEBank(String bankID, String firmID, String account, String accountPwd, double money, String remark) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{入金，由市场端调用{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("bankID[" + bankID + "]firmID[" + firmID + "]account[" + account + "]accountPwd[" + accountPwd + "]money[" + money
				+ "]remark[" + remark + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}}");
		ReturnValue returnValue = new ReturnValue();

		returnValue.result = tradeDate(bankID);
		if (returnValue.result != 0L) {
			return returnValue;
		}

		long capitalID = 0L;

		long rateID = 0L;

		TransferInfoValue payInfo = null;

		TransferInfoValue receiveInfo = null;

		Connection conn = null;

		Timestamp curTime = new Timestamp(System.currentTimeMillis());

		double inRate = 0.0D;

		boolean isopen = bankAccountIsOpen(firmID, bankID, account) == 1;
		if (!isopen) {
			returnValue.result = -10019L;
			log("市场发起入金，交易商未签约。");
			return returnValue;
		}
		try {
			conn = DAO.getConnection();

			inRate = getTransRate(bankID, firmID, money, 0, 0, account, conn);

			if (((firmID == null) || (firmID.trim().equals(""))) && ((account == null) || (account.trim().equals("")))) {
				returnValue.result = -10016L;
				log("市场发起入金，参数非法，错误码=" + returnValue.result);
			} else if ((firmID == null) || (firmID.trim().equals(""))) {
				Vector cList = DAO.getCorrespondList("where bankid='" + bankID + "' and account='" + account + "'", conn);
				if (cList.size() > 0) {
					firmID = ((CorrespondValue) cList.get(0)).firmID;
				} else {
					returnValue.result = -10011L;
					log("市场发起入金，非法银行帐号，错误码=" + returnValue.result);
				}
			} else if ((account == null) || (account.trim().equals(""))) {
				Vector cList = DAO.getCorrespondList("where bankid='" + bankID + "' and firmid='" + firmID + "'", conn);
				if (cList.size() > 0) {
					account = ((CorrespondValue) cList.get(0)).account;
				} else {
					returnValue.result = -10012L;
					log("市场发起入金，非法交易商代码，错误码=" + returnValue.result);
				}
			} else if (DAO.getCorrespond(bankID, firmID, account, conn) == null) {
				returnValue.result = -10013L;
				log("市场发起入金，交易商代码和帐号对应关系错误，错误码=" + returnValue.result);
			}
			Vector cList = DAO.getCorrespondList("where bankid='" + bankID + "' and firmid='" + firmID + "' and status=0 ", conn);
			if ((cList == null) || (cList.size() != 1)) {
				returnValue.result = -10013L;
			}
			if ((returnValue.result == 0L) && ((inRate == -1.0D) || (inRate == -2.0D))) {
				returnValue.result = -10024L;
				log("市场发起入金，计算手续费错误，错误码=" + returnValue.result);
			}

			if (returnValue.result == 0L) {
				long checkTrans = 0L;
				if ((getConfig("inMoneyAudit") != null) && (getConfig("inMoneyAudit").trim().equals("true"))) {
					checkTrans = checkTrans(bankID, firmID, money, curTime, 0, conn);
				}
				if (checkTrans == 0L) {
					returnValue.result = DAO.getActionID(conn);
					try {
						conn.setAutoCommit(false);

						CapitalValue cVal = new CapitalValue();
						cVal.actionID = returnValue.result;
						cVal.bankID = bankID;
						cVal.creditID = firmID;
						cVal.debitID = ((String) dataProcess.getBANKSUB().get(bankID));
						cVal.firmID = firmID;
						cVal.money = money;
						cVal.note = remark;
						cVal.oprcode = dataProcess.getINSUMMARY() + "";
						cVal.status = 2;
						cVal.type = 0;

						capitalID = DAO.addCapitalInfo(cVal, conn);

						cVal.creditID = "Market";
						cVal.debitID = firmID;
						cVal.money = inRate;
						cVal.oprcode = dataProcess.getFEESUMMARY() + "";
						cVal.type = 2;

						rateID = DAO.addCapitalInfo(cVal, conn);

						conn.commit();
					} catch (SQLException e) {
						e.printStackTrace();
						conn.rollback();
						returnValue.result = -10026L;
						log("市场端发起入金写资金流水SQLException,数据回滚:" + e);
						throw e;
					} finally {
						conn.setAutoCommit(true);
					}

				}

				returnValue.result = checkTrans;
			}

			if (returnValue.result > 0L) {
				payInfo = getPayInfo(bankID, firmID, 0, conn);

				receiveInfo = getReceiveInfo(bankID, firmID, 0, conn);

				BankValue bv = DAO.getBank(bankID);
				InMoneyVO inMoneyInfo = new InMoneyVO(bankID, bv.bankName, money, firmID, payInfo, accountPwd, receiveInfo, remark,
						returnValue.result);

				BankAdapterRMI bankadapter = getAdapter(bankID);

				System.out.println(bankadapter);
				if (bankadapter == null) {
					returnValue.result = -920000L;
					log("处理器连接适配器[" + bankID + "]失败");
				} else {
					returnValue = bankadapter.inMoneyQueryBankByEBank(inMoneyInfo);
				}
				try {
					log(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>适配器的处理结果：" + returnValue.result);

					conn.setAutoCommit(false);
					if (returnValue.result == 0L) {
						log("适配器处理成功，业务流水处理成功,入金成功。");
					} else if (returnValue.result == 5L) {
						DAO.modCapitalInfoStatus(capitalID, returnValue.funID, 2, curTime, conn);
						DAO.modCapitalInfoStatus(rateID, returnValue.funID, 2, curTime, conn);
						log("适配器处理成功，业务流水处理中。");
					} else if (returnValue.result == -50010L) {
						DAO.modCapitalInfoStatus(capitalID, returnValue.funID, 5, curTime, conn);
						DAO.modCapitalInfoStatus(rateID, returnValue.funID, 5, curTime, conn);
						log("适配器连接银行，返回银行无应答");
					} else if (returnValue.result == -920008L) {
						DAO.modCapitalInfoStatus(capitalID, returnValue.funID, 6, curTime, conn);
						DAO.modCapitalInfoStatus(rateID, returnValue.funID, 6, curTime, conn);
						log("适配器连接银行，返回银行返回市场代码为空");
					} else {
						DAO.modCapitalInfoStatus(capitalID, returnValue.funID, 1, curTime, conn);
						DAO.modCapitalInfoStatus(rateID, returnValue.funID, 1, curTime, conn);
						log("适配器提交失败:" + returnValue.result);
					}
					conn.commit();
				} catch (SQLException e) {
					returnValue.result = -10014L;
					e.printStackTrace();
					conn.rollback();
					log("市场端发起网银入金,更新资金流失状态为失败SQLException,数据回滚:" + returnValue.result);
				} catch (Exception e) {
					returnValue.result = -10015L;
					e.printStackTrace();
					conn.rollback();
					log("市场端发起网银入金,系统异常Exception,数据回滚:" + returnValue.result);
				} finally {
					conn.setAutoCommit(true);
				}

				if (returnValue.result == -10014L) {
					DAO.modCapitalInfoStatus(capitalID, returnValue.funID, 1, curTime, conn);
					DAO.modCapitalInfoStatus(rateID, returnValue.funID, 1, curTime, conn);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			returnValue.result = -10014L;
			log("市场端发起网银入金SQLException:" + returnValue.result);
		} catch (Exception e) {
			e.printStackTrace();
			returnValue.result = -10015L;
			log("市场端发起网银入金Exception:" + returnValue.result);
		} finally {
			DAO.closeStatement(null, null, conn);
		}

		return returnValue;
	}

	public ReturnValue delAccountMaketForYJF(CorrespondValue correspondValue) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("银行帐号注销");
		System.out.println("correspondValue[" + correspondValue.toString() + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}");

		ReturnValue result = new ReturnValue();
		result = ifQuitFirm(correspondValue.firmID, correspondValue.bankID);
		if (result.result != 0L) {
			log("不满足解约条件，" + result.remark);
			return result;
		}
		Connection conn = null;
		if ((correspondValue.bankID == null) || (correspondValue.bankID.length() == 0) || (correspondValue.firmID == null)
				|| (correspondValue.firmID.length() == 0) || (correspondValue.account == null) || (correspondValue.account.length() == 0)) {
			result.result = -40011L;
			result.remark = "处理器判断：信息不完整。操作失败";

			return result;
		}

		try {
			conn = DAO.getConnection();
			Vector vector = DAO.getCorrespondList(" where bankID='" + correspondValue.bankID + "' and firmID='" + correspondValue.firmID
					+ "' and account='" + correspondValue.account + "'", conn);
			if ((vector == null) || (vector.size() <= 0)) {
				result.result = -40014L;
				result.remark = "银行账号未绑定";
				log("银行帐号注销，帐号未注册，错误码=" + result);
			} else {
				CorrespondValue cv = (CorrespondValue) vector.get(0);
				if (cv.frozenFuns > 0.0D) {
					result.result = -920002L;
					result.remark = "银行接口冻结资金不为0";

					ReturnValue localReturnValue1 = result;
					return localReturnValue1;
				}

				if ((cv.isOpen == 1) || (cv.isOpen == 2)) {
					BankAdapterRMI bankadapter = getAdapter(correspondValue.bankID);
					ReturnValue returnValue = null;
					if (bankadapter != null) {
						returnValue = bankadapter.delAccountQuery(correspondValue);
						result = returnValue;
					}
					if ((returnValue != null) && (returnValue.result == 0L)) {
						int num = DAO.delCorrespond(correspondValue, conn);
						if (num != 0) {
							log("删除数据库中对应关系异常");
							result.result = num;
							result.remark = "删除数据库中对应关系异常";
						}
					} else {
						log(returnValue.remark);

						log("银行帐号注销，银行解约失败，错误码=" + result.result);
					}
				} else {
					log("交易商未签约或者没有预签约，不通知银行，只删除市场端对应关系");
					result.result = DAO.delCorrespond(correspondValue, conn);
					if (result.result == 0L)
						result.remark = "市场端注销成功";
					else {
						result.remark = "市场端注销失败";
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			result.result = -40016L;
			result.remark = "银行账号注销，数据库操作失败";
			log("银行帐号注销SQLException，错误码=" + result + "  " + e);
		} catch (Exception e) {
			e.printStackTrace();
			result.result = -40017L;
			result.remark = ("银行账号注销系统异常，错误码" + result.result);
			log("银行帐号注销Exception，错误码=" + result + "  " + e);
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public AbcInfoValue getAbcInfo(String firmID, long orderNo, int type) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("农行交易相关信息");
		System.out.println("firmID[" + firmID + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}");
		AbcInfoValue result = new AbcInfoValue();
		Connection conn = null;
		try {
			conn = DAO.getConnection();

			result = DAO.getAbcInfo(firmID, orderNo, type, conn);
		} catch (Exception e) {
			log("农行交易相关信息Exception:" + e);
			e.printStackTrace();
		}

		return result;
	}

	public ReturnValue insertAbcInfo(AbcInfoValue value) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("写入农行交易相关信息");

		System.out.println("}}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}");
		ReturnValue result = new ReturnValue();
		Connection conn = null;
		try {
			if (value == null) {
				result.result = -1L;
				result.remark = "传入信息为空";
			}
			if ((value.actionID < 0L) || (value.signInfo == null) || (value.signInfo.length() < 1600)) {
				result.result = -1L;
				result.remark = "签名为空";
			}
			try {
				conn = DAO.getConnection();
				conn.setAutoCommit(false);
				DAO.addAbcInfo(value, conn);
				result.result = 0L;
				conn.commit();
			} catch (SQLException e) {
				e.printStackTrace();
				result.result = -1L;
				result.remark = "写入农行交易相关信息异常";
				log("写入农行交易相关信息异常，数据回滚");
				throw e;
			} finally {
				conn.setAutoCommit(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.result = -1L;
			result.remark = "写入农行交易相关信息异常";
			log("写入农行交易相关信息异常，错误码=" + result);
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public long inMoneyMarketAbc(long actionID, String bankID, String firmID, String account, String accountPwd, double money, String remark) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{入金，由市场端调用{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("bankID[" + bankID + "]firmID[" + firmID + "]account[" + account + "]accountPwd[" + accountPwd + "]money[" + money
				+ "]remark[" + remark + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}}");

		long result = 0L;
		result = tradeDate(bankID);
		if (result != 0L) {
			return result;
		}

		long capitalID = 0L;

		long rateID = 0L;

		TransferInfoValue payInfo = null;

		TransferInfoValue receiveInfo = null;

		Connection conn = null;

		Timestamp curTime = new Timestamp(System.currentTimeMillis());

		double inRate = 0.0D;

		boolean isopen = bankAccountIsOpen(firmID, bankID, account) == 1;
		if (!isopen) {
			result = -10019L;
			log("市场发起入金，交易商未签约。");
			return result;
		}
		try {
			conn = DAO.getConnection();

			inRate = getTransRate(bankID, firmID, money, 0, 0, account, conn);

			if (((firmID == null) || (firmID.trim().equals(""))) && ((account == null) || (account.trim().equals("")))) {
				result = -10016L;
				log("市场发起入金，参数非法，错误码=" + result);
			} else if ((firmID == null) || (firmID.trim().equals(""))) {
				Vector cList = DAO.getCorrespondList("where bankid='" + bankID + "' and account='" + account + "'", conn);
				if (cList.size() > 0) {
					firmID = ((CorrespondValue) cList.get(0)).firmID;
				} else {
					result = -10011L;
					log("市场发起入金，非法银行帐号，错误码=" + result);
				}
			} else if ((account == null) || (account.trim().equals(""))) {
				Vector cList = DAO.getCorrespondList("where bankid='" + bankID + "' and firmid='" + firmID + "'", conn);
				if (cList.size() > 0) {
					account = ((CorrespondValue) cList.get(0)).account;
				} else {
					result = -10012L;
					log("市场发起入金，非法交易商代码，错误码=" + result);
				}
			} else if (DAO.getCorrespond(bankID, firmID, account, conn) == null) {
				result = -10013L;
				log("市场发起入金，交易商代码和帐号对应关系错误，错误码=" + result);
			}
			Vector cList = DAO.getCorrespondList("where bankid='" + bankID + "' and firmid='" + firmID + "' and status=0 ", conn);
			if ((cList == null) || (cList.size() != 1)) {
				result = -10013L;
			}
			if ((result == 0L) && ((inRate == -1.0D) || (inRate == -2.0D))) {
				result = -10024L;
				log("市场发起入金，计算手续费错误，错误码=" + result);
			}

			if (result == 0L) {
				long checkTrans = 0L;
				if ((getConfig("inMoneyAudit") != null) && (getConfig("inMoneyAudit").trim().equals("true"))) {
					checkTrans = checkTrans(bankID, firmID, money, curTime, 0, conn);
				}
				if (checkTrans == 0L) {
					result = actionID;
					try {
						conn.setAutoCommit(false);

						CapitalValue cVal = new CapitalValue();
						cVal.actionID = result;
						cVal.bankID = bankID;
						cVal.creditID = firmID;
						cVal.debitID = ((String) dataProcess.getBANKSUB().get(bankID));
						cVal.firmID = firmID;
						cVal.money = money;
						cVal.note = remark;
						cVal.oprcode = dataProcess.getINSUMMARY() + "";
						cVal.status = 2;
						cVal.type = 0;

						capitalID = DAO.addCapitalInfo(cVal, conn);

						cVal.creditID = "Market";
						cVal.debitID = firmID;
						cVal.money = inRate;
						cVal.oprcode = dataProcess.getFEESUMMARY() + "";
						cVal.type = 2;

						rateID = DAO.addCapitalInfo(cVal, conn);

						conn.commit();
					} catch (SQLException e) {
						e.printStackTrace();
						conn.rollback();
						result = -10026L;
						log("市场端发起入金写资金流水SQLException,数据回滚:" + e);
						throw e;
					} finally {
						conn.setAutoCommit(true);
					}

				}

				result = checkTrans;
			}

			if (result > 0L) {
				payInfo = getPayInfo(bankID, firmID, 0, conn);

				receiveInfo = getReceiveInfo(bankID, firmID, 0, conn);

				BankValue bv = DAO.getBank(bankID);
				InMoneyVO inMoneyInfo = new InMoneyVO(bankID, bv.bankName, money, firmID, payInfo, accountPwd, receiveInfo, remark, result);

				BankAdapterRMI bankadapter = getAdapter(bankID);
				ReturnValue rVal = null;
				System.out.println(bankadapter);
				if (bankadapter == null) {
					rVal = new ReturnValue();
					rVal.result = -920000L;
					log("处理器连接适配器[" + bankID + "]失败");
				} else {
					inMoneyInfo.setActionID(actionID);
					rVal = bankadapter.inMoneyQueryBank(inMoneyInfo);
				}

				try {
					conn.setAutoCommit(false);
					if (rVal.result == 0L) {
						result = rVal.result;
						log("适配器处理成功，业务流水处理成功,入金成功。");
					} else if (rVal.result == 5L) {
						result = rVal.result;
						DAO.modCapitalInfoStatus(capitalID, rVal.funID, 2, curTime, conn);
						DAO.modCapitalInfoStatus(rateID, rVal.funID, 2, curTime, conn);
						log("适配器处理成功，业务流水处理中。");
					} else if (rVal.result == -50010L) {
						result = rVal.result;
						DAO.modCapitalInfoStatus(capitalID, rVal.funID, 5, curTime, conn);
						DAO.modCapitalInfoStatus(rateID, rVal.funID, 5, curTime, conn);
						log("适配器连接银行，返回银行无应答");
					} else if (rVal.result == -920008L) {
						result = rVal.result;
						DAO.modCapitalInfoStatus(capitalID, rVal.funID, 6, curTime, conn);
						DAO.modCapitalInfoStatus(rateID, rVal.funID, 6, curTime, conn);
						log("适配器连接银行，返回银行返回市场代码为空");
					} else {
						result = rVal.result;
						DAO.modCapitalInfoStatus(capitalID, rVal.funID, 1, curTime, conn);
						DAO.modCapitalInfoStatus(rateID, rVal.funID, 1, curTime, conn);
						result = rVal.result;
						log("失败:" + result);
					}
					conn.commit();
				} catch (SQLException e) {
					result = -10014L;
					e.printStackTrace();
					conn.rollback();
					log("市场端发起入金,更新资金流失状态为失败SQLException,数据回滚:" + result);
				} catch (Exception e) {
					result = -10015L;
					e.printStackTrace();
					conn.rollback();
					log("市场端发起入金,系统异常Exception,数据回滚:" + result);
				} finally {
					conn.setAutoCommit(true);
				}

				if (result == -10014L) {
					DAO.modCapitalInfoStatus(capitalID, rVal.funID, 1, curTime, conn);
					DAO.modCapitalInfoStatus(rateID, rVal.funID, 1, curTime, conn);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			result = -10014L;
			log("市场端发起入金SQLException:" + result);
		} catch (Exception e) {
			e.printStackTrace();
			result = -10015L;
			log("市场端发起入金Exception:" + result);
		} finally {
			DAO.closeStatement(null, null, conn);
		}

		return result;
	}

	public HashMap<Integer, Long> handleOUtMoneyAbc(long result, Connection conn, long checkTrans, double outRate, String bankID, double money,
			String firmID, String funID, int express, int type) throws SQLException {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("检查交易商是否符合出金条件,符合条件进行出金处理,记录流水");
		System.out.println("result[" + result + "]checkTrans[" + checkTrans + "]outRate[" + outRate + "]bankID[" + bankID + "]money[" + money
				+ "]firmID[" + firmID + "]funID[" + funID + "]express[" + express + "]type[" + type + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}");
		HashMap map = new HashMap();
		long capitalID = 0L;
		long rateID = 0L;
		try {
			conn.setAutoCommit(false);
			if (result > 0L) {
				Vector cv = DAO.getCapitalInfoList(
						" where bankID='" + bankID + "' and trunc(createtime)=trunc(sysdate) and actionID='" + result + "'", conn);
				if ((cv != null) && (cv.size() > 0)) {
					result = -20042L;
					log("该流水号[" + result + "]已经存在");
				}
			}
			if (result >= 0L) {
				CapitalValue cVal = new CapitalValue();
				cVal.actionID = result;
				cVal.bankID = bankID;
				cVal.creditID = ((String) dataProcess.getBANKSUB().get(bankID));
				cVal.debitID = firmID;
				cVal.firmID = firmID;
				cVal.money = money;
				cVal.oprcode = dataProcess.getOUTSUMMARY() + "";
				cVal.status = 3;
				cVal.type = 1;
				cVal.express = express;
				cVal.funID = (funID == null ? "" : funID);
				if (type == 0) {
					cVal.note = "market_out";
				} else if (type == 1) {
					cVal.note = "bank_out";
				}

				capitalID = DAO.addCapitalInfo(cVal, conn);

				cVal.creditID = "Market";
				cVal.debitID = firmID;
				cVal.money = outRate;
				cVal.oprcode = dataProcess.getFEESUMMARY() + "";
				cVal.type = 2;

				rateID = DAO.addCapitalInfo(cVal, conn);
			}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			conn.rollback();
			result = -20038L;
			log("市场发起出金SQLException,数据回滚" + e);
			throw e;
		} finally {
			conn.setAutoCommit(true);
		}
		map.put(Integer.valueOf(1), Long.valueOf(result));
		map.put(Integer.valueOf(2), Long.valueOf(capitalID));
		map.put(Integer.valueOf(3), Long.valueOf(rateID));
		return map;
	}

	public int getBankCompareInfoAbc(String bankID, java.util.Date date) {
		log("获取银行对账信息 getBankCompareInfo bankID[" + bankID + "]date[" + date + "]");
		int result = 0;
		Vector moneyInfoList = new Vector();
		try {
			if ("1".equals(Tool.getConfig("ifFtp"))) {
				String basePath = Tool.getConfig("basePath");
				FtpUtil ftp = new FtpUtil();
				String filename = Tool.fmtDateNo(date) + "农行账单.xls";
				log("filename[" + filename + "]");
				String host = Tool.getConfig("ftpIp");
				String ftpName = Tool.getConfig("ftpName");
				String ftpPassword = Tool.getConfig("ftpPassword");
				String ftpPath = Tool.getConfig("ftpPath");
				try {
					ftp.connectServer(host, ftpName, ftpPassword, ftpPath);
					ftp.download(filename, basePath + filename);
				} catch (IOException e) {
					result = -30013;
					log("下载日终对账文件失败2=" + result);
					return result;
				} catch (Exception e) {
					result = -30013;
					log("下载日终对账文件失败3=" + result);
					return result;
				}
			}
			String files = Tool.getConfig("basePath") + Tool.fmtDateNo(date) + "农行账单.xls";

			log("读取files[" + files + "]");

			HSSFWorkbook wookbook = new HSSFWorkbook(new FileInputStream(files));

			HSSFSheet sheet = wookbook.getSheetAt(0);

			int rows = sheet.getPhysicalNumberOfRows();
			log("Excel表行数：" + rows);

			for (int i = 1; i < rows - 1; i++) {
				HSSFRow row = sheet.getRow(i);

				if (row != null) {
					int cells = row.getPhysicalNumberOfCells();
					String value = "";

					for (int j = 0; j < cells; j++) {
						HSSFCell cell = row.getCell((short) j);
						if (cell != null) {
							switch (cell.getCellType()) {
							case 2:
								break;
							case 0:
								value = value + cell.getNumericCellValue() + ",";
								break;
							case 1:
								value = value + cell.getStringCellValue() + ",";
								break;
							default:
								value = value + "0";
							}
						}

					}

					String[] val = value.split(",");

					Connection conn = null;
					MoneyInfoValue moneyInfoValue = new MoneyInfoValue();
					try {
						conn = DAO.getConnection();
						Vector list = DAO.getCapitalInfoList(" where bankID='" + bankID.trim() + "' and funid='" + val[1] + "' ", conn);

						for (int a = 0; a < list.size(); a++) {
							CapitalValue cv = (CapitalValue) list.get(a);
							moneyInfoValue.firmID = cv.firmID;
							Vector vcv = DAO.getCorrespondList(" where (firmID='" + cv.firmID + "' or firmID='D_'||'" + cv.firmID + "') and (bankID='"
									+ bankID.trim() + "'or bankID='D_'||'" + bankID.trim() + "')", conn);
							if ((vcv != null) && (vcv.size() >= 0)) {
								for (int b = 0; b < vcv.size(); b++) {
									CorrespondValue cp = (CorrespondValue) vcv.get(b);

									moneyInfoValue.account = (cp.account == null ? "9999999999" : cp.account);
								}
							} else {
								result = -30013;
								log("该流水号" + val[1] + "不存在");

								return result;
							}
						}
					} catch (SQLException localSQLException) {
					} catch (Exception localException1) {
					} finally {
						DAO.closeStatement(null, null, conn);
					}
					moneyInfoValue.status = 0;
					moneyInfoValue.bankID = bankID;
					moneyInfoValue.id = val[1];
					moneyInfoValue.m_Id = Long.parseLong(val[1]);

					if (val[2].equals("客户转市场")) {
						moneyInfoValue.money = Tool.strToDouble(val[3]);
						moneyInfoValue.type = 0;
					} else if (val[2].equals("市场转客户")) {
						moneyInfoValue.money = Tool.strToDouble(val[4]);
						moneyInfoValue.type = 1;
					}

					moneyInfoValue.compareDate = new java.sql.Date(Tool.strToDate1(val[0]).getTime());
					if (moneyInfoValue.compareDate == null)
						moneyInfoValue.compareDate = new java.sql.Date(date.getTime());
					if ((val[2].equals("客户转市场")) || (val[2].equals("市场转客户"))) {
						moneyInfoList.add(moneyInfoValue);
					}
					moneyInfoValue.toString();
				}

			}

			if (moneyInfoList == null) {
				result = -30013;
				log("获取银行对账信息,错误码=" + result);

				return result;
			}

			result = insertBankCompareInfo(moneyInfoList);
			if (result < 0) {
				result = -30014;
				log("插入银行转账信息失败=" + result);

				return result;
			}
		} catch (IOException e) {
			result = -30015;
			log("读取银行转账信息失败=" + result);

			return result;
		}

		return result;
	}

	public Vector<FirmBalance> firmBalance(String bankID, java.util.Date date) {
		log("判断不是当天，累加数据");
		Vector vector = new Vector();
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			java.util.Date trDate = Tool.strToDate(Tool.fmtDate(new java.util.Date()));
			Vector<FirmBalance> vector1 = DAO.getFirmBalance(bankID, trDate);
			log("交易商个数：" + vector1.size());
			Map mapFirmBalance = new HashMap();
			for (FirmBalance firmBalance : vector1) {
				mapFirmBalance.put(firmBalance.firmID, firmBalance);
			}
			trDate = DAO.getlastDate(trDate, conn);
			while ((date.before(trDate)) || (trDate.compareTo(date) == 0)) {
				Vector<FirmBalance> vector2 = DAO.getFirmBalance(bankID, trDate);
				for (FirmBalance firmBal : vector2) {
					if (mapFirmBalance.get(firmBal.firmID) != null) {
						((FirmBalance) mapFirmBalance.get(firmBal.firmID)).FeeMoney += firmBal.FeeMoney;
						((FirmBalance) mapFirmBalance.get(firmBal.firmID)).QYChangeMoney += firmBal.QYChangeMoney;
					}
				}
				trDate = DAO.getlastDate(trDate, conn);
			}
			Set key = mapFirmBalance.keySet();
			for (Iterator it = key.iterator(); it.hasNext();) {
				String s = (String) it.next();
				vector.add((FirmBalance) mapFirmBalance.get(s));
			}
		} catch (SQLException e) {
			log("发送银行清算数据，数据库异常");
			e.printStackTrace();
		} catch (Exception e) {
			log("发送银行清算数据，连接适配器异常");
			e.printStackTrace();
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		log("交易商个数：" + vector.size());
		return vector;
	}

	public double getCanOutBalance(String firmID, String bankID, Connection conn) {
		double result = 0.0D;
		try {
			result = dataProcess.getRealFunds(firmID, conn);
			log("获取交易商当前余额【" + result + "】");

			Vector vecFBF = DAO.getFirmBankFunds(" and f.firmID='" + firmID + "' and f.bankcode='" + bankID + "'", conn);
			if ((vecFBF == null) || (vecFBF.size() != 1)) {
				Tool.log("分银行资金账户表f_firmbankfunds中查询到平台号[" + firmID + "]有关银行[" + bankID + "]的信息异常，返回可用余额为0，不允许出金");
				return 0.0D;
			}

			double money = Arith.sub(((FirmBankFunds) vecFBF.get(0)).balance, ((FirmBankFunds) vecFBF.get(0)).outMoneyFrozenFunds);
			if (money < result) {
				result = money;
			}

			Vector vecHis = DAO.getFirmBankFundsHis(" and f.firmID='" + firmID + "' and f.bankcode='" + bankID
					+ "' and f.b_date=(select max(B_date) from f_h_firmbankfunds where B_date< to_date('" + Tool.fmtDate(new java.util.Date())
					+ "','yyyy-MM-dd'))");
			double money2 = 0.0D;
			if ((vecHis == null) || (vecHis.size() <= 0))
				money2 = ((FirmBankFunds) vecFBF.get(0)).OutInMoney;
			else {
				money2 = ((FirmBankFunds) vecHis.get(0)).balance + ((FirmBankFunds) vecFBF.get(0)).OutInMoney;
			}

			if (result > money2)
				result = money2;
		} catch (Exception e) {
			log(Tool.getExceptionTrace(e));
			e.printStackTrace();
		}
		log("获取该交易商可出资金为【" + result + "】");
		return result;
	}

	public double getCanOutBalance(String firmID, Connection conn) {
		double result = 0.0D;
		try {
			result = dataProcess.getRealFunds(firmID, conn);
			log("获取交易商当前余额【" + result + "】");

			FirmBalance fb = DAO.getFirmBala(firmID, new java.util.Date(), conn);

			double themp = fb.yesQYMoney - fb.outMoneySum;

			if (!"true".equalsIgnoreCase(getConfig("todayInMoneyNoOut"))) {
				themp += fb.inMoneySum;
				log("交易上日结算后余额+当日入金-当日出金=【" + themp + "】");
			} else {
				log("交易上日结算后余额-当日出金=【" + themp + "】");

				result -= fb.inMoneySum;
			}

			if ("true".equalsIgnoreCase(getConfig("todayYLNoOut"))) {
				if (result >= themp) {
					result = themp;
				}

			}

			if (result < 0.0D)
				result = 0.0D;
		} catch (Exception e) {
			log(Tool.getExceptionTrace(e));
			e.printStackTrace();
		}
		log("获取该交易商可出资金为【" + result + "】");
		return result;
	}

	public double getCanOutBalance(String firmID) {
		double result = 0.0D;
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			result = getCanOutBalance(firmID, conn);
		} catch (Exception e) {
			log(Tool.getExceptionTrace(e));
			e.printStackTrace();
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public String getPlatNum(String sysFirmID, String systemID) {
		String result = "";

		Vector mappings = null;
		try {
			mappings = DAO.getFirmMapping(" where firmID='" + sysFirmID + "' and moduleID=" + Integer.parseInt(systemID));
		} catch (SQLException e) {
			log("查询交易商映射关系异常：" + Tool.getExceptionTrace(e));
			return result;
		}
		if ((mappings == null) || (mappings.size() <= 0)) {
			log("交易系统交易商映射关系表中没有查到交易系统[" + systemID + "]的交易商[" + sysFirmID + "]的映射关系");
			return result;
		}
		result = ((FirmID2SysFirmID) mappings.get(0)).firmID;
		return result;
	}

	public String getPlatNum(Connection conn) {
		String firmID = getNewFirmID();
		ReturnValue result = synchroFirmToFinance(firmID, conn);
		if (result.result != 0L) {
			log("把平台号[" + firmID + "]对应的信息同步到财务模块失败");
			firmID = "";
		}
		return firmID;
	}

	public ReturnValue synchFirmAccount(CorrespondValue corr) {
		log("交易系统发起的预签约，CorrespondValue：[" + corr.toString() + "]");
		ReturnValue result = new ReturnValue();

		Vector vecf2f = null;
		try {
			vecf2f = DAO.getFirmID2SysFirmID(" where systemID='" + corr.systemID + "' and sysFirmid='" + corr.sysFirmID + "'");
		} catch (SQLException e) {
			log("查询交易商对应关系异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "查询交易商对应关系异常";
			return result;
		}
		if ((vecf2f != null) && (vecf2f.size() > 0)) {
			log("交易系统[" + corr.systemID + "]交易商[" + corr.sysFirmID + "]的签约关系已经存在");
			result.result = -1L;
			result.remark = ("交易系统[" + corr.systemID + "]交易商[" + corr.sysFirmID + "]的签约关系已经存在");
			return result;
		}
		String firmID = "";
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			conn.setAutoCommit(false);
			if ("true".equalsIgnoreCase(Tool.getConfig("GetPlatNum")))
				firmID = getPlatNum(conn);
			else
				firmID = getPlatNum(corr.sysFirmID, corr.systemID);
			ReturnValue localReturnValue1;
			if ((firmID == null) || ("".equals(firmID))) {
				log("生成平台号失败");
				result.result = -1L;
				result.remark = "平台生成平台号失败";
				conn.rollback();
				localReturnValue1 = result;
				return localReturnValue1;
			}

			if ((corr.fundsPwd == null) || ("".equals(corr.fundsPwd))) {
				log("传入的资金密码为空");
				result.result = -1L;
				result.remark = "资金密码为空";
				conn.rollback();
				localReturnValue1 = result;
				return localReturnValue1;
			}

			result = isPassword(firmID, corr.fundsPwd, conn);
			if (result.result == 1L) {
				log("写入平台号[" + firmID + "]的资金密码");
				result = modPwd(firmID, null, corr.fundsPwd, conn);
			}
			if (result.result != 0L) {
				log("该交易商的资金密码操作失败");
				result.result = -1L;
				result.remark = "资金密码错误";
				conn.rollback();
				localReturnValue1 = result;
				return localReturnValue1;
			}

			Vector vecCorr = DAO.getCorrespondList(" where firmID='" + firmID + "' and bankID='" + corr.bankID + "'");
			if ((vecCorr == null) || (vecCorr.size() <= 0)) {
				Vector vecCorr2 = DAO.getCorrespondList(" where contact='" + firmID + "' and bankID='" + corr.bankID + "'");
				if ((vecCorr2 == null) || (vecCorr2.size() <= 0)) {
					log("和平台号[" + firmID + "]相同的签约号在银行[" + corr.bankID + "]不存在签约关系，签约号可以使用[" + firmID + "]");
					corr.contact = firmID;
				} else {
					log("和平台号[" + firmID + "]相同的签约号在银行[" + corr.bankID + "]已经存在签约关系，签约号使用[" + firmID + "QYN" + corr.bankID + "]");
					corr.contact = (firmID + "QYN" + corr.bankID);
				}
				corr.firmID = firmID;
				corr.status = 1;
				corr.isOpen = 0;
				result = rgstAccount(corr, conn);
			} else {
				result.result = 0L;
			}
			if (result.result != 0L) {
				log("添加平台号[" + firmID + "]和银行[" + corr.bankID + "]的绑定关系失败");
				conn.rollback();
				localReturnValue1 = result;
				return localReturnValue1;
			}
			FirmID2SysFirmID f2f = new FirmID2SysFirmID();
			f2f.bankID = corr.bankID;
			f2f.firmID = firmID;
			f2f.sysFirmID = corr.sysFirmID;
			f2f.systemID = corr.systemID;
			f2f.defaultSystem = corr.systemID;
			result.result = DAO.addFirmID2SysFirmID(f2f, conn);
			if (result.result != 1L) {
				log("添加交易系统[" + corr.systemID + "]的交易商[" + corr.sysFirmID + "]和平台号[" + firmID + "]的对应关系失败");
				result.result = -1L;
				result.remark = "添加对应关系失败";
				conn.rollback();
				localReturnValue1 = result;
				return localReturnValue1;
			}

			FirmBankFunds firmBankFunds = new FirmBankFunds();
			firmBankFunds.firmID = firmID;
			firmBankFunds.bankID = corr.bankID;

			Vector vecFBF = DAO.getFirmBankFunds(" and f.firmID='" + firmID + "' and f.bankcode='" + corr.bankID + "'", conn);

			if ((vecFBF == null) || (vecFBF.size() <= 0)) {
				log("平台号[" + firmID + "]还没有签约银行[" + corr.bankID + "]");

				Vector vecFBF2 = DAO.getFirmBankFunds(" and f.firmID='" + firmID + "' and f.primarybankflag=1", conn);
				if ((vecFBF2 == null) || (vecFBF2.size() <= 0)) {
					log("平台号[" + firmID + "]没有签约主银行，新签约的银行[" + corr.bankID + "]为主银行");
					firmBankFunds.primaryBankFlag = 1;
				} else {
					log("平台号[" + firmID + "]已经签约了主银行，新签约的银行[" + corr.bankID + "]为次银行");
					firmBankFunds.primaryBankFlag = 2;
				}

				result.result = DAO.addFirmBankFunds(firmBankFunds, conn);
			} else {
				log("平台号[" + firmID + "]已经签约银行[" + ((FirmBankFunds) vecFBF.get(0)).bankID + "]");

				Vector vecCorrIsOpen = DAO.getCorrespondList(" where firmID='" + firmID + "' and bankID='" + corr.bankID + "' and isOpen=1");
				if ((vecCorrIsOpen != null) && (vecCorrIsOpen.size() > 0)) {
					log("f_b_firmidandaccount表中也已经存在和该银行的对应关系");
					result.result = 0L;
					result.remark = "签约成功";

					result.msg = new Object[] { firmID };
					conn.commit();
					localReturnValue1 = result;
					return localReturnValue1;
				}

				result.result = 1L;
			}
			if (result.result != 1L) {
				log("添加资金账户失败，数据回滚");
				result.result = -1L;
				result.remark = "添加资金账户信息失败";
				conn.rollback();
				localReturnValue1 = result;
				return localReturnValue1;
			}

			result = openAccountMarket(corr, conn);
			if ((result.result != 0L) && (result.result != 5L)) {
				log("平台向银行端发起签约失败");
				result.result = -1L;
				result.remark = "签约失败";
				conn.rollback();
				localReturnValue1 = result;
				return localReturnValue1;
			}

			result.msg = new Object[] { firmID };
			conn.commit();
		} catch (Exception e) {
			log("系统异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "平台处理器处理异常";
			try {
				conn.rollback();
			} catch (SQLException e1) {
				log("回滚数据异常：" + Tool.getExceptionTrace(e1));
				result.result = -1L;
				result.remark = "平台处理异常，回滚数据异常";
			}
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public ReturnValue synchroFirmToFinance(String firmID, Connection conn) {
		log("同步平台交易商[" + firmID + "]信息到财务模块");
		ReturnValue result = new ReturnValue();
		CallableStatement proc = null;

		MFirmValue mfv = new MFirmValue();
		mfv.firmID = firmID;
		mfv.name = "平台号";
		mfv.bankName = "银行";
		mfv.bankAccount = "999999999999999999";
		try {
			result.result = DAO.addMFirm(mfv, conn);
		} catch (SQLException e) {
			log("向交易系统表M_firm添加记录异常:" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "数据库异常";
		}
		if (result.result <= 0L) {
			result.result = -1L;
			result.remark = "同步交易商到交易系统失败";
		} else {
			try {
				proc = conn.prepareCall("{ call SP_M_FirmAdd_sys(?) }");
				proc.setString(1, firmID);
				proc.executeQuery();
				result.result = 0L;
				result.remark = "同步交易商到财务成功";
			} catch (Exception e) {
				log("调用同步信息存储出现异常：" + Tool.getExceptionTrace(e));
				result.result = -1L;
				result.remark = "同步交易商信息异常";

				if (proc != null)
					try {
						proc.close();
						proc = null;
					} catch (Exception e1) {
						e1.printStackTrace();
					}
			} finally {
				if (proc != null) {
					try {
						proc.close();
						proc = null;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return result;
	}

	public ReturnValue openAccountBank(CorrespondValue corr) {
		log("银行端发起的签约，签约银行[" + corr.bankID + "]");
		ReturnValue result = new ReturnValue();

		Vector vec = null;
		try {
			vec = DAO.getCorrespondList(" where bankID='" + corr.bankID + "' and card='" + corr.card + "'");
		} catch (Exception e) {
			log("查询交易商绑定关系时出现异常:" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "系统异常";
			return result;
		}
		if ((vec == null) || (vec.size() <= 0)) {
			log("不存在平台号[" + corr.firmID + "]和银行[" + corr.bankID + "]的对应关系");
			result.result = -1L;
			result.remark = "没有找到签约关系";
			return result;
		}
		if (((CorrespondValue) vec.get(0)).isOpen == 1) {
			log("不存在平台号[" + corr.firmID + "]和银行[" + corr.bankID + "]已经是签约关系");
			result.result = -1L;
			result.remark = "已经签约成功";
			return result;
		}

		Vector vecf2f = null;
		try {
			vecf2f = DAO.getFirmID2SysFirmID(" where firmid='" + corr.firmID + "'");
		} catch (SQLException e) {
			log("查询平台号[" + corr.firmID + "]和交易系统交易商的对应关系异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "查询平台号和交易系统对应关系异常";
			return result;
		}
		if ((vecf2f == null) || (vecf2f.size() <= 0)) {
			log("没有查询到平台号[" + corr.firmID + "]和交易系统交易商的对应关系");
			result.result = -1L;
			result.remark = "没有查到对应关系";
			return result;
		}
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			conn.setAutoCommit(false);

			corr.status = 0;
			corr.isOpen = 1;
			result.result = DAO.modCorrespond(corr, conn);
			ReturnValue localReturnValue1;
			if (result.result != 0L) {
				log("修改平台端签约状态失败，数据回滚");
				conn.rollback();
				result.result = -1L;
				result.remark = "处理失败";
				localReturnValue1 = result;
				return localReturnValue1;
			}

			Vector vecFBF = DAO.getFirmBankFunds(" and f.firmID='" + corr.firmID + "' and f.bankcode='" + corr.bankID + "'", conn);
			if ((vecFBF == null) || (vecFBF.size() != 1)) {
				Tool.log("未找到平台号[" + corr.firmID + "]签约银行[" + corr.bankID + "]的记录");
				conn.rollback();
				result.result = -1L;
				result.remark = "市场端找不到对应数据";
				localReturnValue1 = result;
				return localReturnValue1;
			}
			if (((FirmBankFunds) vecFBF.get(0)).primaryBankFlag == 1) {
				Tool.log("平台号[" + corr.firmID + "]签约的银行[" + corr.bankID + "]已经是主银行，直接回执成功");
				conn.commit();
				result.result = 0L;
				result.remark = "签约成功";
			} else {
				Vector vecFBF2 = DAO.getFirmBankFunds(" and f.firmID='" + corr.firmID + "' and f.primaryBankFlag=1", conn);
				if ((vecFBF2 == null) || (vecFBF2.size() != 1)) {
					Tool.log("没有找到平台号[" + corr.firmID + "]签约主银行的记录，或者找到多条记录，请注意！！！！！！！！！！！！");
					result.result = -1L;
					result.remark = "市场端处理失败";
					conn.rollback();
					localReturnValue1 = result;
					return localReturnValue1;
				}

				Vector vecOpen = DAO
						.getCorrespondList(" where firmid='" + corr.firmID + "' and bankid='" + ((FirmBankFunds) vecFBF2.get(0)).bankID + "'", conn);
				if ((vecOpen == null) || (vecOpen.size() != 1)) {
					Tool.log("银行账户绑定关系表存在多条相同记录或缺失记录");
					result.result = -1L;
					result.remark = "市场处理失败";
					conn.rollback();
					localReturnValue1 = result;
					return localReturnValue1;
				}

				if (((CorrespondValue) vecOpen.get(0)).isOpen == 1) {
					Tool.log("平台号[" + corr.firmID + "]签约的银行[" + corr.bankID + "]为次银行，其已经签约了主银行[" + ((FirmBankFunds) vecFBF2.get(0)).bankID + "]");
					result.result = 0L;
					result.remark = "签约成功";
					conn.commit();
				} else {
					Tool.log("平台号[" + corr.firmID + "]之前签约的主银行[" + ((FirmBankFunds) vecFBF2.get(0)).bankID + "]没有最终签约成功，将本次签约的银行[" + corr.bankID
							+ "]设置为主银行，原有主银行修改为次银行");

					FirmBankFunds oldMainBank = (FirmBankFunds) vecFBF2.get(0);
					oldMainBank.primaryBankFlag = 2;
					if (DAO.modFirmBankFunds(oldMainBank, conn) != 1) {
						Tool.log("将原有主银行[" + oldMainBank.bankID + "]修改为次银行失败");
						conn.rollback();
						result.result = -1L;
						result.remark = "市场端签约失败";
						localReturnValue1 = result;
						return localReturnValue1;
					}

					FirmBankFunds newMainBank = (FirmBankFunds) vecFBF.get(0);
					newMainBank.primaryBankFlag = 1;
					if (DAO.modFirmBankFunds(newMainBank, conn) != 1) {
						Tool.log("将新签约成功银行[" + newMainBank.bankID + "]修改为主银行失败");
						conn.rollback();
						result.result = -1L;
						result.remark = "市场端签约失败";
						localReturnValue1 = result;
						return localReturnValue1;
					}

					Tool.log("将新签约成功银行[" + newMainBank.bankID + "]修改为主银行成功");
					conn.commit();
					result.result = 0L;
					result.remark = "签约成功";
				}

			}

			conn.setAutoCommit(true);
		} catch (Exception e) {
			log("修改平台交易商签约状态异常:" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "数据库异常";
		} finally {
			DAO.closeStatement(null, null, conn);
		}

		return result;
	}

	public ReturnValue delCorrBank(CorrespondValue corr) {
		log("银行端解约，签约号[" + corr.firmID + "]、银行[" + corr.bankID + "]");
		ReturnValue result = new ReturnValue();

		Vector vecCorr = null;
		try {
			vecCorr = DAO.getCorrespondList(" where contact='" + corr.firmID + "' and bankid='" + corr.bankID + "' and isopen=1");
		} catch (Exception e1) {
			log("查询签约号[" + corr.firmID + "]和银行[" + corr.bankID + "]的签约记录异常：" + Tool.getExceptionTrace(e1));
			result.result = -1L;
			result.remark = "系统异常";
			return result;
		}
		if ((vecCorr == null) || (vecCorr.size() <= 0)) {
			log("没有查询到签约号[" + corr.firmID + "]和银行[" + corr.bankID + "]的签约记录");
			result.result = -1L;
			result.remark = "没有查询到签约关系";
			return result;
		}
		corr.firmID = ((CorrespondValue) vecCorr.get(0)).firmID;

		Vector vecF2F = null;
		try {
			vecF2F = DAO.getFirmID2SysFirmID(" where firmid='" + corr.firmID + "' and bankid='" + corr.bankID + "'");
		} catch (SQLException e) {
			Tool.log("查询平台号[" + corr.firmID + "]和银行[" + corr.bankID + "]关于交易商的对应关系异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "系统异常";
			return result;
		}
		if ((vecF2F != null) && (vecF2F.size() > 0)) {
			Tool.log("平台号[" + corr.firmID + "]还有和交易商关于银行[" + corr.bankID + "]的签约记录，请先从客户端发起解约");
			result.result = -1L;
			result.remark = "解约失败，请先从市场端发起解约";
			return result;
		}
		try {
			if (DAO.delCorrespond((CorrespondValue) vecCorr.get(0)) != 0) {
				Tool.log("删除平台号[" + corr.firmID + "]和银行[" + corr.bankID + "]的对应关系失败");
				result.result = -1L;
				result.remark = "删除签约关系失败";
			} else {
				Tool.log("删除平台号[" + corr.firmID + "]和银行[" + corr.bankID + "]的对应关系成功");
				result.result = 0L;
				result.remark = "解约成功";
			}
		} catch (Exception e) {
			Tool.log("删除平台号[" + corr.firmID + "]和银行[" + corr.bankID + "]的对应关系异常" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "系统异常";
		}
		return result;
	}

	public ReturnValue noticeDelPlatFirmID(String systemID, String platFirmID, String firmID) {
		Tool.log("通知交易系统[" + systemID + "]删除该系统交易商[" + firmID + "]和平台号[" + platFirmID + "]的对应关系");
		ReturnValue result = new ReturnValue();

		TradeProcessorRMI tradeProc = getTradeCaptialProcessor(systemID);
		if (tradeProc == null) {
			Tool.log("获取交易系统[" + systemID + "]的处理器RMI链接失败");
			result.result = -1L;
			result.remark = "获取交易系统处理器失败";
			return result;
		}
		RequestMsg req = new RequestMsg();
		req.setBankID("");
		req.setMethodName("noticeDelPlatFirmID");
		req.setParams(new Object[] { platFirmID, firmID });
		try {
			result = tradeProc.doWork(req);
		} catch (RemoteException e) {
			Tool.log("调用交易系统处理器方法异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "系统异常";
		}
		return result;
	}

	public ReturnValue isOpenQuery(CorrespondValue corr) {
		log("从交易系统端发起了签约结果查询");
		ReturnValue result = new ReturnValue();

		return result;
	}

	public Vector<SystemMessage> getSystemMessages(String filter) {
		Vector vec = new Vector();
		try {
			vec = DAO.getSystemMessages(filter);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vec;
	}

	public long modSystemMessage(SystemMessage sysMsg) {
		long result = 0L;
		try {
			result = DAO.modSystemMessage(sysMsg);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public String getNewFirmID() {
		String result = "";
		long maxFirmID = 0L;
		try {
			maxFirmID = DAO.getNewFirmID();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (maxFirmID > 0L) {
			String maxFirmString = String.valueOf(maxFirmID);
			if (maxFirmString.length() < 7) {
				for (int i = 0; i < 7 - maxFirmString.length(); i++) {
					result = result + "0";
				}
			}
			result = result + maxFirmString;
		}
		result = "PTN" + result;
		return result;
	}

	public boolean canDelCorr(CorrespondValue corr) {
		log("判断平台号[" + corr.firmID + "]银行[" + corr.bankID + "]是否满足解约条件");
		boolean result = true;

		Vector fbfOld = null;
		try {
			fbfOld = DAO.getFirmBankFundsHis(" and firmID='" + corr.firmID + "' and bankcode='" + corr.bankID + "' order by b_date desc");
		} catch (SQLException e1) {
			log("查询交易商上日结算后余额异常：" + Tool.getExceptionTrace(e1));
			return false;
		}

		if ((fbfOld != null) && (fbfOld.size() > 0) && ((((FirmBankFunds) fbfOld.get(0)).RIGHTS != 0.0D)
				|| (((FirmBankFunds) fbfOld.get(0)).balance != 0.0D) || (((FirmBankFunds) fbfOld.get(0)).RIGHTSFROZENFUNDS != 0.0D))) {
			log("平台号[" + corr.firmID + "]上一日[" + Tool.fmtDate(((FirmBankFunds) fbfOld.get(0)).b_date) + "]结算后权益["
					+ ((FirmBankFunds) fbfOld.get(0)).RIGHTS + "]可用[" + ((FirmBankFunds) fbfOld.get(0)).balance + "]冻结["
					+ ((FirmBankFunds) fbfOld.get(0)).RIGHTSFROZENFUNDS + "]有余额不为零，不允许解约");
			result = false;
			return result;
		}

		Vector vecCV = null;
		try {
			vecCV = DAO.getCapitalInfoList(" where bankID='" + corr.bankID + "' and firmID='" + corr.firmID + "' and trunc(banktime) =to_date('"
					+ Tool.fmtDate(new java.util.Date()) + "', 'yyyy-MM-dd')");
		} catch (Exception e) {
			log("查询平台出入金流水记录异常");
			return false;
		}

		if ((vecCV != null) && (vecCV.size() > 0)) {
			log("平台号[" + corr.sysFirmID + "]关于银行[" + corr.bankID + "]在平台存在当天的转账流水");
			result = false;
			return result;
		}

		Vector vecFBF = null;
		try {
			vecFBF = DAO.getFirmBankFunds(" and f.firmID='" + corr.firmID + "' and f.bankcode='" + corr.bankID + "'");
		} catch (SQLException e) {
			Tool.log("查询签约关系异常1：" + Tool.getExceptionTrace(e));
		}
		if ((vecFBF == null) || (vecFBF.size() <= 0)) {
			Tool.log("平台号[" + corr.firmID + "]和银行[" + corr.bankID + "]在交易商银行资金表f_firmBankFunds中不存在签约关系");
			result = false;
			return result;
		}
		if (((FirmBankFunds) vecFBF.get(0)).primaryBankFlag == 1) {
			Vector<FirmBankFunds> vecFBF2 = null;
			try {
				vecFBF2 = DAO.getFirmBankFunds(" and f.firmID='" + corr.firmID + "' and f.primarybankflag=2");
			} catch (SQLException e) {
				Tool.log("查询签约关系异常2：" + Tool.getExceptionTrace(e));
				return false;
			}

			if ((vecFBF2 != null) && (vecFBF2.size() > 0)) {
				try {
					String filter = "where firmid='" + corr.firmID + "' and bankid in (";
					for (FirmBankFunds fbf : vecFBF2) {
						filter = filter + "'" + fbf.bankID + "',";
					}
					filter = filter.substring(0, filter.length() - 1);
					filter = filter + ") and isopen=1";
					Vector vecCorr = DAO.getCorrespondList(filter);
					if ((vecCorr != null) && (vecCorr.size() > 0)) {
						Tool.log("平台号[" + corr.firmID + "]还有辅银行没有解约，主银行禁止解约");
						return false;
					}

					Tool.log("平台号[" + corr.firmID + "]没有对应的签约成功的辅银行");
				} catch (Exception e) {
					Tool.log("查询签约关系异常3：" + Tool.getExceptionTrace(e));
					return false;
				}
			} else {
				Tool.log("平台号[" + corr.firmID + "]没有签约过次银行");
			}
		}

		return result;
	}

	public ReturnValue outMoneyBank(InOutMoney outMoney) {
		log("从银行[" + outMoney.bankID + "]发起的平台号[" + outMoney.firmID + "]的交易商的出金");
		ReturnValue result = new ReturnValue();
		if (!isTradeDate(outMoney.defaultSystem)) {
			log("出金默认的交易系统[" + outMoney.systemID + "]当前不是转账时间");
			result.result = -1L;
			result.remark = "交易系统不在转账时间内";
			return result;
		}

		long capitalID = 0L;

		Connection conn = null;

		Timestamp curTime = new Timestamp(System.currentTimeMillis());

		boolean isopen = bankAccountIsOpen(outMoney.firmID, outMoney.bankID, outMoney.account) == 1;
		if (!isopen) {
			result.result = -1L;
			result.remark = "未找到签约的绑定关系";
			log("交易系统不存在签约关系");
			return result;
		}
		try {
			conn = DAO.getConnection();
			ReturnValue localReturnValue1;
			if ((outMoney.firmID == null) || (outMoney.systemID == null) || (outMoney.sysFirmID == null) || (outMoney.account == null)
					|| ("".equals(outMoney.firmID)) || ("".equals(outMoney.systemID)) || ("".equals(outMoney.sysFirmID))
					|| ("".equals(outMoney.account))) {
				log("银行端出金参数非法");
				result.result = -1L;
				result.remark = "出金参数错误";
				localReturnValue1 = result;
				return localReturnValue1;
			}

			Vector vecf2f = null;
			vecf2f = DAO.getFirmID2SysFirmID(
					" where firmID='" + outMoney.firmID + "' and sysFirmID='" + outMoney.sysFirmID + "' and systemID='" + outMoney.systemID + "'");
			if ((vecf2f == null) || (vecf2f.size() <= 0)) {
				log("没有找到平台交易商[" + outMoney.firmID + "]和子系统[" + outMoney.systemID + "]的交易商[" + outMoney.sysFirmID + "]关于银行[" + outMoney.bankID
						+ "]的绑定关系");
				result.result = -1L;
				result.remark = "交易商同平台的绑定关系不存在";
				localReturnValue1 = result;
				return localReturnValue1;
			}
			Vector vecCV = null;
			vecCV = DAO.getCorrespondList(
					" where firmID='" + outMoney.firmID + "' and bankID='" + outMoney.bankID + "' and account='" + outMoney.account + "'");
			if ((vecCV == null) || (vecCV.size() <= 0) || (((CorrespondValue) vecCV.get(0)).isOpen != 1)) {
				log("交易商[" + outMoney.firmID + "]和银行[" + outMoney.bankID + "]没有建立绑定关系，或者是未签约成功状态");
				result.result = -1L;
				result.remark = "没有最终签约成功";
				localReturnValue1 = result;
				return localReturnValue1;
			}

			double realFunds = getCanOutBalance(outMoney.firmID, outMoney.bankID, conn);

			double outRate = getTransRate(outMoney.bankID, outMoney.firmID, outMoney.money, 1, 0, null, conn);

			if (realFunds < Arith.sub(outMoney.money, outRate)) {
				Tool.log("平台交易商[" + outMoney.firmID + "]的可用金额为[" + realFunds + "],小于出金金额[" + outMoney.money + "]和手续费[" + outRate + "]的和");
				result.result = -1L;
				result.remark = "交易商余额不足";
				localReturnValue1 = result;
				return localReturnValue1;
			}
			try {

				conn.setAutoCommit(false);

				CapitalValue cVal = new CapitalValue();
				cVal.actionID = outMoney.actionID;
				cVal.bankID = outMoney.bankID;
				cVal.creditID = outMoney.firmID;
				cVal.debitID = ((String) dataProcess.getBANKSUB().get(outMoney.bankID));
				cVal.firmID = outMoney.firmID;
				cVal.money = outMoney.money;
				cVal.note = outMoney.remark;
				cVal.funID = outMoney.funID;
				cVal.oprcode = dataProcess.getOUTSUMMARY() + "";
				cVal.status = 2;
				cVal.type = 1;
				cVal.systemID = outMoney.systemID;
				cVal.sysFirmID = outMoney.sysFirmID;
				cVal.tradeSource = "2";
				capitalID = DAO.addCapitalInfo(cVal, conn);

				dataProcess.updateFrozenFunds(outMoney.firmID, Arith.sub(outMoney.money, outRate), conn);

				bankFrozenFuns(outMoney.firmID, outMoney.bankID, outMoney.account, Arith.sub(outMoney.money, outRate), conn);

				conn.commit();
				result.result = 0L;
			} catch (Exception e) {
				conn.rollback();
				result.result = -10026L;
				result.remark = "写入资金流水异常";
				log("市场端发起出金写资金流水SQLException,数据回滚:" + Tool.getExceptionTrace(e));
				throw e;
			} finally {
				conn.setAutoCommit(true);
			}
			if (result.result != 0L) {
				log("记录流水失败");
				result.result = -1L;
				result.remark = "记录流水失败";
				localReturnValue1 = result;
				return localReturnValue1;
			}

			result = outMoneyPtToSystem(outMoney);
			try {
				conn.setAutoCommit(false);
				if (result.result == 0L) {
					log("业务流水处理成功,出金成功。");
					result.remark = "成功";
					DAO.modCapitalInfoStatus(capitalID, outMoney.funID, 0, curTime, conn);
					DAO.modCapitalInfoNote(capitalID, "银行端出金，出金成功", conn);

					bankFrozenFuns(outMoney.firmID, outMoney.bankID, null, -1.0D * Arith.sub(outMoney.money, outRate), conn);

					dataProcess.updateFrozenFunds(outMoney.firmID, -1.0D * Arith.sub(outMoney.money, outRate), conn);

					dataProcess.updateFundsFull(outMoney.firmID, dataProcess.getOUTSUMMARY() + "",
							(String) dataProcess.getBANKSUB().get(outMoney.bankID), outMoney.money, outMoney.actionID, conn);
				} else if (result.result == 5L) {
					log("业务流水处理中。");
					result.remark = "处理中";
				} else {
					result.remark = "处理失败";
					DAO.modCapitalInfoStatus(capitalID, outMoney.funID, 1, curTime, conn);
					DAO.modCapitalInfoNote(capitalID, "银行端出金，出金失败", conn);

					log("平台处理流水失败");

					bankFrozenFuns(outMoney.firmID, outMoney.bankID, null, -1.0D * Arith.sub(outMoney.money, outRate), conn);

					dataProcess.updateFrozenFunds(outMoney.firmID, -1.0D * Arith.sub(outMoney.money, outRate), conn);
				}
				conn.commit();
			} catch (Exception e) {
				result.result = -10015L;
				result.remark = "系统异常";
				conn.rollback();
				log("银行端发起出金,系统异常Exception,数据回滚:" + Tool.getExceptionTrace(e));
			} finally {
				conn.setAutoCommit(true);
			}
		} catch (Exception e) {
			log("平台处理银行端出金异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "市场端出金发生异常";
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public ReturnValue inMoneyBank(InOutMoney inMoney) {
		log("从银行[" + inMoney.bankID + "]发起的平台号为[" + inMoney.firmID + "]的入金");
		ReturnValue result = new ReturnValue();
		if (!isTradeDate(inMoney.systemID)) {
			log("交易系统[" + inMoney.systemID + "]当前不在交易时间内");
			result.result = -1L;
			result.remark = "交易系统不在转账时间内";
			return result;
		}

		Connection conn = null;

		boolean isopen = bankAccountIsOpen(inMoney.firmID, inMoney.bankID, inMoney.account) == 1;
		if (!isopen) {
			result.result = -1L;
			result.remark = "未找到签约的绑定关系";
			log("交易系统不存在签约关系");
			return result;
		}
		try {
			conn = DAO.getConnection();
			ReturnValue localReturnValue1;
			if ((inMoney.firmID == null) || (inMoney.systemID == null) || (inMoney.sysFirmID == null) || (inMoney.account == null)
					|| ("".equals(inMoney.firmID)) || ("".equals(inMoney.systemID)) || ("".equals(inMoney.sysFirmID))
					|| ("".equals(inMoney.account))) {
				log("银行端入金参数非法");
				result.result = -1L;
				result.remark = "入金参数错误";
				localReturnValue1 = result;
				return localReturnValue1;
			}

			Vector vecf2f = null;
			vecf2f = DAO.getFirmID2SysFirmID(" where firmID='" + inMoney.firmID + "' and sysFirmID='" + inMoney.sysFirmID + "' and systemID='"
					+ inMoney.systemID + "' and bankID='" + inMoney.bankID + "'");
			if ((vecf2f == null) || (vecf2f.size() <= 0)) {
				log("没有找到平台交易商[" + inMoney.firmID + "]和子系统[" + inMoney.systemID + "]的交易商[" + inMoney.sysFirmID + "]关于银行[" + inMoney.bankID
						+ "]的绑定关系");
				result.result = -1L;
				result.remark = "交易商同平台的绑定关系不存在";
				localReturnValue1 = result;
				return localReturnValue1;
			}
			boolean notice = true;

			Vector vecSystem = DAO.getSystemMessages(" where systemID='" + ((FirmID2SysFirmID) vecf2f.get(0)).defaultSystem + "'");
			if ((vecSystem == null) || (vecSystem.size() < 0)) {
				log("没有找到交易系统编号为[" + ((FirmID2SysFirmID) vecf2f.get(0)).systemID + "]的交易系统");
				notice = false;
			} else {
				if (!isTradeDate(inMoney.systemID)) {
					log("平台号[" + inMoney.firmID + "]交易系统[" + inMoney.systemID + "]交易商[" + inMoney.sysFirmID + "]银行[" + inMoney.bankID + "]对应的默认入金系统["
							+ ((FirmID2SysFirmID) vecf2f.get(0)).defaultSystem + "]不在转账时间内");
					notice = false;
				}

				inMoney.defaultSystem = ((FirmID2SysFirmID) vecf2f.get(0)).defaultSystem;
			}

			try {
				conn.setAutoCommit(false);

				CapitalValue cVal = new CapitalValue();
				cVal.actionID = inMoney.actionID;
				cVal.bankID = inMoney.bankID;
				cVal.creditID = inMoney.firmID;
				cVal.debitID = ((String) dataProcess.getBANKSUB().get(inMoney.bankID));
				cVal.firmID = inMoney.firmID;
				cVal.money = inMoney.money;
				cVal.note = inMoney.remark;
				cVal.oprcode = dataProcess.getINSUMMARY() + "";
				cVal.status = 0;
				cVal.bankTime = new Timestamp(System.currentTimeMillis());
				cVal.type = 0;
				cVal.funID = inMoney.funID;

				cVal.systemID = inMoney.systemID;
				cVal.sysFirmID = inMoney.sysFirmID;
				cVal.tradeSource = "2";

				DAO.addCapitalInfo(cVal, conn);

				if (!notice) {
					DAO.addCapitalInfoNotice(cVal, conn);
				}

				dataProcess.updateFundsFull(inMoney.firmID, dataProcess.getINSUMMARY() + "", (String) dataProcess.getBANKSUB().get(inMoney.bankID),
						inMoney.money, inMoney.actionID, conn);

				conn.commit();
				result.result = 0L;
				result.remark = "入金成功";
				log("平台处理银行端入金成功");
			} catch (Exception e) {
				conn.rollback();
				result.result = -10026L;
				result.remark = "写入资金流水异常";
				log("市场端发起入金写资金流水SQLException,数据回滚:" + Tool.getExceptionTrace(e));
				throw e;
			} finally {
				conn.setAutoCommit(true);
			}
			if (notice) {
				inMoneyPtToSystem(inMoney);
			}
		} catch (Exception e) {
			log("平台处理入金异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "平台处理入金发生异常";
		} finally {
			DAO.closeStatement(null, null, conn);
		}

		return result;
	}

	public ReturnValue inMoneyPtToSystem(InOutMoney inMoney) {
		log("平台交易商[" + inMoney.firmID + "]发起入金到子系统[" + inMoney.systemID + "]");
		Connection conn = null;
		ReturnValue result = new ReturnValue();
		Timestamp bankTime = new Timestamp(System.currentTimeMillis());
		long capitalID = 0L;
		try {
			conn = DAO.getConnection();
			try {
				conn.setAutoCommit(false);

				CapitalValue cVal = new CapitalValue();
				cVal.actionID = inMoney.actionID;
				cVal.bankID = inMoney.bankID;
				cVal.creditID = inMoney.firmID;
				cVal.debitID = ((String) dataProcess.getBANKSUB().get(inMoney.bankID));
				cVal.firmID = inMoney.firmID;
				cVal.money = inMoney.money;
				cVal.note = inMoney.remark;
				cVal.oprcode = dataProcess.getINSUMMARY() + "";
				cVal.status = 0;
				cVal.type = 0;
				cVal.bankTime = bankTime;

				cVal.systemID = inMoney.defaultSystem;
				cVal.sysFirmID = inMoney.sysFirmID;
				cVal.tradeSource = "1";

				capitalID = DAO.addCapitalInfoPT(cVal, conn);

				conn.commit();
				result.result = 0L;
			} catch (Exception e) {
				conn.rollback();
				result.result = -10026L;
				result.remark = "写入资金流水异常";
				log("平台端发起到子系统入金写资金流水SQLException,数据回滚:" + Tool.getExceptionTrace(e));
			} finally {
				conn.setAutoCommit(true);
			}
			ReturnValue localReturnValue1;
			if (result.result != 0L) {
				log("记录流水失败！");
				result.result = -1L;
				result.remark = "写流水失败";
				localReturnValue1 = result;
				return localReturnValue1;
			}

			TradeProcessorRMI tradeProc = getTradeCaptialProcessor(inMoney.defaultSystem);
			if (tradeProc == null) {
				log("获取交易系统[" + inMoney.defaultSystem + "]处理器RMI失败");
				result.result = -1L;
				result.remark = "获取交易系统处理器失败";
				localReturnValue1 = result;
				return localReturnValue1;
			}
			RequestMsg reqMsg = new RequestMsg();
			reqMsg.setBankID("");
			reqMsg.setMethodName("inMoney");
			Object[] params = new Object[2];
			params[0] = inMoney;
			params[1] = Integer.valueOf(2);
			reqMsg.setParams(params);
			result = tradeProc.doWork(reqMsg);

			if (result == null) {
				log("平台处理器调用交易系统[" + inMoney.defaultSystem + "]处理器入金方法失败");
				localReturnValue1 = result;
				return localReturnValue1;
			}
			try {

				conn.setAutoCommit(false);
				DAO.modCapitalInfoStatusPT(capitalID, result.funID, 0, bankTime, conn);
				conn.commit();
			} catch (Exception e) {
				conn.rollback();
				log("写入市场端流水号异常：" + Tool.getExceptionTrace(e));
				result.result = -1L;
				result.remark = "写入市场端流水号异常";
			} finally {
				conn.setAutoCommit(true);
			}

			if (result.result == 0L)
				log("平台处理器调用交易系统[" + inMoney.defaultSystem + "]处理器入金方法成功，交易系统端处理成功");
			else
				log("平台处理器调用交易系统[" + inMoney.defaultSystem + "]处理器入金方法成功，交易系统端处理失败");
		} catch (Exception e) {
			log("平台处理出现异常" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "系统异常";
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public ReturnValue outMoneyPtToSystem(InOutMoney outMoney) {
		log("平台交易商[" + outMoney.firmID + "]发起出金到子系统[" + outMoney.systemID + "]");
		Connection conn = null;

		Timestamp curTime = new Timestamp(System.currentTimeMillis());
		long capitalID = 0L;
		ReturnValue result = new ReturnValue();
		try {
			conn = DAO.getConnection();
			try {
				conn.setAutoCommit(false);

				CapitalValue cVal = new CapitalValue();
				cVal.actionID = outMoney.actionID;
				cVal.bankID = outMoney.bankID;
				cVal.creditID = outMoney.firmID;
				cVal.debitID = ((String) dataProcess.getBANKSUB().get(outMoney.bankID));
				cVal.firmID = outMoney.firmID;
				cVal.money = outMoney.money;
				cVal.note = outMoney.remark;
				cVal.oprcode = dataProcess.getOUTSUMMARY() + "";
				cVal.status = 2;
				cVal.type = 1;

				cVal.systemID = outMoney.systemID;
				cVal.sysFirmID = outMoney.sysFirmID;
				cVal.tradeSource = "1";

				capitalID = DAO.addCapitalInfoPT(cVal, conn);

				conn.commit();
				result.result = 0L;
			} catch (Exception e) {
				conn.rollback();
				result.result = -10026L;
				result.remark = "写入资金流水异常";
				log("平台端发起到子系统出金写资金流水SQLException,数据回滚:" + Tool.getExceptionTrace(e));
			} finally {
				conn.setAutoCommit(true);
			}
			ReturnValue localReturnValue1;
			if (result.result != 0L) {
				log("记录流水失败");
				result.result = -1L;
				result.remark = "记录流水失败";
				localReturnValue1 = result;
				return localReturnValue1;
			}
			Vector vecBank = DAO.getBankList(" where bankID='" + outMoney.bankID + "'");
			if ((vecBank == null) || (vecBank.size() <= 0)) {
				Tool.log("没有找到银行[" + outMoney.bankID + "]的信息");
				result.result = -1L;
				result.remark = "没有找到该银行的信息";
			}

			double checkOutMoney = ((BankValue) vecBank.get(0)).maxAuditMoney;

			FirmValue fv = DAO.getFirm(outMoney.firmID, conn);
			if (fv == null) {
				Tool.log("没有找到平台号[" + outMoney.firmID + "]的信息");
				result.result = -1L;
				result.remark = ("没有平台号[" + outMoney.firmID + "]的信息");
			}
			if (result.result == 0L) {
				if ((checkOutMoney > fv.maxAuditMoney) && (fv.maxAuditMoney != 0.0D)) {
					checkOutMoney = fv.maxAuditMoney;
				}

				if (checkOutMoney <= outMoney.money) {
					Tool.log("平台号[" + outMoney.firmID + "]出金[" + outMoney.money + "]到银行[" + outMoney.bankID + "]，超过了审核额度[" + checkOutMoney
							+ "]，进入审核队列");
					result.result = 5L;
					result.remark = "超出审核额度，等待审核";
					try {
						conn.setAutoCommit(false);
						DAO.modCapitalInfoStatusPT(capitalID, outMoney.sysAciontID + "", 3, curTime, conn);
						DAO.modCapitalInfoNotePT(capitalID, "银行端出金，等待审核", conn);
						conn.commit();
					} catch (Exception e) {
						Tool.log("修改出金流水[" + outMoney.actionID + "]的状态为待审核状态异常，数据回滚：" + Tool.getExceptionTrace(e));
						result.result = -1L;
						result.remark = "出金进入审核队列异常";
						conn.rollback();
					} finally {
						conn.setAutoCommit(true);
					}
					localReturnValue1 = result;
					return localReturnValue1;
				}
			}

			TradeProcessorRMI tradeProc = getTradeCaptialProcessor(outMoney.defaultSystem);
			if (tradeProc == null) {
				log("获取交易系统[" + outMoney.defaultSystem + "]处理器RMI失败");
				result.result = -1L;
				result.remark = "获取交易系统处理器失败";
				localReturnValue1 = result;
				return localReturnValue1;
			}

			RequestMsg reqMsg = new RequestMsg();
			reqMsg.setBankID("");
			reqMsg.setMethodName("outMoney");
			Object[] params = new Object[2];
			params[0] = outMoney;
			params[1] = Integer.valueOf(2);
			reqMsg.setParams(params);

			result = tradeProc.doWork(reqMsg);
			try {
				conn.setAutoCommit(false);
				if (result.result == 0L) {
					log("子系统[" + outMoney.systemID + "]处理成功，业务流水处理成功,出金成功。");
					result.remark = "成功";
					DAO.modCapitalInfoStatusPT(capitalID, result.funID, 0, curTime, conn);
					DAO.modCapitalInfoNotePT(capitalID, "银行端出金，出金成功", conn);
				} else if (result.result == 5L) {
					log("平台到子系统适配器处理成功，业务流水处理中。");
					DAO.modCapitalInfoStatusPT(capitalID, result.funID, 2, curTime, conn);
					DAO.modCapitalInfoNotePT(capitalID, "银行端出金，处理中", conn);
				} else {
					DAO.modCapitalInfoStatusPT(capitalID, result.funID, 1, curTime, conn);
					DAO.modCapitalInfoNotePT(capitalID, "银行端出金，出金失败", conn);
					log("请求子系统出金失败");
				}
				conn.commit();
			} catch (Exception e) {
				result.result = -10015L;
				result.remark = "系统异常";
				conn.rollback();
				log("平台端发起出金,系统异常Exception,数据回滚:" + Tool.getExceptionTrace(e));
			} finally {
				conn.setAutoCommit(true);
			}
		} catch (Exception e) {
			log("平台处理出现异常" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "系统异常";
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public ReturnValue transferSysToSys(String firmID, double money, int inOut) {
		log("处理子系统之间的转账业务，涉及到的平台交易商是[" + firmID + "]");
		ReturnValue result = new ReturnValue();
		FirmValue fv = null;
		try {
			fv = DAO.getFirm(firmID);
		} catch (Exception e) {
			log("查询平台交易商[" + firmID + "]信息异常" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "平台系统查询异常";
		}
		if (fv == null) {
			log("没有在平台上查询到平台交易商[" + firmID + "]的相关信息");
			result.result = -1L;
			result.remark = "未找到交易商在平台的信息";
			return result;
		}
		if (inOut == 0) {
			FirmBalanceValue fbv = getMarketBalance(firmID);
			if (fbv.avilableBalance < money) {
				log("平台记录的可用于子账户之间互转的金额不够");
				result.result = -1L;
				result.remark = "平台记录的可用于子账户之间互转的金额不够";
				return result;
			}
			result.result = 0L;
		} else {
			result.result = 0L;
		}
		return result;
	}

	public boolean isTradeDate(String systemID) {
		log("判断子系统[" + systemID + "]当前是否是交易日");
		boolean result = false;
		Vector sm = null;
		try {
			sm = DAO.getSystemMessages(" where systemID='" + systemID + "'");
		} catch (SQLException e) {
			log("查询子系统信息异常");
		}
		if ((sm != null) && (sm.size() > 0)) {
			java.util.Date now = new java.util.Date();
			java.util.Date startTime = Tool.time2Today(((SystemMessage) sm.get(0)).startTime);
			java.util.Date endTime = Tool.time2Today(((SystemMessage) sm.get(0)).endTime);
			if ((now.after(startTime)) && (now.before(endTime))) {
				result = true;
			}
		}
		return result;
	}

	protected ReturnValue outMoneyCheck(String bankID, String firmID, double money, Timestamp time, Connection conn) {
		log("判断交易商是否满足设置的出金条件");
		log("bankID[" + bankID + "]firmID[" + firmID + "]money[" + money + "]time[" + time + "]");
		ReturnValue result = new ReturnValue();
		result.result = checkTrans(bankID, firmID, money, time, 1, conn);
		if (result.result == -30000L) {
			result.remark = "超出单笔最大转账金额";
			log("超出单笔最大转账金额");
		}
		if (result.result == -30002L) {
			result.remark = "超出当日最大转账金额";
			log("超出当日最大转账金额");
		}
		if (result.result == -30003L) {
			result.remark = "超出最大转账次数";
			log("超出最大转账次数");
		}
		return result;
	}

	protected TradeProcessorRMI getTradeCaptialProcessor(String systemID) {
		TradeProcessorRMI tradeProc = null;
		Vector vec = null;
		try {
			vec = DAO.getSystemMessages(" where systemID='" + systemID + "'");
			if ((vec == null) || (vec.size() <= 0)) {
				log("根据交易系统编号获取交易系统处理器RMI链接，未找到交易系统编号[" + systemID + "]的交易系统");
				return tradeProc;
			}
			tradeProc = (TradeProcessorRMI) Naming.lookup(((SystemMessage) vec.get(0)).rmiURL);
			System.out.println(tradeProc == null ? "获取的交易系统处理器为空" : "获取的交易系统处理器不为空");
		} catch (MalformedURLException e) {
			System.out.println("MalformedURLException:" + e.getMessage());
			e.printStackTrace();
		} catch (RemoteException e) {
			System.out.println("RemoteException:" + e.getMessage());
			e.printStackTrace();
		} catch (NotBoundException e) {
			System.out.println("NotBoundException:" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Exception" + e.getMessage());
			e.printStackTrace();
		}
		System.out.println("返回交易系统处理器对象信息" + tradeProc);
		return tradeProc;
	}

	private BankAdapterRMI getSysToSysAdapter() {
		BankAdapterRMI bankadapter = null;
		String RMIURL = Tool.getConfig("adapterURL");
		try {
			bankadapter = (BankAdapterRMI) Naming.lookup(RMIURL);
			System.out.println(bankadapter == null ? "获取的适配器为空" : "获取的适配器服务不为空");
		} catch (MalformedURLException e) {
			System.out.println("MalformedURLException:" + e.getMessage());
			e.printStackTrace();
		} catch (RemoteException e) {
			System.out.println("RemoteException:" + e.getMessage());
			e.printStackTrace();
		} catch (NotBoundException e) {
			System.out.println("NotBoundException:" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Exception" + e.getMessage());
			e.printStackTrace();
		}
		System.out.println("返回银行适配器对象信息" + bankadapter);
		return bankadapter;
	}

	public Vector<SystemMessage> getSystemMsg(String filter) {
		Vector sysMsg = null;
		try {
			sysMsg = DAO.getSystemMessages(filter);
		} catch (SQLException e) {
			log(Tool.getExceptionTrace(e));
		}
		return sysMsg;
	}

	public ReturnValue loginOrQuit(String systemID, int flag, String key) {
		log("平台处理器处理子系统[" + systemID + "]的" + (flag == 0 ? "签到" : "签退") + "业务");
		ReturnValue result = new ReturnValue();

		return result;
	}

	public Vector<FirmID2SysFirmID> getFirmID2SysFirmID(String filter) {
		Vector vec = null;
		try {
			vec = DAO.getFirmID2SysFirmID(filter);
		} catch (SQLException e) {
			log("查询平台交易商和子系统交易商对应关系异常:" + Tool.getExceptionTrace(e));
		}
		return vec;
	}

	public ReturnBalance getPTandBankBalance(CorrespondValue corr) {
		log("子系统发起查询余额");
		ReturnBalance rb = new ReturnBalance();
		FirmBalanceValue fv1 = getMarketBalance(corr.firmID);
		log("查询到平台端可用余额：" + fv1.avilableBalance);
		FirmBalanceValue fv2 = getBankBalance(corr.bankID, corr.firmID, corr.bankCardPassword);
		if (fv2 == null) {
			fv2 = new FirmBalanceValue();
		}
		log("查询到银行端可用余额：" + fv1.bankBalance);
		rb.ptBalance = fv1.avilableBalance;
		rb.bankBalance = fv2.bankBalance;
		return rb;
	}

	public ReturnValue inOutMoneyResultQuery(InOutMoney inOutMoney) {
		log("子系统[" + inOutMoney.systemID + "]发起的流水号为[" + inOutMoney.sysAciontID + "]的出入金结果查询");
		ReturnValue result = new ReturnValue();
		Vector vec = null;
		try {
			vec = DAO.getCapitalInfoListPT(" where funID='" + inOutMoney.sysAciontID + "' and systemID='" + inOutMoney.systemID + "' and type<>2");
		} catch (Exception e) {
			log("查询流水信息异常：" + Tool.getExceptionTrace(e));
			result.result = 5L;
			result.remark = "数据库异常";
		}
		if ((vec == null) || (vec.size() <= 0)) {
			log("没有查到子系统端流水号为[" + inOutMoney.sysAciontID + "]的流水");
			result.result = -1L;
			result.remark = "没有对应的流水";
			return result;
		}
		if (((CapitalValue) vec.get(0)).status == 0) {
			log("查询到流水状态为成功");
			result.result = 0L;
			result.remark = "该笔流水已经成功";
			result.actionId = ((CapitalValue) vec.get(0)).actionID;
		} else if (((CapitalValue) vec.get(0)).status == 1) {
			log("查询到流水状态为失败");
			result.result = -1L;
			result.remark = "改笔流水已经失败";
		} else {
			log("查询到流水状态为处理中或者其他状态");
			result.result = 5L;
			result.remark = "改笔流水处理中";
		}
		return result;
	}

	public Vector<CapitalValue> getCRJMX(String systemID) {
		Vector result = new Vector();
		Vector vecSys = null;
		try {
			vecSys = DAO.getSystemMessages(" where systemID='systemID'");
		} catch (SQLException e1) {
			log("查询子系统[" + systemID + "]信息异常");
		}
		if ((vecSys == null) || (vecSys.size() <= 0)) {
			log("没有查询到子系统[" + systemID + "]的相关信息");
			return result;
		}
		SystemMessage sm = (SystemMessage) vecSys.get(0);
		if (isTradeDate(systemID)) {
			log("系统还是签到状态");
			return result;
		}
		try {
			result = DAO.getCapitalInfoListPT(" where systemID='" + systemID + "' and type<>2 and status=0 and bankTime>=to_date('"
					+ Tool.fmtTime(Tool.time2Today(sm.startTime)) + "','yyyy-MM-dd HH:mm:ss') and bankTime<=to_date('"
					+ Tool.fmtTime(Tool.time2Today(sm.endTime)) + "','yyyy-MM-dd HH:mm:ss')");
		} catch (Exception e) {
			log("查询流水异常:" + Tool.getExceptionTrace(e));
		}
		return result;
	}

	public ReturnValue saveQSData(Vector<SystemQSValue> vec) {
		log("将子系统[" + ((SystemQSValue) vec.get(0)).systemID + "]发送的日终数据保存到数据库");
		ReturnValue result = new ReturnValue();
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			conn.setAutoCommit(false);
			for (int i = 0; i < vec.size(); i++) {
				String filter = " where firmid='" + ((SystemQSValue) vec.get(i)).firmID + "' and sysfirmid='" + ((SystemQSValue) vec.get(i)).sysFirmID
						+ "' and systemid='" + ((SystemQSValue) vec.get(i)).systemID + "' and bankcode='" + ((SystemQSValue) vec.get(i)).bankCode
						+ "' and tradedate=to_date('" + Tool.fmtDate(((SystemQSValue) vec.get(i)).tradeDate) + "','yyyy-MM-dd')";
				Vector vecHiven = DAO.getSystemQSData(filter, conn);
				if ((vecHiven != null) && (vecHiven.size() > 0)) {
					DAO.dellSystemQSData((SystemQSValue) vecHiven.get(0), conn);
				}
				DAO.addSystemQSData((SystemQSValue) vec.get(i), conn);
			}
			conn.commit();
			result.result = 0L;
			result.remark = "操作成功";
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				log("数据回滚异常：" + Tool.getExceptionTrace(e1));
			}
			log("系统异常:" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "插入数据库异常";
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public ReturnValue inOutMoneyResultNotice(InOutMoney inOutMoney, int inOrOut, int Flag) {
		ReturnValue result = new ReturnValue();
		BankAdapterRMI bankadapter = getSysToSysAdapter();
		if (bankadapter == null) {
			log("获取平台到子系统适配器失败");
			result.result = -1L;
			result.remark = "平台处理器获取平台到交易系统适配器失败";
			return result;
		}
		try {
			if (inOrOut == 0)
				result = bankadapter.inMoneyResultNotice(inOutMoney, Flag);
			else if (inOrOut == 1)
				result = bankadapter.outMoneyResultNotice(inOutMoney, Flag);
		} catch (RemoteException e) {
			log("调用平台到子系统适配器方法异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "调用平台到子系统适配器方法异常";
		}
		return result;
	}

	public ReturnValue getSystemDetailsFile(String systemID) {
		ReturnValue result = new ReturnValue();
		Vector vecSysMsg = null;
		try {
			vecSysMsg = DAO.getSystemMessages(" where systemID='" + systemID + "'");
		} catch (SQLException e) {
			log("查询交易系统信息数据库异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "查询交易系统信息数据库异常";
		}
		if ((vecSysMsg == null) || (vecSysMsg.size() <= 0)) {
			log("没有找到该市场" + systemID + "的对应信息");
			result.result = -1L;
			result.remark = ("没有找到该市场" + systemID + "的对应信息");
			return result;
		}
		SystemMessage sysMsg = (SystemMessage) vecSysMsg.get(0);
		if (isTradeDate(systemID)) {
			log("交易系统[" + systemID + "]还是交易时间");
			result.result = -1L;
			result.remark = ("交易系统[" + systemID + "]还是交易时间");
			return result;
		}
		if ((sysMsg.startTime == null) || (sysMsg.endTime == null) || ("".equals(sysMsg.startTime)) || ("".equals(sysMsg.endTime))) {
			log("交易系统[" + systemID + "]的状态不正确，信息不完整");
			result.result = -1L;
			result.remark = ("交易系统[" + systemID + "]的状态不正确，信息不完整");
			return result;
		}
		Vector vecCapital = null;
		try {
			String filter = " where systemID='" + systemID + "' and status=0 " + " and type<>2 and createtime>=to_date('"
					+ Tool.fmtTime(Tool.time2Today(sysMsg.startTime)) + "','yyyy-MM-dd hh24:mi:ss') " + " and createtime<=to_date('"
					+ Tool.fmtTime(Tool.time2Today(sysMsg.endTime)) + "','yyyy-MM-dd hh24:mi:ss')";
			vecCapital = DAO.getCapitalInfoListPT(filter);
		} catch (Exception e) {
			log("查询流水明细，数据库异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "查询平台到交易系统流水明细，数据库异常";
		}
		if ((vecCapital == null) || (vecCapital.size() < 0)) {
			log("查询出错");
			result.result = -1L;
			result.remark = "出现未知异常";
			return result;
		}
		BankAdapterRMI bankadapter = getSysToSysAdapter();
		if (bankadapter == null) {
			log("获取平台到子系统适配器失败");
			result.result = -1L;
			result.remark = "平台处理器获取平台到交易系统适配器失败";
			return result;
		}
		try {
			result = bankadapter.getSystemInOutMoneyDetails(vecCapital, Tool.time2Today(sysMsg.startTime), systemID);
		} catch (RemoteException e) {
			log("调用平台到子系统适配器方法异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "调用平台到子系统适配器方法异常";
		}
		if (result.result == 0L) {
			result.remark = "操作成功";
		} else {
			result.result = -1L;
			if ((result.remark == null) || ("".equals(result.remark))) {
				result.remark = "操作失败";
			}
		}
		return result;
	}

	public ReturnValue rgstAccount(CorrespondValue correspondValue, Connection conn) {
		ReturnValue result = new ReturnValue();
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("银行帐号注册");
		System.out.println("correspondValue[" + correspondValue + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}}");
		String defaultAccount = getConfig("DefaultAccount");
		if ((correspondValue.bankID == null) || (correspondValue.bankID.length() == 0) || (correspondValue.firmID == null)
				|| (correspondValue.firmID.length() == 0) || (correspondValue.account == null) || (correspondValue.account.length() == 0)) {
			log("注册信息不完整");
			result.result = -1L;
			result.remark = "注册信息不完成";
			return result;
		}
		try {
			if (DAO.getFirm(correspondValue.firmID, conn) == null) {
				log("交易商不存在");
				result.result = -1L;
				result.remark = "平台交易商不存在";
			} else if (DAO.getBank(correspondValue.bankID, conn) == null) {
				log("银行帐号注册，银行不存在");
				result.result = -1L;
				result.remark = "银行不存在";
			} else if (DAO.getCorrespond(correspondValue.bankID, correspondValue.firmID, correspondValue.account, conn) != null) {
				log("银行帐号注册，帐号已注册，错");
				result.result = -1L;
				result.remark = "账号已经注册";
			} else if (DAO.getCorrespondList(" where bankID='" + correspondValue.bankID + "' and firmID='" + correspondValue.firmID
					+ "' and (account='" + correspondValue.account + "' or account='" + defaultAccount + "') and isopen=0", conn).size() > 0) {
				BankAdapterRMI bankadapter = getAdapter(correspondValue.bankID);
				ReturnValue returnValue = bankadapter.rgstAccountQuery(correspondValue);
				if ((returnValue == null) || (returnValue.result == 0L)) {
					if ((correspondValue.bankID.equals("017")) || (correspondValue.bankID.equals("17"))) {
						correspondValue.account1 = returnValue.remark;
					}

					correspondValue.isOpen = 1;
					DAO.modCorrespond(correspondValue, conn);
					log("满足注册条件，修改为签约状态" + result);
				} else {
					log("银行帐号注册，银行签约失败");
					result.result = -1L;
					result.remark = "银行签约失败";
				}
			} else if (DAO.getCorrespondList(" where bankID='" + correspondValue.bankID + "' and firmID='" + correspondValue.firmID
					+ "' and account='" + correspondValue.account + "' and status=1 and isopen=1", conn).size() > 0) {
				DAO.modCorrespond(correspondValue, conn);
			} else if (DAO.getCorrespondList(" where bankID='" + correspondValue.bankID + "' and firmID='" + correspondValue.firmID
					+ "' and account='" + correspondValue.account + "'", conn).size() <= 0) {
				log("添加交易商");
				if (DAO.addCorrespond(correspondValue, conn) == 0) {
					log("添加交易商成功");
					result.result = 0L;
					result.remark = "添加交易商成功";
				} else {
					log("添加交易商失败");
					result.result = -1L;
					result.remark = "添加交易商失败";
				}
			} else {
				log("帐号已存在");
				result.result = -1L;
				result.remark = "账号已经存在";
			}
		} catch (Exception e) {
			log("注册账号异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "系统异常";
		}
		return result;
	}

	public ReturnValue openAccountMarket(CorrespondValue cv, Connection conn) {
		System.out.println(">>>>市场开户方法>>>>时间：" + Tool.fmtTime(new java.util.Date()) + ">>>>\ncv[" + cv.toString() + "]");
		long checkResult = chenckCorrespondValue(cv, conn);
		ReturnValue rv = new ReturnValue();
		CorrespondValue cv2 = null;
		if (checkResult == 0L) {
			rv.actionId = getMktActionID();
			try {
				cv.isOpen = 1;
				List cvresult = null;
				if (cv.bankID.equals("005"))
					cvresult = DAO.getCorrespondList(" where bankId='" + cv.bankID.trim() + "' and firmId='" + cv.firmID.trim() + "' ", conn);
				else {
					cvresult = DAO.getCorrespondList(" where bankId='" + cv.bankID.trim() + "' and firmId='" + cv.firmID.trim()
							+ "' and account like '%" + cv.account.trim() + "%'", conn);
				}
				if ((cvresult == null) || (cvresult.size() <= 0)) {
					rv.result = -40001L;
				} else {
					cv2 = (CorrespondValue) cvresult.get(0);
					cv2.isOpen = 1;
					cv2.status = cv.status;
					cv2.falg = cv.falg;
					cv2.signInfo = cv.signInfo;
					cv2.actionID = cv.actionID;
					BankAdapterRMI bankadapter = getAdapter(cv2.bankID);
					if (bankadapter == null) {
						rv.result = -920000L;
					} else {
						cv2.bankCardPassword = cv.bankCardPassword;

						rv = bankadapter.rgstAccountQuery(cv2);
						if (rv.result == 0L) {
							cv2.status = 0;
							if ((cv.bankID.equals("005")) || (cv.bankID.equals("17")) || ("21".equals(cv.bankID))) {
								cv2.bankCardPassword = null;
								cv2.account1 = rv.remark;
							}
							if (cv.bankID.equals("050")) {
								String sCustomer = rv.remark;
								String[] aa = sCustomer.split("\\|");
								cv2.account1 = aa[0];
								cv2.accountName = aa[1];
							}

							if (cv.bankID.equals("20")) {
								cv2.amount = getMarketBalance(cv2.firmID).avilableBalance;
							}
							rv.result = DAO.modCorrespond(cv2, conn);
						} else if ((rv.result == 5L) && (rv.type == 0)) {
							cv2.isOpen = 2;
							cv2.status = 0;
							rv.result = DAO.modCorrespond(cv2, conn);
						} else if ((rv.result == 5L) && (rv.type != 0)) {
							log("签约的银行[" + cv.bankID + "]不支持市场端签约");

							rv.result = 0L;
							rv.remark = "信息同步成功，还需要从银行端发起签约";
						}
					}
				}
			} catch (SQLException e) {
				rv.result = -40006L;
				e.printStackTrace();
				log("银行开户 交易商代码与银行帐号对应SQLException," + e);
			} catch (Exception e) {
				rv.result = -40007L;
				e.printStackTrace();
				log("银行开户 交易商代码与银行帐号对应SQLException," + e);
			}
		} else {
			rv.result = ((int) checkResult);
			rv.remark = "交易商代码与银行、帐号对应有误!";
		}
		return rv;
	}

	public ReturnValue fundsTransfer(InOutMoney outMoney, String systemID) {
		log("交易系统[" + outMoney.systemID + "]的交易商[" + outMoney.sysFirmID + "]向交易系统[" + systemID + "]划转资金[" + outMoney.money + "]");
		ReturnValue result = new ReturnValue();

		outMoney.actionID = getMktActionID();

		long capitalID = 0L;

		Connection conn = null;

		Timestamp curTime = new Timestamp(System.currentTimeMillis());
		try {
			conn = DAO.getConnection();
			ReturnValue localReturnValue1;
			if ((outMoney.firmID == null) || (outMoney.systemID == null) || (outMoney.sysFirmID == null) || ("".equals(outMoney.firmID))
					|| ("".equals(outMoney.systemID)) || ("".equals(outMoney.sysFirmID))) {
				log("市场间资金划转参数非法");
				result.result = -1L;
				result.remark = "参数错误";
				localReturnValue1 = result;
				return localReturnValue1;
			}

			Vector vecf2f = DAO.getFirmID2SysFirmID(
					" where firmID='" + outMoney.firmID + "' and sysFirmID='" + outMoney.sysFirmID + "' and systemID='" + outMoney.systemID + "'");
			if ((vecf2f == null) || (vecf2f.size() <= 0)) {
				log("没有找到平台交易商[" + outMoney.firmID + "]和子系统[" + outMoney.systemID + "]的交易商[" + outMoney.sysFirmID + "]关于银行[" + outMoney.bankID
						+ "]的绑定关系");
				result.result = -1L;
				result.remark = "交易商同平台的绑定关系不存在";
				localReturnValue1 = result;
				return localReturnValue1;
			}
			Vector vecf2f2 = DAO.getFirmID2SysFirmID(" where firmID='" + outMoney.firmID + "' and systemID='" + systemID + "'");
			if ((vecf2f2 == null) || (vecf2f2.size() <= 0)) {
				log("没有找到平台交易商[" + outMoney.firmID + "]和子系统[" + systemID + "]的交易商存在绑定关系");
				result.result = -1L;
				result.remark = "对应关系不存在不能划拨";
				localReturnValue1 = result;
				return localReturnValue1;
			}
			double canuseMoney = dataProcess.getRealFunds(outMoney.firmID, conn);
			if (canuseMoney < outMoney.money) {
				log("平台交易商[" + outMoney.firmID + "]资金余额[" + canuseMoney + "]不足够做金额[" + outMoney.money + "]的资金划转");
				result.result = -1L;
				result.remark = "余额不足";
				localReturnValue1 = result;
				return localReturnValue1;
			}

			CapitalValue cVal = null;
			try {
				conn.setAutoCommit(false);

				cVal = new CapitalValue();
				cVal.actionID = outMoney.actionID;
				cVal.bankID = outMoney.bankID;
				cVal.creditID = outMoney.firmID;
				cVal.debitID = ((String) dataProcess.getBANKSUB().get(outMoney.bankID));
				cVal.firmID = outMoney.firmID;
				cVal.money = outMoney.money;
				cVal.funID = outMoney.sysAciontID + "";
				cVal.bankTime = curTime;
				cVal.note = ("系统间资金划转_划出[" + outMoney.actionID + "]");
				cVal.oprcode = dataProcess.getOUTSUMMARY() + "";
				cVal.status = 0;
				cVal.type = 1;
				cVal.systemID = outMoney.systemID;
				cVal.sysFirmID = outMoney.sysFirmID;
				cVal.tradeSource = "0";

				DAO.addCapitalInfoPT(cVal, conn);
				result.funID = String.valueOf(outMoney.actionID);

				dataProcess.updateFrozenFunds(outMoney.firmID, outMoney.money, conn);

				cVal.note = ("系统间资金划转_划入[" + outMoney.actionID + "]");
				cVal.oprcode = dataProcess.getINSUMMARY() + "";
				cVal.type = 0;
				cVal.systemID = systemID;
				cVal.sysFirmID = ((FirmID2SysFirmID) vecf2f2.get(0)).sysFirmID;
				cVal.status = 2;
				cVal.funID = "";

				outMoney.actionID = getMktActionID();
				cVal.actionID = outMoney.actionID;
				cVal.tradeSource = "1";
				capitalID = DAO.addCapitalInfoPT(cVal, conn);

				conn.commit();
				result.result = 0L;
			} catch (Exception e) {
				e.printStackTrace();
				conn.rollback();
				result.result = -10026L;
				result.remark = "写入资金流水异常";
				log("市场端发起出金写资金流水SQLException,数据回滚:" + Tool.getExceptionTrace(e));
				throw e;
			} finally {
				conn.setAutoCommit(true);
			}
			if (result.result != 0L) {
				log("添加流水失败");
				result.result = -1L;
				result.remark = "添加流水失败";
				localReturnValue1 = result;
				return localReturnValue1;
			}

			TradeProcessorRMI tradeSystem = getTradeCaptialProcessor(systemID);
			if (tradeSystem == null) {
				log("获取交易系统[" + systemID + "]的处理器RMI服务为空");

				result.remark = "本系统转出成功，转入方接收失败，交易系统RMI处理器链接失败";
				localReturnValue1 = result;
				return localReturnValue1;
			}

			RequestMsg reqMsg = new RequestMsg();
			reqMsg.setMethodName("inMoney");
			Object[] params = new Object[1];
			outMoney.systemID = systemID;
			outMoney.sysFirmID = ((FirmID2SysFirmID) vecf2f2.get(0)).sysFirmID;
			outMoney.bankID = ((FirmID2SysFirmID) vecf2f2.get(0)).bankID;
			outMoney.sysAciontID = 0L;
			outMoney.remark = ("从系统[" + outMoney.systemID + "]转入");
			params[0] = outMoney;
			reqMsg.setParams(params);
			ReturnValue rv = tradeSystem.doWork(reqMsg);
			try {
				conn.setAutoCommit(false);
				if (rv.result == 0L) {
					log("转出系统转出成功，转入系统转入成功");
					result.remark = "成功转入对方系统";
					DAO.modCapitalInfoStatusPT(capitalID, rv.funID, 0, curTime, conn);

					dataProcess.updateFrozenFunds(outMoney.firmID, -1.0D * outMoney.money, conn);
				} else {
					log("转出系统转出成功，转入系统转入失败，失败原因：" + rv.remark);
					result.remark = ("转出成功，转入对方系统失败：" + rv.remark);
				}
				conn.commit();
			} catch (Exception e) {
				e.printStackTrace();
				conn.rollback();
				log("市场端发起出金,系统异常Exception,数据回滚:" + Tool.getExceptionTrace(e));
			} finally {
				conn.setAutoCommit(true);
			}
		} catch (Exception e) {
			log("平台处理出金异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "平台处理出金发生异常";
		} finally {
			DAO.closeStatement(null, null, conn);
		}

		return result;
	}

	public Vector<SystemMessage> getSystem(String systemID, String sysFirmID, String firmID) {
		Vector vec = new Vector();

		Vector<FirmID2SysFirmID> vecf2f = null;
		try {
			vecf2f = DAO.getFirmID2SysFirmID(" where firmID='" + firmID + "' and systemID='" + systemID + "' and sysfirmid='" + sysFirmID + "'");
		} catch (SQLException e) {
			log("查询交易商对应关系异常:" + Tool.getExceptionTrace(e));
		}
		if ((vecf2f == null) || (vecf2f.size() <= 0)) {
			log("验证交易商绑定关系失败，不存在对应的绑定关系，不允许做资金划转");
			return vec;
		}
		try {
			vecf2f = DAO.getFirmID2SysFirmID(" where firmID='" + firmID + "'");
		} catch (SQLException e1) {
			log("查询交易商对应关系异常:" + Tool.getExceptionTrace(e1));
		}
		if ((vecf2f == null) || (vecf2f.size() <= 0)) {
			log("验证交易商绑定关系失败，不存在对应的绑定关系，不允许做资金划转");
			return vec;
		}

		String filter = " where systemID in (";
		for (FirmID2SysFirmID ff : vecf2f) {
			filter = filter + "'" + ff.systemID + "',";
		}
		filter = filter + "'')";
		try {
			vec = DAO.getSystemMessages(filter);
		} catch (SQLException e) {
			log("查询交易系统信息异常:" + Tool.getExceptionTrace(e));
		}
		return vec;
	}

	public ReturnValue contactUsed(String contact, String bankID) {
		ReturnValue result = new ReturnValue();
		Vector vec = null;
		try {
			vec = DAO.getCorrespondList(" where contact='" + contact + "' and bankid='" + bankID + "'");
		} catch (Exception e) {
			Tool.log("查询交易商账号对应关系异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "系统异常";
		}
		if (result.result == -1L)
			return result;
		if ((vec == null) || (vec.size() <= 0)) {
			result.result = 0L;
			result.remark = "该签约号可以签约该银行";
		} else {
			result.result = 1L;
			result.remark = "该签约号在该银行已经被使用";
		}
		return result;
	}

	public ReturnValue outMoneyAudit(long actionID, String systemID, String sysActionID, boolean Flag) {
		ReturnValue result = new ReturnValue();

		return result;
	}

	public ReturnValue associatedFirmInfo(String firmID, String tradeFirmID, String oldFirmID, String systemID) throws RemoteException {
		log("交易系统发起关联账号，交易账号[" + tradeFirmID + "]原平台号[" + oldFirmID + "]关联平台号[" + firmID + "]交易系统编号[" + systemID + "]");
		ReturnValue rv = new ReturnValue();
		rv.result = -1L;
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			conn.setAutoCommit(false);

			Vector vec = DAO
					.getFirmID2SysFirmID(" where sysfirmid='" + tradeFirmID + "' and firmid='" + oldFirmID + "' and systemid='" + systemID + "'");
			ReturnValue localReturnValue1;
			if ((vec == null) || (vec.size() == 0)) {
				rv.remark = "账号不存在";
				conn.rollback();
				localReturnValue1 = rv;
				return localReturnValue1;
			}
			Vector vec3 = DAO.getFirmID2SysFirmID(" where firmid='" + oldFirmID + "'");
			if ((vec3 != null) && (vec3.size() > 1)) {
				log("发起方的平台号[" + oldFirmID + "]和多个交易系统存在对应关系，不允许发起关联");
				rv.result = -1L;
				rv.remark = ("平台号[" + oldFirmID + "]和多个交易系统存在对应关系，不允许发起关联");
				conn.rollback();
				localReturnValue1 = rv;
				return localReturnValue1;
			}

			FirmValue fv = DAO.getFirm(firmID);
			if (fv == null) {
				log("被关联的平台号[" + firmID + "]不存在");
				rv.result = -1L;
				rv.remark = "被关联的平台号不存在";
				conn.rollback();
				localReturnValue1 = rv;
				return localReturnValue1;
			}

			Vector vec2 = DAO.getFirmID2SysFirmID(" where firmid='" + firmID + "' and systemid='" + systemID + "'");
			if ((vec2 != null) && (vec2.size() > 0)) {
				log("被关联的平台号[" + firmID + "]和交易系统[" + systemID + "]已经存在了对应关系，不允许再次关联");
				rv.result = -1L;
				rv.remark = ("被关联平台号已经和发起交易系统的交易商[" + ((FirmID2SysFirmID) vec2.get(0)).sysFirmID + "]存在对应关系");
				conn.rollback();
				localReturnValue1 = rv;
				return localReturnValue1;
			}

			Vector corr = DAO.getCorrespondList(" where bankid in (select bankid from f_b_firmidandaccount where isopen=1 and firmid='" + firmID
					+ "') and isopen=1 and firmid='" + oldFirmID + "'");
			if ((corr != null) && (corr.size() > 0)) {
				rv.remark = ("交易账号[" + oldFirmID + "]与被关联平台号[" + firmID + "]存在相同银行签约关系");
				conn.rollback();
				localReturnValue1 = rv;
				return localReturnValue1;
			}

			Vector corr1 = DAO.getCorrespondList(" where bankid in (select bankid from f_b_firmidandaccount where isopen=1 and firmid='" + oldFirmID
					+ "') and isopen=1 and firmid='" + firmID + "'");
			if ((corr1 != null) && (corr1.size() > 0)) {
				rv.remark = ("交易账号[" + oldFirmID + "]与被关联平台号[" + firmID + "]存在相同银行签约关系");
				conn.rollback();
				localReturnValue1 = rv;
				return localReturnValue1;
			}

			Vector bcap = DAO.getCapitalInfoList(" where firmid in ('" + oldFirmID + "','" + firmID + "')");
			if ((bcap == null) || (bcap.size() == 0)) {
				rv.result = -1L;
				rv.remark = "当日有银行资金转账不允许合并账号";
			}
			Vector mcap = DAO.getCapitalInfoListPT(" where firmid in ('" + oldFirmID + "','" + firmID + "')");
			if ((mcap == null) || (mcap.size() == 0)) {
				rv.result = -1L;
				rv.remark = "当日有系统间资金转账不允许合并账号";
			}

			FirmBalanceValue fbv = getMarketBalance(oldFirmID);
			if (fbv.frozenBalance != 0.0D) {
				log("平台号[" + oldFirmID + "]还有冻结资金[" + fbv.frozenBalance + "],不允许发起关联");
				rv.result = -1L;
				rv.remark = ("平台号[" + oldFirmID + "]还有冻结资金，不允许发起关联");
				conn.rollback();
				localReturnValue1 = rv;
				return localReturnValue1;
			}

			if (fbv.avilableBalance != 0.0D) {
				try {
					dataProcess.updateFundsFull(firmID, "101", "10201", fbv.avilableBalance, getMktActionID(), conn);
					dataProcess.updateFundsFull(oldFirmID, "102", "10201", fbv.avilableBalance, getMktActionID(), conn);
					rv.result = 0L;
				} catch (Exception e) {
					log("将执行操作的账号资金资金转移到被关联账号上异常：" + Tool.getExceptionTrace(e));
					rv.result = -1L;
				}
				if (rv.result != 0L) {
					log("将执行操作的账号资金资金转移到被关联账号上失败");
					rv.remark = "平台号之间转资金失败";
					conn.rollback();
					localReturnValue1 = rv;
					return localReturnValue1;
				}
			}

			FirmID2SysFirmID fsf = (FirmID2SysFirmID) vec.get(0);
			fsf.note = ("该条对应关系的平台号[" + fsf.firmID + "]在[" + Tool.fmtTime(new java.util.Date()) + "]被修改为新的平台号[" + firmID + "]");
			rv.result = DAO.addFirmID2SysFirmIDHis(fsf, conn);
			if (rv.result != 1L) {
				log("备份原有平台号和交易商的对应关系失败");
				rv.result = -1L;
				rv.remark = "备份历史信息失败";
				conn.rollback();
				localReturnValue1 = rv;
				return localReturnValue1;
			}

			FirmID2SysFirmID newfsf = (FirmID2SysFirmID) vec.get(0);
			newfsf.firmID = firmID;
			if (DAO.modFirmID2SysFirmID(fsf, newfsf, conn) != 1L) {
				log("交易系统与平台账号对应关系更新失败");
				rv.result = -1L;
				rv.remark = "交易系统与平台账号对应关系更新失败";
				conn.rollback();
				localReturnValue1 = rv;
				return localReturnValue1;
			}

			Vector vecCorr = DAO.getCorrespondList(" where firmID='" + oldFirmID + "' and bankID='" + fsf.bankID + "'", conn);
			if ((vecCorr == null) || (vecCorr.size() <= 0)) {
				log("旧的平台号[" + oldFirmID + "]和银行[" + fsf.bankID + "]的签约关系不存在");
				rv.result = -1L;
				rv.remark = ("平台号[" + oldFirmID + "]和银行[" + fsf.bankID + "]的签约关系不存在");
				conn.rollback();
				localReturnValue1 = rv;
				return localReturnValue1;
			}

			CorrespondValue cv = (CorrespondValue) vecCorr.get(0);
			CorrespondValue cvNew = (CorrespondValue) vecCorr.get(0);
			cvNew.firmID = firmID;
			rv.result = DAO.modCorrespond(cv, cvNew, conn);
			if (rv.result != 1L) {
				log("将平台号和银行账号的对应关系表中的旧平台号[" + cv.firmID + "]修改成新的平台号[" + firmID + "]失败");
				rv.result = -1L;
				rv.remark = "替换平台号失败";
				conn.rollback();
				localReturnValue1 = rv;
				return localReturnValue1;
			}

			TradeProcessorRMI tradeSystem = getTradeCaptialProcessor(systemID);
			if (tradeSystem == null) {
				log("获取交易系统处理器RMI失败");
				rv.result = -1L;
				rv.remark = "获取交易系统处理器失败";
				conn.rollback();
				localReturnValue1 = rv;
				return localReturnValue1;
			}

			RequestMsg req = new RequestMsg();
			req.setMethodName("modPlatformNum");
			req.setParams(new Object[] { tradeFirmID, firmID });
			rv = tradeSystem.doWork(req);
			if (rv.result != 0L) {
				log("交易系统修改平台号失败");
				rv.result = -1L;
				rv.remark = "交易系统端修改平台号失败";
				conn.rollback();
				localReturnValue1 = rv;
				return localReturnValue1;
			}

			conn.commit();
		} catch (SQLException e) {
			log(Tool.getExceptionTrace(e));
		} catch (ClassNotFoundException e) {
			log(Tool.getExceptionTrace(e));
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return rv;
	}

	public ReturnValue getSystemMsgs(String firmID) {
		ReturnValue result = new ReturnValue();

		Vector<FirmID2SysFirmID> vec = null;
		try {
			vec = DAO.getFirmID2SysFirmID(" where firmID='" + firmID + "'");
		} catch (SQLException e1) {
			log("查询平台交易商和交易系统交易商签约对应关系异常：" + Tool.getExceptionTrace(e1));
			result.result = -1L;
			result.remark = "查询平台交易商和交易系统交易商签约对应关系异常";
		}
		if ((vec == null) || (vec.size() <= 0)) {
			log("没有查询到平台号[" + firmID + "]对应的交易系统签约记录");
			result.result = -1L;
			result.remark = "无对应的记录";
			return result;
		}

		String filter = " where systemID in (";
		for (FirmID2SysFirmID f2f : vec) {
			filter = filter + "'" + f2f.systemID + "',";
		}
		filter = filter.substring(0, filter.length() - 1) + ")";

		Vector sysMsgs = new Vector();
		try {
			sysMsgs = DAO.getSystemMessages(filter);
		} catch (SQLException e) {
			log("查询平台交易商和交易系统交易商签约对应关系异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "平台系统异常";
		}
		if ((sysMsgs == null) || (sysMsgs.size() <= 0)) {
			log("没有查询到交易系统信息");
			result.result = -1L;
			result.remark = "没有查询到交易系统信息";
			return result;
		}
		result.result = 0L;
		result.remark = "查询成功";
		result.msg = new Object[] { sysMsgs };
		return result;
	}

	public ReturnValue sentRZQS(Vector<RZQS> rzqs, String systemID) {
		log("收到交易系统[" + systemID + "]清算数据[" + rzqs.size() + "]");
		ReturnValue rv = new ReturnValue();
		rv.result = -1L;
		if ((systemID == null) || ("".equals(systemID))) {
			rv.remark = "交易系统模块号不允许为空!";
			return rv;
		}
		if (rzqs.size() < 1) {
			rv.remark = "清算数据为空";
			return rv;
		}
		try {
			int i = DAO.addRZQS(rzqs, systemID);
			log("交易系统[" + systemID + "]清算数据[" + rzqs.size() + "]保存成功数据[" + i + "]条");

			rv.result = 0L;
			rv.remark = ("交易系统[" + systemID + "]清算数据[" + rzqs.size() + "]保存成功数据[" + i + "]条");
		} catch (SQLException e) {
			log(Tool.getExceptionTrace(e));
			rv.remark = "系统异常";
		} catch (ClassNotFoundException e) {
			log(Tool.getExceptionTrace(e));
			rv.remark = "系统异常";
		}
		return rv;
	}

	public ReturnValue getFirmBalances(String firmID, String bankID, String bankPwd, String pwd) {
		ReturnValue result = new ReturnValue();
		FirmBalanceValue fbv = getFirmBalance(bankID, firmID, bankPwd);
		if (fbv == null) {
			log("查询平台和银行余额失败");
			result.result = -1L;
			result.remark = "查询平台和银行余额失败";
			return result;
		}
		result.result = 0L;
		result.remark = "查询成功";
		result.msg = new Object[] { Double.valueOf(fbv.avilableBalance), Double.valueOf(fbv.bankBalance) };
		return result;
	}

	public ReturnValue allotbankfunds(java.util.Date date) {
		log("====>执行资金结算date[" + date.toLocaleString() + "]" + new java.util.Date());
		ReturnValue rv = new ReturnValue();
		Connection conn = null;
		long i = 0L;
		try {
			conn = DAO.getConnection();
			conn.setAutoCommit(false);

			Vector vec = DAO.checkRZQSData(date);
			ReturnValue localReturnValue1;
			if ((vec == null) || (vec.size() <= 0)) {
				Tool.log("平台不存在交易系统[" + Tool.fmtDate(date) + "]的日终数据，不允许结算");
				rv.result = -1L;
				rv.remark = ("平台没有[" + Tool.fmtDate(date) + "]的日终数据，不允许做改日资金结算");
				localReturnValue1 = rv;
				return localReturnValue1;
			}

			int del = DAO.delPlatRzqs(date, conn);
			log("删除数据[" + del + "]条");
			if (del < 0) {
				Tool.log("删除数据异常，数据回滚");
				conn.rollback();
				rv.result = -1L;
				rv.remark = "结算失败";
				localReturnValue1 = rv;
				return localReturnValue1;
			}

			int add = DAO.addPlatRzqs(date, conn);
			if (add < 0) {
				Tool.log("插入数据异常，数据回滚");
				conn.rollback();
				rv.result = -1L;
				rv.remark = "结算失败";
				localReturnValue1 = rv;
				return localReturnValue1;
			}

			log("写入数据[" + add + "]条");
			i = dataProcess.allotbankfunds(date, conn);
			rv.result = i;

			if (rv.result == 1L) {
				Tool.log("执行结算成功，提交数据");
				conn.commit();
			}
		} catch (Exception e) {
			rv.result = -1L;
			rv.remark = "资金结算异常";
			try {
				conn.rollback();
			} catch (SQLException e1) {
				Tool.log("回滚数据异常：" + Tool.getExceptionTrace(e1));
			}
			log(Tool.getExceptionTrace(e));
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return rv;
	}

	public ReturnValue getOpenBanks(String firmID) {
		ReturnValue result = new ReturnValue();

		Vector<CorrespondValue> corrVec = null;
		try {
			corrVec = DAO.getCorrespondList(" where firmID='" + firmID + "' and isOpen=1");
		} catch (Exception e) {
			log("查询绑定关系异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "查询绑定关系异常";
		}
		if ((corrVec == null) || (corrVec.size() <= 0)) {
			log("没有查询到平台号[" + firmID + "]有已经签约成功的银行");
			result.result = -1L;
			result.remark = "该平台号没有签约成功的银行";
			return result;
		}

		String filter = " where bankID in (";
		for (CorrespondValue cv : corrVec) {
			filter = filter + "'" + cv.bankID + "',";
		}
		filter = filter.substring(0, filter.length() - 1) + ")";
		Vector vec = null;
		try {
			vec = DAO.getBankList(filter);
		} catch (Exception e) {
			log("查询银行信息异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "查询银行信息异常";
			return result;
		}
		if ((vec == null) || (vec.size() <= 0)) {
			log("没有查询到银行信息");
			result.result = -1L;
			result.remark = "没有查询到银行信息";
			return result;
		}
		result.result = 0L;
		result.remark = "查询成功";
		result.msg = new Object[] { vec };
		return result;
	}

	public ReturnValue getBankList(String filter) {
		Tool.log("查询银行列表信息");
		ReturnValue result = new ReturnValue();
		Vector vec = null;
		try {
			vec = DAO.getBankList(filter);
		} catch (Exception e) {
			Tool.log("查询银行列表信息异常：" + Tool.getExceptionTrace(e));
		}
		if ((vec == null) || (vec.size() <= 0)) {
			Tool.log("查询银行信息失败");
			result.result = -1L;
			result.remark = "查询银行列表信息失败";
			return result;
		}
		result.result = 0L;
		result.remark = "查询银行信息成功";
		result.msg = new Object[] { vec };
		return result;
	}

	public ReturnValue getSystemMessage(String filter) {
		Tool.log("获取平台接入的交易系统信息");
		ReturnValue result = new ReturnValue();
		Vector vec = null;
		try {
			vec = DAO.getSystemMessages(filter);
		} catch (SQLException e) {
			Tool.log("查询异常：" + Tool.getExceptionTrace(e));
		}
		if ((vec == null) || (vec.size() <= 0)) {
			Tool.log("查询平台接入的交易系统信息失败");
			result.result = -1L;
			result.remark = "查询失败";
			return result;
		}
		result.result = 0L;
		result.remark = "查询成功";
		result.msg = new Object[] { vec };
		return result;
	}

	public String getFirmID(String contact, String bankID) {
		Tool.log("根据签约号[" + contact + "]和银行[" + bankID + "]获取平台号");
		String result = "";
		try {
			Vector vec = DAO.getCorrespondList(" where contact='" + contact + "' and bankid= '" + bankID + "'");
			if ((vec == null) || (vec.size() <= 0))
				Tool.log("根据银行[" + bankID + "]和签约号[" + contact + "]获取平台号失败");
			else
				result = ((CorrespondValue) vec.get(0)).firmID;
		} catch (Exception e) {
			Tool.log("根据银行[" + bankID + "]和签约号[" + contact + "]获取平台号异常：" + Tool.getExceptionTrace(e));
		}
		return result;
	}
}