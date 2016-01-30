package gnnt.MEBS.timebargain.mgr.action.firmSet;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.common.QueryConditions;
import gnnt.MEBS.timebargain.mgr.model.firmSet.TCustomer;
import gnnt.MEBS.timebargain.mgr.model.firmSet.TTrader;
import gnnt.MEBS.timebargain.mgr.model.firmSet.TradePrivilege;
import gnnt.MEBS.timebargain.mgr.service.TradePrivilegeService;
import gnnt.MEBS.timebargain.mgr.statictools.CommonDictionary;

@Controller("tradePrivilegeAction")
@Scope("request")
public class TradePrivilegeAction extends EcsideAction {
	private static final long serialVersionUID = 6792576342232310520L;

	@Resource(name = "firm_statusMap")
	private Map<Integer, String> firm_statusMap;

	@Resource(name = "tradePrivilege_typeMap")
	private Map<Integer, String> tradePrivilege_typeMap;

	@Resource(name = "tradePrivilege_kindMap")
	private Map<Integer, String> tradePrivilege_kindMap;

	@Resource(name = "privilegeCode_BMap")
	private Map<Integer, String> privilegeCode_BMap;

	@Resource(name = "privilegeCode_SMap")
	private Map<Integer, String> privilegeCode_SMap;

	@Autowired
	@Qualifier("tradePrivilegeService")
	private TradePrivilegeService tradePrivilegeService;

	public String addFirmPrivilege() {
		this.logger.debug("enter addFirmPrivilege");
		getService().add(this.entity);
		addReturnValue(1, 119901L);
		return "success";
	}

	public String updateStatus() {
		this.logger.debug("enter updateStatus");
		String[] arrayOfString = this.request.getParameterValues("ids");
		String str = this.request.getParameter("status");
		getService().updateBYBulk(this.entity, "status=" + str, arrayOfString);
		return "success";
	}

	public String listCustomer() throws Exception {
		this.logger.debug("enter listCustomer");
		String str = this.request.getParameter("firmID");
		if ((str != null) && (!"".equals(str))) {
			PageRequest localPageRequest = super.getPageRequest(this.request);
			QueryConditions localQueryConditions = (QueryConditions) localPageRequest.getFilters();
			localQueryConditions.addCondition("primary.firmIDs", "=", str);
			listByLimit(localPageRequest);
			this.request.setAttribute("firmID", str);
		}
		return "success";
	}

	public String forwardAddCustomer() {
		this.logger.debug("enter forwardAddCustomer");
		String str = this.request.getParameter("firmID");
		this.request.setAttribute("firmID", str);
		return "success";
	}

	public String addCustomer() throws Exception {
		this.logger.debug("enter addCustomer");
		String str1 = this.request.getParameter("firmID");
		String str2 = this.request.getParameter("code");
		String str3 = this.request.getParameter("startCode");
		String str4 = this.request.getParameter("endCode");
		String str5 = this.request.getParameter("status");
		String[] arrayOfString = null;
		if ((str2 != null) && (!"".equals(str2)))
			arrayOfString = str2.split(",");
		int i = 0;
		int j = 0;
		if (((str3 != null ? 1 : 0) & (!"".equals(str3) ? 1 : 0)) != 0) {
			i = Integer.parseInt(str3);
			j = Integer.parseInt(str4) + 1;
		}
		String str6 = "";
		int k = 0;
		int m;
		TCustomer localTCustomer1;
		TCustomer localTCustomer2;
		if (arrayOfString != null)
			for (m = 0; m < arrayOfString.length; m++) {
				localTCustomer1 = new TCustomer();
				localTCustomer1.setCustomerID(str1 + arrayOfString[m].trim());
				if (arrayOfString[m].trim().length() != 2) {
					addReturnValue(0, 151103L, new Object[] { "输入格式不正确，请重新输入！" });
					k++;
				} else {
					localTCustomer2 = (TCustomer) getService().get(localTCustomer1);
					if (localTCustomer2 != null) {
						addReturnValue(-1, 151103L, new Object[] { "添加失败，已存在二级客户代码：" + arrayOfString[m].trim() });
						k++;
						break;
					}
				}
				if (k == 0) {
					localTCustomer1.setFirmIDs(str1);
					localTCustomer1.setCode(arrayOfString[m].trim());
					localTCustomer1.setStatus(Integer.valueOf(Integer.parseInt(str5)));
					localTCustomer1.setCreateTime(getService().getSysDate());
					localTCustomer1.setModifyTime(getService().getSysDate());
					getService().add(localTCustomer1);
					addReturnValue(1, 119901L);
				}
			}
		if (i != 0)
			for (m = i; m < j; m++) {
				if (m < 10)
					str6 = "0" + m;
				else
					str6 = m + "";
				localTCustomer1 = new TCustomer();
				localTCustomer1.setCustomerID(str1 + str6);
				localTCustomer2 = (TCustomer) getService().get(localTCustomer1);
				if (localTCustomer2 != null) {
					addReturnValue(-1, 151103L, new Object[] { "添加失败，已存在二级客户代码：" + str6 });
					k++;
					break;
				}
				if (k == 0) {
					localTCustomer1.setFirmIDs(str1);
					localTCustomer1.setCode(str6);
					localTCustomer1.setStatus(Integer.valueOf(Integer.parseInt(str5)));
					localTCustomer1.setCreateTime(getService().getSysDate());
					localTCustomer1.setModifyTime(getService().getSysDate());
					getService().add(localTCustomer1);
					addReturnValue(1, 119901L);
				}
			}
		return "success";
	}

	public String batchSetSaveFirmPrivilege() throws Exception {
		logger.debug("enter batchSetFirmPrivilege");
		String s = request.getParameter("typeID");
		String s1 = request.getParameter("startFirm");
		String s2 = request.getParameter("endFirm");
		TradePrivilege tradeprivilege = (TradePrivilege) entity;
		try {
			String as[] = null;
			if (s != null && !"".equals(s))
				as = s.split(",");
			String s3 = "";
			if (as != null) {
				for (int i = 0; i < as.length; i++) {
					String s4 = as[i].trim();
					List list = getService()
							.getListBySql((new StringBuilder()).append("select * from m_firm where FirmId='").append(s4).append("'").toString());
					if (list != null && list.size() > 0) {
						TradePrivilege tradeprivilege1 = new TradePrivilege();
						tradeprivilege1.setType(tradeprivilege.getType());
						tradeprivilege1.setTypeID(s4);
						tradeprivilege1.setKind(tradeprivilege.getKind());
						tradeprivilege1.setKindID(tradeprivilege.getKindID());
						tradeprivilege1.setPrivilegeCode_B(tradeprivilege.getPrivilegeCode_B());
						tradeprivilege1.setPrivilegeCode_S(tradeprivilege.getPrivilegeCode_S());
						tradePrivilegeService.addTradePrivilege(tradeprivilege1);
						addReturnValue(1, 0x1d463L);
						writeOperateLog(1505, (new StringBuilder()).append("交易商：").append(tradeprivilege1.getTypeID()).append("，添加交易权限").toString(),
								1, "添加交易商交易权限");
					} else {
						s3 = (new StringBuilder()).append(s3).append(s4).append(",").toString();
					}
				}

			}
			boolean flag = false;
			boolean flag1 = false;
			String s5 = "";
			if (s1 != null && !"".equals(s1) && s2 != null && !"".equals(s2)) {
				int j = Integer.parseInt(s1);
				int k = Integer.parseInt(s2) + 1;
				for (int l = j; l < k; l++) {
					String s6 = (new StringBuilder()).append(l).append("").toString();
					List list1 = getService()
							.getListBySql((new StringBuilder()).append("select * from m_firm where FirmId='").append(s6).append("'").toString());
					if (list1 != null && list1.size() > 0) {
						TradePrivilege tradeprivilege2 = new TradePrivilege();
						tradeprivilege2.setType(tradeprivilege.getType());
						tradeprivilege2.setTypeID(s6);
						tradeprivilege2.setKind(tradeprivilege.getKind());
						tradeprivilege2.setKindID(tradeprivilege.getKindID());
						tradeprivilege2.setPrivilegeCode_B(tradeprivilege.getPrivilegeCode_B());
						tradeprivilege2.setPrivilegeCode_S(tradeprivilege.getPrivilegeCode_S());
						tradePrivilegeService.addTradePrivilege(tradeprivilege2);
						addReturnValue(1, 0x1d463L);
						writeOperateLog(1505, (new StringBuilder()).append("交易商：").append(tradeprivilege2.getTypeID()).append("，添加交易权限").toString(),
								1, "添加交易商交易权限");
					} else {
						s3 = (new StringBuilder()).append(s3).append(s6).append(",").toString();
					}
				}

			}
			if (s3 != null && !"".equals(s3))
				addReturnValue(1, 0x24e3fL, new Object[] { (new StringBuilder()).append("添加失败， 交易商 【").append(s3).append("】 不存在").toString() });
		} catch (Exception exception) {
			exception.printStackTrace();
			addReturnValue(-1, 0x1d464L);
		}
		return "success";
	}

	public String batchSetClearFirmPrivilege() throws Exception {
		this.logger.debug("enter batchSetFirmPrivilege");
		String str1 = this.request.getParameter("typeID");
		String str2 = this.request.getParameter("startFirm");
		String str3 = this.request.getParameter("endFirm");
		TradePrivilege localTradePrivilege1 = (TradePrivilege) this.entity;
		try {
			String[] arrayOfString = null;
			if ((str1 != null) && (!"".equals(str1)))
				arrayOfString = str1.split(",");
			if (arrayOfString != null)
				for (int i = 0; i < arrayOfString.length; i++) {
					TradePrivilege localTradePrivilege2 = new TradePrivilege();
					localTradePrivilege2.setType(localTradePrivilege1.getType());
					localTradePrivilege2.setTypeID(arrayOfString[i].trim());
					localTradePrivilege2.setKind(localTradePrivilege1.getKind());
					localTradePrivilege2.setKindID(localTradePrivilege1.getKindID());
					localTradePrivilege2.setPrivilegeCode_B(localTradePrivilege1.getPrivilegeCode_B());
					localTradePrivilege2.setPrivilegeCode_S(localTradePrivilege1.getPrivilegeCode_S());
					this.tradePrivilegeService.deleteTradePrivilege(localTradePrivilege2);
					addReturnValue(1, 119907L);
					writeOperateLog(1505, "交易商：" + localTradePrivilege2.getTypeID() + "，删除交易权限", 1, "删除交易商交易权限");
				}
			int i = 0;
			int j = 0;
			String str4 = "";
			if ((str2 != null) && (!"".equals(str2)) && (str3 != null) && (!"".equals(str3))) {
				i = Integer.parseInt(str2);
				j = Integer.parseInt(str3) + 1;
				for (int k = i; k < j; k++) {
					str4 = k + "";
					TradePrivilege localTradePrivilege3 = new TradePrivilege();
					localTradePrivilege3.setType(localTradePrivilege1.getType());
					localTradePrivilege3.setTypeID(str4);
					localTradePrivilege3.setKind(localTradePrivilege1.getKind());
					localTradePrivilege3.setKindID(localTradePrivilege1.getKindID());
					localTradePrivilege3.setPrivilegeCode_B(localTradePrivilege1.getPrivilegeCode_B());
					localTradePrivilege3.setPrivilegeCode_S(localTradePrivilege1.getPrivilegeCode_S());
					this.tradePrivilegeService.deleteTradePrivilege(localTradePrivilege3);
					addReturnValue(1, 119907L);
					writeOperateLog(1505, "交易商：" + localTradePrivilege3.getTypeID() + "，删除交易权限", 1, "删除交易商交易权限");
				}
			}
		} catch (Exception localException) {
			localException.printStackTrace();
			addReturnValue(-1, 119908L);
		}
		return "success";
	}

	public String deleteCustomer() throws SecurityException, NoSuchFieldException {
		logger.debug("enter deleteCustomer");
		String as[] = request.getParameterValues("ids");
		if (as == null || as.length == 0)
			throw new IllegalArgumentException("删除主键数组不能为空！");
		if (entity == null)
			throw new IllegalArgumentException("业务对象为空，所以操作表未知，不允许通过主键数组批量删除！");
		if (entity.fetchPKey() == null || entity.fetchPKey().getKey() == null || entity.fetchPKey().getKey().length() == 0)
			throw new IllegalArgumentException("业务对象未设置主键，不允许通过主键数组批量删除！");
		boolean flag = true;
		if (as != null) {
			String s = "";
			String as1[] = as;
			int i = as1.length;
			for (int l = 0; l < i; l++) {
				String s1 = as1[l];
				String s2 = s1.substring(s1.length() - 2, s1.length());
				if ("00".equals(s2))
					s = (new StringBuilder()).append(s).append(s1).append(" ").toString();
			}

			if (!s.equals("")) {
				addReturnValue(-1, 0x24e3fL, new Object[] { (new StringBuilder()).append(s).append("是默认客户，不能删除请重新选择！").toString() });
				flag = false;
			}
		}
		if (flag) {
			Class class1 = entity.getClass().getDeclaredField(entity.fetchPKey().getKey()).getType();
			Object aobj[];
			if (class1.equals(Long.class)) {
				aobj = new Long[as.length];
				for (int j = 0; j < as.length; j++)
					aobj[j] = Long.valueOf(as[j]);

			} else if (class1.equals(Integer.class)) {
				aobj = new Integer[as.length];
				for (int k = 0; k < as.length; k++)
					aobj[k] = Integer.valueOf(as[k]);

			} else {
				aobj = as;
			}
			getService().deleteBYBulk(entity, aobj);
			addReturnValue(1, 0x1d45fL);
		}
		return "success";
	}

	public String listCustomerPrivilege() throws Exception {
		this.logger.debug("enter listCustomerPrivilege");
		String str1 = this.request.getParameter("firmID");
		String str2 = this.request.getParameter("customerID");
		PageRequest localPageRequest = super.getPageRequest(this.request);
		if ((str2 != null) && (!"".equals(str2))) {
			List localList = this.tradePrivilegeService.getCustomerPrivilege(str2);
			Page localPage = new Page(localPageRequest.getPageNumber(), localPageRequest.getPageSize(), localList.size(), localList);
			this.request.setAttribute("pageInfo", localPage);
			this.request.setAttribute("customerID", str2);
			this.request.setAttribute("firmID", str1);
			this.request.setAttribute("FIRMPRIVILEGE_B", CommonDictionary.FIRMPRIVILEGE_B);
			this.request.setAttribute("FIRMPRIVILEGE_S", CommonDictionary.FIRMPRIVILEGE_S);
		}
		return "success";
	}

	public String listFirmPrivilege() throws Exception {
		this.logger.debug("enter listFirmPrivilege");
		String str = this.request.getParameter("firmID");
		PageRequest localPageRequest = super.getPageRequest(this.request);
		if ((str != null) && (!"".equals(str))) {
			List localList = this.tradePrivilegeService.getFirmPrivilege(str);
			Page localPage = new Page(localPageRequest.getPageNumber(), localPageRequest.getPageSize(), localList.size(), localList);
			this.request.setAttribute("pageInfo", localPage);
			this.request.setAttribute("firmID", str);
			this.request.setAttribute("FIRMPRIVILEGE_B", CommonDictionary.FIRMPRIVILEGE_B);
			this.request.setAttribute("FIRMPRIVILEGE_S", CommonDictionary.FIRMPRIVILEGE_S);
		}
		return "success";
	}

	public String fowardAddPrivilege() {
		this.logger.debug("enter addPrivilegeFoward");
		String str = this.request.getParameter("typeID");
		List localList1 = this.tradePrivilegeService.getBreed();
		List localList2 = this.tradePrivilegeService.getCommodity();
		this.request.setAttribute("typeID", str);
		this.request.setAttribute("breedList", localList1);
		this.request.setAttribute("commodityList", localList2);
		return "success";
	}

	public String viewPrivilege() {
		this.logger.debug("enter viewPrivilege");
		this.entity = getService().get(this.entity);
		List localList1 = this.tradePrivilegeService.getBreed();
		List localList2 = this.tradePrivilegeService.getCommodity();
		this.request.setAttribute("breedList", localList1);
		this.request.setAttribute("commodityList", localList2);
		return "success";
	}

	public String listTrader() throws Exception {
		this.logger.debug("enter listTrader");
		String str = this.request.getParameter("firmID");
		if ((str != null) && (!"".equals(str))) {
			PageRequest localPageRequest = super.getPageRequest(this.request);
			QueryConditions localQueryConditions = (QueryConditions) localPageRequest.getFilters();
			localQueryConditions.addCondition("firm.firmID", "=", str);
			listByLimit(localPageRequest);
			this.request.setAttribute("firmID", str);
		}
		return "success";
	}

	public String listTraderPrivilege() throws Exception {
		this.logger.debug("enter listTraderPrivilege");
		String str1 = this.request.getParameter("firmID");
		String str2 = this.request.getParameter("traderID");
		if ((str2 != null) && (!"".equals(str2))) {
			PageRequest localPageRequest = super.getPageRequest(this.request);
			List localList = this.tradePrivilegeService.getTraderPrivilege(str2);
			Page localPage = new Page(localPageRequest.getPageNumber(), localPageRequest.getPageSize(), localList.size(), localList);
			this.request.setAttribute("pageInfo", localPage);
			this.request.setAttribute("traderID", str2);
			this.request.setAttribute("firmID", str1);
			this.request.setAttribute("FIRMPRIVILEGE_B", CommonDictionary.FIRMPRIVILEGE_B);
			this.request.setAttribute("FIRMPRIVILEGE_S", CommonDictionary.FIRMPRIVILEGE_S);
		}
		return "success";
	}

	public String viewCode() {
		this.logger.debug("enter viewCode");
		String str1 = this.request.getParameter("firmID");
		String str2 = this.request.getParameter("traderID");
		List localList = this.tradePrivilegeService.getCodeNotChoose(str1, str2);
		String str3 = this.tradePrivilegeService.getOperateCode(str2);
		this.request.setAttribute("traderID", str2);
		this.request.setAttribute("firmID", str1);
		this.request.setAttribute("codeNotChoose", localList);
		this.request.setAttribute("operateCode", str3);
		return "success";
	}

	public String updateCode() {
		String str1 = this.request.getParameter("traderID");
		String str2 = this.request.getParameter("code");
		try {
			TTrader localTTrader1 = new TTrader();
			localTTrader1.setTraderID(str1);
			TTrader localTTrader2 = (TTrader) getService().get(localTTrader1);
			localTTrader1.setOperateCode(str2);
			localTTrader1.setModifyTime(getService().getSysDate());
			if (localTTrader2 != null)
				getService().update(localTTrader1);
			else
				getService().add(localTTrader1);
			addReturnValue(1, 119907L);
		} catch (Exception localException) {
			localException.printStackTrace();
			addReturnValue(-1, 119908L);
		}
		return "success";
	}

	public Map<Integer, String> getFirm_statusMap() {
		return this.firm_statusMap;
	}

	public Map<Integer, String> getTradePrivilege_typeMap() {
		return this.tradePrivilege_typeMap;
	}

	public Map<Integer, String> getTradePrivilege_kindMap() {
		return this.tradePrivilege_kindMap;
	}

	public Map<Integer, String> getPrivilegeCode_BMap() {
		return this.privilegeCode_BMap;
	}

	public Map<Integer, String> getPrivilegeCode_SMap() {
		return this.privilegeCode_SMap;
	}

	public TradePrivilegeService getTradePrivilegeService() {
		return this.tradePrivilegeService;
	}
}