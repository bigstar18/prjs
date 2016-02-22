<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.Map"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jstl/xml" prefix="x"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/tld/forDateFormat" prefix="datefn"%>
<%@ taglib uri="/tld/rightButton" prefix="rightButton"%>
<%@ include file="choSkin.jsp"%>
<%@page import="gnnt.MEBS.config.constant.ActionConstant"%>

<%
	//基本url
	String rootPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
	String projectPath = request.getContextPath();
	String serverPath = rootPath+projectPath+"/common";
	String surfacePath = rootPath+projectPath+"/common/surface";
%>
<%
	//设置页面的强制刷新
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Cache-Control","no-store");
	response.setHeader("Pragma","no-cache");

%>
<%String publicPath = basePath + "/public"; %>
<c:set var="skinPath" value="<%=skinPath %>" />
<c:set var="picPath" value="<%=picPath %>" />
<c:set var="skinName" value="<%=skinName %>" />
<c:set var="menuPicPath" value="<%=menuPicPath %>" />
<c:set var="projectPath" value="<%=projectPath %>" />
<c:set var="default" value="default" />

<!-- 获取返回信息 -->
<c:if test="${not empty resultMsg }">
	<script>
        alert('${resultMsg}');
    </script>
</c:if>

<link rel="stylesheet" href="<%=skinPath%>/css.css" type="text/css"/>
<link rel="stylesheet" href="<%=skinPath%>/css02.css" type="text/css"/>
<link rel="stylesheet" href="<%=skinPath%>/mainstyle.css" type="text/css"/>
<link rel="stylesheet" type="text/css" media="all" href="<%=skinPath%>/xtree.css" />
<link rel="stylesheet" type="text/css" href="<%=skinPath%>/common1.css"/>

