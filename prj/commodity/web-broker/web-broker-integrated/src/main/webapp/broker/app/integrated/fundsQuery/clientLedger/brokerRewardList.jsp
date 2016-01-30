<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/broker/public/includefiles/allincludefiles.jsp"%>
<html>
<head>


<title>待付佣金列表</title>
<script type="text/javascript">

	
	//执行查询列表
	function dolistquery() {
		frm.submit();
	}

// -->
</script>
</head>
<body>
	<div id="main_body">
	      <div class="div_cx">
					<form name="frm" action="${basePath}/broker/clientLedger/listBrokerReward.action" method="post">
						<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
							<tr>
								<td class="table5_td_width">
									<div class="div2_top">
										<table class="table4_style" border="0" cellspacing="0" cellpadding="0">
											<tr>
											   <td class="table3_td_1" align="left">
													    加盟商编号：
													<label>
														<input type="text" class="input_text" id="brokerId"   name="${GNNT_}primary.brokerId[=]" value="${oldParams['primary.brokerId[=]']}" />
													</label>
												</td>
												<td class="table3_td_1" align="left">
															&nbsp;&nbsp;模块：
															<label>
																<select id="moduleId" name="${GNNT_}primary.moduleId[=][Long]"  class="normal" style="width: 120px">
																	<option value="">全部</option>
																	<c:forEach items="${moduleIdMap}" var="map">
																		<option value="${map.key}">${map.value}</option>
																	</c:forEach>
																	
																</select>
															</label>
															 <script >
																frm.moduleId.value = "<c:out value='${oldParams["primary.moduleId[=][Long]"] }'/>";
					  										</script>
												</td>
												<td class="table3_td_1" align="left">
											            &nbsp;&nbsp;开始日期:
											        <input type="text" class="wdate" id="beginDate"  style="width: 106px" 
												       name="${GNNT_}primary.occurDate[>=][date]"			
												       value="${oldParams['primary.occurDate[>=][date]']}"					
												       onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" />			
							                     </td>

									              <td class="table3_td_1" align="left">
											           &nbsp;&nbsp;结束日期:
											         <input type="text" class="wdate" id="endDate"  style="width: 106px" 
												      name="${GNNT_}primary.occurDate[<=][date]"			
												       value="${oldParams['primary.occurDate[<=][date]']}"					
												      onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" />			
							                     </td>		
														
												<td class="table3_td_anniu" align="left">
													<button class="btn_sec" id="view" onclick=dolistquery();>查询</button>
													<button class="btn_cz" onclick="myReset();">重置</button>
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
			<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
				<tr>
					<td>
						<ec:table items="pageInfo.result" var="broker"
							action="${basePath}/broker/clientLedger/listBrokerReward.action"											
							autoIncludeParameters="${empty param.autoInc}"
							xlsFileName="export.xls" csvFileName="export.csv"
							showPrint="true" listWidth="100%"
							minHeight="345"  style="table-layout:fixed;">
							<ec:row>
							
		
								<ec:column property="brokerId" width="5%" title="加盟商编号" style="text-align:center;"/>
								<ec:column property="moduleId" title="模块" width="5%" style="text-align:center;">${moduleIdMap[broker.moduleId]}</ec:column>
								<ec:column property="occurDate" title="发生日" width="10%" style="text-align:center;">
								     <fmt:formatDate value="${broker.occurDate }" pattern="yyyy-MM-dd" />
								</ec:column>
								<ec:column property="paidAmount" title="已付服务费" width="8%" style="text-align:right;" calc="total" calcTitle= "合计" format="#,##0.00">
								    <fmt:formatNumber value="${broker.paidAmount }" pattern="#,##0.00"/>
								</ec:column>
								<ec:column property="payDate" title="付款日" width="10%" style="text-align:center;">
								     <fmt:formatDate value="${broker.payDate }" pattern="yyyy-MM-dd" />
								</ec:column>
								<ec:column property="amount" title="待付服务费" width="8%" style="text-align:right;" calc="total" format="#,##0.00">
								    <fmt:formatNumber value="${broker.amount }" pattern="#,##0.00" />
								</ec:column>
					
							</ec:row>
						</ec:table>
					</td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>
