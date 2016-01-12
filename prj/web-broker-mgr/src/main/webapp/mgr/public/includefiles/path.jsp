<%@ page contentType="text/html;charset=GBK"%>
<%@page import="gnnt.MEBS.common.mgr.model.User"%>
<%@page import="gnnt.MEBS.common.mgr.common.Global"%>
<%@page import="gnnt.MEBS.common.mgr.statictools.ApplicationContextInit"%>
<%
	//记录的样式名称
	String skinName = null;
	User sessinUser = (User) request.getSession().getAttribute(
			Global.CURRENTUSERSTR);
	if (sessinUser == null) {//如果用户为空，则样式名称为默认样式名称
		skinName = "default";
	} else {//如果用户不为空，则样式名称为用户样式名称
		skinName = sessinUser.getSkin();
	}
	//市场名称：common_demo
	String path = request.getContextPath();
	//web url：http://127.0.0.1:8080/common_demo
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
	//mgr 路径：http://127.0.0.1:8080/common_demo/mgr
	String mgrPath=basePath+"/mgr";
	//public 路径：http://127.0.0.1:8080/common_demo/mgr/public
	String publicPath = mgrPath + "/public";
	//公共加载页面路径：http://127.0.0.1:8080/common_demo/mgr/public/includefiles
	String includePath = publicPath + "/includefiles";
	//当前样式路径：http://127.0.0.1:8080/common_demo/mgr/skinstyle/default
	String skinPath = mgrPath + "/skinstyle/" + skinName;
	//ecside 样式路径：http://127.0.0.1:8080/common_demo/mgr/skinstyle/default/ecside
	String escideskinPath =skinPath+ "/ecside";
	//框架路径：http://127.0.0.1:8080/common_demo/mgr/frame
	String framePath = basePath + "/mgr/frame";
	//按钮图片路径：http://127.0.0.1:8080/common_demo/mgr/skinstyle/default/image/frame/menu/
	String menuPicPath = skinPath + "/image/frame/menu/";
	/*用于 申请审核 包含页面include动态路径无法引用外页面的路径 */
	session.setAttribute("skinPath",skinPath);
%>
<c:set var="basePath" value="<%=basePath %>" />
<c:set var="mgrPath" value="<%=mgrPath %>" />
<c:set var="publicPath" value="<%=publicPath %>" />
<c:set var="includePath" value="<%=includePath %>" />
<c:set var="skinPath" value="<%=skinPath %>" />
<c:set var="escideskinPath" value="<%=escideskinPath %>" />
<c:set var="framePath" value="<%=framePath %>" />
<c:set var="menuPicPath" value="<%=menuPicPath %>" />
<c:set var="skinName" value="<%=skinName %>" />
<script>
//javascript 中皮肤路径
var jsSkinPath="../mgr/skinstyle/<%=skinName%>/";
var allJsSkinPath="<%=skinPath%>";
</script>