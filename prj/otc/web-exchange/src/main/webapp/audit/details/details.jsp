<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<%@ include file="/public/ecsideLoad.jsp"%>


	</head>
	<body>
		<br />
		<form name="myForm" action=""
			method="post">
			<fieldset width="50%" height="80%">
				<legend>
					审核具体信息${applyType}
				</legend>
				<input type="hidden" name="obj.id" value="${obj.id}">
				<table border="0" cellspacing="0" cellpadding="4" width="100%"
					align="center">
					<tr height="20">
						<td align="right">
							名称:
						</td>
						<td>
							<input type="text" id="name" name="obj.name" value="${obj.name }">
						</td>
					</tr>
					<tr height="20">
						<td align="right">
							年龄:
						</td>
						<td>
							<input type="text" id="age" name="obj.age" value="${obj.age}">
						</td>
					</tr>
					<tr height="20">
						<td align="right">
							地址:
						</td>
						<td>
							<input type="text" id="address" name="obj.address" value="${obj.address}">
						</td>
					</tr>
				</table>
			</fieldset>
		<br />
		<br />
		<fieldset width="40%" height="20%">
				<legend>
					审核历史记录
				</legend>
				<%@ include file="detailsList.jsp"%>
		</fieldset>
		<fieldset width="40%" height="20%">
				<legend>
					所需操作
				</legend>
				<%@ include file="buttonList.jsp"%>
		</fieldset>
		</form>
	</body>
</html>
<script language="javascript">
	function audit(aa){
		myForm.action="${basePath}/audit/audits.action";
		myForm.buttonClick.value=aa;		
		myForm.submit();
	}
</script>