<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/session.jsp"%>
<html>
  <head>
    <title></title>
  </head>
  <body onload="initBody('${returnRefresh}')">
  	<form name="frm" action="<%=basePath%>firmPermissionController.zcjs?funcflg=list" method="post">
		<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
	<fieldset width="95%">
			<legend>交易商权限查询</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
					<td align="right">交易商代码：</td>
					<td align="left">
						<input id="firmId" name="_firmId[like]"
								value="<c:out value='${oldParams["firmId[like]"]}'/>"
								type=text class="text" style="width: 100px" onkeypress="notSpace()">
					</td>
				 <td align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;
						<button type="button" class="smlbtn" onclick="doQuery();">查询</button>&nbsp;&nbsp;
						<button type="button" class="smlbtn" onclick="resetForm();">重置</button>	
				</td>
				</tr>
			</table>
	</fieldset>	
	  <%@ include file="firmPermissionTable.jsp"%>
	</form>
  </body>
</html>
<SCRIPT LANGUAGE="JavaScript">
	function fmod(firmId) {
		frm.action = "<%=basePath%>firmPermissionController.zcjs?funcflg=updateForward&firmId="+firmId;
		frm.submit();
	}
	function resetForm() {
		document.getElementById('firmId').value="";
		frm.submit();
	}
</SCRIPT>