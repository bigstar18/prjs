package gnnt.MEBS.common.mgr.webframe.securitycheck;

import gnnt.MEBS.common.mgr.common.ActiveUserManager;
import gnnt.MEBS.common.mgr.common.Global;
import gnnt.MEBS.common.mgr.model.User;
import gnnt.MEBS.common.mgr.statictools.ApplicationContextInit;
import gnnt.MEBS.common.mgr.statictools.Tools;
import gnnt.MEBS.logonService.vo.CheckUserResultVO;
import gnnt.MEBS.logonService.vo.CheckUserVO;
import gnnt.MEBS.logonService.vo.ISLogonResultVO;

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

/**
 * 初始化权限 获取登录用户信息
 * 
 * @author xuejt
 * 
 */
public class SecurityFilter implements Filter {

	private static final long serialVersionUID = -1672503674085645491L;

	/**
	 * 当session中没有user数据的时候需要通过Au判断登陆；由于Au判断登陆较慢所以需要加一个锁保证只调用一次Au
	 */
	private static Object syncObject = new Object();

	public void destroy() {
	}

	/**
	 * 过滤器判断当前请求是否有权限访问资源
	 */
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		if (RequestUtils.isAJAXRequest(request)) {
			request.setCharacterEncoding("UTF-8");
			// 设置编码后获取Parameter避免以后重新设定编码后获取Parameter乱码
			request.getParameter("");
		}

		// jquery ajax 请求头的X-Requested-With值为XMLHttpRequest
		if (request.getHeader("X-Requested-With") != null
				&& request.getHeader("X-Requested-With").equalsIgnoreCase(
						"XMLHttpRequest")) {
			request.setCharacterEncoding("UTF-8");
			// 设置编码后获取Parameter避免以后重新设定编码后获取Parameter乱码
			request.getParameter("");
		} else {
			request.setCharacterEncoding("GBK");
		}

		String contextPath = request.getContextPath();

		String url = request.getServletPath();

		// public static String sessionID = "";
		// if (!sessionID.equals(request.getSession().getId())) {
		// System.out.println("url="+url);
		// sessionID=request.getSession().getId();
		// System.out.println("sessionID=" +sessionID);
		// System.out.println("User="
		// + request.getSession().getAttribute(Global.CURRENTUSERSTR));
		// }

		// 以下代码专门为birt定制权限路径 说明 **** 如果路径为 /frameset 则为birt报表路径；判断url权限时判断报表路径权限
		if (url.equals("/frameset")  || url.equals("/output") || url.equals("/extract")) {
			String statisticsPath = request.getParameter("__report");
			url = statisticsPath;
		}

		/**
		 * 登录地址
		 */
		String loginURL = ApplicationContextInit.getConfig("LoginURL");
		/**
		 * 没有权限地址
		 */
		String noRightURL = ApplicationContextInit.getConfig("NoRightURL");

		// 从spring容器中获取url访问权限检查实例
		UrlCheck urlCheck = (UrlCheck) ApplicationContextInit
				.getBean("urlCheck");

		// 从session中获取用户信息
		User user = (User) (request.getSession()
				.getAttribute(Global.CURRENTUSERSTR));
		
		
		// 如果用户信息不为空
		if (user != null) {
			try {
				// 如果又传入了sessionid
				if (request.getParameter(Global.SESSIONID) != null
						&& request.getParameter(Global.SELFLOGONTYPE) != null
							) {
					Long sessionID = Tools.strToLong(request.getParameter(Global.SESSIONID));
					// 如果传进来的sessionID与当前session中用户的sessionID不同则通过传进来的sessionID从Au中获取对应的用户
					// 如果和当前用户相同则将传进来的sessionID替换掉当前用户的sessionID否则清空当前session中的用户信息重新加载
					if (!sessionID.equals(user.getSessionId())) {
						synchronized (syncObject) {
							
							ISLogonResultVO au =  ActiveUserManager.isLogon(user.getUserId(), sessionID, user.getModuleID(),user.getLogonType());

							if (au != null && au.getUserManageVO() != null
									&& au.getUserManageVO().getUserID().equals(user.getUserId())) {
								user.setSessionId(sessionID);
							} else {
								user = null;
								request.getSession().invalidate();
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

		if (user == null) {

			synchronized (syncObject) {
				// 从session中获取用户信息
				user = (User) (request.getSession()
						.getAttribute(Global.CURRENTUSERSTR));
				if (user == null) {
					// 从请求变量中获取sessionID
					Long sessionID = null;

					if (request.getParameter(Global.SESSIONID) != null) {
						sessionID = Tools.strToLong(request
								.getParameter(Global.SESSIONID));
					}
					String selflogontype = request.getParameter(Global.SELFLOGONTYPE);
					if(selflogontype == null || selflogontype.length()<=0){
						selflogontype = Global.getSelfLogonType();
					}
					int selfModuleID = Tools.strToInt(request.getParameter(Global.SELFMODULEID),Global.getSelfModuleID());
					// 如果sessionID不为空
					if (sessionID != null) {
						
						CheckUserVO checkUserVO = new CheckUserVO();
						checkUserVO.setSessionID(sessionID);
						checkUserVO.setUserID(request.getParameter(Global.USERID));
						checkUserVO.setToModuleID(selfModuleID);
						checkUserVO.setLogonType(selflogontype);
						
						//来源 RMI 编号
						int fromModuleID = Tools.strToInt(request.getParameter(Global.FROMMODULEID));
						//来源登录类型
						String  fromLogonType = request.getParameter(Global.FROMLOGONTYPE);
						/**
						 * 用户跳转
						 * <br/><br/>
						 * @param checkUserVO 登录 RMI 信息
						 * @param fromauID 来源 RMI 编号
						 * @param fromLogonType 来源登录类型 (web web服务登录、pc 电脑客户端登录、mobile 手机客户端登录)
						 * @return
						 */
						CheckUserResultVO au = ActiveUserManager.checkUser(checkUserVO, fromModuleID, fromLogonType);

						// 如果au不为空则说明sessionID合法 重新加载User信息并写入session
						if (au != null && au.getResult() == 1) {
							if(ActiveUserManager.logon(au.getUserManageVO().getUserID(), request, sessionID,selflogontype,selfModuleID)){
								user = (User) request.getSession().getAttribute(Global.CURRENTUSERSTR);
							}
						}

					}
				}
			}
		}

		// 检查url权限
		UrlCheckResult urlCheckResult = urlCheck.check(url, user,request);

		// 根据返回结果做处理
		switch (urlCheckResult) {
		case SUCCESS:
		case NEEDLESSCHECK:
			chain.doFilter(req, res);
			break;
		case USERISNULL:
			response.sendRedirect(contextPath + loginURL + "?"
					+ Global.TOLOGINURLREASON + "=" + urlCheckResult);
			break;
		case AUOVERTIME:
			response.sendRedirect(contextPath + loginURL + "?"
					+ Global.TOLOGINURLREASON + "=" + urlCheckResult);
			break;
		case AUUSERKICK:
			response.sendRedirect(contextPath + loginURL + "?"
					+ Global.TOLOGINURLREASON + "=" + urlCheckResult);
			break;
		case NOPURVIEW:
			request.getRequestDispatcher(noRightURL).forward(req, res);
			break;
		case NEEDLESSCHECKRIGHT:
			chain.doFilter(req, res);
			break;
		default:
			response.sendRedirect(contextPath + loginURL + "?"
					+ Global.TOLOGINURLREASON + "=" + urlCheckResult);
			break;
		}

	}

	public void init(FilterConfig filterConfig) throws ServletException {

	}
}
