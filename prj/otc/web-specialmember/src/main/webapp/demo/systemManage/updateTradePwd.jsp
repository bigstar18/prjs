<%@ page contentType="text/html;charset=GBK"%>
<html>
	<head>
		<title>�޸Ľ�������</title>
	</head>

<base target="_self">
	<body>
		<form name="frm" action="/ecsideTest/ecside/add.action" method="post" target="frame">
			<fieldset width="50%" height="60%">
				<legend> 
					��¼�����޸�
				</legend>
					<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center">
						<tr height="20">
							<td align="right">�ɵ�¼����:							</td>
							<td>
							  <input type="text" id="name" name="test.name" value="">							</td>
						</tr>
						<tr height="20">
							<td align="right">
							�µ�¼����:							</td>
							<td><input type="text" id="test.name" name="test.name2" value=""></td>
						</tr>
							<tr height="20">
							  <td align="right">ȷ�����룺</td>
							  <td><input type="text" id="test.name2" name="test.name22" value=""></td>
					  </tr>
						</table> 
						<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center">
						<tr>
							<td  align="center">
								<input type="button" value="�޸�">
							</td>
						</tr>
					</table> 
			</fieldset>
		</form>
	</body>
</html>