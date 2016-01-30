package gnnt.MEBS.integrated.mgr.action.usermanage;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.common.mgr.common.Global;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.common.QueryConditions;
import gnnt.MEBS.common.mgr.common.ReturnValue;
import gnnt.MEBS.common.mgr.model.TradeModule;
import gnnt.MEBS.common.mgr.model.User;
import gnnt.MEBS.common.mgr.statictools.Tools;
import gnnt.MEBS.integrated.mgr.integrated.IntegratedGlobal;
import gnnt.MEBS.integrated.mgr.model.CertificateType;
import gnnt.MEBS.integrated.mgr.model.FirmCategory;
import gnnt.MEBS.integrated.mgr.model.Industry;
import gnnt.MEBS.integrated.mgr.model.SystemProps;
import gnnt.MEBS.integrated.mgr.model.Zone;
import gnnt.MEBS.integrated.mgr.model.usermanage.MFirm;
import gnnt.MEBS.integrated.mgr.model.usermanage.MFirmApply;
import gnnt.MEBS.integrated.mgr.model.usermanage.MFirmModule;
import gnnt.MEBS.integrated.mgr.model.usermanage.Trader;
import gnnt.MEBS.integrated.mgr.service.MFirmService;
import gnnt.MEBS.logonService.vo.CompulsoryLogoffVO;

@Controller("mfirmAction")
@Scope("request")
public class MFirmAction extends EcsideAction {
	private static final long serialVersionUID = 1L;
	@Autowired
	@Qualifier("mfirmService")
	private MFirmService mfirmService;
	@Autowired
	@Resource(name = "firmStatusMap")
	protected Map<String, String> firmStatusMap;
	@Resource(name = "mfirmTypeMap")
	protected Map<Integer, String> mfirmTypeMap;
	@Resource(name = "certificateTypeMap")
	protected Map<Integer, String> certificateTypeMap;

	public Map<String, String> getFirmStatusMap() {
		return this.firmStatusMap;
	}

	public Map<Integer, String> getMfirmTypeMap() {
		return this.mfirmTypeMap;
	}

	public Map<Integer, String> getCertificateTypeMap() {
		return this.certificateTypeMap;
	}

	public String addFirmForward() {
		this.logger.debug("enter addFirmForward");
		Map localMap = getTradeMap();
		this.request.setAttribute("tradeModuleMap", localMap);
		PageRequest localPageRequest = new PageRequest(" and isvisibal = 'Y' order by sortNo");
		SystemProps localSystemProps = new SystemProps();
		localSystemProps.setPropsKey("Offset");
		localSystemProps = (SystemProps) getService().get(localSystemProps);
		String str = localSystemProps.getFirmIdLength();
		int i = Integer.parseInt(str.trim());
		this.request.setAttribute("firmLen", Integer.valueOf(i));
		Page localPage1 = getService().getPage(localPageRequest, new Zone());
		this.request.setAttribute("zoneList", localPage1.getResult());
		Page localPage2 = getService().getPage(localPageRequest, new Industry());
		this.request.setAttribute("industryList", localPage2.getResult());
		Page localPage3 = getService().getPage(localPageRequest, new CertificateType());
		this.request.setAttribute("certificateTypeList", localPage3.getResult());
		Page localPage4 = getService().getPage(localPageRequest, new FirmCategory());
		this.request.setAttribute("firmCategoryList", localPage4.getResult());
		return "success";
	}

	private Map<Integer, TradeModule> getTradeMap() {
		LinkedHashMap localLinkedHashMap = new LinkedHashMap();
		if ((Global.modelContextMap != null) && (Global.modelContextMap.size() > 0)) {
			Set localSet = Global.modelContextMap.keySet();
			Iterator localIterator = localSet.iterator();
			while (localIterator.hasNext()) {
				Integer localInteger = (Integer) localIterator.next();
				Map localMap = (Map) Global.modelContextMap.get(localInteger);
				if ("Y".equalsIgnoreCase((String) localMap.get("isFirmSet"))) {
					TradeModule localTradeModule = new TradeModule();
					localTradeModule.setAddFirmFn((String) localMap.get("addFirmFn"));
					localTradeModule.setCnName((String) localMap.get("cnName"));
					localTradeModule.setDelFirmFn((String) localMap.get("delFirmFn"));
					localTradeModule.setEnName((String) localMap.get("enName"));
					localTradeModule.setIsFirmSet((String) localMap.get("isFirmSet"));
					localTradeModule.setModuleId(localInteger);
					localTradeModule.setShortName((String) localMap.get("shortName"));
					localTradeModule.setUpdateFirmStatusFn((String) localMap.get("updateFirmStatusFn"));
					localLinkedHashMap.put(localInteger, localTradeModule);
				}
			}
		}
		return localLinkedHashMap;
	}

	public String addMfirm() throws Exception {
		MFirm localMFirm = (MFirm) this.entity;
		String str1 = localMFirm.getFirmId();
		int i = str1.length();
		SystemProps localSystemProps = new SystemProps();
		localSystemProps.setPropsKey("Offset");
		localSystemProps = (SystemProps) getService().get(localSystemProps);
		String str2 = localSystemProps.getFirmIdLength();
		int j = Integer.parseInt(str2.trim());
		if (i > j) {
			addReturnValue(-1, 130104L, new Object[] { str1, Integer.valueOf(j) });
			return "success";
		}
		localMFirm.setStatus("N");
		localMFirm.setCreateTime(getService().getSysDate());
		HashMap localHashMap = new HashMap();
		String[] arrayOfString = this.request.getParameterValues("firmModules");
		if ((arrayOfString != null) && (arrayOfString.length > 0)) {
			for (String localObject4 : arrayOfString) {
				localHashMap.put(Integer.valueOf(Tools.strToInt((String) localObject4, 64536)), Boolean.valueOf(true));
			}
		}
		Map<Integer, TradeModule> map = getTradeMap();
		if (map != null) {
			Set localObject2 = new HashSet();
			Iterator localObject3 = ((Map) map).keySet().iterator();
			while (((Iterator) localObject3).hasNext()) {
				Integer localObject4 = (Integer) ((Iterator) localObject3).next();
				TradeModule localObject5 = (TradeModule) ((Map) map).get(localObject4);
				MFirmModule localMFirmModule = new MFirmModule();
				if (localHashMap.get(((TradeModule) localObject5).getModuleId()) != null) {
					localMFirmModule.setEnabled("Y");
				} else {
					localMFirmModule.setEnabled("N");
				}
				localMFirmModule.setModuleId((Integer) localObject4);
				localMFirmModule.setMfirm(localMFirm);
				((Set) localObject2).add(localMFirmModule);
			}
			localMFirm.setFirmModuleSet((Set) localObject2);
		}
		Object localObject2 = this.request.getParameter("username");
		Object localObject3 = this.request.getParameter("password");
		Object localObject4 = this.request.getParameter("enableKey");
		Object localObject5 = this.request.getParameter("kcode");
		int n = this.mfirmService.addFirm(localMFirm, (String) localObject2, (String) localObject3, (String) localObject4, (String) localObject5);
		if (n > 0) {
			addReturnValue(1, 110101L, new Object[] { localMFirm.getFirmId() });
			writeOperateLog(1021, "添加交易商:" + localMFirm.getFirmId(), 1, "");
		} else {
			addReturnValue(-1, 130101L, new Object[] { localMFirm.getFirmId() });
			writeOperateLog(1021, "添加交易商失败", 0, "调用添加交易商存储失败");
		}
		return "success";
	}

	public String passMfirm() throws Exception {
		this.logger.debug("交易商审核通过==添加交易商");
		String str1 = this.request.getParameter("applyID");
		MFirmApply localMFirmApply = new MFirmApply();
		localMFirmApply.setApplyID(Long.valueOf(Tools.strToLong(str1)));
		localMFirmApply = (MFirmApply) getService().get(localMFirmApply);
		if (localMFirmApply.getStatus().intValue() != 0) {
			addReturnValue(1, 110102L, new Object[] { str1 });
			return "success";
		}
		String str2 = this.request.getParameter("auditNote");
		MFirm localMFirm = (MFirm) this.entity;
		String str3 = localMFirm.getFirmId();
		int i = str3.length();
		SystemProps localSystemProps = new SystemProps();
		localSystemProps.setPropsKey("Offset");
		localSystemProps = (SystemProps) getService().get(localSystemProps);
		String str4 = localSystemProps.getFirmIdLength();
		int j = Integer.parseInt(str4.trim());
		if (i > j) {
			addReturnValue(-1, 130103L, new Object[] { str1, str3, str3, Integer.valueOf(j) });
			return "success";
		}
		localMFirm.setStatus("N");
		localMFirm.setCreateTime(getService().getSysDate());
		localMFirm.setModifyTime(getService().getSysDate());
		HashMap localHashMap = new HashMap();
		String[] arrayOfString = this.request.getParameterValues("firmModules");
		if ((arrayOfString != null) && (arrayOfString.length > 0)) {
			for (String localObject4 : arrayOfString) {
				localHashMap.put(Integer.valueOf(Tools.strToInt((String) localObject4, 64536)), Boolean.valueOf(true));
			}
		}
		Map map = getTradeMap();
		if (map != null) {
			Set localObject2 = new HashSet();
			Iterator localObject3 = ((Map) map).keySet().iterator();
			while (((Iterator) localObject3).hasNext()) {
				Integer localObject4 = (Integer) ((Iterator) localObject3).next();
				TradeModule localObject5 = (TradeModule) ((Map) map).get(localObject4);
				MFirmModule localMFirmModule = new MFirmModule();
				if (localHashMap.get(((TradeModule) localObject5).getModuleId()) != null) {
					localMFirmModule.setEnabled("Y");
				} else {
					localMFirmModule.setEnabled("N");
				}
				localMFirmModule.setModuleId(((TradeModule) localObject5).getModuleId());
				localMFirmModule.setMfirm(localMFirm);
				((Set) localObject2).add(localMFirmModule);
			}
			localMFirm.setFirmModuleSet((Set) localObject2);
		}
		Object localObject2 = this.request.getParameter("username");
		Object localObject3 = this.request.getParameter("password");
		Object localObject4 = this.request.getParameter("enableKey");
		Object localObject5 = this.request.getParameter("kcode");
		int n = this.mfirmService.passFirm(localMFirm, (String) localObject2, (String) localObject3, str1, str2, (String) localObject4,
				(String) localObject5);
		if (n > 0) {
			writeOperateLog(1021, "申请交易商,申请号为" + str1 + ",对应的交易商代码为" + localMFirm.getFirmId(), 1, "");
			addReturnValue(1, 110103L, new Object[] { localMFirm.getFirmId() });
		} else {
			writeOperateLog(1021, "申请交易商,申请号为" + str1 + ",对应的交易商代码为" + localMFirm.getFirmId(), 0, "添加交易商时出现错误");
			addReturnValue(-1, 130102L, new Object[] { str1, localMFirm.getFirmId() });
		}
		return "success";
	}

	public String getFirmDetails() {
		this.logger.debug("enter getFirmrDetails");
		MFirm localMFirm = (MFirm) getService().get(this.entity);
		this.request.setAttribute("entity", localMFirm);
		if (localMFirm != null) {
			Map localObject1 = new HashMap();
			Iterator localObject2 = localMFirm.getFirmModuleSet().iterator();
			while (((Iterator) localObject2).hasNext()) {
				MFirmModule localObject3 = (MFirmModule) ((Iterator) localObject2).next();
				((Map) localObject1).put(((MFirmModule) localObject3).getModuleId(), ((MFirmModule) localObject3).getEnabled());
			}
			this.request.setAttribute("firmModuleMap", localObject1);
			this.request.setAttribute("tradeModuleMap", getTradeMap());
		}
		Object localObject1 = new PageRequest(" order by sortNo");
		Object localObject2 = getService().getPage((PageRequest) localObject1, new Zone());
		this.request.setAttribute("zoneList", ((Page) localObject2).getResult());
		Object localObject3 = getService().getPage((PageRequest) localObject1, new Industry());
		this.request.setAttribute("industryList", ((Page) localObject3).getResult());
		Page localPage1 = getService().getPage((PageRequest) localObject1, new CertificateType());
		this.request.setAttribute("certificateTypeList", localPage1.getResult());
		Page localPage2 = getService().getPage((PageRequest) localObject1, new FirmCategory());
		this.request.setAttribute("firmCategoryList", localPage2.getResult());
		return "success";
	}

	public String getFreezeFirmDetails() throws Exception {
		this.logger.debug("enter getFreezeFirmDetails");
		PageRequest localPageRequest1 = getPageRequest(this.request);
		MFirm localMFirm = (MFirm) getAbstractService(localPageRequest1).get(this.entity);
		this.request.setAttribute("entity", localMFirm);
		PageRequest localPageRequest2 = new PageRequest(" order by sortNo");
		Page localPage = getService().getPage(localPageRequest2, new FirmCategory());
		this.request.setAttribute("firmCategoryList", localPage.getResult());
		return "success";
	}

	public String updateFirm() throws Exception {
		MFirm localMFirm1 = (MFirm) this.entity;
		int i = 0;
		if ((localMFirm1.getStatus() != null) && (!localMFirm1.getStatus().equalsIgnoreCase("N"))) {
			i = 1;
		}
		try {
			localMFirm1.setModifyTime(getService().getSysDate());
		} catch (Exception localException) {
			this.logger.debug(Tools.getExceptionTrace(localException));
		}
		MFirm localMFirm2 = (MFirm) getService().get(localMFirm1);
		HashMap localHashMap = new HashMap();
		String[] arrayOfString = this.request.getParameterValues("firmModules");
		if ((arrayOfString != null) && (arrayOfString.length > 0)) {
			for (String localObject3 : arrayOfString) {
				localHashMap.put(Integer.valueOf(Tools.strToInt((String) localObject3, 64536)), Boolean.valueOf(true));
			}
		}
		Map map = getTradeMap();
		if (map != null) {
			Set localObject2 = new HashSet();
			Iterator localIterator1 = ((Map) map).keySet().iterator();
			while (localIterator1.hasNext()) {
				Integer localObject3 = (Integer) localIterator1.next();
				TradeModule localTradeModule = (TradeModule) ((Map) map).get(localObject3);
				MFirmModule localMFirmModule1 = null;
				if ((localMFirm2.getFirmModuleSet() != null) && (localMFirm2.getFirmModuleSet().size() > 0)) {
					Iterator localIterator2 = localMFirm2.getFirmModuleSet().iterator();
					while (localIterator2.hasNext()) {
						MFirmModule localMFirmModule2 = (MFirmModule) localIterator2.next();
						if (localMFirmModule2.getModuleId().equals(localObject3)) {
							localMFirmModule1 = (MFirmModule) localMFirmModule2.clone();
							break;
						}
					}
				}
				if (localMFirmModule1 == null) {
					localMFirmModule1 = new MFirmModule();
					localMFirmModule1.setModuleId((Integer) localObject3);
					localMFirmModule1.setMfirm(localMFirm2);
				}
				if (localHashMap.get(localTradeModule.getModuleId()) != null) {
					localMFirmModule1.setEnabled("Y");
				} else {
					if ("Y".equalsIgnoreCase(localMFirmModule1.getEnabled())) {
						i = 1;
					}
					localMFirmModule1.setEnabled("N");
				}
				((Set) localObject2).add(localMFirmModule1);
			}
			localMFirm1.setFirmModuleSet((Set) localObject2);
		}
		if (!"N".equalsIgnoreCase(localMFirm2.getStatus())) {
			i = 0;
		}
		Object localObject2 = null;
		if (i != 0) {
			localObject2 = downOnlineTraders(new String[] { localMFirm1.getFirmId() });
		}
		this.mfirmService.updateFirm(localMFirm1);
		if ((localObject2 != null) && (((String) localObject2).trim().length() > 0)) {
			addReturnValue(1, 999999L, new Object[] { "修改成功，但强制下线交易员" + (String) localObject2 + "失败" });
		} else {
			addReturnValue(1, 119902L);
		}
		writeOperateLog(1021, "修改交易商：" + localMFirm2.getFirmId(), 1, "");
		return "success";
	}

	public String freezeFirmList() throws Exception {
		PageRequest localPageRequest = getPageRequest(this.request);
		QueryConditions localQueryConditions = getQueryConditionsFromRequest(this.request);
		localQueryConditions.addCondition("status", "=", "D");
		localPageRequest.setFilters(localQueryConditions);
		localPageRequest.setSortColumns(" order by firmId");
		listByLimit(localPageRequest);
		return "success";
	}

	public String expurgateFirmList() throws Exception {
		PageRequest localPageRequest = getPageRequest(this.request);
		QueryConditions localQueryConditions = getQueryConditionsFromRequest(this.request);
		localQueryConditions.addCondition("status", "=", "E");
		localPageRequest.setFilters(localQueryConditions);
		localPageRequest.setSortColumns(" order by firmId");
		listByLimit(localPageRequest);
		return "success";
	}

	public String expurgate() throws Exception {
		String[] arrayOfString = this.request.getParameterValues("ids");
		try {
			Map localMap = this.mfirmService.delMfirmStatus(arrayOfString, "E");
			if (((BigDecimal) localMap.get("result")).intValue() > 0) {
				String localObject1 = "";
				for (String str : arrayOfString) {
					if (((String) localObject1).trim().length() > 0) {
						localObject1 = (String) localObject1 + ",";
					}
					if (((String) localObject1).length() > 1) {
						localObject1 = (String) localObject1 + str + ",";
					} else {
						localObject1 = str;
					}
				}
				addReturnValue(1, 110301L, new Object[] { localObject1 });
				writeOperateLog(1021, "注销交易商：(" + (String) localObject1 + ")", 1, "");
			} else {
				String localObject1 = "";
				for (String str : arrayOfString) {
					localObject1 = (String) localObject1 + str + ",";
				}
				localObject1 = ((String) localObject1).substring(0, ((String) localObject1).length() - 1);
				addReturnValue(-1, 130308L, new Object[] { localObject1, localMap.get("errorInfo").toString() });
				ReturnValue value = (ReturnValue) this.request.getAttribute("ReturnValue");
				if (value != null) {
					writeOperateLog(1021, "注销交易商(" + (String) localObject1 + ")", 0, ((ReturnValue) value).getInfo());
				}
			}
		} catch (Exception localException) {
			Object localObject1 = "";
			for (String str : arrayOfString) {
				localObject1 = (String) localObject1 + str + ",";
			}
			addReturnValue(-1, 130307L, new Object[] { localObject1 });
			writeOperateLog(1021, "注销交易商：(" + (String) localObject1 + ")", 0, localException.getMessage());
		}
		return "success";
	}

	public String freeze() throws Exception {
		logger.debug("enter freeze  冻结交易商");
		String as[] = request.getParameterValues("ids");
		String s = "";
		ArrayList arraylist = new ArrayList();
		String as1[] = as;
		int i = as1.length;
		for (int k = 0; k < i; k++) {
			String s2 = as1[k];
			String as2[] = s2.split(",");
			if (as2 == null || as2.length <= 0)
				continue;
			if ("N".equals(as2[1])) {
				s = (new StringBuilder()).append(s).append(as2[0]).append(",").toString();
				arraylist.add(as2[0]);
			} else {
				s = (new StringBuilder()).append(s).append("【[").append(as2[0]).append("]冻结失败，状态错误】,").toString();
			}
		}

		if (!"".equals(s))
			s = s.substring(0, s.length() - 1);
		as1 = new String[arraylist.size()];
		for (int j = 0; j < arraylist.size(); j++)
			as1[j] = (String) arraylist.get(j);

		String s1 = downOnlineTraders(as1);
		int l = mfirmService.updateMfrimStatus(as1, "D");
		if (l > 0) {
			if (s1 != null && s1.trim().length() > 0) {
				addReturnValue(1, 0x1af42L, new Object[] { s, s1 });
				writeOperateLog(1021, (new StringBuilder()).append("冻结交易商：(").append(s).append(")").toString(), 1, "");
			} else {
				addReturnValue(1, 0x1af41L, new Object[] { s });
				writeOperateLog(1021, (new StringBuilder()).append("冻结交易商：(").append(s).append(")").toString(), 1, "");
			}
		} else {
			switch (l) {
			case 0: // '\0'
				addReturnValue(-1, 0x1fd61L, new Object[] { s });
				break;

			case -900:
				addReturnValue(-1, 0x1fd61L, new Object[] { s });
				break;

			case -901:
				addReturnValue(-1, 0x1fd62L, new Object[] { s });
				break;

			case -902:
				addReturnValue(-1, 0x1fd63L, new Object[] { s });
				break;

			case -903:
				addReturnValue(-1, 0x1fd64L, new Object[] { s });
				break;

			case -1000:
				addReturnValue(-1, 0x1fd65L, new Object[] { s });
				break;

			default:
				addReturnValue(-1, 0x1fd66L, new Object[] { s, Integer.valueOf(l) });
				break;
			}
			ReturnValue returnvalue = (ReturnValue) request.getAttribute("ReturnValue");
			if (returnvalue != null)
				writeOperateLog(1021, (new StringBuilder()).append("冻结交易商(").append(s).append(")").toString(), 0, returnvalue.getInfo());
		}
		return "success";
	}

	public String unfreeze() throws Exception {
		String as[] = request.getParameterValues("ids");
		int i = mfirmService.updateMfrimStatus(as, "N");
		String s = "";
		String as1[] = as;
		int j = as1.length;
		for (int k = 0; k < j; k++) {
			String s1 = as1[k];
			s = (new StringBuilder()).append(s).append(s1).append(",").toString();
		}

		s = s.substring(0, s.length() - 1);
		if (i > 0) {
			addReturnValue(1, 0x1afa5L, new Object[] { s });
			writeOperateLog(1021, (new StringBuilder()).append("解冻交易商：(").append(s).append(")").toString(), 1, "");
		} else {
			switch (i) {
			case -900:
				addReturnValue(-1, 0x1fdc5L, new Object[] { s });
				break;

			case -901:
				addReturnValue(-1, 0x1fdc6L, new Object[] { s });
				break;

			case -902:
				addReturnValue(-1, 0x1fdc7L, new Object[] { s });
				break;

			case -903:
				addReturnValue(-1, 0x1fdc8L, new Object[] { s });
				break;

			case -1000:
				addReturnValue(-1, 0x1fdc9L, new Object[] { s });
				break;

			default:
				addReturnValue(-1, 0x1fdcaL, new Object[] { s, Integer.valueOf(i) });
				break;
			}
			ReturnValue returnvalue = (ReturnValue) request.getAttribute("ReturnValue");
			if (returnvalue != null)
				writeOperateLog(1021, (new StringBuilder()).append("解冻交易商(").append(s).append(")").toString(), 0, returnvalue.getInfo());
		}
		return "success";
	}

	private String downOnlineTraders(String[] as) {
		String s = "强制交易员下线";
		logger.debug(s);
		String s1 = "";
		if (as == null || as.length <= 0)
			return "";
		String s2 = "";
		String as1[] = as;
		int i = as1.length;
		for (int j = 0; j < i; j++) {
			String s3 = as1[j];
			if (s2.trim().length() > 0)
				s2 = (new StringBuilder()).append(s2).append(",").toString();
			s2 = (new StringBuilder()).append(s2).append("'").append(s3).append("'").toString();
		}

		QueryConditions queryconditions = new QueryConditions();
		queryconditions.addCondition("mfirm.firmId", "in", (new StringBuilder()).append("(").append(s2).append(")").toString());
		PageRequest pagerequest = new PageRequest(1, 10000, queryconditions, "");
		Page page = getService().getPage(pagerequest, new Trader());
		if (page == null || page.getResult() == null || page.getResult().size() <= 0)
			return "";
		String as2[] = new String[page.getResult().size()];
		for (int k = 0; k < page.getResult().size(); k++) {
			Trader trader = (Trader) page.getResult().get(k);
			if ("N".equalsIgnoreCase(trader.getStatus()))
				as2[k] = trader.getTraderId();
		}

		User user = (User) request.getSession().getAttribute("CurrentUser");
		CompulsoryLogoffVO compulsorylogoffvo = new CompulsoryLogoffVO();
		compulsorylogoffvo.setOperator(user.getUserId());
		compulsorylogoffvo.setLogonIP(user.getIpAddress());
		ArrayList arraylist = new ArrayList();
		String as3[] = as2;
		int i1 = as3.length;
		for (int j1 = 0; j1 < i1; j1++) {
			String s4 = as3[j1];
			arraylist.add(s4);
		}

		compulsorylogoffvo.setUserIDList(arraylist);
		int l = -3;
		try {
			l = IntegratedGlobal.getLogonService().compulsoryLogoff("front", compulsorylogoffvo);
		} catch (RemoteException remoteexception) {
			try {
				IntegratedGlobal.clearLogonService();
				l = IntegratedGlobal.getLogonService().compulsoryLogoff("front", compulsorylogoffvo);
			} catch (MalformedURLException malformedurlexception) {
				logger.error(remoteexception.getMessage());
				addReturnValue(-1, 0xf423fL, new Object[] { "远程调用协议错误" });
			} catch (NotBoundException notboundexception) {
				logger.error(remoteexception.getMessage());
				addReturnValue(-1, 0xf423fL, new Object[] { "远程调用无服务" });
			} catch (RemoteException remoteexception1) {
				logger.error(remoteexception.getMessage());
				addReturnValue(-1, 0xf423fL, new Object[] { "退出用户时连接RMI失败" });
			} catch (Exception exception1) {
				logger.error(remoteexception.getMessage());
				addReturnValue(-1, 0xf423fL, new Object[] { "调用远程服务异常" });
			}
		} catch (Exception exception) {
			logger.error(exception.getMessage());
			addReturnValue(-1, 0xf423fL, new Object[] { "调用远程服务异常" });
		}
		if (l == 1)
			s1 = "";
		else
			s1 = compulsorylogoffvo.getUserIDList().toString();
		return s1;
	}
}
