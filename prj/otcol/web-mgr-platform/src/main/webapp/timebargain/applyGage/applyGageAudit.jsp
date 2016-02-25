<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<head>
    <%@ include file="/timebargain/common/ecside_head.jsp" %>
</head>

<body>
<form action="" name="auditOrRefusedForm" method="post">
	<input type="hidden" name="applyId" value="${applyGage.applyId }">
   <table width="100%" height="90%" align="center">
	   	<tr align="center">
   			<td align="right" width="20%">申请单号：</td>
   			<td align="left" width="20%"><c:out value="${applyGage.applyId }"/></td>
   			<td align="right" width="20%">&nbsp;</td>
   			<td align="left" width="20%">&nbsp;</td>
   		</tr>
   		<tr align="center">
   			<td align="right" width="20%">商品代码：</td>
   			<td align="left" width="20%"><c:out value="${applyGage.commodityID }"/></td>
   			<td align="right" width="20%">申请数量：</td>
   			<td align="left" width="20%"><fmt:formatNumber value="${applyGage.quantity }" pattern="##,###,###"/></td>
   			
   		</tr>
   		<tr align="center">
   			<td align="right" width="20%">交易商代码：</td>
   			<td align="left" width="20%"><c:out value="${applyGage.firmID }"/></td>
   			<td align="right" width="20%">交易二级代码：</td>
   			<td align="left" width="20%"><c:out value="${applyGage.customerID }"/></td>
   		</tr>
   		<tr align="center">
   			<td align="right" width="20%">申请种类：</td>
   			<td align="left" width="20%">
   				<c:choose>
   					<c:when test="${applyGage.applyType==0 }">正常</c:when>
   					<c:when test="${applyGage.applyType==1 }">卖仓单</c:when>
   					<c:when test="${applyGage.applyType==2 }">抵顶转让</c:when>
   				</c:choose>
   			</td>
   			<td align="right" width="20%">当前状态：</td>
   			<td align="left" width="20%">
				<c:choose>
   					<c:when test="${applyGage.status==1 }">待审核</c:when>
   					<c:when test="${applyGage.status==2 }">审核通过</c:when>
   					<c:when test="${applyGage.status==3 }">审核失败</c:when>
   				</c:choose>
			</td>
   		</tr>
   		<tr align="center">
   			<td align="right" width="20%">创建人：</td>
   			<td align="left" width="20%"><c:out value="${applyGage.创建人 }"/></td>
   			<td align="right" width="20%">创建人备注：</td>
   			<td align="left" width="20%"><c:out value="${applyGage.remark1 }"/></td>
   		</tr>
   		<tr align="center">
   			<td align="right" width="20%">最后修改人：</td>
   			<td align="left" width="20%"><c:out value="${applyGage.modifier }"/></td>
   			<td align="right" width="20%">修改人备注：</td>
   			<td align="left" width="20%"><c:out value="${applyGage.remark2 }"/></td>
   		</tr>
   		<tr align="center">
   			<td align="right" width="20%">创建时间：</td>
   			<td align="left" width="20%"><fmt:formatDate value="${applyGage.createTime }" pattern="yyyy-MM-dd"/></td>
   			<td align="right" width="20%">修改时间：</td>
   			<td align="left" width="20%"><fmt:formatDate value="${applyGage.modifyTime }" pattern="yyyy-MM-dd"/></td>
   		</tr>
   </table>
   </form>
<script type="text/javascript">
function showApplyMessage(v)
{
	window.location.href = "/timebargain/menu/applyGage.do?funcflg=auditApplyGage&d="+Date();
}
</script>
</body>