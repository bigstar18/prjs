<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/broker/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>交易商列表</title>
		<SCRIPT type="text/javascript">
		//添加信息跳转
			function addBroAndFirmForward(){
				//获取配置权限的 URL
				var addUrl=document.getElementById('add').action;
				//获取完整跳转URL
				var url = "${basePath}"+addUrl;
				//弹出添加页面
				if(showDialog(url, "", 600, 250)){
					//如果添加成功，则刷新列表
					document.getElementById("view").click();
				}
			}
			//批量删除信息
			function delateBroAndFirm(){
				//获取配置权限的 URL
				var delateUrl = document.getElementById('delate').action;
				//获取完整跳转URL
				var url = "${basePath}"+delateUrl+"?autoInc=false";
				//执行删除操作
				updateRMIEcside(ec.ids,url);
			}
			
			function returnBrokerageList(){
				var url = "${basePath}/broker/brokerage/brokeragelist.action?sortColumns=order+by+brokerageid+asc";
				document.location.href = url;
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
							<form name="frm" action="${basePath}/broker/brokerage/managerFirm.action?brokerAgeId=${brokerAgeId }&sortColumns=order+by+brokerAgeId+asc" method="post">
								<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
									<tr>
										<td>
											<div class="warning">
												<div class="content">
													温馨提示 :居间商&nbsp;${brokerAgeId }&nbsp;所辖交易商信息
												</div>
											</div>
										</td>
									</tr>
									<tr>
										<td class="table5_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td class="table3_td_1" align="left">
															交易商代码:&nbsp;
															<label>
																<input type="text" class="input_text" id="firmId"  checked="checked" name="${GNNT_}primary.firmId[allLike]" value="${oldParams['primary.firmId[allLike]']}"/>
															</label>
														</td>
														<td class="table3_td_1" align="left">
															交易商名称:&nbsp;
															<label>
																<input type="text" class="input_text" id="name" name="${GNNT_}primary.mfirm.name[allLike]" value="${oldParams['primary.mfirm.name[allLike]']}" />
															</label>
														</td>
														<td class="table3_td_1" align="left">
															交易商类型:&nbsp;
															<label>
																<select id="type" name="${GNNT_}primary.mfirm.type[=][int]"   class="normal" style="width: 120px">
																	<option value="">全部</option>
																	<c:forEach items="${mfirmTypeMap}" var="map">
																		<option value="${map.key }">${map.value }</option>
																	</c:forEach>
																</select>
															</label>
															<script >
																frm.type.value = "<c:out value='${oldParams["primary.mfirm.type[=][int]"] }'/>"
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
							<rightButton:rightButton name="添加" onclick="addBroAndFirmForward();" className="anniu_btn" action="/broker/brokerage/managerFirmForward.action?brokerAgeId=${brokerAgeId}" id="add"></rightButton:rightButton>
							&nbsp;&nbsp;
							<rightButton:rightButton name="删除" onclick="delateBroAndFirm();" className="anniu_btn" action="/broker/brokerage/managerFirmDele.action" id="delate"></rightButton:rightButton>
							&nbsp;&nbsp;
							<rightButton:rightButton name="返回" onclick="returnBrokerageList();" className="anniu_btn" action="/broker/brokerage/managerFirmReturnBrokerage.action" id="return"></rightButton:rightButton>
						</div>
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
								<tr>
									<td>
										<ec:table items="pageInfo.result" var="broAndFirm"
											action="${basePath}/broker/brokerage/managerFirm.action"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="demo.xls" csvFileName="demo.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
												<ec:column cell="checkbox" headerCell="checkbox" alias="ids" style="text-align:center; " value="${broAndFirm.firmId}" width="5%" viewsAllowed="html" />
												<ec:column width="5%" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												<ec:column property="firmId" width="10%" title="交易商代码" style="text-align:left;" ellipsis="true">${broAndFirm.firmId}</ec:column>
												<ec:column property="mfirm.name" title="交易商名称" width="10%" style="text-align:center;" ellipsis="true">${broAndFirm.mfirm.name}</ec:column>
												<ec:column property="mfirm.fullName" title="交易商全称" width="10%" style="text-align:center;" ellipsis="true">${broAndFirm.mfirm.fullName}</ec:column>
												<ec:column property="mfirm.type" title="交易商类型" width="10%" style="text-align:center;">
													<c:if test="${broAndFirm.mfirm.type==1}">法人</c:if>
													<c:if test="${broAndFirm.mfirm.type==2}">代理</c:if>
													<c:if test="${broAndFirm.mfirm.type==3}">个人 </c:if>
												</ec:column>
												<ec:column property="mfirm.phone" title="联系电话" width="10%" sortable="false" style="text-align:center;">${broAndFirm.mfirm.phone}</ec:column>
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