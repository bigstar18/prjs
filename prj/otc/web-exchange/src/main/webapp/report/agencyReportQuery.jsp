<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/public/session.jsp"%>
<html xmlns:MEBS>
	<head>
		<title>��������</title>
		<import namespace="MEBS"
			implementation="${basePath}/report/public/calendar.htc">
		<link href="${basePath }/report/report_css.css" rel="stylesheet"
			type="text/css" />
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
									&nbsp;&nbsp;��ѯ����
								</div>
								<div class="report_bor01">
									<form action="${basePath}/report/agency/agencyReport.action"
										name="frm" id="frm" method="post" target="report">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<td colspan="6">
													&nbsp;
												</td>
												<tr>
													<td width="15%">
														&nbsp;
													</td>
													<td width="15%">
														&nbsp;
													</td>
													<td width="10%">
														&nbsp;
													</td>
													<td width="10%">
														&nbsp;
													</td>
													<td width="30%" rowspan="5" valign="top">
														<table width="100%" border="0" cellspacing="0"
															cellpadding="0">
															<tr>
																<td width="2%" align="left" valign="bottom"
																	class="report_showbgmid">
																</td>
																<td class="report_showbgmid">
																	<div class="report_showtitle">
																		��ʾѡ��
																	</div>
																</td>
																<td width="2%" align="right" valign="bottom"
																	class="report_showbgmid">
																</td>
															</tr>
															<tr>
																<td colspan="3" class="report_showbor">
																	<table width="100%" border="0" cellspacing="0"
																		cellpadding="0">
																		<tr>
																			<td width="10%">
																				&nbsp;
																			</td>
																			<td width="40%" align="left" class="report_w12h">
																				<input type="checkbox" name="tableList"
																					value="closePositionReport" checked="checked" />
																				ƽ�ֵ�
																			</td>
																			<td width="45%" align="left" class="report_w12h">
																				<input type="checkbox" name="tableList"
																					value="capitalAccountReport" />
																				�ʽ��˻�
																			</td>
																			<td width=5%">
																				&nbsp;
																			</td>
																		</tr>
																		<tr>
																			<td>
																				&nbsp;
																			</td>
																			<td align="left" class="report_w12h">
																				<input type="checkbox" name="tableList"
																					value="remarkReport" />
																				��ע
																			</td>
																			<td align="left" class="report_w12h">
																				<input type="checkbox" name="tableList"
																					value="notClosePositionReport" checked="checked" />
																				δƽ�ֵ�
																			</td>
																			<td>
																				&nbsp;
																			</td>
																		</tr>
																		<tr>
																			<td>
																				&nbsp;
																			</td>
																			<td align="left" class="report_w12h">
																				<input type="checkbox" name="tableList"
																					value="demurrageReport" checked="checked" />
																				���ڷ�
																			</td>
																			<td align="left" class="report_w12h">
																				<input type="checkbox" name="tableList"
																					value="capitalFlowingReport" />
																				�ʽ���ˮ
																			</td>
																			<td>
																				&nbsp;
																			</td>
																		</tr>
																		<tr>
																			<td>
																				&nbsp;
																			</td>
																			<td align="left" class="report_w12h">
																				<input type="checkbox" id="isData"
																					name="${GNNT_}primary.isData[=][String]" value=""
																					onclick="change()" />
																				��ʾ�޵��˻�
																			</td>
																			<td>
																				<input type="checkbox" name="tableList"
																					value="holdPositionReport" checked="checked" />
																				�ֲֵ�
																			</td>
																		</tr>
																	</table>
																</td>
															</tr>
															<tr>
																<td align="left" valign="bottom"
																	class="report_showborbot">
																</td>
																<td class="report_showborbot"></td>
																<td align="right" valign="bottom"
																	class="report_showborbot">
																</td>
															</tr>
														</table>
													</td>
													<td>
														&nbsp;
													</td>
												</tr>
												<tr class="report_w12h">
													<td align="right">
														������
													</td>
													<td align="left">
														<input type="text" id="memberName"
															name="${GNNT_}primary.memberName[like]"
															value="${oldParams['primary.memberName[like]'] }"
															size="14" class="from" />
													</td>
													<td align="right">
														���ͣ�
													</td>
													<td>
														<input type="text" id="memberName"
															name="${GNNT_}primary.memberName[like]"
															value="${oldParams[primary.memberName[like]'] }"
															size="14" class="from" />
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
															name="${GNNT_}trunc(primary.atClearDate)[>=][date]"
															value='${oldParams["trunc(primary.atClearDate)[>=][date]"]}'
															onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
													</td>
													<td align="right">
														�������ڣ�
													</td>
													<td>
														<input type="text" style="width: 100px" id="endDate"
															class="wdate" maxlength="10"
															name="${GNNT_}trunc(primary.atClearDate)[<=][date]"
															value='${oldParams["trunc(primary.atClearDate)[<=][date]"]}'
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

														<button class="button_02" onClick="select1()">
															��ѯ
														</button>
														<input type="button" class="button_03" onclick="myReset()"
															value="����" />
														<input type="button" class="button_02" value="����"
															onclick="xls()" />
														<input type="hidden" id="type" name="type">
													</td>
												</tr>
										</table>
								</div>
							</td>
						</tr>
						<tr>
							<td class="report_bor02">
								<iframe name="report" src="${basePath }/report/memberReport.jsp"
									frameborder="0" scrolling="auto" width="100%" height="340"></iframe>
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
		checkQueryDate(frm.startDate.value,frm.endDate.value);
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
	function change(){
	var check1=document.getElementById('isData');
		if(check1.checked){
			check1.value="'N'";
		}
		else{
			check1.value="'Y'";
		}
	}

//-->
</script>