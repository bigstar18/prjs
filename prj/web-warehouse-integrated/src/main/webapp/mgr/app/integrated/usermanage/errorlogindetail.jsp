<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>详情</title>
		<meta http-equiv="Pragma" content="no-cache">
	</head>
	<body>
		<div style="position: absolute;padding-left:14px; top: 2; z-index: 1; overflow: auto; height: 360px;overflow-x: hidden;">
			<table id="tb" border="0" cellspacing="0" cellpadding="1" width="100%"
				style="word-break: break-all; table-layout: fixed; border-right: 1px solid #d9d9d9; border-top: 1px solid #d9d9d9; border-bottom: 1px solid #d9d9d9;">
				<thead>
				<tr height="25" align="center">
					<td class="panel_tHead_MB" width="20%" align="center">错误编号</td>
					<td class="panel_tHead_MB" width="20%" align="center">用户代码</td>
					<td class="panel_tHead_MB" width="30%" align="center">登录时间</td>
					<td  class="panel_tHead_MB" width="30%" align="center">登录IP</td>
				</tr>
				</thead>
				<tbody>
				<c:forEach items="${pageInfo.result }" var="errorLog">
				<tr height="25" align="center">
					<td class="tab_td_border" align="center" style="font-size: 12px;">${errorLog.errorLoginID }</td>
					<td class="tab_td_border" align="center" style="font-size: 12px;">${errorLog.userID }</td>
					<td class="tab_td_border" align="center" style="font-size: 12px;"><fmt:formatDate value="${errorLog.loginDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td class="tab_td_border" align="center" style="font-size: 12px;">${errorLog.ip}</td>
				</tr>
				</c:forEach>
				</tbody>
				<!-- 
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
				 -->
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