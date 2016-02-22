<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title>添加</title>
	</head>
	<body>
		<form name="myFrm" action="${basePath}/student/add.action"  method="post" targetType="hidden">
			<fieldset width="50%" height="60%">
				<legend> 
					分类信息
				</legend>
					<%@include file="communalTable.jsp" %>
					<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center">
						<tr>
							<td  align="center">
								<input type="button" value="添加" onclick="commitStuInfo()">
							</td>
							<td align="center">
								<input type="button" value="关闭" onclick="window.close()">
							</td>
						</tr>
					</table> 
			</fieldset>
		</form>
	</body>
</html>

<%@ include file="/public/footInc.jsp"%>
<%@ include file="/public/addUpdatejs.jsp"%>
