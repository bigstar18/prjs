<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>ժҪ�б�</title>
		<SCRIPT type="text/javascript">
		<!-- 
			//�����Ϣ��ת
			function addAccountForward(){
				//��ȡ����Ȩ�޵� URL
				var addUrl=document.getElementById('add').action;
				//��ȡ������תURL
				var url = "${basePath}"+addUrl;
				//�������ҳ��
				if(showDialog(url, "", 550, 350)){
					//�����ӳɹ�����ˢ���б�
					document.getElementById("view").click();
				}
			}
			//�޸���Ϣ��ת
			function updateAccountForward(code){
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
			function deleteAccountList(){
				//��ȡ����Ȩ�޵� URL
				var deleteUrl = document.getElementById('delete').action;
				//��ȡ������תURL
				var url = "${basePath}"+deleteUrl+"?autoInc=false";
				//ִ��ɾ������
				updateRMIEcside(ec.ids,url);
			}
			//�鿴��Ϣ����
			function accountDetails(code){
				var url = "${basePath}/finance/account/updateAccountforward.action?entity.code="+code;
				if(showDialog(url, "", 550, 350)){
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
							<form name="frm" action="${basePath}/finance/account/accountList.action?sortColumns=order+by+code" method="post">
								<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
									<tr>
										<td class="table5_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td class="table3_td_1" align="left">
															��Ŀ����:&nbsp;
															<label>
																<input type="text" class="input_text" id="code"  checked="checked" name="${GNNT_}primary.code[allLike]" value="${oldParams['primary.code[allLike]']}" />
															</label>
														</td>
														<td class="table3_td_1" align="left">
															��Ŀ����:&nbsp;
															<label>
																<input type="text" class="input_text" id="name" name="${GNNT_}primary.name[allLike]" value="${oldParams['primary.name[allLike]'] }"/>
															</label>
														</td>
														<td class="table3_td_anniu" align="left">
															<button class="btn_sec" id="view" onclick=dolistquery();>��ѯ</button>
															&nbsp;&nbsp;
															<button class="btn_cz" onclick="myReset();">����</button>
														</td>
													</tr>
													<tr>
														<td class="table3_td_1" align="left">
															�������:&nbsp;
															<label>
																<select id="dcFlag" name="${GNNT_}primary.dcFlag[=]"  class="normal" style="width: 120px">
																	<option value="">ȫ��</option>
																	<c:forEach items="${account_dCFlagMap}" var="map">
																		<option value="${map.key}">${map.value}</option>
																	</c:forEach>
																</select>
															</label>
															 <script >
																frm.dcFlag.value = "<c:out value='${oldParams["primary.dcFlag[=]"] }'/>";
					  										</script>
														</td>
														<td class="table3_td_1" align="left" colspan="2">
															��Ŀ����:&nbsp;
															<label>
																<select id="accountLevel" name="${GNNT_}primary.accountLevel[=]"  class="normal" style="width: 120px">
																	<option value="">ȫ��</option>
																	<option value="1">1</option>
																	<option value="2">2</option>
																	<option value="3">3</option>
																	<option value="4">4</option>
																</select>
															</label>
															 <script >
																frm.accountLevel.value = "<c:out value='${oldParams["primary.accountLevel[=]"] }'/>";
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
							<rightButton:rightButton name="���" onclick="addAccountForward();" className="anniu_btn" action="/finance/account/addAccountForward.action" id="add"></rightButton:rightButton>
							&nbsp;&nbsp;
							<rightButton:rightButton name="ɾ��" onclick="deleteAccountList();" className="anniu_btn" action="/finance/account/deleteAccountList.action" id="delete"></rightButton:rightButton>
						</div>
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
								<tr>
									<td>
										<ec:table items="pageInfo.result" var="account"
											action="${basePath}/finance/account/accountList.action?sortColumns=order+by+code"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="demo.xls" csvFileName="demo.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
												<ec:column cell="checkbox" headerCell="checkbox" alias="ids" style="text-align:center; " value="${account.code}" width="5%" viewsAllowed="html" />
												<ec:column property="code" width="10%" title="��Ŀ����" style="text-align:center;">
													<a href="#" class="blank_a" onclick="return accountDetails('${account.code}');"><font color="#880000">${account.code}</font>&nbsp;</a>
												</ec:column>
												<ec:column property="name" title="��Ŀ����" width="10%" style="text-align:center;"/>
												<ec:column property="dcFlag" title="�������" width="10%" style="text-align:center;">${account_dCFlagMap[account.dcFlag]}</ec:column>
												<ec:column property="accountLevel" title="��Ŀ����" width="10%" style="text-align:center;"/>
												<ec:column property="isInit" title="�Ƿ��ʼ��" width="10%" style="text-align:center;">${account_isInitMap[account.isInit]}</ec:column>
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