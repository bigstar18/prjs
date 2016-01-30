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
			<form id="frm" action="${frontPath}/bill/exit/list.action" method="post">
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
								<td><div class="ordercol" id="breed.breedName">品名</div></td>
								<td><div class="ordercol" id="warehouseId">仓库编号</div></td>
								<td><div class="ordercol" id="quantity">商品数量</div></td>
								<td>单位</td>
								<c:if test="${stockStatus eq 2}">
								<td><div class="ordercol" id="company">物流公司</div></td>
								<td><div class="ordercol" id="logisticsOrder">订单号</div></td>
								<td><div class="ordercol" id="receivedDate">收货时间</div></td>
								</c:if>

								<td><div class="ordercol" id="lastTime">最后变更时间</div></td>
								<td><div class="ordercol" id="createTime">创建时间</div></td>
								<!-- 
								<td><div class="ordercol" id="key">提货密钥</div></td>
								 -->
								<c:if test="${stockStatus eq 5}">
									<td>操作</td>
								</c:if>
								<c:if test="${stockStatus eq 2}">
									<td>操作</td>
								</c:if>
							</tr>
							<c:forEach var="stock" items="${pageInfo.result}">
							<tr>
								<td>
									<a href="${frontPath}/bill/exit/detail.action?entity.stockId=${stock.stockId}&status=${stockStatus}">${stock.stockId}</a>
								</td>
								<td>
								<c:if test="${stockStatus eq 2}">
								   ${stock.breedName}
								</c:if>
								<c:if test="${stockStatus ne 2}">
								   ${stock.breed.breedName}
								</c:if>
								</td>
								<td>${stock.warehouseId}</td>
								<td><fmt:formatNumber pattern="#0.00" value="${stock.quantity }"></fmt:formatNumber></td>
								<td>${stock.unit}</td>
								<c:if test="${stockStatus eq 2}">
									<td>${stock.company}</td>
									<td>${stock.logisticsOrder}</td>
									<td><fmt:formatDate value='${stock.receivedDate}' pattern="yyyy-MM-dd HH:mm:ss" /></td>
								</c:if>
								<td><fmt:formatDate value='${stock.lastTime}' pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td><fmt:formatDate value='${stock.createTime}' pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<c:if test="${stockStatus eq 5}">
									<td><a href="#" onclick="stockOutCancel(${stock.stockId})">撤销申请</a></td>
								</c:if>
								<c:if test="${stockStatus eq 2}">
							    	<td>
								 	<c:if test="${stock.isReceived eq 1}">
									    已收货
									</c:if>
									<c:if test="${stock.isReceived ne 1}">
										<a href="#" onclick="stockReceived(${stock.stockId},${stock.invoiceStatus})">确认收货</a>
									</c:if>
									</td>
								</c:if>
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

function stockOutCancel(stockId){
	var result = confirm("确认撤销该仓单的出库申请吗？");
	if(result){
		$("#frm").attr("action","${frontPath}/bill/exit/stockOutCancel.action?stockId="+stockId);
		$("#frm").submit();
	}
}
function stockReceived(stockId,invoiceStatus){
    var  str="你确认收到了足够的货物";
    if(invoiceStatus!=0){
    	 str+="与发票";
    }
    if(confirm(str+"?"))
    {
    	$("#frm").attr("action","${frontPath}/bill/exit/stockConfirm.action?stockId="+stockId);
    	$("#frm").submit();
    }
   //alert(str+"?");
}
</script>
<%@include file="/front/public/jsp/commonmsg.jsp"%>