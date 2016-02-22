<%@ page pageEncoding="GBK"%>
<%@page import="java.util.Map"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jstl/xml" prefix="x"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/tld/forDateFormat" prefix="datefn"%>
<%@ taglib uri="/tld/rightButton" prefix="rightButton"%>
<%@ include file="/common/public/choSkin.jsp"%>
<%@page import="gnnt.MEBS.config.constant.ActionConstant"%>


<link rel="stylesheet" href="<%=skinPath%>/css.css" type="text/css" />
<link rel="stylesheet" href="<%=skinPath%>/css02.css" type="text/css" />
<link rel="stylesheet" href="<%=skinPath%>/mainstyle.css" type="text/css" />

<%@ page isELIgnored="false"%>
<%
	String publicPath = basePath + "/public";
%>
<script language="javascript">
        var AUsessionId='${LOGINIDS}';
		var rightMap=${sessionScope.rightMap};
		var registerId='${sessionScope.REGISTERID}';
		var currenUserId='${sessionScope.CURRENUSERID}';
		var ORGANIZATIONID='${sessionScope.ORGANIZATIONID}';
</script>
<c:set var="GNNT_" value="<%=ActionConstant.GNNT_%>" />
<c:set var="ORIGINAL_" value="<%=ActionConstant.ORIGINAL_%>" />
<c:set var="ADMIN" value="<%=ActionConstant.ADMIN%>" />
<c:set var="DEFAULT_SUPER_ADMIN" value="<%=ActionConstant.DEF_SUPERADMIN%>" />
<c:set var="DEF_ADMIN" value="<%=ActionConstant.DEF_ADMIN%>" />

<!-- 获取返回信息 -->
<c:if test="${not empty resultMsg }">
	<script>
        alert('${resultMsg}');
    </script>
</c:if>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
<META HTTP-EQUIV="expires" CONTENT="0">
</head>
<%  
response.setHeader("Pragma","No-cache");    
response.setHeader("Cache-Control","no-cache");    
response.setDateHeader("Expires", -10);   
%>
<script language="javascript" src="<%=basePath%>/common/public/jslib/tools.js"></script>
<script language="javascript" src="<%=basePath%>/public/submitCount.js"></script>
<script type="text/javascript" src="<%=basePath%>/public/limit.js"
			defer="defer"></script>
<script type="text/javascript" src="<%=basePath%>/public/checkDate.js"
			defer="defer"></script>
<script type="text/javascript" src="<%=basePath%>/public/validator.js"></script>
<script type="text/javascript" src="<%=basePath%>/public/calendar/WdatePicker.js"
			defer="defer"></script>
<script type="text/javascript"
	src="<%=publicPath%>/formInit.js"></script>
<script type="text/javascript"
	src="<%=publicPath%>/common.js"></script>
<script type="text/javascript"
	src="<%=publicPath%>/main.js"></script>
<c:set var="path" value="<%=path%>" />
<c:set var="basePath" value="<%=basePath%>" />
<c:set var="publicPath" value="<%=publicPath%>" />
