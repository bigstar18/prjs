<%@ page contentType="text/html;charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>�ֵ�����</title>
		<link href="${skinPath}/css/mgr/memberadmin/module.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div class="main">
			<jsp:include page="/front/frame/current.jsp"></jsp:include>
			<div class="warning">
		  		<div class="title font_orange_14b">��ܰ��ʾ��</div>
				<div class="content">�òֵ����ֵ��ţ�${entity.stockId}��Ϊ
	     				�Ѿ��������Ĳֵ���
				</div>
			</div>
			<div class="form margin_10b">
				<div class="column1">
					<h1>   �ֵ���ϸ��Ϣ  </h1>
				</div>
				<table border="0" cellspacing="0" cellpadding="0" class="content">
					<tr>
						<th width="15%" align="center" valign="middle" scope="row">�ֿ�ԭʼƾ֤�ţ�</th>
						<td width="18%" align="center" valign="middle">${entity.realStockCode }&nbsp;</td>
						<th width="15%" align="center" valign="middle" scope="row">�ֿ��ţ�</th>
                        <td width="" align="center" valign="middle">${entity.warehouseId}&nbsp;</td>
                        <th width="15%" align="center" valign="middle" scope="row">����ʱ�䣺</th>
                        <td width="" align="center" valign="middle"><fmt:formatDate value="${entity.createTime}" pattern="yyyy-MM-dd  HH:mm:ss"/>&nbsp;</td>
					</tr>
					<tr>
						<th width="15%" align="center" valign="middle" scope="row">Ʒ����</th>
						<td width="18%" align="center" valign="middle">${entity.breed.breedName}&nbsp;</td>
						<th width="15%" align="center" valign="middle" scope="row">������</th>
                        <td width="" align="center" valign="middle"><fmt:formatNumber pattern="#0.00" value="${entity.quantity }"></fmt:formatNumber>(${entity.unit }) &nbsp;</td>
						<th width="15%" align="center" valign="middle" scope="row">�����ʱ�䣺</th>
						<td width="" align="center" valign="middle"><fmt:formatDate value="${entity.lastTime}" pattern="yyyy-MM-dd  HH:mm:ss"/>&nbsp;</td>
					</tr>
					<c:if test="${not empty tpmap }" >
					<c:forEach var="map" items="${tpmap}">
					<tr>
						<td colspan="6"><div class="column1"><h1>   ${map.key.name}  </h1></div></td>
					</tr>
					<c:set var="propertysize" value="${fn:length(map.value)}"></c:set>
					<tr>
						<c:forEach var="property" items="${map.value }" varStatus="status">
							<c:if test="${(status.count-1)%3==0 and status.count!=1}"></tr><tr></c:if>
							<th align="center" valign="middle" scope="row">${property.propertyName}��</th>
							<td align="center" valign="middle">${property.propertyValue}&nbsp;</td>
						</c:forEach>
						<c:if test="${propertysize%3!=0}">
						<c:forEach begin="1" end="${3-(propertysize%3)}">
							<th align="center" valign="middle" scope="row">&nbsp;</th><td align="center" valign="middle">&nbsp;</td>
						</c:forEach>
						</c:if>
					</tr>
					</c:forEach>
					</c:if>
					<!-- �ѳ���ֵ�չʾ�����Կ -->
					<c:if test="${entity.stockStatus==2||entity.stockStatus==5}">
					<tr>
						<th width="15%" align="center" valign="middle" scope="row">�����Կ��</th>
						<td width="18%" align="center" valign="middle">${outStock.KEY}&nbsp;</td>
						<th width="15%" align="center" valign="middle" scope="row">����ˣ�</th>
						<td width="18%" align="center" valign="middle">${outStock.DELIVERYPERSON}&nbsp;</td>
						<c:if test="${outStock.IDNUMBER != null}">
						<th width="15%" align="center" valign="middle" scope="row">��������֤�ţ�</th>
						<td width="18%" align="center" valign="middle">${outStock.IDNUMBER}&nbsp;</td>
						</c:if>
					</tr>
					<c:if test="${outStock.ADDRESS != null}">
					<tr>
						<th width="15%" align="center" valign="middle" scope="row">��ַ��</th>
						<td width="18%" align="center" valign="middle">${outStock.ADDRESS}&nbsp;</td>
						<th width="15%" align="center" valign="middle" scope="row">�绰��</th>
						<td width="18%" align="center" valign="middle">${outStock.PHONE}&nbsp;</td>
					</tr>
					</c:if>
					</c:if>
				</table>
			</div>	
			<div class="form margin_10b">
				
				<div class="page text_center">&nbsp;&nbsp;
					<label><input name="button3" type="button" onclick="javascript:listjsp()" class="btbg" id="button3" value="����" /></label>
					<form id='frm' action="" method="post">
					</form>
					<script type="text/javascript">
						function listjsp(){
							frm.action="${backUrl}";
							frm.submit();
						}
					</script>
				</div>
			</div>
			<div class="form margin_10b"></div>		
		</div>
	</body>
</html>
