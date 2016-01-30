<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<table border="0" cellspacing="0" cellpadding="0" class="content">
	<tr class="font_b tr_bg">
		<td><div class="ordercol" id="stockId">�ֵ���</div></td>
		<td><div class="ordercol" id="breed.breedName">Ʒ��</div></td>
		<td><div class="ordercol" id="warehouseId">�ֿ���</div></td>
		<td><div class="ordercol" id="quantity">��Ʒ����</div></td>
		<td>��λ</td>
		<td>ģ����</td>
		<td>��ͬ��</td>
		<td><div class="ordercol" id="lastTime">�����ʱ��</div></td>
		<td><div class="ordercol" id="createTime">����ʱ��</div></td>
		<c:if test="${operation == ''}">
			<td>����</td>
		</c:if>
	</tr>
	<c:forEach var="trade" items="${pageInfo.result}">
	<tr>
		<td>
			<a href="${frontPath}/bill/register/tradestockdetail.action?entity.stockId=${trade.stock.stockId}&tradestock=${trade.tradeStockId}">${trade.stock.stockId}</a>
		</td>
		<td>${trade.stock.breed.breedName}</td>
		<td>${trade.stock.warehouseId}</td>
		<td><fmt:formatNumber pattern="#0.00" value="${trade.stock.quantity }"></fmt:formatNumber></td>
		<td>${trade.stock.unit}</td>
		<td>
			<c:forEach var="module" items="${moduleSysList}">
				<c:if test="${module.MODULEID==trade.moduleId}">
					${module.CNNAME }
				</c:if>
			</c:forEach>
		</td>
		<td>${trade.tradeNo }</td>
		<td><fmt:formatDate value='${trade.stock.lastTime}' pattern="yyyy-MM-dd HH:mm:ss" /></td>
		<td><fmt:formatDate value='${trade.stock.createTime}' pattern="yyyy-MM-dd HH:mm:ss" /></td>
		<c:if test="${operation == ''}">
			<td><a href="#" onclick="logoutStock('${trade.stock.stockId}')">ע��</a></td>
		</c:if>
	</tr>
	</c:forEach>
</table>
