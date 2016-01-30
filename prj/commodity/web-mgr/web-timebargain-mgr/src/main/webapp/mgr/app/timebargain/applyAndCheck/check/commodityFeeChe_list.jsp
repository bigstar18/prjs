<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>	

<script type="text/javascript">
	jQuery(document).ready(function() {
		jQuery("#frm").validationEngine('attach', {promptPosition : "bottomRight"});
	});
	
	function dolistquery() {
		if(jQuery("#frm").validationEngine('validateform')){
			frm.submit();
		}
	}
	function addForward(){
		//获取配置权限的 URL
		var addUrl=document.getElementById('add').action;
		//获取完整跳转URL
		var url = "${basePath}"+addUrl;

		document.location.href = url;
		
	}
	function add_commodityFee(){
		document.location.href = "${basePath}/timebargain/apply/commodityFeeAppList.action";
	}
	
	function check_commodityFee(id){
		//获取配置权限的 URL
		var detailUrl = "${basePath}/timebargain/check/updateCommodityFeeCheforward.action?entity.id=";
		//获取完整跳转URL
		var url = detailUrl + id;
		//弹出修改页面
		if(showDialog(url, "", 600, 550)){
			//如果修改成功，则刷新列表
			ECSideUtil.reload("ec");
		}
	}
</script>

<title>
审核与查看商品手续费
</title>
</head>
<body >
<div id="main_body">
	<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
				<div class="div_cx">
					<form id="frm" name="frm" action="${basePath}/timebargain/check/commodityFeeCheList.action" method="post">
						<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
							<tr>
								<td class="table5_td_width">
									<div class="div2_top">
										<table class="table3_style" border="0" cellspacing="0" cellpadding="0" style="margin-top: 5px">
											<tr>
												<td align="right">
														商品代码：
												</td>
												<td>
													<input type="text" id="commodityID" name="${GNNT_}primary.commodityID[=]" style="width:111;ime-mode:disabled" maxlength="16" title=""
														class="validate[onlyLetterNumber] input_text" value="${oldParams['primary.commodityID[=]']}"/>
												</td>
												<td align="right">状态：</td>
			                                    <td>
			                                        <select id="status" name="${GNNT_}primary.Status[=]" style="width:111">
							                            <option value="">全部</option>
								                        <option value="1" <c:if test="${oldParams['primary.Status[=]'] == '1'}">selected</c:if>>待审核</option>
								                        <option value="2" <c:if test="${oldParams['primary.Status[=]'] == '2'}">selected</c:if>>审核通过</option>
								                        <option value="3" <c:if test="${oldParams['primary.Status[=]'] == '3'}">selected</c:if>>审核不通过</option>
						                            </select>			                            
			                                    </td>																			
											
												<td class="table3_td_1" align="left">
													<button class="btn_sec" id="view" onclick="dolistquery();">查询</button>	
												    &nbsp;&nbsp;
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
				<div class="div_list">
					<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
						<tr>
							<td>
								<ec:table items="pageInfo.result" var="commodityFee"
									action="${basePath}/timebargain/check/commodityFeeCheList.action"											
									autoIncludeParameters="${empty param.autoInc}"
									xlsFileName="导出列表.xls" csvFileName="导出列表.csv"
									showPrint="true" listWidth="100%"
									minHeight="345"  style="table-layout:fixed;">
									<ec:row>
									    <ec:column property="COMMODITYID" title="商品代码" width="80"  style="text-align:center;"/>
									    <ec:column property="FEEALGR" title="交易手续费算法" editTemplate="ecs_t_feeAlgr" mappingItem="FEEALGR" width="120" style="text-align:right;"/>
									    <ec:column property="SETTLEFEEALGR" title="交收手续费算法" editTemplate="ecs_t_settleFeeAlgr" mappingItem="FEEALGR" width="120" style="text-align:center;"/>
										<ec:column property="LOWESTSETTLEFEE" title="最低交收手续费金额" width="140"  style="text-align:center;"/>
										<ec:column property="STATUS" title="当前状态" editTemplate="ecs_t_status" mappingItem="VIRTUALFUNDSTATUS" width="90" style="text-align:center;"/>
										<ec:column property="PROPOSER" title="申请人" width="90" style="text-align:center;"/>
										<ec:column property="APPLYTIME" title="申请时间" cell="date" width="90" style="text-align:center;"/>
										<ec:column property="_1" title="审核与查看"  width="90" style="text-align:center;">
										<c:choose>
												<c:when test="${commodityFee.STATUS == 1}">
													<%--<a href="#" onclick="check_commodityFee('<c:out value="${commodityFee.ID}"/>')"><img height="20" title="审核与查看" src="<%=skinPath%>/image/app/timebargain/commodity.gif"/></a> --%>
													<rightHyperlink:rightHyperlink text="<img title='审核与查看' src='${skinPath}/image/app/timebargain/commodity.gif'/>" href="#" onclick="check_commodityFee('${commodityFee.ID}')" action="/timebargain/check/updateCommodityFeeCheforward.action"/> 
												</c:when>
												<c:otherwise>
													<%-- <a href="#" onclick="check_commodityFee('<c:out value="${commodityFee.ID}"/>')"><img height="20" title="审核与查看" src="<%=skinPath%>/image/app/timebargain/commodity.gif"/></a>--%>
													<rightHyperlink:rightHyperlink text="<img title='审核与查看' src='${skinPath}/image/app/timebargain/commodity.gif'/>" href="#" onclick="check_commodityFee('${commodityFee.ID}')" action="/timebargain/check/updateCommodityFeeCheforward.action"/>
												</c:otherwise>
										</c:choose>
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
	<textarea id="ecs_t_input" rows="" cols="" style="display:none">
		<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" 
		 style="width:100%;" name="" />
	</textarea>
	<!-- 编辑状态所用模板 -->
	<textarea id="ecs_t_feeAlgr" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="feeAlgr" >
			<ec:options items="FEEALGR" />
		</select>
	</textarea>	
	<textarea id="ecs_t_settleFeeAlgr" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="settleFeeAlgr" >
			<ec:options items="SETTLEFEEALGR" />
		</select>
	</textarea>
	<textarea id="ecs_t_status" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="VIRTUALFUNDSTATUS" />
		</select>
	</textarea>		

</body>

</html>
