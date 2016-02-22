<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/public/session.jsp"%>
<html xmlns:MEBS>
	<head>
		<title>客户报表</title>
		<link href="${basePath }/report/report_css.css" rel="stylesheet"
			type="text/css" />
		<import namespace="MEBS"
			implementation="${basePath}/report/public/calendar.htc">
	</head>
	<body class="report_body" onload="init();">
		<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
			<tr height="100%">
				<td>
					<table width="100%" height="100%" border="0" align="center" cellpadding="0"
						cellspacing="3">
						<tr height="20%">
							<td>
								<div class="report_bor01">
									<form
										action="${basePath}/report/queryReport/customerReportQuery.action?LOGINID=${LOGINID}&username=${username}"
										name="frm" id="frm" method="post" target="report">
										<table width="820" border="0" cellspacing="0" cellpadding="0" style="padding-left:15px;">
											<tr>
												<td colspan="6">
													&nbsp;
												</td></tr>
												<tr>
													<td align="right">
														开始日期：
													</td>
													<td align="left">
														<input type="text" style="width: 100px" id="startDate"
															class="wdate" maxlength="10"
															name="${GNNT_}primary.clearDate[>=][date]"
															value='${oldParams["primary.clearDate[>=][date]"]}'
															onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
													</td>
													<td align="right">
														结束日期：
													</td>
													<td>
														<input type="text" style="width: 100px" id="endDate"
															class="wdate" maxlength="10"
															name="${GNNT_}primary.clearDate[<=][date]"
															value='${oldParams["primary.clearDate[<=][date]"]}'
															onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
													</td>
													
													<td width="24%">
														&nbsp;

													</td>
												</tr>
												<tr>
												<td colspan="6">
													&nbsp;
												</td></tr>	
												<tr class="report_w12h">
													
													<td width="40%" colspan="4" valign="top">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        
        <td width="2%" align="left" valign="bottom" class="report_showbgmid"><img src="../images/report_bg05tl.gif" width="11" height="15" /></td>
        <td class="report_showbgmid"><div class="report_showtitle">显示选项</div></td>
        <td width="2%" align="right" valign="bottom" class="report_showbgmid"><img src="../images/report_bg05tr.gif" width="11" height="15" /></td>
      </tr>
      <tr>
        <td colspan="3" class="report_showbor">
        	<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
  <td width="5%">&nbsp;</td>
    <td align="left" class="report_w12h"><input type="checkbox" name="tableList" value="closePositionReport" checked="checked" />
																				平仓明细
    <input type="checkbox" name="tableList" value="tradeDetailReport" checked="checked" />
																				成交明细
	<input type="checkbox" name="tableList" value="holdPositionReport"  checked="checked"/>
																				持仓明细
    <input type="checkbox" name="tableList" value="capitalFlowingReport" checked="checked" />
																				资金流水
	 <input type="checkbox" name="tableList" value="capitalAccountReport" checked="checked"/>
																				资金状况</td>
    <td width="5%">&nbsp;</td>
  </tr>
  
</table>        </td>
        </tr>
      <tr>
        <td align="left" valign="bottom" class="report_showborbot"><img src="../images/report_bg05bl.gif" width="11" height="8" /></td>
        <td class="report_showborbot"></td>
        <td align="right" valign="bottom" class="report_showborbot"><img src="../images/report_bg05br.gif" width="11" height="8" /></td>
      </tr>
    </table>
												</td>
													
													<td align="center">
													<button class="btn_anniu" onclick="select1()">
														查询
													</button>
													<button class="btn_anniu" onclick="myReset()">
														重置
													</button>
													<button class="btn_anniu" onclick="xls()">
														导出
													</button>
														<input type="hidden" id="type" name="type">
													</td>
												</tr>
											<tr>
												<td colspan="6">
													&nbsp;
												</td></tr>	
										</table>
									</form>
								</div>
							</td>
						</tr>
						<tr height="80%">
							<td class="report_bor01">
								<iframe name="report"
									src="${basePath }/report/noDateReport.jsp" frameborder="0"
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

function change() {
	var check1 = document.getElementById('isData');
	if (check1.checked) {
		check1.value = "'N'";
	} else {
		check1.value = "'Y'";
	}
}
function select1() {
	var action = frm.action;
	frm.action = "${basePath}/report/queryReport/customerReport.action?LOGINID=${LOGINID}&username=${username}";
	checkQueryDate(frm.startDate.value, frm.endDate.value);
	frm.action = action;
}
function xls() {
	frm.type.value = "xls";
	select1();
	frm.type.value = "";
}
function getDate() {
	var date = new Date();
	var thisYear = date.getYear();
	var thisMonth = date.getMonth() + 1;
	if (thisMonth < 10) {
		thisMonth = "0" + thisMonth;
	}
	var thisDay = date.getDate();
	if (thisDay < 10) {
		thisDay = "0" + thisDay;
	}
	return thisYear + "-" + thisMonth + "-" + thisDay;
}
function init() {
	frm.startDate.value = '${date}';
	frm.endDate.value = '${date}';
}
function clickText() {
	var memberIds = frm.memberIds.value;
	var url = "${basePath}/report/customer/memberInfoList.action?LOGINID=${LOGINID}&username=${username}&oldMemberIds="
			+ memberIds;
	var result = window.showModalDialog(url);
	if (result != null && result != '') {
		var result1 = result.split('####');
		frm.memberIds.value = result1[0];
		frm.memberNames.value = result1[1];
	}
}
</script>
<%@ include file="/public/footInc.jsp"%>