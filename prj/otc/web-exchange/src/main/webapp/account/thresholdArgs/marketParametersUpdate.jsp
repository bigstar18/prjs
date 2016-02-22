<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title>市场参数设置</title>
	</head>
	<body leftmargin="0" topmargin="0" class="st_body">
		<form name="frm" id="frm"
			action="${basePath}/settlement/marketParameters/update.action"
			method="post">
			<%
				String clearDelaySecs = "";
				String customerOpenMinFunds = "";
				String memberOpenMinFunds = "";
				String specialMemberOpenMinFunds = "";
				String brokeMemberOpenMinFunds = "";
				String customerOrgOpenMinFunds = "";
			%>
			<div>
				<table border="0" width="70%" align="center">
					<tr>
						<td height="30">
							&nbsp;
						</td>
					</tr>
					<tr>
						<td>
							<div class="div_cxtjd">
								<img src="<%=skinPath%>/cssimg/13.gif" />&nbsp;最小入金设置
							</div>
							<table border="0" cellspacing="0" cellpadding="4" width="100%"
								style="table-layout: fixed" align="center" class="st_bor">
								<input type="hidden" name="obj.marketCode"
									value="${obj.marketCode}">
								<tr height="40">
									<td align="right" width="200">
										客户激活最小入金:
									</td>
									<td width="220px" align="left">
										<input type="text" class="input_text"
											style="text-align: right" id="customerOpenMinFunds"
											onblur="myblur('customerOpenMinFunds')"
											onfocus="myfocus('customerOpenMinFunds')"
											value="<fmt:formatNumber value="${obj.customerOpenMinFunds}"   pattern="###,##0.00"/>"
											name="obj.customerOpenMinFunds" size="10" maxlength="15">
										<span>（元）<strong class="check_input">&nbsp;*</strong> </span>
									</td>
									<td align="left" height="40"
										style="padding-right: 70px;">
										<div id="customerOpenMinFunds_vTip"><%=customerOpenMinFunds%></div>
									</td>
								</tr>
								<!-- 
								<tr height="40">
									<td align="right">
										机构客户激活最小入金:
									</td>
									<td>
										<input type="text" class="input_text"
											style="text-align: right" id="customerOrgOpenMinFunds"
											onblur="myblur('customerOrgOpenMinFunds')"
											onfocus="myfocus('customerOrgOpenMinFunds')"
											name="obj.customerOrgOpenMinFunds"
											value="<fmt:formatNumber value="${obj.customerOrgOpenMinFunds}"   pattern="###,##0.00"/>"
											size="10" maxlength="15">
										<span>（元）<strong class="check_input">&nbsp;*</strong> </span>
									</td>
									<td align="left" height="40" style="padding-right: 70px;">
										<div id="customerOrgOpenMinFunds_vTip"><%=customerOrgOpenMinFunds%></div>
									</td>
								</tr> -->
								<tr height="40">
									<td align="right">
										会员激活最小入金:
									</td>
									<td>
										<input type="text" class="input_text" id="memberOpenMinFunds"
											style="text-align: right" name="obj.memberOpenMinFunds"
											onblur="myblur('memberOpenMinFunds')"
											onfocus="myfocus('memberOpenMinFunds')"
											value="<fmt:formatNumber value="${obj.memberOpenMinFunds}"   pattern="###,##0.00"/>"
											size="10" maxlength="15">
										<span>（元）<strong class="check_input">&nbsp;*</strong> </span>
									</td>
									<td align="left" height="40" style="padding-right: 70px;">
										<div id="memberOpenMinFunds_vTip"><%=memberOpenMinFunds%></div>
									</td>
								</tr>
								<tr height="40">
									<td align="right">
										特别会员激活最小入金:
									</td>
									<td>
										<input type="text" class="input_text"
											style="text-align: right" id="specialMemberOpenMinFunds"
											name="obj.specialMemberOpenMinFunds"
											onblur="myblur('specialMemberOpenMinFunds')"
											onfocus="myfocus('specialMemberOpenMinFunds')"
											value="<fmt:formatNumber value="${obj.specialMemberOpenMinFunds}"  pattern="###,##0.00"/>"
											size="10" maxlength="15">
										<span>（元）<strong class="check_input">&nbsp;*</strong> </span>
									</td>
									<td align="left" height="40" style="padding-right: 70px;">
										<div id="specialMemberOpenMinFunds_vTip"><%=specialMemberOpenMinFunds%></div>
									</td>
								</tr>
								<tr height="40">
									<td align="right">
										经纪会员激活最小入金:
									</td>
									<td>
										<input type="text" class="input_text"
											style="text-align: right" id="brokeMemberOpenMinFunds"
											name="obj.brokeMemberOpenMinFunds"
											onblur="myblur('brokeMemberOpenMinFunds')"
											onfocus="myfocus('brokeMemberOpenMinFunds')"
											value="<fmt:formatNumber value="${obj.brokeMemberOpenMinFunds}"  pattern="###,##0.00"/>"
											size="10" maxlength="15">
										<span>（元）<strong class="check_input">&nbsp;*</strong> </span>
									</td>
									<td align="left" height="40" style="padding-right: 70px;">
										<div id="brokeMemberOpenMinFunds_vTip"><%=brokeMemberOpenMinFunds%></div>
									</td>
								</tr>
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
												保存
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
	if ("customerOpenMinFunds" == userID) {
		flag = customerOpenMinFunds(userID);
	}
	 //else if ("customerOrgOpenMinFunds" == userID) {
	 //	flag = customerOrgOpenMinFunds(userID);
	 //}
	 else if ("memberOpenMinFunds" == userID) {
		flag = memberOpenMinFunds(userID);
	} else if ("specialMemberOpenMinFunds" == userID) {
		flag = specialMemberOpenMinFunds(userID);
	} else if ("brokeMemberOpenMinFunds" == userID) {
		flag = brokeMemberOpenMinFunds(userID);
	} else {

		if (!customerOpenMinFunds("customerOpenMinFunds"))
			flag = false;
		//if (!customerOrgOpenMinFunds("customerOrgOpenMinFunds"))
		//	flag = false;
		if (!memberOpenMinFunds("memberOpenMinFunds"))
			flag = false;
		if (!specialMemberOpenMinFunds("specialMemberOpenMinFunds"))
			flag = false;
		if (!brokeMemberOpenMinFunds("brokeMemberOpenMinFunds"))
			flag = false;
	}
	return flag;
}
//结算秒数
function clearDelaySecs(userID) {
	var flag = false;
	var str = "";
	var innerHTML = "";
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	if (isEmpty(user.value)) {
		innerHTML = "不能为空";
	} else if (!integer(user.value)) {
		innerHTML = str + "必须为整数";
	} else {
		innerHTML = '<%=clearDelaySecs%>';
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

//客户激活最小入金
function customerOpenMinFunds(userID) {
	var flag = false;
	var str = "请输入≥0的数字";
	var innerHTML = "";
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	removeStr(userID);
	if (isEmpty(user.value)) {
		innerHTML = "不能为空";
	} else if (user.value < 0) {
		innerHTML = str;
	} else if (!flote(user.value, 2)) {
		innerHTML = "最多两位小数的数字";
	} else {
		innerHTML = '<%=customerOpenMinFunds%>';
		transStr(userID);
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
//机构客户激活最小入金
function customerOrgOpenMinFunds(userID) {
	var flag = false;
	var str = "请输入≥0的数字";
	var innerHTML = "";
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	removeStr(userID);
	if (isEmpty(user.value)) {
		innerHTML = "不能为空";
	} else if (user.value < 0) {
		innerHTML = str;
	} else if (!flote(user.value, 2)) {
		innerHTML = "最多两位小数的数字";
	} else {
		innerHTML = '<%=customerOrgOpenMinFunds%>';
		transStr(userID);
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
//会员激活最小入金
function memberOpenMinFunds(userID) {
	var flag = false;
	var str = "请输入≥0的数字";
	var innerHTML = "";
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	removeStr(userID);
	if (isEmpty(user.value)) {
		innerHTML = "不能为空";
	} else if (user.value < 0) {
		innerHTML = str;
	} else if (!flote(user.value, 2)) {
		innerHTML = "最多两位小数的数字";
	} else {
		innerHTML = '<%=memberOpenMinFunds%>';
		transStr(userID);
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
//特别会员激活最小入金
function specialMemberOpenMinFunds(userID) {
	var flag = false;
	var str = "请输入≥0的数字";
	var innerHTML = "";
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	removeStr(userID);
	if (isEmpty(user.value)) {
		innerHTML = "不能为空";
	} else if (user.value < 0) {
		innerHTML = str;
	} else if (!flote(user.value, 2)) {
		innerHTML = "最多两位小数的数字";
	} else {
		innerHTML = '<%=specialMemberOpenMinFunds%>';
		transStr(userID);
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
//经纪激活最小入金
function brokeMemberOpenMinFunds(userID) {
	var flag = false;
	var str = "请输入≥0的数字";
	var innerHTML = "";
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	removeStr(userID);
	if (isEmpty(user.value)) {
		innerHTML = "不能为空";
	} else if (user.value < 0) {
		innerHTML = str;
	} else if (!flote(user.value, 2)) {
		innerHTML = "最多两位小数的数字";
	} else {
		innerHTML = '<%=brokeMemberOpenMinFunds%>';
		transStr(userID);
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
	vTip.className = "";*/

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

</script>
<%@ include file="/public/footInc.jsp"%>