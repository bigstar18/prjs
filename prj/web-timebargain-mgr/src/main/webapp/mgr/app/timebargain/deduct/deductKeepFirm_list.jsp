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
	<title>�ͻ�����</title>
	<base target="_self">
	
	</head>
	<body >
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
					
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0" width="80%">
								<tr>
									<td>
										<ec:table items="pageInfo.result" var="deductKeep"
											action="${basePath}/timebargain/deduct/deductKeepFirmByDeductId.action?deductId=${deductId}"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="deductKeep.xls" csvFileName="deductKeep.csv"
											showPrint="true" listWidth="100%"
											minHeight="200"  style="table-layout:fixed;">
											<ec:row>
												<ec:column property="customerId" width="25%" title="���׿ͻ�����" ellipsis="true" style="text-align:center;"/>
												<ec:column property="_" title="������־" width="25%" style="text-align:center;" >
												<c:if test="${deductKeep.bs_Flag==1}">��</c:if><c:if test="${deductKeep.bs_Flag==2}">��${deductId}</c:if>
												</ec:column>
												<ec:column property="keepQty" title="��������" width="25%" style="text-align:center;"/>
											</ec:row>
										</ec:table>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
			</table>
		<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;" name="" />
		</textarea>
	</body>
</html>
