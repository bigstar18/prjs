package gnnt.MEBS.common.broker.common;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

import gnnt.MEBS.checkLogon.check.broker.BrokerAgeCheck;
import gnnt.MEBS.checkLogon.check.broker.BrokerCheck;
import gnnt.MEBS.common.broker.model.Menu;
import gnnt.MEBS.common.broker.model.Right;
import gnnt.MEBS.common.broker.model.StandardModel;
import gnnt.MEBS.common.broker.model.TradeModule;
import gnnt.MEBS.common.broker.service.MenuService;
import gnnt.MEBS.common.broker.service.RightService;
import gnnt.MEBS.common.broker.service.StandardService;
import gnnt.MEBS.common.broker.statictools.ApplicationContextInit;
import gnnt.MEBS.common.broker.statictools.Tools;
import gnnt.MEBS.logonServerUtil.au.LogonActualize;

public class Global implements ServletContextListener {
	public static final String CURRENTUSERSTR = "CurrentUser";
	public static final String CURRENTUSERSTRTYPE = "CurrentUserType";
	public static final String TOLOGINURLREASON = "reason";
	public static final String SESSIONID = "sessionID";
	public static final String ISSUPERADMIN = "IsSuperAdmin";
	public static final String RETURNVALUE = "ReturnValue";
	public static final String FROMMODULEID = "FromModuleID";
	public static final String FROMLOGONTYPE = "FromLogonType";
	public static final String SELFLOGONTYPE = "LogonType";
	public static final String USERID = "userID";
	public static int COMMONMODULEID = 99;
	public static long ROOTRIGHTID = 9900000000L;
	private static Integer selfModuleID;
	private static String selfLogonType;
	public static final String HAVERIGHTMENU = "HaveRightMenu";
	private static List<Integer> moduleIDArray;
	public static Map<Integer, Map<Object, Object>> modelContextMap;
	private static Menu rootMenu;
	private static Right rightTree;
	private static Map<String, String> marketInfoMap;

	public void contextDestroyed(ServletContextEvent paramServletContextEvent) {
	}

	public void contextInitialized(ServletContextEvent paramServletContextEvent) {
		MenuService localMenuService = (MenuService) ApplicationContextInit.getBean("com_menuService");
		StandardService localStandardService = (StandardService) ApplicationContextInit.getBean("com_standardService");
		modelContextMap = getContextMap(localMenuService);
		ROOTRIGHTID = Tools.strToLong(ApplicationContextInit.getConfig("RootRightID"), ROOTRIGHTID);
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
		int i = Tools.strToInt(ApplicationContextInit.getConfig("CallMode"), 0);
		DataSource localDataSource = (DataSource) ApplicationContextInit.getBean("dataSource");
		long l = Tools.strToLong(ApplicationContextInit.getConfig("timeSpace"), 200L);
		int j = Tools.strToInt(ApplicationContextInit.getConfig("clearRMITimes"), -1);
		Map localMap2 = null;
		try {
			localMap2 = (Map) ApplicationContextInit.getBean("auExpireTimeMap");
		} catch (Exception localException1) {
		}
		try {
			LogonActualize.createInstance(getSelfModuleID(), i, localDataSource, localMap2, l, j, "broker");
			BrokerCheck.createInstance(localDataSource);
			BrokerAgeCheck.createInstance(localDataSource);
		} catch (Exception localException2) {
			localException2.printStackTrace();
		}
	}

	public Menu getMenuById(MenuService paramMenuService) {
		Menu localMenu1 = paramMenuService.getMenuById(ROOTRIGHTID, getModuleIDList());
		if ((modelContextMap != null) && (modelContextMap.size() > 0)) {
			LinkedHashSet localLinkedHashSet = new LinkedHashSet();
			Iterator localIterator1 = modelContextMap.keySet().iterator();
			while (localIterator1.hasNext()) {
				Integer localInteger = (Integer) localIterator1.next();
				Iterator localIterator2 = localMenu1.getChildMenuSet().iterator();
				while (localIterator2.hasNext()) {
					Menu localMenu2 = (Menu) localIterator2.next();
					if ((localInteger != null) && (localInteger.equals(localMenu2.getModuleId())))
						localLinkedHashSet.add(localMenu2);
				}
			}
			localMenu1.setChildMenuSet(localLinkedHashSet);
		}
		return localMenu1;
	}

	public static List<Integer> getModuleIDList() {
		if (moduleIDArray == null)
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
		return moduleIDArray;
	}

	private Map<Integer, Map<Object, Object>> getContextMap(StandardService paramStandardService) {
		StandardService localStandardService = (StandardService) ApplicationContextInit.getBean("com_standardService");
		LinkedHashMap localLinkedHashMap1 = new LinkedHashMap();
		LinkedHashMap localLinkedHashMap2 = new LinkedHashMap();
		Iterator localIterator1;
		Object localObject;
		try {
			List localList1 = localStandardService.getListBySql("select * from c_deploy_config t where t.systype='broker' order by t.moduleid asc");
			localIterator1 = localList1.iterator();
			while (localIterator1.hasNext()) {
				localObject = (Map) localIterator1.next();
				localLinkedHashMap2.put(Integer.valueOf(Tools.strToInt((BigDecimal) ((Map) localObject).get("MODULEID") + "")), localObject);
			}
		} catch (Exception localException) {
		}
		if ((localLinkedHashMap2 != null) && (localLinkedHashMap2.size() > 0)) {
			List localList2 = paramStandardService.getListBySql("select * from c_trademodule where 1=1", new TradeModule());
			if (localList2 != null) {
				localIterator1 = localLinkedHashMap2.keySet().iterator();
				while (localIterator1.hasNext()) {
					localObject = (Integer) localIterator1.next();
					Iterator localIterator2 = localList2.iterator();
					while (localIterator2.hasNext()) {
						StandardModel localStandardModel = (StandardModel) localIterator2.next();
						TradeModule localTradeModule = (TradeModule) localStandardModel;
						if ((localObject != null) && (((Integer) localObject).equals(localTradeModule.getModuleId()))) {
							Map localMap = (Map) localLinkedHashMap2.get(localObject);
							if (localMap != null) {
								localMap.put("enName", localTradeModule.getEnName());
								localMap.put("isFirmSet", localTradeModule.getIsFirmSet());
								localMap.put("cnName", localTradeModule.getCnName());
								localMap.put("shortName", localTradeModule.getShortName());
							}
							localLinkedHashMap1.put(localObject, localMap);
							break;
						}
					}
				}
			}
		}
		return localLinkedHashMap1;
	}

	public static int getSelfModuleID() {
		if (selfModuleID == null)
			synchronized (Global.class) {
				if (selfModuleID == null)
					try {
						selfModuleID = Integer.valueOf(Tools.strToInt(ApplicationContextInit.getConfig("SelfModuleID"), COMMONMODULEID));
					} catch (Exception localException) {
						selfModuleID = Integer.valueOf(COMMONMODULEID);
					}
			}
		return selfModuleID.intValue();
	}

	public static String getSelfLogonType() {
		if (selfLogonType == null)
			synchronized (Global.class) {
				if (selfLogonType == null)
					try {
						selfLogonType = ApplicationContextInit.getConfig("selfLogonType");
						if ((selfLogonType == null) || (selfLogonType.trim().length() <= 0))
							selfLogonType = "web";
					} catch (Exception localException) {
						selfLogonType = "web";
					}
			}
		return selfLogonType;
	}

	public static Menu getRootMenu() {
		return rootMenu;
	}

	public static Right getRightTree() {
		return rightTree;
	}

	public static Map<String, String> getMarketInfoMap() {
		return marketInfoMap;
	}
}