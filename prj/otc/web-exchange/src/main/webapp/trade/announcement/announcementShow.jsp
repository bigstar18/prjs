<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>查看公告</title>
	</head>
	<body style="overflow-y: hidden">
		<form name="frm" action="" method="post" targetType="hidden">
			<div style="overflow:auto;height:500px;">
				<table border="0" width="90%" align="center">
					<tr height="30"></tr>
					<tr>
						<td>
							<div class="st_title">
								<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />
								&nbsp;&nbsp;查看公告
							</div>
							<table border="0" cellspacing="0" cellpadding="10" width="100%"
								align="center" class="st_bor" style="table-layout: fixed;word-break:break-all;">
								<tr>
									<td align="right" width="20%">
										操作员:
									</td>
									<td>
										<input type="text" class="input_text" id="author" name="obj.author" style="width: 160"
											value="${obj.author}" readonly="readonly">
									</td>
								</tr>
								<tr>
									<td align="right">
										发布机构:
									</td>
									<td>
										<input type="text" class="input_text" id="authorOrganization" name="obj.authorOrganization" style="width: 160"
											value="${obj.authorOrganization}">
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
										<input type="text" class="input_text" id="sendTime" style="width: 160"
											name="obj.sendTime" value="${datefn:formatdate(obj.sendTime)}" readonly="readonly">
									</td>
								</tr>
								<tr>
									<td align="right">
										失效时间:
									</td>
									<td>
										<input type="text" class="input_text" id="expiryTime" style="width: 160"
											name="obj.expiryTime" value="${datefn:formatdate(obj.expiryTime)}" readonly="readonly">
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
			<div class="tab_pad">
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
			</div>
		</form>
	</body>

</html>

<%@ include file="/public/footInc.jsp"%>

