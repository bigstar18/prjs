package gnnt.MEBS.logonService.kernel.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gnnt.MEBS.activeUser.operation.ActiveUserManage;
import gnnt.MEBS.activeUser.util.LogonMode;
import gnnt.MEBS.activeUser.vo.AULogonTimeOutThreadVO;
import gnnt.MEBS.logonService.dao.LogonManagerDAO;
import gnnt.MEBS.logonService.po.LogonConfigPO;
import gnnt.MEBS.logonService.po.ModuleAndAUPO;
import gnnt.MEBS.logonService.vo.LogonTimeOutThreadVO;
import gnnt.MEBS.logonService.vo.RemoteLogonServerVO;

public class LogonServiceBase extends UnicastRemoteObject {
	/** 序列编号 */
	private static final long serialVersionUID = -3808685203518790727L;

	/** 日志属性 */
	protected transient final Log logger = LogFactory.getLog(this.getClass());

	/** 数据库查询 */
	private LogonManagerDAO logonManagerDAO;

	/** 多少次重新连接 RMI 服务失败后，则重新获取连接 */
	protected int clearRMITimes;

	/** 本 RMI 信息 */
	private LogonConfigPO selfLogonConfigPO;

	/**
	 * 同本系统名称一致的所有 AU 非本 RMI 服务 Map集合<br/>
	 * key AU 编号;value AU 连接信息
	 */
	protected Map<Integer, RemoteLogonServerVO> logonManagerMap = new HashMap<Integer, RemoteLogonServerVO>();

	/**
	 * 同本系统名称一致的所有模块对应 AU 编号的 Map 集合<br/>
	 * key 模块编号;value AU 编号
	 */
	protected Map<Integer, Integer> moduleAndAUMap = new HashMap<Integer, Integer>();

	/**
	 * 
	 * 构造方法
	 * <br/>
	 * <br/>
	 * 
	 * @throws RemoteException
	 */
	protected LogonServiceBase() throws RemoteException {
		super();
	}

	/**
	 * 
	 * 初始化服务
	 * <br/>
	 * <br/>
	 * 
	 * @param selfConfigID
	 *            本 AU 编号
	 * @param clearRMITimes
	 *            重置 RMI 连接次数后重新到数据库中获取连接地址的次数
	 * @param logonTimeOutThreadVO
	 *            登录超时扫描线程信息
	 * @param dataSource
	 *            数据源
	 */
	public void init(int selfConfigID, int clearRMITimes, LogonTimeOutThreadVO logonTimeOutThreadVO, DataSource dataSource) {
		this.clearRMITimes = clearRMITimes;

		logonManagerDAO = new LogonManagerDAO();
		logonManagerDAO.setDataSource(dataSource);

		// 初始化本 AU 信息
		this.initSelfLogonConfig(selfConfigID);

		// 初始化本 AU 同组 AU 信息
		this.initLogonManagerMap();

		// 初始化 模块和 AU 对应关系 Map
		initModuleAndAUMap();

		// 初始化本 AU 互踢模式
		this.initLogonMode();

		// 启动登录超时线程
		this.startLogonTimeOutThread(logonTimeOutThreadVO);
	}

	/**
	 * 
	 * 通过模块编号获取 AU 编号
	 * <br/>
	 * <br/>
	 * 
	 * @param moduleID
	 *            模块编号
	 * @return int AU 编号 (-1 表示没有对应关系)
	 */
	public int getConfigID(int moduleID) {
		// 如果 AU 中已经加载了对应关系，则直接返回对应的 AU 编号
		if (moduleAndAUMap.get(moduleID) != null) {
			return moduleAndAUMap.get(moduleID);
		}

		// 通过模块编号和系统类型获取对应关系
		List<ModuleAndAUPO> list = logonManagerDAO.getModuleAndAUList(moduleID, selfLogonConfigPO.getSysname());
		if (list == null || list.size() <= 0) {
			logger.error("系统中没有配置模块[" + moduleID + "]在[" + selfLogonConfigPO.getSysname() + "]类型 AU 中的对应关系");
			return -1;
		}

		if (list.size() > 1) {
			String aus = "";
			for (ModuleAndAUPO moduleAndAUPO : list) {
				if (aus.trim().length() > 0) {
					aus += ",";
				}
				aus += moduleAndAUPO.getConfigID();
			}
			logger.error("系统中找到模块[" + moduleID + "]对应[" + aus + "]多个 AU 系统将从中找到第一个可用的 AU 作为对应连接");
		}

		for (ModuleAndAUPO moduleAndAUPO : list) {
			// 配置的 AU 编号
			int configID = moduleAndAUPO.getConfigID();

			// 如果对应的 AU 已经在配置中，则直接返回对应的 AU 编号
			if (logonManagerMap.get(configID) != null) {
				moduleAndAUMap.put(moduleID, configID);
				return configID;
			}

			// 通过 AU 编号获取AU信息
			LogonConfigPO logonConfigPO = logonManagerDAO.getLogonConfigByID(configID);

			// 如果没有查到 AU 则继续下一循环
			if (logonConfigPO == null) {
				continue;
			}

			// 如果 AU IP 地址为空，则继续下一循环
			if (logonConfigPO.getHostIP() == null || logonConfigPO.getHostIP().trim().length() <= 0) {
				continue;
			}

			RemoteLogonServerVO logonManagerVO = new RemoteLogonServerVO();
			logonManagerVO.setLogonConfigPO(logonConfigPO);
			logonManagerMap.put(logonConfigPO.getConfigID(), logonManagerVO);
			logger.info("加载 " + logonConfigPO.getConfigID() + "AU ：rmi://" + logonConfigPO.getHostIP() + ":" + logonConfigPO.getPort() + "/"
					+ logonConfigPO.getServiceName() + RemoteLogonServerVO.serviceEnd);

			moduleAndAUMap.put(moduleID, configID);

			return configID;
		}

		logger.error("模块[" + moduleID + "]对应的 AU 不可用，请调整");

		return -1;
	}

	/**
	 * 
	 * 初始化本 AU 数据库配置信息
	 * <br/>
	 * <br/>
	 * 
	 * @param selfConfigID
	 * @param logonManagerDAO
	 */
	private void initSelfLogonConfig(int selfConfigID) {
		logger.info("加载本 AU 信息......");
		selfLogonConfigPO = logonManagerDAO.getLogonConfigByID(selfConfigID);
		if (selfLogonConfigPO == null) {
			throw new IllegalArgumentException("通过编号 " + selfConfigID + "未查询到 AU 配置信息");
		}
	}

	public void init(LogonConfigPO paramLogonConfigPO, int paramInt, LogonTimeOutThreadVO paramLogonTimeOutThreadVO, DataSource paramDataSource) {
		this.clearRMITimes = paramInt;
		this.logonManagerDAO = new LogonManagerDAO();
		this.logonManagerDAO.setDataSource(paramDataSource);
		this.selfLogonConfigPO = paramLogonConfigPO;
		initLogonManagerMap();
		initModuleAndAUMap();
		initLogonMode();
		startLogonTimeOutThread(paramLogonTimeOutThreadVO);
	}

	private void initLogonManagerMap() {
		this.logger.info("加载除本 AU 外的其他同组 AU 信息 ......");
		List localList = this.logonManagerDAO.getLogonConfigList(this.selfLogonConfigPO.getSysname());
		if ((localList != null) && (localList.size() > 0)) {
			Iterator localIterator = localList.iterator();
			while (localIterator.hasNext()) {
				LogonConfigPO localLogonConfigPO = (LogonConfigPO) localIterator.next();
				if ((localLogonConfigPO.getConfigID() != this.selfLogonConfigPO.getConfigID()) && (localLogonConfigPO.getHostIP() != null)
						&& (localLogonConfigPO.getHostIP().trim().length() > 0)) {
					RemoteLogonServerVO localRemoteLogonServerVO = new RemoteLogonServerVO();
					localRemoteLogonServerVO.setLogonConfigPO(localLogonConfigPO);
					this.logonManagerMap.put(localLogonConfigPO.getConfigID(), localRemoteLogonServerVO);
					this.logger.info(new StringBuilder().append("加载 ").append(localLogonConfigPO.getConfigID()).append("AU ：rmi://")
							.append(localLogonConfigPO.getHostIP()).append(":").append(localLogonConfigPO.getPort()).append("/")
							.append(localLogonConfigPO.getServiceName()).append("LogonService").toString());
				}
			}
		}
	}

	private void initModuleAndAUMap() {
		this.logger.info("加载各个模块对应 AU 编号信息......");
		List localList = this.logonManagerDAO.getModuleAndAUList(this.selfLogonConfigPO.getSysname());
		if ((localList == null) || (localList.size() <= 0)) {
			this.logger.error(
					new StringBuilder().append("没有模块用到类型为 ").append(this.selfLogonConfigPO.getSysname()).append(" 的  AU ，无需启动 ......").toString());
			throw new IllegalArgumentException(
					new StringBuilder().append("没有模块用到类型为 ").append(this.selfLogonConfigPO.getSysname()).append(" 的  AU ，无需启动 ......").toString());
		}
		HashMap localHashMap = new HashMap();
		Iterator localIterator = localList.iterator();
		Object localObject;
		StringBuilder localStringBuilder;
		while (localIterator.hasNext()) {
			localObject = (ModuleAndAUPO) localIterator.next();
			if ((this.logonManagerMap.get(Integer.valueOf(((ModuleAndAUPO) localObject).getConfigID())) == null)
					&& (((ModuleAndAUPO) localObject).getConfigID() != this.selfLogonConfigPO.getConfigID().intValue())) {
				localStringBuilder = (StringBuilder) localHashMap.get(Integer.valueOf(((ModuleAndAUPO) localObject).getConfigID()));
				if (localStringBuilder == null) {
					localStringBuilder = new StringBuilder();
					localHashMap.put(Integer.valueOf(((ModuleAndAUPO) localObject).getConfigID()), localStringBuilder);
				} else {
					localStringBuilder.append(",");
				}
				localStringBuilder.append(((ModuleAndAUPO) localObject).getModuleID());
			} else {
				this.moduleAndAUMap.put(Integer.valueOf(((ModuleAndAUPO) localObject).getModuleID()),
						Integer.valueOf(((ModuleAndAUPO) localObject).getConfigID()));
			}
		}
		if (localHashMap.size() > 0) {
			localIterator = localHashMap.keySet().iterator();
			while (localIterator.hasNext()) {
				localObject = (Integer) localIterator.next();
				localStringBuilder = (StringBuilder) localHashMap.get(localObject);
				this.logger.info(new StringBuilder().append("有模块[").append(localStringBuilder.toString()).append("]对应着AU[").append(localObject)
						.append("]但 AU 无法连接。").toString());
			}
		}
	}

	private void initLogonMode() {
		this.logger.info("初始化本 AU 的互踢模式......");
		if ((this.selfLogonConfigPO.getLogonMode() != null) && (this.selfLogonConfigPO.getLogonMode().intValue() == 2)) {
			ActiveUserManage.getInstance().setLogonMode(LogonMode.MULTI_MODE);
			this.logger.info("AU 当前设置为不互踢模式。");
		} else {
			ActiveUserManage.getInstance().setLogonMode(LogonMode.SINGLE_MODE);
			this.logger.info("AU 当前设置为互踢模式。");
		}
	}

	private void startLogonTimeOutThread(LogonTimeOutThreadVO paramLogonTimeOutThreadVO) {
		this.logger.info("启动 AU 超时自动退出线程......");
		AULogonTimeOutThreadVO localAULogonTimeOutThreadVO = new AULogonTimeOutThreadVO();
		localAULogonTimeOutThreadVO.setAuExpireTimeMap(paramLogonTimeOutThreadVO.getAuExpireTimeMap());
		localAULogonTimeOutThreadVO.setTimeSpace(paramLogonTimeOutThreadVO.getTimeSpace());
		ActiveUserManage.getInstance().startLogonTimeOutThread(localAULogonTimeOutThreadVO);
	}

	protected LogonConfigPO getLogonConfigByID(int paramInt) {
		LogonConfigPO localLogonConfigPO = this.logonManagerDAO.getLogonConfigByID(paramInt);
		return localLogonConfigPO;
	}

	public LogonConfigPO getSelfLogonConfigPO() {
		return this.selfLogonConfigPO;
	}
}