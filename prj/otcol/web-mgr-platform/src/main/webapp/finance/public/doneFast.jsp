<%@ page contentType="text/html;charset=GBK" %>
<html>
<head>
	<%@ include file="headInc.jsp" %>
	<title>操作已完成</title>
</head>
<script language="javascript">
   <c:if test='${not empty result}'>
   <c:choose>
   <c:when test="${result>0}">
    window.location="<%=basePath%>/voucherController.spr?funcflg=voucherEditForward&sign=edit&voucherNo=" +${result};
    </c:when>
    <c:otherwise>
	window.location="<%=basePath%>/voucher/createVoucherFast.jsp";
	</c:otherwise>
	</c:choose> 
	</c:if>
</script>