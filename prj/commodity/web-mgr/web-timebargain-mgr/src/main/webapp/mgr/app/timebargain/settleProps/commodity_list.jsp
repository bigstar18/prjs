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

		//�����Ϣ��ת
		function forwardAdd(id,flag){
			//��ȡ����Ȩ�޵� URL
			var addUrl="/timebargain/settleProps/forwardAddSettleProps.action?commodityId="+id+"&flag="+flag;
			//��ȡ������תURL
			var url = "${basePath}"+addUrl;
			//�������ҳ��
			if(showDialog(url, "", 700, 450)){
				//�����ӳɹ�����ˢ���б�
				frm.submit();
			};
		}

		function deleteSettleProps(id){
			if(confirm("��ȷ��Ҫ����ѡ�е�������")){
				var url = "${basePath}/timebargain/settleProps/deleteSettleProps.action?commodityId="+id;
				frm.action=url;
				frm.submit();
			}
		}
		//ִ�в�ѯ�б�
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
															&nbsp;&nbsp;&nbsp;��Ʒ����:&nbsp;
															<label>
																 <input type="text" class="input_text" id="commodityId" name="${GNNT_}primary.commodityId[allLike][String]" value="${oldParams['primary.commodityId[allLike][String]'] }"  maxlength="10"/>
															</label>
														</td>
														<td class="table3_td_1" align="left">
															&nbsp;&nbsp;&nbsp;��Ʒ����:&nbsp;
															<label>
																 <input type="text" class="input_text" id="name" name="${GNNT_}primary.name[allLike][String]" value="${oldParams['primary.name[allLike][String]'] }"  maxlength="10"/>
															</label>
														</td>
														<td class="table3_td_1" align="left">
															<label>
																 &nbsp;��ǰ��Ʒ<input type="radio" id="flag1" name="flag"  value="C" <c:if test="${flag == 'C'}">checked</c:if>>
																 &nbsp;������Ʒ<input type="radio" id="flag2" name="flag" value="S"  <c:if test="${flag == 'S'}">checked</c:if>>
															</label>
														</td>
														<td class="table3_td_anniu" align="left">
															<button class="btn_sec" id="view" onclick=dolistquery();>��ѯ</button>
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
												<ec:column property="commodityId" title="��Ʒ����"  width="10%" style="text-align:center;" >
													<%-- <a href="javascript:void(0);" class="blank_a"
														onclick="forwardAdd('${commodity.commodityId}');"> ${commodity.commodityId }</a>--%>
													<rightHyperlink:rightHyperlink text="${commodity.commodityId}" className="blank_a" onclick="forwardAdd('${commodity.commodityId}','${flag}');" action="/timebargain/settleProps/forwardAddSettleProps.action" />	
												</ec:column>
												<ec:column property="name" title="��Ʒ����" width="10%" style="text-align:center;" />
												<ec:column property="breedId" title="Ʒ�ִ���" width="10%" style="text-align:center;" />
												<ec:column property="breed.breedName" title="Ʒ������" width="10%" style="text-align:center;" />
												<ec:column property="sortId" title="�������" width="10%" style="text-align:center;" />
												<ec:column property="category.categoryName" title="��������" width="10%" style="text-align:center;"/>
												<ec:column property="_" title="�Ƿ�������" width="10%" style="text-align:center;">
												<c:if test="${fn:length(commodity.settleProps)>0}">
												��
												</c:if>
												<c:if test="${fn:length(commodity.settleProps)==0}">
												��
												</c:if>
												</ec:column>
												<ec:column property="_" title="ɾ������" width="10%" style="text-align:center;">
												<c:if test="${fn:length(commodity.settleProps)>0}">
												 <%-- <a href="javascript:void(0);" class="blank_a"
														onclick="deleteSettleProps('${commodity.commodityId}');"><font
														color="#880000">ɾ��</font> </a>--%>
													<rightHyperlink:rightHyperlink text="<font color='#880000'>ɾ��</font>" className="blank_a" onclick="deleteSettleProps('${commodity.commodityId}');" action="/timebargain/settleProps/deleteSettleProps.action" />	
												</c:if>
												<c:if test="${fn:length(commodity.settleProps)==0}">
												<button class="anniu_btn" disabled="disabled">δ����</button>
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
		<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;" name="" />
		</textarea>
	</body>
</html>
