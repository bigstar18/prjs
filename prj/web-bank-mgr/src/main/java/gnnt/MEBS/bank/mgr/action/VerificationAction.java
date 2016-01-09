package gnnt.MEBS.bank.mgr.action;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import gnnt.MEBS.bank.mgr.core.BankCoreCode;
import gnnt.MEBS.bank.mgr.coremodel.CompareResultVO;
import gnnt.MEBS.bank.mgr.model.Bank;
import gnnt.MEBS.bank.mgr.statictools.Util;
import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.common.QueryConditions;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.User;
import gnnt.MEBS.common.mgr.statictools.ApplicationContextInit;
import gnnt.MEBS.common.mgr.statictools.Tools;
import gnnt.trade.bank.processorrmi.CapitalProcessorRMI;
import gnnt.trade.bank.vo.CompareResult;
import gnnt.trade.bank.vo.ReturnValue;

@Controller("verificationAction")
@Scope("request")
public class VerificationAction extends EcsideAction {
	private static final long serialVersionUID = 4664717292382601574L;

	@Resource(name = "compareResultErrorType")
	private Map<Integer, String> compareResultErrorType;

	@Resource(name = "capitalInfoType")
	private Map<Integer, String> capitalInfoType;

	@Autowired
	@Qualifier("capitalProcessorRMI")
	private CapitalProcessorRMI capitalProcessorRMI;

	public Map<Integer, String> getCompareResultErrorType() {
		return this.compareResultErrorType;
	}

	public Map<Integer, String> getCapitalInfoType() {
		return this.capitalInfoType;
	}

	public String verificationForward() {
		PageRequest pageRequest = new PageRequest(1, 100, new QueryConditions());
		Page page = getService().getPage(pageRequest, new Bank());
		if ((page != null) && (page.getResult() != null)) {
			List bankList = new ArrayList();

			String noAdapterBank = ApplicationContextInit.getConfig("noAdapterBank");
			for (StandardModel sm : (List<StandardModel>) page.getResult()) {
				Bank bank = (Bank) sm;

				if (!Util.isContentsBank(noAdapterBank, bank.getBankID())) {
					bankList.add(bank);
				}
			}
			this.request.setAttribute("bankList", bankList);
		}
		return "success";
	}

	public String getVerificationFile() {
		String bankID = this.request.getParameter("bankID");
		this.request.setAttribute("bankID", bankID);

		Date s_time = Tools.strToDate(this.request.getParameter("s_time"));
		this.request.setAttribute("s_time", s_time);

		User user = (User) this.request.getSession().getAttribute("CurrentUser");
		gnnt.MEBS.bank.mgr.model.Log log = new gnnt.MEBS.bank.mgr.model.Log();
		log.setLogDate(new Date());
		log.setLogIP(user.getIpAddress());
		log.setLogopr(user.getUserId());

		if (Util.isContentsBank(ApplicationContextInit.getConfig("noAdapterBank"), bankID)) {
			log.setLogcontent("获取银行对账数据，银行编号：" + bankID + " 本银行为模拟银行，模拟银行不能进行对账");
			getService().add(log);

			addReturnValue(-1, 133401L);
			return "success";
		}
		try {
			int getDataResult = this.capitalProcessorRMI.getBankCompareInfo(bankID, s_time);

			if (getDataResult == 0) {
				log.setLogcontent("获取银行对账数据，银行编号：" + bankID + " 对账日期：" + Tools.fmtDate(s_time) + "，成功");
				getService().add(log);

				addReturnValue(1, 113401L);
			} else if (getDataResult == -1) {
				log.setLogcontent("获取银行对账数据，银行编号：" + bankID + " 对账日期：" + Tools.fmtDate(s_time) + "，失败，系统尚未结算");
				getService().add(log);

				addReturnValue(-1, 133403L);
			} else {
				log.setLogcontent("获取银行对账数据，银行编号：" + bankID + " 对账日期：" + Tools.fmtDate(s_time) + "，失败，" + BankCoreCode.getCode(getDataResult));
				getService().add(log);

				addReturnValue(-1, 130000L, new Object[] { BankCoreCode.getCode(getDataResult) });
			}
		} catch (Exception e) {
			log.setLogcontent("获取银行对账数据，银行编号：" + bankID + " 对账日期：" + Tools.fmtDate(s_time) + " 发生异常");
			getService().add(log);

			addReturnValue(-1, 133402L);
			this.logger.error("获取银行" + bankID + "对账数据异常", e);
		}
		return "success";
	}

	public String getErrorCapitalInfo() {
		String bankID = this.request.getParameter("bankID");
		this.request.setAttribute("bankID", bankID);

		Date s_time = Tools.strToDate(this.request.getParameter("s_time"));
		this.request.setAttribute("s_time", s_time);

		User user = (User) this.request.getSession().getAttribute("CurrentUser");
		gnnt.MEBS.bank.mgr.model.Log log = new gnnt.MEBS.bank.mgr.model.Log();
		log.setLogDate(new Date());
		log.setLogIP(user.getIpAddress());
		log.setLogopr(user.getUserId());

		if (Util.isContentsBank(ApplicationContextInit.getConfig("noAdapterBank"), bankID)) {
			log.setLogcontent("核对出入金流水，银行编号：" + bankID + " 本银行为模拟银行，模拟银行不能进行对账");
			getService().add(log);

			addReturnValue(-1, 133501L);
			return "success";
		}
		try {
			boolean marketStatus = this.capitalProcessorRMI.getTraderStatus();
			if (!marketStatus) {
				log.setLogcontent("核对出入金流水，银行编号：" + bankID + " 系统尚未结算，不能对账");
				getService().add(log);

				addReturnValue(-1, 133503L);
				return "success";
			}
		} catch (Exception e) {
			log.setLogcontent("核对出入金流水，银行编号：" + bankID + " 判断系统结算状态异常");
			getService().add(log);

			addReturnValue(-1, 133502L);
			return "success";
		}
		try {
			List list = this.capitalProcessorRMI.checkMoney(bankID, s_time);
			if (list == null) {
				log.setLogcontent("核对出入金流水，银行编号：" + bankID + " 对账日期：" + Tools.fmtDate(s_time) + " 银行数据尚未就绪");
				getService().add(log);

				addReturnValue(-1, 133505L);
			} else if (list.size() == 0) {
				log.setLogcontent("核对出入金流水，银行编号：" + bankID + " 对账日期：" + Tools.fmtDate(s_time) + " 对账成功");
				getService().add(log);

				addReturnValue(1, 113501L);
			} else if (list.size() > 0) {
				log.setLogcontent("核对出入金流水，银行编号：" + bankID + " 对账日期：" + Tools.fmtDate(s_time) + " 对账失败，流水不平");
				getService().add(log);
				this.request.setAttribute("errormessage", "核对出入金流水失败，交易所出入金流水与银行出入金流水不一致");
				if ((this.request.getParameter("pageNum") == null) || (this.request.getParameter("pageNum").trim().length() <= 0)) {
					addReturnValue(-1, 133506L);
				}

			}

			if ((list != null) && (list.size() > 0)) {
				int pageSize = Tools.strToInt(this.request.getParameter("pageSize"), 20);
				if (pageSize <= 0)
					pageSize = 20;
				int pageNum = Tools.strToInt(this.request.getParameter("pageNum"), 1);
				if (pageNum <= 0)
					pageNum = 1;
				int rowSize = list.size();
				int page = rowSize % pageSize == 0 ? rowSize / pageSize : rowSize / pageSize + 1;
				if (pageNum > page)
					pageNum = page;
				if (pageNum <= 0)
					pageNum = 1;

				int start = pageSize * (pageNum - 1) + 1;
				int end = list.size() > start + pageSize ? start + pageSize : list.size();

				List result = new ArrayList();
				for (int i = start - 1; i < end; i++) {
					CompareResultVO vo = new CompareResultVO();
					CompareResult cr = (CompareResult) list.get(i);

					vo.setAccount(cr.account);
					vo.setBankID(cr.bankID);
					vo.setCompareDate(cr.compareDate);
					vo.setErrorType(cr.errorType);
					vo.setFirmID(cr.firmID);
					vo.setId(cr.id);
					vo.setM_Id(cr.m_Id);
					vo.setM_money(cr.m_money);
					vo.setM_type(cr.m_type);
					vo.setMoney(cr.money);
					vo.setType(cr.type);

					result.add(vo);
				}
				this.request.setAttribute("compareResultList", result);
				this.request.setAttribute("pageSize", Integer.valueOf(pageSize));
				this.request.setAttribute("pageNum", Integer.valueOf(pageNum));
				this.request.setAttribute("rowSize", Integer.valueOf(rowSize));
				this.request.setAttribute("page", Integer.valueOf(page));
			}
		} catch (RemoteException e) {
			log.setLogcontent("核对出入金流水，银行编号：" + bankID + " 获取对账结果异常");
			getService().add(log);

			addReturnValue(-1, 133504L);
		}
		return "success";
	}

	public String sendQsFile() {
		String bankID = this.request.getParameter("bankID");
		this.request.setAttribute("bankID", bankID);

		Date s_time = Tools.strToDate(this.request.getParameter("s_time"));
		this.request.setAttribute("s_time", s_time);

		User user = (User) this.request.getSession().getAttribute("CurrentUser");
		gnnt.MEBS.bank.mgr.model.Log log = new gnnt.MEBS.bank.mgr.model.Log();
		log.setLogDate(new Date());
		log.setLogIP(user.getIpAddress());
		log.setLogopr(user.getUserId());

		if (Util.isContentsBank(ApplicationContextInit.getConfig("noAdapterBank"), bankID)) {
			log.setLogcontent("发送清算文件，银行编号：" + bankID + " 本银行为模拟银行，模拟银行不能发送清算文件");
			getService().add(log);

			addReturnValue(-1, 133401L);
			return "success";
		}
		try {
			ReturnValue rv = null;

			if ("25".equals(bankID))
				rv = this.capitalProcessorRMI.sendCMBCQSValue(bankID, s_time);
			else if ("17".equals(bankID))
				rv = this.capitalProcessorRMI.hxSentQS(bankID, s_time);
			else if ("39".equals(bankID))
				rv = this.capitalProcessorRMI.sendHRBQSValue(bankID, null, s_time);
			else if ("19".equals(bankID))
				rv = this.capitalProcessorRMI.sendXYQSValue(bankID, null, s_time);
			else if ("12".equals(bankID))
				rv = this.capitalProcessorRMI.sendZHQS(bankID, null, s_time);
			else {
				rv = this.capitalProcessorRMI.sentFirmBalance(bankID, s_time);
			}
			if (rv.result == 0L) {
				log.setLogcontent("发送清算文件，银行编号：" + bankID + " 日期：" + Tools.fmtDate(s_time) + "，成功");
				getService().add(log);

				addReturnValue(1, 113701L);
			} else if (rv.result == -1L) {
				log.setLogcontent("发送清算文件，银行编号：" + bankID + " 日期：" + Tools.fmtDate(s_time) + "，失败，系统尚未结算");
				getService().add(log);

				addReturnValue(-1, 133403L);
			} else {
				log.setLogcontent("发送清算文件，银行编号：" + bankID + " 日期：" + Tools.fmtDate(s_time) + "，失败，" + BankCoreCode.getCode(rv.result));
				getService().add(log);

				addReturnValue(-1, 130000L, new Object[] { BankCoreCode.getCode(rv.result) });
			}
		} catch (Exception e) {
			log.setLogcontent("发送清算文件，银行编号：" + bankID + " 日期：" + Tools.fmtDate(s_time) + " 发生异常");
			getService().add(log);

			addReturnValue(-1, 133702L);
			this.logger.error("发送清算文件" + bankID + "数据异常", e);
		}
		return "success";
	}

	public String sentDZ() {
		String bankID = this.request.getParameter("bankID");
		this.request.setAttribute("bankID", bankID);

		Date s_time = Tools.strToDate(this.request.getParameter("s_time"));
		this.request.setAttribute("s_time", s_time);

		User user = (User) this.request.getSession().getAttribute("CurrentUser");
		gnnt.MEBS.bank.mgr.model.Log log = new gnnt.MEBS.bank.mgr.model.Log();
		log.setLogDate(new Date());
		log.setLogIP(user.getIpAddress());
		log.setLogopr(user.getUserId());

		if (Util.isContentsBank(ApplicationContextInit.getConfig("noAdapterBank"), bankID)) {
			log.setLogcontent("华夏资金核对，银行编号：" + bankID + " 本银行为模拟银行，模拟银行不能华夏资金核对");
			getService().add(log);

			addReturnValue(-1, 133401L);
			return "success";
		}
		try {
			ReturnValue rv = this.capitalProcessorRMI.hxSentDZ(bankID, s_time);
			if (rv.result == 0L) {
				log.setLogcontent("华夏资金核对，银行编号：" + bankID + " 日期：" + Tools.fmtDate(s_time) + "，成功");
				getService().add(log);

				addReturnValue(1, 113601L);
			} else if (rv.result == -1L) {
				log.setLogcontent("华夏资金核对，银行编号：" + bankID + " 日期：" + Tools.fmtDate(s_time) + "，失败，系统尚未结算");
				getService().add(log);

				addReturnValue(-1, 133403L);
			} else {
				log.setLogcontent("华夏资金核对，银行编号：" + bankID + " 日期：" + Tools.fmtDate(s_time) + "，失败，" + BankCoreCode.getCode(rv.result));
				getService().add(log);

				addReturnValue(-1, 130000L, new Object[] { BankCoreCode.getCode(rv.result) });
			}
		} catch (Exception e) {
			log.setLogcontent("华夏资金核对，银行编号：" + bankID + " 日期：" + Tools.fmtDate(s_time) + " 发生异常");
			getService().add(log);

			addReturnValue(-1, 133602L);
			this.logger.error("华夏资金核对" + bankID + "数据异常", e);
		}
		return "success";
	}
}