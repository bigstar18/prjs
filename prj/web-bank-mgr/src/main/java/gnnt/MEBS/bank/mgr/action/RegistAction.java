package gnnt.MEBS.bank.mgr.action;

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
import gnnt.MEBS.bank.mgr.model.Bank;
import gnnt.MEBS.bank.mgr.model.FirmIDAndAccount;
import gnnt.MEBS.bank.mgr.model.FirmUser;
import gnnt.MEBS.bank.mgr.model.integrated.MFirm;
import gnnt.MEBS.bank.mgr.statictools.Util;
import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.common.QueryConditions;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.User;
import gnnt.MEBS.common.mgr.statictools.ApplicationContextInit;
import gnnt.trade.bank.processorrmi.CapitalProcessorRMI;
import gnnt.trade.bank.util.Encryption;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.ReturnValue;

@Controller("registAction")
@Scope("request")
public class RegistAction extends EcsideAction {
	private static final long serialVersionUID = 4688278172242845063L;

	@Resource(name = "firmIDAndAccountCardType")
	private Map<String, String> firmIDAndAccountCardType;

	@Resource(name = "firmIDAndAccountIsOpen")
	private Map<Integer, String> firmIDAndAccountIsOpen;

	@Resource(name = "firmIDAndAccountStatus")
	private Map<Integer, String> firmIDAndAccountStatus;

	@Autowired
	@Qualifier("capitalProcessorRMI")
	private CapitalProcessorRMI capitalProcessorRMI;

	public Map<String, String> getFirmIDAndAccountCardType() {
		return this.firmIDAndAccountCardType;
	}

	public Map<Integer, String> getFirmIDAndAccountIsOpen() {
		return this.firmIDAndAccountIsOpen;
	}

	public Map<Integer, String> getFirmIDAndAccountStatus() {
		return this.firmIDAndAccountStatus;
	}

	public String firmIDAndAccountList() throws Exception {
		return listByLimit();
	}

	public String firmIDRegistList() throws Exception {
		String firmID = this.request.getParameter("firmID");
		if ((firmID == null) || (firmID.trim().length() <= 0)) {
			return "success";
		}

		this.request.setAttribute("firmID", firmID);

		PageRequest pageRequest = super.getPageRequest(this.request);
		QueryConditions qc = (QueryConditions) pageRequest.getFilters();
		qc.addCondition("firm.firmID", "=", firmID);
		listByLimit(pageRequest);
		return "success";
	}

	public String addRegistForward() {
		String firmID = this.request.getParameter("firmID");
		if ((firmID == null) || (firmID.trim().length() <= 0)) {
			return "success";
		}
		this.request.setAttribute("firmID", firmID);

		QueryConditions bqc = new QueryConditions();
		bqc.addCondition("validflag", "=", Integer.valueOf(0));
		PageRequest bpageRequest = new PageRequest(1, 100, bqc);
		Page bpage = getService().getPage(bpageRequest, new Bank());

		if ((bpage != null) && (bpage.getResult().size() > 0)) {
			QueryConditions fqc = new QueryConditions();
			fqc.addCondition("firm.firmID", "=", firmID);
			PageRequest fpageRequest = new PageRequest(1, 100, fqc);
			Page fpage = getService().getPage(fpageRequest, new FirmIDAndAccount());

			List bankList = new ArrayList();

			for (StandardModel sm : (List<StandardModel>) bpage.getResult()) {
				boolean isVisable = true;
				Bank bank = (Bank) sm;
				if ((fpage != null) && (fpage.getResult().size() > 0)) {
					for (StandardModel fsm : (List<StandardModel>) fpage.getResult()) {
						FirmIDAndAccount fa = (FirmIDAndAccount) fsm;
						if (bank.getBankID().equals(fa.getBank().getBankID())) {
							isVisable = false;
							break;
						}
					}
				}
				if (isVisable) {
					bankList.add(bank);
				}
			}
			this.request.setAttribute("bankList", bankList);
		}

		return "success";
	}

	public String addRegist() {
		FirmIDAndAccount firmIDAndAccount = (FirmIDAndAccount) this.entity;
		firmIDAndAccount.setStatus(Integer.valueOf(1));
		firmIDAndAccount.setIsOpen(Integer.valueOf(0));
		firmIDAndAccount.setFrozenFuns(Double.valueOf(0.0D));

		User user = (User) this.request.getSession().getAttribute("CurrentUser");
		gnnt.MEBS.bank.mgr.model.Log log = new gnnt.MEBS.bank.mgr.model.Log();
		log.setLogDate(new Date());
		log.setLogIP(user.getIpAddress());
		log.setLogopr(user.getUserId());

		if ((firmIDAndAccount.getAccount() == null) || (firmIDAndAccount.getAccount().trim().length() <= 0)) {
			if (!Util.isContentsBank(ApplicationContextInit.getConfig("needlessAccountBank"), firmIDAndAccount.getBank().getBankID())) {
				log.setLogcontent(
						"添加交易商 " + firmIDAndAccount.getFirm().getFirmID() + " 在银行 " + firmIDAndAccount.getBank().getBankID() + " 的绑定信息，失败，未传入银行账号");
				getService().add(log);

				addReturnValue(-1, 132901L);
				return "success";
			}
			firmIDAndAccount.setAccount(ApplicationContextInit.getConfig("defaultAccount"));
		}
		add();
		log.setLogcontent("添加交易商 " + firmIDAndAccount.getFirm().getFirmID() + " 在银行 " + firmIDAndAccount.getBank().getBankID() + " 的绑定信息，成功");
		getService().add(log);

		return "success";
	}

	public String updateRegistForward() throws Exception {
		return viewById();
	}

	public String updateRegist() {
		FirmIDAndAccount firmIDAndAccount = (FirmIDAndAccount) this.entity;

		if ((firmIDAndAccount.getAccount() == null) || (firmIDAndAccount.getAccount().trim().length() <= 0)) {
			if (Util.isContentsBank(ApplicationContextInit.getConfig("needlessAccountBank"), firmIDAndAccount.getBank().getBankID())) {
				firmIDAndAccount.setAccount(ApplicationContextInit.getConfig("defaultAccount"));
			}
		}
		update();

		User user = (User) this.request.getSession().getAttribute("CurrentUser");
		gnnt.MEBS.bank.mgr.model.Log log = new gnnt.MEBS.bank.mgr.model.Log();
		log.setLogDate(new Date());
		log.setLogIP(user.getIpAddress());
		log.setLogopr(user.getUserId());
		log.setLogcontent("修改交易商 " + firmIDAndAccount.getFirm().getFirmID() + " 在银行 " + firmIDAndAccount.getBank().getBankID() + " 的绑定信息");
		getService().add(log);
		return "success";
	}

	public String synchroRegist() {
		String firmID = this.request.getParameter("firmID");
		if ((firmID == null) || (firmID.trim().length() <= 0)) {
			addReturnValue(-1, 133301L);
			return "success";
		}
		String[] bankIDs = getBankIDs();
		if ((bankIDs == null) || (bankIDs.length <= 0)) {
			addReturnValue(-1, 133302L);
			return "success";
		}

		User user = (User) this.request.getSession().getAttribute("CurrentUser");
		gnnt.MEBS.bank.mgr.model.Log log = new gnnt.MEBS.bank.mgr.model.Log();
		log.setLogDate(new Date());
		log.setLogIP(user.getIpAddress());
		log.setLogopr(user.getUserId());

		String success = "";
		String error = "";
		for (String bankID : bankIDs) {
			FirmIDAndAccount firmIDAndAccount = (FirmIDAndAccount) getFirmIDAndAccount(firmID, bankID).clone();
			CorrespondValue cv = firmIDAndAccount.getCorrespondValue();
			cv.status = 0;
			cv.isOpen = 0;
			try {
				if (!Util.isContentsBank(ApplicationContextInit.getConfig("marketPerformSynchroAccount"), bankID)) {
					log.setLogcontent("同步交易商 " + firmID + " 在银行 " + bankID + " 帐号，失败。本银行不允许交易所发起同步");
					getService().add(log);
					this.logger.debug("同步交易商 " + firmID + " 在银行 " + bankID + " 帐号，失败。本银行不允许交易所发起同步");
					if (error.trim().length() > 0)
						error = error + ",";
					error = error + bankID;
				} else {
					ReturnValue result = this.capitalProcessorRMI.synchroAccountMarket(cv);
					if (result.result == 0L) {
						log.setLogcontent("同步交易商 " + firmID + " 在银行 " + bankID + " 帐号，成功。");
						getService().add(log);
						if (success.trim().length() > 0)
							success = success + ",";
						success = success + bankID;
					} else {
						log.setLogcontent("同步交易商 " + firmID + " 在银行 " + bankID + " 帐号，失败。" + BankCoreCode.getCode(result.result));
						getService().add(log);
						this.logger.debug("同步交易商 " + firmID + " 在银行 " + bankID + " 帐号，" + BankCoreCode.getCode(result.result));
						if (error.trim().length() > 0)
							error = error + ",";
						error = error + "银行" + bankID + "：" + BankCoreCode.getCode(result.result);
					}
				}
			} catch (Exception e) {
				log.setLogcontent("同步交易商 " + firmID + " 在银行 " + bankID + " 帐号，发生异常");
				getService().add(log);
				if (error.trim().length() > 0)
					error = error + ",";
				error = error + "银行" + bankID + "：异常";
				this.logger.error("同步交易商 " + firmID + " 在银行 " + bankID + " 帐号异常", e);
			}
		}
		String msg = "同步交易商 " + firmID + " 银行帐号";
		if (success.length() > 0) {
			msg = msg + "," + success + " 成功";
		}
		if (error.length() > 0) {
			msg = msg + "," + error;
		}

		addReturnValue(1, 130000L, new Object[] { msg });

		return "success";
	}

	public String openRegist() {
		String firmID = this.request.getParameter("firmID");
		if ((firmID == null) || (firmID.trim().length() <= 0)) {
			addReturnValue(-1, 133301L);
			return "success";
		}
		String[] bankIDs = getBankIDs();
		if ((bankIDs == null) || (bankIDs.length <= 0)) {
			addReturnValue(-1, 133302L);
			return "success";
		}

		User user = (User) this.request.getSession().getAttribute("CurrentUser");
		gnnt.MEBS.bank.mgr.model.Log log = new gnnt.MEBS.bank.mgr.model.Log();
		log.setLogDate(new Date());
		log.setLogIP(user.getIpAddress());
		log.setLogopr(user.getUserId());

		String success = "";
		String error = "";
		for (String bankID : bankIDs) {
			FirmIDAndAccount firmIDAndAccount = (FirmIDAndAccount) getFirmIDAndAccount(firmID, bankID).clone();
			CorrespondValue cv = firmIDAndAccount.getCorrespondValue();
			cv.status = 0;
			cv.isOpen = 0;
			try {
				if (!Util.isContentsBank(ApplicationContextInit.getConfig("marketPerformOpenAccount"), bankID)) {
					log.setLogcontent("签约交易商 " + firmID + " 在银行 " + bankID + " 代码，失败。本银行不允许交易所发起签约");
					getService().add(log);
					this.logger.debug("签约交易商 " + firmID + " 在银行 " + bankID + " 代码，失败。本银行不允许交易所发起签约");
					if (error.trim().length() > 0)
						error = error + ",";
					error = error + bankID;
				} else {
					ReturnValue result = this.capitalProcessorRMI.openAccountMarket(cv);
					if (result.result == 0L) {
						log.setLogcontent("签约交易商 " + firmID + " 在银行 " + bankID + " 代码，成功");
						getService().add(log);
						if (success.trim().length() > 0)
							success = success + ",";
						success = success + bankID;
					} else {
						log.setLogcontent("签约交易商 " + firmID + " 在银行 " + bankID + " 代码，失败。" + BankCoreCode.getCode(result.result));
						getService().add(log);
						this.logger.debug("签约交易商 " + firmID + " 在银行 " + bankID + " 代码，" + BankCoreCode.getCode(result.result));
						if (error.trim().length() > 0)
							error = error + ",";
						error = error + "银行" + bankID + "：" + BankCoreCode.getCode(result.result);
					}
				}
			} catch (Exception e) {
				log.setLogcontent("签约交易商 " + firmID + " 在银行 " + bankID + " 代码，发生异常");
				getService().add(log);
				if (error.trim().length() > 0)
					error = error + ",";
				error = error + "银行" + bankID + "：异常";
				this.logger.error("签约交易商 " + firmID + " 在银行 " + bankID + " 代码异常", e);
			}
		}
		String msg = "签约交易商 " + firmID + " 银行代码";
		if (success.length() > 0) {
			msg = msg + "," + success + " 成功";
		}
		if (error.length() > 0) {
			msg = msg + "," + error;
		}

		addReturnValue(1, 130000L, new Object[] { msg });

		return "success";
	}

	public String delRegist() {
		String firmID = this.request.getParameter("firmID");
		if ((firmID == null) || (firmID.trim().length() <= 0)) {
			addReturnValue(-1, 133301L);
			return "success";
		}
		String[] bankIDs = getBankIDs();
		if ((bankIDs == null) || (bankIDs.length <= 0)) {
			addReturnValue(-1, 133302L);
			return "success";
		}

		User user = (User) this.request.getSession().getAttribute("CurrentUser");
		gnnt.MEBS.bank.mgr.model.Log log = new gnnt.MEBS.bank.mgr.model.Log();
		log.setLogDate(new Date());
		log.setLogIP(user.getIpAddress());
		log.setLogopr(user.getUserId());

		String success = "";
		String error = "";
		for (String bankID : bankIDs) {
			FirmIDAndAccount firmIDAndAccount = (FirmIDAndAccount) getFirmIDAndAccount(firmID, bankID).clone();
			CorrespondValue cv = firmIDAndAccount.getCorrespondValue();
			try {
				long result = 0L;
				if (Util.isContentsBank(ApplicationContextInit.getConfig("noAdapterBank"), bankID))
					result = this.capitalProcessorRMI.delAccountNoAdapter(cv);
				else {
					result = this.capitalProcessorRMI.delAccountMaket(cv);
				}
				if (result == 0L) {
					log.setLogcontent("注销交易商 " + firmID + " 在银行 " + bankID + " 帐号，成功");
					getService().add(log);
					if (success.trim().length() > 0)
						success = success + ",";
					success = success + bankID;
				} else {
					log.setLogcontent("注销交易商 " + firmID + " 在银行 " + bankID + " 帐号，失败，" + BankCoreCode.getCode(result));
					getService().add(log);
					this.logger.debug("注销交易商 " + firmID + " 在银行 " + bankID + " 帐号，" + BankCoreCode.getCode(result));
					if (error.trim().length() > 0)
						error = error + ",";
					error = error + "银行" + bankID + "：" + BankCoreCode.getCode(result);
				}
			} catch (Exception e) {
				log.setLogcontent("注销交易商 " + firmID + " 在银行 " + bankID + " 帐号，发生异常");
				getService().add(log);
				if (error.trim().length() > 0)
					error = error + ",";
				error = error + "银行" + bankID + "：异常";
				this.logger.error("注销交易商 " + firmID + " 在银行 " + bankID + " 帐号异常", e);
			}
		}

		String msg = "注销交易商 " + firmID + " 银行帐号";
		if (success.length() > 0) {
			msg = msg + "," + success + " 成功";
		}
		if (error.length() > 0) {
			msg = msg + "," + error;
		}

		addReturnValue(1, 130000L, new Object[] { msg });

		return "success";
	}

	public String forbidRegist() {
		String firmID = this.request.getParameter("firmID");
		if ((firmID == null) || (firmID.trim().length() <= 0)) {
			addReturnValue(-1, 133301L);
			return "success";
		}
		String[] bankIDs = getBankIDs();
		if ((bankIDs == null) || (bankIDs.length <= 0)) {
			addReturnValue(-1, 133302L);
			return "success";
		}
		String success = "";
		for (String bankID : bankIDs) {
			FirmIDAndAccount firmIDAndAccount = getFirmIDAndAccount(firmID, bankID);
			firmIDAndAccount.setStatus(Integer.valueOf(1));
			getService().update(firmIDAndAccount);
			if (success.trim().length() > 0)
				success = success + ",";
			success = success + bankID;
		}

		String msg = "禁用交易商 " + firmID + " 银行帐号 " + success + " 成功";

		User user = (User) this.request.getSession().getAttribute("CurrentUser");
		gnnt.MEBS.bank.mgr.model.Log log = new gnnt.MEBS.bank.mgr.model.Log();
		log.setLogDate(new Date());
		log.setLogIP(user.getIpAddress());
		log.setLogopr(user.getUserId());
		log.setLogcontent(msg);
		getService().add(log);

		addReturnValue(1, 130000L, new Object[] { msg });

		return "success";
	}

	public String recoverRegist() {
		String firmID = this.request.getParameter("firmID");
		if ((firmID == null) || (firmID.trim().length() <= 0)) {
			addReturnValue(-1, 133301L);
			return "success";
		}
		String[] bankIDs = getBankIDs();
		if ((bankIDs == null) || (bankIDs.length <= 0)) {
			addReturnValue(-1, 133302L);
			return "success";
		}
		String success = "";
		for (String bankID : bankIDs) {
			FirmIDAndAccount firmIDAndAccount = getFirmIDAndAccount(firmID, bankID);
			firmIDAndAccount.setStatus(Integer.valueOf(0));
			getService().update(firmIDAndAccount);
			if (success.trim().length() > 0)
				success = success + ",";
			success = success + bankID;
		}

		String msg = "启用交易商 " + firmID + " 银行帐号 " + success + " 成功";

		User user = (User) this.request.getSession().getAttribute("CurrentUser");
		gnnt.MEBS.bank.mgr.model.Log log = new gnnt.MEBS.bank.mgr.model.Log();
		log.setLogDate(new Date());
		log.setLogIP(user.getIpAddress());
		log.setLogopr(user.getUserId());
		log.setLogcontent(msg);
		getService().add(log);

		addReturnValue(1, 130000L, new Object[] { msg });

		return "success";
	}

	public String resetsmmy() {
		String firmID = this.request.getParameter("firmID");
		if ((firmID == null) || (firmID.trim().length() <= 0)) {
			addReturnValue(-1, 133301L);
			return "success";
		}
		FirmUser firmUser = new FirmUser();
		firmUser.setFirmID(firmID);
		MFirm firm = new MFirm();
		firm.setFirmID(firmID);
		firmUser.setFirm(firm);
		firmUser.setPassword(Encryption.encryption(firmID, "111111", null));
		getService().update(firmUser);

		User user = (User) this.request.getSession().getAttribute("CurrentUser");
		gnnt.MEBS.bank.mgr.model.Log log = new gnnt.MEBS.bank.mgr.model.Log();
		log.setLogDate(new Date());
		log.setLogIP(user.getIpAddress());
		log.setLogopr(user.getUserId());
		log.setLogcontent("初始化交易商 " + firmID + " 密码");
		getService().add(log);

		addReturnValue(1, 130000L, new Object[] { "初始化交易商 " + firmID + " 密码为 111111" });
		return "success";
	}

	public String[] getBankIDs() {
		String[] ids = this.request.getParameterValues("ids");
		if ((ids == null) || (ids.length == 0)) {
			throw new IllegalArgumentException("删除主键数组不能为空！");
		}
		return ids;
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
}