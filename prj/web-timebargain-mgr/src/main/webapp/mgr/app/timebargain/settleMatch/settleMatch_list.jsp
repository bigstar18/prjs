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

		//添加信息跳转
		function addSettleMatch(){
			//获取配置权限的 URL
			var addUrl=document.getElementById('add').action;
			//获取完整跳转URL
			var url = "${basePath}"+addUrl;
			//弹出添加页面
			if(showDialog(url, "", 900, 700)){
				//如果添加成功，则刷新列表
				document.getElementById("view").click();
			}
		}

		function view(id){
			var url = "${basePath}/timebargain/applyGage/viewById.action?entity.applyId="+id;
			if(showDialog(url, "", 600, 480)){
				frm.submit();
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
							<form name="frm" id="frm"  action="${basePath}/timebargain/settleMatch/listSettleMatch.action?sortColumns=order+by+createTime+desc"  method="post" >
							<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
									<tr>
										<td class="table5_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td class="table3_td_1" align="left">
															买方代码:&nbsp;
															<label>
															    <input type="text" class="input_text" id="firmID_B" name="${GNNT_}primary.firmID_B[allLike][String]" value="${oldParams['primary.firmID_B[allLike][String]'] }"  maxlength="<fmt:message key='MFirm.firmId_q' bundle='${PropsFieldLength}' />"/>
															</label>
														</td>
														<td class="table3_td_1" align="left">
															卖方代码:&nbsp;
															<label>
															    <input type="text" class="input_text" id="firmID_S" name="${GNNT_}primary.firmID_S[allLike][String]" value="${oldParams['primary.firmID_S[allLike][String]'] }"  maxlength="<fmt:message key='MFirm.firmId_q' bundle='${PropsFieldLength}' />"/>
															</label>
														</td>
														<td class="table3_td_1" align="left">
															&nbsp;&nbsp;&nbsp;商品代码:&nbsp;
															<label>
																 <input type="text" class="input_text" id="commodityId" name="${GNNT_}primary.commodityId[allLike][String]" value="${oldParams['primary.commodityId[allLike][String]'] }"  maxlength="10"/>
															</label>
														</td></tr>
														<tr>
														<td class="table3_td_1" align="left">
															&nbsp;&nbsp;&nbsp;状态:&nbsp;
															<label>
																<select id="status" name="${GNNT_}primary.status[=][int]"  class="normal" style="width: 120px">
																	<option value="">全部</option>
																	<c:forEach items="${settleMatch_statusMapM}" var="map">
																		<option value="${map.key}">${map.value}</option>
																	</c:forEach>
																</select>
															</label>
														</td>
														 <script >
																frm.status.value = "<c:out value='${oldParams["primary.status[=][int]"] }'/>";
					  									</script>
					  									<td class="table3_td_1" align="left">
															&nbsp;&nbsp;&nbsp;执行结果:&nbsp;
															<label>
																<select id="result" name="${GNNT_}primary.result[=][int]"  class="normal" style="width: 120px">
																	<option value="">全部</option>
																	<c:forEach items="${settleMatch_resultMapM}" var="map">
																		<option value="${map.key}">${map.value}</option>
																	</c:forEach>
																</select>
															</label>
														</td>
														 <script >
																frm.result.value = "<c:out value='${oldParams["primary.result[=][int]"] }'/>";
					  									</script>
														<td class="table3_td_1" align="left">
															&nbsp;&nbsp;&nbsp;交收类型:&nbsp;
															<label>
																<select id="settleType" name="${GNNT_}primary.settleType[=][int]"  class="normal" style="width: 120px">
																	<option value="">全部</option>
																	<c:forEach items="${settleMatch_settleTypeMapM}" var="map">
																		<option value="${map.key}">${map.value}</option>
																	</c:forEach>
																</select>
															</label>
														</td>
														<script >
																frm.settleType.value = "<c:out value='${oldParams["primary.settleType[=][int]"] }'/>";
					  									</script>
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
						<div class="div_gn">
							<rightButton:rightButton name="添加" onclick="addSettleMatch();" className="anniu_btn" action="/timebargain/settleMatch/forwardAddSettleMatch.action" id="add"></rightButton:rightButton>
						</div>
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
								<tr>
									<td>
										<ec:table items="pageInfo.result" var="settleMatch"
											action="${basePath}/timebargain/settleMatch/listSettleMatch.action?sortColumns=order+by+createTime+desc"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="settleMatch.xls" csvFileName="settleMatch.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
												<ec:column property="matchId" title="配对编号"  width="10%" style="text-align:center;" />
												<ec:column property="commodityId" title="商品代码" width="10%" style="text-align:center;" />
												<ec:column property="quantity" title="交收数量" width="10%" style="text-align:center;" />
												<ec:column property="firmID_B" title="买方交易商代码" width="10%" ellipsis="true" style="text-align:center;" />
												<ec:column property="firmID_S" title="卖方交易商代码" width="10%" ellipsis="true" style="text-align:center;" />
												<ec:column property="status" title="状态" width="10%" style="text-align:center;" >
												${settleMatch_statusMapM[settleMatch.status] }
												</ec:column>
												<ec:column property="result" title="执行结果" width="10%" style="text-align:center;" >
												${settleMatch_resultMapM[settleMatch.result] }
												</ec:column>
												<ec:column property="settleType" title="交收类型" width="10%" style="text-align:center;" >
												${settleMatch_settleTypeMapM[settleMatch.settleType] }
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
