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
			function addTransferForward(){
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
			
			//����ɾ����Ϣ
			function delateTransferList(){
				//��ȡ����Ȩ�޵� URL
				var deleteUrl = document.getElementById('delate').action;
				//��ȡ������תURL
				var url = "${basePath}"+deleteUrl+"?autoInc=false";
				//ִ��ɾ������
				updateRMIEcside(ec.ids,url);
			}
		//ִ�в�ѯ�б�
		function dolistquery() {
			frm.submit();
		}
		
		function check_transfer(id){
			//��ȡ������תURL
			var url = "${basePath}/timebargain/transfer/transferAuditForward.action?entity.transferID=" + id;
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
							<form name="frm" action="${basePath}/timebargain/transfer/transferList.action?sortColumns=order+by+transferID+desc" method="post">
								<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
									<tr>
										<td class="table5_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td class="table3_td_1" align="left">
															&nbsp;&nbsp;&nbsp;��Ʒ����:&nbsp;
															<label>
																<input type="text" class="input_text" id="commodityID"  checked="checked" name="${GNNT_}primary.commodityID[=][String]" value="${oldParams['primary.commodityID[=][String]']}" maxLength="50"/>
															</label>
														</td>
														<td align="left" style="width:300px;height:40px;padding:0px;line-height:40px;color:#000;">
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;״̬:&nbsp;
															<label>
																<select id="status" name="${GNNT_}primary.status[=][int]"  class="normal" style="width: 120px">
																	<option value="">ȫ��</option>
																	<c:forEach items="${Transfer_statusMap}" var="map">
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
							<rightButton:rightButton name="���" onclick="addTransferForward();" className="anniu_btn" action="/timebargain/transfer/addTransferforward.action" id="add"></rightButton:rightButton>
							&nbsp;&nbsp;
							
							<rightButton:rightButton name="ɾ��" onclick="delateTransferList();" className="anniu_btn" action="/timebargain/transfer/delateTransfer.action" id="delate"></rightButton:rightButton>
						
						</div>
						
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
								<tr>
									<td>
										<ec:table items="pageInfo.result" var="transfer"
											action="${basePath}/timebargain/transfer/transferList.action"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="deduct.xls" csvFileName="deduct.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
												<ec:column cell="checkbox" headerCell="checkbox" alias="ids" style="text-align:center; " value="${transfer.transferID}" width="5%" viewsAllowed="html" />
												<ec:column property="commodityID" title="��Ʒ����" width="15%" style="text-align:center;" />
												<ec:column property="customerID_s" title="�����˶�������" width="13%" ellipsis="true" style="text-align:center;" />
												<ec:column property="bs_flag" title="�ֲַ���" width="6%" style="text-align:center;" >
												<c:forEach items="${BS_flagMap}" var="result">
													<c:if test="${transfer.bs_flag==result.key }">${result.value }</c:if>
												</c:forEach>
												</ec:column>
												<ec:column property="type" title="��������" width="6%" style="text-align:center;" >
												<c:forEach items="${Transfer_typeMap}" var="result">
													<c:if test="${transfer.type==result.key }">${result.value }</c:if>
												</c:forEach>
												</ec:column>
												<ec:column property="quantity" title="��������" width="7%" style="text-align:center;" />
												<ec:column property="customerID_b" title="�����˶�������" ellipsis="true" width="13%" style="text-align:center;" />
												
												<ec:column property="createTime" title="����ʱ��" width="13%" style="text-align:center;" >
												  <fmt:formatDate value="${transfer.createTime }" pattern="yyyy-MM-dd HH:mm:ss" />
												</ec:column>
												<ec:column property="modifyTime" title="����޸�ʱ��" width="13%" style="text-align:center;" >
												  <fmt:formatDate value="${transfer.modifyTime }" pattern="yyyy-MM-dd HH:mm:ss" />
												</ec:column>
												
												<ec:column property="status" title="���״̬" width="7%" style="text-align:center;" >
													<c:forEach items="${Transfer_statusMap}" var="result">
														<c:if test="${transfer.status==result.key }">${result.value }</c:if>
													</c:forEach>
												</ec:column>
												<ec:column property="_1" title="�����鿴"  width="12%" style="text-align:center;">
													<c:choose>
														<c:when test="${transfer.status == 0}">
															<%-- <a href="#" onclick="check_transfer('<c:out value="${transfer.transferID}"/>')" style="text-align:center;">
																<img  height="20" title="���" src="<%=skinPath%>/image/app/timebargain/commodity.gif"/>
															</a>--%>
															<rightHyperlink:rightHyperlink text="<img title='���' src='${skinPath}/image/app/timebargain/commodity.gif'/>" onclick="check_transfer('${transfer.transferID}')" action="/timebargain/transfer/transferAuditForward.action"/>
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
