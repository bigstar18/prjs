<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%
   String nowDate = Tools.fmtDate(new Date());
%>
<html>
	<head>
		<title>�������ʽ���ˮ</title>
	</head>
	<body onload="change();">
		<SCRIPT type="text/javascript">

		function setDisabled(obj)
		{
		  obj.disabled = true;
		  obj.style.backgroundColor = "#C0C0C0";
		}
		function setEnabled(obj)
		{
		  obj.disabled = false;
		  obj.style.backgroundColor = "white";
		}
		
			function select1() {
					checkQueryDate(frm.beginDate.value,frm.endDate.value);
			}
		
			function changeBlance(sel){
					if(sel=="b"){
						frm.balanceB.value="0";
						frm.balanceEq.value="";
						frm.balanceS.value="";
					} else if(sel=="eq"){
						frm.balanceB.value="";
						frm.balanceEq.value="0";
						frm.balanceS.value="";
					} else if(sel=="s"){
						frm.balanceB.value="";
						frm.balanceEq.value="";
						frm.balanceS.value="0";
					}else {
						frm.balanceB.value="";
						frm.balanceEq.value="";
						frm.balanceS.value="";
					}
				}
			
			function change(){
				var value=frm.type.value;
				if(value=='D')
				{
					//frm.beginDate.disabled=true;
					//frm.endDate.disabled=true;
					setDisabled(frm.beginDate);
					setDisabled(frm.endDate);
				}
				else if(value=='H')
				{
					//frm.beginDate.disabled=false;
					//frm.endDate.disabled=false;
					setEnabled(frm.beginDate);
					setEnabled(frm.endDate);
				}
			}
		</SCRIPT>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>
						<div class="div_cx">
							<form name="frm"
								action="${basePath}/finance/firmfunds/firmfundflow.action?sortColumns=order+by+fundFlowId+desc"
								method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table5_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0" >
													<tr>
														<td class="table3_td_1" align="left">
															&nbsp;&nbsp;&nbsp;��ѯ��Χ:&nbsp;
															<label>
																<select name="type" size="1" id="type"
																	style="width: 120" onchange="change()">
																	<option value="D">
																		��ǰ
																	</option>
																	<option value="H">
																		��ʷ
																	</option>
																</select>
															</label>
														</td>
														<script>
															document.getElementById("type").value="${type}";
														</script>
														<td class="table3_td_1" align="left">
															&nbsp;&nbsp;&nbsp;��ʼ����:&nbsp;
															<label>
																<input type="text" style="width: 120px" id="beginDate"
																	class="wdate" maxlength="10"
																	name="${GNNT_}primary.b_Date[>=][date]"
																	value='${oldParams["primary.b_Date[>=][date]"]}'
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
															</label>
														</td>
														<td class="table3_td_1" align="left">
														&nbsp;&nbsp;&nbsp;��������:&nbsp;
															<label>
																<input type="text" style="width: 120px" id="endDate"
																	class="wdate" maxlength="10"
																	name="${GNNT_}primary.b_Date[<=][datetime]"
																	value='${oldParams["primary.b_Date[<=][datetime]"]}'
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
															</label>
														</td>
														
														
													</tr>
													<tr>
													    <td class="table3_td_1" align="left" >
															&nbsp;&nbsp;&nbsp;ϵͳ����:&nbsp;
															<label>
																<select type="text" class="input_text" id="moduleId"
																	name="${GNNT_}primary.summaryF.ledgerField.moduleId[=]">
																	<option value="">ȫ��</option>
																	<c:forEach var="list" items="${moduleList}">
																		<option value="${list.moduleId}">${list.name }</option>
																	</c:forEach>
																</select>
															</label>
														</td>
														<script>
															document.getElementById("moduleId").value="${oldParams['primary.summaryF.ledgerField.moduleId[=]']}";
														</script>
														<td class="table3_td_1" align="left">
															&nbsp;&nbsp;&nbsp;ҵ������:&nbsp;
															<label>
																<select type="text" class="input_text" id="summaryNo"
																	name="${GNNT_}primary.summaryF.summaryNo[=][String]">
																	<option value="">ȫ��</option>
																	<c:forEach var="list" items="${summaryList}">
																		<option value="${list.summaryNo }">${list.summary }</option>
																	</c:forEach>
																</select>
															</label>
														</td>
														<script>
															document.getElementById("summaryNo").value="${oldParams['primary.summaryF.summaryNo[=][String]']}";
														</script>
														<td class="table3_td_1" align="left">
															&nbsp;&nbsp;&nbsp;�����̴���:&nbsp;
															<label>
																<input type="text" class="validate[maxSize[<fmt:message key='MFirm.firmId_q' bundle='${PropsFieldLength}' />]] input_text" id="id"
																	name="${GNNT_}primary.firmId[allLike]"
																	value="${oldParams['primary.firmId[allLike]']}" style="width: 110"/>
															</label>
														</td>
											    </tr>
												<tr>
														<td class="table3_td_1" align="left">
															&nbsp;&nbsp;&nbsp;������:&nbsp;
															<label>
																<input type="text" class="input_text" id="amount"
																	name="${GNNT_}primary.amount[=][double]" 
																	value="${oldParams['primary.amount[=][double]'] }" />
															</label>
														</td>
														<td class="table3_td_1" align="left" >
															&nbsp;&nbsp;&nbsp;�ʽ����:&nbsp;
															<label>
																<select id="balance" name="balance" onchange="changeBlance(this.value);" class="normal" style="width: 120px">
																	<option value="">ȫ��</option>
																	<option value="b">������</option>
																	<option value="eq">������</option>
																	<option value="s">С����</option>
																</select>
															</label>
															<input id="balanceB" name="${GNNT_}primary.balance[>][double]" type="hidden" value="" >
															<input id="balanceEq" name="${GNNT_}primary.balance[=][double]" type="hidden" value="">
															<input id="balanceS" name="${GNNT_}primary.balance[<][double]" type="hidden" value="">
															 </td>
															 <script >
															 	if(${oldParams["primary.balance[>][double]"] !=""}&&${oldParams["primary.balance[=][double]"] ==""}&&${oldParams["primary.balance[<][double]"] ==""})
																frm.balance.value = "b";
																if(${oldParams["primary.balance[=][double]"] !=""}&&${oldParams["primary.balance[>][double]"] ==""}&&${oldParams["primary.balance[<][double]"] ==""})
																frm.balance.value = "eq";
																if(${oldParams["primary.balance[<][double]"] !=""}&&${oldParams["primary.balance[>][double]"] ==""}&&${oldParams["primary.balance[=][double]"] ==""})
																frm.balance.value = "s";
																if(${oldParams["primary.balance[>][double]"] ==""}&&${oldParams["primary.balance[=][double]"] ==""}&&${oldParams["primary.balance[<][double]"] ==""})
																frm.balance.value = "";
																changeBlance(frm.balance.value);
					  										</script>
														<td class="table3_td_anniu" align="left">
															<button class="btn_sec" onclick=select1();>
																��ѯ
															</button>
															&nbsp;&nbsp;
															<button class="btn_cz" onclick=myReset();>
																����
															</button>
														</td>
													</tr>
												</table>
											</div>
										</td>
									</tr>
								</table>
							</form>
						</div>
						&nbsp;
						<br />
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%">
								<tr>
									<td>
										<ec:table items="pageInfo.result" var="fundFlow"
											action="${basePath}/finance/firmfunds/firmfundflow.action?sortColumns=order+by+fundFlowId+desc"
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="fundFlow.xls" csvFileName="fundFlow.csv"
											showPrint="true" listWidth="130%" minHeight="345">
											<ec:row>
												<ec:column width="3%" property="_0" title="���" style="text-align:center;" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												<ec:column property="fundFlowId" width="5%" title="��ˮ���" style="text-align:center; "/>
												<c:if test="${type=='H'}">
													<ec:column property="b_Date" title="�������� " width="8%" style="text-align:center;">
														<fmt:formatDate value="${fundFlow.b_Date}" pattern="yyyy-MM-dd " />
													</ec:column>
												</c:if>
												<ec:column property="firmId" width="8%" title="�����̴���" style="text-align:center;" ellipsis="true"/>
												<ec:column property="summaryF.summary" title="ҵ������" width="10%" style="text-align:center;" ellipsis="true"/>
												<ec:column property="contractNo" title="��ͬ��" width="8%" style="text-align:left;"/>
												<ec:column property="commodityId" width="8%" title="��Ʒ����" style="text-align:left; "/>
												<ec:column property="amount" title="������(Ԫ)" width="10%" style="text-align:right;">
													<fmt:formatNumber value="${fundFlow.amount}" pattern="#,##0.00" />
												</ec:column>
												<ec:column property="balance" title="�ʽ���� (Ԫ)" width="10%" style="text-align:right;">
													<fmt:formatNumber value="${fundFlow.balance}" pattern="#,##0.00" />
												</ec:column>
												<ec:column property="appendAmount" title="�����˽��(Ԫ) " width="8%" style="text-align:right;">
													<fmt:formatNumber value="${fundFlow.appendAmount}" pattern="#,##0.00" />
												</ec:column>
												<ec:column property="voucherNo" width="8%" title="ƾ֤��" style="text-align:center; "/>
												<ec:column property="createTime" title="�������� " width="10%" style="text-align:center;">
													<fmt:formatDate value="${fundFlow.createTime}" pattern="yyyy-MM-dd HH:mm:ss" />
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
		<input type="text" class="inputtext" value=""
					onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
					name="" />
	</textarea>
	</body>
</html>