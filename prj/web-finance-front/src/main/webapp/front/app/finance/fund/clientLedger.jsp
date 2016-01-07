<%@ page contentType="text/html;charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>交易商总账单</title>
<link href="${skinPath}/css/mgr/memberadmin/module.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${skinPath }/css/validationEngine.jquery.css" type="text/css" />
<script type="text/javascript" src="${publicPath}/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="${publicPath }/js/jquery.validationEngine.js"></script>
<script type="text/javascript" src="${publicPath}/js/calendar/WdatePicker.js"></script>
<script type="text/javascript" src="${publicPath}/js/tool.js"></script>
<script type="text/javascript">
	$ (function ()
    {
	    $ ("#view").click (function ()
	    {
		    frm.submit ();
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
			<div class="content">在此展示交易商总账单信息。</div>
		</div>
		<div class="main_copy">
			<form id="frm" action="${basePath}/front/app/finance/ledger/clientLedger.action" method="post">
				<div class="sort">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td align="left">&nbsp;&nbsp;&nbsp;开始日期:&nbsp; <label> <input type="text" style="width: 120px" id="beginDate" class="wdate" maxlength="10" readonly="readonly" name="beginDate" onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen',maxDate:'#F{$dp.$D(\'endDate\')||\'%y-%M-%d\'}'})" />
							</label>
						</td>
							<td align="left">&nbsp;&nbsp;&nbsp;结束日期:&nbsp; <label> <input type="text" style="width: 120px" id="endDate" class="wdate" maxlength="10" readonly="readonly" name="endDate" onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen',maxDate:'%y-%M-%d',minDate:'#F{$dp.$D(\'beginDate\')}'})" />
							</label> <script>
								document.getElementById ("beginDate").value = "${beginDate}";
                                document.getElementById ("endDate").value = "${endDate}";
							</script>
						</td>
							<td align="left">&nbsp;&nbsp;系统名称:&nbsp; <label> <select class="input_text" id="moduleId" name="moduleId">
										<option value="">全部</option>
										<c:forEach var="list" items="${moduleList}">
											<option value="${list.moduleId}">${list.name }</option>
										</c:forEach>
								</select>
							</label> <script>
								document.getElementById ("moduleId").value = "${moduleId}";
							</script>
						</td>
							<td width="110" height="30"><a href="#" id="view"><img src="${skinPath }/image/memberadmin/searchbt1.gif" width="93" height="23" border="0" /> </a></td>
						</tr>
					</table>
				</div>
				<div class="content" style="overflow-x: auto; overflow-y: hidden">
					<table width="260%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<div class="tb_bg">
									<table width="100%" border="0" cellspacing="0" cellpadding="0" class="content" summary="进行中的交易">
										<tr class="font_b tr_bg">
											<td align="center">结算日期</td>
											<td align="center">交易商代码</td>
											<td align="center">期初余额</td>
											<c:forEach items="${fieldList}" var="lf">
												<c:if test="${lf.fieldSign==-1}">
													<c:set var="name" value="-${lf.name}"></c:set>
												</c:if>
												<c:if test="${lf.fieldSign==1}">
													<c:set var="name" value="+${lf.name}"></c:set>
												</c:if>
												<td align="center">${name}</td>
											</c:forEach>
											<td align="center">期末余额</td>
											<td align="center">期初担保金</td>
											<td align="center">期末担保金</td>
										</tr>
										<c:forEach var="firmBalance" items="${pageInfo.result}">
											<tr>
												<td align="center"><fmt:formatDate value="${firmBalance.b_Date}" pattern="yyyy-MM-dd " /></td>
												<td align="center">${firmBalance.firmId}</td>
												<td align="center"><fmt:formatNumber value="${firmBalance.lastBalance}" pattern="#,##0.00" /></td>
												<c:set var="moduleMoney" value="0"></c:set>
												<c:forEach items="${fieldList}" var="lf">
													<c:set var="flag" value="-1"></c:set>
													<c:if test="${lf.fieldSign==-1}">
														<c:set var="name" value="-${lf.name}"></c:set>
													</c:if>
													<c:if test="${lf.fieldSign==1}">
														<c:set var="name" value="+${lf.name}"></c:set>
													</c:if>
													<c:forEach items="${firmBalance.clientLedger}" var="cl">
														<c:choose>
															<c:when test="${lf.code == cl.code }">
																<td align="center"><fmt:formatNumber value="${cl.value}" pattern="#,##0.00" /></td>
																<c:set var="flag" value="0"></c:set>
																<c:set var="moduleMoney" value="${cl.value*lf.fieldSign+moduleMoney}"></c:set>
															</c:when>
														</c:choose>
													</c:forEach>
													<c:if test="${flag==-1}">
														<td><fmt:formatNumber value="${0}" pattern="#,##0.00" /></td>
													</c:if>
												</c:forEach>
												<td align="center"><fmt:formatNumber value="${firmBalance.todayBalance}" pattern="#,##0.00" /></td>
												<td align="center"><fmt:formatNumber value="${firmBalance.lastWarranty}" pattern="#,##0.00" /></td>
												<td align="center"><fmt:formatNumber value="${firmBalance.todayWarranty}" pattern="#,##0.00" /></td>
												<c:if test="${oldParams['moduleId']!=null&&oldParams['moduleId']!=''}">
													<tr class="font_b">
														<td><div class="ordercol">(+) 其他交易系统：</div></td>
														<td><fmt:formatNumber value="${listValue['OTHER']}" pattern="#,##0.00" /></td>
													</tr>
												</c:if>
											</tr>
										</c:forEach>
									</table>
								</div>
						</td>
						</tr>
					</table>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="100%"><jsp:include page="/front/app/finance/fund/pageinfo.jsp"></jsp:include></td>
						</tr>
					</table>
				</div>
			</form>
		</div>
	</div>
	<!-------------------------Body End------------------------->
</body>
</html>