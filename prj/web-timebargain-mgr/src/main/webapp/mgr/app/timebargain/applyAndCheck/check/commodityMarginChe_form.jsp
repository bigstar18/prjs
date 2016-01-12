<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%@ page import="gnnt.MEBS.timebargain.mgr.model.apply.CommodityMarginApply" %>
<html>
	<head>
		<base target="_self" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
		<title>
		商品保证金信息
		</title>
		<script type="text/javascript"> 
function window_onload()
{
    /*
    if (frm.status.value == "1") {
    	setReadWrite(frm.note);
    }else {
    	setReadOnly(frm.note);
    }*/
}
//query_onclick

function cancel_onclick()
{
   window.close();
}

function relase()
{
	    frm.query.disabled = false;
	  	frm.query1.disabled = false;
}

function ok_onclick(){
	if (confirm("您确定要提交吗？")) {
		frm.status.value = "2";//2为通过
		frm.submit();
	  	frm.query.disabled = true;  
	  	frm.query1.disabled = true;
	}
}

function fail_onclick(){
	if (confirm("您确定要提交吗？")) {
		frm.status.value = "3";//3为不通过
		frm.submit();
	  	frm.query.disabled = true;
	  	frm.query1.disabled = true;
	}
}

</script>
	</head>

	<body leftmargin="2" topmargin="0" 
		onkeypress="keyEnter(event.keyCode);" onload="window_onload()">
		<table border="0"  width="100%" cellpadding="0" cellspacing="0" class="common" align="center">
			<tr>
				<td>
					<%
					CommodityMarginApply apply = (CommodityMarginApply) request.getAttribute("commodityMarginApply");				
								%>
					<form action="<%=basePath%>/timebargain/check/updateCommodityMarginChe.action"
						method="POST" class="form" name="frm">
						<fieldset class="pickList" >
							<legend class="common">
								<b>商品保证金信息
								</b>
							</legend>
							<table width="100%" border="0" align="center" cellpadding="5" cellspacing="5" border="0" class="common">
								<tr>
									<td width="280" align="right">
									商品代码：<%=apply.getCommodityID()%>
									</td>
									<td width="220" >
										 保证金算法：${marginAlgr}
									</td>
									<td width="220" colspan="2">
										 保证金计算依据：${marginPriceType}
									</td>
								</tr>
								
								<tr>
									<td width="280" rowspan="2" valign="top" align="right">
										第一阶段保证金 生效日期：<%=apply.getSettleDate1()%>
									</td>
									<%
										if (apply.getMarginAlgr() == 1) {
									%>
										<td width="150">
											买保证金1：<%=apply.getMarginItem1()%>%
										</td>
										<td width="150">
											买担保金1：<%=apply.getMarginItemAssure1()%>%
										</td>
									<%
										}else {
									%>
										<td width="150">
											买保证金1：<%=apply.getMarginItem1()%>
										</td>
										<td width="150">
											买担保金1：<%=apply.getMarginItemAssure1()%>
										</td>
									<%
										}
									%>
									
								</tr>
									
								<tr>
									<%
										if (apply.getMarginAlgr() == 1) {
									%>
										<td width="150">
											卖保证金1：<%=apply.getMarginItem1_S()%>%&nbsp;
										</td>
										<td width="150">
											卖担保金1：<%=apply.getMarginItemAssure1_S()%>%&nbsp;
										</td>
									<%
										}else {
									%>
										<td width="150">
											卖保证金1：<%=apply.getMarginItem1_S()%>&nbsp;
										</td>
										<td width="150">
											卖担保金1：<%=apply.getMarginItemAssure1_S()%>&nbsp;
										</td>
									<%
										}
									%>
								</tr>
			
			<tr>
									<td width="280" rowspan="2" valign="top" align="right">
										第二阶段保证金 生效日期：<%=apply.getSettleDate2()%>
									</td>
									<%
										if (apply.getMarginAlgr() == 1) {
									%>
										<td width="150">
											买保证金2：${apply.marginItem2}%&nbsp;
										</td>
										<td width="150">
											买担保金2：${apply.marginItemAssure2}%&nbsp;
										</td>
									<%
										}else {
									%>
										<td width="150">
											买保证金2：${apply.marginItem2}&nbsp;
										</td>
										<td width="150">
											买担保金2：${apply.marginItemAssure2}&nbsp;
										</td>
									<%
										}
									%>
									
								</tr>
									
								<tr>
									<%
										if (apply.getMarginAlgr() == 1) {
									%>
										<td width="150">
											卖保证金2：${apply.marginItem2_S}%&nbsp;
										</td>
										<td width="150">
											卖担保金2：${apply.marginItemAssure2_S}%&nbsp;
										</td>
									<%
										}else {
									%>
										<td width="150">
											卖保证金2：${apply.marginItem2_S}&nbsp;
										</td>
										<td width="150">
											卖担保金2：${apply.marginItemAssure2_S}&nbsp;
										</td>
									<%
										}
									%>
								</tr>
			
			<tr>
									<td width="280" rowspan="2" valign="top" align="right">
										第三阶段保证金 生效日期：<%=apply.getSettleDate3()%>
									</td>
									<%
										if (apply.getMarginAlgr() == 1) {
									%>
										<td width="150">
											买保证金3：${apply.marginItem3}%&nbsp;
										</td>
										<td width="150">
											买担保金3：${apply.marginItemAssure3}%&nbsp;
										</td>
									<%
										}else {
									%>
										<td width="150">
											买保证金3：${apply.marginItem3}&nbsp;
										</td>
										<td width="150">
											买担保金3：${apply.marginItemAssure3}&nbsp;
										</td>
									<%
										}
									%>
									
								</tr>
									
								<tr>
									<%
										if (apply.getMarginAlgr() == 1) {
									%>
										<td width="150">
											卖保证金3：${apply.marginItem3_S}%&nbsp;
										</td>
										<td width="150">
											卖担保金3：${apply.marginItemAssure3_S}%&nbsp;
										</td>
									<%
										}else {
									%>
										<td width="150">
											卖保证金3：${apply.marginItem3_S}&nbsp;
										</td>
										<td width="150">
											卖担保金3：${apply.marginItemAssure3_S}&nbsp;
										</td>
									<%
										}
									%>
								</tr>
			
			<tr>
									<td width="280" rowspan="2" valign="top" align="right">
										第四阶段保证金 生效日期：<%=apply.getSettleDate4()%>
									</td>
									<%
										if (apply.getMarginAlgr() == 1) {
									%>
										<td width="150">
											买保证金4：${apply.marginItem4}%&nbsp;
										</td>
										<td width="150">
											买担保金4：${apply.marginItemAssure4}%&nbsp;
										</td>
									<%
										}else {
									%>
										<td width="150">
											买保证金4：${apply.marginItem4}&nbsp;
										</td>
										<td width="150">
											买担保金4：${apply.marginItemAssure4}&nbsp;
										</td>
									<%
										}
									%>
									
								</tr>
									
								<tr>
									<%
										if (apply.getMarginAlgr() == 1) {
									%>
										<td width="150">
											卖保证金4：${apply.marginItem4_S}%&nbsp;
										</td>
										<td width="150">
											卖担保金4：${apply.marginItemAssure4_S}%&nbsp;
										</td>
									<%
										}else {
									%>
										<td width="150">
											卖保证金4：${apply.marginItem4_S}&nbsp;
										</td>
										<td width="150">
											卖担保金4：${apply.marginItemAssure4_S}&nbsp;
										</td>
									<%
										}
									%>
								</tr>
			
			<tr>
									<td width="280" rowspan="2" valign="top" align="right">
										第五阶段保证金 生效日期：<%=apply.getSettleDate5()%>
									</td>
									<%
										if (apply.getMarginAlgr() == 1) {
									%>
										<td width="150">
											买保证金5：${apply.marginItem5}%&nbsp;
										</td>
										<td width="150">
											买担保金5：${apply.marginItemAssure5}%&nbsp;
										</td>
									<%
										}else {
									%>
										<td width="150">
											买保证金5：${apply.marginItem5}&nbsp;
										</td>
										<td width="150">
											买担保金5：${apply.marginItemAssure5}&nbsp;
										</td>
									<%
										}
									%>
									
								</tr>
									
								<tr>
									<%
										if (apply.getMarginAlgr() == 1) {
									%>
										<td width="150">
											卖保证金5：${apply.marginItem5_S}%&nbsp;
										</td>
										<td width="150">
											卖担保金5：${apply.marginItemAssure5_S}%&nbsp;
										</td>
									<%
										}else {
									%>
										<td width="150">
											卖保证金5：${apply.marginItem5_S}&nbsp;
										</td>
										<td width="150">
											卖担保金5：${apply.marginItemAssure5_S}&nbsp;
										</td>
									<%
										}
									%>
								</tr>
									<tr>
										<td width="280" rowspan="2" align="right">
											当前生效交易保证金：
										</td>
										
									</tr>
								<%
									if (apply.getMarginAlgr() == 1) {
								%>
									<tr>
										<td width="150">
											买方保证金：${apply.marginRate_B}%
										</td>
										<td width="150">
											卖方保证金：${apply.marginRate_S}%
										</td>
									</tr>
								<%
									}else {
								%>
									<tr>
										<td width="150">
											买方保证金：${apply.marginRate_B}
										</td>
										<td width="150">
											卖方保证金：${apply.marginRate_S}
										</td>
									</tr>
								<%
									}
								%>
								
								
								<tr>
									<td width="280" align="right" rowspan="2">
										交收保证金 生效日期：<%=apply.getSettleDate()%>
									</td>
									<td width="150">
											买方方式：${settleMarginAlgr_B}
									</td>
									<%
										if (apply.getSettleMarginAlgr_B() == 1) {
									%>
										<td width="150">
											买方标准：<%=apply.getSettleMarginRate_B()%>%
										</td>
									<%		
										}else {
									%>
										<td width="150">
											买方标准：<%=apply.getSettleMarginRate_B()%>
										</td>
									<%
										}
									%>
									
									
								</tr>
								<tr>
									<td width="150">
											卖方方式：${settleMarginAlgr_S}
									</td>
									<%
										if (apply.getSettleMarginAlgr_S() == 1) {
									%>
										<td width="150">
											卖方标准：<%=apply.getSettleMarginRate_S()%>%
										</td>
									<%		
										}else {
									%>
										<td width="150">
											卖方标准：<%=apply.getSettleMarginRate_S()%>
										</td>
									<%
										}
									%>
								</tr>
								<tr>
									<td width="280" align="right">
										交收货款算法：${payoutAlgr}
									</td>
									<%
										if (apply.getPayoutAlgr() == 1) {
									%>
										<td>
											交收货款标准：<%=apply.getPayoutRate()%>%
										</td>
									<%
										}else {
									%>
										<td>
											交收货款标准：<%=apply.getPayoutRate()%>
										</td>
									<%
										}
									%>
								</tr>
								
								<tr>
									
									<td width="280" align="right">
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
										<textarea name="note" rows="3" cols="95"  style="width:520" class="text">${entity.note}</textarea>
										
									</td>
									
								</tr>
								
								<tr>
						            <td align="center" colspan="4">
						            	<c:if test="${entity.status == 1}">
										<input type="button" name="query" value="通过" class="anniu_btn"
											onclick="javascript:return ok_onclick();"/>
											
										<input type="button" name="query1" value="不通过" class="anniu_btn"
											onclick="javascript:return fail_onclick();"/>
										</c:if>	
										<input type="button" name="close" value="关闭" class="anniu_btn"
											onclick="javascript:return cancel_onclick();"/>
											
									</td>  		                                                           							
								</tr>
								
							</table>
						</fieldset>
						<input type="hidden" name="id" value="${entity.id}"/>
						<input type="hidden" name="status" value="${entity.status}"/>
						<input type="hidden" name="commodityID" value="<%=apply.getCommodityID()+""%>"/>
						<input type="hidden" name="marginAlgr" value="<%=apply.getMarginAlgr()+""%>"/>
						<input type="hidden" name="marginPriceType" value="<%=apply.getMarginPriceType()+""%>"/>
						<input type="hidden" name="settleDate1" value="<%=apply.getSettleDate1()%>"/>
						<input type="hidden" name="settleDate2" value="<%=apply.getSettleDate2()%>"/>
						<input type="hidden" name="settleDate3" value="<%=apply.getSettleDate3()%>"/>
						<input type="hidden" name="settleDate4" value="<%=apply.getSettleDate4()%>"/>
						<input type="hidden" name="settleDate5" value="<%=apply.getSettleDate5()%>"/>
						<input type="hidden" name="settleDate" value="<%=apply.getSettleDate()%>"/>
						<input type="hidden" name="marginItem1" value="<%=apply.getMarginItem1()+""%>"/>
						<input type="hidden" name="marginItem2" value="<%=apply.getMarginItem2()+""%>"/>
						<input type="hidden" name="marginItem3" value="<%=apply.getMarginItem3()+""%>"/>
						<input type="hidden" name="marginItem4" value="<%=apply.getMarginItem4()+""%>"/>
						<input type="hidden" name="marginItem5" value="<%=apply.getMarginItem5()+""%>"/>
						
						<input type="hidden" name="marginItem1_S" value="<%=apply.getMarginItem1_S()+""%>"/>
						<input type="hidden" name="marginItem2_S" value="<%=apply.getMarginItem2_S()+""%>"/>
						<input type="hidden" name="marginItem3_S" value="<%=apply.getMarginItem3_S()+""%>"/>
						<input type="hidden" name="marginItem4_S" value="<%=apply.getMarginItem4_S()+""%>"/>
						<input type="hidden" name="marginItem5_S" value="<%=apply.getMarginItem5_S()+""%>"/>
						
						<input type="hidden" name="marginItemAssure1" value="<%=apply.getMarginItemAssure1()+""%>"/>
						<input type="hidden" name="marginItemAssure2" value="<%=apply.getMarginItemAssure2()+""%>"/>
						<input type="hidden" name="marginItemAssure3" value="<%=apply.getMarginItemAssure3()+""%>"/>
						<input type="hidden" name="marginItemAssure4" value="<%=apply.getMarginItemAssure4()+""%>"/>
						<input type="hidden" name="marginItemAssure5" value="<%=apply.getMarginItemAssure5()+""%>"/>
						<input type="hidden" name="marginItemAssure1_S" value="<%=apply.getMarginItemAssure1_S()+""%>"/>
						<input type="hidden" name="marginItemAssure2_S" value="<%=apply.getMarginItemAssure2_S()+""%>"/>
						<input type="hidden" name="marginItemAssure3_S" value="<%=apply.getMarginItemAssure3_S()+""%>"/>
						<input type="hidden" name="marginItemAssure4_S" value="<%=apply.getMarginItemAssure4_S()+""%>"/>
						<input type="hidden" name="marginItemAssure5_S" value="<%=apply.getMarginItemAssure5_S()+""%>"/>
						<input type="hidden" name="settleMarginAlgr_B" value="<%=apply.getSettleMarginAlgr_B()+""%>"/>
						<input type="hidden" name="settleMarginAlgr_S" value="<%=apply.getSettleMarginAlgr_S()+""%>"/>
						<input type="hidden" name="settleMarginRate_B" value="<%=apply.getSettleMarginRate_B()+""%>"/>
						<input type="hidden" name="settleMarginRate_S" value="<%=apply.getSettleMarginRate_S()+""%>"/>
						<input type="hidden" name="payoutAlgr" value="<%=apply.getPayoutAlgr()+""%>"/>
						<input type="hidden" name="payoutRate" value="<%=apply.getPayoutRate()+""%>"/>
					</form>
					
					
					
				</td>
			</tr>
		</table>
	</body>
</html>
