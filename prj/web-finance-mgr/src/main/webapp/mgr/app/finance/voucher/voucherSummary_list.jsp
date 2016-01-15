<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
	    <base target="_self">
		<title>摘要列表</title>
		<SCRIPT type="text/javascript">
		
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

			var summaryNo = "";
			
			// 单击选择
			function row_onclick(id){

				var objTr=event.srcElement;
				var objRadio = objTr.all.namedItem("selectRadio");
				if(objRadio){
					objRadio.checked = true;
					summaryNo = id;
				}
							
			}

			// 双击选择
			function row_ondblclick(id){
				
				window.returnValue = id;
				window.close();
			}

	
			// 单击选择
			function onclickRadio(id){
				summaryNo = id;
			}
			
			function selectSummary(){
				if(summaryNo != ""){
					window.returnValue = summaryNo;
					window.close();
				}
				else{
					alert("请选择摘要！");
				}
			}

			// 取消
			function cancel(){
				window.close();
			}

			// 重置
			function myResets(){

				document.getElementById('summaryNo').value = "";
				document.getElementById('summary').value = "";
				
			}
		
		</SCRIPT>
	</head>
	<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<div class="div_cx" >
							<form name="frm" action="${basePath}/finance/voucher/viewSummary.action?sortColumns=order+by+summaryNo" method="post">
								<table border="0" cellpadding="0" cellspacing="0" class="table2_style" >
									<tr>
										<td class="table5_td_width" >
											<div class="div2_top" >
												<table class="table3_style" border="0" cellspacing="0" cellpadding="0" style="width: 600px;">
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
															<button class="btn_sec" id="view" onclick="dolistquery()">查询</button>
															&nbsp;&nbsp;
															<button class="btn_cz" onclick="myResets();">重置</button>
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
										<ec:table items="pageInfo.result" var="summary"
											action="${basePath}/finance/voucher/viewSummary.action?sortColumns=order+by+summaryNo"											
											autoIncludeParameters="${empty param.autoInc}"
											
											showPrint="false" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row onclick="row_onclick('${summary.summaryNo}')"  ondblclick="javascript:row_ondblclick('${summary.summaryNo}')">
											
												<ec:column property="summaryNo" width="10%" title="摘要号" style="text-align:center;">		
												  <input type="radio" name="selectRadio" onclick="onclickRadio('${summary.summaryNo}')" value="${summary.summaryNo}">
												  ${summary.summaryNo}
												</ec:column>
												<ec:column property="summary" title="摘要名称" width="10%" style="text-align:center;"/>
												
											</ec:row>
											
										</ec:table>
									</td>
								</tr>
							</table>
							
						  <div align="right">
							<button class="anniu_btn" onClick="selectSummary()">确定</button>&nbsp;&nbsp;
							<button class="anniu_btn" onClick="cancel()">关闭</button>
						  </div>
						</div>
					</td>
				</tr>
			</table>
		
	</body>
</html>