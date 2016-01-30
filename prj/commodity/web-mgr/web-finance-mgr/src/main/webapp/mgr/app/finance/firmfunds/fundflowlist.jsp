<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%
   String nowDate = Tools.fmtDate(new Date());
%>
<html>
	<head>
		<title>交易商资金流水</title>
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
															&nbsp;&nbsp;&nbsp;查询范围:&nbsp;
															<label>
																<select name="type" size="1" id="type"
																	style="width: 120" onchange="change()">
																	<option value="D">
																		当前
																	</option>
																	<option value="H">
																		历史
																	</option>
																</select>
															</label>
														</td>
														<script>
															document.getElementById("type").value="${type}";
														</script>
														<td class="table3_td_1" align="left">
															&nbsp;&nbsp;&nbsp;开始日期:&nbsp;
															<label>
																<input type="text" style="width: 120px" id="beginDate"
																	class="wdate" maxlength="10"
																	name="${GNNT_}primary.b_Date[>=][date]"
																	value='${oldParams["primary.b_Date[>=][date]"]}'
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
															</label>
														</td>
														<td class="table3_td_1" align="left">
														&nbsp;&nbsp;&nbsp;结束日期:&nbsp;
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
															&nbsp;&nbsp;&nbsp;系统名称:&nbsp;
															<label>
																<select type="text" class="input_text" id="moduleId"
																	name="${GNNT_}primary.summaryF.ledgerField.moduleId[=]">
																	<option value="">全部</option>
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
															&nbsp;&nbsp;&nbsp;业务名称:&nbsp;
															<label>
																<select type="text" class="input_text" id="summaryNo"
																	name="${GNNT_}primary.summaryF.summaryNo[=][String]">
																	<option value="">全部</option>
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
															&nbsp;&nbsp;&nbsp;交易商代码:&nbsp;
															<label>
																<input type="text" class="validate[maxSize[<fmt:message key='MFirm.firmId_q' bundle='${PropsFieldLength}' />]] input_text" id="id"
																	name="${GNNT_}primary.firmId[allLike]"
																	value="${oldParams['primary.firmId[allLike]']}" style="width: 110"/>
															</label>
														</td>
											    </tr>
												<tr>
														<td class="table3_td_1" align="left">
															&nbsp;&nbsp;&nbsp;发生额:&nbsp;
															<label>
																<input type="text" class="input_text" id="amount"
																	name="${GNNT_}primary.amount[=][double]" 
																	value="${oldParams['primary.amount[=][double]'] }" />
															</label>
														</td>
														<td class="table3_td_1" align="left" >
															&nbsp;&nbsp;&nbsp;资金余额:&nbsp;
															<label>
																<select id="balance" name="balance" onchange="changeBlance(this.value);" class="normal" style="width: 120px">
																	<option value="">全部</option>
																	<option value="b">大于零</option>
																	<option value="eq">等于零</option>
																	<option value="s">小于零</option>
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
																查询
															</button>
															&nbsp;&nbsp;
															<button class="btn_cz" onclick=myReset();>
																重置
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
												<ec:column width="3%" property="_0" title="序号" style="text-align:center;" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												<ec:column property="fundFlowId" width="5%" title="流水编号" style="text-align:center; "/>
												<c:if test="${type=='H'}">
													<ec:column property="b_Date" title="结算日期 " width="8%" style="text-align:center;">
														<fmt:formatDate value="${fundFlow.b_Date}" pattern="yyyy-MM-dd " />
													</ec:column>
												</c:if>
												<ec:column property="firmId" width="8%" title="交易商代码" style="text-align:center;" ellipsis="true"/>
												<ec:column property="summaryF.summary" title="业务名称" width="10%" style="text-align:center;" ellipsis="true"/>
												<ec:column property="contractNo" title="合同号" width="8%" style="text-align:left;"/>
												<ec:column property="commodityId" width="8%" title="商品代码" style="text-align:left; "/>
												<ec:column property="amount" title="发生额(元)" width="10%" style="text-align:right;">
													<fmt:formatNumber value="${fundFlow.amount}" pattern="#,##0.00" />
												</ec:column>
												<ec:column property="balance" title="资金余额 (元)" width="10%" style="text-align:right;">
													<fmt:formatNumber value="${fundFlow.balance}" pattern="#,##0.00" />
												</ec:column>
												<ec:column property="appendAmount" title="附加账金额(元) " width="8%" style="text-align:right;">
													<fmt:formatNumber value="${fundFlow.appendAmount}" pattern="#,##0.00" />
												</ec:column>
												<ec:column property="voucherNo" width="8%" title="凭证号" style="text-align:center; "/>
												<ec:column property="createTime" title="发生日期 " width="10%" style="text-align:center;">
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


			<!-- 编辑和过滤所使用的 通用的文本框模板 -->
			<textarea id="ecs_t_input" rows="" cols="" style="display: none">
		<input type="text" class="inputtext" value=""
					onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
					name="" />
	</textarea>
	</body>
</html>