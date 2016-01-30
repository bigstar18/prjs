package gnnt.MEBS.common.mgr.webframe.securitycheck;

import gnnt.MEBS.common.mgr.model.User;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * 检查用户是否有url访问权限
 * 
 * @author xuejt
 * 
 */
public class UrlCheck {
	/**
	 * 检查用户是否有url访问权限实现类集合；循环集合判断用户是否有权限访问url
	 */
	private List<IUrlCheck> urlCheckList;

	/**
	 * 检查用户是否有url访问权限实现类集合；循环集合判断用户是否有权限访问url
	 */
	public List<IUrlCheck> getUrlCheckList() {
		return urlCheckList;
	}

	/**
	 * 检查用户是否有url访问权限实现类集合；循环集合判断用户是否有权限访问url
	 */
	public void setUrlCheckList(List<IUrlCheck> urlCheckList) {
		this.urlCheckList = urlCheckList;
	}

	/**
	 * 检查用户是否有url访问权限
	 * 
	 * @param url
	 *            url 路径
	 * @param user
	 *            用户
	 * @return 检查结果
	 * @see UrlCheckResult
	 */
	public UrlCheckResult check(String url, User user, HttpServletRequest request) {
		// 初始化返回值为成功
		UrlCheckResult urlCheckResult = UrlCheckResult.SUCCESS;
		// 检查集合不为空 循环判断权限；当检查到没有权限时跳出循环返回没有权限原因
		if (urlCheckList != null && urlCheckList.size() > 0) {
			for (IUrlCheck urlCheck : urlCheckList) {
				urlCheckResult = urlCheck.check(url, user,request);
				if (urlCheckResult != UrlCheckResult.SUCCESS) {
					break;
				}
			}
		}
		return urlCheckResult;
	}
}
