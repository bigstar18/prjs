<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title></title>
	</head>
	<body leftmargin="0" topmargin="0" class="st_body">
		<form action="${basePath}/account/memberInfo/add.action" name="frm" method="post" targetType="hidden">
			<table border="0" height="340" width="500" align="center">
				<tr height="100"></tr>
				<tr>
					<td>
						   <div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" width="43" height="40" align="absmiddle" />&nbsp;&nbsp;会员信息添加</div>
						<%@include file="commonMemberTable.jsp" %>
							<table cellspacing="0" cellpadding="0" border="0" width="500">
								<tr>
									<td width="250" align="center">
										<div class="st_anbor"><a href="#" class="st_an" name="addMemberInfo" onClick="addMember()">添加</a></div>
									</td>

									<td width="250" align="center">
										<div class="st_anbor"><a href="#" class="st_an" onClick="window.close()">关闭</a></div>
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
	function addMember(){
		if(frm.memberName.value==""){
			alert('名称不允许为空');
			frm.memberName.focus();
			return false;
		}
		frm.submit();
	}
	function checkMemberId(){
		var id=document.getElementById("id").value;
		checkAction.existMemberInfoId(id,function(isExist){
			if(isExist){
				alert('会员编号已存在，请重新添加');
				document.getElementById("id").value="";
				document.getElementById("id").focus();
			}
		});
	
	}
</script>
<%@ include file="/public/footInc.jsp"%>