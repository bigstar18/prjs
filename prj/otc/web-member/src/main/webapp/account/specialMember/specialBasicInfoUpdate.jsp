<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title></title>
	</head>

	<body leftmargin="0" topmargin="0" class="st_body">
		<form action="${basePath}/account/specialMemberInfo/update.action" name="frm" method="post" targetType="hidden">
			<table border="0" height="300" width="500" align="center" >
				<tr height="100"></tr>
				<tr>
					<td>
							<div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" width="43" height="40" align="absmiddle" />&nbsp;&nbsp;特别会员信息修改</div>
						<%@include file="commonSpecialMemberTable.jsp" %>
							<table cellspacing="0" cellpadding="0" border="0" width="500">
								<tr>
									<td width="250" align="center">
										<div class="st_anbor"><a href="#" value="updateSpecialMemberInfo" onClick="updatSpecialMember()" class="st_an">修改</a></div>
									</td>

									<td width="250" align="center">
										<div class="st_anbor"><a href="#" onClick="window.close()" class="st_an">关闭</a></div>
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