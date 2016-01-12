<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%
String nowDate = Tools.fmtDate(new Date());
%>
<html>
	<head>
		<title>加盟商成交合计</title>
		<SCRIPT type="text/javascript">

		function window_onload(){

			if(frm.beginDate.value=="")
			  {
				frm.beginDate.value = '<%=nowDate%>';
				   
			  }
			  if(frm.endDate.value==""){
				  frm.endDate.value = '<%=nowDate%>';
				  
			  }
			isQryHis_onclick();
		}
		
		function setDisabled(obj)
		{
		  obj.disabled = true;
		  obj.style.backgroundColor = "#C0C0C0";
		}
		function setEnabled(obj)
		{
		  obj.disabled = false;
		  obj.style.backgroundColor = "white";
		}
		function isQryHis_onclick()
		{
		  if(frm.isQryHis.checked)
		  {
			  setEnabled(frm.beginDate);
			  setEnabled(frm.endDate);
		    
		  }
		  else
		  {
			  setDisabled(frm.beginDate);
			  setDisabled(frm.endDate);
		    
		  }
		  
		}

			//执行查询列表
			function dolistquery() {

		         if(frm.isQryHis.checked)
				  {
					  frm.isQryHisHidd.value = "yes";
					 
				  }else{
					  frm.isQryHisHidd.value = "no";
				  }
				  	       	
		         frm.submit();
			}
		</SCRIPT>
	</head>
	<body onload="window_onload()">
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<div class="div_cx">
							<form name="frm" action="${basePath}/timebargain/dataquery/listBrokerTradeCount.action" method="post">
								<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
									<tr>
										<td class="table5_td_width">
											<div class="div2_top">
												<table class="table4_style" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td class="table3_td_1" align="left">
															加盟商代码：
															<label>
																<input type="text" class="input_text" id="brokerID"   name="brokerID"" />
															</label>
														</td>
														<td class="table3_td_1" align="left">
															加盟商名称：
															<label>
																<input type="text" class="input_text" id="brokerName"   name="broekerName" />
															</label>
														</td>
														<td class="table3_td_1" align="right">
															&nbsp;&nbsp;买卖：
															<label>
																<select id="flag" name="flag"  class="normal" style="width: 120px">
																	<option value="">全部</option>
																	<c:forEach items="${BS_flagMap}" var="map">
																		<option value="${map.key}">${map.value}</option>
																	</c:forEach>
																	
																</select>
															</label>
										
														</td>
												 </tr>
												 
												<tr>
													    <td class="table3_td_1" align="right">
															&nbsp;&nbsp;成交类型：
															<label>
																<select id="tradeType" name="tradeType"  class="normal" style="width: 120px">
																	<option value="">全部类型</option>
																	<c:forEach items="${tradeTypeMap}" var="map">
																		<option value="${map.key}">${map.value}</option>
																	</c:forEach>
																	
																</select>
															</label>
															 
														</td>
													    <td class="table3_td_1" align="right">
															&nbsp;&nbsp;商品代码：
															<label>
																<input type="text" class="input_text" id="commodityId"   name="commodityId" title="可输入模式匹配符查询" />
															</label>
														</td>
														<td class="table3_td_1" align="right">
															&nbsp;&nbsp;委托类型：
															<label>
																<select id="orderType" name="orderType"  class="normal" style="width: 120px">
																	<option value="">全部类型</option>
																	<c:forEach items="${orderTypeMap}" var="map">
																		<option value="${map.key}">${map.value}</option>
																	</c:forEach>
																	
																</select>
															</label>
															
														</td>
													</tr>
													<tr>
													
															<td class="table3_td_1" align="right">
																   <input type="checkbox" name="isQryHis" id="isQryHis" onclick="isQryHis_onclick()" <c:if test="${isQryHisHidd=='yes' }">checked</c:if> class="NormalInput"/><label for="isQryHis" class="hand">查历史</label>
																   <input type="hidden" id="isQryHisHidd" name="isQryHisHidd" >
																</td>
											              <td class="table3_td_1" align="right">
																开始日期:
															   <input type="text" class="wdate" id="beginDate"  style="width: 106px"
																	name="beginDate"				
																	onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" />			
													    </td>
														  <td class="table3_td_1" align="right">
																结束日期:
																<input type="text" class="wdate" id="endDate"  style="width: 106px"
																	name="endDate"			
																	onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" />			
													      </td>		
												         <td class="table3_td_anniu" align="center">
															<button class="btn_sec" id="view" onclick=dolistquery();>查询</button>
															&nbsp;&nbsp;
															<button class="btn_cz" onclick="myReset();">重置</button>
												         </td>	
												  </tr>
												</table>
												<script type="text/javascript">
											      frm.brokerID.value = "<c:out value='${brokerID}'/>";
											      frm.brokerName.value = "<c:out value='${brokerName}'/>";
											      frm.flag.value = "<c:out value='${flag}'/>";
											      frm.tradeType.value = "<c:out value='${tradeType}'/>";
											      frm.commodityId.value = "<c:out value='${commodityId}'/>";
											      frm.orderType.value = "<c:out value='${orderType}'/>";
											      frm.beginDate.value = "<c:out value='${beginDate}'/>";
											      frm.endDate.value = "<c:out value='${endDate}'/>";
											    </script>
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
											action="${basePath}/timebargain/dataquery/listBrokerTradeCount.action"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="demo.xls" csvFileName="demo.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
												<ec:column property="BROKERID" title="加盟商代码" width="10%" ellipsis="true" style="text-align:center;"/>
											    <ec:column property="NAME" title="加盟商名称" width="10%" style="text-align:center;" ellipsis="true"/>
												<ec:column property="COMMODITYID" title="商品代码" width="10%" style="text-align:center;"/>
												<ec:column property="BS_FLAG" title="买卖" width="10%" style="text-align:center;">
												  <c:if test="${broker.BS_FLAG == 1}">
												            买
												  </c:if>
												  <c:if test="${broker.BS_FLAG == 2}">
												           卖
												  </c:if>
												</ec:column>									
												<ec:column property="ORDERTYPE" title="委托类型" width="10%" style="text-align:center;">
												   <c:if test="${broker.ORDERTYPE == 1}">
												             订立
												   </c:if>
												   <c:if test="${broker.ORDERTYPE == 2}">
												             转让
												   </c:if>
												</ec:column>
												<ec:column property="QUANTITY" title="成交数量" cell="number" calcTitle= "合计" calc="total"  width="10%" style="text-align:right;"/>								
												<ec:column property="TRADETYPE" title="成交类型" width="10%" style="text-align:center;">
												   <c:if test="${broker.TRADETYPE == 1}">
												             正常交易
												   </c:if>
												   <c:if test="${broker.TRADETYPE == 3}">
												             强制转让
												   </c:if>
												   <c:if test="${broker.TRADETYPE == 4}">
												             委托交易
												   </c:if>
												   <c:if test="${broker.TRADETYPE == 6}">
												             协议交收
												   </c:if>
												   <c:if test="${broker.TRADETYPE == 7}">
												             卖仓单
												   </c:if>
												   <c:if test="${broker.TRADETYPE == 8}">
												             抵顶转让
												   </c:if>
												</ec:column>
												<ec:column property="CLOSE_PL" title="转让盈亏" width="10%" cell="currency" calc="total" format="#,##0.00" style="text-align:right;"/>		
												<ec:column property="TRADEFEE" title="手续费" width="10%" cell="currency" calc="total" format="#,##0.00" style="text-align:right;"/>	
												<ec:column property="CLOSEADDEDTAX" title="转让增值税" width="10%" cell="currency" calc="total" format="#,##0.00" style="text-align:right;"/>													
											</ec:row>
										</ec:table>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
			</table>
		<!-- 编辑和过滤所使用的 通用的文本框模板 -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;" name="" />
		</textarea>
	</body>
</html>