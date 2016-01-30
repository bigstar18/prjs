<%@ page contentType="text/html;charset=GBK"%>
<table border="0" width="100%" align="center">
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="/struts-tags" prefix="s"%>
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
						${property.propertyName}£º
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