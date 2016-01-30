package gnnt.MEBS.common.front.webFrame.securityCheck;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gnnt.MEBS.common.front.common.Page;
import gnnt.MEBS.common.front.common.PageRequest;
import gnnt.MEBS.common.front.model.StandardModel;
import gnnt.MEBS.common.front.model.front.Right;
import gnnt.MEBS.common.front.model.integrated.User;
import gnnt.MEBS.common.front.service.StandardService;
import gnnt.MEBS.common.front.statictools.ApplicationContextInit;

public class UrlCheckUserPurview implements IUrlCheck {
	private final transient Log logger = LogFactory.getLog(UrlCheckUserPurview.class);
	private static Map<String, Right> needlessCheckRightUrl;

	public UrlCheckResult check(String paramString, User paramUser) {
		Map localMap = paramUser.getRightMap();
		if (localMap == null) {
			return UrlCheckResult.NOPURVIEW;
		}
		Object localObject3;
		Object localObject4;
		if (needlessCheckRightUrl == null) {
			needlessCheckRightUrl = new HashMap();
			StandardService localObject1 = (StandardService) ApplicationContextInit.getBean("com_standardService");
			PageRequest localObject2 = new PageRequest(" and type=-3 ");
			((PageRequest) localObject2).setPageSize(100);
			localObject3 = ((StandardService) localObject1).getPage((PageRequest) localObject2, new Right());
			if (((Page) localObject3).getResult() != null) {
				localObject4 = ((Page) localObject3).getResult().iterator();
				while (((Iterator) localObject4).hasNext()) {
					StandardModel localStandardModel = (StandardModel) ((Iterator) localObject4).next();
					Right localRight = (Right) localStandardModel;
					if ((localRight.getUrl() != null) && (localRight.getUrl().trim().length() > 0)) {
						this.logger.debug("url:" + localRight.getUrl() + "       id: " + localRight.getId());
						needlessCheckRightUrl.put(localRight.getUrl(), localRight);
					}
				}
			}
		}
		Object localObject1 = (Right) needlessCheckRightUrl.get(paramString);
		if (localObject1 != null) {
			return UrlCheckResult.NEEDLESSCHECKRIGHT;
		}
		Object localObject2 = localMap.values().iterator();
		while (((Iterator) localObject2).hasNext()) {
			localObject3 = (Right) ((Iterator) localObject2).next();
			if ((((Right) localObject3).getUrl() != null) && (((Right) localObject3).getUrl().length() != 0)) {
				if (((Right) localObject3).getUrl().equals(paramString)) {
					return UrlCheckResult.SUCCESS;
				}
				if (((Right) localObject3).getUrl().endsWith("*")) {
					if (((Right) localObject3).getType().intValue() == 0) {
						if (((Right) localObject3).getVisiturl().contains(paramString)) {
							return UrlCheckResult.SUCCESS;
						}
					} else {
						localObject4 = ((Right) localObject3).getUrl().substring(0, ((Right) localObject3).getUrl().length() - 1);
						if (paramString.contains((CharSequence) localObject4)) {
							return UrlCheckResult.SUCCESS;
						}
					}
				}
			}
		}
		return UrlCheckResult.NOPURVIEW;
	}

	public static void main(String[] paramArrayOfString) {
		Pattern localPattern = Pattern.compile("/user/add.*");
		Matcher localMatcher = localPattern.matcher("/user/add.action");
		if (localMatcher.matches()) {
			System.out.println("匹配成功！");
		}
	}
}
