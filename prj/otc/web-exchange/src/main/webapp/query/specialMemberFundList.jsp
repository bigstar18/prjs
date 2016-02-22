
<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@page import="java.util.*"%>
<html xmlns:MEBS>
	<head>
		<title>特别会员资金查询</title>
		<%@ include file="/public/ecsideLoad.jsp"%>
		<IMPORT namespace="MEBS"
			implementation="${basePath}/common/jslib/calendar.htc">
	</head>
	<body >
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>
						<div class="div_tj">
							<form name="frm"
								action="${basePath}/query/querySpecialMemberFundSearch/list.action?sortName=primary.s_memberNo"
								method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table2_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0" style="table-layout:fixed">
													<tr>
														
														<td class="table3_td_1tjcx" align="left">
															特别会员编号:
															<label>
																<input type="text" class="input_textmin" id="s_memberNo"
																	name="${GNNT_}s_memberNo[=][String]" size="14"
																	value="${oldParams['s_memberNo[=][String]'] }" />
															</label>
														</td>
														<td class="table3_td_1tjcx" align="left">
															&nbsp;&nbsp; 特别会员名称:
															<input type="hidden" name="${ORIGINAL_}smemberIds"
																id="memberIds" value="${original_smemberIds}"
																class="input_text">
															<input type="text" id="memberNames"
																name="${ORIGINAL_}memberNames"
																value="${original_memberNames}" onclick="clickText()"
																readonly=true size="8" class="input_textmin">
														</td>
													
														<td class="table3_td_anniu" align="left">
															<button class="btn_sec" onClick="search1()">
																查询
															</button>
															&nbsp;&nbsp;
															<button class="btn_cz" onClick="myReset()">
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
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%">
								<tr>
									<td>
										<ec:table items="resultList"
											autoIncludeParameters="${empty param.autoInc}"
											var="specialMemberFund"
											action="${basePath}/query/querySpecialMemberFundSearch/list.action?sortName=primary.s_memberNo"
											title="" minHeight="345" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="导出列表.csv" 
											style="table-layout:fixed">
											<ec:row ondblclick="">
												<ec:column width="3%" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												<ec:column property="s_memberNo[like]" title="特别会员编号"
													width="5%" style="text-align:left; "
													value="${specialMemberFund.s_memberNo}"  ellipsis="true"/>
												<ec:column property="s_memberName[like]" title="特别会员名称"
													width="6%" style="text-align:left; "
													value="${specialMemberFund.s_memberName}"  ellipsis="true"/>
												<ec:column property="beginningCaptical[=][double]" title="期初权益"
													width="7%" style="text-align:right; ">
													<fmt:formatNumber value="${specialMemberFund.beginningCaptical}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="runtimeFundio[=][double]" title="出入金"
													width="6%" style="text-align:right; ">
													<fmt:formatNumber value="${specialMemberFund.runtimeFundio}" 
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="runtimeClosepl[=][double]" title="平仓盈亏"
													width="6%" style="text-align:right; ">
													<fmt:formatNumber value="${specialMemberFund.runtimeClosepl}" 
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="floatingLoss[=][double]" title="持仓盈亏"
													width="6%" style="text-align:right; ">
													<fmt:formatNumber value="${specialMemberFund.floatingLoss}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="presentCaptical[=][double]" title="当前权益"
													width="7%" style="text-align:right; ">
													<fmt:formatNumber value="${specialMemberFund.presentCaptical}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="riskRate_log[=][String]" title="风险率" width="4%"
													style="text-align:right;" value="${specialMemberFund.riskRate_log}" sortable="false" filterable="false" />
											</ec:row>
											<c:if test="${ifHas eq 1}">		
												<ec:extendrow>
													<td>
													          合计:
													</td>
													<td>&nbsp;</td><td>&nbsp;</td>
													<td align="right" style="font-weight:bold">${beginningCapticalAll}</td>
													<td align="right" style="font-weight:bold">${runtimeFundioAll}</td>	
													<td align="right" style="font-weight:bold">${runtimeCloseplAll}</td>	
													<td align="right" style="font-weight:bold">${floatingLossAll}</td>	
													<td align="right" style="font-weight:bold">${presentCapticalAll}</td>
													<td>&nbsp;</td>
												</ec:extendrow>
											</c:if>
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
			frm.submit();
			//checkTotalQueryDate(frm.beginDate.value,frm.endDate.value);
			//changeOn();
		}
		 function init(queryType){
			 document.getElementById("beginDate").disabled=true;
			document.getElementById("endDate").disabled=true;
			change(queryType);
		}
function clickText() {
	var memberIds = frm.memberIds.value;
	var url = "${basePath}/report/specialMember/specialMemberList.action?original_oldMemberIds="
			+ memberIds;
	var result = window.showModalDialog(url, '',
			"dialogWidth=350px;dialogHeight=520px");
	if (result != null && result != '') {
		var result1 = result.split('####');
		frm.memberIds.value = result1[0];
		frm.memberNames.value = result1[1];
	}
}
		</SCRIPT>