package gnnt.MEBS.common.mgr.webframe.securitycheck;

import gnnt.MEBS.common.mgr.model.User;

/**
 * Url 检查用户session
 * 
 * @author xuejt
 * 
 */
public class UrlCheckSession implements IUrlCheck {

	public UrlCheckResult check(String url, User user) {
		if (user == null) {
			return UrlCheckResult.USERISNULL;
		}
		return UrlCheckResult.SUCCESS;
	}
}
