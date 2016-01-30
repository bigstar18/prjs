<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%
   
%>
<html>
	<head>
		<title>加盟商资金合计</title>
		<SCRIPT type="text/javascript">

		
		
			//执行查询列表
			function dolistquery() {  
		         frm.submit();
			}

			//查看信息详情
			function customerFunds_table(brokerID){
				var url = "${basePath}/timebargain/dataquery/customerFunds.action?firmId="+brokerID;
				showDialog(url, "", 900, 650);
			}
		</SCRIPT>
	</head>
	<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<div class="div_cx">
							<form name="frm" action="${basePath}/timebargain/dataquery/listBrokerFundsCount.action" method="post">
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
																<input type="text" class="input_text" id="brokerName"   name="brokerName" />
															</label>
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
											action="${basePath}/timebargain/dataquery/listBrokerFundsCount.action"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="demo.xls" csvFileName="demo.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
												
												<ec:column property="BROKERID" title="加盟商代码" width="170" ellipsis="true" style="text-align:center;"/>	
												<ec:column property="NAME" title="加盟商名称" width="80" style="text-align:center;" ellipsis="true"/>
												<ec:column property="NOWLEAVEBALANCE" title="当前可用资金" cell="currency" calcTitle= "合计" calc="total" width="120" style="text-align:right;" format="#,##0.00"/>
												
												<ec:column property="RUNTIMEFL" title="当前浮亏" cell="currency" calc="total" width="120" style="text-align:right;" format="#,##0.00"/>
												<ec:column property="RUNTIMEMARGIN" title="当前保证金" cell="currency" calc="total" width="120" style="text-align:right;" format="#,##0.00"/>
												<ec:column property="RUNTIMEASSURE" title="当前担保金" cell="currency" calc="total" width="120" style="text-align:right;" format="#,##0.00"/>
																			
												<ec:column property="LASTBALANCE" title="上日资金余额" cell="currency" calc="total" width="120" style="text-align:right;" format="#,##0.00"/>
												<ec:column property="CLEARFL" title="上日浮亏" cell="currency" calc="total" width="120" style="text-align:right;" format="#,##0.00"/>
												<ec:column property="CLEARMARGIN" title="上日保证金" cell="currency" calc="total" width="120" style="text-align:right;" format="#,##0.00"/>
												<ec:column property="CLEARASSURE" title="上日担保金" cell="currency" calc="total" width="120" style="text-align:right;" format="#,##0.00"/>
												
												<ec:column property="TRADEFEE" title="交易手续费" cell="currency" calc="total" width="120" style="text-align:right;" format="#,##0.00"/>			
												<ec:column property="MAXOVERDRAFT" title="质押资金" cell="currency" calc="total" width="120" style="text-align:right;" format="#,##0.00"/>	
												<ec:column property="VIRTUALFUNDS" title="虚拟资金" cell="currency" calc="total" width="120" style="text-align:right;" format="#,##0.00"/>	
												<ec:column property="CLOSE_PL" title="当日转让盈亏" cell="currency" calc="total" width="120" style="text-align:right;" format="#,##0.00"/>
												<%--<ec:column property="settle_PL" title="当日交收盈亏" cell="currency" calc="total" width="120" style="text-align:right;"/>	--%>
												<%--  <c:set var="out_Amount" value="${BALANCE+CLEARASSURE+CLEARMARGIN+CLEARFL+PL+CLOSE_PL-TRADEFEE}"></c:set>--%>
												<ec:column property="QUANYI" title="当前权益" cell="currency" calc="total" width="120" style="text-align:right;" format="#,##0.00">
												  <%--  ${broker.BALANCE+broker.CLEARASSURE+broker.CLEARMARGIN+broker.CLEARFL+broker.PL+broker.CLOSE_PL-broker.TRADEFEE}--%>
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
		<!-- 编辑和过滤所使用的 通用的文本框模板 -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;" name="" />
		</textarea>
	</body>
</html>