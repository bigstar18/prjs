<%@ page contentType="text/html;charset=GBK"%>
<%@page import="gnnt.trade.bank.vo.CapitalValue"%>
<%@page import="gnnt.trade.bank.vo.BankValue"%>
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
							<input id="beginTime" name="beginTime" class="Wdate validate[custom[date],past[${today}],funcCall[checkBeginDate]] datepicker" name="${GNNT_}primary.createtime[>=][date]" value="${oldParams['primary.createtime[>=][date]']}" onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})"/>
						</label>
					</td>
					<td>
						<label>结束日期：
							<input id="endTime" name="endTime" class="Wdate validate[custom[date],past[${today}],funcCall[checkEndDate]] datepicker" name="${GNNT_}primary.createtime[<=][datetime]" value="${oldParams['primary.createtime[<=][datetime]']}" onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})"/>
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
									<td width="8%" align="center"><div class="ordercol" id="id">记录流水号</div></td>
									<td width="8%" align="center">市场流水号</td>
									<td width="8%" align="center">平台流水号</td>
									<td width="8%" align="center">转账银行</td>
									<td width="8%" align="center">银行代码</td>
									<td width="8%" align="center">转账类型</td>
									<td width="8%" align="center">交易系统</td>
									<td width="8%" align="center">平台号</td>
									<td width="8%" align="center">交易商代码</td>
									<td width="8%" align="center"><div class="ordercol" id="money">交易金额</div></td>
									<td width="5%" align="center">状态</td>
									<td width="8%" align="center"><div class="ordercol" id="createtime">日期</div></td>
									<td width="23%" align="center">备注</td>
								</tr>
								<c:forEach var="capitalinfo" items="${capitalList}">
								<tr>
									<td align="center">${capitalinfo.iD}</td>
									<td align="center">${capitalinfo.funID}</td>
									<td align="center">${capitalinfo.actionID}</td>
									<td align="center">${capitalinfo.bankID}</td>
									<td align="center">
									<c:forEach var="bankIDs" items="${vecBanks}">
								     <c:if test="${bankIDs.bankID eq capitalinfo.bankID}">${bankIDs.bankName}</c:if>
							        </c:forEach>
							        </td>
									<td align="center">${capitalInfoType[capitalinfo.type]}</td>
									<td align="center">
									<c:forEach items="${collectionSys}" var="systemMessage">
										<c:if test="${systemMessage.systemID eq capitalinfo.systemID}">
											<c:out value="${systemMessage.systemName}"/>
										</c:if>
 									</c:forEach>
									
<!--									<c:if test="${capitalinfo.systemID=='1001'}">大融合</c:if>
									<c:if test="${capitalinfo.systemID=='1001'}">报价</c:if>-->
									</td>
									<td align="center">${capitalinfo.firmID}</td>
									<td align="center">${capitalinfo.sysFirmID}</td>
									<td align="center"><fmt:formatNumber value="${capitalinfo.money}" pattern="#,##0.00" />&nbsp;</td>
									<td align="center">${capitalInfoStatus[capitalinfo.status]}</td>
									<td align="center"><fmt:formatDate value="${capitalinfo.createtime}" pattern="yyyy-MM-dd HH:mm:dd"/></td>
									<td align="center">
										${capitalinfo.note}
									</td>
									
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