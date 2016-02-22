<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
	<%
		String quotePointB="提示信息，未确定信息";
		String quotePoint_S="提示信息，未确定信息";
		String quotePoint_B_RMB = "提示信息，未确定信息";
		String quotePoint_S_RMB ="提示信息，未确定信息";
	%>
<html>
	<head>
		<title>报价点差设置</title>
	</head>
	<body leftmargin="0" topmargin="0" class="st_body"
		style="overflow-y: hidden">
		<form name="frm"
			action="${basePath}/settlement/quotePointRunTime/update.action"
			method="post" targetType="hidden">
			<div>
				<table border="0" width="90%" align="center">
					<tr height="30"></tr>
					<tr>
						<td>
							<div class="st_title">
								<img src="<%=skinPath%>/cssimg/st_ico1.gif" width="43"
									height="40" align="absmiddle" />
								&nbsp;&nbsp;报价点差设置
							</div>
							<table border="0" cellspacing="0" cellpadding="4" width="100%"
								align="center" class="st_bor">
								<input type="hidden" name="obj.commodityId"
									value="${obj.commodityId}">
								<input type="hidden" name="obj.m_FirmId" value="${obj.m_FirmId}">

								<tr height="20">
									<td align="right">
										商品名称:
									</td>
									<td>
										<input type="text" id="name" class="input_text"
											name="obj.commodityName" value="${obj.commodityName}"
											style="background-color: #bebebe" readonly="readonly">
									</td>
									<td align="right">
										会员名称:
										<br>
									</td>
									<td>
										<input type="text" id="name" class="input_text"
											name="obj.firmName" value="${obj.firmName}"
											style="background-color: #bebebe" readonly="readonly">
									</td>
								</tr>
								<tr height="20">
									<td align="right">
										买报价点差:
									</td>
									<td>
										<input type="text" class="input_text" id="quotePointB"
										onblur="myblur('quotePointB')"  onfocus="myfocus('quotePointB')"
											name="obj.quotePoint_B" value="${obj.quotePoint_B}">
									</td>
									<td colspan="2"><div id="quotePointB_vTip" class="onFocus"><%=quotePointB %></div></td>
								</tr>
								<tr height="20">
									<td align="right">
										卖报价点差:
									</td>
									<td>
										<input type="text" class="input_text" id="quotePoint_S"
										onblur="myblur('quotePoint_S')"  onfocus="myfocus('quotePoint_S')"
											name="obj.quotePoint_S" value="${obj.quotePoint_S}">
									</td>
									<td colspan="2"><div id="quotePoint_S_vTip" class="onFocus"><%=quotePoint_S %></div></td>
								</tr>
								<tr height="20">
									<td align="right">
										买报价点差金额:
									</td>
									<td>
										<input type="text" class="input_text" id="quotePoint_B_RMB"
										onblur="myblur('quotePoint_B_RMB')"  onfocus="myfocus('quotePoint_B_RMB')"
											name="obj.quotePoint_B_RMB" value="${obj.quotePoint_B_RMB}">
									</td>
									<td colspan="2"><div id="quotePoint_B_RMB_vTip" class="onFocus"><%=quotePoint_B_RMB %></div></td>
								</tr>
								<tr height="20">
									<td align="right">
										卖报价点差金额:
									</td>
									<td>
										<input type="text" class="input_text" id="quotePoint_S_RMB"
										onblur="myblur('quotePoint_S_RMB')"  onfocus="myfocus('quotePoint_S_RMB')"
											name="obj.quotePoint_S_RMB" value="${obj.quotePoint_S_RMB}">
									</td>
									<td colspan="2"><div id="quotePoint_S_RMB_vTip" class="onFocus"><%=quotePoint_S_RMB %></div></td>
								</tr>
							</table>
							</div>
							<div class="tab_pad">

								<table cellspacing="0" cellpadding="0" border="0" width="100%"
									align="center">
									<tr>
										<td align="center">
											<button class="btn_sec" onClick="updateMargin()" id="update">
												保存
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
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>
<script type="text/javascript">
function myblur(userID){
	var flag = true;
	 if("quotePointB"==userID){
		flag = quotePointB(userID);
	}else if("quotePoint_S"==userID){
		flag = quotePoint_S(userID);
	}else if("quotePoint_B_RMB"==userID){
		flag = quotePoint_B_RMB(userID);
	}else if("quotePoint_S_RMB"==userID){
		flag = quotePoint_S_RMB(userID);
	}else{
		if(!quotePointB("quotePointB")) flag = false;
		if(!quotePoint_S("quotePoint_S")) flag = false;
		if(!quotePointB("quotePoint_B_RMB")) flag = false;
		if(!quotePoint_S("quotePoint_S_RMB")) flag = false;
	}
	return flag;
}
function quotePointB(userID){
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID+"_vTip");
	var innerHTML = "";
	var flag = false;
	if(isEmpty(user.value+'')){
		innerHTML = "不能为空";
	}else if(isNaN(user.value)){
		innerHTML = "请输入数字";
	}else{
		if(!flote(user.value,4)){
			innerHTML = "请输入最多四位小数的数字";
		}else if(user.value<0 || user.value>100000){
			innerHTML = "应在0-100000之间";
		}else{
			innerHTML = "<%=quotePointB%>";
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
function quotePoint_S(userID){
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID+"_vTip");
	var innerHTML = "";
	var flag = false;
	if(isEmpty(user.value+'')){
		innerHTML = "不能为空";
	}else if(isNaN(user.value)){
		innerHTML = "请输入数字";
	}else{
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
}function quotePoint_B_RMB(userID){
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID+"_vTip");
	var innerHTML = "";
	var flag = false;
	if(isEmpty(user.value+'')){
		innerHTML = "不能为空";
	}else if(isNaN(user.value)){
		innerHTML = "请输入数字";
	}else{
		if(!flote(user.value,4)){
			innerHTML = "请输入最多四位小数的数字";
		}else if(user.value<0 || user.value>100000){
			innerHTML = "应在0-100000之间";
		}else{
			innerHTML = "<%=quotePoint_B_RMB%>";
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
function quotePoint_S_RMB(userID){
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID+"_vTip");
	var innerHTML = "";
	var flag = false;
	if(isEmpty(user.value+'')){
		innerHTML = "不能为空";
	}else if(isNaN(user.value)){
		innerHTML = "请输入数字";
	}else{ 
		if(!flote(user.value,4)){
			innerHTML = "请输入最多四位小数的数字";
		}else if(user.value<0 || user.value>100000){
			innerHTML = "应在0-100000之间";
		}else{
			innerHTML = "<%=quotePoint_S_RMB%>";
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
function updateMargin() {
	var flag = myblur("all");
		if(!flag){return false;}
		if(!isFormChanged(null,null)){
			alert("没有修改内容");
			return false;}
	frm.submit();
}
</script>
<%@ include file="/public/footInc.jsp"%>