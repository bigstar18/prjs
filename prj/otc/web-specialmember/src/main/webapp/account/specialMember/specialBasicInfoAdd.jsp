<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title></title>
	</head>
	<body leftmargin="0" topmargin="0" class="st_body"  style="overflow-y:hidden">
		<form action="${basePath}/account/specialMemberInfo/add.action" name="frm" method="post" targetType="hidden">
		  <div class="div_scromid">
			<table border="0" height="340" width="500" align="center">
				<tr height="60"></tr>
				<tr>
					<td>
						<div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" width="43" height="40" align="absmiddle" />&nbsp;&nbsp;�ر��Ա��Ϣ���</div>
						<%@include file="commonSpecialMemberTable.jsp" %>
					</td>
				</tr>
				</table>
			</div>
			<table cellspacing="0" align="center" cellpadding="0" border="0" width="100%" style="padding-top:20px;">
					    <tr>
						<td align="center">
							<button  class="btn_sec" onClick="addSpecialMemberInfo()" id="add">���</button>
						</td>

						<td align="center">
							<button  class="btn_sec" onClick="window.close()">�ر�</button>
						</td>
					</tr>
		   </table>
		</form>
	</body>
</html>
<script type="text/javascript">
	function addSpecialMemberInfo(){
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