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
								<b>ѡ��״̬</b>
							</legend>
							<table border="0" width="500" height="196">
								<tr height="20">
								</tr>
								<tr height="20">
									<td align="center">��ǰʱ�䣺<span class="req">${now }</span></td>
								</tr>
								<tr height="20">
								</tr>
							<c:choose>
								<c:when test="${statusResult==true }">
								<tr height="20">
									<td align="center">
										<input type="radio" value="Y" name="statRadio" checked="true">����
										&nbsp;&nbsp;&nbsp;&nbsp;
										<input type="radio" value="N" name="statRadio">��ֹ
									</td>
								</tr>
								</c:when>
								<c:otherwise>
								<tr height="20">
									<td align="center">
										<input type="radio" value="Y" name="statRadio">����
										&nbsp;&nbsp;&nbsp;&nbsp;
										<input type="radio" value="N" name="statRadio" checked="true">��ֹ
									</td>
								</tr>
								</c:otherwise>
							</c:choose>
								<tr align="center">
									<td>
									<input type="hidden" name="hiddenValue">
										<input type="button" value="ȷ��" onclick="doSetting()"/>
									</td>
								</tr>
							</table>
						</fieldset>
					</form>
				</td>
			</tr>
		</table>
	</body>
