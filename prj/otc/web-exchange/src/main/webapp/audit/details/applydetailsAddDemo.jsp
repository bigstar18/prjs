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
					申请具体信息${applyType}
				</legend>
				<input type="hidden" name="obj.id" value="">
				<table border="0" cellspacing="0" cellpadding="4" width="100%"
					align="center">
					<tr height="20">
						<td align="right">
							名称:
						</td>
						<td>
							<input type="text" id="name" name="obj.name" value="">
						</td>
					</tr>
					<tr height="20">
						<td align="right">
							年龄:
						</td>
						<td>
							<input type="text" id="age" name="obj.age" value="">
						</td>
					</tr>
					<tr height="20">
						<td align="right">
							地址:
						</td>
						<td>
							<input type="text" id="address" name="obj.address" value="">
						</td>
					</tr>
				</table>
			</fieldset>
		<br />
		<br />
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
		myForm.action="${basePath}/apply/audits.action";
		myForm.buttonClick.value=aa;		
		myForm.submit();
	}
</script>