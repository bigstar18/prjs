
<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@page import="java.util.*"%>
<html xmlns:MEBS>
	<head>
		<title>�ر��Ա�ʽ���ˮ��ѯ</title>
		<%@ include file="/public/ecsideLoad.jsp"%>
		<IMPORT namespace="MEBS"
			implementation="${basePath}/common/jslib/calendar.htc">
	</head>
	<body onload="init('${queryType}')">
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>
						<div class="div_tj">
							<form name="frm"
								action="${basePath}/query/querySpecialMemberFundFlowSearch/list.action?sortName=primary.fundFlowId&sortOrder=true"
								method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table2_td_widthmax1">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0" style="table-layout: fixed">
													<tr>
														<td class="table3_td_1tjcx" align="left">
															��ѯ��Χ:&nbsp;
															<label>
																<select name="queryType" size="1" id="queryType"
																	style="width:100" onchange="changeOn()">
																	<option value="D">
																		��ǰ
																	</option>
																	<option value="H">
																		��ʷ
																	</option>
																</select>
															</label>
														</td>
														<td class="table3_td_1tjcxmid" align="left">
															�ر��Ա���:
															<label>
																<input type="text" name="${GNNT_}s_memberNo[=][String]"
																	id="memberName" value="${oldParams['s_memberNo[=][String]'] }"
																	class="input_text" />
															</label>
														</td>
														<td class="table3_td_1tjcx" align="left">
															&nbsp;�ر��Ա����:
															<input type="hidden" name="${ORIGINAL_}smemberIds"
																id="memberIds" value="${original_smemberIds}"
																class="input_text">
															<input type="text" id="memberNames"
																name="${ORIGINAL_}memberNames"
																value="${original_memberNames}" onclick="clickText()"
																readonly=true size="8" class="input_textmin">
														</td>
														<td class="table3_td_1tjcx" align="left">
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��ˮ��:&nbsp;
															<label>
																<input type="text" name="${GNNT_}primary.fundFlowId[=][int]"
																	id="memberName" value="${oldParams['primary.fundFlowId[=][int]'] }"
																	class="input_textmin" />
															</label>
														</td>
													</tr>
													<tr>
														<td class="table3_td_1tjcx" align="left">
															��ʼ����:&nbsp;
															<label>
																<input type="text" style="width: 100px" id="beginDate"
																	class="wdate" maxlength="10"
																	name="${GNNT_}clearDate[>=][date]"
																	value='${oldParams["clearDate[>=][date]"]}'
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
															</label>
														</td>
														<td class="table3_td_1tjcxmid" align="left">
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��������:
															<label>
																<input type="text" style="width: 120px" id="endDate"
																	class="wdate" maxlength="10"
																	name="${GNNT_}clearDate[<=][date]"
																	value='${oldParams["clearDate[<=][date]"]}'
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
															</label>
														</td>
														<td class="table3_td_1tjcx" align="left">
															&nbsp;<button class="btn_sec" onClick="search1()">
																��ѯ
															</button>
															&nbsp;&nbsp;
															<button class="btn_cz" onClick="myReset()">
																����
															</button>
														</td>
													</tr>
													<tr>
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
										<ec:table items="resultList"
											autoIncludeParameters="${empty param.autoInc}"
											var="specialMemberFundFlow"
											action="${basePath}/query/querySpecialMemberFundFlowSearch/list.action?sortName=primary.fundFlowId&sortOrder=true"
											title="" minHeight="345" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="�����б�.csv" style="table-layout:fixed">
											<ec:row ondblclick="">
												<ec:column width="4%" property="_0" title="���" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												<c:if test="${queryType=='H'}">
													<ec:column property="clearDate[=][date]" title="��������"
														width="10%" style="text-align:left; "
														ellipsis="true">
														<fmt:formatDate value="${specialMemberFundFlow.clearDate}"
															pattern="yyyy-MM-dd" />
													</ec:column>
												</c:if>
												<ec:column property="s_memberNo[like]" title="�ر��Ա���"
													width="9%" style="text-align:left; "
													value="${specialMemberFundFlow.s_memberNo}"
													ellipsis="true" />
												<ec:column property="s_name[like]" title="�ر��Ա����" width="10%"
													style="text-align:left; "
													value="${specialMemberFundFlow.s_name}" ellipsis="true" />
												<ec:column property="fundFlowId[=][int]" title="��ˮ��"
													width="8%" style="text-align:left; "
													value="${specialMemberFundFlow.fundFlowId}" ellipsis="true"/>
												<ec:column property="oprcode[=][String]" title="ҵ������"
													width="11%" style="text-align:left; "
													editTemplate="esc_codeType" ellipsis="true">
													<c:set var="code">
														<c:out value="${specialMemberFundFlow.oprcode}"></c:out>
													</c:set>
													${firstMap[code]}
												</ec:column>
												<ec:column property="createTime[=][date]" title="����ʱ��"
													width="12%" style="text-align:left;"
													value="${datefn:formatdate(specialMemberFundFlow.createTime)}" ellipsis="true"/>
												<ec:column property="amount[=][double]" title="�䶯���"
													width="11%" style="text-align:right; ">
													<fmt:formatNumber value="${specialMemberFundFlow.amount}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="balance[=][double]" title="�����"
													width="12%" style="text-align:right; ">
													<fmt:formatNumber value="${specialMemberFundFlow.balance}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="voucherno[=][String]" title="��������"
													width="9%" style="text-align:left; "
													value="${specialMemberFundFlow.voucherno}" ellipsis="true" />
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
		<textarea id="esc_codeType" rows="" cols="" style="display: none">
		<select onBlur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="oprcode[=]">
			<ec:options items="firstMap" />
		</select>
	    </textarea>
	</body>
</html>

<SCRIPT type="text/javascript">
		function changeOn(){
			var todayHis=document.getElementById("queryType").value;
			change(todayHis);
		}
		function search1(){
			checkTotalQueryDate(frm.beginDate.value,frm.endDate.value,frm.queryType.value);
			changeOn();
		}
		 function init(queryType){
			 if(frm.beginDate.value=="" && frm.endDate.value==""){
				 frm.beginDate.value='${date}';
				 frm.endDate.value='${date}';
			 }
			 document.getElementById("beginDate").disabled=true;
			document.getElementById("endDate").disabled=true;
			change(queryType);
		}
		function change(value){
			if(value=='D')
			{
			  frm.beginDate.disabled=true;
			  frm.endDate.disabled=true;
			  frm.beginDate.style.backgroundColor="#d0d0d0";
			  frm.endDate.style.backgroundColor="#d0d0d0";
			   frm.queryType.value='D';
			 // ec.action="${basePath}/query/queryCustomerFundFlowSearch/list.action?queryType=D&sortName=primary.customer_tradeId";
			  //frm.action="${basePath}/query/queryCustomerFundFlowSearch/list.action?queryType=D&sortName=primary.customer_tradeId";
			}
			else if(value=='H')
			{
			   frm.beginDate.disabled=false;
			   frm.endDate.disabled=false;
			   frm.beginDate.style.backgroundColor="#ffffff";
			  frm.endDate.style.backgroundColor="#ffffff";
			   frm.queryType.value='H';
			  //  ec.action="${basePath}/query/queryCustomerFundFlowSearch/list.action?queryType=H&sortName=primary.customer_tradeId";
			  //  frm.action="${basePath}/query/queryCustomerFundFlowSearch/list.action?queryType=H&sortName=primary.customer_tradeId";
			}
		}
function clickText() {
	var memberIds = frm.memberIds.value;
	var url = "${basePath}/report/specialMember/specialMemberList.action?original_oldMemberIds="
			+ memberIds;
	var result = window.showModalDialog(url, '',
			"dialogWidth=350px;dialogHeight=520px");
	if (result != null && result != '') {
		var result1 = result.split('####');
		frm.memberIds.value = result1[0];
		frm.memberNames.value = result1[1];
	}
}
		</SCRIPT>