<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/public/session.jsp"%>
<html xmlns:MEBS>
	<head>
		<title>���ͱ���</title>
		<import namespace="MEBS"
			implementation="${basePath}/report/public/calendar.htc">
		<link href="${basePath }/report/report_css.css" rel="stylesheet" type="text/css" />
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
								<div class="report_bor01">
									<form 
									    action="${basePath}/report/manager/managerReportQuery.action"
										name="frm" id="frm" method="post" target="report">
										<table width="820" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<td colspan="6">
													&nbsp;
												</td>
											<tr>
												<td width="10%">
													&nbsp;
												</td>
												<td width="12%">
													&nbsp;
												</td>
												<td width="10%">
													&nbsp;
												</td>
												<td width="14%">
													&nbsp;
												</td>
												<td width="28%" rowspan="5" valign="top">
													<table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="2%" align="left" valign="bottom" class="report_showbgmid"><img src="../images/report_bg05tl.gif" width="11" height="15" /></td>
        <td class="report_showbgmid"><div class="report_showtitle">��ʾѡ��</div></td>
        <td width="2%" align="right" valign="bottom" class="report_showbgmid"><img src="../images/report_bg05tr.gif" width="11" height="15" /></td>
      </tr>
      <tr>
        <td colspan="3" class="report_showbor">
        	<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="10%">&nbsp;</td>
    <td width="40%" align="left" class="report_w12h"><input type="checkbox" name="tableList" value="closePositionReport"  />
																			ƽ����ϸ</td>
    <td width="45%" align="left" class="report_w12h"><input type="checkbox" name="tableList" value="tradeDetailReport"  />
																			�ɽ���ϸ</td>
    <td width="5%">&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td align="left" class="report_w12h"><input type="checkbox" name="tableList" value="holdPositionReport" />
																			�ֲ���ϸ</td>
    <td align="left" class="report_w12h"><input type="checkbox" name="tableList" value="capitalFlowingReport"  />
																			�ʽ���ˮ</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td align="left" class="report_w12h"><input type="checkbox"  name="tableList" value="capitalAccountReport" checked="checked" >
																			�ʽ�״��</td>
    <td align="left" class="report_w12h"><input type="hidden" />
																			</td>
    <td>&nbsp;</td>
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
												<td>
													&nbsp;
												</td>
											</tr>
											<tr class="report_w12h">
												<td align="right">
													��Ա��ţ�
												</td>
												<td>
													<input type="text" name="${GNNT_}memberno[=][String]" id="memberNo"
																value="${oldParams['memberno[=][String]'] }" class="input_textmin">
												</td>
												<td align="right">
													��Ա���ƣ�
												</td>
												<td align="left">
													<input type="hidden" name="${ORIGINAL_}memberIds" id="memberIds"
																value="${original_memberIds}" class="input_text">
															<input type="text" id="memberNames" name="${ORIGINAL_}memberNames" value="${original_memberNames}"
																onclick="clickText()" readonly=true size="8" class="input_textmin">
												</td>
												
												<td>
													&nbsp;
												</td>
											</tr>
											<tr class="report_w12h">
												<td align="right">
													��ʼ���ڣ�
												</td>
												<td align="left">
													<input type="text" style="width: 100px" id="startDate"
															class="wdate" maxlength="10"
															name="${GNNT_}trunc(primary.clearDate)[>=][date]"
															value='${oldParams["trunc(primary.clearDate)[>=][date]"]}'
															onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
												</td>
												<td align="right">
													�������ڣ�
												</td>
												<td>
													<input type="text" style="width: 100px" id="endDate"
															class="wdate" maxlength="10"
															name="${GNNT_}trunc(primary.clearDate)[<=][date]"
															value='${oldParams["trunc(primary.clearDate)[<=][date]"]}'
															onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
												</td>
												<td>
													&nbsp;
												</td>
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
												<td align="center">

														<button  class="button_02" onClick="select1()">��ѯ</button>&nbsp;
														<button  class="button_03" onClick="myReset()">����</button>&nbsp;
														<button  class="button_02"
													    onclick="xls()">����</button>
													<input type="hidden" id="type" name="type">
												</td>
											</tr>
										</table>
								</div>
							</td>
						</tr>
						<tr>
							<td class="report_bor01">
								<iframe name="report" src="${basePath }/report/noDateReport.jsp"
									frameborder="0" scrolling="auto" width="100%" height="400"></iframe>
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
	frm.action="${basePath}/report/manager/managerReport.action";
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
function clickText(){
		var memberIds = frm.memberIds.value;
		var url="${basePath}/account/customer/memberList.action?original_oldMemberIds="+memberIds;
		var result=window.showModalDialog(url);
		if(result!=null&&result!=''){
		var result1=result.split('####');
		frm.memberIds.value=result1[0];
		frm.memberNames.value=result1[1];
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
		frm.endDate.value = '${date}';
	}

//-->
</script>