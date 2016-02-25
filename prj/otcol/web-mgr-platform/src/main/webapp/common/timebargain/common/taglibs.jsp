<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean-el" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html-el" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-logic-el" prefix="logic-el" %>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.ecside.org" prefix="ec" %>

<%@ include file="../../public/choSkin.jsp"%>
<link rel="stylesheet" href="<%=skinPathForTimebargain%>/common.css" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=escideskinPathForTimebargain%>/ecside_style.css"/>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/common";
pageContext.setAttribute("basePath", basePath);
%>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<META HTTP-EQUIV="pragma" CONTENT="no-cache">
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
	<META HTTP-EQUIV="expires" CONTENT="0">
	<script language="javascript" src="<%=basePath%>/timebargain/scripts/global.js"></script>
	<script language="javascript" src="<%=basePath%>/timebargain/scripts/open.js"></script>
	