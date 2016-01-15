package gnnt.MEBS.activeUser.operation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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

public class ActiveUserManage {
	private final transient Log logger = LogFactory.getLog(getClass());
	private static ActiveUserManage instance;
	private int restartRMI = 1;

	private Map<String, AULogonTypeManageVO> logonTypeMap = new HashMap();

	private List<AULogonOrLogoffUserVO> logonOrLogoffUserList = new ArrayList();
	private LogonTimeOutThread logonTimeOutThread;
	private LogonMode logonMode = LogonMode.SINGLE_MODE;

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

	public void setLogonMode(LogonMode logonMode) {
		if (logonMode != null)
			this.logonMode = logonMode;
	}

	public void startLogonTimeOutThread(AULogonTimeOutThreadVO logonTimeOutThreadVO) {
		this.logonTimeOutThread = new LogonTimeOutThread(logonTimeOutThreadVO);
		this.logonTimeOutThread.start();
	}

	public Map<String, AULogonTypeManageVO> getLogonTypeMap() {
		return this.logonTypeMap;
	}

	public AULogonResultVO logon(AULogonVO logonVO) {
		AULogonResultVO result = new AULogonResultVO();
		result.setResult(-1);

		if ((logonVO == null) || (logonVO.getUserID() == null) || (logonVO.getLogonType() == null)) {
			result.setRecode("-1001");
			result.setMessage("传入信息不完整");
			if (logonVO != null)
				this.logger.info("用户[" + logonVO.getUserID() + "]在模块[" + logonVO.getModuleID() + "]以[" + logonVO.getLogonType() + "]类型登录信息不完整");
			else {
				this.logger.info("用户登录，传入信息为空");
			}
			return result;
		}

		AULogonTypeManageVO logonTypeManageVO = getLogonTypeManageVO(logonVO.getLogonType());

		List userManageList = (List) logonTypeManageVO.getUserMap().get(logonVO.getUserID());

		if (userManageList == null) {
			userManageList = new ArrayList();
		}

		if ((this.logonMode != LogonMode.MULTI_MODE) && (userManageList.size() > 0)) {
			AUUserManageVO userManageVO = (AUUserManageVO) userManageList.get(0);
			AULogoffVO logoffVO = new AULogoffVO();
			logoffVO.setLogonType(logonVO.getLogonType());
			logoffVO.setSessionID(userManageVO.getSessionID());
			logoffVO.setUserID(userManageVO.getUserID());
			logoff(logoffVO);
			this.logger.info(
					"新用户[" + logonVO.getUserID() + "]以[" + logonVO.getLogonType() + "]类型登录，踢出原用户 SessionID[" + userManageVO.getSessionID() + "]");
		}

		AUUserManageVO newUserManageVO = new AUUserManageVO();
		newUserManageVO.setLogonType(logonVO.getLogonType());
		if (logonVO.getModuleID() > 0) {
			newUserManageVO.getModuleIDList().add(Integer.valueOf(logonVO.getModuleID()));
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
		logonTypeManageVO.getSessionMap().put(Long.valueOf(newUserManageVO.getSessionID()), newUserManageVO);

		putLogonOrLogoffUserList(newUserManageVO, 1);

		this.logger.info("用户[" + logonVO.getUserID() + logonVO.getLastLogonTime() + "]在模块[" + logonVO.getModuleID() + "]以[" + logonVO.getLogonType()
				+ "]类型登录成功，返回 SessionID [" + newUserManageVO.getSessionID() + "]");
		result.setResult(1);
		result.setUserManageVO(newUserManageVO);

		return result;
	}

	public AULogoffResultVO logoff(AULogoffVO logoffVO) {
		AULogoffResultVO result = new AULogoffResultVO();
		result.setResult(-1);

		if ((logoffVO == null) || (logoffVO.getLogonType() == null) || (logoffVO.getUserID() == null)) {
			result.setRecode("-1101");
			result.setMessage("传入信息不完整");
			if (logoffVO != null)
				this.logger.info("用户[" + logoffVO.getUserID() + "]退出在[" + logoffVO.getLogonType() + "]类型登录");
			else {
				this.logger.info("退出登录，传入信息为空");
			}
			return result;
		}

		AULogonTypeManageVO logonTypeManageVO = getLogonTypeManageVO(logoffVO.getLogonType());

		AUUserManageVO userManageVO = (AUUserManageVO) logonTypeManageVO.getSessionMap().get(Long.valueOf(logoffVO.getSessionID()));
		if (userManageVO == null) {
			result.setResult(1);
			this.logger.info("用户[" + logoffVO.getUserID() + "]退出在[" + logoffVO.getLogonType() + "]类型登录，发现用户已退出");
			return result;
		}

		if (!userManageVO.getUserID().equals(logoffVO.getUserID())) {
			result.setRecode("-1102");
			result.setMessage("传入的用户名与记录用户不符");
			this.logger.info("用户[" + logoffVO.getUserID() + "]退出在[" + logoffVO.getLogonType() + "]类型登录，AU SessionID 不符");
			return result;
		}

		logonTypeManageVO.getSessionMap().remove(Long.valueOf(userManageVO.getSessionID()));
		List list = (List) logonTypeManageVO.getUserMap().get(userManageVO.getUserID());
		if (list != null) {
			if (list.size() <= 1) {
				logonTypeManageVO.getUserMap().remove(userManageVO.getUserID());
			}
			list.remove(userManageVO);
		}

		putLogonOrLogoffUserList(userManageVO, 2);

		this.logger
				.info("用户[" + logoffVO.getUserID() + "]退出在[" + logoffVO.getLogonType() + "]类型登录的 SessionID[" + userManageVO.getSessionID() + "]成功");

		result.setResult(1);

		return result;
	}

	public AUCheckUserResultVO checkUser(AUCheckUserVO checkUserVO) {
		AUCheckUserResultVO result = new AUCheckUserResultVO();
		result.setResult(-1);

		if ((checkUserVO == null) || (checkUserVO.getUserID() == null)) {
			result.setRecode("-1201");
			result.setMessage("传入信息不完整");
			return result;
		}

		AULogonTypeManageVO logonTypeManageVO = getLogonTypeManageVO(checkUserVO.getLogonType());

		List userManageList = (List) logonTypeManageVO.getUserMap().get(checkUserVO.getUserID());

		boolean initUserMap = false;

		if (userManageList == null) {
			userManageList = new ArrayList();
		}

		if (userManageList.size() > 0) {
			if (this.logonMode == LogonMode.MULTI_MODE) {
				for (AUUserManageVO userManageVO : (List<AUUserManageVO>) userManageList)
					if (userManageVO.getSessionID() == checkUserVO.getSessionID()) {
						initUserMap = true;

						userManageVO.setLastTime(System.currentTimeMillis());
						result.setUserManageVO(userManageVO);
					}
			} else {
				AUUserManageVO userManageVO = (AUUserManageVO) userManageList.get(0);
				if (userManageVO.getSessionID() == checkUserVO.getSessionID()) {
					List moduleIDList = userManageVO.getModuleIDList();

					boolean enableModule = false;
					for (Integer moduleID : (List<Integer>) moduleIDList) {
						if (moduleID.intValue() == checkUserVO.getToModuleID()) {
							enableModule = true;
							break;
						}
					}
					if (!enableModule) {
						moduleIDList.add(Integer.valueOf(checkUserVO.getToModuleID()));
					}
					userManageVO.setLastTime(System.currentTimeMillis());

					initUserMap = true;
					result.setUserManageVO(userManageVO);
				} else {
					AULogoffVO logoffVO = new AULogoffVO();
					logoffVO.setLogonType(checkUserVO.getLogonType());
					logoffVO.setSessionID(userManageVO.getSessionID());
					logoffVO.setUserID(userManageVO.getUserID());
					logoff(logoffVO);
				}
			}
		}

		if (!initUserMap) {
			AUUserManageVO newUserManageVO = new AUUserManageVO();
			newUserManageVO.setLogonType(checkUserVO.getLogonType());
			newUserManageVO.getModuleIDList().add(Integer.valueOf(checkUserVO.getToModuleID()));
			newUserManageVO.setSessionID(checkUserVO.getSessionID());
			newUserManageVO.setUserID(checkUserVO.getUserID());
			newUserManageVO.setLogonTime(checkUserVO.getLogonTime());
			newUserManageVO.setLogonIp(checkUserVO.getLogonIp());
			newUserManageVO.setLastLogonIp(checkUserVO.getLastLogonIp());
			newUserManageVO.setLastLogonTime(checkUserVO.getLastLogonTime());
			userManageList.add(newUserManageVO);

			logonTypeManageVO.getUserMap().put(newUserManageVO.getUserID(), userManageList);
			logonTypeManageVO.getSessionMap().put(Long.valueOf(newUserManageVO.getSessionID()), newUserManageVO);

			result.setUserManageVO(newUserManageVO);

			putLogonOrLogoffUserList(newUserManageVO, 1);
		}

		result.setResult(1);

		return result;
	}

	public AUGetUserResultVO getUserBySessionID(AUGetUserVO auGetUserVO) {
		AUGetUserResultVO result = new AUGetUserResultVO();
		result.setResult(-1);

		if ((auGetUserVO == null) || (auGetUserVO.getLogonType() == null)) {
			result.setRecode("-1301");
			result.setMessage("传入信息不完整");
			return result;
		}
		AULogonTypeManageVO logonTypeManageVO = getLogonTypeManageVO(auGetUserVO.getLogonType());

		AUUserManageVO userManageVO = (AUUserManageVO) logonTypeManageVO.getSessionMap().get(Long.valueOf(auGetUserVO.getSessionID()));

		if (userManageVO == null) {
			result.setRecode("-1302");
			result.setMessage("用户未登录");
			return result;
		}

		if ((auGetUserVO.getModuleID() != null) && (auGetUserVO.getModuleID().intValue() >= 0)) {
			List moduleIDList = userManageVO.getModuleIDList();

			boolean enableModule = false;
			for (Integer moduleID : (List<Integer>) moduleIDList) {
				if (moduleID.intValue() == auGetUserVO.getModuleID().intValue()) {
					enableModule = true;
				}
			}
			if (!enableModule) {
				moduleIDList.add(auGetUserVO.getModuleID());
			}
		}

		userManageVO.setLastTime(System.currentTimeMillis());

		result.setUserManageVO(userManageVO);
		result.setResult(1);

		return result;
	}

	public AUISLogonResultVO isLogon(AUISLogonVO isLogonVO) {
		AUISLogonResultVO result = new AUISLogonResultVO();
		result.setResult(-1);

		if ((isLogonVO == null) || (isLogonVO.getLogonType() == null) || (isLogonVO.getUserID() == null)) {
			result.setRecode("-1301");
			result.setMessage("传入信息不完整");
			return result;
		}

		AULogonTypeManageVO logonTypeManageVO = getLogonTypeManageVO(isLogonVO.getLogonType());

		List userManageList = (List) logonTypeManageVO.getUserMap().get(isLogonVO.getUserID());
		if ((userManageList == null) || (userManageList.size() <= 0)) {
			result.setRecode("-1302");
			result.setMessage("用户未登录");
			return result;
		}

		AUUserManageVO userManageVO = (AUUserManageVO) logonTypeManageVO.getSessionMap().get(Long.valueOf(isLogonVO.getSessionID()));

		if (userManageVO == null) {
			if (this.logonMode == LogonMode.MULTI_MODE) {
				result.setRecode("-1302");
				result.setMessage("用户未登录");
				return result;
			}
			result.setRecode("-1303");
			result.setMessage("账户在其他地方登录");
			return result;
		}

		if ((isLogonVO.getModuleID() != null) && (isLogonVO.getModuleID().intValue() >= 0)) {
			List moduleIDList = userManageVO.getModuleIDList();

			boolean enableModule = false;
			for (Integer moduleID : (List<Integer>) moduleIDList) {
				if (moduleID.intValue() == isLogonVO.getModuleID().intValue()) {
					enableModule = true;
				}
			}
			if (!enableModule) {
				moduleIDList.add(isLogonVO.getModuleID());
			}
		}
		System.out.println("====userManageVO===========" + userManageVO.getLastLogonTime());

		userManageVO.setLastTime(System.currentTimeMillis());

		result.setUserManageVO(userManageVO);
		result.setResult(1);

		return result;
	}

	public AUCompulsoryLogoffResultVO compulsoryLogoff(AUCompulsoryLogoffVO compulsoryLogoffVO) {
		AUCompulsoryLogoffResultVO result = new AUCompulsoryLogoffResultVO();
		result.setResult(-1);

		if ((compulsoryLogoffVO == null) || (compulsoryLogoffVO.getLogonIP() == null) || (compulsoryLogoffVO.getOperator() == null)
				|| (compulsoryLogoffVO.getUserIDList() == null) || (compulsoryLogoffVO.getUserIDList().size() <= 0)) {
			result.setRecode("-1401");
			result.setMessage("传入信息不完整");
			if (compulsoryLogoffVO != null)
				this.logger.info("管理员[" + compulsoryLogoffVO.getOperator() + "]在[" + compulsoryLogoffVO.getLogonIP() + "]地址强制退出用户["
						+ compulsoryLogoffVO.getUserIDList() + "]信息不完整");
			else {
				this.logger.info("强制退出用户登录，传入信息为空");
			}
			return result;
		}

		for (String userID : compulsoryLogoffVO.getUserIDList()) {
			for (String logonType : this.logonTypeMap.keySet()) {
				AULogonTypeManageVO logonTypeManageVO = (AULogonTypeManageVO) this.logonTypeMap.get(logonType);

				List<AUUserManageVO> userManageList = logonTypeManageVO.getUserMap().get(userID);

				if (userManageList != null) {
					for (AUUserManageVO userManageVO : userManageList) {
						logonTypeManageVO.getSessionMap().remove(Long.valueOf(userManageVO.getSessionID()));
					}

					logonTypeManageVO.getUserMap().remove(userID);
				}

			}

			this.logger.info("管理员[" + compulsoryLogoffVO.getOperator() + "]在[" + compulsoryLogoffVO.getLogonIP() + "]地址强制退出用户[" + userID + "]成功");
		}

		result.setResult(1);
		return result;
	}

	public List<AULogonOrLogoffUserVO> getLogonOrLogoffUserList() {
		List result = new ArrayList();
		this.restartRMI = 2;
		synchronized (this.logonOrLogoffUserList) {
			for (AULogonOrLogoffUserVO e : this.logonOrLogoffUserList) {
				result.add(e);
			}
			this.logonOrLogoffUserList.clear();
		}
		return result;
	}

	public List<AULogonOrLogoffUserVO> getAllLogonUserList() {
		List result = new ArrayList();
		this.restartRMI = 2;
		synchronized (this.logonOrLogoffUserList) {
			this.logonOrLogoffUserList.clear();
		}

		AULogonOrLogoffUserVO e = null;
		for (String key : this.logonTypeMap.keySet()) {
			synchronized (getClass()) {
				AULogonTypeManageVO auLogonTypeManageVO = (AULogonTypeManageVO) this.logonTypeMap.get(key);
				Map map = auLogonTypeManageVO.getSessionMap();
				for (AUUserManageVO auUserManageVO : (Collection<AUUserManageVO>) map.values()) {
					e = new AULogonOrLogoffUserVO();
					e.setLogonOrlogOff(1);
					e.setUserManageVO(auUserManageVO);
					result.add(e);
				}
			}
		}

		return result;
	}

	public List<AULogonOrLogoffUserVO> onlyGetAllLogonUserList() {
		List result = new ArrayList();

		AULogonOrLogoffUserVO e = null;
		for (String key : this.logonTypeMap.keySet()) {
			synchronized (getClass()) {
				AULogonTypeManageVO auLogonTypeManageVO = (AULogonTypeManageVO) this.logonTypeMap.get(key);
				Map map = auLogonTypeManageVO.getSessionMap();
				for (AUUserManageVO auUserManageVO : (Collection<AUUserManageVO>) map.values()) {
					e = new AULogonOrLogoffUserVO();
					e.setLogonOrlogOff(1);
					e.setUserManageVO(auUserManageVO);
					result.add(e);
				}
			}
		}

		return result;
	}

	public int isRestartStartRMI() {
		return this.restartRMI;
	}

	private void putLogonOrLogoffUserList(AUUserManageVO userManageVO, int logonOrLogoff) {
		AULogonOrLogoffUserVO logonOrLogoffUserVO = new AULogonOrLogoffUserVO();
		logonOrLogoffUserVO.setUserManageVO(userManageVO);
		if (logonOrLogoff == 2)
			logonOrLogoffUserVO.setLogonOrlogOff(2);
		else {
			logonOrLogoffUserVO.setLogonOrlogOff(1);
		}
		synchronized (this.logonOrLogoffUserList) {
			if (this.logonOrLogoffUserList.size() > 1000) {
				this.logonOrLogoffUserList.clear();
			}
			this.logonOrLogoffUserList.add(logonOrLogoffUserVO);
		}
	}

	private AULogonTypeManageVO getLogonTypeManageVO(String logonType) {
		AULogonTypeManageVO logonTypeManageVO = (AULogonTypeManageVO) this.logonTypeMap.get(logonType);
		if (logonTypeManageVO == null) {
			synchronized (getClass()) {
				if (this.logonTypeMap.get(logonType) == null) {
					logonTypeManageVO = new AULogonTypeManageVO();
					this.logonTypeMap.put(logonType, logonTypeManageVO);
				} else {
					logonTypeManageVO = (AULogonTypeManageVO) this.logonTypeMap.get(logonType);
				}
			}
		}
		return logonTypeManageVO;
	}
}