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
				<img src="<%=skinPath%>/cssimg/13.gif" />&nbsp;��Ʒ����
			</div>
			<table border="0" cellspacing="0" cellpadding="4" width="100%"
				align="center" class="st_bor">
				<input type="hidden" class="input_text_pwdmin" id="name"
							name="obj.commodityId" value="${obj.commodityId}"
							readonly="readonly">
				<tr height="40">
					<td align="right" width="25%">
						��Ʒ����:
					</td>
					<td colspan="2">
						<input type="text" class="input_text_pwdmin" id="name"
							name="obj.commodityName" value="${obj.commodityName}"
							readonly="readonly">
					</td>
				</tr>
				<tr height="40">
					<td align="right">
						������Ʒ����:
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
						���㵥λ:
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
						���ۻ���:
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
						����ϵ��:
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
//�������
function clearExchageRate(userID){
	var flag = false;
	var str = "�������0.01�ҡ�100������";
	var innerHTML = "";
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID+"_vTip");
	if(isEmpty(user.value)){
		innerHTML="����Ϊ��";
	}else if(!flote(user.value,2)){
		innerHTML = "���2λС��������";
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

//������ˮ
function quoteAgio(userID){
	var flag = false;
	var str = "�������0.01�ҡ�100������";
	var innerHTML = "";
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID+"_vTip");
	if(isEmpty(user.value)){
		innerHTML="����Ϊ��";
	}else if(!flote(user.value,2)){
		innerHTML ="���2λС��������";
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
//���㵥λ
function quoteRate(userID){
	var flag = false;
	var str = "�������0������";
	var innerHTML = "";
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID+"_vTip");
	if(isEmpty(user.value)){
		innerHTML="����Ϊ��";
	}else if(!flote(user.value,6)){
		innerHTML = "���6λС��������";
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
//���ۻ���
function quoteExchangeRate(userID){
	var flag = false;
	var str = "�������0������";
	var innerHTML = "";
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID+"_vTip");
	if(isEmpty(user.value)){
		innerHTML="����Ϊ��";
	}else if(!flote(user.value,4)){
		innerHTML ="���4λС��������";
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
	vTip.innerHTML = "����Ϊ��";
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
