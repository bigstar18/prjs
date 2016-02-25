<%@ include file="../../common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>

<%@ page import="java.text.SimpleDateFormat"%>

<%@ page import="gnnt.MEBS.common.manage.service.*"%>
<%@ page import="gnnt.MEBS.common.manage.model.*"%>
<%@ page import="gnnt.MEBS.finance.base.util.SysData"%>
<html>
	<head>
		<title>
		�����鿴�����ʽ�
		</title>
		<script type="text/javascript"> 
function window_onload()
{
    highlightFormElements();
    if (virtualFundForm.status.value == "1") {
    	setReadWrite(virtualFundForm.note);
    }else {
    	setReadOnly(virtualFundForm.note);
    }
}
//query_onclick

function cancel_onclick()
{
   window.close();
}

function relase()
{
	    virtualFundForm.query.disabled = false;
	  	virtualFundForm.query1.disabled = false;
}

function ok_onclick(){
	if (confirm("��ȷ��Ҫ�ύ��")) {
		virtualFundForm.status.value = "2";//2Ϊͨ��
		virtualFundForm.submit();
	  	virtualFundForm.query.disabled = true;
	  	virtualFundForm.query1.disabled = true;  
	}
	
	//window.close();
}

function fail_onclick(){
	if (confirm("��ȷ��Ҫ�ύ��")) {
		virtualFundForm.status.value = "3";//3Ϊ��ͨ��
		virtualFundForm.submit();
	  	virtualFundForm.query.disabled = true;
	  	virtualFundForm.query1.disabled = true;
	}
	
	//window.close();
	//document.location.href = "<c:url value="/apply/apply.do?method=applyWaitListCheckFail&applyID="/>" + virtualFundForm.applyID.value;
	
} 

</script>
	</head>

	<body leftmargin="2" topmargin="0" onload="window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="200" width="380" cellpadding="0" cellspacing="0" class="common" align="center">
			<tr>
				<td>
					<%
											ApplyManage mgr = (ApplyManage) SysData.getBean("applyManager");
											Apply_T_VirtualMoney apply=new Apply_T_VirtualMoney();
											String id = request.getParameter("id");
											long relID = 0;
											if (id != null && !"".equals(id)) {
												relID = Long.parseLong(id);
												apply.setId(relID);
											}
											apply = (Apply_T_VirtualMoney)mgr.getApplyById(apply, false);
											
											String firmId = "";
											String money = "";
											String applytype = "";
											String status = "";
											String proposer = "";
											String approver = "";
											String note = "";
											
											if (apply != null) {
												firmId = apply.getFirmId();
												money = apply.getMoney()+"";
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
												
												proposer = apply.getProposer();
												approver = apply.getApprover();
												note = apply.getNote();
												if(note==null)
												{
												   note="";
												}
											}
								%>
					<form action="<%=basePath%>/timebargain/virtualFundCheck.spr"
						method="POST" class="form" target="HiddFrame2" name="virtualFundForm">
						<fieldset class="pickList" >
							<legend class="common">
								<b>�����鿴�����ʽ�
								</b>
							</legend>
							<table border="0" align="center" cellpadding="5" cellspacing="5" border="0" class="common">
								<tr>
									<td width="100" align="right">�����̴��룺</td>
									<td width="80">
										<%=firmId%>
									</td>
									<td width="80" align="right">�����ʽ�</td>
									<td width="80">
										<fmt:formatNumber value="<%=money%>" pattern="#,##0.00"/>&nbsp;</td><td class="td_reportMdHead">
									</td>
								</tr>
								<tr>
									<td width="80" align="right">��ǰ״̬��</td>
									<td width="80">
										<%=status%>
									</td>
									<td width="80" align="right">�����ˣ�</td>
									<td width="80">
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
									<td >��ע��</td>
								</tr>
								<tr>
									
									<td colspan="4">
										<textarea name="note" rows="4" cols="95"  style="width:320" class="text" ><%=note %></textarea>
									</td>
								</tr>
								<tr></tr>
								<%
									if ("1".equals(apply.getStatus()+"")) {
								%>
									<tr>
								
						            <td align="center" colspan="4">
										<input type="button" name="query" style="width:60" class="button" value="ͨ��"
											onclick="javascript:return ok_onclick();"/>
										<input type="button" name="query1" style="width:60" class="button" value="��ͨ��"
											onclick="javascript:return fail_onclick();"/>
										<input type="button" name="close" style="width:60" class="button" value="�ر�"
											onclick="javascript:return cancel_onclick();"/>
									</td>  		                                                           							
								</tr>
								<%		
									}else {
								%>
									<tr>
									 
									<td colspan="4" align="center" >
										<input type="button" name="close" style="width:60" class="button" value="�ر�"
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
					</form>
					
					
					
				</td>
			</tr>
		</table>

		<%@ include file="../../common/messages.jsp"%>
	</body>
</html>

