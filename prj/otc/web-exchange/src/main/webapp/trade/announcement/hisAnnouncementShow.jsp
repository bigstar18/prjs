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
								align="center" class="st_bor">
								<tr height="20">
									<td align="right">
										操作员:
									</td>
									<td>
										<input type="text" class="input_text" id="author" name="obj.author"
											value="${obj.author}" readonly="readonly">
									</td>
								</tr>
								<tr>
									<td align="right">
										主题：
									</td>
									<td>
										<textarea rows="4" cols="37" name="notice.title" id="title" style="width: 160" readonly="readonly">${obj.title}</textarea>
									</td>
								</tr>
								<tr height="20">
									<td align="right">
										发布时间:
									</td>
									<td>
										<input type="text" class="input_text" id="sendTime"
											name="obj.sendTime" value="${obj.sendTime}" readonly="readonly">
									</td>
								</tr>
								<tr>
									<td align="right">
										公告内容：
									</td>
									<td>
										<textarea rows="11" cols="50" id="content"
											name="content" value="${obj.content}" readonly="readonly">${obj.content}</textarea>
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
<script type="text/javascript">

</script>

<%@ include file="/public/footInc.jsp"%>

