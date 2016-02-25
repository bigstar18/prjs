<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.text.SimpleDateFormat"/>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>
<%@ page import="gnnt.trade.bank.*" %>
<%
	String compareTime = Tool.getCompareTime();
	CapitalProcessorRMI cp = null;
	try{
		cp = (CapitalProcessorRMI)Naming.lookup("//" + RmiIpAddress + ":" + RmiPortNumber + "/"+ RmiServiceName);
	}catch(Exception e){
		e.printStackTrace();
	}
	BankDAO dao = BankDAOFactory.getDAO();
	Vector bankList = dao.getBankList(" where validFlag=0 ");
%>
<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
    <IMPORT namespace="MEBS" implementation="../lib/calendar.htc">
    <title>银行清算总页面</title>
  </head>
  <body>
  	<form id="frm" action="" method="post">
		<fieldset width="100%">
			<legend>银行清算功能</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">	
				<td align="right">银行：&nbsp;</td>
				<td align="left">
					<select name="bankID" class="normal" style="width:120px" onchange="gotoBankQS();">
						<OPTION value="-1" >请选择</OPTION>
						<%
						for(int i=0;i<bankList.size();i++) {
							BankValue bv = (BankValue)bankList.get(i);
							if(sendQSBank(bv.bankID) != 0){
							%>
							<option value="<%=bv.bankID%>" ><%=bv.bankName%></option>
							<%
							}
						}
						%>
					</select>
				</td>
				</tr>
			</table>
		</fieldset>
	</form>
  </body>
</html>