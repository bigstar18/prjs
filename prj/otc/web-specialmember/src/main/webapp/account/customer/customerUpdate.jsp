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
					<button  class="btn_sec" onClick="updateCustomer()" id="update">修改</button>
				</td>
				<td align="center">
					<button  class="btn_sec" onClick="window.close()">关闭</button>
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
	            alert('客户名称不允许为空');
	            frm.customerName.focus();
			    return false;
	         }
	         
			var obj=document.getElementById("memberNo").value;
	        if(!isNull(obj)){
	            alert('所属会员不允许为空');
	            frm.memberNo.focus();
			    return false;
	         }

			var obj=document.getElementById("papersType").value;
	        if(!isNull(obj)){
	            alert('证件类型不允许为空');
	            frm.papersType.focus();
			    return false;
	         }
			
			/*var obj=document.getElementById("email").value;
	        if(!isNull(obj)){
	            alert('电子邮箱不允许为空');
			    frm.email.focus();
			    return false;
	         }
			
			var obj=document.getElementById("phone").value;
	        if(!isNull(obj)){
	            alert('联系电话不允许为空');
			    frm.phone.focus();
			    return false;
	         }
			
			var obj=document.getElementById("fax").value;
	        if(!isNull(obj)){
	          alert('传真机号不允许为空');
			  frm.fax.focus();
			  return false;
	         }
			var obj=document.getElementById("postCode").value;
	        if(!isNull(obj)){
	          alert('邮编不允许为空');
			  frm.postCode.focus();
			  return false;
	         }
			var obj=document.getElementById("address").value;
	        if(!isNull(obj)){
	          alert('地址不允许为空');
			  frm.address.focus();
			  return false;
	         }*/
			frm.submit();
		}
		function checkMemberNo(){}
	</script>
<%@ include file="/public/footInc.jsp"%>
