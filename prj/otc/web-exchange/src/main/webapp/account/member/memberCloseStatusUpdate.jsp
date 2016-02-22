<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>会员终止</title>
	</head>
	<body class="st_body" style="overflow-y:hidden">
		<form name="frm" action="${basePath}/account/memberCloseStatus/updateCloseStatus.action"
			method="post" targetType="hidden">
			<div class="div_scromin1">
			<table border="0" width="80%" align="center">
				<tr>
					<td>
						<div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;&nbsp;会员终止</div>
						<table border="0" cellspacing="0" cellpadding="4" width="100%"
					align="center" class="st_bor">
					<tr height="35">
						<td align="right" width="38%">
							会员编号:
						</td>
						<td>
							<input type="text" class="input_text_pwd" id="id" id="id" name="obj.id" value="${obj.id}" readonly="readonly">
						</td>
					</tr>
					<tr height="35">
						<td align="right">
							会员名称:
						</td>
						<td>
							<input type="text" class="input_text_pwd" id="name" name="obj.name" value="${obj.name}" readonly="readonly">
						</td>
					</tr>
					<tr height="35">
						<td align="right">
							会员状态:
						</td>
						<td>
						<input type="hidden" name="obj.compMember.status" value="D" >
							 <input type="text" id="status" name="status" value="${memberStatusMap[obj.compMember.status]}" class="input_text_pwd" readonly="readonly">
						</td>
					</tr>
				</table>
					</td>
				</tr>
			</table>
			</div>
			<div class="tab_pad">
			<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center">
						<tr>
							<td  align="center">
								<button  class="btn_sec" onClick="updateMemberStatus()" id="update">终止</button>
							</td>
							<td align="center">
								<button  class="btn_sec" onclick="window.close()" >关闭</button>
							</td>
						</tr>
					</table>
					</div>
			</form>
		
	</body>
</html>
<script type="text/javascript">
	function updateMemberStatus(){
		frm.submit();
	}


</script>
<%@ include file="/public/footInc.jsp"%>