<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<base target="_self" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<title>
		�����Ѻ�ʽ�
		</title>
		<script type="text/javascript"> 
function window_onload()
{
/*
    if (pledgeForm.status.value == "1") {
    	setReadWrite(pledgeForm.note);
    }else {
    	setReadOnly(pledgeForm.note);
    }*/
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
} 

</script>
	</head>

	<body leftmargin="2" topmargin="0" onload="window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="200" width="380" cellpadding="0" cellspacing="0" class="common" align="center">
			<tr>
				<td>
					<form id="pledgeForm" action="${basePath}/timebargain/check/updatePledgeChe.action"
						method="POST" class="form">
						<input type="hidden" name="billID" value="${apply.billID }"/>
		                <input type="hidden" name="quantity" value="${apply.quantity }"/>
		                <input type="hidden" name="type" value="${apply.type }"/>
						<fieldset class="pickList" >
							<legend class="common">
								<b>�����Ѻ�ʽ�
								</b>
							</legend>
							<table border="0" align="center" cellpadding="0" cellspacing="5" border="5" class="common">
								<tr>
									<td width="80" align="right">�ֵ��ţ�</td>
									<td>
										${apply.billID }
									</td>
									<td width="80" align="right">Ʒ�����ƣ�</td>
									<td>
										${apply.commodityName }
									</td>
								</tr>
								
								<tr>
									<td width="80" align="right">��Ѻ������</td>
									<td  width="80">
										<fmt:formatNumber value="${apply.quantity }" pattern="#,##0.####"/>&nbsp;
									</td>
									<td width="80" align="right">��Ѻ��</td>
									<td width="80">
										<fmt:formatNumber value="${apply.billFund }" pattern="#,##0.##"/>&nbsp;
									</td>
								</tr>
								<tr>
									<td width="80" align="right">�����̴��룺</td>
									<td width="80">
										${apply.firmId }
									</td>
									<td width="80" align="right">��ǰ״̬��</td>
									<td width="80">
										${status }
									</td>
								</tr>
								<tr>
									<td width="80" align="right"><font color="blue">���������</font></td>
									<td>
										<font color="blue">${type }</font>
									</td>
									<td width="80" align="right">�����ˣ�</td>
									<td>
										${entity.proposer }
									</td>
								</tr>
								<%
									//if (apply.getStatus() == 2 || apply.getStatus() == 3) {
								%>
									<tr>
									<td align="right">
										����ˣ�
									</td>
									<td>
										${entity.approver }
									</td>
									</tr>
								<%		
									//}
								%>
								
								<tr>
									<td>
										��ע��
									</td>
								</tr>
								<tr>
									<td colspan="4">
										<textarea name="note" rows="3" cols="95"  style="width:320" class="text">${entity.note }</textarea>
										
									</td>
									
								</tr>
								
								<tr></tr>

								<tr>
						            <td align="center" colspan="4">
						                <c:if test="${entity.status != 1}">
										<input type="button" name="query" value="ͨ��" style="width:60" class="button"
											onclick="javascript:return ok_onclick();"/>
											
										<input type="button" name="query1" value="��ͨ��" style="width:60" class="button"
											onclick="javascript:return fail_onclick();"/>
										</c:if>	
										<input type="button" name="close" value="�ر�" class="anniu_btn"
											onclick="javascript:return cancel_onclick();"/>	
									</td>  		                                                           							
								</tr>
								
							</table>
						</fieldset>
						<input type="hidden" name="firmID" value=""/>
						<input type="hidden" name="id" value=""/>
						<input type="hidden" name="status" value=""/>
						<input type="hidden" name="billFund" value=""/>
					</form>		
				</td>
			</tr>
		</table>

	</body>
</html>
