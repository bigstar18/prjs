<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>
<base target="_self"> 
<%
	BankDAO dao = BankDAOFactory.getDAO();
	Vector<SystemMessage> vec = null;
	try{
		vec = dao.getSystemMessages("");
	}catch(Exception e){
		vec = new Vector<SystemMessage>();
	}
%>
<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
	<script language="javascript" src="../lib/tools.js"></script>
    <title>ѡ����ϵͳ</title>	
  </head>
  
  <body>
  	<form id="frm" action="" method="post">
		<fieldset width="100%" style="margin-left:10px;">
			<legend>ѡ����ϵͳ</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
					<td align="center">��ѡ����ϵͳ��&nbsp;</td>
				</tr>
				<tr height="35">
					<td align="center">
						<select name="systemID" class="normal" style="width: 140px">
							<OPTION value="-1">��ѡ��</OPTION>
							<%for(SystemMessage sm : vec){%>
								<option value="<%=sm.systemID%>"><%=sm.systemName%></option>	
							<%}%>
					</td>
				</tr>
				<tr height="35">					
					<td align="center">
						<button type="button" class="smlbtn" onclick="doAdd();">ȷ��</button>&nbsp;
						<button type="button" class="smlbtn" onclick="window.close();">ȡ��</button>&nbsp;
					</td>
				</tr>
			</table>
		</fieldset>	  
	</form>
  </body>
</html>

<SCRIPT LANGUAGE="JavaScript">
<!--

function doAdd(){
	if(frm.systemID.value == -1){
		alert("��ѡ����ϵͳ");
	}else{
		window.returnValue = frm.systemID.value;
		window.close();
	}
}

//-->
</SCRIPT>