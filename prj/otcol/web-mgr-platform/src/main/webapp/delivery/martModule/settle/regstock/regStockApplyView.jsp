<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../../public/session.jsp"%>
<html xmlns:MEBS>
<script type="text/javascript">
</script>
<body> 
    <form id="formNew" name="frm" method="POST" targetType="hidden">
    	<input type="hidden" name="applyId" value="${operateObj.applyId}">
		<fieldset width="100%">
		<legend>注册仓单申请信息详情</legend>
		<br>
			<table width="100%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#333333" style="border-collapse:collapse;">
				<tr>
					<td colspan="2" align="center"><b>注册仓单申请号：${operateObj.applyId}
						<font color="red">
							<c:choose>
								<c:when test="${operateObj.status==1 }">(未审核)</c:when>
								<c:when test="${operateObj.status==2 }">(已审核)</c:when>
								<c:when test="${operateObj.status==-2 }">(驳回)</c:when>
							</c:choose>
						</font></b>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
				<tr height="30px">
					<td class="cd_bt"  width="35%"  align="right">商品品种：</td><td align="left">${commodity.name}</td>
				</tr>
				<tr height="30px">
					<td class="cd_bt"  width="35%"  align="right">${FIRMNAME }：</td><td align="left">${operateObj.firmId}</td>
				</tr>
				<c:if test="${operateObj.type==0 }">
					<tr height="30px">
						<td class="cd_bt"  width="35%"  align="right">仓库名称：</td><td align="left">${warehouse.name}</td>
					</tr>
					<tr height="30px">
						<td class="cd_bt"  width="35%"  align="right">入库单号：</td><td align="left">${operateObj.stockId}</td>
					</tr>
				</c:if> 
				<tr height="30px">
					<td  class="cd_bt" width="35%"  align="right">数量：</td><td align="left">${operateObj.weight}</td>
				</tr>
				<tr height="30px">
					<td class="cd_bt"  width="35%"  align="right">单位数量：</td><td align="left">${operateObj.unitWeight}</td>
				</tr>
				<!-- <tr height="30px">
					<td  class="cd_bt" width="35%"  align="right">注册仓单号：</td><td align="left">${operateObj.regStockId}</td>
				</tr> -->
				<tr height="30px">
					<td class="cd_bt"  width="35%"  align="right">申请人：</td><td align="left">${operateObj.applicant}</td>
				</tr>
				<tr height="30px">
					<td class="cd_bt"  width="35%"  align="right">申请时间：</td><td align="left"><fmt:formatDate value="${operateObj.applyTime}" pattern="yyyy-MM-dd"/></td>
				</tr>
				<!-- 
				<tr height="30px">
					<td  class="cd_bt" width="35%"  align="right">审核人：</td><td align="left">${operateObj.auditor}</td>
				</tr>
				<tr height="30px">
					<td  class="cd_bt" width="35%"  align="right">审核时间：</td><td align="left"><fmt:formatDate value="${operateObj.auditTime}" pattern="yyyy-MM-dd"/></td>
				</tr>
				 -->	
				</table>
				<%@ include file= "../../../public/approveOrOverrule.jsp" %>
		</fieldset>
			<br>
    </form>
</body>
</html>