<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title>市场参数设置</title>
	</head>
	<body leftmargin="0" topmargin="0" class="st_body"
		style="overflow-y: hidden">
		<form name="frm"
			action="${basePath}/tradeManage/openMarketParameters/update.action"
			method="post">
			<%
				String initPresecs = "";
			%>
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
								&nbsp;&nbsp;开市设置
							</div>
							<table border="0" cellspacing="0" cellpadding="4" width="650px"
								height="100" align="center" class="st_bor">
								<input type="hidden" name="obj.marketCode"
									value="${obj.marketCode}">
								<tr height="40">
									<td align="right">开市模式:</td>
									<td>
										手动:<input type="radio"  id="runMode" name="obj.runMode" value="0" <c:if test="${obj.runMode==0}">checked="checked"</c:if> >
										自动:<input type="radio"  id="runMode" name="obj.runMode" value="1" <c:if test="${obj.runMode==1}">checked="checked"</c:if>>
										<span>&nbsp;&nbsp;<font color="red">*</font>(当前开市模式：
										<font color="red">
										<c:if test="${market.runMode==0}">手动</c:if>
										<c:if test="${market.runMode==1}">自动</c:if>
										</font>)</span>
									</td>
									<td align="left" height="40"></td>
								</tr>
								<tr height="40">
									<td align="right" >开市准备提前秒数:</td>
									<td>
										<input type="text" class="input_text" id="initPresecs"
											onblur="myblur('initPresecs')"
											onfocus="myfocus('initPresecs')" name="obj.initPresecs"
											value="${obj.initPresecs}" size="10" maxlength="10">
										<span><font color="red">*</font>(当前开市准备提前秒数：<font
											color="red">${market.initPreSecs}</font>)</span>
									</td>
									<td align="left" height="40">
										<div id="initPresecs_vTip"><%=initPresecs%></div>
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
							<button class="btn_sec" onClick="updateMarket()" id="update">
								修改
							</button>
							&nbsp;&nbsp;&nbsp;
							<button class="btn_sec" onClick="updateMarketInfo()" id="update">
								实时生效
							</button>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>
<script type="text/javascript">
function myblur(userID) {
	var flag = true;
	if ("initPresecs" == userID) {
		flag = initPresecs(userID);
	} else {
		if (!initPresecs("initPresecs"))
			flag = false;
	}
	return flag;
}
//开市准备提前秒数
function initPresecs(userID) {
	var flag = false;
	var str = "请输入≤3600的正整数";
	var innerHTML = "";
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	if (isEmpty(user.value)) {
		innerHTML = "不能为空";
	} else if (!integer(user.value)) {
		innerHTML = str;
	} else if (user.value < 0 || user.value > 3600) {
		innerHTML = str;
	} else {
		innerHTML = '<%=initPresecs%>';
		flag = true;
	}
	vTip.innerHTML = innerHTML;
	if (flag) {
		vTip.className = "";
	} else {
		vTip.className = "onError";
	}
	return flag;
}

function myfocus(userID) {
	/*var vTip = document.getElementById(userID+"_vTip");
	vTip.innerHTML = "不能为空";
	vTip.className = "onFocus";*/

}
function updateMarket() {
	var flag = myblur("all");
	if (!flag) {
		return false;
	}
	if (!isFormChanged(null, null)) {
		alert("没有修改内容");
		return false;
	}
	var vaild = affirm("您确定要操作吗？");
	if (vaild == true) {
		frm.submit();
	} else {
		return false;
	}
}
function updateMarketInfo() {
	var vaild = affirm("您确定要操作吗？");
	if (vaild == true) {
		frm.action="${basePath}/tradeManage/openMarketParameters/updateMarketInfo.action"
		frm.submit();
	} else {
		return false;
	}
}
</script>
<%@ include file="/public/footInc.jsp"%>