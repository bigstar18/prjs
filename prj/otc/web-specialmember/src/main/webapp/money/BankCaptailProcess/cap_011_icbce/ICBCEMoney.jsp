<%@ page contentType="text/html;charset=GBK" %>
<%@ page import="gnnt.trade.bank.util.EncodedICBCEBank" %>
<base target="_blank">
<html>
<%
String tranDataXmlStr=request.getParameter("remark6");
String ip="124.207.182.162";//request.getRemoteAddr();
String cerPath="/users/tjgjs/tomcat_tradeweb/webapps/money/BankCaptailProcess/cap_011_icbce/key/test201401.crt";
String keyPath="/users/tjgjs/tomcat_tradeweb/webapps/money/BankCaptailProcess/cap_011_icbce/key/test201401.key";
String pwd="12345678";
System.out.println("tranDataXmlStr"+tranDataXmlStr);
String mes=EncodedICBCEBank.createFormData(ip,tranDataXmlStr,cerPath,keyPath, pwd);
System.out.println("mes[[[["+mes+"]]]]");
String tranData=mes.split("<GNNTEND/>")[0];
String merSignMsg=mes.split("<GNNTEND/>")[1];
String merCert=mes.split("<GNNTEND/>")[2];
%>
<body >
<form name="frm" id="frm"  action="" method="post" >
	<input type="hidden" name="interfaceName" value="ICBC_PERBANK_B2C" />
	<input type="hidden" name="interfaceVersion" value="1.0.0.11" />
	<input type="hidden" name="tranData" value="<%=tranData%>" />
	<input type="hidden" name="merSignMsg" value="<%=merSignMsg%>" />
	<input type="hidden" name="merCert" value="<%=merCert%>" />
	<!--input type="button" value="提交" onclick="subFrm();" /-->
</form>
</body>
</html>
<SCRIPT LANGUAGE="JavaScript">
//function subFrm(){
frm.action = 'https://mybank3.dccnet.com.cn/servlet/ICBCINBSEBusinessServlet';//银行url
frm.submit();
window.location = '../../Money.jsp';
//}
</SCRIPT>