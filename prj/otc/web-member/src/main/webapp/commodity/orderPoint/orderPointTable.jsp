<%@ page contentType="text/html;charset=GBK"%>
<%
	String l_Open_Point = "";
	String stopLossPoint = "";
	String stopProfitPoint = "";
	String min_M_OrderPoint = "";
	String max_M_OrderPoint = "";
	String m_OrderPoint = "";
%>
<table border="0" width="100%" align="center">
	<tr height="30"></tr>
	<tr>
		<td>
		<div class="div_cxtjd">
				<img src="<%=skinPath%>/cssimg/13.gif" />&nbsp;&nbsp;默认委托点差</div>
			<table border="0" cellspacing="0" cellpadding="4" width="100%"
				align="center" class="st_bor">
				<input type="hidden" name="obj.memberFirmId"
					value="${obj.memberFirmId}">
				<input type="hidden" name="obj.commodityId"
					value="${obj.commodityId}">
				<input type="hidden" id="id" class="input_text_pwdmin"
							name="obj.orderPointId" value="${obj.commodityId}"
							readonly="readonly">
				
				<tr height="40">
					<td align="right" width="25%">
						商品名称:
					</td>
					<td colspan="2">
						<input type="text" id="name" class="input_text_pwdmin"
							name="obj.commodityName" value="${obj.commodityName}"
							readonly="readonly">
					</td>
				</tr>
				<tr>
					<td align="right">
						类型:
					</td>
					<td colspan="2">
						<input type="text" id="firmName" class="input_text_pwdmin"
							name="obj.firmName" value="${firmDisMap[obj.memberFirmId]}"
							readonly="readonly">
					</td>
				</tr>
				<tr height="40">
					<td align="right" width="25%">
						指价建仓点差:
					</td>
					<td width="40%">
						<input type="text" class="input_text" id="l_Open_Point" style="text-align:right"
							onblur="myblur('l_Open_Point')" onfocus="myfocus('l_Open_Point')"
							name="obj.l_Open_Point" value="${obj.l_Open_Point}">
					</td>
					<td align="left" height="40" style="padding-right: 10px;">
						<div id="l_Open_Point_vTip"><%=l_Open_Point%></div>
					</td>
				</tr>
				<tr height="40">
					<td align="right">
						止损下单点差:
					</td>
					<td>
						<input type="text" class="input_text" id="stopLossPoint" style="text-align:right"
							onblur="myblur('stopLossPoint')"
							onfocus="myfocus('stopLossPoint')" name="obj.stopLossPoint"
							value="${obj.stopLossPoint}">
					</td>
					<td align="left" height="40" style="padding-right: 10px;">
						<div id="stopLossPoint_vTip" ><%=stopLossPoint%></div>
					</td>
				</tr>
				<tr height="40">
					<td align="right">
						止盈下单点差:
					</td>
					<td>
						<input type="text" class="input_text" id="stopProfitPoint" style="text-align:right"
							onblur="myblur('stopProfitPoint')"
							onfocus="myfocus('stopProfitPoint')" name="obj.stopProfitPoint"
							value="${obj.stopProfitPoint}">
					</td>
					<td align="left" height="40" style="padding-right: 10px;">
						<div id="stopProfitPoint_vTip"><%=stopProfitPoint%></div>
					</td>
				</tr>
				<tr height="40">
					<td align="right">
						市价点差最小值:
					</td>
					<td>
						<input type="text" class="input_text" id="min_M_OrderPoint" style="text-align:right"
							onblur="myblur('min_M_OrderPoint')"
							onfocus="myfocus('min_M_OrderPoint')" name="obj.min_M_OrderPoint"
							value="${obj.min_M_OrderPoint}">
					</td>
					<td align="left" height="40" style="padding-right: 10px;">
						<div id="min_M_OrderPoint_vTip" ><%=min_M_OrderPoint%></div>
					</td>
				</tr>
				<tr height="40">
					<td align="right">
						市价点差最大值:
					</td>
					<td>
						<input type="text" class="input_text" id="max_M_OrderPoint" style="text-align:right"
							onblur="myblur('max_M_OrderPoint')"	onfocus="myfocus('max_M_OrderPoint')" 
							name="obj.max_M_OrderPoint"		value="${obj.max_M_OrderPoint}">
					</td>
					<td align="left" height="40" style="padding-right: 10px;">
						<div id="max_M_OrderPoint_vTip" ><%=max_M_OrderPoint%></div>
					</td>
				</tr>
				<tr height="40">
					<td align="right">
						默认市价点差:
					</td>
					<td>
						<input type="text" class="input_text" id="m_OrderPoint" style="text-align:right"
							onblur="myblur('m_OrderPoint')" onfocus="myfocus('m_OrderPoint')"
							name="obj.m_OrderPoint" value="${obj.m_OrderPoint}">
					</td>
					<td align="left" height="40" style="padding-right: 10px;">
						<div id="m_OrderPoint_vTip" ><%=m_OrderPoint%></div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<script type="text/javascript">
function myblur(userID) {
	var flag = true;
	if ("l_Open_Point" == userID) {
		flag = l_Open_Point(userID);
	} else if ("stopLossPoint" == userID) {
		flag = stopLossPoint(userID);
	} else if ("stopProfitPoint" == userID) {
		flag = stopProfitPoint(userID);
	} else if ("min_M_OrderPoint" == userID) {
		flag = min_M_OrderPoint(userID);
	} else if ("max_M_OrderPoint" == userID) {
		flag = max_M_OrderPoint(userID);
	} else if ("m_OrderPoint" == userID) {
		flag = m_OrderPoint(userID);
	} else {
		if (!l_Open_Point("l_Open_Point")) {
			flag = false;
		}
		if (!stopLossPoint("stopLossPoint")) {
			flag = false;
		}
		if (!stopProfitPoint("stopProfitPoint")) {
			flag = false;
		}
		if (!min_M_OrderPoint("min_M_OrderPoint")) {
			flag = false;
		}
		if (!max_M_OrderPoint("max_M_OrderPoint")) {
			flag = false;
		}
		if (!m_OrderPoint("m_OrderPoint")) {
			flag = false;
		}
	}
	return flag;
}
function l_Open_Point(userID) {
	var innerHTML = "";
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	var str = "请输入≥0且≤200的整数";
	var flag = false;
	if (isEmpty('' + user.value)) {
		innerHTML = "不能为空";
	} else if (!integer(user.value)) {
		innerHTML = str;
	} else if ((user.value < 0 || user.value > 200)) {
		innerHTML = str ;
	} else {
		innerHTML = "<%=l_Open_Point%>";
		flag = true;
	}
	if (flag) {
		vTip.className = "";
	} else {
		vTip.className = "onError";
	}
	vTip.innerHTML = innerHTML;
	return flag;
}
function stopLossPoint(userID) {
	var innerHTML = "";
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	var str = "请输入≥0且≤200的整数";
	var flag = false;
	if (isEmpty('' + user.value)) {
		innerHTML = "不能为空";
	} else if (!integer(user.value)) {
			innerHTML = str;
	} else if ((user.value < 0 || user.value > 200)) {
			innerHTML = str;
	} else {
		innerHTML = "<%=stopLossPoint%>";
		flag = true;
	}
	if (flag) {
		vTip.className = "";
	} else {
		vTip.className = "onError";
	}
	vTip.innerHTML = innerHTML;
	return flag;
}
function stopProfitPoint(userID) {
	var innerHTML = "";
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	var str = "请输入≥0且≤200的整数";
	var flag = false;
	if (isEmpty('' + user.value)) {
		innerHTML = "不能为空";
	} else if (!integer(user.value)) {
		innerHTML = str;
	} else if ((user.value < 0 || user.value > 200)) {
		innerHTML =str;
	} else {
		innerHTML = "<%=stopProfitPoint%>";
		flag = true;
	}
	if (flag) {
		vTip.className = "";
	} else {
		vTip.className = "onError";
	}
	vTip.innerHTML = innerHTML;
	return flag;
}
function min_M_OrderPoint(userID) {
	var innerHTML = "";
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	var str = "请输入≥0且≤200的整数";
	var flag = false;
	if (isEmpty('' + user.value)) {
		innerHTML = "不能为空";
	} else if (!integer(user.value)) {
		innerHTML = str;
	} else if ((user.value < 0 || user.value > 200)) {
		innerHTML = str;
	} else {
		innerHTML = "<%=min_M_OrderPoint%>";
		flag = true;
	}
	if (flag) {
		vTip.className = "";
	} else {
		vTip.className = "onError";
	}
	vTip.innerHTML = innerHTML;
	return flag;
}
function max_M_OrderPoint(userID) {
	var innerHTML = "";
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	var str = "请输入≥0且≤500的整数";
	var flag = false;
	if (isEmpty('' + user.value)) {
		innerHTML = "不能为空";
	} else if (!integer(user.value)) {
		innerHTML =str;
	} else if ((user.value < 0 || user.value > 500)) {
		innerHTML =str;
	} else {
		innerHTML = "<%=max_M_OrderPoint%>";
		flag = true;
	}
	if (flag) {
		vTip.className = "";
	} else {
		vTip.className = "onError";
	}
	vTip.innerHTML = innerHTML;
	return flag;
}
function m_OrderPoint(userID) {
	var innerHTML = "";
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	var str = "请输入≥0且≤200的整数";
	var flag = false;
	if (isEmpty('' + user.value)) {
		innerHTML = "不能为空";
	} else if (!integer(user.value)) {
		innerHTML = str;
	} else if ((user.value < 0 || user.value > 200)) {
		innerHTML =str;
	} else {
		innerHTML = "<%=m_OrderPoint%>";
		flag = true;
	}
	if (flag) {
		vTip.className = "";
	} else {
		vTip.className = "onError";
	}
	vTip.innerHTML = innerHTML;
	return flag;
}

function myfocus(userID) {
	/**var vTip = document.getElementById(userID+"_vTip");
	var innerHTML = "";
	if("l_Open_Point"==userID){
		innerHTML = "请输入限价建仓点差";
	}
	if("stopLossPoint"==userID){
		innerHTML = "请输入止损下单点差";
	}
	if("stopProfitPoint"==userID){
		innerHTML = "请输入止盈下单点差";
	}
	if("min_M_OrderPoint"==userID){
		innerHTML = "请输入市价点差最小值";
	}
	if("max_M_OrderPoint"==userID){
		innerHTML = "请输入市价点差最大值";
	}
	if("m_OrderPoint"==userID){
		innerHTML = "请输入默认市价点差";
	}
	vTip.innerHTML=innerHTML;
	vTip.className="onFocus";*/
}
</script>