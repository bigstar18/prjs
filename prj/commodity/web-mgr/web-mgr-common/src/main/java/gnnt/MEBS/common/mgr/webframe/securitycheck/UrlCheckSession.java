package gnnt.MEBS.common.mgr.webframe.securitycheck;

import javax.servlet.http.HttpServletRequest;

import gnnt.MEBS.common.mgr.model.User;

/**
 * Url 检查用户session
 * 
 * @author xuejt
 * 
 */
public class UrlCheckSession implements IUrlCheck {

	public UrlCheckResult check(String url, User user, HttpServletRequest request) {
		if (user == null) {
			return UrlCheckResult.USERISNULL;
		}
		return UrlCheckResult.SUCCESS;
	}
}
