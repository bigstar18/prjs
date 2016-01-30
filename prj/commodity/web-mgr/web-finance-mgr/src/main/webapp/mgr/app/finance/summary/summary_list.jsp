<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>摘要列表</title>
		<SCRIPT type="text/javascript">
		<!-- 
			//添加信息跳转
			function addSummaryForward(){
				//获取配置权限的 URL
				var addUrl=document.getElementById('add').action;
				//获取完整跳转URL
				var url = "${basePath}"+addUrl;
				//弹出添加页面
				if(showDialog(url, "", 750, 350)){
					//如果添加成功，则刷新列表
					document.getElementById("view").click();
				}
			}
			//修改信息跳转
			function updateSummaryForward(code){
				//获取配置权限的 URL
				var updateUrl = document.getElementById('update'+customerId).action;
				//获取完整跳转URL
				var url = "${basePath}"+updateUrl;
				//给 URL 添加参数
				if(url.indexOf("?")>0){
					url += "&entity.code="+code;
				}else{
					url += "?entity.code="+code;
				}
				//弹出修改页面
				if(showDialog(url, "", 800, 550)){
					//如果修改成功，则刷新列表
					document.getElementById("view").click();
				};
			}
			//批量删除信息
			function deleteSummaryList(){
				//获取配置权限的 URL
				var deleteUrl = document.getElementById('delete').action;
				//获取完整跳转URL
				var url = "${basePath}"+deleteUrl+"?autoInc=false";
				//执行删除操作
				updateRMIEcside(ec.ids,url);
			}
			//查看信息详情
			function summaryDetails(code){
				var url = "${basePath}/finance/summary/updateSummaryforward.action?entity.summaryNo="+code;
				if(showDialog(url, "", 750, 350)){
					//如果修改成功，则刷新列表
					document.getElementById("view").click();
				};
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
						<div class="div_cx">
							<form name="frm" action="${basePath}/finance/summary/summaryList.action?sortColumns=order+by+summaryNo" method="post">
								<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
									<tr>
										<td class="table5_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td class="table3_td_1" align="left">
															摘 要 号:&nbsp;
															<label>
																<input type="text" class="input_text" id="summaryNo"  checked="checked" name="${GNNT_}primary.summaryNo[allLike]" value="${oldParams['primary.summaryNo[allLike]']}" maxLength="5"/>
															</label>
														</td>
														<td class="table3_td_1" align="left">
															摘要名称:&nbsp;
															<label>
																<input type="text" class="input_text" id="summary" name="${GNNT_}primary.summary[allLike]" value="${oldParams['primary.summary[allLike]'] }" maxLength="16"/>
															</label>
														</td>
														<td class="table3_td_anniu" align="left">
															<button class="btn_sec" id="view" onclick="dolistquery();">查询</button>
															&nbsp;&nbsp;
															<button class="btn_cz" onclick="myReset();">重置</button>
														</td>
													</tr>
													<tr>
														<td class="table3_td_1" align="left">
															归入总账:&nbsp;
															<label>
																<select id="ledgerFieldCode" name="${GNNT_}primary.ledgerField.code[=]"  class="normal" style="width: 120px">
																	<option value="">全部</option>
																	<c:forEach items="${ledgerFieldPage.result}" var="map">
																		<option value="${map.code}">${map.name}</option>
																	</c:forEach>
																</select>
															</label>
															 <script >
																frm.ledgerFieldCode.value = "<c:out value='${oldParams["primary.ledgerField.code[=]"] }'/>";
					  										</script>
														</td>
														<td class="table3_td_1" align="left" colspan="2">
															系&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;统:&nbsp;
															<label>
																<select id="ledgerFieldmoduleId" name="${GNNT_}primary.ledgerField.moduleId[=]"  class="normal" style="width: 120px">
																	<option value="">全部</option>
																	<c:forEach items="${tradeMoudelList}" var="map">
																		<option value="${map['MODULEID']}">${map["NAME"]}</option>
																	</c:forEach>
																	
																</select>
															</label>
															 <script >
																frm.ledgerFieldmoduleId.value = "<c:out value='${oldParams["primary.ledgerField.moduleId[=]"] }'/>";
					  										</script>
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
							<rightButton:rightButton name="添加" onclick="addSummaryForward();" className="anniu_btn" action="/finance/summary/addSummaryForward.action" id="add"></rightButton:rightButton>
							&nbsp;&nbsp;
							<rightButton:rightButton name="删除" onclick="deleteSummaryList();" className="anniu_btn" action="/finance/summary/deleteSummaryList.action" id="delete"></rightButton:rightButton>
						</div>
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
								<tr>
									<td>
										<ec:table items="pageInfo.result" var="summary"
											action="${basePath}/finance/summary/summaryList.action?sortColumns=order+by+summaryNo"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="demo.xls" csvFileName="demo.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
												<ec:column cell="checkbox" headerCell="checkbox" alias="ids" style="text-align:center; " value="${summary.summaryNo}" width="5%" viewsAllowed="html" />
												<ec:column property="summaryNo" width="10%" title="摘要号" style="text-align:center;">
													<a href="#" class="blank_a" onclick="return summaryDetails('${summary.summaryNo}');"><font color="#880000">${summary.summaryNo}</font>&nbsp;</a>
												</ec:column>
												<ec:column property="summary" title="摘要名称" width="10%" style="text-align:center;" ellipsis="true"/>
												<ec:column property="ledgerField.name" title="归入总账" width="10%" style="text-align:center;">${summary.ledgerField.name }</ec:column>
												<ec:column property="fundDCFlag" title="资金借贷方向" width="10%" style="text-align:center;">${summary_fundDCFlagMap[summary.fundDCFlag]}</ec:column>
												<ec:column property="accountCodeOpp" title="对方科目代码" width="10%" style="text-align:center;"/>
												<ec:column property="appendAccount" title="附加账" width="10%" style="text-align:center;">${summary_appendAccountMap[summary.appendAccount]}</ec:column>
												<ec:column property="isInit" title="是否初始化" width="10%" style="text-align:center;">${summary_isInitMap[summary.isInit]}</ec:column>
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