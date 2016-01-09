<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>交易商管理</title>
		<script type="text/javascript">
			function select1(){<%//执行查询方法%>
				frm.submit();
			}
			function update(id){<%//进入修改交易商页面%>
				var url = "${basePath}/bank/firm/updateFirmForward.action?entity.firmID="+id;
				if(showDialog(url, "", 650, 530)){
					select1();
				};
			}
			function updatefee(id){<%//进入修改交易商手续费页面%>
				var url = "${basePath}/bank/fee/setUpFirmFeeForward.action?firmID="+id;
				showDialog(url, "", 900, 530)
			}
			function updatefirmBank(id){
				document.getElementById("subfirmID").value=id;
				subfirm.submit();
			}
		</script>
	</head>
	<body>
		<form action="${basePath}/bank/firmregistbank/firmIDRegistList.action?sortColumns=order+by+bank.bankID" method="post" name="subfirm" id="subfirm">
		<input id="subfirmID" name="firmID" type="hidden"/>
		</form>
		<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>
					<div class="div_cx">
						<form name="frm" action="${basePath}/bank/firm/firmList.action?sortColumns=order+by+firmID" method="post">
							<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
								<tr>
									<td class="table5_td_width">
										<div class="div2_top">
											<table class="table3_style" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td class="table3_td_1" align="left">
														交易商代码:&nbsp;
														<label>
															<input id="firmID" name="${GNNT_}primary.firmID[allLike]" value="${oldParams['primary.firmID[allLike]'] }" class="input_text"/>
														</label>
													</td>
													<td class="table3_td_anniu" align="left">
														<button class="btn_sec" onclick=select1();>查询</button>&nbsp;&nbsp;
														<button class="btn_cz" onclick=myReset();>重置</button>
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
									<ec:table items="pageInfo.result" var="firm"
										action="${basePath}/bank/firm/firmList.action"											
										autoIncludeParameters="${empty param.autoInc}"
										xlsFileName="firm.xls" csvFileName="firm.csv"
										showPrint="true" listWidth="100%"
										minHeight="345"  style="table-layout:fixed;">
										<ec:row>
											<ec:column width="5%" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" style="text-align:right;"/>
											<ec:column property="firmID" width="10%" title="交易商代码" style="text-align:center;"><rightHyperlink:rightHyperlink href="#" className="blank_a" onclick="return update('${firm.firmID}');" text="<font color='#880000'>${firm.firmID}</font>" action="/bank/firm/updateFirmForward.action">
											</rightHyperlink:rightHyperlink></ec:column>
											<ec:column property="property" title="单笔最大出金金额" width="10%" style="text-align:right;" sortable="false"><c:if 
												test="${firm.maxPersgltransMoney<=0}">--</c:if><c:if 
												test="${firm.maxPersgltransMoney>0}"><fmt:formatNumber value="${firm.maxPersgltransMoney}" pattern="#,##0.00" /></c:if></ec:column>
											<ec:column property="property" title="每日最大出金金额" width="10%" style="text-align:right;" sortable="false"><c:if 
												test="${firm.maxPertransMoney<=0}">--</c:if><c:if 
												test="${firm.maxPertransMoney>0}"><fmt:formatNumber value="${firm.maxPertransMoney}" pattern="#,##0.00" /></c:if></ec:column>
											<ec:column property="maxPertranscount" title="每日最大出金次数" width="10%" style="text-align:right;" sortable="false"/>
											<ec:column property="property" title="出金审核额度" width="10%" style="text-align:right;" sortable="false"><c:if 
	  											test="${firm.maxAuditMoney<=0}">--</c:if><c:if 
												test="${firm.maxAuditMoney>0}"><fmt:formatNumber value="${firm.maxAuditMoney}" pattern="#,##0.00" /></c:if></ec:column>
											<ec:column property="property" title="手续费设置" width="10%" style="text-align:center;" sortable="false"><rightHyperlink:rightHyperlink display="none" href="#" className="blank_a" onclick="return updatefee('${firm.firmID}');" text="<font color='#880000'>设置</font>" action="/bank/fee/setUpFirmFeeForward.action">
											</rightHyperlink:rightHyperlink></ec:column>
											<ec:column property="property" title="银行帐号管理" width="10%" style="text-align:center;" sortable="false"><rightHyperlink:rightHyperlink display="none" href="#" className="blank_a" onclick="return updatefirmBank('${firm.firmID}');" text="<font color='#880000'>管理</font>" action="/bank/firmregistbank/firmIDRegistList.action">
											</rightHyperlink:rightHyperlink></ec:column>
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