
<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@page import="java.util.*"%>
<html xmlns:MEBS>
	<head>
		<title>��Ա�ɽ���ϸ��ѯ</title>
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
								action="${basePath}/query/queryMemberOrdersSearch/list.action?sortName=primary.tradeno&sortOrder=true"
								method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table2_td_widthmid">
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
														<td class="table3_td_1" align="left">
															��Ʒ����:&nbsp;
															<span class="right_03zi"><select id="commodityId"
																	name="${GNNT_}primary.commodityId[=][String]"
																	class="input_textmin">
																	<option value="">
																		��ѡ��
																	</option>
																	<c:forEach items="${commodityList}" var="commodit">
																		<option value="${commodit.id}">
																			${commodit.name }
																		</option>
																	</c:forEach>
																</select> </span>
															<script type="text/javascript">
																frm.commodityId.value = '${oldParams["primary.commodityId[=][String]"] }';
															</script>
														</td>
														<td class="table3_td_1" align="left">
															�ɽ�����:&nbsp;
															<label>
																<input type="text"
																	name="${GNNT_}primary.tradeno[=][int]" id="traderId"
																	value="${oldParams['primary.tradeno[=][int]'] }"
																	class="input_textmin" />
															</label>
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
															��������:&nbsp;
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
											autoIncludeParameters="${empty param.autoInc}"
											var="memberOrder"
											action="${basePath}/query/queryMemberOrdersSearch/list.action?sortName=primary.tradeno&sortOrder=true"
											title="" minHeight="345" listWidth="160%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" csvFileName="�����б�.csv"
											style="table-layout:fixed">
											<ec:row ondblclick="">
												<ec:column width="3%" property="_0" title="���"
													value="${GLOBALROWCOUNT}" sortable="false"
													filterable="false" />
												<c:if test="${queryType=='H'}">
													<ec:column property="clearDate[=][date]" title="��������"
														width="5%" style="text-align:left; "
														ellipsis="true">
														<fmt:formatDate value="${memberOrder.clearDate}" pattern="yyyy-MM-dd"/>
														</ec:column>
												</c:if>
												<ec:column property="oc_Flag[like]" title="��/ƽ��"
												    width="3%" style="text-align:left; "
												    editTemplate="esc_modmap" ellipsis="true">
												    <c:set var="typeKey">
														<c:out value="${memberOrder.oc_Flag}"></c:out>
													</c:set>
		  											${firstMap[typeKey]}
												   </ec:column>
												<ec:column property="tradeno[=][int]" title="�ɽ�����"
													width="4%" style="text-align:left; "
													value="${memberOrder.tradeno}" ellipsis="true" />
												<ec:column property="holdno[=][int]" title="�ֲֵ���"
													width="4%" style="text-align:left; "
													value="${memberOrder.holdno}" ellipsis="true" />
												<ec:column property="tradeTime[=][date]" title="�ɽ�ʱ��"
													width="8%" style="text-align:left; "
													value="${datefn:formatdate(memberOrder.tradeTime)}"
													ellipsis="true" />
												<ec:column property="commodityName[like]" title="��Ʒ"
													width="6%" style="text-align:left; "
													value="${memberOrder.commodityName}" ellipsis="true" />
												<ec:column property="quantity[=][int]" title="�ɽ���" width="3%"
													style="text-align:right; " >
													<fmt:formatNumber value="${memberOrder.quantity}"
														pattern="#,##0" />
												</ec:column>
												<ec:column property="tradefunds[=][double]" title="�ɽ����"
													width="7%" style="text-align:right; " >
													<fmt:formatNumber
														value="${memberOrder.tradefunds}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="bs_flag[=][int]" title="��������"
													width="3%" style="text-align:left; "
													editTemplate="esc_bsFlag" ellipsis="true">
													<c:set var="typeKey">
														<c:out value="${memberOrder.bs_flag}"></c:out>
													</c:set>
		  											${secondMap[typeKey]}
		  											</ec:column>
		  										<ec:column property="openprice[=][double]" title="���ּ�"
													width="5%" style="text-align:right; " >
													<fmt:formatNumber value="${memberOrder.openprice}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="closeprice[=][double]" title="ƽ�ּ�"
													width="5%" style="text-align:right; " >
													<fmt:formatNumber value="${memberOrder.closeprice}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="holdprice[=][double]" title="�ֲּ�"
													width="5%" style="text-align:right; ">
													<fmt:formatNumber value="${memberOrder.holdprice}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="tradefee[=][double]" title="������"
													width="5%" style="text-align:right; " >
													<fmt:formatNumber value="${memberOrder.tradefee}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="close_pl[=][double]" title="ƽ��ӯ��"
													width="6%" style="text-align:right; " >
													<fmt:formatNumber value="${memberOrder.close_pl}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="actualpl[=][double]" title="ʵ��ӯ��"
													width="6%" style="text-align:right; " 
													>
													<fmt:formatNumber
														value="${memberOrder.actualpl}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="traderId[like]" title="������"
													width="6%" style="text-align:left; "
													value="${memberOrder.traderId}" ellipsis="true" />
												<ec:column property="s_Name[like]" title="�ر��Ա����"
													width="7%" style="text-align:left; "
													value="${memberOrder.s_Name}" ellipsis="true" />
												<ec:column property="tradeType[=][int]" title="�ɽ�����"
													width="4%" style="text-align:left; "
													editTemplate="esc_tradeMap" ellipsis="true">
													<c:set var="typeKey">
														<c:out value="${memberOrder.tradeType}"></c:out>
													</c:set>
		  											${thirdMap[typeKey]}
		  											</ec:column>
		  										<ec:column property="operatetype[=][int]" title="��������"
													width="4%" style="text-align:left; "
													editTemplate="esc_operateMap" ellipsis="true">
													<c:set var="typeKey">
														<c:out value="${memberOrder.operatetype}"></c:out>
													</c:set>
		  											${fourthMap[typeKey]}
		  											</ec:column>
											</ec:row>
											<c:if test="${ifHas eq 1}">		
												<ec:extendrow>
													<td>
													          �ϼ�:
													</td>
													<c:if test="${queryType=='H'}">
														<td>&nbsp;</td>
													</c:if>
													<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
													<td align="right" style="font-weight:bold">${quantityAll}</td>
													<td align="right" style="font-weight:bold">${tradefundsAll}</td>	
													<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
													<td align="right" style="font-weight:bold">${tradefeeAll}</td>	
													<td align="right" style="font-weight:bold">${close_plAll}</td>													
													<td align="right" style="font-weight:bold">${actualplAll}</td>
													<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
												</ec:extendrow>
											</c:if>
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
		<textarea id="esc_tradeMap" rows="" cols="" style="display: none">
		<select onBlur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="tradeType[=][int]">
			<ec:options items="thirdMap" />
		</select>
	    </textarea>
	     <!-- �༭�͹�����ʹ�õĽ�ƽ��ģ�� -->
		<textarea id="esc_modmap" rows="" cols="" style="display: none">
		<select onBlur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="oc_Flag[like]">
			<ec:options items="firstMap" />
		</select>
	    </textarea>
		<!-- �༭�͹�����ʹ�õĽ��ַ����־ģ�� -->
		<textarea id="esc_bsFlag" rows="" cols="" style="display: none">
		<select onBlur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="build_flag[=]">
			<ec:options items="secondMap" />
		</select>
	    </textarea>
	     <!-- �༭�͹�����ʹ�õĲ���ģ�� -->
		<textarea id="esc_operateMap" rows="" cols="" style="display: none">
		<select onBlur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="operatetype[=][int]">
			<ec:options items="fourthMap" />
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
			  //ec.action="${basePath}/query/queryMemberTradeSearch/list.action?queryType=D&sortName=memberNo";
			  //frm.action="${basePath}/query/queryMemberTradeSearch/list.action?queryType=D&sortName=memberNo";
			}
			else if(value=='H')
			{
			   frm.beginDate.disabled=false;
			   frm.endDate.disabled=false;
			   
frm.beginDate.style.backgroundColor="#ffffff";
frm.endDate.style.backgroundColor="#ffffff";
			   frm.queryType.value='H';
			    //ec.action="${basePath}/query/queryMemberTradeSearch/list.action?queryType=H&sortName=memberNo";
			     //frm.action="${basePath}/query/queryMemberTradeSearch/list.action?queryType=H&sortName=memberNo";
			}
		}
		
		</SCRIPT>