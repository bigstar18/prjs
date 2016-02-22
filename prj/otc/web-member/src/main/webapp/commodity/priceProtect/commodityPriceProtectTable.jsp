<%@ page contentType="text/html;charset=GBK"%>
<%
	String screeningPricePoint = "";
	String timeoutInterval = "";
%>
<table border="0" width="100%" align="center">
	<tr height="30"></tr>
	<tr>
		<td>
			<div class="div_cxtjmid">
				<img src="<%=skinPath%>/cssimg/13.gif" />&nbsp;&nbsp;
				商品行情保护
			</div>
			<table border="0" cellspacing="0" cellpadding="4" width="100%"
				align="center" class="st_bor">
				<input type="hidden" id="id" class="input_text_pwdmin"
							name="obj.commodityId" value="${obj.commodityId}"
							readonly="readonly">
				<tr height="40">
					<td align="right" width="23%">
						商品名称:
					</td>
					<td width="30%">
						<input type="text" id="name" class="input_text_pwdmin"
							name="obj.commodityName" value="${obj.commodityName}"
							readonly="readonly">
					</td>
				</tr>
				<tr height="40">
					<td align="right">
						滤价点差:
					</td>
					<td>
						<input type="text" onblur="myblur('screeningPricePoint')"  onfocus="myfocus('screeningPricePoint')" 
						 style="text-align:right" class="input_text" id="screeningPricePoint"
							name="obj.screeningPricePoint" value="${obj.screeningPricePoint}">
					</td>
					<td align="left" height="40"><div id="screeningPricePoint_vTip"><%=screeningPricePoint %></div></td>
				</tr>
				<tr height="40">
					<td align="right">
						超时间隔:
					</td>
					<td>
						<input type="text" onblur="myblur('timeoutInterval')"  onfocus="myfocus('timeoutInterval')" 
						 style="text-align:right" id="timeoutInterval" class="input_text"
							name="obj.timeoutInterval" value="${obj.timeoutInterval}">
					</td>
					<td align="left" height="40"><div id="timeoutInterval_vTip" ><%=timeoutInterval %></div></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<script>
function myblur(userID){
	var flag = true;
	if("screeningPricePoint"==userID){
		flag = screeningPricePoint(userID);
	}else if("timeoutInterval"==userID){
		flag = timeoutInterval(userID);
	}else{
		if(!screeningPricePoint("screeningPricePoint"))flag = false;
		if(!timeoutInterval("timeoutInterval"))flag = false;
	}
	return flag;
}
function screeningPricePoint(userID){
	var flag = false;
	var innerHTML = "";
	var str = "请输入≥0且≤200的正数";
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID+"_vTip");
	if(isEmpty(user.value)){
		innerHTML = "不能为空";
	}else if(!flote(user.value,2)){
		innerHTML = "最多2位小数的数字";
	}else if(user.value<0 || user.value>200){
		innerHTML = str;
	}else{
		innerHTML = "<%=screeningPricePoint %>";
		flag = true;
	}
	vTip.innerHTML = innerHTML;
	if(flag){
		vTip.className = "";
	}else{
		vTip.className = "onError";
	}
	return flag;
}
function timeoutInterval(userID){
	var flag = false;
	var innerHTML = "";
	var str = "请输入≤3600的正整数";
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID+"_vTip");
	if(isEmpty(user.value)){
		innerHTML = "不能为空";
	} else if (!integer(user.value)) {
		innerHTML = str;
	}else if (user.value<0||user.value>3600){
		innerHTML = str;
	} else{
		innerHTML = "<%=timeoutInterval %>";
		flag = true;
	}
	vTip.innerHTML = innerHTML;
	if(flag){
		vTip.className = "";
	}else{
		vTip.className = "onError";
	}
	return flag;
}
function myfocus(userID){
	/*var vTip = document.getElementById(userID+"_vTip");
	vTip.innerHTML = "不能为空";
	vTip.className = "onFocus";*/
}
</script>