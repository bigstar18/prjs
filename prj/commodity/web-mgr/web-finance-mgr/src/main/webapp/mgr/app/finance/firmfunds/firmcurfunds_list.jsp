<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>�����̵�ǰ�ʽ�</title>
		<SCRIPT type="text/javascript">
		<!-- 
			//�鿴��Ϣ����
			function fundsDetail(firmId){
				var url = "${basePath}/finance/firmfunds/fundsdetail.action?firmId="+firmId;
				showDialog(url, "", 400, 500);
			}
			//ִ�в�ѯ�б�
			function dolistquery() {
				frm.submit();
			}
			function changeBlanceSel(sel){
			//alert(sel);
			if(sel=="eq"){
				frm.balanceNe.value="";
				frm.balanceEq.value="0";
			} else if(sel=="ne"){
				frm.balanceNe.value="0";
				frm.balanceEq.value="";
			} else {
				frm.balanceNe.value="";
				frm.balanceEq.value="";
			}
		}
		//-->
		</SCRIPT>
	</head>
	<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<div class="div_cx">
							<form name="frm" action="${basePath}/finance/firmfunds/firmcurfunds.action?sortColumns=order+by+firmId" method="post">
								<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
									<tr>
										<td class="table5_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td class="table3_td_1" align="left" style="width: 40%">
															�����̴���:&nbsp;
															<label>
																<input type="text" class="validate[maxSize[<fmt:message key='MFirm.firmId_q' bundle='${PropsFieldLength}' />]] input_text" id="id"  checked="checked" name="${GNNT_}primary.firmId[=][String]" value="${oldParams['primary.firmId[=][String]']}" />
															</label>
														</td>
					  									<td class="table3_td_1" align="left" style="width: 40%">
															����������:&nbsp;
															<label>
																<input type="text" class="input_text" id="firmName"  checked="checked" name="${GNNT_}primary.name[like][String]" value="${oldParams['primary.name[like][String]']}" />
															</label>
														</td>	
					  									
														<td style="width: 20%">&nbsp;</td>
													</tr>
													<tr>
														
														<td class="table3_td_1" align="left" style="width: 40%">
															�����������:&nbsp;
															<label>
																<select id="balanceSubtract" name="balanceSubtract" onchange="changeBlanceSel(this.value);" class="normal" style="width: 120px">
																	<option value="">ȫ��</option>
																	<option value="ne">����</option>
																	<option value="eq">���</option>
																</select>
															</label>
															<input id="balanceNe" name="${GNNT_}primary.balanceSubtract[!=][double]" type="hidden" value="" >
															<input id="balanceEq" name="${GNNT_}primary.balanceSubtract[=][double]" type="hidden" value="">
															 <script >
															 	if(${oldParams["primary.balanceSubtract[=][double]"] ==""}&&${oldParams["primary.balanceSubtract[!=][double]"] !=""})
																frm.balanceSubtract.value = "ne";
																if(${oldParams["primary.balanceSubtract[=][double]"] !=""}&&${oldParams["primary.balanceSubtract[!=][double]"] ==""})
																frm.balanceSubtract.value = "eq";
																if(${oldParams["primary.balanceSubtract[=][double]"] ==""}&&${oldParams["primary.balanceSubtract[!=][double]"] ==""})
																frm.balanceSubtract.value = "";
																changeBlanceSel(frm.balanceSubtract.value);
					  										</script>
					  									</td>
					  									<td class="table3_td_anniu" align="left" style="width: 20%">
															<button class="btn_sec" id="view" onclick=dolistquery();>��ѯ</button>
															&nbsp;&nbsp;
															<button class="btn_cz" onclick="myReset();">����</button>
														</td>
													</tr>
												</table>
											</div>
										</td>
									</tr>
								</table>
							</form>
						</div>
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
								<tr>
									<td>
										<ec:table items="pageInfo.result" var="firmCurFunds"
											action="${basePath}/finance/firmfunds/firmcurfunds.action?sortColumns=order+by+firmId"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="demo.xls" csvFileName="demo.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
												<ec:column property="firmId" width="8%" title="�����̴���" style="text-align:center;" ellipsis="true"/>
												<ec:column property="name" title="����������" width="8%" style="text-align:center;"  ellipsis="true"/>
												<ec:column property="f_balance" title="����ϵͳ��ǰ���" width="10%" style="text-align:right;">
													<fmt:formatNumber value="${firmCurFunds.f_balance}" pattern="#,##0.00"/>
												</ec:column>
												<ec:column property="l_balance" title="����������" width="12%" style="text-align:right;">
													<fmt:formatNumber value="${firmCurFunds.l_balance}" pattern="#,##0.00"/>
												</ec:column>
												<ec:column property="y_balance" title="����δ������" width="12%" style="text-align:right;">
													<span onclick="fundsDetail('<c:out value="${firmCurFunds.firmId}"/>')" style="cursor:hand;color:blue">
										  			<fmt:formatNumber value="${firmCurFunds.y_balance}" pattern="#,##0.00"/>&nbsp;<img src="<%=skinPath%>/image/app/finance/firmfunds/053753258.gif" width="15" height="15" align="bottom"/>
										  			</span>
												</ec:column>
												<ec:column property="balanceSubtract" title="���" width="10%" style="text-align:right;">
													<fmt:formatNumber value="${firmCurFunds.balanceSubtract}" pattern="#,##0.00"/>
												</ec:column>
												
												<ec:column property="user_balance" title="�����ʽ�" width="12%" style="text-align:right;">
													<fmt:formatNumber value="${firmCurFunds.user_balance}" pattern="#,##0.00"/>
												</ec:column>
											</ec:row>
										</ec:table>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
			</table>
		<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;" name="" />
		</textarea>
	</body>
</html>