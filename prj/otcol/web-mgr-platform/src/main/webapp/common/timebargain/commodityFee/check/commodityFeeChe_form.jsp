<%@ include file="../../common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>



<%@ page import="gnnt.MEBS.common.manage.service.*"%>
<%@ page import="gnnt.MEBS.common.manage.model.*"%>
<%@ page import="gnnt.MEBS.finance.base.util.SysData"%>
<html>
	<head>
		<title>
		商品手续费信息
		</title>
		<script type="text/javascript"> 
function window_onload()
{
    highlightFormElements();
    if (commodityFeeForm.status.value == "1") {
    	setReadWrite(commodityFeeForm.note);
    }else {
    	setReadOnly(commodityFeeForm.note);
    }
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

	<body leftmargin="2" topmargin="0" 
		onkeypress="keyEnter(event.keyCode);" onload="window_onload()">
		<table border="0"  width="100%" cellpadding="0" cellspacing="0" class="common" align="center">
			<tr>
				<td>
					<%
											ApplyManage mgr = (ApplyManage) SysData.getBean("applyManager");
											Apply_T_CommodityFee apply=new Apply_T_CommodityFee();
											String id = request.getParameter("id");
											long relID = 0;
											if (id != null && !"".equals(id)) {
												relID = Long.parseLong(id);
												apply.setId(relID);
											}
											apply = (Apply_T_CommodityFee)mgr.getApplyById(apply, false);
											
											
											String commodityID = "";
											String feeAlgr = "";
											String feeRate_B = "";
											String feeRate_S ="";
											String historyCloseFeeRate_B = "";
											String historyCloseFeeRate_S = "";
											
											String todayCloseFeeRate_B = "";
											String todayCloseFeeRate_S = "";
											String forceCloseFeeRate_B = "";
											String forceCloseFeeRate_S ="";
											String settleFeeAlgr = "";
											String settleFeeRate_B = "";
											String settleFeeRate_S = "";
											String lowestSettleFee = "";
											
											String status = "";
											String proposer ="";
											String type = "";
											String note = "";
											String approver = "";
											
											if (apply != null) {
												commodityID = apply.getCommodityID();
												if (apply.getFeeAlgr() == 1) {
													feeAlgr = "按百分比";
												}else if (apply.getFeeAlgr() == 2) {
													feeAlgr = "按绝对值";
												}
												feeRate_B = apply.getFeeRate_B()+"";
												feeRate_S = apply.getFeeRate_S()+"";
												historyCloseFeeRate_B = apply.getHistoryCloseFeeRate_B()+"";
												historyCloseFeeRate_S = apply.getHistoryCloseFeeRate_S()+"";
												todayCloseFeeRate_B = apply.getTodayCloseFeeRate_B()+"";
												todayCloseFeeRate_S = apply.getTodayCloseFeeRate_S()+"";
												forceCloseFeeRate_B = apply.getForceCloseFeeRate_B()+"";
												forceCloseFeeRate_S = apply.getForceCloseFeeRate_S()+"";
												if (apply.getSettleFeeAlgr() == 1) {
													settleFeeAlgr = "按百分比";
												}else if (apply.getSettleFeeAlgr() == 2) {
													settleFeeAlgr = "按绝对值";
												}
												settleFeeRate_B = apply.getSettleFeeRate_B()+"";
												settleFeeRate_S = apply.getSettleFeeRate_S()+"";
												lowestSettleFee = apply.getLowestSettleFee()+"";
												
												if ("1".equals(apply.getStatus()+"")) {
													status = "待审核";
												}else if ("2".equals(apply.getStatus()+"")) {
													status = "审核通过";
												}else if ("3".equals(apply.getStatus()+"")) {
													status = "审核不通过";
												}
												proposer = apply.getProposer();
												note = apply.getNote();
												if(note==null)
												note="";
												approver = apply.getApprover();
											}
								%>
					<form action="<%=basePath%>/timebargain/commodityFeeCheck.spr"
						method="POST" class="form" target="HiddFrame2" name="commodityFeeForm">
						<fieldset class="pickList" >
							<legend class="common">
								<b>商品手续费信息
								</b>
							</legend>
							<table width="100%" border="0" align="center" cellpadding="5" cellspacing="5" border="0" class="common">
								<tr>
									<td width="150" align="right">
									商品代码：<%=commodityID%>
									</td>
									<td width="150" colspan="2">
										最低交收手续费金额：<%=lowestSettleFee%>
									</td>
								</tr>
								
								<tr>
									<td width="150" rowspan="4" valign="top" align="right">
										交易手续费算法：<%=feeAlgr%>
									</td>
									<%
										if (apply.getFeeAlgr() == 1) {
									%>
										<td width="150">
											买订立：<fmt:formatNumber value="<%=feeRate_B%>" pattern="#,##0.00"/>%&nbsp;
										</td>
										<td width="150">
											卖订立：<fmt:formatNumber value="<%=feeRate_S%>" pattern="#,##0.00"/>%&nbsp;
										</td>
									<%
										}else {
									%>
										<td width="150">
											买订立：<fmt:formatNumber value="<%=feeRate_B%>" pattern="#,##0.00"/>&nbsp;
										</td>
										<td width="150">
											卖订立：<fmt:formatNumber value="<%=feeRate_S%>" pattern="#,##0.00"/>&nbsp;
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
											买转让历史订货：<%=historyCloseFeeRate_B%>%
										</td>
										<td width="150">
											卖转让历史订货：<%=historyCloseFeeRate_S%>%
										</td>
										
									</tr>
									<tr>
										<td width="150">
											买转让今订货：<%=todayCloseFeeRate_B%>%
										</td>
										<td width="150">
											卖转让今订货：<%=todayCloseFeeRate_S%>%
										</td>
										
									</tr>
									<tr>
										<td width="150">
											买强制转让：<%=forceCloseFeeRate_B%>%
										</td>
										<td width="150">
											卖强制转让：<%=forceCloseFeeRate_S%>%
										</td>
									</tr>
								<%
									}else {
								%>
									<tr>
										<td width="150">
											买转让历史订货：<%=historyCloseFeeRate_B%>
										</td>
										<td width="150">
											卖转让历史订货：<%=historyCloseFeeRate_S%>
										</td>
										
									</tr>
									<tr>
										<td width="150">
											买转让今订货：<%=todayCloseFeeRate_B%>
										</td>
										<td width="150">
											卖转让今订货：<%=todayCloseFeeRate_S%>
										</td>
										
									</tr>
									<tr>
										<td width="150">
											买强制转让：<%=forceCloseFeeRate_B%>
										</td>
										<td width="150">
											卖强制转让：<%=forceCloseFeeRate_S%>
										</td>
									</tr>
								<%
									}
								%>
								
								
								<tr>
									<td width="150" align="right">
										交收手续费算法：<%=settleFeeAlgr%>
									</td>
									<%
										if (apply.getSettleFeeAlgr() == 1) {
									%>
										<td width="150">
											交收买：<%=settleFeeRate_B%>%
										</td>
										<td width="150">
											交收卖：<%=settleFeeRate_S%>%
										</td>
									<%		
										}else {
									%>
										<td width="150">
											交收买：<%=settleFeeRate_B%>
										</td>
										<td width="150">
											交收卖：<%=settleFeeRate_S%>
										</td>
									<%
										}
									%>
									
									
								</tr>
								
								<tr>
									
									<td width="150" align="right">
										当前状态：<%=status%>
									</td>
									<td width="150" colspan="2">
										申请人：<%=proposer%>
									</td>
								</tr>
								<%
									if (apply.getStatus() == 2 || apply.getStatus() == 3) {
								%>
									<tr>
									<td align="right">
										审核人：
									</td>
									<td>
										<%=approver%>
									</td>
									</tr>
								<%		
									}
								%>
								
								<tr>
									<td>
										备注：
									</td>
								</tr>
								<tr>
									<td colspan="3">
										<textarea name="note" rows="3" cols="95"  style="width:420" class="text"><%=note%></textarea>
										
									</td>
									
								<%
									if ("1".equals(apply.getStatus()+"")) {
								%>
									<tr>
								
									
						            <td align="center" colspan="3">
										<input type="button" name="query" value="通过" style="width:60" class="button"
											onclick="javascript:return ok_onclick();"/>
											
										<input type="button" name="query1" value="不通过" style="width:60" class="button"
											onclick="javascript:return fail_onclick();"/>
											
										<input type="button" name="close" value="关闭" style="width:60" class="button"
											onclick="javascript:return cancel_onclick();"/>
											
									</td>  		                                                           							
								</tr>
								<%		
									}else {
								%>
									<tr>
									<td align="center" colspan="3">
										<input type="button" name="close" value="关闭" style="width:60" class="button"
											onclick="javascript:return cancel_onclick();"/>
											
									</td>  		                                                           							
								</tr>
								<%	
									}
								%>
							</table>
						</fieldset>
						<input type="hidden" name="id" value="<%=id%>"/>
						<input type="hidden" name="status" value="<%=apply.getStatus()+""%>"/>
						<input type="hidden" name="commodityID" value="<%=apply.getCommodityID()+""%>"/>
						<input type="hidden" name="feeAlgr" value="<%=apply.getFeeAlgr()+""%>"/>
						<input type="hidden" name="feeRate_B" value="<%=apply.getFeeRate_B()+""%>"/>
						<input type="hidden" name="feeRate_S" value="<%=apply.getFeeRate_S()+""%>"/>
						<input type="hidden" name="historyCloseFeeRate_B" value="<%=apply.getHistoryCloseFeeRate_B()+""%>"/>
						<input type="hidden" name="historyCloseFeeRate_S" value="<%=apply.getHistoryCloseFeeRate_S()+""%>"/>
						<input type="hidden" name="todayCloseFeeRate_B" value="<%=apply.getTodayCloseFeeRate_B()+""%>"/>
						<input type="hidden" name="todayCloseFeeRate_S" value="<%=apply.getTodayCloseFeeRate_S()+""%>"/>
						<input type="hidden" name="forceCloseFeeRate_B" value="<%=apply.getForceCloseFeeRate_B()+""%>"/>
						<input type="hidden" name="forceCloseFeeRate_S" value="<%=apply.getForceCloseFeeRate_S()+""%>"/>
						<input type="hidden" name="settleFeeAlgr" value="<%=apply.getSettleFeeAlgr()+""%>"/>
						<input type="hidden" name="settleFeeRate_B" value="<%=apply.getSettleFeeRate_B()+""%>"/>
						<input type="hidden" name="settleFeeRate_S" value="<%=apply.getSettleFeeRate_S()+""%>"/>
						
					</form>
					
					
					
				</td>
			</tr>
		</table>

		<%@ include file="../../common/messages.jsp"%>
	</body>
</html>
