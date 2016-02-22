<%@ page contentType="text/html;charset=GBK"%>
<html>
	<head>
		<title>添加客户</title>
	</head>

<base target="_self">
	<body>
		<form name="frm" action="" method="post" target="frame">
			<fieldset width="50%" height="60%">
				<legend>基本信息</legend>
					<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center">
						<tr height="20">
							<td align="right">登录账号:							</td>
							<td>
							  <input type="text" id="name" name="test.name" value="">							</td>
							<td align="right">
							用户名称：							</td>
							<td>
							  <input type="text" id="age" name="test.age" value="">							</td>
						</tr>
						<tr height="20">
							<td align="right">
							英文名称:							</td>
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
							证件类型:							</td>
							<td><label>
							  <select name="select">
							    <option value="1">身份证</option>
						            </select>
							</label></td>
							<td align="right">
							电子邮箱:							</td>
							<td>
							  <input type="text" id="address" name="test.address" value="">							</td>
						</tr>
							<tr height="20">
							  <td align="right">所属经纪人:</td>
							  <td><input type="text" id="test.address2" name="test.address3" value=""></td>
							  <td align="right">所属机构:</td>
							  <td><input type="text" id="test.address3" name="test.address4" value=""></td>
					  </tr>
							<tr height="20">
							<td align="right">
							传真机号:							</td>
							<td>
							  <input type="text" id="address" name="test.address" value="">							</td>
							<td align="right">
							邮编:							</td>
							<td>
							  <input type="text" id="address" name="test.address" value="">							</td>
						</tr>
						<tr height="20">
							<td align="right">
							通讯地址:							</td>
							<td><input type="text" id="test.address" name="test.address2" value=""></td>
						</tr>
			</table> 
		  </fieldset><br />
		  <br/>
		  <table border="0" cellspacing="0" cellpadding="4" width="80%" align="center">
		  	<tr height="20">
				<td><div align="center">
				  <label>
				  <input type="button" name="Submit3" value="添加">
				  </label>
				</div></td><td><div align="center">
				  <label>
				    <input type="button" name="Submit4" value="返回">
			      </label>
				</div></td>
			</tr>
		  </table>
		</form>
	</body>
</html>