<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title>�޸Ļ�Ա</title>
	</head>

	<body leftmargin="0" topmargin="0" class="st_body">
		<form action="${basePath}/account/memberInfo/update.action" name="frm" method="post" targetType="hidden">
			<table border="0" height="300" width="500" align="center" >
				<tr height="100"></tr>
				<tr>
					<td>
							<div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" width="43" height="40" align="absmiddle" />&nbsp;&nbsp;�޸��л�Ա</div>
						<%@include file="commonMemberTable.jsp" %>
							<table cellspacing="0" cellpadding="0" border="0" width="500">
								<tr>
									<td width="250" align="center">
										<div class="st_anbor"><a href="#" onClick="updatmember()" value="updateMemberInfo" class="st_an">�޸�</a></div>
									</td>

									<td width="250" align="center">
										<div class="st_anbor"><a href="#" onClick="window.close()"  value="updateMemberInfo" class="st_an">�ر�</a></div>
									</td>
								</tr>
							</table>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
<script type="text/javascript">
	document.getElementById('id').readOnly=true;
		document.getElementById('id').style.background="#bebebe";
		function checkMemberId(){
			
		}
	function updatmember(){
		if(frm.memberName.value==""){
			alert('���Ʋ�����Ϊ��');
			frm.memberName.focus();
			return false;
		}
		frm.submit();
	}
</script>
<%@ include file="/public/footInc.jsp"%>