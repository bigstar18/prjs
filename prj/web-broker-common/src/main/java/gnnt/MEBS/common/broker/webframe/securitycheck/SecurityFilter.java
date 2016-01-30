package gnnt.MEBS.common.broker.webframe.securitycheck;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ecside.util.RequestUtils;

import gnnt.MEBS.common.broker.common.ActiveUserManager;
import gnnt.MEBS.common.broker.common.Global;
import gnnt.MEBS.common.broker.model.User;
import gnnt.MEBS.common.broker.statictools.ApplicationContextInit;
import gnnt.MEBS.common.broker.statictools.Tools;
import gnnt.MEBS.logonService.vo.CheckUserResultVO;
import gnnt.MEBS.logonService.vo.CheckUserVO;
import gnnt.MEBS.logonService.vo.ISLogonResultVO;

public class SecurityFilter implements Filter {
	private static final long serialVersionUID = -1672503674085645491L;
	private static Object syncObject = new Object();

	public void destroy() {
	}

	public void doFilter(ServletRequest paramServletRequest, ServletResponse paramServletResponse, FilterChain paramFilterChain)
			throws IOException, ServletException {
		HttpServletRequest localHttpServletRequest = (HttpServletRequest) paramServletRequest;
		HttpServletResponse localHttpServletResponse = (HttpServletResponse) paramServletResponse;
		if (RequestUtils.isAJAXRequest(localHttpServletRequest)) {
			localHttpServletRequest.setCharacterEncoding("UTF-8");
			localHttpServletRequest.getParameter("");
		}
		if ((localHttpServletRequest.getHeader("X-Requested-With") != null)
				&& (localHttpServletRequest.getHeader("X-Requested-With").equalsIgnoreCase("XMLHttpRequest"))) {
			localHttpServletRequest.setCharacterEncoding("UTF-8");
			localHttpServletRequest.getParameter("");
		} else {
			localHttpServletRequest.setCharacterEncoding("GBK");
		}
		String str1 = localHttpServletRequest.getContextPath();
		Object localObject1 = localHttpServletRequest.getServletPath();
		if ((((String) localObject1).equals("/frameset")) || (((String) localObject1).equals("/output"))) {
			localObject1 = localHttpServletRequest.getParameter("__report");

		}
		String str2 = ApplicationContextInit.getConfig("LoginURL");
		String str3 = ApplicationContextInit.getConfig("NoRightURL");
		UrlCheck localUrlCheck = (UrlCheck) ApplicationContextInit.getBean("urlCheck");
		User localUser = (User) localHttpServletRequest.getSession().getAttribute("CurrentUser");
		Object localObject2;
		if ((localUser != null) && (localHttpServletRequest.getParameter("sessionID") != null)) {
			Long localLong = Long.valueOf(Tools.strToLong(localHttpServletRequest.getParameter("sessionID")));
			if (localUser.getType().equals("0")) {
				if (!localLong.equals(Long.valueOf(localUser.getSessionId()))) {
					synchronized (syncObject) {
						localObject2 = null;
						try {
							localObject2 = ActiveUserManager.isLogon(localUser.getUserId(), localLong.longValue(), Global.getSelfModuleID(),
									localUser.getLogonType());
						} catch (Exception localException1) {
							localException1.printStackTrace();
						}
						if ((localObject2 != null)
								&& (((ISLogonResultVO) localObject2).getUserManageVO().getUserID().equals(localUser.getBroker().getBrokerId()))) {
							localUser.setSessionId(localLong.longValue());
							localUser.getBroker().setSessionId(localLong.longValue());
						} else {
							localUser = null;
							localHttpServletRequest.getSession().invalidate();
						}
					}
				}
			} else if (!localLong.equals(Long.valueOf(localUser.getSessionId()))) {
				synchronized (syncObject) {
					localObject2 = null;
					try {
						localObject2 = ActiveUserManager.isLogon(localUser.getUserId(), localLong.longValue(), Global.getSelfModuleID(),
								localUser.getLogonType());
					} catch (Exception localException2) {
						localException2.printStackTrace();
					}
					if ((localObject2 != null)
							&& (((ISLogonResultVO) localObject2).getUserManageVO().getUserID().equals(localUser.getBrokerAge().getBrokerAgeId()))) {
						localUser.setSessionId(localLong.longValue());
						localUser.getBrokerAge().setSessionId(localLong.longValue());
					} else {
						localUser = null;
						localHttpServletRequest.getSession().invalidate();
					}
				}
			}
		}
		if (localUser == null) {
			synchronized (syncObject) {
				localUser = (User) localHttpServletRequest.getSession().getAttribute("CurrentUser");
				if (localUser == null) {

					Long sessionID = null;
					localObject2 = "";
					if (localHttpServletRequest.getParameter("sessionID") != null) {
						sessionID = Long.valueOf(Tools.strToLong(localHttpServletRequest.getParameter("sessionID")));
					}
					if (localHttpServletRequest.getParameter("CurrentUserType") != null) {
						localObject2 = localHttpServletRequest.getParameter("CurrentUserType");
					}
					String str4 = localHttpServletRequest.getParameter("LogonType");
					if ((str4 == null) || (str4.length() <= 0)) {
						str4 = Global.getSelfLogonType();
					}
					if (sessionID != null) {
						CheckUserVO localCheckUserVO = new CheckUserVO();
						localCheckUserVO.setSessionID(((Long) sessionID).longValue());
						localCheckUserVO.setUserID(localHttpServletRequest.getParameter("userID"));
						localCheckUserVO.setToModuleID(Global.getSelfModuleID());
						localCheckUserVO.setLogonType(str4);
						int i = Tools.strToInt(localHttpServletRequest.getParameter("FromModuleID"));
						String str5 = localHttpServletRequest.getParameter("FromLogonType");
						CheckUserResultVO localCheckUserResultVO = ActiveUserManager.checkUser(localCheckUserVO, i, str5);
						if (((String) localObject2).equals("0")) {
							if ((localCheckUserResultVO != null) && (localCheckUserResultVO.getResult() == 1)) {
								ActiveUserManager.brokerLogonSuccess(localCheckUserResultVO.getUserManageVO().getUserID(), localHttpServletRequest,
										localCheckUserResultVO.getUserManageVO().getSessionID(),
										localCheckUserResultVO.getUserManageVO().getLogonType());
								localUser = (User) localHttpServletRequest.getSession().getAttribute("CurrentUser");
							}
						} else if ((localCheckUserResultVO != null) && (localCheckUserResultVO.getResult() == 1)) {
							ActiveUserManager.brokerAgeLogonSuccess(localCheckUserResultVO.getUserManageVO().getUserID(), localHttpServletRequest,
									localCheckUserResultVO.getUserManageVO().getSessionID(), localCheckUserResultVO.getUserManageVO().getLogonType());
							localUser = (User) localHttpServletRequest.getSession().getAttribute("CurrentUser");
						}
					}
				}
			}
		}
		UrlCheckResult urlCheckResult = localUrlCheck.check((String) localObject1, localUser);
		switch (urlCheckResult) {
		case SUCCESS:
		case NEEDLESSCHECK:
			paramFilterChain.doFilter(paramServletRequest, paramServletResponse);
			break;
		case USERISNULL:
			localHttpServletResponse.sendRedirect(str1 + str2 + "?" + "reason" + "=" + urlCheckResult);
			break;
		case AUOVERTIME:
			localHttpServletResponse.sendRedirect(str1 + str2 + "?" + "reason" + "=" + urlCheckResult);
			break;
		case AUUSERKICK:
			localHttpServletResponse.sendRedirect(str1 + str2 + "?" + "reason" + "=" + urlCheckResult);
			break;
		case NOPURVIEW:
			localHttpServletRequest.getRequestDispatcher(str3).forward(paramServletRequest, paramServletResponse);
			break;
		case NEEDLESSCHECKRIGHT:
			paramFilterChain.doFilter(paramServletRequest, paramServletResponse);
			break;
		default:
			localHttpServletResponse.sendRedirect(str1 + str2 + "?" + "reason" + "=" + urlCheckResult);
		}
	}

	public void init(FilterConfig paramFilterConfig) throws ServletException {
	}
}
