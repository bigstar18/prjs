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
			action="<%=basePath%>sortController.zcjs?funcflg=insert">
			<fieldset width="60%">
				<legend> 
					�������
				</legend>
				<table align="center">
					<tr>
						<td align="right">
							��������:
						</td>
						<td align="center">
							<input type="text" name="sortName" value="${sort.sortName }"/>
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
	if(frm.sortName.value == "")
	{	
		alert('�������������');
		frm.sortName.focus();
		return false;
	}
	frm.submit();
}
function freturn(){
		frm.action = "<%=basePath%>sortController.zcjs?funcflg=getTableList";
		frm.submit();
	}
		</script>


