<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/public/session.jsp"%>
<html xmlns:MEBS>
	<head>
		<title>�ر��Ա������ֵ�����</title>
		<import namespace="MEBS"
			implementation="${basePath}/report/public/calendar.htc">
		<link href="${basePath }/report/report_css.css" rel="stylesheet"
			type="text/css" />
	</head>
	<body class="report_body" onload="init();">
		<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
			<tr height="100%">
				<td>
					<table width="98%" height="100%" border="0" align="center" cellpadding="0"
						cellspacing="3">
						<tr height="13%">
							<td>
								<div class="report_bor01">
									<form
										action="${basePath}/report/specialMemberFundioThreshold/specialMemberFundioThresholdReportQuary.action"
										name="frm" id="frm" method="post" target="report">
										<table width="820" border="0" class="table2_td_widthmax" cellspacing="0" cellpadding="0">
											
											<tr>
											
												<td align="left" height="40" width="240">&nbsp;&nbsp;
												 �ر��Ա��ţ�
													<input type="text" id="customerNo"
														name="${GNNT_}s_memberNo[=][String]"
														value="${oldParams['s_memberNo[=][String]'] }" size="14"
														class="input_textmin" />
												</td>
												<td align="left" width="240" colspan="2">  
												 �ر��Ա���ƣ�<input type="hidden" name="${ORIGINAL_}smemberIds"
															id="memberIds" value="${original_smemberIds}"
															class="input_text">
														<input type="text" id="memberNames"
															name="${ORIGINAL_}memberNames"
															value="${original_memberNames}" onclick="clickText()"
															readonly=true size="8" class="input_textmin">
												</td>
												
												
											</tr>
											<tr class="report_w12h">
												<td align="left" height="40">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��ʼ���ڣ�
													<input type="text" style="width: 100px" id="startDate"
															class="wdate" maxlength="10"
															name="${GNNT_}trunc(primary.cleardate)[>=][date]"
															value='${oldParams["trunc(primary.clearDate)[>=][date]"]}'
															onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
												</td>
												<td align="left">&nbsp;&nbsp;&nbsp;
													�������ڣ�
													<input type="text" style="width: 100px" id="endDate"
															class="wdate" maxlength="10"
															name="${GNNT_}trunc(primary.cleardate)[<=][date]"
															value='${oldParams["trunc(primary.clearDate)[<=][date]"]}'
															onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
												</td>
												<td align="left">
													<input type="button" class="button_02" onclick="select1()"
														value="��ѯ" />&nbsp;
													<input type="button" class="button_03"
														onclick="myReset()" value="����" />&nbsp;
													<input type="button" class="button_02"
													   value="����" onclick="xls()"/>
													<input type="hidden" id="type" name="type">
												</td>
											</tr>
										</table>
									</form>
								</div>
							</td>
						</tr>
						<tr height="87%">
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
		frm.action="${basePath}/report/specialMemberFundioThreshold/specialMemberFundioThresholdReport.action";
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