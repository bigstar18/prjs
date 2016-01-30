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
		function addSettleMatch(){
			//��ȡ����Ȩ�޵� URL
			var addUrl=document.getElementById('add').action;
			//��ȡ������תURL
			var url = "${basePath}"+addUrl;
			//�������ҳ��
			if(showDialog(url, "", 900, 700)){
				//�����ӳɹ�����ˢ���б�
				document.getElementById("view").click();
			}
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
							<form name="frm" id="frm"  action="${basePath}/timebargain/settleMatch/listSettleMatch.action?sortColumns=order+by+createTime+desc"  method="post" >
							<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
									<tr>
										<td class="table5_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td class="table3_td_1" align="left">
															�򷽴���:&nbsp;
															<label>
															    <input type="text" class="input_text" id="firmID_B" name="${GNNT_}primary.firmID_B[allLike][String]" value="${oldParams['primary.firmID_B[allLike][String]'] }"  maxlength="<fmt:message key='MFirm.firmId_q' bundle='${PropsFieldLength}' />"/>
															</label>
														</td>
														<td class="table3_td_1" align="left">
															��������:&nbsp;
															<label>
															    <input type="text" class="input_text" id="firmID_S" name="${GNNT_}primary.firmID_S[allLike][String]" value="${oldParams['primary.firmID_S[allLike][String]'] }"  maxlength="<fmt:message key='MFirm.firmId_q' bundle='${PropsFieldLength}' />"/>
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
															&nbsp;&nbsp;&nbsp;״̬:&nbsp;
															<label>
																<select id="status" name="${GNNT_}primary.status[=][int]"  class="normal" style="width: 120px">
																	<option value="">ȫ��</option>
																	<c:forEach items="${settleMatch_statusMapM}" var="map">
																		<option value="${map.key}">${map.value}</option>
																	</c:forEach>
																</select>
															</label>
														</td>
														 <script >
																frm.status.value = "<c:out value='${oldParams["primary.status[=][int]"] }'/>";
					  									</script>
					  									<td class="table3_td_1" align="left">
															&nbsp;&nbsp;&nbsp;ִ�н��:&nbsp;
															<label>
																<select id="result" name="${GNNT_}primary.result[=][int]"  class="normal" style="width: 120px">
																	<option value="">ȫ��</option>
																	<c:forEach items="${settleMatch_resultMapM}" var="map">
																		<option value="${map.key}">${map.value}</option>
																	</c:forEach>
																</select>
															</label>
														</td>
														 <script >
																frm.result.value = "<c:out value='${oldParams["primary.result[=][int]"] }'/>";
					  									</script>
														<td class="table3_td_1" align="left">
															&nbsp;&nbsp;&nbsp;��������:&nbsp;
															<label>
																<select id="settleType" name="${GNNT_}primary.settleType[=][int]"  class="normal" style="width: 120px">
																	<option value="">ȫ��</option>
																	<c:forEach items="${settleMatch_settleTypeMapM}" var="map">
																		<option value="${map.key}">${map.value}</option>
																	</c:forEach>
																</select>
															</label>
														</td>
														<script >
																frm.settleType.value = "<c:out value='${oldParams["primary.settleType[=][int]"] }'/>";
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
							<rightButton:rightButton name="���" onclick="addSettleMatch();" className="anniu_btn" action="/timebargain/settleMatch/forwardAddSettleMatch.action" id="add"></rightButton:rightButton>
						</div>
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
								<tr>
									<td>
										<ec:table items="pageInfo.result" var="settleMatch"
											action="${basePath}/timebargain/settleMatch/listSettleMatch.action?sortColumns=order+by+createTime+desc"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="settleMatch.xls" csvFileName="settleMatch.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
												<ec:column property="matchId" title="��Ա��"  width="10%" style="text-align:center;" />
												<ec:column property="commodityId" title="��Ʒ����" width="10%" style="text-align:center;" />
												<ec:column property="quantity" title="��������" width="10%" style="text-align:center;" />
												<ec:column property="firmID_B" title="�򷽽����̴���" width="10%" ellipsis="true" style="text-align:center;" />
												<ec:column property="firmID_S" title="���������̴���" width="10%" ellipsis="true" style="text-align:center;" />
												<ec:column property="status" title="״̬" width="10%" style="text-align:center;" >
												${settleMatch_statusMapM[settleMatch.status] }
												</ec:column>
												<ec:column property="result" title="ִ�н��" width="10%" style="text-align:center;" >
												${settleMatch_resultMapM[settleMatch.result] }
												</ec:column>
												<ec:column property="settleType" title="��������" width="10%" style="text-align:center;" >
												${settleMatch_settleTypeMapM[settleMatch.settleType] }
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
