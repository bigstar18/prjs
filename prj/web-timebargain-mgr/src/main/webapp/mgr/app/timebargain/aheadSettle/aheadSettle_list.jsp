<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>��ǰ�����б�</title>
		<SCRIPT type="text/javascript">
		<!-- 
			//������ת
			function addAheadSettleForward(){
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
			
			//�ֵ�����鿴��ת
			function showBillInfo(applyId){
				//��ȡ����Ȩ�޵� URL
				var showUrl="/timebargain/aheadSettle/getBillListByApplyId.action";
				//��ȡ������תURL
				var url = "${basePath}"+showUrl+"?entity.applyId="+applyId;
				//�������ҳ��
				showDialog(url, "", 700, 550);
			}
			
			
			//�����ǰ������Ϣ��ת
			function auditAheadSettleForward(applyId){
				//��ȡ����Ȩ�޵� URL
				var auditUrl = "/timebargain/aheadSettle/aheadSettleAuditForward.action"
				//��ȡ������תURL
				var url = "${basePath}"+auditUrl+"?entity.applyId="+applyId;
				//�����޸�ҳ��
				if(showDialog(url, "", 700, 400)){
					//����޸ĳɹ�����ˢ���б�
					document.getElementById("view").click();
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
						<div class="div_cx">
							<form name="frm" action="${basePath}/timebargain/aheadSettle/aheadSettleList.action?sortColumns=order+by+applyID" method="post">
								<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
									<tr>
										<td class="table5_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0" cellpadding="0" style="width: 100%;">
													<tr>
														<td align="left" style="width:300px;height:40px;padding:0px;line-height:40px;color:#000;">
															������������:&nbsp;
															<label>
																<input type="text" class="input_text" id="customerId_S" name="${GNNT_}primary.customerId_S[=][String]" value="${oldParams['primary.customerId_S[=][String]']}" />
															</label>
														</td>
														<td align="left" style="width:300px;height:40px;padding:0px;line-height:40px;color:#000;">
															�򷽶�������:&nbsp;
															<label>
																<input type="text" class="input_text" id="customerId_B" name="${GNNT_}primary.customerId_B[=][String]" value="${oldParams['primary.customerId_B[=][String]'] }" />
															</label>
														</td>
													</tr>
													<tr>
														<td align="left" style="width:300px;height:40px;padding:0px;line-height:40px;color:#000;">
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��Ʒ����:&nbsp;
															<label>
																<input type="text" class="input_text" id="commodityId" name="${GNNT_}primary.commodityId[=][String]" value="${oldParams['primary.commodityId[=][String]'] }"/>
															</label>
														</td>
														<td align="left" style="width:300px;height:40px;padding:0px;line-height:40px;color:#000;">
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;״̬:&nbsp;
															<label>
																<select id="status" name="${GNNT_}primary.status[=][int]"  class="normal" style="width: 120px">
																	<option value="">ȫ��</option>
																	<c:forEach items="${audit_statusMap}" var="map">
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
							<rightButton:rightButton name="����" onclick="addAheadSettleForward();" className="anniu_btn" action="/timebargain/aheadSettle/addAheadSettleforward.action" id="add"></rightButton:rightButton>
						</div>
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
								<tr>
									<td>
										<ec:table items="pageInfo.result" var="applyAheadSettle" 
											action="${basePath}/timebargain/aheadSettle/aheadSettleList.action?sortColumns=order+by+applyID"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="demo.xls" csvFileName="demo.csv"
											showPrint="true" listWidth="150%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
												<ec:column property="applyId" title="���뵥��" width="5%" style="text-align:center;"/>
												<ec:column property="commodityId" title="��Ʒ����" width="5%" style="text-align:center;"/>
												<ec:column property="customerId_S" title="������������" width="9%" ellipsis="true" style="text-align:center;"/>
												<ec:column property="customerId_B" title="�򷽶�������" width="9%" ellipsis="true" style="text-align:center;"/>
												<ec:column property="price" title="���ռ۸�" width="6%" style="text-align:center;">
												<c:if test="${applyAheadSettle.price!=0}"><fmt:formatNumber value="${applyAheadSettle.price}" pattern="#0.00"/></c:if>
												<c:if test="${applyAheadSettle.price==0}">�����۽���</c:if>
												</ec:column>
												<ec:column property="quantity" title="��������" width="5%" style="text-align:center;"/>
												<ec:column property="status" title="״̬" width="6%" style="text-align:center;">
												<c:forEach items="${audit_statusMap}" var="result">
													<c:if test="${applyAheadSettle.status==result.key }">${result.value }</c:if>
												</c:forEach>
												</ec:column>
												<ec:column property="createTime" title="����ʱ��" width="8%" style="text-align:center;">
												<fmt:formatDate value="${applyAheadSettle.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
												</ec:column>
												<ec:column property="creator" title="������" width="6%" style="text-align:center;" ellipsis="true"/>
												<ec:column property="remark1" title="�����˱�ע" width="7%" style="text-align:center;" ellipsis="true"/>
												<ec:column property="modifyTime" title="�޸�ʱ��" width="8%" style="text-align:center;">
												<fmt:formatDate value="${applyAheadSettle.modifyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
												</ec:column>
												<ec:column property="modifier" title="����޸���" width="5%" style="text-align:center;"/>
												<ec:column property="remark2" title="�޸��˱�ע" width="7%" style="text-align:center;"/>
												<ec:column property="_1" title="�鿴�ֵ�" width="4%" style="text-align:center;" sortable="false">
												<c:if test="${applyAheadSettle.status!=3}">
												<a href="#" onclick="javescript:showBillInfo('${applyAheadSettle.applyId }');">�ֵ�����</a>
												</c:if>
												<c:if test="${applyAheadSettle.status==3}">
												--
												</c:if>
												</ec:column>
												<ec:column property="_2" title="���" width="3%" style="text-align:center;" sortable="false">
												<c:if test="${applyAheadSettle.status==1}">
												<a href="#" onclick="javescript:auditAheadSettleForward('${applyAheadSettle.applyId }');">���</a>
												</c:if>
												<c:if test="${applyAheadSettle.status!=1}">
												--
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