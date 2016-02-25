<%@ page contentType="text/html;charset=GBK" %>
<html>
<head>
	<%@ include file="headInc.jsp" %>
	<title>操作已完成</title>
</head>
<script language="javascript">
   <c:if test='${not empty result}'>
   <c:choose>
   <c:when test="${result>=0}">
     <c:if test='${not empty result1}'>
       window.location="<%=basePath%>/voucherController.spr?funcflg=voucherCheckDate";
      </c:if>
      <c:if test='${empty result1}'>
       window.location="<%=basePath%>/voucher/accountVoucher.jsp?sign=1";
      </c:if>
    </c:when>
    <c:otherwise>
	window.location="<%=basePath%>/voucher/accountVoucher.jsp";
	</c:otherwise>
	</c:choose> 
	</c:if>
	 <c:if test='${empty result}'>
	 window.location="<%=basePath%>/voucher/accountVoucher.jsp";
	 </c:if>
</script>