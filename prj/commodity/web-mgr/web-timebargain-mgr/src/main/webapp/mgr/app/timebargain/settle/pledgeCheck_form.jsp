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
		pledgeForm.status.value = "1";//1Ϊͨ��
		pledgeForm.submit();
	  	pledgeForm.query.disabled = true;  
	  	pledgeForm.query1.disabled = true;
	}
	
	//window.close();
}

function fail_onclick(){
	if (confirm("��ȷ��Ҫ�ύ��")) {
		pledgeForm.status.value = "2";//2Ϊ��ͨ��
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
					<form id="pledgeForm" action="${basePath}/timebargain/pledge/pledgeCheck.action"
						method="POST" class="form">
						<input type="hidden" id="pledgeID" name="pledgeID" value="${entity.pledgeID}"/>
						<input type="hidden" id="status" name="status" value="${entity.status}"/>
						<input type="hidden" id="type" name="type" value="${entity.type}"/>
						<input type="hidden" id="billFund" name="billFund" value="${entity.billFund}"/>
						<fieldset class="pickList" >
							<legend class="common">
								<b>��Ѻ�ʽ���Ϣ</b>
							</legend>
							<table border="0" align="center" cellpadding="0" cellspacing="5" border="5" class="common">
								<tr>
									<td width="80" align="right">�������ͣ�</td>
									<td>
										<c:forEach items="${Pledge_typeMap}" var="result">
											<c:if test="${entity.type==result.key }">${result.value }</c:if>
										</c:forEach>
									</td>
									<td width="80" align="right">���״̬��</td>
									<td>
										<c:forEach items="${Pledge_statusMap}" var="result">
											<c:if test="${entity.status==result.key}">${result.value}</c:if>
										</c:forEach>
									</td>
								</tr>
								
								<tr>
									<td width="80" align="right">�����̴��룺</td>
									<td width="80">
										${entity.firmID}
									</td>
									<td width="80" align="right">�ֵ��ţ�</td>
									<td width="80">
										${entity.billID}
									</td>
								</tr>
								<tr>
									<td width="80" align="right">�ֿ��ţ�</td>
									<td width="80">
										${entity.stock.warehouseID}
									</td>
									<td width="80" align="right">Ʒ�����ƣ�</td>
									<td width="80">
										${entity.breedName}
									</td>
								</tr>
								<tr>
									<td width="80" align="right">��Ʒ������</td>
									<td width="80">
										${entity.quantity}
									</td>
									<td width="80" align="right">��Ѻ��</td>
									<td width="80">
										${entity.billFund}
									</td>
								</tr>
								
								<tr>
						            <td align="center" colspan="4">
						                <c:if test="${entity.status == 0}">
										<input type="button" name="query" value="ͨ��" style="width:60" class="anniu_btn"
											onclick="javascript:return ok_onclick();"/>
											
										<input type="button" name="query1" value="��ͨ��" style="width:60" class="anniu_btn"
											onclick="javascript:return fail_onclick();"/>
										</c:if>	
										<input type="button" name="close" value="�ر�" class="anniu_btn"
											onclick="javascript:return cancel_onclick();"/>	
									</td>  		                                                           							
								</tr>
								
							</table>
						</fieldset>
						<!--  input type="hidden" name="firmID" value=""/>
						<input type="hidden" name="id" value=""/>
						<input type="hidden" name="status" value=""/>
						<input type="hidden" name="billFund" value=""/>
						-->
					</form>		
				</td>
			</tr>
		</table>

	</body>
</html>
