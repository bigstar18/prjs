<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<table border="0" cellspacing="0" cellpadding="0" class="content">
	<tr class="font_b tr_bg">
		<td><div class="ordercol" id="stockId">仓单号</div></td>
		<td><div class="ordercol" id="breed.breedName">品名</div></td>
		<td><div class="ordercol" id="warehouseId">仓库编号</div></td>
		<td><div class="ordercol" id="quantity">商品数量</div></td>
		<td>单位</td>
		<td><div class="ordercol" id="lastTime">最后变更时间</div></td>
		<td><div class="ordercol" id="createTime">创建时间</div></td>
	</tr>
	<c:forEach var="financing" items="${pageInfo.result}">
	<tr>
		<td>
			<a href="${frontPath}/bill/register/financingstockdetail.action?entity.stockId=${financing.stock.stockId}">${financing.stock.stockId}</a>
		</td>
		<td>${financing.stock.breed.breedName}</td>
		<td>${financing.stock.warehouseId}</td>
		<td><fmt:formatNumber pattern="#0.00" value="${financing.stock.quantity }"></fmt:formatNumber></td>
		<td>${financing.stock.unit}</td>
		<td><fmt:formatDate value='${financing.stock.lastTime}' pattern="yyyy-MM-dd HH:mm:ss" /></td>
		<td><fmt:formatDate value='${financing.stock.createTime}' pattern="yyyy-MM-dd HH:mm:ss" /></td>
	</tr>
	</c:forEach>
</table>
