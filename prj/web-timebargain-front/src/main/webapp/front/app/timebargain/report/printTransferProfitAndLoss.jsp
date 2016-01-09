<%@ page contentType="text/html;charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>转让盈亏明细表页面</title>
		<link href="${skinPath}/css/mgr/memberadmin/module.css"
			rel="stylesheet" type="text/css" />
		<link rel="stylesheet"
			href="${skinPath }/css/validationEngine.jquery.css" type="text/css" />
		<script type="text/javascript"
			src="${publicPath}/js/jquery-1.6.2.min.js"></script>
		<script type="text/javascript"
			src="${frontPath }/app/timebargain/js/jquery.validationEngine-zh_CN.js"></script>
		<script type="text/javascript"
			src="${publicPath }/js/jquery.validationEngine.js"></script>
		<script type="text/javascript"
			src="${publicPath}/js/calendar/WdatePicker.js"></script>
		<script type="text/javascript" src="${publicPath}/js/tool.js"></script>
		<script>
	jQuery(document).ready(function() {
		//设置表单验证
			$("#frm").validationEngine("attach");
			$("#view").click(function() {
				var tradeDate = jQuery('#tradeDate').val();
				if (tradeDate == '') {
					alert("查询日期不能为空");
					return;
				}
				if ($("#frm").validationEngine('validate')) {
					$("#frm").submit();
				}
			});
		});
</script>
	</head>
	<body>
		<!-------------------------Body Begin------------------------->
		<div class="main">
			<jsp:include page="/front/frame/current.jsp"></jsp:include>
			<div class="warning">
				<div class="title font_orange_14b">
					温馨提示 :
				</div>
				<div class="content">
					在此展示您的转让盈亏明细信息。
				</div>
			</div>
			<div class="main_copy">
				<form id="frm"
					action="${frontPath}/app/timebargain/report/transferProfitAndLossQuery.action"
					method="post">
					<div class="sort">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td height="30" width="250">
									<label>
										查询日期：
										<input id="d1"
											class="Wdate validate[required,custom[date],past[${today}]] datepicker"
											name="d1" value="<c:out value="${d1 }" />"
											onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" />
									</label>
								</td>
								<td width="110" height="30" align="center" valign="middle">
									<a href="#" id="view"><img
											src="${skinPath }/image/memberadmin/searchbt1.gif" width="93"
											height="23" border="0" /> </a>
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
												<td width="10%" align="center"><div class="ordercol" id="noticeId">客户代码</div></td>
												<td width="12%" align="center"><div class="ordercol" id="noticeId">商品代码</div></td>
												<td width="10%" align="center"><div class="ordercol" id="createTime">交易类型</div></td>
												<td width="10%" align="center"><div class="ordercol" id="noticeId">数量(批)</div></td>
												<td width="10%" align="center"><div class="ordercol" id="noticeId">转让价格</div></td>
												<td width="12%" align="center"><div class="ordercol" id="noticeId">订货价格</div></td>
												<td width="12%" align="center"><div class="ordercol" id="noticeId">订货日期</div></td>												
												<td width="14%" align="center"><div class="ordercol" id="noticeId">交易盈亏</div></td>
												
											</tr>
											<c:set var="close_PLSum" value="0.00" />
											<c:set var="listCount" value="0" />
											<c:forEach items="${transferProfitAndLossList}" var="close_PL">
											      <c:set var="listCount" value="1" />
										          <tr>
										            <td class="main_tbw02" width="10%"><c:out value="${close_PL.CUSTOMERID}"/></td>
										            <td class="main_tbw01" width="12%"><c:out value="${close_PL.COMMODITYID}"/></td>
										            <td class="main_tbw01" width="10%"><c:out value="${close_PL.BSORDERTYPE}"/></td>
										            <td class="main_tbw01" width="10%">&nbsp;<c:out value="${close_PL.QUANTITY}"/></td>
										            <td class="main_tbw01" width="10%"><fmt:formatNumber value="${close_PL.PRICE}" pattern="#,##0.00"/></td>
										            <td class="main_tbw01" width="12%"><fmt:formatNumber value="${close_PL.HOLDPRICE}" pattern="#,##0.00"/></td>
										            <td class="main_tbw01" width="12%"><fmt:formatDate value="${close_PL.HOLDTIME}" pattern="yyyy-MM-dd" /></td>
										            <td class="main_tbw01" width="14%"><fmt:formatNumber value="${close_PL.CLOSE_PL}" pattern="#,##0.00"/></td>
										          </tr>
										            <c:set var="holdQtySum" value="${holdQtySum+close_PL.QUANTITY}"/>	
		                                            <c:set var="close_PLSum" value="${close_PLSum+close_PL.CLOSE_PL}" />
												</c:forEach>
												<c:if test="${listCount == 1}">
												  <tr>
										            <td class="main_tbw02" width="10%">合计</td>
										            <td class="main_tbw01" width="12%">&nbsp;</td>
										            <td class="main_tbw01" width="10%">&nbsp;</td>
										            <td class="main_tbw01" width="10%">&nbsp;<c:out value="${holdQtySum}"/></td>
										            <td class="main_tbw01" width="10%">&nbsp;</td>
										            <td class="main_tbw01" width="12%">&nbsp;</td>
										            <td class="main_tbw01" width="12%">&nbsp;</td>
										            <td class="main_tbw01" width="14%"><fmt:formatNumber value="${close_PLSum}" pattern="#,##0.00"/></td>
										          </tr>
										     </c:if>
										</table>
									</div>
								</td>
							</tr>
							<tr>
								<td>
								  <c:if test="${pageInfo != null}">
									<jsp:include page="/front/frame/pageinfo.jsp"></jsp:include>
								  </c:if>
								</td>
							</tr>
						</table>
					</div>
		       </form>
			</div>
		</div>
		<!-------------------------Body End------------------------->
	</body>
</html>