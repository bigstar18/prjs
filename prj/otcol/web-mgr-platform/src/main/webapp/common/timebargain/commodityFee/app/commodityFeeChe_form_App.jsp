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
    setReadOnly(commodityFeeForm.note);
}
//query_onclick

function cancel_onclick()
{
   window.close();
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
					<form action="<%=basePath%>/timebargain/pledgeCheck.spr"
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
									
								</tr>
								
									<tr align="center">
									
									<td width="100%" colspan="3" align="center">
										<input type="button" name="close" value="关闭" style="width:60" class="button"
											onclick="javascript:return cancel_onclick();"/>
											
									</td>  		                                                           							
								</tr>
								
								
								
								
							</table>
						</fieldset>
						
					</form>
					
					
					
				</td>
			</tr>
		</table>

		<%@ include file="../../common/messages.jsp"%>
	</body>
</html>
