
package gnnt.MEBS.activeUser.vo;


/**
 * <P>类说明：验证用户是否已经登录，传入 VO 对象
 * <br/>
 * <br/>
 * </p>
 * 修改记录:
 * <br/>
 * <ul>
 * 
 * <li> 创建类                    |2014-4-21下午04:27:59|金网安泰 </li>
 * 
 * </ul>
 * @author liuzx
 */
public class AUISLogonVO extends AUBaseVO{
	/** 序列编号 */
	private static final long serialVersionUID = -8519409235414142280L;

	/** AU SessionID */
	private long sessionID;

	/** 用户名 */
	private String userID;

	/**
	 * 模块编号<br/>
	 * 本模块编号只是用于记录，不做判断使用
	 */
	private Integer moduleID;

	/**
	 * 登录类型<br/>
	 * web web服务登录<br/>
	 * pc 电脑客户端登录<br/>
	 * mobile 手机客户端登录<br/>
	 */
	private String logonType;

	/**
	 * 
	 * AU SessionID
	 * <br/><br/>
	 * @return
	 */
	public long getSessionID() {
		return sessionID;
	}

	/**
	 * 
	 * AU SessionID
	 * <br/><br/>
	 * @param sessionID
	 */
	public void setSessionID(long sessionID) {
		this.sessionID = sessionID;
	}

	/**
	 * 
	 * 用户名
	 * <br/><br/>
	 * @return
	 */
	public String getUserID() {
		return userID;
	}

	/**
	 * 
	 * 用户名
	 * <br/><br/>
	 * @param userID
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}

	/**
	 * 
	 * 模块编号<br/>
	 * 本模块编号只是用于记录，不做判断使用
	 * <br/><br/>
	 * @return
	 */
	public Integer getModuleID() {
		return moduleID;
	}

	/**
	 * 
	 * 模块编号<br/>
	 * 本模块编号只是用于记录，不做判断使用
	 * <br/><br/>
	 * @param moduleID
	 */
	public void setModuleID(Integer moduleID) {
		this.moduleID = moduleID;
	}

	/**
	 * 
	 * 登录类型<br/>
	 * web web服务登录<br/>
	 * pc 电脑客户端登录<br/>
	 * mobile 手机客户端登录<br/>
	 * <br/><br/>
	 * @return
	 */
	public String getLogonType() {
		return logonType;
	}

	/**
	 * 
	 * 登录类型<br/>
	 * web web服务登录<br/>
	 * pc 电脑客户端登录<br/>
	 * mobile 手机客户端登录<br/>
	 * <br/><br/>
	 * @param logonType
	 */
	public void setLogonType(String logonType) {
		this.logonType = logonType;
	}
}

