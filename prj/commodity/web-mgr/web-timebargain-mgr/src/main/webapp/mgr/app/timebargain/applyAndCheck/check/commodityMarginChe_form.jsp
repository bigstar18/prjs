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
		��Ʒ��֤����Ϣ
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
	if (confirm("��ȷ��Ҫ�ύ��")) {
		frm.status.value = "2";//2Ϊͨ��
		frm.submit();
	  	frm.query.disabled = true;  
	  	frm.query1.disabled = true;
	}
}

function fail_onclick(){
	if (confirm("��ȷ��Ҫ�ύ��")) {
		frm.status.value = "3";//3Ϊ��ͨ��
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
								<b>��Ʒ��֤����Ϣ
								</b>
							</legend>
							<table width="100%" border="0" align="center" cellpadding="5" cellspacing="5" border="0" class="common">
								<tr>
									<td width="280" align="right">
									��Ʒ���룺<%=apply.getCommodityID()%>
									</td>
									<td width="220" >
										 ��֤���㷨��${marginAlgr}
									</td>
									<td width="220" colspan="2">
										 ��֤��������ݣ�${marginPriceType}
									</td>
								</tr>
								
								<tr>
									<td width="280" rowspan="2" valign="top" align="right">
										��һ�׶α�֤�� ��Ч���ڣ�<%=apply.getSettleDate1()%>
									</td>
									<%
										if (apply.getMarginAlgr() == 1) {
									%>
										<td width="150">
											��֤��1��<%=apply.getMarginItem1()%>%
										</td>
										<td width="150">
											�򵣱���1��<%=apply.getMarginItemAssure1()%>%
										</td>
									<%
										}else {
									%>
										<td width="150">
											��֤��1��<%=apply.getMarginItem1()%>
										</td>
										<td width="150">
											�򵣱���1��<%=apply.getMarginItemAssure1()%>
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
											����֤��1��<%=apply.getMarginItem1_S()%>%&nbsp;
										</td>
										<td width="150">
											��������1��<%=apply.getMarginItemAssure1_S()%>%&nbsp;
										</td>
									<%
										}else {
									%>
										<td width="150">
											����֤��1��<%=apply.getMarginItem1_S()%>&nbsp;
										</td>
										<td width="150">
											��������1��<%=apply.getMarginItemAssure1_S()%>&nbsp;
										</td>
									<%
										}
									%>
								</tr>
			
			<tr>
									<td width="280" rowspan="2" valign="top" align="right">
										�ڶ��׶α�֤�� ��Ч���ڣ�<%=apply.getSettleDate2()%>
									</td>
									<%
										if (apply.getMarginAlgr() == 1) {
									%>
										<td width="150">
											��֤��2��${apply.marginItem2}%&nbsp;
										</td>
										<td width="150">
											�򵣱���2��${apply.marginItemAssure2}%&nbsp;
										</td>
									<%
										}else {
									%>
										<td width="150">
											��֤��2��${apply.marginItem2}&nbsp;
										</td>
										<td width="150">
											�򵣱���2��${apply.marginItemAssure2}&nbsp;
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
											����֤��2��${apply.marginItem2_S}%&nbsp;
										</td>
										<td width="150">
											��������2��${apply.marginItemAssure2_S}%&nbsp;
										</td>
									<%
										}else {
									%>
										<td width="150">
											����֤��2��${apply.marginItem2_S}&nbsp;
										</td>
										<td width="150">
											��������2��${apply.marginItemAssure2_S}&nbsp;
										</td>
									<%
										}
									%>
								</tr>
			
			<tr>
									<td width="280" rowspan="2" valign="top" align="right">
										�����׶α�֤�� ��Ч���ڣ�<%=apply.getSettleDate3()%>
									</td>
									<%
										if (apply.getMarginAlgr() == 1) {
									%>
										<td width="150">
											��֤��3��${apply.marginItem3}%&nbsp;
										</td>
										<td width="150">
											�򵣱���3��${apply.marginItemAssure3}%&nbsp;
										</td>
									<%
										}else {
									%>
										<td width="150">
											��֤��3��${apply.marginItem3}&nbsp;
										</td>
										<td width="150">
											�򵣱���3��${apply.marginItemAssure3}&nbsp;
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
											����֤��3��${apply.marginItem3_S}%&nbsp;
										</td>
										<td width="150">
											��������3��${apply.marginItemAssure3_S}%&nbsp;
										</td>
									<%
										}else {
									%>
										<td width="150">
											����֤��3��${apply.marginItem3_S}&nbsp;
										</td>
										<td width="150">
											��������3��${apply.marginItemAssure3_S}&nbsp;
										</td>
									<%
										}
									%>
								</tr>
			
			<tr>
									<td width="280" rowspan="2" valign="top" align="right">
										���Ľ׶α�֤�� ��Ч���ڣ�<%=apply.getSettleDate4()%>
									</td>
									<%
										if (apply.getMarginAlgr() == 1) {
									%>
										<td width="150">
											��֤��4��${apply.marginItem4}%&nbsp;
										</td>
										<td width="150">
											�򵣱���4��${apply.marginItemAssure4}%&nbsp;
										</td>
									<%
										}else {
									%>
										<td width="150">
											��֤��4��${apply.marginItem4}&nbsp;
										</td>
										<td width="150">
											�򵣱���4��${apply.marginItemAssure4}&nbsp;
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
											����֤��4��${apply.marginItem4_S}%&nbsp;
										</td>
										<td width="150">
											��������4��${apply.marginItemAssure4_S}%&nbsp;
										</td>
									<%
										}else {
									%>
										<td width="150">
											����֤��4��${apply.marginItem4_S}&nbsp;
										</td>
										<td width="150">
											��������4��${apply.marginItemAssure4_S}&nbsp;
										</td>
									<%
										}
									%>
								</tr>
			
			<tr>
									<td width="280" rowspan="2" valign="top" align="right">
										����׶α�֤�� ��Ч���ڣ�<%=apply.getSettleDate5()%>
									</td>
									<%
										if (apply.getMarginAlgr() == 1) {
									%>
										<td width="150">
											��֤��5��${apply.marginItem5}%&nbsp;
										</td>
										<td width="150">
											�򵣱���5��${apply.marginItemAssure5}%&nbsp;
										</td>
									<%
										}else {
									%>
										<td width="150">
											��֤��5��${apply.marginItem5}&nbsp;
										</td>
										<td width="150">
											�򵣱���5��${apply.marginItemAssure5}&nbsp;
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
											����֤��5��${apply.marginItem5_S}%&nbsp;
										</td>
										<td width="150">
											��������5��${apply.marginItemAssure5_S}%&nbsp;
										</td>
									<%
										}else {
									%>
										<td width="150">
											����֤��5��${apply.marginItem5_S}&nbsp;
										</td>
										<td width="150">
											��������5��${apply.marginItemAssure5_S}&nbsp;
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
											�򷽱�֤��${apply.marginRate_B}%
										</td>
										<td width="150">
											������֤��${apply.marginRate_S}%
										</td>
									</tr>
								<%
									}else {
								%>
									<tr>
										<td width="150">
											�򷽱�֤��${apply.marginRate_B}
										</td>
										<td width="150">
											������֤��${apply.marginRate_S}
										</td>
									</tr>
								<%
									}
								%>
								
								
								<tr>
									<td width="280" align="right" rowspan="2">
										���ձ�֤�� ��Ч���ڣ�<%=apply.getSettleDate()%>
									</td>
									<td width="150">
											�򷽷�ʽ��${settleMarginAlgr_B}
									</td>
									<%
										if (apply.getSettleMarginAlgr_B() == 1) {
									%>
										<td width="150">
											�򷽱�׼��<%=apply.getSettleMarginRate_B()%>%
										</td>
									<%		
										}else {
									%>
										<td width="150">
											�򷽱�׼��<%=apply.getSettleMarginRate_B()%>
										</td>
									<%
										}
									%>
									
									
								</tr>
								<tr>
									<td width="150">
											������ʽ��${settleMarginAlgr_S}
									</td>
									<%
										if (apply.getSettleMarginAlgr_S() == 1) {
									%>
										<td width="150">
											������׼��<%=apply.getSettleMarginRate_S()%>%
										</td>
									<%		
										}else {
									%>
										<td width="150">
											������׼��<%=apply.getSettleMarginRate_S()%>
										</td>
									<%
										}
									%>
								</tr>
								<tr>
									<td width="280" align="right">
										���ջ����㷨��${payoutAlgr}
									</td>
									<%
										if (apply.getPayoutAlgr() == 1) {
									%>
										<td>
											���ջ����׼��<%=apply.getPayoutRate()%>%
										</td>
									<%
										}else {
									%>
										<td>
											���ջ����׼��<%=apply.getPayoutRate()%>
										</td>
									<%
										}
									%>
								</tr>
								
								<tr>
									
									<td width="280" align="right">
										��ǰ״̬��${status}
									</td>
									<td width="150" colspan="2">
										�����ˣ�${entity.proposer}
									</td>
								</tr>
								<c:if test="${entity.status == 2 or entity.status == 3}">
									<tr>
									<td align="right">
										����ˣ�
									</td>
									<td>
										${entity.approver}
									</td>
									</tr>
								</c:if>
								
								<tr>
									<td>
										��ע��
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
										<input type="button" name="query" value="ͨ��" class="anniu_btn"
											onclick="javascript:return ok_onclick();"/>
											
										<input type="button" name="query1" value="��ͨ��" class="anniu_btn"
											onclick="javascript:return fail_onclick();"/>
										</c:if>	
										<input type="button" name="close" value="�ر�" class="anniu_btn"
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
