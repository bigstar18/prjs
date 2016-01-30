<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>ģ���б�</title>
		<SCRIPT type="text/javascript">
		<!-- 
			//�����Ϣ��ת
			function addVoucherModelForward(){
				//��ȡ����Ȩ�޵� URL
				var addUrl=document.getElementById('add').action;
				//��ȡ������תURL
				var url = "${basePath}"+addUrl;
				//�������ҳ��
				if(showDialog(url, "", 800, 450)){
					//�����ӳɹ�����ˢ���б�
					document.getElementById("view").click();
				}
			}
			//�޸���Ϣ��ת
			function updateVoucherModelForward(code){
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
			function deleteVoucherModelList(){
				//��ȡ����Ȩ�޵� URL
				var deleteUrl = document.getElementById('delete').action;
				//��ȡ������תURL
				var url = "${basePath}"+deleteUrl;
				//ִ��ɾ������
				updateRMIEcside(ec.ids,url);
			}
			//�鿴��Ϣ����
			function voucherModelDetails(code){
				var url = "${basePath}/finance/financialVindicate/updateVoucherModelforward.action?entity.code="+code;
				if(showDialog(url, "", 750, 450)){
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
							<form name="frm" action="${basePath}/finance/financialVindicate/voucherModelList.action?sortColumns=order+by+code" method="post">
								<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
									<tr>
										<td class="table5_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td class="table3_td_1" align="left">
															ģ�����:&nbsp;
															<label>
																<input type="text" class="input_text" id="code"  checked="checked" name="${GNNT_}primary.code[allLike]" value="${oldParams['primary.code[allLike]']}" maxLength="8"/>
															</label>
														</td>
														<td class="table3_td_1" align="left">
															ģ������:&nbsp;
															<label>
																<input type="text" class="input_text" id="name" name="${GNNT_}primary.name[allLike]" value="${oldParams['primary.name[allLike]'] }" maxLength="32"/>
															</label>
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
							<rightButton:rightButton name="���" onclick="addVoucherModelForward();" className="anniu_btn" action="/finance/financialVindicate/addVoucherModelForward.action" id="add"></rightButton:rightButton>
							&nbsp;&nbsp;
							<rightButton:rightButton name="ɾ��" onclick="deleteVoucherModelList();" className="anniu_btn" action="/finance/financialVindicate/deleteVoucherModelList.action" id="delete"></rightButton:rightButton>
						</div>
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
								<tr>
									<td>
										<ec:table items="pageInfo.result" var="voucherModel"
											action="${basePath}/finance/financialVindicate/voucherModelList.action?sortColumns=order+by+code"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="demo.xls" csvFileName="demo.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
												<ec:column cell="checkbox" headerCell="checkbox" alias="ids" style="text-align:center; " value="${voucherModel.code}" width="5%" viewsAllowed="html" />
												<ec:column property="code" width="10%" title="ģ�����" style="text-align:center;">
													<a href="#" class="blank_a" onclick="return voucherModelDetails('${voucherModel.code}');"><font color="#880000">${voucherModel.code}</font>&nbsp;</a>
												</ec:column>
												<ec:column property="name" title="ģ������" width="10%" style="text-align:center;"/>
												<ec:column property="summaryNo" title="ժҪ��" width="10%" style="text-align:center;"/>
												<ec:column property="debitCode" title="�跽��Ŀ����" width="10%" style="text-align:center;"/>
												<ec:column property="creditCode" title="������Ŀ����" width="10%" style="text-align:center;"/>
												<ec:column property="needcontractNo" title="��Ҫ��ͬ��" width="10%" style="text-align:center;">${voucherModel_need[voucherModel.needcontractNo]}</ec:column>
												<ec:column property="note" title="��ע" width="10%" style="text-align:center;"/>
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