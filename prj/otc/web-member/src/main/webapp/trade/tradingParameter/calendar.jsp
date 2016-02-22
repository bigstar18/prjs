<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; char=GBK">
		<title>日</title>
	</head>
	<body>
	<div class="body_bgcolor">
			<form name="sm" method="post" action="">
				<table border="0" width="168" height="20" align="center">
					<tr>
						<td width=28%>
							<input type=text name="year" value="${barginCalendarVO.year }"
								size=4 maxlength=4 onkeyup="this.value=this.value.replace(/\D/g,'')">
						</td>
						<td>
							年
						</td>
						<td width=30%>
							<select name="month" size="1" _disibledevent=>
								<option value="0">
									1月
								</option>
								<option value="1">
									2月
								</option>
								<option value="2">
									3月
								</option>
								<option value="3">
									4月
								</option>
								<option value="4">
									5月
								</option>
								<option value="5">
									6月
								</option>
								<option value="6">
									7月
								</option>
								<option value="7">
									8月
								</option>
								<option value="8">
									9月
								</option>
								<option value="9">
									10月
								</option>
								<option value="10">
									11月
								</option>
								<option value="11">
									12月
								</option>
							</select>
						</td>

						<td width=28%>
							<input type="button" value="查询" onclick="notNullFun();">
						</td>
						<td width=28%>
							<input type="button" value="重置" onclick="submitFun();">
						</td>
					</tr>
				</table>

				<table border="1" cellpadding="0"
			cellspacing="0"  width="500" height="350" align=center>
					<tr>
						<td width="14%" height="50" align="center">
							<font size='2' color='black'><b>周日</b> </font>
						</td>
						<td width="14%" height="50" align="center">
							<font size='2' color='black'><b>周一</b> </font>
						</td>
						<td width="14%" height="50" align="center">
							<font size='2' color='black'><b>周二</b> </font>
						</td>
						<td width="14%" height="50" align="center">
							<font size='2' color='black'><b>周三</b> </font>
						</td>
						<td width="14%" height="50" align="center">
							<font size='2' color='black'><b>周四</b> </font>
						</td>
						<td width="14%" height="50" align="center">
							<font size='2' color='black'><b>周五</b> </font>
						</td>
						<td width="14%" height="50" align="center">
							<font size='2' color='black'><b>周六</b> </font>
						</td>
					</tr>

					<c:forEach begin="0" end="5" varStatus="j">
						<tr>
							<c:forEach begin="${j.index*7 }" end="${(j.index+1)*7-1}"
								varStatus="i">
								<td width="14%" height="50" valign="middle" align="center">
									<c:set var="day" value="${days[i.index]}"></c:set>
									<c:if test="${day ne ''}">
										<c:set value="${bcs[day]}" var="bc" />
										<c:if test="${bc.isToday}">
											<div style="background-color: yellow">
										</c:if>
											<c:if test="${bc.status == 2}">
												<c:out value="${day }"></c:out>
												<br>
												<font size='2' color='green'>交易日</b> </font>
											</c:if>
											<c:if test="${bc.status == 1}">
												<c:out value="${day }"></c:out>
												<br>
												<font size='2' color='green'>交易日</b> </font>
											</c:if>
										<c:if test="${bc.status == -2}">
											<c:out value="${day }"></c:out>
											<br>
											<font size='2' color='red'>非交易日</b> </font>
										</c:if>
										<c:if test="${bc.status == -1}">
											<c:out value="${day }"></c:out>
											<br>
											<font size='2' color='red'>非交易日</b> </font>
										</c:if>
										<c:if test="${bc.isToday}">
											</div>
										</c:if>
									</c:if>
									<c:if test="${day eq ''}">
										&nbsp;
									</c:if>
								</td>
							</c:forEach>
						</tr>
					</c:forEach>
				</table>
			</form>
			</div>
<script Language="JavaScript">
<!-- 
document.sm.month.options.selectedIndex=${barginCalendarVO.month};
function submitFun(){
	var thisDate = new Date();
	sm.year.value = ${returnYear };
	sm.month.value = ${returnMonth};
	sm.action = "${basePath}/tradeManage/marketTradingParameter/calendar.action";
	sm.submit();
}
function notNullFun(){
	if(sm.year.value == "") {
		alert("年不能为空");
		sm.year.focus();
		return false;
	}
	sm.action = "${basePath}/tradeManage/marketTradingParameter/calendar.action";
	sm.submit();
}
//--> 
</script>
	</body>
</html>
