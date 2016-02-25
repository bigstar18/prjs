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
   			<td align="right" width="20%">���뵥�ţ�</td>
   			<td align="left" width="20%"><c:out value="${applyGage.applyId }"/></td>
   			<td align="right" width="20%">&nbsp;</td>
   			<td align="left" width="20%">&nbsp;</td>
   		</tr>
   		<tr align="center">
   			<td align="right" width="20%">��Ʒ���룺</td>
   			<td align="left" width="20%"><c:out value="${applyGage.commodityID }"/></td>
   			<td align="right" width="20%">����������</td>
   			<td align="left" width="20%"><fmt:formatNumber value="${applyGage.quantity }" pattern="##,###,###"/></td>
   			
   		</tr>
   		<tr align="center">
   			<td align="right" width="20%">�����̴��룺</td>
   			<td align="left" width="20%"><c:out value="${applyGage.firmID }"/></td>
   			<td align="right" width="20%">���׶������룺</td>
   			<td align="left" width="20%"><c:out value="${applyGage.customerID }"/></td>
   		</tr>
   		<tr align="center">
   			<td align="right" width="20%">�������ࣺ</td>
   			<td align="left" width="20%">
   				<c:choose>
   					<c:when test="${applyGage.applyType==0 }">����</c:when>
   					<c:when test="${applyGage.applyType==1 }">���ֵ�</c:when>
   					<c:when test="${applyGage.applyType==2 }">�ֶ�ת��</c:when>
   				</c:choose>
   			</td>
   			<td align="right" width="20%">��ǰ״̬��</td>
   			<td align="left" width="20%">
				<c:choose>
   					<c:when test="${applyGage.status==1 }">�����</c:when>
   					<c:when test="${applyGage.status==2 }">���ͨ��</c:when>
   					<c:when test="${applyGage.status==3 }">���ʧ��</c:when>
   				</c:choose>
			</td>
   		</tr>
   		<tr align="center">
   			<td align="right" width="20%">�����ˣ�</td>
   			<td align="left" width="20%"><c:out value="${applyGage.������ }"/></td>
   			<td align="right" width="20%">�����˱�ע��</td>
   			<td align="left" width="20%"><c:out value="${applyGage.remark1 }"/></td>
   		</tr>
   		<tr align="center">
   			<td align="right" width="20%">����޸��ˣ�</td>
   			<td align="left" width="20%"><c:out value="${applyGage.modifier }"/></td>
   			<td align="right" width="20%">�޸��˱�ע��</td>
   			<td align="left" width="20%"><c:out value="${applyGage.remark2 }"/></td>
   		</tr>
   		<tr align="center">
   			<td align="right" width="20%">����ʱ�䣺</td>
   			<td align="left" width="20%"><fmt:formatDate value="${applyGage.createTime }" pattern="yyyy-MM-dd"/></td>
   			<td align="right" width="20%">�޸�ʱ�䣺</td>
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