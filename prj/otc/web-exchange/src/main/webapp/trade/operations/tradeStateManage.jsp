<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title></title>
		<script language="JavaScript" src="../../public/global.js"></script>
		<script language="JavaScript" src="../../public/open.js"></script>
		<%@ include file="/public/ecsideLoad.jsp"%>
		<script type="text/javascript">
			function window_load(result) {
				if (result == "3") { // 资金结算完成
					agencyForm.ok2.disabled = true;
					agencyForm.ok3.disabled = true;
					agencyForm.ok4.disabled = false;
					agencyForm.ok1.disabled = true;
				}
				if (result == "5") {// 交易中
					agencyForm.ok2.disabled = false;
					agencyForm.ok3.disabled = false;
					agencyForm.ok4.disabled = true;
					agencyForm.ok1.disabled = true;
				}
				if (result == "4") { // 暂停交易
					agencyForm.ok2.disabled = true;
					agencyForm.ok3.disabled = true;
					agencyForm.ok4.disabled = true;
					agencyForm.ok1.disabled = false;
				}
				if (result == "6") {// 节间休息
					agencyForm.ok2.disabled = false;
					agencyForm.ok3.disabled = false;
					agencyForm.ok4.disabled = true;
					agencyForm.ok1.disabled = true;
				}
				if (result == "7") { // 交易结束
					agencyForm.ok2.disabled = true;
					agencyForm.ok3.disabled = true;
					agencyForm.ok4.disabled = true;
					agencyForm.ok1.disabled = true;
				}
				if (result == "0" || result == "2") {// 初始化完成、结算中
					agencyForm.ok2.disabled = true;
					agencyForm.ok3.disabled = true;
					agencyForm.ok4.disabled = true;
					agencyForm.ok1.disabled = true;
				}
			
			}
			
			function balanceChk_onclick(id) {
				var name;
			
				if (id == '06') {
					name = "恢复交易";
				}
				if (id == '09') {
					name = "交易结束";
				}
				if (id == '05') {
					name = "暂停交易";
				}
				if (id == '08') {
					name = "开市准备";
				}
				if (id == '07') {
					name = "闭市操作";
				}
				if (id == "99") {
					name = "设定恢复时间"
				}
				if (affirm("您确定要" + name + "吗？")) {
					document.getElementById("tradeManageVo.status").value=id;
					agencyForm.submit();
					agencyForm.ok2.disabled = true;
					agencyForm.ok3.disabled = true;
					agencyForm.ok4.disabled = true;
					agencyForm.ok1.disabled = true;
				}
			}
			
			function writeTime() {
				var url="${basePath}/tradeManage/tradeStatusManage/forwardUpdateRecoverTime.action"+'?AUsessionId='+AUsessionId+'&d='+new Date();
				var result=window.showModalDialog(url, window, "dialogWidth=" + 600 + "px; dialogHeight=" + 450 + "px; status=yes;scroll=yes;help=no;");//ecsideDialog1(url, window, 600, 400);
				if(result>0){
					agencyForm.action="${basePath}/tradeManage/tradeStatusManage/forwardUpdate.action";
					agencyForm.submit();
				}
			}
		</script>
	</head>
	<body leftmargin="0" topmargin="0" onkeypress="keyEnter(event.keyCode);" <c:if test="${dateFlag}">onload="window_load(${obj.status })"</c:if>>
		<div id="main_body">
			<table width="550" border="0" cellspacing="0"
				cellpadding="0" align="center">
				<tr><td height="50">&nbsp;</td></tr>
				<tr><td height="5">&nbsp;</td></tr>
				<tr>
					<td><div class="div_cxtjmid"><img src="<%=skinPath%>/cssimg/13.gif" />&nbsp;&nbsp;交易状态管理</div>
						<div class="div_tj">
							<form name="agencyForm" method="post" action="${basePath}/tradeManage/tradeStatusManage/updateTradeStatus.action">
								<input type="hidden" name="tradeManageVo.recoverTime" value="${obj.recoverTime }">
								<input type="hidden" name="tradeManageVo.status" id="obj.status" value="${obj.status}">
								<input type="hidden" name="systemStatusLog" id="obj.status" value="${obj.status}">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style" width="95%" >
									<tr>
										<td>
											<div class="div2_top">
												<table width="65%" border="0" cellspacing="0"
													cellpadding="0" align="center">
													<c:if test="${dateFlag}">
													<tr><td height="15" colspan="2">&nbsp;</td></tr>
													<tr><td height="3" colspan="2"></td></tr>
													<tr>
														<td align="left" colspan="2" height="35">
															<span style="font-weight:bold;color:#f38243;">&nbsp;&nbsp;交易日：</span>&nbsp;<span style="font-family: 宋体;font-size: 14px;font-weight:bold;color:#7d0c01;"><fmt:formatDate value="${obj.tradeDate}" type="date"/></span>
														</td>
													</tr>
													<tr><td height="3" colspan="2"></td></tr>
													<tr>
														<td colspan="2" align="left" height="35">
															<span style="font-weight:bold;color:#f38243;">&nbsp;&nbsp;市场状态：</span>&nbsp;<span style="font-family: 宋体;font-size: 14px;font-weight:bold;color:#7d0c01;">${statusMap[obj.status]}</span>&nbsp;&nbsp;
															
														</td>
													</tr>
													<tr><td colspan="2" height="3"></td></tr>
													<c:if test="${obj.node!=null}">
														<tr style="background-color: #f7e3ce;">
														<td colspan="2" align="left" height="35">
															<span style="font-weight:bold;color:#f38243;">&nbsp;&nbsp;备注：</span>&nbsp;${obj.node }
														</td>
													</tr>
													<tr><td colspan="2" height="3"></td></tr>
													</c:if>
													<tr>
														<td align="left" width="35%" height="35">&nbsp;&nbsp;
															<font color=red>●</font>&nbsp;
															<button class="btn1_mouseout" name="ok4" id="update"
																onclick="javascript:balanceChk_onclick('08');">
																开市准备
															</button>
														</td>
														<td style="font-family: 宋体;font-size: 14px;font-weight:bold;color:#7d0c01;">
															(<c:forEach items="${tradeRunModeMap}" var="maps">
																<c:if test="${obj.runMode==maps.key}">${maps.value}</c:if>
															</c:forEach>)
							  							</td>
													</tr>
													<tr><td colspan="2" height="3"></td></tr>
													<tr>
														<td colspan="2" align="left" height="35">&nbsp;&nbsp;
															<font color=red>●</font>&nbsp;
															<c:if test="${obj.status==0}">
																<font color="blue">&nbsp;<span style="font-weight:bold;font-size:13px;">开始交易</span></font>
															</c:if>
															<c:if test="${obj.status!=0}">
																<font color="gray">&nbsp;<span style="font-weight:bold;font-size:13px;">开始交易</span></font>
															</c:if>
														</td>
													</tr>
													<tr><td colspan="2" height="3"></td></tr>
													<tr>
														<td colspan="2" align="left" height="35">&nbsp;&nbsp;
															<font color=red>●</font>&nbsp;
															<button class="btn1_mouseout" name="ok3" id="update"
																onclick="javascript:balanceChk_onclick('05');">
																暂停交易
															</button>
														</td>
													</tr>
													<tr><td colspan="2" height="3"></td></tr>
													<c:if test="${obj.status==4}">
													<tr>
														<td align="left" height="35" width="40%">&nbsp;&nbsp;
															恢复时间：&nbsp;<span class="req" id="t"></span>${obj.recoverTime }
														</td>
														<td>
															<button id="update11" class="btn1_mouseout" onclick="writeTime()">设定恢复时间</button>
														</td>
													</tr>
													<tr><td colspan="2" height="3"></td></tr>
													</c:if>
													<tr>
														<td colspan="2" align="left" height="35">&nbsp;&nbsp;
															<font color=red>●</font>&nbsp;
															<button class="btn1_mouseout" name="ok1" id="update"
																onclick="javascript:balanceChk_onclick('06');">
																恢复交易
															</button>
														</td>
													</tr>
													<tr><td colspan="2" height="3"></td></tr>
													<tr>
														<td align="left" width="35%" height="35">&nbsp;&nbsp;
															<font color=red>●</font>&nbsp;
															<button class="btn1_mouseout" name="ok2" id="update"
																onclick="javascript:balanceChk_onclick('09');">
																交易结束
															</button>
														</td>
														<td style="font-family: 宋体;font-size: 14px;font-weight:bold;color:#7d0c01;">(默认自动)</td>
													</tr>
													<tr><td colspan="2" height="3"></td></tr>
													<tr>
														<td colspan="2" align="left">
														<div id="status1" align="center" class="common1"></div>
														</td>
													</tr>
													<tr><td colspan="2" height="15">&nbsp;</td></tr>
													</c:if>
													<c:if test="${!dateFlag}">
													<tr>
														<td align="center" colspan="2" height="135">
															<span style="font-weight:bold;color:green;font-size: 20">&nbsp;&nbsp;非交易日</span>&nbsp;
														</td>
													</tr>
													</c:if>
												</table>
											</div>
										</td>
									</tr>
								</table>
							</form>
						</div>
					</td>
				</tr>
			</table>
			</div>
	</body>
</html>

<%@ include file="/public/footInc.jsp"%>
