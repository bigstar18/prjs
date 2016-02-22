<%@ page pageEncoding="GBK"%>
<table border="0" cellspacing="0" cellpadding="0" width="40%"
	height="400">
	<tr>
		<td align="center">
			审批人
		</td>
		<td align="center">
			审批时间
		</td>
		<td align="center">
			状态
		</td>
		<tr>
			<c:forEach items="${auditList}" var="result">
				<tr>
					<td align="center">
						${result.proposer}
					</td>
					<td align="center">
						${result.modTime}
					</td>
					<td align="center">
						${result.statusDiscribe}
					</td>
				</tr>
			</c:forEach>
</table>