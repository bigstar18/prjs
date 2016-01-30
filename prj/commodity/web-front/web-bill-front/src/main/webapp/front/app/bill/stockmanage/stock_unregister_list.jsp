<%@ page contentType="text/html;charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>未注册仓单列表</title>
		<link href="${skinPath}/css/mgr/memberadmin/module.css"
			rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${publicPath}/js/jquery-1.6.2.min.js"></script>
		<script>
		jQuery(document).ready(function() {
			$("#view").click(function(){
				frm.target="";
				frm.action="${frontPath}/bill/unregister/list.action";
				frm.submit();
			});
		});
		</script>
	</head>
	<body onload="getFocusID('stockID_q');">
	<div class="main">
		<jsp:include page="/front/frame/current.jsp"></jsp:include>
		<div class="warning">
			<div class="title font_orange_14b">温馨提示 :</div>
			<div class="content">未注册仓单是指货物入库后，可进行货物调拨的所有权凭证。未注册仓单可用于办理出库、拆分仓单业务。也可以申请为注册仓单。</div>
		</div>
	</div>
		<div class="main_copy">
			<form id="frm" action="${frontPath}/bill/unregister/list.action" method="post">
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
									<td width="140" height="32" align="center" valign="bottom" class="c_titlebt_on">
										<a id="all" href="#">未注册仓单</a>
									</td>
									<td>
									</td>
									<td >
									</td>
									<td>&nbsp;</td>
								</tr>
							</table>
							<input id="status" name="${GNNT_}primary.status[=][int]" type="hidden" value="${oldParams['primary.bsFlag[=]']}"/>
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
								<td><div class="ordercol" id="lastTime">最后变更时间</div></td>
								<td><div class="ordercol" id="createTime">创建时间</div></td>
								<td>操作</td>
							</tr>
							<c:forEach var="stock" items="${pageInfo.result}">
							<tr>
								<td>
								<a href="${frontPath}/bill/unregister/detail.action?entity.stockId=${stock.stockId}">${stock.stockId}</a>
								</td>
								<td>${stock.breed.breedName}</td>
								<td>${stock.warehouseId}</td>
								<td><fmt:formatNumber pattern="#0.00" value="${stock.quantity }"></fmt:formatNumber></td>
								<td>${stock.unit}</td>
								<td><fmt:formatDate value='${stock.lastTime}' pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td><fmt:formatDate value='${stock.createTime}' pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td>
									<a href="#" id="registerStock" onclick="registerStock('${stock.stockId }')" >注册</a>
									&nbsp;
									<a href="#" id="dismantleStock" onclick="dismantleStock('${stock.stockId }')">拆单</a>
									&nbsp;
									<a href="#" id="exitStock" onclick="exitStock('${stock.stockId }')"><c:if test="${haveWarehouse eq 1}">出库</c:if><c:if test="${haveWarehouse eq 0}">出库申请</c:if></a>
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
			 <iframe style="display: none;" id="frame" name="frame"></iframe>
		</div>
</body>
</html>

<script>
//注册仓单
function registerStock(stockId){
	if(affirm("确认注册该仓单？")){
		frm.target="frame";
		frm.action = "${frontPath}/bill/unregister/registerStock.action?stockId=" + stockId;
		frm.submit();
	}
}
//出库
function exitStock(stockId){
	var haveWarehouse =  '${haveWarehouse}';
	if(haveWarehouse==''){
		alert("仓单交易核心未能正常启动：仓单出库操作暂时无法进行操作，请稍后重试！")
	}else{
		//if(haveWarehouse==1){
			 if(affirm()){
				var url = "${frontPath}/bill/unregister/forwardExitStock.action?entity.stockId=" + stockId;
			  	var result=showDialog(url,'',750,750);
			  	if(result>0){
			  		$("#view").click();
				}else{
					clearSubmitCount();
				}
			}
		<%--}else{
			if(confirm("确认出库该仓单？")){
				frm.action = "${frontPath}/bill/unregister/exitStock.action?stockId=" + stockId;
				frm.submit();
			}
		}--%>
	}
}
//拆单
function dismantleStock(stockId){
	frm.action = "${frontPath}/bill/unregister/dismantleStockToPage.action?entity.stockId=" + stockId;
	frm.submit();
}
function showMsgBoxCallbak(result){
	window.location="${frontPath}/bill/unregister/list.action?sortColumns=" + '${sortColumns}'; 
}
</script>
<%@include file="/front/public/jsp/commonmsg.jsp"%>