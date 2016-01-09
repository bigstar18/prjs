<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>二次审核</title>
		<script src="${publicPath }/js/tool.js" type="text/javascript"></script>
		<script type="text/javascript">
			function select1(){<%//执行查询功能%>
				checkQueryDate(frm.beginTime.value,frm.endTime.value);
			}
			function auditOutMoney(btn){<%//执行出金审核%>
				var url = "${basePath}"+btn.action+"?autoInc=false";
				updateRMIEcside(ec.ids,url);
			}
		</script>
	</head>
	<body>
		<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>
					<div class="div_cx">
						<form name="frm" id="frm" action="${basePath}/bank/capital/outMoneySecondAuditList.action?sortColumns=order+by+id+desc" method="post">
							<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
								<tr>
									<td class="table5_td_width">
										<div class="div2_top">
											<table class="table3_style" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td class="table3_td_1" align="left">
														交易商代码:&nbsp;
														<label>
															<input id="firmID" name="${GNNT_}primary.firm.firmID[allLike]" value="${oldParams['primary.firm.firmID[allLike]'] }" class="input_text"/>
														</label>
													</td>
													<td class="table3_td_1" align="left">
														银行代码:&nbsp;
														<label>
															<input id="bankID" name="${GNNT_}primary.bank.bankID[=]" value="${oldParams['primary.bank.bankID[=]'] }" class="input_text"/>
														</label>
													</td>
													<td>
													&nbsp;
													</td>
													<td>
													&nbsp;
													</td>
												</tr>
												<tr>
													<td class="table3_td_1" align="left">
														起始日期:&nbsp;
														<label>
															<input id="beginTime" name="${GNNT_}primary.createtime[>=][date]" value="${oldParams['primary.createtime[>=][date]']}" class="wdate validate[custom[date],funcCall[checkBeginDate]] datepicker" onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})"/>
														</label>
													</td>
													<td class="table3_td_1" align="left">
														结束日期:&nbsp;
														<label>
															<input id="endTime" name="${GNNT_}primary.createtime[<=][datetime]" value="${oldParams['primary.createtime[<=][datetime]']}" class="wdate validate[custom[date],funcCall[checkEndDate]] datepicker" onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})"/>
														</label>
													</td>
													<td class="table3_td_anniu" align="right">
														<button class="btn_sec" id="view" onclick="select1()">查询</button>&nbsp;&nbsp;
														<button class="btn_cz" onclick=myReset();>重置</button>
													</td>
													<td>
													&nbsp;
													</td>
												</tr>
											</table>
										</div>
									</td>
								</tr>
							</table>
						</form>
					</div>
					<div class="div_gn">
						<rightButton:rightButton name="审核通过" onclick="auditOutMoney(this);" className="anniu_btn" action="/bank/capital/outMoneySecondAuditPass.action" id="auditpass"></rightButton:rightButton>&nbsp;&nbsp;
						<rightButton:rightButton name="审核拒绝" onclick="auditOutMoney(this);" className="anniu_btn" action="/bank/capital/outMoneySecondAuditRefuse.action" id="auditrefuse"></rightButton:rightButton>
					</div>
					<div class="div_list">
						<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
							<tr>
								<td>
									<ec:table items="pageInfo.result" var="capital"
										action="${basePath}/bank/capital/outMoneySecondAuditList.action"											
										autoIncludeParameters="${empty param.autoInc}"
										xlsFileName="firm.xls" csvFileName="firm.csv"
										showPrint="true" listWidth="100%"
										minHeight="345"  style="table-layout:fixed;">
										<ec:row>
											<ec:column cell="checkbox" headerCell="checkbox" alias="ids" style="text-align:center; " value="${capital.actionID}" width="5%" viewsAllowed="html" />
											<ec:column width="5%" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" style="text-align:right;"/>
											<ec:column property="id" width="10%" title="记录流水号" style="text-align:right;"/>
											<ec:column property="actionID" width="10%" title="市场流水号" style="text-align:right;"/>
											<ec:column property="firm.firmID" title="交易商代码" width="10%" style="text-align:center;"/>
											<ec:column property="bank.bankID" title="银行代码" width="10%" style="text-align:center;"/>
											<ec:column property="money" title="金额" width="10%" style="text-align:right;"><fmt:formatNumber 
													value="${capital.money}" pattern="#,##0.00" /></ec:column>
											<ec:column property="createtime" title="创建时间" width="10%" style="text-align:center;"><fmt:formatDate 
												value="${capital.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/></ec:column>
											<ec:column property="property" title="备注" width="10%" style="text-align:center;" sortable="false"><c:if 
												test="${fn:indexOf(capital.note,'market_in')>=0}">市场入金</c:if><c:if 
												test="${fn:indexOf(capital.note,'market_out')>=0}">市场出金</c:if><c:if 
												test="${fn:indexOf(capital.note,'bank_out')>=0}">银行出金</c:if><c:if 
												test="${!(fn:indexOf(capital.note,'market_in')>=0 
												|| fn:indexOf(capital.note,'market_out')>=0 
												|| fn:indexOf(capital.note,'bank_out')>=0)}">银行入金</c:if></ec:column>
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