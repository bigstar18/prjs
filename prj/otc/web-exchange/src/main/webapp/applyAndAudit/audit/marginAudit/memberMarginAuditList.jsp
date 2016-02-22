<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>审核</title>
		<%@ include file="/public/ecsideLoad.jsp"%>
	</head>
	<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>
						&nbsp;
						<div class="div_cxtj">
							<img src="<%=skinPath%>/cssimg/13.gif" />
							&nbsp;&nbsp;查询条件${applyType}
						</div>
						<div class="div_tj">
							<form name="myForm"
								action="${basePath}/audit/memberMarginAudit/auditList.action"
								method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td height="60">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td class="table3_td" align="right">
															商品名称:&nbsp;
														</td>
														<td class="table3_td2">
															<label>
															 <input type="text" id="name" name="${GNNT_}commodityName[like]" value="${oldParams['commodityName[like]'] }"/>
															</label>
														</td>
														<td class="table3_td" align="right">
															&nbsp;
														</td>
														<td class="table3_td2">
															<label>
																&nbsp;
															</label>
														</td>
														<td class="table3_td" align="right">
															<button class="btn_sec" onclick="submitSelect()">
																查询
															</button>
														</td>
														<td class="table3_td2" align="left">
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
										<ec:table items="list"
											autoIncludeParameters="${empty param.autoInc}" var="obj"
											action="${basePath}/audit/memberMarginAudit/auditList.action"
											title="" minHeight="460" listWidth="100%" height="460px"
											resizeColWidth="true" retrieveRowsCallback="limit"
											sortRowsCallback="limit" filterRowsCallback="limit" >
											<ec:row ondblclick="doubleClick(${obj.id})">
												<ec:column property="id[=][long]" title="申请流水号" width="20%"
													style="text-align:left; " value="${obj.id}" />
												<ec:column property="commodityName[like]" title="商品名称"
													width="20%" style="text-align:left;"
													value="${obj.commodityName}" />
												<ec:column property="name[like]" title="类型" width="20%"
													style="text-align:left;" value="${obj.firmName}" />
												<ec:column property="statusDiscribe[like]" title="状态"
													width="20%" style="text-align:left;"
													value="${obj.statusDiscribe}" />
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
	</body>
	<script type="text/javascript">
function doubleClick(id) {
	var url = "${basePath}/audit/memberMarginAudit/auditDetails.action?applyId="
			+ id;
	ecsideDialog(url, window, 600, 500);
}
function submitSelect() {
	var vaild = window.confirm("您确定要操作吗？");
		if(vaild==true){
			myForm.submit();
	    }else{
           return false;
	    }
}
</script>
</html>