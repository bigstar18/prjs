<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title>强平设置</title>
	</head>
	<body leftmargin="0" topmargin="0" class="st_body"
		style="overflow-y: hidden">
		<form name="frm"
			action="${basePath}/customerClosePosition/customerList/update.action"
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
								&nbsp;&nbsp;客户强平
							</div>
							<table border="0" cellspacing="0" cellpadding="4" width="100%"
								height="100" align="center" class="st_bor">
								<tr height="40">
									<td align="left" width="25%">
										交易账号:<input type="text" name="customerNo" id="customerNo"  />
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
	 if("customerNo"==userID){
		flag = customerNo(userID);
	}else{
		if(!customerNo("customerNo"))return false;
	}
	 return flag;
 }
function customerNo(userID){
	var user = document.getElementById(userID);
	var flag = false;
	if(isEmpty(user.value+"")){
		alert("交易账号不能为空");
	}else{
		user.value = mytrim(user.value);
		flag=true;
	}
	return flag;
}
function backList(){
	frm.action = '${basePath}/customerClosePosition/customerList/list.action?sortName=primary.customerNo';
	frm.submit();
}
</script>
<%@ include file="/public/footInc.jsp"%>