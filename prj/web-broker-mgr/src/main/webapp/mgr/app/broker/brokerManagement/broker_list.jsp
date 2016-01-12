<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
<head>


<title>加盟商设置列表</title>
<script type="text/javascript">
<!--
	//添加信息跳转
	function addForward(){
		//获取配置权限的 URL
		var addUrl=document.getElementById('add').action;
		//获取完整跳转URL
		var url = "${basePath}"+addUrl;

		if(showDialog(url, "", 800, 700)){
			//如果添加成功，则刷新列表
			ECSideUtil.reload("ec");
		}
		
	}
	//修改信息跳转
	function detailForward(id){
		//获取配置权限的 URL
		var detailUrl = "${basePath}/broker/brokerManagement/updateBrokerforward.action";
		//获取完整跳转URL
		var url = detailUrl+ "?entity.brokerId=" + id;
		//弹出修改页面
		if(showDialog(url, "", 800, 700)){
			//如果修改成功，则刷新列表
			ECSideUtil.reload("ec");
		};
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
		 
		 var startDate = document.getElementById("beginDate").value;
		 var endDate =  document.getElementById("endDate").value;	

		  if(startDate != "" && endDate != "" || startDate == "" && endDate != "" || startDate != "" && endDate == "")
		  {
			  if(startDate == ""){
					alert("开始日期不能为空！");
					frm.beginDate.focus();
					return false;
					
				}
				if(endDate == ""){
					alert("结束日期不能为空！");
					frm.endDate.focus();
					return false;
					
				}
			if ( startDate > endDate ) { 
		        alert("开始日期不能大于结束日期!"); 
		        return false; 
		    } 
		  }
		frm.submit();
	}
	
	//修改密码跳转
	function detailForward1(id){
		//获取配置权限的 URL
		var detailUrl = "${basePath}/broker/brokerManagement/updateBrokerPasswordforward.action?entity.brokerId=" + id;
		//获取完整跳转URL
		var url = detailUrl;
		//弹出修改页面
		if(showDialog(url, "", 550, 350)){
			//如果修改成功，则刷新列表
			ECSideUtil.reload("ec");
		};
	}
	// 所辖交易商跳转
	function detailForward2(id){
		//获取配置权限的 URL
		var detailUrl = "${basePath}/broker/brokerManagement/updateBrokerFirmforward.action";
		//获取完整跳转URL
		var url = detailUrl + "?brokerId=" + id;
		//弹出修改页面
		document.location.href = url;
	}
	//权限设置跳转
	function detailForward3(id){
		//获取配置权限的 URL
		var detailUrl = "${basePath}/broker/brokerManagement/updateBrokerRightforward.action";
		//获取完整跳转URL
		var url = detailUrl+ "?brokerId=" + id;
		//弹出修改页面
		if(showDialog(url, "", 500, 650)){
			//如果修改成功，则刷新列表
			ECSideUtil.reload("ec");
		};
	}
// -->
</script>
</head>
<body>
	<div id="main_body" >
	      <div class="div_cx">
					<form name="frm" action="${basePath}/broker/brokerManagement/listBroker.action" method="post">
						<table border="0" cellpadding="0" cellspacing="0" class="table2_style" >
							<tr>
								<td class="table5_td_width">
									<div class="div2_top">
										<table class="table4_style" border="0" cellspacing="0" cellpadding="0" style="width: 110%">
											<tr >
											   <td class="table3_td_1" align="right" style="width: 25%">
													    加盟商编号：
													<label>
														<input type="text" class="input_text" id="brokerId"   name="${GNNT_}primary.brokerId[like]" value="${oldParams['primary.brokerId[like]']}" />
													</label>
												</td>
												<td class="table3_td_1" align="right" style="width: 25%">
													    加盟商账号：
													<label>
														<input type="text" class="input_text" id="firmId"   name="${GNNT_}primary.firmId[=]" value="${oldParams['primary.firmId[=]']}" />
													</label>
												</td>
												<td class="table3_td_1" align="right" style="width: 25%">
													  加盟商名称：
													<label>
														<input type="text" class="input_text" id="brokerName"   name="${GNNT_}primary.name[like]" value="${oldParams['primary.name[like]']}" />
													</label>
												</td>
												 <td class="table3_td_1" align="right" style="width: 25%">
													  区域：
													<label>
														<input type="text" class="input_text" id="areaName"   name="${GNNT_}primary.brokerArea.name[like]" value="${oldParams['primary.brokerArea.name[like]']}" />
													</label>
												</td>
												
											</tr>
										
										<tr>
										     <td class="table3_td_1" align="right" style="width: 28%">
													    市场开发人员：
													<label> 
														<input type="text" class="input_text" id="marketManager"  name="${GNNT_}primary.marketManager[like]" value="${oldParams['primary.marketManager[like]']}" />
													</label>
												</td>
										      
												<td class="table3_td_1" align="right" >
													  &nbsp;&nbsp;&nbsp;开始日期：
													<label >
														<input type="text" class="wdate" id="beginDate"  style="width:120"
												       name="${GNNT_}primary.modifyTime[>=][date]"			
												       value="${oldParams['primary.modifyTime[>=][date]']}"					
												       onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" />	
													</label>
												</td>
												<td class="table3_td_1" align="right" >
													  结束日期：
													<label>
														<input type="text" class="wdate" id="endDate"   style="width:120"
												       name="${GNNT_}primary.modifyTime[<=][date]"			
												       value="${oldParams['primary.modifyTime[<=][date]']}"					
												       onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" />	
													</label>
												</td>
												
												<td class="table3_td_anniu" align="right" style="width: 12%;">
													<button class="btn_sec" id="view" onclick=dolistquery();>查询</button><span>&nbsp;</span>
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
			<rightButton:rightButton name="添加" onclick="addForward();" className="anniu_btn" action="/broker/brokerManagement/addBrokerforward.action" id="add"></rightButton:rightButton>
			&nbsp;&nbsp;
			<rightButton:rightButton name="删除" onclick="deleteList();" className="anniu_btn" action="/broker/brokerManagement/deleteBroker.action?autoInc=false" id="delete"></rightButton:rightButton>
		 </div>
		<div class="div_list">
			<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
				<tr>
					<td>
						<ec:table items="pageInfo.result" var="broker"
							action="${basePath}/broker/brokerManagement/listBroker.action"											
							autoIncludeParameters="${empty param.autoInc}"
							xlsFileName="export.xls" csvFileName="export.csv"
							showPrint="true" listWidth="150%"
							minHeight="345"  style="table-layout:fixed;">
							<ec:row>
							
								<ec:column cell="checkbox" headerCell="checkbox" alias="ids" style="text-align:center; " value="${broker.brokerId}" width="2%" viewsAllowed="html" />
								<ec:column property="brokerId" width="4%" title="加盟商编号" style="text-align:center;">
								    <a href="#" class="blank_a" onclick="detailForward('${broker.brokerId}')" title="修改"><font color="#880000">${broker.brokerId}</font></a> 	
								</ec:column>
								<ec:column property="firmId" title="加盟商账号" width="8%" ellipsis="true" style="text-align:center;"/>
								<ec:column property="name" title="加盟商名称" width="8%" ellipsis="true" style="text-align:center;"/>
								<ec:column property="telephone" title="电话" width="7%" style="text-align:center;"/>
								<ec:column property="mobile" title="手机" width="7%" style="text-align:center;"/>
								<ec:column property="email" title="电子邮件" width="9%" style="text-align:center;"/>
								<ec:column property="brokerArea" title="区域" width="8%" style="text-align:center;">							  
								${broker.brokerArea.name }
								</ec:column>
								<ec:column property="marketManager" title="市场开发人员" width="8%" style="text-align:center;"/>
								<ec:column property="memberType" title="会员类型" width="5%" style="text-align:center;">${broker.brokerType.brokerName }</ec:column>
								<ec:column property="timeLimit" title="会籍期限" width="6%" style="text-align:center;">
								     <fmt:formatDate value="${broker.timeLimit }" pattern="yyyy-MM-dd" />
								</ec:column>
								<ec:column property="modifyTime" title="创建日期" width="6%" style="text-align:center;">
								     <fmt:formatDate value="${broker.modifyTime }" pattern="yyyy-MM-dd" />
								</ec:column>
								<ec:column property="_1" title="权限设置" width="5%" sortable="false" filterable="false" style="text-align:center;" >
                                   <a href="#" onclick="detailForward3('${broker.brokerId}')">查看</a> 
                                </ec:column>
                                <ec:column property="_1" title="所辖交易商" width="5%" sortable="false" filterable="false" style="text-align:center;" >
                                	<c:if test="${broker.brokerType.borkerType==1 or broker.brokerType.borkerType==2}">
                                		--
                                	</c:if>
                                	<c:if test="${broker.brokerType.borkerType==0}">
                                   		<a href="#" onclick="detailForward2('${broker.brokerId}')">查看</a> 
                                	</c:if>
                                </ec:column>
                                <ec:column property="_1" title="密码修改" width="5%" sortable="false" filterable="false" style="text-align:center;" >
                                   <a href="#" onclick="detailForward1('${broker.brokerId}')">修改</a> 
                                </ec:column>
							</ec:row>
						</ec:table>
					</td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>
