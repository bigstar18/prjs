<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title>�г���������</title>
	</head>
	<body leftmargin="0" topmargin="0" class="st_body"
		style="overflow-y: hidden">
		<form name="frm"
			action="${basePath}/settlement/marketParametersSecond/update.action"
			method="post">
			<%
				String clearDelaySecs = "";
			%>
			<div>
				<table border="0" width="50%" align="center">
					<tr>
						<td height="100">
							&nbsp;
						</td>
					</tr>
					<tr>
						<td>
							<div class="st_title">
								<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />
								&nbsp;&nbsp;��������
							</div>
							<table border="0" cellspacing="0" cellpadding="4" width="650px"
								height="100" align="center" class="st_bor">
								<input type="hidden" name="obj.marketCode"
									value="${obj.marketCode}">
								<tr height="40">
									<td align="right">����ģʽ:</td>
									<td>
										�ֶ�:<input type="radio"  id="clearRunMode" name="obj.clearRunMode" value="0" <c:if test="${obj.clearRunMode==0}">checked="checked"</c:if> >
										�Զ�:<input type="radio"  id="clearRunMode" name="obj.clearRunMode" value="1" <c:if test="${obj.clearRunMode==1}">checked="checked"</c:if>>
										<span>&nbsp;&nbsp;<font color="red">*</font>(��ǰ����ģʽ��
										<font color="red">
										<c:if test="${market.clearRunMode==0}">�ֶ�</c:if>
										<c:if test="${market.clearRunMode==1}">�Զ�</c:if>
										</font>)</span>
									</td>
									<td align="left" height="40"></td>
								</tr>
								<tr height="40">
									<td align="right" width="115px">�����ӳ�����:</td>
									<td width="380px">
										<input type="text" class="input_text" id="clearDelaySecs"
											onblur="myblur('clearDelaySecs')"
											onfocus="myfocus('clearDelaySecs')" name="obj.clearDelaySecs"
											value="${obj.clearDelaySecs}" size="10" maxlength="10">
										<span><font color="red">*</font>(��ǰ�����ӳ�������<font
											color="red">${market.clearDelaySecs}</font>)</span>
									</td>
									<td align="left" height="40"  width="160px">
										<div id="clearDelaySecs_vTip"><%=clearDelaySecs%></div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
			<div>
				<table cellspacing="0" cellpadding="0" border="0" width="80%"
					align="center">
					<tr>
						<td align="center" height="20">
							&nbsp;
						</td>
					</tr>
					<tr>
						<td align="center">
							<button class="btn_sec" onClick="updateMarket()" id="update">
								�޸�
							</button>
							&nbsp;&nbsp;&nbsp;
							<button class="btn_sec" onClick="updateMarketInfo()" id="update">
								ʵʱ��Ч
							</button>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>
<script type="text/javascript">
function myblur(userID) {
	var flag = true;
	if ("clearDelaySecs" == userID) {
		flag = clearDelaySecs(userID);
	} else {
		if (!clearDelaySecs("clearDelaySecs"))
			flag = false;
	}
	return flag;
}
//��������
function clearDelaySecs(userID) {
	var flag = false;
	var str = "�������3600��������";
	var innerHTML = "";
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	if (isEmpty(user.value)) {
		innerHTML = "����Ϊ��";
	} else if (!integer(user.value)) {
		innerHTML = str;
	} else if (user.value < 0 || user.value > 3600) {
		innerHTML = str;
	} else {
		innerHTML = '<%=clearDelaySecs%>';
		flag = true;
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
	/*var vTip = document.getElementById(userID+"_vTip");
	vTip.innerHTML = "����Ϊ��";
	vTip.className = "onFocus";*/

}
function updateMarket() {
	var flag = myblur("all");
	if (!flag) {
		return false;
	}
	if (!isFormChanged(null, null)) {
		alert("û���޸�����");
		return false;
	}
	var vaild = affirm("��ȷ��Ҫ������");
	if (vaild == true) {
		frm.submit();
	} else {
		return false;
	}
}
function updateMarketInfo() {
	var vaild = affirm("��ȷ��Ҫ������");
	if (vaild == true) {
		frm.action="${basePath}/settlement/marketParametersSecond/updateMarketInfo.action"
		frm.submit();
	} else {
		return false;
	}
}
</script>
<%@ include file="/public/footInc.jsp"%>