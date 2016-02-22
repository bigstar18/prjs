<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="javax.naming.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="javax.sql.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>
<%@ page import="java.text.*"%>
<%@ page import="java.math.*"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/fmt' prefix='fmt' %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	//设置页面的强制刷新
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Cache-Control","no-store");
	response.setHeader("Pragma","no-cache");

%>
<%
	//基本url
	String rootPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
	String projectPath = request.getContextPath();
	String serverPath = rootPath+projectPath+"/common";
	String surfacePath = rootPath+projectPath+"/common/surface";
	String commonUserControllerPath =  rootPath+projectPath+"/common/commonUserController.cmn?funcflg=";
	String commonRightControllerPath = rootPath+projectPath+"/common/commonRightController.cmn?funcflg=";
	String commonRoleControllerPath = rootPath+projectPath+"/common/commonRoleController.cmn?funcflg=";
%>
<%@ include file="choSkin.jsp"%>
<%String publicPath = basePath + "/public"; %>
<c:set var="skinPath" value="<%=skinPath %>" />
<c:set var="picPath" value="<%=picPath %>" />
<c:set var="skinName" value="<%=skinName %>" />
<c:set var="menuPicPath" value="<%=menuPicPath %>" />
<c:set var="projectPath" value="<%=projectPath %>" />
<c:set var="default" value="default" />
<c:set var="gray" value="gray" />

<link rel="stylesheet" href="<%=skinPath%>/style.css" type="text/css"/>
<link rel="stylesheet" href="<%=skinPath%>/main.css" type="text/css"/>
<link rel="stylesheet" href="<%=skinPath%>/button.css" type="text/css"/>
<link rel="stylesheet" href="<%=skinPath%>/print.css" type="text/css"/>
<link rel="stylesheet" href="<%=skinPath%>/report.css" type="text/css"/>
<link rel="stylesheet" href="<%=skinPath%>/css.css" type="text/css"/>
<link rel="stylesheet" href="<%=skinPath%>/css02.css" type="text/css"/>
<link rel="stylesheet" href="<%=skinPath%>/mainstyle.css" type="text/css"/>
<link rel="stylesheet" type="text/css" media="all" href="<%=skinPath%>/xtree.css" />
<link rel="stylesheet" type="text/css" href="<%=skinPath%>/common1.css"/>

<script language="javascript" src="<%=serverPath %>/public/jslib/tools.js"></script>
<script language="javascript" src="<%=serverPath %>/public/jslib/common.js"></script>
<script language="javascript" src="<%=serverPath %>/public/jslib/frameCtrl.js"></script>
<script language="javascript" src="<%=serverPath %>/public/jslib/formInit.js"></script>
<script language="javascript" src="<%=serverPath %>/public/jslib/print.js"></script>

<script type="text/javascript"
	src="<%=publicPath%>/formInit.js"></script>
<c:choose>
	<c:when  test='${not empty resultMsg }'>
		<script type="text/javascript">
			alert('<c:out value="${resultMsg }"/>');
		</script>
	</c:when>
</c:choose>
<%@ include file="disableRightMenu.jsp"%>