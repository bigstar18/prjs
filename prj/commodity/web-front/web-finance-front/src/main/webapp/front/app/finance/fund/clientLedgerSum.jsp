<%@ page contentType="text/html;charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>交易商总账合计页面</title>
<link href="${skinPath}/css/mgr/memberadmin/module.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${publicPath}/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="${publicPath}/js/calendar/WdatePicker.js"></script>
<script type="text/javascript" src="${publicPath}/js/tool.js"></script>
<style type="text/css">
tr td:nth-child(odd) {
	text-align: right;
}

tr td:nth-child(even) {
	text-align: center;
}
</style>
<script type="text/javascript">
	$ (function ()
    {
	    $ ("#view").click (function ()
	    {
		    $ ("#frm").submit ();
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
			<div class="content">在此展示您的总账单合计信息。</div>
		</div>
		<div class="main_copy">
			<form id="frm" action="${frontPath}/app/finance/ledgerSum/clientLedgerSum.action" method="post">
				<div class="sort">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="30" width="250"><label>开始日期： <input id="beginDate" class="Wdate datepicker" name="beginDate" value="${beginDate}" onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen',maxDate:'#F{$dp.$D(\'endDate\')||\'%y-%M-%d\'}',isShowClear:false,readOnly:true})" />
							</label></td>
							<td><label>结束日期： <input id="endDate" class="Wdate datepicker" name="endDate" value="${endDate}" onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen',maxDate:'%y-%M-%d',minDate:'#F{$dp.$D(\'beginDate\')}',isShowClear:false,readOnly:true})" />
							</label></td>
							<td><label>系统名称： <select type="text" class="input_text" id="moduleId" name="${GNNT_}moduleId">
										<option value="">全部</option>
										<c:forEach var="list" items="${moduleList}">
											<c:choose>
												<c:when test="${oldParams['moduleId'] == list.moduleId }">
													<option value="${list.moduleId}" selected="selected">${list.name }</option>
												</c:when>
												<c:otherwise>
													<option value="${list.moduleId}">${list.name }</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
								</select>
							</label></td>
							<td width="110" height="30" align="center" valign="middle"><a href="#" id="view"><img src="${skinPath }/image/memberadmin/searchbt1.gif" width="93" height="23" border="0" /> </a></td>
						</tr>
					</table>
				</div>
				<div class="content">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<div class="tb_bg">
									<table width="100%" border="0" cellspacing="0" cellpadding="0" class="content">
										<tr class="font_b">
											<td width="20%"><div class="ordercol">期初余额：</div></td>
											<td width="20%"><fmt:formatNumber value="${listValue['LASTBALANCE'] }" pattern="#,##0.00" /></td>
											<td width="20%"><div class="ordercol">期末余额：</div></td>
											<td width="20%"><fmt:formatNumber value="${listValue['TODAYBALANCE']}" pattern="#,##0.00" /></td>
										</tr>
										<c:if test="${oldParams['moduleId']==null||oldParams['moduleId']==''||oldParams['moduleId']=='15'}">
											<tr class="font_b">
												<td><div class="ordercol">期初担保金：</div></td>
												<td><fmt:formatNumber value="${listValue['LASTWARRANTY']}" pattern="#,##0.00" /></td>
												<td><div class="ordercol">期末担保金：</div></td>
												<td><fmt:formatNumber value="${listValue['TODAYWARRANTY']}" pattern="#,##0.00" /></td>
											</tr>
										</c:if>
										<c:forEach items="${fieldList}" var="field" step="2" varStatus="status">
											<tr class="font_b">
												<c:set var="index" value="${status.index}"></c:set>
												<c:set var="code" value="${fieldList[index].code}"></c:set>
												<c:set var="code2" value="${fieldList[index+1].code}"></c:set>
												<td width="20%">
													<div class="ordercol">
														<c:if test="${field.fieldSign==-1}">
													(-) ${fieldList[index].name }：
												</c:if>
														<c:if test="${field.fieldSign==1}">
													(+) ${fieldList[index].name }：
												</c:if>
													</div>
											</td>
												<td><fmt:formatNumber value="${listValue[code]}" pattern="#,##0.00" /></td>
												<td>
													<div class="ordercol">
														<c:if test="${fieldList[index+1].fieldSign == -1}">
													(-) ${fieldList[index+1].name }：
												</c:if>
														<c:if test="${fieldList[index+1].fieldSign == 1}">
													(+) ${fieldList[index+1].name }：
												</c:if>
													</div>
											</td>
												<td width="20%"><fmt:formatNumber value="${listValue[code2]}" pattern="#,##0.00" /></td>
											</tr>
										</c:forEach>
										<c:if test="${oldParams['moduleId']!=null&&oldParams['moduleId']!=''}">
											<tr class="font_b">
												<td><div class="ordercol">(+) 其他交易系统：</div></td>
												<td><fmt:formatNumber value="${listValue['OTHER']}" pattern="#,##0.00" /></td>
											</tr>
										</c:if>
									</table>
								</div>
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