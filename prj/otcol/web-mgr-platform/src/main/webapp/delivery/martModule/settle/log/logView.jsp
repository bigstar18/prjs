<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../../public/session.jsp"%>

<html xmlns:MEBS>
  <head>
    <title><%=TITLE%></title>
  </head>
  
  <body>
  	<form name="frm" action="<%=basePath%>servlet/settleLogController.${POSTFIX}?funcflg=operateLogReturn" method="post">
	<fieldset width="95%">
		<legend>������־��ϸ</legend>
	  <table align="center" border="0" width="100%" height="270">
  		<tr align="center">
  			<td align="right" width="30%">�����˱�ţ�</td><td align="left"><c:out value="${oprLog.id}"/></td>
  		</tr>
  		<tr align="center">
  			<td align="right" width="30%">���������ͣ�</td>
  			<td align="left">
  				<c:choose>
  					<c:when test="${oprLog.type==0 }">�г�</c:when>
  					<c:when test="${oprLog.type==1 }">�ֿ�</c:when>
  					<c:otherwise>������</c:otherwise>
  				</c:choose>
  			</td>
  		</tr>
  		<tr align="center">
  			<td align="right" width="30%">����ʱ�䣺</td>
  			<td align="left">
  				<fmt:formatDate value="${oprLog.operatime}" pattern="yyyy-MM-dd"/>
  			</td>
  		</tr>
  		<tr align="center">
  			<td align="right" width="30%">�������ݣ�</td>
  			<td align="left">
  				<textarea class="normal" rows="5" cols="40"><c:out value="${oprLog.content}"/>
  				</textarea>  			
  			</td>
  		</tr>	 
	  </table>
	  </fieldset>
	  <br>
	  <table align="center">
	  <tr align="center"><td align="center">
	  <input class="smlbtn" type="button" value="����" onclick="returnListPage();">
	  </td></tr>
	  </table>
	</form>
  </body>
</html>
<script type="text/javascript">
<!--
function returnListPage()
{
	frm.submit();
}
//-->
</script>

