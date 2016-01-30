<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>详情</title>
		<meta http-equiv="Pragma" content="no-cache">
	</head>
	<body>
		<div>
	<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
		<thead>
		<tr align="center">
			<td class="table_tHead_LB"  width="5%">&nbsp;</td>
			<td class="table_tHead_MB" width="30%" align="center">交易员代码</td>
			<td class="table_tHead_MB" width="30%" align="center">登录时间</td>
			<td  class="table_tHead_MB_last" width="30%" align="center">登录IP</td>
			<td class="table_tHead_RB" width="5%">&nbsp;</td>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${pageInfo.result }" var="errorLog">
		<tr align="center">
			<td class="table_tBody_LB">&nbsp;</td>
			<td class="underLine" align="center">${errorLog.traderId }</td>
			<td class="underLine" align="center"><fmt:formatDate value="${errorLog.loginDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td class="underLine_last" align="center">${errorLog.ip }</td>
			<td class="table_tBody_RB">&nbsp;</td>
		</tr>
		</c:forEach>
		</tbody>
		<tFoot>
			<tr  align="center" height="100%">
				<td class="table_tBody_LB">&nbsp;</td>
				<td colspan="3">&nbsp;</td>
				<td class="table_tBody_RB">&nbsp;</td>
			</tr>
			<tr  align="center" height="22">
				<td class="table_tFoot_LB">&nbsp;</td>
				<td class="table_tFoot_MB" colspan="3"></td>
				<td class="table_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
	</table>
</div>
<div class="tab_pad">
				<table border="0" cellspacing="0" cellpadding="4" width="100%"
					align="center">
					<tr>
						<td align="center">
							<button class="btn_sec" onClick="window.close();">
								关闭
							</button>
						</td>
					</tr>
				</table>
			</div>
	</body>
</html>