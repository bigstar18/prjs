<%@ page contentType="text/html;charset=GBK"%>
<%
	String feeRate = "";
	String feeMode = "";
	String mkt_FeeRate = "";
%>
<html>
	<head>
	</head>
	<body leftmargin="0" topmargin="0" class="st_body"
		style="overflow-y: hidden">
		<table border="0" width="100%" align="center">
			<tr height="30"></tr>
			<tr>
				<td>
					<div class="div_cxtj">
						<img src="<%=skinPath%>/cssimg/13.gif" />
						Ĭ��������
					</div>
					<table border="0" cellspacing="0" cellpadding="4" width="100%"
						align="center" class="st_bor">
						<input type="hidden" name="obj.commodityId"
							value="${obj.commodityId}">
						<input type="hidden" name="obj.firmId" value="${obj.firmId}">
						<tr height="40">
							<td align="right" width="25%">
								��Ʒ����:
							</td>
							<td width="30%">
								<input type="text" id="name" class="input_text_pwdmin"
									name="obj.commodityName" value="${obj.commodityName}"
									readonly="readonly">
							</td>
						</tr>
						<tr height="40">
							<td align="right">
								���ͣ�
							</td>
							<td>
								<input type="text" id="firmName" class="input_text_pwdmin"
									name="obj.firmName" value="${firmDisMap[obj.firmId]}"
									readonly="readonly">
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
									<c:forEach items="${commodityFeeModeMap}" var="maps">
						<option value="${maps.key}"	<c:if test="${obj.feeMode==maps.key}">selected="selected"</c:if>>${maps.value}</option>
									</c:forEach>
								</select>
							</td>
							<td align="left" height="40">
								<div id="feeMode_vTip" ><%=feeMode%></div>
							</td>
						</tr>
						<tr height="40">
							<td align="right">
								�������㷨:
							</td>
							<td>
								<select id="feeAlgr" name="obj.feeAlgr_v"
									onchange="feeAlgrChange();" class="select_widmid">
									<c:forEach items="${commodityFeeAlgrMap}" var="maps">
										<option value="${maps.key}"
											<c:if test="${maps.key==obj.feeAlgr_v}">selected=selected</c:if>>
											${maps.value}
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
								<input type="text" onblur="myblur('feeRate')" style="text-align:right"
									onfocus="myfocus('feeRate')" class="input_text" id="feeRate"
									name="obj.feeRate_v" value="${obj.feeRate_v}">
								<span id="feeRate_span"></span>
							</td>
							<td align="left" height="40">
								<div id="feeRate_vTip" ><%=feeRate%></div>
							</td>
						</tr>
						<tr height="40">
							<td align="right">
								��������ȡ������:
							</td>
							<td width="30%">
								<input class="input_text" type="text" style="text-align:right"
									onblur="myblur('mkt_FeeRate')" onfocus="myfocus('mkt_FeeRate')"
									id="mkt_FeeRate" name="obj.mkt_FeeRate_v"
									value="${obj.mkt_FeeRate_v}">
								<span id="mkt_feeRate_span"></span>
							</td>
							<td align="left" height="40">
								<div id="mkt_FeeRate_vTip" ><%=mkt_FeeRate%></div>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>


<script type="text/javascript">
function myblur(userID) {
	var flag = true;

	if ("feeRate" == userID||"mkt_FeeRate"==userID) {
		flag = feeRate(userID);
	} else if ("feeMode" == userID) {
		flag = feeMode(userID);
	} else {
		if (!feeRate("feeRate"))
			flag = false;
		if (!feeRate("mkt_FeeRate"))
			flag = false;
		if (!feeMode("feeMode"))
			flag = false;
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
	vTip.innerHTML = "<%=feeMode%>";
	vTip.className = "";
	return true;
}
function myfocus(userID) {
	/*var vTip = document.getElementById(userID+'_vTip');
	var innerHTML = "";
	if('feeAlgr'==userID){
		innerHTML = "��ѡ��";
	}
	if('feeRate' == userID){
		innerHTML  =  "����Ϊ��";
	}
	if('feeMode' == userID){
		innerHTML = "��ѡ��";
	}
	vTip.innerHTML = innerHTML;
	vTip.className = "onFocus";*/
}

function feeAlgrChange() {
	var fee = myForm.feeAlgr.value;
	if (fee == '1') {
		document.getElementById("feeRate_span").innerHTML = "%";
		document.getElementById("mkt_feeRate_span").innerHTML = "%";
	}
	if (fee == '2') {
		document.getElementById("feeRate_span").innerHTML = "";
		document.getElementById("mkt_feeRate_span").innerHTML = "";
	}
}
</script>
<script type="text/javascript">
feeAlgrChange('${obj.feeAlgr_v}');
</script>