<%@ include file="../../common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>



<%@ page import="gnnt.MEBS.common.manage.service.*"%>
<%@ page import="gnnt.MEBS.common.manage.model.*"%>
<%@ page import="gnnt.MEBS.finance.base.util.SysData"%>
<html>
	<head>
		<title>
		��Ʒ��֤����Ϣ
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
											Apply_T_CommodityMargin apply = new Apply_T_CommodityMargin();
											String id = request.getParameter("id");
											System.out.println("id: "+id);
											long relID = 0;
											if (id != null && !"".equals(id)) {
												relID = Long.parseLong(id);
												apply.setId(relID);
											}
											apply = (Apply_T_CommodityMargin)mgr.getApplyById(apply, false);
											
											
											String commodityID = "";
											String marginAlgr = "";
											String marginPriceType = "";
											String settleDate1 ="";
											String settleDate2 = "";
											String settleDate3 = "";
											String settleDate4 = "";
											String settleDate5 = "";
											String marginItem1 = "";
											String marginItem2 ="";
											String marginItem3 = "";
											String marginItem4 = "";
											String marginItem5 = "";
											String marginItem1_S = "";
											String marginItem2_S = "";
											String marginItem3_S = "";
											String marginItem4_S = "";
											String marginItem5_S = "";
											String marginItemAssure1 = "";
											String marginItemAssure2 = "";
											String marginItemAssure3 = "";
											String marginItemAssure4 = "";
											String marginItemAssure5 = "";
											String marginItemAssure1_S = "";
											String marginItemAssure2_S = "";
											String marginItemAssure3_S = "";
											String marginItemAssure4_S = "";
											String marginItemAssure5_S = "";
											
											String marginRate_B = "";
											String marginRate_S = "";
											String settleDate = "";
											String settleMarginAlgr_B = "";
											String settleMarginAlgr_S = "";
											String settleMarginRate_B = "";
											String settleMarginRate_S = "";
											String payoutAlgr = "";
											String payoutRate = "";
											
											String status = "";
											String proposer ="";
											String type = "";
											String note = "";
											String approver = "";
											
											if (apply != null) {
												commodityID = apply.getCommodityID();
												if (apply.getMarginAlgr() == 1) {
													marginAlgr = "���ٷֱ�";
												}else if (apply.getMarginAlgr() == 2) {
													marginAlgr = "������ֵ";
												}
												if (apply.getMarginPriceType() == 0) {
													marginPriceType = "������";
												}else if (apply.getMarginPriceType() == 1) {
													marginPriceType = "������";
												}else if (apply.getMarginPriceType() == 2) {
													marginPriceType = "���а�������";
												}
												settleDate1 = apply.getSettleDate1() == null ? "" : apply.getSettleDate1();
												settleDate2 = apply.getSettleDate2() == null ? "" : apply.getSettleDate2();
												settleDate3 = apply.getSettleDate3() == null ? "" : apply.getSettleDate3();
												settleDate4 = apply.getSettleDate4() == null ? "" : apply.getSettleDate4();
												settleDate5 = apply.getSettleDate5() == null ? "" : apply.getSettleDate5();
												settleDate = apply.getSettleDate() == null ? "" : apply.getSettleDate();
												marginItem1 = apply.getMarginItem1() == null ? "" : apply.getMarginItem1()+"";
												marginItem2 = apply.getMarginItem2() == null ? "" : apply.getMarginItem2()+"";
												marginItem3 = apply.getMarginItem3() == null ? "" : apply.getMarginItem3()+"";
												marginItem4 = apply.getMarginItem4() == null ? "" : apply.getMarginItem4()+"";
												marginItem5 = apply.getMarginItem5() == null ? "" : apply.getMarginItem5()+"";
												
												marginItem1_S = apply.getMarginItem1_S() == null ? "" : apply.getMarginItem1_S()+"";
												marginItem2_S = apply.getMarginItem2_S() == null ? "" : apply.getMarginItem2_S()+"";
												marginItem3_S = apply.getMarginItem3_S() == null ? "" : apply.getMarginItem3_S()+"";
												marginItem4_S = apply.getMarginItem4_S() == null ? "" : apply.getMarginItem4_S()+"";
												marginItem5_S = apply.getMarginItem5_S() == null ? "" : apply.getMarginItem5_S()+"";
												
												marginItemAssure1 = apply.getMarginItemAssure1() == null ? "" : apply.getMarginItemAssure1()+"";
												marginItemAssure2 = apply.getMarginItemAssure2() == null ? "" : apply.getMarginItemAssure2()+"";
												marginItemAssure3 = apply.getMarginItemAssure3() == null ? "" : apply.getMarginItemAssure3()+"";
												marginItemAssure4 = apply.getMarginItemAssure4() == null ? "" : apply.getMarginItemAssure4()+"";
												marginItemAssure5 = apply.getMarginItemAssure5() == null ? "" : apply.getMarginItemAssure5()+"";
												
												marginItemAssure1_S = apply.getMarginItemAssure1_S() == null ? "" : apply.getMarginItemAssure1_S()+"";
												marginItemAssure2_S = apply.getMarginItemAssure2_S() == null ? "" : apply.getMarginItemAssure2_S()+"";
												marginItemAssure3_S = apply.getMarginItemAssure3_S() == null ? "" : apply.getMarginItemAssure3_S()+"";
												marginItemAssure4_S = apply.getMarginItemAssure4_S() == null ? "" : apply.getMarginItemAssure4_S()+"";
												marginItemAssure5_S = apply.getMarginItemAssure5_S() == null ? "" : apply.getMarginItemAssure5_S()+"";
												
												marginRate_B = apply.getMarginRate_B() == null ? "" : apply.getMarginRate_B()+"";
												marginRate_S = apply.getMarginRate_S() == null ? "" : apply.getMarginRate_S()+"";
												
												if (apply.getSettleMarginAlgr_B() == 1) {
													settleMarginAlgr_B = "���ٷֱ�";
												}else if (apply.getSettleMarginAlgr_B() == 2){
													settleMarginAlgr_B = "������ֵ";
												}
												
												if (apply.getSettleMarginAlgr_S() == 1) {
													settleMarginAlgr_S = "���ٷֱ�";
												}else if (apply.getSettleMarginAlgr_S() == 2){
													settleMarginAlgr_S = "������ֵ";
												}
												
												
												settleMarginRate_B = apply.getSettleMarginRate_B() == null ? "" : apply.getSettleMarginRate_B()+"";
												settleMarginRate_S = apply.getSettleMarginRate_S() == null ? "" : apply.getSettleMarginRate_S()+"";
												
												if (apply.getPayoutAlgr() == 1) {
													payoutAlgr = "���ٷֱ�";
												}else if (apply.getPayoutAlgr() == 2){
													payoutAlgr = "������ֵ";
												}
												payoutRate = apply.getPayoutRate()+"";
												
												if ("1".equals(apply.getStatus()+"")) {
													status = "�����";
												}else if ("2".equals(apply.getStatus()+"")) {
													status = "���ͨ��";
												}else if ("3".equals(apply.getStatus()+"")) {
													status = "��˲�ͨ��";
												}
												proposer = apply.getProposer();
												note = apply.getNote();
												if(note==null)
												note="";
												approver = apply.getApprover();
											}
								%>
					<form action="<%=basePath%>/timebargain/commodityMarginCheck.spr"
						method="POST" class="form" target="HiddFrame2" name="commodityFeeForm">
						<fieldset class="pickList" >
							<legend class="common">
								<b>��Ʒ��֤����Ϣ
								</b>
							</legend>
							<table width="100%" border="0" align="center" cellpadding="5" cellspacing="5" border="0" class="common">
								<tr>
									<td width="280" align="right">
									��Ʒ���룺<%=commodityID%>
									</td>
									<td width="220" >
										 ��֤���㷨��<%=marginAlgr%>
									</td>
									<td width="220" colspan="2">
										 ��֤��������ݣ�<%=marginPriceType%>
									</td>
								</tr>
								
								<tr>
									<td width="280" rowspan="2" valign="top" align="right">
										��һ�׶α�֤�� ��Ч���ڣ�<%=settleDate1%>
									</td>
									<%
										if (apply.getMarginAlgr() == 1) {
									%>
										<td width="150">
											��֤��1��<%=marginItem1%>%
										</td>
										<td width="150">
											�򵣱���1��<%=marginItemAssure1%>%
										</td>
									<%
										}else {
									%>
										<td width="150">
											��֤��1��<%=marginItem1%>
										</td>
										<td width="150">
											�򵣱���1��<%=marginItemAssure1%>
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
											����֤��1��<%=marginItem1_S%>%&nbsp;
										</td>
										<td width="150">
											��������1��<%=marginItemAssure1_S%>%&nbsp;
										</td>
									<%
										}else {
									%>
										<td width="150">
											����֤��1��<%=marginItem1_S%>&nbsp;
										</td>
										<td width="150">
											��������1��<%=marginItemAssure1_S%>&nbsp;
										</td>
									<%
										}
									%>
								</tr>
			
			<tr>
									<td width="280" rowspan="2" valign="top" align="right">
										�ڶ��׶α�֤�� ��Ч���ڣ�<%=settleDate2%>
									</td>
									<%
										if (apply.getMarginAlgr() == 1) {
									%>
										<td width="150">
											��֤��2��<%=marginItem2%>%&nbsp;
										</td>
										<td width="150">
											�򵣱���2��<%=marginItemAssure2%>%&nbsp;
										</td>
									<%
										}else {
									%>
										<td width="150">
											��֤��2��<%=marginItem2%>&nbsp;
										</td>
										<td width="150">
											�򵣱���2��<%=marginItemAssure2%>&nbsp;
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
											����֤��2��<%=marginItem2_S%>%&nbsp;
										</td>
										<td width="150">
											��������2��<%=marginItemAssure2_S%>%&nbsp;
										</td>
									<%
										}else {
									%>
										<td width="150">
											����֤��2��<%=marginItem2_S%>&nbsp;
										</td>
										<td width="150">
											��������2��<%=marginItemAssure2_S%>&nbsp;
										</td>
									<%
										}
									%>
								</tr>
			
			<tr>
									<td width="280" rowspan="2" valign="top" align="right">
										�����׶α�֤�� ��Ч���ڣ�<%=settleDate3%>
									</td>
									<%
										if (apply.getMarginAlgr() == 1) {
									%>
										<td width="150">
											��֤��3��<%=marginItem3%>%&nbsp;
										</td>
										<td width="150">
											�򵣱���3��<%=marginItemAssure3%>%&nbsp;
										</td>
									<%
										}else {
									%>
										<td width="150">
											��֤��3��<%=marginItem3%>&nbsp;
										</td>
										<td width="150">
											�򵣱���3��<%=marginItemAssure3%>&nbsp;
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
											����֤��3��<%=marginItem3_S%>%&nbsp;
										</td>
										<td width="150">
											��������3��<%=marginItemAssure3_S%>%&nbsp;
										</td>
									<%
										}else {
									%>
										<td width="150">
											����֤��3��<%=marginItem3_S%>&nbsp;
										</td>
										<td width="150">
											��������3��<%=marginItemAssure3_S%>&nbsp;
										</td>
									<%
										}
									%>
								</tr>
			
			<tr>
									<td width="280" rowspan="2" valign="top" align="right">
										���Ľ׶α�֤�� ��Ч���ڣ�<%=settleDate4%>
									</td>
									<%
										if (apply.getMarginAlgr() == 1) {
									%>
										<td width="150">
											��֤��4��<%=marginItem4%>%&nbsp;
										</td>
										<td width="150">
											�򵣱���4��<%=marginItemAssure4%>%&nbsp;
										</td>
									<%
										}else {
									%>
										<td width="150">
											��֤��4��<%=marginItem4%>&nbsp;
										</td>
										<td width="150">
											�򵣱���4��<%=marginItemAssure4%>&nbsp;
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
											����֤��4��<%=marginItem4_S%>%&nbsp;
										</td>
										<td width="150">
											��������4��<%=marginItemAssure4_S%>%&nbsp;
										</td>
									<%
										}else {
									%>
										<td width="150">
											����֤��4��<%=marginItem4_S%>&nbsp;
										</td>
										<td width="150">
											��������4��<%=marginItemAssure4_S%>&nbsp;
										</td>
									<%
										}
									%>
								</tr>
			
			<tr>
									<td width="280" rowspan="2" valign="top" align="right">
										����׶α�֤�� ��Ч���ڣ�<%=settleDate5%>
									</td>
									<%
										if (apply.getMarginAlgr() == 1) {
									%>
										<td width="150">
											��֤��5��<%=marginItem5%>%&nbsp;
										</td>
										<td width="150">
											�򵣱���5��<%=marginItemAssure5%>%&nbsp;
										</td>
									<%
										}else {
									%>
										<td width="150">
											��֤��5��<%=marginItem5%>&nbsp;
										</td>
										<td width="150">
											�򵣱���5��<%=marginItemAssure5%>&nbsp;
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
											����֤��5��<%=marginItem5_S%>%&nbsp;
										</td>
										<td width="150">
											��������5��<%=marginItemAssure5_S%>%&nbsp;
										</td>
									<%
										}else {
									%>
										<td width="150">
											����֤��5��<%=marginItem5_S%>&nbsp;
										</td>
										<td width="150">
											��������5��<%=marginItemAssure5_S%>&nbsp;
										</td>
									<%
										}
									%>
								</tr>
									<tr>
										<td width="280" rowspan="2" align="right">
											��ǰ��Ч���ױ�֤��
										</td>
										
									</tr>
								<%
									if (apply.getMarginAlgr() == 1) {
								%>
									<tr>
										<td width="150">
											�򷽱�֤��<%=marginRate_B%>%
										</td>
										<td width="150">
											������֤��<%=marginRate_S%>%
										</td>
									</tr>
								<%
									}else {
								%>
									<tr>
										<td width="150">
											�򷽱�֤��<%=marginRate_B%>
										</td>
										<td width="150">
											������֤��<%=marginRate_S%>
										</td>
									</tr>
								<%
									}
								%>
								
								
								<tr>
									<td width="280" align="right" rowspan="2">
										���ձ�֤�� ��Ч���ڣ�<%=settleDate%>
									</td>
									<td width="150">
											�򷽷�ʽ��<%=settleMarginAlgr_B%>
									</td>
									<%
										if (apply.getSettleMarginAlgr_B() == 1) {
									%>
										<td width="150">
											�򷽱�׼��<%=settleMarginRate_B%>%
										</td>
									<%		
										}else {
									%>
										<td width="150">
											�򷽱�׼��<%=settleMarginRate_B%>
										</td>
									<%
										}
									%>
									
									
								</tr>
								<tr>
									<td width="150">
											������ʽ��<%=settleMarginAlgr_S%>
									</td>
									<%
										if (apply.getSettleMarginAlgr_S() == 1) {
									%>
										<td width="150">
											������׼��<%=settleMarginRate_S%>%
										</td>
									<%		
										}else {
									%>
										<td width="150">
											������׼��<%=settleMarginRate_S%>
										</td>
									<%
										}
									%>
								</tr>
								<tr>
									<td width="280" align="right">
										���ջ����㷨��<%=payoutAlgr%>
									</td>
									<%
										if (apply.getPayoutAlgr() == 1) {
									%>
										<td>
											���ջ����׼��<%=payoutRate%>%
										</td>
									<%
										}else {
									%>
										<td>
											���ջ����׼��<%=payoutRate%>
										</td>
									<%
										}
									%>
								</tr>
								
								<tr>
									
									<td width="280" align="right">
										��ǰ״̬��<%=status%>
									</td>
									<td width="150" colspan="2">
										�����ˣ�<%=proposer%>
									</td>
								</tr>
								<%
									if (apply.getStatus() == 2 || apply.getStatus() == 3) {
								%>
									<tr>
									<td align="right">
										����ˣ�
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
										��ע��
									</td>
								</tr>
								<tr>
									<td colspan="3">
										<textarea name="note" rows="3" cols="95"  style="width:520" class="text"><%=note%></textarea>
										
									</td>
									
								</tr>
								
									<tr align="center">
									
									<td width="100%" colspan="4" align="center">
										<input type="button" name="close" value="�ر�" style="width:60" class="button"
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
