<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<script type="text/javascript" src="<%=basePath%>/public/isReadOnly.js"></script>
<html>
	<head>
	<title>商品信息</title>
		<%@ include file="/public/ecsideLoad.jsp"%>
	</head>
	<body style="overflow-y:hidden">
		<form name="myForm" action=""
			method="post" targetType="hidden">
			<div class="div_scrospmid">
			<table border="0" width="90%" align="center">
				<tr>
					<td>
				<div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;详细信息</div>
				<input type="hidden" name="applyId" value="${obj.applyId}">
				<%@ include file="/commodity/baseInfo/baseInfoTable.jsp"%>
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
<script language="javascript">
	function audit(aa){
		myForm.action="${basePath}/audit/${COMMODITY_}baseCommodityAudit/audits.action";
		myForm.buttonClick.value=aa;		
		var vaild = affirm("您确定要操作吗？");
		if(vaild==true){
			myForm.submit();
	    }else{
           return false;
	    }
	}
	setReadOnly();
</script>
<%@ include file="/public/footInc.jsp"%>
