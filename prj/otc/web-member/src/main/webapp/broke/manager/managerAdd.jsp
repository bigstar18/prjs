<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<script type='text/javascript' src='<%=basePath%>/dwr/engine.js'>
</script>
<script type='text/javascript' src='<%=basePath%>/dwr/util.js'>
</script>
<script type="text/javascript"
	src='<%=basePath%>/dwr/interface/checksAction.js' />
</script>
<html>
	<head>
		<title>客户代表信息添加</title>
	</head>
	<body leftmargin="0" topmargin="0" style="overflow-y: hidden">
		<form action="${basePath}/broke/manager/add.action" name="frm"
			method="post" targetType="hidden">
			<div style="overflow: auto; height: 370px;">
				<table border="0" width="100%" align="center">
					<tr>
						<td>
							<div class="st_title">
								<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;客户代表信息添加
							</div>
							<%@include file="commonTable.jsp"%>
						</td>
					</tr>
				</table>
			</div>
			<div class="tab_pad">
				<table cellspacing="0" cellpadding="0" border="0" width="100%"
					align="center">
					<tr>
						<td align="center">
							<button class="btn_sec" onClick="addManager()" id="add">
								添加
							</button>
						</td>
						<td align="center">
							<button class="btn_sec" onClick="window.close()">
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
function addManager() {
	if (!myblur("all")) {
		return false;
	}
	var vaild = window.confirm("您确定要操作吗？");
	if (vaild == true) {
		frm.submit();
	} else {
		return false;
	}
}
function checkManagerId(userID) {
	var innerHTML = "";
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	var flag = false;
	if (isEmpty(user.value + "")) {
		innerHTML = "不能为空";
	} else if (isNaN(user.value)) {
		innerHTML = "不合法的字符";
	} else if (user.value.length != 3) {
		innerHTML = "请输入3位的数字";
	} else {
		flag = true;
	}
	vTip.innerHTML = innerHTML;
	if (flag) {
		vTip.className = "";
		var id = document.getElementById("managerNo").value;
	checksAction.existManagerId(id, function(isExist) {
		if (isExist) {
			document.getElementById("managerNo").value="";
			alert('经纪人代码已存在，请重新添加');
			document.getElementById("managerNo").focus();
		}
	});
	} else {
		vTip.className = "onError";
	}
	//var id = document.getElementById("managerNo").value;
	//checksAction.existManagerId(id, function(isExist) {
		//if (isExist) {
			//document.getElementById("managerNo").value="";
			//alert('经纪人代码已存在，请重新添加');
			//document.getElementById("managerNo").focus();
		//}
	//});
	return true;
}
</script>
<%@ include file="/public/footInc.jsp"%>