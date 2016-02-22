<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%
	String begTime = "例：hh:mm:ss";
%>
<html>
	<head>
		<%@ include file="/public/ecsideLoad.jsp"%>
		<title>平均价</title>
	</head>
	<body>
		<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>
					<div class="div_tj">
						<table border="0" cellpadding="0" cellspacing="0"
							class="table2_style">
							<tr>
								<td class="table2_td_widthcdmax">
									<div class="div2_top">
										<form name="frm" method="post"
											action="${basePath}/settlement/avgPrice/avgPrice.action">
											<table class="table3_style" border="0" cellspacing="0"
												cellpadding="0" style="table-layout: fixed">
												<tr>
													<td class="table3_td_1tjcx_1">
														&nbsp;&nbsp;交易日:
														<fmt:formatDate value='${tradeDate }' pattern='yyyy-MM-dd' />
													</td>
													<td align="left" width="140px;">
														&nbsp;
													</td>
													<td class="table3_td_1tjcx_1" align="left">
														商品名称:
														<span class="right_03zi"> <select id="commodityId"
																name="${GNNT_}primary.commodityId[=]"
																class="input_textmin">
																<option value="">
																	请选择
																</option>
																<c:forEach items="${commodityList}" var="commodit">
																	<option value="${commodit.id}">
																		${commodit.name }
																	</option>
																</c:forEach>
															</select> </span>
														<script type="text/javascript">
frm.commodityId.value = '${oldParams["primary.commodityId[=]"] }';</script>
													</td>
													<td align="left" width="140px;">
														<div id="commodityId_vTip">&nbsp;</div>
													</td>
													<td class="table3_td_anniu">
														&nbsp;
													</td>
												</tr>
												<tr>
													<td  align="left"  colspan="2">
														开始时间:
															<input type="text" style="width: 160px" id="begTime"
																class="wdate" maxlength="10"
																name="${GNNT_}occurTime[>][timestemp]"
																value='${oldParams["occurTime[>][timestemp]"]}'
																onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd HH:mm:ss',skin:'whyGreen'})">
																<div id="begTime_vTip">&nbsp;</div>
													</td>
													<td  align="left" colspan="2">
														结束时间:
														<label>
														<input type="text" style="width: 160px" id="endTime"
																class="wdate" maxlength="10"
																name="${GNNT_}occurTime[<][timestemp]"
																value='${oldParams["occurTime[<][timestemp]"]}'
																onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd HH:mm:ss',skin:'whyGreen'})">
														<div id="endTime_vTip">&nbsp;</div>
														</label>
													</td>
													<td class="table3_td_anniu" align="left">
														<button class="btn_sec" onClick="search1()">
															查询
														</button>
													</td>
												</tr>
											</table>
										</form>
									</div>
								</td>
							</tr>
						</table>
					</div>
					<br>
					<div class="div_gn">
						<table id="tb" border="0" cellspacing="0" align="left"  
							cellpadding="0" width="40%" >
							<tr>
								<td style="font-weight: bold;">
									&nbsp;&nbsp;平均价：
									<fmt:formatNumber value="${avgPriceArray[0]}"   minFractionDigits="${avgPriceArray[3]}"/>
								</td>
								<td style="font-weight: bold;">
									总条数：
									${avgPriceArray[1]}
								</td>
							</tr>
						</table>
					</div>
					<br>
					<div class="div_list">
						<table id="tb" border="0" cellspacing="0" cellpadding="0"
							width="100%">
							<tr>
								<td>
									<ec:table items="resultList"
										autoIncludeParameters="${empty param.autoInc}" var="avgPrice"
										action="${basePath}/settlement/avgPrice/avgPrice.action"
										filterRowsCallback="limit" title="" minHeight="345"
										listWidth="100%" csvFileName="导出列表.csv"
										style="table-layout:fixed">
										<ec:row recordKey="${avgPrice.detailId}">
											<ec:column width="10%" property="_0" title="序号"
												value="${GLOBALROWCOUNT}" sortable="false"
												filterable="false" />
											<ec:column property="primary.commodityId[=][String]"
												title="商品名称" width="33%" style="text-align:center;"
												editTemplate="ecs_status" filterable="false"
												sortable="false">
												<c:forEach items="${commodityList}" var="commodit">
													<option value="${avgPrice.commodityId }">
														<c:if test="${avgPrice.commodityId==commodit.id }">${commodit.name }</c:if>
													</option>
												</c:forEach>
											</ec:column>
											<ec:column property="primary.price[=][double]" title="价格"
												width="33%" style="text-align:center;"
												editTemplate="ecs_status" filterable="false"
												sortable="false">
												 <fmt:formatNumber value="${avgPrice.price}"   minFractionDigits="${avgPriceArray[3]}"/>
											</ec:column>
											<ec:column property="primary.occurTime[=][timestemp]"
												title="发生时间" width="34%" style="text-align:center;"
												editTemplate="ecs_note" filterable="false" sortable="false">
												 ${datefn:formatdate(avgPrice.occurTime) }	
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

	</body>
</html>
<SCRIPT type="text/javascript">

function checkTotalDate(startDate,endDate,tradeDate) {
	var now = new Date(Date.parse(tradeDate.replace(/-/g,   "/")));
	var   s   =   new   Date(Date.parse(startDate.replace(/-/g,   "/")));
	var   e   =   new   Date(Date.parse(endDate.replace(/-/g,   "/")));
	 if (s!="" && s < now ) {
		alert("开始时间不能小于交易日");
		return false;
	}else if(s>e){
		alert("开始时间不能大于结束时间");
		return false;
	} 
	else {
		return true;
	}
}
function search1(){
			var commodityId = document.getElementById("commodityId").value;
			var date1 = document.getElementById("begTime").value;
			var date2 = document.getElementById("endTime").value;
			var tradeDate ="<fmt:formatDate value='${tradeDate }' pattern='yyyy-MM-dd HH:mm:ss' />";
			if(commodityId==""){
				alert("商品不能为空");
				return false;
			} 
			if(date1==""){
				alert("开始时间不能为空");
				return false;
			}
			if(date1==""){
				alert("结束时间不能为空");
				return false;
			}
			if(!checkTotalDate(date1,date2,tradeDate)){return false;}
			frm.submit();
		}
</SCRIPT>