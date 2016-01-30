package gnnt.MEBS.integrated.mgr.action.usermanage;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import gnnt.MEBS.checkLogon.util.MD5;
import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.common.mgr.common.Condition;
import gnnt.MEBS.common.mgr.common.Global;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.common.QueryConditions;
import gnnt.MEBS.common.mgr.common.ReturnValue;
import gnnt.MEBS.common.mgr.model.TradeModule;
import gnnt.MEBS.common.mgr.model.User;
import gnnt.MEBS.common.mgr.statictools.Tools;
import gnnt.MEBS.integrated.mgr.integrated.IntegratedGlobal;
import gnnt.MEBS.integrated.mgr.model.SystemProps;
import gnnt.MEBS.integrated.mgr.model.usermanage.MFirmModule;
import gnnt.MEBS.integrated.mgr.model.usermanage.OnlineTrader;
import gnnt.MEBS.integrated.mgr.model.usermanage.Trader;
import gnnt.MEBS.integrated.mgr.model.usermanage.TraderModule;
import gnnt.MEBS.integrated.mgr.service.TraderService;
import gnnt.MEBS.logonService.vo.CompulsoryLogoffVO;

@Controller("traderAction")
@Scope("request")
public class TraderAction extends EcsideAction {
	private static final long serialVersionUID = 2156801697679454774L;
	@Autowired
	@Qualifier("traderService")
	private TraderService traderService;
	@Autowired
	@Resource(name = "traderStatusMap")
	protected Map<String, String> traderStatusMap;
	@Autowired
	@Resource(name = "traderTypeMap")
	protected Map<String, String> traderTypeMap;
	@Autowired
	@Resource(name = "traderKeyMap")
	protected Map<String, String> traderKeyMap;

	public Map<String, String> getTraderStatusMap() {
		return this.traderStatusMap;
	}

	public Map<String, String> getTraderTypeMap() {
		return this.traderTypeMap;
	}

	public Map<String, String> getTraderKeyMap() {
		return this.traderKeyMap;
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

	public String addTraderForward() {
		Map localMap = getTradeMap();
		SystemProps localSystemProps = new SystemProps();
		localSystemProps.setPropsKey("Offset");
		localSystemProps = (SystemProps) getService().get(localSystemProps);
		String str = localSystemProps.getFirmIdLength();
		int i = Integer.parseInt(str.trim());
		this.request.setAttribute("firmLen", Integer.valueOf(i));
		this.request.setAttribute("tradeModuleMap", localMap);
		return "success";
	}

	public String addTrader() {
		String str = "添加交易员";
		this.logger.debug(str);
		Trader localTrader = (Trader) this.entity;
		localTrader.setPassword(MD5.getMD5(localTrader.getTraderId(), localTrader.getPassword()));
		HashMap localHashMap = new HashMap();
		String[] arrayOfString = this.request.getParameterValues("traderModules");
		if ((arrayOfString != null) && (arrayOfString.length > 0)) {
			for (String localObject3 : arrayOfString) {
				localHashMap.put(Integer.valueOf(Tools.strToInt((String) localObject3, 64536)), Boolean.valueOf(true));
			}
		}
		Map map = getTradeMap();
		if (map != null) {
			Set localObject2 = new HashSet();
			Iterator localIterator = ((Map) map).keySet().iterator();
			while (localIterator.hasNext()) {
				Integer localObject3 = (Integer) localIterator.next();
				TradeModule localTradeModule = (TradeModule) ((Map) map).get(localObject3);
				TraderModule localTraderModule = new TraderModule();
				if (localHashMap.get(localTradeModule.getModuleId()) != null) {
					localTraderModule.setEnabled("Y");
				} else {
					localTraderModule.setEnabled("N");
				}
				localTraderModule.setModuleId(localTradeModule.getModuleId());
				localTraderModule.setTrader(localTrader);
				((Set) localObject2).add(localTraderModule);
			}
			localTrader.setTraderModuleSet((Set) localObject2);
		}
		this.traderService.addTrader(localTrader);
		addReturnValue(1, 119901L);
		Object localObject2 = (ReturnValue) this.request.getAttribute("ReturnValue");
		if (localObject2 != null) {
			if (((ReturnValue) localObject2).getResult() > 0) {
				writeOperateLog(1031, str, 1, ((ReturnValue) localObject2).getInfo());
			} else {
				writeOperateLog(1031, str, 0, ((ReturnValue) localObject2).getInfo());
			}
		}
		return "success";
	}

	public String getTraderDetails() {
		Trader localTrader = (Trader) getService().get(this.entity);
		this.request.setAttribute("entity", localTrader);
		if ((localTrader != null) && (localTrader.getMfirm() != null)) {
			HashMap localHashMap = new HashMap();
			Object localObject1 = localTrader.getTraderModuleSet().iterator();
			while (((Iterator) localObject1).hasNext()) {
				TraderModule localObject2 = (TraderModule) ((Iterator) localObject1).next();
				localHashMap.put(((TraderModule) localObject2).getModuleId(), ((TraderModule) localObject2).getEnabled());
			}
			this.request.setAttribute("traderModuleMap", localHashMap);
			localObject1 = getTradeMap();
			this.request.setAttribute("tradeModuleMap", localObject1);
			Object localObject2 = new QueryConditions();
			((QueryConditions) localObject2).addCondition("firmId", "=", localTrader.getMfirm().getFirmId());
			((QueryConditions) localObject2).addCondition("enabled", "=", "Y");
			PageRequest localPageRequest = new PageRequest(1, 100, localObject2, " order by moduleId ");
			Page localPage = getService().getPage(localPageRequest, new MFirmModule());
			this.request.setAttribute("firmList", localPage.getResult());
		}
		return "success";
	}

	public String updateTrader() throws Exception {
		String str = "修改交易员信息";
		this.logger.debug(str);
		Trader localTrader1 = (Trader) this.entity;
		int i = 0;
		if ((localTrader1.getStatus() != null) && (!localTrader1.getStatus().equalsIgnoreCase("N"))) {
			i = 1;
		}
		try {
			localTrader1.setModifyTime(getService().getSysDate());
		} catch (Exception localException) {
			this.logger.debug(Tools.getExceptionTrace(localException));
		}
		Trader localTrader2 = (Trader) getService().get(localTrader1);
		if (localTrader2 != null) {
			Map localObject1 = new HashMap();
			String[] arrayOfString = this.request.getParameterValues("traderModules");
			if ((arrayOfString != null) && (arrayOfString.length > 0)) {
				for (String localObject3 : arrayOfString) {
					((Map) localObject1).put(Integer.valueOf(Tools.strToInt((String) localObject3, 64536)), Boolean.valueOf(true));
				}
			}
			Map map = getTradeMap();
			if (map != null) {
				HashSet localHashSet = new HashSet();
				Iterator localIterator1 = ((Map) map).keySet().iterator();
				while (localIterator1.hasNext()) {
					Integer localObject3 = (Integer) localIterator1.next();
					TradeModule localTradeModule = (TradeModule) ((Map) map).get(localObject3);
					Object localObject4 = null;
					if (localTrader2.getTraderModuleSet() != null) {
						Iterator localIterator2 = localTrader2.getTraderModuleSet().iterator();
						while (localIterator2.hasNext()) {
							TraderModule localTraderModule = (TraderModule) localIterator2.next();
							if (localTraderModule.getModuleId().equals(localObject3)) {
								localObject4 = localTraderModule;
								break;
							}
						}
					}
					if (localObject4 == null) {
						localObject4 = new TraderModule();
						((TraderModule) localObject4).setModuleId((Integer) localObject3);
						((TraderModule) localObject4).setTrader(localTrader2);
					}
					if (((Map) localObject1).get(localTradeModule.getModuleId()) != null) {
						((TraderModule) localObject4).setEnabled("Y");
					} else {
						if ("Y".equalsIgnoreCase(((TraderModule) localObject4).getEnabled())) {
							i = 1;
						}
						((TraderModule) localObject4).setEnabled("N");
					}
					localHashSet.add(localObject4);
				}
				localTrader1.setTraderModuleSet(localHashSet);
			}
			if (!"N".equalsIgnoreCase(localTrader2.getStatus())) {
				i = 0;
			}
			if (i != 0) {
				downOnlineTraders(new String[] { localTrader1.getTraderId() });
			}
			this.traderService.updateTrader(localTrader1);
		}
		addReturnValue(1, 119902L);
		Object localObject1 = (ReturnValue) this.request.getAttribute("ReturnValue");
		if (localObject1 != null) {
			if (((ReturnValue) localObject1).getResult() > 0) {
				writeOperateLog(1031, str, 1, ((ReturnValue) localObject1).getInfo());
			} else {
				writeOperateLog(1031, str, 0, ((ReturnValue) localObject1).getInfo());
			}
		}
		return "success";
	}

	public String updatePassword() {
		String str1 = "修改交易员密码";
		this.logger.debug(str1);
		this.logger.debug("enter passwordSelfSave");
		String str2 = this.request.getParameter("password");
		Trader localTrader = (Trader) this.entity;
		localTrader.setPassword(MD5.getMD5(localTrader.getTraderId(), str2));
		try {
			localTrader.setModifyTime(getService().getSysDate());
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		getService().update(localTrader);
		addReturnValue(1, 110801L, new Object[] { localTrader.getTraderId() });
		ReturnValue localReturnValue = (ReturnValue) this.request.getAttribute("ReturnValue");
		if (localReturnValue != null) {
			if (localReturnValue.getResult() > 0) {
				writeOperateLog(1031, str1, 1, localReturnValue.getInfo());
			} else {
				writeOperateLog(1031, str1, 0, localReturnValue.getInfo());
			}
		}
		return "success";
	}

	public String forbideTrader() {
		String str1 = "禁止交易员登陆";
		this.logger.debug(str1);
		String[] arrayOfString1 = this.request.getParameterValues("ids");
		downOnlineTraders(arrayOfString1);
		String str2 = "";
		for (String localObject : arrayOfString1) {
			if (str2.trim().length() > 0) {
				str2 = str2 + ",";
			}
			str2 = str2 + (String) localObject;
		}
		try {
			super.getService().updateBYBulk(this.entity, "status='D'", arrayOfString1);
			addReturnValue(1, 110802L, new Object[] { str2 });
		} catch (Exception localException1) {
			this.logger.error(Tools.getExceptionTrace(localException1));
			addReturnValue(-1, 130801L, new Object[] { str2 });
		}
		ReturnValue localReturnValue = (ReturnValue) this.request.getAttribute("ReturnValue");
		if (localReturnValue != null) {
			if (localReturnValue.getResult() > 0) {
				writeOperateLog(1031, str1, 1, localReturnValue.getInfo());
			} else {
				writeOperateLog(1031, str1, 0, localReturnValue.getInfo());
			}
		}
		User localUser = (User) this.request.getSession().getAttribute("CurrentUser");
		ArrayList localArrayList = new ArrayList();
		Object localObject = new CompulsoryLogoffVO();
		((CompulsoryLogoffVO) localObject).setOperator(localUser.getUserId());
		((CompulsoryLogoffVO) localObject).setLogonIP(localUser.getIpAddress());
		for (String str3 : arrayOfString1) {
			localArrayList.add(str3);
		}
		((CompulsoryLogoffVO) localObject).setUserIDList(localArrayList);
		try {
			IntegratedGlobal.getLogonService().compulsoryLogoff("front", (CompulsoryLogoffVO) localObject);
		} catch (RemoteException localRemoteException1) {
			try {
				IntegratedGlobal.clearLogonService();
				IntegratedGlobal.getLogonService().compulsoryLogoff("front", (CompulsoryLogoffVO) localObject);
			} catch (MalformedURLException localMalformedURLException) {
				this.logger.error(localRemoteException1.getMessage());
				addReturnValue(-1, 999999L, new Object[] { "远程调用协议错误" });
			} catch (NotBoundException localNotBoundException) {
				this.logger.error(localRemoteException1.getMessage());
				addReturnValue(-1, 999999L, new Object[] { "远程调用无服务" });
			} catch (RemoteException localRemoteException2) {
				this.logger.error(localRemoteException1.getMessage());
				addReturnValue(-1, 999999L, new Object[] { "退出用户时连接RMI失败" });
			} catch (Exception localException3) {
				this.logger.error(localRemoteException1.getMessage());
				addReturnValue(-1, 139904L);
			}
		} catch (Exception localException2) {
			this.logger.error(localException2.getMessage());
			addReturnValue(-1, 999999L, new Object[] { "调用远程服务异常" });
		}
		return "success";
	}

	public String recoverTrader() {
		String str1 = "恢复交易员登陆";
		this.logger.debug(str1);
		this.logger.debug("enter recoverTrader");
		String[] arrayOfString1 = this.request.getParameterValues("ids");
		String str2 = "";
		for (String str3 : arrayOfString1) {
			if (str2.trim().length() > 0) {
				str2 = str2 + ",";
			}
			str2 = str2 + str3;
		}
		try {
			super.getService().updateBYBulk(this.entity, "status='N'", arrayOfString1);
			addReturnValue(1, 110803L, new Object[] { str2 });
		} catch (Exception localException) {
			this.logger.error(Tools.getExceptionTrace(localException));
			addReturnValue(-1, 130802L, new Object[] { str2 });
		}
		ReturnValue localReturnValue = (ReturnValue) this.request.getAttribute("ReturnValue");
		if (localReturnValue != null) {
			if (localReturnValue.getResult() > 0) {
				writeOperateLog(1031, str1, 1, localReturnValue.getInfo());
			} else {
				writeOperateLog(1031, str1, 0, localReturnValue.getInfo());
			}
		}
		return "success";
	}

	public String onlineTraderList() {
		this.logger.debug("enter OnlineTraderList");
		try {
			PageRequest localPageRequest = getPageRequest(this.request);
			QueryConditions localQueryConditions = getQueryConditionsFromRequest(this.request);
			List localList = localQueryConditions.getConditionList();
			localPageRequest.setSortColumns("order by logonTime desc");
			int i = 0;
			if ((localList != null) && (localList.size() > 0)) {
				i = 1;
			}
			ArrayList localArrayList = new ArrayList();
			Object localObject1 = new ArrayList();
			try {
				localObject1 = IntegratedGlobal.getLogonService().getOnLineUserList("front", null, "", "");
			} catch (Exception localException2) {
				try {
					IntegratedGlobal.clearLogonService();
					localObject1 = IntegratedGlobal.getLogonService().getOnLineUserList("front", null, "", "");
				} catch (MalformedURLException localMalformedURLException) {
					this.logger.error(localException2.getMessage());
					addReturnValue(-1, 999999L, new Object[] { "远程调用协议错误" });
				} catch (NotBoundException localNotBoundException) {
					this.logger.error(localException2.getMessage());
					addReturnValue(-1, 999999L, new Object[] { "远程调用无服务" });
				} catch (RemoteException localRemoteException) {
					this.logger.error(localException2.getMessage());
					addReturnValue(-1, 999999L, new Object[] { "退出用户时连接RMI失败" });
				} catch (Exception localException3) {
					this.logger.error(localException2.getMessage());
					addReturnValue(-1, 139904L);
				}
			}
			if ((localObject1 != null) && (((List) localObject1).size() > 0)) {
				Iterator localObject2 = ((List) localObject1).iterator();
				while (((Iterator) localObject2).hasNext()) {
					String str = (String) ((Iterator) localObject2).next();
					String[] arrayOfString = str.split(",");
					OnlineTrader localOnlineTrader = new OnlineTrader();
					localOnlineTrader.setTraderId(arrayOfString[0]);
					localOnlineTrader.setLogonIp(arrayOfString[1]);
					localOnlineTrader.setLogonTime(arrayOfString[2]);
					if (i != 0) {
						Iterator localIterator = localList.iterator();
						while (localIterator.hasNext()) {
							Condition localCondition = (Condition) localIterator.next();
							if (localOnlineTrader.getTraderId().contains(localCondition.getValue().toString())) {
								localArrayList.add(localOnlineTrader);
							}
						}
					} else {
						localArrayList.add(localOnlineTrader);
					}
				}
			}
			Object localObject2 = new Page(localPageRequest.getPageNumber(), 100000, localArrayList.size(), localArrayList);
			this.request.setAttribute("pageInfo", localObject2);
			this.request.setAttribute("oldParams", getParametersStartingWith(this.request, "gnnt_"));
		} catch (Exception localException1) {
			this.logger.error(Tools.getExceptionTrace(localException1));
			addReturnValue(-1, 130803L);
		}
		return "success";
	}

	public String downOnlineTraders() throws Exception {
		String[] arrayOfString = this.request.getParameterValues("ids");
		downOnlineTraders(arrayOfString);
		return "success";
	}

	private void downOnlineTraders(String[] paramArrayOfString) {
		String str1 = "强制交易员下线";
		this.logger.debug(str1);
		if ((paramArrayOfString == null) || (paramArrayOfString.length <= 0)) {
			return;
		}
		User localUser = (User) this.request.getSession().getAttribute("CurrentUser");
		CompulsoryLogoffVO localCompulsoryLogoffVO = new CompulsoryLogoffVO();
		localCompulsoryLogoffVO.setOperator(localUser.getUserId());
		localCompulsoryLogoffVO.setLogonIP(localUser.getIpAddress());
		ArrayList localArrayList = new ArrayList();
		for (String str2 : paramArrayOfString) {
			localArrayList.add(str2);
		}
		localCompulsoryLogoffVO.setUserIDList(localArrayList);
		int i = -3;
		try {
			i = IntegratedGlobal.getLogonService().compulsoryLogoff("front", localCompulsoryLogoffVO);
		} catch (RemoteException localRemoteException1) {
			try {
				IntegratedGlobal.clearLogonService();
				i = IntegratedGlobal.getLogonService().compulsoryLogoff("front", localCompulsoryLogoffVO);
			} catch (MalformedURLException localMalformedURLException) {
				this.logger.error(localRemoteException1.getMessage());
				addReturnValue(-1, 999999L, new Object[] { "远程调用协议错误" });
			} catch (NotBoundException localNotBoundException) {
				this.logger.error(localRemoteException1.getMessage());
				addReturnValue(-1, 999999L, new Object[] { "远程调用无服务" });
			} catch (RemoteException localRemoteException2) {
				this.logger.error(localRemoteException1.getMessage());
				addReturnValue(-1, 999999L, new Object[] { "退出用户时连接RMI失败" });
			} catch (Exception localException2) {
				this.logger.error(localRemoteException1.getMessage());
				addReturnValue(-1, 139904L);
			}
		} catch (Exception localException1) {
			this.logger.error(localException1.getMessage());
			addReturnValue(-1, 999999L, new Object[] { "调用远程服务异常" });
		}
		if (i == 1) {
			addReturnValue(1, 110804L, new Object[] { localCompulsoryLogoffVO.getUserIDList() });
		} else {
			addReturnValue(-1, 130804L, new Object[] { localCompulsoryLogoffVO.getUserIDList() });
		}
		ReturnValue localReturnValue = (ReturnValue) this.request.getAttribute("ReturnValue");
		if (localReturnValue != null) {
			if (localReturnValue.getResult() > 0) {
				writeOperateLog(1031, str1, 1, localReturnValue.getInfo());
			} else {
				writeOperateLog(1031, str1, 0, localReturnValue.getInfo());
			}
		}
	}
}
