package gnnt.MEBS.common.front.webFrame.securityCheck;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gnnt.MEBS.common.front.common.ActiveUserManager;
import gnnt.MEBS.common.front.common.Global;
import gnnt.MEBS.common.front.model.integrated.User;
import gnnt.MEBS.common.front.statictools.ApplicationContextInit;
import gnnt.MEBS.common.front.statictools.Tools;
import gnnt.MEBS.logonService.vo.CheckUserResultVO;

public class SecurityFilter implements Filter {
	private static Object syncObject = new Object();

	public void destroy() {
	}

	public void doFilter(ServletRequest paramServletRequest, ServletResponse paramServletResponse, FilterChain paramFilterChain)
			throws IOException, ServletException {
		HttpServletRequest localHttpServletRequest = (HttpServletRequest) paramServletRequest;
		HttpServletResponse localHttpServletResponse = (HttpServletResponse) paramServletResponse;
		if ((localHttpServletRequest.getHeader("X-Requested-With") != null)
				&& (localHttpServletRequest.getHeader("X-Requested-With").equalsIgnoreCase("XMLHttpRequest"))) {
			localHttpServletRequest.setCharacterEncoding("UTF-8");
		} else {
			localHttpServletRequest.setCharacterEncoding("GBK");
		}
		localHttpServletRequest.getParameter("");
		String str1 = localHttpServletRequest.getContextPath();
		String str2 = localHttpServletRequest.getServletPath();
		String str3 = ApplicationContextInit.getConfig("LoginURL");
		String str4 = ApplicationContextInit.getConfig("NoRightURL");
		String str5 = ApplicationContextInit.getConfig("NoModuleRightURL");
		UrlCheck localUrlCheck = (UrlCheck) ApplicationContextInit.getBean("urlCheck");
		User localUser = (User) localHttpServletRequest.getSession().getAttribute("CurrentUser");
		if (localUser != null) {
			String str6 = localHttpServletRequest.getParameter("sessionID");
			if (str6 != null) {
				Long localLong = Long.valueOf(Tools.strToLong(str6));
				if (!localLong.equals(localUser.getSessionId())) {
					synchronized (syncObject) {
						localUser = (User) localHttpServletRequest.getSession().getAttribute("CurrentUser");
						if ((localUser != null) && (!localLong.equals(localUser.getSessionId()))) {
							localUser = null;
							localHttpServletRequest.getSession().invalidate();
						}
					}
				}
			}
		}
		if (localUser == null) {
			synchronized (syncObject) {
				long l = Tools.strToLong(localHttpServletRequest.getParameter("sessionID"), -1L);
				int i = Tools.strToInt(localHttpServletRequest.getParameter("FromModuleID"), -1);
				String str8 = localHttpServletRequest.getParameter("userID");
				if ((l > 0L) && (i > 0)) {
					String str9 = localHttpServletRequest.getParameter("FromLogonType");
					String str10 = localHttpServletRequest.getParameter("LogonType");
					if ((str10 == null) || (str10.trim().length() == 0)) {
						str10 = Global.getSelfLogonType();
					}
					int j = Tools.strToInt(localHttpServletRequest.getParameter("ModuleID"), Global.getSelfModuleID());
					CheckUserResultVO localCheckUserResultVO = ActiveUserManager.checkUser(str8, l, i, str10, str9, j);
					if (localCheckUserResultVO.getUserManageVO() != null) {
						boolean bool = ActiveUserManager.logon(localCheckUserResultVO.getUserManageVO().getUserID(), localHttpServletRequest,
								localCheckUserResultVO.getUserManageVO().getSessionID(), str10, j);
						if (bool) {
							localUser = (User) localHttpServletRequest.getSession().getAttribute("CurrentUser");
						}
					}
				}
			}
		}
		localHttpServletRequest.setAttribute("currenturl", str2);
		UrlCheckResult urlCheckResult = localUrlCheck.check(str2, localUser);
		String str7 = (String) localHttpServletRequest.getSession().getAttribute("currentRealPath");
		switch (urlCheckResult) {
		case SUCCESS:
		case NEEDLESSCHECK:
			if ((localUser == null) && (str7 != null)) {
				String errorMsg = localHttpServletRequest.getParameter("errorMsg");
				if ((errorMsg != null) && (!((String) errorMsg).equals(""))) {
					localHttpServletRequest.setAttribute("msg", errorMsg);
				}
			}
			paramFilterChain.doFilter(paramServletRequest, paramServletResponse);
			break;
		case USERISNULL:
			localHttpServletResponse.sendRedirect(str1 + str3 + "?" + "reason" + "=" + urlCheckResult + "&" + "preUrl" + "=" + str7);
			break;
		case AUOVERTIME:
			localHttpServletResponse.sendRedirect(str1 + str3 + "?" + "reason" + "=" + urlCheckResult + "&" + "preUrl" + "=" + str7);
			break;
		case AUUSERKICK:
			localHttpServletResponse.sendRedirect(str1 + str3 + "?" + "reason" + "=" + urlCheckResult + "&" + "preUrl" + "=" + str7);
			break;
		case NOPURVIEW:
			localHttpServletRequest.getRequestDispatcher(str4).forward(paramServletRequest, paramServletResponse);
			break;
		case NOMODULEPURVIEW:
			localHttpServletRequest.getRequestDispatcher(str5).forward(paramServletRequest, paramServletResponse);
			break;
		case NEEDLESSCHECKRIGHT:
			paramFilterChain.doFilter(paramServletRequest, paramServletResponse);
			break;
		default:
			localHttpServletResponse.sendRedirect(str1 + str3 + "?" + "reason" + "=" + urlCheckResult);
		}
	}

	public void init(FilterConfig paramFilterConfig) throws ServletException {
	}
}
