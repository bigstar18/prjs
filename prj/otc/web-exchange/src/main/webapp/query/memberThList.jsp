
<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@page import="java.util.*"%>
<html xmlns:MEBS>
	<head>
		<title>�ۺϻ�Ա��ֵ��ѯ</title>
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
								action="${basePath}/query/queryMemberThSearch/list.action?sortName=primary.name&sortOrder=true"
								method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table2_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0" style="table-layout: fixed">
													<tr>
														<td class="table3_td_1" align="left">
															��ѯ��Χ:&nbsp;
															<label>
																<select name="queryType" size="1" id="queryType"
																	style="width: 100" onchange="changeOn()">
																	<option value="D">
																		��ǰ
																	</option>
																	<option value="H">
																		��ʷ
																	</option>
																</select>
															</label>
														</td>
														<td class="table3_td_1tjcx" align="left">
															�ۺϻ�Ա���:&nbsp;
															<label>
																<input type="text" name="${GNNT_}memberNo[like]"
																	id="memberNo" value="${oldParams['memberNo[like]'] }"
																	class="input_textmin" />
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
													</tr>
													<tr>
														<td class="table3_td_1" align="left">
															��ʼ����:&nbsp;
															<label>
																<input type="text" style="width: 100px" id="beginDate"
																	class="wdate" maxlength="10"
																	name="${GNNT_}clearDate[>=][date]"
																	value='${oldParams["clearDate[>=][date]"]}'
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
															</label>
														</td>
														<td class="table3_td_1" align="left">
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��������:&nbsp;
															<label>
																<input type="text" style="width: 100px" id="endDate"
																	class="wdate" maxlength="10"
																	name="${GNNT_}clearDate[<=][date]"
																	value='${oldParams["clearDate[<=][date]"]}'
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
															</label>
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
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%">
								<tr>
									<td>
										<ec:table items="resultList"
											autoIncludeParameters="${empty param.autoInc}" var="memberTh"
											action="${basePath}/query/queryMemberThSearch/list.action"
											title="" minHeight="345" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="�����б�.csv" style="table-layout:fixed">
											<ec:row ondblclick="">
												<ec:column width="4%" property="_0" title="���"
													value="${GLOBALROWCOUNT}" sortable="false"
													filterable="false" />
												<c:if test="${queryType=='H'}">
													<ec:column property="clearDate[=][date]" title="��������"
														width="12%" style="text-align:left; "
														value="${datefn:formatdate(memberTh.clearDate)}"
														ellipsis="true" />
												</c:if>
												<ec:column property="memberNo[like]" title="�ۺϻ�Ա���" width="8%"
													style="text-align:left; " value="${memberTh.memberNo}"
													ellipsis="true" />
												<ec:column property="name[like]" title="�ۺϻ�Ա����" width="8%"
													style="text-align:left; " value="${memberTh.name}"
													ellipsis="true" />
												<ec:column property="memberMarginTh[=][double]"
													title="�ۺϻ�Ա�ʽ�Ԥ����ֵ" width="12%" style="text-align:right; ">
													<fmt:formatNumber value="${memberTh.memberMarginTh}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="customerMarginTh[=][double]"
													title="�ͻ��ʽ�Ԥ����ֵ" width="12%" style="text-align:right; ">
													<fmt:formatNumber value="${memberTh.customerMarginTh}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="memberFreezeTh[=][double]"
													title="�ۺϻ�Ա������ֵ����" width="12%" style="text-align:right; ">
													<fmt:formatNumber value="${memberTh.memberFreezeTh_v}"
														pattern="0.00" />%
												</ec:column>
												<ec:column property="memberNetHoldTh[=][double]"
													title="�ۺϻ�Ա������ֵ����" width="12%" style="text-align:right; ">
													<fmt:formatNumber value="${memberTh.memberNetHoldTh_v}"
														pattern="0.00" />%
												</ec:column>
												<ec:column property="traderate[=][double]" title="�ۺϻ�Ա�ǿͻ�ͷ�罻�ױ���"
													width="10%" style="text-align:right; " >
													<fmt:formatNumber value="${memberTh.traderate_v}"
														pattern="0.00" />%
												</ec:column>
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
		<textarea id="esc_statusType" rows="" cols="" style="display: none">
		<select onBlur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="state[=]">
			<ec:options items="statusMap" />
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
			checkTotalQueryDate(frm.beginDate.value,frm.endDate.value);
			
			
		}
		function init(queryType){
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
			 // ec.action="${basePath}/query/queryMemberThSearch/list.action?queryType=D&sortName=memberNo";
			//frm.action="${basePath}/query/queryMemberThSearch/list.action?queryType=D&sortName=memberNo";
			}
			else if(value=='H')
			{
			   frm.beginDate.disabled=false;
			   frm.endDate.disabled=false;
			   
frm.beginDate.style.backgroundColor="#ffffff";
frm.endDate.style.backgroundColor="#ffffff";
			   frm.queryType.value='H';
			 //   ec.action="${basePath}/query/queryMemberThSearch/list.action?queryType=H&sortName=memberNo";
			//	frm.action="${basePath}/query/queryMemberThSearch/list.action?queryType=H&sortName=memberNo";
			}
		}
function clickText() {
	var memberIds = frm.memberIds.value;
	var url = "${basePath}/report/customer/memberInfoList.action?oldMemberIds="
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