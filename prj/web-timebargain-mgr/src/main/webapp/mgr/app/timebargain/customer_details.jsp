<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>客户信息详情</title>
	</head>
	<body>
		<div class="div_cx">
			<table border="0" width="100%" align="center">
				<tr>
					<td>
						<div class="warning">
							<div class="content">
								温馨提示 :客户${entity.customerId}信息详情
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<table border="0" width="100%" align="center">
							<tr>
								<td>
									<div class="div_cxtj">
										<div class="div_cxtjL"></div>
										<div class="div_cxtjC">
											详情信息
										</div>
										<div class="div_cxtjR"></div>
									</div>
									<div style="clear: both;"></div>
									<div>
										<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
												<tr>
													<td align="right">
														<fmt:message key="Customer.customerId" bundle="${PropsFieldDic}" />：
													</td>
													<td>
														${entity.customerId}&nbsp;
													</td>
													<td align="right">
														<fmt:message key="MFirm.name" bundle="${PropsFieldDic}" />：
													</td>
													<td>
														${entity.firm.name}&nbsp;
													</td>
												</tr>
												<tr>
													<td align="right">
														<fmt:message key="Customer.status" bundle="${PropsFieldDic}" />：
													</td>
													<td>
														${customer_statusMap[entity.status]}
													</td>
													<td align="right">
														<fmt:message key="Customer.type" bundle="${PropsFieldDic}" />：
													</td>
													<td>
														${customer_typeMap[entity.type]}
													</td>
												</tr>
												<tr>
													<td align="right">
														<fmt:message key="Customer.name" bundle="${PropsFieldDic}" />：
													</td>
													<td>
														${entity.name}
													</td>
													<td align="right">
														<fmt:message key="Customer.fullName" bundle="${PropsFieldDic}" />：
													</td>
													<td colspan="2">
														${entity.fullName}
													</td>
												</tr>
												<tr>
													<td align="right">
														<fmt:message key="Customer.cardType" bundle="${PropsFieldDic}" />：
													</td>
													<td>
														${customer_cardTypeMap[entity.cardType]}
													</td>
													<td align="right">
														<fmt:message key="Customer.card" bundle="${PropsFieldDic}" />：
													</td>
													<td>
														${entity.card}
													</td>
												</tr>
												<tr>
													<td align="right">
														<fmt:message key="Customer.bankCode" bundle="${PropsFieldDic}" />：
													</td>
													<td>
														${customer_bankCodeMap[entity.bankCode]}
													</td>
													<td align="right">
														<fmt:message key="Customer.bankAccount" bundle="${PropsFieldDic}" />：
													</td>
													<td colspan="2">
														${entity.bankAccount}
													</td>
												</tr>
												<tr>
													<td align="right">
														<fmt:message key="Customer.contactMan" bundle="${PropsFieldDic}" />：
													</td>
													<td>
														${entity.contactMan}
													</td>
													<td align="right">
														<fmt:message key="Customer.phone" bundle="${PropsFieldDic}" />：
													</td>
													<td>
														${entity.phone}
													</td>
												</tr>
												<tr>
													<td align="right">
														<fmt:message key="Customer.email" bundle="${PropsFieldDic}" />：
													</td>
													<td>
														${entity.email}
													</td>
													<td align="right">
														<fmt:message key="Customer.postcode" bundle="${PropsFieldDic}" />：
													</td>
													<td>
														${entity.postcode}
													</td>
												</tr>
												<tr>
													<td align="right">
														<fmt:message key="Customer.address" bundle="${PropsFieldDic}" />：
													</td>
													<td colspan="3">
														${entity.address}
													</td>
												</tr>
												<tr>
													<td align="right">
														<fmt:message key="Customer.note" bundle="${PropsFieldDic}" />：
													</td>
													<td colspan="3">
														${entity.note}
													</td>
												</tr>
											</table>
									</div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<div class="tab_pad">
			<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center">
				<tr>
					<td align="right">
						<button class="btn_sec" onClick="window.close();">关闭</button>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>