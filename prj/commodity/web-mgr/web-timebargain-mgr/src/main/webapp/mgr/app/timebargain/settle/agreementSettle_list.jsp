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
			function addAgreementForward(){
				//��ȡ����Ȩ�޵� URL
				var addUrl=document.getElementById('add').action;
				//��ȡ������תURL
				var url = "${basePath}"+addUrl;
				//�������ҳ��
				if(showDialog(url, "", 800, 300)){
					//�����ӳɹ�����ˢ���б�
					document.getElementById("view").click();
				}
			}
			
		//ִ�в�ѯ�б�
		function dolistquery() {
			frm.submit();
		}
		
		function check_agreement(id){
			//��ȡ������תURL
			var url = "${basePath}/timebargain/agreementSettle/agreementAuditForward.action?entity.applyID=" + id;
			//�����޸�ҳ��
			if(showDialog(url, "", 450, 300)){
				//����޸ĳɹ�����ˢ���б�
				ECSideUtil.reload("ec");
			}
		}
		
		//-->
		</SCRIPT>
	</head>
	<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
					
						<div class="div_cx">
							<form name="frm" action="${basePath}/timebargain/agreementSettle/agreementSettleList.action?sortColumns=order+by+applyID+desc" method="post">
								<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
									<tr>
										<td class="table5_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td align="left" style="width:300px;height:40px;padding:0px;line-height:40px;color:#000;">
															������������:&nbsp;
															<label>
																<input type="text" class="input_text" id="customerID_S" name="${GNNT_}primary.customerID_S[=][String]" value="${oldParams['primary.customerID_S[=][String]']}" maxLength="10"/>
															</label>
														</td>
														<td align="left" style="width:300px;height:40px;padding:0px;line-height:40px;color:#000;">
															�򷽶�������:&nbsp;
															<label>
																<input type="text" class="input_text" id="customerID_B" name="${GNNT_}primary.customerID_B[=][String]" value="${oldParams['primary.customerID_B[=][String]'] }" maxLength="10"/>
															</label>
														</td>
													</tr>
													<tr>
														<td align="left" style="width:300px;height:40px;padding:0px;line-height:40px;color:#000;">
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��Ʒ����:&nbsp;
															<label>
																<input type="text" class="input_text" id="commodityID" name="${GNNT_}primary.commodityID[=][String]" value="${oldParams['primary.commodityID[=][String]'] }" maxLength="10"/>
															</label>
														</td>
														<td align="left" style="width:300px;height:40px;padding:0px;line-height:40px;color:#000;">
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;״̬:&nbsp;
															<label>
																<select id="status" name="${GNNT_}primary.status[=][int]"  class="normal" style="width: 120px">
																	<option value="">ȫ��</option>
																	<c:forEach items="${Agreement_statusMap}" var="map">
																		<option value="${map.key}">${map.value}</option>
																	</c:forEach>
																	
																</select>
															</label>
															 <script >
																frm.status.value = "<c:out value='${oldParams["primary.status[=][int]"] }'/>";
					  										</script>
														</td>
														<td class="table3_td_anniu" align="left">
															<button class="btn_sec" id="view" onclick=dolistquery();>��ѯ</button>
															&nbsp;&nbsp;
															<button class="btn_cz" onclick="myReset();">����</button>
														</td>
													</tr>
												</table>
											</div>
										</td>
									</tr>
								</table>
							</form>
						</div>
						<!-- 
						<c:forEach items="${BS_flagMap}" var="result">
							${result }
						</c:forEach>
						--> 
						<div class="div_gn">
							<rightButton:rightButton name="���" onclick="addAgreementForward();" className="anniu_btn" action="/timebargain/agreementSettle/addAgreementforward.action" id="add"></rightButton:rightButton>
							&nbsp;&nbsp;
						</div>
						
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
								<tr>
									<td>
										<ec:table items="pageInfo.result" var="agreement"
											action="${basePath}/timebargain/agreementSettle/agreementSettleList.action"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="deduct.xls" csvFileName="deduct.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
												<ec:column property="commodityID" title="��Ʒ����" width="15%" style="text-align:center;" />
												<ec:column property="price" title="�۸�" width="8%" style="text-align:center;" />
												<ec:column property="customerID_B" title="�򷽶�������" width="12%" ellipsis="true" style="text-align:center;" />
												<ec:column property="customerID_S" title="������������" width="12%" ellipsis="true" style="text-align:center;"/>
												<ec:column property="quantity" title="ת������" width="7%" style="text-align:center;" />
												<ec:column property="creator" title="�����û�" width="10%" style="text-align:center;" />
												<ec:column property="createTime" title="����ʱ��" width="13%" style="text-align:center;" >
												  <fmt:formatDate value="${agreement.createTime }" pattern="yyyy-MM-dd HH:mm:ss" />
												</ec:column>
												<ec:column property="modifyTime" title="����޸�ʱ��" width="13%" style="text-align:center;" >
												  <fmt:formatDate value="${agreement.modifyTime }" pattern="yyyy-MM-dd HH:mm:ss" />
												</ec:column>
												<ec:column property="status" title="���״̬" width="7%" style="text-align:center;" >
													<c:forEach items="${Agreement_statusMap}" var="result">
														<c:if test="${agreement.status==result.key }">${result.value}</c:if>
													</c:forEach>
												</ec:column>
												<ec:column property="_1" title="���"  width="7%" style="text-align:center;">
													<c:choose>
														<c:when test="${agreement.status == 1}">
															<%-- <a href="#" onclick="check_agreement('<c:out value="${agreement.applyID}"/>')" style="text-align:center;">
																<img  height="20" title="���" src="<%=skinPath%>/image/app/timebargain/commodity.gif"/>
															</a>--%>
															<rightHyperlink:rightHyperlink text="<img title='���' src='${skinPath}/image/app/timebargain/commodity.gif'/>" onclick="check_agreement('${agreement.applyID}')" action="/timebargain/agreementSettle/agreementAuditForward.action"/> 
														</c:when>
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
		<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;" name="" />
		</textarea>
	</body>
</html>
