<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>¼��ƾ֤�б�</title>
		<SCRIPT type="text/javascript">
		<!-- 
			//���ƾ֤��ת
			function addVoucherForward(){

				// ��ȡ����Ȩ�޵� URL
				var addUrl=document.getElementById('add').action;
				// ��ȡ������תURL
				var url = "${basePath}"+addUrl;

				 document.location.href = url;
				// �������ҳ��
				//if(showDialog(url, "", 1000, 600)){
				//	// �����ӳɹ�����ˢ���б�
				//	ECSideUtil.reload("ec");
				//}
			}
			//����ɾ��ƾ֤
			function deleteVoucher(){
				//��ȡ����Ȩ�޵� URL
				var deleteUrl = document.getElementById('delete').action;
				//��ȡ������תURL
				var url = "${basePath}"+deleteUrl;
				//ִ��ɾ������
				updateRMIEcside(ec.ids,url);
			}
			//�鿴ƾ֤����
			function voucherDetails(voucherno){
				// ��ȡ����Ȩ�޵� URL
				var viewUrl = "/finance/voucher/viewVoucher.action";
				// ��ȡ������תURL
				var url = "${basePath}"+viewUrl;
				// �� URL ��Ӳ���
				url += "?entity.voucherNo=" + voucherno;

				document.location.href = url;
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
							<form name="frm" action="${basePath}/finance/voucher/voucherList.action?sortColumns=order+by+voucherNo+desc" method="post">
								<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
									<tr>
										<td class="table5_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td class="table3_td_1" align="right">
															ƾ֤��:&nbsp;
															<label>
																<input type="text" class="input_text" id="id"  checked="checked" name="${GNNT_}primary.voucherNo[=][Long]" value="${oldParams['primary.voucherNo[=][Long]']}" maxLength="<fmt:message key='Voucher.voucherNo_q' bundle='${PropsFieldLength}'/>"/>
															</label>
														</td>
														<td class="table3_td_1" align="right">
															ƾ֤ժҪ��:&nbsp;
															<label>
																<input type="text" class="input_text" id="id"  checked="checked" name="${GNNT_}primary.summaryNo[allLike]" value="${oldParams['primary.summaryNo[allLike]']}" maxLength="<fmt:message key='Voucher.summaryNo_q' bundle='${PropsFieldLength}'/>"/>
															</label>
														</td>
														
													</tr>
													<tr>
													   <td class="table3_td_1" align="right">
															ƾ֤ժҪ:&nbsp;
															<label>
																<input type="text" class="input_text" id="id"  checked="checked" name="${GNNT_}primary.summary[=][String]" value="${oldParams['primary.summary[=][String]']}" maxLength="<fmt:message key='Voucher.summary_q' bundle='${PropsFieldLength}'/>"/>
															</label>
														</td>	
														<td class="table3_td_anniu" align="left">
															<button class="btn_sec" id="view" onclick="dolistquery();">��ѯ</button>
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
							<rightButton:rightButton name="���ƾ֤" onclick="addVoucherForward();" className="anniu_btn" action="/finance/voucher/addVoucherForward.action" id="add"></rightButton:rightButton>
							&nbsp;&nbsp;
							<rightButton:rightButton name="ɾ��ƾ֤" onclick="deleteVoucher();" className="anniu_btn" action="/finance/voucher/deleteVoucher.action" id="delete"></rightButton:rightButton>
						</div>
						
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
								<tr>
									<td>
										<ec:table items="pageInfo.result" var="voucher"
											action="${basePath}/finance/voucher/voucherList.action?sortColumns=order+by+voucherNo"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="voucher.xls" csvFileName="voucher.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
												<ec:column cell="checkbox" headerCell="checkbox" alias="ids" style="text-align:center; " 
												value="${voucher.voucherNo}" width="5%" viewsAllowed="html" />
												<ec:column property="voucherNo" width="10%" title="ƾ֤��" style="text-align:center;">
													<a href="#" class="blank_a" onclick="return voucherDetails('${voucher.voucherNo}');"><font color="#880000">${voucher.voucherNo}</font></a>
												</ec:column>
												<ec:column property="summaryNo" title="ƾ֤ժҪ��" width="10%" style="text-align:center;" />
												<ec:column property="summary" title="ƾ֤ժҪ" width="10%" style="text-align:center;"  ellipsis="true"/>
												<ec:column property="status" title="ƾ֤״̬" width="5%" style="text-align:center;">${voucher_statusMap[voucher.status]}</ec:column>
												<ec:column property="inputUser" title="¼��Ա" width="10%" style="text-align:center;" />
												<ec:column property="inputTime" title="¼��ʱ��" width="10%" style="text-align:center;"><fmt:formatDate value="${voucher.inputTime}" pattern="yyyy-MM-dd HH:mm:ss"/></ec:column>
												<ec:column property="auditor" title="���Ա" width="10%" style="text-align:center;" />
												<ec:column property="auditTime" title="���ʱ��" width="10%" style="text-align:center;"><fmt:formatDate value="${voucher.auditTime}" pattern="yyyy-MM-dd HH:mm:ss"/></ec:column>
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