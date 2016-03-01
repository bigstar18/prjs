package gnnt.MEBS.bank.front.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import gnnt.MEBS.bank.front.model.Bank;
import gnnt.MEBS.bank.front.model.FirmIDAndAccount;
import gnnt.MEBS.common.front.action.StandardAction;
import gnnt.MEBS.common.front.common.Page;
import gnnt.MEBS.common.front.common.PageRequest;
import gnnt.MEBS.common.front.common.QueryConditions;
import gnnt.MEBS.common.front.model.StandardModel;
import gnnt.MEBS.common.front.model.integrated.MFirm;
import gnnt.MEBS.common.front.model.integrated.User;
import gnnt.MEBS.common.front.statictools.ActionUtil;
import gnnt.MEBS.common.front.statictools.ApplicationContextInit;
import gnnt.MEBS.common.front.statictools.Tools;
import gnnt.bank.platform.util.CardType;
import gnnt.bank.platform.util.Tool;
import gnnt.bank.platform.vo.CheckMessage;
import gnnt.bank.platform.vo.RequestMsg;
import gnnt.bank.trade.rmi.TradeProcessorRMI;
import gnnt.trade.bank.vo.AbcInfoValue;
import gnnt.trade.bank.vo.BankValue;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.InOutMoney;
import gnnt.trade.bank.vo.ReturnValue;
import gnnt.trade.bank.vo.SystemMessage;

@Controller("moneyAction")
@Scope("request")
public class MoneyAction extends StandardAction {
	private static final long serialVersionUID = -8408589201557981299L;
	@Resource(name = "capitalInfoType")
	private Map<Integer, String> capitalInfoType;
	@Resource(name = "capitalInfoStatus")
	private Map<Integer, String> capitalInfoStatus;
	@Autowired
	@Qualifier("capitalProcessorRMI")
	private TradeProcessorRMI capitalProcessorRMI;

	public Map<Integer, String> getCapitalInfoType() {
		return this.capitalInfoType;
	}

	public Map<Integer, String> getCapitalInfoStatus() {
		return this.capitalInfoStatus;
	}

	public String gotoChangePasswordPage() {
		User user = (User) this.request.getSession().getAttribute("CurrentUser");

		return "success";
	}

	public String changePassword() {
		User user = (User) this.request.getSession().getAttribute("CurrentUser");
		String oldpwd = this.request.getParameter("oldpwd");
		String newpwd = this.request.getParameter("newpwd");

		String FIRMID = user.getBelongtoFirm().getFirmID();
		String oldpassword = this.request.getParameter("oldpwd");
		String newpassword = this.request.getParameter("newpwd");
		ReturnValue result = new ReturnValue();

		String mgs = "";
		if (FIRMID != null) {
			try {
				RequestMsg req = new RequestMsg();
				req.setBankID("");
				req.setMethodName("modFundsPwd");
				req.setParams(new Object[] { FIRMID, oldpassword, newpassword });
				result = this.capitalProcessorRMI.doWork(req);
				if (result.result >= 0L) {
					mgs = "修改密码成功";
				} else if (("".equals(result.remark)) || (result.remark == null)) {
					mgs = "修改密码失败";
				} else {
					mgs = result.remark;
				}
			} catch (Exception e) {
				mgs = "修改密码时系统出现异常";
			}
		} else {
			mgs = "您的登录已经失效，请重新登录";
		}
		addReturnValue(1, 130000L, new Object[] { mgs });
		return "success";
	}

	public String gotoMoneyPage() {
		User user = (User) this.request.getSession().getAttribute("CurrentUser");

		ReturnValue result = new ReturnValue();
		RequestMsg req1 = new RequestMsg();
		req1.setBankID("");
		req1.setMethodName("getIsOpenBanks");
		req1.setParams(new Object[] { user.getBelongtoFirm().getFirmID() });
		try {
			result = this.capitalProcessorRMI.doWork(req1);
		} catch (Exception e) {
			this.logger.info("调用平台出错");
			e.printStackTrace();
		}
		Vector<BankValue> vecBanks = null;
		if (result.result == 0L) {
			vecBanks = (Vector) result.msg[0];
		} else {
			vecBanks = new Vector();
		}
		this.request.setAttribute("vecBanks", vecBanks);
		RequestMsg req = new RequestMsg();
		req.setBankID("");
		req.setMethodName("getRelationSystem");
		req.setParams(new Object[] { user.getBelongtoFirm().getFirmID() });
		try {
			result = this.capitalProcessorRMI.doWork(req);
		} catch (RemoteException e) {
			Tool.log("调用平台处理器方法异常：" + Tool.getExceptionTrace(e));
			result.remark = "调用平台处理器方法异常";
			result.msg = new Object[] { new Vector() };
		}
		Vector<SystemMessage> ves = (Vector) result.msg[0];
		this.request.setAttribute("collectionSys", result.msg[0]);

		return "success";
	}

	public String gotoSysFundstranferPage() {
		this.logger.info("进入系统间资金划转页面");
		User user = (User) this.request.getSession().getAttribute("CurrentUser");

		ReturnValue result = new ReturnValue();
		RequestMsg req = new RequestMsg();
		req.setBankID("");
		req.setMethodName("getRelationSystem");
		req.setParams(new Object[] { user.getBelongtoFirm().getFirmID() });
		try {
			result = this.capitalProcessorRMI.doWork(req);
		} catch (RemoteException e) {
			Tool.log("调用平台处理器方法异常：" + Tool.getExceptionTrace(e));
			result.remark = "调用平台处理器方法异常";
			result.msg = new Object[] { new Vector() };
		}
		Vector<SystemMessage> ves = (Vector) result.msg[0];
		this.request.setAttribute("collectionSys", result.msg[0]);

		return "success";
	}

	public String moneyTransfer() {
		String bankID = this.request.getParameter("bankID");
		String SystemId = this.request.getParameter("collectionSys");
		this.logger.info("SystemID===========================" + SystemId);
		if ((bankID == null) || (bankID.trim().length() <= 0)) {
			addReturnValue(-1, 2830101L);
			return "success";
		}
		User user = (User) this.request.getSession().getAttribute("CurrentUser");

		String money = this.request.getParameter("money");
		if ((money == null) || ("".equals(money)) || (Double.parseDouble(money) <= 0.0D)) {
			addReturnValue(-1, 2830102L);
			return "success";
		}
		String password = this.request.getParameter("password");
		String bankPwd = this.request.getParameter("bankPwd");

		String inoutMoney = this.request.getParameter("inoutMoney");
		System.out.println("==============出入金标识=======" + inoutMoney + "======");
		ReturnValue result = new ReturnValue();
		RequestMsg req = new RequestMsg();
		String RequestID = this.request.getParameter("RequestID");
		String RequestIDpay = this.request.getParameter("RequestIDpay");
		String CustSignInfo = this.request.getParameter("CustSignInfo");

		CorrespondValue corr = null;
		req.setBankID(bankID);
		req.setMethodName("getCorrespondValueInfo");
		req.setParams(new Object[] { user.getBelongtoFirm().getFirmID(), bankID });
		try {
			result = this.capitalProcessorRMI.doWork(req);
		} catch (RemoteException e1) {
			this.logger.info("调用处理器查询有关银行签约的特殊设置异常：" + Tool.getExceptionTrace(e1));
		}
		if ((result == null) || (result.result != 0L)) {
			addReturnValue(1, 130000L, new Object[] { "获取签约关系失败" });
			return "success";
		}
		corr = (CorrespondValue) ((Vector) result.msg[0]).get(0);
		if ((RequestID == null) || ("".equals(RequestID))) {
			req.setMethodName("getCheckMsg");
			req.setParams(new Object[] { bankID, "1" });
			try {
				result = this.capitalProcessorRMI.doWork(req);
			} catch (RemoteException e1) {
				this.logger.info("调用处理器查询有关银行签约的特殊设置异常：" + Tool.getExceptionTrace(e1));
			}
			if ((result != null) && (result.result == 0L)) {
				Vector<CheckMessage> vec = (Vector) result.msg[0];
				if ((vec != null) && (vec.size() > 0)) {
					this.logger.info("银行[" + bankID + "]有特殊的签约设置");

					req.setMethodName("getActionID");
					req.setParams(null);
					try {
						result = this.capitalProcessorRMI.doWork(req);
					} catch (RemoteException e) {
						this.logger.info("调用处理器获取流水号异常：" + Tool.getExceptionTrace(e));
					}
					if ((result == null) || (result.result != 0L)) {
						this.logger.info("调用处理器获取流水号失败");
						addReturnValue(1, 130000L, new Object[] { "获取流水号失败" });
						return "success";
					}
					RequestID = String.valueOf(result.actionId);
					try {
						result = this.capitalProcessorRMI.doWork(req);
					} catch (RemoteException e) {
						this.logger.info("调用处理器获取流水号异常：" + Tool.getExceptionTrace(e));
					}
					if ((result == null) || (result.result != 0L)) {
						this.logger.info("调用处理器获取流水号失败");
						addReturnValue(1, 130000L, new Object[] { "获取流水号失败" });
						return "success";
					}
					RequestIDpay = String.valueOf(result.actionId);
					this.request.setAttribute("RequestID", RequestID);
					this.request.setAttribute("RequestIDpay", RequestIDpay);
					this.request.setAttribute("accountMame", corr.accountName);
					this.request.setAttribute("addOrDel", "2");
					return bankID;
				}
			}
		}
		InOutMoney inOutMoney = new InOutMoney();
		inOutMoney.sysFirmID = user.getBelongtoFirm().getFirmID();
		inOutMoney.bankID = bankID;
		inOutMoney.systemID = SystemId;
		inOutMoney.money = Double.parseDouble(money);
		inOutMoney.inOutMoneyFlag = inoutMoney;
		inOutMoney.accountPwd = bankPwd;
		inOutMoney.pwd = password;
		AbcInfoValue value = new AbcInfoValue();
		value.firmID = corr.firmID;
		value.account1 = corr.account1;
		value.orderNo = RequestID;
		value.actionID = Long.parseLong((RequestIDpay == null) || ("".equals(RequestIDpay)) ? "0" : RequestIDpay);
		if (String.valueOf(1).equals(inoutMoney)) {
			value.type = 1;
		} else if (String.valueOf(0).equals(inoutMoney)) {
			value.type = 0;
		}
		value.signInfo = CustSignInfo;
		inOutMoney.abcInfo = value;
		inOutMoney.actionID = Long.parseLong((RequestID == null) || ("".equals(RequestID)) ? "0" : RequestID);
		req.setBankID("");
		req.setMethodName("inOutMoneyRequest");
		req.setParams(new Object[] { inOutMoney });
		try {
			result = this.capitalProcessorRMI.doWork(req);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		if ((result != null) && ((result.remark == null) || ("".equals(result.remark)))) {
			if (result.result == 0L) {
				result.remark = (String.valueOf(0).equals(inoutMoney) ? "入金成功" : "出金成功");
			} else if (result.result == 5L) {
				result.remark = (String.valueOf(0).equals(inoutMoney) ? "入金处理中" : "出金处理中");
			} else {
				result.remark = (String.valueOf(0).equals(inoutMoney) ? "入金失败" : "出金失败");
			}
		}
		if ((result.result == 0L) && (String.valueOf(0).equals(inoutMoney))) {
			result.remark = "入金成功";
			addReturnValue(1, 9930092L, new Object[] { result.remark });
		} else if ((result.result == 0L) && (String.valueOf(1).equals(inoutMoney))) {
			result.remark = "出金成功";
			addReturnValue(1, 9930092L, new Object[] { result.remark });
		} else {
			addReturnValue(-1, 9930092L, new Object[] { result.remark });
		}
		return "success";
	}

	public String gotoQueryMoneyPage() {
		User user = (User) this.request.getSession().getAttribute("CurrentUser");

		ReturnValue result = new ReturnValue();

		RequestMsg req = new RequestMsg();
		req.setBankID("");
		req.setMethodName("getIsOpenBanks");
		req.setParams(new Object[] { user.getBelongtoFirm().getFirmID() });
		try {
			result = this.capitalProcessorRMI.doWork(req);
		} catch (Exception e) {
			this.logger.info("调用平台出错");
			e.printStackTrace();
		}
		Vector<BankValue> vecBanks = null;
		if (result.result == 0L) {
			vecBanks = (Vector) result.msg[0];
		} else {
			vecBanks = new Vector();
		}
		this.request.setAttribute("vecBanks", vecBanks);

		boolean isCheckMarketPassword = Tools.strToBoolean(ApplicationContextInit.getConfig("isCheckMarketPassword"));
		this.request.setAttribute("isCheckMarketPassword", Boolean.valueOf(isCheckMarketPassword));

		String queryMoneyNeedPasswordBankID = ApplicationContextInit.getConfig("queryMoneyNeedPasswordBankID");
		this.request.setAttribute("queryMoneyNeedPasswordBankID", queryMoneyNeedPasswordBankID);

		return "success";
	}

	public String queryMoney() {
		String bankID = this.request.getParameter("bankID");
		if ((bankID == null) || (bankID.trim().length() <= 0)) {
			addReturnValue(-1, 2830101L);
			return "success";
		}
		User user = (User) this.request.getSession().getAttribute("CurrentUser");
		String password = this.request.getParameter("password");
		String bankPwd = this.request.getParameter("bankPwd");
		ReturnValue returnValue = null;
		String bankAccount = "";
		double bankBalance = 0.0D;
		double platBalance = 0.0D;

		RequestMsg req = new RequestMsg();
		req.setBankID(bankID);
		req.setMethodName("getFirmBalance");
		req.setParams(new Object[] { user.getBelongtoFirm().getFirmID(), bankID, password, bankPwd });
		try {
			returnValue = this.capitalProcessorRMI.doWork(req);
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
		this.logger.info("(returnValue.result================" + returnValue.result);
		if (returnValue.result == 0L) {
			bankAccount = (String) returnValue.msg[0];
			bankBalance = ((Double) returnValue.msg[1]).doubleValue();
			platBalance = ((Double) returnValue.msg[2]).doubleValue();
		}
		if (returnValue.result != 0L) {
			if (returnValue.result == -1L) {
				addReturnValue(-1, 9930092L, new Object[] { "密码错误" });
			} else if (returnValue.result == -2L) {
				addReturnValue(-1, 9930092L, new Object[] { "为查询到您的信息" });
			} else {
				addReturnValue(-1, 9930092L, new Object[] { Long.valueOf(returnValue.result) });
			}
			return "success";
		}
		this.request.setAttribute("bankAccount", bankAccount);
		this.request.setAttribute("platBalance", Double.valueOf(platBalance));
		this.request.setAttribute("bankBalance", Double.valueOf(bankBalance));
		return "success";
	}

	private boolean getNeedPasswordBankID(String pb, String bankID) {
		if ((pb == null) || (pb.trim().length() <= 0)) {
			return false;
		}
		if (pb.trim().equalsIgnoreCase(bankID)) {
			return true;
		}
		if (pb.contains("," + bankID + ",")) {
			return true;
		}
		if (pb.startsWith(bankID + ",")) {
			return true;
		}
		if (pb.endsWith("," + bankID)) {
			return true;
		}
		return false;
	}

	public String getCapitalInfoList() throws Exception {
		User user = (User) this.request.getSession().getAttribute("CurrentUser");
		String qrDate = this.request.getParameter("beginTime");
		this.logger.info("qrDate=================" + qrDate);
		String srDate = this.request.getParameter("endTime");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String today = format.format(new Date());
		if ((qrDate == null) || (qrDate.trim().length() <= 0)) {
			qrDate = today;
		}
		if ((srDate == null) || (srDate.trim().length() <= 0)) {
			srDate = today;
		}
		ReturnValue resultDef = new ReturnValue();
		RequestMsg reqDef = new RequestMsg();
		reqDef.setBankID("");
		reqDef.setMethodName("getBanks");
		reqDef.setParams(new Object[] { "" });
		try {
			resultDef = this.capitalProcessorRMI.doWork(reqDef);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Vector<BankValue> vecBanks = null;
		if (resultDef.result == 0L) {
			vecBanks = (Vector) resultDef.msg[0];
		} else {
			vecBanks = new Vector();
		}
		this.request.setAttribute("vecBanks", vecBanks);

		ReturnValue result = new ReturnValue();
		RequestMsg req = new RequestMsg();
		req.setBankID("");
		req.setMethodName("getAllCapitalList");
		req.setParams(new Object[] { user.getBelongtoFirm().getFirmID(), qrDate, srDate });
		try {
			result = this.capitalProcessorRMI.doWork(req);
		} catch (Exception e) {
			this.logger.info("连接平台异常");
		}
		Vector capitalList = new Vector();
		if (result.result == 0L) {
			capitalList = (Vector) result.msg[0];
		} else {
			capitalList = new Vector();
		}
		req.setMethodName("");
		req.setMethodName("getRelationSystem");
		req.setParams(new Object[] { user.getBelongtoFirm().getFirmID() });
		try {
			result = this.capitalProcessorRMI.doWork(req);
		} catch (Exception e) {
			this.logger.info("调用平台出错");
			e.printStackTrace();
		}
		Vector CollectionSys = new Vector();
		if (result.result == 0L) {
			CollectionSys = (Vector) result.msg[0];
		}
		int pageNumber;
		if ((this.request.getParameter("inputpagenum") == null) || ("".equals(this.request.getParameter("pageSize")))) {
			pageNumber = 1;
		} else {
			pageNumber = Integer.parseInt(this.request.getParameter("inputpagenum"));
		}
		int pageSize;
		if ((this.request.getParameter("pageSize") == null) || ("".equals(this.request.getParameter("pageSize")))) {
			pageSize = 10;
		} else {
			pageSize = Integer.parseInt(this.request.getParameter("pageSize"));
		}
		if ((capitalList != null) && (capitalList.size() > 0)) {
			Page<StandardModel> pagec = new Page(pageNumber, pageSize, capitalList.size());
			this.request.setAttribute("pageInfo", pagec);
		}
		this.logger.info("=======result.result====" + result.result + "capitalList======" + capitalList.size());
		this.request.setAttribute("capitalList", capitalList);

		this.request.setAttribute("capitalList", capitalList);
		this.request.setAttribute("collectionSys", CollectionSys);

		this.request.setAttribute("oldParams", ActionUtil.getParametersStartingWith(this.request, "gnnt_"));

		return "success";
	}

	public String gotoRgstDelFirmInfoPage() {
		User user = (User) this.request.getSession().getAttribute("CurrentUser");
		QueryConditions qc = new QueryConditions();
		qc.addCondition("firm.firmID", "=", user.getBelongtoFirm().getFirmID());
		qc.addCondition("bank.validflag", "=", Integer.valueOf(0));

		PageRequest<QueryConditions> pageRequest = new PageRequest(1, 100, qc, " order by bank.bankID ");
		Page<StandardModel> page = getService().getPage(pageRequest, new FirmIDAndAccount());
		this.request.setAttribute("pageInfo", page);

		boolean isCheckMarketPassword = Tools.strToBoolean(ApplicationContextInit.getConfig("isCheckMarketPassword"));
		this.request.setAttribute("isCheckMarketPassword", Boolean.valueOf(isCheckMarketPassword));

		String rgstAccountNeedPasswordBankID = ApplicationContextInit.getConfig("rgstAccountNeedPasswordBankID");
		this.request.setAttribute("rgstAccountNeedPasswordBankID", rgstAccountNeedPasswordBankID);

		String delAccountNeedPasswordBankID = ApplicationContextInit.getConfig("delAccountNeedPasswordBankID");
		this.request.setAttribute("delAccountNeedPasswordBankID", delAccountNeedPasswordBankID);

		return "success";
	}

	public String gotoFirmInfoPage() {
		User user = (User) this.request.getSession().getAttribute("CurrentUser");

		ReturnValue result = new ReturnValue();
		RequestMsg req = new RequestMsg();
		req.setBankID("");
		req.setMethodName("getCorrespondValue");
		req.setParams(new Object[] { user.getBelongtoFirm().getFirmID() });
		try {
			result = this.capitalProcessorRMI.doWork(req);
		} catch (Exception e) {
			this.logger.info("调用平台异常");
		}
		Vector<CorrespondValue> cvs = null;
		this.logger.info("result.result===============" + result.result);
		if (result.result == 0L) {
			cvs = (Vector) result.msg[0];
			if ((cvs != null) && (cvs.size() == 0)) {
				cvs.add(new CorrespondValue());
			}
		} else {
			cvs = new Vector();
			CorrespondValue cv = new CorrespondValue();
			cvs.add(cv);
		}
		CardType cardType = new CardType();
		cardType.load();
		Map<String, String> cardTypeMap = CardType.cardTypeMap;
		req.setBankID("");
		req.setMethodName("getIsOpenBanks");
		req.setParams(new Object[] { user.getBelongtoFirm().getFirmID() });
		try {
			result = this.capitalProcessorRMI.doWork(req);
		} catch (Exception e) {
			this.logger.info("调用平台异常");
			e.printStackTrace();
		}
		Vector<BankValue> vecBankList = null;
		if (result.result == 0L) {
			vecBankList = (Vector) result.msg[0];
		} else {
			vecBankList = new Vector();
		}
		Map<String, BankValue> banksMap = new HashMap();
		for (BankValue bv : vecBankList) {
			banksMap.put(bv.bankID, bv);
		}
		this.request.setAttribute("cvs", cvs);

		this.request.setAttribute("banksMap", banksMap);
		this.request.setAttribute("cardTypeMap", cardTypeMap);
		return "success";
	}

	public String gotoRgsAccPage() {
		this.logger.info("注册交易商信息页面");
		User user = (User) this.request.getSession().getAttribute("CurrentUser");
		QueryConditions qc = new QueryConditions();
		qc.addCondition("validflag", "=", Integer.valueOf(0));
		PageRequest<QueryConditions> pageRequest = new PageRequest(1, 100, qc);
		Page<StandardModel> page = getService().getPage(pageRequest, new Bank());
		this.request.setAttribute("pageInfo", page);
		this.request.setAttribute("firmID", user.getBelongtoFirm().getFirmID());
		return "success";
	}

	public String gotoPwd() {
		this.logger.info("进入输入密码页面");
		User user = (User) this.request.getSession().getAttribute("CurrentUser");
		String flag = this.request.getParameter("flag");
		String bankID = this.request.getParameter("bankID");
		String msg = "";
		ReturnValue result = new ReturnValue();

		RequestMsg req = new RequestMsg();
		req.setBankID("");
		req.setMethodName("getIsOpenBanks");
		req.setParams(new Object[] { user.getBelongtoFirm().getFirmID() });
		try {
			result = this.capitalProcessorRMI.doWork(req);
		} catch (Exception e) {
			this.logger.info("调用平台出错");
			e.printStackTrace();
		}
		Vector<BankValue> vecBanks = null;
		if (result.result == 0L) {
			vecBanks = (Vector) result.msg[0];
		} else {
			vecBanks = new Vector();
		}
		this.request.setAttribute("flag", flag);
		this.request.setAttribute("firmID", user.getBelongtoFirm().getFirmID());
		this.request.setAttribute("bankID", bankID);
		this.request.setAttribute("vecBanks", vecBanks);
		return "success";
	}

	public String gotoInputInfo() {
		this.logger.info("进入签约信息页面");
		User user = (User) this.request.getSession().getAttribute("CurrentUser");
		String flag = this.request.getParameter("flag");
		String bankID = this.request.getParameter("bankID");
		String msg = "";
		ReturnValue result = new ReturnValue();
		RequestMsg reqDef = new RequestMsg();
		reqDef.setBankID("");
		reqDef.setMethodName("getBanks");
		reqDef.setParams(new Object[] { "" });
		try {
			result = this.capitalProcessorRMI.doWork(reqDef);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Vector<BankValue> vecBanks = null;
		if (result.result == 0L) {
			vecBanks = (Vector) result.msg[0];
		} else {
			vecBanks = new Vector();
		}
		reqDef.setMethodName("getIsOpenBanks");
		reqDef.setParams(new Object[] { user.getBelongtoFirm().getFirmID() });
		try {
			result = this.capitalProcessorRMI.doWork(reqDef);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Vector<BankValue> vecBankOpen = null;
		if (result.result == 0L) {
			vecBankOpen = (Vector) result.msg[0];
		} else {
			vecBankOpen = new Vector();
		}
		Vector<String> VecOpen = new Vector();
		for (BankValue banValue : vecBankOpen) {
			VecOpen.add(banValue.bankID);
		}
		Iterator<BankValue> it = vecBanks.iterator();
		if (it.hasNext()) {
			String str = ((BankValue) it.next()).bankID;
			if (VecOpen.contains(str)) {
				it.remove();
			}
		}
		this.request.setAttribute("flag", flag);
		this.request.setAttribute("firmID", user.getBelongtoFirm().getFirmID());
		this.request.setAttribute("bankID", bankID);
		this.request.setAttribute("vecBanks", vecBanks);
		return "success";
	}

	public String PreRgsAccPage() {
		this.logger.info("进入预签约方法");
		User user = (User) this.request.getSession().getAttribute("CurrentUser");
		String bankID = this.request.getParameter("bankID");
		String account = this.request.getParameter("account");
		String accountName = this.request.getParameter("accountName");
		String cardType = this.request.getParameter("cardType");
		String card = this.request.getParameter("card");
		String fundsPwd = this.request.getParameter("pwd");
		String bankPwd = this.request.getParameter("bankpwd");
		String RequestID = this.request.getParameter("RequestID");
		String CustSignInfo = this.request.getParameter("CustSignInfo");
		ReturnValue result = new ReturnValue();
		RequestMsg req = new RequestMsg();
		req.setBankID(bankID);
		if ((RequestID == null) || ("".equals(RequestID))) {
			req.setMethodName("getCheckMsg");
			req.setParams(new Object[] { bankID, "1" });
			try {
				result = this.capitalProcessorRMI.doWork(req);
			} catch (RemoteException e1) {
				this.logger.info("调用处理器查询有关银行签约的特殊设置异常：" + Tool.getExceptionTrace(e1));
			}
			if ((result != null) && (result.result == 0L)) {
				Vector<CheckMessage> vec = (Vector) result.msg[0];
				if ((vec != null) && (vec.size() > 0)) {
					this.logger.info("银行[" + bankID + "]有特殊的签约设置");

					req.setMethodName("getActionID");
					req.setParams(null);
					try {
						result = this.capitalProcessorRMI.doWork(req);
					} catch (RemoteException e) {
						this.logger.info("调用处理器获取流水号异常：" + Tool.getExceptionTrace(e));
					}
					if ((result == null) || (result.result != 0L)) {
						this.logger.info("调用处理器获取流水号失败");
						addReturnValue(1, 130000L, new Object[] { "获取流水号失败" });
						return "success";
					}
					RequestID = String.valueOf(result.actionId);
					this.request.setAttribute("RequestID", RequestID);
					this.request.setAttribute("addOrDel", "1");
					return bankID;
				}
			}
		}
		CorrespondValue corr = new CorrespondValue();
		corr.sysFirmID = user.getBelongtoFirm().getFirmID();
		corr.bankID = bankID;
		corr.accountName = accountName;
		corr.account = account;
		corr.cardType = cardType;
		corr.card = card;
		corr.fundsPwd = fundsPwd;
		corr.bankCardPassword = bankPwd;
		corr.signInfo = CustSignInfo;
		corr.actionID = RequestID;
		System.out.println("获取到的证件类型[" + corr.cardType + "]");
		req.setBankID(corr.bankID);
		req.setMethodName("signedContract");
		req.setParams(new Object[] { corr });
		try {
			result = this.capitalProcessorRMI.doWork(req);
			if (result.result == 0L) {
				result.remark = "签约成功";
			} else if (result.result == 5L) {
				result.remark = "预签约成功";
			} else if ((result.remark == null) || ("".equals(result.remark))) {
				result.remark = "签约失败";
			}
		} catch (Exception e) {
			this.logger.info("调用处理器签约异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "签约失败，处理器异常";
		}
		addReturnValue(1, 130000L, new Object[] { result.remark });
		return "success";
	}

	public String gotoUnsign() {
		this.logger.info("跳转到解约界面");
		User user = (User) this.request.getSession().getAttribute("CurrentUser");
		RequestMsg req = new RequestMsg();
		req.setBankID("");
		req.setMethodName("getIsOpenBanks");
		req.setParams(new Object[] { user.getBelongtoFirm().getFirmID() });
		ReturnValue result = null;
		try {
			result = this.capitalProcessorRMI.doWork(req);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		Vector<BankValue> vecBanks = null;
		if (result.result == 0L) {
			vecBanks = (Vector) result.msg[0];
		} else {
			vecBanks = new Vector();
		}
		this.request.setAttribute("firmID", user.getBelongtoFirm().getFirmID());
		this.request.setAttribute("vecBanks", vecBanks);
		return "success";
	}

	public void returnCorrespondInfo() {
		this.logger.info("根据选择的银行返回交易商的信息");
		System.out.println("修改交易商信息查询资料7777777777777777777");
		User user = (User) this.request.getSession().getAttribute("CurrentUser");
		String bankID = this.request.getParameter("bankID");
		String firmID = user.getBelongtoFirm().getFirmID();
		ReturnValue result = null;
		RequestMsg req = new RequestMsg();
		req.setBankID(bankID);
		req.setMethodName("getCorrespondValueInfo");
		req.setParams(new Object[] { firmID, bankID });
		CorrespondValue cv = new CorrespondValue();
		try {
			result = this.capitalProcessorRMI.doWork(req);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		Vector<CorrespondValue> vecCorrespondValue = null;
		if (result.result == 0L) {
			vecCorrespondValue = (Vector) result.msg[0];
		} else {
			vecCorrespondValue = new Vector();
		}
		if (vecCorrespondValue.size() > 0) {
			cv = (CorrespondValue) vecCorrespondValue.get(0);
		}
		this.logger.info("交易商银行账号：" + cv.account + "===" + cv.accountName + "===" + cv.card + "===" + cv.cardType);
		this.response.setContentType("text/html;charset=GBK");
		PrintWriter out = null;
		try {
			out = this.response.getWriter();
			out.print(cv.account + "," + cv.accountName + "," + cv.card + "," + cv.cardType);
			System.out.println(cv.account + "," + cv.accountName + "," + cv.card + "," + cv.cardType);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
		}
	}

	public String Unsign() {
		this.logger.info("进入解约方法");
		User user = (User) this.request.getSession().getAttribute("CurrentUser");
		String firmID = user.getBelongtoFirm().getFirmID();
		String bankID = this.request.getParameter("bankID");
		String fundsPwd = this.request.getParameter("pwd");
		String bankPwd = this.request.getParameter("bankpwd");
		String RequestID = this.request.getParameter("RequestID");
		String CustSignInfo = this.request.getParameter("CustSignInfo");
		ReturnValue result = new ReturnValue();
		RequestMsg req = new RequestMsg();
		req.setBankID(bankID);
		if ((RequestID == null) || ("".equals(RequestID))) {
			req.setMethodName("getCheckMsg");
			req.setParams(new Object[] { bankID, "1" });
			try {
				result = this.capitalProcessorRMI.doWork(req);
			} catch (RemoteException e1) {
				this.logger.info("调用处理器查询有关银行签约的特殊设置异常：" + Tool.getExceptionTrace(e1));
			}
			if ((result != null) && (result.result == 0L)) {
				Vector<CheckMessage> vec = (Vector) result.msg[0];
				if ((vec != null) && (vec.size() > 0)) {
					this.logger.info("银行[" + bankID + "]有特殊的签约设置");

					req.setMethodName("getActionID");
					req.setParams(null);
					try {
						result = this.capitalProcessorRMI.doWork(req);
					} catch (RemoteException e) {
						this.logger.info("调用处理器获取流水号异常：" + Tool.getExceptionTrace(e));
					}
					if ((result == null) || (result.result != 0L)) {
						this.logger.info("调用处理器获取流水号失败");
						addReturnValue(1, 130000L, new Object[] { "获取流水号失败" });
						return "success";
					}
					RequestID = String.valueOf(result.actionId);
					this.request.setAttribute("RequestID", RequestID);
					this.request.setAttribute("addOrDel", "2");
					return bankID;
				}
			}
		}
		CorrespondValue corr = new CorrespondValue();
		RequestMsg req1 = new RequestMsg();
		req1.setBankID(bankID);
		req1.setMethodName("getCorrespondValueInfo");
		req1.setParams(new Object[] { firmID, bankID });
		ReturnValue returnValue = null;
		try {
			returnValue = this.capitalProcessorRMI.doWork(req1);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		Vector<CorrespondValue> vecCorrespondValue = null;
		if (returnValue.result == 0L) {
			vecCorrespondValue = (Vector) returnValue.msg[0];
		} else {
			vecCorrespondValue = new Vector();
		}
		if (vecCorrespondValue.size() > 0) {
			corr = (CorrespondValue) vecCorrespondValue.get(0);
		}
		corr.fundsPwd = fundsPwd;
		corr.bankCardPassword = bankPwd;
		corr.sysFirmID = firmID;
		corr.actionID = RequestID;
		corr.signInfo = CustSignInfo;
		this.logger.info(corr.toString());
		req.setBankID(bankID);
		req.setMethodName("removeContract");
		req.setParams(new Object[] { corr });
		try {
			result = this.capitalProcessorRMI.doWork(req);
		} catch (Exception e) {
			Tool.log("调用处理器解约方法异常：" + Tool.getExceptionTrace(e));
			result.remark = "调用处理器解约异常";
			e.printStackTrace();
		}
		if ((result.remark == null) || ("".equals(result.remark))) {
			if (result.result == 0L) {
				result.remark = "解约成功";
			} else if (result.result == 5L) {
				result.remark = "该银行不支持市场端解约，请从银行端发起解约";
			} else {
				result.remark = "解约失败";
			}
		}
		addReturnValue(1, 130000L, new Object[] { result.remark });
		return "success";
	}

	public String gotoModAcc() {
		this.logger.info("查询交易商对应的平台号关联的银行");
		User user = (User) this.request.getSession().getAttribute("CurrentUser");
		String firmID = user.getBelongtoFirm().getFirmID();

		ReturnValue result = new ReturnValue();
		RequestMsg req = new RequestMsg();
		req.setBankID("");
		req.setMethodName("getCorrespondValue");
		req.setParams(new Object[] { firmID });
		try {
			result = this.capitalProcessorRMI.doWork(req);
		} catch (Exception e) {
			this.logger.info("调用平台异常");
		}
		Vector<CorrespondValue> cvs = null;
		if ((result != null) && (result.result == 0L)) {
			cvs = (Vector) result.msg[0];
			if ((cvs != null) && (cvs.size() == 0)) {
				cvs.add(new CorrespondValue());
			}
		} else {
			cvs = new Vector();
			CorrespondValue cv = new CorrespondValue();
			cvs.add(cv);
		}
		Vector<String> vecBankID = new Vector();
		for (CorrespondValue cv : cvs) {
			vecBankID.add(cv.bankID);
		}
		req.setMethodName("getBanks");
		req.setParams(new Object[] { "" });
		try {
			result = this.capitalProcessorRMI.doWork(req);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Vector<BankValue> vecBanks = null;
		if ((result != null) && (result.result == 0L)) {
			vecBanks = (Vector) result.msg[0];
		} else {
			vecBanks = new Vector();
		}
		Object it = vecBanks.iterator();
		while (((Iterator) it).hasNext()) {
			String str = ((BankValue) ((Iterator) it).next()).bankID;
			if (!vecBankID.contains(str)) {
				((Iterator) it).remove();
			}
		}
		this.request.setAttribute("vecBanks", vecBanks);
		return "success";
	}

	public String modAcc() {
		this.logger.info("进入修改银行账户信息方法");
		User user = (User) this.request.getSession().getAttribute("CurrentUser");
		String firmID = user.getBelongtoFirm().getFirmID();
		String bankID = this.request.getParameter("bankID");
		String account = this.request.getParameter("account");
		String accountName = this.request.getParameter("accountName");
		String card = this.request.getParameter("card");
		String fundsPwd = this.request.getParameter("pwd");
		String bankPwd = this.request.getParameter("bankpwd");

		CorrespondValue corr = new CorrespondValue();
		RequestMsg req = new RequestMsg();
		req.setBankID(bankID);
		req.setMethodName("getCorrespondValueInfo");
		req.setParams(new Object[] { firmID, bankID });
		ReturnValue returnValue = null;
		try {
			returnValue = this.capitalProcessorRMI.doWork(req);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		Vector<CorrespondValue> vecCorrespondValue = null;
		if ((returnValue != null) && (returnValue.result == 0L)) {
			vecCorrespondValue = (Vector) returnValue.msg[0];
		} else {
			vecCorrespondValue = new Vector();
		}
		if (vecCorrespondValue.size() > 0) {
			corr = (CorrespondValue) vecCorrespondValue.get(0);
		}
		if (corr.isOpen == 1) {
			addReturnValue(1, 130000L, new Object[] { "已经签约的银行不能修改信息" });
			return "success";
		}
		CorrespondValue corrNew = new CorrespondValue();
		corrNew.fundsPwd = fundsPwd;
		corrNew.bankCardPassword = bankPwd;
		corrNew.sysFirmID = firmID;
		corrNew.bankID = bankID;
		corrNew.accountName = accountName;
		corrNew.account = account;
		corrNew.card = card;
		corrNew.cardType = corr.cardType;

		req.setMethodName("modCorrespondValue");
		req.setParams(new Object[] { corr, corrNew });
		ReturnValue result = null;
		try {
			result = this.capitalProcessorRMI.doWork(req);
		} catch (Exception e) {
			Tool.log("调用处理器解约方法异常：" + Tool.getExceptionTrace(e));
			result.remark = "调用处理器解约异常";
			e.printStackTrace();
		}
		if ((result.remark == null) || ("".equals(result.remark))) {
			if (result.result == 0L) {
				result.remark = "修改成功";
			} else {
				result.remark = "修改失败";
			}
		}
		addReturnValue(1, 130000L, new Object[] { result.remark });
		return "success";
	}

	public String gotoHXCrossFirmInfoPage() {
		this.logger.info("华夏他行签约");
		User user = (User) this.request.getSession().getAttribute("CurrentUser");

		String bankID = this.request.getParameter("bankID");
		QueryConditions qc = new QueryConditions();
		qc.addCondition("firm.firmID", "=", user.getBelongtoFirm().getFirmID());
		qc.addCondition("bank.validflag", "=", Integer.valueOf(0));

		PageRequest<QueryConditions> pageRequest = new PageRequest(1, 100, qc, " order by bank.bankID ");
		Page<StandardModel> page = getService().getPage(pageRequest, new FirmIDAndAccount());
		this.request.setAttribute("pageInfo", page);

		List<StandardModel> firm = getService().getListBySql(
				"select * from F_b_firmIDAndAccount where firmID ='" + user.getBelongtoFirm().getFirmID() + "'", new FirmIDAndAccount());
		this.request.setAttribute("firmIDAndAccount", firm.get(0));

		String filterForGetCitys = "where parentID='0'";

		return "success";
	}

	public String delRegistByPlat() {
		String firmID = this.request.getParameter("firmID");
		if ((firmID == null) || (firmID.trim().length() <= 0)) {
			addReturnValue(-1, 133301L);
			return "success";
		}
		String bankID = this.request.getParameter("bankID");
		if ((bankID == null) || (bankID.trim().length() <= 0)) {
			addReturnValue(-1, 133302L);
			return "success";
		}
		User user = (User) this.request.getSession().getAttribute("CurrentUser");
		gnnt.MEBS.bank.front.model.Log log = new gnnt.MEBS.bank.front.model.Log();
		log.setLogDate(new Date());
		log.setLogIP(user.getIpAddress());
		log.setLogopr(user.getUserID());
		String fundsPwd = this.request.getParameter("pwd");
		String bankPwd = this.request.getParameter("bankpwd");
		String RequestID = this.request.getParameter("RequestID");
		String CustSignInfo = this.request.getParameter("CustSignInfo");

		ReturnValue result = new ReturnValue();
		RequestMsg req = new RequestMsg();
		if ((RequestID == null) || ("".equals(RequestID))) {
			req.setMethodName("getCheckMsg");
			req.setParams(new Object[] { bankID, "1" });
			try {
				result = this.capitalProcessorRMI.doWork(req);
			} catch (RemoteException e1) {
				this.logger.info("调用处理器查询有关银行签约的特殊设置异常：" + Tool.getExceptionTrace(e1));
			}
			if ((result != null) && (result.result == 0L)) {
				Vector<CheckMessage> vec = (Vector) result.msg[0];
				if ((vec != null) && (vec.size() > 0)) {
					this.logger.info("银行[" + bankID + "]有特殊的签约设置");

					req.setMethodName("getActionID");
					req.setParams(null);
					try {
						result = this.capitalProcessorRMI.doWork(req);
					} catch (RemoteException e) {
						this.logger.info("调用处理器获取流水号异常：" + Tool.getExceptionTrace(e));
					}
					if ((result == null) || (result.result != 0L)) {
						this.logger.info("调用处理器获取流水号失败");
						addReturnValue(1, 130000L, new Object[] { "获取流水号失败" });
						return "success";
					}
					RequestID = String.valueOf(result.actionId);
					this.request.setAttribute("RequestID", RequestID);
					this.request.setAttribute("addOrDel", "2");
					return bankID;
				}
			}
		}
		FirmIDAndAccount firmIDAndAccount = (FirmIDAndAccount) getFirmIDAndAccount(firmID, bankID).clone();
		CorrespondValue cv = firmIDAndAccount.getCorrespondValue();
		cv.bankCardPassword = bankPwd;
		cv.fundsPwd = fundsPwd;
		cv.signInfo = CustSignInfo;
		cv.actionID = RequestID;
		try {
			req.setBankID(cv.bankID);
			req.setMethodName("removeContract");
			req.setParams(new Object[] { cv });
			result = this.capitalProcessorRMI.doWork(req);
			if ((result != null) && ((result.remark == null) || ("".equals(result.remark)))) {
				if (result.result == 0L) {
					result.remark = "解约成功";
				} else {
					result.remark = "解约失败";
				}
			}
		} catch (Exception e) {
			log.setLogcontent("注销交易商 " + firmID + " 在银行 " + bankID + " 帐号，发生异常");
			getService().add(log);
			this.logger.error("注销交易商 " + firmID + " 在银行 " + bankID + " 帐号异常", e);
		}
		addReturnValue(1, 130000L, new Object[] { result.remark });
		return "success";
	}

	private FirmIDAndAccount getFirmIDAndAccount(String firmID, String bankID) {
		FirmIDAndAccount result = new FirmIDAndAccount();
		MFirm firm = new MFirm();
		firm.setFirmID(firmID);
		result.setFirm(firm);
		Bank bank = new Bank();
		bank.setBankID(bankID);
		result.setBank(bank);
		return (FirmIDAndAccount) getService().get(result);
	}

	public String SysMoneyTransfer() {
		this.logger.info("进入系统间资金划转 方法");
		String mgs = "";
		ReturnValue result = new ReturnValue();
		String pwd = this.request.getParameter("password");
		User user = (User) this.request.getSession().getAttribute("CurrentUser");
		String paySys = this.request.getParameter("PaycollectionSys");
		String collectionSys = this.request.getParameter("collectionSys");
		this.logger.info("====paySys===" + paySys + "collectionSys====" + collectionSys);
		if (paySys.equals(collectionSys)) {
			addReturnValue(1, 130000L, new Object[] { "收付款系统不能相同" });
			return "success";
		}
		double money = Double.parseDouble(this.request.getParameter("money"));
		System.out.println("firmID====" + user.getBelongtoFirm() + "money======" + money + "password=====" + pwd);
		RequestMsg req = new RequestMsg();
		req.setBankID("");
		req.setMethodName("transferFunds");
		req.setParams(new Object[] { paySys, collectionSys, user.getBelongtoFirm().getFirmID(), Double.valueOf(money), pwd });
		try {
			result = this.capitalProcessorRMI.doWork(req);
		} catch (Exception e) {
			this.logger.info("连接平台异常");
		}
		if (result.result == 0L) {
			mgs = "资金划转成功";
		} else {
			mgs = result.remark;
			if ((mgs == null) || ("".equals(mgs))) {
				mgs = "资金划转失败";
			}
		}
		addReturnValue(1, 130000L, new Object[] { mgs });
		return "success";
	}

	public String returnBack() {
		this.logger.info("返回到初始页面");
		addReturnValue(1, 130000L, new Object[] { "取消操作" });
		return "success";
	}
}
