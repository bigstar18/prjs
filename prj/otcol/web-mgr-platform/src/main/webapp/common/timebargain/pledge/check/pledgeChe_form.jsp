<%@ include file="../../common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>



<%@ page import="gnnt.MEBS.common.manage.service.*"%>
<%@ page import="gnnt.MEBS.common.manage.model.*"%>
<%@ page import="gnnt.MEBS.finance.base.util.SysData"%>
<html>
	<head>
		<title>
		�����Ѻ�ʽ�
		</title>
		<script type="text/javascript"> 
function window_onload()
{
    highlightFormElements();
    if (pledgeForm.status.value == "1") {
    	setReadWrite(pledgeForm.note);
    }else {
    	setReadOnly(pledgeForm.note);
    }
}
//query_onclick

function cancel_onclick()
{
   window.close();
}

function relase()
{
	    pledgeForm.query.disabled = false;
	  	pledgeForm.query1.disabled = false;
}

function ok_onclick(){
	if (confirm("��ȷ��Ҫ�ύ��")) {
		pledgeForm.status.value = "2";//2Ϊͨ��
		pledgeForm.submit();
  	pledgeForm.query.disabled = true;  
  	pledgeForm.query1.disabled = true;
	}
	
	//window.close();
}

function fail_onclick(){
	if (confirm("��ȷ��Ҫ�ύ��")) {
		  pledgeForm.status.value = "3";//3Ϊ��ͨ��
		  pledgeForm.submit();
	  	pledgeForm.query.disabled = true;
	  	pledgeForm.query1.disabled = true;
	}
	  
	//window.close();
	//document.location.href = "<c:url value="/apply/apply.do?method=applyWaitListCheckFail&applyID="/>" + pledgeForm.applyID.value;
	
} 

</script>
	</head>

	<body leftmargin="2" topmargin="0" onload="window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="200" width="380" cellpadding="0" cellspacing="0" class="common" align="center">
			<tr>
				<td>
					<%
											//ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(application);
											ApplyManage mgr = (ApplyManage) SysData.getBean("applyManager");
											Apply_T_PledgeMoney apply=new Apply_T_PledgeMoney();
											String id = request.getParameter("id");
											long relID = 0;
											if (id != null && !"".equals(id)) {
												relID = Long.parseLong(id);
												apply.setId(relID);
											}
											apply = (Apply_T_PledgeMoney)mgr.getApplyById(apply, false);
											
											PledgeManage pmgr = (PledgeManage) SysData.getBean("pledgeManager");
											if (apply.getType() == 2) {
												apply = pmgr.getApply_T_PledgeMoneyByBillID(apply);
											}
											
											String firmId = "";
											String billID = "";
											String billFund = "";
											String commodityName ="";
											String quantity = "";
											String applytype = "";
											String status = "";
											String proposer ="";
											String type = "";
											String note = "";
											String approver = "";
											
											
											if (apply != null) {
												firmId = apply.getFirmId();
												billID = apply.getBillID();
												if (apply.getBillFund() != 0) {
													billFund = apply.getBillFund()+"";
												}
												commodityName = apply.getCommodityName();
												if (apply.getQuantity() != 0) {
													quantity = apply.getQuantity()+"";
												}
												
												if ("1".equals(apply.getApplyType()+"")) {
													applytype = "�����ʽ�";
												}else {
													applytype = "��Ѻ";
												}
												
												if ("1".equals(apply.getStatus()+"")) {
													status = "�����";
												}else if ("2".equals(apply.getStatus()+"")) {
													status = "���ͨ��";
												}else if ("3".equals(apply.getStatus()+"")) {
													status = "��˲�ͨ��";
												}
												
												if (apply.getType() == 1) {
													type = "���";
												}else if (apply.getType() == 2) {
													type = "ɾ��";
												}
												proposer = apply.getProposer();
												note = apply.getNote();
												if(note==null)
												note="";
												approver = apply.getApprover();
											}
								%>
					<form id="pledgeForm" action="<%=basePath%>/timebargain/pledgeCheck.spr"
						method="POST" class="form" target="HiddFrame2">
						<input type="hidden" name="billID" value="<%=billID%>"/>
		                <input type="hidden" name="quantity" value="<%=quantity%>"/>
		                <input type="hidden" name="type" value="<%=apply.getType()%>"/>
						<fieldset class="pickList" >
							<legend class="common">
								<b>�����Ѻ�ʽ�
								</b>
							</legend>
							<table border="0" align="center" cellpadding="0" cellspacing="5" border="5" class="common">
								<tr>
									<td width="80" align="right">�ֵ��ţ�</td>
									<td>
										<%=billID%>
									</td>
									<td width="80" align="right">Ʒ�����ƣ�</td>
									<td>
										<%=commodityName%>
									</td>
								</tr>
								
								<tr>
									<td width="80" align="right">��Ѻ������</td>
									<td  width="80">
										<fmt:formatNumber value="<%=quantity%>" pattern="#,##0.####"/>&nbsp;
									</td>
									<td width="80" align="right">��Ѻ��</td>
									<td width="80">
										<fmt:formatNumber value="<%=billFund%>" pattern="#,##0.##"/>&nbsp;
									</td>
								</tr>
								<tr>
									<td width="80" align="right">�����̴��룺</td>
									<td width="80">
										<%=firmId%>
									</td>
									<td width="80" align="right">��ǰ״̬��</td>
									<td width="80">
										<%=status%>
									</td>
								</tr>
								<tr>
									<td width="80" align="right"><font color="blue">���������</font></td>
									<td>
										<font color="blue"><%=type%></font>
									</td>
									<td width="80" align="right">�����ˣ�</td>
									<td>
										<%=proposer%>
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
									<td colspan="4">
										<textarea name="note" rows="3" cols="95"  style="width:320" class="text"><%=note%></textarea>
										
									</td>
									
								</tr>
								
								<tr></tr>
								<%
									if ("1".equals(apply.getStatus()+"")) {
								%>
									<tr>
								
									
						            <td align="center" colspan="4">
										<input type="button" name="query" value="ͨ��" style="width:60" class="button"
											onclick="javascript:return ok_onclick();"/>
											
										<input type="button" name="query1" value="��ͨ��" style="width:60" class="button"
											onclick="javascript:return fail_onclick();"/>
											
										<input type="button" name="close" value="�ر�" style="width:60" class="button"
											onclick="javascript:return cancel_onclick();"/>
											
									</td>  		                                                           							
								</tr>
								<%		
									}else {
								%>
									<tr>
									<td align="center" colspan="4">
										<input type="button" name="close" value="�ر�" style="width:60" class="button"
											onclick="javascript:return cancel_onclick();"/>
											
									</td>  		                                                           							
								</tr>
								<%	
									}
								%>
								
								
								
							</table>
						</fieldset>
						<input type="hidden" name="billID" value="<%=billID%>"/>
						<input type="hidden" name="firmID" value="<%=firmId%>"/>
						<input type="hidden" name="id" value="<%=id%>"/>
						<input type="hidden" name="status" value="<%=apply.getStatus()+""%>"/>
						<input type="hidden" name="billFund" value="<%=billFund%>"/>
						<input type="hidden" name="type" value="<%=apply.getType()+""%>"/>
					</form>
					
					
					
				</td>
			</tr>
		</table>

		<%@ include file="../../common/messages.jsp"%>
	</body>
</html>
