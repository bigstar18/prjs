<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>摘要列表</title>
		<SCRIPT type="text/javascript">
		<!-- 
			//添加信息跳转
			function addAccountForward(){
				//获取配置权限的 URL
				var addUrl=document.getElementById('add').action;
				//获取完整跳转URL
				var url = "${basePath}"+addUrl;
				//弹出添加页面
				if(showDialog(url, "", 550, 350)){
					//如果添加成功，则刷新列表
					document.getElementById("view").click();
				}
			}
			//修改信息跳转
			function updateAccountForward(code){
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
			function deleteAccountList(){
				//获取配置权限的 URL
				var deleteUrl = document.getElementById('delete').action;
				//获取完整跳转URL
				var url = "${basePath}"+deleteUrl+"?autoInc=false";
				//执行删除操作
				updateRMIEcside(ec.ids,url);
			}
			//查看信息详情
			function accountDetails(code){
				var url = "${basePath}/finance/account/updateAccountforward.action?entity.code="+code;
				if(showDialog(url, "", 550, 350)){
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
							<form name="frm" action="${basePath}/finance/account/accountList.action?sortColumns=order+by+code" method="post">
								<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
									<tr>
										<td class="table5_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td class="table3_td_1" align="left">
															科目代码:&nbsp;
															<label>
																<input type="text" class="input_text" id="code"  checked="checked" name="${GNNT_}primary.code[allLike]" value="${oldParams['primary.code[allLike]']}" />
															</label>
														</td>
														<td class="table3_td_1" align="left">
															科目名称:&nbsp;
															<label>
																<input type="text" class="input_text" id="name" name="${GNNT_}primary.name[allLike]" value="${oldParams['primary.name[allLike]'] }"/>
															</label>
														</td>
														<td class="table3_td_anniu" align="left">
															<button class="btn_sec" id="view" onclick=dolistquery();>查询</button>
															&nbsp;&nbsp;
															<button class="btn_cz" onclick="myReset();">重置</button>
														</td>
													</tr>
													<tr>
														<td class="table3_td_1" align="left">
															借贷方向:&nbsp;
															<label>
																<select id="dcFlag" name="${GNNT_}primary.dcFlag[=]"  class="normal" style="width: 120px">
																	<option value="">全部</option>
																	<c:forEach items="${account_dCFlagMap}" var="map">
																		<option value="${map.key}">${map.value}</option>
																	</c:forEach>
																</select>
															</label>
															 <script >
																frm.dcFlag.value = "<c:out value='${oldParams["primary.dcFlag[=]"] }'/>";
					  										</script>
														</td>
														<td class="table3_td_1" align="left" colspan="2">
															科目级别:&nbsp;
															<label>
																<select id="accountLevel" name="${GNNT_}primary.accountLevel[=]"  class="normal" style="width: 120px">
																	<option value="">全部</option>
																	<option value="1">1</option>
																	<option value="2">2</option>
																	<option value="3">3</option>
																	<option value="4">4</option>
																</select>
															</label>
															 <script >
																frm.accountLevel.value = "<c:out value='${oldParams["primary.accountLevel[=]"] }'/>";
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
							<rightButton:rightButton name="添加" onclick="addAccountForward();" className="anniu_btn" action="/finance/account/addAccountForward.action" id="add"></rightButton:rightButton>
							&nbsp;&nbsp;
							<rightButton:rightButton name="删除" onclick="deleteAccountList();" className="anniu_btn" action="/finance/account/deleteAccountList.action" id="delete"></rightButton:rightButton>
						</div>
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
								<tr>
									<td>
										<ec:table items="pageInfo.result" var="account"
											action="${basePath}/finance/account/accountList.action?sortColumns=order+by+code"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="demo.xls" csvFileName="demo.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
												<ec:column cell="checkbox" headerCell="checkbox" alias="ids" style="text-align:center; " value="${account.code}" width="5%" viewsAllowed="html" />
												<ec:column property="code" width="10%" title="科目代码" style="text-align:center;">
													<a href="#" class="blank_a" onclick="return accountDetails('${account.code}');"><font color="#880000">${account.code}</font>&nbsp;</a>
												</ec:column>
												<ec:column property="name" title="科目名称" width="10%" style="text-align:center;"/>
												<ec:column property="dcFlag" title="借贷方向" width="10%" style="text-align:center;">${account_dCFlagMap[account.dcFlag]}</ec:column>
												<ec:column property="accountLevel" title="科目级别" width="10%" style="text-align:center;"/>
												<ec:column property="isInit" title="是否初始化" width="10%" style="text-align:center;">${account_isInitMap[account.isInit]}</ec:column>
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