<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/broker/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>居间商列表</title>
		<SCRIPT type="text/javascript">
		//添加信息跳转
			function addBrokerageForward(){
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
			function update(id){
				var url = "${basePath}/broker/brokerage/viewByIdBrokerage.action?entity.brokerAgeId="+id;
				if(showDialog(url, "", 650, 530)){
					frm.submit();
				};
			}
			function updatePWD(id){
				var url="${basePath}/broker/brokerage/updatePWDForward.action?entity.brokerAgeId="+id;
				if(showDialog(url, "", 650, 330)){
					frm.submit();
				};
			}
			//批量删除信息
			function delateBrokerageList(){
				//获取配置权限的 URL
				var delateUrl = document.getElementById('delate').action;
				//获取完整跳转URL
				var url = "${basePath}"+delateUrl+"?autoInc=false";
				//执行删除操作
				updateRMIEcside(ec.ids,url);
			}
			function mngFirm(id){
				var url="${basePath}/broker/brokerage/managerFirm.action?brokerAgeId="+id;
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
							<form name="frm" action="${basePath}/broker/brokerage/brokeragelist.action?sortColumns=order+by+brokerAgeId+asc" method="post">
								<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
									<tr>
										<td class="table5_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td class="table3_td_1" align="left">
															居间商代码:&nbsp;
															<label>
																<input type="text" class="input_text" id="brokerAgeId"  checked="checked" name="${GNNT_}primary.brokerAgeId[allLike]" value="${oldParams['primary.brokerAgeId[allLike]']}" maxLength="16"/>
															</label>
														</td>
														<td class="table3_td_1" align="left">
															居间商名称:&nbsp;
															<label>
																<input type="text" class="input_text" id="name" name="${GNNT_}primary.name[allLike]" value="${oldParams['primary.name[allLike]'] }" maxLength="16"/>
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
						<div class="div_gn">						
							<rightButton:rightButton name="添加" onclick="addBrokerageForward();" className="anniu_btn" action="/broker/brokerage/brokerageforward.action" id="add"></rightButton:rightButton>
							&nbsp;&nbsp;
							<rightButton:rightButton name="删除" onclick="delateBrokerageList();" className="anniu_btn" action="/broker/brokerage/deleteBrokerage.action" id="delate"></rightButton:rightButton>
						</div>
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
								<tr>
									<td>
										<ec:table items="pageInfo.result" var="brokerage"
											action="${basePath}/broker/brokerage/brokeragelist.action"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="demo.xls" csvFileName="demo.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
												<ec:column cell="checkbox" headerCell="checkbox" alias="ids" style="text-align:center; " value="${brokerage.brokerAgeId}" width="5%" viewsAllowed="html" />
												<ec:column width="5%" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												<ec:column property="brokerAgeId" width="10%" title="居间商代码" style="text-align:left;">
													<a href="#" class="blank_a" onclick="return update('${brokerage.brokerAgeId}');"><font color="#880000">${brokerage.brokerAgeId}</font></a>
												</ec:column>
												<ec:column property="name" title="居间商名称" width="10%" style="text-align:center;">${brokerage.name}</ec:column>
												<ec:column property="broker.brokerId" title="所属会员" width="10%" style="text-align:center;">${brokerage.broker.brokerId}</ec:column>
												<ec:column property="brokerAge.brokerAgeId" title="所属居间商" width="10%" style="text-align:center;">${brokerage.brokerAge.brokerAgeId}</ec:column>
												<ec:column property="creatTime" title="创建时间" width="10%" style="text-align:center;"><fmt:formatDate value="${brokerage.creatTime}" pattern="yyyy-MM-dd HH:mm:ss"/></ec:column>
												<ec:column property="password" width="10%" title="密码修改" style="text-align:center;">
													<a href="#" class="blank_a" onclick="return updatePWD('${brokerage.brokerAgeId}');"><font color="#880000">修改</font></a>
												</ec:column>
												<ec:column property="password" width="10%" title="所辖交易商" style="text-align:center;">
													<a href="#" class="blank_a" onclick="return mngFirm('${brokerage.brokerAgeId}');"><font color="#880000">查看</font></a>
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