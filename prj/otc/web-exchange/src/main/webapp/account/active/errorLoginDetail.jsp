<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@ page import="gnnt.MEBS.config.constant.ActionConstant"%>
<c:set var="GNNT_" value="<%=ActionConstant.GNNT_%>"/>

<base target="_self">
<head>
	<title>µÇÂ¼ÏêÇé</title>
</head>
<body style="overflow-y: hidden">
 <div class="st_title">
			&nbsp;&nbsp;<img src="<%=skinPath%>/cssimg/xr.gif" align="absmiddle" />
			&nbsp;µÇÂ¼ÏêÇé
	</div>
	<div style="position:absolute;top:40;z-index:1;overflow:auto;height:380px;">
		<table border="0" width="100%" align="center">
			<tr>
				<td align="center">
					<form name="frm"
						action="${basePath}/account/active/activeOne.action?traderid=${traderid}"
						method="post" targetType="hidden">
						<table border="0" cellspacing="0" cellpadding="0"
							width="98%">
								<tr height="25" align=center>
									<td width="30%" class="panel_tHead_MB" align=center>
										½»Ò×ÕËºÅ
									</td>
									<td width="40%" class="panel_tHead_MB" align=center>
										µÇÂ¼Ê±¼ä
									</td>
									<td width="20%" class="panel_tHead_MB" align=center>
										µÇÂ¼IP
									</td>
								</tr>
								<c:forEach items="${list}" var="errorLogin">
									<tr align=center>
										<td class="tab_td_border" align="center">
											<font size="3px">${errorLogin.traderid }</font>
										</td>
										<td class="tab_td_border" align="center">
											<font size="3px"><fmt:formatDate value="${errorLogin.loginDate}" pattern="yyyy-MM-dd HH:mm:ss"/></font>&nbsp;
										</td>
										<td class="tab_td_border" align="center">
											<font size="3px">${errorLogin.ip }</font>&nbsp;
										</td>
									</tr>
								</c:forEach>
						</table>
					</form>
				</td>
			</tr>
		</table>
	</div>
	<div class="tab_pad">
		<table border="0" cellspacing="0" cellpadding="0" width="100%"
			align="center">
			<tr height="35">
				<td align="center" id="tdId">
					<td align="center">
					<button class="btn_sec" onClick="window.close()">
						¹Ø±Õ
					</button>
				</td>
			</tr>
		</table>
	</div>
</body>
<%@ include file="/public/footInc.jsp"%>