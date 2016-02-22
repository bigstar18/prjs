<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title>特别会员风险阈值列表</title>
		<%@ include file="/public/ecsideLoad.jsp"%>
	</head>
	<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<div class="div_tj">
						<form name="frm"
								action="${basePath}/account/specialThreshold/list.action?sortName=primary.id"
								method="post">
							<table border="0" cellpadding="0" cellspacing="0"
								class="table2_style">
								<tr>
									<td class="table2_td_widthmid">
										<div class="div2_top">
											<table class="table3_style" border="0" cellspacing="0"
												cellpadding="0">
												<tr>
													<td class="table3_td_1mid" align="left">
														特别会员编号:&nbsp;
														<label>
															<input type="text" class="input_text" id="firmId" name="${GNNT_}primary.firmId[like]"
																value="${oldParams['primary.firmId[like]'] }">
														</label>
													</td>
													<td class="table3_td_1mid" align="left">
														特别会员名称:&nbsp;
														<label>
															<input type="text" class="input_text" id="firmName" name="${GNNT_}name[like]"
																value="${oldParams['name[like]'] }">
														</label>
													</td>
													<td class="table3_td_anniu" align="left">
														<button  class="btn_sec" onclick="select1()" name="selectCustomer">查询</button>&nbsp;&nbsp;
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
											var="specialThreshold"
											action="${basePath}/account/specialThreshold/list.action?sortName=primary.id"
											minHeight="345" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="导出列表.csv" style="table-layout:fixed">

											<ec:row recordKey="specialThreshold.firmId">
												<ec:column width="4%" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												<ec:column property="primary.firmId[like]" title="特别会员编号" style="text-align:left; " width="19%" ellipsis="true"><a href="#" class="blank_a" onclick="return update('${specialThreshold.firmId}');"><font color="#880000">${specialThreshold.firmId}</font></a></ec:column>
												<ec:column property="specialMember.name[like]" title="特别会员名称"
													style="text-align:left;overflow:hidden;text-overflow:ellipsis" width="19%" ellipsis="true"
													value="${specialThreshold.memberName}" tipTitle="${specialThreshold.memberName}"/>
												<ec:column property="primary.minRiskFund[=][bigdecimal]" title="特别会员出金阈值"
													style="text-align:right;" width="17%">
													<fmt:formatNumber value="${specialThreshold.minRiskFund}"  pattern="###,##0.00"/>
													</ec:column>
												<ec:column property="primary.warnTh[=][bigdecimal]" title="特别会员预警风险率"
													style="text-align:right;" width="19%"
													value="${specialThreshold.warnTh_v}%" />
												<ec:column property="primary.frozenTh[=][bigdecimal]" title="特别会员冻结风险率"
													style="text-align:right;" width="19%"
													value="${specialThreshold.frozenTh_v}%" />
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
			<input type="text" class="inputtext" value=""
				onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="" />
		</textarea>
		<script type="text/javascript">

function update(id) {
	var url = "${basePath}/account/specialThreshold/forwardUpdate.action?obj.firmId="
			+ id;
	ecsideDialog(url,"",600,390);
}
function select1() {
	frm.submit();
}
</script>
	</body>
</html>