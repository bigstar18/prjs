<%@ page pageEncoding="GBK"%>
<table border="0" cellspacing="0" cellpadding="0" width="40%"
	height="400">
	<tr>
		<td align="center">
			������
		</td>
		<td align="center">
			����ʱ��
		</td>
		<td align="center">
			״̬
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