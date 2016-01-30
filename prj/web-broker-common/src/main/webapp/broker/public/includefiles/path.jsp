<%@ page contentType="text/html;charset=GBK"%>
<%@page import="gnnt.MEBS.common.broker.common.Global"%>
<%@page import="gnnt.MEBS.common.broker.statictools.ApplicationContextInit"%>
<%
	//记录样式名称为默认样式名称
	String skinName = "default";
	//市场名称：common_mgr
	String path = request.getContextPath();
	//服务路径 http://127.0.0.1:8080/
	String serverPath=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+"/";
	//web url：http://127.0.0.1:8080/common_mgr
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
	//broker 路径：http://127.0.0.1:8080/common_mgr/broker
	String mgrPath=basePath+"/broker";
	//public 路径：http://127.0.0.1:8080/common_mgr/broker/public
	String publicPath = mgrPath + "/public";
	//公共加载页面路径：http://127.0.0.1:8080/common_mgr/broker/public/includefiles
	String includePath = publicPath + "/includefiles";
	//当前样式路径：http://127.0.0.1:8080/common_mgr/broker/skinstyle/default
	String skinPath = mgrPath + "/skinstyle/" + skinName;
	//ecside 样式路径：http://127.0.0.1:8080/common_mgr/broker/skinstyle/default/ecside
	String escideskinPath =skinPath+ "/ecside";
	//框架路径：http://127.0.0.1:8080/common_mgr/broker/frame
	String framePath = basePath + "/broker/frame";
	//按钮图片路径：http://127.0.0.1:8080/common_mgr/broker/skinstyle/default/image/frame/menu/
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
var jsSkinPath="../broker/skinstyle/<%=skinName%>/";
var allJsSkinPath="<%=skinPath%>/";
</script>