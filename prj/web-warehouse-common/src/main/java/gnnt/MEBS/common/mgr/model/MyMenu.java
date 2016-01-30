package gnnt.MEBS.common.mgr.model;

import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;



/**
 * 菜单常用设置
 * @author Administrator
 *
 */
public class MyMenu extends StandardModel{

	private static final long serialVersionUID = 1L;

	/**关联用户*/
	@ClassDiscription(name="关联用户",description="")
	private User user;

	/**关联权限*/
	@ClassDiscription(name="关联权限",description="")
	private Right right;

	/**
	 * 与用户关联
	 * @return
	 */
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	/**
	 * 与权限表关联
	 * @return
	 */
	public Right getRight() {
		return right;
	}

	public void setRight(Right right) {
		this.right = right;
	}

	@Override
	public PrimaryInfo fetchPKey() {
		return null;
	}

	
}
