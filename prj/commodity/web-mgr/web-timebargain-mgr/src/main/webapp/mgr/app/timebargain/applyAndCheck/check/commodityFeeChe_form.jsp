<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%@ page import="gnnt.MEBS.timebargain.mgr.model.apply.CommodityFeeApply" %>
<html>
	<head>
	    <base target="_self" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>	
		<title>
		商品手续费信息
		</title>
		<script type="text/javascript"> 
function window_onload()
{
   /*
    if (commodityFeeForm.status.value == "1") {
    	setReadWrite(commodityFeeForm.note);
    }else {
    	setReadOnly(commodityFeeForm.note);
    }*/
}
//query_onclick

function cancel_onclick()
{
   window.close();
}

function relase()
{
	    commodityFeeForm.query.disabled = false;
	  	commodityFeeForm.query1.disabled = false;
}

function ok_onclick(){
	if (confirm("您确定要提交吗？")) {
		commodityFeeForm.status.value = "2";//2为通过
		commodityFeeForm.submit();
	  	commodityFeeForm.query.disabled = true;  
	  	commodityFeeForm.query1.disabled = true;
	}
}

function fail_onclick(){
	if (confirm("您确定要提交吗？")) {
		commodityFeeForm.status.value = "3";//3为不通过
		commodityFeeForm.submit();
	  	commodityFeeForm.query.disabled = true;
	  	commodityFeeForm.query1.disabled = true;
	}
}

</script>
	</head>

	<body>
		<table border="0"  width="100%" cellpadding="0" cellspacing="0" class="common" align="center">
			<tr>
				<td>
					<form action="${basePath }/timebargain/check/updateCommodityFeeChe.action"
						method="POST" class="form" id="commodityFeeForm" name="commodityFeeForm">
						<fieldset class="pickList" >
							<legend class="common">
								<b>商品手续费信息
								</b>
							</legend>
							<table width="100%" border="0" align="center" cellpadding="5" cellspacing="5" border="0" class="common">
								<tr>
									<td width="150" align="right">
									商品代码：${apply.commodityID}
									</td>
									<td width="150" colspan="2">
										最低交收手续费金额：${apply.lowestSettleFee}
									</td>
								</tr>
								
								<tr>
									<td width="150" rowspan="4" valign="top" align="right">
										交易手续费算法：${applyAndCheck_algrMap[apply.feeAlgr]}
									</td>
									<%
									    CommodityFeeApply apply = (CommodityFeeApply) request.getAttribute("commodityFeeApply");
										if (apply.getFeeAlgr() == 1) {
									%>
										<td width="150">
											买订立：<fmt:formatNumber value="${apply.feeRate_B}" pattern="#,##0.00"/>%&nbsp;
										</td>
										<td width="150">
											卖订立：<fmt:formatNumber value="${apply.feeRate_S}" pattern="#,##0.00"/>%&nbsp;
										</td>
									<%
										}else {
									%>
										<td width="150">
											买订立：<fmt:formatNumber value="${apply.feeRate_B}" pattern="#,##0.00"/>&nbsp;
										</td>
										<td width="150">
											卖订立：<fmt:formatNumber value="${apply.feeRate_S}" pattern="#,##0.00"/>&nbsp;
										</td>									
									<%
										}
									%>
									
								</tr>
								
								<%
									if (apply.getFeeAlgr() == 1) {
								%>
									<tr>
										<td width="150">
											买转让历史订货：${apply.historyCloseFeeRate_B}%
										</td>
										<td width="150">
											卖转让历史订货：${apply.historyCloseFeeRate_S}%
										</td>
										
									</tr>
									<tr>
										<td width="150">
											买转让今订货：${apply.todayCloseFeeRate_B}%
										</td>
										<td width="150">
											卖转让今订货：${apply.todayCloseFeeRate_S}%
										</td>
										
									</tr>
									<tr>
										<td width="150">
											买强制转让：${apply.forceCloseFeeRate_B}%
										</td>
										<td width="150">
											卖强制转让：${apply.forceCloseFeeRate_S}%
										</td>
									</tr>
								<%
									}else {
								%>
									<tr>
										<td width="150">
											买转让历史订货：${apply.historyCloseFeeRate_B}
										</td>
										<td width="150">
											卖转让历史订货：${apply.historyCloseFeeRate_S}
										</td>
										
									</tr>
									<tr>
										<td width="150">
											买转让今订货：${apply.todayCloseFeeRate_B}
										</td>
										<td width="150">
											卖转让今订货：${apply.todayCloseFeeRate_S}
										</td>
										
									</tr>
									<tr>
										<td width="150">
											买强制转让：${apply.forceCloseFeeRate_B}
										</td>
										<td width="150">
											卖强制转让：${apply.forceCloseFeeRate_S}
										</td>
									</tr>
								<%
									}
								%>
								
								
								<tr>
									<td width="150" align="right">
										交收手续费算法：${applyAndCheck_algrMap[apply.settleFeeAlgr]}
									</td>
									<%
										if (apply.getSettleFeeAlgr() == 1) {
									%>
										<td width="150">
											交收买：${apply.settleFeeRate_B}%
										</td>
										<td width="150">
											交收卖：${apply.settleFeeRate_S}%
										</td>
									<%		
										}else {
									%>
										<td width="150">
											交收买：${apply.settleFeeRate_B}
										</td>
										<td width="150">
											交收卖：${apply.settleFeeRate_S}
										</td>
									<%
										}
									%>
									
									
								</tr>
								
								<tr>
									
									<td width="150" align="right">
										当前状态：${status}
									</td>
									<td width="150" colspan="2">
										申请人：${entity.proposer}
									</td>
								</tr>
								<c:if test="${entity.status == 2 or entity.status == 3}">
									<tr>
									<td align="right">
										审核人：
									</td>
									<td>
										${entity.approver}
									</td>
									</tr>
								</c:if>
								
								<tr>
									<td>
										备注：
									</td>
								</tr>
								<tr>
									<td colspan="3">
										<textarea name="note" rows="3" cols="95"  style="width:420" class="text">${entity.note}</textarea>
										
									</td>
									
								</tr>
									
								<tr>
						            <td align="center" colspan="3">
						            	<c:if test="${entity.status == 1}">
										<input type="button" id="query" value="通过" class="anniu_btn"
											onclick="javascript:return ok_onclick();"/>
											
										<input type="button" id="query1" value="不通过" class="anniu_btn"
											onclick="javascript:return fail_onclick();"/>
										</c:if>	
										<input type="button" name="close" value="关闭" class="anniu_btn"
											onclick="javascript:return cancel_onclick();"/>
											
									</td>  		                                                           							
								</tr>
								

							</table>
						</fieldset>
						<input type="hidden" name="id" value="${entity.id}"/>
						<input type="hidden" id="status" name="status" value="${entity.status}"/>
						<input type="hidden" name="commodityID" value="${apply.commodityID}"/>
						<input type="hidden" name="feeAlgr" value="${apply.feeAlgr}"/>
						<input type="hidden" name="feeRate_B" value="${apply.feeRate_B}"/>
						<input type="hidden" name="feeRate_S" value="${apply.feeRate_S}"/>
						<input type="hidden" name="historyCloseFeeRate_B" value="${apply.historyCloseFeeRate_B}"/>
						<input type="hidden" name="historyCloseFeeRate_S" value="${apply.historyCloseFeeRate_S}"/>
						<input type="hidden" name="todayCloseFeeRate_B" value="${apply.todayCloseFeeRate_B}"/>
						<input type="hidden" name="todayCloseFeeRate_S" value="${apply.todayCloseFeeRate_S}"/>
						<input type="hidden" name="forceCloseFeeRate_B" value="${apply.forceCloseFeeRate_B}"/>
						<input type="hidden" name="forceCloseFeeRate_S" value="${apply.forceCloseFeeRate_S}"/>
						<input type="hidden" name="settleFeeAlgr" value="${apply.settleFeeAlgr}"/>
						<input type="hidden" name="settleFeeRate_B" value="${apply.settleFeeRate_B}"/>
						<input type="hidden" name="settleFeeRate_S" value="${apply.settleFeeRate_S}"/>
						
					</form>
					
					
					
				</td>
			</tr>
		</table>

	</body>
</html>
