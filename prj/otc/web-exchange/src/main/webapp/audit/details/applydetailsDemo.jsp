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
				<input type="hidden" name="applyType" value="${applyType}">
				<%@ include file="showDetails.jsp"%>
			</fieldset>
		<br />
		<br />
		</form>
		<button name="返回" onclick="returnA()">返回</button>
	</body>
</html>
<script language="javascript">
	function returnA(){
		myForm.action="${basePath}/apply/applyList.action";
		myForm.submit();
	}
</script>