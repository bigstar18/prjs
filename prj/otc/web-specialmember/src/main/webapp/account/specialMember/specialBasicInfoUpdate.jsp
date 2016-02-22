<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title></title>
	</head>

	<body leftmargin="0" topmargin="0" class="st_body" style="overflow-y:hidden">
		<form action="${basePath}/account/specialMemberInfo/update.action" name="frm" method="post" targetType="hidden">
		<div class="div_scromax">
			<table border="0" height="300" width="500" align="center" >
				<tr height="100"></tr>
				<tr>
					<td>
						<div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" width="43" height="40" align="absmiddle" />&nbsp;&nbsp;特别会员信息修改</div>
						<%@include file="commonSpecialMemberTable.jsp" %>
					</td>
				</tr>
			</table>
			<table cellspacing="0" cellpadding="0" border="0" width="500" align="center" class="tab_pad">
				<tr>
					<td width="250" align="center">
						<button  class="btn_sec" onClick="updatSpecialMember()" id="update">修改</button>
					</td>

					<td width="250" align="center">
						<button  class="btn_sec" onClick="window.close()">关闭</button>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
<script type="text/javascript">
	document.getElementById('id').readOnly=true;
		document.getElementById('id').style.background="#bebebe";
	function checkSpecialMemberId(){
			
		}
	function updatSpecialMember(){
		if(frm.memberName.value==""){
			alert('名称不允许为空');
			frm.memberName.focus();
			return false;
		}
		frm.submit();
	}
</script>
<%@ include file="/public/footInc.jsp"%>