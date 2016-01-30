<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%
	String accountCode = (String)request.getAttribute("accountCode");
	String nowDate = Tools.fmtDate(new Date());
%>
<html>
	<head>
		<title>已结算凭证查询列表</title>
		<SCRIPT type="text/javascript">
		<!-- 
			function init() {
				if(frm.beginDate.value == null || frm.beginDate.value == ""){
					frm.beginDate.value = '<%=nowDate%>';
				}
				if(frm.endDate.value == null || frm.endDate.value == ""){
					frm.endDate.value = '<%=nowDate%>';
				}
			}
			//查看信息详情
			function voucherDetails(voucherno){
				var url = "${basePath}/finance/voucher/viewVoucherDetails.action?entity.voucherNo="+voucherno;
				showDialog(url, "", 700, 500);
			}
			//执行查询列表
			function dolistquery() {
				 var startDate = document.getElementById("beginDate").value;
				 var endDate =  document.getElementById("endDate").value;
		
				  if(startDate != "" && endDate != "" || startDate == "" && endDate != "" || startDate != "" && endDate == "")
				  {
					if(startDate == ""){
						alert("开始日期不能为空！");
						frm.beginDate.focus();
						return false;
						
					}
					if(endDate == ""){
						alert("结束日期不能为空！");
						frm.endDate.focus();
						return false;
						
					}
					if ( startDate > '<%=nowDate%>' ) { 
					        alert("开始日期不能大于当天日期!"); 
					        frm.beginDate.focus();
					        return false; 
					} 
					if ( startDate > endDate ) { 
				        alert("开始日期不能大于结束日期!"); 
				        return false; 
				    } 
				  }
				frm.submit();
			}
		//-->
		</SCRIPT>
	</head>
	<body onload="init()">
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<div class="div_cx">
							<form name="frm" action="${basePath}/finance/voucher/voucherHistoryList.action" method="post">
								<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
									<tr>
										<td class="table5_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td class="table3_td_1" align="right">开始日期:&nbsp;
															<label>
																<input type="text" class="input_text wdate" id="beginDate"
																	name="${GNNT_}primary.b_date[>=][date]"
																	value="${oldParams['primary.b_date[>=][date]']}" 
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})"/>
															</label>
														</td>
														<td class="table3_td_1" align="right">结束日期:&nbsp;
															<label>
																<input type="text" class="input_text wdate" id="endDate"
																	name="${GNNT_}primary.b_date[<=][date]"
																	value="${oldParams['primary.b_date[<=][date]']}" 
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})"/>
															</label>
														</td>
														<td class="table3_td_1" align="right">凭证号:&nbsp;
															<label>
																<input type="text" class="input_text" id="voucherNo" name="${GNNT_}primary.voucherNo[=][Long]" value="${oldParams['primary.voucherNo[=][Long]']}" maxLength="<fmt:message key='Voucher.voucherNo_q' bundle='${PropsFieldLength}'/>"/>
															</label>
														</td>
													</tr>
													<tr>
														
														<td class="table3_td_1" align="right">录入员:&nbsp;
															<label>
																<input type="text" class="input_text" id="inputUser" name="${GNNT_}primary.inputUser[=][String]" value="${oldParams['primary.inputUser[=][String]']}" maxLength="<fmt:message key='Voucher.summary_q' bundle='${PropsFieldLength}'/>"/>
															</label>
														</td>
														<td class="table3_td_1" align="right">审核员:&nbsp;
															<label>
																<input type="text" class="input_text" id="auditor" name="${GNNT_}primary.auditor[=][String]" value="${oldParams['primary.auditor[=][String]']}" maxLength="<fmt:message key='Voucher.summary_q' bundle='${PropsFieldLength}'/>"/>
															</label>
														</td>
														<td class="table3_td_1" align="right">凭证摘要号:&nbsp;
															<label>
																<input type="text" class="input_text" id="summaryno" name="${GNNT_}primary.summaryNo[allLike]" value="${oldParams['primary.summaryNo[allLike]']}" maxLength="<fmt:message key='Voucher.summaryNo_q' bundle='${PropsFieldLength}'/>"/>
															</label>
														</td>
													</tr>
													<tr>
														
														<td class="table3_td_1" align="right">凭证摘要:&nbsp;
															<label>
																<input type="text" class="input_text" id="summary" name="${GNNT_}primary.summary[allLike]" value="${oldParams['primary.summary[allLike]']}" maxLength="<fmt:message key='Voucher.summary_q' bundle='${PropsFieldLength}'/>"/>
															</label>
														</td>
														<td class="table3_td_anniu" align="right">
															<button class="btn_sec" id="view" onclick=dolistquery();>查询</button>&nbsp;
															<button class="btn_cz" onclick="myReset();">重置</button>
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
										<ec:table items="pageInfo.result" var="voucher"
											action="${basePath}/finance/voucher/voucherHistoryList.action"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="voucher.xls" csvFileName="voucher.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
												<ec:column property="voucherNo" width="10%" title="凭证号" style="text-align:center;">
													<a href="#" class="blank_a" onclick="return voucherDetails('${voucher.voucherNo}');"><font color="#880000">${voucher.voucherNo}</font></a>
												</ec:column>
												<ec:column property="b_date" title="结算日期" width="10%" style="text-align:center;"><fmt:formatDate value="${voucher.b_date}" pattern="yyyy-MM-dd"/></ec:column>
												<ec:column property="summaryNo" title="凭证摘要号" width="10%" style="text-align:center;"/>
												<ec:column property="summary" title="凭证摘要" width="10%" style="text-align:center;" ellipsis="true"/>
												<ec:column property="inputUser" title="录入员" width="10%" style="text-align:center;"/>
												<ec:column property="inputTime" title="录入时间" width="10%" style="text-align:center;"><fmt:formatDate value="${voucher.inputTime}" pattern="yyyy-MM-dd HH:mm:ss"/></ec:column>
												<ec:column property="auditor" title="审核员" width="10%" style="text-align:center;"/>
												<ec:column property="auditTime" title="审核时间" width="10%" style="text-align:center;"><fmt:formatDate value="${voucher.auditTime}" pattern="yyyy-MM-dd HH:mm:ss"/></ec:column>
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
			<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;" name="" />
		</textarea>
	</body>
</html>