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
								action="${basePath}/commodity/commodityFee/list.action?sortName=primary.commodityId"
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
														  </button>&nbsp;&nbsp;
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
											var="commodityFee"
											action="${basePath}/commodity/commodityFee/list.action?sortName=primary.commodityId"
											title="" minHeight="345" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="导出列表.csv" style="table-layout:fixed">
											<ec:row recordKey="${commodityFee.firmId}">
												<ec:column width="4%" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												<ec:column property="commodity.name[Like]" title="商品名称" width="20%" style="text-align:left;" ellipsis="true" > <a href="#" class="blank_a" onclick="return update('${commodityFee.firmId}','${commodityFee.commodityId}');"><font color="#880000">${commodityFee.commodityName}</font> </a> </ec:column>
												<ec:column property="primary.firmId[like]" title="类型"
													width="20%" style="text-align:left;"
													editTemplate="ecs_t_firmDis">
		  											${firmDisMap[commodityFee.firmId]}
		  										</ec:column>
												<ec:column property="primary.feeAlgr[=][int]"
													title="手续费算法" width="19%" style="text-align:left;"
													editTemplate="ecs_t_commodityFeeAlgrMap">
													<c:set var="commodityFeeAlgrKey">
														<c:out value="${commodityFee.feeAlgr}"></c:out>
													</c:set>
		  										${commodityFeeAlgrMap[commodityFeeAlgrKey]}
           										</ec:column>
												<ec:column property="primary.feeRate[=][bigdecimal]"
													title="手续费系数" width="19%" style="text-align:right; "
													value="${commodityFee.feeRate_log}" filterable="false"/>
												<ec:column property="primary.mkt_FeeRate[=][bigdecimal]"
													title="交易所收取手续费" width="19%" style="text-align:right; "
													value="${commodityFee.mkt_FeeRate_log}" filterable="false"/>
												<ec:column property="primary.feeMode[=][int]" title=" 收取方式"
													width="19%" style="text-align:left;"
													editTemplate="ecs_t_commodityFeeModeMap">
													<c:set var="commodityFeeModeKey">
														<c:out value="${commodityFee.feeMode}"></c:out>
													</c:set>
											  		${commodityFeeModeMap[commodityFeeModeKey]}
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
		<!-- 编辑和过滤所使用的商品手续费算法模板 -->
		<textarea id="ecs_t_commodityFeeAlgrMap" rows="" cols=""
			style="display: none">
		<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="primary.feeAlgr[=][String]">
			<ec:options items="commodityFeeAlgrMap" />
		</select>
	    </textarea>
		<!-- 编辑和过滤所使用的商品收取方式模板 -->
		<textarea id="ecs_t_commodityFeeModeMap" rows="" cols=""
			style="display: none">
		<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="primary.feeMode[=][int]">
			<ec:options items="commodityFeeModeMap" />
		</select>
	    </textarea>

		<!-- 编辑和过滤所使用的默认交易商模板 -->
		<textarea id="ecs_t_firmDis" rows="" cols="" style="display: none">
		<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="primary.firmId[like]">
			<ec:options items="firmDisMap" />
		</select>
	    </textarea>
		<SCRIPT type="text/javascript">
		
		function update(firmId,commodityId){
			var url = "${basePath}/commodity/commodityFee/forwardUpdate.action?obj.commodityId="+commodityId+"&obj.firmId="+firmId;
			
			ecsideDialog(url,"",580,400);
		}
		function submitMember(){
			frm.submit();
		}
		</SCRIPT>
	</body>
</html>