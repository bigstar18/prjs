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
						<div class="div_tj">
						<form name="frm"
								action="${basePath}/commodity/tradeAuthForTrader/list.action?sortName=primary.commodityId"
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
																name="${GNNT_}commodity.name[like]"
																id="commodityName"
																value="${oldParams['commodity.name[like]'] }" />
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
							<button class="anniu_btn" onclick="addTraderTrade()" id="update">
								添加
							</button>
						</div>

						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%">
								<tr>
									<td>
										<ec:table items="resultList"
											autoIncludeParameters="${empty param.autoInc}"
											var="tradeAuth"
											action="${basePath}/commodity/tradeAuthForTrader/list.action?sortName=primary.commodityId" title=""
											minHeight="345" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="导出列表.csv" style="table-layout:fixed">
											<ec:row recordKey="${tradeAuth.firmId}">
												<ec:column width="4%" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												<ec:column property="commodity.name[Like]" title="商品名称" width="7%" style="text-align:left;" ellipsis="true"><a href="#" class="blank_a" onclick="return update('${tradeAuth.firmId}','${tradeAuth.commodityId}');"><font color="#880000">${tradeAuth.commodityName }</font> </a></ec:column>
												<ec:column property="customer.name[=][String]" title="客户名称"
													width="10%" style="text-align:left;" ellipsis="true"
													 value="${tradeAuth.firmName}" />
		  										<ec:column property="primary.display[=][int]" title="显示权限"
													width="5%" style="text-align:left;"
													editTemplate="ecs_m_B_Open">
													<c:set var="m_B_OpenKey">
														<c:out value="${tradeAuth.display}"></c:out>
													</c:set>
							  						${authorityMap[m_B_OpenKey]}
					            				</ec:column>
		  										<ec:column property="primary.m_B_Open[=][int]" title="买入市建"
													width="5%" style="text-align:left;"
													editTemplate="ecs_m_B_Open">
													<c:set var="m_B_OpenKey">
														<c:out value="${tradeAuth.m_B_Open}"></c:out>
													</c:set>
							  						${authorityMap[m_B_OpenKey]}
					            				</ec:column>
					            				<ec:column property="primary.m_S_Open[=][int]" title="卖出市建"
													width="5%" style="text-align:left;"
													editTemplate="ecs_m_B_Open">
													<c:set var="m_S_OpenKey">
														<c:out value="${tradeAuth.m_S_Open}"></c:out>
													</c:set>
											  		${authorityMap[m_S_OpenKey]}
									            </ec:column>
									            <ec:column property="primary.m_B_Close[=][int]" title="买入市平"
													width="5%" style="text-align:left;"
													editTemplate="ecs_m_B_Close">
													<c:set var="m_B_CloseKey">
														<c:out value="${tradeAuth.m_B_Close}"></c:out>
													</c:set>
							  					${authorityMap[m_B_CloseKey]}
					            				</ec:column>
					            				<ec:column property="primary.m_S_Close[=][int]" title="卖出市平"
													width="5%" style="text-align:left;"
													editTemplate="ecs_m_S_Close">
													<c:set var="m_S_CloseKey">
														<c:out value="${tradeAuth.m_S_Close}"></c:out>
													</c:set>
											  		${authorityMap[m_S_CloseKey]}
									            </ec:column>
									            <ec:column property="primary.l_B_Open[=][int]" title="买入止建"
													width="5%" style="text-align:left;"
													editTemplate="ecs_l_B_Open">
													<c:set var="l_B_OpenKey">
														<c:out value="${tradeAuth.l_B_Open}"></c:out>
													</c:set>
							  					${authorityMap[l_B_OpenKey]}
					            				</ec:column>
					            				<ec:column property="primary.l_S_Open[=][int]" title="卖出止建"
													width="5%" style="text-align:left;"
													editTemplate="ecs_l_S_Open">
													<c:set var="l_S_OpenKey">
														<c:out value="${tradeAuth.l_S_Open}"></c:out>
													</c:set>
											  		${authorityMap[l_S_OpenKey]}
									            </ec:column>
									            <ec:column property="primary.l_B_CloseLose[=][int]" title="买入止损"
													width="5%" style="text-align:left;"
													editTemplate="ecs_l_B_CloseLose">
													<c:set var="l_B_CloseLoseKey">
														<c:out value="${tradeAuth.l_B_CloseLose}"></c:out>
													</c:set>
											  		${authorityMap[l_B_CloseLoseKey]}
									            </ec:column>
									            <ec:column property="primary.l_S_CloseLose[=][int]" title="卖出止损"
													width="5%" style="text-align:left;"
													editTemplate="ecs_l_S_CloseLose">
													<c:set var="l_S_CloseLoseKey">
														<c:out value="${tradeAuth.l_S_CloseLose}"></c:out>
													</c:set>
											  		${authorityMap[l_S_CloseLoseKey]}
									            </ec:column>
									            <ec:column property="primary.l_B_CloseProfit[=][int]" title="买入止盈"
													width="5%" style="text-align:left;"
													editTemplate="ecs_l_B_CloseProfit">
													<c:set var="l_B_CloseProfitKey">
														<c:out value="${tradeAuth.l_B_CloseProfit}"></c:out>
													</c:set>
											  		${authorityMap[l_B_CloseProfitKey]}
									            </ec:column>
									            <ec:column property="primary.l_S_CloseProfit[=][int]" title="卖出止盈"
													width="5%" style="text-align:left;"
													editTemplate="ecs_l_S_CloseProfit">
													<c:set var="l_S_CloseProfitKey">
														<c:out value="${tradeAuth.l_S_CloseProfit}"></c:out>
													</c:set>
											  		${authorityMap[l_S_CloseProfitKey]}
									            </ec:column>
		  										<ec:column property="primary.cancel_L_Open[=][int]" title="撤销止建"
													width="5%" style="text-align:left;"
													editTemplate="ecs_cancel1">
													<c:set var="cancel_L_Open">
														<c:out value="${tradeAuth.cancel_L_Open}"></c:out>
													</c:set>
							  						${authorityMap[cancel_L_Open]}
					            				</ec:column>
		  										<ec:column property="primary.cancel_StopLoss[=][int]" title="撤销止损"
													width="5%" style="text-align:left;"
													editTemplate="ecs_cancel2">
													<c:set var="cancel_StopLoss">
														<c:out value="${tradeAuth.cancel_StopLoss}"></c:out>
													</c:set>
							  						${authorityMap[cancel_StopLoss]}
					            				</ec:column>				
		  										<ec:column property="primary.cancel_StopProfit[=][int]" title="撤销止盈"
													width="5%" style="text-align:left;"
													editTemplate="ecs_cancel3">
													<c:set var="cancel_StopProfit">
														<c:out value="${tradeAuth.cancel_StopProfit}"></c:out>
													</c:set>
							  						${authorityMap[cancel_StopProfit]}
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
		<!-- 编辑和过滤所使用的 通用的文本框模板 -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value=""
				onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="" />
		</textarea>
		<!-- 编辑和过滤所使用的默认交易商模板 -->
		<textarea id="ecs_t_firmDis" rows="" cols="" style="display: none">
		<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="primary.firmId[like]">
			<ec:options items="firmDisMap" />
		</select>
	    </textarea>
		<!-- 编辑和过滤所使用的买入市建模板 -->
		<textarea id="ecs_m_B_Open" rows="" cols="" style="display: none">
		<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="primary.m_B_Open[=][int]">
			<ec:options items="authorityMap" />
		</select>
	    </textarea>
		<!-- 编辑和过滤所使用的买入市平模板 -->
		<textarea id="ecs_m_B_Close" rows="" cols="" style="display: none">
		<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="primary.m_B_Close[=][int]">
			<ec:options items="authorityMap" />
		</select>
	    </textarea>
		<!-- 编辑和过滤所使用的买入止建模板 -->
		<textarea id="ecs_l_B_Open" rows="" cols="" style="display: none">
		<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="primary.l_B_Open[=][int]">
			<ec:options items="authorityMap" />
		</select>
	    </textarea>
		<!-- 编辑和过滤所使用的买入止损模板 -->
		<textarea id="ecs_l_B_CloseLose" rows="" cols="" style="display: none">
		<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="primary.l_B_CloseLose[=][int]">
			<ec:options items="authorityMap" />
		</select>
	    </textarea>
		<!-- 编辑和过滤所使用的买入止盈模板 -->
		<textarea id="ecs_l_B_CloseProfit" rows="" cols=""
			style="display: none">
		<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="primary.l_B_CloseProfit[=][int]">
			<ec:options items="authorityMap" />
		</select>
	    </textarea>


		<!-- 编辑和过滤所使用的卖出市建模板 -->
		<textarea id="ecs_m_B_Open" rows="" cols="" style="display: none">
		<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="primary.m_B_Open[=][int]">
			<ec:options items="authorityMap" />
		</select>
	    </textarea>
		<!-- 编辑和过滤所使用的卖出市平模板 -->
		<textarea id="ecs_m_S_Close" rows="" cols="" style="display: none">
		<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="primary.m_S_Close[=][int]">
			<ec:options items="authorityMap" />
		</select>
	    </textarea>
		<!-- 编辑和过滤所使用的卖出止建模板 -->
		<textarea id="ecs_l_S_Open" rows="" cols="" style="display: none">
		<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="primary.l_S_Open[=][int]">
			<ec:options items="authorityMap" />
		</select>
	    </textarea>
		<!-- 编辑和过滤所使用的卖出止损模板 -->
		<textarea id="ecs_l_S_CloseLose" rows="" cols="" style="display: none">
		<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="primary.l_S_CloseLose[=][int]">
			<ec:options items="authorityMap" />
		</select>
	    </textarea>
		<!-- 编辑和过滤所使用的卖出止盈模板 -->
		<textarea id="ecs_l_S_CloseProfit" rows="" cols=""
			style="display: none">
		<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="primary.l_S_CloseProfit[=][int]">
			<ec:options items="authorityMap" />
		</select>
	    </textarea>
	    <!-- 编辑和过滤所使用的撤销止建模板 -->
		<textarea id="ecs_cancel1" rows="" cols=""
			style="display: none">
		<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="primary.cancel_L_Open[=][int]">
			<ec:options items="authorityMap" />
		</select>
	    </textarea>
	    <!-- 编辑和过滤所使用的撤销止损模板 -->
		<textarea id="ecs_cancel2" rows="" cols=""
			style="display: none">
		<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="primary.cancel_StopLoss[=][int]">
			<ec:options items="authorityMap" />
		</select>
	    </textarea>
	    <!-- 编辑和过滤所使用的撤销止盈模板 -->
		<textarea id="ecs_cancel3" rows="" cols=""
			style="display: none">
		<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="primary.cancel_StopProfit[=][int]">
			<ec:options items="authorityMap" />
		</select>
	    </textarea>

		<SCRIPT type="text/javascript">
		function update(firmId,commodityId){
			var url="${basePath}/commodity/tradeAuthForTrader/forwardUpdate.action?obj.commodityId="+commodityId+"&obj.firmId="+firmId;
			ecsideDialog(url,"",580,600);
		}
		function submitMember(){
			frm.submit();
		}
		function addTraderTrade() {
			var urlFee = "${basePath}/commodity/tradeAuthForTrader/forwardAdd.action";
			ecsideDialog(urlFee, "", 580, 560);
		}
		</SCRIPT>
	</body>
</html>