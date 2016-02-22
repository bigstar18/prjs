<%@ page contentType="text/html;charset=GBK"%>

<script type='text/javascript' src='<%=basePath%>/dwr/engine.js'>
</script>
<script type='text/javascript' src='<%=basePath%>/dwr/util.js'>
</script>
<script type="text/javascript" src='<%=basePath%>/dwr/interface/checkAction.js' />
</script>
<div class="div_cxtjmid">
	<img src="<%=skinPath%>/cssimg/13.gif" />
	特别会员报价点差
</div>
<%
		String quotePointB="";
		String quotePoint_S="";
	
	%>
<table border="0" cellspacing="0" cellpadding="4" width="100%"
	align="center" class="st_bor">
	<tr height="40">
		<td align="right" width="23%">
			商品名称:
		</td>
		<td width="30%">
			<input type="hidden" name="obj.commodityName" id="commodityName" />
			<select id="commodityId" name="obj.commodityId"
				onchange="setCommodityName();" class="select_widmid" onblur="myblur('commodityId')" onfocus="myfocus('commodityId')">
				<option value="">
					请选择
				</option>
				<c:forEach items="${commodityList }" var="commodity">
					<option value="${commodity.id}">
						${commodity.name }
					</option>
				</c:forEach>
			</select>
		</td>
		<td>
			<div id="commodityId_vTip">
				
			</div>
		</td>
	</tr>
	<tr height="40">
		<td align="right">
			特别会员名称：
		</td>
		<td>
		<input type="hidden" name="obj.firmName" id="firmName">
			<select id="memberNo" name="obj.m_firmId" class="select_widmid" onblur="myblur('memberNo')" onfocus="myfocus('memberNo')">
				<option value="">
					请选择
				</option>
				<c:forEach items="${memberInfoList }" var="member">
					<option value="${member.id}" title ='${member.name }'>
						${member.name }
					</option>
				</c:forEach>
			</select>
		</td>
		<td>
			<div id="memberNo_vTip">
				
			</div>
		</td>
	</tr>
	<tr height="40">
		<td align="right">
			报价点差算法:
		</td>
		<td>
			<select id="delayFeeAlgr" name="obj.quotePointAlgr_v"
				onchange="quotePointAlgrChange(this.value);" class="select_widmid">
				<c:forEach items="${delayFeeAlgrMap }" var="maps">
					<option value="${maps.key}">
						${maps.value }
					</option>
				</c:forEach>
			</select>
		</td>
		<td>
			<div id="delayFeeAlgr_vTip" >
			</div>
		</td>
	</tr>
	<tr height="40">
		<td align="right">
			买报价点差:
		</td>
		<td>
			<input type="text" class="input_text" id="quotePointB" style="text-align:right"
				name="obj.quotePointB_v" onblur="myblur('quotePointB')"
				onfocus="myfocus('quotePointB')">
			<span id="quotePointB_span"></span>
		</td>
		<td>
			<div id="quotePointB_vTip" ><%=quotePointB %></div>
		</td>
	</tr>
	<!-- <tr height="40">
		<td align="right">
			卖报价点差:
		</td>
		<td> -->
			<input type="hidden" class="input_text" id="quotePoint_S" style="text-align:right"
				name="obj.quotePointS_v" value="0">
		<!-- 	<span id="quotePointS_span"></span>
		</td>
		<td align="left" height="40">
			<div id="quotePoint_S_vTip" >
				<%=quotePoint_S %>
			</div>
		</td>
	</tr> -->
	<tr height="40">
		<td align="right">
			控制方式:
		</td>
		<td>
			<select id="" name="obj.operate">
				<option value="P" selected="selected">
					个性化
				</option>
			</select>
		</td>
	</tr>
</table>
<script type="text/javascript">

function myblur(userID) {
	var flag = true;
 if ("quotePointB" == userID) {
		flag = quotePointB(userID);
	}
	//else if ("quotePoint_S" == userID) {
	//	flag = quotePoint_S(userID);
	//} 
 	else if ("commodityId" == userID) {
		flag = commodityId(userID);
	} else if ("memberNo" == userID) {
		flag = memberNo(userID);
	} else {
		if (!quotePointB("quotePointB"))
			flag = false;
	//	if (!quotePoint_S("quotePoint_S"))
		//	flag = false;
		if (!commodityId("commodityId"))
			flag = false;
		if (!memberNo("memberNo"))
			flag = false;
	}
	return flag;
}

function commodityId(userID) {
	var innerHTML="";
	var vTip = document.getElementById(userID + "_vTip");
	var user = document.getElementById(userID);
	var flag = false;
	if (isEmpty(user.value)) {
		innerHTML = "请选择";
		flag = false;
	} else {
		flag = true;
	}
	vTip.innerHTML = innerHTML;
	if (flag) {
		vTip.className = "";
	} else {
		vTip.className = "onError";
		
	}
	var firmId = document.getElementById("memberNo").value;
	var commodityId = document.getElementById("commodityId").value;
	if(firmId){
		checkAction.existSpecialMemberQuotePoint(commodityId, firmId, function(isExist){
			if(isExist){
				alert('信息已存在，请重新添加');
				document.getElementById("commodityId").value="";
				document.getElementById("memberNo").value="";
				document.getElementById("commodityId").focus();
			} else {
				checkAction.existApplyAddThread(commodityId, firmId, '${applyType}', function(applyView){
					if(applyView){
						alert("此申请已由"+applyView.proposer+"于"+applyView.modTimeString+"提交");
						document.getElementById("commodityId").value="";
						document.getElementById("memberNo").value="";
						document.getElementById("commodityId").focus();
					}
				});
			}
		});
	}
	return flag;
}
function memberNo(userID) {
	var innerHTML="";
	var vTip = document.getElementById(userID + "_vTip");
	var user = document.getElementById(userID);
	var flag = false;
	if (isEmpty(user.value)) {
		innerHTML = "请选择";
		flag = false;
	} else {
		flag = true;
	}
	vTip.innerHTML = innerHTML;
	if (flag) {
		vTip.className = "";
	} else {
		vTip.className = "onError";
		
	}
	var firmId = document.getElementById("memberNo").value;
	var commodityId = document.getElementById("commodityId").value;
	if(commodityId){
		checkAction.existSpecialMemberQuotePoint(commodityId, firmId, function(isExist){
			if(isExist){
				alert('信息已存在，请重新添加');
				document.getElementById("commodityId").value="";
				document.getElementById("memberNo").value="";
				document.getElementById("commodityId").focus();
			} else {
				checkAction.existApplyAddThread(commodityId, firmId, '${applyType}', function(applyView){
					if(applyView){
						alert("此申请已由"+applyView.proposer+"于"+applyView.modTimeString+"提交");
						document.getElementById("commodityId").value="";
						document.getElementById("memberNo").value="";
						document.getElementById("commodityId").focus();
					}
				});
			}
		});
	}
	return flag;
}
function quotePointB(userID) {
	var user = document.getElementById(userID);
	var feeAlgr = document.getElementById("delayFeeAlgr").value;
	var vTip = document.getElementById(userID + "_vTip");
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
function quotePoint_S(userID) {
	var user = document.getElementById(userID);
	var delayFeeAlgr = document.getElementById("delayFeeAlgr").value;
	var vTip = document.getElementById(userID + "_vTip");
	var innerHTML = "";
	var flag = false;
	if (isEmpty(user.value)) {
		innerHTML = "不能为空";
	} else if (delayFeeAlgr == 1) {//百分比
		if (!flote(user.value, 2)) {
			innerHTML = "请输入最多两位小数的数字";
		} else if (user.value < 0 || user.value > 100) {
			innerHTML = "应在0到100之间";
		} else {
			innerHTML = "<%=quotePoint_S%>";
			flag = true;
		}
	} else {//绝对值
		if (!flote(user.value, 4)) {
			innerHTML = "请输入最多四位小数的数字";
		} else if (user.value < 0 || user.value > 100000) {
			innerHTML = "应在0-100000之间";
		} else {
			innerHTML = "<%=quotePoint_S%>";
			flag = true;
		}
	}
	if (flag) {
		vTip.className = "onFocus";
	} else {
		vTip.className = "onError";
	}
	vTip.innerHTML = innerHTML;
	return flag;
}
function myfocus(userID) {
/*	var vTip = document.getElementById(userID + "_vTip");
	var innerHTML = "";
	if ("delayFeeAlgr" == userID) {
		innerHTML = "请选择";
	}
	if ("quotePointB" == userID) {
		innerHTML = "不能为空";
	}
	if ("quotePoint_S" == userID) {
		innerHTML = "不能为空";
	}
	if ("commodityId" == userID) {
		innerHTML = "不能为空";
	}
	if ("memberNo" == userID) {
		innerHTML = "不能为空";
	}
	vTip.innerHTML = innerHTML;
	vTip.className = "onFocus";*/
}

function quotePointAlgrChange(quotePointAlgr) {
	if (quotePointAlgr == '1') {
		document.getElementById("quotePointB_span").innerHTML = "%";
	//	document.getElementById("quotePointS_span").innerHTML = "%";
	}
	if (quotePointAlgr == '2') {
		document.getElementById("quotePointB_span").innerHTML = "";
	//	document.getElementById("quotePointS_span").innerHTML = "";
	}
}
function setCommodityName() {
	var obj = document.getElementById('commodityId');
	var value = obj.options[obj.selectedIndex].text;
	document.getElementById('commodityName').value = value;
	var m_FirmId = document.getElementById('memberNo');
	var firmValue = m_FirmId.options[m_FirmId.selectedIndex].text;
	document.getElementById('firmName').value = firmValue;
}
</script>