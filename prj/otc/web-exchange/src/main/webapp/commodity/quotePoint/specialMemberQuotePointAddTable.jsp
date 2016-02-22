<%@ page contentType="text/html;charset=GBK"%>

<script type='text/javascript' src='<%=basePath%>/dwr/engine.js'>
</script>
<script type='text/javascript' src='<%=basePath%>/dwr/util.js'>
</script>
<script type="text/javascript" src='<%=basePath%>/dwr/interface/checkAction.js' />
</script>
<div class="div_cxtjmid">
	<img src="<%=skinPath%>/cssimg/13.gif" />
	�ر��Ա���۵��
</div>
<%
		String quotePointB="";
		String quotePoint_S="";
	
	%>
<table border="0" cellspacing="0" cellpadding="4" width="100%"
	align="center" class="st_bor">
	<tr height="40">
		<td align="right" width="23%">
			��Ʒ����:
		</td>
		<td width="30%">
			<input type="hidden" name="obj.commodityName" id="commodityName" />
			<select id="commodityId" name="obj.commodityId"
				onchange="setCommodityName();" class="select_widmid" onblur="myblur('commodityId')" onfocus="myfocus('commodityId')">
				<option value="">
					��ѡ��
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
			�ر��Ա���ƣ�
		</td>
		<td>
		<input type="hidden" name="obj.firmName" id="firmName">
			<select id="memberNo" name="obj.m_firmId" class="select_widmid" onblur="myblur('memberNo')" onfocus="myfocus('memberNo')">
				<option value="">
					��ѡ��
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
			���۵���㷨:
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
			�򱨼۵��:
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
			�����۵��:
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
			���Ʒ�ʽ:
		</td>
		<td>
			<select id="" name="obj.operate">
				<option value="P" selected="selected">
					���Ի�
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
		innerHTML = "��ѡ��";
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
				alert('��Ϣ�Ѵ��ڣ����������');
				document.getElementById("commodityId").value="";
				document.getElementById("memberNo").value="";
				document.getElementById("commodityId").focus();
			} else {
				checkAction.existApplyAddThread(commodityId, firmId, '${applyType}', function(applyView){
					if(applyView){
						alert("����������"+applyView.proposer+"��"+applyView.modTimeString+"�ύ");
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
		innerHTML = "��ѡ��";
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
				alert('��Ϣ�Ѵ��ڣ����������');
				document.getElementById("commodityId").value="";
				document.getElementById("memberNo").value="";
				document.getElementById("commodityId").focus();
			} else {
				checkAction.existApplyAddThread(commodityId, firmId, '${applyType}', function(applyView){
					if(applyView){
						alert("����������"+applyView.proposer+"��"+applyView.modTimeString+"�ύ");
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
	var str = "�������0�ҡ�200����";
	var flag = false;
	if (isEmpty(user.value + "")) {
		innerHTML = "����Ϊ��";
	} else if (feeAlgr == 1) {//�ٷֱ�
		if (!flote(user.value, 2)) {
			innerHTML = "���2λС��������";
		} else if (user.value < 0 || user.value > 200) {
			innerHTML = str;
		} else {
			innerHTML = "";
			flag = true;
		}
	} else {//����ֵ
		if (!flote(user.value, 2)) {
			innerHTML = "���2λС��������";
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
		innerHTML = "����Ϊ��";
	} else if (delayFeeAlgr == 1) {//�ٷֱ�
		if (!flote(user.value, 2)) {
			innerHTML = "�����������λС��������";
		} else if (user.value < 0 || user.value > 100) {
			innerHTML = "Ӧ��0��100֮��";
		} else {
			innerHTML = "<%=quotePoint_S%>";
			flag = true;
		}
	} else {//����ֵ
		if (!flote(user.value, 4)) {
			innerHTML = "�����������λС��������";
		} else if (user.value < 0 || user.value > 100000) {
			innerHTML = "Ӧ��0-100000֮��";
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
		innerHTML = "��ѡ��";
	}
	if ("quotePointB" == userID) {
		innerHTML = "����Ϊ��";
	}
	if ("quotePoint_S" == userID) {
		innerHTML = "����Ϊ��";
	}
	if ("commodityId" == userID) {
		innerHTML = "����Ϊ��";
	}
	if ("memberNo" == userID) {
		innerHTML = "����Ϊ��";
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