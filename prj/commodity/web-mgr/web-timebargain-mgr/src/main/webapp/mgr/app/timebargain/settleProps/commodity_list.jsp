<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<link rel="stylesheet"
		href="${skinPath }/css/validationengine/validationEngine.jquery.css"
		type="text/css" />
	<link rel="stylesheet"
		href="${skinPath }/css/validationengine/template.css" type="text/css" />
	<script src="${publicPath }/js/jquery-1.6.min.js"
		type="text/javascript">
	
</script>
	<script
		src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js"
		type="text/javascript" charset="GBK">
	
</script>
	<script
		src="${publicPath }/js/validationengine/jquery.validationEngine.js"
		type="text/javascript" charset="GBK"></script>
	
	<head>
		<SCRIPT type="text/javascript">

		//添加信息跳转
		function forwardAdd(id,flag){
			//获取配置权限的 URL
			var addUrl="/timebargain/settleProps/forwardAddSettleProps.action?commodityId="+id+"&flag="+flag;
			//获取完整跳转URL
			var url = "${basePath}"+addUrl;
			//弹出添加页面
			if(showDialog(url, "", 700, 450)){
				//如果添加成功，则刷新列表
				frm.submit();
			};
		}

		function deleteSettleProps(id){
			if(confirm("你确定要操作选中的数据吗？")){
				var url = "${basePath}/timebargain/settleProps/deleteSettleProps.action?commodityId="+id;
				frm.action=url;
				frm.submit();
			}
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
							<form name="frm" id="frm"  action="${basePath}/timebargain/settleProps/listCommodity.action"  method="post" >
							<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
									<tr>
										<td class="table5_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td class="table3_td_1" align="left">
															&nbsp;&nbsp;&nbsp;商品代码:&nbsp;
															<label>
																 <input type="text" class="input_text" id="commodityId" name="${GNNT_}primary.commodityId[allLike][String]" value="${oldParams['primary.commodityId[allLike][String]'] }"  maxlength="10"/>
															</label>
														</td>
														<td class="table3_td_1" align="left">
															&nbsp;&nbsp;&nbsp;商品名称:&nbsp;
															<label>
																 <input type="text" class="input_text" id="name" name="${GNNT_}primary.name[allLike][String]" value="${oldParams['primary.name[allLike][String]'] }"  maxlength="10"/>
															</label>
														</td>
														<td class="table3_td_1" align="left">
															<label>
																 &nbsp;当前商品<input type="radio" id="flag1" name="flag"  value="C" <c:if test="${flag == 'C'}">checked</c:if>>
																 &nbsp;交收商品<input type="radio" id="flag2" name="flag" value="S"  <c:if test="${flag == 'S'}">checked</c:if>>
															</label>
														</td>
														<td class="table3_td_anniu" align="left">
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
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
								<tr>
									<td>
										<ec:table items="pageInfo.result" var="commodity"
											action="${basePath}/timebargain/settleProps/listCommodity.action"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="applyGage.xls" csvFileName="applyGage.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
												<ec:column property="commodityId" title="商品代码"  width="10%" style="text-align:center;" >
													<%-- <a href="javascript:void(0);" class="blank_a"
														onclick="forwardAdd('${commodity.commodityId}');"> ${commodity.commodityId }</a>--%>
													<rightHyperlink:rightHyperlink text="${commodity.commodityId}" className="blank_a" onclick="forwardAdd('${commodity.commodityId}','${flag}');" action="/timebargain/settleProps/forwardAddSettleProps.action" />	
												</ec:column>
												<ec:column property="name" title="商品名称" width="10%" style="text-align:center;" />
												<ec:column property="breedId" title="品种代码" width="10%" style="text-align:center;" />
												<ec:column property="breed.breedName" title="品种名称" width="10%" style="text-align:center;" />
												<ec:column property="sortId" title="分类代码" width="10%" style="text-align:center;" />
												<ec:column property="category.categoryName" title="分类名称" width="10%" style="text-align:center;"/>
												<ec:column property="_" title="是否已设置" width="10%" style="text-align:center;">
												<c:if test="${fn:length(commodity.settleProps)>0}">
												是
												</c:if>
												<c:if test="${fn:length(commodity.settleProps)==0}">
												否
												</c:if>
												</ec:column>
												<ec:column property="_" title="删除操作" width="10%" style="text-align:center;">
												<c:if test="${fn:length(commodity.settleProps)>0}">
												 <%-- <a href="javascript:void(0);" class="blank_a"
														onclick="deleteSettleProps('${commodity.commodityId}');"><font
														color="#880000">删除</font> </a>--%>
													<rightHyperlink:rightHyperlink text="<font color='#880000'>删除</font>" className="blank_a" onclick="deleteSettleProps('${commodity.commodityId}');" action="/timebargain/settleProps/deleteSettleProps.action" />	
												</c:if>
												<c:if test="${fn:length(commodity.settleProps)==0}">
												<button class="anniu_btn" disabled="disabled">未设置</button>
												</c:if>
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
