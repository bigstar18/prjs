
<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@page import="java.util.*"%>
<html xmlns:MEBS>
	<head>
		<title>��Աƽ�ֵ���ѯ</title>
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
								action="${basePath}/query/queryMemberTradeSearch/list.action?sortName=primary.de_builderId&sortOrder=true"
								method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table2_td_widthmax">
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
															��Ա���:&nbsp;
															<label>
																<input type="text" name="${GNNT_}memberNo[like]"
																	id="memberName" value="${oldParams['memberNo[like]'] }"
																	class="input_textmin" />
															</label>
														</td>
														<td class="table3_td_1" align="left">
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
														<td class="table3_td_1" align="left">
															�ر��Ա:&nbsp;
															<label>
																<span class="right_03zi"> <select
																		name="${GNNT_}s_memberNo[like]" size="1"
																		id="specialId" style="width: 100">
																		<option value="">
																			��ѡ��
																		</option>
																		<c:forEach items="${specialMemberList}" var="list">
																			<option value="${list.id }">
																				${list.name }
																			</option>
																		</c:forEach>
																	</select> </span>
															</label>
														</td>
														<script type="text/javascript">
frm.specialId.value = '${oldParams["s_memberNo[=][String]"] }';
</script>
													</tr>
													<tr>
														<td class="table3_td_1" align="left">
															��ʼ����:&nbsp;
															<label>
																<input type="text" style="width: 100px" id="beginDate"
																	class="wdate" maxlength="10"
																	name="${GNNT_}atClearDate[>=][date]"
																	value='${oldParams["atClearDate[>=][date]"]}'
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
															</label>
														</td>
														<td class="table3_td_1" align="left">
															��������:&nbsp;
															<label>
																<input type="text" style="width: 100px" id="endDate"
																	class="wdate" maxlength="10"
																	name="${GNNT_}atClearDate[<=][date]"
																	value='${oldParams["atClearDate[<=][date]"]}'
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
															</label>
														</td>
														<td class="table3_td_1" align="left">
															����/����:
															<label>
																<span class="right_03zi"> <select
																		name="${GNNT_}build_flag[=][int]" size="1" id="flag"
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
														<td class="table3_td_anniu" align="left">
															<button class="btn_sec" onClick="search1()">
																��ѯ
															</button>
															&nbsp;&nbsp;
															<button class="btn_cz" onClick="myReset()">
																����
															</button>
														</td>
														<script type="text/javascript">
frm.flag.value = '${oldParams["build_flag[=][int]"] }';
</script>
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
											var="memberTrade"
											action="${basePath}/query/queryMemberTradeSearch/list.action"
											title="" minHeight="345" listWidth="200%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="�����б�.csv" style="table-layout:fixed">
											<ec:row ondblclick="">
												<ec:column width="3%" property="_0" title="���"
													value="${GLOBALROWCOUNT}" sortable="false"
													filterable="false" />
												<c:if test="${queryType=='H'}">
													<ec:column property="atClearDate[=][date]" title="��������"
														width="5%" style="text-align:left; "
														value="${datefn:formatdate(memberTrade.atClearDate)}"
														ellipsis="true" />
												</c:if>
												<ec:column property="de_builderId[like]" title="ƽ�ֵ���"
													width="5%" style="text-align:left; "
													value="${memberTrade.de_builderId}" ellipsis="true"/>
												<ec:column property="memberNo[like]" title="��Ա���" width="4%"
													style="text-align:left; " value="${memberTrade.memberNo}" ellipsis="true" />

												<ec:column property="commodityName[like]" title="��Ʒ����"
													width="5%" style="text-align:left; "
													value="${memberTrade.commodityName}" ellipsis="true"/>
												<ec:column property="holdQty[=][int]" title="����" width="4%"
													style="text-align:right; " ellipsis="true">
													<fmt:formatNumber value="${memberTrade.holdQty}"
														pattern="#,##0" />
												</ec:column>
												<ec:column property="s_memberNo[like]" title="�ӵ��ر��Ա��"
													width="6%" style="text-align:left; "
													value="${memberTrade.s_memberNo}" ellipsis="true">
												</ec:column>
												<ec:column property="o_firmName[like]" title="�ӵ��ر��Ա����"
													width="6%" style="text-align:left; "
													value="${memberTrade.o_firmName}" ellipsis="true" />
												<ec:column property="de_buildprice[=][double]" title="ƽ�ּ�"
													width="4%" style="text-align:right; ">
													<fmt:formatNumber value="${memberTrade.de_buildprice}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="de_buildDate[=][date]" title="ƽ��ʱ��"
													width="7%" style="text-align:left; "
													value="${datefn:formatdate(memberTrade.de_buildDate)}" ellipsis="true"/>
												<ec:column property="builderId[like]" title="���ֵ���"
													width="4%" style="text-align:left; "
													value="${memberTrade.builderId}" ellipsis="true"/>
												<ec:column property="build_flag[=][int]" title="���ַ���"
													width="4%" style="text-align:left; "
													editTemplate="esc_bsFlag" ellipsis="true">
													<c:set var="typeKey">
														<c:out value="${memberTrade.build_flag}"></c:out>
													</c:set>
		  											${bsFlagMap[typeKey]}
		  											</ec:column>
												<ec:column property="openPrice[=][double]" title="���ּ�" width="4%"
													style="text-align:right; ">
													<fmt:formatNumber value="${memberTrade.openPrice}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="holdPrice[=][double]" title="�ֲּ�" width="4%"
													style="text-align:right; " ellipsis="true">
													<fmt:formatNumber value="${memberTrade.holdPrice}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="buildDate[=][date]" title="����ʱ��"
													width="7%" style="text-align:left; "
													value="${datefn:formatdate(memberTrade.buildDate)}" ellipsis="true"/>
												<ec:column property="floatingLoss[=][double]" title="ӯ��"
													width="4%" style="text-align:right; ">
													<fmt:formatNumber value="${memberTrade.floatingLoss}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="netfloatingLoss[=][double]" title="��ӯ��"
													width="4%" style="text-align:right; ">
													<fmt:formatNumber value="${memberTrade.netfloatingLoss}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="tradefee[=][double]" title="������"
													width="4%" style="text-align:right; ">
													<fmt:formatNumber value="${memberTrade.tradefee}"
														pattern="#,##0.00" />
												</ec:column>
												<ec:column property="closetraderid[like]" title="ƽ����"
													width="4%" style="text-align:right; "
													value="${memberTrade.closetraderid}" ellipsis="true">
												</ec:column>
												<ec:column property="opentraderid[like]" title="������"
													width="4%" style="text-align:right;"
													value="${memberTrade.opentraderid}" ellipsis="true">
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
				name="buildTradeType[=]">
			<ec:options items="tradeTypeMap" />
		</select>
	    </textarea>
		<!-- �༭�͹�����ʹ�õĽ��ַ����־ģ�� -->
		<textarea id="esc_bsFlag" rows="" cols="" style="display: none">
		<select onBlur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="build_flag[=]">
			<ec:options items="bsFlagMap" />
		</select>
	    </textarea>

		<!-- �༭�͹�����ʹ�õ�ƽ�ַ����־ģ�� -->
		<textarea id="esc_de_bsFlag" rows="" cols="" style="display: none">
		<select onBlur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="de_build_flag[=]">
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