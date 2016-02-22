<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

	

<html>
  <head>
	<style type="text/css">
<!--
.xbg1 {
	background-attachment: fixed;
	background-image: url(<%=skinPath%>/cssimg/main_blue.jpg);
	background-repeat: no-repeat;
	background-position: center;
}
.xbg2 {
	background-attachment: fixed;
	background-image: url(<%=skinPath%>/cssimg/main_gray.jpg);
	background-repeat: no-repeat;
	background-position: center;
}
-->
	</style>
</head>
<script type="text/javascript">
	
	window.open("${basePath}/tradeManage/serverInfo/queryServerInfo.action");
	</script>
	
<body>
  <table width="100%" height="100%" border="1" valign="center" align="center" cellpadding="0" cellspacing="3" bordercolor="#FFF6F4">
  <tr>
	  	<c:choose>
	  		<c:when test="${skinName == default }">
	  			<td class="xbg1"></td>
	  		</c:when>
	  		<c:when test="${skinName == gray }">
	  			<td class="xbg2"></td>
	  		</c:when>
	  	</c:choose>
  </tr>
</table>
</body>
</html>