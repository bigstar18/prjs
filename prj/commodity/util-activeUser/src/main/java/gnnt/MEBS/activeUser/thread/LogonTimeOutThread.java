
package gnnt.MEBS.activeUser.thread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gnnt.MEBS.activeUser.operation.ActiveUserManage;
import gnnt.MEBS.activeUser.vo.AULogoffVO;
import gnnt.MEBS.activeUser.vo.AULogonTimeOutThreadVO;
import gnnt.MEBS.activeUser.vo.AULogonTypeManageVO;
import gnnt.MEBS.activeUser.vo.AUUserManageVO;

/**
 * <P>
 * 类说明：用户登录超时检查线程
 * <br/>
 * <br/>
 * </p>
 * 修改记录:
 * <br/>
 * <ul>
 * 
 * <li>创建类 |2014-4-21下午01:37:52|金网安泰</li>
 * 
 * </ul>
 * 
 * @author liuzx
 */
public class LogonTimeOutThread extends BaseThread {
	/**
	 * AU 超时时间，以毫秒为单位<br/>
	 * key 登录类型(web web服务登录,pc 电脑客户端登录,mobile 手机客户端登录);value 超时时间
	 */
	private Map<String, Long> auExpireTimeMap;

	/**
	 * 
	 * 构造方法
	 * <br/>
	 * <br/>
	 * 
	 * @param timeSpace
	 *            线程休眠时间，以 毫秒为单位
	 * @param auExpireTime
	 *            AU 登录超时时间，以分为单位
	 */
	public LogonTimeOutThread(AULogonTimeOutThreadVO logonTimeOutThreadVO) {
		this.timeSpace = logonTimeOutThreadVO.getTimeSpace();
		if (logonTimeOutThreadVO.getAuExpireTimeMap() != null) {
			this.auExpireTimeMap = logonTimeOutThreadVO.getAuExpireTimeMap();
		} else {
			this.auExpireTimeMap = new HashMap<String, Long>();
		}
		this.logger.debug("设置超时时间：");
		for (String key : this.auExpireTimeMap.keySet()) {
			this.logger.debug("[" + key + ":" + this.auExpireTimeMap.get(key) + "]");
		}
	}

	@Override
	public void run() {
		// 设置线程停止标识符为 false
		this.stop = false;
		while (!stop) {
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
					Thread.sleep(timeSpace);
				} catch (Exception e) {
					logger.debug("执行线程睡眠异常", e);
				}
			}
		}
	}

	/**
	 * 
	 * 执行退出用户总方法
	 * <br/>
	 * <br/>
	 */
	private void logoffTimeOutUser() {
		Map<String, AULogonTypeManageVO> map = ActiveUserManage.getInstance().getLogonTypeMap();

		for (String logonType : map.keySet()) {
			AULogonTypeManageVO logonTypeManageVO = map.get(logonType);

			try {
				long expireTime = getAUExpireTime(logonType);
				logoffLogonTypeMap(logonTypeManageVO, expireTime);
			} catch (Exception e) {
				logger.error("退出类型[" + logonType + "]的超时登录异常", e);
			}
		}
	}

	/**
	 * 
	 * 退出单个登录类型的超时登录
	 * <br/>
	 * <br/>
	 * 
	 * @param logonTypeManageVO
	 * @param expireTime
	 *            超时时间
	 */
	private void logoffLogonTypeMap(AULogonTypeManageVO logonTypeManageVO, long expireTime) {
		Map<Long, AUUserManageVO> map = logonTypeManageVO.getSessionMap();
		List<AUUserManageVO> logoutList = new ArrayList<AUUserManageVO>();
		for (Long key : map.keySet()) {
			AUUserManageVO userManageVO = map.get(key);
			if (System.currentTimeMillis() - userManageVO.getLastTime() > expireTime) {
				logoutList.add(userManageVO);
			}
		}
		for (AUUserManageVO userManageVO : logoutList) {
			try {
				logoffUser(userManageVO);
			} catch (Exception e) {
				logger.error(
						"登录超时，退出[" + userManageVO.getUserID() + "]在[" + userManageVO.getLogonType() + "]的登录[" + userManageVO.getSessionID() + "]异常",
						e);
			}
		}
	}

	/**
	 * 
	 * 退出单个登录用户
	 * <br/>
	 * <br/>
	 * 
	 * @param userManageVO
	 * @param expireTime
	 */
	private void logoffUser(AUUserManageVO userManageVO) {
		AULogoffVO logoffVO = new AULogoffVO();
		logoffVO.setLogonType(userManageVO.getLogonType());
		logoffVO.setSessionID(userManageVO.getSessionID());
		logoffVO.setUserID(userManageVO.getUserID());
		ActiveUserManage.getInstance().logoff(logoffVO);
	}

	/**
	 * 
	 * 获取相应类型登录的超时时间
	 * <br/>
	 * <br/>
	 * 
	 * @param logonType
	 * @return
	 */
	private long getAUExpireTime(String logonType) {
		long result = 120 * 60 * 1000;
		if (auExpireTimeMap != null) {
			if (auExpireTimeMap.get(logonType) != null) {
				result = auExpireTimeMap.get(logonType);
			}
		}
		return result;
	}
}
