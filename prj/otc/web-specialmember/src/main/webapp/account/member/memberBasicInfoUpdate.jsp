<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title></title>
	</head>

	<body leftmargin="0" topmargin="0" class="st_body" style="overflow-y:hidden">
		<form action="${basePath}/account/memberInfo/update.action" name="frm" method="post" targetType="hidden">
		<div class="div_scromax">
			<table border="0" height="300" width="500" align="center" >
				<tr height="100"></tr>
				<tr>
					<td>
							<div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;&nbsp;�޸��л�Ա</div>
						<%@include file="commonMemberTable.jsp" %>
					</td>
				</tr>
			</table>
			</div>
			<table cellspacing="0" cellpadding="0" border="0" align="center" width="500" class="tab_pad">
				<tr>
					<td align="center">
						<button  class="btn_sec" onClick="updateMember()" id="update">�޸�</button>
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
	document.getElementById('id').readOnly=true;
		document.getElementById('id').style.background="#bebebe";
		function checkMemberId(){
			
		}
	function updateMember(){
		var obj=document.getElementById("memberName").value;
	    if(!isNull(obj)){
	        alert('���Ʋ�����Ϊ��');
			frm.memberName.focus();
			return false;
	    }
		frm.submit();
	}
</script>
<%@ include file="/public/footInc.jsp"%>