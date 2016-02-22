 <%@ page contentType="text/html;charset=GBK"%>
 <%@ include file="/common/public/common.jsp"%>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<META HTTP-EQUIV="pragma" CONTENT="no-cache">
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
	<META HTTP-EQUIV="expires" CONTENT="0">
<html>
	<head>
		<title></title>
		<LINK REL="stylesheet" type="text/css" href="css/styles/maintest.css" />
	</head>
	<body leftmargin="0" topmargin="0">
			<table border="1" height="300" width="1000" align="center">
				<tr height="100"></tr>
				<tr>
					<td>
						<fieldset>
							<legend class="common">
								<b>贵金属账户 </b>

							</legend>
							<span id="baseinfo9">
								<table width="50%">
									<!-- 基本信息 -->
									<tr class="common">
										<td colspan="4">
											<fieldset>
												<legend>
													<table width="100%">
														<col width="80"></col>
														<col></col>
														<col width="8"></col>
														<tr>
															<td>
																<b>实时提示</b>
															</td>
															<td>
																<hr width="360" class="pickList" />
															</td>
														</tr>
													</table>
												</legend>
												<span id="baseinfo">
													<table width="100%">
														<tr height="35">
															<td align="right">
																建仓单
																<span style="color: red"> *</span>
															</td>
															<td align="left" colspan="3">
																<input id="title" name="info.title" type="checkbox">
															</td>
														</tr>
														<tr height="35">
															<td align="right">
																平仓单
																<span style="color: red"> *</span>
															</td>
															<td align="left" colspan="3">
																<input id="title" name="info.title" type="checkbox">
															</td>
														</tr>
														<tr height="35">
															<td align="right">
																指价单
																<span style="color: red"> *</span>
															</td>
															<td align="left" colspan="3">
																<input id="title" name="info.title" type="checkbox">
															</td>
														</tr>
														<tr height="35">
															<td align="right">
																出入金
																<span style="color: red"> *</span>
															</td>
															<td align="left" colspan="3">
																<input id="title" name="info.title" type="checkbox">
															</td>
														</tr>
														<tr height="35">
															<td align="right">
																登录
																<span style="color: red"> *</span>
															</td>
															<td align="left" colspan="3">
																<input id="title" name="info.title" type="checkbox">
															</td>
														</tr>
														<tr height="35">
															<td align="right">
																结算
																<span style="color: red"> *</span>
															</td>
															<td align="left" colspan="3">
																<input id="title" name="info.title" type="checkbox">
															</td>
														</tr>
														<tr height="35">
															<td align="right">
																限制客户个数
																<span style="color: red"> *</span>
															</td>
															<td align="left" colspan="3">
																<input id="title" name="info.title" type="text">
															</td>
														</tr>
													</table> </span>
											</fieldset>
										</td>
									</tr>



									<!-- 参数信息 -->


									<tr>
										<td colspan="4">
											<fieldset>
												<legend>
													<table width="100%">
														<col width="80"></col>
														<col></col>
														<col width="8"></col>
														<tr>
															<td>
																<b>点差设置</b>
															</td>
															<td>
																<hr width="360" class="pickList" />
															</td>
														</tr>
													</table>
												</legend>
												<span id="baseinfo2">
													<table
													width="100%" align="center" border='0'>
														<tr height="35">
															<td align="left" width="30%"> 
																中间价提示点差
															</td>
															<td align="left">
																<input id="title" name="info.title" type="checkbox" class="text">
															</td>
														</tr>
														<tr height="35">
															<td align="left"> 
																中间价限制点差
															</td>
															<td align="left">
																<input id="title" name="info.title" type="checkbox" class="text">
															</td>
														</tr>
														<tr height="35">
															<td align="left"> 
																开高低收提示点差
															</td>
															<td align="left">
																<input id="title" name="info.title" type="checkbox" class="text">
															</td>
														</tr>
														<tr height="35">
															<td align="left"> 
																开高低收限制点差
															</td>
															<td align="left">
																<input id="title" name="info.title" type="checkbox" class="text">
															</td>
														</tr>
														<tr height="35">
															<td align="left"> 
																下单提醒点差
															</td>
															<td align="left">
																<input id="title" name="info.title" type="checkbox" class="text">
															</td>
														</tr>
													</table>
												</span>
											</fieldset>
										</td>
									</tr>
								</table> </span>
						</fieldset>
					</td>
				</tr>
			</table>
			<br><br>
			<table align="center">
			<tr>
			<td>
					<input id='delete' type="reset" value="修改"/>
				</td>
				
			</tr>
		</table>
		</form>
	</body>
</html>
