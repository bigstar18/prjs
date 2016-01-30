<%@ page contentType="text/html;charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<base target="_self">
	<html xmlns="http://www.w3.org/1999/xhtml">
		<head>
			<title>未使用的仓单列表</title>
			<link href="${skinPath}/css/mgr/memberadmin/module.css"
				rel="stylesheet" type="text/css" />
			<script type="text/javascript"
				src="${publicPath}/js/jquery-1.6.2.min.js"></script>
		</head>
		<script type="text/javascript">
	
</script>
		<body>
			<div class="main_copy">
				<form id="frm"
					action="${frontPath}bill/stock/getNotUseStock.action"
					method="post">
					<div class="content">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<div class="tb_bg">
										<table border="0" cellspacing="0" cellpadding="0"
											class="content">
											<tr class="font_b tr_bg">
												<td width="14%">
													选择仓单
												</td>
												<td width="14%">
													仓单号
												</td>
												<td width="14%">
													仓库原始凭证号
												</td>
												<td width="14%">
													分类
												</td>
												<td width="14%">
													品名
												</td>
												<td width="14%">
													商品数量
												</td>
												<td width="14%">
													属性信息
												</td>
											</tr>
											<c:forEach var="stock" items="${pageInfo.result}">
												<tr>
													<td valign="top">
														<a href="#" onclick="closeDialog('${stock.stockId}');">选择</a>
													</td>
													<td valign="top">
														${stock.stockId}
													</td>
													<td valign="top">
														${stock.realStockCode}
													</td>
													<td valign="top">
														${stock.breed.category.categoryName}
													</td>
													<td valign="top">
														${stock.breed.breedName}
													</td>
													<td valign="top">
														<fmt:formatNumber value="${stock.quantity}"
															pattern="#,##0.00" />
														(${stock.unit})
													</td>
													<td class="maxsize" lang="20">
														<c:forEach var="property" items="${stock.goodsProperties}"
															varStatus="status">
															<c:if test="${status.count != 1}">、</c:if>[${property.propertyName}：${property.propertyValue}]</c:forEach>
														&nbsp;
													</td>
												</tr>
											</c:forEach>
										</table>
								</td>
							</tr>
						</table>
						<jsp:include page="/front/frame/pageinfo.jsp"></jsp:include>
					</div>
			</div>
			</form>
			</div>
		</body>
	</html>