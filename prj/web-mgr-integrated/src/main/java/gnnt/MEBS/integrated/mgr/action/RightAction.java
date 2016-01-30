package gnnt.MEBS.integrated.mgr.action;

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
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.common.mgr.service.UserService;
import gnnt.MEBS.common.mgr.statictools.Tools;
import gnnt.MEBS.integrated.mgr.model.commonmodel.RoleRight;
import gnnt.MEBS.integrated.mgr.service.RightService;
import gnnt.MEBS.integrated.mgr.service.RoleService;

@Controller("rightAction")
@Scope("request")
public class RightAction extends StandardAction {
	private static final long serialVersionUID = -1905373738157671461L;
	@Autowired
	@Qualifier("rightService")
	private RightService rightService;
	@Autowired
	@Qualifier("com_userService")
	private UserService userService;
	@Autowired
	@Qualifier("roleService")
	private RoleService roleService;
	private static Map<Integer, TradeModule> tradeModuleMap;
	private static Map<Integer, List<Right>> rightMap;

	public RightAction() {
		super.setEntityName(Right.class.getName());
	}

	public void setEntityName(String paramString) {
	}

	public StandardService getService() {
		return this.rightService;
	}

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

	public String userInfo() {
		User localUser = this.userService.getUserByID(this.request.getParameter("userId"));
		this.request.setAttribute("userInfo", localUser);
		return "success";
	}

	public String roleInfo() {
		String str = this.request.getParameter("roleId");
		Role localRole = this.roleService.getRoleById(Long.parseLong(str), true, false);
		this.request.setAttribute("roleInfo", localRole);
		return "success";
	}

	public String saveUserRight() {
		String str = "修改管理员权限";
		this.logger.debug(str);
		try {
			String localObject1 = this.request.getParameter("userId");
			if ((localObject1 == null) || (((String) localObject1).length() == 0)) {
				addReturnValue(-1, 131101L);
				String localObject2 = "error";
				ReturnValue localObject3 = (ReturnValue) this.request.getAttribute("ReturnValue");
				if (localObject3 != null) {
					if (((ReturnValue) localObject3).getResult() > 0) {
						writeOperateLog(1011, str, 1, ((ReturnValue) localObject3).getInfo());
					} else {
						writeOperateLog(1011, str, 0, ((ReturnValue) localObject3).getInfo());
					}
				}
				return (String) localObject2;
			}
			Object localObject2 = this.userService.getUserByID((String) localObject1, false, false);
			if (localObject2 == null) {
				addReturnValue(-1, 131102L, new Object[] { localObject1 });
				String localObject3 = "error";
				ReturnValue localReturnValue2;
				return (String) localObject3;
			}
			Object localObject3 = this.request.getParameterValues("ck");
			this.rightService.saveUserRights((User) localObject2, (String[]) localObject3);
			addReturnValue(1, 111101L, new Object[] { localObject1 });
			ReturnValue localObject11 = (ReturnValue) this.request.getAttribute("ReturnValue");
			if (localObject11 != null) {
				if (((ReturnValue) localObject11).getResult() > 0) {
					writeOperateLog(1011, str, 1, ((ReturnValue) localObject11).getInfo());
				} else {
					writeOperateLog(1011, str, 0, ((ReturnValue) localObject11).getInfo());
				}
			}
		} catch (Exception localException) {
			addReturnValue(-1, 999998L);
		} finally {
			ReturnValue localReturnValue1;
			ReturnValue localReturnValue3 = (ReturnValue) this.request.getAttribute("ReturnValue");
			if (localReturnValue3 != null) {
				if (localReturnValue3.getResult() > 0) {
					writeOperateLog(1011, str, 1, localReturnValue3.getInfo());
				} else {
					writeOperateLog(1011, str, 0, localReturnValue3.getInfo());
				}
			}
		}
		return "success";
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
		int i = Tools.strToInt(this.request.getParameter("moduleId"), 64536);
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
		try {
			Object localObject1 = this.request.getParameter("roleId");
			String[] arrayOfString1 = this.request.getParameterValues("chmoduleIds");
			String[] arrayOfString2 = this.request.getParameterValues("ck");
			Role localRole = this.roleService.getRoleById(Tools.strToLong((String) localObject1), true, true);
			if (localRole == null) {
				addReturnValue(-1, 131104L, new Object[] { localObject1 });
				String str2 = "error";
				ReturnValue localReturnValue2;
				return str2;
			}
			this.roleService.saveRoleRights(localRole, arrayOfString2, arrayOfString1);
			addReturnValue(1, 111102L, new Object[] { "【" + localRole.getName() + "】" });
			localObject1 = (ReturnValue) this.request.getAttribute("ReturnValue");
			if (localObject1 != null) {
				if (((ReturnValue) localObject1).getResult() > 0) {
					writeOperateLog(1001, str1, 1, ((ReturnValue) localObject1).getInfo());
				} else {
					writeOperateLog(1001, str1, 0, ((ReturnValue) localObject1).getInfo());
				}
			}
		} catch (Exception localException) {
			addReturnValue(-1, 999998L);
		} finally {
			ReturnValue localReturnValue1;
			ReturnValue localReturnValue3 = (ReturnValue) this.request.getAttribute("ReturnValue");
			if (localReturnValue3 != null) {
				if (localReturnValue3.getResult() > 0) {
					writeOperateLog(1001, str1, 1, localReturnValue3.getInfo());
				} else {
					writeOperateLog(1001, str1, 0, localReturnValue3.getInfo());
				}
			}
		}
		return "success";
	}
}
