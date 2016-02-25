<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<div id="wait" class="common" style="z-index:1;position:absolute;left:0px;top:0px;width:200px;height:25px;visibility: hidden;background-color:#ffffcc;cursor: wait;text-align: center;border-right: silver thin solid;border-top: silver thin solid;border-left: silver thin solid;border-bottom: silver thin solid;">ÕýÔÚ<c:choose><c:when test="${!empty param['prompt']}"><c:out value="${param['prompt']}" escapeXml="false"/></c:when><c:otherwise>²éÑ¯</c:otherwise></c:choose>£¬ÇëÉÔºò<img src="<c:url value="/timebargain/images/waitbar.gif"/>"></div>


