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
		<td>ί�к�</td>
		<td><div class="ordercol" id="lastTime">�����ʱ��</div></td>
		<td><div class="ordercol" id="createTime">����ʱ��</div></td>
	</tr>
	<c:forEach var="sell" items="${pageInfo.result}">
	<tr>
		<td>
			<a href="${frontPath}/bill/register/sellstockdetail.action?entity.stockId=${sell.stock.stockId}&pledgestock=${sell.pledgestock }">${sell.stock.stockId}</a>
		</td>
		<td>${sell.stock.breed.breedName}</td>
		<td>${sell.stock.warehouseId}</td>
		<td><fmt:formatNumber pattern="#0.00" value="${sell.stock.quantity }"></fmt:formatNumber></td>
		<td>${sell.stock.unit}</td>
		<td>
			<c:forEach var="module" items="${moduleSysList}">
				<c:if test="${module.MODULEID==sell.moduleId}">
					${module.CNNAME }
				</c:if>
			</c:forEach>
		</td>
		<td>${sell.orderId}</td>
		<td><fmt:formatDate value='${sell.stock.lastTime}' pattern="yyyy-MM-dd HH:mm:ss" /></td>
		<td><fmt:formatDate value='${sell.stock.createTime}' pattern="yyyy-MM-dd HH:mm:ss" /></td>
	</tr>
	</c:forEach>
</table>
