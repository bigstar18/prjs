<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title></title>
	</head>

	<body leftmargin="0" topmargin="0" class="st_body">
		<form action="${basePath}/account/specialMemberInfo/add.action" name="frm" method="post" targetType="hidden">
			<table border="0" height="300" width="500" align="center">
				<tr height="100"></tr>
				<tr>
					<td>
							<div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" width="43" height="40" align="absmiddle" />&nbsp;&nbsp;�ر��Ա��Ϣ���</div>
						<%@include file="commonSpecialMemberTable.jsp" %>
							<table cellspacing="0" cellpadding="0" border="0" width="500">
								<tr>
									<td width="250" align="center">
										<div class="st_anbor"><a href="#" name="addSpecialMemberInfo" onClick="addSpecialMember()" class="st_an">���</a></div>
									</td>

									<td width="250" align="center">
										<div class="st_anbor"><a href="#" onClick="window.close()" class="st_an">�ر�</a></div>
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
	function addSpecialMember(){
		if(frm.memberName.value==""){
			alert('���Ʋ�����Ϊ��');
			frm.memberName.focus();
			return false;
		}
		frm.submit();
	}
	
	function checkSpecialMemberId(){
		var id=document.getElementById("id").value;
		checkAction.existSpecialMemberId(id,function(isExist){
			if(isExist){
				alert('�ر��Ա����Ѵ��ڣ����������');
				document.getElementById("id").value="";
				document.getElementById("id").focus();
			}
		});
	
	}
</script>
<%@ include file="/public/footInc.jsp"%>