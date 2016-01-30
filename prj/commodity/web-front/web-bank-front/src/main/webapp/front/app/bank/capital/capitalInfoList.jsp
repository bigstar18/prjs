<%@ page contentType="text/html;charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>资金流水列表</title>
		<link href="${skinPath}/css/mgr/memberadmin/module.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationEngine.jquery.css" type="text/css" />
		<script type="text/javascript" src="${publicPath}/js/jquery-1.6.2.min.js"></script>
		<script type="text/javascript" src="${frontPath }/app/bank/js/jquery.validationEngine-zh_CN.js"></script>
		<script type="text/javascript" src="${publicPath }/js/jquery.validationEngine.js"></script>
		<script type="text/javascript" src="${publicPath}/js/calendar/WdatePicker.js"></script>
		<script type="text/javascript" src="${publicPath}/js/tool.js"></script>
		<script>
			jQuery(document).ready(function(){
				//设置表单验证
				$("#frm").validationEngine("attach");
				$("#view").click(function(){
					if ($("#frm").validationEngine('validate')) {
						frm.submit();
					}
				});
			});

			function checkBeginDate(){
				var beginTime=document.getElementById("beginTime").value;
				var endTime=document.getElementById("endTime").value;
				if(!Util.CompareDate(beginTime,endTime)){
						return "*开始日期不能大于结束日期";
				}else{
					jQuery('#endTime').validationEngine('hide')
				}
			}
			
			function checkEndDate(){
				var beginTime=document.getElementById("beginTime").value;
				var endTime=document.getElementById("endTime").value;
				if(!Util.CompareDate(beginTime,endTime)){
						return "*结束日期不能小于开始日期";
				}else{
					jQuery('#beginTime').validationEngine('hide')
				}
			}
		</script>
	</head>
	<body>
<!-------------------------Body Begin------------------------->
<div class="main">
	<jsp:include page="/front/frame/current.jsp"></jsp:include>
	<div class="warning">
		<div class="title font_orange_14b">温馨提示 :</div>
		<div class="content">在此展示所有您曾经的转账流水信息。</div>
	</div>
<div class="main_copy">
	<form id="frm" action="${basePath}/bank/capital/getCapitalInfoList.action" method="post">
		<div class="sort">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="30" width="250">
						<label>开始日期：
							<input id="beginTime" class="Wdate validate[custom[date],past[${today}],funcCall[checkBeginDate]] datepicker" name="${GNNT_}primary.createtime[>=][date]" value="${oldParams['primary.createtime[>=][date]']}" onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})"/>
						</label>
					</td>
					<td>
						<label>结束日期：
							<input id="endTime"  class="Wdate validate[custom[date],past[${today}],funcCall[checkEndDate]] datepicker" name="${GNNT_}primary.createtime[<=][datetime]" value="${oldParams['primary.createtime[<=][datetime]']}" onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})"/>
						</label>
					</td>
					<td>
						<label>转账银行：
							<select id="bankID" name="${GNNT_}primary.bank.bankID[=]" style="width: 127px;">
								<option value="">请选择</option>
								<c:forEach var="firmIDAndAccount" items="${firmIDAndAccountList.result}">
								<option value="${firmIDAndAccount.bank.bankID}" <c:if test="${oldParams['primary.bank.bankID[=]'] eq firmIDAndAccount.bank.bankID}">selected="selected"</c:if>>${firmIDAndAccount.bank.bankName}</option>
								</c:forEach>
							</select>
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
							<table width="100%" border="0" cellspacing="0" cellpadding="0" class="content">
								<tr class="font_b tr_bg">
									<td width="10%" align="center"><div class="ordercol" id="id">记录流水号</div></td>
									<td width="10%" align="center"><div class="ordercol" id="actionID">市场流水号</div></td>
									<td width="10%" align="center">转账银行</td>
									<td width="10%" align="center">转账类型</td>
									<td width="20%" align="center"><div class="ordercol" id="money">交易金额</div></td>
									<td width="10%" align="center">状态</td>
									<td width="10%" align="center">备注</td>
									<td width="20%" align="center"><div class="ordercol" id="createtime">日期</div></td>
								</tr>
								<c:forEach var="capitalinfo" items="${pageInfo.result}">
								<tr>
									<td align="right">${capitalinfo.id}</td>
									<td align="right">${capitalinfo.actionID}</td>
									<td align="center">${capitalinfo.bank.bankName}</td>
									<td align="center">${capitalInfoType[capitalinfo.type]}</td>
									<td align="right"><fmt:formatNumber value="${capitalinfo.money}" pattern="#,##0.00" />&nbsp;</td>
									<td align="center">${capitalInfoStatus[capitalinfo.status]}</td>
									<td align="center">
										<c:if test="${fn:indexOf(capitalinfo.note,'market_in')>=0}">市场入金</c:if>
										<c:if test="${fn:indexOf(capitalinfo.note,'market_out')>=0}">市场出金</c:if>
										<c:if test="${fn:indexOf(capitalinfo.note,'bank_out')>=0}">银行出金</c:if>
										<c:if test="${!(fn:indexOf(capitalinfo.note,'market_in')>=0 or fn:indexOf(capitalinfo.note,'market_out')>=0 or fn:indexOf(capitalinfo.note,'bank_out')>=0)}">银行入金</c:if>
									</td>
									<td align="center"><fmt:formatDate value="${capitalinfo.createtime}" pattern="yyyy-MM-dd HH:mm:dd"/></td>
								</tr>
								</c:forEach>
							</table>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<jsp:include page="/front/frame/pageinfo.jsp"></jsp:include>
					</td>
				</tr>
			</table>
		</div>
	</form>
</div></div>
<!-------------------------Body End------------------------->
	</body>
</html>