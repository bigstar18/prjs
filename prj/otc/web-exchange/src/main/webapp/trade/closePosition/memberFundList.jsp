
<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@page import="java.util.*"%>
<html xmlns:MEBS>
	<head>
		<title>�ۺϻ�Ա�ʽ��ѯ</title>
		<%@ include file="/public/ecsideLoad.jsp"%>
		<IMPORT namespace="MEBS"
			implementation="${basePath}/common/jslib/calendar.htc">
	</head>
	<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>
						<div class="div_tj">
							<form name="frm"
								action="${basePath}/memberClosePosition/memberList/list.action?sortName=primary.memberNo"
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
															�ۺϻ�Ա���:
															<label>
																<input type="text" class="input_textmin" id="memberNo"
																	name="${GNNT_}memberNo[like]" size="14"
																	value="${oldParams['memberNo[like]'] }" />
															</label>
														</td>
															<td class="table3_td_1tjcx" align="left">
															�ۺϻ�Ա����:&nbsp;
															<input type="text" id="memberNames"
																name="${ORIGINAL_}memberNames"
																value="${original_memberNames}" onclick="clickText()"
																readonly=true size="8" class="input_textmin">
															<input type="hidden" name="${ORIGINAL_}memberIds"
																id="memberIds" value="${original_memberIds}"
																class="input_text">
														</td>
													
														<td class="table3_td_anniu" align="left">
															<button class="btn_sec" onClick="search1()">
																��ѯ
															</button>
															&nbsp;&nbsp;
															<button class="btn_cz" onClick="myReset()">
																����
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
						<div class="div_gn">
							<button class="anniu_btnmax" onclick="closePosition()" id="update">
								�ۺϻ�Աǿƽ
							</button>
							<button class="anniu_btn" onclick="refreshHandle()" >
								�ֶ�ˢ��
							</button>
						</div>
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%">
								<tr>
									<td>
										<ec:table items="resultList"
											autoIncludeParameters="${empty param.autoInc}"
											var="memberFund"
											action="${basePath}/memberClosePosition/memberList/list.action?sortName=primary.memberNo"
											title="" minHeight="345" listWidth="140%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="�����б�.csv" 
											style="table-layout:fixed">
											<ec:row ondblclick="">
												<ec:column width="3%" property="_0" title="���" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												<ec:column property="memberNo[like]" title="�ۺϻ�Ա���"
													width="6%" style="text-align:left; "
													value="${memberFund.memberNo}"  ellipsis="true"/>
												<ec:column property="memberName[like]" title="�ۺϻ�Ա����"
													width="6%" style="text-align:left; "
													value="${memberFund.memberName}"  ellipsis="true"/>
												<ec:column property="beginningCaptical[=][double]" title="�ڳ�Ȩ��"
													width="6%" style="text-align:right; ">
													<fmt:formatNumber value="${memberFund.beginningCaptical}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="runtimeFundio[=][double]" title="�����"
													width="6%" style="text-align:right; ">
													<fmt:formatNumber value="${memberFund.runtimeFundio}" 
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="runtimeClosepl[=][double]" title="ƽ��ӯ��"
													width="6%" style="text-align:right; ">
													<fmt:formatNumber value="${memberFund.runtimeClosepl}" 
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="floatingLoss[=][double]" title="�ֲ�ӯ��"
													width="6%" style="text-align:right; ">
													<fmt:formatNumber value="${memberFund.floatingLoss}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="runtimeFee[=][double]" title="������"
													width="6%" style="text-align:right; ">
													<fmt:formatNumber value="${memberFund.runtimeFee}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="presentCaptical[=][double]" title="��ǰȨ��"
													width="6%" style="text-align:right; ">
													<fmt:formatNumber value="${memberFund.presentCaptical}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="riskRate_log[=][String]" title="������" width="4%"
													style="text-align:right;" value="${memberFund.riskRate_log}"  sortable="false" filterable="false" />
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
		<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value=""
				onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="" />
		</textarea>
		<!-- �༭�͹�����ʹ�õĲ���ģ�� -->
		<textarea id="esc_fundFlowType" rows="" cols="" style="display: none">
		<select onBlur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="fundFlowType[=]">
			<ec:options items="fundFlowMap" />
		</select>
	    </textarea>
	</body>
</html>

<SCRIPT type="text/javascript">
function search1(){
	frm.submit();
}
function init(queryType){
	document.getElementById("beginDate").disabled=true;
	document.getElementById("endDate").disabled=true;
	change(queryType);
}

function clickText() {
	var memberIds = frm.memberIds.value;
	var url = "${basePath}/report/customer/memberInfoList.action?oldMemberIds=" + memberIds;
	var result = window.showModalDialog(url, '', "dialogWidth=350px;dialogHeight=520px");
	if (result != null && result != '') {
		var result1 = result.split('####');
		frm.memberIds.value = result1[0];
		frm.memberNames.value = result1[1];
	}
}
function closePosition(){
	var url = '${basePath}/memberClosePosition/memberList/forwardUpdate.action';
	ecsideDialog(url,null,600,500);
}
function refreshHandle(){
	ec.submit();	
}
</SCRIPT>