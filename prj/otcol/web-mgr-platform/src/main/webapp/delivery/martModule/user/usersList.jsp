<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../public/session.jsp"%>
<html>
  <head>
    <title><%=TITLE%></title>
  </head>
  <body onload="initBody('${returnRefresh}')">
  	<form name="frm" action="<%=basePath%>servlet/userController.${POSTFIX}?funcflg=userList" method="post">
		<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
		<input type="hidden" id="userId" name="userId">
		<input type="hidden" id="warehouseId" name="warehouseId">
		<fieldset width="95%">
			<legend>管理员查询</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="90%" height="35">
				<tr height="35">
					<td align="right">管理员编号：</td>
					<td align="left">
						<input id="useIdForQuery" name="_userId[like]" value="<c:out value='${oldParams["userId[like]"]}'/>" type=text class="text" style="width: 100px" maxlength="16">
					</td>
					<td align="right">管理员名称：</td>
					<td align="left">
						<input id="nameForQuery" name="_name[like]" value="<c:out value='${oldParams["name[like]"]}'/>" type=text  class="text" style="width: 100px" onkeypress="onlyNumberAndCharInput()" maxlength="16">
					</td>
					<td align="left">
						<button type="button" class="smlbtn" onclick="doQuery();">查询</button>&nbsp;
						<button type="button" class="smlbtn" onclick="resetForm();">重置</button>&nbsp;
					</td>
				</tr>
			</table>
		</fieldset>
		<%@ include file="userTable.jsp"%>
	</form>
  </body>
</html>
<SCRIPT LANGUAGE="JavaScript">
<!--
	function doQuery(){
		frm.submit();
	}
	function viewUser(userId,warehouseId){
		frm.userId.value = userId;
		frm.warehouseId.value = warehouseId;
		frm.action = "<%=basePath%>servlet/userController.${POSTFIX}?funcflg=userView";
		frm.submit();
	}
	function fadd(){
		frm.action = "";
		frm.submit();
	}
	function resetForm(){
		frm.useIdForQuery.value = "";
		frm.nameForQuery.value = "";
		frm.submit();
	}
	// 修改密码
	function editPwd(userId,warehouseId){
	   var v = "<%=basePath%>servlet/userController.${POSTFIX}?funcflg=userPwdForward&userId=" + userId + "&warehouseId=" + warehouseId;
	   var result = window.showModalDialog(v,'', 
					"dialogWidth=400px; dialogHeight=250px; status=no;scroll=yes;help=no;");
	   if(result){
	    window.location.href = "<%=basePath%>servlet/userController.${POSTFIX}?funcflg=userReturn";
		 
	   }
	}
//-->
</SCRIPT>
