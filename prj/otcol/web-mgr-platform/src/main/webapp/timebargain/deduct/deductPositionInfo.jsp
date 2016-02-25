<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page import="gnnt.MEBS.timebargain.manage.util.Arith" %>
<%@ page pageEncoding="GBK"%>


<%@ page import="gnnt.MEBS.timebargain.manage.model.Deduct" %>


	<%
				System.out.println("111111111111");
				
											Deduct deduct = (Deduct)request.getAttribute("dd");
											
											String deductedQty = (String)request.getAttribute("deductedQty");
											String counteractedQty = (String)request.getAttribute("counteractedQty");
											
											String deductDate = "";
											String commodityID = "";
											String deductStatus = "";
											String deductPrice = "";
											String loserBSFlag ="";
											String loserMode = "";
											String lossRate = "";
											String selfCounteract = "";
											String profitLvl1 = "";
											String profitLvl2 = "";
											String profitLvl3 = "";
											String profitLvl4 = "";
											String profitLvl5 = "";
											String execTime = "";
											String alert = "";
											
											if (deduct != null) {
												if (deduct.getDeductDate() != null) {
													deductDate = deduct.getDeductDate().toString().split(" ")[0];
												}
												commodityID = deduct.getCommodityID();
												if ("N".equals(deduct.getDeductStatus()) || "P".equals(deduct.getDeductStatus())) {
													deductStatus = "δ����";
												}
												
												if ("Y".equals(deduct.getDeductStatus())) {
													deductStatus = "������";
												}
												
												if (deduct.getDeductPrice() != null) {
													deductPrice = deduct.getDeductPrice().toString();
												}
												
												if (deduct.getLoserBSFlag() != null) {
													if ("1".equals(deduct.getLoserBSFlag().toString())) {
														loserBSFlag = "��";
													}else {
														loserBSFlag = "����";
													}
												}
												
												if (deduct.getLoserMode() != null) {
													if ("1".equals(deduct.getLoserMode().toString())) {
														loserMode = "���п����Ա";
													}else if ("2".equals(deduct.getLoserMode().toString())) {
														loserMode = "�����ұ�����Ա";
													}
												}
												
												if (deduct.getLossRate() != null) {
													Double relLossRate = Arith.mul(deduct.getLossRate(),new Double(100));
													lossRate = relLossRate.toString();
												}
												
												if (deduct.getSelfCounteract() != null) {
													if ("0".equals(deduct.getSelfCounteract().toString())) {
														selfCounteract = "��";
													}
													if ("1".equals(deduct.getSelfCounteract().toString())) {
														selfCounteract = "���л�Ա˫�򶩻��Գ�";
													}
													if ("2".equals(deduct.getSelfCounteract().toString())) {
														selfCounteract = "���������걨�Գ�";
													}
												}
												
												if (deduct.getProfitLvl1() != null) {
													Double relProfitLvl1 = Arith.mul(deduct.getProfitLvl1(),new Double(100));
													profitLvl1 = relProfitLvl1.toString();
												}
												if (deduct.getProfitLvl2() != null) {
													Double relProfitLvl2 = Arith.mul(deduct.getProfitLvl2(),new Double(100));
													profitLvl2 = relProfitLvl2.toString();
												}
												if (deduct.getProfitLvl3() != null) {
													Double relProfitLvl3 = Arith.mul(deduct.getProfitLvl3(),new Double(100));
													profitLvl3 = relProfitLvl3.toString();
												}
												if (deduct.getProfitLvl4() != null) {
													Double relProfitLvl4 = Arith.mul(deduct.getProfitLvl4(),new Double(100));
													profitLvl4 = relProfitLvl4.toString();
												}
												if (deduct.getProfitLvl5() != null) {
													Double relProfitLvl5 = Arith.mul(deduct.getProfitLvl5(),new Double(100));
													profitLvl5 = relProfitLvl5.toString();
												}
												
												if (deduct.getExecTime() != null) {
													execTime = deduct.getExecTime().toString();
												}
												if (deduct.getAlert() == null) {
													alert = "";
												}else {
													alert = deduct.getAlert();
												}
												
											}
											
								%>	
	
<html>

	<head>
		<LINK REL="stylesheet" type="text/css" href="<c:url value="/timebargain/styles/common.css"/>" />
		<c:import url="/timebargain/statquery/customerID_change.jsp"/>
		<c:import url="/timebargain/common/date.jsp"/>
		<script language="VBScript" src="<c:url value="/timebargain/scripts/vbfunction.vbs"/>"></script>
		<title>
		</title>
		<script type="text/javascript"> 
function window_onload()
{
    highlightFormElements();
}
//query_onclick

function cancel_onclick()
{
   window.close();
}

function ok_onclick(){
	applyForm.type.value = "1";//1Ϊͨ��
	applyForm.submit();
  	applyForm.query.disabled = true;  
	window.close();
}

function fail_onclick(){
	applyForm.type.value = "2";//2Ϊ��ͨ��
	applyForm.submit();
  	applyForm.query.disabled = true;  
	window.close();
	//document.location.href = "<c:url value="/apply/apply.do?method=applyWaitListCheckFail&applyID="/>" + applyForm.applyID.value;
	
} 

</script>
	</head>

	<body leftmargin="2" topmargin="0" 
		onkeypress="keyEnter(event.keyCode);">
		<c:import url="/common/waitbar.jsp" charEncoding="GBK"/>
		
		<table border="0" height="450" width="600" cellpadding="0" cellspacing="0" class="common" align="center">
			<tr>
				<td>
						<html:form action="/timebargain/deduct/deduct.do?funcflg=saveUpdate"
						method="POST" styleClass="form" target="mainFrame">
						<fieldset class="pickList" >
							<legend class="common">
								<b>ǿ�Ƽ�������
								</b>
							</legend>
							<table border="0" width="600" align="center" cellpadding="5" cellspacing="5"
								class="common">
								<tr>
									<td align="right" width="100">
											ǿ�����ڣ�
									</td>
									<td width="100">
										<%=deductDate%>
									</td>
									<td align="right">
											��Ʒ���룺
									</td>
									<td>
										<%=commodityID%>
									</td>
											                                                           							
								</tr>
								
								<tr>
									<td align="right">
											ӯ���ּ�����1��
									</td>
									<td>
										<%=profitLvl1%><span>%</span>
									</td>
									
									<td align="right">
											ǿ���۸�
									</td>
									<td>
										<%=deductPrice%>
									</td>
								</tr>
								
								<tr>
									<td align="right">
											ӯ���ּ�����2��
									</td>
									<td>
										<%=profitLvl2%><span>%</span>
									</td>
									
									<td align="right">
											����������־��
									</td>
									<td>
										<%=loserBSFlag%>
									</td>
								</tr>
								<tr>
									<td align="right">
											ӯ���ּ�����3��
									</td>
									<td>
										<%=profitLvl3%><span>%</span>
									</td>
									
									<td align="right">
											����ǿ��ģʽ��
									</td>
									<td>
										<%=loserMode%>
									</td>
								</tr>
								<tr>
									<td align="right">
											ӯ���ּ�����4��
									</td>
									<td>
										<%=profitLvl4%><span>%</span>
									</td>
									
									<td align="right">
											�Ƿ�Գ壺
									</td>
									<td>
										<%=selfCounteract%>
									</td>
								</tr>
								<tr>
									<td align="right">
											ӯ���ּ�����5��
									</td>
									<td>
										<%=profitLvl5%><span>%</span>
									</td>
									<td align="right">
											���������
									</td>
									<td>
										<%=lossRate%><span>%</span>
									</td>
								</tr>
								<tr>
									<td align="right">
											�ܼ���ǿ��������
									</td>
									<td>
										<%=deductedQty%>
									</td>
									<td align="right">
											�ܼ��ѶԳ�������
									</td>
									<td>
										<%=counteractedQty%>
									</td>
								</tr>
								
								<tr>
									
									<td align="right">
											ǿ��״̬��
									</td>
									<td>
										<%=deductStatus%>
									</td>
									<td align="right">
											ִ��ʱ�䣺
									</td>
									<td>
										<%=execTime%>
									</td>
								</tr>
								
								<tr>
									<td align="right">
											���棺
									</td>
									<td>
										<%=alert%>
									</td>
								</tr>
								<tr>
								
									<td colspan="4" align="center">
										<html:button property="cancel" styleClass="button"
											onclick="javascript:return cancel_onclick();">
											�ر�
										</html:button>
										
									</td>
								</tr>
							</table>
						</fieldset>
						<html:hidden property="deductID"/>
					</html:form>
					
					
				</td>
			</tr>
			
		</table>

		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>
