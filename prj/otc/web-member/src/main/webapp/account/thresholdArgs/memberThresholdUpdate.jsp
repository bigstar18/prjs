<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%
	String minRiskFund = "��Ա����*��֤�����";
	String warnTh = "����ʵʱ�ķ��ձ�֤��/ǰһ�����ս�������ĩ���ձ�֤��*100%";
	String frozenTh = "����ʵʱ�ķ��ձ�֤��/ǰһ�����ս�������ĩ���ձ�֤��*100%";
	String cu_F_WarnTh = "";
	String m_SelfTradeRate = "";
%>
<html>
	<head>
		<title>��Ա������ֵ�޸�</title>
	</head>
	<body class="st_body" style="overflow-y: hidden">
		<div style="overflow: auto; height: 350px;">
			<form name="frm"
				action="${basePath}/account/memberThreshold/update.action"
				method="post" targetType="hidden">
				<input type="hidden" name="obj.memberNo" value="${obj.memberNo }">
				<table border="0" width="95%" align="center">
					<tr>
						<td>
							<div class="st_title">
								<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />
								&nbsp;&nbsp;��Ա������ֵ�޸�
							</div>
							<table border="0" cellspacing="0" cellpadding="4" width="100%"
								align="center" class="st_bor">
								<tr height="35">
									<td align="right" width="25%">
										��Ա���:
									</td>
									<td colspan="2">
										<input class="input_text" type="text" id="id"
											name="obj.thresholdId" value="${obj.id}" readonly="readonly"
											style="background-color: #bebebe">
									</td>
								</tr>
								<tr height="35">
									<td align="right">
										��Ա����:
									</td>
									<td>
										<input class="input_text" type="text" id="name"
											name="obj.name" value="${obj.memberName}" readonly="readonly"
											style="background-color: #bebebe">
									</td>
								</tr>
								<tr height="35">
									<td align="right" width="25%">
										��Ա������ֵ:
									</td>
									<td width="35%">
										<input class="input_text" type="text" id="minRiskFund" style="text-align:right"
											name="obj.minRiskFund" value="<fmt:formatNumber value="${obj.minRiskFund}"   pattern="###,##0.00"/>"
											onblur="myblur('minRiskFund')"
											onfocus="myfocus('minRiskFund')"><span>��Ԫ��</span>
									</td>
									<td align="left" height="40">
										<div id="minRiskFund_vTip" class="onFocus"><%=minRiskFund%></div>
									</td>
								</tr>
								<tr height="35">
									<td align="right">
										��ԱԤ��������:
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
								<tr height="35">
									<td align="right">
										��Ա���������:
									</td>
									<td>
										<input class="input_text" type="text" id="frozenTh" style="text-align:right"
											name="obj.frozenTh_v" value="${obj.frozenTh_v}"
											onblur="myblur('frozenTh')"	onfocus="myfocus('frozenTh')">%
									</td>
									<td align="left" height="40">
										<div id="frozenTh_vTip" class="onFocus"><%=frozenTh%></div>
									</td>
								</tr>
								<tr height="35">
									<td align="right">
										<font color="gray">�ͻ��ʽ�Ԥ����ֵ:&nbsp;</font>
									</td>
									<td>
										<input class="input_text" type="text" id="cu_F_WarnTh" style="text-align:right"
											name="obj.cu_F_WarnTh" value="<fmt:formatNumber value="${obj.cu_F_WarnTh}"   pattern="###,##0.00"/>" readonly="readonly">
											<span>��Ԫ��</span>
									</td>
									<td align="left" height="40">
										<div id="cu_F_WarnTh_vTip"><%=cu_F_WarnTh%></div>
									</td>
								</tr>
								<tr height="35">
									<td align="right">
										��Ա�ǿͻ�ͷ�罻�ױ���:
									</td>
									<td>
										<input class="input_text" type="text" id="m_SelfTradeRate" style="text-align:right"
											name="obj.m_SelfTradeRate_v" value="${obj.m_SelfTradeRate_v }"
											onblur="myblur('m_SelfTradeRate')"
											onfocus="myfocus('m_SelfTradeRate')">%
									</td>
									<td align="left" height="40">
										<div id="m_SelfTradeRate_vTip" ><%=m_SelfTradeRate%></div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="tab_pad">
			<table border="0" cellspacing="0" cellpadding="4" width="100%"
				align="center">
				<tr>
					<td align="center">
						<button class="btn_sec" id="update"
							onclick="updateMemberThreshold()">
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
	</body>
</html>
<script type="text/javascript">
function updateMemberThreshold() {
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
	} else if ("m_SelfTradeRate" == userID) {
		flag = m_SelfTradeRate(userID);
	} else {
		if (!minRiskFund("minRiskFund"))
			flag = false;
		if (!warnTh("warnTh"))
			flag = false;
		if (!frozenTh("frozenTh"))
			flag = false;
		if (!m_SelfTradeRate("m_SelfTradeRate"))
			flag = false;
	}
	return flag;
}
//��Ա������ֵ
function minRiskFund(userID) {
	var innerHTML = "";
	var str = "�������0����";
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
		}  else {
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
	var str = "�������200������";
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
			innerHTML =str;
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

function m_SelfTradeRate(userID) {
	var innerHTML = "";
	var str = "�������500������"; 
	var marg = document.getElementById("m_SelfTradeRate").value;
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	var flag = false;
	if (isEmpty(user.value + "")) {
		innerHTML = "����Ϊ��";
	} else {
		if(!flote(user.value,'2')){
			innerHTML = "�����λС��������";
		} else if(user.value > 500) {
			innerHTML = str;
		} else {
			innerHTML = '<%=m_SelfTradeRate%>';
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
function myfocus(userID) {
	/*var vTip = document.getElementById(userID + '_vTip');
	var innerHTML = "";
	if ('minRiskFund' == userID) {
		innerHTML = "����Ϊ��";
	}
	if ('warnTh' == userID) {
		innerHTML = "����Ϊ��";
	}
	if ('frozenTh' == userID) {
		innerHTML = "����Ϊ��";
	}
	if ('cu_F_WarnTh' == userID) {
		innerHTML = "����Ϊ��";
	}
	if ('m_SelfTradeRate' == userID) {
		innerHTML = "����Ϊ��";
	}
	vTip.innerHTML = innerHTML;
	vTip.className = "onFocus";*/
}
</script>
<%@ include file="/public/footInc.jsp"%>