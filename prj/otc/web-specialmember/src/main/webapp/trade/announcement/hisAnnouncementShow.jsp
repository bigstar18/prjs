<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>查看公告</title>
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
								&nbsp;&nbsp;查看公告
							</div>
							<table border="0" cellspacing="0" cellpadding="10" width="100%"
								align="center" class="st_bor" style="table-layout: fixed;word-break:break-all;">
								<tr>
									<td align="right" width="20%">
										操作员:
									</td>
									<td>
										${obj.author}
									</td>
								</tr>
								<tr>
									<td align="right">
										发布机构:
									</td>
									<td width="350">
										${obj.publishOrganization}
									</td>
								</tr>
								<tr>
									<td align="right">
										主题:
									</td>
									<td>
										<textarea rows="2" cols="50" id="title"
											name="obj.title" readonly="readonly">${obj.title}</textarea>
									</td>
								</tr>
								<tr>
									<td align="right">
										发布时间:
									</td>
									<td>
										${datefn:formatdate(obj.sendTime)}
									</td>
								</tr>
								<tr>
									<td align="right">
										失效时间:
									</td>
									<td>
										${datefn:formatdate(obj.expiryTime)}
									</td>
								</tr>
								<tr>
									<td align="right" valign="top">
										公告内容:
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
							关闭
						</button>
					</td>
				</tr>
			</table>
		</form>
	</body>

</html>

<%@ include file="/public/footInc.jsp"%>

