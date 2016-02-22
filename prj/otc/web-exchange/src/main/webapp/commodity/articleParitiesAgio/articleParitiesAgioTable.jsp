<%@ page contentType="text/html;charset=GBK"%>
<%
	String clearExchageRate="";
	String quoteAgio="";
	String quoteRate="";
	String quoteExchangeRate="";
%>
<table border="0" width="100%" align="center">
	<tr height="30"></tr>
	<tr>
		<td>
			<div class="div_cxtjd">
				<img src="<%=skinPath%>/cssimg/13.gif" />&nbsp;商品汇率
			</div>
			<table border="0" cellspacing="0" cellpadding="4" width="100%"
				align="center" class="st_bor">
				<input type="hidden" class="input_text_pwdmin" id="name"
							name="obj.commodityId" value="${obj.commodityId}"
							readonly="readonly">
				<tr height="40">
					<td align="right" width="25%">
						商品名称:
					</td>
					<td colspan="2">
						<input type="text" class="input_text_pwdmin" id="name"
							name="obj.commodityName" value="${obj.commodityName}"
							readonly="readonly">
					</td>
				</tr>
				<tr height="40">
					<td align="right">
						接入商品代码:
						<br>
					</td>
					<td colspan="2">
						<input type="text" class="input_text_pwdmin" id="inCommodityId"
							name="obj.inCommodityId" value="${obj.inCommodityId}"
							readonly="readonly">
					</td>
				</tr>
				<tr height="40">
					<td align="right">
						换算单位:
					</td>
					<td>
						<input type="text" onblur="myblur('quoteRate')"  onfocus="myfocus('quoteRate')" readonly="readonly"
						 style="text-align:right" class="input_text" id="quoteRate"
							name="obj.quoteRate" value="${obj.quoteRate}">
					</td>
					<td align="left" height="40" style="padding-right: 10px;"><div id="quoteRate_vTip" ><%=quoteRate%></div></td>
				</tr>
				<tr height="40">
					<td align="right">
						报价汇率:
					</td>
					<td>
						<input type="text" onblur="myblur('quoteExchangeRate')"  onfocus="myfocus('quoteExchangeRate')" 
						 style="text-align:right" class="input_text" id="quoteExchangeRate"
							name="obj.quoteExchangeRate" value="${obj.quoteExchangeRate}">
					</td>
					<td align="left" height="40" style="padding-right: 10px;"><div id="quoteExchangeRate_vTip"><%=quoteExchangeRate%></div></td>
				</tr>
				<tr height="40">
					<td align="right">
						报价系数:
					</td>
					<td>
						<input type="text" readonly="readonly"
						 style="text-align:right" class="input_text" id="quoteRateAndQuoteExchangeRate"
							name="obj.quoteRateAndQuoteExchangeRate" value="<fmt:formatNumber value="${obj.quoteRateAndQuoteExchangeRate}" pattern="#0.000000"/>">
					</td>
					<td align="left" height="40" style="padding-right: 10px;"><div id="quoteRate_vTip" ></div></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<script>
function myblur(userID){
	var flag = true;
	if("quoteRate"==userID){
		flag = quoteRate(userID);
	}else if("quoteExchangeRate"==userID){
		flag = quoteExchangeRate(userID);
	}else{
		if(!quoteRate("quoteRate")) flag = false;
		if(!quoteExchangeRate("quoteExchangeRate")) flag = false;
	}
	return flag;
}
//结算汇率
function clearExchageRate(userID){
	var flag = false;
	var str = "请输入≥0.01且≤100的数字";
	var innerHTML = "";
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID+"_vTip");
	if(isEmpty(user.value)){
		innerHTML="不能为空";
	}else if(!flote(user.value,2)){
		innerHTML = "最多2位小数的数字";
	}else if(user.value<0 || user.value>1000){
		innerHTML = str;
	}else{
		innerHTML = '<%=clearExchageRate%>';
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

//报价贴水
function quoteAgio(userID){
	var flag = false;
	var str = "请输入≥0.01且≤100的数字";
	var innerHTML = "";
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID+"_vTip");
	if(isEmpty(user.value)){
		innerHTML="不能为空";
	}else if(!flote(user.value,2)){
		innerHTML ="最多2位小数的数字";
	}else if(user.value<0 || user.value>100){
		innerHTML = str;
	}else{
		innerHTML = '<%=quoteAgio%>';
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
//换算单位
function quoteRate(userID){
	var flag = false;
	var str = "请输入≥0的数字";
	var innerHTML = "";
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID+"_vTip");
	if(isEmpty(user.value)){
		innerHTML="不能为空";
	}else if(!flote(user.value,6)){
		innerHTML = "最多6位小数的数字";
	}else if(user.value<0){
		innerHTML = str;
	}else{
		innerHTML = '<%=quoteRate%>';
		flag = true;
		var user2 = document.getElementById("quoteExchangeRate").value;
		var user3 = document.getElementById("quoteRateAndQuoteExchangeRate");
		user3.value =accMul(user.value,user2);
	}
	vTip.innerHTML = innerHTML;
	if(flag){
		vTip.className = "";
	}else{
		vTip.className = "onError";
	}
	return flag;
}
//报价汇率
function quoteExchangeRate(userID){
	var flag = false;
	var str = "请输入≥0的数字";
	var innerHTML = "";
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID+"_vTip");
	if(isEmpty(user.value)){
		innerHTML="不能为空";
	}else if(!flote(user.value,4)){
		innerHTML ="最多4位小数的数字";
	}else if(user.value<0){
		innerHTML = str;
	}else{
		innerHTML = '<%=quoteExchangeRate%>';
		flag = true;
		var user2 = document.getElementById("quoteRate").value;
		var user3 = document.getElementById("quoteRateAndQuoteExchangeRate");
		user3.value =accMul(user.value,user2);
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
  function accMul(arg1,arg2) 
	{ 
	var m=0,s1=arg1.toString(),s2=arg2.toString(); 
	try{m+=s1.split(".")[1].length}catch(e){} 
	try{m+=s2.split(".")[1].length}catch(e){} 
 	var number = Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m); 
	number = formatNumber(number,"#0.000000");
	return number;
	} 
</script>
