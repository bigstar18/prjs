package gnnt.MEBS.common.broker.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import gnnt.MEBS.common.broker.model.User;
import gnnt.MEBS.common.broker.statictools.ApplicationContextInit;
import gnnt.MEBS.common.broker.webframe.securitycheck.UrlCheck;
import gnnt.MEBS.common.broker.webframe.securitycheck.UrlCheckResult;

public class RightHyperlinkTag extends TagSupport {
	private static final long serialVersionUID = 8362880742737834205L;
	private String id;
	private String name;
	private String title;
	private String text;
	private String className;
	private String style;
	private String target;
	private String display;
	private String onclick;
	private String action;
	private String href;

	public String getId() {
		return this.id;
	}

	public void setId(String paramString) {
		this.id = paramString;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String paramString) {
		this.name = paramString;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String paramString) {
		this.title = paramString;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String paramString) {
		this.text = paramString;
	}

	public String getClassName() {
		return this.className;
	}

	public void setClassName(String paramString) {
		this.className = paramString;
	}

	public String getStyle() {
		return this.style;
	}

	public void setStyle(String paramString) {
		this.style = paramString;
	}

	public String getTarget() {
		return this.target;
	}

	public void setTarget(String paramString) {
		this.target = paramString;
	}

	public String getDisplay() {
		return this.display;
	}

	public void setDisplay(String paramString) {
		this.display = paramString;
	}

	public String getOnclick() {
		return this.onclick;
	}

	public void setOnclick(String paramString) {
		this.onclick = paramString;
	}

	public String getAction() {
		return this.action;
	}

	public void setAction(String paramString) {
		this.action = paramString;
	}

	public String getHref() {
		return this.href;
	}

	public void setHref(String paramString) {
		this.href = paramString;
	}

	public int doEndTag() {
		try {
			HttpServletRequest localHttpServletRequest = (HttpServletRequest) this.pageContext.getRequest();
			User localUser = (User) localHttpServletRequest.getSession().getAttribute("CurrentUser");
			UrlCheck localUrlCheck = (UrlCheck) ApplicationContextInit.getBean("urlCheck");
			UrlCheckResult localUrlCheckResult = UrlCheckResult.NOPURVIEW;
			if ((this.href != null) && (this.href.trim().length() > 0) && (!this.href.trim().equals("#"))) {
				localUrlCheckResult = localUrlCheck.check(this.href, localUser);
			} else if ((this.action != null) && (this.action.trim().length() > 0)) {
				localUrlCheckResult = localUrlCheck.check(this.action, localUser);
			}
			int i = 0;
			switch (localUrlCheckResult) {
			case SUCCESS:
			case NEEDLESSCHECKRIGHT:
			case NEEDLESSCHECK:
				i = 1;
				break;
			case USERISNULL:
			case AUOVERTIME:
			case AUUSERKICK:
			case NOPURVIEW:
				i = 0;
				break;
			default:
				i = 0;
			}
			JspWriter localJspWriter;
			if (i != 0) {
				localJspWriter = this.pageContext.getOut();
				localJspWriter.println("<a "
						+ ((this.id == null) || (this.id.trim().length() <= 0) ? ""
								: new StringBuilder().append("id=\"").append(this.id.trim()).append("\" ").toString())
						+ ((this.name == null) || (this.name.trim().length() <= 0) ? ""
								: new StringBuilder().append("name=\"").append(this.name.trim()).append("\" ").toString())
						+ ((this.title == null) || (this.title.trim().length() <= 0) ? ""
								: new StringBuilder().append("title=\"").append(this.title.trim()).append("\" ").toString())
						+ ((this.className == null) || (this.className.trim().length() <= 0) ? ""
								: new StringBuilder().append("class=\"").append(this.className.trim()).append("\" ").toString())
						+ ((this.style == null) || (this.style.trim().length() <= 0) ? ""
								: new StringBuilder().append("style=\"").append(this.style.trim()).append("\" ").toString())
						+ ((this.target == null) || (this.target.trim().length() <= 0) ? ""
								: new StringBuilder().append("target=\"").append(this.target.trim()).append("\" ").toString())
						+ ((this.onclick == null) || (this.onclick.trim().length() <= 0) ? ""
								: new StringBuilder().append("onclick=\"").append(this.onclick.trim()).append("\" ").toString())
						+ ((this.action == null) || (this.action.trim().length() <= 0) ? ""
								: new StringBuilder().append("action=\"").append(this.action.trim()).append("\" ").toString())
						+ ((this.href == null) || (this.href.trim().length() <= 0) ? "href=\"#\""
								: new StringBuilder().append("href=\"").append(this.href.trim()).append("\" ").toString())
						+ ">" + this.text + "</a>");
			} else if ((this.display == null) || (this.display.trim().equalsIgnoreCase("true"))) {
				localJspWriter = this.pageContext.getOut();
				localJspWriter.println(this.text);
			}
		} catch (Exception localException) {
		}
		return 6;
	}
}
