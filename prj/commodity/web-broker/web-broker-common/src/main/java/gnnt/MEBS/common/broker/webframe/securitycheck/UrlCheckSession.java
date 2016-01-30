package gnnt.MEBS.common.broker.webframe.securitycheck;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gnnt.MEBS.common.broker.common.Page;
import gnnt.MEBS.common.broker.common.PageRequest;
import gnnt.MEBS.common.broker.model.Right;
import gnnt.MEBS.common.broker.model.StandardModel;
import gnnt.MEBS.common.broker.model.User;
import gnnt.MEBS.common.broker.service.StandardService;
import gnnt.MEBS.common.broker.statictools.ApplicationContextInit;

public class UrlCheckSession implements IUrlCheck {
	private final transient Log logger = LogFactory.getLog(UrlCheckSession.class);
	private static Map<String, Right> needlessCheckRightUrl;

	public UrlCheckResult check(String paramString, User paramUser) {
		if (paramUser == null)
			return UrlCheckResult.USERISNULL;
		if (needlessCheckRightUrl == null) {
			needlessCheckRightUrl = new HashMap();
			StandardService localObject = (StandardService) ApplicationContextInit.getBean("com_standardService");
			PageRequest localPageRequest = new PageRequest(" and type=-3 ");
			localPageRequest.setPageSize(100);
			Page localPage = ((StandardService) localObject).getPage(localPageRequest, new Right());
			if (localPage.getResult() != null) {
				Iterator localIterator = localPage.getResult().iterator();
				while (localIterator.hasNext()) {
					StandardModel localStandardModel = (StandardModel) localIterator.next();
					Right localRight = (Right) localStandardModel;
					if ((localRight.getUrl() != null) && (localRight.getUrl().trim().length() > 0)) {
						this.logger.debug("url:" + localRight.getUrl() + "       id: " + localRight.getId());
						needlessCheckRightUrl.put(localRight.getUrl(), localRight);
					}
				}
			}
		}
		Object localObject = (Right) needlessCheckRightUrl.get(paramString);
		if (localObject != null)
			return UrlCheckResult.NEEDLESSCHECKRIGHT;
		return UrlCheckResult.SUCCESS;
	}
}