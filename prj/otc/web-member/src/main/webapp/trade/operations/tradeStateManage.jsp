<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title></title>
		<script language="JavaScript" src="../../public/global.js"></script>
		<script language="JavaScript" src="../../public/open.js"></script>
		
		<script type="text/javascript">
			function window_load(result) {
				
			}
			
			function balanceChk_onclick(id) {
				var name;
			
				if (id == '06') {
					name = "�ָ�����";
				}
				if (id == '09') {
					name = "���׽���";
				}
				if (id == '05') {
					name = "��ͣ����";
				}
				if (id == '08') {
					name = "����׼��";
				}
				if (id == '07') {
					name = "���в���";
				}
				if (id == "99") {
					name = "�趨�ָ�ʱ��"
				}
				if (confirm("��ȷ��Ҫ" + name + "��")) {
					document.getElementById("tradeManageVo.status").value=id;
					agencyForm.submit();
					agencyForm.ok2.disabled = true;
					agencyForm.ok3.disabled = true;
					agencyForm.ok4.disabled = true;
					agencyForm.ok1.disabled = true;
				}
			}
			
			function writeTime() {
					var url="${basePath}/tradeManage/tradeStatusManage/forwardUpdateRecoverTime.action";
					var result=ecsideDialog(url,window,600,400);
					if(result>0){
						agencyForm.action="${basePath}/tradeManage/tradeStatusManage/forwardUpdate.action";
						agencyForm.submit();
					}
			}
		</script>
	</head>
	<body leftmargin="0" topmargin="0" onkeypress="keyEnter(event.keyCode);" onload="window_load(${obj.status })">
		<div id="main_body">
			<table width="550" border="0" cellspacing="0"
				cellpadding="0" align="center">
				<tr><td height="50">&nbsp;</td></tr>
				<tr><td height="20"><span style="font-weight:bold;color:#bc4763;font-size:14px;border-bottom:1px solid #fd1a00;"><img src="<%=skinPath%>/cssimg/xr.gif" align="absmiddle" />
							&nbsp;����״̬����</span></td></tr>
				<tr><td height="5">&nbsp;</td></tr>
				<tr>
					<td>
						<div class="div_tj">
							<form name="agencyForm" method="post" action="${basePath}/tradeManage/tradeStatusManage/updateTradeStatus.action">
						<input type="hidden" name="tradeManageVo.recoverTime" value="${obj.recoverTime }">
						<input type="hidden" name="tradeManageVo.status" id="obj.status" value="${obj.status}">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style" width="95%">
									<tr>
										<td height="60">
											<div class="div2_top">
												<table width="65%" border="0" cellspacing="0"
													cellpadding="0" align="center">
													<tr><td height="15" colspan="2">&nbsp;</td></tr>
													<tr><td height="3" colspan="2"></td></tr>
													<tr>
														<td align="left" height="35" colspan="2">
															<span style="font-weight:bold;color:#f38243;">�����գ�</span>&nbsp;<span style="font-family: ����;font-size: 14px;font-weight:bold;color:#7d0c01;"><fmt:formatDate value="${obj.tradeDate}" type="date"/></span>
														</td>
													</tr>
													<tr><td height="3" colspan="2"></td></tr>
													<tr>
														<td align="left" height="35" colspan="2">
															<span style="font-weight:bold;color:#f38243;">�г�״̬��</span>&nbsp;<span style="font-family: ����;font-size: 14px;font-weight:bold;color:#7d0c01;">${statusMap[obj.status]}</span>&nbsp;&nbsp;
														</td>
													</tr>
													<tr><td height="3" colspan="2"></td></tr>
													<!-- <c:if test="${obj.node!=null}">
														<tr height="35">
														<td align="left" height="35" colspan="2">
															<span style="font-weight:bold;color:#f38243;">&nbsp;&nbsp;��ע��</span>&nbsp;<span style="font-weight:bold;color:#7d0c01;">${obj.node }</span>
														</td>
													</tr>
													<tr><td height="3" colspan="2"></td></tr>
													</c:if>
													<tr height="35">
														<td align="left" width="35%">
														    <span style="font-size: 7px;color: #ff0000;">��</span>&nbsp;
															<font color="gray">&nbsp;<span style="font-weight:bold;font-size:15px;">����׼��</span></font>
														</td>
														<td align="left">
														<span style="font-family: ����;font-size: 14px;font-weight:bold;color:#7d0c01;">(<c:forEach items="${tradeRunModeMap}" var="maps">
				  												<c:if test="${obj.runMode==maps.key}">${maps.value}</c:if>
				  										</c:forEach>)</span>
				  										</td>
													</tr>
													<tr><td colspan="2" height="3"></td></tr>
													<tr height="35">
														<td colspan="2">
														    <span style="font-size: 7px;color: #ff0000;">��</span>&nbsp;
															<c:if test="${obj.status==0}">
																<font color="blue">&nbsp;<span style="font-weight:bold;font-size:13px;">��ʼ����</span></font>
															</c:if>
															<c:if test="${obj.status!=0}">
																<font color="gray">&nbsp;<span style="font-weight:bold;font-size:15px;">��ʼ����</span></font>
															</c:if>
														</td>
													</tr>
													<tr><td colspan="2" height="3"></td></tr>
													<tr height="35">
														<td colspan="2">
														    <span style="font-size: 7px;color: #ff0000;">��</span>&nbsp;
															<font color="gray">&nbsp;<span style="font-weight:bold;font-size:15px;">��ͣ����</span></font>
														</td>
													</tr>
													<tr><td colspan="2" height="3"></td></tr>
													<c:if test="${obj.status==4}">
													<tr height="35">
														<td colspan="2" align="left" height="35">
															<span style="font-weight:bold;color:#f38243;">&nbsp;&nbsp;�ָ�ʱ�䣺</span>&nbsp;<span class="req" id="t"></span>${obj.recoverTime }
															<button id="updat1e11" class="btn1_mouseout" style="cursor:null">�趨�ָ�ʱ��</button>
														</td>
													</tr>
													<tr><td colspan="2" height="3"></td></tr>
													</c:if>
													<tr height="35">
														<td colspan="2">
														    <span style="font-size: 7px;color: #ff0000;">��</span>&nbsp;
															<font color="gray">&nbsp;<span style="font-weight:bold;font-size:15px;">�ָ�����</span></font>
														</td>
													</tr>
													<tr><td colsapn="2" height="3"></td></tr>
													<tr height="35">
														<td>
														    <span style="font-size: 7px;color: #ff0000;">��</span>&nbsp;
															<font color="gray">&nbsp;<span style="font-weight:bold;font-size:15px;">���׽���</span></font>
														</td>
														<td>
														<span style="font-family: ����;font-size: 14px;font-weight:bold;color:#7d0c01;">(Ĭ���Զ�)</span>
														</td>
													</tr>
													<tr><td height="3"></td></tr>
													
													<tr>
														<td align="left">
														<font color=red>��</font>&nbsp;
															<button class="btn_secmid" name="ok11" id="updat1e"
											onclick="javascript:balanceChk_onclick('07');">
																���в���
															</button>
														</td>
													</tr>
													 -->
													<tr>
														<td colspan="2" align="left">
														<div id="status1" align="center" class="common1">
										</div>
														</td>
													</tr>
													<tr><td colspan="2" height="15">&nbsp;</td></tr>
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
