
<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@page import="java.util.*"%>
<html xmlns:MEBS>
	<head>
		<title>��Ա�ֲֵ���ѯ</title>
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
								action="${basePath}/query/queryMemberHoldSearch/list.action?sortName=primary.holdNo&sortOrder=true"
								method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table2_td_widthmax">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0" style="table-layout:fixed">
													<tr>
														<td class="table3_td_1tjcx" align="left">
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
															��Ա���:&nbsp;
															<label>
																<input type="text" name="${GNNT_}memberNo[like]"
																	id="memberName" value="${oldParams['memberNo[like]'] }"
																	class="input_text" />
															</label>
														</td>
														<td class="table3_td_1tjcx" align="left">
															��Ʒ����:&nbsp;
															<span class="right_03zi"><select id="commodityId"
																	name="${GNNT_}primary.commodityName[like]"
																	class="input_textmin">
																	<option value="">
																		��ѡ��
																	</option>
																	<c:forEach items="${commodityList}" var="commodit">
																		<option value="${commodit.name}">
																			${commodit.name }
																		</option>
																	</c:forEach>
																</select>
															</span>
															<script type="text/javascript">
frm.commodityId.value = '${oldParams["primary.commodityName[like]"] }';
</script>
														</td>
													</tr>
													<tr>
														<td class="table3_td_1tjcx" align="left">
															��ʼ����:&nbsp;
															<label>
																<input type="text" style="width: 100px" id="beginDate"
																	class="wdate" maxlength="10"
																	name="${GNNT_}atClearDate[>=][date]"
																	value='${oldParams["atClearDate[>=][date]"]}'
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
															</label>
														</td>
														<td class="table3_td_1tjcx" align="left">
															��������:&nbsp;
															<label>
																<input type="text" style="width: 120px" id="endDate"
																	class="wdate" maxlength="10"
																	name="${GNNT_}atClearDate[<=][date]"
																	value='${oldParams["atClearDate[<=][date]"]}'
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
															</label>
														</td>
														<td class="table3_td_1tjcx" align="left">
															&nbsp;&nbsp;&nbsp;����Ա:&nbsp;
															<label>
																<input type="text" name="${GNNT_}traderId[like]"
																	id="traderId" value="${oldParams['traderId[like]'] }"
																	class="input_textmin" />
															</label>
														</td>
													</tr>
													<tr>
														<td class="table3_td_1tjcx" align="left">
															��������:&nbsp;
															<label>
																<span class="right_03zi"> <select
																		name="${GNNT_}tradeType[=][int]" size="1"
																		id="tradeType" style="width: 100">
																		<option value="" selected="selected">
																			��ѡ��
																		</option>
																		<option value="1">
																			��������
																		</option>
																		<option value="2">
																			�Գ��ǿƽ
																		</option>
																	</select> </span>
															</label>
														</td>
														<script type="text/javascript">
															frm.tradeType.value = '${oldParams["tradeType[=][int]"] }';
														</script>
														<td class="table3_td_1tjcx" align="left">
															����/����:
															<label>
																<span class="right_03zi"> <select
																		name="${GNNT_}bs_Flag[=][int]" size="1" id="flag"
																		value="${oldParams['bs_Flag[=][int]'] }"
																		style="width: 100">
																		<option value="">
																			��ѡ��
																		</option>
																		<option value="1">
																			����
																		</option>
																		<option value="2">
																			����
																		</option>
																	</select> </span>
															</label>
														</td>
														<td class="table3_td_anniutjcx" align="left">
															<button class="btn_sec" onClick="search1()">
																��ѯ
															</button>
															&nbsp;&nbsp;
															<button class="btn_cz" onClick="myReset()">
																����
															</button>
														</td>
													</tr>
													<script type="text/javascript">
														frm.flag.value = '${oldParams["bs_Flag[=][int]"] }';
													</script>
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
											var="memberHoldSearch"
											action="${basePath}/query/queryMemberHoldSearch/list.action"
											title="" minHeight="345" listWidth="190%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="�����б�.csv"  
											style="table-layout:fixed">
											<ec:row ondblclick="">
												<ec:column width="3%" property="_0" title="���" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												<c:if test="${queryType=='H'}">
												<ec:column property="atClearDate[=][date]" title="��������"
													width="6%" style="text-align:left; "
													value="${datefn:formatdate(memberHoldSearch.atClearDate)}" ellipsis="true"/>
												</c:if>
												<ec:column property="holdNo[like]" title="�ֲֵ���" width="5%"
													style="text-align:center; "
													value="${memberHoldSearch.holdNo}" ellipsis="true"/>
												<ec:column property="mem_TradeId[like]" title="�˺�"
													width="6%" style="text-align:left; "
													value="${memberHoldSearch.mem_TradeId}"  ellipsis="true"/>
												<ec:column property="commodityName[like]" title="��Ʒ����"
													width="6%" style="text-align:left; "
													value="${memberHoldSearch.commodityName}" ellipsis="true"/>
												<ec:column property="traderId[like]" title="����Ա" width="5%"
													style="text-align:left; "
													value="${memberHoldSearch.traderId}"  ellipsis="true"/>
												<ec:column property="traderName[like]" title="����Ա����"
													width="6%" style="text-align:left; "
													value="${memberHoldSearch.traderName}"  ellipsis="true"/>
												<ec:column property="tradeType[=][int]" title="��������" width="4%"
													style="text-align:left; " editTemplate="esc_tradeType">
													<c:set var="typeKey">
														<c:out value="${memberHoldSearch.tradeType}"></c:out>
													</c:set>
		  											${tradeTypeMap[typeKey]}
												</ec:column>
												<ec:column property="memberNo[like]" title="��Ա���" width="6%"
													style="text-align:left; "
													value="${memberHoldSearch.memberNo}" ellipsis="true"/>
												<ec:column property="holdQty[=][int]" title="����" width="4%"
													style="text-align:right; "
													value="${memberHoldSearch.holdQty}" />
												<ec:column property="bs_flag[=][int]" title="������־" width="4%"
													style="text-align:left; " editTemplate="esc_bsFlag">
													<c:set var="typeKey">
														<c:out value="${memberHoldSearch.bs_flag}"></c:out>
													</c:set>
		  											${bsFlagMap[typeKey]}
												</ec:column>
												<ec:column property="holdTime[=][date]" title="����ʱ��" width="7%"
													style="text-align:left; "
													value="${datefn:formatdate(memberHoldSearch.holdTime)}" ellipsis="true"/>
												<ec:column property="s_memberNo[like]" title="�ӵ��ر��Ա��"
													width="6%" style="text-align:left; "
													value="${memberHoldSearch.s_memberNo}" ellipsis="true"/>
												<ec:column property="o_firmName[like]" title="�ӵ��ر��Ա����"
													width="6%" style="text-align:left; "
													value="${memberHoldSearch.o_firmName}" ellipsis="true"/>
												<ec:column property="openPrice[=][double]" title="���ּ�"
													width="4%" style="text-align:right; ">
													<fmt:formatNumber value="${memberHoldSearch.openPrice}"
														pattern="0.00" />
												</ec:column>
												<ec:column property="holdPrice[=][double]" title="�ֲּ�"
													width="4%" style="text-align:right; ">
													<fmt:formatNumber value="${memberHoldSearch.holdPrice}"
														pattern="0.00" />
												</ec:column>
												<ec:column property="price[=][double]" title="�м��" width="4%"
													style="text-align:right; ">
													<fmt:formatNumber value="${memberHoldSearch.price}"
														pattern="0.00" />
												</ec:column>
												<ec:column property="floatingLoss[=][double]" title="ӯ��"
													width="4%" style="text-align:right; ">
													<fmt:formatNumber value="${memberHoldSearch.floatingLoss}"
														pattern="0.00" />
												</ec:column>
												<ec:column property="tradeFee[=][double]" title="������"
													width="4%" style="text-align:right; ">
													<fmt:formatNumber value="${memberHoldSearch.tradeFee}"
														pattern="0.00" />
												</ec:column>
												<ec:column property="delayFee[=][double]" title="���ڷ�"
													width="4%" style="text-align:right; ">
													<fmt:formatNumber value="${memberHoldSearch.delayFee}"
														pattern="0.00" />
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
		<textarea id="esc_tradeType" rows="" cols="" style="display: none">
		<select onBlur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="tradeType[like]">
			<ec:options items="tradeTypeMap" />
		</select>
	    </textarea>
		<!-- �༭�͹�����ʹ�õ�������־ģ�� -->
		<textarea id="esc_bsFlag" rows="" cols="" style="display: none">
		<select onBlur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="bs_Flag[like]">
			<ec:options items="bsFlagMap" />
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
			changeOn();
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
			 // ec.action="${basePath}/query/queryMemberHoldSearch/list.action?queryType=D";
			 // frm.action="${basePath}/query/queryMemberHoldSearch/list.action?queryType=D";
			}
			else if(value=='H')
			{
			   frm.beginDate.disabled=false;
			   frm.endDate.disabled=false;
			   frm.beginDate.style.backgroundColor="#ffffff";
frm.endDate.style.backgroundColor="#ffffff";
			   frm.queryType.value='H';
			  //  ec.action="${basePath}/query/queryMemberHoldSearch/list.action?queryType=H";
			  //  frm.action="${basePath}/query/queryMemberHoldSearch/list.action?queryType=H";
			}
		}
		
		</SCRIPT>