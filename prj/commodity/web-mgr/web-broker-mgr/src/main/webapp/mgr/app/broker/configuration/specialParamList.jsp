<%@ page contentType="text/html;charset=GBK"%>
<%@page import="gnnt.MEBS.broker.mgr.model.configparam.BrokerRewardProps"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
<head>

<title>特殊佣金设置列表</title>
<script type="text/javascript">
<!--
//添加信息跳转
function addForward(){
	//获取配置权限的 URL
	var addUrl=document.getElementById('add').action;
	//获取完整跳转URL
	var url = "${basePath}"+addUrl;

	document.location.href = url;
	
}

//修改信息跳转
function detailForward(moduleId,rewardType,brokerId,commodityId){
	//获取配置权限的 URL
	var detailUrl = "${basePath}/config/special/detail.action?entity.moduleId=";
	//获取完整跳转URL
	var url = detailUrl+moduleId+"&entity.rewardType="+rewardType+"&entity.brokerId="+brokerId+"&entity.commodityId="+commodityId;

	document.location.href = url;
	
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


function dolistquery() {
	frm.submit();
}
// -->
</script>
</head>
<body>
<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<div class="div_cx">
							<form name="frm" action="${basePath}/config/special/specialParamList.action" method="post">
								<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
									<tr>
										<td class="table5_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td class="table3_td_1" align="right">
															模块名称:&nbsp;															
															<label>
																<select id="moduleId" name="${GNNT_ }primary.moduleId[=][int]"  class="normal" style="width: 120px">
																<%--
																	<option value="">全部</option>
																	<option value="15" <c:if test="${entity.moduleId == 15}">selected</c:if>>${request.shortName }</option>
																--%>		
																<option value="">全部</option>
																 <c:forEach items="${brTradeModule }" var="d">
																		<option value="${d.moduleId }">${d.cnName }</option>
																</c:forEach>								
																</select>
															</label>
															 <script >
																frm.moduleId.value = "${oldParams['primary.moduleId[=][int]']}";
					  										</script>
					  										
															<%-- <label>
																<input name="${GNNT_ }primary.moduleId[=][int]" class="input_text" value="${oldParams['primary.moduleId[=][int]']}"/>
															</label>--%>
															
														</td>
														<td class="table3_td_1" align="left">
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
						<br />
						<div class="div_gn">
							<rightButton:rightButton name="添加" onclick="addForward();" className="anniu_btn" action="/config/special/addforward.action?flag=common" id="add"></rightButton:rightButton>
							&nbsp;&nbsp;
							<rightButton:rightButton name="删除" onclick="deleteList();" className="anniu_btn" action="/config/special/delete.action" id="delete"></rightButton:rightButton>
						</div>
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
								<tr>
									<td>
										<ec:table items="pageInfo.result" var="special"
											action="${basePath}/config/special/specialParamList.action"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="导出列表.xls" csvFileName="导出列表.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
											    <ec:column cell="checkbox" headerCell="checkbox" alias="ids" style="text-align:center; " value="${special.brokerId},${special.moduleId},${special.commodityId},${special.rewardType}" width="5%" viewsAllowed="html" />
											    <ec:column property="brokerId" title="加盟商编号" width="10%" sortable="false" style="text-align:center;">
											   		 <a href="#" class="blank_a" onclick="return detailForward('${special.moduleId}','${special.rewardType }','${special.brokerId }','${special.commodityId }');"><font color="#880000">${special.brokerId}</font></a>
												</ec:column>
												<ec:column property="-1" width="20%" title="模块" style="text-align:center;" sortable="false">
												<c:forEach items="${brTradeModule }" var="ddd">
													<c:if test="${special.moduleId == ddd.moduleId}">
															${ddd.cnName }
													</c:if>
												</c:forEach>
												</ec:column>
												<ec:column property="commodityId" title="商品" width="10%" sortable="false" style="text-align:center;">
													${special.commodityId}
												</ec:column>
												<ec:column property="rewardType" title="佣金类型" width="10%" sortable="false" style="text-align:center;">${config_rewardTypeMap[special.rewardType]}</ec:column>
												<ec:column property="rewardRate" title="手续费佣金比例" width="15%" style="text-align:center;">
													${special.rewardRateTemp }%
												</ec:column>
												<ec:column property="firstPayRate" title="提成首付比例" width="15%" style="text-align:center;">
													${special.firstPayRateTemp }%
												</ec:column>
												<ec:column property="secondPayRate" title="提成尾款比例" width="15%" style="text-align:center;">
													${special.secondPayRateTemp }%
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
		<!-- 编辑和过滤所使用的 通用的文本框模板 -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;" name="" />
		</textarea>
</body>

</html>
