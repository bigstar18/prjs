
package gnnt.MEBS.activeUser.vo;
/**
 * <P>类说明：执行登录方法返回对象
 * <br/>
 * <br/>
 * </p>
 * 修改记录:
 * <br/>
 * <ul>
 * 
 * <li> 创建类                    |2014-4-21下午02:16:12|金网安泰 </li>
 * 
 * </ul>
 * @author liuzx
 */
public class AULogonResultVO extends AUResultVO{
	/** 序列编号 */
	private static final long serialVersionUID = -4008331784951547600L;

	/** 登录用户信息 */
	private AUUserManageVO userManageVO;

	/**
	 * 
	 * 登录用户信息
	 * <br/><br/>
	 * @return
	 */
	public AUUserManageVO getUserManageVO() {
		return userManageVO;
	}

	/**
	 * 
	 * 登录用户信息
	 * <br/><br/>
	 * @param userManageVO
	 */
	public void setUserManageVO(AUUserManageVO userManageVO) {
		this.userManageVO = userManageVO;
	}
}