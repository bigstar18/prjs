<%@ page contentType="text/html;charset=GBK"%>
<html>
	<head>
		<title>��ӹ���</title>
	</head>

<base target="_self">
	<body>
		<form name="frm" action="" method="post" target="frame">
		  <fieldset width="50%" height="60%">
				<legend>������Ϣ</legend>
					<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center">
						<tr height="20">
							<td align="right">��Ϣ��:							</td>
							<td>
							  <input type="text" id="name" name="test.name" value="">							</td>
							<td align="right">&nbsp;</td>
							<td>&nbsp;</td>
						</tr>
						<tr height="20">
							<td align="right">
							��������:							</td>
							<td><input type="text" id="test.name" name="test.name2" value=""></td>
							<td align="right">
							��Ч����:							</td>
							<td><select name="select2">
							  <option value="1">��</option>
							  <option>Ů</option>
                                                        </select></td>
						</tr>
							<tr height="20">
							<td align="right">
							��������:							</td>
							<td><input type="text" id="test.name3" name="test.name4" value=""></td>
							<td align="right">
							����:							</td>
							<td><input type="text" id="test.name4" name="test.name5" value=""></td>
						</tr>
							
							<tr height="20">
							<td align="right">
							������:							</td>
							<td>
							  <input type="text" id="address" name="test.address" value="">							</td>
							<td align="right">
							����Ա:							</td>
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
				  <input type="button" name="Submit3" value="���">
				  </label>
				</div></td><td><div align="center">
				  <label>
				    <input type="button" name="Submit4" value="����" onClick="window.close()">
			      </label>
				</div></td>
			</tr>
		  </table>
		</form>
	</body>
</html>