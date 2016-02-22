<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@page import="java.util.*"%>
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
								action="${basePath}/commodity/baseInfo/list.action"
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
														商品代码:&nbsp;
														<label>
															<input type="text" class="input_text"
																name="${GNNT_}primary.id[like]" id="commodityId"
																value="${oldParams['primary.id[like]'] }" />
														</label>
													</td>
													<td class="table3_td_1" align="left">
														商品名称:&nbsp;
														<label>
															<input type="text" class="input_text"
																name="${GNNT_}name[like]"
																id="commodityName" value="${oldParams['name[like]'] }" />
														</label>
													</td>
													<td class="table3_td_anniu" align="left">														
														<button  class="btn_sec" onclick="select1()">查询</button>&nbsp;&nbsp;
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
							<button class="anniu_btn" onclick="addCommodity()" id="add">
								添加
							</button>
							&nbsp;&nbsp;
							<button class="anniu_btn" onclick="deleteCommodity()" id="delete">
								删除
							</button>
						</div>

						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%">
								<tr>
									<td>
										<ec:table items="resultList"
											autoIncludeParameters="${empty param.autoInc}"
											var="commodity"
											action="${basePath}/commodity/baseInfo/list.action" title=""
											minHeight="345" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="导出列表.csv" style="table-layout:fixed">
											<ec:row recordKey="${commodity.id}">
												<ec:column width="4%" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												<ec:column property="primary.id[like]" title="商品代码" width="10%"
													style="text-align:left; ">
													<a href="#" class="blank_a"
														onclick="return update('${commodity.id}');"><font
														color="#880000">${commodity.id}</font>
													</a>
												</ec:column>
												<ec:column property="name[Like]" title="商品名称" width="10%"
													style="text-align:left;" value="${commodity.name}" ellipsis="true"/>
													<ec:column property="status[=][int]" title="商品状态" width="10%"
													style="text-align:left;" editTemplate="ecs_t_status">
													<c:set var="statusKey">
														<c:out value="${commodity.status}"></c:out>
													</c:set>
											  		${commodityStatusMap[statusKey]}
											  	</ec:column>
													<ec:column property="contractFactor[=][bigdecimal]" title="合约单位"
													width="10%" style="text-align:right;"
													value="${commodity.contractFactor}" />
												<%--
												<ec:column property="minHQMove[=][bigdecimal]" title="最小行情单位"
													width="10%" style="text-align:right;"
													value="${commodity.minHQMove}" />
												 --%>
												<ec:column property="minPriceMove[=][bigdecimal]" title="最小变动单位"
													width="10%" style="text-align:right;"
													value="${commodity.minPriceMove}" />
												<%--
												<ec:column property="stepMove[=][bigdecimal]" title="步进值"
													width="10%" style="text-align:right;"
													value="${commodity.stepMove}" />
												 --%>
												<ec:column property="marketDate[=][timestemp]" title="上市日期"
													width="10%" style="text-align:left;" value="${datefn:formatdate(commodity.marketDate)}">
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
		<!-- 编辑和过滤所使用的涨跌幅算法模板 -->
		<textarea id="ecs_t_spreadAlgr" rows="" cols="" style="display: none">
		<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="spreadAlgr[=][int]">
			<ec:options items="commoditySpreadAlgrMap" />
		</select>
	    </textarea>
		<!-- 编辑和过滤所使用的商品状态模板 -->
		<textarea id="ecs_t_status" rows="" cols="" style="display: none">
		<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="status[=][int]">
			<ec:options items="commodityStatusMap" />
		</select>
	    </textarea>
	    
	    <!-- 编辑和过滤所使用的延期费算法模板 -->
		<textarea id="ecs_t_fee" rows="" cols=""
			style="display: none">
		<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="delayFeeAlgr[=][int]">
			<ec:options items="delayFeeAlgrMap" />
		</select>
	    </textarea>

		<!-- 编辑和过滤所使用的交易模式模板 -->
		<textarea id="ecs_t_tradeMode" rows="" cols="" style="display: none">
		<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="tradeMode[like]">
			<ec:options items="commodityTradeModeMap" />
		</select>
	    </textarea>
		<SCRIPT type="text/javascript">
		function update(id){
			var url="${basePath}/commodity/baseInfo/forwardUpdate.action?obj.id="+id;
			ecsideDialog(url,window,580,550);
		}
		function select1(){
			frm.submit();
		}
		function addCommodity(){
			var url="${basePath}/tradeManage/notInMarket/forwardAdd.action";
			ecsideDialog(url,window,580,500);
		}
		function deleteCommodity(){
			frm.action = "${basePath}/tradeManage/notInMarket/delete.action";
			frm.submit();
		}
		
		</SCRIPT>
	</body>
</html>
