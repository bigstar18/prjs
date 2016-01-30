
package gnnt.MEBS.activeUser.vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <P>类说明：按登录类型区分的记录信息
 * <br/>
 * <br/>
 * </p>
 * 修改记录:
 * <br/>
 * <ul>
 * 
 * <li> 创建类                    |2014-4-21下午01:30:21|金网安泰 </li>
 * 
 * </ul>
 * @author liuzx
 */
public class AULogonTypeManageVO extends AUBaseVO{
	/** 序列编号 */
	private static final long serialVersionUID = 7813463771868808545L;

	/**
	 * 按 SessionID 保存的用户信息 Map<br/>
	 * key SessionID;value UserManageVO
	 */
	private Map<Long,AUUserManageVO> sessionMap = new HashMap<Long,AUUserManageVO>();

	/**
	 * 按 用户名 保存的用户信息 Map<br/>
	 * key userID;value UserManageVO
	 */
	private Map<String,List<AUUserManageVO>> userMap = new HashMap<String,List<AUUserManageVO>>();

	/**
	 * 
	 * 按 SessionID 保存的用户信息 Map<br/>
	 * key SessionID;value UserManageVO
	 * <br/><br/>
	 * @return
	 */
	public Map<Long, AUUserManageVO> getSessionMap() {
		return sessionMap;
	}

	/**
	 * 
	 * 按 SessionID 保存的用户信息 Map<br/>
	 * key SessionID;value UserManageVO
	 * <br/><br/>
	 * @param sessionMap
	 */
	public void setSessionMap(Map<Long, AUUserManageVO> sessionMap) {
		this.sessionMap = sessionMap;
	}

	/**
	 * 
	 * 按 用户名 保存的用户信息 Map<br/>
	 * key userID;value UserManageVO
	 * <br/><br/>
	 * @return
	 */
	public Map<String, List<AUUserManageVO>> getUserMap() {
		return userMap;
	}

	/**
	 * 
	 * 按 用户名 保存的用户信息 Map<br/>
	 * key userID;value UserManageVO
	 * <br/><br/>
	 * @param userMap
	 */
	public void setUserMap(Map<String, List<AUUserManageVO>> userMap) {
		this.userMap = userMap;
	}

}

