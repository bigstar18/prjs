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
		<title>ǿ����ϸ</title>
		<base target="_self">
	</head>
	<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
								<tr>
									<td>
										<ec:table items="pageInfo.result" var="deductDetail"
											action="${basePath}/timebargain/deduct/deductDetailByDeductId.action?deductId=${deductId}"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="deductDetail.xls" csvFileName="deductDetail.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
												<ec:column property="customerId" width="170" ellipsis="true" title="���׿ͻ�����" style="text-align:center;"/>
												<ec:column property="buyQty" title="�򶩻�" width="80" style="text-align:center;" />
												<ec:column property="sellQty" title="������" width="80" style="text-align:center;" />
												<ec:column property="buyKeepQty" title="����" width="80" style="text-align:center;" />
												<ec:column property="sellKeepQty" title="������" width="80" style="text-align:center;" />
												<ec:column property="pureHoldQty" title="��������" width="80" style="text-align:center;" >
												<c:if test="${deductDetail.pureHoldQty<0}">S${deductDetail.pureHoldQty*-1}</c:if>
												<c:if test="${deductDetail.pureHoldQty>0}">B${deductDetail.pureHoldQty}</c:if>
												<c:if test="${deductDetail.pureHoldQty==0}">${deductDetail.pureHoldQty}</c:if>
												</ec:column>
												<ec:column property="pL" title="ӯ���ϼ�" width="80" style="text-align:center;" />
												<ec:column property="pL_Ratio" title="ӯ����" width="80" style="text-align:center;" />
												<ec:column property="counteractQty" title="�Գ���" width="80" style="text-align:center;" />
												<ec:column property="orderQty" title="ί����" width="80" style="text-align:center;" />
												<ec:column property="deductableQty" title="��ǿ����" width="80" style="text-align:center;" />
												<ec:column property="estimateQty" title="����ǿ����" width="80" style="text-align:center;" />
												<ec:column property="deductQty" title="Ӧǿ����" width="80" style="text-align:center;" />
												<ec:column property="deductedQty" title="��ǿ����" width="80" style="text-align:center;" />
												<ec:column property="counteractedQty" title="�ѶԳ���" width="80" style="text-align:center;" />
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
