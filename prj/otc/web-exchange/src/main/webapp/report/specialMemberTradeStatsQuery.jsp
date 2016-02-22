<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/public/session.jsp"%>
<html xmlns:MEBS>
	<head>
		<title>特别会员交易统计表</title>
			<%@ include file="/public/ecsideLoad.jsp"%>
		<link href="${basePath }/report/report_css.css" rel="stylesheet" type="text/css" />
		<import namespace="MEBS"
			implementation="${basePath}/report/public/calendar.htc">
	</head>
<body id="main_body" onload="init();">
  <div id="main_body">
			<table class="table1_style" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>
						<div class="div_tj">
							<form action="${basePath}/report/specialMemberTradeStats/traderStatsReportQuery.action"
										name="frm" id="frm" method="post" target="report">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table2_td_widthmid">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0" style="table-layout:fixed">
													<tr>
														<td class="table3_td_1tjcxmid" align="left">
															特别会员编号：
													<input type="text" id="customerNo"
														name="${GNNT_}s_memberNo[like]"
														value="${oldParams['s_memberNo[like]'] }" size="14"
														class="input_textmin" />
														</td>
														<td class="table3_td_1tjcxmid" align="left">
															 特别会员名称： <input type="text" id="memberNames"
															name="${ORIGINAL_}memberNames"
															value="${original_memberNames}" onclick="clickText()"
															readonly=true size="8" class="input_textmin">
															<input type="hidden" name="${ORIGINAL_}smemberIds"
															id="memberIds" value="${original_smemberIds}"
															class="input_text">
														</td>
														<td class="table3_td_1tjcx_1" align="left">
														商品：<select id="commodityId" name="${GNNT_}primary.commodityId[like]"
														class="input_textmin">
														<option value="">
															请选择
														</option>
														<c:forEach items="${commodityList}" var="commodit">
															<option value="${commodit.id}">
																${commodit.name }
															</option>
														</c:forEach>
													</select>
													<script type="text/javascript">
														frm.commodityId.value='${oldParams['primary.commodityId[like]'] }';
													</script>
														</td>
													</tr>
													<tr>
													<td class="table3_td_1tjcxmid" align="left">
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;开始日期：
													<input type="text" style="width: 100px" id="startDate"
															class="wdate" maxlength="10"
															name="${GNNT_}primary.cleardat)[>=][date]"
															value='${oldParams["primary.clearDate[>=][date]"]}'
															onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
														</td>
														<td class="table3_td_1tjcxmid" align="left">
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;结束日期：
													<input type="text" style="width: 100px" id="endDate"
															class="wdate" maxlength="10"
															name="${GNNT_}primary.cleardate[<=][date]"
															value='${oldParams["primary.clearDate[<=][date]"]}'
															onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
														</td>
														<td class="table3_td_1tjcx_1" align="left">
													<button class="btn_sec" onclick="select1()">
																查询
															</button>&nbsp;
															<button class="btn_cz" onclick="myReset()">
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
										<ec:table items="list"
													autoIncludeParameters="${empty param.autoInc}"
													var="specialMemberTradeStats"
													action="${basePath}/report/specialMemberTradeStats/traderStatsReportQuery.action"
													title="" minHeight="345" listWidth="150%"
													retrieveRowsCallback="limit" sortRowsCallback="limit"
													filterRowsCallback="limit" csvFileName="导出列表.csv"
													style="table-layout:fixed">
													<ec:row ondblclick="">
														<ec:column width="2%" property="_0" title="序号"
															value="${GLOBALROWCOUNT}" sortable="false"
															filterable="false" />
														<ec:column property="clearDate[=][date]" title="结算日期"
															width="5%" style="text-align:left; "
															
															ellipsis="true" >
															<fmt:formatDate value="${specialMemberTradeStats.clearDate}" pattern="yyyy-MM-dd" />
														</ec:column>
														<ec:column property="commodityName[like]" title="商品"
															width="5%" style="text-align:left; "
															value="${specialMemberTradeStats.commodityName}" ellipsis="true" />
														<ec:column property="s_memberNo[like]" title="特别会员编号"
															width="5%" style="text-align:left; "
															value="${specialMemberTradeStats.s_memberNo}" ellipsis="true" />
														<ec:column property="s_memberName[like]" title="特别会员名称"
															width="5%" style="text-align:left; "
															value="${specialMemberTradeStats.s_memberName}" ellipsis="true" />
														<ec:column property="sm_holdqtysum" title="成交量"
															width="5%" 
															style="text-align:right; ">
															<fmt:formatNumber value="${specialMemberTradeStats.sm_holdqtysum}"   pattern="#,##0" />
														</ec:column>
														<ec:column property="sm_holderfundsum" title="成交金额"
															width="5%" style="text-align:right; "
															 filterable="false">
															<fmt:formatNumber value="${specialMemberTradeStats.sm_holderfundsum}"    pattern="#,##0.00" />
														</ec:column>
														<ec:column property="sm_buyqtysum" title="买单"
															width="5%" style="text-align:right; "
															 filterable="false">
															<fmt:formatNumber value="${specialMemberTradeStats.sm_buyqtysum}"    pattern="#,##0" />
														</ec:column>
														<ec:column property="sm_sellqtysum" title="卖单"
															width="5%" style="text-align:right; "
															 filterable="false">
															<fmt:formatNumber value="${specialMemberTradeStats.sm_sellqtysum}"   pattern="#,##0" />
														</ec:column>
														<ec:column property="netqyt" title="净头寸"
															width="5%" style="text-align:right; "
															 filterable="false">
															<fmt:formatNumber value="${specialMemberTradeStats.netqyt}"   pattern="#,##0" />
														</ec:column>
														<ec:column property="delayfee" title="收延期费"
															width="5%" style="text-align:right; "
															 filterable="false">
															<fmt:formatNumber value="${specialMemberTradeStats.delayfee}"   pattern="#,##0.00" />
														</ec:column>
														<ec:column property="closepl" title="平仓交易盈亏"
															width="5%" style="text-align:right; "
															 filterable="false">
															<fmt:formatNumber value="${specialMemberTradeStats.closepl}"   pattern="#,##0.00" />
														</ec:column>
														<ec:column property="holdpl" title="持仓交易盈亏"
															width="5%" style="text-align:right; "
															 filterable="false" >
															<fmt:formatNumber value="${specialMemberTradeStats.holdpl}"   pattern="#,##0.00" />
														</ec:column>
														<ec:column property="endcapital" title="交易盈亏合计"
															width="5%" style="text-align:right; "
															 filterable="false"   
															  format="#,##0.00">
															  <fmt:formatNumber value="${specialMemberTradeStats.closepl+specialMemberTradeStats.holdpl}"  pattern="#,##0.00" />
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
<script type="text/javascript">
	function select1(){
		var action=frm.action;
		frm.action="${basePath}/report/specialMemberTradeStats/traderStatsReportQuery.action";
		checkQueryDate(frm.startDate.value,frm.endDate.value);
		frm.action=action;
	}
	function xls(){
		frm.type.value="xls";
		select1();
		frm.type.value="";
	}
	function getDate() {
		var date = new Date();
		var thisYear = date.getYear();   
		var thisMonth = date.getMonth() + 1;   
		if(thisMonth<10){
			thisMonth = "0" + thisMonth;   
		}
		var thisDay = date.getDate();   
		if(thisDay<10) {
			thisDay = "0" + thisDay;   
		}
		return thisYear + "-" + thisMonth + "-" + thisDay;   
	} 
	function init(){
		frm.startDate.value ='${date}';
		frm.endDate.value ='${date}';
	}
	function clickText(){
		var memberIds = frm.memberIds.value;
		var url="${basePath}/report/specialMember/specialMemberList.action?original_oldMemberIds="+memberIds;
		var result=window.showModalDialog(url,'',"dialogWidth=350px;dialogHeight=520px");
		if(result!=null&&result!=''){
		var result1=result.split('####');
		frm.memberIds.value=result1[0];
		frm.memberNames.value=result1[1];
		}	
		}
</script>