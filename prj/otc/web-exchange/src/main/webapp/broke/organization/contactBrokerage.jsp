<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title>选择关联居间人</title>
	</head>

	<body leftmargin="0" topmargin="0" style="overflow-y:hidden">
		<form action="" name="frm" method="POST" targetType="hidden">
		<input type="hidden" name="obj.organizationNO" value="${organization.organizationNO }">
				<div class="div_scro">
			<table border="0" height="300" width="90%" align="center">
				<tr height="100"></tr>
				<tr>
				 <td>
				 <div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" width="43" height="40" align="absmiddle" />&nbsp;&nbsp;选择关联居间人</div>
				<table border="0" cellspacing="0" cellpadding="0" width="100%" class="st_bor">
					<tr>
						<td>
							&nbsp;
						</td>
					</tr>
					<c:forEach items="${brokerageList}" var="result">
						<tr>
							<td align="right" width="50%">
								<input type="checkbox" name="checkId" value="${result.brokerageNo }"
								<c:forEach items="${organization.brokerageSet }" var="broker">
									<c:if test="${broker.brokerageNo==result.brokerageNo }">
										checked="checked"
									</c:if>
								</c:forEach>
									/>
							</td>
							</td>
							<td align="left" width="50%">
								<span id="${result.brokerageNo}">${result.name }</span>
							</td>
						</tr>
					</c:forEach>
				</table>
			</td>
			</tr>
			</table>
			</div>
			<div class="tab_pad">
			<table border="0" cellspacing="0" cellpadding="0" width="90%" align="center" >
			<tr height="35">
				<td align="center">
			<input type="button" class="btn_sec" name="submitMem" onclick="sub()"
				value="保存">
			<input type="button" class="btn_sec" name="returnMem" onclick="resetMem()" value="关闭">
			</td>
			</tr>
			</table>
			</div>
		</form>
	</body>
</html>
<script type="text/javascript">


function sub(){
		frm.action="${basePath}/broke/organization/updateCotactBroke.action";
		frm.submit();
}

function resetMem(){
	window.close();
}
</script>
<%@ include file="/public/footInc.jsp"%>