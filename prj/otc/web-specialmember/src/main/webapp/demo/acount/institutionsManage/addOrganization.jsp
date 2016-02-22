<%@ page contentType="text/html;charset=GBK"%>
<html>
	<head>
		<title>机构(新建)</title>
	</head>

<base target="_self">
	<body>
		<form name="frm" action="/ecsideTest/ecside/add.action" method="post" target="frame">
			<fieldset width="50%" height="60%">
				<legend> 
					机构(新建)
				</legend>
					<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center">
						<tr height="20">
							<td align="right">机构名称:							</td>
							<td><label>
							  <input type="text">
							</label></td>
						</tr>
						<tr height="20">
							<td align="right">
							上级机构:							</td>
							<td><label>
							  <select name="select2">
							    <option>请选择</option>
						      </select>
							</label></td>
						</tr>
							
			</table> 
						<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center">
						<tr>
							<td  align="center">
								<input type="button" value="修改">
							</td>
							<td align="center">
								<input type="button" value="返回" onClick="window.close()">
							</td>
						</tr>
					</table> 
			</fieldset>
		</form>
	</body>
</html>