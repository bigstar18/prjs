<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<script type='text/javascript' src='<%=basePath%>/dwr/engine.js'>
</script>
<script type='text/javascript' src='<%=basePath%>/dwr/util.js'>
</script>
<script type="text/javascript" src='<%=basePath%>/dwr/interface/checkAction.js' />
</script>
<%
	String stepRate = "";
%>
<html>
	<head>
		<title>��Ա������ֵ���</title>
	</head>
	<body class="st_body" style="overflow-y: hidden">
		<br />
		<form name="frm"
			action="${basePath}/commodity/memberFundsLadder/add.action"
			method="post" targetType="hidden">
			<div class="div_scro">
				<div class="st_title">
					<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />
					&nbsp;&nbsp;��ϸ��Ϣ
				</div>
				<table border="0" width="90%" align="center">
					<tr height="30"></tr>
					<tr>
						<td>
							<table border="0" cellspacing="0" cellpadding="4" width="100%"
								align="center" class="st_bor">
								<div class="div_cxtjmid">
									<img src="<%=skinPath%>/cssimg/13.gif" />
									&nbsp;&nbsp;��Ա������ֵ
								</div>
								<tr height="40">
									<td align="right" width="30%">
										��Ա����:
									</td>
									<td width="30%">
									<input  type="hidden" 	id="memberName"	name="special.memberName">
										<select name="special.memberNo" id="memberNo" onchange="setMemberName();"
											onblur="myblur('memberNo')" onfocus="myfocus('memberNo')">
											<option value="">
												��ѡ��
											</option>
											<c:forEach items="${memberInfoList}" var="result">
												<option value="${result.id}">
													${result.name}
												</option>
											</c:forEach>
										</select>
										<strong class="check_input">&nbsp;*</strong>
									</td>
									<td>
										<div id="memberNo_vTip"></div>
									</td>
								</tr>
								<c:forEach items="${fundsLadderVO.fundsLadderList}"
									var="fundsLadder">
									<tr height="40">
										<c:forEach var="fundder" items="${ladderList}">
											<c:if test="${fundder.stepNo==fundsLadder.stepNo}">
												<c:set var="note" value="${fundder.note}"></c:set>
											</c:if>
										</c:forEach>
										<td align="right">
											${note}�ĳ�����ֵ����:
										</td>
										<td>
											<input class="input_text" type="text" style="text-align:right" 
												id="stepRate${fundsLadder.stepNo}"
												onblur="myblur('stepRate${fundsLadder.stepNo}')"
												onfocus="myfocus('stepRate${fundsLadder.stepNo}')"
												name="special.stepRate_v" value="">%<strong class="check_input">&nbsp;*</strong>
										</td>
										<td align="left" height="40" width="35%">
											<div id="stepRate${fundsLadder.stepNo}_vTip"><%=stepRate%></div>
										</td>
									</tr>
								</c:forEach>
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
							<button class="btn_sec" id="update" onclick="addFundsLadder()">
								���
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
function myblur(userID) {
	var number = document.getElementsByName("special.stepRate_v").length;
	var flag = true;
	if (userID != null && userID.indexOf('stepRate') >= 0) {
		flag = delayFee(userID);
	} else if("memberNo" == userID){
		flag = memberNo(userID);
	} else {
		for ( var i = 1; i <= number; i++) {
			if (!delayFee("stepRate" + i)) {
				flag = false;
			}
		}
		if (!memberNo("memberNo"))
			flag = false;
	}
	return flag;
}

function memberNo(userID){
	var innerHTML="";
	var vTip = document.getElementById(userID + "_vTip");
	var user = document.getElementById(userID);
	var flag = false;
	if (isEmpty(user.value+'')) {
		innerHTML = "��ѡ���Ա";
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
	if(firmId){
		checkAction.existMemberFundsLadder(firmId, function(isExist){
			if(isExist){
				alert('��Ϣ�Ѵ��ڣ����������');
				document.getElementById("memberNo").value="";
				document.getElementById("memberNo").focus();
			}
		});
	}
	return flag;
}
function delayFee(userID) {
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	var innerHTML = "";
	var flag = false;
	if (isEmpty(user.value)) {
		innerHTML = "����Ϊ��";
	} else if (!flote(user.value, 2)) {
		innerHTML = "���2λС��������";
	} else if (user.value < 0 || user.value > 100) {
		innerHTML = "������>0�ҡ�100������";
	} else {
		innerHTML = "<%=stepRate%>";
		flag = true;
	}
	if (flag) {
		vTip.className = "";
	} else {
		vTip.className = "onError";
	}
	vTip.innerHTML = innerHTML;
	return flag;
}
function myfocus(userID) {
	/*var vTip = document.getElementById(userID+"_vTip");
	vTip.innerHTML = "����Ϊ��";
	vTip.className="onFocus";*/
}
function addFundsLadder() {
	if (!myblur("all")) {
		return false;
	}
	var stepRates = document.getElementsByName("stepRate");
	for ( var i = 0; i < stepRates.length; i++) {
		if (stepRates[i].value == '') {
			var num = i + 1;
			alert('��' + num + '����ֵ������Ϊ��');
			stepRates[i].focus();
			return false;
		}
		if (isNaN(stepRates[i].value)) {
			var num = i + 1;
			alert('��' + num + '����ֵӦΪ����');
			stepRates[i].focus();
			return false;
		}
	}
	var vaild = window.confirm("��ȷ��Ҫ������");
	if (vaild == true) {
		frm.submit();
	} else {
		return false;
	}
}
function setMemberName() {
	var obj = document.getElementById('memberNo');
	var value = obj.options[obj.selectedIndex].text;
	document.getElementById('memberName').value = value;
}
</script>
<%@ include file="/public/footInc.jsp"%>