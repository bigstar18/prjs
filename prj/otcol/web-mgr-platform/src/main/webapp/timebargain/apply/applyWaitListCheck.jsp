<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>

<%@ page import="gnnt.MEBS.timebargain.manage.service.StatQueryManager"  %>
<%@ page import="gnnt.MEBS.timebargain.manage.service.MarketManager"  %>
<%@ page import="gnnt.MEBS.timebargain.manage.model.Apply" %>
<%@ page import="gnnt.MEBS.timebargain.manage.util.SysData" %>
<%@ page import="gnnt.MEBS.timebargain.manage.util.DateUtil"  %>
<%@ page import="java.util.Date"  %>
<%@ page import="java.util.List"  %>
<%@ page import="java.util.Map"  %>
<%@ page import="java.math.*"  %>
<%@ page import="gnnt.MEBS.timebargain.manage.util.*"  %>
<%@ page import="java.text.SimpleDateFormat"%>
<html>
	<head>
		<LINK REL="stylesheet" type="text/css" href="<c:url value="/timebargain/styles/common.css"/>" />
		<c:import url="/timebargain/statquery/customerID_change.jsp"/>
		<c:import url="/timebargain/common/date.jsp"/>
		<script language="VBScript" src="<c:url value="/timebargain/scripts/vbfunction.vbs"/>"></script>
		<title>
		��˲ֵ�
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
  	applyForm.ok.disabled = true;
  	applyForm.fail.disabled = true;  
}

function fail_onclick(){
	applyForm.type.value = "2";//2Ϊ��ͨ��
	applyForm.submit();
	applyForm.ok.disabled = true;
  	applyForm.fail.disabled = true;  
	
} 

</script>
	</head>

	<body leftmargin="2" topmargin="0" 
		onkeypress="keyEnter(event.keyCode);">
		<c:import url="/common/waitbar.jsp" charEncoding="GBK"/>
		<table border="0" height="300" width="480" cellpadding="0" cellspacing="0" class="common" align="center">
			<tr>
				<td>
					<%
											
											StatQueryManager mgr = (StatQueryManager)SysData.getBean("statQueryManager");
											String applyID = request.getParameter("applyID");
											Apply app = mgr.getApplyBillById(applyID);
											
											
											
											String commodityID = app.getCommodityID();
											String firmID_S = "";
											String customerID_S = "";
											String billID = "";
											String quantity = "";
											String applyType ="";
											String status = "";
											String customerID_B = "";
											String price ="";
											String modifier = "";
											if (app != null) {
												
												firmID_S = app.getFirmID_S();
												customerID_S = app.getCustomerID_S();
												billID = app.getBillID();
												
												if (app.getQuantity() != null) {
													quantity = app.getQuantity().toString();
												}
												
												if (app.getApplyType() != null) {
													applyType = app.getApplyType().toString();
												}
												
												if (app.getStatus() != null) {
													status = app.getStatus().toString();
												}
												customerID_B = app.getCustomerID_B();
												
												if (app.getPrice() != null) {
													price = app.getPrice().toString();
												}
												modifier = app.getModifier();
												
												
											}
								%>
								
					<%
						if ("1".equals(applyType) || "2".equals(applyType)) {
					%>
						<html:form action="/timebargain/apply/apply.do?funcflg=applyWaitListSuccessCheck"
						method="POST" styleClass="form" target="HiddFrame2">
						<fieldset class="pickList" >
							<legend class="common">
								<b>��˲ֵ������ֶ�
								</b>
							</legend>
							<table border="0" align="center" cellpadding="0" cellspacing="0" border="0" class="common">
								<tr>
									<td>
										�ֵ��ţ�<%=billID%>
									</td>
								</tr>
								<tr>
									<td width="160">
										��Ʒ���룺<%=commodityID%>
									</td>
								</tr>
								<tr>
									<td>
										���������̶���ID��<%=customerID_S%>
									</td>
								</tr>
								<tr>
									<td>
										�ֵ�������<%=quantity%>
									</td>
								</tr>
								
									<%
										if ("1".equals(applyType)) {
									%>
										<tr>
										<td>
										�������ͣ���������
										</td>
										</tr>
									<%	
										}else if ("2".equals(applyType)) {
									%>
									<tr>
										<td>
										�������ͣ�ǿ�Ƴ���
										</td>
									</tr>
									<%
										}
									%>
								
								<tr>
									
									<td>
										��ע��<html:textarea property="remark2" rows="3" cols="55"  style="width:260" styleClass="text" />
										
									</td>
									<td>&nbsp;&nbsp;</td>
								</tr>
								<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
								<tr>
								
								
						            <td align="center" width="200">
										<html:button property="ok" style="width:60" styleClass="button"
											onclick="javascript:return ok_onclick();">
											ͨ��
										</html:button>
										<html:button property="fail" style="width:60" styleClass="button"
											onclick="javascript:return fail_onclick();">
											��ͨ��
										</html:button>
										<html:button property="close" style="width:60" styleClass="button"
											onclick="javascript:return cancel_onclick();">
											�ر�
										</html:button>
									</td>  		                                                           							
								</tr>
								
							</table>
						</fieldset>
								<html:hidden property="applyID" value="<%=applyID%>"/>
								<html:hidden property="commodityID" value="<%=commodityID%>"/>
								<html:hidden property="firmID_S" value="<%=firmID_S%>"/>
								<html:hidden property="customerID_S" value="<%=customerID_S%>"/>
								<html:hidden property="billID" value="<%=billID%>"/>
								<html:hidden property="quantity" value="<%=quantity%>"/>
								<html:hidden property="applyType" value="<%=applyType%>"/>
								<html:hidden property="status" value="<%=status%>"/>
								<html:hidden property="customerID_B" value="<%=customerID_B%>"/>
								<html:hidden property="price" value="<%=price%>"/>
								<html:hidden property="modifier" value="<%=modifier%>"/>
								<html:hidden property="type"/>
					</html:form>
					<%	
						}
					%>
					
					<%
						if ("3".equals(applyType)) {
					%>
						<html:form action="/timebargain/apply/apply.do?funcflg=applyWaitListSuccessCheck"
						method="POST" styleClass="form" target="HiddFrame2">
						<fieldset class="pickList" >
							<legend class="common">
								<b>��˲ֵ����вֵ�ת��ǰ����
								</b>
							</legend>
							<table border="0" align="center" cellpadding="0" cellspacing="0" border="0" class="common">
								<tr>
									<td>
										�ֵ��ţ�<%=billID%>
									</td>
								</tr>
								<tr>
									<td>
										��Ʒ���룺<%=commodityID%>
									</td>
								</tr>
								<tr>
									<td>
										���������̶���ID��<%=customerID_S%>
									</td>
								</tr>
								<tr>
									<td>
										�ֵ�������<%=quantity%>
									</td>
								</tr>
								
								<tr>
									
									<td>
										��ע��<html:textarea property="remark2" rows="3" cols="55"  style="width:260" styleClass="text" />
										
									</td>
								</tr>
								<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
								<tr>
								
								
						            <td align="center" >
										<html:button property="ok" style="width:60" styleClass="button"
											onclick="javascript:return ok_onclick();">
											ͨ��
										</html:button>
										<html:button property="fail" style="width:60" styleClass="button"
											onclick="javascript:return fail_onclick();">
											��ͨ��
										</html:button>
										<html:button property="close" style="width:60" styleClass="button"
											onclick="javascript:return cancel_onclick();">
											�ر�
										</html:button>
									</td>  		                                                           							
								</tr>
								
							</table>
						</fieldset>
								<html:hidden property="applyID" value="<%=applyID%>"/>
								<html:hidden property="commodityID" value="<%=commodityID%>"/>
								<html:hidden property="firmID_S" value="<%=firmID_S%>"/>
								<html:hidden property="customerID_S" value="<%=customerID_S%>"/>
								<html:hidden property="billID" value="<%=billID%>"/>
								<html:hidden property="quantity" value="<%=quantity%>"/>
								<html:hidden property="applyType" value="<%=applyType%>"/>
								<html:hidden property="status" value="<%=status%>"/>
								<html:hidden property="customerID_B" value="<%=customerID_B%>"/>
								<html:hidden property="price" value="<%=price%>"/>
								<html:hidden property="modifier" value="<%=modifier%>"/>
								<html:hidden property="type"/>
					</html:form>
					<%	
						}
					%>
					
					<%
						if ("4".equals(applyType)) {
					%>
						<html:form action="/timebargain/apply/apply.do?funcflg=applyWaitListSuccessCheck"
						method="POST" styleClass="form" target="HiddFrame2">
						<fieldset class="pickList" >
							<legend class="common">
								<b>��˲ֵ��ȴ�����
								</b>
							</legend>
							<table border="0" align="center" cellpadding="0" cellspacing="0" border="0" class="common">
								<tr>
									<td>
										�ֵ��ţ�<%=billID%>
									</td>
								</tr>
								<tr>
									<td>
										��Ʒ���룺<%=commodityID%>
									</td>
								</tr>
								<tr>
									<td>
										���������̴��룺<%=firmID_S%>
									</td>
								</tr>
								<tr>
									<td>
										�ֵ�������<%=quantity%>
									</td>
								</tr>
								
								<tr>
									
									<td>
										��ע��<html:textarea property="remark2" rows="3" cols="55"  style="width:260" styleClass="text" />
										
									</td>
								</tr>
								<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
								<tr>
								
								
						            <td align="center" >
										<html:button property="ok" style="width:60" styleClass="button"
											onclick="javascript:return ok_onclick();">
											ͨ��
										</html:button>
										<html:button property="fail" style="width:60" styleClass="button"
											onclick="javascript:return fail_onclick();">
											��ͨ��
										</html:button>
										<html:button property="close" style="width:60" styleClass="button"
											onclick="javascript:return cancel_onclick();">
											�ر�
										</html:button>
									</td>  		                                                           							
								</tr>
								
							</table>
						</fieldset>
								<html:hidden property="applyID" value="<%=applyID%>"/>
								<html:hidden property="commodityID" value="<%=commodityID%>"/>
								<html:hidden property="firmID_S" value="<%=firmID_S%>"/>
								<html:hidden property="customerID_S" value="<%=customerID_S%>"/>
								<html:hidden property="billID" value="<%=billID%>"/>
								<html:hidden property="quantity" value="<%=quantity%>"/>
								<html:hidden property="applyType" value="<%=applyType%>"/>
								<html:hidden property="status" value="<%=status%>"/>
								<html:hidden property="customerID_B" value="<%=customerID_B%>"/>
								<html:hidden property="price" value="<%=price%>"/>
								<html:hidden property="modifier" value="<%=modifier%>"/>
								<html:hidden property="type"/>
					</html:form>
					<%
						}
					%>
					
					<%
						if ("5".equals(applyType)) {
					%>
						<html:form action="/timebargain/apply/apply.do?funcflg=applyWaitListSuccessCheck"
						method="POST" styleClass="form" target="HiddFrame2">
						<fieldset class="pickList" >
							<legend class="common">
								<b>��˲ֵ��ֶ�
								</b>
							</legend>
							<table border="0" align="center" cellpadding="0" cellspacing="0" border="0" class="common">
								<tr>
									<td>
										�ֵ��ţ�<%=billID%>
									</td>
								</tr>
								<tr>
									<td>
										��Ʒ���룺<%=commodityID%>
									</td>
								</tr>
								<tr>
									<td>
										���������̶���ID��<%=customerID_S%>
									</td>
								</tr>
								<tr>
									<td>
										�ֵ�������<%=quantity%>
									</td>
								</tr>
								
								<tr>
									
									<td>
										��ע��<html:textarea property="remark2" rows="3" cols="55"  style="width:260" styleClass="text" />
										
									</td>
								</tr>
								<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
								<tr>
								
								
						            <td align="center" >
										<html:button property="ok" style="width:60" styleClass="button"
											onclick="javascript:return ok_onclick();">
											ͨ��
										</html:button>
										<html:button property="fail" style="width:60" styleClass="button"
											onclick="javascript:return fail_onclick();">
											��ͨ��
										</html:button>
										<html:button property="close" style="width:60" styleClass="button"
											onclick="javascript:return cancel_onclick();">
											�ر�
										</html:button>
									</td>  		                                                           							
								</tr>
								
							</table>
						</fieldset>
								<html:hidden property="applyID" value="<%=applyID%>"/>
								<html:hidden property="commodityID" value="<%=commodityID%>"/>
								<html:hidden property="firmID_S" value="<%=firmID_S%>"/>
								<html:hidden property="customerID_S" value="<%=customerID_S%>"/>
								<html:hidden property="billID" value="<%=billID%>"/>
								<html:hidden property="quantity" value="<%=quantity%>"/>
								<html:hidden property="applyType" value="<%=applyType%>"/>
								<html:hidden property="status" value="<%=status%>"/>
								<html:hidden property="customerID_B" value="<%=customerID_B%>"/>
								<html:hidden property="price" value="<%=price%>"/>
								<html:hidden property="modifier" value="<%=modifier%>"/>
								<html:hidden property="type"/>
					</html:form>
					<%	
						}
					%>
					
					<%
						if ("6".equals(applyType)) {
					%>
						<html:form action="/timebargain/apply/apply.do?funcflg=applyWaitListSuccessCheck"
						method="POST" styleClass="form" target="HiddFrame2">
						<fieldset class="pickList" >
							<legend class="common">
								<b>��˲ֵ���ǰ����
								</b>
							</legend>
							<table border="0" align="center" cellpadding="0" cellspacing="0" border="0" class="common">
								<tr>
									<td>
										�ֵ��ţ�<%=billID%>
									</td>
								</tr>
								<tr>
									<td>
										��Ʒ���룺<%=commodityID%>
									</td>
								</tr>
								<tr>
									<td>
										��ǰ���ռ۸�<%=price%>
									</td>
								</tr>
								<tr>
									<td>
										�ֵ�������<%=quantity%>
									</td>
								</tr>
								<tr>
									<td>
										���������̶���ID��<%=customerID_S%>
									</td>
								</tr>
								<tr>
									<td>
										�򷽽����̶���ID��<%=customerID_B%>
									</td>
								</tr>
								
								<tr>
									
									<td>
										��ע��<html:textarea property="remark2" rows="3" cols="55"  style="width:260" styleClass="text" />
										
									</td>
								</tr>
								<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
								<tr>
								
								
						            <td align="center" >
										<html:button property="ok" style="width:60" styleClass="button"
											onclick="javascript:return ok_onclick();">
											ͨ��
										</html:button>
										<html:button property="fail" style="width:60" styleClass="button"
											onclick="javascript:return fail_onclick();">
											��ͨ��
										</html:button>
										<html:button property="close" style="width:60" styleClass="button"
											onclick="javascript:return cancel_onclick();">
											�ر�
										</html:button>
									</td>  		                                                           							
								</tr>
								
							</table>
						</fieldset>
								<html:hidden property="applyID" value="<%=applyID%>"/>
								<html:hidden property="commodityID" value="<%=commodityID%>"/>
								<html:hidden property="firmID_S" value="<%=firmID_S%>"/>
								<html:hidden property="customerID_S" value="<%=customerID_S%>"/>
								<html:hidden property="billID" value="<%=billID%>"/>
								<html:hidden property="quantity" value="<%=quantity%>"/>
								<html:hidden property="applyType" value="<%=applyType%>"/>
								<html:hidden property="status" value="<%=status%>"/>
								<html:hidden property="customerID_B" value="<%=customerID_B%>"/>
								<html:hidden property="price" value="<%=price%>"/>
								<html:hidden property="modifier" value="<%=modifier%>"/>
								<html:hidden property="type"/>
					</html:form>
					<%
						}
					%>
					
					<%
						if ("7".equals(applyType)) {
					%>
						<html:form action="/timebargain/apply/apply.do?funcflg=applyWaitListSuccessCheck"
						method="POST" styleClass="form" target="HiddFrame2">
						<fieldset class="pickList" >
							<legend class="common">
								<b>��˲ֵ������ȴ�����
								</b>
							</legend>
							<table border="0" align="center" cellpadding="0" cellspacing="0" border="0" class="common">
								<tr>
									<td>
										�ֵ��ţ�<%=billID%>
									</td>
								</tr>
								<tr>
									<td>
										��Ʒ���룺<%=commodityID%>
									</td>
								</tr>
								<tr>
									<td>
										���������̴��룺<%=firmID_S%>
									</td>
								</tr>
								<tr>
									<td>
										�ֵ�������<%=quantity%>
									</td>
								</tr>
								
								<tr>
									
									<td>
										��ע��<html:textarea property="remark2" rows="3" cols="55"  style="width:260" styleClass="text" />
										
									</td>
								</tr>
								<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
								<tr>
								
								
						            <td align="left" >
										<html:button property="ok" style="width:60" styleClass="button"
											onclick="javascript:return ok_onclick();">
											ͨ��
										</html:button>
										<html:button  property="fail" style="width:60" styleClass="button"
											onclick="javascript:return fail_onclick();">
											��ͨ��
										</html:button>
										<html:button property="close" style="width:60" styleClass="button"
											onclick="javascript:return cancel_onclick();">
											�ر�
										</html:button>
									</td>  		                                                           							
								</tr>
								
							</table>
						</fieldset>
								<html:hidden property="applyID" value="<%=applyID%>"/>
								<html:hidden property="commodityID" value="<%=commodityID%>"/>
								<html:hidden property="firmID_S" value="<%=firmID_S%>"/>
								<html:hidden property="customerID_S" value="<%=customerID_S%>"/>
								<html:hidden property="billID" value="<%=billID%>"/>
								<html:hidden property="quantity" value="<%=quantity%>"/>
								<html:hidden property="applyType" value="<%=applyType%>"/>
								<html:hidden property="status" value="<%=status%>"/>
								<html:hidden property="customerID_B" value="<%=customerID_B%>"/>
								<html:hidden property="price" value="<%=price%>"/>
								<html:hidden property="modifier" value="<%=modifier%>"/>
								<html:hidden property="type"/>
					</html:form>
					<%
						}
					%>
					
					
					<%
						if ("8".equals(applyType)) {
					%>
						<html:form action="/timebargain/apply/apply.do?funcflg=applyWaitListSuccessCheck"
						method="POST" styleClass="form" target="HiddFrame2">
						<fieldset class="pickList" >
							<legend class="common">
								<b>��˲ֵ����ڽ���
								</b>
							</legend>
							<table border="0" align="center" cellpadding="0" cellspacing="0" border="0" class="common">
								<tr>
									<td>
										�ֵ��ţ�<%=billID%>
									</td>
								</tr>
								<tr>
									<td>
										��Ʒ���룺<%=commodityID%>
									</td>
								</tr>
								<tr>
									<td>
										���������̴��룺<%=firmID_S%>
									</td>
								</tr>
								<tr>
									<td>
										�ֵ�������<%=quantity%>
									</td>
								</tr>
								
								<tr>
									
									<td>
										��ע��<html:textarea property="remark2" rows="3" cols="55"  style="width:260" styleClass="text" />
										
									</td>
								</tr>
								<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
								<tr>
								
								
						            <td align="center" >
										<html:button property="ok" style="width:60" styleClass="button"
											onclick="javascript:return ok_onclick();">
											ͨ��
										</html:button>
										<html:button property="fail" style="width:60" styleClass="button"
											onclick="javascript:return fail_onclick();">
											��ͨ��
										</html:button>
										<html:button property="close" style="width:60" styleClass="button"
											onclick="javascript:return cancel_onclick();">
											�ر�
										</html:button>
									</td>  		                                                           							
								</tr>
								
							</table>
						</fieldset>
								<html:hidden property="applyID" value="<%=applyID%>"/>
								<html:hidden property="commodityID" value="<%=commodityID%>"/>
								<html:hidden property="firmID_S" value="<%=firmID_S%>"/>
								<html:hidden property="customerID_S" value="<%=customerID_S%>"/>
								<html:hidden property="billID" value="<%=billID%>"/>
								<html:hidden property="quantity" value="<%=quantity%>"/>
								<html:hidden property="applyType" value="<%=applyType%>"/>
								<html:hidden property="status" value="<%=status%>"/>
								<html:hidden property="customerID_B" value="<%=customerID_B%>"/>
								<html:hidden property="price" value="<%=price%>"/>
								<html:hidden property="modifier" value="<%=modifier%>"/>
								<html:hidden property="type"/>
					</html:form>
					<%	
						}
					%>
					
					<%
						if ("9".equals(applyType)) {
					%>
						<html:form action="/timebargain/apply/apply.do?funcflg=applyWaitListSuccessCheck"
						method="POST" styleClass="form" target="HiddFrame2">
						<fieldset class="pickList" >
							<legend class="common">
								<b>��˲ֵ��������ڽ���
								</b>
							</legend>
							<table border="0" align="center" cellpadding="0" cellspacing="0" border="0" class="common">
								<tr>
									<td>
										�ֵ��ţ�<%=billID%>
									</td>
								</tr>
								<tr>
									<td width="160">
										��Ʒ���룺<%=commodityID%>
									</td>
								</tr>
								<tr>
									<td>
										���������̴��룺<%=firmID_S%>
									</td>
								</tr>
								<tr>
									<td>
										�ֵ�������<%=quantity%>
									</td>
								</tr>
								
								<tr>
									
									<td>
										��ע��<html:textarea property="remark2" rows="3" cols="55"  style="width:260" styleClass="text" />
										
									</td>
									<td>&nbsp;&nbsp;</td>
								</tr>
								<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
								<tr>
								
								
						            <td align="center" width="200">
										<html:button property="query" style="width:60" styleClass="button"
											onclick="javascript:return ok_onclick();">
											ͨ��
										</html:button>
										<html:button property="query" style="width:60" styleClass="button"
											onclick="javascript:return fail_onclick();">
											��ͨ��
										</html:button>
										<html:button property="close" style="width:60" styleClass="button"
											onclick="javascript:return cancel_onclick();">
											�ر�
										</html:button>
									</td>  		                                                           							
								</tr>
								
							</table>
						</fieldset>
								<html:hidden property="applyID" value="<%=applyID%>"/>
								<html:hidden property="commodityID" value="<%=commodityID%>"/>
								<html:hidden property="firmID_S" value="<%=firmID_S%>"/>
								<html:hidden property="customerID_S" value="<%=customerID_S%>"/>
								<html:hidden property="billID" value="<%=billID%>"/>
								<html:hidden property="quantity" value="<%=quantity%>"/>
								<html:hidden property="applyType" value="<%=applyType%>"/>
								<html:hidden property="status" value="<%=status%>"/>
								<html:hidden property="customerID_B" value="<%=customerID_B%>"/>
								<html:hidden property="price" value="<%=price%>"/>
								<html:hidden property="modifier" value="<%=modifier%>"/>
								<html:hidden property="type"/>
					</html:form>
					<%	
						}
					%>
				</td>
			</tr>
		</table>

		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>
