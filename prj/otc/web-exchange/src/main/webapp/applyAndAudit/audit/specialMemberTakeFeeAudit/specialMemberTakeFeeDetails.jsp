<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<script type="text/javascript" src="<%=basePath%>/public/isReadOnly.js"></script>
<html>
	<head><title>�ر��Ա������</title>
		<%@ include file="/public/ecsideLoad.jsp"%>
	</head>
	<body style="overflow-y:hidden" class="st_body">
		<form name="frm" action="" method="post" targetType="hidden">
			<div class="div_scrospmid">
			<table border="0" width="90%" align="center">
				<tr>
				<td>
				<div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;���������Ϣ</div>
				<input type="hidden" name="applyId" value="${obj.applyId}">
				<%@ include file="/commodity/commodityFee/specialMemberTakeFeeUpdateTable.jsp"%>
				<%@ include file="/applyAndAudit/public/statusDiscribe.jsp"%>
				<c:if test="${param.apply!='1'}">
				
				<%@ include file="/applyAndAudit/public/detailsList.jsp"%>
				</c:if>
				</td>
				</tr>
				</table>
				
			</div>
			<c:if test="${empty param.apply}">
			<%@ include file="/applyAndAudit/public/auditButtonList.jsp"%>
			</c:if>
			<c:if test="${not empty param.apply}">
			<%@ include file="/applyAndAudit/public/commitAuditButtonList.jsp"%>
			</c:if>
		</form>
	</body>
</html>
<%@ include file="/public/footInc.jsp"%>
<script type="text/javascript">
	function audit(aa){
		frm.action="${basePath}/audit/${COMMODITY_}specialMemberTakeFeeAudit/audits.action";
		frm.buttonClick.value=aa;		
		frm.submit();
	}
	setReadOnly();
</script>