
package gnnt.MEBS.activeUser.vo;

import java.io.Serializable;

/**
 * <P>类说明：登录或退出用户记录类
 * <br/>
 * <br/>
 * </p>
 * 修改记录:
 * <br/>
 * <ul>
 * 
 * <li> 创建类                    |2014-4-21下午06:19:53|金网安泰 </li>
 * 
 * </ul>
 * @author liuzx
 */
public class AULogonOrLogoffUserVO implements Serializable{
	/** 序列编号 */
	private static final long serialVersionUID = 5801424245105564486L;

	/**
	 * 登录或退出<br/>
	 * 1 登录;2 退出
	 */
	private int logonOrlogOff;

	/** 用户信息 */
	private AUUserManageVO userManageVO;

	/**
	 * 
	 * 登录或退出<br/>
	 * 1 登录;2 退出
	 * <br/><br/>
	 * @return
	 */
	public int getLogonOrlogOff() {
		return logonOrlogOff;
	}

	/**
	 * 
	 * 登录或退出<br/>
	 * 1 登录;2 退出
	 * <br/><br/>
	 * @param logonOrlogOff
	 */
	public void setLogonOrlogOff(int logonOrlogOff) {
		this.logonOrlogOff = logonOrlogOff;
	}

	/**
	 * 
	 * 用户信息
	 * <br/><br/>
	 * @return
	 */
	public AUUserManageVO getUserManageVO() {
		return userManageVO;
	}

	/**
	 * 
	 * 用户信息
	 * <br/><br/>
	 * @param userManageVO
	 */
	public void setUserManageVO(AUUserManageVO userManageVO) {
		this.userManageVO = userManageVO;
	}

}

