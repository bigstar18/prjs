<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
	<head>
		<%@ include file="/public/ecsideLoad.jsp"%>
	</head>
	<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>
						<div class="div_tj">
							<form name="frm"
								action="${basePath}/commodity/orderPoint/list.action?sortName=primary.commodityId"
								method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table2_td_widthmin">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td class="table3_td_1" align="left">
															商品名称:&nbsp;
															<label>
																<input type="text" class="input_text"
																	name="${GNNT_}commodity.name[like]" id="commodityName"
																	value="${oldParams['commodity.name[like]'] }" />
															</label>
														</td>
														<td class="table3_td_anniu" align="left">
															<button class="btn_sec" onclick="submitMember()">
																查询
															</button>
															&nbsp;&nbsp;
															<button class="btn_cz" onclick="myReset()">
																重置
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
											var="orderPoint"
											action="${basePath}/commodity/orderPoint/list.action?sortName=primary.commodityId"
											title="" minHeight="345" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="导出列表.csv" style="table-layout:fixed">
											<ec:row recordKey="${orderPoint.commodityId}">
												<ec:column width="4%" property="_0" title="序号"
													value="${GLOBALROWCOUNT}" sortable="false"
													filterable="false" />
												
													<ec:column property="commodity.name[like]" title="商品名称" width="11%" style="text-align:left;" ellipsis="true"> <a href="#" class="blank_a" onclick="return forwardUpdate('${orderPoint.commodityId}','${orderPoint.memberFirmId}');"><font color="#880000">${orderPoint.commodityName }</font> </a></ec:column>
												<ec:column property="primary.memberFirmId[like]" title="类型"
													width="11%" style="text-align:left;"
													editTemplate="ecs_t_firmDis">
											  		${firmDisMap[orderPoint.memberFirmId]}
											  	</ec:column>
												<ec:column property="primary.stopLossPoint[=][int]"
													title="止损下单点差" width="11%" style="text-align:right;"
													value="${orderPoint.stopLossPoint}" />
												<ec:column property="primary.stopProfitPoint[=][int]"
													title="止盈下单点差" width="11%" style="text-align:right;"
													value="${orderPoint.stopProfitPoint}" />
												<ec:column property="primary.l_Open_Point[=][int]"
													title="指价建仓点差" width="11%" style="text-align:right;"
													value="${orderPoint.l_Open_Point}" />
												<ec:column property="primary.m_OrderPoint[=][int]"
													title="默认市价点差" width="11%" style="text-align:right;"
													value="${orderPoint.m_OrderPoint}" />
												<ec:column property="primary.min_M_OrderPoint[=][int]"
													title="市价点差最小值" width="11%" style="text-align:right;"
													value="${orderPoint.min_M_OrderPoint}" />
												<ec:column property="primary.max_M_OrderPoint[=][int]"
													title="市价点差最大值" width="11%" style="text-align:right;"
													value="${orderPoint.max_M_OrderPoint}" />
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
		<!-- 编辑和过滤所使用的默认交易商模板 -->
		<textarea id="ecs_t_firmDis" rows="" cols="" style="display: none">
		<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="primary.m_FirmId[like]">
			<ec:options items="firmDisMap" />
		</select>
	    </textarea>
		<SCRIPT type="text/javascript">
		function forwardUpdate(commodityId,memberFirmId){
			var url="${basePath}/commodity/orderPoint/forwardUpdate.action?obj.commodityId="+commodityId+"&obj.memberFirmId="+memberFirmId;
			
			ecsideDialog(url,"",580,500);
		}
		function submitMember(){
			frm.submit();
		}
		</SCRIPT>
	</body>
</html>