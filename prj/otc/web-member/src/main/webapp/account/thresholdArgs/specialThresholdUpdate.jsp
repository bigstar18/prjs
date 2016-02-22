<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%
	String minRiskFund = "�ر��Ա����*��֤�����";
	String warnTh = "1-�����ر��Աӯ���ܺ�/(��һ��������ĩ���ձ�֤��������)��";
	String frozenTh = "1-�����ر��Աӯ���ܺ�/(��һ��������ĩ���ձ�֤��������)";
%>
<html>
	<head>
		<title>�ر��Ա������ֵ�޸�</title>
	</head>
	<body class="st_body" style="overflow-y: hidden">
		<form name="frm"
			action="${basePath}/account/specialThreshold/update.action"
			method="post" targetType="hidden">
			<input type="hidden" name="obj.firmId" value="${obj.firmId }">
			<div style="overflow: auto; height: 330px;">
				<table border="0" width="95%" align="center">
					<tr>
						<td>
							<div class="st_title">
								<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />
								&nbsp;&nbsp;�ر��Ա������ֵ�޸�
							</div>
							<table border="0" cellspacing="0" cellpadding="4" width="100%"
								align="center" class="st_bor">
								<tr height="35">
									<td align="right" width="28%">
										�ر��Ա���:
									</td>
									<td colspan="2">
										<input class="input_text" type="text" id="id"
											name="obj.thresholdId" value="${obj.id}" readonly="readonly"
											style="background-color: #bebebe">
									</td>
								</tr>
								<tr height="35">
									<td align="right" width="25%">
										�ر��Ա����:
									</td>
									<td colspan="2">
										<input class="input_text" type="text" id="name"
											name="obj.name" value="${obj.memberName}" readonly="readonly"
											style="background-color: #bebebe">
									</td>
								</tr>
								<tr height="50">
									<td align="right" width="25%">
										�ر��Ա������ֵ:
									</td>
									<td width="40%">
										<input class="input_text" type="text" id="minRiskFund" style="text-align:right"
											name="obj.minRiskFund" value="<fmt:formatNumber value="${obj.minRiskFund}"   pattern="###,##0.00"/>"
											onblur="myblur('minRiskFund')"
											onfocus="myfocus('minRiskFund')"><span>��Ԫ��</span>
									</td>
									<td align="left" height="40">
										<div id="minRiskFund_vTip" class="onFocus"><%=minRiskFund%></div>
									</td>
								</tr>
								<tr height="50">
									<td align="right">
										�ر��ԱԤ��������:
									</td>
									<td>
										<input class="input_text" type="text" id="warnTh" style="text-align:right"
											name="obj.warnTh_v" value="${obj.warnTh_v}"
											onblur="myblur('warnTh')"
											onfocus="myfocus('warnTh')">%
									</td>
									<td align="left" height="40">
										<div id="warnTh_vTip" class="onFocus"><%=warnTh%></div>
									</td>
								</tr>
								<tr height="50">
									<td align="right">
										�ر��Ա���������:
									</td>
									<td>
										<input class="input_text" type="text" id="frozenTh" style="text-align:right"
											name="obj.frozenTh_v" value="${obj.frozenTh_v}"
											onblur="myblur('frozenTh')"
											onfocus="myfocus('frozenTh')">%
									</td>
									<td align="left" height="40">
										<div id="frozenTh_vTip" class="onFocus"><%=frozenTh%></div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
			<div class="tab_pad">
				<table border="0" cellspacing="0" cellpadding="4" width="100%"
					align="center">
					<tr>
						<td align="center">
							<button class="btn_sec" id="update"
								onclick="updateSpecialThreshold()">
								����
							</button>
						</td>
						<td align="center">
							<button class="btn_sec" onclick="window.close()">
								�ر�
							</button>
						</td>
					</tr>
				</table>
			</div>
		</form>

	</body>
</html>
<script type="text/javascript">
function updateSpecialThreshold() {
	var flag = myblur("all");
	if(!flag){return false;}
	if(!isFormChanged(null,null)){
			alert("û���޸�����");
			return false;}
		var vaild = window.confirm("��ȷ��Ҫ������");
		if (vaild == true) {
			frm.submit();
		} else {
			return false;
		}
}
function myblur(userID) {
	var flag = true;

	if ("minRiskFund" == userID) {
		flag = minRiskFund(userID);
	} else if ("warnTh" == userID) {
		flag = warnTh(userID);
	} else if ("frozenTh" == userID) {
		flag = frozenTh(userID);
	} else {
		if (!minRiskFund("minRiskFund"))
			flag = false;
		if (!warnTh("warnTh"))
			flag = false;
		if (!frozenTh("frozenTh"))
			flag = false;
	}
	return flag;
}
//�ر��Ա������ֵ
function minRiskFund(userID) {
	var innerHTML = "";
	var str ="�������0����";
	var marg = document.getElementById("minRiskFund").value;
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	removeStr(userID);
	var flag = false;
	if (isEmpty(user.value + "")) {
		innerHTML = "����Ϊ��";
	} else {
		if (user.value < 0) {
			innerHTML =str;
		}else if(!flote(user.value,'2')){
			innerHTML = "�����λС��������";
		} else {
			innerHTML = '<%=minRiskFund%>';
			transStr(userID);
			flag = true;
		}
	}
	vTip.innerHTML = innerHTML;
	if (flag) {
		vTip.className = "onFocus";
	} else {
		vTip.className = "onError";
	}
	return flag;
}

function warnTh(userID) {
	var innerHTML = "";
	var str ="�������������200������";
	var marg = document.getElementById("warnTh").value;
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	var flag = false;
	if (isEmpty(user.value + "")) {
		innerHTML = "����Ϊ��";
	} else {
		if(!flote(user.value,'2')){
			innerHTML = "�����λС��������";
		} else if(user.value > 200) {
			innerHTML = str;
		} else {
			innerHTML = '<%=warnTh%>';
			flag = true;
		}
	}
	vTip.innerHTML = innerHTML;
	if (flag) {
		vTip.className = "onFocus";
	} else {
		vTip.className = "onError";
	}
	return flag;
}
function frozenTh(userID) {
	var innerHTML = "";
	var str = "�������200������";
	var marg = document.getElementById("frozenTh").value;
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	var flag = false;
	if (isEmpty(user.value + "")) {
		innerHTML = "����Ϊ��";
	} else {
		if(!flote(user.value,'2')){
			innerHTML = "�����λС��������";
		} else if(user.value > 200) {
			innerHTML =str;
		} else {
			innerHTML = '<%=frozenTh%>';
			flag = true;
		}
	}
	vTip.innerHTML = innerHTML;
	if (flag) {
		vTip.className = "onFocus";
	} else {
		vTip.className = "onError";
	}
	return flag;
}
function myfocus(userID) {
	/*var vTip = document.getElementById(userID + '_vTip');
	var innerHTML = "";
	if ('minRiskFund' == userID) {
		innerHTML = "����Ϊ��";
	}
	if ('warnTh' == userID) {
		innerHTML = "����Ϊ��";
	}
	vTip.innerHTML = innerHTML;
	vTip.className = "onFocus";*/
}
</script>
<%@ include file="/public/footInc.jsp"%>