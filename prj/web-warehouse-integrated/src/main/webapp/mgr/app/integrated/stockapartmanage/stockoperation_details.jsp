<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
	<head>
		<title>仓单详情</title>
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
										温馨提示 :仓单详情
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
													<s:iterator id="stockOperation"
														value="#attr.pageInfo.result">
														<tr>
															<td width="20%" align="right">
																仓单号：
															</td>
															<td width="30%">
																${stockOperation.stock.stockId }
																<input type="hidden" id="stock.stockId"
																	name="stock.stockId" class="input_text"
																	value="${stockOperation.stock.stockId}" />
															</td>
															<!-- 
															<td width="20%" align="right">
																仓库编号：
															</td>
															<td width="30%">
																${stockOperation.stock.warehouseId }
																<input type="hidden" id="stock.warehouseId"
																	name="stock.warehouseId" class="input_text"
																	value="${stockOperation.stock.warehouseId}" />
															</td>
															 -->
														</tr>
														<tr>
															<td align="right" width="20%">
																仓库原始凭证号：
															</td>
															<td width="30%">
																<div style="width:180px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;wzy:expression(void(this.title=this.innerText))">${stockOperation.stock.realStockCode}</div>
															</td>
															<td align="right" width="20%">
																所属交易商：
															</td>
															<td width="20%">
																${stockOperation.stock.ownerFirm}
															</td>
														</tr>
														<tr>
															<td width="20%" align="right">
																品名：
															</td>
															<td width="30%">
																${stockOperation.stock.breed.breedName }
															</td>
															<td width="20%" align="right">
																商品数量：
															</td>
															<td width="30%"><div style="width:180px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;wzy:expression(void(this.title=this.innerText))">
																<fmt:formatNumber
																	value="${stockOperation.stock.quantity }"
																	pattern="#,##0.00" />
																（${stockOperation.stock.unit }）					
															</div>

															</td>
														</tr>
														<tr>
															<td width="20%" align="right">
																创建时间：
															</td>
															<td width="30%">
																<fmt:formatDate
																	value="${stockOperation.stock.createTime}"
																	pattern="yyyy-MM-dd HH:mm:ss" />
															</td>
															<td width="20%" align="right">
																最后变更时间：
															</td>
															<td width="30%">
																<fmt:formatDate
																	value="${stockOperation.stock.lastTime }"
																	pattern="yyyy-MM-dd HH:mm:ss" />
															</td>
														</tr>

													</s:iterator>
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