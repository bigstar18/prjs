
package gnnt.MEBS.activeUser.operation;

import gnnt.MEBS.activeUser.thread.LogonTimeOutThread;
import gnnt.MEBS.activeUser.util.ActiveUserUtil;
import gnnt.MEBS.activeUser.util.LogonMode;
import gnnt.MEBS.activeUser.vo.AUCheckUserResultVO;
import gnnt.MEBS.activeUser.vo.AUCheckUserVO;
import gnnt.MEBS.activeUser.vo.AUCompulsoryLogoffResultVO;
import gnnt.MEBS.activeUser.vo.AUCompulsoryLogoffVO;
import gnnt.MEBS.activeUser.vo.AUGetUserResultVO;
import gnnt.MEBS.activeUser.vo.AUGetUserVO;
import gnnt.MEBS.activeUser.vo.AUISLogonResultVO;
import gnnt.MEBS.activeUser.vo.AUISLogonVO;
import gnnt.MEBS.activeUser.vo.AULogoffResultVO;
import gnnt.MEBS.activeUser.vo.AULogoffVO;
import gnnt.MEBS.activeUser.vo.AULogonOrLogoffUserVO;
import gnnt.MEBS.activeUser.vo.AULogonResultVO;
import gnnt.MEBS.activeUser.vo.AULogonTimeOutThreadVO;
import gnnt.MEBS.activeUser.vo.AULogonTypeManageVO;
import gnnt.MEBS.activeUser.vo.AULogonVO;
import gnnt.MEBS.activeUser.vo.AUUserManageVO;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <P>
 * 类说明：AU 开放方法类
 * <br/>
 * <br/>
 * </p>
 * 修改记录:
 * <br/>
 * <ul>
 * 
 * <li>创建类 |2014-4-21下午01:40:27|金网安泰</li>
 * 
 * </ul>
 * 
 * @author liuzx
 */
public class ActiveUserManage {
	/** 日志属性 */
	private transient final Log logger = LogFactory.getLog(this.getClass());

	/** 本身实例 */
	private static ActiveUserManage instance;

	/**
	 * 是否重新启动 RMI<br/>
	 * 刚刚启动时，为 1，后台获取过了本 AU 内存数据后改为 2
	 */
	private int restartRMI = 1;

	/**
	 * 登录用户总 Map<br/>
	 * key 登录类型; value LogonTypeManageVO
	 */
	private Map<String, AULogonTypeManageVO> logonTypeMap = new HashMap<String, AULogonTypeManageVO>();

	/** 用户登录或退出后，向list 中加入信息 */
	private List<AULogonOrLogoffUserVO> logonOrLogoffUserList = new ArrayList<AULogonOrLogoffUserVO>();

	/** 判断超时登录，退出的线程 */
	private LogonTimeOutThread logonTimeOutThread;

	/**
	 * 多点登录还是单点登录<br/>
	 * 默认为不互踢
	 */
	private LogonMode logonMode = LogonMode.SINGLE_MODE;

	/**
	 * 
	 * 构造方法
	 * <br/>
	 * <br/>
	 */
	private ActiveUserManage() {
	}

	/**
	 * 
	 * 获取本身实例对象
	 * <br/>
	 * <br/>
	 * 
	 * @return
	 */
	public static ActiveUserManage getInstance() {
		if (instance == null) {
			synchronized (ActiveUserManage.class) {
				if (instance == null) {
					instance = new ActiveUserManage();
				}
			}
		}
		return instance;
	}

	/**
	 * 
	 * 设置 AU 是否互踢
	 * <br/>
	 * <br/>
	 * 
	 * @param activeUserInitVO
	 *            AU
	 * @param logonTimeOutThreadVO
	 *            用户登录超时退出线程参数
	 */
	public void setLogonMode(LogonMode logonMode) {
		if (logonMode != null) {
			this.logonMode = logonMode;
		}
	}

	/**
	 * 
	 * 启动 AU 验证长时间不防问用户登录超时，退出的用户的线程
	 * <br/>
	 * <br/>
	 * 
	 * @param logonTimeOutThreadVO
	 */
	public void startLogonTimeOutThread(AULogonTimeOutThreadVO logonTimeOutThreadVO) {
		logonTimeOutThread = new LogonTimeOutThread(logonTimeOutThreadVO);
		logonTimeOutThread.start();
	}

	/**
	 * 
	 * 登录用户总 Map<br/>
	 * key 登录类型; value LogonTypeManageVO
	 * <br/>
	 * <br/>
	 * 
	 * @return
	 */
	public Map<String, AULogonTypeManageVO> getLogonTypeMap() {
		return logonTypeMap;
	}

	/**
	 * 
	 * 用户登录
	 * <br/>
	 * <br/>
	 * 
	 * @param logonVO
	 *            登录时，传入对象
	 * @return
	 */
	public AULogonResultVO logon(AULogonVO logonVO) {
		AULogonResultVO result = new AULogonResultVO();
		result.setResult(-1);

		if (logonVO == null || logonVO.getUserID() == null || logonVO.getLogonType() == null) {
			result.setRecode("-1001");
			result.setMessage("传入信息不完整");
			if (logonVO != null) {
				logger.info("用户[" + logonVO.getUserID() + "]在模块[" + logonVO.getModuleID() + "]以[" + logonVO.getLogonType() + "]类型登录信息不完整");
			} else {
				logger.info("用户登录，传入信息为空");
			}
			return result;
		}

		AULogonTypeManageVO logonTypeManageVO = getLogonTypeManageVO(logonVO.getLogonType());

		// 如果内存中已经有了本用户的登录用户，则清除本用户原有的登录
		List<AUUserManageVO> userManageList = logonTypeManageVO.getUserMap().get(logonVO.getUserID());

		if (userManageList == null) {
			userManageList = new ArrayList<AUUserManageVO>();
		}

		// 如果设置的不是多点登录
		if (logonMode != LogonMode.MULTI_MODE) {
			if (userManageList.size() > 0) {
				AUUserManageVO userManageVO = userManageList.get(0);
				AULogoffVO logoffVO = new AULogoffVO();
				logoffVO.setLogonType(logonVO.getLogonType());
				logoffVO.setSessionID(userManageVO.getSessionID());
				logoffVO.setUserID(userManageVO.getUserID());
				logoff(logoffVO);
				logger.info(
						"新用户[" + logonVO.getUserID() + "]以[" + logonVO.getLogonType() + "]类型登录，踢出原用户 SessionID[" + userManageVO.getSessionID() + "]");
			}
		}

		AUUserManageVO newUserManageVO = new AUUserManageVO();
		newUserManageVO.setLogonType(logonVO.getLogonType());
		if (logonVO.getModuleID() > 0) {
			newUserManageVO.getModuleIDList().add(logonVO.getModuleID());
		}
		newUserManageVO.setSessionID(ActiveUserUtil.createSessionID());
		newUserManageVO.setUserID(logonVO.getUserID());
		newUserManageVO.setLogonIp(logonVO.getLogonIp());
		newUserManageVO.setLogonTime(logonVO.getLogonTime());
		newUserManageVO.setLogonType(logonVO.getLogonType());
	    newUserManageVO.setLastLogonIp(logonVO.getLastLogonIp());
	    newUserManageVO.setLastLogonTime(logonVO.getLastLogonTime());
		userManageList.add(newUserManageVO);

		logonTypeManageVO.getUserMap().put(logonVO.getUserID(), userManageList);
		logonTypeManageVO.getSessionMap().put(newUserManageVO.getSessionID(), newUserManageVO);

		putLogonOrLogoffUserList(newUserManageVO, 1);

		logger.info("用户[" + logonVO.getUserID() + "]在模块[" + logonVO.getModuleID() + "]以[" + logonVO.getLogonType() + "]类型登录成功，返回 SessionID ["
				+ newUserManageVO.getSessionID() + "]");
		result.setResult(1);
		result.setUserManageVO(newUserManageVO);

		return result;
	}

	/**
	 * 
	 * 退出登录
	 * <br/>
	 * <br/>
	 * 
	 * @param logoffVO
	 * @return
	 */
	public AULogoffResultVO logoff(AULogoffVO logoffVO) {
		AULogoffResultVO result = new AULogoffResultVO();
		result.setResult(-1);

		if (logoffVO == null || logoffVO.getLogonType() == null || logoffVO.getUserID() == null) {
			result.setRecode("-1101");
			result.setMessage("传入信息不完整");
			if (logoffVO != null) {
				logger.info("用户[" + logoffVO.getUserID() + "]退出在[" + logoffVO.getLogonType() + "]类型登录");
			} else {
				logger.info("退出登录，传入信息为空");
			}
			return result;
		}

		AULogonTypeManageVO logonTypeManageVO = getLogonTypeManageVO(logoffVO.getLogonType());

		AUUserManageVO userManageVO = logonTypeManageVO.getSessionMap().get(logoffVO.getSessionID());
		if (userManageVO == null) {
			result.setResult(1);
			logger.info("用户[" + logoffVO.getUserID() + "]退出在[" + logoffVO.getLogonType() + "]类型登录，发现用户已退出");
			return result;
		}

		if (!userManageVO.getUserID().equals(logoffVO.getUserID())) {
			result.setRecode("-1102");
			result.setMessage("传入的 SessionID 与用户不符");
			logger.info("用户[" + logoffVO.getUserID() + "]退出在[" + logoffVO.getLogonType() + "]类型登录，AU SessionID 不符");
			return result;
		}

		logonTypeManageVO.getSessionMap().remove(userManageVO.getSessionID());
		List<AUUserManageVO> list = logonTypeManageVO.getUserMap().get(userManageVO.getUserID());
		if (list != null) {
			if (list.size() <= 1) {
				logonTypeManageVO.getUserMap().remove(userManageVO.getUserID());
			}
			list.remove(userManageVO);
		}

		putLogonOrLogoffUserList(userManageVO, 2);

		logger.info("用户[" + logoffVO.getUserID() + "]退出在[" + logoffVO.getLogonType() + "]类型登录的 SessionID[" + userManageVO.getSessionID() + "]成功");

		result.setResult(1);

		return result;
	}

	/**
	 * 
	 * 用户跳转
	 * <br/>
	 * <br/>
	 * 
	 * @param checkUserVO
	 * @return
	 */
	public AUCheckUserResultVO checkUser(AUCheckUserVO checkUserVO) {
		AUCheckUserResultVO result = new AUCheckUserResultVO();
		result.setResult(-1);

		if (checkUserVO == null || checkUserVO.getUserID() == null) {
			result.setRecode("-1201");
			result.setMessage("传入信息不完整");
			return result;
		}

		AULogonTypeManageVO logonTypeManageVO = getLogonTypeManageVO(checkUserVO.getLogonType());

		List<AUUserManageVO> userManageList = logonTypeManageVO.getUserMap().get(checkUserVO.getUserID());

		// 是否已经录入了用户信息，默认为未录入
		boolean initUserMap = false;

		if (userManageList == null) {// 如果还没有登录
			userManageList = new ArrayList<AUUserManageVO>();
		}

		if (userManageList.size() > 0) {// 现在有同用户登录
			if (logonMode == LogonMode.MULTI_MODE) {// 如果是不互踢
				for (AUUserManageVO userManageVO : userManageList) {
					if (userManageVO.getSessionID() == checkUserVO.getSessionID()) {
						initUserMap = true;// 如果设置成不互踢，只要其中有 Session 相同的用户，则认为已经登录过了
						userManageVO.setLastTime(System.currentTimeMillis());
						result.setUserManageVO(userManageVO);
					}
				}
			} else {// 如果设置成互踢模式
				AUUserManageVO userManageVO = userManageList.get(0);
				if (userManageVO.getSessionID() == checkUserVO.getSessionID()) {// 如果登录的是本个 SessionID则不需要重复登录
					List<Integer> moduleIDList = userManageVO.getModuleIDList();
					// 登录中是否已经存在了本模块
					boolean enableModule = false;
					for (Integer moduleID : moduleIDList) {
						if (moduleID.intValue() == checkUserVO.getToModuleID()) {
							enableModule = true;
							break;
						}
					}
					if (!enableModule) {
						moduleIDList.add(checkUserVO.getToModuleID());
					}
					userManageVO.setLastTime(System.currentTimeMillis());
					// 已经录入了信息
					initUserMap = true;
					result.setUserManageVO(userManageVO);
				} else {// 如果登录的不是本格 SessionID 则退出原登录
						// 如果内存中已经有了本用户的登录用户，则清除本用户原有的登录
					AULogoffVO logoffVO = new AULogoffVO();
					logoffVO.setLogonType(checkUserVO.getLogonType());
					logoffVO.setSessionID(userManageVO.getSessionID());
					logoffVO.setUserID(userManageVO.getUserID());
					logoff(logoffVO);
				}
			}
		}

		if (!initUserMap) {// 如果未录入用户信息
			AUUserManageVO newUserManageVO = new AUUserManageVO();
			newUserManageVO.setLogonType(checkUserVO.getLogonType());
			newUserManageVO.getModuleIDList().add(checkUserVO.getToModuleID());
			newUserManageVO.setSessionID(checkUserVO.getSessionID());
			newUserManageVO.setUserID(checkUserVO.getUserID());
			newUserManageVO.setLogonTime(checkUserVO.getLogonTime());
			newUserManageVO.setLogonIp(checkUserVO.getLogonIp());
			newUserManageVO.setLastLogonIp(checkUserVO.getLastLogonIp());
			newUserManageVO.setLastLogonTime(checkUserVO.getLastLogonTime());
			userManageList.add(newUserManageVO);

			logonTypeManageVO.getUserMap().put(newUserManageVO.getUserID(), userManageList);
			logonTypeManageVO.getSessionMap().put(newUserManageVO.getSessionID(), newUserManageVO);

			result.setUserManageVO(newUserManageVO);

			putLogonOrLogoffUserList(newUserManageVO, 1);
		}

		result.setResult(1);

		return result;
	}

	/**
	 * 
	 * 通过 SessionID 获取登录用户信息
	 * <br/>
	 * <br/>
	 * 
	 * @param auGetUserVO
	 * @return
	 */
	public AUGetUserResultVO getUserBySessionID(AUGetUserVO auGetUserVO) {
		AUGetUserResultVO result = new AUGetUserResultVO();
		result.setResult(-1);

		if (auGetUserVO == null || auGetUserVO.getLogonType() == null) {
			result.setRecode("-1301");
			result.setMessage("传入信息不完整");
			return result;
		}
		AULogonTypeManageVO logonTypeManageVO = getLogonTypeManageVO(auGetUserVO.getLogonType());

		AUUserManageVO userManageVO = logonTypeManageVO.getSessionMap().get(auGetUserVO.getSessionID());

		if (userManageVO == null) {// 如果没有查询到登录信息
			result.setRecode("-1302");
			result.setMessage("用户未登录");
			return result;
		}

		if (auGetUserVO.getModuleID() != null && auGetUserVO.getModuleID() >= 0) {
			List<Integer> moduleIDList = userManageVO.getModuleIDList();

			// 是否已经加载了本模块编号
			boolean enableModule = false;
			for (Integer moduleID : moduleIDList) {
				if (moduleID.intValue() == auGetUserVO.getModuleID().intValue()) {
					enableModule = true;
				}
			}
			if (!enableModule) {
				moduleIDList.add(auGetUserVO.getModuleID());
			}
		}
		// 设置用户最后访问时间
		userManageVO.setLastTime(System.currentTimeMillis());

		result.setUserManageVO(userManageVO);
		result.setResult(1);

		return result;
	}

	/**
	 * 
	 * 判断用户是否已经登录
	 * <br/>
	 * <br/>
	 * 
	 * @param isLogonVO
	 * @return
	 */
	public AUISLogonResultVO isLogon(AUISLogonVO isLogonVO) {
		AUISLogonResultVO result = new AUISLogonResultVO();
		result.setResult(-1);

		if (isLogonVO == null || isLogonVO.getLogonType() == null || isLogonVO.getUserID() == null) {
			result.setRecode("-1301");
			result.setMessage("传入信息不完整");
			return result;
		}

		AULogonTypeManageVO logonTypeManageVO = getLogonTypeManageVO(isLogonVO.getLogonType());

		List<AUUserManageVO> userManageList = logonTypeManageVO.getUserMap().get(isLogonVO.getUserID());
		if (userManageList == null || userManageList.size() <= 0) {
			result.setRecode("-1302");
			result.setMessage("用户未登录");
			return result;
		}

		AUUserManageVO userManageVO = logonTypeManageVO.getSessionMap().get(isLogonVO.getSessionID());

		if (userManageVO == null) {// 如果没有查询到登录信息
			if (logonMode == LogonMode.MULTI_MODE) {// 如果配置的是多用户登录
				result.setRecode("-1302");
				result.setMessage("用户未登录");
				return result;
			} else {// 如果配置的是单用户登录
				result.setRecode("-1303");
				result.setMessage("账户在其他地方登录");
				return result;
			}
		}

		if (isLogonVO.getModuleID() != null && isLogonVO.getModuleID() >= 0) {
			List<Integer> moduleIDList = userManageVO.getModuleIDList();

			// 是否已经加载了本模块编号
			boolean enableModule = false;
			for (Integer moduleID : moduleIDList) {
				if (moduleID.intValue() == isLogonVO.getModuleID().intValue()) {
					enableModule = true;
				}
			}
			if (!enableModule) {
				moduleIDList.add(isLogonVO.getModuleID());
			}
		}
	    System.out.println("====userManageVO===========" + 
	    	      userManageVO.getLastLogonTime());
		// 设置用户最后访问时间
		userManageVO.setLastTime(System.currentTimeMillis());

		result.setUserManageVO(userManageVO);
		result.setResult(1);

		return result;
	}

	/**
	 * 
	 * 强制退出用户
	 * <br/>
	 * <br/>
	 * 
	 * @param compulsoryLogoffVO
	 * @return
	 */
	public AUCompulsoryLogoffResultVO compulsoryLogoff(AUCompulsoryLogoffVO compulsoryLogoffVO) {
		AUCompulsoryLogoffResultVO result = new AUCompulsoryLogoffResultVO();
		result.setResult(-1);

		if (compulsoryLogoffVO == null || compulsoryLogoffVO.getLogonIP() == null || compulsoryLogoffVO.getOperator() == null
				|| compulsoryLogoffVO.getUserIDList() == null || compulsoryLogoffVO.getUserIDList().size() <= 0) {
			result.setRecode("-1401");
			result.setMessage("传入信息不完整");
			if (compulsoryLogoffVO != null) {
				logger.info("管理员[" + compulsoryLogoffVO.getOperator() + "]在[" + compulsoryLogoffVO.getLogonIP() + "]地址强制退出用户["
						+ compulsoryLogoffVO.getUserIDList() + "]信息不完整");
			} else {
				logger.info("强制退出用户登录，传入信息为空");
			}
			return result;
		}

		for (String userID : compulsoryLogoffVO.getUserIDList()) {
			// 遍历所有登录类型，强退本用户的登录
			for (String logonType : logonTypeMap.keySet()) {
				AULogonTypeManageVO logonTypeManageVO = logonTypeMap.get(logonType);

				List<AUUserManageVO> userManageList = logonTypeManageVO.getUserMap().get(userID);

				if (userManageList != null) {
					for (AUUserManageVO userManageVO : userManageList) {
						logonTypeManageVO.getSessionMap().remove(userManageVO.getSessionID());
						/*
						 * 因为强制退出用户时，后台已经知道了退出用户，所以就不需要再增加退出列表了
						 */
						// putLogonOrLogoffUserList(userManageVO,2);
					}
					logonTypeManageVO.getUserMap().remove(userID);

				}
			}

			logger.info("管理员[" + compulsoryLogoffVO.getOperator() + "]在[" + compulsoryLogoffVO.getLogonIP() + "]地址强制退出用户[" + userID + "]成功");
		}

		result.setResult(1);
		return result;
	}

	/**
	 * 
	 * 获取登录退出列表
	 * <br/>
	 * <br/>
	 * 
	 * @return
	 */
	public List<AULogonOrLogoffUserVO> getLogonOrLogoffUserList() {
		List<AULogonOrLogoffUserVO> result = new ArrayList<AULogonOrLogoffUserVO>();
		restartRMI = 2;
		synchronized (logonOrLogoffUserList) {
			for (AULogonOrLogoffUserVO e : logonOrLogoffUserList) {
				result.add(e);
			}
			logonOrLogoffUserList.clear();
		}
		return result;
	}

	/**
	 * 
	 * 获取当前系统中的所有登录信息，并删除记录的登录退出流水
	 * <br/>
	 * <br/>
	 * 
	 * @return List<AULogonOrLogoffUserVO>
	 */
	public List<AULogonOrLogoffUserVO> getAllLogonUserList() {
		List<AULogonOrLogoffUserVO> result = new ArrayList<AULogonOrLogoffUserVO>();
		restartRMI = 2;
		synchronized (logonOrLogoffUserList) {// 清空记录的登录退出列表
			logonOrLogoffUserList.clear();
		}

		AULogonOrLogoffUserVO e = null;
		for (String key : logonTypeMap.keySet()) {// 遍历所有类型登录方式的登录信息，将登录信息放到返回结果中
			synchronized (this.getClass()) {
				AULogonTypeManageVO auLogonTypeManageVO = logonTypeMap.get(key);
				Map<Long, AUUserManageVO> map = auLogonTypeManageVO.getSessionMap();
				for (AUUserManageVO auUserManageVO : map.values()) {
					e = new AULogonOrLogoffUserVO();
					e.setLogonOrlogOff(1);
					e.setUserManageVO(auUserManageVO);
					result.add(e);
				}
			}
		}

		return result;
	}

	/**
	 * 
	 * 只获取当前系统中的所有登录信息，不删除记录的登录退出流水
	 * <br/>
	 * <br/>
	 * 
	 * @return
	 */
	public List<AULogonOrLogoffUserVO> onlyGetAllLogonUserList() {
		List<AULogonOrLogoffUserVO> result = new ArrayList<AULogonOrLogoffUserVO>();

		AULogonOrLogoffUserVO e = null;
		for (String key : logonTypeMap.keySet()) {// 遍历所有类型登录方式的登录信息，将登录信息放到返回结果中
			synchronized (this.getClass()) {
				AULogonTypeManageVO auLogonTypeManageVO = logonTypeMap.get(key);
				Map<Long, AUUserManageVO> map = auLogonTypeManageVO.getSessionMap();
				for (AUUserManageVO auUserManageVO : map.values()) {
					e = new AULogonOrLogoffUserVO();
					e.setLogonOrlogOff(1);
					e.setUserManageVO(auUserManageVO);
					result.add(e);
				}
			}
		}

		return result;
	}

	/**
	 * 
	 * 是否重新启动的服务
	 * <br/>
	 * <br/>
	 * 
	 * @return int 1 是，2 否
	 */
	public int isRestartStartRMI() {
		return restartRMI;
	}

	/**
	 * 
	 * 向记录登录退出的队列中加入新登录或退出
	 * <br/>
	 * <br/>
	 * 
	 * @param userManageVO
	 *            用户信息
	 * @param logonOrLogoff
	 *            登录或退出 (1 登录，2 退出)
	 */
	private void putLogonOrLogoffUserList(AUUserManageVO userManageVO, int logonOrLogoff) {
		AULogonOrLogoffUserVO logonOrLogoffUserVO = new AULogonOrLogoffUserVO();
		logonOrLogoffUserVO.setUserManageVO(userManageVO);
		if (logonOrLogoff == 2) {
			logonOrLogoffUserVO.setLogonOrlogOff(2);
		} else {
			logonOrLogoffUserVO.setLogonOrlogOff(1);
		}
		synchronized (logonOrLogoffUserList) {
			if (logonOrLogoffUserList.size() > 1000) {
				logonOrLogoffUserList.clear();
			}
			logonOrLogoffUserList.add(logonOrLogoffUserVO);
		}
	}

	/**
	 * 
	 * 获取登录类型存储的登录信息对象
	 * <br/>
	 * <br/>
	 * 
	 * @param logonType
	 *            登录类型
	 * @return
	 */
	private AULogonTypeManageVO getLogonTypeManageVO(String logonType) {
		AULogonTypeManageVO logonTypeManageVO = logonTypeMap.get(logonType);
		if (logonTypeManageVO == null) {
			synchronized (this.getClass()) {
				// 如果内存中没有保存本类型的信息，则新建本类型的存储信息
				if (logonTypeMap.get(logonType) == null) {
					logonTypeManageVO = new AULogonTypeManageVO();
					logonTypeMap.put(logonType, logonTypeManageVO);
				} else {
					logonTypeManageVO = logonTypeMap.get(logonType);
				}
			}
		}
		return logonTypeManageVO;
	}
}
