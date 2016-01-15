<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>ժҪ�б�</title>
		<SCRIPT type="text/javascript">
		<!-- 
			//�����Ϣ��ת
			function addSummaryForward(){
				//��ȡ����Ȩ�޵� URL
				var addUrl=document.getElementById('add').action;
				//��ȡ������תURL
				var url = "${basePath}"+addUrl;
				//�������ҳ��
				if(showDialog(url, "", 750, 350)){
					//�����ӳɹ�����ˢ���б�
					document.getElementById("view").click();
				}
			}
			//�޸���Ϣ��ת
			function updateSummaryForward(code){
				//��ȡ����Ȩ�޵� URL
				var updateUrl = document.getElementById('update'+customerId).action;
				//��ȡ������תURL
				var url = "${basePath}"+updateUrl;
				//�� URL ��Ӳ���
				if(url.indexOf("?")>0){
					url += "&entity.code="+code;
				}else{
					url += "?entity.code="+code;
				}
				//�����޸�ҳ��
				if(showDialog(url, "", 800, 550)){
					//����޸ĳɹ�����ˢ���б�
					document.getElementById("view").click();
				};
			}
			//����ɾ����Ϣ
			function deleteSummaryList(){
				//��ȡ����Ȩ�޵� URL
				var deleteUrl = document.getElementById('delete').action;
				//��ȡ������תURL
				var url = "${basePath}"+deleteUrl+"?autoInc=false";
				//ִ��ɾ������
				updateRMIEcside(ec.ids,url);
			}
			//�鿴��Ϣ����
			function summaryDetails(code){
				var url = "${basePath}/finance/summary/updateSummaryforward.action?entity.summaryNo="+code;
				if(showDialog(url, "", 750, 350)){
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
							<form name="frm" action="${basePath}/finance/summary/summaryList.action?sortColumns=order+by+summaryNo" method="post">
								<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
									<tr>
										<td class="table5_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td class="table3_td_1" align="left">
															ժ Ҫ ��:&nbsp;
															<label>
																<input type="text" class="input_text" id="summaryNo"  checked="checked" name="${GNNT_}primary.summaryNo[allLike]" value="${oldParams['primary.summaryNo[allLike]']}" maxLength="5"/>
															</label>
														</td>
														<td class="table3_td_1" align="left">
															ժҪ����:&nbsp;
															<label>
																<input type="text" class="input_text" id="summary" name="${GNNT_}primary.summary[allLike]" value="${oldParams['primary.summary[allLike]'] }" maxLength="16"/>
															</label>
														</td>
														<td class="table3_td_anniu" align="left">
															<button class="btn_sec" id="view" onclick="dolistquery();">��ѯ</button>
															&nbsp;&nbsp;
															<button class="btn_cz" onclick="myReset();">����</button>
														</td>
													</tr>
													<tr>
														<td class="table3_td_1" align="left">
															��������:&nbsp;
															<label>
																<select id="ledgerFieldCode" name="${GNNT_}primary.ledgerField.code[=]"  class="normal" style="width: 120px">
																	<option value="">ȫ��</option>
																	<c:forEach items="${ledgerFieldPage.result}" var="map">
																		<option value="${map.code}">${map.name}</option>
																	</c:forEach>
																</select>
															</label>
															 <script >
																frm.ledgerFieldCode.value = "<c:out value='${oldParams["primary.ledgerField.code[=]"] }'/>";
					  										</script>
														</td>
														<td class="table3_td_1" align="left" colspan="2">
															ϵ&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ͳ:&nbsp;
															<label>
																<select id="ledgerFieldmoduleId" name="${GNNT_}primary.ledgerField.moduleId[=]"  class="normal" style="width: 120px">
																	<option value="">ȫ��</option>
																	<c:forEach items="${tradeMoudelList}" var="map">
																		<option value="${map['MODULEID']}">${map["NAME"]}</option>
																	</c:forEach>
																	
																</select>
															</label>
															 <script >
																frm.ledgerFieldmoduleId.value = "<c:out value='${oldParams["primary.ledgerField.moduleId[=]"] }'/>";
					  										</script>
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
							<rightButton:rightButton name="���" onclick="addSummaryForward();" className="anniu_btn" action="/finance/summary/addSummaryForward.action" id="add"></rightButton:rightButton>
							&nbsp;&nbsp;
							<rightButton:rightButton name="ɾ��" onclick="deleteSummaryList();" className="anniu_btn" action="/finance/summary/deleteSummaryList.action" id="delete"></rightButton:rightButton>
						</div>
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
								<tr>
									<td>
										<ec:table items="pageInfo.result" var="summary"
											action="${basePath}/finance/summary/summaryList.action?sortColumns=order+by+summaryNo"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="demo.xls" csvFileName="demo.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
												<ec:column cell="checkbox" headerCell="checkbox" alias="ids" style="text-align:center; " value="${summary.summaryNo}" width="5%" viewsAllowed="html" />
												<ec:column property="summaryNo" width="10%" title="ժҪ��" style="text-align:center;">
													<a href="#" class="blank_a" onclick="return summaryDetails('${summary.summaryNo}');"><font color="#880000">${summary.summaryNo}</font>&nbsp;</a>
												</ec:column>
												<ec:column property="summary" title="ժҪ����" width="10%" style="text-align:center;" ellipsis="true"/>
												<ec:column property="ledgerField.name" title="��������" width="10%" style="text-align:center;">${summary.ledgerField.name }</ec:column>
												<ec:column property="fundDCFlag" title="�ʽ�������" width="10%" style="text-align:center;">${summary_fundDCFlagMap[summary.fundDCFlag]}</ec:column>
												<ec:column property="accountCodeOpp" title="�Է���Ŀ����" width="10%" style="text-align:center;"/>
												<ec:column property="appendAccount" title="������" width="10%" style="text-align:center;">${summary_appendAccountMap[summary.appendAccount]}</ec:column>
												<ec:column property="isInit" title="�Ƿ��ʼ��" width="10%" style="text-align:center;">${summary_isInitMap[summary.isInit]}</ec:column>
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