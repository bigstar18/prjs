
package gnnt.MEBS.logonServerUtil.au;

import gnnt.MEBS.logonService.dao.LogonManagerDAO;
import gnnt.MEBS.logonService.kernel.ILogonService;
import gnnt.MEBS.logonService.po.LogonConfigPO;
import gnnt.MEBS.logonService.po.ModuleAndAUPO;
import gnnt.MEBS.logonService.vo.CheckUserResultVO;
import gnnt.MEBS.logonService.vo.CheckUserVO;
import gnnt.MEBS.logonService.vo.CompulsoryLogoffResultVO;
import gnnt.MEBS.logonService.vo.CompulsoryLogoffVO;
import gnnt.MEBS.logonService.vo.GetUserResultVO;
import gnnt.MEBS.logonService.vo.GetUserVO;
import gnnt.MEBS.logonService.vo.ISLogonResultVO;
import gnnt.MEBS.logonService.vo.ISLogonVO;
import gnnt.MEBS.logonService.vo.LogoffResultVO;
import gnnt.MEBS.logonService.vo.LogoffVO;
import gnnt.MEBS.logonService.vo.LogonOrLogoffUserVO;
import gnnt.MEBS.logonService.vo.LogonResultVO;
import gnnt.MEBS.logonService.vo.LogonVO;
import gnnt.MEBS.logonService.vo.RemoteLogonServerVO;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * <P>类说明：登录开放类
 * <br/>
 * <br/>
 * </p>
 * 修改记录:
 * <br/>
 * <ul>
 * 
 * <li> 创建类                    |2014-4-24上午10:42:18|金网安泰 </li>
 * 
 * </ul>
 * @author liuzx
 */
public class LogonActualize {
	/** 日志属性 */
	private transient final Log logger = LogFactory.getLog(this.getClass());

	/** 本类本身实例 */
	private volatile static LogonActualize instance;

	/** 本 AU 编号 */
	private int selfConfigID = -1;

	/** 本 AU 类型 */
	private String sysname;

	/** 重置 RMI 连接次数后重新到数据库中获取连接地址的次数 */
	private int clearRMITimes;

	/** 连接数据库 DAO */
	private LogonManagerDAO logonManagerDAO;

	/**
	 * 模块编号和 AU 编号的 Map 集合
	 * key 模块编号，value AU 编号
	 */
	private Map<Integer,Integer> moduleIDAndConfigIDMap = new HashMap<Integer,Integer>();

	/**
	 * 
	 * 构造方法
	 * <br/><br/>
	 * @param moduleID 模块编号
	 * @param callMode 访问 AU 方式。<br/>0 rmi 访问登录管理；1 本地访问，并启动 RMI 服务
	 * @param dataSource 数据源
	 * @param auExpireTimeMap 用户登录超时线程中超时时间配置，以毫秒为单位
	 * @param timeSpace 用户超时线程睡眠时间，以毫秒为单位
	 * @param clearRMITimes 重置 RMI 连接次数后重新到数据库中获取连接地址的次数
	 * @param sysname 系统类型
	 * @throws Exception 
	 */
	private LogonActualize (int moduleID,int callMode,DataSource dataSource,Map<String,Long> auExpireTimeMap,long timeSpace,int clearRMITimes,String sysname) throws Exception{
		this.clearRMITimes = clearRMITimes;
		this.sysname = sysname;
		logonManagerDAO = new LogonManagerDAO();
		logonManagerDAO.setDataSource(dataSource);
		selfConfigID = getConfigID(moduleID);
		AUConnectManager.getInstance().init(selfConfigID, callMode, dataSource, auExpireTimeMap,timeSpace,clearRMITimes);
	}

	/**
	 * 
	 * 通过模块编号获取 AU 编号
	 * <br/><br/>
	 * @param moduleID 模块编号
	 * @return
	 */
	private int getConfigID(int moduleID){

		if(moduleIDAndConfigIDMap.get(moduleID) == null){
			//AU 编号
			int result = -1;

			//通过模块编号获取对应的 AU 编号
			List<ModuleAndAUPO> list = logonManagerDAO.getModuleAndAUList(moduleID, sysname);
	
			if(list == null || list.size() <= 0){
				throw new IllegalArgumentException("没有查到模块["+moduleID+"]在系统类型["+sysname+"]中的配置信息");
			}
	
			for(ModuleAndAUPO moduleAndAUPO : list){
				int configID = moduleAndAUPO.getConfigID();
				LogonConfigPO logonConfigPO = logonManagerDAO.getLogonConfigByID(configID);
				if(logonConfigPO.getHostIP() != null){
					result = logonConfigPO.getConfigID();
				}
			}
	
			if(result < 0){
				throw new IllegalArgumentException("模块["+moduleID+"]在系统类型["+sysname+"]中的配置信息有误");
			}

			moduleIDAndConfigIDMap.put(moduleID, result);
		}

		return moduleIDAndConfigIDMap.get(moduleID);
	}

	/**
	 * 
	 * 单例创建本类实例，并返回实例
	 * <br/><br/>
	 * @param moduleID 模块编号
	 * @param callMode 访问 AU 方式。0 rmi 访问登录管理；1 本地访问，并启动 RMI 服务
	 * @param dataSource 数据源
	 * @param auExpireTimeMap 用户登录超时线程中超时时间配置，以毫秒为单位
	 * @param timeSpace 用户超时线程睡眠时间，以毫秒为单位
	 * @param clearRMITimes 重置 RMI 连接次数后重新到数据库中获取连接地址的次数
	 * @param sysname 系统类型
	 * @return LogonActualize
	 * @throws Exception 
	 */
	public static LogonActualize createInstance(int moduleID,int callMode,DataSource dataSource,Map<String,Long> auExpireTimeMap,long timeSpace,int clearRMITimes,String sysname) throws Exception{
		if(instance == null){
			synchronized(LogonActualize.class){
				if(instance == null){
					instance = new LogonActualize(moduleID,callMode,dataSource,auExpireTimeMap,timeSpace,clearRMITimes,sysname);
				}
			}
		}
		return instance;
	}

	/**
	 * 
	 * 远程调用 AU 时简易构造连接方法
	 * <br/><br/>
	 * @param moduleID 模块编号
	 * @param dataSource 数据源
	 * @param sysname 系统类型
	 * @return
	 * @throws Exception 
	 */
	public static LogonActualize createInstance(int moduleID,DataSource dataSource,int clearRMITimes,String sysname) throws Exception{
		if(instance == null){
			synchronized(LogonActualize.class){
				if(instance == null){
					instance = new LogonActualize(moduleID,0,dataSource,null,0,clearRMITimes,sysname);
				}
			}
		}
		return instance;
	}

	/**
	 * 获取 本类实例
	 * @return
	 */
	public static LogonActualize getInstance(){
		return instance;
	}

	/**
	 * 
	 * 获取数据库连接 DAO
	 * <br/><br/>
	 * @return LogonManagerDAO
	 */
	public LogonManagerDAO getlogonManagerDAO(){
		return logonManagerDAO;
	}

	/**
	 * 
	 * 用户登录
	 * <br/><br/>
	 * @param logonVO 登录时，传入对象
	 * @return
	 */
	public LogonResultVO logon(LogonVO logonVO){
		LogonResultVO result = new LogonResultVO();
		result.setResult(-1);
		ILogonService logonService = null;

		RemoteLogonServerVO remoteLogonServerVO = null;
		if(logonVO.getModuleID() > 0){
			remoteLogonServerVO = AUConnectManager.getInstance().getRemoteLogonServerVO(getConfigID(logonVO.getModuleID()));
		}else{
			remoteLogonServerVO = AUConnectManager.getInstance().getRemoteLogonServerVO();
		}
		try {
			logonService = remoteLogonServerVO.getRmiService();
			result = logonService.logon(logonVO);
		} catch (RemoteException e){
			int times = remoteLogonServerVO.clearRMI();
			try{
				logonService = remoteLogonServerVO.getRmiService();
				result = logonService.logon(logonVO);
			} catch (RemoteException e1){
				if(clearRMITimes > 0 && times > clearRMITimes){
					LogonConfigPO logonConfigPO = logonManagerDAO.getLogonConfigByID(selfConfigID);
					remoteLogonServerVO.setLogonConfigPO(logonConfigPO);
				}
				result.setRecode("-1");
				result.setMessage("调用 AU 异常");
				logger.error("调用 AU 异常",e1);
			}catch(Exception e1){
				result.setRecode("-1");
				result.setMessage("调用 AU 异常");
				logger.error("调用 AU 异常",e1);
			}
		} catch (Exception e) {
			result.setRecode("-1");
			result.setMessage("调用 AU 异常");
			logger.error("调用 AU 异常",e);
		}
		return result;
	}

	/**
	 * 
	 * 退出登录
	 * <br/><br/>
	 * @param logoffVO
	 * @return
	 */
	public LogoffResultVO logoff(LogoffVO logoffVO){
		LogoffResultVO result = new LogoffResultVO();
		result.setResult(-1);
		ILogonService logonService = null;

		RemoteLogonServerVO remoteLogonServerVO = null;
		if(logoffVO.getModuleID() > 0){
			remoteLogonServerVO = AUConnectManager.getInstance().getRemoteLogonServerVO(getConfigID(logoffVO.getModuleID()));
		}else{
			remoteLogonServerVO = AUConnectManager.getInstance().getRemoteLogonServerVO();
		}
		try {
			logonService = remoteLogonServerVO.getRmiService();
			result = logonService.logoff(logoffVO);
		} catch (RemoteException e){
			int times = remoteLogonServerVO.clearRMI();
			try{
				logonService = remoteLogonServerVO.getRmiService();
				result = logonService.logoff(logoffVO);
			} catch (RemoteException e1){
				if(clearRMITimes > 0 && times > clearRMITimes){
					LogonConfigPO logonConfigPO = logonManagerDAO.getLogonConfigByID(selfConfigID);
					remoteLogonServerVO.setLogonConfigPO(logonConfigPO);
				}
				result.setRecode("-1");
				result.setMessage("调用 AU 异常");
				logger.error("调用 AU 异常",e1);
			}catch(Exception e1){
				result.setRecode("-1");
				result.setMessage("调用 AU 异常");
				logger.error("调用 AU 异常",e1);
			}
		} catch (Exception e) {
			result.setRecode("-1");
			result.setMessage("调用 AU 异常");
			logger.error("调用 AU 异常",e);
		}
		return result;
	}

	/**
	 * 
	 * 用户跳转
	 * <br/><br/>
	 * @param checkUserVO 登录 RMI 信息
	 * @param fromModuleID 来源模块编号
	 * @param fromLogonType 来源登录类型 (web web服务登录、pc 电脑客户端登录、mobile 手机客户端登录)
	 * @return
	 */
	public CheckUserResultVO checkUser(CheckUserVO checkUserVO,int fromModuleID,String fromLogonType){
		CheckUserResultVO result = new CheckUserResultVO();
		result.setResult(-1);
		ILogonService logonService = null;

		RemoteLogonServerVO remoteLogonServerVO = null;
		if(checkUserVO.getToModuleID() > 0){
			remoteLogonServerVO = AUConnectManager.getInstance().getRemoteLogonServerVO(getConfigID(checkUserVO.getToModuleID()));
		}else{
			remoteLogonServerVO = AUConnectManager.getInstance().getRemoteLogonServerVO();
		}
		try {
			logonService = remoteLogonServerVO.getRmiService();
			result = logonService.checkUser(checkUserVO, fromModuleID,fromLogonType);
		} catch (RemoteException e){
			int times = remoteLogonServerVO.clearRMI();
			try{
				logonService = remoteLogonServerVO.getRmiService();
				result = logonService.checkUser(checkUserVO, fromModuleID,fromLogonType);
			} catch (RemoteException e1){
				if(clearRMITimes > 0 && times > clearRMITimes){
					LogonConfigPO logonConfigPO = logonManagerDAO.getLogonConfigByID(selfConfigID);
					remoteLogonServerVO.setLogonConfigPO(logonConfigPO);
				}
				result.setRecode("-1");
				result.setMessage("调用 AU 异常");
				logger.error("调用 AU 异常",e1);
			}catch(Exception e1){
				result.setRecode("-1");
				result.setMessage("调用 AU 异常");
				logger.error("调用 AU 异常",e1);
			}
		} catch (Exception e) {
			result.setRecode("-1");
			result.setMessage("调用 AU 异常");
			logger.error("调用 AU 异常",e);
		}
		return result;
	}

	/**
	 * 
	 * 通过 SessionID 获取登录用户信息
	 * <br/><br/>
	 * @param isLogonVO
	 * @return
	 */
	public GetUserResultVO getUserBySessionID(GetUserVO getUserVO){
		GetUserResultVO result = new GetUserResultVO();
		result.setResult(-1);
		ILogonService logonService = null;

		RemoteLogonServerVO remoteLogonServerVO = null;
		if(getUserVO.getModuleID() > 0){
			remoteLogonServerVO = AUConnectManager.getInstance().getRemoteLogonServerVO(getConfigID(getUserVO.getModuleID()));
		}else{
			remoteLogonServerVO = AUConnectManager.getInstance().getRemoteLogonServerVO();
		}

		try {
			logonService = remoteLogonServerVO.getRmiService();
			result = logonService.getUserBySessionID(getUserVO);
		} catch (RemoteException e){
			int times = remoteLogonServerVO.clearRMI();
			try{
				logonService = remoteLogonServerVO.getRmiService();
				result = logonService.getUserBySessionID(getUserVO);
			} catch (RemoteException e1){
				if(clearRMITimes > 0 && times > clearRMITimes){
					LogonConfigPO logonConfigPO = logonManagerDAO.getLogonConfigByID(selfConfigID);
					remoteLogonServerVO.setLogonConfigPO(logonConfigPO);
				}
				result.setRecode("-1");
				result.setMessage("调用 AU 异常");
				logger.error("调用 AU 异常",e1);
			}catch(Exception e1){
				result.setRecode("-1");
				result.setMessage("调用 AU 异常");
				logger.error("调用 AU 异常",e1);
			}
		} catch (Exception e) {
			result.setRecode("-1");
			result.setMessage("调用 AU 异常");
			logger.error("调用 AU 异常",e);
		}
		return result;
	}

	/**
	 * 
	 * 判断用户是否已经登录
	 * <br/><br/>
	 * @param isLogonVO
	 * @return
	 */
	public ISLogonResultVO isLogon(ISLogonVO isLogonVO){
		ISLogonResultVO result = new ISLogonResultVO();
		result.setResult(-1);
		ILogonService logonService = null;

		RemoteLogonServerVO remoteLogonServerVO = null;
		if(isLogonVO.getModuleID() > 0){
			remoteLogonServerVO = AUConnectManager.getInstance().getRemoteLogonServerVO(getConfigID(isLogonVO.getModuleID()));
		}else{
			remoteLogonServerVO = AUConnectManager.getInstance().getRemoteLogonServerVO();
		}

		try {
			logonService = remoteLogonServerVO.getRmiService();
			result = logonService.isLogon(isLogonVO);
		} catch (RemoteException e){
			int times = remoteLogonServerVO.clearRMI();
			try{
				logonService = remoteLogonServerVO.getRmiService();
				result = logonService.isLogon(isLogonVO);
			} catch (RemoteException e1){
				if(clearRMITimes > 0 && times > clearRMITimes){
					LogonConfigPO logonConfigPO = logonManagerDAO.getLogonConfigByID(selfConfigID);
					remoteLogonServerVO.setLogonConfigPO(logonConfigPO);
				}
				result.setRecode("-1");
				result.setMessage("调用 AU 异常");
				logger.error("调用 AU 异常",e1);
			}catch(Exception e1){
				result.setRecode("-1");
				result.setMessage("调用 AU 异常");
				logger.error("调用 AU 异常",e1);
			}
		} catch (Exception e) {
			result.setRecode("-1");
			result.setMessage("调用 AU 异常");
			logger.error("调用 AU 异常",e);
		}
		return result;
	}

	/**
	 * 
	 * 强制退出用户
	 * <br/><br/>
	 * @param compulsoryLogoffVO
	 * @return
	 */
	public CompulsoryLogoffResultVO compulsoryLogoff(CompulsoryLogoffVO compulsoryLogoffVO){
		CompulsoryLogoffResultVO result = new CompulsoryLogoffResultVO();
		result.setResult(-1);
		ILogonService logonService = null;

		RemoteLogonServerVO remoteLogonServerVO = AUConnectManager.getInstance().getRemoteLogonServerVO();

		try {
			logonService = remoteLogonServerVO.getRmiService();
			result = logonService.compulsoryLogoff(compulsoryLogoffVO);
		} catch (RemoteException e){
			int times = remoteLogonServerVO.clearRMI();
			try{
				logonService = remoteLogonServerVO.getRmiService();
				result = logonService.compulsoryLogoff(compulsoryLogoffVO);
			} catch (RemoteException e1){
				if(clearRMITimes > 0 && times > clearRMITimes){
					LogonConfigPO logonConfigPO = logonManagerDAO.getLogonConfigByID(selfConfigID);
					remoteLogonServerVO.setLogonConfigPO(logonConfigPO);
				}
				result.setRecode("-1");
				result.setMessage("调用 AU 异常");
				logger.error("调用 AU 异常",e1);
			}catch(Exception e1){
				result.setRecode("-1");
				result.setMessage("调用 AU 异常");
				logger.error("调用 AU 异常",e1);
			}
		} catch (Exception e) {
			result.setRecode("-1");
			result.setMessage("调用 AU 异常");
			logger.error("调用 AU 异常",e);
		}
		return result;
	}

	/**
	 * 
	 * 获取登录退出列表
	 * <br/><br/>
	 * @return
	 */
	public List<LogonOrLogoffUserVO> getLogonOrLogoffUserList(){
		List<LogonOrLogoffUserVO> result = null;
		ILogonService logonService = null;

		RemoteLogonServerVO remoteLogonServerVO = AUConnectManager.getInstance().getRemoteLogonServerVO();

		try {
			logonService = remoteLogonServerVO.getRmiService();
			result = logonService.getLogonOrLogoffUserList();
		} catch (RemoteException e){
			int times = remoteLogonServerVO.clearRMI();
			try{
				logonService = remoteLogonServerVO.getRmiService();
				result = logonService.getLogonOrLogoffUserList();
			} catch (RemoteException e1){
				if(clearRMITimes > 0 && times > clearRMITimes){
					LogonConfigPO logonConfigPO = logonManagerDAO.getLogonConfigByID(selfConfigID);
					remoteLogonServerVO.setLogonConfigPO(logonConfigPO);
				}
				logger.error("调用 AU 异常",e1);
			}catch(Exception e1){
				logger.error("调用 AU 异常",e1);
			}
		} catch (Exception e) {
			logger.error("调用 AU 异常",e);
		}
		return result;
	}

	/**
	 * 
	 * 获取 AU 中的所有登录用户信息
	 * <br/><br/>
	 * @return
	 */
	public List<LogonOrLogoffUserVO> getAllLogonUserList(){
		List<LogonOrLogoffUserVO> result = null;
		ILogonService logonService = null;

		RemoteLogonServerVO remoteLogonServerVO = AUConnectManager.getInstance().getRemoteLogonServerVO();

		try {
			logonService = remoteLogonServerVO.getRmiService();
			result = logonService.getAllLogonUserList();
		} catch (RemoteException e){
			int times = remoteLogonServerVO.clearRMI();
			try{
				logonService = remoteLogonServerVO.getRmiService();
				result = logonService.getAllLogonUserList();
			} catch (RemoteException e1){
				if(clearRMITimes > 0 && times > clearRMITimes){
					LogonConfigPO logonConfigPO = logonManagerDAO.getLogonConfigByID(selfConfigID);
					remoteLogonServerVO.setLogonConfigPO(logonConfigPO);
				}
				logger.error("调用 AU 异常",e1);
			}catch(Exception e1){
				logger.error("调用 AU 异常",e1);
			}
		} catch (Exception e) {
			logger.error("调用 AU 异常",e);
		}
		return result;
	}

	/**
	 * 
	 * 是否重新启动的服务
	 * <br/><br/>
	 * @return int 1 是，2 否，0 调用 AU 服务异常
	 */
	public int isRestartStartRMI(){
		ILogonService logonService = null;

		RemoteLogonServerVO remoteLogonServerVO = AUConnectManager.getInstance().getRemoteLogonServerVO();

		try {
			logonService =remoteLogonServerVO.getRmiService();
			return logonService.isRestartStartRMI();
		} catch (RemoteException e){
			int times = remoteLogonServerVO.clearRMI();
			try{
				logonService = remoteLogonServerVO.getRmiService();
				return logonService.isRestartStartRMI();
			} catch (RemoteException e1){
				if(clearRMITimes > 0 && times > clearRMITimes){
					LogonConfigPO logonConfigPO = logonManagerDAO.getLogonConfigByID(selfConfigID);
					remoteLogonServerVO.setLogonConfigPO(logonConfigPO);
				}
				logger.error("调用 AU 异常",e1);
			}catch(Exception e1){
				logger.error("调用 AU 异常",e1);
			}
		} catch (Exception e) {
			logger.error("调用 AU 异常",e);
		}
		return 0;
	}
}

