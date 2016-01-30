<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%
	String nowDate = Tools.fmtDate(new Date());
%>
<html>
	<head>
		<title>附加说明查询</title>
	</head>
	<body onload="change();">
		<SCRIPT type="text/javascript">
	function frmSubmitFn() {
		frm.submit();
	}

	function select1() {
		var beginD = frm.beginDate.value;
		var endD = frm.endDate.value;
		var value = frm.type.value;
		if (value == 'H') {
			checkQueryDate(frm.beginDate.value,frm.endDate.value);

		}else{
			frmSubmitFn();
		}
	}
	function setDisabled(obj) {
		obj.disabled = true;
		obj.style.backgroundColor = "#C0C0C0";
	}
	function setEnabled(obj) {
		obj.disabled = false;
		obj.style.backgroundColor = "white";
	}

	function change() {
		var value = frm.type.value;
		if (value == 'D') {
			setDisabled(frm.beginDate);
			setDisabled(frm.endDate);
		} else if (value == 'H') {
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
								action="${basePath}/finance/financialQuery/attachExplain.action"
								method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table5_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0">
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
	document.getElementById("type").value = "${type}";
</script>
														<td class="table3_td_1" align="left">
															&nbsp;&nbsp;&nbsp;开始日期:&nbsp;
															<label>
																<input type="text" style="width: 120px" id="beginDate"
																	class="wdate" maxlength="10"
																	name="${GNNT_}primary.happenDate[>=][date]"
																	value='${oldParams["primary.happenDate[>=][date]"]}'
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
															</label>
														</td>
														<td class="table3_td_1" align="left">
															&nbsp;&nbsp;&nbsp;结束日期:&nbsp;
															<label>
																<input type="text" style="width: 120px" id="endDate"
																	class="wdate" maxlength="10"
																	name="${GNNT_}primary.happenDate[<=][datetime]"
																	value='${oldParams["primary.happenDate[<=][datetime]"]}'
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
															</label>
														</td>

														<td class="table3_td_anniu" align="left">
															<button class="btn_sec" onclick=
	select1();
>
																查询
															</button>
															&nbsp;&nbsp;
															<button class="btn_cz" onclick=
	myReset();
>
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
										<ec:table items="pageInfo.result" var="attach"
											action="${basePath}/finance/financialQuery/attachExplain.action"
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="attach.xls" csvFileName="attach.csv"
											showPrint="true" listWidth="100%" minHeight="345"
											style="table-layout:fixed;">
											<ec:row>
												<ec:column width="3%" property="_0" title="序号"
													value="${GLOBALROWCOUNT}" sortable="false"
													filterable="false" style="text-align:center;" />
												<ec:column property="attachCode" title="附加代码" width="3%"
													style="text-align:center;" />
												<ec:column property="fattachExplainType.attachTypeExplain"
													title="附加类型" width="3%" style="text-align:center;"
													ellipsis="true" />

												<ec:column property="happenMoney" title="发生额" width="3%"
													style="text-align:right;">
													<fmt:formatNumber value="${attach.happenMoney}"
														pattern="#,##0.00" />
												</ec:column>

												<ec:column property="attachExplain" title="附加说明" width="3%"
													style="text-align:center;" />

												<ec:column property="happenDate" title="发生日期" width="3%"
													style="text-align:center;">
													<fmt:formatDate value="${attach.happenDate }"
														pattern="yyyy-MM-dd" />

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