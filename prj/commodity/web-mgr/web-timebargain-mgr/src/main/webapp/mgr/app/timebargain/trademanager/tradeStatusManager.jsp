<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
	<head>
		<title></title>
		<script type="text/javascript">
		function window_load(result){
			if (result == "3") {				// �ʽ�������
				frm.ok2.disabled = true;
		  		frm.ok3.disabled = true;
		  		frm.ok4.disabled = false;
		  		frm.ok1.disabled = true;
		  		frm.ok11.disabled = true;
			}
			if (result == "5" || result == "8") {// �����С����Ͼ��۽�����
			
				frm.ok2.disabled = false;
		  		frm.ok3.disabled = false;
		  		frm.ok4.disabled = true;
		  		frm.ok1.disabled = true;
		  		frm.ok11.disabled = true;
			}
			if (result == "4") {				// ��ͣ����
				frm.ok2.disabled = true;
		  		frm.ok3.disabled = true;
		  		frm.ok4.disabled = true;
		  		frm.ok1.disabled = false;
		  		frm.ok11.disabled = true;
		  		document.getElementById("t").innerHTML = frm.recoverTime.value;
			}
			if (result == "6") {// �ڼ���Ϣ
				frm.ok2.disabled = false;
		  		frm.ok3.disabled = false;
		  		frm.ok4.disabled = true;
		  		frm.ok1.disabled = true;
		  		frm.ok11.disabled = true;
			}
			if (result == "9") {// ���Ͼ��۽��׽���
				frm.ok2.disabled = false;
		  		frm.ok3.disabled = true;
		  		frm.ok4.disabled = true;
		  		frm.ok1.disabled = true;
		  		frm.ok11.disabled = true;
			}
			if (result == "7") {				// ���׽���
				frm.ok2.disabled = true;         
		  		frm.ok3.disabled = true;
		  		frm.ok4.disabled = true;
		  		frm.ok1.disabled = true;
		  		frm.ok11.disabled = false;
			}
			//if (result == "��ʼ��ʧ��") {
			//	frm.ok2.disabled = true;
		  	//	frm.ok3.disabled = true;
		  	//	frm.ok4.disabled = false;
		  	//	frm.ok1.disabled = true;
		  	//	frm.ok11.disabled = false;
			//}
			if (result == "0" || result == "1" || result == "2" || result == "10") {// ��ʼ����ɡ�����״̬�������С����׽������
				frm.ok2.disabled = true;
		  		frm.ok3.disabled = true;
		  		frm.ok4.disabled = true;
		  		frm.ok1.disabled = true;
		  		frm.ok11.disabled = true;
			}	
		}
		function balanceChk_onclick(id)
		{
		  var name;
		  
		  if(id=='06')
		  {
		   name="�ָ�����";
		  }
		  if(id=='09')
		  {
		   name="���׽���";
		  }
		  if(id=='05')
		  {
		  name="��ͣ����";
		  }
		  if(id=='08')
		  {
		   name="����׼��";
		  }
		  if(id=='07')
		  {
		  name="���в���";
		  }
		  if (id == "99") {
		  	name="�趨�ָ�ʱ��"
		  }
		  if (confirm("��ȷ��Ҫ" + name + "��"))
		    {
		  frm.marketStatus.value=id;
		  frm.submit();
		 
		  frm.ok2.disabled = true;
		  frm.ok3.disabled = true;
		  frm.ok4.disabled = true;
		  frm.ok1.disabled = true;
		  frm.ok11.disabled = true;
		     }
		}
			
		function writeTime() {
			var url="${basePath}/timebargain/tradeManager/updateRecoverTimeToPage.action?"+new Date();
			var result=showDialogRes(url, '', 500, 300);//ecsideDialog1(url, window, 600, 400);
			if(result>0){
				frm.action="${basePath}/timebargain/tradeManager/tradeStatus.action";
				frm.submit();
			}
		}
		</script>
	</head>
	<body leftmargin="0" topmargin="0" onkeypress="keyEnter(event.keyCode);" onload="window_load(${systemStatus.status})">
			<table width="550" border="0" cellspacing="0"
				cellpadding="0" align="center">
				<tr><td height="50">&nbsp;</td></tr>
				<tr><td height="5">&nbsp;</td></tr>
				<tr>
					<td>
					<div class="div_cxtj">
				    	<div class="div_cxtjL"></div>
				        <div class="div_cxtjC">����״̬����</div>
				        <div class="div_cxtjR"></div>
		   		    </div>
					<div style="clear: both;"></div>
					<div class="div_tj">
							<form name="frm" method="post" action="${basePath}/timebargain/tradeManager/updateTradeStatus.action">
								<input type="hidden" name="marketStatus" value="${systemStatus.status}">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style" width="95%" >
									<tr>
										<td>
											<div class="div2_top">
												<table width="65%" border="0" cellspacing="0"
													cellpadding="0" align="center">
													<tr><td height="15" colspan="2">&nbsp;</td></tr>
													<tr><td height="3" colspan="2"></td></tr>
													<tr>
														<td height="35" colspan="2">
															<span style="font-weight:bold;color:#f38243;font-size: 14px">&nbsp;&nbsp;��ǰʱ�䣺</span>
															<span style="font-family: ����;font-size: 12px;font-weight:bold;color:#7d0c01;">${sysTime}</span>&nbsp;&nbsp;
														</td>
													</tr>
													<tr>
														<td colspan="2" align="left" height="35">
															<span style="font-weight:bold;color:#f38243;font-size: 14px">&nbsp;&nbsp;�������ڣ�</span>
															<span style="font-family: ����;font-size: 12px;font-weight:bold;color:#7d0c01;"><fmt:formatDate value="${systemStatus.tradeDate}" pattern="yyyy-MM-dd"/></span>
														</td>
													</tr>
													<tr>
														<td colspan="2" align="left" height="35">
															<span style="font-weight:bold;color:#f38243;font-size: 14px">&nbsp;&nbsp;�г�״̬��</span>
															<span style="font-family: ����;font-size: 12px;font-weight:bold;color:#7d0c01;">${tradeStatusMap[systemStatus.status]}</span>&nbsp;&nbsp;&nbsp;
														</td>
													</tr>
													<tr>
														<td height="35" >
															<span style="font-weight:bold;color:#f38243;font-size: 14px">&nbsp;&nbsp;��ע��</span>
															<span style="font-family: ����;font-size: 12px;font-weight:bold;color:#7d0c01;">${systemStatus.note}</span>&nbsp;&nbsp;
														</td>
														<td height="35" >
															<span style="font-weight:bold;color:#f38243;font-size: 14px">&nbsp;&nbsp;���׽ڣ�</span>
															<span style="font-family: ����;font-size: 12px;font-weight:bold;color:#7d0c01;">${systemStatus.sectionID}</span>&nbsp;&nbsp;
														</td>
													</tr>
													
													<tr><td height="3" colspan="2"></td></tr>
													<tr><td colspan="2" height="3"></td></tr>
													<tr>
														<td align="left" width="35%" height="35">&nbsp;&nbsp;
															<font color=red>��</font>&nbsp;
															<button class="btn_sec" name="ok4" id="update"
																onclick="javascript:balanceChk_onclick('08');">
																����׼��
															</button>
														</td>
														<td>
														<span style="color:#f38243;font-size: 12px">(${tradeRunModeMap[runMode]})</span>
							  							</td>
													</tr>
													<tr height="30">
											        <td align="left" width="35%" height="35">&nbsp;&nbsp;
														<font color=red>��</font>&nbsp;
														<c:if test="${systemStatus.status==0}">
										        			<font color="blue">��ʼ����</font>
												       	</c:if>
												        <c:if test="${systemStatus.status!=0}">
												        	<font color="gray">��ʼ����</font>
												        </c:if>
											        </td>
											        </tr>
													<tr><td colspan="2" height="3"></td></tr>
													<tr><td colspan="2" height="3"></td></tr>
													<tr>
														<td colspan="2" align="left" height="35">&nbsp;&nbsp;
															<font color=red>��</font>&nbsp;
															<button class="btn_sec" name="ok3" id="update"
																onclick="javascript:balanceChk_onclick('05');">
																��ͣ���� 
															</button>
														</td>
													</tr>
													<tr><td colspan="2" height="3"></td></tr>
													<c:if test="${systemStatus.status==4}">
													<tr>
														<td align="center" height="35" width="40%" >&nbsp;&nbsp;
															�ָ�ʱ�䣺&nbsp;<span class="req" id="t"></span>${systemStatus.recoverTime }
														</td>
														<td>
															<button id="update11"  class="btn_sec1"  onclick="writeTime()">�趨�ָ�ʱ��</button>
														</td>
													</tr>
													<tr><td colspan="2" height="3"></td></tr>
													</c:if>
													<tr>
														<td colspan="2" align="left" height="35">&nbsp;&nbsp;
															<font color=red>��</font>&nbsp;
															<button class="btn_sec" name="ok1" id="update"
																onclick="javascript:balanceChk_onclick('06');">
																�ָ�����
															</button>
														</td>
													</tr>
													<tr><td colspan="2" height="3"></td></tr>
													<tr>
														<td align="left" width="35%" height="35">&nbsp;&nbsp;
															<font color=red>��</font>&nbsp;
															<button class="btn_sec" name="ok2" id="update"
																onclick="javascript:balanceChk_onclick('09');">
																���׽���
															</button>
														</td>
														<td>
														<span style="color:#f38243;font-size: 12px">(Ĭ���Զ�)</span>
														</td>
														<td>
														</td>
													</tr>
													<tr><td colspan="2" height="3"></td></tr>
													<tr>
														<td align="left" width="35%" height="35">&nbsp;&nbsp;
															<font color=red>��</font>&nbsp;
															<button class="btn_sec" name="ok11" id="update"
																onclick="javascript:balanceChk_onclick('07');">
																���в���
															</button>
														</td>
														
													</tr>
													<tr><td colspan="2" height="3"></td></tr>
													<tr>
														<td colspan="2" align="left">
														<div id="status1" align="center" class="common1"></div>
														</td>
													</tr>
													<tr><td colspan="2" height="15">&nbsp;</td></tr>
													
												</table>
											</div>
										</td>
									</tr>
								</table>
							</form>
					</td>
				</tr>
			</table>
	</body>
</html>