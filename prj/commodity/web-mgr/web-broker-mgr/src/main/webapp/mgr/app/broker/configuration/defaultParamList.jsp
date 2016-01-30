<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
<head>

<title>默认佣金设置列表</title>
<script type="text/javascript">
<!--
//修改信息跳转
function detailForward(id, type){
	//获取配置权限的 URL
	var detailUrl = "${basePath}/config/default/detailDefaultParam.action?entity.moduleId=";
	//获取完整跳转URL
	var url = detailUrl + id + "&entity.rewardType=" + type;

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
						<br />						
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
								<tr>
									<td>
										<ec:table items="pageInfo.result" var="default"
											action="${basePath}/config/default/defaultParamList.action"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="导出列表.xls" csvFileName="导出列表.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
												<ec:column property="-1" width="20%" title="模块" style="text-align:center;" sortable="false">
													<a href="#" class="blank_a" onclick="return detailForward('${default.moduleId}','${default.rewardType }');"><font color="#880000">默认</font></a>
												</ec:column>
												<ec:column property="rewardType" title="佣金类型" width="20%" sortable="false" style="text-align:center;">${config_rewardTypeMap[default.rewardType]}</ec:column>
												<ec:column property="rewardRate" title="手续费佣金比例" width="20%" style="text-align:center;">
													${default.rewardRateTemp }%
												</ec:column>
												<ec:column property="firstPayRate" title="提成首付比例" width="20%" style="text-align:center;">
													${default.firstPayRateTemp }%
												</ec:column>
												<ec:column property="secondPayRate" title="提成尾款比例" width="20%" style="text-align:center;">
													${default.secondPayRateTemp }%
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
