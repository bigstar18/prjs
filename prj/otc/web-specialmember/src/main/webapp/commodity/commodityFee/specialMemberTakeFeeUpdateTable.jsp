<%@ page contentType="text/html;charset=GBK"%>
<%
	String feeRate="";
	String feeMode="";
	String mkt_FeeRate="";
%>
<div class="div_cxtjmid"><img src="<%=skinPath%>/cssimg/13.gif" />&nbsp;�ر��Ա������</div>
<table border="0" cellspacing="0" cellpadding="4" width="100%"
	align="center" class="st_bor">
	<input class="input_text_pwdmin" type="hidden" id="commodityId"
				name="obj.commodityId" value="${obj.commodityId}"
				readonly="readonly">
	<tr height="40">
		<td align="right" width="23%">
			��Ʒ����:
		</td>
		<td width="30%">
			<input class="input_text_pwdmin" type="text" id="name"
				name="obj.commodityName" value="${obj.commodityName}"
				readonly="readonly">
		</td>
	</tr>
	<tr height="40">
		<td align="right">
			�ر��Ա����:
		</td>
		<td>
			<input type="hidden" name="obj.m_FirmId" value="${obj.m_FirmId }">
			<input class="input_text_pwdmin" type="text" id="memberName"
				name="obj.memberName" value="${obj.memberName}" readonly="readonly">
		</td>
	</tr><tr height="40">
		<td align="right">
			�������㷨:
		</td>
		<td>
			<select id="feeAlgr" name="obj.feeAlgr_v" class="select_widmid"
				 onchange="feeAlgrChange(this.value);" class="select_widmid">
				<c:forEach items="${feeAlgrMap}" var="maps">
					<option value="${maps.key}" <c:if test="${maps.key==obj.feeAlgr_v}">selected=selected</c:if>>
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
				name="obj.feeRate_v" value="${obj.feeRate_v}">
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
				name="obj.mkt_FeeRate_v" value="${obj.mkt_FeeRate_v}">
			<span id="mkt_feeRate_span"></span>
		</td>
		<td align="left" height="40">
			<div id="mkt_FeeRate_vTip" ><%= mkt_FeeRate%></div>
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
					<option value="${maps.key}"<c:if test="${maps.key==obj.feeMode}">selected=selected</c:if>>${maps.value }</option>
				</c:forEach>
			</select>
		</td>
		<td align="left" height="40">
			<div id="feeMode_vTip" ><%= feeMode%></div>
		</td>
	</tr>
	<tr height="40">
		<td align="right">
			���Ʒ�ʽ:
		</td>
		<td>
			<select id="operate" name="obj.operate" class="select_widmid">
				<option value="P">
					���Ի�
				</option>
				<option value="D">
					Ĭ��
				</option>
			</select>
			<script type="text/javascript">
document.getElementById('operate').value = '${obj.operate}';
</script>
		</td>
	</tr>
</table>

<script type="text/javascript">
<!--
function updateTakeFeeStatus() {
	if (parseFloat(frm.feeRate.value) < parseFloat(frm.mkt_FeeRate.value)) {
		alert("��������ȡ�����ѱ���С��������ϵ��");
		return false;
	}
	var vaild = window.confirm("��ȷ��Ҫ������");
	if (vaild == true) {
		frm.submit();
	} else {
		return false;
	}
}

function myblur(userID) {
	var flag = true;
	 if ("feeRate" == userID) {
		flag = feeRate(userID);
	} else if ("feeMode" == userID) {
		flag = feeMode(userID);
	} else if ("mkt_FeeRate" == userID) {
		flag = mkt_FeeRate(userID);
	} else {
		if (!feeRate("feeRate"))
			flag = false;
		if (!feeMode("feeMode"))
			flag = false;
		if (!mkt_FeeRate("mkt_FeeRate"))
			flag = false;
	}
	return flag;
}
function feeRate(userID) {
	var feeAlgr = document.getElementById("feeAlgr").value;
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
			innerHTML = "���2λС���ķǸ���";
		} else if (user.value < 0 || user.value > 100) {
			innerHTML = str;
		} else {
			innerHTML = "";
			flag = true;
		}
	} else {//����ֵ
		if (!flote(user.value, 2)) {
			innerHTML = "���2λС���ķǸ���";
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
	var feeAlgr = document.getElementById("feeAlgr").value;
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + '_vTip');
	var innerHTML = "";
	var str = "�������0�ҡ�100������";
	var flag = false;
	if (isEmpty(user.value + "")) {
		innerHTML = "����Ϊ��";
	} else if (feeAlgr == 1) {//�ٷֱ�
		if (!flote(user.value,2)) {
			innerHTML = "���2λС���ķǸ���";
		} else if (user.value < 0 || user.value > 100) {
			innerHTML = str;
		} else {
			innerHTML = "";
			flag = true;
		}
	} else {//����ֵ
		if (!flote(user.value, 2)) {
			innerHTML = "���2λС���ķǸ���";
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
function feeMode(userID) {
	var vTip = document.getElementById(userID + '_vTip');
	vTip.innerHTML =  "<%= feeMode%>";
	vTip.className = "";
	return true;
}
function myfocus(userID) {
	/*var vTip = document.getElementById(userID + '_vTip');
	var innerHTML = "";
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
//-->
</script>
<script type="text/javascript">
feeAlgrChange("${obj.feeAlgr_v}");
</script>