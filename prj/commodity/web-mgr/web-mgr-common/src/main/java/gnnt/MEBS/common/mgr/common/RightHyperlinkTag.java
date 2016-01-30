
package gnnt.MEBS.common.mgr.common;

import gnnt.MEBS.common.mgr.model.User;
import gnnt.MEBS.common.mgr.statictools.ApplicationContextInit;
import gnnt.MEBS.common.mgr.webframe.securitycheck.UrlCheck;
import gnnt.MEBS.common.mgr.webframe.securitycheck.UrlCheckResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * <P>类说明：
 * <br/>
 * <br/>
 * </p>
 * 修改记录:
 * <br/>
 * <ul>
 * 
 * <li> 创建类                    |2013-10-24下午06:10:06|金网安泰 </li>
 * 
 * </ul>
 * @author liuzx
 */
public class RightHyperlinkTag extends TagSupport {
	/** 序列编号 */
	private static final long serialVersionUID = 8362880742737834205L;

	/** 标签 ID 号 */
	private String id;

	/** 标签名称 */
	private String name;

	/** 鼠标移动到标签上的显示文字 */
	private String title;

	/** 标签展示信息 */
	private String text;

	/** 标签样式 */
	private String className;

	/** 标签的样式属性 */
	private String style;

	/** 连接到的位置 */
	private String target;

	/**
	 * 如果没有权限是否展示标签上的文字<br/>
	 * 为空或为 true 展示，其余不展示
	 */
	private String display;

	/** 按钮点击事件 */
	private String onclick;

	/** 用于判断是否有权限的字段，如果有 onclick 事件，也可以做 onclick 事件的 action */
	private String action;

	/** 标签点击事件的连接 */
	private String href;

	/**
	 * 标签 ID 号
	 */
	public String getId() {
		return id;
	}

	/**
	 * 标签 ID 号
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 
	 * 标签名称
	 * <br/><br/>
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * 标签名称
	 * <br/><br/>
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * 鼠标移动到标签上的显示文字
	 * <br/><br/>
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 
	 * 鼠标移动到标签上的显示文字
	 * <br/><br/>
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 
	 * 标签展示信息
	 * <br/><br/>
	 * @return
	 */
	public String getText() {
		return text;
	}

	/**
	 * 
	 * 标签展示信息
	 * <br/><br/>
	 * @param text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * 
	 * 标签样式
	 * <br/><br/>
	 * @return
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * 
	 * 标签样式
	 * <br/><br/>
	 * @param className
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * 
	 * 标签的样式属性
	 * <br/><br/>
	 * @return
	 */
	public String getStyle() {
		return style;
	}

	/**
	 * 
	 * 标签的样式属性
	 * <br/><br/>
	 * @param style
	 */
	public void setStyle(String style) {
		this.style = style;
	}

	/**
	 * 
	 * 连接到的位置
	 * <br/><br/>
	 * @return
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * 
	 * 连接到的位置
	 * <br/><br/>
	 * @param target
	 */
	public void setTarget(String target) {
		this.target = target;
	}

	/**
	 * 
	 * 如果没有权限是否展示标签上的文字<br/>
	 * 为空或为 true 展示，其余不展示
	 * <br/><br/>
	 * @return
	 */
	public String getDisplay() {
		return display;
	}

	/**
	 * 
	 * 如果没有权限是否展示标签上的文字<br/>
	 * 为空或为 true 展示，其余不展示
	 * <br/><br/>
	 * @param display
	 */
	public void setDisplay(String display) {
		this.display = display;
	}

	/**
	 * 
	 * 按钮点击事
	 * <br/><br/>
	 * @return
	 */
	public String getOnclick() {
		return onclick;
	}

	/**
	 * 
	 * 按钮点击事
	 * <br/><br/>
	 * @param onclick
	 */
	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	/**
	 * 
	 * 用于判断是否有权限的字段，如果有 onclick 事件，也可以做 onclick 事件的 action
	 * <br/><br/>
	 * @return
	 */
	public String getAction() {
		return action;
	}

	/**
	 * 
	 * 用于判断是否有权限的字段，如果有 onclick 事件，也可以做 onclick 事件的 action
	 * <br/><br/>
	 * @param action
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * 
	 * 标签点击事件的连接
	 * <br/><br/>
	 * @return
	 */
	public String getHref() {
		return href;
	}

	/**
	 * 
	 * 标签点击事件的连接
	 * <br/><br/>
	 * @param href
	 */
	public void setHref(String href) {
		this.href = href;
	}

	//通过判断权限决定是否使用连接
	public int doEndTag() {
		try{
			HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
			// 从session中获取用户信息
			User user = (User) (request.getSession().getAttribute(Global.CURRENTUSERSTR));
			UrlCheck urlCheck = (UrlCheck) ApplicationContextInit.getBean("urlCheck");
			
			// 检查url权限
			UrlCheckResult urlCheckResult = UrlCheckResult.NOPURVIEW;
			if(href != null && href.trim().length()>0 && !href.trim().equals("#")){
				urlCheckResult = urlCheck.check(href, user, request);
			}else if(action != null && action.trim().length()>0){
				urlCheckResult = urlCheck.check(action, user, request);
			}

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

			if(isRight){
				JspWriter out = pageContext.getOut();
				out.println("<a "+
					((id==null || id.trim().length()<=0) ? "" : "id=\""+id.trim()+"\" ")+
					((name==null || name.trim().length()<=0) ? "" : "name=\""+name.trim()+"\" ")+
					((title==null || title.trim().length()<=0) ? "" : "title=\""+title.trim()+"\" ")+
					((className==null || className.trim().length()<=0) ? "" : "class=\""+className.trim()+"\" ")+
					((style==null || style.trim().length()<=0) ? "" : "style=\""+style.trim()+"\" ")+
					((target==null || target.trim().length()<=0) ? "" : "target=\""+target.trim()+"\" ")+
					((onclick==null || onclick.trim().length()<=0) ? "" : "onclick=\""+onclick.trim()+"\" ")+
					((action==null || action.trim().length()<=0) ? "" : "action=\""+action.trim()+"\" ")+
					((href==null || href.trim().length()<=0) ? "href=\"#\"" : "href=\""+href.trim()+"\" ")+
					">"+text+"</a>");
			}else if(display == null || display.trim().equalsIgnoreCase("true")){
				JspWriter out = pageContext.getOut();
				out.println(text);
			}
		}catch(Exception e){
		}
		return EVAL_PAGE;
	}
}

