<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>提前交收列表</title>
		<SCRIPT type="text/javascript">
		<!-- 
			//申请跳转
			function addAheadSettleForward(){
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
			
			//仓单详情查看跳转
			function showBillInfo(applyId){
				//获取配置权限的 URL
				var showUrl="/timebargain/aheadSettle/getBillListByApplyId.action";
				//获取完整跳转URL
				var url = "${basePath}"+showUrl+"?entity.applyId="+applyId;
				//弹出添加页面
				showDialog(url, "", 700, 550);
			}
			
			
			//审核提前交收信息跳转
			function auditAheadSettleForward(applyId){
				//获取配置权限的 URL
				var auditUrl = "/timebargain/aheadSettle/aheadSettleAuditForward.action"
				//获取完整跳转URL
				var url = "${basePath}"+auditUrl+"?entity.applyId="+applyId;
				//弹出修改页面
				if(showDialog(url, "", 700, 400)){
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
							<form name="frm" action="${basePath}/timebargain/aheadSettle/aheadSettleList.action?sortColumns=order+by+applyID" method="post">
								<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
									<tr>
										<td class="table5_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0" cellpadding="0" style="width: 100%;">
													<tr>
														<td align="left" style="width:300px;height:40px;padding:0px;line-height:40px;color:#000;">
															卖方二级代码:&nbsp;
															<label>
																<input type="text" class="input_text" id="customerId_S" name="${GNNT_}primary.customerId_S[=][String]" value="${oldParams['primary.customerId_S[=][String]']}" />
															</label>
														</td>
														<td align="left" style="width:300px;height:40px;padding:0px;line-height:40px;color:#000;">
															买方二级代码:&nbsp;
															<label>
																<input type="text" class="input_text" id="customerId_B" name="${GNNT_}primary.customerId_B[=][String]" value="${oldParams['primary.customerId_B[=][String]'] }" />
															</label>
														</td>
													</tr>
													<tr>
														<td align="left" style="width:300px;height:40px;padding:0px;line-height:40px;color:#000;">
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;商品代码:&nbsp;
															<label>
																<input type="text" class="input_text" id="commodityId" name="${GNNT_}primary.commodityId[=][String]" value="${oldParams['primary.commodityId[=][String]'] }"/>
															</label>
														</td>
														<td align="left" style="width:300px;height:40px;padding:0px;line-height:40px;color:#000;">
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;状态:&nbsp;
															<label>
																<select id="status" name="${GNNT_}primary.status[=][int]"  class="normal" style="width: 120px">
																	<option value="">全部</option>
																	<c:forEach items="${audit_statusMap}" var="map">
																		<option value="${map.key}">${map.value}</option>
																	</c:forEach>
																	
																</select>
															</label>
															 <script >
																frm.status.value = "<c:out value='${oldParams["primary.status[=][int]"] }'/>";
					  										</script>
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
						<div class="div_gn">
							<rightButton:rightButton name="申请" onclick="addAheadSettleForward();" className="anniu_btn" action="/timebargain/aheadSettle/addAheadSettleforward.action" id="add"></rightButton:rightButton>
						</div>
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
								<tr>
									<td>
										<ec:table items="pageInfo.result" var="applyAheadSettle" 
											action="${basePath}/timebargain/aheadSettle/aheadSettleList.action?sortColumns=order+by+applyID"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="demo.xls" csvFileName="demo.csv"
											showPrint="true" listWidth="150%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
												<ec:column property="applyId" title="申请单号" width="5%" style="text-align:center;"/>
												<ec:column property="commodityId" title="商品代码" width="5%" style="text-align:center;"/>
												<ec:column property="customerId_S" title="卖方二级代码" width="9%" ellipsis="true" style="text-align:center;"/>
												<ec:column property="customerId_B" title="买方二级代码" width="9%" ellipsis="true" style="text-align:center;"/>
												<ec:column property="price" title="交收价格" width="6%" style="text-align:center;">
												<c:if test="${applyAheadSettle.price!=0}"><fmt:formatNumber value="${applyAheadSettle.price}" pattern="#0.00"/></c:if>
												<c:if test="${applyAheadSettle.price==0}">订立价交收</c:if>
												</ec:column>
												<ec:column property="quantity" title="交收数量" width="5%" style="text-align:center;"/>
												<ec:column property="status" title="状态" width="6%" style="text-align:center;">
												<c:forEach items="${audit_statusMap}" var="result">
													<c:if test="${applyAheadSettle.status==result.key }">${result.value }</c:if>
												</c:forEach>
												</ec:column>
												<ec:column property="createTime" title="创建时间" width="8%" style="text-align:center;">
												<fmt:formatDate value="${applyAheadSettle.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
												</ec:column>
												<ec:column property="creator" title="创建人" width="6%" style="text-align:center;" ellipsis="true"/>
												<ec:column property="remark1" title="创建人备注" width="7%" style="text-align:center;" ellipsis="true"/>
												<ec:column property="modifyTime" title="修改时间" width="8%" style="text-align:center;">
												<fmt:formatDate value="${applyAheadSettle.modifyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
												</ec:column>
												<ec:column property="modifier" title="最后修改人" width="5%" style="text-align:center;"/>
												<ec:column property="remark2" title="修改人备注" width="7%" style="text-align:center;"/>
												<ec:column property="_1" title="查看仓单" width="4%" style="text-align:center;" sortable="false">
												<c:if test="${applyAheadSettle.status!=3}">
												<a href="#" onclick="javescript:showBillInfo('${applyAheadSettle.applyId }');">仓单详情</a>
												</c:if>
												<c:if test="${applyAheadSettle.status==3}">
												--
												</c:if>
												</ec:column>
												<ec:column property="_2" title="审核" width="3%" style="text-align:center;" sortable="false">
												<c:if test="${applyAheadSettle.status==1}">
												<a href="#" onclick="javescript:auditAheadSettleForward('${applyAheadSettle.applyId }');">审核</a>
												</c:if>
												<c:if test="${applyAheadSettle.status!=1}">
												--
												</c:if>
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