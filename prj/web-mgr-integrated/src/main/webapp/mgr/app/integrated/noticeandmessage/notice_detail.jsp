<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>公告详情</title>
		<meta http-equiv="Pragma" content="no-cache">
	</head>

	<body style="overflow-y: hidden">
			<div class="div_cx" >
				<%@include file="notice_show.jsp" %>
			</div>
			<div >
				<table border="0" cellspacing="0" cellpadding="4" width="100%"
					align="center">
					<tr>
						<td align="center">
							<button class="btn_sec" onClick=window.close();>
								关闭
							</button>
						</td>
					</tr>
				</table>
			</div>
	</body>
</html>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>