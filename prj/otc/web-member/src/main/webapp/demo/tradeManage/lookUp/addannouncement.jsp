<%@ page contentType="text/html;charset=GBK"%>
<html>
	<head>
		<title>添加公告</title>
	</head>

<base target="_self">
	<body>
		<form name="frm" action="" method="post" target="frame">
		  <fieldset width="50%" height="60%">
				<legend>基本信息</legend>
					<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center">
						<tr height="20">
							<td align="right">消息号:							</td>
							<td>
							  <input type="text" id="name" name="test.name" value="">							</td>
							<td align="right">&nbsp;</td>
							<td>&nbsp;</td>
						</tr>
						<tr height="20">
							<td align="right">
							发布日期:							</td>
							<td><input type="text" id="test.name" name="test.name2" value=""></td>
							<td align="right">
							有效日期:							</td>
							<td><select name="select2">
							  <option value="1">男</option>
							  <option>女</option>
                                                        </select></td>
						</tr>
							<tr height="20">
							<td align="right">
							公布内容:							</td>
							<td><input type="text" id="test.name3" name="test.name4" value=""></td>
							<td align="right">
							主题:							</td>
							<td><input type="text" id="test.name4" name="test.name5" value=""></td>
						</tr>
							
							<tr height="20">
							<td align="right">
							发布者:							</td>
							<td>
							  <input type="text" id="address" name="test.address" value="">							</td>
							<td align="right">
							操作员:							</td>
							<td>
							  <input type="text" id="address" name="test.address" value="">							</td>
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
				    <input type="button" name="Submit4" value="返回" onClick="window.close()">
			      </label>
				</div></td>
			</tr>
		  </table>
		</form>
	</body>
</html>