<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<link rel="stylesheet"
		href="${skinPath }/css/validationengine/validationEngine.jquery.css"
		type="text/css" />
	<link rel="stylesheet"
		href="${skinPath }/css/validationengine/template.css" type="text/css" />
	<script src="${publicPath }/js/jquery-1.6.min.js"
		type="text/javascript">
	
</script>
	<script
		src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js"
		type="text/javascript" charset="GBK">
	
</script>
	<script
		src="${publicPath }/js/validationengine/jquery.validationEngine.js"
		type="text/javascript" charset="GBK"></script>
	
	<head>
		<SCRIPT type="text/javascript">
		//执行查询列表
		function dolistquery() {
			frm.submit();
		}
		
		function check_transfer(id){
			//获取完整跳转URL
			var url = "${basePath}/timebargain/transfer/transferAuditForward.action?entity.transferID=" + id;
			//弹出修改页面
			if(showDialog(url, "", 450, 300)){
				//如果修改成功，则刷新列表
				ECSideUtil.reload("ec");
			}
		}
		</SCRIPT>
	</head>
	<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
					
						<div class="div_cx">
							<form name="frm" action="${basePath}/timebargain/transfer/transferAudit.action?sortColumns=order+by+transferID+desc" method="post">
								<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
									<tr>
										<td class="table5_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td class="table3_td_1" align="left">
															&nbsp;&nbsp;&nbsp;商品代码:&nbsp;
															<label>
																<input type="text" class="input_text" id="commodityID"  checked="checked" name="${GNNT_}primary.commodityID[=][String]" value="${oldParams['primary.commodityID[=][String]']}" maxLength="50"/>
															</label>
														</td>
														<td class="table3_td_anniu" align="left">
															<button class="btn_sec" id="view" onclick=dolistquery();>查询</button>
															&nbsp;&nbsp;
															<button class="btn_cz" onclick="myReset();">重置</button>
														</td>
													</tr>
												</table>
											</div>
										</td>
									</tr>
								</table>
							</form>
						</div>
						<!-- 
						<c:forEach items="${BS_flagMap}" var="result">
							${result }
						</c:forEach>
						 
						<div class="div_gn">
							<rightButton:rightButton name="添加" onclick="addTransferForward();" className="anniu_btn" action="/timebargain/transfer/addTransferforward.action" id="add"></rightButton:rightButton>
							&nbsp;&nbsp;
							
							<rightButton:rightButton name="删除" onclick="delateTransferList();" className="anniu_btn" action="/timebargain/transfer/delateTransfer.action" id="delate"></rightButton:rightButton>
						
						</div>
						-->
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
								<tr>
									<td>
										<ec:table items="pageInfo.result" var="transfer"
											action="${basePath}/timebargain/transfer/transferAudit.action"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="deduct.xls" csvFileName="deduct.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
												<ec:column property="commodityID" title="商品代码" width="15%" style="text-align:center;" />
												<ec:column property="customerid_s" title="过户人二级代码" width="15%" style="text-align:center;" />
												<ec:column property="bs_flag" title="持仓方向" width="10%" style="text-align:center;" >
												<c:forEach items="${BS_flagMap}" var="result">
													<c:if test="${transfer.bs_flag==result.key }">${result.value }</c:if>
												</c:forEach>
												</ec:column>
												<ec:column property="type" title="过户类型" width="10%" style="text-align:center;" >
												<c:forEach items="${Transfer_typeMap}" var="result">
													<c:if test="${transfer.type==result.key }">${result.value }</c:if>
												</c:forEach>
												</ec:column>
												<ec:column property="quantity" title="过户数量" width="10%" style="text-align:center;" />
												<ec:column property="customerid_b" title="接受人二级代码" width="15%" style="text-align:center;" />
												<ec:column property="status" title="审核状态" width="10%" style="text-align:center;" >
												<c:forEach items="${Transfer_statusMap}" var="result">
													<c:if test="${transfer.status==result.key }">${result.value }</c:if>
												</c:forEach>
												</ec:column>
												<ec:column property="_1" title="审核与查看"  width="15%" style="text-align:center;">
													<c:choose>
														<c:when test="${transfer.status == 0}">
															<a href="#" onclick="check_transfer('<c:out value="${transfer.transferID}"/>')" style="text-align:center;">
																<img  height="20" title="审核" src="<%=skinPath%>/image/app/timebargain/commodity.gif"/>
															</a>
														</c:when>
													</c:choose>
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
		<!-- 编辑和过滤所使用的 通用的文本框模板 -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;" name="" />
		</textarea>
	</body>
</html>
