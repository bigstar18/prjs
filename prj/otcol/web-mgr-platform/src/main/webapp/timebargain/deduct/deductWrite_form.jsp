<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<html>
	<head>
		<LINK REL="stylesheet" type="text/css"
			href="<c:url value="/timebargain/styles/common.css"/>" />
		<script language="JavaScript"
			src="<c:url value="/timebargain/scripts/global.js"/>"></script>
		<title>
		</title>

<script type="text/javascript">
//ҳ��װ��ʱ���� 
function window_onload()
{
    highlightFormElements();
    
}
function save_onclick(){
	if (confirm("��ȷ��Ҫ�ύ��")) {
		if (deductForm.customerID.value == ""){
			alert("�������벻��Ϊ�գ�");
			return false;
		}
		if (deductForm.commodityID.value == ""){
			alert("��Ʒ���벻��Ϊ�գ�");
			return false;
		}
		if (deductForm.BS_Flag.value == ""){
			alert("��������Ϊ�գ�");
			return false;
		}
		if (deductForm.deductPrice.value == ""){
			alert("ί�м۸���Ϊ�գ�");
			return false;
		}
		if (deductForm.keepQty.value == ""){
			alert("ί����������Ϊ�գ�");
			return false;
		}
		deductForm.submit();
	}
}
</script>
	</head>

	<body leftmargin="0" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="400" width="700" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/deduct/deduct.do?funcflg=saveDeductWrite" method="POST" >
						<fieldset>
							<legend class="common">
								<b>ǿ��ί��¼��
								</b>
							</legend>
							<table border="0" align="center" cellpadding="2" cellspacing="0"
								class="common">
	    <tr>	     
			<td align="right"><label>�������룺</label></td>
			<td>
				<html:text property="customerID" maxlength="16"  styleClass="text" style="width:105;ime-mode:disabled" onkeypress="onlyNumberAndCharInput()" />
				<span class="req">*</span>			
			</td> 
		
            <td align="right"><label>��Ʒ���룺</label></td>
			<td>
			    <html:text property="commodityID" maxlength="16"   style="width:105;ime-mode:disabled"onkeypress="onlyNumberAndCharInput()"/>                            
				<span class="req">*</span>
			</td>
            <td align="right"><label>������</label></td>
			<td>
				<html:select property="BS_Flag" style="width:105">
				    <html:option value=""></html:option>
					<html:option value="1">���</html:option>
					<html:option value="2">����</html:option>
			    </html:select>
				<span class="req">*</span>
			</td>
            	
		</tr>																
		<tr>
			
			<td align="right"><label>ί�м۸�</label></td>
			<td>
				<html:text property="deductPrice" maxlength="16"  style="ime-mode:disabled;width:105"  onkeypress="return numberPass()" styleClass="Number"/>
				<span class="req">*</span>
			</td>	
			<td align="right"><label>ί��������</label></td>
			<td>
				<html:text property="keepQty" maxlength="16" onkeypress="return numberPass()" style="ime-mode:disabled;width:105" styleClass="Number"/>
				<span class="req">*</span>
			</td>					
	     </tr>
		 
	     <tr>	       							
			<td colspan="6" align="center">
			  <html:button  property="order" styleClass="button" onclick="javascript:return save_onclick();">ȷ��</html:button>
			  <html:reset  property="reset" styleClass="button">����</html:reset>	
			  	  
			</td>
		</tr>
		
							</table>
						</fieldset>
					</html:form>
				</td>
			</tr>
		</table>

<%@ include file="/timebargain/common/messages.jsp" %>
	</body>
</html>
