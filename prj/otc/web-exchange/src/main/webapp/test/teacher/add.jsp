<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title>���</title>
	</head>
	<body>
		<form name="teaFrm" action="${basePath}/teacher/add.action" method="post" targetType="hidden">
			<fieldset width="50%" height="60%">
				<legend> 
					������Ϣ
				</legend>
					<%@include file="communalTable.jsp" %>
					<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center">
						<tr>
							<td  align="center">
								<input type="button" value="���" onclick="return commitTeaInfo();">
							</td>
							<td align="center">
								<input type="button" value="�ر�" onclick="window.close()">
							</td>
						</tr>
					</table> 
			</fieldset>
		</form>
		<iframe src="" name="frame" style="visibility: hidden"></iframe>
<c:if test="${not empty resultMsg }">
	<script>
		window.returnValue='1';
		window.close();
	</script>
</c:if>
	</body>
</html>
<%@ include file="/public/footInc.jsp"%>
<%@ include file="/public/addUpdatejs.jsp"%>
