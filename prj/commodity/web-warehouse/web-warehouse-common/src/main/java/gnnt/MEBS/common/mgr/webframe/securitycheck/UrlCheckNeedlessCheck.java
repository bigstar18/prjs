package gnnt.MEBS.common.mgr.webframe.securitycheck;

import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.model.Right;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.User;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.common.mgr.statictools.ApplicationContextInit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 不需要检查的URL权限判断类
 * 
 * @author Administrator
 * 
 */
public class UrlCheckNeedlessCheck implements IUrlCheck {

	private transient final Log logger = LogFactory
			.getLog(UrlCheckNeedlessCheck.class);

	// 关键字集合（以此集合中结尾的url不需要进行权限判断）
	// .css,.js,.jpg,.png,.htc,.ico,.bmp,.gif,.zip,.htm
	private List<String> noKeywordList = null;
	// 不要要权限检查目录集合（以此集合中开头的url不需要进行权限判断）
	// /front
	private List<String> needlessCheckDirList = null;

	/**
	 * 不需要检查的URL路径 从数据库中获取（c_right表中type==-2 的为不检查权限url）
	 */
	private static Map<String, Right> needlessCheckUrl;

	public UrlCheckResult check(String url, User user) {
		if (noKeywordList == null) {
			synchronized(this.getClass()){
				if (noKeywordList == null) {
					// 初始化集合
					noKeywordList = new ArrayList<String>();
					// 从配置文件中获取 NOKEYWORDS 以逗号分隔
					String noKeyword = ApplicationContextInit.getConfig("NoKeyWords");
					if (noKeyword != null && noKeyword.length() > 0) {
						// 将以逗号分隔的字符串转拆分为数组
						String[] noKeywords = noKeyword.split(",");
						// 将数组放到集合中
						for (int i = 0; i < noKeywords.length; i++) {
							noKeywordList.add(noKeywords[i]);
						}
					}
				}
			}
		}

		// 遍历集合 如果url以此集合中任何一个元素结尾则返回不需要进行权限检查
		for (String key : noKeywordList) {
			 //logger.debug("key:"+key);
			if (key!=null && url.endsWith(key)) {
				return UrlCheckResult.NEEDLESSCHECK;
			}
		}
		
		if (needlessCheckDirList == null) {
			// 初始化集合
			needlessCheckDirList = new ArrayList<String>();
			// 从配置文件中获取 NOKEYWORDS 以逗号分隔
			String needlessCheckDir = ApplicationContextInit.getConfig("NeedlessCheckDir");
			if (needlessCheckDir != null && needlessCheckDir.length() > 0) {
				// 将以逗号分隔的字符串转拆分为数组
				String[] needlessCheckDirs = needlessCheckDir.split(",");
				// 将数组放到集合中
				for (int i = 0; i < needlessCheckDirs.length; i++) {
					needlessCheckDirList.add(needlessCheckDirs[i]);
				}
			}
		}

		// 遍历集合 如果url以此集合中任何一个元素开头则返回不需要进行权限检查
		for (String key : needlessCheckDirList) {
			// logger.debug("key:"+key);
			if (url.startsWith(key)) {
				return UrlCheckResult.NEEDLESSCHECK;
			}
		}

		// 如果neddlessCheckUrl为空 初始化neddlessCheckUrl
		if (needlessCheckUrl == null) {
			needlessCheckUrl = new HashMap<String, Right>();
			// 获取标准service
			StandardService standardService = (StandardService) ApplicationContextInit
					.getBean("com_standardService");

			// 设置查询条件 c_right表中type==-2 的为不检查权限url
			PageRequest<String> pageRequest = new PageRequest<String>(
					" and type=-2 ");
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
						logger.debug("url:" + right.getUrl() + "       id: "
								+ right.getId());
						needlessCheckUrl.put(right.getUrl(), right);
					}
				}
			}
		}

		// 如果url地址在neddlessCheckUrl中则直接返回不需要权限判断
		Right right = needlessCheckUrl.get(url);
		if (right != null) {
			return UrlCheckResult.NEEDLESSCHECK;
		}

		return UrlCheckResult.SUCCESS;
	}


}
