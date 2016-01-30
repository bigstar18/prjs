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
		function addApplyGage(){
			//��ȡ����Ȩ�޵� URL
			var addUrl=document.getElementById('add').action;
			//��ȡ������תURL
			var url = "${basePath}"+addUrl;
			//�������ҳ��
			if(showDialog(url, "", 600, 450)){
				//�����ӳɹ�����ˢ���б�
				frm.submit();
			};
		}

		function view(id){
			var url = "${basePath}/timebargain/applyGage/viewById.action?entity.applyId="+id;
			if(showDialog(url, "", 600, 480)){
				frm.submit();
			};
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
							<form name="frm" id="frm"  action="${basePath}/timebargain/applyGage/listApplyGage.action"  method="post" >
							<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
									<tr>
										<td class="table5_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td class="table3_td_1" align="left">
															�����̴���:&nbsp;
															<label>
															    <input type="text" class="input_text" id="firmId" name="${GNNT_}primary.firmId[allLike][String]" value="${oldParams['primary.firmId[allLike][String]'] }"  maxlength="<fmt:message key='MFirm.firmId_q' bundle='${PropsFieldLength}' />"/>
															</label>
														</td>
														<td class="table3_td_1" align="left">
															&nbsp;&nbsp;&nbsp;��Ʒ����:&nbsp;
															<label>
																 <input type="text" class="input_text" id="commodityId" name="${GNNT_}primary.commodityId[allLike][String]" value="${oldParams['primary.commodityId[allLike][String]'] }"  maxlength="10"/>
															</label>
														</td></tr>
														<tr>
														<td class="table3_td_1" align="left">
															&nbsp;&nbsp;&nbsp;���״̬:&nbsp;
															<label>
																<select id="status" name="${GNNT_}primary.status[=][int]"  class="normal" style="width: 120px">
																	<option value="">ȫ��</option>
																	<c:forEach items="${applyGage_statusMap}" var="map">
																		<option value="${map.key}">${map.value}</option>
																	</c:forEach>
																</select>
															</label>
														</td>
														 <script >
																frm.status.value = "<c:out value='${oldParams["primary.status[=][int]"] }'/>";
					  									</script>
														<td class="table3_td_1" align="left">
															&nbsp;&nbsp;&nbsp;��������:&nbsp;
															<label>
																<select id="applyType" name="${GNNT_}primary.applyType[=][int]"  class="normal" style="width: 120px">
																	<option value="">ȫ��</option>
																	<c:forEach items="${applyGage_typeMap}" var="map">
																		<option value="${map.key}">${map.value}</option>
																	</c:forEach>
																</select>
															</label>
														</td>
														<script >
																frm.applyType.value = "<c:out value='${oldParams["primary.applyType[=][int]"] }'/>";
					  									</script>
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
						<div class="div_gn">
							<rightButton:rightButton name="���" onclick="addApplyGage();" className="anniu_btn" action="/timebargain/applyGage/forwardAddApplyGage.action" id="add"></rightButton:rightButton>
						</div>
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
								<tr>
									<td>
										<ec:table items="pageInfo.result" var="applyGage"
											action="${basePath}/timebargain/applyGage/listApplyGage.action"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="applyGage.xls" csvFileName="applyGage.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
												<ec:column property="applyId" title="���뵥��"  width="10%" style="text-align:center;" />
												<ec:column property="commodityId" title="��Ʒ����" width="10%" style="text-align:center;" />
												<ec:column property="firmId" title="�����̴���" width="13%"  ellipsis="true" style="text-align:center;" />
												<ec:column property="customerId" title="�����̶�������" width="13%" ellipsis="true" style="text-align:center;" />
												<ec:column property="quantity" title="��������" width="10%" style="text-align:center;" />
												<ec:column property="applyType" title="��������" width="7%" style="text-align:center;" >
												${applyGage_typeMap[applyGage.applyType] }
												</ec:column>
												<ec:column property="createTime" title="����ʱ��" width="13%" style="text-align:center;" >
												  <fmt:formatDate value="${applyGage.createTime }" pattern="yyyy-MM-dd HH:mm:SS" />
												</ec:column>
												<ec:column property="creator" title="������" width="10%" style="text-align:center;" />
												<ec:column property="status" title="���״̬" width="7%" style="text-align:center;" >
												${applyGage_statusMap[applyGage.status] }
												</ec:column>
												<ec:column property="_" width="10%" title="���ݲ���"
													style="text-align:center; " sortable="false">
													<c:if test="${applyGage.status==1}">
													<%-- <a href="javascript:void(0);" class="blank_a"
														onclick="view(${applyGage.applyId});"><font
														color="#880000">��˲���</font> </a>--%>
														<rightHyperlink:rightHyperlink text="<font color='#880000'>��˲���</font>" className="blank_a" onclick="view(${applyGage.applyId});" action="/timebargain/applyGage/viewById.action" />
													</c:if>
													<c:if test="${applyGage.status !=1}">
														<button class="anniu_btn" disabled="disabled">�����</button>
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
