<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
<head>

<title>品种管理列表</title>
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
function addSortForward(){
	//获取配置权限的 URL
	var addUrl=document.getElementById('addBC').action;
	//获取完整跳转URL
	var url = "${basePath}"+addUrl;

	if(window.open(url, "", "width=450,height=400")){
		//如果修改成功，则刷新列表
		ECSideUtil.reload("ec");
	};
	
}
//修改信息跳转
function detailForward(id){
	//获取配置权限的 URL
	var detailUrl = "${basePath}/timebargain/tradeparams/detailBreed.action?breedID=";
	//获取完整跳转URL
	var url = detailUrl + id;

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

function updateForward(id) {
	//获取配置权限的 URL
	var updateUrl = "/timebargain/tradeparams/detailToCommodity.action";
	//获取完整跳转URL
	var url = "${basePath}"+updateUrl;
	//给 URL 添加参数
	url += "?breedID="+id+"&sortColumns=order+by+commodityID+asc";

	document.location.href = url;
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
							<form name="frm" action="${basePath}/timebargain/tradeparams/breedsList.action?sortColumns=order+by+breedID+asc" method="post">
								<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
									<tr>
										<td class="table5_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td class="table3_td_1" align="right">
															品种名称:&nbsp;
															<label>
															    <!-- 
																<select id="sortID" name="${GNNT_}primary.sortID[=][Long]"  class="normal" style="width: 120px">
																	<option value="">全部品种</option>
																	<c:forEach items="${resultList}" var="sort">
																		<option value="${sort.sortID}">${sort.sortName}</option>
																	</c:forEach>	
																</select> -->
																<input id="tariffID" name="${GNNT_}primary.breedName[allLike]" type="text" value="${oldParams['primary.breedName[allLike]'] }" class="input_text"/>	
															</label>
															
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
							<rightButton:rightButton name="添加" onclick="addForward();" className="anniu_btn" action="/timebargain/tradeparams/addBreedforward.action" id="add"></rightButton:rightButton>
							&nbsp;&nbsp;
							<rightButton:rightButton name="删除" onclick="deleteList();" className="anniu_btn" action="/timebargain/tradeparams/deleteBreed.action?autoInc=false" id="delete"></rightButton:rightButton>
							&nbsp;&nbsp;<!--  
							<rightButton:rightButton name="品种分类" onclick="addSortForward();" className="anniu_btn" action="/timebargain/tradeparams/addBCategoryList.action" id="addBC"></rightButton:rightButton>
						-->
						</div>
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
								<tr>
									<td>
										<ec:table items="pageInfo.result" var="breed"
											action="${basePath}/timebargain/tradeparams/breedsList.action?sortColumns=order+by+breedID+asc"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="导出列表.xls" csvFileName="导出列表.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
												<ec:column cell="checkbox" headerCell="checkbox" alias="ids" style="text-align:center; " value="${breed.breedID}" width="5%" viewsAllowed="html" />
												<ec:column property="breedID" width="35%" title="品种ID" style="text-align:center;">
												   <rightHyperlink:rightHyperlink text="<font color='#880000'>${breed.breedID}</font>" className="blank_a" onclick="return detailForward('${breed.breedID}');" action="/timebargain/tradeparams/detailBreed.action" />
													<%-- <a href="#" class="blank_a" onclick="return detailForward('${breed.breedID}');"><font color="#880000">${breed.breedID}</font></a> --%>
												</ec:column>
												<ec:column property="breedName" title="品种名称" width="40%" sortable="false" style="text-align:center;"/>
												<ec:column property="prop" title="对应商品" resizeColWidth="false" tipTitle="进入商品信息" sortable="false" width="20%" style="text-align:center;">
												   <rightHyperlink:rightHyperlink text="<img src='${skinPath }/image/app/timebargain/commodity.gif'/>" href="#" onclick="updateForward('${breed.breedID}')" action="/timebargain/tradeparams/detailToCommodity.action"/>
													<%-- <a href="#" onclick="updateForward('<c:out value="${breed.breedID}"/>')"><img src="<c:url value="${skinPath }/image/app/timebargain/commodity.gif"/>"></a> --%>
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
