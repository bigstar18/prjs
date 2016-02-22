<%@ page contentType="text/html;charset=GBK"%>
<html>
	<head>
		<title>修改交易密码</title>
	</head>

<base target="_self">
	<body>
		<form name="frm" action="/ecsideTest/ecside/add.action" method="post" target="frame">
			<fieldset width="50%" height="60%">
				<legend> 
					登录密码修改
				</legend>
					<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center">
						<tr height="20">
							<td align="right">旧登录密码:							</td>
							<td>
							  <input type="text" id="name" name="test.name" value="">							</td>
						</tr>
						<tr height="20">
							<td align="right">
							新登录密码:							</td>
							<td><input type="text" id="test.name" name="test.name2" value=""></td>
						</tr>
							<tr height="20">
							  <td align="right">确认密码：</td>
							  <td><input type="text" id="test.name2" name="test.name22" value=""></td>
					  </tr>
						</table> 
						<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center">
						<tr>
							<td  align="center">
								<input type="button" value="修改">
							</td>
						</tr>
					</table> 
			</fieldset>
		</form>
	</body>
</html>