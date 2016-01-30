// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi

package gnnt.MEBS.common.front.webFrame.securityCheck;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gnnt.MEBS.common.front.common.Page;
import gnnt.MEBS.common.front.common.PageRequest;
import gnnt.MEBS.common.front.model.StandardModel;
import gnnt.MEBS.common.front.model.front.Right;
import gnnt.MEBS.common.front.model.integrated.User;
import gnnt.MEBS.common.front.service.StandardService;
import gnnt.MEBS.common.front.statictools.ApplicationContextInit;

// Referenced classes of package gnnt.MEBS.common.front.webFrame.securityCheck:
// IUrlCheck, UrlCheckResult

public class UrlCheckNeedlessCheck implements IUrlCheck {

	private final transient Log logger = LogFactory.getLog(UrlCheckNeedlessCheck.class);
	private List noKeywordList;
	private List needlessCheckDirList;
	private static Map needlessCheckUrl;

	public UrlCheckNeedlessCheck() {
		noKeywordList = null;
		needlessCheckDirList = null;
	}

	public UrlCheckResult check(String s, User user) {
		if (noKeywordList == null)
			synchronized (getClass()) {
				if (noKeywordList == null) {
					noKeywordList = new ArrayList();
					String s2 = ApplicationContextInit.getConfig("NoKeyWords");
					if (s2 != null && s2.length() > 0) {
						String as1[] = s2.split(",");
						for (int j = 0; j < as1.length; j++)
							if (as1[j] != null && as1[j].trim() != null && as1[j].trim().length() > 0)
								noKeywordList.add(as1[j]);

					}
				}
			}
		for (Iterator iterator = noKeywordList.iterator(); iterator.hasNext();) {
			String s3 = (String) iterator.next();
			if (s.endsWith(s3))
				return UrlCheckResult.NEEDLESSCHECK;
		}

		if (needlessCheckDirList == null) {
			needlessCheckDirList = new ArrayList();
			String s1 = ApplicationContextInit.getConfig("NeedlessCheckDir");
			if (s1 != null && s1.length() > 0) {
				String as[] = s1.split(",");
				for (int i = 0; i < as.length; i++)
					needlessCheckDirList.add(as[i]);

			}
		}
		for (Iterator iterator1 = needlessCheckDirList.iterator(); iterator1.hasNext();) {
			String s4 = (String) iterator1.next();
			if (s.startsWith(s4))
				return UrlCheckResult.NEEDLESSCHECK;
		}

		if (needlessCheckUrl == null) {
			needlessCheckUrl = new HashMap();
			StandardService standardservice = (StandardService) ApplicationContextInit.getBean("com_standardService");
			PageRequest pagerequest = new PageRequest(" and type=-2 ");
			pagerequest.setPageSize(100);
			Page page = standardservice.getPage(pagerequest, new Right());
			if (page.getResult() != null) {
				Iterator iterator2 = page.getResult().iterator();
				do {
					if (!iterator2.hasNext())
						break;
					StandardModel standardmodel = (StandardModel) iterator2.next();
					Right right1 = (Right) standardmodel;
					if (right1.getUrl() != null && right1.getUrl().trim().length() > 0) {
						logger.debug(
								(new StringBuilder()).append("url:").append(right1.getUrl()).append("       id: ").append(right1.getId()).toString());
						needlessCheckUrl.put(right1.getUrl(), right1);
					}
				} while (true);
			}
		}
		Right right = (Right) needlessCheckUrl.get(s);
		if (right != null)
			return UrlCheckResult.NEEDLESSCHECK;
		else
			return UrlCheckResult.SUCCESS;
	}
}
