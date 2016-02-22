<%@ page contentType="text/html;charset=GBK" import="java.util.*"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>交易节修改</title>
		
		<script type='text/javascript' src='<%=basePath%>/dwr/engine.js'>
		</script>
		<script type='text/javascript' src='<%=basePath%>/dwr/util.js'>
		</script>
		<script type="text/javascript"
			src='<%=basePath%>/dwr/interface/checkAction.js' />
		</script>
		<script language="JavaScript" src="<%=basePath%>/public/Date.js"></script>	
	</head>
	<body onload="return window_onload()">
		<form name="frm" action="${basePath}/tradeManage/tradingParameter/add.action" method="post" targetType="hidden">
			<input type="hidden" name="tradeTimeType" id="tradeTimeType" value="${tradeTimeType}">
			<input type="hidden" name="obj.sectionId" id="sectionId" value="${tradeTimeSize+1}" />
			<input type="hidden" name="obj.name" id="name" value="交易节${tradeTimeSize+1}" />
			<table border="0" width="90%" align="center" >
				<tr height="20"></tr>
				<tr>
					<td>
					<div class="st_title">&nbsp;&nbsp;<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;&nbsp;交易节添加</div>
				<table cellSpacing="0" cellPadding="5" width="100%" border="0"
		align="center" class="st_bor">
		<tr>
			<td align="right">
				交易开始时间：
			</td>
			<td>
				<input class="from" type="text" name="obj.startTime" value="${obj.startTime}" id="startTime" maxlength="8" class="text"
					onkeypress="return suffixNamePress()" />
				<strong class="check_input">&nbsp;* HH:MM:SS(24小时制)</strong>
			</td>
		</tr>
		<tr>
			<td align="right">
				交易结束时间：
			</td>
			<td>
				<input class="from" type="text" name="obj.endTime" value="${obj.endTime}" id="endTime" maxlength="8" class="text"
					onkeypress="return suffixNamePress()" />
				<strong class="check_input">&nbsp;* HH:MM:SS(24小时制)</strong>
			</td>
		</tr>
		<tr>
			<td align="right">
				交易节状态：
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
							<button id="addBaseInfo" onclick="addCommodity()" class="btn_sec">添加</button>
						</td>
						<td align="center">
							<button class="btn_sec" onclick="window.close()">关闭</button>
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
}

//save
function addCommodity() {
	if (affirm("您确定要提交吗？")) {
		if (document.getElementById("startTime").value == "") {
			alert("交易开始时间不能为空！");
			document.getElementById("startTime").focus();
			return false;
		}
		if (document.getElementById("endTime").value == "") {
			alert("交易结束时间不能为空！");
			document.getElementById("endTime").focus();
			return false;
		}
		if (document.getElementById("startTime").value.indexOf("：") != "-1") {
			alert("时间不能输入中文冒号！");
			return false;
		}
		if (document.getElementById("endTime").value.indexOf("：") != "-1") {
			alert("时间不能输入中文冒号！");
			return false;
		}

		if (!isTime(document.getElementById("startTime").value)) {
			alert("交易开始时间格式不正确！");
			document.getElementById("startTime").focus();
			return false;
		}
		if (!isTime(document.getElementById("endTime").value)) {
			alert("交易结束时间格式不正确！");
			document.getElementById("endTime").focus();
			return false;
		}

		/*if (document.getElementById("tradeTimeType").value == "0") {//同一天交易
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
				alert("交易开始时间应早于交易结束时间！");
				document.getElementById("startTime").focus();
				return false;
			}
		}*/
		document.forms(0).submit();
		document.forms(0).addBaseInfo.disabled = false;
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