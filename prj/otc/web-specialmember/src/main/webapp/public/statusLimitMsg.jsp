<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/common/public/choSkin.jsp"%>
<%@page import="gnnt.MEBS.config.constant.ActionConstant"%>
<%
	String publicPath = basePath + "/public";
	String resultMsg = (String) request.getSession().getAttribute(ActionConstant.RESULTMSG);
	int resultValue = (Integer) request.getSession().getAttribute(ActionConstant.RESULTVAULE);
	request.setAttribute(ActionConstant.RESULTMSG, resultMsg);
	request.setAttribute(ActionConstant.RESULTVAULE, resultValue);
	request.getSession().removeAttribute(ActionConstant.RESULTMSG);
	request.getSession().removeAttribute(ActionConstant.RESULTVAULE);
%>
<!-- 获取返回信息 -->
<c:if test="${not empty resultMsg }">
	<script>
        alert('${resultMsg}');
        window.close();
    </script>
</c:if>


<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
<META HTTP-EQUIV="expires" CONTENT="0">

