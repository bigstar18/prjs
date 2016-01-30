<%@ page contentType="text/html;charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>拆仓单列表</title>
		<link href="${skinPath}/css/mgr/memberadmin/module.css"
			rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${publicPath}/js/jquery-1.6.2.min.js"></script>
	</head>
	<body onload="getFocusID('stockID_q');">
	<div class="main">
		<jsp:include page="/front/frame/current.jsp"></jsp:include>
		<div class="warning">
			<div class="title font_orange_14b">温馨提示 :</div>
			<div class="content">1.当卖方的货物仓单数量与合同数量不匹配时，可以提交未注册仓单的拆分申请，由交易管理方审核后生成数量合适的仓单。卖方可再针对某笔合同转入合适的仓单。
			<br />2.列表中展示与您相关的拆仓单业务，包括拆单成功、拆单失败以及申请中的拆单。</div>
		</div>
		<div class="main_copy">
			<form id="frm" action="${frontPath}/bill/split/list.action" method="post">
			<div class="sort">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="30" width="312">
						<label>仓单号：
							<input type="text" id="stockID_q" maxLength="<fmt:message key="stockID_q" bundle="${proplength}" />" name="${GNNT_}primary.stock.stockId[=]" value="${oldParams['primary.stock.stockId[=]']}"  onkeyup ="this.value=this.value.replace(' ','')"/>
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
										<a id="splited" href="#">已拆仓单</a>
									</td>
									<td width="140" height="32" align="center" valign="bottom" class="c_titlebt">
										<a id="spliting" href="#">拆单申请中</a>
									</td>
									<td width="140" height="32" align="center" valign="bottom" class="c_titlebt">
										<a id="failed" href="#">拆单失败</a>
									</td>
									<td width="140" height="32" align="center" valign="bottom">
										
									</td>
									<td>&nbsp;</td>
								</tr>
							</table>
							<input id="status" name="status" type="hidden" value="${status}"/>
						</td>
					</tr>
					<tr>
						<td>
					<div class="tb_bg">
						<table border="0" cellspacing="0" cellpadding="0" class="content">
							<tr class="font_b tr_bg">
								<td><div class="ordercol" id="stock.stockId">仓单号</div></td>
								<td><div class="ordercol" id="dismantleId">拆单编号</div></td>
								<td><div class="ordercol" id="newStockId">新仓单号</div></td>
								<td><div class="ordercol" id="realStockCode">仓库原始凭证号</div></td>
								<td><div class="ordercol" id="amount">商品数量</div></td>
								<td><div class="ordercol" id="processTime">处理时间</div></td>
							</tr>
							<c:forEach var="dismantle" items="${pageInfo.result}">
							<tr>
								<td>
									<a href="${frontPath}/bill/split/detail.action?entity.stockId=${dismantle.stock.stockId}">${dismantle.stock.stockId}</a>
								</td>
								<td>${dismantle.dismantleId }</td>
								<td>${empty dismantle.newStockId ? '--' : dismantle.newStockId}</td>
								<td>${empty dismantle.realStockCode ? '--' : dismantle.realStockCode}</td>
								<td><fmt:formatNumber pattern="#0.00" value="${dismantle.amount}"></fmt:formatNumber></td>
								
								<td>
									<c:if test="${not empty dismantle.processTime}">
										<fmt:formatDate value='${dismantle.processTime}' pattern='yyyy-MM-dd HH:mm:ss' />
									</c:if>
									<c:if test="${empty dismantle.processTime}">
										--
									</c:if>
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
	if(${status=='1'}){
		$("#splited").parent().attr("class","c_titlebt_on");
	} else if(${status=='0'}){
		$("#spliting").parent().attr("class","c_titlebt_on");
	}else{
		$("#failed").parent().attr("class","c_titlebt_on");
	}
	//查看按钮
	$("#view").click(function(){
		$("#frm").submit();
	});
	//查看已拆仓单信息
	$("#splited").click(function() {
		$("#status").attr("value","1");
		$("#view").click();
	});
	//查看拆单中仓单信息
	$("#spliting").click(function() {
		$("#status").attr("value","0");
		$("#view").click();
	});
	//查看拆单中仓单信息
	$("#failed").click(function() {
		$("#status").attr("value","2");
		$("#view").click();
	});
});
</script>
<%@include file="/front/public/jsp/commonmsg.jsp"%>