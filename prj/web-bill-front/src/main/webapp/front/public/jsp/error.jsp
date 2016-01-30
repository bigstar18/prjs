<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<html>
	<head>
		<title>error</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
	</head>
		<c:if test="${not empty ReturnValue.info }">
		<script>
			//alert('${ReturnValue.info}');
		</script>
		</c:if>
	<body class="login_body" style="background-color: #fff6f4;">
		<table width="772" border="0" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td height="160"></td>
			</tr>
			<tr>
				<td>
					<div class="error_bor01">
						<div class="error_bor02">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td align="center"><img src="${skinPath}/image/error.jpg" /></td>
								</tr>
								<tr>
									<td height="20">&nbsp;</td>
								</tr>
								<tr>
									<td align="center">
										<c:if test="${not empty ReturnValue.detailInfo }">
										<span>${ReturnValue.detailInfo}</span>
										</c:if>
									</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
								</tr>
							</table>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td align="center" class="login_bq">&nbsp;</td>
			</tr>
		</table>
	</body>
</html>
<%@ include file="submitsuccess.jsp" %>