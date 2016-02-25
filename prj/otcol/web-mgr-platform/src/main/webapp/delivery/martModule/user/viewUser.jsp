<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../public/session.jsp"%>

<html xmlns:MEBS>
  <head>
    <title><%=TITLE%></title>
  </head>
  <body>
  	<form name="frm" action="" method="post">
	<fieldset width="95%">
		<legend>管理员信息明细</legend>
	  <table align="center" border="0" width="100%" height="270">
  		<tr align="center">
  			<td align="right" width="30%">管理员编号：</td><td align="left"><c:out value="${user.userId}"/></td>
  		</tr>
  		<tr align="center">
  			<td align="right" width="30%">管理员名称：</td><td align="left"><c:out value="${user.name}"/></td>
  		</tr>
  		<tr align="center">
  			<td align="right" width="30%">所属仓库：</td>
  			<td align="left">
  				<c:if test="${user.warehousename==null }">
	  					暂无所属仓库
	  				</c:if>
	  				${user.warehousename }
  			</td>
  		</tr>
  		<tr align="center">
  			<td align="right" width="30%">管理员级别：</td>  			
  			<td align="left">
					<c:choose>
						<c:when test="${user.roleStatus==0 }">超级管理员</c:when>
						<c:otherwise>普通管理员</c:otherwise>
					</c:choose>
			</td>
  		</tr>	 
	  </table>
	  </fieldset>
	  <br>
	  <table align="center">
	  <tr align="center"><td align="center">
	  <input class="smlbtn" type="button" value="返回" onclick="returnListPage();">
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

