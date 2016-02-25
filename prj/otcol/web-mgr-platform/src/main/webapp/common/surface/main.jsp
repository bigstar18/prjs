<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/common.jsp"%>
<html>
  <head>
	<style type="text/css">
<!--
.xbg1 {
	background-attachment: fixed;
	background-image: url(<%=skinPath%>/pic/main_blue.jpg);
	background-repeat: no-repeat;
	background-position: center;
}
.xbg2 {
	background-attachment: fixed;
	background-image: url(<%=skinPath%>/pic/main_gray.jpg);
	background-repeat: no-repeat;
	background-position: center;
}
-->
	</style>
</head>
<body>
  <table width="100%" height="100%" border="1" valign="center" align="center" cellpadding="0" cellspacing="3" bordercolor="#CCCCCC">
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