<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
<head>

<title>Ĭ��Ӷ�������б�</title>
<script type="text/javascript">
<!--
//�޸���Ϣ��ת
function detailForward(id, type){
	//��ȡ����Ȩ�޵� URL
	var detailUrl = "${basePath}/config/default/detailDefaultParam.action?entity.moduleId=";
	//��ȡ������תURL
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
											xlsFileName="�����б�.xls" csvFileName="�����б�.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
												<ec:column property="-1" width="20%" title="ģ��" style="text-align:center;" sortable="false">
													<a href="#" class="blank_a" onclick="return detailForward('${default.moduleId}','${default.rewardType }');"><font color="#880000">Ĭ��</font></a>
												</ec:column>
												<ec:column property="rewardType" title="Ӷ������" width="20%" sortable="false" style="text-align:center;">${config_rewardTypeMap[default.rewardType]}</ec:column>
												<ec:column property="rewardRate" title="������Ӷ�����" width="20%" style="text-align:center;">
													${default.rewardRateTemp }%
												</ec:column>
												<ec:column property="firstPayRate" title="����׸�����" width="20%" style="text-align:center;">
													${default.firstPayRateTemp }%
												</ec:column>
												<ec:column property="secondPayRate" title="���β�����" width="20%" style="text-align:center;">
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
		<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;" name="" />
		</textarea>
</body>

</html>
