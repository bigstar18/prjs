package gnnt.MEBS.common.mgr.common;

import gnnt.MEBS.common.mgr.model.User;

import gnnt.MEBS.common.mgr.statictools.ApplicationContextInit;
import gnnt.MEBS.common.mgr.webframe.securitycheck.UrlCheck;
import gnnt.MEBS.common.mgr.webframe.securitycheck.UrlCheckResult;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 带权限按钮
 * 
 * @author xuejt
 * 
 */
public class RightButtonTag extends TagSupport {

	private static final long serialVersionUID = -3558314216634059969L;

	private transient final Log logger = LogFactory
			.getLog(RightButtonTag.class);

	private String className;

	private String onclick;

	private String id;

	private String name;

	private String action;

	/**
	 * 按钮名称
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 按钮名称
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 样式名称
	 * 
	 * @return
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * 样式名称
	 * 
	 * @param className
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * 点击事件
	 * 
	 * @return
	 */
	public String getOnclick() {
		return onclick;
	}

	/**
	 * 点击事件
	 * 
	 * @param onclick
	 */
	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	/**
	 * 按钮ID
	 */
	public String getId() {
		return id;
	}

	/**
	 * 按钮ID
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 按钮执行action
	 * 
	 * @return
	 */
	public String getAction() {
		return action;
	}

	/**
	 * 按钮执行action
	 * 
	 * @param action
	 */
	public void setAction(String action) {
		this.action = action;
	}

	// 通过判断权限决定是否显示按钮
	public int doEndTag() {
		try {
			HttpServletRequest request = (HttpServletRequest) pageContext
					.getRequest();

			// 从session中获取用户信息
			User user = (User) (request.getSession()
					.getAttribute(Global.CURRENTUSERSTR));

			UrlCheck urlCheck = (UrlCheck) ApplicationContextInit
					.getBean("urlCheck");

			// 检查url权限
			UrlCheckResult urlCheckResult = urlCheck.check(action, user);

			// 是否有权限
			boolean isRight = false;

			// 根据返回结果做处理
			switch (urlCheckResult) {
			case SUCCESS://成功 有权限
			case NEEDLESSCHECKRIGHT://不需要检查权限的URL
			case NEEDLESSCHECK://不需要检查的URL
				isRight = true;
				break;
			case USERISNULL://用户信息为空
			case AUOVERTIME://AU超时
			case AUUSERKICK://AU判断用户被踢
			case NOPURVIEW://用户没有权限
				isRight = false;
				break;
			default:
				isRight = false;
				break;
			}

			if (isRight) {
				JspWriter out = pageContext.getOut();
				out.println("<button class=\"" + className + "\" id=\"" + id
						+ "\" action=\"" + action + "\" onclick=\"" + onclick
						+ "\">" + name + "</button>");
			}
		} catch (IOException e) {
			logger.error("rightButton occor Exception ;exception info "
					+ e.getMessage());
		}

		return EVAL_PAGE;
	}
}
