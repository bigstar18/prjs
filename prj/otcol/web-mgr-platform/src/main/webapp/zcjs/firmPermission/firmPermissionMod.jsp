<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/fmt' prefix='fmt'%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title></title>
		<script type="text/javascript" src="javascript/common.js"></script>
		<%@ include file="../public/session.jsp"%>
	</head>
	<body>
		<form name="frm" method="post"
			action="<%=basePath%>firmPermissionController.zcjs?funcflg=updateFirmPermission">
			<fieldset width="60%">
				<legend> 
					������Ȩ���޸�
				</legend>
				<table align="center" border="0">
					<input type="hidden" name="firmId" value="${firmPermission.firmId }"></input>
					<tr>
						<td align="center">
							�����̴��룺
						</td>
						<td align="center">
							${firmPermission.firmId }
						</td>
					</tr>
					<tr>
						<td align="center">
							<input name="purview" type="checkbox" value="buyListing" <c:if test="${firmPermission.buyListing=='Y'}">checked="checked" </c:if>/>
						</td>
						<td>
							�����Ȩ��
						</td>
					</tr>	
					<tr>
						<td align="center">
							<input name="purview" type="checkbox" value="sellListing" <c:if test="${firmPermission.sellListing=='Y'}">checked="checked" </c:if>/>
						</td>
						<td>
							������Ȩ��
						</td>
					</tr>
					<tr>
						<td align="center">
							<input name="purview" type="checkbox" value="buyDelist" <c:if test="${firmPermission.buyDelist=='Y'}">checked="checked" </c:if>/>
						</td>
						<td>
							��ժ��Ȩ��
						</td>
					</tr>		
					<tr>
						<td align="center">
							<input name="purview" type="checkbox" value="sellDelist" <c:if test="${firmPermission.sellDelist=='Y'}">checked="checked" </c:if>/>
						</td>
						<td>
							��ժ��Ȩ��
						</td>
					</tr>		
					<tr>
						<td align="center">
							<input name="purview" type="checkbox" value="sellRegstock" <c:if test="${firmPermission.sellRegstock=='Y'}">checked="checked" </c:if>/>
						</td>
						<td>
							���ֵ�Ȩ��
						</td>
					</tr>						
				</table>
				<table align="center">
					<tr>
						<td align="center">
							<input type="button" value="����" onclick="save()" />
						</td>
						<td>
							&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
						</td>
						<td align="center">
							<input type="button" value="����" onclick="freturn()" />
						</td>
					</tr>
				</table>
			</fieldset>
		</form>

	</body>
</html>
<script type="text/javascript">
function save(){		
	if(confirm("ȷ���޸Ĵ˽�����Ȩ��ô?")){
		frm.submit();
	}
	
}
function freturn(){
		frm.action = "<%=basePath%>firmPermissionController.zcjs?funcflg=list";
		frm.submit();
	}
		</script>


