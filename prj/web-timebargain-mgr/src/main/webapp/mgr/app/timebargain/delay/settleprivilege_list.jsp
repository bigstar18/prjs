<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
        <link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
<title></title>
<script type="text/javascript">

jQuery(document).ready(function() {
	jQuery("#frm").validationEngine('attach', {promptPosition : "bottomRight"});
});
//添加信息跳转
function addForward(){
	//获取配置权限的 URL
	var addUrl=document.getElementById('add').action;
	//获取完整跳转URL
	var url = "${basePath}"+addUrl;

	if(showDialog(url, "", 800, 550)){
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

function updateForward(id) {
	//获取配置权限的 URL
	var updateUrl = "/timebargain/delay/detailPrivilegeforward.action";
	//获取完整跳转URL
	var url = "${basePath}"+updateUrl;
	//给 URL 添加参数
	url += "?entity.id="+id;

	if(showDialog(url, "", 800, 550)){
		ECSideUtil.reload("ec");
	}	
}

function dolistquery() {
	if(jQuery("#frm").validationEngine('validateform')){
		frm.submit();
	}
}
</script>
</head>
<body leftmargin="2" topmargin="0">
<div id="main_body">
	<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
				<div class="div_cx">
					<form id="frm" name="frm" action="${basePath}/timebargain/delay/privilegeList.action?sortColumns=order+by+ID+asc" method="post">
						<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
							<tr>
								<td class="table5_td_width">
									<div class="div2_top">
										<table class="table3_style" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<td class="table3_td_1" align="right">
														&nbsp;&nbsp;交易商代码：
												</td>
												<td>
													<input type="text" id="typeId" name="${GNNT_}primary.typeId[=]" style="width:111;ime-mode:disabled" maxlength="<fmt:message key='MFirm.firmId_q' bundle='${PropsFieldLength}' />" title=""
														class="validate[onlyLetterNumber] input_text" value="${oldParams['primary.typeId[=]']}"/>
												</td>
						                        <td align="right">
														商品代码：
												</td>
												<td>
													<input type="text" id="kindId" name="${GNNT_}primary.kindId[=]"  style="width:111;ime-mode:disabled" maxlength="18"
														class="validate[onlyLetterNumber] input_text" value="${oldParams['primary.kindId[=]']}"/>
												</td>
												<td class="table3_td_1" align="left">
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
				<br />
				<div class="div_gn">
					<rightButton:rightButton name="添加" onclick="addForward();" className="anniu_btn" action="/timebargain/delay/addPrivilegeforward.action" id="add"></rightButton:rightButton>
					&nbsp;&nbsp;
					<rightButton:rightButton name="删除" onclick="deleteList();" className="anniu_btn" action="/timebargain/delay/deletePrivilege.action" id="delete"></rightButton:rightButton>
				</div>
				<div class="div_list">
					<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
						<tr>
							<td>
								<ec:table items="pageInfo.result" var="settleprivilege"
									action="${basePath}/timebargain/delay/privilegeList.action?sortColumns=order+by+ID+asc"											
									autoIncludeParameters="${empty param.autoInc}"
									xlsFileName="导出列表.xls" csvFileName="导出列表.csv"
									showPrint="true" listWidth="100%"
									minHeight="345"  style="table-layout:fixed;">
									<ec:row>
										<ec:column cell="checkbox" headerCell="checkbox" alias="ids" value="${settleprivilege.id}" viewsAllowed="html" style="text-align:center;padding-left:7px;width:20%;"/>		
									    <ec:column property="typeId" title="交易商代码" width="20%" ellipsis="true" style="text-align:center;">
									       <%-- <a onclick="updateForward('<c:out value="${settleprivilege.id}"/>');" style="cursor:hand" title="查看"><c:out value="${settleprivilege.typeId}"/></a> --%>
									       <rightHyperlink:rightHyperlink text="${settleprivilege.typeId}" title='查看' onclick="updateForward('${settleprivilege.id}');" action="/timebargain/delay/detailPrivilegeforward.action" />
									    </ec:column>
							            <ec:column property="kindId" title="商品代码" width="20%" style="text-align:center;"/>
							            <ec:column property="privilegecodeb" title="买方权限" width="20%" style="text-align:center;" mappingItem="PRIVILEGECODE_B"/>
										<ec:column property="privilegecodes" title="卖方权限" width="20%" style="text-align:center;" mappingItem="PRIVILEGECODE_S"/>
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
	<textarea id="ecs_t_status" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="BS_FLAG1" />
		</select>
	</textarea>	
</body>
</html>