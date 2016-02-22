<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title>选择关联居间人</title>
	</head>
	<%
		try {
	%>
	<body leftmargin="0" topmargin="0" style="overflow-y: hidden">
		<div class="st_title">
			<img src="<%=skinPath%>/cssimg/st_ico1.gif"
				align="absmiddle" />
			&nbsp;&nbsp;选择关联居间人
		</div>
		<form action="${basePath}/broke/organization/updateCotactBroke.action"
			name="frm" method="POST" targetType="hidden">
			<input type="hidden" name="obj.organizationNO"
				value="${organization.organizationNO }">
			<div style="overflow: auto; height: 345px;">
				<table border="0" width="90%" align="center">
					<tr>
						<td>
							<table border="0" cellspacing="0" cellpadding="0" width="100%"
								class="st_bor">
								<tr>
									<td>
										&nbsp;
									</td>
								</tr>
								<c:forEach items="${brokerageAllList}" var="result">
									<c:set var="flag" value="false"></c:set>
									<c:forEach items="${brokerageList}" var="result1">
										<c:if test="${ result.brokerageNo==result1.brokerageNo}">
											<c:set var="flag" value="true"></c:set>
										</c:if>
									</c:forEach>
									<c:if test="${ flag==false}">
										<tr>
											<td align="right" width="50%">
												<input type="checkbox" name="checkId"
													value="${result.brokerageNo }" />

											</td>
											</td>
											<td align="left" width="50%">
												<span id="${result.brokerageNo}">${result.name }</span>
											</td>
										</tr>
									</c:if>
								</c:forEach>

								<c:forEach items="${organization.brokerageSet }" var="broker">
									<tr>
										<td align="right" width="50%">
											<input type="checkbox" name="checkId"
												value="${broker.brokerageNo }" checked="checked"/>
										</td>
										</td>
										<td align="left" width="50%">
											<span id="${broker.brokerageNo}">${broker.name }</span>
										</td>
									</tr>
								</c:forEach>
							</table>
						</td>
					</tr>
				</table>
			</div>
			<div class="tab_pad">
				<table border="0" cellspacing="0" cellpadding="0" width="100%"
					align="center">
					<tr height="35">
						<td align="center">
							<input type="button" class="btn_sec" name="submitMem"
								onclick="sub()" value="确定">
						</td>
						<td align="center">
							<input type="button" class="btn_sec" name="returnMem"
								onclick="resetMem()" value="返回">
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
	<%
		} catch (Exception e) {
			e.printStackTrace();
		}
	%>
</html>
<script type="text/javascript">

function sub() {
	frm.submit();
}

function resetMem() {
	window.close();
}
</script>
<%@ include file="/public/footInc.jsp"%>