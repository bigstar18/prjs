<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title></title>
	</head>
	<body leftmargin="0" topmargin="0" style="overflow-y:hidden">
		<form name="frm" action="${basePath}/account/customer/update.action"  method="post" targetType="hidden"> 
		<div class="div_scro">
			<table border="0" width="600" align="center">
				<tr height="30"></tr>
				<tr>
					<td>
							<span><%@include file="commonTable.jsp" %></span>
					</td>
				</tr>
			</table>
			</div>
			<table cellspacing="0" cellpadding="0" border="0" width="520" align="center" class="tab_padmax">
			<tr>
				<td  align="center">
					<button  class="btn_sec" onClick="updateCustomer()" id="update">�޸�</button>
				</td>
				<td align="center">
					<button  class="btn_sec" onClick="window.close()">�ر�</button>
				</td>
			</tr>
		</table>
		</form>
	</body>
</html>
	<script type="text/javascript">
		document.getElementById("memberNo").disabled="disabled";
		document.getElementById("shortId").readOnly=true;
		function updateCustomer(){
			var obj=document.getElementById("customerName").value;
	        if(!isNull(obj)){
	            alert('�ͻ����Ʋ�����Ϊ��');
	            frm.customerName.focus();
			    return false;
	         }
	         
			var obj=document.getElementById("memberNo").value;
	        if(!isNull(obj)){
	            alert('������Ա������Ϊ��');
	            frm.memberNo.focus();
			    return false;
	         }

			var obj=document.getElementById("papersType").value;
	        if(!isNull(obj)){
	            alert('֤�����Ͳ�����Ϊ��');
	            frm.papersType.focus();
			    return false;
	         }
			
			/*var obj=document.getElementById("email").value;
	        if(!isNull(obj)){
	            alert('�������䲻����Ϊ��');
			    frm.email.focus();
			    return false;
	         }
			
			var obj=document.getElementById("phone").value;
	        if(!isNull(obj)){
	            alert('��ϵ�绰������Ϊ��');
			    frm.phone.focus();
			    return false;
	         }
			
			var obj=document.getElementById("fax").value;
	        if(!isNull(obj)){
	          alert('������Ų�����Ϊ��');
			  frm.fax.focus();
			  return false;
	         }
			var obj=document.getElementById("postCode").value;
	        if(!isNull(obj)){
	          alert('�ʱ಻����Ϊ��');
			  frm.postCode.focus();
			  return false;
	         }
			var obj=document.getElementById("address").value;
	        if(!isNull(obj)){
	          alert('��ַ������Ϊ��');
			  frm.address.focus();
			  return false;
	         }*/
			frm.submit();
		}
		function checkMemberNo(){}
	</script>
<%@ include file="/public/footInc.jsp"%>
