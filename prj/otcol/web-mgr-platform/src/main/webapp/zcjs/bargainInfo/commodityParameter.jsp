<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/session.jsp"%>
<html >
<head>
  	
<title>��Ʒ����</title>
</head>
<body >
<fieldset width="100%">
  <legend>��Ʒ����(��ͬ�ţ�${tradeNo })</legend>
  <table border="0" cellspacing="0" cellpadding="0" width="100%"> 
  	<c:forEach items="${list}" var="list">
	   <tr>	
	   			<td align="center">${list.name }��</td>
	 			  <td >${list.value }</td>  				
	  </tr> 
	  <tr><td>&nbsp;</td></tr>
	</c:forEach>  	
	<tr><td align="center" colspan="2"><button class="smlbtn" type="button" onclick="window.close()">�رմ���</button></td></tr>
</table>
 
</fieldset>
</body>
</html>

