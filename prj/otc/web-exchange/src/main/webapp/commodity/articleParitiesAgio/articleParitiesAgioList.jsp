<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
	<head>
		<%@ include file="/public/ecsideLoad.jsp"%>
	</head>
	<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						</div>
						<div class="div_tj">
						<form name="frm"
								action="${basePath}/commodity/articleParitiesAgio/list.action?sortName=primary.commodityId"
								method="post">
							<table border="0" cellpadding="0" cellspacing="0"
								class="table2_style">
								<tr>
									<td class="table2_td_width">
										<div class="div2_top">
											<table class="table3_style" border="0" cellspacing="0"
												cellpadding="0">
												<tr>
													<td class="table3_td_1" align="left">
														商品名称:&nbsp;
														<label>
															<input type="text" name="${GNNT_}commodity.name[like]"
																id="commodityName"
																value="${oldParams['commodity.name[like]'] }"
																class="input_text">
														</label>
													</td>
													<td class="table3_td_anniu" align="left">
														<button  class="btn_sec" onclick="submitMember()">查询</button>&nbsp;&nbsp;
														<button  class="btn_cz" onclick="myReset()">重置</button>
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
							<button class="anniu_btn" id='update' onclick="updateRMI()">
								实时生效
							</button>
						</div>

						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%">
								<tr>
									<td>
										<ec:table items="resultList"
											autoIncludeParameters="${empty param.autoInc}"
											var="articleParitiesAgio"
											action="${basePath}/commodity/articleParitiesAgio/list.action?sortName=primary.commodityId"
											title="" minHeight="345" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="导出列表.csv" style="table-layout:fixed">
											<ec:row recordKey="${articleParitiesAgio.commodityId}">
												<ec:column width="4%" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												<ec:column property="commodity.name[Like]" title="商品名称" width="14%" style="text-align:left;" ellipsis="true"> <a href="#" class="blank_a" onclick="return update('${articleParitiesAgio.commodityId}');"><font color="#880000">${articleParitiesAgio.commodityName}</font> </a> </ec:column>
												<ec:column property="primary.inCommodityId[like]" title="接入商品代码"
													width="15%" style="text-align:left;"
													value="${articleParitiesAgio.inCommodityId}" />
												<ec:column property="primary.quoteRate[=][bigdecimal]" title="换算单位"
													width="14%" style="text-align:right;" filterable="false"
													value="${articleParitiesAgio.quoteRate}" />
												<ec:column property="primary.quoteExchangeRate[=][bigdecimal]" title="报价汇率"
													width="14%" style="text-align:right;"  filterable="false"
													value="${articleParitiesAgio.quoteExchangeRate}" />
												<ec:column property="primary.quoteRateAndQuoteExchangeRate[=][bigdecimal]" title="报价系数"
												sortable="false" filterable="false" width="14%" style="text-align:right;"
													value="${articleParitiesAgio.quoteRateAndQuoteExchangeRate}"  />
											
												<%--<ec:column property="primary.clearExchageRate[like]" title="结算汇率"
													width="14%" style="text-align:right;"
													value="${articleParitiesAgio.clearExchageRate}" />
												<ec:column property="primary.quoteAgio[like]" title="报价贴水"
													width="14%" style="text-align:right;"
													value="${articleParitiesAgio.quoteAgio}" />--%>
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
		<!-- 编辑和过滤所使用的 通用的文本框模板 -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value=""
				onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="" />
		</textarea>
		<SCRIPT type="text/javascript">
		function update(commodityId){
			var url="${basePath}/commodity/articleParitiesAgio/forwardUpdate.action?obj.commodityId="+commodityId;
			ecsideDialog(url,"",580,425);
		}
		function submitMember(){
			frm.submit();
		}
		function updateRMI(){
			if(window.confirm("您确定要操作吗？")){
				frm.action = "${basePath}/commodity/articleParitiesAgio/updateRMI.action";
				frm.submit();
			}
		}
		</SCRIPT>
	</body>
</html>