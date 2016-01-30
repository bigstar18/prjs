<%@ page contentType="text/html;charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>仓单列表</title>
		<link href="${skinPath}/css/mgr/memberadmin/module.css"
			rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${publicPath}/js/jquery-1.6.2.min.js"></script>
	</head>
	<body onload="getFocusID('stockID_q');">
	<div class="main">
		<jsp:include page="/front/frame/current.jsp"></jsp:include>
		<div class="warning">
			<div class="title font_orange_14b">温馨提示 :</div>
			<div class="content">此页面显示您的出库仓单信息。您的提货密钥作为提货时的身份验证信息之一，请注意保密。</div>
		</div>
		<div class="main_copy">
			<form id="frm" action="${frontPath}/bill/exit/stockBySeller.action" method="post">
			<div class="sort">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="30" width="312">
						<label>仓单号：
							<input type="text" id="stockID_q" maxLength="<fmt:message key="stockID_q" bundle="${proplength}" />" name="${GNNT_}primary.stockId[=]" value="${oldParams['primary.stockId[=]']}"  onkeyup ="this.value=this.value.replace(' ','')"/>
						</label>
					</td>
					<td height="30" width="312">
					</td>
					<td width="110" height="30" align="center" valign="middle">
						<a href="#" id="view"><img src="${skinPath }/image/memberadmin/searchbt1.gif" width="93" height="23" border="0" /></a>
					</td>
				</tr>
			</table>
		</div>
				<div class="content">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr valign="bottom">
						<td height="40">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="140" height="32" align="center" valign="bottom" class="c_titlebt">
										<a id="exited" href="#">出库仓单信息</a>
									</td>
									<c:if test="${haveWarehouse != 1}">
									<td width="140" height="32" align="center" valign="bottom" class="c_titlebt">
										<a id="apply" href="#">出库申请中仓单</a>
									</td>
									</c:if>
								     <td width="140" height="32" align="center" valign="bottom" class="c_titlebt">
										<a id="selled"  href="#">卖出仓单信息</a>
									 </td>
									<td>&nbsp;</td>
								</tr>
							</table>
							<input id="status" name="status" type="hidden" value="${stockStatus}"/>
						</td>
					</tr>
					<tr>
						<td>
					<div class="tb_bg">
						<table border="0" cellspacing="0" cellpadding="0" class="content">
							<tr class="font_b tr_bg">
								<td><div class="ordercol" id="stockId">仓单号</div></td>
								<td><div class="ordercol" id="breedName">品名</div></td>
								<td><div class="ordercol" id="warehouseId">仓库编号</div></td>
								<td><div class="ordercol" id="quantity">商品数量</div></td>
								<td>单位</td>
								<td><div class="ordercol" id="sellTime">卖出时间</div></td>
								<td><div class="ordercol" id="received">收货状态</div></td>
								<td><div class="ordercol" id="receivedDate">收货时间</div></td>
								<td><div class="ordercol">操作</div></td>
							
							</tr>
							<c:forEach var="sellstock" items="${pageInfo.result}">
							<tr>  
								<td>
									<a href="${frontPath}/bill/exit/detail.action?entity.stockId=${sellstock.stockId}&status=${stockStatus}">${sellstock.stockId}</a>
								</td>
								<td>${sellstock.breedName}</td>
								<td>${sellstock.warehouseId}</td>
								<td><fmt:formatNumber pattern="#0.00" value="${sellstock.quantity }"></fmt:formatNumber></td>
								<td>${sellstock.unit}</td>
								<td><fmt:formatDate value='${sellstock.sellTime}' pattern="yyyy-MM-dd" /></td>
								<td>
								<c:if test="${sellstock.received eq 1}">已收货</c:if>
								<c:if test="${sellstock.received ne 1}">未收货</c:if>
								</td>
								<td><fmt:formatDate value='${sellstock.receivedDate}' pattern="yyyy-MM-dd" /></td>
								<td>
								<c:if test="${sellstock.invoiceStatus gt 0}">
									<a href="#" onclick="showInvoice(${sellstock.stockId})">查看发票</a>
								</c:if>
								<c:if test="${sellstock.invoiceStatus eq 0}">--&nbsp;&nbsp;--</c:if>
							    </td>
							</tr>
							</c:forEach>
						</table>
					</div>
						</td>
				</tr>
			</table>
				<jsp:include page="/front/frame/pageinfo.jsp"></jsp:include>
				</div>
			</form>
		</div>	</div>
</body>
</html>
<script>
jQuery(document).ready(function() {
	//设置选中样式
	if(${stockStatus=='2'}){
		$("#exited").parent().attr("class","c_titlebt_on");
	}
	<c:if test="${haveWarehouse != 1}">
	else if(${stockStatus=='5'}){
		$("#apply").parent().attr("class","c_titlebt_on");
	}
	</c:if>
	if(${reciveid=='1'}){
		$("#selled").parent().attr("class","c_titlebt_on");
	}
	//查看按钮
	$("#view").click(function(){
		$("#frm").submit();
	});
	//查看已出库仓单信息
	$("#exited").click(function() {
		$("#status").attr("value","2");
		$("#frm").attr("action","${frontPath}/bill/exit/list.action");
		$("#view").click();
	});
	<c:if test="${haveWarehouse != 1}">
	//查看出库申请中的仓单信息
	$("#apply").click(function() {
		$("#status").attr("value","5");
		$("#frm").attr("action","${frontPath}/bill/exit/stockOutApplyList.action");
		$("#view").click();
	});
	</c:if>
	//卖出仓单查看
	$("#selled").click(function(){
		$("#frm").attr("action","${frontPath}/bill/exit/stockBySeller.action");
		$("#view").click();
	});
});

function showInvoice(stockId){
		var url="${basePath}/front/bill/exit/showInvoice.action?stockId="+stockId;
		if(showDialog(url, "", 500, 600)){
			frm.submit();
		}
}
</script>
<%@include file="/front/public/jsp/commonmsg.jsp"%>