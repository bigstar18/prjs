<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/broker/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>客户信息列表</title>
		<SCRIPT type="text/javascript">
		<!-- 
			//添加信息跳转
			function addCustomerForward(){
				//获取配置权限的 URL
				var addUrl=document.getElementById('add').action;
				//获取完整跳转URL
				var url = "${basePath}"+addUrl;
				//弹出添加页面
				if(showDialog(url, "", 800, 550)){
					//如果添加成功，则刷新列表
					document.getElementById("view").click();
				}
			}
			//修改信息跳转
			function updateCustomerForward(customerId){
				//获取配置权限的 URL
				var updateUrl = document.getElementById('update'+customerId).action;
				//获取完整跳转URL
				var url = "${basePath}"+updateUrl;
				//给 URL 添加参数
				if(url.indexOf("?")>0){
					url += "&entity.customerId="+customerId;
				}else{
					url += "?entity.customerId="+customerId;
				}
				//弹出修改页面
				if(showDialog(url, "", 800, 550)){
					//如果修改成功，则刷新列表
					document.getElementById("view").click();
				};
			}
			//批量删除信息
			function delateCustomerList(){
				//获取配置权限的 URL
				var delateUrl = document.getElementById('delate').action;
				//获取完整跳转URL
				var url = "${basePath}"+delateUrl;
				//执行删除操作
				updateRMIEcside(ec.ids,url);
			}
			//查看信息详情
			function customerDetails(customerId){
				var url = "${basePath}/dem/customerplay/customerdetails.action?entity.customerId="+customerId;
				showDialog(url, "", 700, 450);
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
							<form name="frm" action="${basePath}/dem/customerplay/customerlist.action?sortColumns=order+by+customerId+desc" method="post">
								<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
									<tr>
										<td class="table5_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td class="table3_td_1" align="left">
															<fmt:message key ="Customer.customerId" bundle="${PropsFieldDic}"/>:&nbsp;
															<label>
																<input type="text" class="input_text" id="id"  checked="checked" name="${GNNT_}primary.customerId[=][Long]" value="${oldParams['primary.customerId[=][Long]']}" maxLength="<fmt:message key='Customer.customerId_q' bundle='${PropsFieldLength}'/>"/>
															</label>
														</td>
														<td class="table3_td_1" align="left">
															<fmt:message key ="User.userId" bundle="${PropsFieldDic}"/>:&nbsp;
															<label>
																<input type="text" class="input_text" id="userID" name="${GNNT_}primary.user.userId[allLike]" value="${oldParams['primary.user.userId[allLike]'] }" maxLength="<fmt:message key='User.userId_q' bundle='${PropsFieldLength}'/>"/>
															</label>
														</td>
														<td class="table3_td_1" align="left">
															<fmt:message key ="Customer.status" bundle="${PropsFieldDic}"/>:&nbsp;
															<label>
																<select id="status" name="${GNNT_}primary.status[=]"  class="normal" style="width: 120px">
																	<option value="">全部</option>
																	<c:forEach items="${customer_statusMap}" var="map">
																		<option value="${map.key}">${map.value}</option>
																	</c:forEach>
																	
																</select>
															</label>
															 <script >
																frm.status.value = "<c:out value='${oldParams["primary.status[=]"] }'/>";
					  										</script>
														</td>
													</tr>
													<tr>
														<td class="table3_td_1" align="left">&nbsp;</td>
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
							<rightButton:rightButton name="添加" onclick="addCustomerForward();" className="anniu_btn" action="/dem/customerplay/addcustomerforward.action" id="add"></rightButton:rightButton>
							&nbsp;&nbsp;
							<rightButton:rightButton name="删除" onclick="delateCustomerList();" className="anniu_btn" action="/dem/customerplay/delatecustomerlist.action" id="delate"></rightButton:rightButton>
						</div>
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
								<tr>
									<td>
										<ec:table items="pageInfo.result" var="customer"
											action="${basePath}/dem/customerplay/customerlist.action?sortColumns=order+by+customerId+desc"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="demo.xls" csvFileName="demo.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
												<ec:column cell="checkbox" headerCell="checkbox" alias="ids" style="text-align:center; " value="${customer.customerId}" width="5%" viewsAllowed="html" />
												<ec:column width="5%" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												<ec:column property="prop" width="10%" title="Customer.customerId" style="text-align:left;">
													<a href="#" class="blank_a" onclick="return customerDetails('${customer.customerId}');"><font color="#880000">${customer.customerId}</font></a>
												</ec:column>
												<ec:column property="user.name" title="User.name" width="10%" style="text-align:left;"  ellipsis="true"/>
												<ec:column property="name" title="Customer.name" width="10%" style="text-align:left;"  ellipsis="true"/>
												<ec:column property="prop" title="Customer.status" width="10%" style="text-align:center;">${customer_statusMap[customer.status]}</ec:column>
												<ec:column property="prop" title="Customer.modifyTime" width="10%" style="text-align:center;"><fmt:formatDate value="${customer.modifyTime}" pattern="yyyy-MM-dd HH:mm:ss"/></ec:column>
												<ec:column property="prop" title="修改信息" width="10%" style="text-align:center;">
													<rightButton:rightButton name="修改" onclick="updateCustomerForward('${customer.customerId}');" className="anniu_btn" action="/dem/customerplay/updatecustomerforward.action" id="update${customer.customerId}"></rightButton:rightButton>
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