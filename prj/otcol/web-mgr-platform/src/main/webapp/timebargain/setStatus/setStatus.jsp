<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<c:if test="${not empty handleResult }">
		<script type="text/javascript">
			alert("<c:out value='${handleResult }'/>");
		</script>
</c:if>
	<head>
		<script type="text/javascript" defer="defer">
			function doSetting() {
				ordersForm.submit();
			}
		</script>
	</head>

	<body leftmargin="0" topmargin="0">
		<table border="0" height="500" align="center">
			<tr>
				<td>
					<form name="ordersForm" method="post" action="<%=request.getContextPath() %>/timebargain/menu/querySetting.do?funcflg=setStatus">
						<fieldset class="pickList">
							<legend class="common">
								<b>选择状态</b>
							</legend>
							<table border="0" width="500" height="196">
								<tr height="20">
								</tr>
								<tr height="20">
									<td align="center">当前时间：<span class="req">${now }</span></td>
								</tr>
								<tr height="20">
								</tr>
							<c:choose>
								<c:when test="${statusResult==true }">
								<tr height="20">
									<td align="center">
										<input type="radio" value="Y" name="statRadio" checked="true">启动
										&nbsp;&nbsp;&nbsp;&nbsp;
										<input type="radio" value="N" name="statRadio">禁止
									</td>
								</tr>
								</c:when>
								<c:otherwise>
								<tr height="20">
									<td align="center">
										<input type="radio" value="Y" name="statRadio">启动
										&nbsp;&nbsp;&nbsp;&nbsp;
										<input type="radio" value="N" name="statRadio" checked="true">禁止
									</td>
								</tr>
								</c:otherwise>
							</c:choose>
								<tr align="center">
									<td>
									<input type="hidden" name="hiddenValue">
										<input type="button" value="确定" onclick="doSetting()"/>
									</td>
								</tr>
							</table>
						</fieldset>
					</form>
				</td>
			</tr>
		</table>
	</body>
