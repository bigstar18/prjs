<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>


<html>
	<head>
		<title>�޸Ľ���ʱ��</title>

		<script language="JavaScript" src="<%=basePath%>/public/Date.js"></script>	
<script type="text/javascript">	
function window_onload() {
	//highlightFormElements();
	document.getElementById("status").value = "${obj.status}";
}

//save
function updateCommodity() {
	if (confirm("��ȷ��Ҫ�ύ��")) {
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

		if (document.getElementById("sectionId").value == "") {
			alert("���׽�ID����Ϊ�գ�");
			document.getElementById("sectionId").focus();
			return false;
		}
		if (document.getElementById("status").value == "") {
			alert("״̬����Ϊ�գ�");
			document.getElementById("status").focus();
			return false;
		}
		if (document.getElementById("name").value == "") {
			alert("���׽����Ʋ���Ϊ�գ�");
			document.getElementById("status").focus();
			return false;
		}

		if (document.getElementById("tradeTimeType").value == "0") {//ͬһ�콻��
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
		}
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

	</head>
	<body onload="return window_onload()" class="st_body">
		<form name="frm" action="${basePath}/tradeManage/tradingParameter/updateTradeTime.action" method="post" targetType="hidden">
			<input type="hidden" name="tradeTimeType" id="tradeTimeType">		
			<fieldset width="100%" height="100%">
				<div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" width="43" height="40" align="absmiddle" />&nbsp;&nbsp;���׽��޸�</div>
				<%@ include file="tradeTimeTable.jsp"%>
				<table border="0" cellspacing="0" cellpadding="4" width="100%"
					align="center">
					<tr>
						<td align="center">
							<div class="st_anbor"><a href="#" id="updateBaseInfo" onclick="updateCommodity()" class="st_an">�ύ</a></div>
						</td>
						<td align="center">
							<div class="st_anbor"><a href="#" class="st_an" onclick="window.close()">����</a></div>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</body>
</html>

<%@ include file="/public/footInc.jsp"%>