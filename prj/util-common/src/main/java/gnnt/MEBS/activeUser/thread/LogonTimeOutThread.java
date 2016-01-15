package gnnt.MEBS.activeUser.thread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import gnnt.MEBS.activeUser.operation.ActiveUserManage;
import gnnt.MEBS.activeUser.vo.AULogoffVO;
import gnnt.MEBS.activeUser.vo.AULogonTimeOutThreadVO;
import gnnt.MEBS.activeUser.vo.AULogonTypeManageVO;
import gnnt.MEBS.activeUser.vo.AUUserManageVO;

public class LogonTimeOutThread extends BaseThread {
	private Map<String, Long> auExpireTimeMap;

	public LogonTimeOutThread(AULogonTimeOutThreadVO logonTimeOutThreadVO) {
		this.timeSpace = logonTimeOutThreadVO.getTimeSpace();
		if (logonTimeOutThreadVO.getAuExpireTimeMap() != null)
			this.auExpireTimeMap = logonTimeOutThreadVO.getAuExpireTimeMap();
		else {
			this.auExpireTimeMap = new HashMap();
		}
		this.logger.debug("设置超时时间：");
		for (String key : this.auExpireTimeMap.keySet())
			this.logger.debug("[" + key + ":" + this.auExpireTimeMap.get(key) + "]");
	}

	public void run() {
		this.stop = false;
		while (!this.stop)
			try {
				logoffTimeOutUser();
			} catch (Exception e) {
				this.logger.error("执行用户登录超时退出线程异常", e);
				try {
					Thread.sleep(this.timeSpace);
				} catch (Exception e1) {
					this.logger.debug("执行线程睡眠异常", e1);
				}
			} finally {
				try {
					Thread.sleep(this.timeSpace);
				} catch (Exception e) {
					this.logger.debug("执行线程睡眠异常", e);
				}
			}
	}

	private void logoffTimeOutUser() {
		Map map = ActiveUserManage.getInstance().getLogonTypeMap();

		for (String logonType : (Set<String>) map.keySet()) {
			AULogonTypeManageVO logonTypeManageVO = (AULogonTypeManageVO) map.get(logonType);
			try {
				long expireTime = getAUExpireTime(logonType);
				logoffLogonTypeMap(logonTypeManageVO, expireTime);
			} catch (Exception e) {
				this.logger.error("退出类型[" + logonType + "]的超时登录异常", e);
			}
		}
	}

	private void logoffLogonTypeMap(AULogonTypeManageVO logonTypeManageVO, long expireTime) {
		Map map = logonTypeManageVO.getSessionMap();
		List logoutList = new ArrayList();
		for (Long key : (Set<Long>) map.keySet()) {
			AUUserManageVO userManageVO = (AUUserManageVO) map.get(key);
			if (System.currentTimeMillis() - userManageVO.getLastTime() > expireTime) {
				logoutList.add(userManageVO);
			}
		}
		for (AUUserManageVO userManageVO : (List<AUUserManageVO>) logoutList)
			try {
				logoffUser(userManageVO);
			} catch (Exception e) {
				this.logger.error(
						"登录超时，退出[" + userManageVO.getUserID() + "]在[" + userManageVO.getLogonType() + "]的登录[" + userManageVO.getSessionID() + "]异常",
						e);
			}
	}

	private void logoffUser(AUUserManageVO userManageVO) {
		AULogoffVO logoffVO = new AULogoffVO();
		logoffVO.setLogonType(userManageVO.getLogonType());
		logoffVO.setSessionID(userManageVO.getSessionID());
		logoffVO.setUserID(userManageVO.getUserID());
		ActiveUserManage.getInstance().logoff(logoffVO);
	}

	private long getAUExpireTime(String logonType) {
		long result = 7200000L;
		if ((this.auExpireTimeMap != null) && (this.auExpireTimeMap.get(logonType) != null)) {
			result = ((Long) this.auExpireTimeMap.get(logonType)).longValue();
		}

		return result;
	}
}