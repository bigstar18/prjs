<%@ page pageEncoding="GBK"%>
<table border="0" width="100%" align="center" >
	<tr height="50"></tr>
	<tr>
		<td>
	<div class="div_cxtjd">
		<img src="<%=skinPath%>/cssimg/13.gif" />
		&nbsp;&nbsp;�����ʷ��¼
	</div>
	<div class="div_tj">
<table border="0" cellspacing="0" cellpadding="0" width="80%" height="100" class="table2_style">
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
						${datefn:formatdate(result.modTime)}
					</td>
					<td align="center">
						${statusMap[result.status]}
					</td>
				</tr>
			</c:forEach>
</table>
</div>
</td>
</tr>
</table>