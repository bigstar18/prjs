<%@ page contentType="text/html;charset=GBK"%>

<script type='text/javascript' src='<%=basePath%>/dwr/engine.js'>
</script>
<script type='text/javascript' src='<%=basePath%>/dwr/util.js'>
</script>
<script type="text/javascript" src='<%=basePath%>/dwr/interface/checkAction.js' />
</script>
<%
	String feeRate="";
	String feeMode="";
	String mkt_FeeRate="";
%>

<div class="div_cxtjmax" >
	<img src="<%=skinPath%>/cssimg/13.gif"  />&nbsp;��Ա��Ͻ�ͻ�������
</div>
<table border="0" cellspacing="0" cellpadding="4" width="100%"
	align="center" class="st_bor">
	<tr height="40">
		<td align="right" width="25%">
			��Ʒ����:
		</td>
		<td width="30%">
			<input type="hidden" name="obj.commodityName" id="commodityName">
			<select id="commodityId" name="obj.commodityId" class="select_widmid"
				onblur="myblur('commodityId')"  onfocus="myfocus('commodityId')"
				onchange="setCommodityName();">
				<option value="">
					��ѡ��
				</option>
				<c:forEach items="${commodityList}" var="commodit">
					<option value="${commodit.id}">
						${commodit.name }
					</option>
				</c:forEach>
			</select>
		</td>
		<td align="left" height="40"><div id="commodityId_vTip"></div></td>
	</tr>
	<tr height="40">
		<td align="right">
			������Ա:
		</td>
		<td>
			<input type="hidden" name="obj.memberName" id="memberName">
			<select id="m_FirmId" name="obj.m_FirmId" class="select_widmid"
				onblur="myblur('m_FirmId')"  onfocus="myfocus('m_FirmId')"
				onchange="setCommodityName();" >
				<option value="">
					��ѡ��
				</option>
				<c:forEach items="${memberInfoList}" var="memberInfo">
					<option value="${memberInfo.id}" title ='${memberInfo.name}'>
						${memberInfo.name }
					</option>
				</c:forEach>
			</select>
		</td>
		<td align="left" height="40"><div id="m_FirmId_vTip" ></div></td>
	</tr>
	<tr height="40">
		<td align="right">
			�������㷨:
		</td>
		<td>
			<select id="feeAlgr" name="obj.feeAlgr_v" class="select_widmid"
				 onchange="feeAlgrChange(this.value);" >
				<c:forEach items="${feeAlgrMap}" var="maps">
					<option value="${maps.key}">
						${maps.value }
					</option>
				</c:forEach>
			</select>
		</td>
	</tr>
	<tr height="40">
		<td align="right">
			������ϵ��:
		</td>
		<td>
			<input class="input_text" type="text" id="feeRate" style="text-align:right"
				onblur="myblur('feeRate')" onfocus="myfocus('feeRate')"
				name="obj.feeRate_v">
			<span id="feeRate_span"></span>
		</td>
		<td align="left" height="40">
			<div id="feeRate_vTip" >	<%= feeRate%></div>
		</td>
	</tr>
	<tr height="40">
		<td align="right">
			��������ȡ������:
		</td>
		<td width="30%">
			<input class="input_text" type="text" onblur="myblur('mkt_FeeRate')" style="text-align:right"
				onfocus="myfocus('mkt_FeeRate')" id="mkt_FeeRate"
				name="obj.mkt_FeeRate_v" >
			<span id="mkt_feeRate_span"></span>
		</td>
		<td align="left" height="40">
			<div id="mkt_FeeRate_vTip"><%= mkt_FeeRate%></div>
		</td>
	</tr>
	<tr height="40">
		<td align="right">
			��ȡ��ʽ:
		</td>
		<td>
			<select id="feeMode" onblur="myblur('feeMode')"
				onfocus="myfocus('feeMode')" name="obj.feeMode"
				class="select_widmid">
				<c:forEach items="${takeFeeMap }" var="maps">
					<option value="${maps.key}">
						${maps.value }
					</option>
				</c:forEach>
			</select>
		</td>
		<td align="left" height="40">
			<div id="feeMode_vTip"><%= feeMode%></div>
		</td>
	</tr>
	<tr height="40">
		<td align="right">
			���Ʒ�ʽ:
		</td>
		<td>
			<select id="" name="obj.operate" class="select_widmid">
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
	if("commodityId"==userID){
		flag = commodityId(userID);
	}else if("m_FirmId"==userID){
		flag = m_FirmId(userID)
	} else if ("feeRate" == userID) {
		flag = feeRate(userID);
	} else if ("feeMode" == userID) {
		flag = feeMode(userID);
	} else if ("mkt_FeeRate" == userID) {
		flag = mkt_FeeRate(userID);
	} else {
		if(!commodityId("commodityId")){
			flag =false;
		}
		if(!m_FirmId("m_FirmId")){
			flag = false;
		}
		
		if (!feeRate("feeRate"))
			flag = false;
		if (!feeMode("feeMode"))
			flag = false;
		if (!mkt_FeeRate("mkt_FeeRate"))
			flag = false;
	}
	return flag;
}

function commodityId(userID){
	var innerHTML = "";
	var vTip = document.getElementById(userID+"_vTip");
	var user = document.getElementById(userID);
	var flag = false;
	if(isEmpty(user.value)){
		innerHTML = "��ѡ����Ʒ";
		flag =false;
	}else{
		flag =true;
	}	
	vTip.innerHTML=innerHTML;
	if(flag){
		vTip.className="";
	}else{
		vTip.className="onError";
	}
	var firmId = document.getElementById("m_FirmId").value;
	var commodityId = document.getElementById("commodityId").value;
	if(firmId){
		checkAction.existMemCustomerTakeFee(commodityId, firmId, function(isExist){
			if(isExist){
				alert('��Ϣ�Ѵ��ڣ����������');
				document.getElementById("commodityId").value="";
				document.getElementById("m_FirmId").value="";
				document.getElementById("commodityId").focus();
			} else {
				checkAction.existApplyAddAnOther(commodityId, firmId, '${applyType}', function(applyView){
					if(applyView){
						alert("����������"+applyView.proposer+"��"+applyView.modTimeString+"�ύ");
						document.getElementById("commodityId").value="";
						document.getElementById("m_FirmId").value="";
						document.getElementById("commodityId").focus();
					}
				});
			}
		});
	}
	return flag;
}
function m_FirmId(userID){
	var innerHTML = "";
	var vTip = document.getElementById(userID+"_vTip");
	var user = document.getElementById(userID);
	var flag = false;
	if(isEmpty(user.value)){
		innerHTML = "��ѡ���Ա";
		flag =false;
	}else{
		flag =true;
	}	
	vTip.innerHTML=innerHTML;
	if(flag){
		vTip.className="";
	}else{
		vTip.className="onError";
	}
	var firmId = document.getElementById("m_FirmId").value;
	var commodityId = document.getElementById("commodityId").value;
	if(commodityId){
		checkAction.existMemCustomerTakeFee(commodityId, firmId, function(isExist){
			if(isExist){
				alert('��Ϣ�Ѵ��ڣ����������');
				document.getElementById("commodityId").value="";
				document.getElementById("m_FirmId").value="";
				document.getElementById("commodityId").focus();
			} else {
				checkAction.existApplyAddAnOther(commodityId, firmId, '${applyType}', function(applyView){
					if(applyView){
						alert("����������"+applyView.proposer+"��"+applyView.modTimeString+"�ύ");
						document.getElementById("commodityId").value="";
						document.getElementById("m_FirmId").value="";
						document.getElementById("commodityId").focus();
					}
				});
			}
		});
	}
	return flag;
}

function feeRate(userID) {
	var feeAlgr = document.getElementById("feeAlgr").value;
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + '_vTip');
	var innerHTML = "";
	var str = "�������0�ҡ�100������";
	var flag = false;
	if (isEmpty(user.value + "")) {
		innerHTML = "����Ϊ��";
	} else if (feeAlgr == 1) {//�ٷֱ�
		if (!flote(user.value, 2)) {
			innerHTML = "���2λС��������";
		} else if (user.value < 0 || user.value > 100) {
			innerHTML = str;
		} else {
			innerHTML = "";
			flag = true;
		}
	} else {//����ֵ
		if (!flote(user.value, 2)) {
			innerHTML = "���2λС��������";
		} else if (user.value < 0) {
			innerHTML = "�������0����";
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

function mkt_FeeRate(userID) {
	var feeAlgr = document.getElementById("feeAlgr").value;
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + '_vTip');
	var innerHTML = "";
	var str = "�������0�ҡ�100������";
	var flag = false;
	if (isEmpty(user.value + "")) {
		innerHTML = "����Ϊ��";
	} else if (feeAlgr == 1) {//�ٷֱ�
		if (!flote(user.value, 2)) {
			innerHTML = "���2λС��������";
		} else if (user.value < 0 || user.value > 100) {
			innerHTML = str;
		} else {
			innerHTML = "";
			flag = true;
		}
	} else {//����ֵ
		if (!flote(user.value, 2)) {
			innerHTML = "���2λС��������";
		} else if (user.value <0) {
			innerHTML = "�������0����";
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
function feeMode(userID) {
	var vTip = document.getElementById(userID + '_vTip');
	vTip.innerHTML =  "<%= feeMode%>";
	vTip.className = "";
	return true;
}
function myfocus(userID) {
	/*var vTip = document.getElementById(userID + '_vTip');
	var innerHTML = "";
	if("commodityId"==userID){
		innerHTML = "��ѡ����Ʒ";
	}
	if("m_FirmId"==userID){
		innerHTML = "��ѡ���Ա";
	}
	if ('feeAlgr' == userID) {
		innerHTML = "��ѡ��";
	}
	if ('feeRate' == userID) {
		innerHTML = "����Ϊ��";
	}
	if ('feeMode' == userID) {
		innerHTML = "��ѡ��";
	}
	if ('mkt_FeeRate' == userID) {
		innerHTML = "����Ϊ��";
	}
	vTip.innerHTML = innerHTML;
	vTip.className = "onFocus";*/
}

function feeAlgrChange(fee) {
	if (fee == '1') {
		document.getElementById("feeRate_span").innerHTML = "%";
		document.getElementById("mkt_feeRate_span").innerHTML = "%";
	}
	if (fee == '2') {
		document.getElementById("feeRate_span").innerHTML = "";
		document.getElementById("mkt_feeRate_span").innerHTML = "";
	}
}
function setCommodityName() {
	var obj = document.getElementById('commodityId');
	var m_FirmId = document.getElementById('m_FirmId');
	var value = obj.options[obj.selectedIndex].text;
	var firmValue = m_FirmId.options[m_FirmId.selectedIndex].text;
	document.getElementById('commodityName').value = value;
	document.getElementById('memberName').value = firmValue;

}
</script>
<script type="text/javascript">
feeAlgrChange('${obj.feeAlgr_v}');
</script>