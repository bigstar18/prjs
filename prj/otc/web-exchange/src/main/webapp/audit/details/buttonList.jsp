<%@ page pageEncoding="GBK" %>

			<input type="hidden" name="applyType" value="${applyType}">
			<input type="hidden" name="buttonClick" value="">
<table border="0" cellspacing="0" cellpadding="0" width="40%" height="100">
	<c:forEach items="${buttonList}" var="result">
			  		<tr>
			  			<td align="center"><button name="buttons" onclick="audit('${result.operateKey}')">${result.operateName}</button></td> 
			  		</tr>
	 </c:forEach>
</table>
