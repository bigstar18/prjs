<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../globalDef.jsp"%>
<%@ include file="../../session.jsp"%>
<%@ include file="MarketInfo.jsp" %>
<base target="_blank">
<html>
<%

String result = request.getParameter("remark1");
double moneyD=Double.parseDouble(request.getParameter("remark2"))*100;
long moneyL=(long) moneyD;
String money =""+moneyL;
String acount = request.getParameter("remark3");
String contact= request.getParameter("remark4");
String payType=request.getParameter("remark5");
String computerIP = request.getRemoteAddr();
String datetime=Tool.fmtDateTime(new java.util.Date());
%>
<body oncontextmenu="return false">
<form name="frm" id="frm"  action="TLGatewayPost.jsp" method="post"  >
	<input type="hidden" id="version" name="version" value="1.0"/>
	<input type="hidden" id="merchantId" name="merchantId" value="<%=marketCode%>"/>
	<input type="hidden" id="orderAmount" name="orderAmount" value="<%=money%>"/>
	<input type="hidden" id="orderNo" name="orderNo" value="<%=contact+"-"+result%>"/>
	<input type="hidden" id="receiveUrl" name="receiveUrl" value="<%=receiveUrl%>"/>
	<input type="hidden" id="pickupUrl" name="pickupUrl" value="<%=pickupUrl%>"/>
	<input type="hidden" id="payType" name="payType" value="<%=payType%>"/>
	<input type="hidden" id="orderDatetime" name="orderDatetime" value="<%=datetime%>"/>
	<input type="hidden" id="ext1" name="ext1" value="<%=contact%>"/>
	<input type="hidden" id="MD5Key" name="MD5Key" value="<%=md5Key%>"/>
</form>
</body>
</html>
<SCRIPT LANGUAGE="JavaScript">
frm.action = 'TLGatewayPost.jsp';
frm.submit();
window.location = "../../Money.jsp";

</SCRIPT>