<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title>强平设置</title>
	</head>
	<body leftmargin="0" topmargin="0" class="st_body"
		style="overflow-y: hidden">
		<form name="frm"
			action="${basePath}/memberClosePosition/memberList/update.action"
			method="post" targetType="hidden">
			<div>
				<table border="0" width="50%" align="center">
					<tr>
						<td height="100">
							&nbsp;
						</td>
					</tr>
					<tr>
						<td>
							<div class="st_title">
								<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />
								&nbsp;&nbsp;综合会员强平
							</div>
							<table border="0" cellspacing="0" cellpadding="4" width="100%"
								height="100" align="center" class="st_bor">
								<tr height="40">
									<td align="left" width="25%">
										综合会员编号:<input type="text" name="memberNo" id="memberNo"  />
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
			<div>
				<table cellspacing="0" cellpadding="0" border="0" width="80%"
					align="center">
					<tr>
						<td align="center" height="20">
							&nbsp;
						</td>
					</tr>
					<tr>
						<td align="center">
							<button class="btn_sec" onClick="updateForceClose()" id="update">
								强平
							</button>&nbsp;&nbsp;&nbsp;&nbsp;
							<button class="btn_sec" onClick="window.close();">
								关闭
							</button>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>
<script type="text/javascript">
function updateForceClose() {
	if(!myblur("all")){return false;}
	var vaild = affirm("您确定要操作吗？");
	if (vaild == true) {
		frm.submit();
	} else {
		return false;
	}
}
function myblur(userID){
	var flag = true;
	 if("memberNo"==userID){
		flag = memberNo(userID);
	}else{
		if(!memberNo("memberNo"))return false;
	}
	 return flag;
 }
function memberNo(userID){
	var user = document.getElementById(userID);
	var flag = false;
	if(isEmpty(user.value+"")){
		alert("综合会员编号不能为空");
		frm.memberNo.focus();
	}else{
		user.value = mytrim(user.value);
		flag=true;
	}
	return flag;
}
</script>
<%@ include file="/public/footInc.jsp"%>