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
					���������Ϣ
				</legend>
				<input type="hidden" name="obj.id" value="">
				<%@ include file="showDetails.jsp"%>
			</fieldset>
		<br />
		<br />
		<fieldset width="40%" height="20%">
				<legend>
					�������
				</legend>
				<%@ include file="/applyAndAudit/public/buttonList.jsp"%>
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