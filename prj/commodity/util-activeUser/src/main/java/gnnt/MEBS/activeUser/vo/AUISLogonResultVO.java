
package gnnt.MEBS.activeUser.vo;
/**
 * <P>类说明：验证用户是否已经登录返回结果
 * <br/>
 * <br/>
 * </p>
 * 修改记录:
 * <br/>
 * <ul>
 * 
 * <li> 创建类                    |2014-4-21下午04:28:12|金网安泰 </li>
 * 
 * </ul>
 * @author liuzx
 */
public class AUISLogonResultVO extends AUResultVO{
	/** 序列编号 */
	private static final long serialVersionUID = -5637391744278487851L;

	/** 用户登录信息 */
	private AUUserManageVO userManageVO;

	/**
	 * 
	 * 用户登录信息
	 * <br/><br/>
	 * @return
	 */
	public AUUserManageVO getUserManageVO() {
		return userManageVO;
	}

	/**
	 * 
	 * 用户登录信息
	 * <br/><br/>
	 * @param userManageVO
	 */
	public void setUserManageVO(AUUserManageVO userManageVO) {
		this.userManageVO = userManageVO;
	}
}

