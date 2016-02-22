<%@ page pageEncoding="GBK"%>
<%@page import="java.util.Map"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jstl/xml" prefix="x"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/tld/forDateFormat" prefix="datefn"%>
<%@ include file="/common/public/choSkin.jsp"%>
<%@page import="gnnt.MEBS.config.constant.ActionConstant"%>
<link rel="stylesheet" href="<%=skinPath%>/button.css" type="text/css" />
<link rel="stylesheet" href="<%=skinPath%>/css.css" type="text/css" />
<link rel="stylesheet" href="<%=skinPath%>/css02.css" type="text/css" />
<link rel="stylesheet" href="<%=skinPath%>/mainstyle.css" type="text/css" />
<link rel="stylesheet" type="text/css" href="<%=escideskinPathForTimebargain%>/ecside_style.css"/>

<%@ page isELIgnored="false"%>
<%
	String publicPath = basePath + "/public";
%>
<c:set var="GNNT_" value="<%=ActionConstant.GNNT_%>" />
<c:set var="ORIGINAL_" value="<%=ActionConstant.ORIGINAL_%>" />

<!-- 获取返回信息 -->
<c:if test="${not empty resultMsg }">
	<script>
        alert('${resultMsg}');
    </script>
</c:if>
<script type="text/javascript" src="<%=basePath%>/public/checkDate.js"
			defer="defer"></script>
<script type="text/javascript" src="<%=basePath%>/public/calendar/WdatePicker.js"
			defer="defer"></script>
<script type="text/javascript"
	src="<%=publicPath%>/main.js"></script>
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
<c:set var="path" value="<%=path%>" />
<c:set var="basePath" value="<%=basePath%>" />
<c:set var="publicPath" value="<%=publicPath%>" />


<script type="text/javascript">
//禁止右键菜单
if (window.Event) 
  document.captureEvents(Event.MOUSEUP); 
function nocontextmenu() 
{
 event.cancelBubble = true
 event.returnValue = false;
 return false;
}
 
function norightclick(e) 
{
 if (window.Event) 
 {
  if (e.which == 2 || e.which == 3)
   return false;
 }
 else
  if (event.button == 2 || event.button == 3)
  {
   event.cancelBubble = true
   event.returnValue = false;
   return false;
  }
}
document.oncontextmenu = nocontextmenu;  // for IE5+
document.onmousedown = norightclick;  // for all others
</script>