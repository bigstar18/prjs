<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>财务审核列表</title>
		<SCRIPT type="text/javascript">
		<!-- 
			//查看凭证详情
			function voucherDetails(voucherno){
				var url = "${basePath}/finance/voucher/viewVoucherAudit.action?entity.voucherNo="+voucherno;
				//showDialog(url, "", 700, 500);
				document.location.href = url;
			}
			//执行查询列表
			function dolistquery() {
				frm.submit();
			}

			function auditVoucher(vIsPass){
				if( vIsPass == "true" )
					msg = "确认审核【通过】？审核通过后凭证将不能修改！";
				else
					msg = "确认审核【失败】？审核失败后凭证将变为编辑状态！";
				if(confirm( msg )){
					frm.isPass.value = vIsPass;
					var url = "${basePath}/finance/voucher/auditVoucher.action?isPass="+frm.isPass.value;
					deleteEcside(ec.ids,url);
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
							<form name="frm" action="${basePath}/finance/voucher/voucherAuditList.action?sortColumns=order+by+voucherNo+desc" method="post">
								<input name="isPass" value="" type="hidden">
								<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
									<tr>
										<td class="table5_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td class="table3_td_1" align="right">
															凭证号:&nbsp;
															<label>
																<input type="text" class="input_text" id="id"  checked="checked" name="${GNNT_}primary.voucherNo[=][Long]" value="${oldParams['primary.voucherNo[=][Long]']}" maxLength="<fmt:message key='Voucher.voucherNo_q' bundle='${PropsFieldLength}'/>"/>
															</label>
														</td>
														<td class="table3_td_1" align="right">
															凭证摘要号:&nbsp;
															<label>
																<input type="text" class="input_text" id="id"  checked="checked" name="${GNNT_}primary.summaryNo[allLike]" value="${oldParams['primary.summaryNo[allLike]']}" maxLength="<fmt:message key='Voucher.summaryNo_q' bundle='${PropsFieldLength}'/>"/>
															</label>
														</td>
														
													</tr>
													<tr>
														<td class="table3_td_1" align="right">
															凭证摘要:&nbsp;
															<label>
																<input type="text" class="input_text" id="id"  checked="checked" name="${GNNT_}primary.summary[=][String]" value="${oldParams['primary.summary[=][String]']}" maxLength="<fmt:message key='Voucher.summary_q' bundle='${PropsFieldLength}'/>"/>
															</label>
														</td>
														<td class="table3_td_anniu" align="left">
															<button class="btn_sec" id="view" onclick=dolistquery();>查询</button>
															&nbsp;&nbsp;
															<button class="btn_cz" onclick="myReset();">重置</button>
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
							<rightButton:rightButton name="审核通过" onclick="auditVoucher('true');" className="anniu_btn" action="/finance/voucher/auditVoucher.action" id="add"></rightButton:rightButton>
							&nbsp;&nbsp;
							<rightButton:rightButton name="审核不通过" onclick="auditVoucher('false');" className="anniu_btn" action="/finance/voucher/auditVoucher.action" id="delete"></rightButton:rightButton>
						</div>
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
								<tr>
									<td>
										<ec:table items="pageInfo.result" var="voucher"
											action="${basePath}/finance/voucher/voucherAuditList.action?sortColumns=order+by+voucherNo"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="voucher.xls" csvFileName="voucher.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
												<ec:column cell="checkbox" headerCell="checkbox" alias="ids" style="text-align:center; " value="${voucher.voucherNo}" width="5%" viewsAllowed="html" />
												<ec:column property="voucherNo" width="10%" title="凭证号" style="text-align:center;">
													<a href="#" class="blank_a" onclick="return voucherDetails('${voucher.voucherNo}');"><font color="#880000">${voucher.voucherNo}</font></a>
												</ec:column>
												<ec:column property="summaryNo" title="凭证摘要号" width="10%" style="text-align:center;" />
												<ec:column property="summary" title="凭证摘要" width="10%" style="text-align:center;"  ellipsis="true"/>
												<ec:column property="status" title="凭证状态" width="5%" style="text-align:center;">${voucher_statusMap[voucher.status]}</ec:column>
												<ec:column property="inputUser" title="录入员" width="10%" style="text-align:center;" />
												<ec:column property="inputTime" title="录入时间" width="10%" style="text-align:center;"><fmt:formatDate value="${voucher.inputTime}" pattern="yyyy-MM-dd HH:mm:ss"/></ec:column>
												<ec:column property="auditor" title="审核员" width="10%" style="text-align:center;" />
												<ec:column property="auditTime" title="审核时间" width="10%" style="text-align:center;"><fmt:formatDate value="${voucher.auditTime}" pattern="yyyy-MM-dd HH:mm:ss"/></ec:column>
											</ec:row>
										</ec:table>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
			</table>
		<!-- 编辑和过滤所使用的 通用的文本框模板 -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;" name="" />
		</textarea>
	</body>
</html>