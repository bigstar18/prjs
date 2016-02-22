<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>默认延期费设置列表</title>
		<%@ include file="/public/ecsideLoad.jsp"%>
	</head>
	<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<div class="div_tj">
						<form name="frm"
								action="${basePath}/commodity/tcDelayFee/list.action"
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
															<input type="text"
																class="input_text" id="name" name="${GNNT_}commodity.name[like]"
																value="${oldParams['commodity.name[like]'] }">
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

						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%">
								<tr>
									<td>
										<ec:table items="resultList"
											autoIncludeParameters="${empty param.autoInc}"
											var="tcDelayFee"
											action="${basePath}/commodity/tcDelayFee/list.action"
											minHeight="345" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="导出列表.csv" style="table-layout:fixed">

											<ec:row recordKey="tcDelayFee.memberNo">
												<ec:column width="4%" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												<ec:column property="commodity.name[like]" title="商品名称" filterable="false" width="10%" style="text-align:left;" ellipsis="true"> <a href="#" class="blank_a" onclick="return update('${tcDelayFee.memberNo}','${tcDelayFee.commodityId}');"><font color="#880000">${tcDelayFee.commodityName }</font></a></ec:column>
												<ec:column property="primary.firmId[like]" title="类型" width="10%"
													style="text-align:left;" editTemplate="ecs_t_memberNameType">
													<c:set var="memberName">
														<c:out value="${tcDelayFee.memberNo}"></c:out>
													</c:set>
							  							${firmDisMap_v[memberName]}
							  						</ec:column>
							  						<ec:column property="primary.feeAlgr_v[=][int]"
													title="延期费算法" width="10%" style="text-align:left;"
													editTemplate="ecs_t_commodityFeeAlgrMap">
													<c:set var="commodityFeeAlgrKey">
														<c:out value="${tcDelayFee.feeAlgr_v}"></c:out>
													</c:set>
		  											${commodityFeeAlgrMap[commodityFeeAlgrKey]}
           											</ec:column>
												<c:forEach var="ta" begin="1" end="${total}" step="1">
													<c:set var="step" value="stepNO${ta}"></c:set>
													<c:set var="mkt_step" value="mkt_stepNO${ta}"></c:set>
													<c:forEach var="fundder" items="${ladderList}">
															<c:if test="${fundder.stepNo==ta}">
																<c:set var="note" value="${fundder.note}"></c:set>
															</c:if>
													</c:forEach>
													<ec:column property="stepNO${ta}[=]" title="${note}的延期费率"
														style="text-align:right;" filterable="false" sortable="false" width="15%">
													${tcDelayFee[step]}
													<c:if test="${commodityFeeAlgrKey==1}">%</c:if>
													</ec:column>		
													<ec:column property="mkt_stepNO${ta}[=]" title="${note}的交易所收取延期费"
														style="text-align:right;" filterable="false" sortable="false" width="18%">
													${tcDelayFee[mkt_step]}
													<c:if test="${commodityFeeAlgrKey==1}">%</c:if>
													</ec:column>
													</c:forEach>
											</ec:row>
										</ec:table>
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

		<!-- 编辑和过滤所使用的会员类型模板 -->
		<textarea id="ecs_t_memberNameType" rows="" cols="" style="display: none">
		<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="memberNo[like]">
			<ec:options items="firmDisMap_v" />
		</select>
	    </textarea>
					</td>
				</tr>
			</table>
			</div>
			
		<script type="text/javascript">
function update(memberNo,commodityId) {
	var url = "${basePath}/commodity/tcDelayFee/forwardUpdate.action?obj.firmId="+memberNo+"&obj.commodityId="+commodityId;
	ecsideDialog(url,"",580,400);
}
function select1() {
	frm.submit();
}

</script>
	</body>
</html>