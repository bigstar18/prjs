package gnnt.MEBS.common.mgr.webframe.securitycheck;

import gnnt.MEBS.common.mgr.model.User;

/**
 * 检查用户是否有url访问权限接口
 * 
 * @author Administrator
 * 
 */
public interface IUrlCheck {
	/**
	 * 检查用户是否有url访问权限
	 * @param url url 路径 
	 * @param user 用户
	 * @return 检查结果
	 * @see UrlCheckResult
	 */
	public UrlCheckResult check(String url, User user);
}
