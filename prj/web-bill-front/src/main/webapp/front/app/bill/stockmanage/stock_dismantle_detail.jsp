<%@ page contentType="text/html;charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>仓单详情</title>
		<link href="${skinPath}/css/mgr/memberadmin/module.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div class="main">
			<jsp:include page="/front/frame/current.jsp"></jsp:include>
			<div class="warning">
		  		<div class="title font_orange_14b">温馨提示：</div>
				<div class="content">　该仓单【仓单号：${entity.stockId}】为
	     				拆单仓单；拆单仓单分为已拆仓单、拆单申请中仓单和拆单失败仓单；<br/>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1、拆单仓单为已经拆分的仓单，已拆分仓单只作为一种追溯记录；<br/>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、拆单申请中仓单等待仓库进行拆单处理，拆单申请中仓单不能用于交易；<br/>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3、拆单失败仓单为申请拆单后仓库系统无法按照要求拆单而拒绝拆单申请的仓单；
				</div>
			</div>
			<div class="form margin_10b">
				<div class="column1">
					<h1>   仓单详细信息  </h1>
				</div>
				<table border="0" cellspacing="0" cellpadding="0" class="content">
					<tr>
						<th width="15%" align="center" valign="middle" scope="row">仓库原始凭证号：</th>
						<td width="18%" align="center" valign="middle">${entity.realStockCode }&nbsp;</td>
						<th width="15%" align="center" valign="middle" scope="row">仓库编号：</th>
                        <td width="" align="center" valign="middle">${entity.warehouseId}&nbsp;</td>
                        <th width="15%" align="center" valign="middle" scope="row">创建时间：</th>
                        <td width="" align="center" valign="middle"><fmt:formatDate value="${entity.createTime}" pattern="yyyy-MM-dd  HH:mm:ss"/>&nbsp;</td>
					</tr>
					<tr>
						<th width="15%" align="center" valign="middle" scope="row">品名：</th>
						<td width="18%" align="center" valign="middle">${entity.breed.breedName}&nbsp;</td>
						<th width="15%" align="center" valign="middle" scope="row">数量：</th>
                        <td width="" align="center" valign="middle"><fmt:formatNumber pattern="#0.00" value="${entity.quantity }"></fmt:formatNumber>(${entity.unit }) &nbsp;</td>
						<th width="15%" align="center" valign="middle" scope="row">最后变更时间：</th>
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
							<th align="center" valign="middle" scope="row">${property.propertyName}：</th>
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
					<!-- 已出库仓单展示提货密钥 -->
					<c:if test="${entity.stockStatus==2}">
					<tr>
						<th width="15%" align="center" valign="middle" scope="row">提货密钥：</th>
						<td align="left" colspan="5" valign="middle">${entity.key}&nbsp;</td>
					</tr>
					</c:if>
				</table>
			</div>	
			<div class="form margin_10b">
				
				<div class="page text_center">&nbsp;&nbsp;
					<label><input name="button3" type="button" onclick="javascript:listjsp()" class="btbg" id="button3" value="返回" /></label>
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
