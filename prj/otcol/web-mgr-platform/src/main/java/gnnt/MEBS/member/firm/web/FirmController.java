package gnnt.MEBS.member.firm.web;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.util.WebUtils;

import gnnt.MEBS.base.copy.ParamUtil;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.base.query.QueryHelper;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.common.util.query.Utils;
import gnnt.MEBS.finance.util.StringUtil;
import gnnt.MEBS.member.ActiveUser.MD5;
import gnnt.MEBS.member.firm.services.FirmService;
import gnnt.MEBS.member.firm.services.SystemService;
import gnnt.MEBS.member.firm.services.TraderService;
import gnnt.MEBS.member.firm.unit.Firm;
import gnnt.MEBS.member.firm.unit.FirmCategory;
import gnnt.MEBS.member.firm.unit.FirmExtendData;
import gnnt.MEBS.member.firm.unit.FirmLog;
import gnnt.MEBS.member.firm.unit.FirmModule;
import gnnt.MEBS.member.firm.unit.Trader;
import gnnt.MEBS.member.firm.unit.TraderModule;
import gnnt.MEBS.timebargain.manage.service.TariffManager;
import gnnt.MEBS.timebargain.manage.webapp.action.AgencyRMIBean;

public class FirmController extends MultiActionController {
	private final transient Log logger = LogFactory.getLog(FirmController.class);

	public ModelAndView firmList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.logger.debug("entering 'firmList' method...");
		String brokerId = request.getParameter("brokerId");
		QueryConditions qc = QueryHelper.getQueryConditionsFromRequest(request);
		if (qc == null) {
			qc = new QueryConditions();
		}
		String LRphoneNo = "";
		String legalRepresentative = "";
		String beginDate = "";
		String endDate = "";
		if (qc.getConditionValue("LRphoneNo") != null) {
			LRphoneNo = qc.getConditionValue("LRphoneNo").toString();
		}
		if (qc.getConditionValue("legalRepresentative") != null) {
			legalRepresentative = qc.getConditionValue("legalRepresentative").toString();
		}
		if (qc.getConditionValue("m.createTime", ">=") != null) {
			beginDate = Utils.formatDate("yyyy-MM-dd", (Date) qc.getConditionValue("m.createTime", ">="));
		}
		if (qc.getConditionValue("m.createTime", "<=") != null) {
			endDate = Utils.formatDate("yyyy-MM-dd", (Date) qc.getConditionValue("m.createTime", "<="));
		}
		qc.addCondition("m.status", "in", "('N','D','E','U')");

		PageInfo pageInfo = QueryHelper.getPageInfoFromRequest(request);
		if (pageInfo == null) {
			pageInfo = new PageInfo(1, 15, "firmId", false);
		}
		FirmService fs = (FirmService) gnnt.MEBS.member.firm.util.SysData.getBean("m_firmService");
		TariffManager tariffManager = (TariffManager) gnnt.MEBS.timebargain.manage.util.SysData.getBean("tariffManager");
		List resultList = new ArrayList();
		if ((brokerId != null) && (!"".equals(brokerId))) {
			resultList = fs.getFirmLists(qc, pageInfo, brokerId);
		} else {
			resultList = fs.getFirmList(qc, pageInfo);
		}
		Map map = WebUtils.getParametersStartingWith(request, "_");
		ModelAndView mv = new ModelAndView("member/firm/listFirm", "resultList", resultList);
		if (request.getParameter("excel") != null) {
			mv = new ModelAndView("member/firm/listFirmToExcel", "resultList", resultList);
			mv.addObject("excel", "1");
		} else {
			mv.addObject("pageInfo", pageInfo);
			mv.addObject("oldParams", map);
			mv.addObject("brokerId", brokerId);
			mv.addObject("LRphoneNo", LRphoneNo);
			mv.addObject("legalRepresentative", legalRepresentative);
			mv.addObject("beginDate", beginDate);
			mv.addObject("endDate", endDate);
			mv.addObject("tariffList", tariffManager.getTariffPage());
		}
		List users = fs.getFirmCategoryList(null, null, null);
		request.setAttribute("users", users);
		return mv;
	}

	public ModelAndView firmAddForward(HttpServletRequest request, HttpServletResponse response) throws Exception {
		SystemService ss = (SystemService) gnnt.MEBS.member.firm.util.SysData.getBean("m_systemService");
		List moduleList = ss.getTradeModuleList(null, null);
		List<Map> bankList = ss.getBankList();
		List resultList = ((FirmService) gnnt.MEBS.member.firm.util.SysData.getBean("m_firmService")).getFirmCategoryList(null, null, null);
		ModelAndView mv = new ModelAndView("member/firm/createNewFirm", "resultList", resultList);
		mv.addObject("moduleList", moduleList);
		mv.addObject("bankList", bankList);
		return mv;
	}

	public ModelAndView firmAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.logger.debug("entering 'firmAdd' method...");

		FirmExtendData firm = new FirmExtendData();
		ParamUtil.bindData(request, firm);
		String accountName = request.getParameter("accountName");
		firm.setAccountName(accountName);
		String[] permissions = request.getParameterValues("permissions");
		List moduleList = new ArrayList();
		if ((permissions != null) && (permissions.length > 0)) {
			for (int i = 0; i < permissions.length; i++) {
				FirmModule firmModule = new FirmModule();
				firmModule.setModuleId(permissions[i]);
				firmModule.setEnabled("N");
				firmModule.setFirmId(firm.getFirmId());
				moduleList.add(firmModule);
			}
		}
		firm.addFirmModule(moduleList);

		String logonUser = AclCtrl.getLogonID(request);
		FirmService fs = (FirmService) gnnt.MEBS.member.firm.util.SysData.getBean("m_firmService");

		FirmLog firmLog = new FirmLog();
		String action = "交易商" + firm.getFirmId() + "添加,手续费套餐：" + firm.getTariffID();
		System.out.println(action);
		firmLog.setAction(action);
		firmLog.setFirmId(firm.getFirmId());
		firmLog.setUserId(logonUser);

		Map map = new HashMap();
		map.put("logopr", logonUser);
		map.put("logIp", getRemortIP(request));
		int result = -1;
		try {
			result = fs.createFirm(firm, firmLog, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String msg = "";
		if (result == 1) {
			msg = "创建交易商成功！";
		} else if (result == -2) {
			msg = "交易商重复";
		} else if (result == -1) {
			msg = "操作异常";
		} else {
			msg = "其他错误";
		}
		return new ModelAndView("member/public/done", "resultMsg", msg);
	}

	public ModelAndView firmMod(HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.logger.debug("entering 'firmMod' method...");

		this.logger.debug("firmId:" + request.getParameter("firmId"));
		String resultMsg = "";
		String firmId = ServletRequestUtils.getRequiredStringParameter(request, "firmId");
		FirmService fs = (FirmService) gnnt.MEBS.member.firm.util.SysData.getBean("m_firmService");
		Firm firm = fs.getFirmById(firmId);
		ParamUtil.bindData(request, firm);
		String[] permissions = request.getParameterValues("permissions");
		List moduleList = new ArrayList();
		if ((permissions != null) && (permissions.length > 0)) {
			for (int i = 0; i < permissions.length; i++) {
				FirmModule firmModule = new FirmModule();
				firmModule.setModuleId(permissions[i]);
				firmModule.setFirmId(firm.getFirmId());
				moduleList.add(firmModule);
			}
		}
		firm.addFirmModule(moduleList);
		FirmExtendData firmExtendData = new FirmExtendData();
		ParamUtil.bindData(request, firmExtendData);

		firm.setExtendData(firmExtendData.getExtendData());

		String logonUser = AclCtrl.getLogonID(request);
		String storeFirm = fs.getFirmById(firm.getFirmId()).getTariffID();
		String str = firm.getFirmId();
		if (!storeFirm.equals(firm.getTariffID())) {
			str = str + "手续费套餐：" + storeFirm + "-->" + firm.getTariffID();
		}
		fs.updateFirm(firm);
		this.logger.debug("修改交易商" + str);

		return new ModelAndView("member/public/done", "resultMsg", "更新交易商成功！");
	}

	public ModelAndView firmView(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String firmId = request.getParameter("firmId");
		FirmService fs = (FirmService) gnnt.MEBS.member.firm.util.SysData.getBean("m_firmService");
		Firm firm = fs.getFirmById(firmId);
		SystemService ss = (SystemService) gnnt.MEBS.member.firm.util.SysData.getBean("m_systemService");
		List moduleList = ss.getTradeModuleList(null, null);
		List resultList = ((FirmService) gnnt.MEBS.member.firm.util.SysData.getBean("m_firmService")).getFirmCategoryList(null, null, null);
		List<Map> bankList = fs.getBankList();
		ModelAndView mv = new ModelAndView("member/firm/editNewFirm", "firm", firm);
		List brokerList = fs.getBrokerByFirmId(firmId);
		String brokerId = null;
		if (brokerList.size() > 0) {
			brokerId = ((Map) brokerList.get(0)).get("brokerId").toString();
		}
		mv.addObject("moduleList", moduleList);
		request.setAttribute("resultList", resultList);
		mv.addObject("bankList", bankList);
		mv.addObject("brokerId", brokerId);
		return mv;
	}

	public ModelAndView setStatusFirm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.logger.debug("entering 'setStatusFirm' method...");

		String[] delCodes = request.getParameterValues("delCheck");
		String firmStatus = request.getParameter("status");
		String msg = "";
		String logonUser = AclCtrl.getLogonID(request);
		FirmService fs = (FirmService) gnnt.MEBS.member.firm.util.SysData.getBean("m_firmService");
		Firm firm = null;
		for (int i = 0; i < delCodes.length; i++) {
			firm = fs.getFirmById(delCodes[i]);
			firm.setStatus(firmStatus);

			FirmLog firmLog = new FirmLog();
			String action = "交易商" + firm.getFirmId() + "设为";
			if ("D".equals(firm.getStatus())) {
				action = action + "禁止";
			}
			if ("E".equals(firm.getStatus())) {
				action = action + "退市";
			} else {
				action = action + "正常";
			}
			firmLog.setAction(action);
			firmLog.setFirmId(firm.getFirmId());
			firmLog.setUserId(logonUser);

			int result = fs.setStatusFirm(firm, firmLog);
			if ((result < 0) && (result < 0)) {
				msg = msg + delCodes[i] + ",";
			}
		}
		if ("".equals(msg)) {
			msg = "设置成功！";
		} else {
			msg = msg.substring(0, msg.length() - 1);
			msg = msg + "设置状态有误";
		}
		return new ModelAndView("member/public/done", "resultMsg", msg);
	}

	public ModelAndView firmDelete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.logger.debug("entering 'firmDelete' method...");
		String[] delCodes = request.getParameterValues("delCheck");
		String msg = "";
		String logonUser = AclCtrl.getLogonID(request);
		FirmService fs = (FirmService) gnnt.MEBS.member.firm.util.SysData.getBean("m_firmService");
		for (int i = 0; i < delCodes.length; i++) {
			FirmLog firmLog = new FirmLog();
			String action = "交易商" + delCodes[i] + "删除";
			firmLog.setAction(action);
			firmLog.setFirmId(delCodes[i]);
			firmLog.setUserId(logonUser);

			int result = fs.deleteFirm(fs.getFirmById(delCodes[i]), firmLog);
			if (result < 0) {
				msg = msg + delCodes[i] + ",";
			}
		}
		if ("".equals(msg)) {
			msg = "删除成功！";
		} else {
			msg = msg.substring(0, msg.length() - 1);
			msg = msg + "非退市状态,不能删除";
		}
		return new ModelAndView("member/public/done", "resultMsg", msg);
	}

	public ModelAndView getFirmAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String firmId = request.getParameter("firmId");
		String sign = null;
		String permissions = "";
		FirmService fs = (FirmService) gnnt.MEBS.member.firm.util.SysData.getBean("m_firmService");
		if (firmId != null) {
			Firm firm = fs.getFirmById(firmId);
			if (firm != null) {
				sign = "true";
			} else {
				sign = "false";
			}
			List listFm = firm.getFirmModule();
			if ((listFm != null) && (listFm.size() > 0)) {
				for (int i = 0; i < listFm.size(); i++) {
					permissions = permissions + "," + ((FirmModule) listFm.get(i)).getModuleId() + ",";
				}
			}
		}
		PrintWriter out = response.getWriter();
		out.flush();
		out.print(sign + "|||" + permissions);
		out.close();
		return null;
	}

	public ModelAndView getTraderAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String traderId = request.getParameter("traderId");
		TraderService ts = (TraderService) gnnt.MEBS.member.firm.util.SysData.getBean("m_traderService");
		String sign = null;
		if (traderId != null) {
			Trader trader = ts.getTraderById(traderId);
			if (trader != null) {
				sign = "false";
			} else {
				sign = "true";
			}
		}
		PrintWriter out = response.getWriter();
		out.flush();
		out.print(sign);
		out.close();
		return null;
	}

	public ModelAndView traderList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.logger.debug("entering 'traderList' method...");

		QueryConditions qc = QueryHelper.getQueryConditionsFromRequest(request);
		PageInfo pageInfo = QueryHelper.getPageInfoFromRequest(request);
		if (pageInfo == null) {
			pageInfo = new PageInfo(1, 15, "traderId", false);
		}
		if (qc == null) {
			qc = new QueryConditions();
		}
		String firmId = request.getParameter("firmId");
		if (firmId != null) {
			qc.addCondition("firmId", "=", firmId);
		}
		String beginDate = "";
		String endDate = "";
		if (qc.getConditionValue("createTime", ">=") != null) {
			beginDate = Utils.formatDate("yyyy-MM-dd", (Date) qc.getConditionValue("createTime", ">="));
		}
		if (qc.getConditionValue("createTime", "<=") != null) {
			endDate = Utils.formatDate("yyyy-MM-dd", (Date) qc.getConditionValue("createTime", "<="));
		}
		TraderService ts = (TraderService) gnnt.MEBS.member.firm.util.SysData.getBean("m_traderService");
		List resultList = ts.getTraderList(qc, pageInfo);
		Map map = WebUtils.getParametersStartingWith(request, "_");
		if (firmId != null) {
			map = new HashMap();
			map.put("firmId[=]", firmId);
		}
		ModelAndView mv = new ModelAndView("member/trader/listTrader", "resultList", resultList);
		if (request.getParameter("excel") != null) {
			List excelList = ts.getTraderList(qc, null);
			mv = new ModelAndView("member/trader/listTraderToExcel", "resultList", excelList);
			mv.addObject("excel", "1");
			mv.addObject("firmId", firmId);
		} else {
			mv.addObject("pageInfo", pageInfo);
			mv.addObject("oldParams", map);
			mv.addObject("firmId", firmId);
			mv.addObject("beginDate", beginDate);
			mv.addObject("endDate", endDate);
		}
		return mv;
	}

	public ModelAndView traderAddForward(HttpServletRequest request, HttpServletResponse response) throws Exception {
		SystemService ss = (SystemService) gnnt.MEBS.member.firm.util.SysData.getBean("m_systemService");
		List moduleList = ss.getTradeModuleList(null, null);
		ModelAndView mv = new ModelAndView("member/trader/createTrader");

		String firmId = request.getParameter("firmId");
		if ((firmId != null) && (!"".equals(firmId.trim()))) {
			FirmService fs = (FirmService) gnnt.MEBS.member.firm.util.SysData.getBean("m_firmService");
			Firm firm = fs.getFirmById(firmId);
			mv.addObject("firm", firm);
		}
		mv.addObject("moduleList", moduleList);
		return mv;
	}

	public ModelAndView traderAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.logger.debug("entering 'traderAdd' method...");
		Trader trader = new Trader();
		ParamUtil.bindData(request, trader);
		String[] permissions = request.getParameterValues("permissions");
		String smallCount = request.getParameter("smallCount");
		List<TraderModule> moduleList = new ArrayList();
		if ((permissions != null) && (permissions.length > 0)) {
			for (int i = 0; i < permissions.length; i++) {
				TraderModule traderModule = new TraderModule();
				traderModule.setModuleId(permissions[i]);
				traderModule.setTraderId(trader.getFirmId() + smallCount);
				moduleList.add(traderModule);
			}
		}
		trader.addTraderModule(moduleList);

		trader.setTraderId(trader.getFirmId() + smallCount);
		String pwd = MD5.getMD5(trader.getTraderId(), trader.getPassword());
		trader.setPassword(pwd);
		TraderService ts = (TraderService) gnnt.MEBS.member.firm.util.SysData.getBean("m_traderService");
		int result = ts.createTrader(trader);
		String msg = "";
		if (result < 1) {
			msg = "添加失败";
		} else {
			msg = "添加成功";
		}
		return new ModelAndView("member/public/done", "resultMsg", msg);
	}

	public ModelAndView traderMod(HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.logger.debug("entering 'traderMod' method...");
		String traderId = ServletRequestUtils.getRequiredStringParameter(request, "traderId");
		TraderService ts = (TraderService) gnnt.MEBS.member.firm.util.SysData.getBean("m_traderService");
		Trader trader = ts.getTraderById(traderId);
		ParamUtil.bindData(request, trader);
		String[] permissions = request.getParameterValues("permissions");
		List<TraderModule> moduleList = new ArrayList();
		if ((permissions != null) && (permissions.length > 0)) {
			for (int i = 0; i < permissions.length; i++) {
				TraderModule traderModule = new TraderModule();
				traderModule.setModuleId(permissions[i]);
				traderModule.setTraderId(trader.getTraderId());
				moduleList.add(traderModule);
			}
		}
		trader.addTraderModule(moduleList);
		String msg = "";
		try {
			int result = ts.updateTrader(trader);
			this.logger.debug("result:" + result);
			if (result > 0) {
				msg = "更新成功";
			} else {
				msg = "更新失败";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("member/public/done", "resultMsg", msg);
	}

	public ModelAndView traderView(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String traderId = request.getParameter("traderId");
		TraderService ts = (TraderService) gnnt.MEBS.member.firm.util.SysData.getBean("m_traderService");
		Trader trader = ts.getTraderById(traderId);

		FirmService fs = (FirmService) gnnt.MEBS.member.firm.util.SysData.getBean("m_firmService");
		Firm firm = fs.getFirmById(trader.getFirmId());

		SystemService ss = (SystemService) gnnt.MEBS.member.firm.util.SysData.getBean("m_systemService");
		List moduleList = ss.getTradeModuleList(null, null);
		ModelAndView mv = new ModelAndView("member/trader/editTrader", "trader", trader);
		mv.addObject("moduleList", moduleList);
		mv.addObject("firm", firm);
		return mv;
	}

	public ModelAndView setStatusTrader(HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.logger.debug("entering 'setStatusTrader' method...");
		String[] delCodes = request.getParameterValues("delCheck");
		String traderStatus = request.getParameter("status");
		String msg = "";
		String logonUser = AclCtrl.getLogonID(request);
		TraderService ts = (TraderService) gnnt.MEBS.member.firm.util.SysData.getBean("m_traderService");
		for (int i = 0; i < delCodes.length; i++) {
			Trader trader = ts.getTraderById(delCodes[i]);
			trader.setStatus(traderStatus);

			FirmLog firmLog = new FirmLog();
			String action = "交易员设为";
			if ("D".equals(trader.getStatus())) {
				action = action + "禁止";
			} else {
				action = action + "正常";
			}
			firmLog.setAction(action);
			firmLog.setFirmId(delCodes[i]);
			firmLog.setUserId(logonUser);

			int result = ts.setStatusTrader(trader, firmLog);
			if (result < 0) {
				msg = msg + delCodes[i] + ",";
			}
		}
		if ("".equals(msg)) {
			msg = "设置成功！";
		} else {
			msg = msg.substring(0, msg.length() - 1);
			msg = msg + "设置状态有误";
		}
		return new ModelAndView("member/public/done", "resultMsg", msg);
	}

	public ModelAndView traderDelete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.logger.debug("entering 'traderDelete' method...");
		String msg = "";
		String[] delCodes = request.getParameterValues("delCheck");
		String logonUser = AclCtrl.getLogonID(request);
		TraderService ts = (TraderService) gnnt.MEBS.member.firm.util.SysData.getBean("m_traderService");
		for (int i = 0; i < delCodes.length; i++) {
			FirmLog firmLog = new FirmLog();
			String action = "交易员" + delCodes[i] + "删除";
			firmLog.setAction(action);
			firmLog.setFirmId(delCodes[i]);
			firmLog.setUserId(logonUser);
			int result = ts.deleteTrader(delCodes[i], firmLog);
			if (result < 0) {
				msg = msg + delCodes[i] + ",";
			}
		}
		if ("".equals(msg)) {
			msg = "删除成功！";
		} else {
			msg = msg.substring(0, msg.length() - 1);
			msg = msg + "不能删除";
		}
		return new ModelAndView("member/public/done", "resultMsg", msg);
	}

	public ModelAndView changePwdTrader(HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.logger.debug("entering 'changePwdTrader' method...");

		String traderId = ServletRequestUtils.getRequiredStringParameter(request, "traderId");
		String password = ServletRequestUtils.getRequiredStringParameter(request, "password");
		String pwd = StringUtil.MD5(traderId, password);
		TraderService ts = (TraderService) gnnt.MEBS.member.firm.util.SysData.getBean("m_traderService");
		ts.changePwdTrader(traderId, pwd);

		return new ModelAndView("member/public/done", "resultMsg", "更新密码成功");
	}

	public ModelAndView doSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.logger.debug("entering 'doSubmit' method...");

		this.logger.debug("operation:" + request.getParameter("operation"));
		int operation = Integer.parseInt(request.getParameter("operation"));
		ModelAndView mv = null;
		if (operation == 1) {
			mv = firmMod(request, response);
		} else if (operation == 2) {
			mv = auditFirm(request, response);
		} else if (operation == 3) {
			mv = backFirm(request, response);
		}
		return mv;
	}

	public ModelAndView backFirm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.logger.debug("entering 'backFirm' method...");
		this.logger.debug("firmId:" + request.getParameter("firmId"));

		String firmId = request.getParameter("firmId");
		ModelAndView mv = new ModelAndView("member/public/done");
		mv.addObject("firmId", firmId);

		String reasonContent = request.getParameter("reasonContent");
		if (reasonContent != null) {
			FirmLog log = new FirmLog();
			log.setUserId(AclCtrl.getLogonID(request));
			log.setAction("交易商驳回");
			FirmService firmService = (FirmService) gnnt.MEBS.member.firm.util.SysData.getBean("m_firmService");
			log.setFirmId(firmId);
			firmService.rejectFirm(firmId, reasonContent, log);
			mv.addObject("resultMsg", "操作成功!");
		}
		return mv;
	}

	public ModelAndView auditFirm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.logger.debug("entering 'auditFirm' method...");

		String firmId = request.getParameter("firmId");

		String msg = "审核成功";
		FirmService firmService = (FirmService) gnnt.MEBS.member.firm.util.SysData.getBean("m_firmService");
		String storeFirm = firmService.getFirmById(firmId).getTariffID();
		this.logger.debug("交易商审核" + firmId + "手续费套餐：" + storeFirm);
		try {
			FirmLog log = new FirmLog();
			log.setUserId(AclCtrl.getLogonID(request));
			log.setFirmId(firmId);
			log.setAction("交易商审核");
			int result = firmService.verifyFirm(firmId, "N", log);
			System.out.println("审核结果为" + result);
			if (result == -2) {
				msg = "该交易商已审核成功";
				System.out.println("交易商审核成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg = "审核失败";
		}
		return new ModelAndView("member/public/done", "resultMsg", msg);
	}

	public ModelAndView frozenFirm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.logger.debug("entering 'frozenFirm' method...");

		QueryConditions qc = QueryHelper.getQueryConditionsFromRequest(request);
		if (qc == null) {
			qc = new QueryConditions();
		}
		PageInfo pageInfo = QueryHelper.getPageInfoFromRequest(request);
		if (pageInfo == null) {
			pageInfo = new PageInfo(1, 15, "firmId", false);
		}
		FirmService fs = (FirmService) gnnt.MEBS.member.firm.util.SysData.getBean("m_firmService");
		List resultList = fs.getFirms(qc, pageInfo);

		Map map = WebUtils.getParametersStartingWith(request, "_");
		ModelAndView mv = new ModelAndView("member/firm/frozenFirmList", "resultList", resultList);
		if (request.getParameter("excel") != null) {
			List excelList = fs.getFirms(qc, null);
			mv = new ModelAndView("member/firm/listFirmToExcel", "resultList", excelList);
			mv.addObject("excel", "1");
		} else {
			mv.addObject("pageInfo", pageInfo);
			mv.addObject("oldParams", map);
		}
		return mv;
	}

	public ModelAndView frozenFirmStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.logger.debug("entering 'frozenFirmStatus' method...");
		FirmService fs = (FirmService) gnnt.MEBS.member.firm.util.SysData.getBean("m_firmService");
		String[] delCodes = request.getParameterValues("delCheck");
		String firmStatus = request.getParameter("status");
		String msg = "";

		String logonUser = AclCtrl.getLogonID(request);
		AgencyRMIBean remObject = new AgencyRMIBean(request);
		for (int i = 0; i < delCodes.length; i++) {
			int result = fs.setStatusFirm(delCodes[i], firmStatus, logonUser);
			result = fs.updatefirmbankMaxPertransMoney(delCodes[i], firmStatus, logonUser);
			if (firmStatus.equals("U")) {
				remObject.kickAllTrader(delCodes[i]);
			}
			if ((result < 0) && (result < 0)) {
				msg = msg + delCodes[i] + ",";
			}
		}
		if ("".equals(msg)) {
			msg = "设置成功！";
		} else {
			msg = msg.substring(0, msg.length() - 1);
			msg = msg + "设置状态有误";
		}
		return new ModelAndView("member/public/done", "resultMsg", msg);
	}

	public ModelAndView firmCategoryList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.logger.debug("entering 'firmCategoryList' method...");
		String name = request.getParameter("_name[like]");
		String firmId = request.getParameter("_id[like]");
		PageInfo pageInfo = QueryHelper.getPageInfoFromRequest(request);
		if (pageInfo == null) {
			pageInfo = new PageInfo(1, 15, "id", false);
		}
		FirmService fs = (FirmService) gnnt.MEBS.member.firm.util.SysData.getBean("m_firmService");
		List resultList = fs.getFirmCategoryList(firmId, name, pageInfo);

		Map map = WebUtils.getParametersStartingWith(request, "_");
		ModelAndView mv = new ModelAndView("member/firm/listFirmCategory", "resultList", resultList);

		mv.addObject("pageInfo", pageInfo);
		mv.addObject("oldParams", map);

		return mv;
	}

	public ModelAndView firmCategoryAddForward(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("member/firm/createNewFirmCategory");

		return mv;
	}

	public ModelAndView firmCategoryAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.logger.debug("entering 'firmAdd' method...");

		String name = request.getParameter("name");
		String note = request.getParameter("note");
		String firmId = request.getParameter("firmId");
		FirmCategory firmCategory = new FirmCategory();
		firmCategory.setId(Long.valueOf(firmId));
		firmCategory.setName(name);
		firmCategory.setNote(note);

		FirmService fs = (FirmService) gnnt.MEBS.member.firm.util.SysData.getBean("m_firmService");

		int result = -1;
		try {
			result = fs.createFirmCategory(firmCategory);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String msg = "";
		if (result == 1) {
			msg = "创建交易商类别成功！";
		} else if (result == -2) {
			msg = "交易商类别重复";
		} else if (result == -1) {
			msg = "操作异常";
		} else {
			msg = "其他错误";
		}
		return new ModelAndView("member/public/done", "resultMsg", msg);
	}

	public ModelAndView firmCategoryUpdateForward(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String firmId = request.getParameter("firmId");
		FirmService fs = (FirmService) gnnt.MEBS.member.firm.util.SysData.getBean("m_firmService");
		List firmCategorys = fs.getFirmCategoryById(firmId);
		if ((firmCategorys != null) && (firmCategorys.size() > 0)) {
			request.setAttribute("firmCategory", firmCategorys.get(0));
		}
		ModelAndView mv = new ModelAndView("member/firm/updateNewFirmCategory");

		return mv;
	}

	public ModelAndView firmCategoryUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String name = request.getParameter("name");
		String note = request.getParameter("note");
		String oldId = request.getParameter("oldId");
		String firmId = request.getParameter("firmId");
		FirmCategory firmCategory = new FirmCategory();
		firmCategory.setId(Long.valueOf(firmId.trim()));
		firmCategory.setName(name);
		firmCategory.setNote(note);
		FirmService fs = (FirmService) gnnt.MEBS.member.firm.util.SysData.getBean("m_firmService");

		int result = -1;
		try {
			result = fs.updateFirmCategory(firmCategory, oldId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String msg = "";
		if (result == 1) {
			msg = "更新交易商类别成功！";
		} else if (result == -2) {
			msg = "交易商类别重复";
		} else if (result == -1) {
			msg = "操作异常";
		} else {
			msg = "其他错误";
		}
		return new ModelAndView("member/public/done", "resultMsg", msg);
	}

	public ModelAndView firmCategoryDelete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.logger.debug("entering 'firmDelete' method...");
		String[] delCodes = request.getParameterValues("delCheck");
		String msg = "";
		System.out.println(delCodes[0]);
		FirmService fs = (FirmService) gnnt.MEBS.member.firm.util.SysData.getBean("m_firmService");
		for (int i = 0; i < delCodes.length; i++) {
			int result = fs.deleteFirmCategory(delCodes[i]);
			if (result < 0) {
				msg = msg + delCodes[i] + ",";
			}
		}
		if ("".equals(msg)) {
			msg = "删除成功！";
		} else {
			msg = msg + "删除失败";
		}
		return new ModelAndView("member/public/done", "resultMsg", msg);
	}

	public String getRemortIP(HttpServletRequest request) {
		if (request.getHeader("x-forwarded-for") == null) {
			return request.getRemoteAddr();
		}
		return request.getHeader("x-forwarded-for");
	}
}
