<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../public/session.jsp"%>

<html xmlns:MEBS>
  <head>
    <title><%=TITLE%></title>
  </head>
  <body>
  	<form name="frm" action="" method="post">
	<fieldset width="95%">
		<legend>����Ա��Ϣ��ϸ</legend>
	  <table align="center" border="0" width="100%" height="270">
  		<tr align="center">
  			<td align="right" width="30%">����Ա��ţ�</td><td align="left"><c:out value="${user.userId}"/></td>
  		</tr>
  		<tr align="center">
  			<td align="right" width="30%">����Ա���ƣ�</td><td align="left"><c:out value="${user.name}"/></td>
  		</tr>
  		<tr align="center">
  			<td align="right" width="30%">�����ֿ⣺</td>
  			<td align="left">
  				<c:if test="${user.warehousename==null }">
	  					���������ֿ�
	  				</c:if>
	  				${user.warehousename }
  			</td>
  		</tr>
  		<tr align="center">
  			<td align="right" width="30%">����Ա����</td>  			
  			<td align="left">
					<c:choose>
						<c:when test="${user.roleStatus==0 }">��������Ա</c:when>
						<c:otherwise>��ͨ����Ա</c:otherwise>
					</c:choose>
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
	frm.action = "<%=basePath%>servlet/userController.${POSTFIX}?funcflg=userReturn";
	frm.submit();
}
//-->
</script>

