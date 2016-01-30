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

		function info(deductId){
			var url = "${basePath}/timebargain/deduct/deductInfoByDeductId.action?deductId="+deductId;
			showDialog(url, "", 600, 450);
		}
		function keep(deductId){
			var url = "${basePath}/timebargain/deduct/deductKeepFirmByDeductId.action?deductId="+deductId;
			showDialog(url, "", 550, 400);
		}
		function detail(deductId){
			var url = "${basePath}/timebargain/deduct/deductDetailByDeductId.action?deductId="+deductId;
			showDialog(url, "", 900, 430);
		}
		function operate(deductId){
			frm.deductId.value=deductId;
			var url="${basePath}/timebargain/deduct/updateDeductPositionForward.action";
			frm.action=url;
			frm.submit();
		}
		//执行查询列表
		function dolistquery() {
			frm.submit();
		}
		

		
		//-->
		</SCRIPT>
	</head>
	<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
							<form name="frm" id="frm"  action="${basePath}/timebargain/deduct/deducPositionList.action"  method="post" targetType="hidden">
							<input id="deductId" name="entity.deductId"  type="hidden"/>
							<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
									<tr>
										<td class="table5_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td class="table3_td_1" align="left">
															&nbsp;&nbsp;&nbsp;强减日期:&nbsp;
															<label>
																<input type="text" style="width: 120px" id="dedcutDate"
																	class="wdate" maxlength="10"
																	name="${GNNT_}primary.deductDate[=][date]"
																	value='${oldParams["primary.deductDate[=][date]"]}'
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
															</label>
														</td>
														<td class="table3_td_anniu" align="left">
															<button class="btn_sec" id="view" onclick=dolistquery();>查询</button>
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
							<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
								<tr>
									<td>
										<ec:table items="pageInfo.result" var="deduct"
											action="${basePath}/timebargain/deduct/deducPositionList.action"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="deduct.xls" csvFileName="deduct.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
												<ec:column property="deductDate" width="15%" title="强减日期" style="text-align:center;">
													<fmt:formatDate value="${deduct.deductDate}" pattern="yyyy-MM-dd" />
												</ec:column>
												<ec:column property="commodityId" title="商品代码" width="10%" style="text-align:center;" />
												<ec:column property="deductStatus" title="强减状态" width="10%" style="text-align:center;" >
												${deductStatusMap[deduct.deductStatus] }
												</ec:column>
												<ec:column property="loserMode" title="亏损方强减模式" width="15%" style="text-align:center;" >
												${loserModeMap[deduct.loserMode] }
												</ec:column>
												<ec:column property="lossRate" title="亏损比例" width="10%" style="text-align:center;" />
												<ec:column property="_" title="详细信息" width="10%" style="text-align:center;" >
													<%-- <a href="#" onclick="info('<c:out value="${deduct.deductId}"/>')"><img title="查看" src="<%=skinPath%>/image/app/timebargain/deduct/info.gif" /></a>--%>
													<rightHyperlink:rightHyperlink text="<img title='查看' src='${skinPath}/image/app/timebargain/deduct/info.gif'/>" href="#" onclick="info('${deduct.deductId}')" action="/timebargain/deduct/deductInfoByDeductId.action"/> 
										  		</ec:column>
												<ec:column property="_" title="客户保留" width="10%" style="text-align:center;" >
													<%-- <a href="#" onclick="keep('<c:out value="${deduct.deductId}"/>')"><img title="查看" src="<%=skinPath%>/image/app/timebargain/deduct/customer.gif" /></a>--%>
													<rightHyperlink:rightHyperlink text="<img title='查看' src='${skinPath}/image/app/timebargain/deduct/customer.gif'/>" href="#" onclick="keep('${deduct.deductId}')" action="/timebargain/deduct/deductKeepFirmByDeductId.action"/>
  												</ec:column>
  												<ec:column property="_" title="强减明细" width="10%" style="text-align:center;" >
	  												<c:choose>
														<c:when test="${deduct.deductStatus eq 'N'}">
															<img title="未运行" src="<%=skinPath%>/image/app/timebargain/deduct/oldDetail.gif" />
														</c:when>
														<c:otherwise>
															<%-- <a href="#" onclick="detail('<c:out value="${deduct.deductId}"/>')"><img title="查看" src="<%=skinPath%>/image/app/timebargain/deduct/detail.gif" /></a>--%>
															<rightHyperlink:rightHyperlink text="<img title='查看' src='${skinPath}/image/app/timebargain/deduct/detail.gif'/>" href="#" onclick="detail('${deduct.deductId}')" action="/timebargain/deduct/deductDetailByDeductId.action"/>
														</c:otherwise>
													</c:choose>
  												</ec:column>
												<ec:column property="_" title="强减操作" width="10%" style="text-align:center;" >
													<c:choose>
														<c:when test="${deduct.deductStatus eq 'Y'}">
															<img title="已运行" src="<%=skinPath%>/image/app/timebargain/deduct/right.gif" />
														</c:when>
														<c:otherwise>
															<%-- <a href="#" onclick="operate('<c:out value="${deduct.deductId}"/>')"><img title="操作" src="<%=skinPath%>/image/app/timebargain/deduct/point.gif" /></a>--%>
															<rightHyperlink:rightHyperlink text="<img title='操作' src='${skinPath}/image/app/timebargain/deduct/point.gif'/>" href="#" onclick="operate('${deduct.deductId}')" action="/timebargain/deduct/updateDeductPositionForward.action"/>
														</c:otherwise>
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
