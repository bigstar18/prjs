<%@ page contentType="text/html;charset=GBK"%>
<%@page import="gnnt.MEBS.common.front.model.integrated.User"%>
<%@page import="gnnt.MEBS.common.front.common.Global"%>
<%@page import="gnnt.MEBS.common.front.statictools.ActionUtil"%>
<%@page import="gnnt.MEBS.common.front.statictools.Tools" %>
<%@page import="gnnt.MEBS.common.front.statictools.ApplicationContextInit" %>
<%
/*skinstyle*/
String skinName = null;
User user = (User) request.getSession().getAttribute(Global.CURRENTUSERSTR);
if (user == null) {
	skinName = "default";
} else {
	skinName = user.getSkin();
}
//js使用的皮肤路径(/front/skinstyle/default)
String jsSkinPath = "/front/skinstyle/" + skinName;
//web服务接口(http://localhost:8080)
String serverInterface = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
//web服务名(/RealMardridFront)
String serverName = request.getContextPath();
//web服务根路径(http://localhost:8080/RealMardridFront)
String basePath = serverInterface + serverName;
//当前访问相对路径(/RealMardridFront/front/index.jsp)
StringBuffer currenturl = new StringBuffer((String)request.getAttribute("currenturl"));
//当前访问绝对路径(http://localhost:8080/RealMardridFront/front/index.jsp)
String currentPath = basePath + currenturl.toString();
//判断当前路径是否包含get传参
if(request.getQueryString()!=null){
	currenturl.append("?");
	currenturl.append(request.getQueryString());
}
//包含get参数的当前访问绝对路径(http://localhost:8080/RealMardridFront/front/index.jsp?a='a')
String currentRealPath = basePath + currenturl.toString();
currentRealPath = new String(currentRealPath.getBytes("iso-8859-1"),"GBK");
//前台路径绝对位置(http://localhost:8080/RealMardridFront/front)
   String frontPath=basePath+"/front";
//公用信息绝对位置(http://localhost:8080/RealMardridFront/front/public)
String publicPath = frontPath + "/public";
//公共加载信息绝对位置(http://localhost:8080/RealMardridFront/front/public/includefiles)
String includePath = publicPath + "/includefiles";
//皮肤绝对路径(http://localhost:8080/RealMardridFront/front/skinstyle/default)
String skinPath = basePath+jsSkinPath;
//框架路径(http://localhost:8080/RealMardridFront/front/frame)
String framePath = frontPath + "/frame";
//除css以外的页面使用的一些图片等的存放位置(http://localhost:8080/RealMardridFront/front/skinstyle/default/image/frame/menu/)
String menuPicPath = skinPath + "/image/frame/menu/";
/*用于 申请审核 包含页面include动态路径无法引用外页面的路径 */
session.setAttribute("jsSkinPath",jsSkinPath);//js使用的皮肤路径
session.setAttribute("serverName",serverName);//js使用的项目名称
session.setAttribute("serverPath",serverInterface);
session.setAttribute("basePath",basePath);//web服务根路径
session.setAttribute("currentPath",currentPath);//当前访问相对路径
session.setAttribute("currentRealPath",currentRealPath);//包含get参数的当前访问绝对路径
session.setAttribute("frontPath",frontPath);//前台路径绝对位置
session.setAttribute("publicPath",publicPath);//公用信息绝对位置
session.setAttribute("includePath",includePath);//公共加载信息绝对位置
session.setAttribute("skinPath",skinPath);//皮肤绝对路径
session.setAttribute("framePath",framePath);//框架路径
session.setAttribute("menuPicPath",menuPicPath);//除css以外的页面使用的一些图片等的存放位置
session.setAttribute("today",Tools.fmtDate(new java.util.Date()));
%>
<c:set var="GNNT_" value='<%=ActionUtil.PARAMETERPREFIX%>' />
<script>
	//js用到的样式路径
	var jsSkinPath="<%=skinPath%>/";
</script>
