<%@ page contentType="text/html;charset=GBK"%>
<html>
	<head>
		<title>修改后台管理员</title>
	</head>

<base target="_self">
	<body>
		<form name="frm" action="" method="post" target="frame">
		  <fieldset width="50%" height="60%">
				<legend>基本信息</legend>
					<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center">
						<tr height="20">
							<td align="right">
							登录账号:							</td>
							<td>
							  <input type="text" id="name" name="test.name" value="">							</td>
							<td align="right">&nbsp;</td>
							<td>&nbsp;</td>
						</tr>
						<tr height="20">
							<td align="right">
							操作员姓名:							</td>
							<td><input type="text" id="test.name" name="test.name2" value=""></td>
							<td align="right">
							性别:							</td>
							<td><select name="select2">
							  <option value="1">男</option>
							  <option>女</option>
                                                        </select></td>
						</tr>
							<tr height="20">
							<td align="right">
							国籍:							</td>
							<td><input type="text" id="test.name3" name="test.name4" value=""></td>
							<td align="right">
							机构:							</td>
							<td><input type="text" id="test.name4" name="test.name5" value=""></td>
						</tr>
							
							<tr height="20">
							<td align="right">
							登录密码:							</td>
							<td>
							  <input type="text" id="address" name="test.address" value="">							</td>
							<td align="right">
							确认密码:							</td>
							<td>
							  <input type="text" id="address" name="test.address" value="">							</td>
						</tr>
						
						<tr height="20">
							<td align="right">
							身份证号:							</td>
							<td><input type="text" id="test.address" name="test.address2" value=""></td>
							<td align="right">
							电子邮件:							</td>
							<td><input type="text" id="test.address" name="test.address2" value=""></td>
						</tr>
						<tr height="20">
							<td align="right">
							联系电话:							</td>
							<td><input type="text" id="test.address" name="test.address2" value=""></td>
							<td align="right">手机:							</td>
							<td><input type="text" id="test.address" name="test.address2" value=""></td>
						</tr>
						<tr height="20">
							<td align="right">
							传真机号:							</td>
							<td><input type="text" id="test.address" name="test.address2" value=""></td>
							<td align="right">
							邮编:							</td>
							<td><input type="text" id="test.address" name="test.address2" value=""></td>
						</tr>
						<tr height="20">
							<td align="right">
							通讯地址:							</td>
							<td><input type="text" id="test.address" name="test.address2" value=""></td>
							<td align="right">&nbsp;</td>
							<td>&nbsp;</td>
						</tr>
			</table> 
		  </fieldset><br />
		  <br/>
		  <fieldset width="50%" height="60%">
				<legend>权限信息</legend>
					<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center">
						<tr height="20">
							<td colspan="2" align="right"><div align="center">机构权限树</div></td>
							<td width="49%" colspan="2" align="right"><div align="center">操作权限树</div></td>
						</tr>
			</table> 
		  </fieldset>
		  <br/>
		  <table border="0" cellspacing="0" cellpadding="4" width="80%" align="center">
		  	<tr height="20">
				<td><div align="center">
				  <label>
				  <input type="button" name="Submit3" value="添加">
				  </label>
				</div></td><td><div align="center">
				  <label>
				    <input type="button" name="Submit4" value="返回" onClick="window.close()">
			      </label>
				</div></td>
			</tr>
		  </table>
		</form>
	</body>
</html>