package gnnt.MEBS.common.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gnnt.MEBS.common.model.User;
import gnnt.MEBS.common.security.handler.SecurityHandler;
import gnnt.MEBS.common.util.SysData;

public class SecurityFilter implements Filter {
	private final transient Log logger = LogFactory.getLog(SecurityFilter.class);
	private FilterConfig filterConfig = null;
	private String login_url = null;
	private String noRight_url = null;
	private List<String> noKeywordList = null;
	private List<String> methodList = null;

	public void doFilter(ServletRequest paramServletRequest, ServletResponse paramServletResponse, FilterChain paramFilterChain)
			throws IOException, ServletException {
		String str1 = ((HttpServletRequest) paramServletRequest).getContextPath();
		String str2 = ((HttpServletRequest) paramServletRequest).getRequestURI();
		str2 = str2.replaceAll("/+", "/");
		Iterator localIterator = this.methodList.iterator();
		Object localObject2;
		while (localIterator.hasNext()) {
			String localObject1 = (String) localIterator.next();
			localObject2 = ((HttpServletRequest) paramServletRequest).getParameter((String) localObject1);
			if (localObject2 != null) {
				str2 = str2 + "?" + (String) localObject1 + "=" + (String) localObject2;
			}
		}
		str2 = str2.replaceFirst(str1, "");
		int i = 1;
		Object localObject1 = this.noKeywordList.iterator();
		while (((Iterator) localObject1).hasNext()) {
			localObject2 = (String) ((Iterator) localObject1).next();
			if ((str2 + ",").indexOf((String) localObject2 + ",") > 0) {
				i = 0;
			}
		}
		if (i != 0) {
			this.logger.debug(str2);
			localObject1 = AclCtrl.getLogonID(paramServletRequest);
			localObject2 = AclCtrl.getUser(paramServletRequest);
			SecurityHandler localSecurityHandler = (SecurityHandler) SysData.getBean("securityHandler");
			int j = localSecurityHandler.handleRequest(str2, (User) localObject2);
			this.logger.debug("result:" + j);
			if (j == -1) {
				((HttpServletRequest) paramServletRequest).getRequestDispatcher(this.noRight_url).forward(paramServletRequest, paramServletResponse);
			} else if (j == -99) {
				((HttpServletResponse) paramServletResponse).sendRedirect(str1 + this.login_url);
			} else if (j >= 0) {
				paramFilterChain.doFilter(paramServletRequest, paramServletResponse);
			}
		} else {
			paramFilterChain.doFilter(paramServletRequest, paramServletResponse);
		}
	}

	public void destroy() {
		this.filterConfig = null;
		this.login_url = null;
		this.noRight_url = null;
		this.methodList = null;
		this.noKeywordList = null;
	}

	public void init(FilterConfig paramFilterConfig) throws ServletException {
		this.filterConfig = paramFilterConfig;
		this.login_url = this.filterConfig.getInitParameter("LOGIN_URL");
		this.noRight_url = this.filterConfig.getInitParameter("NORIGHT_URL");
		String str1 = this.filterConfig.getInitParameter("NOKEYWORDS");
		String[] arrayOfString1 = str1.split(",");
		this.noKeywordList = new ArrayList();
		for (int i = 0; i < arrayOfString1.length; i++) {
			this.noKeywordList.add(arrayOfString1[i]);
		}
		String str2 = this.filterConfig.getInitParameter("METHODS");
		String[] arrayOfString2 = str2.split(",");
		this.methodList = new ArrayList();
		for (int j = 0; j < arrayOfString2.length; j++) {
			this.methodList.add(arrayOfString2[j]);
		}
	}
}
