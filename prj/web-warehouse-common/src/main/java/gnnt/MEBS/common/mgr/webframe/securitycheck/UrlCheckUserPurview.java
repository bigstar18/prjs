package gnnt.MEBS.common.mgr.webframe.securitycheck;

import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.model.Right;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.User;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.common.mgr.statictools.ApplicationContextInit;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 检查用户是否拥有此URL的权限
 * 
 * @author xuejt
 * 
 */
public class UrlCheckUserPurview implements IUrlCheck {

	private transient final Log logger = LogFactory
			.getLog(UrlCheckUserPurview.class);

	/**
	 * 只检查session不检查权限的url（c_right表中type==-3 的为只检查session不检查权限的url）
	 */
	private static Map<String, Right> needlessCheckRightUrl;

	public UrlCheckResult check(String url, User user) {
		Map<Long, Right> userRight = user.getRightMap();
		// 如果用户没有任何权限则直接返回没有权限
		if (userRight == null) {
			return UrlCheckResult.NOPURVIEW;
		}

		// 如果neddlessCheckUrl为空 初始化neddlessCheckUrl
		if (needlessCheckRightUrl == null) {
			needlessCheckRightUrl = new HashMap<String, Right>();
			// 获取标准service
			StandardService standardService = (StandardService) ApplicationContextInit
					.getBean("com_standardService");

			// 设置查询条件 c_right表中type==-2 的为不检查权限url
			PageRequest<String> pageRequest = new PageRequest<String>(
					" and type=-3 ");
			// 设置页面大小为100 因为不判断权限的url很少很难超过100 所以设置为100 如果超过100修改此值
			pageRequest.setPageSize(100);
			// 查询
			Page<StandardModel> page = standardService.getPage(pageRequest,
					new Right());
			// 如果查询结果不为空 遍历查询结果放到neddlessCheckUrl中
			if (page.getResult() != null) {
				for (StandardModel model : page.getResult()) {
					Right right = (Right) model;
					if (right.getUrl() != null
							&& right.getUrl().trim().length() > 0) {
						logger.debug("url:" + right.getUrl()
								+ "       id: " + right.getId());
						needlessCheckRightUrl.put(right.getUrl(), right);
					}
				}
			}
		}

		// 如果url地址在neddlessCheckUrl中则直接返回不需要权限判断
		Right needlessRight = needlessCheckRightUrl.get(url);
		if (needlessRight != null) {
			return UrlCheckResult.NEEDLESSCHECKRIGHT;
		}

		for (Right right : userRight.values()) {
			// 如果权限路径为null或者长度等于0 结束本次循环；主菜单和菜单的url地址为空
			if (right.getUrl() == null || right.getUrl().length() == 0) {
				continue;
			}
			// 如果用户拥有此url权限返回成功
			if (right.getUrl().equals(url)) {
				return UrlCheckResult.SUCCESS;
			}
			// 用户以* 通配符结尾 则使用正则表达式判断
			else if (right.getUrl().endsWith("*")) {
				// 将* 用 .*替换 表示以right.getUrl()的*前的字符串开头即可
				/**
				 * 正则表达式效率低十倍以上 所以遗弃 Pattern pattern =
				 * Pattern.compile(right.getUrl().replaceAll( "\\*", ".*"));
				 * Matcher matcher = pattern.matcher(url); if (matcher.find()) {
				 * return UrlCheckResult.SUCCESS; }
				 **/

				// 如果是菜单类型 则匹配菜单路径和访问的url是否匹配
				// 如果菜单也按照通配符匹配则出现权限增加；如拥有菜单 user/* 权限 但是不拥有 user/add*
				// 那么user/add.action也将匹配菜单权限但是用户本不应该拥有add权限
				if (right.getType() == 0) {
					if (right.getVisiturl().contains(url)) {
						return UrlCheckResult.SUCCESS;
					}
				}
				// 将权限中的*去掉 如果url中包含权限则返回有权限
				else {
					String tempUrl = right.getUrl().substring(0,
							right.getUrl().length() - 1);
					if (url.contains(tempUrl)) {
						return UrlCheckResult.SUCCESS;
					}
				}
			}
		}

		return UrlCheckResult.NOPURVIEW;
	}

	public static void main(String[] args) {

		Pattern pattern = Pattern.compile("/user/add.*");
		Matcher matcher = pattern.matcher("/user/add.action");
		if (matcher.matches()) {
			System.out.println("匹配成功！");
		}
		// Pattern pattern = Pattern.compile("/user/*");
		// Matcher matcher = pattern.matcher("/user/add.action");
		// if (matcher.find()) {
		// System.out.println("匹配成功！");
		// }
	}
}
