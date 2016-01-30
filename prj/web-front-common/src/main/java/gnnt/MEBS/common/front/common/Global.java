package gnnt.MEBS.common.front.common;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

import gnnt.MEBS.checkLogon.check.front.FrontCheck;
import gnnt.MEBS.checkLogon.util.Tool;
import gnnt.MEBS.common.front.model.StandardModel;
import gnnt.MEBS.common.front.model.TradeModule;
import gnnt.MEBS.common.front.model.front.Menu;
import gnnt.MEBS.common.front.model.front.Right;
import gnnt.MEBS.common.front.service.MenuService;
import gnnt.MEBS.common.front.service.RightService;
import gnnt.MEBS.common.front.service.StandardService;
import gnnt.MEBS.common.front.statictools.ApplicationContextInit;
import gnnt.MEBS.common.front.statictools.Tools;
import gnnt.MEBS.logonServerUtil.au.LogonActualize;

public class Global implements ServletContextListener {
	public static final String TOLOGINPREURL = "preUrl";
	public static final String CURRENTUSERSTR = "CurrentUser";
	public static final String TOLOGINURLREASON = "reason";
	public static final String SESSIONID = "sessionID";
	public static final String USERID = "userID";
	public static final String FROMMODULEID = "FromModuleID";
	public static final String FROMLOGONTYPE = "FromLogonType";
	public static final String SELFLOGONTYPE = "LogonType";
	public static final String SELFLMODULEID = "ModuleID";
	public static final String ISSUPERADMIN = "IsSuperAdmin";
	public static final String RETURNVALUE = "ReturnValue";
	public static int COMMONMODULEID = 99;
	public static long ROOTRIGHTID = 9900000000L;
	private static Integer selfModuleID;
	private static String selfLogonType;
	private static List<Integer> moduleIDArray;
	public static Map<Integer, Map<Object, Object>> modelContextMap;
	public static final String HAVERIGHTMENU = "HaveRightMenu";
	private static Menu rootMenu;
	private static Right rightTree;
	private static Map<String, String> marketInfoMap;

	public void contextDestroyed(ServletContextEvent paramServletContextEvent) {
	}

	public void contextInitialized(ServletContextEvent paramServletContextEvent) {
		MenuService localMenuService = (MenuService) ApplicationContextInit.getBean("com_menuService");
		StandardService localStandardService = (StandardService) ApplicationContextInit.getBean("com_standardService");
		ROOTRIGHTID = Tools.strToLong(ApplicationContextInit.getConfig("RootRightID"), ROOTRIGHTID);
		modelContextMap = getContextMap(localMenuService);
		List localList = localStandardService.getListBySql("select * from c_marketInfo");
		marketInfoMap = new LinkedHashMap();
		if ((localList != null) && (localList.size() > 0)) {
			Iterator localObject = localList.iterator();
			while (((Iterator) localObject).hasNext()) {
				Map localMap1 = (Map) ((Iterator) localObject).next();
				marketInfoMap.put((String) localMap1.get("INFONAME"), (String) localMap1.get("INFOVALUE"));
			}
		}
		rootMenu = getMenuById(localMenuService);
		Object localObject = (RightService) ApplicationContextInit.getBean("com_rightService");
		rightTree = ((RightService) localObject).loadRightTree();
		int i = Tool.strToInt(ApplicationContextInit.getConfig("CallMode"), 0);
		DataSource localDataSource = (DataSource) ApplicationContextInit.getBean("dataSource");
		int j = Tool.strToInt(ApplicationContextInit.getConfig("clearRMITimes"), -1);
		Map localMap2 = null;
		Long localLong = null;
		try {
			localMap2 = (Map) ApplicationContextInit.getBean("auExpireTimeMap");
			localLong = Long.valueOf(Tool.strToLong(ApplicationContextInit.getConfig("timeSpace"), 200L));
		} catch (Exception localException1) {
		}
		try {
			LogonActualize.createInstance(getSelfModuleID(), i, localDataSource, localMap2, localLong.longValue(), j, "front");
		} catch (Exception localException2) {
			localException2.printStackTrace();
		}
		FrontCheck.createInstance(localDataSource);
	}

	public static Menu getRootMenu() {
		return rootMenu;
	}

	public static Right getRightTree() {
		return rightTree;
	}

	private Map<Integer, Map<Object, Object>> getContextMap(StandardService paramStandardService) {
		StandardService localStandardService = (StandardService) ApplicationContextInit.getBean("com_standardService");
		LinkedHashMap localLinkedHashMap1 = new LinkedHashMap();
		LinkedHashMap localLinkedHashMap2 = new LinkedHashMap();
		Iterator localIterator;
		Object localObject1;
		Object localObject2;
		Object localObject3;
		Object localObject4;
		try {
			List localList1 = localStandardService
					.getListBySql("select * from c_deploy_config t where t.systype='front' order by t.sortno,t.moduleid asc");
			localIterator = localList1.iterator();
			while (localIterator.hasNext()) {
				localObject1 = (Map) localIterator.next();
				localObject2 = new HashMap();
				localObject3 = ((Map) localObject1).keySet().iterator();
				while (((Iterator) localObject3).hasNext()) {
					localObject4 = ((Iterator) localObject3).next();
					((Map) localObject2).put(localObject4, ((Map) localObject1).get(localObject4));
				}
				localLinkedHashMap2.put(Integer.valueOf(Tools.strToInt((BigDecimal) ((Map) localObject2).get("MODULEID") + "")), localObject2);
			}
		} catch (Exception localException) {
		}
		if ((localLinkedHashMap2 != null) && (localLinkedHashMap2.size() > 0)) {
			List localList2 = paramStandardService
					.getListBySql("select * from c_trademodule where 1=1 and (isFirmSet='Y' or moduleID=" + COMMONMODULEID + ")", new TradeModule());
			if (localList2 != null) {
				localIterator = localLinkedHashMap2.keySet().iterator();
				while (localIterator.hasNext()) {
					localObject1 = (Integer) localIterator.next();
					localObject2 = localList2.iterator();
					while (((Iterator) localObject2).hasNext()) {
						localObject3 = (StandardModel) ((Iterator) localObject2).next();
						localObject4 = (TradeModule) localObject3;
						if ((localObject1 != null) && (((Integer) localObject1).equals(((TradeModule) localObject4).getModuleID()))) {
							Map localMap = (Map) localLinkedHashMap2.get(localObject1);
							if (localMap != null) {
								localMap.put("enName", ((TradeModule) localObject4).getEnName());
								localMap.put("isFirmset", ((TradeModule) localObject4).getIsFirmSet());
								localMap.put("cnName", ((TradeModule) localObject4).getCnName());
								localMap.put("shortName", ((TradeModule) localObject4).getShortName());
							}
							localLinkedHashMap1.put(localObject1, localMap);
							break;
						}
					}
				}
			}
		}
		return localLinkedHashMap1;
	}

	private Menu getMenuById(MenuService paramMenuService) {
		Menu localMenu1 = paramMenuService.getMenuById(ROOTRIGHTID, getModuleIDList());
		if ((modelContextMap != null) && (modelContextMap.size() > 0)) {
			LinkedHashSet localLinkedHashSet = new LinkedHashSet();
			Iterator localIterator1 = modelContextMap.keySet().iterator();
			while (localIterator1.hasNext()) {
				Integer localInteger = (Integer) localIterator1.next();
				Iterator localIterator2 = localMenu1.getChildMenuSet().iterator();
				while (localIterator2.hasNext()) {
					Menu localMenu2 = (Menu) localIterator2.next();
					if ((localInteger != null) && (localInteger.equals(localMenu2.getModuleId()))) {
						localLinkedHashSet.add(localMenu2);
					}
				}
			}
			localMenu1.setChildMenuSet(localLinkedHashSet);
		}
		return localMenu1;
	}

	public static List<Integer> getModuleIDList() {
		if (moduleIDArray == null) {
			synchronized (Global.class) {
				if (moduleIDArray == null) {
					moduleIDArray = new ArrayList();
					moduleIDArray.add(Integer.valueOf(COMMONMODULEID));
					int i = getSelfModuleID();
					if (i != COMMONMODULEID) {
						moduleIDArray.add(Integer.valueOf(i));
					} else if (modelContextMap != null) {
						Iterator localIterator = modelContextMap.keySet().iterator();
						while (localIterator.hasNext()) {
							Integer localInteger = (Integer) localIterator.next();
							moduleIDArray.add(localInteger);
						}
					}
				}
			}
		}
		return moduleIDArray;
	}

	public static int getSelfModuleID() {
		if (selfModuleID == null) {
			synchronized (Global.class) {
				if (selfModuleID == null) {
					try {
						selfModuleID = Integer.valueOf(Tools.strToInt(ApplicationContextInit.getConfig("SelfModuleID"), COMMONMODULEID));
					} catch (Exception localException) {
						selfModuleID = Integer.valueOf(COMMONMODULEID);
					}
				}
			}
		}
		return selfModuleID.intValue();
	}

	public static String getSelfLogonType() {
		if (selfLogonType == null) {
			synchronized (Global.class) {
				if (selfLogonType == null) {
					selfLogonType = ApplicationContextInit.getConfig("selfLogonType");
					if ((selfLogonType == null) || (selfLogonType.length() == 0)) {
						selfLogonType = "web";
					}
				}
			}
		}
		return selfLogonType;
	}

	public static Map<String, String> getMarketInfoMap() {
		return marketInfoMap;
	}
}
