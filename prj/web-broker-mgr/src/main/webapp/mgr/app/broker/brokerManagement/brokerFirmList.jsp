<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
<head>


<title>交易商列表</title>
<script type="text/javascript">
<!--
	//添加信息跳转
	function addForward(){
		//获取配置权限的 URL
		var addUrl=document.getElementById('add').action;
		//获取完整跳转URL
		var url = "${basePath}"+addUrl;
        url = url + "?brokerId=" + '${brokerId}';
		if(showDialog(url, "", 550, 300)){
			//如果添加成功，则刷新列表
			ECSideUtil.reload("ec");
		}
		
	}
	//批量删除信息
	function deleteList(){
		//获取配置权限的 URL
		var deleteUrl = document.getElementById('delete').action;
		//获取完整跳转URL
		var url = "${basePath}"+deleteUrl;
		//执行删除操作
		updateRMIEcside(ec.ids,url);
	}
	//执行查询列表
	function dolistquery() {
		frm.submit();
	}
	//返回
	function goback(){
		
		//获取配置权限的 URL
		document.location.href = "${basePath}/broker/brokerManagement/listBroker.action";
	
		}
// -->
</script>
</head>
<body>
	<div id="main_body">
	      <div class="div_cx">
					<form name="frm" action="${basePath}/broker/brokerManagement/updateBrokerFirmforward.action?brokerId=${brokerId}" method="post">
						<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
						 
						  
							<tr>
								<td class="table5_td_width">
									<div class="div2_top">
										<table class="table4_style" border="0" cellspacing="0" cellpadding="0">
											<tr>											   
												<td class="table3_td_1" align="left">
													    交易商代码：
													<label>
														<input type="text" class="input_text" id="firmId"   name="${GNNT_}primary.firmId[like]" value="${oldParams['primary.firmId[like]']}" />
													</label>
												</td>
												<td class="table3_td_1" align="left">
													    交易商名称：
													<label>
														<input type="text" class="input_text" id="firm.name"   name="${GNNT_}primary.firm.name[like]" value="${oldParams['primary.firm.name[like]']}" />
													</label>
												</td>		
												<td class="table3_td_1" align="left">
															&nbsp;&nbsp;类型：
															<label>
																<select id="type" name="${GNNT_}primary.firm.type[=][int]"  class="normal" style="width: 120px">
																	<option value="">全部</option>
																	<c:forEach items="${typeMap}" var="map">
																		<option value="${map.key}">${map.value}</option>
																	</c:forEach>
																	
																</select>
															</label>
															 <script >
																frm.type.value = "<c:out value='${oldParams["primary.firm.type[=][int]"] }'/>";
					  										</script>
												</td>		
												<td class="table3_td_anniu" align="left">
													<button class="btn_sec" id="view" onclick=dolistquery();>查询</button>
													<button class="btn_cz" onclick="myReset();">重置</button>
												</td>	
											</tr>
											<tr>
											    <td class="table3_td_1" align="left">
													    联系人：
													<label>
														<input type="text" class="input_text" id="firm.contactMan"   name="${GNNT_}primary.firm.contactMan[like]" value="${oldParams['primary.firm.contactMan[like]']}" />
													</label>
												 </td>	
												 <td class="table3_td_1" align="left">
													    联系电话：
													<label>
														<input type="text" class="input_text" id="firm.phone"   name="${GNNT_}primary.firm.phone[like]" value="${oldParams['primary.firm.phone[like]']}" />
													</label>
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
			<rightButton:rightButton name="添加" onclick="addForward();" className="anniu_btn" action="/broker/brokerManagement/addBrokerFirmforward.action" id="add"></rightButton:rightButton>
			&nbsp;&nbsp;
			<rightButton:rightButton name="删除" onclick="deleteList();" className="anniu_btn" action="/broker/brokerManagement/deleteBrokerFirm.action?autoInc=false&brokerId=${brokerId}" id="delete"></rightButton:rightButton>
			
								
		 </div>
		<div class="div_list">
			<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
				<tr>
					<td>
						<ec:table items="pageInfo.result" var="broker"
							action="${basePath}/broker/brokerManagement/updateBrokerFirmforward.action?brokerId=${brokerId }"											
							autoIncludeParameters="${empty param.autoInc}"
							xlsFileName="export.xls" csvFileName="export.csv"
							showPrint="true" listWidth="100%"
							minHeight="345"  style="table-layout:fixed;">
							<ec:row>
							
								<ec:column cell="checkbox" headerCell="checkbox" alias="ids" style="text-align:center; " value="${broker.firmId}" width="3%" viewsAllowed="html" />
								<ec:column property="firmId" width="10%" title="交易商代码" style="text-align:center;"/>
								<ec:column property="firm.name" title="交易商名称" width="10%" style="text-align:center;"/>
								<ec:column property="firm.fullName" title="交易商全称" width="10%" style="text-align:center;"/>
								<ec:column property="firm.contactMan" title="联系人" width="10%" style="text-align:center;"/>
								<ec:column property="firm.phone" title="联系电话" width="10%" style="text-align:center;"/>
								<ec:column property="firm.type" title="类型" width="5%" style="text-align:center;">
								  ${typeMap[broker.firm.type]}
								</ec:column>
			
	 					   </ec:row>
							
							<ec:extend >
  			                   <a href="#" onclick="goback()">返回加盟商列表</a>
  		                    </ec:extend>
						</ec:table>
					</td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>
