<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../../public/session.jsp"%>
<html xmlns:MEBS>
<script type="text/javascript">
</script>
<body> 
    <form id="formNew" name="frm" method="POST" targetType="hidden">
    	<input type="hidden" name="applyId" value="${operateObj.applyId}">
		<fieldset width="100%">
		<legend>ע��ֵ�������Ϣ����</legend>
		<br>
			<table width="100%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#333333" style="border-collapse:collapse;">
				<tr>
					<td colspan="2" align="center"><b>ע��ֵ�����ţ�${operateObj.applyId}
						<font color="red">
							<c:choose>
								<c:when test="${operateObj.status==1 }">(δ���)</c:when>
								<c:when test="${operateObj.status==2 }">(�����)</c:when>
								<c:when test="${operateObj.status==-2 }">(����)</c:when>
							</c:choose>
						</font></b>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
				<tr height="30px">
					<td class="cd_bt"  width="35%"  align="right">��ƷƷ�֣�</td><td align="left">${commodity.name}</td>
				</tr>
				<tr height="30px">
					<td class="cd_bt"  width="35%"  align="right">${FIRMNAME }��</td><td align="left">${operateObj.firmId}</td>
				</tr>
				<c:if test="${operateObj.type==0 }">
					<tr height="30px">
						<td class="cd_bt"  width="35%"  align="right">�ֿ����ƣ�</td><td align="left">${warehouse.name}</td>
					</tr>
					<tr height="30px">
						<td class="cd_bt"  width="35%"  align="right">��ⵥ�ţ�</td><td align="left">${operateObj.stockId}</td>
					</tr>
				</c:if> 
				<tr height="30px">
					<td  class="cd_bt" width="35%"  align="right">������</td><td align="left">${operateObj.weight}</td>
				</tr>
				<tr height="30px">
					<td class="cd_bt"  width="35%"  align="right">��λ������</td><td align="left">${operateObj.unitWeight}</td>
				</tr>
				<!-- <tr height="30px">
					<td  class="cd_bt" width="35%"  align="right">ע��ֵ��ţ�</td><td align="left">${operateObj.regStockId}</td>
				</tr> -->
				<tr height="30px">
					<td class="cd_bt"  width="35%"  align="right">�����ˣ�</td><td align="left">${operateObj.applicant}</td>
				</tr>
				<tr height="30px">
					<td class="cd_bt"  width="35%"  align="right">����ʱ�䣺</td><td align="left"><fmt:formatDate value="${operateObj.applyTime}" pattern="yyyy-MM-dd"/></td>
				</tr>
				<!-- 
				<tr height="30px">
					<td  class="cd_bt" width="35%"  align="right">����ˣ�</td><td align="left">${operateObj.auditor}</td>
				</tr>
				<tr height="30px">
					<td  class="cd_bt" width="35%"  align="right">���ʱ�䣺</td><td align="left"><fmt:formatDate value="${operateObj.auditTime}" pattern="yyyy-MM-dd"/></td>
				</tr>
				 -->	
				</table>
				<%@ include file= "../../../public/approveOrOverrule.jsp" %>
		</fieldset>
			<br>
    </form>
</body>
</html>