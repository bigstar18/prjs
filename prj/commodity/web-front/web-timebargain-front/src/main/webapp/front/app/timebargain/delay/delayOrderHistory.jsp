<%@ page contentType="text/html;charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>委托历史</title>
		<link href="${skinPath}/css/mgr/memberadmin/module.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationEngine.jquery.css" type="text/css" />
		<script type="text/javascript" src="${publicPath}/js/jquery-1.6.2.min.js"></script>
		<script type="text/javascript" src="${publicPath }/js/jquery.validationEngine.js"></script>
		<script type="text/javascript" src="${publicPath}/js/calendar/WdatePicker.js"></script>
		<script type="text/javascript" src="${publicPath}/js/tool.js"></script>
		<script>
			jQuery(document).ready(function(){
				$("#view").click(function(){
					frm.submit();
				});
			});

		</script>
	</head>
	<body>
<!-------------------------Body Begin------------------------->
<div class="main">
	<jsp:include page="/front/frame/current.jsp"></jsp:include>
	<div class="warning">
		<div class="title font_orange_14b">温馨提示 :</div>
		<div class="content">在此展示所有委托历史信息。</div>
	</div>
<div class="main_copy">
	<form id="frm" action="${frontPath}/app/timebargain/delay/delayOrderHistory.action" method="post">
		<div class="sort">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="30" width="250">
						<label>开始日期：
							<input id="beginTime" class="Wdate datepicker" readonly="readonly" name="${GNNT_}primary.orderTime[>=][date]" value="${oldParams['primary.orderTime[>=][date]']}" onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen',maxDate:'#F{$dp.$D(\'endTime\')||\'%y-%M-%d\'}'})"/>
						</label>
					</td>
					<td>
						<label>结束日期：
							<input id="endTime"  class="Wdate datepicker" readonly="readonly" name="${GNNT_}primary.orderTime[<=][datetime]" value="${oldParams['primary.orderTime[<=][datetime]']}" onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen',maxDate:'%y-%M-%d',minDate:'#F{$dp.$D(\'beginTime\')}'})"/>
						</label>
					</td>
					<td width="110" height="30" align="center" valign="middle">
						<a href="#" id="view"><img src="${skinPath }/image/memberadmin/searchbt1.gif" width="93" height="23" border="0" /> </a>
					</td>
				</tr>
			</table>
		</div>
		<div class="content">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<div class="tb_bg">
							<table width="100%" border="0" cellspacing="0" cellpadding="0" class="content" summary="进行中的交易">
								<tr class="font_b tr_bg">
									<td width="5%" align="center">委托单号</td>
									<td width="7%" align="center">商品代码</td>
									<td width="7%" align="center">交易商代码</td>
									<td width="7%" align="center">交易员代码</td>
									<td width="4%" align="center">买/卖</td>
									<td width="5%" align="center">类型</td>
									<td width="6%" align="center">状态</td>
									<td width="6%" align="center">撤单类型</td>
									<td width="5%" align="center">委托数量</td>
									<td width="5%" align="center">委托价格</td>
									<td width="5%" align="center">已成交数量</td>
									<td width="7%" align="center">冻结资金</td>
									<td width="7%" align="center">解冻资金</td>
									<td width="8%" align="center">撤单时间</td>
									<td width="8%" align="center">结算日期</td>
									<td width="8%" align="center"><div class="ordercol" id="orderTime">委托时间</div></td>
								</tr>
								<c:forEach var="delayOrder" items="${pageInfo.result}">
								<tr>
									<td class="maxsize" lang="25" align="center">${delayOrder.orderNO}</td>
									<td class="maxsize" lang="40" align="center">${delayOrder.commodityID}</td>
									<td align="center">${delayOrder.firmID}</td>
									<td align="center">${delayOrder.traderID}</td>
									<c:forEach items = "${buySellFlag }" var = "bsf">
										<c:if test = "${bsf.key == delayOrder.bsFlag }"><td align="center">${bsf.value }</td></c:if>
									</c:forEach>
									<c:forEach items = "${delayOrderTypes}" var = "dot">
										<c:if test = "${dot.key == delayOrder.delayOrderType }"><td align="center">${dot.value }</td></c:if>
									</c:forEach>
									<c:forEach items = "${status}" var = "s">
										<c:if test = "${s.key == delayOrder.status }"><td align="center">${s.value }</td></c:if>
									</c:forEach>
									<c:forEach items = "${withdrawType}" var = "wt">
										<c:if test = "${wt.key == delayOrder.withdrawType }"><td align="center">${wt.value }</td></c:if>
									</c:forEach>
									<c:if test = "${null == delayOrder.withdrawType}"><td align="center">-</td></c:if>
									<td align="center">${delayOrder.quantity}</td>
									<td align="center">${delayOrder.price}</td>
									<td align="center">${delayOrder.tradeQty}</td>
									<td align="center">${delayOrder.frozenFunds}</td>
									<td align="center">${delayOrder.unfrozenFunds}</td>
									<td align="center"><fmt:formatDate value="${delayOrder.withdrawTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
									<td align="center"><fmt:formatDate value="${delayOrder.clearDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
									<td align="center"><fmt:formatDate value="${delayOrder.orderTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
								</tr>
								</c:forEach>
							</table>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<jsp:include page="/front/app/timebargain/delay/pageinfo.jsp"></jsp:include>
					</td>
				</tr>
			</table>
		</div>
	</form>
</div></div>
<!-------------------------Body End------------------------->
	</body>
</html>