<%@ page contentType="text/html;charset=GBK"%>
<table border="0" width="100%" align="center">
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="/struts-tags" prefix="s"%>
	<tr>
		<td>
			<div class="div_cxtj">
				<div class="div_cxtjL"></div>
				<div class="div_cxtjC">
					仓单详情信息
				</div>
				<div class="div_cxtjR"></div>
			</div>
			<div style="clear: both;"></div>
			<div>
				<table border="0" cellspacing="0" cellpadding="4" width="100%"
					align="center" class="table2_style">
					<s:iterator id="stockOperation" value="#attr.pageInfo.result">
						<tr>
							<td width="20%" align="right">
								仓单号:
							</td>
							<td width="30%" >
								${stockOperation.stock.stockId }
								<input type="hidden" id="stock.stockId" name="stock.stockId"
									class="input_text" value="${stockOperation.stock.stockId}" />
							</td>
							<td width="20%" align="right">
								仓库编号:
							</td>
							<td width="30%">
								${stockOperation.stock.warehouseId }
								<input type="hidden" id="stock.warehouseId" name="stock.warehouseId"
									class="input_text" value="${stockOperation.stock.warehouseId}" />
							</td>
						</tr>
						<tr>
							<td align="right" width="20%">
								仓库原始凭证号：
							</td>
							<td width="30%">
								${stockOperation.stock.realStockCode}
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
								数量：
							</td>
							<td width="30%">
								<fmt:formatNumber value="${stockOperation.stock.quantity }"
									pattern="#,##0.00"/>（${stockOperation.stock.unit }）
								
							</td>
						</tr>
						<tr>
							<td width="20%" align="right">
								申请时间：
							</td>
							<td width="30%">
								<fmt:formatDate value="${stockOperation.stock.createTime}"
									pattern="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td width="20%" align="right">
								修改时间：
							</td>
							<td width="30%">
								<fmt:formatDate value="${stockOperation.stock.lastTime }"
									pattern="yyyy-MM-dd HH:mm:ss" />
							</td>
						</tr>

					</s:iterator>
					
					<c:if test="${not empty stockOperation.stock.goodsProperties }">
						<c:set var="propertysize"
							value="${fn:length(stockOperation.stock.goodsProperties)}"></c:set>
						<tr>
							<c:forEach var="property" items="${stockOperation.stock.goodsProperties }"
								varStatus="status">
								<c:if test="${(status.count-1)%2==0 and status.count!=1}">
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
</table>
