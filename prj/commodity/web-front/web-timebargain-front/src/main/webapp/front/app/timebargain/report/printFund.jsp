<%@ page contentType="text/html;charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>资金结算表页面</title>
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
					在此展示您的资金结算信息。
				</div>
			</div>
			<div class="main_copy">
				<form id="frm"
					action="${frontPath}/app/timebargain/report/firmFundsQuery.action"
					method="post">
					<div class="sort">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td height="30" width="250">
									<label>
										查询日期：
										<input id="tradeDate"
											class="Wdate validate[required,custom[date],past[${today}]] datepicker"
											name="${GNNT_}b_date" value="${oldParams['b_date']}"
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
				</form>
				<div class="form margin_10b">
					<div class="column1">
						资金结算表：
					</div>
					<table border="0" cellspacing="0" cellpadding="0" class="content">
						<c:if test="${fn:length(fundList)>0}">
							<c:forEach items="${fundList}" var="fund">
								<tr>
									<th width="17%" scope="row">
										结算日期：
									</th>
									<td width="83%">
										<fmt:formatDate value="${fund.B_DATE}" pattern="yyyy-MM-dd" />
										&nbsp;
									</td>
								</tr>
								<tr>
									<th scope="row">
										交易商代码：
									</th>
									<td>
										<c:out value="${fund.FIRMID}" />
										&nbsp;
									</td>
								</tr>
								<tr>
									<th scope="row" class="font_orange_12">
										期初资金余额：
										<span class="font_orange_12">*</span>
									</th>
									<td>
										<fmt:formatNumber value="${fund.LASTBALANCE}"
											pattern="#,##0.00#" />
										&nbsp;
									</td>
								</tr>
								<c:if test="${fn:length(fundFieldList)>0}">
									<c:forEach items="${fundFieldList}" var="funFilds">
										<tr>
											<th scope="row">
												<c:choose>
													<c:when test="${funFilds.FIELDSIGN==1}">
														<c:out value="(+)${funFilds.NAME}：" />&nbsp;
												</c:when>
													<c:otherwise>
														<c:out value="(-)${funFilds.NAME}：" />&nbsp;
												</c:otherwise>
												</c:choose>
											</th>
											<td>
												<fmt:formatNumber value="${funFilds.VALUE}"
													pattern="#,##0.00#" />
												&nbsp;
											</td>
										</tr>
									</c:forEach>
								</c:if>
								<tr>
									<th scope="row">
										(+)其他系统其他项：
									</th>
									<td>
										<fmt:formatNumber value="${fund.VALUE}" pattern="#,##0.00#" />
										&nbsp;
									</td>
								</tr>
								<tr>
									<th scope="row" class="font_orange_12">
										期末资金余额：
										<span class="font_orange_12">*</span>
									</th>
									<td>
										<fmt:formatNumber value="${fund.TODAYBALANCE}"
											pattern="#,##0.00#" />
										&nbsp;
									</td>
								</tr>
								<tr>
									<th scope="row">
										(+)当日保证金：
									</th>
									<td>
										<fmt:formatNumber value="${fund.RUNTIMEMARGIN}"
											pattern="#,##0.00#" />
										&nbsp;
									</td>
								</tr>
								<tr>
									<th scope="row">
										(+)当日浮亏：
									</th>
									<td>
										<fmt:formatNumber value="${fund.RUNTIMEFL}"
											pattern="#,##0.00#" />
										&nbsp;
									</td>
								</tr>
								<tr>
									<th scope="row">
										(+)当日交收保证金：
									</th>
									<td>
										<fmt:formatNumber value="${fund.RUNTIMESETTLEMARGIN}"
											pattern="#,##0.00#" />
										&nbsp;
									</td>
								</tr>
								<tr>
									<th scope="row">
										(+)浮动盈亏：
									</th>
									<td>
										<fmt:formatNumber value="${fund.PL}" pattern="#,##0.00#" />
										&nbsp;
									</td>
								</tr>
								<!-- 初始化当日权益 -->
								<c:set var="benefit" value="0.00" />
								<c:set var="benefit"
									value="${benefit+fund.TODAYBALANCE+fund.RUNTIMEMARGIN+fund.RUNTIMESETTLEMARGIN+fund.RUNTIMEFL+fund.PL+fund.CLOSE_PL-fund.TRADEFEE}" />
								<tr>
									<th scope="row" class="font_orange_12">
										当日权益：&nbsp;
									</th>
									<td>
										<fmt:formatNumber value="${ benefit }" pattern="#,##0.00#" />
										&nbsp;
									</td>
								</tr>
								<tr>
									<th scope="row">
										上日保证金：&nbsp;
									</th>
									<td>
										<fmt:formatNumber value="${fund.CLEARMARGIN}"
											pattern="#,##0.00#" />
										&nbsp;
									</td>
								</tr>
								<tr>
									<th scope="row">
										上日浮亏：&nbsp;
									</th>
									<td>
										<fmt:formatNumber value="${fund.CLEARFL}" pattern="#,##0.00#" />
										&nbsp;
									</td>
								</tr>
								<tr>
									<th scope="row">
										上日交收保证金：&nbsp;
									</th>
									<td>
										<fmt:formatNumber value="${fund.CLEARSETTLEMARGIN}"
											pattern="#,##0.00#" />
										&nbsp;
									</td>
								</tr>
								<tr>
									<th scope="row">
										结算准备金最低限额：&nbsp;
									</th>
									<td>
										<fmt:formatNumber value="${fund.MINCLEARDEPOSIT}"
											pattern="#,##0.00#" />
										&nbsp;
									</td>
								</tr>
								<tr>
									<th scope="row">
										质押资金：&nbsp;
									</th>
									<td>
										<fmt:formatNumber value="${fund.MAXOVERDRAFT}"
											pattern="#,##0.00#" />
										&nbsp;
									</td>
								</tr>
								<c:if test="${fund.TODAYBALANCE<0}">
									<font color="red">提醒：交易商资金不足,请及时追加</font>
								</c:if>
							</c:forEach>
						</c:if>

					<c:if test="${fn:length(fundList)<=0}">
							<c:if test="${fn:length(query)>0}">
								<div class="column1">
									<c:out value="您查询的日期无资金记录，没有进行资金结算或者是非交易日" />&nbsp;
								</div>
								
							</c:if>	
					</c:if>
					</table>
					
				</div>
			</div>
		</div>
		<!-------------------------Body End------------------------->
	</body>
</html>