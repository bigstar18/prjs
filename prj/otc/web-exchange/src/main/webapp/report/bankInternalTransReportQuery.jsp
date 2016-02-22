<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/public/session.jsp"%>
<html xmlns:MEBS>
	<head>
		<title>主次银行转账记录表</title>
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
						<tr height="20%">
							<td>
								<div class="report_bor01">
									<form
										action="${basePath}/report/bankInternalTrans/bankInternalTransReportQuary.action"
										name="frm" id="frm" method="post" target="report">
										<table border="0" width="99%" cellpadding="0">
											<tr class="report_w12h">
												<td class="table3_td_1tjcx" align="left">
													&nbsp;转账记录号：
													<input type="text" id="transId"
														name="${GNNT_}transId[like]"
														value="${oldParams['transId[like]'] }" size="14"
														class="input_textmin" />
												</td>
													
												<td class="table3_td_1tjcx" align="left">
													交易商名称：
													<input type="text" id="firmName"
														name="${GNNT_}firmName[like]"
														value="${oldParams['firmName[like]'] }" size="14"
														class="input_textmin" />
													
												</td>
												
												
											</tr>
											<tr class="report_w12h">
												<td align="left" class="table3_td_1">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;银行：
												<input type="text" id="bankName"
														name="${GNNT_}bankName[like]"
														value="${oldParams['bankName[like]'] }" size="14"
														class="input_textmin" />
												</td>
												<td align="left" class="table3_td_1">&nbsp;&nbsp;目标银行：
												<input type="text" id="bankName"
														name="${GNNT_}bankTargetName[like]"
														value="${oldParams['bankTargetName[like]'] }" 
														class="input_textmin" />
												</td>
												
												<td align="left">
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
						<tr height="80%">
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
		frm.action="${basePath}/report/bankInternalTrans/bankInternalTransReport.action";
		frm.submit();
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
		frm.endDate.value = '${date}';
	}
function clickText() {
	var memberIds = frm.memberIds.value;
	var url = "${basePath}/report/customer/memberInfoList.action?oldMemberIds="
			+ memberIds;
	var result = window.showModalDialog(url,'',"dialogWidth=350px;dialogHeight=520px");
	if (result != null && result != '') {
		var result1 = result.split('####');
		frm.memberIds.value = result1[0];
		frm.memberNames.value = result1[1];
	}
}
function checkReportSDate(startDate,endDate,startName,endName) {
	
	
	var now = new Date();
	var   s   =   new   Date(Date.parse(startDate.replace(/-/g,   "/")));
	var   e   =   new   Date(Date.parse(endDate.replace(/-/g,   "/")));
	if (s!="" && s > now ) {
		alert(startName+"不能大于当前日期");
		return false;
	}else if(e!="" &&　e>now){
		alert(endName+"不能大于当前日期");
		return false;
	}
	else if(s>e){
		alert(startName+"不能大于"+endName);
		return false;
	}else{
		return true;
	}
	
}
</script>