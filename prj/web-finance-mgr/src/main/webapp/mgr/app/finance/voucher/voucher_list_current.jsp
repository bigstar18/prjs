<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>δ����ƾ֤��ѯ�б�</title>
		<SCRIPT type="text/javascript">
		<!-- 
			//�鿴��Ϣ����
			function voucherDetails(voucherno){
				var url = "${basePath}/finance/voucher/viewVoucherDetails.action?entity.voucherNo="+voucherno;
				showDialog(url, "", 700, 500);
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
							<form name="frm" action="${basePath}/finance/voucher/voucherCurrentList.action" method="post">
								<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
									<tr>
										<td class="table5_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td class="table3_td_1" align="right">ƾ֤��:&nbsp;
															<label>
																<input type="text" class="input_text" id="voucherNo"  checked="checked" name="${GNNT_}primary.voucherNo[=][Long]" value="${oldParams['primary.voucherNo[=][Long]']}" maxLength="<fmt:message key='Voucher.voucherNo_q' bundle='${PropsFieldLength}'/>"/>
															</label>
														</td>
														<td class="table3_td_1" align="right">ƾ֤ժҪ��:&nbsp;
															<label>
																<input type="text" class="input_text" id="summaryno"  checked="checked" name="${GNNT_}primary.summaryNo[allLike]" value="${oldParams['primary.summaryNo[allLike]']}" maxLength="<fmt:message key='Voucher.summaryNo_q' bundle='${PropsFieldLength}'/>"/>
															</label>
														</td>
													</tr>
													<tr>
														<td class="table3_td_1" align="right">ƾ֤ժҪ:&nbsp;
															<label>
																<input type="text" class="input_text" id="summary"  checked="checked" name="${GNNT_}primary.summary[allLike]" value="${oldParams['primary.summary[allLike]']}" maxLength="<fmt:message key='Voucher.summary_q' bundle='${PropsFieldLength}'/>"/>
															</label>
														</td>
														<td class="table3_td_anniu" align="right">
															<button class="btn_sec" id="view" onclick=dolistquery();>��ѯ</button>&nbsp;
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
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
								<tr>
									<td>
										<ec:table items="pageInfo.result" var="voucher"
											action="${basePath}/finance/voucher/voucherCurrentList.action"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="voucher.xls" csvFileName="voucher.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
												<ec:column property="voucherNo" width="10%" title="ƾ֤��" style="text-align:center;">
													<a href="#" class="blank_a" onclick="return voucherDetails('${voucher.voucherNo}');"><font color="#880000">${voucher.voucherNo}</font></a>
												</ec:column>
												<ec:column property="summaryNo" title="ƾ֤ժҪ��" width="10%" style="text-align:center;"/>
												<ec:column property="summary" title="ƾ֤ժҪ" width="15%" style="text-align:center;" ellipsis="true"/>
												<ec:column property="status" title="ƾ֤״̬" width="5%" style="text-align:center;">${voucher_statusMap[voucher.status]}</ec:column>
												<ec:column property="inputUser" title="¼��Ա" width="10%" style="text-align:center;"/>
												<ec:column property="inputTime" title="¼��ʱ��" width="10%" style="text-align:center;"><fmt:formatDate value="${voucher.inputTime}" pattern="yyyy-MM-dd HH:mm:ss"/></ec:column>
												<ec:column property="auditor" title="���Ա" width="10%" style="text-align:center;"/>
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