
package gnnt.MEBS.activeUser.vo;
/**
 * <P>类说明：用户登录验证返回结果
 * <br/>
 * <br/>
 * </p>
 * 修改记录:
 * <br/>
 * <ul>
 * 
 * <li> 创建类                    |2014-4-21下午03:41:33|金网安泰 </li>
 * 
 * </ul>
 * @author liuzx
 */
public class AUCheckUserResultVO extends AUResultVO{
	/** 序列编号 */
	private static final long serialVersionUID = 7926723787864184497L;

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

