<%@ page contentType="text/html;charset=GBK"%>
<table border="0" width="100%" align="center">
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
					<tr>
						<td align="right" width="20%">
							仓单号：
						</td>
						<td width="20%">
							${entity.stockId}
						</td>
						<td align="right" width="20%">
							仓库编号：
						</td>
						<td width="30%">
							${entity.warehouseId}
						</td>
					</tr>
					<tr>
						<td align="right" width="20%">
							仓库原始凭证号：
						</td>
						<td width="30%">
							${entity.realStockCode}
						</td>
						<td align="right" width="20%">
							所属交易商：
						</td>
						<td width="20%">
							${entity.ownerFirm}
						</td>
					</tr>
					<tr>
						<td align="right" width="20%">
							商品名称：
						</td>
						<td width="20%">
							${entity.breed.breedName}
						</td>
						<td align="right" width="20%">
							商品数量（${entity.unit }）：
						</td>
						<td width="30%">
							<fmt:formatNumber value="${entity.quantity}"
								pattern="#,##0.00"/>
						</td>
					</tr>
					<tr>
						<td align="right" width="20%">
							创建时间：
						</td>
						<td width="20%">
							<fmt:formatDate value="${entity.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
					<c:if test="${entity.lastTime !=null||entity.lastTime ! ='' }">
						<td align="right" width="20%">
							变更时间：
						</td>
						<td width="30%">
							<fmt:formatDate value="${entity.lastTime}"
								pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
					</c:if>
					</tr>
					<c:if test="${not empty entity.goodsProperties }" >
					<c:set var="propertysize" value="${fn:length(entity.goodsProperties)}"></c:set>
					<tr>
						<c:forEach var="property" items="${entity.goodsProperties }" varStatus="status">
							<c:if test="${(status.count-1)%2==0 and status.count!=1}"></tr><tr></c:if>
							<td align="right"  width="20%" scope="row">${property.propertyName}：</td>
							<td width="30%" >${property.propertyValue}</td>
						</c:forEach>
						<c:if test="${propertysize%2!=0}">
						<c:forEach begin="1" end="${2-(propertysize%2)}">
							<th align="center" valign="middle" scope="row">&nbsp;</th><td align="center" valign="middle">&nbsp;</td>
						</c:forEach>
						</c:if>
					</tr>
					</c:if>
				</table>
			</div>
		</td>
	</tr>
</table>
