<%@ page contentType="text/html;charset=GBK"%>
<table border="0" width="100%" align="center">
	<tr height="30"></tr>
	<%
		String quotePointB="";
		String quotePoint_S="";
	
	%>
	<tr>
		<td>
			<div class="div_cxtjd">
				<img src="<%=skinPath%>/cssimg/13.gif" />&nbsp;默认报价点差
			</div>
			<table border="0" cellspacing="0" cellpadding="4" width="100%"
				align="center" class="st_bor">
				<input type="hidden" name="obj.commodityId"
					value="${obj.commodityId}">
				<input type="hidden" name="obj.m_firmId" value="${obj.m_firmId}">
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
						类型：
					</td>
					<td>
						<input type="text" class="input_text_pwdmin" id="firmName"
							name="obj.firmName" value="${firmDisMap[obj.m_firmId]}"
							readonly="readonly">
					</td>
				</tr>
				<tr height="40">
					<td align="right">
						报价点差算法:
					</td>
					<td>
						<select id="delayFeeAlgr"  name="obj.quotePointAlgr_v"
							onchange="quotePointAlgrChange(this.value);" class="select_widmid">
							<c:forEach items="${delayFeeAlgrMap}" var="maps">
								<option value="${maps.key}" <c:if test="${maps.key==obj.quotePointAlgr_v}">selected=selected</c:if>>
									${maps.value }
								</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr height="40">
					<td align="right">
						买报价点差:
					</td>
					<td>
						<input type="text" onblur="myblur('quotePointB')"  onfocus="myfocus('quotePointB')"
						style="text-align:right"class="input_text" id="quotePointB"
							name="obj.quotePointB_v" value="${obj.quotePointB_v}">
						<span id="quotePointB_span"></span>
					</td>
					<td><div id="quotePointB_vTip"><%=quotePointB %></div></td>
				</tr>
				<!--<tr height="40">
					<td align="right">
						卖报价点差:
					</td>
					<td >-->
						<input type="hidden" 
						style="text-align:right"class="input_text" id="quotePoint_S"
							name="obj.quotePointS_v" value="${obj.quotePointS_v}"><!-- 
						<span id="quotePointS_span"></span>
					</td>
					<td align="left" height="40">
						<div id="quotePoint_S_vTip" class="onFocus">
							<%=quotePoint_S %>
						</div>
					</td>
				</tr>
			--></table>
		</td>
		<td align="right">
		</td>
		<td>
		</td>
	</tr>
</table>
<script type="text/javascript">
function myblur(userID){
	var flag = true;
	 if("quotePointB"==userID){
		flag = quotePointB(userID);
	}
	 //else if("quotePoint_S"==userID){
	//	flag = quotePoint_S(userID);
	//}
	 else{
		if(!quotePointB("quotePointB")) flag = false;
		//if(!quotePoint_S("quotePoint_S")) flag = false;
	}
	return flag;
}
function quotePointB(userID){
	var user = document.getElementById(userID);
	var feeAlgr = document.getElementById("delayFeeAlgr").value;
	var vTip = document.getElementById(userID+"_vTip");
	var innerHTML = "";
	var str = "请输入≥0且≤200的数";
	var flag = false;
	if (isEmpty(user.value + "")) {
		innerHTML = "不能为空";
	} else if (feeAlgr == 1) {//百分比
		if (!flote(user.value, 2)) {
			innerHTML = "最多2位小数的数字";
		} else if (user.value < 0 || user.value > 200) {
			innerHTML = str;
		} else {
			innerHTML = "";
			flag = true;
		}
	} else {//绝对值
		if (!flote(user.value, 2)) {
			innerHTML = "最多2位小数的数字";
		} else if (user.value < 0 || user.value > 200) {
			innerHTML = str;
		} else {
			innerHTML = "";
			flag = true;
		}
	}
	vTip.innerHTML = innerHTML;
	if (flag) {
		vTip.className = "";
	} else {
		vTip.className = "onError";
	}
	return flag;
}
function quotePoint_S(userID){
	var user = document.getElementById(userID);
	var delayFeeAlgr = document.getElementById("delayFeeAlgr").value;
	var vTip = document.getElementById(userID+"_vTip");
	var innerHTML = "";
	var flag = false;
	if(isEmpty(user.value+'')){
		innerHTML = "不能为空";
	}else if(delayFeeAlgr==1){//百分比
		if(!flote(user.value,2)){
			innerHTML = "请输入最多两位小数的数字";
		}else if(user.value<0 || user.value>100){
			innerHTML = "应在0到100之间";
		}else{
			innerHTML = "<%=quotePoint_S%>";
			flag = true;
		}
	}else{//绝对值
		if(!flote(user.value,4)){
			innerHTML = "请输入最多四位小数的数字";
		}else if(user.value<0 || user.value>100000){
			innerHTML = "应在0-100000之间";
		}else{
			innerHTML = "<%=quotePoint_S%>";
			flag = true;
		}
	}
	if(flag){
		vTip.className="onFocus";
	}else{
		vTip.className="onError";
	}
	vTip.innerHTML=innerHTML;
	return flag;
}
function myfocus(userID){
	/*var vTip = document.getElementById(userID+"_vTip");
	var innerHTML = "";
	if("delayFeeAlgr"==userID){
		innerHTML = "请选择";
	}
	if("quotePointB"==userID){
		innerHTML = "不能为空";
	}
	if("quotePoint_S"==userID){
		innerHTML = "不能为空";
	}
	vTip.innerHTML = innerHTML;
	vTip.className = "onFocus";*/
}
function quotePointAlgrChange(quotePointAlgr) {
	if (quotePointAlgr == '1') {
		document.getElementById("quotePointB_span").innerHTML = "%";
		//document.getElementById("quotePointS_span").innerHTML = "%";
	}
	if (quotePointAlgr == '2') {
		document.getElementById("quotePointB_span").innerHTML = "";
	//	document.getElementById("quotePointS_span").innerHTML = "";
	}
}
function updateMargin() {
	if (frm.quotePointB.value == "") {
		alert("买报价点差不能为空！");
		frm.quotePointB.focus();
		return false;
	}
	//if (frm.quotePoint_S.value == "") {
	//	alert("卖报价点差不能为空！");
	//	frm.quotePoint_S.focus();
		//return false;
	//}
	var vaild = window.confirm("您确定要操作吗？");
	if (vaild == true) {
		frm.submit();
	} else {
		return false;
	}
}
</script>
<script type="text/javascript">
quotePointAlgrChange('${obj.quotePointAlgr_v}');
</script>