package gnnt.MEBS.common.mgr.common;

import gnnt.MEBS.checkLogon.check.mgr.MgrCheck;
import gnnt.MEBS.common.mgr.model.Menu;
import gnnt.MEBS.common.mgr.model.Right;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.TradeModule;
import gnnt.MEBS.common.mgr.service.MenuService;
import gnnt.MEBS.common.mgr.service.RightService;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.common.mgr.statictools.ApplicationContextInit;
import gnnt.MEBS.common.mgr.statictools.Tools;
import gnnt.MEBS.logonServerUtil.au.LogonActualize;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

/**
 * 全局对象 系统启动时加载；包含菜单等信息
 * 
 * @author Administrator
 * 
 */
public class Global implements ServletContextListener {
	/**
	 * session中存放当前用户信息的key值<br>
	 * 通过 request.getSession().getAttribute(Global.CURRENTUSERSTR)获取 <br>
	 * 通过 request.getSession().setAttribute(Global.CURRENTUSERSTR)设置 <br>
	 */
	public final static String CURRENTUSERSTR = "CurrentUser";

	/**
	 * 转到登录界面原因变量<br>
	 * 通过 request.getParameter(Global.TOLOGINURLREASON)获取
	 */
	public final static String TOLOGINURLREASON = "reason";

	/**
	 * 存放sessionID 变量<br>
	 * 通过 request.getParameter(Global.SESSIONID)获取
	 */
	public final static String SESSIONID = "sessionID";

	/**
	 * 存放是否超级管理员 变量<br>
	 * 通过 request.getAttribute(Global.ISSUPERADMIN)获取 <br>
	 * 通过 request.setAttribute(Global.ISSUPERADMIN)设置 <br>
	 */
	public final static String ISSUPERADMIN = "IsSuperAdmin";

	/**
	 * 存放返回结果 变量<br>
	 * 通过 request.getSession().getAttribute(Global.RESULTMSG)获取 <br>
	 * 通过 request.getSession().setAttribute(Global.RESULTMSG)设置 <br>
	 */
	public final static String RETURNVALUE = "ReturnValue";

	/**
	 * 存放FromModuleID 变量<br>
	 * 通过 request.getParameter(Global.FROMAUID)获取
	 */
	public final static String FROMMODULEID = "FromModuleID";
	
	/**
	 * 存放fromLogonType 变量(来源登录类型)<br>
	 * 通过 request.getParameter(Global.FROMLOGONTYPE)获取
	 */
	public final static String FROMLOGONTYPE = "FromLogonType"; 

	/**
	 * 存放LogonType 变量<br>
	 * 通过 request.getParameter(Global.SELFLOGONTYPE)获取
	 */
	public final static String SELFLOGONTYPE = "LogonType";

	/**
	 * 存放 ModuleID 变量<br/>
	 * 通过 request.getParameter(Global.SELFMODULEID)获取
	 */
	public final static String SELFMODULEID = "ModuleID";
	
	/**
	 * 用户登录ID<br>
	 * 通过 request.getSession().getAttribute(Global.USERID)获取 <br>
	 * 通过 request.getSession().setAttribute(Global.USERID)设置 <br>
	 */
	public final static String USERID = "userID";

	/**
	 * 公用系统模块编号
	 */
	public static int COMMONMODULEID = 99;

	/**
	 * 主菜单编号
	 */
	public static long ROOTRIGHTID = 9900000000L;

	/**
	 * 存放本系統模块
	 */
	private static Integer selfModuleID;

	/**
	 * 存放本系統配置的登录类型
	 */
	private static String selfLogonType;

	/**
	 * 存放有权限菜单 变量<br>
	 * 通过 request.getAttribute(Global.HaveRightMenu)获取 <br>
	 * 通过 request.setAttribute(Global.HaveRightMenu)设置 <br>
	 */
	public final static String HAVERIGHTMENU = "HaveRightMenu";

	/**
	 * 存放当前项目加载菜单模块编号集合
	 */
	private static List<Integer> moduleIDArray;

	/**
	 * 存放系统模块与Context名称的Map集合
	 */
	public static Map<Integer,Map<Object,Object>> modelContextMap;

	/**
	 * 主菜单对象
	 */
	private static Menu rootMenu;

	/**
	 * 权限树
	 */
	private static Right rightTree;
	
	/**
	 * 市场信息C_marketInfo
	 */
	private static Map<String,String> marketInfoMap;

	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@SuppressWarnings("unchecked")
	public void contextInitialized(ServletContextEvent arg0) {
		
		//获取按钮 Service
		MenuService menuService = (MenuService) ApplicationContextInit.getBean("com_menuService");
		StandardService standardService=(StandardService)ApplicationContextInit.getBean("com_standardService");
		//获取模块编号和Context名称对应的Map集合
		modelContextMap = getContextMap(menuService);

		//获取主菜单编号
		ROOTRIGHTID = Tools.strToLong(ApplicationContextInit.getConfig("RootRightID"),ROOTRIGHTID);

		//获取市场信息
		List<Map<Object, Object>> listMap=standardService.getListBySql("select * from c_marketInfo");
		marketInfoMap=new LinkedHashMap<String, String>();
		if(listMap!=null&&listMap.size()>0){
			for(Map<Object, Object> map:listMap){
				marketInfoMap.put((String)map.get("INFONAME"), (String)map.get("INFOVALUE"));
			}
		}
		//通过模块编号集合获取菜单
		rootMenu = getMenuById(menuService);

		RightService rightService = (RightService) ApplicationContextInit
				.getBean("com_rightService");

		rightTree = rightService.loadRightTree();

		// 访问模式 0：rmi访问登录管理 1：本地访问登录管理
		int callMode = Tools.strToInt(ApplicationContextInit.getConfig("CallMode"),0);

		//获取数据源
		DataSource dataSource = (DataSource) ApplicationContextInit.getBean("dataSource");

		//用户超时线程睡眠时间，以毫秒为单位
		long  timeSpace = Tools.strToLong(ApplicationContextInit.getConfig("timeSpace"),200);

		//重置 RMI 连接次数后重新到数据库中获取连接地址的次数
		int  clearRMITimes = Tools.strToInt(ApplicationContextInit.getConfig("clearRMITimes"),-1);

		//用户登录超时线程中超时时间配置，以毫秒为单位
		Map<String, Long> auExpireTimeMap = null;
		try{
			auExpireTimeMap = (Map<String, Long>) ApplicationContextInit.getBean("auExpireTimeMap");
		}catch(Exception e){
		}
		
		try {
			LogonActualize.createInstance(getSelfModuleID(), callMode, 
					dataSource, auExpireTimeMap, timeSpace, clearRMITimes,"mgr");

			// 后台登录验证类初始化
			MgrCheck.createInstance(dataSource);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取根菜单
	 */
	public Menu getMenuById(MenuService menuService){
		Menu rootMenu = menuService.getMenuById(ROOTRIGHTID, getModuleIDList());
		
		//将根菜单的子菜单按照模块号的顺序进行排序
		if(modelContextMap != null && modelContextMap.size()>0){
			//重新定义新的子菜单集合
			Set<Menu> childMenuSet = new LinkedHashSet<Menu>();
			//遍历配置的模块编号
			for(Integer moduleId : modelContextMap.keySet()){
				//遍历查询出来的子菜单信息
				for(Menu menu : rootMenu.getChildMenuSet()){
					//如果两个模块号相等则将菜单加入新定义的子菜单集合
					if(moduleId != null && moduleId.equals(menu.getModuleId())){
						childMenuSet.add(menu);
					}
				}
			}
			rootMenu.setChildMenuSet(childMenuSet);
		}

		return rootMenu;
	}

	/**
	 * 获取Spring配置文件中配置的需要加载菜单的模块编号集合，两个模块号之间以英文逗号分隔
	 * @return List<Integer>
	 */
	public static List<Integer> getModuleIDList(){
		if(moduleIDArray == null){
			synchronized(Global.class){
				if(moduleIDArray == null){
					moduleIDArray = new ArrayList<Integer>();
					moduleIDArray.add(COMMONMODULEID);
					int module = getSelfModuleID();//获取配置文件中配置的本系统模块号
					if(module != COMMONMODULEID){//如果本系统不是公用系统，则只加载自己的菜单
						moduleIDArray.add(module);
					}else{//如果是公用系统，则加载所有客家在模块菜单
						if(modelContextMap != null){
							for(Integer moduleId : modelContextMap.keySet()){
								moduleIDArray.add(moduleId);
							}
						}
					}
				}
			}
		}
		return moduleIDArray;
	}

	/**
	 * 获取模块类型和context名称的map集合
	 * @param menuService
	 * @return
	 */
	private Map<Integer,Map<Object,Object>> getContextMap(StandardService menuService){
		
		StandardService standardService = (StandardService) ApplicationContextInit.getBean("com_standardService");
		//返回信息
		Map<Integer,Map<Object,Object>> result = new LinkedHashMap<Integer,Map<Object,Object>>();

		//从数据库c_deploy_config表中获取系统配置信息
		Map<Integer,Map<Object,Object>> springTradeModuleMap = new LinkedHashMap<Integer, Map<Object,Object>>();
		try{
			List<Map<Object, Object>> listMap=standardService.getListBySql("select * from c_deploy_config t where t.systype='mgr' order by t.sortno,t.moduleid asc");
			for(Map<Object, Object> map:listMap){
				Map<Object,Object> mapClone = new HashMap<Object,Object>();
				for(Object key : map.keySet()){
					mapClone.put(key, map.get(key));
				}
				springTradeModuleMap.put(Tools.strToInt((BigDecimal)(mapClone.get("MODULEID"))+""),mapClone);
			}
		}catch(Exception e){
		}
		if(springTradeModuleMap != null && springTradeModuleMap.size()>0){
			//查询系统中部署了哪几个系统
			List<StandardModel> list = menuService.getListBySql("select * from c_trademodule where 1=1", new TradeModule());
			if(list != null){
				for(Integer moduleID : springTradeModuleMap.keySet()){//外层循环配置文件中的配置，以使得页面展示按钮时有顺序性
					for(StandardModel model : list){
						TradeModule tm = (TradeModule)model;
						if(moduleID != null && moduleID.equals(tm.getModuleId())){
							Map<Object,Object> value = springTradeModuleMap.get(moduleID);
							/*
							 * 由于 Spring 配置文件中配置的多个地方引用同一个ID号时，
							 * 认为本ID号所引用的实体对象是同一个实体对象，操作的是同一块内存。
							 * 所以：如果配置文件中错误的将两个 moduleID 配置成一个 Map 对象
							 * 则两个 moduleID 中保存的模块信息完全相同，导致页面上展示的信息也是完全相同的。
							 */
							if(value != null){
								value.put("enName", tm.getEnName());
								value.put("isFirmSet", tm.getIsFirmSet());
								value.put("cnName", tm.getCnName());
								value.put("shortName", tm.getShortName());
								value.put("ISNEEDBREED", tm.getIsNeedBreed());
							}
							result.put(moduleID, value);
							break;
						}
					}
				}
			}
		}
		return result;
	}

	/**
	 * 本系统模块编号
	 * @return
	 */
	public static int getSelfModuleID(){
		if(selfModuleID == null){
			synchronized(Global.class){
				if(selfModuleID == null){
					try{
						selfModuleID = Tools.strToInt(ApplicationContextInit.getConfig("SelfModuleID"),COMMONMODULEID);
					}catch(Exception e){
						selfModuleID = COMMONMODULEID;
					}
				}
			}
		}
		return selfModuleID;
	}
	/**
	 * 登录类型
	 * @return
	 */
	public static String getSelfLogonType(){
		if(selfLogonType == null){
			synchronized (Global.class) {
				if(selfLogonType == null){
					try {
						selfLogonType = ApplicationContextInit.getConfig("selfLogonType");
						if(selfLogonType == null || selfLogonType.trim().length() <= 0){
							selfLogonType = "web";
						}
					} catch (Exception e) {
						selfLogonType = "web";
					}
				}
			}
		}
		return selfLogonType;
	}

	/**
	 * 获取主菜单对象
	 * 
	 * @return
	 */
	public static Menu getRootMenu() {
		return rootMenu;
	}

	/**
	 * 权限树
	 * 
	 * @return
	 */
	public static Right getRightTree() {
		return rightTree;
	}
	/**
	 * 获取市场信息
	 * @return
	 */
	public static Map<String, String> getMarketInfoMap(){
		return marketInfoMap;
	}

}
