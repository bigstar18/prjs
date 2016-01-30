package gnnt.MEBS.integrated.mgr.action.usernamage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import gnnt.MEBS.common.mgr.action.StandardAction;
import gnnt.MEBS.common.mgr.common.Global;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.common.QueryConditions;
import gnnt.MEBS.common.mgr.common.ReturnValue;
import gnnt.MEBS.common.mgr.model.Right;
import gnnt.MEBS.common.mgr.model.Role;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.TradeModule;
import gnnt.MEBS.common.mgr.model.User;
import gnnt.MEBS.common.mgr.statictools.Tools;
import gnnt.MEBS.integrated.mgr.model.usermanage.RoleRight;
import gnnt.MEBS.integrated.mgr.service.user.RoleService;
import gnnt.MEBS.integrated.mgr.service.writeopreatelog.WriteOperateLogService;

@Controller("rightAction")
@Scope("request")
public class RightAction extends StandardAction {
	private static final long serialVersionUID = -4649170499307603196L;
	@Autowired
	@Qualifier("roleService")
	private RoleService roleService;
	@Autowired
	@Qualifier("writeOperateLogService")
	private WriteOperateLogService writeOperateLogService;
	private static Map<Integer, TradeModule> tradeModuleMap;
	private static Map<Integer, List<Right>> rightMap;

	public Map<Integer, TradeModule> getTradeModuleMap() {
		if (tradeModuleMap == null) {
			synchronized (getClass()) {
				if (tradeModuleMap == null) {
					tradeModuleMap = new LinkedHashMap();
					Map localMap1 = Global.modelContextMap;
					if (localMap1 != null) {
						TradeModule localTradeModule = null;
						Iterator localIterator = localMap1.keySet().iterator();
						while (localIterator.hasNext()) {
							Integer localInteger = (Integer) localIterator.next();
							Map localMap2 = (Map) localMap1.get(localInteger);
							localTradeModule = new TradeModule();
							localTradeModule.setAddFirmFn((String) localMap2.get("addFirmFn"));
							localTradeModule.setCnName((String) localMap2.get("cnName"));
							localTradeModule.setDelFirmFn((String) localMap2.get("delFirmFn"));
							localTradeModule.setEnName((String) localMap2.get("enName"));
							localTradeModule.setIsFirmSet((String) localMap2.get("isFirmSet"));
							localTradeModule.setModuleId(localInteger);
							localTradeModule.setShortName((String) localMap2.get("shortName"));
							localTradeModule.setUpdateFirmStatusFn((String) localMap2.get("updateFirmStatusFn"));
							tradeModuleMap.put(localInteger, localTradeModule);
						}
					}
				}
			}
		}
		return tradeModuleMap;
	}

	public Map<Integer, List<Right>> getRightMap() {
		rightMap = null;
		if (rightMap == null) {
			synchronized (getClass()) {
				if (rightMap == null) {
					rightMap = new LinkedHashMap();
					Map localMap = getTradeModuleMap();
					if (localMap != null) {
						Iterator localIterator = localMap.keySet().iterator();
						while (localIterator.hasNext()) {
							int i = ((Integer) localIterator.next()).intValue();
							rightMap.put(Integer.valueOf(i), new ArrayList());
						}
						localIterator = Global.getRightTree().getChildRightSet().iterator();
						while (localIterator.hasNext()) {
							Right localRight = (Right) localIterator.next();
							List localList = (List) rightMap.get(localRight.getModuleId());
							if (localList != null) {
								localList.add(localRight);
							} else {
								this.logger.debug("系统中包含有 " + localRight.getModuleId() + " 模块的权限，但是不包含本模块");
							}
						}
					}
				}
			}
		}
		return rightMap;
	}

	public String updateForwardRoleRight() throws Exception {
		getRightMap();
		String str = this.request.getParameter("roleId");
		Role localRole = this.roleService.getRoleById(Long.parseLong(str), false, false);
		this.request.setAttribute("role", localRole);
		return "success";
	}

	public String updateRoleModuleRight() {
		String str = this.request.getParameter("roleId");
		int i = Tools.strToInt(this.request.getParameter("moduleId"), -1000);
		if ((str == null) || (str.length() <= 0)) {
			return "error";
		}
		if (i <= 0) {
			return "error";
		}
		Map localMap = getRightMap();
		if ((localMap == null) || (localMap.get(Integer.valueOf(i)) == null)) {
			return "error";
		}
		this.request.setAttribute("module", getTradeModuleMap().get(Integer.valueOf(i)));
		this.request.setAttribute("frightList", localMap.get(Integer.valueOf(i)));
		QueryConditions localQueryConditions = new QueryConditions();
		localQueryConditions.addCondition("roleId", "=", Long.valueOf(Tools.strToLong(str)));
		localQueryConditions.addCondition("cright.moduleId", "=", Integer.valueOf(i));
		PageRequest localPageRequest = new PageRequest(1, 100000, localQueryConditions, "");
		Page localPage = getService().getPage(localPageRequest, new RoleRight());
		if ((localPage != null) && (localPage.getResult() != null) && (localPage.getResult().size() > 0)) {
			HashMap localHashMap = new HashMap();
			Iterator localIterator = localPage.getResult().iterator();
			while (localIterator.hasNext()) {
				StandardModel localStandardModel = (StandardModel) localIterator.next();
				localHashMap.put(((RoleRight) localStandardModel).getCright().getId(), Boolean.valueOf(true));
			}
			this.request.setAttribute("roleRightMap", localHashMap);
		}
		return "success";
	}

	public String updateRoleRight() {
		String str1 = "修改角色权限";
		this.logger.debug(str1);
		String str2 = this.request.getParameter("roleId");
		Role localRole = null;
		try {
			Object localObject1 = this.request.getParameterValues("ck");
			localRole = this.roleService.getRoleById(Tools.strToLong(str2), true, true);
			if (localRole == null) {
				addReturnValue(-1, 121101L, new Object[] { str2 });
				String localObject2 = "error";
				ReturnValue localObject3 = (ReturnValue) this.request.getAttribute("ReturnValue");
				if (localObject3 != null) {
					User localUser1 = (User) this.request.getSession().getAttribute("CurrentUser");
					String str3 = "";
					if (localRole != null) {
						str3 = localRole.getName();
					}
					if (((ReturnValue) localObject3).getResult() > 0) {
						this.writeOperateLogService.writeOperateLog(1201, localUser1, "修改角色" + str3 + "[" + str2 + "]权限成功", 1, "");
					} else {
						this.writeOperateLogService.writeOperateLog(1201, localUser1, "修改角色" + str3 + "[" + str2 + "]权限失败", 0,
								((ReturnValue) localObject3).getInfo());
					}
				}
				return localObject2;
			}
			this.roleService.saveRoleRights(localRole, (String[]) localObject1);
			addReturnValue(1, 121102L, new Object[] { "【" + localRole.getName() + "】" });
			localObject1 = (ReturnValue) this.request.getAttribute("ReturnValue");
			if (localObject1 != null) {
				User localObject2 = (User) this.request.getSession().getAttribute("CurrentUser");
				String localObject3 = "";
				if (localRole != null) {
					localObject3 = localRole.getName();
				}
				if (((ReturnValue) localObject1).getResult() > 0) {
					this.writeOperateLogService.writeOperateLog(1201, (User) localObject2, "修改角色" + (String) localObject3 + "[" + str2 + "]权限成功", 1,
							"");
				} else {
					this.writeOperateLogService.writeOperateLog(1201, (User) localObject2, "修改角色" + (String) localObject3 + "[" + str2 + "]权限失败", 0,
							((ReturnValue) localObject1).getInfo());
				}
			}
		} catch (Exception localException) {
			Object localObject2;
			Object localObject3;
			addReturnValue(-1, 999998L);
			ReturnValue localReturnValue1 = (ReturnValue) this.request.getAttribute("ReturnValue");
			if (localReturnValue1 != null) {
				localObject2 = (User) this.request.getSession().getAttribute("CurrentUser");
				localObject3 = "";
				if (localRole != null) {
					localObject3 = localRole.getName();
				}
				if (localReturnValue1.getResult() > 0) {
					this.writeOperateLogService.writeOperateLog(1201, (User) localObject2, "修改角色" + (String) localObject3 + "[" + str2 + "]权限成功", 1,
							"");
				} else {
					this.writeOperateLogService.writeOperateLog(1201, (User) localObject2, "修改角色" + (String) localObject3 + "[" + str2 + "]权限失败", 0,
							localReturnValue1.getInfo());
				}
			}
		} finally {
			ReturnValue localReturnValue2 = (ReturnValue) this.request.getAttribute("ReturnValue");
			if (localReturnValue2 != null) {
				User localUser2 = (User) this.request.getSession().getAttribute("CurrentUser");
				String str4 = "";
				if (localRole != null) {
					str4 = localRole.getName();
				}
				if (localReturnValue2.getResult() > 0) {
					this.writeOperateLogService.writeOperateLog(1201, localUser2, "修改角色" + str4 + "[" + str2 + "]权限成功", 1, "");
				} else {
					this.writeOperateLogService.writeOperateLog(1201, localUser2, "修改角色" + str4 + "[" + str2 + "]权限失败", 0,
							localReturnValue2.getInfo());
				}
			}
		}
		return "success";
	}
}
