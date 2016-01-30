<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>仓单信息列表</title>
		<SCRIPT type="text/javascript">
		<!-- 
			//添加信息跳转
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
						<div class="warning">
							<div class="content">
								温馨提示 :查看质押资金仓单号<b style="color: red;">${billID}</b>的仓单详细信息。
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
								<tr>
									<td>
										<ec:table  items="pageInfo.result" var="bill"
											action="${basePath}/timebargain/pledge/getBillListByBillID.action"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="demo.xls" csvFileName="demo.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
												<ec:column property="STOCKID"  alias="remarkHdd" width="18%" title="仓单号" style="text-align:center;" />
												<ec:column property="WAREHOUSEID" title="仓库编号" width="18%" style="text-align:center;"/>
												<ec:column property="BREEDNAME" title="品种名称" width="18%" style="text-align:center;"/>
												<ec:column property="QTYUNIT" title="商品数量" width="18%" style="text-align:center;">
													${bill.QUANTITY}&nbsp;${bill.UNIT}
												</ec:column>
												<ec:column property="LASTTIME" title="最后变更时间" width="28%" style="text-align:center;">
													<fmt:formatDate value="${bill.LASTTIME}" pattern="yyyy-MM-dd HH:mm:ss" /> 
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
		</div>
		<div class="tab_pad">
				<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center">
					<tr>
						<td align="right">
							<button class="btn_sec" onClick="window.close();">关闭</button>
						</td>
					</tr>
				</table>
		</div>
		<!-- 编辑和过滤所使用的 通用的文本框模板 -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;" name="" />
		</textarea>
	</body>
</html>