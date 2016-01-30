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
			function addPledgeForward(){
				//��ȡ����Ȩ�޵� URL
				var addUrl=document.getElementById('add').action;
				//��ȡ������תURL
				var url = "${basePath}"+addUrl;
				//�������ҳ��
				if(showDialog(url, "", 500, 400)){
					//�����ӳɹ�����ˢ���б�
					document.getElementById("view").click();
				}
			}
			
			//ִ�в�ѯ�б�
			function dolistquery() {
				frm.submit();
			}
			
			function check_pledge(id){
				//��ȡ������תURL
				var url = "${basePath}/timebargain/pledge/pledgeAuditForward.action?entity.pledgeID=" + id;
				//�����޸�ҳ��
				if(showDialog(url, "", 450, 300)){
					//����޸ĳɹ�����ˢ���б�
					ECSideUtil.reload("ec");
				}
			}
			
			//�ֵ�����鿴��ת
			function showBillInfo(billID){
				//��ȡ����Ȩ�޵� URL
				var showUrl="/timebargain/pledge/getBillListByBillID.action";
				//��ȡ������תURL
				var url = "${basePath}"+showUrl+"?entity.billID="+billID;
				//�������ҳ��
				showDialog(url, "", 700, 550);
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
							<form name="frm" action="${basePath}/timebargain/pledge/pledgeList.action?sortColumns=order+by+ID+desc" method="post">
								<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
									<tr>
										<td class="table5_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td align="left" style="width:300px;height:40px;padding:0px;line-height:40px;color:#000;">
															&nbsp;&nbsp;�����̴���:&nbsp;
															<label>
																<input type="text" class="input_text" id="firmID"  checked="checked" name="${GNNT_}primary.firmID[=][String]" value="${oldParams['primary.firmID[=][String]']}" maxLength="<fmt:message key='MFirm.firmId_q' bundle='${PropsFieldLength}' />"/>
															</label>
														</td>
														<td align="left" style="width:300px;height:40px;padding:0px;line-height:40px;color:#000;">
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;״̬:&nbsp;
															<label>
																<select id="status" name="${GNNT_}primary.status[=][int]"  class="normal" style="width: 120px">
																	<option value="">ȫ��</option>
																	<c:forEach items="${Pledge_statusMap}" var="map">
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
						<div class="div_gn">
							<rightButton:rightButton name="���" onclick="addPledgeForward();" className="anniu_btn" action="/timebargain/pledge/addPledgeForward.action" id="add"></rightButton:rightButton>
						</div>
						
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
								<tr>
									<td>
										<ec:table items="pageInfo.result" var="pledge"
											action="${basePath}/timebargain/pledge/pledgeList.action"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="deduct.xls" csvFileName="deduct.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
												<ec:column property="billID" title="�ֵ���" width="10%" style="text-align:center;" >
												    <a href="#" onclick="javescript:showBillInfo('${pledge.billID}');">${pledge.billID}</a>
												</ec:column>
												<ec:column property="firmID" title="�����̴���" width="10%" ellipsis="true" style="text-align:center;" />
												<ec:column property="breedName" title="Ʒ������" width="10%" style="text-align:center;" />
												<ec:column property="billFund" title="��Ѻ���" width="15%" style="text-align:center;" >
													<fmt:formatNumber value="${pledge.billFund}" pattern="#0.00"/>
												</ec:column>
												<!--  ec:column property="quantity" title="��Ѻ����" width="10%" style="text-align:center;" />-->
												<ec:column property="type" title="��Ѻ����" width="7%" style="text-align:center;" >
												<c:forEach items="${Pledge_typeMap}" var="result">
													<c:if test="${pledge.type==result.key }">${result.value }</c:if>
												</c:forEach>
												</ec:column>
												<ec:column property="createTime" title="����ʱ��" width="13%" style="text-align:center;" >
												  <fmt:formatDate value="${pledge.createTime }" pattern="yyyy-MM-dd HH:mm:SS" />
												</ec:column>
												<ec:column property="creator" title="������" width="10%" style="text-align:center;" />
												<ec:column property="status" title="���״̬" width="10%" style="text-align:center;" >
													<c:forEach items="${Pledge_statusMap}" var="result">
														<c:if test="${pledge.status==result.key}">${result.value}</c:if>
													</c:forEach>
												</ec:column>
												<ec:column property="_1" title="���"  width="10%" style="text-align:center;">
													<c:choose>
														<c:when test="${pledge.status == 0}">
															<%-- <a href="#" onclick="check_pledge('<c:out value="${pledge.pledgeID}"/>')" style="text-align:center;">
																<img  height="20" title="���" src="<%=skinPath%>/image/app/timebargain/commodity.gif"/>
															</a>--%>
															<rightHyperlink:rightHyperlink text="<img title='���' src='${skinPath}/image/app/timebargain/commodity.gif'/>" onclick="check_pledge('${pledge.pledgeID}')" action="/timebargain/pledge/pledgeAuditForward.action"/>
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
