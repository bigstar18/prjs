<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/public/session.jsp"%>
<html xmlns:MEBS>
	<head>
		<title>交易日报表</title>
		<link href="${basePath }/report/report_css.css" rel="stylesheet" type="text/css" />
		<import namespace="MEBS"
			implementation="${basePath}/report/public/calendar.htc">
	</head>

	<body class="report_body" onload="init();">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="2"></td>
			</tr>
			<tr>
				<td>
					<table width="98%" border="0" align="center" cellpadding="0"
						cellspacing="3">
						<tr>
							<td>
								<div class="right_02">
									<img src="../images/13.gif" />
									&nbsp;&nbsp;查询条件
								</div>
								<div class="report_bor01">
									<form
										action="${basePath}/report/exchange/exchangeReport.action"
										name="frm" id="frm" method="post" target="report">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<td>&nbsp;</td>
											</tr>
											<tr>
												<td>&nbsp;</td>
											</tr>
											<tr>
												<td>
													&nbsp;
												</td>
												<td>
													&nbsp;
												</td>
												<td>
													&nbsp;
												</td>
												<td>
													&nbsp;
												</td>
												<td align="right">

													<input type="button" class="button_02" onclick="select1()"
														value="查询" />
													<input type="button" class="button_03"
														onclick="myReset()" value="重置" />
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
						<tr>
							<td class="report_bor01">
								<iframe name="report"
									 frameborder="0"
									scrolling="auto" width="100%" height="300"></iframe>
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
		frm.submit();
	}
	function xls(){
		frm.type.value="xls";
		frm.submit();
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
		frm.startDate.value = getDate();
		frm.endDate.value = getDate();
	}
	
	function clickText(){
		var memberIds = frm.memberIds.value;
		var url="${basePath}/report/customerTrader/memberInfoList.action?oldMemberIds="+memberIds;
		var result=window.showModalDialog(url);
		if(result!=null&&result!=''){
		var result1=result.split('####');
		frm.memberIds.value=result1[0];
		frm.memberNames.value=result1[1];
		}
	}

</script>