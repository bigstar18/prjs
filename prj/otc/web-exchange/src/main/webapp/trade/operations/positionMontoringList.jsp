<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type='text/javascript' src='<%=basePath%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=basePath%>/dwr/util.js'></script>
<script type="text/javascript" src='<%=basePath%>/dwr/interface/positionMon.js'></script>

<html>
	<head>
		<title>系统用户浏览</title>
		<%@ include file="/public/ecsideLoad.jsp"%>
<script type="text/javascript">
</script>
	</head>
	<body onload="">
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<div class="div_tj">
						<form name="frm"
								action="${basePath}/tradeManage/positionMonitoring/list.action"
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
														交易商:&nbsp;
														<label>
															<input type="text" class="input_text"
																name="${GNNT_}primary.id[like]" id="firmId"
																value="${oldParams['primary.id[like]'] }" />
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
											var="firmUpdate"
											action="${basePath}/tradeManage/positionMonitoring/list.action"
											minHeight="345" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="导出列表.csv"  style="table-layout:fixed">
											<ec:row recordKey="${commodity.id}">
												<ec:column width="5%" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												<ec:column property="primary.id[like]" title="交易商" width="10%"
													style="text-align:left; " value="${firmUpdate.firmId }">
												</ec:column>
												<ec:column property="name[like]" title="HOLDNO" width="10%"
													style="text-align:left;" value="${firmUpdate.holdNo}" />
												<ec:column property="name[like]" title="HOLDQTY" width="10%"
													style="text-align:left;" value="${firmUpdate.holdQty}" />
												<ec:column property="name[like]" title="OLDFLAG" width="10%"
													style="text-align:left;" value="${firmUpdate.oldFlag}" />
												<ec:column property="name[like]" title="NEWFLAG" width="10%"
													style="text-align:left;" value="${firmUpdate.newFlag}" />
												<ec:column property="name[like]" title="HOST" width="25%"
													style="text-align:left;" value="${firmUpdate.host}" />
												<ec:column property="name[like]" title="IP" width="10%"
													style="text-align:left;" value="${firmUpdate.ip}" />
												<ec:column property="primary.modifyTime[=][Date]" width="10%"
													title="修改时间" style="text-align:left;"
													value="${datefn:formatdate(firmUpdate.modifyTime)}"
													filterable="false">
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

		<!-- 编辑和过滤所使用的交易状态模板 -->
		<textarea id="ecs_t_status" rows="" cols="" style="display: none">
			<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="status[=][int]">
				<ec:options items="commodityStatusMap" />
			</select>
	    </textarea>
		<SCRIPT type="text/javascript">
		function update(id){
			var url="${basePath}/tradeManage/positionMonitoring/update.action?obj.id="+id;
			ecsideDialog(url,"",580,235);
		}
		function select1(){
			frm.submit();
		}
		</SCRIPT>
	</body>
</html>

<%@ include file="/public/footInc.jsp"%>