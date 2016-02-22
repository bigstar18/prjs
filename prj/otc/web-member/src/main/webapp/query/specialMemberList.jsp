
<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@page import="java.util.*"%>
<html xmlns:MEBS>
	<head>
		<title>特别会员查询</title>
		<%@ include file="/public/ecsideLoad.jsp"%>
		<IMPORT namespace="MEBS"
			implementation="${basePath}/common/jslib/calendar.htc">
	</head>
	<body onload="init('${queryType}')">
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>
						<div class="div_tj">
							<form name="frm"
								action="${basePath}/query/querySpecialMemberSearch/list.action?sortName=primary.specialMemberName"
								method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table2_td_widthmax">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0" style="table-layout:fixed">
													<tr>
														<td class="table3_td_1" align="left">
															开始日期:&nbsp;
															<label>
																<input type="text" style="width: 100px" id="beginDate" class="wdate"
																	maxlength="10" name="${GNNT_}tradeDate[>=][date]"
																	value='${oldParams["tradeDate[>=][date]"]}'
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
															</label>
														</td>
														<td class="table3_td_1" align="left">
															结束日期:&nbsp;
															<label>
																<input type="text" style="width: 100px" id="endDate" class="wdate"
																	maxlength="10" name="${GNNT_}tradeDate[<=][date]"
																	value='${oldParams["tradeDate[<=][date]"]}'
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
															</label>
														</td>
														<td class="table3_td_1" align="left">
															特别会员:&nbsp;
															<label>
																<span class="right_03zi"> <select
																		name="${GNNT_}s_memberNo[like]" size="1"
																		id="specialMemberName" style="width: 100">
																		<option value="">
																			请选择
																		</option>
																		<c:forEach items="${specialMemberList}" var="list">
																			<option value="${list.s_memberNo }">
																				${list.name }
																			</option>
																		</c:forEach>
																	</select> </span>
															</label>
														</td>
														<td class="table3_td_anniu" align="left">
															<button class="btn_sec" onClick="search1()">
																查询
															</button>&nbsp;&nbsp;
															<button class="btn_cz" onClick="myReset()">
																重置
															</button>
														</td>
														<script type="text/javascript">
frm.specialMemberName.value = '${oldParams["s_memberNo[like]"] }';
</script>
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
											autoIncludeParameters="${empty param.autoInc}"
											var="specialMember"
											action="${basePath}/query/querySpecialMemberSearch/list.action"
											title="" minHeight="345" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="导出列表.csv"   style="table-layout:fixed">
											<ec:row ondblclick="">
												<ec:column width="4%" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												
												<ec:column property="tradeDate[=][date]" title="交易时间"
													width="15%" style="text-align:left; "
													value="${datefn:formatdate(specialMember.tradeDate)}" />
												<ec:column property="s_memberNo[like]" title="特别会员账号"
													width="12%" style="text-align:left; "
													value="${specialMember.s_memberNo}" />
												<ec:column property="specialMemberName[like]" title="特别会员名称"
													width="12%" style="text-align:left; "
													value="${specialMember.specialMemberName}" />
												<ec:column property="riskMargin[=][double]" title="风险保证金"
													width="12%" style="text-align:right; ">
													<fmt:formatNumber value="${specialMember.riskMargin}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="specialLossPeak[=][double]" title="特别会员亏损"
													width="12%" style="text-align:right; ">
													<fmt:formatNumber value="${specialMember.specialLossPeak}"
														pattern="#,##0.00" />
												</ec:column>

												<ec:column property="specialLossPeakPro[[=][double]" title="盈亏比例"
													width="14%" style="text-align:right; ">
													<fmt:formatNumber
														value="${specialMember.specialLossPeakPro}" pattern="0.00" />
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
		</div>
		<!-- 编辑和过滤所使用的 通用的文本框模板 -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value=""
				onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="" />
		</textarea>
	</body>
</html>

<SCRIPT type="text/javascript">
	function search1(){
		checkTotalQueryDate(frm.beginDate.value,frm.endDate.value);
	}
</SCRIPT>