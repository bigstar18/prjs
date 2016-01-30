<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
	<head>
		<title>拆仓单详情</title>
		<meta http-equiv="Pragma" content="no-cache">
	</head>
	<body style="overflow-y: hidden">
		<form id="frm" method="post">
			<div
				style="overflow-x: hidden; position: relative; z-index: 1; overflow: auto; height: 450px; width: 100%;">
				<div class="div_cx">
					<table border="0" width="95%" align="center">
						<tr>
							<td>
								<div class="warning">
									<div class="content">
										温馨提示 :拆仓单详情
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
													仓单信息
												</div>
												<div class="div_cxtjR"></div>
											</div>
											<div style="clear: both;"></div>
											<div>
												<table border="0" cellspacing="0" cellpadding="4"
													width="100%" align="center" class="table2_style">
													<tr>
														<td align="right" width="20%">
															拆仓单号：
														</td>
														<td width="20%">
															${entity.dismantleId}
														</td>
														<td colspan="2">
															&nbsp;
														</td>
													</tr>
													<tr>
														<td align="right" width="20%">
															仓单号：
														</td>
														<td width="20%">
															${entity.stock.stockId}
														</td>
														<td align="right" width="20%">
															新仓单号：
														</td>
														<td width="30%">
															${entity.newStockId}
														</td>
													</tr>
													<tr>
														<td align="right" width="20%">
															仓库原始凭证号：
														</td>
														<td width="30%">
															<div style="width:185px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;wzy:expression(void(this.title=this.innerText))">${entity.realStockCode}</div>
														</td>
														<td align="right" width="20%">
															拆仓单数量：
														</td>
														<td width="20%">
														<fmt:formatNumber value="${entity.amount}"
																pattern="#,##0.00" />(${entity.stock.unit})
														</td>
													</tr>
													<tr>
														<td align="right" width="20%">
															品名：
														</td>
														<td width="20%">
															${entity.stock.breed.breedName}
														</td>
														<td align="right" width="20%">
															商品数量：
														</td>
														<td width="30%"><div style="width:185px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;wzy:expression(void(this.title=this.innerText))">
															<fmt:formatNumber value="${entity.stock.quantity}"
																pattern="#,##0.00" />（${entity.stock.unit }）</div>
														</td>
													</tr>
													<tr>
														<td align="right" width="20%">
															申请时间：
														</td>
														<td width="20%">
															<fmt:formatDate value="${entity.applyTime}"
																pattern="yyyy-MM-dd HH:mm:ss" />
														</td>
													<c:if test="${entity.processTime !=null}">
														<td align="right" width="20%">
															处理时间：
														</td>
														<td width="30%">
															<fmt:formatDate value="${entity.processTime}"
																pattern="yyyy-MM-dd HH:mm:ss" />
														</td>
													</c:if>
													</tr>
													<c:if test="${entity.stock.stockStatus==2}">
													<tr>
														<td align="right" width="20%">
															提货密钥：
														</td>
														<td width="20%">
															${entity.stock.key}
														</td>
													</tr>
													</c:if>
												</table>
											</div>
										</td>
									</tr>
								</table>

							</td>
						</tr>
						<tr>
							<td>
								<table border="0" width="100%" align="center">
									<c:forEach var="map" items="${tpmap}">
									<tr>
										<td>
											<div class="div_cxtj">
												<div class="div_cxtjL"></div>
												<div class="div_cxtjC">
													${map.key.name}
												</div>
												<div class="div_cxtjR"></div>
											</div>
											<div style="clear: both;"></div>
											<div>
												<table border="0" cellspacing="0" cellpadding="4"
													width="100%" align="center" class="table2_style">
													<c:if test="${not empty map.value }">
														<c:set var="propertysize"
															value="${fn:length(map.value)}"></c:set>
														<tr>
															<c:forEach var="property"
																items="${map.value }" varStatus="status">
																<c:if
																	test="${(status.count-1)%2==0 and status.count!=1}">
														</tr>
														<tr>
													</c:if>
													<td align="right" width="20%" scope="row">
														${property.propertyName}：
													</td>
													<td width="30%">
														${property.propertyValue}
													</td>
													</c:forEach>
													<c:if test="${propertysize%2!=0}">
														<c:forEach begin="1" end="${2-(propertysize%2)}">
															<th align="center" valign="middle" scope="row">
																&nbsp;
															</th>
															<td align="center" valign="middle">
																&nbsp;
															</td>
														</c:forEach>
													</c:if>
													</tr>
													</c:if>
												</table>
											</div>
										</td>
									</tr>
									</c:forEach>
								</table>
							</td>
						</tr>

						<tr>
							<td align="right">
								<button class="btn_sec" onClick=window.close();>
									关闭
								</button>
								&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
						</tr>
					</table>
				</div>
			</div>
		</form>

	</body>
</html>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>