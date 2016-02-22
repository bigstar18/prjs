<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="globalDef.jsp"%>
<%@ include file="session.jsp"%>
<%@ include file="ext_connector/ext_gfb_66/marketInfo.jsp"%>
<base target="_blank">
<html>
<%

String result = request.getParameter("remark1");
String money = request.getParameter("remark2");
String acount = request.getParameter("remark3");
String contact = request.getParameter("remark4");
String firmid = request.getParameter("remark5");
String computerIP = request.getRemoteAddr();
String datetime=Tool.fmtDateTime(new java.util.Date());
%>
<body oncontextmenu="return false">
<form name="frm" id="frm"  action="<%=netUrl%>" method="post" >
	<input type="hidden" id="tranCode" name="tranCode" value="8801" size="50"/>
	<input type="hidden" id="version" name="version" value="2.0" size="50"/>
	<input type="hidden" id="merchantID" name="merchantID" value="<%=MarkCode%>" size="50"/>
	<input type="hidden" id="contractNo" name="contractNo" value="<%=acount%>" size="50"/>
	<input type="hidden" id="merOrderNum" name="merOrderNum" value="<%=MarkCode+"-"+result%>" size="50"/>
	<input type="hidden" id="backgroundMerUrl" name="backgroundMerUrl" value="<%=backgroundMerUrl%>" size="50"/>
	<input type="hidden" id="frontMerUrl" name="frontMerUrl" value="<%=frontMerUrl%>" size="50"/>
	<input type="hidden" id="virCardNoIn" name="virCardNoIn" value="<%=marketGSAcount%>" size="50"/>
	
	<input type="hidden" id="tranAmt" name="tranAmt" value="<%=Double.parseDouble(money)%>" size="50"/>
	<input type="hidden" id="currencyType" name="currencyType" value="156" size="50"/>
	<input type="hidden" id="tranDateTime" name="tranDateTime" value="<%=datetime%>" size="50"/>
	<input type="hidden" id="tranIP" name="tranIP" value="<%=computerIP%>" size="50"/>
	<input type="hidden" id="isRepeatSubmit" name="isRepeatSubmit" value="1" size="50"/>
	<input type="hidden" id="msgExt" name="msgExt" value="<%=contact+"|"+acount%>" size="50"/>
	<input type="hidden" id="merRemark1" name="merRemark1" value="<%=firmid%>" size="50"/>
	<input type="hidden" id="merRemark2" name="merRemark2" value="<%=contact%>" size="50"/>
	<input type="hidden" id="signType" name="signType" value="1" size="50"/>
	<%
	System.out.println("国付宝入金   流水号："+MarkCode+"-"+result+"   时间："+datetime);
	String signValue="";
	signValue=MD5orSHA.inMoneyEncryption(Double.parseDouble(money),computerIP,"1",acount,MarkCode+"-"+result,datetime
		,MarkCode,backgroundMerUrl,VerficationCode,marketGSAcount,frontMerUrl);
	System.out.println("签名："+signValue);
	%>
	<input type="hidden" id="signValue" name="signValue" value="<%=signValue%>" size="50"/>
	<input type="hidden" id="gopayServerTime" name="gopayServerTime" value="" size="50"/>
	</form>
</body>
</html>
<SCRIPT LANGUAGE="JavaScript">

frm.action = '<%=netUrl%>';
frm.submit();
window.location = "Money.jsp";
</SCRIPT>