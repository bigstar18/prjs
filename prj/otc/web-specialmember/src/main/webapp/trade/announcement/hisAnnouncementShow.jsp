<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>�鿴����</title>
	</head>
	<body style="overflow-y: hidden">
		<form name="frm" action="" method="post" targetType="hidden">
			<div>
				<table border="0" width="500" align="center">
					<tr height="70"></tr>
					<tr>
						<td>
							<div class="st_title">
								<img src="<%=skinPath%>/cssimg/st_ico1.gif" width="43"
									height="40" align="absmiddle" />
								&nbsp;&nbsp;�鿴����
							</div>
							<table border="0" cellspacing="0" cellpadding="10" width="100%"
								align="center" class="st_bor" style="table-layout: fixed;word-break:break-all;">
								<tr>
									<td align="right" width="20%">
										����Ա:
									</td>
									<td>
										${obj.author}
									</td>
								</tr>
								<tr>
									<td align="right">
										��������:
									</td>
									<td width="350">
										${obj.publishOrganization}
									</td>
								</tr>
								<tr>
									<td align="right">
										����:
									</td>
									<td>
										<textarea rows="2" cols="50" id="title"
											name="obj.title" readonly="readonly">${obj.title}</textarea>
									</td>
								</tr>
								<tr>
									<td align="right">
										����ʱ��:
									</td>
									<td>
										${datefn:formatdate(obj.sendTime)}
									</td>
								</tr>
								<tr>
									<td align="right">
										ʧЧʱ��:
									</td>
									<td>
										${datefn:formatdate(obj.expiryTime)}
									</td>
								</tr>
								<tr>
									<td align="right" valign="top">
										��������:
									</td>
									<td>
										<textarea rows="11" cols="50" id="content"
											name="content"  readonly="readonly">${obj.content}</textarea>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
			<table border="0" cellspacing="0" cellpadding="0" width="100%"
				align="center" style="padding-top: 30px;">
				<tr>
					<td align="center">
						<button class="btn_sec" onclick="window.close()">
							�ر�
						</button>
					</td>
				</tr>
			</table>
		</form>
	</body>

</html>

<%@ include file="/public/footInc.jsp"%>

