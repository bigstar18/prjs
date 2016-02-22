<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/public/session.jsp"%>
<html xmlns:MEBS>
	<head>
		<title>居间签订客户明细</title>
		<link href="${basePath }/report/report_css.css" rel="stylesheet" type="text/css" />
		<import namespace="MEBS"
			implementation="${basePath}/report/public/calendar.htc">
	</head>

	<body class="report_body" onload="init();">
		<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
			<tr height="100%">
				<td>
					<table width="98%" height="100%" border="0" align="center" cellpadding="0"
						cellspacing="3">
						<tr height="12%">
							<td>
								<div class="report_bor01">
									<form
										action="${basePath}/report/brokerageCustomer/brokerageCustomerReportQuary.action"
										name="frm" id="frm" method="post" target="report">
										<table border="0" class="table2_td_widthmax" cellspacing="0" cellpadding="0">
											
											<tr class="report_w12h">
												<td align="left" class="table3_td_1tjcxmid">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												 交易账号：
													<input type="text" id="customerNo"
														name="${GNNT_}customerno[=][String]"
														value="${oldParams['customerno[=][String]'] }" size="14"
														class="input_textmin" />
												</td>
												<td align="left" class="table3_td_1tjcxmid">&nbsp;&nbsp;&nbsp;
												客户名称： 
													<input type="text" id="name"
														name="${GNNT_}primary.customername[like]"
														value="${oldParams['primary.customername[like]'] }" size="14"
														class="input_textmin" />
												</td>
											</tr>
											<tr>
											<td  class="table3_td_1tjcxmid" align="left">&nbsp;&nbsp;签约开始日期：
													<input type="text" style="width: 100px" id="startDate"
															class="wdate" maxlength="10"
															name="${GNNT_}primary.opentime[>=][date]"
															value='${oldParams["primary.opentime[>=][date]"]}'
															onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
												</td>
												<td  class="table3_td_1tjcxmid" align="left">
													签约结束日期：
													<input type="text" style="width: 100px" id="endDate"
															class="wdate" maxlength="10"
															name="${GNNT_}tprimary.opentime[<=][date]"
															value='${oldParams["primary.opentime[<=][date]"]}'
															onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
												</td>
												<td class="table3_td_1tjcxmid" align="left">
													<input type="button" class="button_02" onclick="select1()"
														value="查询" />&nbsp;
													<input type="button" class="button_03"
														onclick="myReset()" value="重置" />&nbsp;
													<input type="button" class="button_02"
													   value="导出" onclick="xls()"/>
													<input type="hidden" id="type" name="type">
												</td>
											</tr>
										</table>
									</form>
								</div>
							</td>
						</tr>
						<tr height="88%">
							<td class="report_bor01">
								<iframe name="report"
									 frameborder="0" src="${basePath}/report/noDateReport.jsp"
									scrolling="auto" width="100%" height="100%"></iframe>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		</form>
	</body>
</html>
<script type="text/javascript">
	function select1(){
		var action=frm.action;
		frm.action="${basePath}/report/brokerageCustomer/brokerageCustomerReport.action";
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
		frm.startDate.value = '${date}';
		frm.endDate.value = ' ${date}';
	}

</script>