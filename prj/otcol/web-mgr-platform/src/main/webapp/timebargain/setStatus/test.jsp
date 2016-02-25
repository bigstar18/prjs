<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=utf-8"%>
<html>
	<head>
		<title></title>
		
	</head>

	<body leftmargin="0" topmargin="0">
	<script type="text/javascript">
			//得到数据库当前状态
			alert("----");
		</script>
		<table border="0" height="500" align="center">
			<tr>
				<td>
					<form name="ordersForm" method="post"
						action="/mgr/timebargain/balance/balance.do?funcflg=balanceChkMarketHold">
						<fieldset class="pickList">
							<legend class="common">
								<b>选择状态</b>
							</legend>
							<table border="0" width="500" height="281">
								<tr height="20">
								</tr>
								<tr height="20">
									<td align="center">
										当前时间：
										<span class="req">
											<%=new Date().toLocaleString()%>
										</span>
									</td>
								</tr>
								<tr height="20">
								</tr>
								<tr height="20">
									<td align="center">
										<input type="radio">
										<span>启动</span>
									</td>
									<td>
										<input type="radio">
										<span>禁止</span>
									</td>
								</tr>
								<tr>
								</tr>
							</table>
						</fieldset>
					</form>
				</td>
			</tr>
		</table>
	</body>
</html>
