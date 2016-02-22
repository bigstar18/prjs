<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/public/session.jsp"%>
<html xmlns:MEBS>
	<head>
		<title>会员报表</title>
		<import namespace="MEBS"
			implementation="${basePath}/report/public/calendar.htc">
		<link href="${basePath }/report/report_css.css" rel="stylesheet"
			type="text/css" />
	</head>
	<body class="report_body" onload="init();">
		<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
			<tr height="100%">
				<td>
					<table width="100%" height="100%" border="0" align="center" cellpadding="0"
						cellspacing="3">
						<tr height="15%">
							<td>
								<div class="report_bor01">
									<form
										action="${basePath}/report/queryReport/memberReportQuery.action?LOGINID=${LOGINID}&username=${username}"
										name="frm" id="frm" method="post" target="report">
										<table width="820" border="0" cellspacing="0" cellpadding="0"
											style="padding-left: 15px;">
											<tr>
												<td colspan="6">
													&nbsp;
												</td>
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
														<td width="14%">
															&nbsp;
														</td>
												</tr>
											<tr class="report_w12h">
											<td width="40%" colspan="4" valign="top">
		<table width="95%" border="0" cellspacing="0" cellpadding="0">
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
	<input type="checkbox" name="tableList" value="holdPositionReport" checked="checked" />
																			持仓明细
    <input type="checkbox" name="tableList" value="capitalFlowingReport" checked="checked" />
																			资金流水
	<input type="checkbox" name="tableList" value="fundsReport" checked="checked" />
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
													<button class="btn_anniu" onClick="select1()">
														查询
													</button>&nbsp;
													<button class="btn_anniu" onClick="myReset()">
														重置
													</button>&nbsp;
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
						<tr height="85%">
							<td class="report_bor01" >
								<iframe name="report" src="${basePath }/report/noDateReport.jsp"
									frameborder="0" scrolling="auto" width="100%" height="100%"></iframe>
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
<!--
function select1(){
	var action=frm.action;
	frm.action="${basePath}/report/queryReport/memberReport.action?LOGINID=${LOGINID}&username=${username}";
	checkQueryDate(frm.startDate.value,frm.endDate.value);
	frm.action=action;
}
function xls(){
		frm.type.value="xls";
		select1();
		frm.type.value="";
	}
function change(){
	var check1=document.getElementById('isData');
		if(check1.checked){
			check1.value="'N'";
		}
		else{
			check1.value="'Y'";
		}
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
		frm.endDate.value ='${date}';
	}

//-->
</script>