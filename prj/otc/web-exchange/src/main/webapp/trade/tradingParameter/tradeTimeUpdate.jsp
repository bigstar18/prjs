<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>


<html>
	<head>
		<title>�޸Ľ���ʱ��</title>

		<script language="JavaScript" src="<%=basePath%>/public/Date.js"></script>	
	</head>
	<body onload="return window_onload()">
		<form name="frm" action="${basePath}/tradeManage/commodityTradingParameter/updateTradeTime.action" method="post" targetType="hidden">
			<input type="hidden" name="tradeTimeType" id="tradeTimeType">
			<input type="hidden" name="obj.sectionId" value="${obj.sectionId }">
			<div>
			<table border="0" width="90%" align="center" >
				<tr height="20"></tr>
				<tr>
					<td>
				<div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;&nbsp;���׽��޸�</div>
				<table cellSpacing="0" cellPadding="5" width="100%" border="0"
		align="center" class="st_bor">
		<tr id="showName">
			<td align="right">
				���׽����ƣ�
			</td>
			<td>
				<input class="from" type="text" name="obj.name" id="name" value="${obj.name}">
				<strong class="check_input">&nbsp;*</strong>
			</td>
		</tr>
		<tr>
			<td align="right">
				���׿�ʼʱ�䣺
			</td>
			<td>
				<input class="from" type="text" name="obj.startTime" value="${obj.startTime}" id="startTime" maxlength="8" class="text"
					onkeypress="return suffixNamePress()" />
				<strong class="check_input">&nbsp;* HH:MM:SS(24Сʱ��)</strong>
			</td>
		</tr>
		<tr>
			<td align="right">
				���׽���ʱ�䣺
			</td>
			<td>
				<input class="from" type="text" name="obj.endTime" value="${obj.endTime}" id="endTime" maxlength="8" class="text"
					onkeypress="return suffixNamePress()" />
				<strong class="check_input">&nbsp;* HH:MM:SS(24Сʱ��)</strong>
			</td>
		</tr>
		<!-- <tr>
			<td align="right">
				���׽�״̬��
			</td>
			<td>
				<select name="obj.status" id="status" style="width: 150">
					<c:forEach items="${tradeStatusMap}" var="tradeStatusMap">
						<option value="${tradeStatusMap.key}">
							<c:out value="${tradeStatusMap.value}" />
						</option>
					</c:forEach>
				</select>
				<strong class="check_input">&nbsp;*</strong>
			</td>
			</tr> -->
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
							<button id="updateBaseInfo" class="btn_sec" onclick="updateCommodity()">����</button>
						</td>
						<td align="center">
							<button class="btn_sec" onclick="window.close()">�ر�</button>
						</td>
					</tr>
				</table>
				</div>
		</form>
	</body>
</html>
<script type="text/javascript">	

function window_onload() {
	//highlightFormElements();
	//document.getElementById("status").value = "${obj.status}";
}

//save
function updateCommodity() {
	if(!isFormChanged(null, null)){
		alert("û���޸�����");
		return false;
	}
	if (affirm("��ȷ��Ҫ�ύ��")) {
		if (document.getElementById("startTime").value == "") {
			alert("���׿�ʼʱ�䲻��Ϊ�գ�");
			document.getElementById("startTime").focus();
			return false;
		}
		if (document.getElementById("endTime").value == "") {
			alert("���׽���ʱ�䲻��Ϊ�գ�");
			document.getElementById("endTime").focus();
			return false;
		}
		if (document.getElementById("startTime").value.indexOf("��") != "-1") {
			alert("ʱ�䲻����������ð�ţ�");
			return false;
		}
		if (document.getElementById("endTime").value.indexOf("��") != "-1") {
			alert("ʱ�䲻����������ð�ţ�");
			return false;
		}
		if (!isTime(document.getElementById("startTime").value)) {
			alert("���׿�ʼʱ���ʽ����ȷ��");
			document.getElementById("startTime").focus();
			return false;
		}
		if (!isTime(document.getElementById("endTime").value)) {
			alert("���׽���ʱ���ʽ����ȷ��");
			document.getElementById("endTime").focus();
			return false;
		}
		if (document.getElementById("name").value == "") {
			alert("���׽����Ʋ���Ϊ�գ�");
			document.getElementById("status").focus();
			return false;
		}
		/*if (document.getElementById("tradeTimeType").value == "0") {//ͬһ�콻��
			var startTimes = document.getElementById("startTime").value.split(":");
			var dateST = new Date(0, 0, 0, startTimes[0], startTimes[1], startTimes[2]);
			var hourST = dateST.getHours();
			var minuteST = dateST.getMinutes();
			var secondST = dateST.getSeconds();
			var relDateST = parseInt(hourST) * 3600 + parseInt(minuteST) * 60 + parseInt(secondST);
			var endTimes = document.getElementById("endTime").value.split(":");
			var dateET = new Date(0, 0, 0, endTimes[0], endTimes[1], endTimes[2]);
			var hourET = dateET.getHours();
			var minuteET = dateET.getMinutes();
			var secondET = dateET.getSeconds();
			var relDateET = parseInt(hourET) * 3600 + parseInt(minuteET) * 60 + parseInt(secondET);
			if (relDateST > relDateET || relDateST == relDateET) {
				alert("���׿�ʼʱ��Ӧ���ڽ��׽���ʱ�䣡");
				document.getElementById("startTime").focus();
				return false;
			}
		}*/
		document.forms(0).submit();
		document.forms(0).updateBaseInfo.disabled = false;
	}
}

function suffixNamePress() {
	if (event.keyCode<=47 || event.keyCode>58) {
		event.returnValue=false;
	} else {
		event.returnValue=true;
	}
}
</script>

<%@ include file="/public/footInc.jsp"%>