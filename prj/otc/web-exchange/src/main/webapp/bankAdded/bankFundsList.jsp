<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>银行资金列表</title>
		<%@ include file="/public/ecsideLoad.jsp"%>
	</head>
	<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<div class="div_tj">
						<form name="frm"
								action="${basePath}/bankFunds/poundageChange/list.action"
								method="post">
							<table border="0" cellpadding="0" cellspacing="0"
								class="table2_style">
								<tr>
									<td class="table2_td_widthmin">
										<div class="div2_top">
											<table class="table3_style" border="0" cellspacing="0"
												cellpadding="0">
												<tr>
													<td class="table3_td_1" align="left">
														银行名称:&nbsp;
														<label>
															<input type="text" id="bankCode"
																name="${GNNT_}bankName[like]"
																value="${oldParams['bankName[like]'] }"
																class="input_text" />
														</label>
													</td>
													<td class="table3_td_anniu" align="left">
														<button  class="btn_sec" onclick="select()">查询</button>&nbsp;&nbsp;
														<button class="btn_cz" onclick="myReset()">重置</button>
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
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%">
								<tr>
									<td>
										<ec:table items="resultList"
											autoIncludeParameters="${empty param.autoInc}" var="bankFunds"
											action="${basePath}/bankFunds/poundageChange/list.action"
											title="" minHeight="345" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="导出列表.csv" style="table-layout:fixed">

											<ec:row recordKey="${bankFunds.bankCode}">
												<ec:column width="4%" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												<ec:column property="banks.bankName[like]" width="15%"
													title="银行名称" style="text-align:center; "
													value="${bankFunds.bankName}" />
												<ec:column property="primary.lastMarketFeeBalance[=][double]" width="13%"
													title="上日交易所收取费用余额" style="text-align:center;">
													<fmt:formatNumber value="${bankFunds.lastMarketFeeBalance}" type="currency" pattern="0.00"/>
												</ec:column>
												<ec:column property="primary.marketFeeNew[=][double]" width="13%"
													title="交易所新增手续费" style="text-align:center;">
													<fmt:formatNumber value="${bankFunds.marketFeeNew}" type="currency" pattern="0.00"/>
												</ec:column>
												<ec:column property="primary.marketdelayFeeNew[=][double]" width="13%"
													title="交易所新增延期费" style="text-align:center;">
													<fmt:formatNumber value="${bankFunds.marketdelayFeeNew}" type="currency" pattern="0.00"/>
												</ec:column>
												<ec:column property="primary.marketFeeBalance[=][double]" width="13%"
													title="交易所收取费用余额" style="text-align:center;">
													<fmt:formatNumber value="${bankFunds.marketFeeBalance}" type="currency" pattern="0.00"/>
												</ec:column>
												<ec:column property="_1" width="13%" sortable="false" filterable="false"
													title="手续费划转" style="text-align:center;"><a class="blank_a"
														href="javascript:change('${bankFunds.bankCode }');"><font
														color="#880000">划转</font></a></ec:column>
												<ec:column property="_2" width="13%" sortable="false" filterable="false"
													title="查看划转记录" style="text-align:center;"><a class="blank_a"
														href="javascript:poundageChange('${bankFunds.bankCode }');"><font
														color="#880000">查看</font></a></ec:column>
											</ec:row>
										</ec:table>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<!-- 编辑和过滤所使用的 通用的文本框模板 -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value=""
				onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="" />
		</textarea>

		<SCRIPT type="text/javascript">
		function select(){
			frm.action = "${basePath}/bankFunds/poundageChange/list.action?sortName=bankCode";
			frm.submit();
		}
		function poundageChange(bankCode){
			frm.action = "${basePath}/bankFunds/poundageChange/transList.action?primary.transType=F&sortName=primary.createTime desc ,primary.createTime&original_bankCode="+bankCode;
			frm.submit();
		}
		function change(id) {
			var url = "${basePath}/bankFunds/poundageChange/forwardUpdate.action?bankCode="+id;
			ecsideDialog(url);
		}
		</SCRIPT>
	</body>
</html>