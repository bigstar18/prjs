<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/session.jsp"%>
<html >
<head>
  	
<title>商品参数</title>
</head>
<body >
<fieldset width="100%">
  <legend>商品参数(合同号：${tradeNo })</legend>
  <table border="0" cellspacing="0" cellpadding="0" width="100%"> 
  	<c:forEach items="${list}" var="list">
	   <tr>	
	   			<td align="center">${list.name }：</td>
	 			  <td >${list.value }</td>  				
	  </tr> 
	  <tr><td>&nbsp;</td></tr>
	</c:forEach>  	
	<tr><td align="center" colspan="2"><button class="smlbtn" type="button" onclick="window.close()">关闭窗口</button></td></tr>
</table>
 
</fieldset>
</body>
</html>

