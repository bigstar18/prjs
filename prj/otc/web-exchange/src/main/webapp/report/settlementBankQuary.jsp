<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/public/session.jsp"%>
<%@ include file="/public/ecsideLoad.jsp"%>
<html xmlns:MEBS>
	<head>
		<title>��������ǩԼ��¼��</title>
			
		<link href="${basePath }/report/report_css.css" rel="stylesheet" type="text/css" />
		<import namespace="MEBS"
			implementation="${basePath}/report/public/calendar.htc">
	</head>

	<body class="report_body">
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>
						<div class="div_tj">
							<form name="frm"
								method="post" action="${basePath}/report/settlementBank/settlementBankReportQuary.action?sortName=primary.signtime&sortOrder=true">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table2_td_widthmax">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0" style="table-layout:fixed">
													<tr>
														<td class="table3_td_1" align="left">
															ǩԼ��ʼ���ڣ�&nbsp;
															<input type="text" style="width: 100px" id="startDate1"
															class="wdate" maxlength="10"
															name="${GNNT_}primary.signtime[>=][date]"
															value='${oldParams["primary.signtime[>=][date]"]}'
															onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})"/>
														</td>
														<td class="table3_td_1" align="left">
															ǩԼ�������ڣ�
													<input type="text" style="width: 100px" id="endDate1"
															class="wdate" maxlength="10"
															name="${GNNT_}primary.signtime[<=][date]"
															value='${oldParams["primary.signtime[<=][date]"]}'
															onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">&nbsp;
															
														</td>
														<td class="table3_td_1" align="left">
															��ţ�&nbsp;
															<input type="text" id="firmid"
																name="${GNNT_}primary.firmId[like]"
																value="${oldParams['primary.firmId[like]'] }" size="14"
																class="input_textmin" />
														</td>
														</tr>
														<tr>
														<td class="table3_td_1" align="left">
															��Լ��ʼ���ڣ�
													<input type="text" style="width: 100px" id="startDate2"
															class="wdate" maxlength="10"
															name="${GNNT_}primary.cancletime[>=][date]"
															value='${oldParams["primary.cancletime[>=][date]"]}'
															onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})"/>
														</td>
														<td class="table3_td_1" align="left">
															��Լ�������ڣ�
													<input type="text" style="width: 100px" id="endDate2"
															class="wdate" maxlength="10"
															name="${GNNT_}primary.cancletime[<=][date]"
															value='${oldParams["primary.cancletime[<=][date]"]}'
															onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})"/>
														</td>
														<td class="table3_td_1" align="left">
															���ƣ�
															<input type="text" id="firmid"
																name="${GNNT_}primary.name[like]"
																value="${oldParams['primary.name[like]'] }" size="14"
																class="input_textmin" />
														</td>
														</tr>
													<tr>
														<td class="table3_td_1" align="left">
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�˻����ͣ� 
												 	<span class="right_03zi"><select id="firmtype" name="${GNNT_}firmtype[=][String]" style="width: 100px">
												 		<option value="">ȫ��</option>
												 		<option value="M">�ۺϻ�Ա</option>
												 		<option value="B">���ͻ�Ա</option>
												 		<option value="S">�ر��Ա</option>
												 		<option value="C">���׿ͻ�</option>
												 	</select></span>
															<script type="text/javascript">
																frm.firmtype.value='${oldParams["firmtype[=][String]"] }';
															</script>
														</td>
														<td class="table3_td_1" align="left">
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ǩԼ���У�
												 	<span class="right_03zi"><select id="bank"
																name="${GNNT_}bankId[=][String]"
																class="input_textmin">
																<option value="">
																	��ѡ��
																</option>
																<c:forEach items="${bankList}" var="bank">
																	<option value="${bank.bankId}">
																		${bank.bankName }
																	</option>
																</c:forEach>
															</select></span>
															<script type="text/javascript">
																frm.bank.value='${oldParams["bankId[=][String]"] }';
															</script>
														</td>
														<td class="table3_td_anniu" align="left">
															<input type="button" class="button_02" onclick="select1()"
														value="��ѯ" />&nbsp;
															<input type="button" class="button_03"
																onclick="myReset()" value="����" />&nbsp;
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
											var="settlementBank"
											action="${basePath}/report/settlementBank/settlementBankReportQuary.action?sortName=primary.signtime&sortOrder=true"
											title="" minHeight="345" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="�����б�.csv" 
											style="table-layout:fixed">
											<ec:row ondblclick="">
												<ec:column width="3%" property="_0" title="���" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												
												<ec:column property="firmId[=][String]" title="���"
													width="5%" style="text-align:left; "
													value="${settlementBank.firmId}" ellipsis="true">
												</ec:column>
												
												<ec:column property="contact[like]" title="�����˺�"
													width="6%" style="text-align:left; "
													value="${settlementBank.contact}" ellipsis="true"/>
												<ec:column property="name[like]" title="����"
												    width="6%" style="text-align:left; "
												    value="${settlementBank.name}" ellipsis="true"/>
												<ec:column property="bankname[like]" title="ǩԼ����"
													width="4%" style="text-align:left; "
													value="${settlementBank.bankname}" ellipsis="true"/>
												<ec:column property="signtime[=][date]" title="ǩԼʱ��"
												    width="6%" style="text-align:left; " ellipsis="true">
												    <fmt:formatDate value="${settlementBank.signtime}"    pattern="yyyy-MM-dd HH:mm:ss" />
												 </ec:column>
												 <ec:column property="cancletime[=][date]" title="��Լʱ��" width="6%"
													style="text-align:left; "  ellipsis="true">
													<fmt:formatDate value="${settlementBank.cancletime}"   pattern="yyyy-MM-dd HH:mm:ss" />
												</ec:column>
												<ec:column property="firmtype[=][String]" title="�˻�����"
												    width="3%" style="text-align:left; "
												     ellipsis="true">
													<c:set var="key">
														<c:out value="${settlementBank.firmtype}"></c:out>
													</c:set>
													${bankMap[key]}
													
													</ec:column>
											</ec:row>
										</ec:table>
									</td>
								</tr>
							</table>
						</div>
						<!-- �༭�͹�����ʹ�õĽ�ƽ��ģ�� -->
						<textarea id="esc_bankMap" rows="" cols="" style="display: none">
						<select onBlur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
								name="firmtype[=][String]">
							<ec:options items="bankMap" />
						</select>
					    </textarea>
					    
					    <!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ��-->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value=""
				onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="" />
		</textarea>
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
		frm.action="${basePath}/report/settlementBank/settlementBankReportQuary.action?sortName=primary.signtime&sortOrder=true";
		if(checkReportSDate(frm.startDate1.value,frm.endDate1.value,"ǩԼ��ʼ����","ǩԼ��������")&&checkReportSDate(frm.startDate2.value,frm.endDate2.value,"��Լ��ʼ����","��Լ��������")){
			frm.submit();
		};
		frm.action=action;
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
		alert(startName+"���ܴ��ڵ�ǰ����");
		return false;
	}else if(e!="" &&��e>now){
		alert(endName+"���ܴ��ڵ�ǰ����");
		return false;
	}
	else if(s>e){
		alert(startName+"���ܴ���"+endName);
		return false;
	}else{
		return true;
	}
	
}
</script>